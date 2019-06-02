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

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

import org.lobo.common.Strings;
import org.lobobrowser.html.domimpl.ElementImpl;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.util.gui.WrapperLayout;

class InputTextAreaControl extends BaseInputControl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cols = -1;

	private int rows = -1;

	private final JTextComponent widget;

	public InputTextAreaControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		setLayout(WrapperLayout.getInstance());
		final JTextComponent widget = createTextField();
		this.widget = widget;
		this.add(new JScrollPane(widget));

		// Note: Value attribute cannot be set in reset() method.
		// Otherwise, layout revalidation causes typed values to
		// be lost (including revalidation due to hover.)

		final ElementImpl element = this.controlElement;
		final String value = element.getTextContent();
		((JTextArea) widget).setLineWrap(true);
		widget.setText(value);
	}

	protected JTextComponent createTextField() {
		return new JTextArea();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.BaseInputControl#getCols()
	 */
	@Override
	public int getCols() {
		return this.cols;
	}

	@Override
	public java.awt.Dimension getPreferredSize() {
		int pw;
		final int cols = this.cols;
		if (cols == -1) {
			pw = 100;
		} else {
			final Font f = this.widget.getFont();
			final FontMetrics fm = this.widget.getFontMetrics(f);
			final Insets insets = this.widget.getInsets();
			pw = insets.left + insets.right + fm.charWidth('*') * cols;
		}
		int ph;
		final int rows = this.rows;
		if (rows == -1) {
			ph = 100;
		} else {
			final Font f = this.widget.getFont();
			final FontMetrics fm = this.widget.getFontMetrics(f);
			final Insets insets = this.widget.getInsets();
			ph = insets.top + insets.bottom + fm.getHeight() * rows;
		}
		return new java.awt.Dimension(pw, ph);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.BaseInputControl#getReadOnly()
	 */
	@Override
	public boolean getReadOnly() {
		return !this.widget.isEditable();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.BaseInputControl#getRows()
	 */
	@Override
	public int getRows() {
		return this.rows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.BaseInputControl#getValue()
	 */
	@Override
	public String getValue() {
		final String text = this.widget.getText();
		return Strings.getCRLFString(text);
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		final ElementImpl element = this.controlElement;
		final String colsStr = element.getAttribute("cols");
		if (colsStr != null) {
			try {
				setCols(Integer.parseInt(colsStr));
			} catch (final NumberFormatException nfe) {
				// ignore
			}
		}
		final String rowsStr = element.getAttribute("rows");
		if (rowsStr != null) {
			try {
				setRows(Integer.parseInt(rowsStr));
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
	 * @see org.xamjwg.html.renderer.BaseInputControl#setCols(int)
	 */
	@Override
	public void setCols(int cols) {
		if (cols != this.cols) {
			this.cols = cols;
			invalidate();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.BaseInputControl#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
		this.widget.setEditable(readOnly);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.BaseInputControl#setRows(int)
	 */
	@Override
	public void setRows(int rows) {
		if (rows != this.rows) {
			this.rows = rows;
			invalidate();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.BaseInputControl#setValue(java.lang.String)
	 */
	@Override
	public void setValue(String value) {
		this.widget.setText(value);
	}
}
