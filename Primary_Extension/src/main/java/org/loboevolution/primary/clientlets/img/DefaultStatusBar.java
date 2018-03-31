package org.loboevolution.primary.clientlets.img;

/**
 * A default status bar implementation that displays the current mouse position
 * (in pixel coordinates) and the colour of the pixel under the cursor.
 * 
 * @author Kazó Csaba
 */
public class DefaultStatusBar extends PixelInfoStatusBar implements ImageMouseMotionListener {

	@Override
	public void mouseMoved(ImageMouseEvent e) {
		setPixel(e.getX(), e.getY());
	}

	@Override
	public void mouseExited(ImageMouseEvent e) {
		setPixel(-1, -1);
	}

	@Override
	public void mouseEntered(ImageMouseEvent e) {
	}

	@Override
	public void mouseDragged(ImageMouseEvent e) {
	}

	@Override
	protected void register(ImageViewer viewer) {
		super.register(viewer);
		viewer.addImageMouseMotionListener(this);
	}

	@Override
	protected void unregister(ImageViewer viewer) {
		super.unregister(viewer);
		viewer.removeImageMouseMotionListener(this);
	}

}
