package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.BrowserFrame;

/**
 * Tag interface for frame nodes.
 */
public interface FrameNode {
	BrowserFrame getBrowserFrame();

	void setBrowserFrame(BrowserFrame frame);
}
