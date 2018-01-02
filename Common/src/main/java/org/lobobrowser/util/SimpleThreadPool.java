/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A thread pool that allows cancelling all running tasks without shutting down
 * the thread pool.
 */
public class SimpleThreadPool {
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(SimpleThreadPool.class);
	/** The task list. */
	private final LinkedList<SimpleThreadPoolTask> taskList = new LinkedList<SimpleThreadPoolTask>();
	/** The running set. */
	private final Set<SimpleThreadPoolTask> runningSet = new HashSet<SimpleThreadPoolTask>();
	/** The min threads. */
	private final int minThreads;
	/** The max threads. */
	private final int maxThreads;
	/** The name. */
	private final String name;
	/** The idle alive millis. */
	private final int idleAliveMillis;
	/** The task monitor. */
	private final Object taskMonitor = new Object();
	/** The thread group. */
	private final ThreadGroup threadGroup;
	/** The num threads. */
	private int numThreads = 0;
	/** The num idle threads. */
	private int numIdleThreads = 0;
	/** The thread number. */
	private int threadNumber = 0;

	/**
	 * Instantiates a new simple thread pool.
	 *
	 * @param name
	 *            the name
	 * @param minShrinkToThreads
	 *            the min shrink to threads
	 * @param maxThreads
	 *            the max threads
	 * @param idleAliveMillis
	 *            the idle alive millis
	 */
	public SimpleThreadPool(String name, int minShrinkToThreads, int maxThreads, int idleAliveMillis) {
		this.minThreads = minShrinkToThreads;
		this.maxThreads = maxThreads;
		this.idleAliveMillis = idleAliveMillis;
		this.name = name;
		// Thread group needed so item requests
		// don't get assigned sub-thread groups.
		// TODO: Thread group needs to be thought through. It's retained in
		// memory, and we need to return the right one in the GUI thread as
		// well.
		this.threadGroup = null; // new ThreadGroup(name);
	}

	/**
	 * Schedule.
	 *
	 * @param task
	 *            the task
	 */
	public void schedule(SimpleThreadPoolTask task) {
		if (task == null) {
			throw new IllegalArgumentException("null task");
		}
		Object monitor = this.taskMonitor;
		synchronized (monitor) {
			if (this.numIdleThreads == 0) {
				this.addThreadImpl();
			}
			this.taskList.add(task);
			monitor.notify();
		}
	}

	/**
	 * Cancel.
	 *
	 * @param task
	 *            the task
	 */
	public void cancel(SimpleThreadPoolTask task) {
		synchronized (this.taskMonitor) {
			this.taskList.remove(task);
		}
		task.cancel();
	}

	/**
	 * Adds the thread impl.
	 */
	private void addThreadImpl() {
		if (this.numThreads < this.maxThreads) {
			Thread t = new Thread(this.threadGroup, new ThreadRunnable(), this.name + this.threadNumber++);
			t.setDaemon(true);
			t.start();
			this.numThreads++;
		}
	}

	/**
	 * Cancels all waiting tasks and any currently running task.
	 */
	public void cancelAll() {
		synchronized (this.taskMonitor) {
			this.taskList.clear();
			Iterator i = this.runningSet.iterator();
			while (i.hasNext()) {
				((SimpleThreadPoolTask) i.next()).cancel();
			}
		}
	}

	/**
	 * The Class ThreadRunnable.
	 */
	private class ThreadRunnable implements Runnable {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			Object monitor = taskMonitor;
			LinkedList tl = taskList;
			Set<SimpleThreadPoolTask> rs = runningSet;
			int iam = idleAliveMillis;
			SimpleThreadPoolTask task = null;
			for (;;) {
				try {
					synchronized (monitor) {
						if (task != null) {
							rs.remove(task);
						}
						numIdleThreads++;
						try {
							long waitBase = System.currentTimeMillis();
							INNER: while (tl.isEmpty()) {
								long maxWait = iam - (System.currentTimeMillis() - waitBase);
								if (maxWait <= 0) {
									if (numThreads > minThreads) {
										// Should be only way to exit thread.
										numThreads--;
										return;
									} else {
										waitBase = System.currentTimeMillis();
										continue INNER;
									}
								}
								monitor.wait(maxWait);
							}
						} finally {
							numIdleThreads--;
						}
						task = taskList.removeFirst();
						rs.add(task);
					}
					Thread currentThread = Thread.currentThread();
					String baseName = currentThread.getName();
					try {
						try {
							currentThread.setName(baseName + ":" + task.toString());
						} catch (Throwable thrown) {
							logger.error("run(): Unable to set task name.", thrown);
						}
						try {
							task.run();
						} catch (Throwable thrown) {
							logger.log(Level.ERROR, "run(): Error in task: " + task + ".", thrown);
						}
					} finally {
						currentThread.setName(baseName);
					}
				} catch (Throwable thrown) {
					logger.log(Level.ERROR, "run(): Error in thread pool: " + SimpleThreadPool.this.name + ".", thrown);
				}
			}
		}
	}
}
