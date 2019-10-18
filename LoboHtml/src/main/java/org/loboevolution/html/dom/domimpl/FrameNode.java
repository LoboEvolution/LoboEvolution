package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.BrowserFrame;

/**
 * Tag interface for frame nodes.
 */
public interface FrameNode {
	BrowserFrame getBrowserFrame();

	void setBrowserFrame(BrowserFrame frame);
}
