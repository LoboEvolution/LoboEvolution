package org.loboevolution.pdf;

import java.io.Serializable;

/**
 * Combines numeric key presses to build a multi-digit page number.
 */
class PageBuilder implements Serializable, Runnable {

	private static final long serialVersionUID = 1L;

	/** The value. */
	private int value = 0;

	/** The timeout. */
	private long timeout;

	/** The anim. */
	private transient Thread anim;
	
	private PDFViewer PDFViewer;
	
	public PageBuilder(PDFViewer PDFViewer) {
		this.PDFViewer = PDFViewer;
	}

	/**
	 * add the digit to the page number and start the timeout thread.
	 *
	 * @param keyval
	 *            the keyval
	 */
	public synchronized void keyTyped(int keyval) {
		value = value * 10 + keyval;
		timeout = System.currentTimeMillis() + 500;
		if (anim == null) {
			anim = new Thread(this);
			anim.setName(getClass().getName());
			anim.start();
		}
	}

	/**
	 * waits for the timeout, and if time expires, go to the specified page
	 * number.
	 */
	@Override
	public void run() {
		long now;
		long then;
		synchronized (this) {
			now = System.currentTimeMillis();
			then = timeout;
		}
		while (now < then) {
			try {
				Thread.sleep(timeout - now);
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
			synchronized (this) {
				now = System.currentTimeMillis();
				then = timeout;
			}
		}
		synchronized (this) {
			PDFViewer.gotoPage(value - 1);
			anim = null;
			value = 0;
		}
	}
}
