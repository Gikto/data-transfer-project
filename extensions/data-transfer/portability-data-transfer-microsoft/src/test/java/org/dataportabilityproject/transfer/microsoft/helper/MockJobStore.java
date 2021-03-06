/*
 * Copyright 2018 The Data Transfer Project Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dataportabilityproject.transfer.microsoft.helper;

import org.dataportabilityproject.spi.cloud.storage.JobStore;
import org.dataportabilityproject.spi.cloud.types.JobAuthorization;
import org.dataportabilityproject.spi.cloud.types.PortabilityJob;
import org.dataportabilityproject.types.transfer.models.DataModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** An implementation for testing. */
public class MockJobStore implements JobStore {
  private final Map<UUID, DataModel> testData = new HashMap<>();
  private final Map<String, InputStream> keyedData = new HashMap<>();

  @Override
  public <T extends DataModel> T findData(Class<T> type, UUID id) {
    return type.cast(testData.get(id));
  }

  @Override
  public void createJob(UUID jobId, PortabilityJob job) throws IOException {}

  @Override
  public void updateJob(UUID jobId, PortabilityJob job) throws IOException {}

  @Override
  public void updateJob(UUID jobId, PortabilityJob job, JobUpdateValidator validator)
      throws IOException {}

  @Override
  public void remove(UUID jobId) throws IOException {}

  @Override
  public PortabilityJob findJob(UUID jobId) {
    return null;
  }

  @Override
  public UUID findFirst(JobAuthorization.State jobState) {
    return null;
  }

  @Override
  public <T extends DataModel> void create(UUID jobId, T model) {
    testData.put(jobId, model);
  }

  @Override
  public <T extends DataModel> void update(UUID jobId, T model) {
    if (!testData.containsKey(jobId)) {
      throw new AssertionError("Data does not exist: " + jobId);
    }
    testData.put(jobId, model);
  }

  @Override
  public void create(UUID jobId, String key, InputStream stream) {
     keyedData.put(jobId.toString()+":"+ key, stream);
  }

  @Override
  public InputStream getStream(UUID jobId, String key) {
    return keyedData.get(jobId.toString()+":"+ key);
  }
}
