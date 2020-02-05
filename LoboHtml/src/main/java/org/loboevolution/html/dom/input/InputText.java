package org.loboevolution.html.dom.input;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.text.PlainDocument;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.common.Strings;
import org.loboevolution.component.input.Autocomplete;
import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.store.InputStore;

public class InputText {

	private static final float DEFAULT_FONT_SIZE = 14.0f;
		
	protected JTextField widget = new JTextField();
	
	private boolean textWrittenIn;
	
	private HTMLInputElementImpl modelNode;

	public InputText(HTMLInputElementImpl modelNode, InputControl ic) {
		this.modelNode = modelNode;
		final boolean autocomplete = modelNode.getAutocomplete();
		final String id = modelNode.getId();
		final String name = modelNode.getName();
		final String type = modelNode.getType();
		
		final Font font = widget.getFont();
		widget.setFont(font.deriveFont(DEFAULT_FONT_SIZE));
		widget.setDocument(new LimitedDocument());
		widget.setText(modelNode.getValue());
		widget.setSelectionColor(Color.BLUE);
		final Dimension ps = widget.getPreferredSize();
		widget.setPreferredSize(new Dimension(128, ps.height));

		if (autocomplete && !"password".equalsIgnoreCase(type)) {
			List<String> list = suggestionList(id, name, type);
			if (ArrayUtilities.isNotBlank(list)) {
				Autocomplete.setupAutoComplete((JTextField) widget, list);
			}
		}

		if (Strings.isNotBlank(modelNode.getPlaceholder())) {
			placeholder(modelNode.getPlaceholder());
		}

		widget.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent event) {
				if (autocomplete && !"password".equalsIgnoreCase(type)) {
					InputStore.insertLogin(id, name, type, widget.getText(),
							modelNode.getUserAgentContext().isNavigationEnabled());
				}
			}
		});
		
		RUIControl ruiControl = ic.getRUIControl();
		final Insets borderInsets = ruiControl.getBorderInsets();
		
		widget.setMargin(new Insets(ruiControl.getMarginTop(), ruiControl.getMarginLeft(), ruiControl.getMarginBottom(), ruiControl.getMarginRight()));
		if (borderInsets.top == 0 && borderInsets.left == 0 && borderInsets.bottom == 0 && borderInsets.right == 0) {
			widget.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		} else {
			widget.setBorder(BorderFactory.createMatteBorder(borderInsets.top, borderInsets.left, borderInsets.bottom, borderInsets.right, Color.BLACK));
		}

		ic.add(widget);
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
		widget.setForeground(new Color(160, 160, 160));
		setTextWrittenIn(false);
	}

	private List<String> suggestionList(String id, String name, String type) {
		List<String> list = InputStore.autocomplete(id);
		if (ArrayUtilities.isNotBlank(list))
			return list;
		list = InputStore.autocomplete(name);
		if (ArrayUtilities.isNotBlank(list))
			return list;
		list = InputStore.autocomplete(type);
		if (ArrayUtilities.isNotBlank(list))
			return list;
		return new ArrayList<String>();
	}
	
	public boolean isTextWrittenIn() {
		return textWrittenIn;
	}

	public void setTextWrittenIn(boolean textWrittenIn) {
		this.textWrittenIn = textWrittenIn;
	}

	private class LimitedDocument extends PlainDocument {

		private static final long serialVersionUID = 1L;

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			final String maxLengthText = modelNode.getAttribute("max-length");
			int max = -1;
			if (maxLengthText != null) {
				try {
					max = Integer.parseInt(maxLengthText);
				} catch (NumberFormatException nfe) {}
			}
			
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