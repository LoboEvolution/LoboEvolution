package org.loboevolution.pdfview.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.pdf.PDFViewer;

public class OutlineAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private PDFViewer dialog;
	
	public OutlineAction(PDFViewer dialog) {
		super("Open Outline");
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.doOutline();

	}
}