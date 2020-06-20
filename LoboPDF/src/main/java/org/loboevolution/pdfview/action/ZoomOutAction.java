package org.loboevolution.pdfview.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.pdf.PDFViewer;

public class ZoomOutAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private PDFViewer dialog;
	
	private float zoomfactor;

	public ZoomOutAction(PDFViewer dialog, float factor) {
		super("Zoom out", dialog.getIcon("/org/loboevolution/images/zoomout.png"));
		zoomfactor = factor;
		this.dialog = dialog;
	}

	public void actionPerformed(ActionEvent evt) {
		dialog.doZoom(zoomfactor);
	}
}