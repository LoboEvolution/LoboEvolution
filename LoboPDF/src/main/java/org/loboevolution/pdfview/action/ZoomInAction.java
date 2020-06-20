package org.loboevolution.pdfview.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.pdf.PDFViewer;

public class ZoomInAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private PDFViewer dialog;
	
	private float zoomfactor;

	public ZoomInAction(PDFViewer dialog, float factor) {
		super("Zoom in", dialog.getIcon("/org/loboevolution/images/zoomin.png"));
		zoomfactor = factor;
		this.dialog = dialog;
	}

	public void actionPerformed(ActionEvent evt) {
		dialog.doZoom(zoomfactor);
	}
}