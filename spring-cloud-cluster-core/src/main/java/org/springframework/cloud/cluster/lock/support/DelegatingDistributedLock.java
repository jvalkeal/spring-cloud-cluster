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
package org.springframework.cloud.cluster.lock.support;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.springframework.cloud.cluster.lock.DistributedLock;
import org.springframework.util.Assert;

/**
 * {@link DistributedLock} which simply delegates to a {@link Lock}.
 * 
 * @author Janne Valkealahti
 *
 */
public class DelegatingDistributedLock extends AbstractDistributedLock {
	
	private final Lock lock;
	
	/**
	 * Instantiates a new delegating distributed lock.
	 *
	 * @param lockKey the locking key
	 * @param lock the lock
	 */
	public DelegatingDistributedLock(String lockKey, Lock lock) {
		super(lockKey);
		Assert.notNull(lock, "Lock must be set");
		this.lock = lock;
	}

	@Override
	public void lock() {
		lock.lock();
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		lock.lockInterruptibly();
	}

	@Override
	public boolean tryLock() {
		return lock.tryLock();
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		return lock.tryLock(time, unit);
	}

	@Override
	public void unlock() {
		lock.unlock();
	}

	@Override
	public Condition newCondition() {
		return lock.newCondition();
	}

}
