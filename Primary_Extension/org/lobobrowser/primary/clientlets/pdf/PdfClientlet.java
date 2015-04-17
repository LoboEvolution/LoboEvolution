/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.clientlets.pdf;

import java.net.URL;

import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.primary.gui.pdf.PdfDialog;

/**
 * The Class PdfClientlet.
 */
public class PdfClientlet implements Clientlet {

    /**
     * Instantiates a new pdf clientlet.
     */
    public PdfClientlet() {
        super();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.Clientlet#process(org.lobobrowser.clientlet.
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
