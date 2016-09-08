/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.clientlet.Clientlet#process(org.lobobrowser.clientlet.
	 * ClientletContext)
	 */
	@Override
	public void process(ClientletContext context) throws ClientletException {
		try {
			InputStream in = context.getResponse().getInputStream();
			try {
				String text = IORoutines.loadAsText(in, "UTF-8");
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
