package org.loboevolution.html.node.js;

/**
 * A screen, usually the one on which the current window is being rendered, and
 * is obtained using window.screen.
 */
public interface Screen {

	int getAvailHeight();

	int getAvailWidth();

	int getColorDepth();

	int getHeight();

	int getPixelDepth();

	int getWidth();

}
