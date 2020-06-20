package org.loboevolution.pdfview.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.pdf.PDFViewer;

public class CloseOutlineAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private PDFViewer dialog;
	
	public CloseOutlineAction(PDFViewer dialog) {
		super("Close Outline");
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.doCloseOutline();
	}
}