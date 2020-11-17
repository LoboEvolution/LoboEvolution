package org.loboevolution.pdfview.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.pdf.PDFViewer;

public class FitAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private final PDFViewer dialog;
	
	public FitAction(PDFViewer dialog) {
		super("Fit", dialog.getIcon("/org/loboevolution/images/fit.png"));
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.doFit(true, true);

	}
}