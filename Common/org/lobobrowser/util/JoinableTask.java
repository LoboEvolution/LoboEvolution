package org.lobobrowser.util;


/**
 * A task that can be used in a thread or thread pool. The caller can wait for
 * the task to finish by joining it.
 */
public abstract class JoinableTask implements SimpleThreadPoolTask {
	
	/** The done. */
	private boolean done = false;

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public final void run() {
		try {
			this.execute();
		} finally {
			synchronized (this) {
				this.done = true;
				this.notifyAll();
			}
		}
	}

	/**
	 * Force done.
	 */
	public final void forceDone() {
		synchronized (this) {
			this.done = true;
			this.notifyAll();
		}
	}

	/**
	 * Join.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void join() throws InterruptedException {
		synchronized (this) {
			while (!this.done) {
				this.wait();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.util.SimpleThreadPoolTask#cancel()
	 */
	public void cancel() {
		this.forceDone();
	}

	/**
	 * Execute.
	 */
	protected abstract void execute();
}
