/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Jan 15, 2006
 */
package org.lobobrowser.html.renderer;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import org.lobobrowser.html.domimpl.ElementImpl;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.util.gui.WrapperLayout;

abstract class BaseInputTextControl extends BaseInputControl {
	/**
	 * Implements maxlength functionality.
	 */
	private class LimitedDocument extends javax.swing.text.PlainDocument {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.text.PlainDocument#insertString(int, java.lang.String,
		 * javax.swing.text.AttributeSet)
		 */
		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			final int max = BaseInputTextControl.this.maxLength;
			if (max != -1) {
				final int docLength = getLength();
				if (docLength >= max) {
					return;
				}
				final int strLen = str.length();
				if (docLength + strLen > max) {
					final String shorterStr = str.substring(0, max - docLength);
					super.insertString(offs, shorterStr, a);
				} else {
					super.insertString(offs, str, a);
				}
			} else {
				super.insertString(offs, str, a);
			}
		}
	}

	private static final float DEFAULT_FONT_SIZE = 14.0f;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int maxLength = -1;

	protected final JTextComponent widget;

	public BaseInputTextControl(final HTMLBaseInputElement modelNode) {
		super(modelNode);
		setLayout(WrapperLayout.getInstance());
		final JTextComponent widget = createTextField();
		final Font font = widget.getFont();
		widget.setFont(font.deriveFont(DEFAULT_FONT_SIZE));
		widget.setDocument(new LimitedDocument());

		// Note: Value attribute cannot be set in reset() method.
		// Otherwise, layout revalidation causes typed values to
		// be lost (including revalidation due to hover.)
		final ElementImpl element = this.controlElement;
		final String value = element.getAttribute("value");
		widget.setText(value);

		this.widget = widget;
		this.add(widget);
	}

	protected abstract JTextComponent createTextField();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#getMaxLength()
	 */
	@Override
	public int getMaxLength() {
		return this.maxLength;
	}

	@Override
	public java.awt.Dimension getPreferredSize() {
		final int size = this.size;
		final JTextComponent widget = this.widget;
		final FontMetrics fm = widget.getFontMetrics(widget.getFont());
		final Insets insets = widget.getInsets();
		int pw, ph;
		if (size == -1) {
			pw = 100;
		} else {
			pw = insets.left + insets.right + fm.charWidth('0') * size;
		}
		ph = fm.getHeight() + insets.top + insets.bottom;
		return new java.awt.Dimension(pw, ph);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#getReadOnly()
	 */
	@Override
	public boolean getReadOnly() {
		return !this.widget.isEditable();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#getValue()
	 */
	@Override
	public String getValue() {
		return this.widget.getText();
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		final String maxLengthText = this.controlElement.getAttribute("maxlength");
		if (maxLengthText != null) {
			try {
				this.maxLength = Integer.parseInt(maxLengthText);
			} catch (final NumberFormatException nfe) {
				// ignore
			}
		}

	}

	@Override
	public void resetInput() {
		this.widget.setText("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#select()
	 */
	@Override
	public void select() {
		this.widget.selectAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#setDisabled(boolean)
	 */
	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		this.widget.setEnabled(!disabled);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#setMaxLength(int)
	 */
	@Override
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
		this.widget.setEditable(!readOnly);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#setValue(java.lang.String)
	 */
	@Override
	public void setValue(String value) {
		this.widget.setText(value);
	}
}
