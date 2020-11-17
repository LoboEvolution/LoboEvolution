package org.loboevolution.pdfview.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.pdf.PDFViewer;

public class FitHeightAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private final PDFViewer dialog;
	
	public FitHeightAction(PDFViewer dialog) {
		super("Fit Height", dialog.getIcon("/org/loboevolution/images/fit-height.png"));
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.doFit(false, false);

	}
}