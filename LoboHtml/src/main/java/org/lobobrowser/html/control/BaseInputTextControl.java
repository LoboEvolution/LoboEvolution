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
package org.lobobrowser.html.control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import org.lobo.common.ArrayUtilities;
import org.lobo.common.Strings;
import org.lobo.common.WrapperLayout;
import org.lobo.component.input.Autocomplete;
import org.lobo.store.InputStore;
import org.lobobrowser.html.dom.domimpl.HTMLBaseInputElement;

abstract class BaseInputTextControl extends BaseInputControl {

	private static final float DEFAULT_FONT_SIZE = 14.0f;

	private static final long serialVersionUID = 1L;

	private int maxLength = -1;

	protected final JTextComponent widget;

	private Color placeholderForeground = new Color(160, 160, 160);

	private boolean textWrittenIn;

	protected abstract JTextComponent createTextField();
	
	public BaseInputTextControl(final HTMLBaseInputElement modelNode) {
		super(modelNode);
		setLayout(WrapperLayout.getInstance());
		final boolean autocomplete = modelNode.getAutocomplete();
		final String id = modelNode.getId();
		final String name = modelNode.getName();
		final String type = modelNode.getType();
		final JTextComponent widget = createTextField();
		final Font font = widget.getFont();
		widget.setFont(font.deriveFont(DEFAULT_FONT_SIZE));
		widget.setDocument(new LimitedDocument());
		widget.setText(modelNode.getValue());
		widget.setSelectionColor(Color.BLUE);
		this.widget = widget;

		if (autocomplete && !"password".equalsIgnoreCase(type)) {
			List<String> list = suggestionList(id, name, type);
			if (ArrayUtilities.isNotBlank(list)) {
				Autocomplete.setupAutoComplete((JTextField) widget, list);
			}
		}
		
        if(Strings.isNotBlank(modelNode.getPlaceholder())) {
			placeholder(modelNode.getPlaceholder());
		}
		
		widget.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent event) {
				if (autocomplete && !"password".equalsIgnoreCase(type)) {
					InputStore.insertLogin(id, name, type, widget.getText(), modelNode.getUserAgentContext().isNavigationEnabled());
				}
			}
		});
		
		this.add(widget);
	}
	
	private void placeholder(String text) {
		this.customizeText(text);
		
		widget.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (!isTextWrittenIn()) {
					widget.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (Strings.isBlank(widget.getText())) {
					customizeText(text);
				}
			}
		});
	}
	
	private void customizeText(String text) {
		widget.setText(text);
		widget.setForeground(placeholderForeground);
		setTextWrittenIn(false);
	}

	@Override
	public int getMaxLength() {
		return this.maxLength;
	}

	@Override
	public Dimension getPreferredSize() {
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
		return new Dimension(pw, ph);
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		final RUIControl ruiControl = this.ruicontrol;
		final Insets borderInsets = ruiControl.getBorderInsets();
		final String maxLengthText = this.controlElement.getAttribute("max-length");
		if (maxLengthText != null) {
			try {
				this.maxLength = Integer.parseInt(maxLengthText);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
		}

		widget.setMargin(new Insets(ruiControl.getMarginTop(), ruiControl.getMarginLeft(), ruiControl.getMarginBottom(), ruiControl.getMarginRight()));
		if (borderInsets.top == 0 && borderInsets.left == 0 && borderInsets.bottom == 0 && borderInsets.right == 0) {
			widget.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		} else {
			widget.setBorder(BorderFactory.createMatteBorder(borderInsets.top, borderInsets.left, borderInsets.bottom, borderInsets.right, Color.BLACK));
		}

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
	public void resetInput() {
		this.widget.setText("");
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
	
	public boolean isTextWrittenIn() {
		return textWrittenIn;
	}

	public void setTextWrittenIn(boolean textWrittenIn) {
		this.textWrittenIn = textWrittenIn;
	}
	
	private List<String> suggestionList(String id, String name, String type) {
        List<String> list = InputStore.autocomplete(id);
        if(ArrayUtilities.isNotBlank(list)) return list;
        list = InputStore.autocomplete(name);
        if(ArrayUtilities.isNotBlank(list)) return list;
        list = InputStore.autocomplete(type);
        if(ArrayUtilities.isNotBlank(list)) return list;
        return new ArrayList<String>();
    }

	private class LimitedDocument extends PlainDocument {

		private static final long serialVersionUID = 1L;

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
}
