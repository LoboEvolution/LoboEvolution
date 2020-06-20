package org.loboevolution.pdfview.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;

import org.loboevolution.pdf.PDFViewer;

/**
 * The Class ThumbAction.
 */
public class ThumbAction extends AbstractAction implements PropertyChangeListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The is open. */
	private boolean isOpen = true;
	
	private PDFViewer dialog;

	/**
	 * Instantiates a new thumb action.
	 */
	public ThumbAction(PDFViewer dialog) {
		super("Hide thumbnails");
		this.dialog = dialog;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
	 * PropertyChangeEvent )
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		int v = ((Integer) evt.getNewValue()).intValue();
		if (v <= 1) {
			isOpen = false;
			putValue(ACTION_COMMAND_KEY, "Show thumbnails");
			putValue(NAME, "Show thumbnails");
		} else {
			isOpen = true;
			putValue(ACTION_COMMAND_KEY, "Hide thumbnails");
			putValue(NAME, "Hide thumbnails");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
	 * ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		dialog.doThumbs(!isOpen);
	}
}
