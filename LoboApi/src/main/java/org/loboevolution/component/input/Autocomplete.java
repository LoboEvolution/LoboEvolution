/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.component.input;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.loboevolution.common.Strings;

/**
 * <p>Autocomplete class.</p>
 */
public class Autocomplete {

	/**
	 * <p>setupAutoComplete.</p>
	 *
	 * @param txtInput a {@link javax.swing.JTextField} object.
	 * @param items a {@link java.util.List} object.
	 */
	public static void setupAutoComplete(final JTextField txtInput, final List<String> items) {
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		final JComboBox<String> cbInput = new JComboBox<>(model) {

			@Serial
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, 0);
            }
        };
		setAdjusting(cbInput, false);
		for (final String item : items) {
			model.addElement(item);
		}
		cbInput.removeAll();
		cbInput.removeAllItems();
		
		cbInput.addActionListener(e -> {
			if (!isAdjusting(cbInput)) {
				if (cbInput.getSelectedItem() != null) {
					txtInput.setText(cbInput.getSelectedItem().toString());
				}
			}
		});

		txtInput.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(final KeyEvent e) {
				setAdjusting(cbInput, true);
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (cbInput.isPopupVisible()) {
						e.setKeyCode(KeyEvent.VK_ENTER);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == KeyEvent.VK_DOWN) {
					e.setSource(cbInput);
					cbInput.dispatchEvent(e);
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						if (cbInput.getSelectedItem() != null)
							txtInput.setText((String)cbInput.getSelectedItem());
						cbInput.setPopupVisible(false);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					cbInput.setPopupVisible(false);
				}
				setAdjusting(cbInput, false);
			}
		});
		txtInput.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(final DocumentEvent e) {
				updateList();
			}

			public void removeUpdate(final DocumentEvent e) {
				updateList();
			}

			public void changedUpdate(final DocumentEvent e) {
				updateList();
			}

			private void updateList() {
				setAdjusting(cbInput, true);
				model.removeAllElements();
				final String input = txtInput.getText();
				if (Strings.isNotBlank(input)) {
					for (final String item : items) {
						if (item.toLowerCase().contains(input.toLowerCase())) {
							model.addElement(item);
						}
					}
				}

				try {
					cbInput.setPopupVisible(model.getSize() > 0);
				} catch (final Exception e) {
				}
				setAdjusting(cbInput, false);
			}
		});
		txtInput.setLayout(new BorderLayout());
		txtInput.add(cbInput, BorderLayout.SOUTH);
	}

	private static boolean isAdjusting(final JComboBox<String> cbInput) {
		if (cbInput.getClientProperty("is_adjusting") instanceof Boolean) {
			return (Boolean) cbInput.getClientProperty("is_adjusting");
		}
		return false;
	}

	private static void setAdjusting(final JComboBox<String> cbInput, final boolean adjusting) {
		cbInput.putClientProperty("is_adjusting", adjusting);
	}
}
