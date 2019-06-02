package org.lobobrowser.util;

/**
 * A task that can be used in a thread or thread pool. The caller can wait for
 * the task to finish by joining it.
 */
public abstract class JoinableTask implements SimpleThreadPoolTask {
	private boolean done = false;

	@Override
	public void cancel() {
		forceDone();
	}

	protected abstract void execute();

	public final void forceDone() {
		synchronized (this) {
			this.done = true;
			notifyAll();
		}
	}

	public void join() throws InterruptedException {
		synchronized (this) {
			while (!this.done) {
				this.wait();
			}
		}
	}

	@Override
	public final void run() {
		try {
			execute();
		} finally {
			synchronized (this) {
				this.done = true;
				notifyAll();
			}
		}
	}
}
