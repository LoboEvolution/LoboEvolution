/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

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
	
	private final PDFViewer PDFViewer;
	
	/**
	 * <p>Constructor for PageBuilder.</p>
	 *
	 * @param PDFViewer a {@link org.loboevolution.pdf.PDFViewer} object.
	 */
	public PageBuilder(final PDFViewer PDFViewer) {
		this.PDFViewer = PDFViewer;
	}

	/**
	 * add the digit to the page number and start the timeout thread.
	 *
	 * @param keyval
	 *            the keyval
	 */
	public synchronized void keyTyped(final int keyval) {
		value = value * 10 + keyval;
		timeout = System.currentTimeMillis() + 500;
		if (anim == null) {
			anim = new Thread(this);
			anim.setName(getClass().getName());
			anim.start();
		}
	}

	/**
	 * {@inheritDoc}
	 *
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
			} catch (final InterruptedException ie) {
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
