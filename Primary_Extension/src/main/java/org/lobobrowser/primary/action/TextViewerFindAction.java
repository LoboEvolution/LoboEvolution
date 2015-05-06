/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.action;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.text.Document;

import org.lobobrowser.primary.ext.TextViewerWindow;
import org.lobobrowser.primary.gui.SearchDialog;
import org.lobobrowser.ua.NavigatorWindow;

/**
 * The Class TextViewerSelectAllAction.
 */
public class TextViewerFindAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The text. */
	private TextViewerWindow text;

	/** The window. */
	private NavigatorWindow window;

	/** The pos. */
	private int pos = 0;

	/**
	 * Instantiates a new text viewer close action.
	 *
	 * @param text            the text
	 * @param window the window
	 */
	public TextViewerFindAction(TextViewerWindow text, NavigatorWindow window) {
		this.text = text;
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		searchWord();
	}

	/**
	 * Search searchWord.
	 */
	private void searchWord() {
		Window awtWindow = window.getAwtWindow();
		if (!(awtWindow instanceof Frame)) {
			throw new IllegalStateException(
					"Search dialog only supported when an AWT Frame is available.");
		}
		SearchDialog dialog = new SearchDialog((Frame) awtWindow, true, "");
		dialog.setTitle("Find");
		dialog.setLocationByPlatform(true);
		dialog.setResizable(false);
		dialog.setSize(new Dimension(200, 100));
		dialog.setVisible(true);
		String find = dialog.getSearchKeywords();
			
			text.getTextArea().requestFocusInWindow();
			// Make sure we have a valid search term
			if (find != null && find.length() > 0) {
				Document document = text.getTextArea().getDocument();
				int findLength = find.length();
				try {
					boolean found = false;
					// Rest the search position if we're at the end of
					// the document
					if (pos + findLength > document.getLength()) {
						pos = 0;
					}
					// While we haven't reached the end...
					// "<=" Correction
					while (pos + findLength <= document.getLength()) {
						// Extract the text from teh docuemnt
						String match = document.getText(pos, findLength);
						// Check to see if it matches or request
						if (match.equals(find)) {
							found = true;
							break;
						}
						pos++;
					}

					// Did we find something...
					if (found) {
						// Get the rectangle of the where the text would
						// be visible...
						Rectangle viewRect = text.getTextArea()
								.modelToView(pos);
						// Scroll to make the rectangle visible
						text.getTextArea().scrollRectToVisible(viewRect);
						// Highlight the text
						text.getTextArea().setCaretPosition(pos + findLength);
						text.getTextArea().moveCaretPosition(pos);
						// Move the search position beyond the current
						// match
						pos += findLength;
					}

				} catch (Exception exp) {
					exp.printStackTrace();
				}

			}
	}
}