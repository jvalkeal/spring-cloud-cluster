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
package org.springframework.cloud.cluster.lock;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;
import org.springframework.cloud.cluster.lock.support.DefaultLockRegistry;
import org.springframework.cloud.cluster.lock.support.DefaultLockServiceLocator;
import org.springframework.cloud.cluster.lock.support.DelegatingDistributedLock;

public class LockTests {

	@Test
	public void testFoo() {
		DefaultLockServiceLocator locator = new DefaultLockServiceLocator(new TestLockService());
		LockRegistry registry = new DefaultLockRegistry(locator);
		DistributedLock lock = registry.get("fookey");
		assertThat(lock, notNullValue());
	}
	
	private static class TestLockService implements LockService {
		
		private Map<String, DistributedLock> locks = new HashMap<String, DistributedLock>();

		@Override
		public synchronized DistributedLock obtain(String lockKey) {
			DistributedLock lock = locks.get(lockKey);
			if (lock == null) {
				ReentrantLock l = new ReentrantLock();
				lock = new DelegatingDistributedLock(lockKey, l);
				locks.put(lockKey, lock);
			}
			return lock;
		}
		
	}
	
}
