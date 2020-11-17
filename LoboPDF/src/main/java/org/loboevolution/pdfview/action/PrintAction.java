package org.loboevolution.pdfview.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.pdf.PDFViewer;

public class PrintAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private final PDFViewer dialog;
	
	public PrintAction(PDFViewer dialog) {
		super("Print...", dialog.getIcon("/org/loboevolution/images/print.png"));
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.doPrint();

	}
}