package org.lobobrowser.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A thread pool that allows cancelling all running tasks without shutting down
 * the thread pool.
 */
public class SimpleThreadPool {
	private class ThreadRunnable implements Runnable {
		@Override
		public void run() {
			final Object monitor = SimpleThreadPool.this.taskMonitor;
			final LinkedList tl = SimpleThreadPool.this.taskList;
			final Set rs = SimpleThreadPool.this.runningSet;
			final int iam = SimpleThreadPool.this.idleAliveMillis;
			SimpleThreadPoolTask task = null;
			for (;;) {
				try {
					synchronized (monitor) {
						if (task != null) {
							rs.remove(task);
						}
						SimpleThreadPool.this.numIdleThreads++;
						try {
							long waitBase = System.currentTimeMillis();
							INNER: while (tl.isEmpty()) {
								final long maxWait = iam - (System.currentTimeMillis() - waitBase);
								if (maxWait <= 0) {
									if (SimpleThreadPool.this.numThreads > SimpleThreadPool.this.minThreads) {
										// Should be only way to exit thread.
										SimpleThreadPool.this.numThreads--;
										return;
									} else {
										waitBase = System.currentTimeMillis();
										continue INNER;
									}
								}
								monitor.wait(maxWait);
							}
						} finally {
							SimpleThreadPool.this.numIdleThreads--;
						}
						task = (SimpleThreadPoolTask) SimpleThreadPool.this.taskList.removeFirst();
						rs.add(task);
					}
					final Thread currentThread = Thread.currentThread();
					final String baseName = currentThread.getName();
					try {
						try {
							currentThread.setName(baseName + ":" + task.toString());
						} catch (final Throwable thrown) {
							logger.log(Level.WARNING, "run(): Unable to set task name.", thrown);
						}
						try {
							task.run();
						} catch (final Throwable thrown) {
							logger.log(Level.SEVERE, "run(): Error in task: " + task + ".", thrown);
						}
					} finally {
						currentThread.setName(baseName);
					}
				} catch (final Throwable thrown) {
					logger.log(Level.SEVERE, "run(): Error in thread pool: " + SimpleThreadPool.this.name + ".",
							thrown);
				}
			}
		}
	}

	private static final Logger logger = Logger.getLogger(SimpleThreadPool.class.getName());
	private final int idleAliveMillis;
	private final int maxThreads;
	private final int minThreads;
	private final String name;
	private int numIdleThreads = 0;
	private int numThreads = 0;
	private final Set runningSet = new HashSet();

	private final LinkedList taskList = new LinkedList();
	private final Object taskMonitor = new Object();
	private final ThreadGroup threadGroup;

	private int threadNumber = 0;

	public SimpleThreadPool(String name, int minShrinkToThreads, int maxThreads, int idleAliveMillis) {
		this.minThreads = minShrinkToThreads;
		this.maxThreads = maxThreads;
		this.idleAliveMillis = idleAliveMillis;
		this.name = name;
		// Thread group needed so item requests
		// don't get assigned sub-thread groups.
		// TODO: Thread group needs to be thought through. It's retained in
		// memory, and we need to return the right one in the GUI thread as well.
		this.threadGroup = null; // new ThreadGroup(name);
	}

	private void addThreadImpl() {
		if (this.numThreads < this.maxThreads) {
			final Thread t = new Thread(this.threadGroup, new ThreadRunnable(), this.name + this.threadNumber++);
			t.setDaemon(true);
			t.start();
			this.numThreads++;
		}
	}

	public void cancel(SimpleThreadPoolTask task) {
		synchronized (this.taskMonitor) {
			this.taskList.remove(task);
		}
		task.cancel();
	}

	/**
	 * Cancels all waiting tasks and any currently running task.
	 */
	public void cancelAll() {
		synchronized (this.taskMonitor) {
			this.taskList.clear();
			final Iterator i = this.runningSet.iterator();
			while (i.hasNext()) {
				((SimpleThreadPoolTask) i.next()).cancel();
			}
		}
	}

	public void schedule(SimpleThreadPoolTask task) {
		if (task == null) {
			throw new IllegalArgumentException("null task");
		}
		final Object monitor = this.taskMonitor;
		synchronized (monitor) {
			if (this.numIdleThreads == 0) {
				addThreadImpl();
			}
			this.taskList.add(task);
			monitor.notify();
		}
	}
}
