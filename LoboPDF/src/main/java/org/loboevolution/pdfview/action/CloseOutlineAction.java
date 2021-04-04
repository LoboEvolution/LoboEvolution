package org.loboevolution.pdfview.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.pdf.PDFViewer;

/**
 * <p>CloseOutlineAction class.</p>
 *
  *
  *
 */
public class CloseOutlineAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private final PDFViewer dialog;
	
	/**
	 * <p>Constructor for CloseOutlineAction.</p>
	 *
	 * @param dialog a {@link org.loboevolution.pdf.PDFViewer} object.
	 */
	public CloseOutlineAction(PDFViewer dialog) {
		super("Close Outline");
		this.dialog = dialog;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.doCloseOutline();
	}
}
