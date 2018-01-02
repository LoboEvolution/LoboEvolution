/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.clientlets.pdf;

import java.net.URL;

import org.loboevolution.clientlet.Clientlet;
import org.loboevolution.clientlet.ClientletContext;
import org.loboevolution.clientlet.ClientletException;
import org.loboevolution.clientlet.ClientletResponse;
import org.loboevolution.primary.gui.pdf.PdfDialog;

/**
 * The Class PdfClientlet.
 */
public class PdfClientlet implements Clientlet {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.clientlet.Clientlet#process(org.loboevolution.clientlet.
	 * ClientletContext)
	 */
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
