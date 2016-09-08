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
 * Created on Jan 15, 2006
 */
package org.lobobrowser.html.control;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.dombl.JTextAreaImpl;
import org.lobobrowser.html.domimpl.DOMElementImpl;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.html.renderer.HtmlController;
import org.lobobrowser.util.gui.WrapperLayout;

/**
 * The Class InputTextAreaControl.
 */
public class InputTextAreaControl extends BaseInputControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The widget. */
	private final JTextAreaImpl widget;

	/**
	 * Instantiates a new input text area control.
	 *
	 * @param modelNode
	 *            the model node
	 */
	public InputTextAreaControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		this.setLayout(WrapperLayout.getInstance());
		JTextAreaImpl widget = (JTextAreaImpl) this.createTextField();
		this.widget = widget;
		this.add(new JScrollPane(widget));

		// Note: Value attribute cannot be set in reset() method.
		// Otherwise, layout revalidation causes typed values to
		// be lost (including revalidation due to hover.)

		DOMElementImpl element = this.controlElement;
		String value = element.getTextContent();
		widget.setLineWrap(true);

		if (modelNode.getTitle() != null) {
			widget.setToolTipText(modelNode.getTitle());
		}
		widget.setVisible(modelNode.getHidden());
		widget.applyComponentOrientation(direction(modelNode.getDir()));
		widget.setEditable(
				new Boolean(modelNode.getContentEditable() == null ? "true" : modelNode.getContentEditable()));
		widget.setEnabled(!modelNode.getDisabled());
		widget.setPlaceholder(modelNode.getPlaceholder());
		widget.setSelectionColor(Color.BLUE);
		widget.setText(value);
		widget.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent event) {
				HtmlController.getInstance().onKeyDown(modelNode, event);
				HtmlController.getInstance().onKeyPress(modelNode, event);
			}

			@Override
			public void keyReleased(KeyEvent event) {
				HtmlController.getInstance().onKeyUp(modelNode, event);

			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.control.BaseInputControl#reset(int, int)
	 */
	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		DOMElementImpl element = this.controlElement;
		String colsStr = element.getAttribute(HtmlAttributeProperties.COLS);
		if (colsStr != null) {
			try {
				this.setCols(Integer.parseInt(colsStr));
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}
		String rowsStr = element.getAttribute(HtmlAttributeProperties.ROWS);
		if (rowsStr != null) {
			try {
				this.setRows(Integer.parseInt(rowsStr));
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}
	}

	/**
	 * Creates the text field.
	 *
	 * @return the j text component
	 */
	protected JTextComponent createTextField() {
		return new JTextAreaImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.render.BaseInputControl#getCols()
	 */
	@Override
	public int getCols() {
		return this.cols;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.render.BaseInputControl#getRows()
	 */
	@Override
	public int getRows() {
		return this.rows;
	}

	/** The cols. */
	private int cols = -1;

	/** The rows. */
	private int rows = -1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.render.BaseInputControl#setCols(int)
	 */
	@Override
	public void setCols(int cols) {
		if (cols != this.cols) {
			this.cols = cols;
			this.invalidate();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.render.BaseInputControl#setRows(int)
	 */
	@Override
	public void setRows(int rows) {
		if (rows != this.rows) {
			this.rows = rows;
			this.invalidate();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	public java.awt.Dimension getPreferredSize() {
		int pw;
		int cols = this.cols;
		if (cols == -1) {
			pw = 100;
		} else {
			Font f = this.widget.getFont();
			FontMetrics fm = this.widget.getFontMetrics(f);
			Insets insets = this.widget.getInsets();
			pw = insets.left + insets.right + (fm.charWidth('*') * cols);
		}
		int ph;
		int rows = this.rows;
		if (rows == -1) {
			ph = 100;
		} else {
			Font f = this.widget.getFont();
			FontMetrics fm = this.widget.getFontMetrics(f);
			Insets insets = this.widget.getInsets();
			ph = insets.top + insets.bottom + (fm.getHeight() * rows);
		}
		return new java.awt.Dimension(pw, ph);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.render.BaseInputControl#getReadOnly()
	 */
	@Override
	public boolean getReadOnly() {
		return !this.widget.isEditable();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.render.BaseInputControl#getValue()
	 */
	@Override
	public String getValue() {
		String text = this.widget.getText();
		return org.lobobrowser.util.Strings.getCRLFString(text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.render.BaseInputControl#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
		this.widget.setEditable(readOnly);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.render.BaseInputControl#setValue(String)
	 */
	@Override
	public void setValue(String value) {
		this.widget.setText(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#resetInput()
	 */
	@Override
	public void resetInput() {
		this.widget.setText("");
	}

	/**
	 * Direction.
	 *
	 * @param dir
	 *            the dir
	 * @return the component orientation
	 */
	private ComponentOrientation direction(String dir) {

		if ("ltr".equalsIgnoreCase(dir)) {
			return ComponentOrientation.LEFT_TO_RIGHT;
		} else if ("rtl".equalsIgnoreCase(dir)) {
			return ComponentOrientation.RIGHT_TO_LEFT;
		} else {
			return ComponentOrientation.UNKNOWN;
		}
	}
}
