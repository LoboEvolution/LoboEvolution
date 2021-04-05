package org.loboevolution.pdfview.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.pdf.PDFViewer;

/**
 * <p>OutlineAction class.</p>
 *
  *
  *
 */
public class OutlineAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private final PDFViewer dialog;
	
	/**
	 * <p>Constructor for OutlineAction.</p>
	 *
	 * @param dialog a {@link org.loboevolution.pdf.PDFViewer} object.
	 */
	public OutlineAction(PDFViewer dialog) {
		super("Open Outline");
		this.dialog = dialog;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.doOutline();

	}
}
