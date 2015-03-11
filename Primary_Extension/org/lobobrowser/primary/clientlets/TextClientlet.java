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
/*
 * Created on Jul 10, 2005
 */
package org.lobobrowser.primary.clientlets;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.util.io.IORoutines;


/**
 * The Class TextClientlet.
 */
public class TextClientlet implements Clientlet {
	
	/**
	 * Instantiates a new text clientlet.
	 */
	public TextClientlet() {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.Clientlet#process(org.lobobrowser.clientlet.ClientletContext)
	 */
	public void process(ClientletContext context) throws ClientletException {
		try {
			InputStream in = context.getResponse().getInputStream();
			try {
				String text = IORoutines.loadAsText(in, "ISO-8859-1");
				JTextArea textArea = new JTextArea(text);
				textArea.setEditable(false);
				JScrollPane pane = new JScrollPane(textArea);
				context.setResultingContent(pane);
			} finally {
				in.close();
			}
		} catch (IOException ioe) {
			throw new ClientletException(ioe);
		}
	}
}
