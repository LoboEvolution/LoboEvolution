package org.lobobrowser.primary.clientlets.pdf;

import java.net.URL;

import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.primary.gui.pdf.PdfDialog;

public class PdfClientlet implements Clientlet {

	public PdfClientlet() {
		super();
	}

	@Override
	public void process(ClientletContext context) throws ClientletException {
		ClientletResponse response = context.getResponse();
		URL url = response.getResponseURL();
		PdfDialog viewer;
		viewer = new PdfDialog(true);
		String fileName = url.toString();
		if (fileName != null) {
			viewer.doOpen(fileName);
		}
	}
}
