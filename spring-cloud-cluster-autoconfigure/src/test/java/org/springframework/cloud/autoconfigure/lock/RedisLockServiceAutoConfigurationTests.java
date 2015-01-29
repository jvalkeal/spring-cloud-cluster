/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.cloud.autoconfigure.lock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.boot.autoconfigure.redis.RedisAutoConfiguration;
import org.springframework.boot.test.EnvironmentTestUtils;

/**
 * Tests for {@link RedisLockServiceAutoConfiguration}.
 * 
 * @author Janne Valkealahti
 *
 */
public class RedisLockServiceAutoConfigurationTests extends AbstractLockAutoConfigurationTests {

	@Test
	public void testDefaults() {
		EnvironmentTestUtils.addEnvironment(this.context);
		context.register(RedisAutoConfiguration.class, RedisLockServiceAutoConfiguration.class);
		context.refresh();
		
		assertThat(context.containsBean("redisLockService"), is(true));		
	}

	@Test
	public void testDisabled() throws Exception {
		EnvironmentTestUtils
				.addEnvironment(
						this.context,
						"spring.cloud.cluster.redis.lock.enabled:false");
		context.register(RedisAutoConfiguration.class, RedisLockServiceAutoConfiguration.class);
		context.refresh();
		
		assertThat(context.containsBean("redisLockService"), is(false));
	}

	@Test
	public void testGlobalLeaderDisabled() throws Exception {
		EnvironmentTestUtils
				.addEnvironment(
						this.context,
						"spring.cloud.cluster.lock.enabled:false",
						"spring.cloud.cluster.redis.lock.enabled:true");
		context.register(RedisAutoConfiguration.class, RedisLockServiceAutoConfiguration.class);
		context.refresh();
		
		assertThat(context.containsBean("redisLockService"), is(false));
	}
	
}
