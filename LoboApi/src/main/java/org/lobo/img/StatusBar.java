package org.lobo.img;

import javax.swing.JComponent;

/**
 * Abstract superclass for status bars of the image viewer.
 * 
 * @author Kazo Csaba
 */
public abstract class StatusBar {
	/**
	 * The ImageViewer associated with this status bar.
	 */
	private ImageViewer imageViewer;

	/**
	 * Returns the status bar component that can be added to the image viewer GUI.
	 * 
	 * @return the status bar component
	 */
	public abstract JComponent getComponent();

	/**
	 * Returns the image viewer that this status bar belongs to.
	 * 
	 * @return the current image viewer, or <code>null</code> if there is none
	 */
	public final ImageViewer getImageViewer() {
		return imageViewer;
	}

	public final void setImageViewer(ImageViewer imageViewer) {
		if (this.imageViewer != null)
			unregister(this.imageViewer);
		this.imageViewer = imageViewer;
		if (this.imageViewer != null)
			register(this.imageViewer);
	}

	/**
	 * Called when this status bar is added to an image viewer. Subclasses can
	 * override this method to register listeners.
	 * 
	 * @param viewer
	 *            the new viewer associated with this status bar
	 */
	protected void register(ImageViewer viewer) {
	}

	/**
	 * Called when this status bar is removed from an image viewer. Subclasses can
	 * override this method to remove listeners.
	 * 
	 * @param viewer
	 *            the viewer that this status bar is removed from
	 */
	protected void unregister(ImageViewer viewer) {
	}
}
