/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.html.dom.input;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.html.control.SelectControl;
import org.loboevolution.html.dom.HTMLOptionElement;
import org.loboevolution.html.dom.HTMLOptionsCollection;
import org.loboevolution.html.dom.domimpl.HTMLSelectElementImpl;
import org.loboevolution.html.renderer.HtmlController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Collection;
import java.util.LinkedList;

/**
 * <p>SelectOption class.</p>
 */
public class SelectOption {

	private final SelectControl control;

	/** The combo box. */
	private final JComboBox<OptionItem> comboBox;

	/** The list. */
	private final JList<OptionItem> list;

	/** The list model. */
	private final DefaultListModel<OptionItem> listModel;

	/** The Constant STATE_NONE. */
	private static final int STATE_NONE = 0;

	/** The Constant STATE_COMBO. */
	private static final int STATE_COMBO = 1;

	/** The Constant STATE_LIST. */
	private static final int STATE_LIST = 2;

	/** The state. */
	private int state = STATE_NONE;

	/** The suspend selections. */
	private boolean suspendSelections = false;

	/**
	 * Instantiates a new input select control.
	 *
	 * @param modelNode the model node
	 * @param control a {@link org.loboevolution.html.control.SelectControl} object.
	 */
	public SelectOption(HTMLSelectElementImpl modelNode, SelectControl control) {
		this.control = control;
		final JComboBox<OptionItem> comboBox = new JComboBox<>();
		comboBox.addItemListener(e -> {
			OptionItem item = (OptionItem) e.getItem();
			if (item != null) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (!suspendSelections) {

						int selectedIndex = comboBox.getSelectedIndex();
						modelNode.setSelectedIndex(selectedIndex);
						HtmlController.getInstance().onChange(modelNode);
					}
				}
			}
		});
		final DefaultListModel<OptionItem> listModel = new DefaultListModel<>();
		final JList<OptionItem> list = new JList<>(listModel);
		this.listModel = listModel;
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && !suspendSelections) {
				boolean changed = false;

				int modelSize = listModel.getSize();
				for (int i = 0; i < modelSize; i++) {
					OptionItem item = listModel.get(i);
					if (item != null) {
						boolean oldIsSelected = item.isSelected();
						boolean newIsSelected = list.isSelectedIndex(i);
						if (oldIsSelected != newIsSelected) {
							changed = true;
							item.setSelected(newIsSelected);
						}
					}
				}

				if (changed) {
					HtmlController.getInstance().onChange(modelNode);
				}
			}
		});

		this.comboBox = comboBox;

		if (modelNode.getTitle() != null) {
			comboBox.setToolTipText(modelNode.getTitle());
		}
		comboBox.setVisible(!modelNode.isHidden());
		comboBox.applyComponentOrientation(direction(modelNode.getDir()));
		comboBox.setEditable(Boolean.parseBoolean(modelNode.getContentEditable() == null ? "true" : modelNode.getContentEditable()));
		comboBox.setEnabled(!modelNode.isDisabled());
		comboBox.setPreferredSize(new Dimension(modelNode.getClientWidth(), modelNode.getClientHeight()));
		this.list = list;
		this.resetItemList(modelNode);
		control.add(comboBox);
	}

	/**
	 * Reset item list.
	 *
	 * @param selectElement a {@link org.loboevolution.html.dom.domimpl.HTMLSelectElementImpl} object.
	 */
	public void resetItemList(HTMLSelectElementImpl selectElement) {
		boolean isMultiple = selectElement.isMultiple();
		if (isMultiple && this.state != STATE_LIST) {
			this.state = STATE_LIST;
			control.removeAll();
			JScrollPane scrollPane = new JScrollPane(this.list);
			control.add(scrollPane);
		} else if (!isMultiple && this.state != STATE_COMBO) {
			this.state = STATE_COMBO;
			control.removeAll();
			control.add(this.comboBox);
		}

		this.suspendSelections = true;
		try {
			HTMLOptionsCollection optionElements = selectElement.getOptions();
			if (this.state == STATE_COMBO) {
				JComboBox<OptionItem> comboBox = this.comboBox;
				// First determine current selected option
				HTMLOptionElement priorSelectedOption = null;
				int priorIndex = selectElement.getSelectedIndex();
				if (priorIndex != -1) {
					int numOptions = optionElements.getLength();
					for (int index = 0; index < numOptions; index++) {
						HTMLOptionElement option = (HTMLOptionElement) optionElements.item(index);
						if (index == priorIndex) {
							priorSelectedOption = option;
						}
					}
				}
				comboBox.removeAllItems();
				OptionItem defaultItem = null;
				OptionItem selectedItem = null;
				OptionItem firstItem = null;
				int numOptions = optionElements.getLength();
				for (int index = 0; index < numOptions; index++) {
					HTMLOptionElement option = (HTMLOptionElement) optionElements.item(index);
					if (option != null) {
						OptionItem item = new OptionItem(option);
						if (firstItem == null) {
							firstItem = item;
							comboBox.addItem(item);
							// Undo automatic selection that occurs
							// when adding the first item.
							// This might set the deferred index as well.
							selectElement.setSelectedIndex(-1);
							if (priorSelectedOption != null) {
								priorSelectedOption.setSelected(true);
							}
						} else {
							comboBox.addItem(item);
						}
						if (option.isSelected()) {
							selectedItem = item;
						}
						if (option.isDefaultSelected()) {
							defaultItem = item;
						}
					}
				}
				if (selectedItem != null) {
					comboBox.setSelectedItem(selectedItem);
				} else if (defaultItem != null) {
					comboBox.setSelectedItem(defaultItem);
				} else if (firstItem != null) {
					comboBox.setSelectedItem(firstItem);
				}
			} else {
				JList<OptionItem> list = this.list;
				Collection<Integer> defaultSelectedIndexes = null;
				Collection<Integer> selectedIndexes = null;
				OptionItem firstItem = null;
				DefaultListModel<OptionItem> listModel = this.listModel;
				listModel.clear();
				int numOptions = optionElements.getLength();
				for (int index = 0; index < numOptions; index++) {
					HTMLOptionElement option = (HTMLOptionElement) optionElements.item(index);
					OptionItem item = new OptionItem(option);
					if (firstItem == null) {
						firstItem = item;
						listModel.addElement(item);
						// Do not select first item automatically.
						list.setSelectedIndex(-1);
					} else {
						listModel.addElement(item);
					}
					if (option.isSelected()) {
						if (selectedIndexes == null) {
							selectedIndexes = new LinkedList<>();
						}
						selectedIndexes.add(index);
					}
					if (option.isDefaultSelected()) {
						if (defaultSelectedIndexes == null) {
							defaultSelectedIndexes = new LinkedList<>();
						}
						defaultSelectedIndexes.add(index);
					}
				}
				if (ArrayUtilities.isNotBlank(selectedIndexes)) {
					for (Integer si : selectedIndexes) {
						list.addSelectionInterval(si, si);
					}
				} else if (ArrayUtilities.isNotBlank(defaultSelectedIndexes)) {
					for (Integer si : defaultSelectedIndexes) {
						list.addSelectionInterval(si, si);
					}
				}
			}
		} finally {
			this.suspendSelections = false;
		}
	}

	/**
	 * <p>getVisibleSize.</p>
	 *
	 * @return a int.
	 */
	public int getVisibleSize() {
		return this.comboBox.getMaximumRowCount();
	}

	/**
	 * <p>setVisibleSize.</p>
	 *
	 * @param value a int.
	 */
	public void setVisibleSize(int value) {
		this.comboBox.setMaximumRowCount(value);
	}

	/**
	 * <p>resetInput.</p>
	 */
	public void resetInput() {
		this.list.setSelectedIndex(-1);
		this.comboBox.setSelectedIndex(-1);
	}

	/**
	 * The Class OptionItem.
	 */
	private static class OptionItem {

		/** The option. */
		private final HTMLOptionElement option;

		/** The caption. */
		private final String caption;

		/**
		 * Instantiates a new option item.
		 *
		 * @param option the option
		 */
		public OptionItem(HTMLOptionElement option) {
			this.option = option;
			String label = option.getLabel();
			if (label == null) {
				this.caption = option.getText();
			} else {
				this.caption = label;
			}
		}

		/**
		 * Sets the selected.
		 *
		 * @param value the new selected
		 */
		public void setSelected(boolean value) {
			this.option.setSelected(value);
		}

		/**
		 * Checks if is selected.
		 *
		 * @return true, if is selected
		 */
		public boolean isSelected() {
			return this.option.isSelected();
		}

		@Override
		public String toString() {
			return this.caption;
		}

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public String getValue() {
			String value = this.option.getValue();
			if (value == null) {
				value = this.option.getText();
			}
			return value;
		}
	}

	/**
	 * Direction.
	 *
	 * @param dir the dir
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
