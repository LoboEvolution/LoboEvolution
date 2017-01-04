/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
package org.lobobrowser.html.dombl;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The Class JTextFieldImpl.
 */
public class JTextFieldImpl extends JTextField {

	/*
	 * Class from
	 * http://lazicbrano.wordpress.com/2013/08/01/jtextfield-placeholder
	 */

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The original font. */
	private Font originalFont;

	/** The original foreground. */
	private Color originalForeground;

	/** Grey by default*. */
	private Color placeholderForeground = new Color(160, 160, 160);

	/** The text written in. */
	private boolean textWrittenIn;

	/**
	 * Instantiates a new j text field impl.
	 */
	public JTextFieldImpl() {
	}

	/**
	 * You can insert all constructors. I inserted only this one.*
	 *
	 * @param columns
	 *            the columns
	 */
	public JTextFieldImpl(int columns) {
		super(columns);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JTextField#setFont(java.awt.Font)
	 */
	@Override
	public void setFont(Font f) {
		super.setFont(f);
		if (!isTextWrittenIn()) {
			originalFont = f;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#setForeground(java.awt.Color)
	 */
	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (!isTextWrittenIn()) {
			originalForeground = fg;
		}
	}

	@Override
	public void setBorder(Border border) {
		super.setBorder(border);
	}

	/**
	 * Gets the grey by default*.
	 *
	 * @return the grey by default*
	 */
	public Color getPlaceholderForeground() {
		return placeholderForeground;
	}

	/**
	 * Sets the grey by default*.
	 *
	 * @param placeholderForeground
	 *            the new grey by default*
	 */
	public void setPlaceholderForeground(Color placeholderForeground) {
		this.placeholderForeground = placeholderForeground;
	}

	/**
	 * Checks if is text written in.
	 *
	 * @return the text written in
	 */
	public boolean isTextWrittenIn() {
		return textWrittenIn;
	}

	/**
	 * Sets the text written in.
	 *
	 * @param textWrittenIn
	 *            the new text written in
	 */
	public void setTextWrittenIn(boolean textWrittenIn) {
		this.textWrittenIn = textWrittenIn;
	}

	/**
	 * Sets the placeholder.
	 *
	 * @param text
	 *            the new placeholder
	 */
	public void setPlaceholder(final String text) {

		this.customizeText(text);

		this.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				if (getText().trim().length() != 0) {
					setFont(originalFont);
					setForeground(originalForeground);
					setTextWrittenIn(true);
				}

			}
		});

		this.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (!isTextWrittenIn()) {
					setText("");
				}

			}

			@Override
			public void focusLost(FocusEvent e) {
				if (getText().trim().length() == 0) {
					customizeText(text);
				}
			}

		});

	}

	@Override
	public synchronized void addKeyListener(KeyListener l) {
		super.addKeyListener(null);
	}

	/**
	 * Customize text.
	 *
	 * @param text
	 *            the text
	 */
	private void customizeText(String text) {
		setText(text);
		setFont(new Font(getFont().getFamily(), Font.ITALIC, getFont().getSize()));
		setForeground(getPlaceholderForeground());
		setTextWrittenIn(false);
	}

}
