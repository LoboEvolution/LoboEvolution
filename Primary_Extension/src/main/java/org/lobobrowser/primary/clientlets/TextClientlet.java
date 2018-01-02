/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.util.io.IORoutines;

/**
 * The Class TextClientlet.
 */
public class TextClientlet implements Clientlet {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(TextClientlet.class);

	/**
	 * Instantiates a new text clientlet.
	 */

	private String extension;

	public TextClientlet(String extension) {
		this.extension = extension;
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
				RSyntaxTextArea textArea = new RSyntaxTextArea(text);

				logger.error("extension: " + extension);

				switch (extension) {
				case "css":
					textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSS);
					break;
				case "js":
					textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
					break;
				case "xml":
					textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
					break;
				case "xaml":
					textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
					break;
				default:
					break;
				}

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

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
}
