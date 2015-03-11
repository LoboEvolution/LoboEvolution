package org.lobobrowser.html.dombl;

import org.lobobrowser.html.BrowserFrame;


/**
 * Tag interface for frame nodes.
 */
public interface FrameNode {
	
	/**
	 * Gets the browser frame.
	 *
	 * @return the browser frame
	 */
	public BrowserFrame getBrowserFrame();

	/**
	 * Sets the browser frame.
	 *
	 * @param frame the new browser frame
	 */
	public void setBrowserFrame(BrowserFrame frame);
}
