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
 * 
 */
package org.loboevolution.html.control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import org.loboevolution.font.LAFSettings;
import org.loboevolution.html.domimpl.DOMElementImpl;
import org.loboevolution.html.domimpl.HTMLBaseInputElement;
import org.loboevolution.html.gui.mouse.GuiMouseImpl;
import org.loboevolution.util.gui.WrapperLayout;

/**
 * The Class BaseInputTextControl.
 */
public abstract class BaseInputTextControl extends BaseInputControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The max length. */
	private int maxLength = -1;

	/** The widget. */
	protected final JTextComponent widget;

	/** Creates the text field. */
	protected abstract JTextComponent createTextField();

	/**
	 * Instantiates a new base input text control.
	 *
	 * @param modelNode
	 *            the model node
	 */
	public BaseInputTextControl(final HTMLBaseInputElement modelNode) {
		super(modelNode);
		this.setLayout(WrapperLayout.getInstance());
		JTextComponent widget = this.createTextField();
		Font font = widget.getFont();
		widget.setFont(font.deriveFont(LAFSettings.getInstance().getFontSize()));
		widget.setDocument(new LimitedDocument());
		widget.setSelectionColor(Color.BLUE);
		DOMElementImpl element = this.controlElement;
		widget.setText(element.getAttribute(VALUE));
		widget.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent event) {
				GuiMouseImpl.getInstance().onKeyDown(modelNode, event);
				GuiMouseImpl.getInstance().onKeyPress(modelNode, event);
			}

			@Override
			public void keyReleased(KeyEvent event) {
				GuiMouseImpl.getInstance().onKeyUp(modelNode, event);

			}
		});

		this.widget = widget;
		this.add(widget);
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		RUIControl ruiControl = this.ruicontrol;
		String maxLengthText = this.controlElement.getAttribute(MAXLENGTH);
		if (maxLengthText != null) {
			try {
				this.maxLength = Integer.parseInt(maxLengthText);
			} catch (NumberFormatException nfe) {
				logger.error(nfe);
			}
		}

		if (ruiControl.hasBackground()) {
			widget.setBackground(ruiControl.getBackgroundColor());
		}

		widget.setMargin(new Insets(ruiControl.getMarginTop(), ruiControl.getMarginLeft(), ruiControl.getMarginBottom(),
				ruiControl.getMarginRight()));

		if (ruiControl.getBorderInsets().top == 0 && ruiControl.getBorderInsets().left == 0
				&& ruiControl.getBorderInsets().bottom == 0 && ruiControl.getBorderInsets().right == 0) {
			widget.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		} else {
			widget.setBorder(
					BorderFactory.createMatteBorder(ruiControl.getBorderInsets().top, ruiControl.getBorderInsets().left,
							ruiControl.getBorderInsets().bottom, ruiControl.getBorderInsets().right, Color.BLACK));
		}

	}

	@Override
	public int getMaxLength() {
		return this.maxLength;
	}

	@Override
	public boolean getReadOnly() {
		return !this.widget.isEditable();
	}

	@Override
	public String getValue() {
		return this.widget.getText();
	}

	@Override
	public void select() {
		this.widget.selectAll();
	}

	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		this.widget.setEnabled(!disabled);
	}

	@Override
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		this.widget.setEditable(!readOnly);
	}

	@Override
	public void setValue(String value) {
		this.widget.setText(value);
	}

	@Override
	public Dimension getPreferredSize() {
		int size = this.size;
		JTextComponent widget = this.widget;
		FontMetrics fm = widget.getFontMetrics(widget.getFont());
		Insets insets = widget.getInsets();
		int pw;
		int ph;
		if (size == -1) {
			pw = 200;
		} else {
			pw = insets.left + insets.right + fm.charWidth('0') * size;
		}
		ph = fm.getHeight() + insets.top;
		return new Dimension(pw, ph);
	}

	@Override
	public void resetInput() {
		this.widget.setText("");
	}

	/**
	 * Implements maxlength functionality.
	 */
	private class LimitedDocument extends PlainDocument {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.text.PlainDocument#insertString(int,
		 * java.lang.String, javax.swing.text.AttributeSet)
		 */
		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			int max = BaseInputTextControl.this.maxLength;
			if (max != -1) {
				int docLength = this.getLength();
				if (docLength >= max) {
					return;
				}
				int strLen = str.length();
				if (docLength + strLen > max) {
					String shorterStr = str.substring(0, max - docLength);
					super.insertString(offs, shorterStr, a);
				} else {
					super.insertString(offs, str, a);
				}
			} else {
				super.insertString(offs, str, a);
			}
		}
	}
}
