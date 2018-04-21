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
/*
 * Created on Jul 10, 2005
 */
package org.loboevolution.primary.clientlets;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.JScrollPane;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.loboevolution.clientlet.Clientlet;
import org.loboevolution.clientlet.ClientletContext;
import org.loboevolution.clientlet.ClientletException;
import org.loboevolution.util.io.IORoutines;

/**
 * The Class TextClientlet.
 */
public class TextClientlet implements Clientlet {

	private String extension;

	/**
	 * Instantiates a new text clientlet.
	 */
	public TextClientlet(String extension) {
		this.extension = extension;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.clientlet.Clientlet#process(org.loboevolution.clientlet.
	 * ClientletContext)
	 */
	@Override
	public void process(ClientletContext context) throws ClientletException {
		try {
			InputStream in = context.getResponse().getInputStream();
			try {
				String text = IORoutines.loadAsText(in, "UTF-8");
				RSyntaxTextArea textArea = new RSyntaxTextArea(text);
				String mimeType = "";
				switch (extension) {
				case "java":
					mimeType = SyntaxConstants.SYNTAX_STYLE_JAVA;
					break;
				case "php":
					mimeType = SyntaxConstants.SYNTAX_STYLE_RUBY;
					break;
				case "bash":
					mimeType = SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL;
					break;
				case "ruby":
					mimeType = SyntaxConstants.SYNTAX_STYLE_RUBY;
					break;
				case "javascript":
					mimeType = SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT;
					break;
				case "css":
					mimeType = SyntaxConstants.SYNTAX_STYLE_CSS;
					break;
				case "html":
					mimeType = SyntaxConstants.SYNTAX_STYLE_HTML;
					break;
				case "csharp":
					mimeType = SyntaxConstants.SYNTAX_STYLE_CSHARP;
					break;
				case "sql":
					mimeType = SyntaxConstants.SYNTAX_STYLE_SQL;
					break;
				case "xml":
					mimeType = SyntaxConstants.SYNTAX_STYLE_XML;
					break;
				case "c":
					mimeType = SyntaxConstants.SYNTAX_STYLE_C;
					break;
				case "objc":
					mimeType = SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS;
					break;
				case "python":
					mimeType = SyntaxConstants.SYNTAX_STYLE_PYTHON;
					break;
				case "perl":
					mimeType = SyntaxConstants.SYNTAX_STYLE_PERL;
					break;
				case "js":
					mimeType = SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT;
					break;
				case "xaml":
					mimeType = SyntaxConstants.SYNTAX_STYLE_XML;
					break;
				case "json":
					mimeType = SyntaxConstants.SYNTAX_STYLE_JSON;
					break;
				default:
					mimeType = SyntaxConstants.SYNTAX_STYLE_HTML;
					break;
				}

				textArea.setSyntaxEditingStyle(mimeType);
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
