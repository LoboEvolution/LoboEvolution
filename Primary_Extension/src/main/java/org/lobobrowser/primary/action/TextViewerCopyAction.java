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
package org.lobobrowser.primary.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.lobobrowser.primary.ext.TextViewerWindow;

/**
 * The Class TextViewerCopyAction.
 */
public class TextViewerCopyAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The text. */
	private TextViewerWindow text;

	/**
	 * Instantiates a new text viewer close action.
	 *
	 * @param text
	 *            the text
	 */
	public TextViewerCopyAction(TextViewerWindow text) {
		this.text = text;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		text.getTextArea().copy();
	}
}