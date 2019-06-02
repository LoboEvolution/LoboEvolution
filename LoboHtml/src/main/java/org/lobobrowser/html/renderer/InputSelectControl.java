package org.lobobrowser.html.renderer;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.lobobrowser.html.dom.HTMLOptionElement;
import org.lobobrowser.html.dom.HTMLOptionsCollection;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.html.domimpl.HTMLSelectElementImpl;
import org.lobobrowser.util.gui.WrapperLayout;

class InputSelectControl extends BaseInputControl {
	private static class OptionItem {
		private final String caption;
		private final HTMLOptionElement option;

		public OptionItem(HTMLOptionElement option) {
			this.option = option;
			final String label = option.getLabel();
			if (label == null) {
				this.caption = option.getText();
			} else {
				this.caption = label;
			}
		}

		public String getValue() {
			String value = this.option.getValue();
			if (value == null) {
				value = this.option.getText();
			}
			return value;
		}

		public boolean isSelected() {
			return this.option.getSelected();
		}

		public void setSelected(boolean value) {
			this.option.setSelected(value);
		}

		@Override
		public String toString() {
			return this.caption;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int STATE_COMBO = 1;
	private static final int STATE_LIST = 2;

	private static final int STATE_NONE = 0;

	private final JComboBox comboBox;

	private boolean inSelectionEvent;
	private final JList list;
	private final DefaultListModel listModel;
	private int selectedIndex = -1;
	private int state = STATE_NONE;

	private boolean suspendSelections = false;

	public InputSelectControl(final HTMLBaseInputElement modelNode) {
		super(modelNode);
		setLayout(WrapperLayout.getInstance());
		final JComboBox comboBox = new JComboBox();
		comboBox.addItemListener(e -> {
			final OptionItem item = (OptionItem) e.getItem();
			if (item != null) {
				switch (e.getStateChange()) {
				case ItemEvent.SELECTED:
					if (!InputSelectControl.this.suspendSelections) {
						// In this case it's better to change the
						// selected index. We don't want multiple selections.
						InputSelectControl.this.inSelectionEvent = true;
						try {
							final int selectedIndex = comboBox.getSelectedIndex();
							final HTMLSelectElementImpl selectElement = (HTMLSelectElementImpl) modelNode;
							selectElement.setSelectedIndex(selectedIndex);
						} finally {
							InputSelectControl.this.inSelectionEvent = false;
						}
						HtmlController.getInstance().onChange(modelNode);
					}
					break;
				case ItemEvent.DESELECTED:
					// Ignore deselection here. It must necessarily
					// be followed by combo-box selection. If we deselect, that
					// changes the state of the control.
					break;
				}
			}
		});
		final DefaultListModel listModel = new DefaultListModel();
		final JList list = new JList(listModel);
		this.listModel = listModel;
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && !InputSelectControl.this.suspendSelections) {
				boolean changed = false;
				InputSelectControl.this.inSelectionEvent = true;
				try {
					final int modelSize = listModel.getSize();
					for (int i = 0; i < modelSize; i++) {
						final OptionItem item = (OptionItem) listModel.get(i);
						if (item != null) {
							final boolean oldIsSelected = item.isSelected();
							final boolean newIsSelected = list.isSelectedIndex(i);
							if (oldIsSelected != newIsSelected) {
								changed = true;
								item.setSelected(newIsSelected);
							}
						}
					}
				} finally {
					InputSelectControl.this.inSelectionEvent = false;
				}
				if (changed) {
					HtmlController.getInstance().onChange(modelNode);
				}
			}
		});

		// Note: Value attribute cannot be set in reset() method.
		// Otherwise, layout revalidation causes typed values to
		// be lost (including revalidation due to hover.)

		this.comboBox = comboBox;
		this.list = list;
		resetItemList();
	}

	@Override
	public int getSelectedIndex() {
		return this.selectedIndex;
	}

	@Override
	public String getValue() {
		if (this.state == STATE_COMBO) {
			final OptionItem item = (OptionItem) this.comboBox.getSelectedItem();
			return item == null ? null : item.getValue();
		} else {
			final OptionItem item = (OptionItem) this.list.getSelectedValue();
			return item == null ? null : item.getValue();
		}
	}

	@Override
	public String[] getValues() {
		if (this.state == STATE_COMBO) {
			final OptionItem item = (OptionItem) this.comboBox.getSelectedItem();
			return item == null ? null : new String[] { item.getValue() };
		} else {
			final Object[] values = this.list.getSelectedValues();
			if (values == null) {
				return null;
			}
			final ArrayList al = new ArrayList();
			for (final Object value2 : values) {
				final OptionItem item = (OptionItem) value2;
				al.add(item.getValue());
			}
			return (String[]) al.toArray(new String[0]);
		}
	}

	@Override
	public int getVisibleSize() {
		return this.comboBox.getMaximumRowCount();
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		// Need to do this here in case element was incomplete
		// when first rendered.
		resetItemList();
	}

	@Override
	public void resetInput() {
		this.list.setSelectedIndex(-1);
		this.comboBox.setSelectedIndex(-1);
	}

	private void resetItemList() {
		final HTMLSelectElementImpl selectElement = (HTMLSelectElementImpl) this.controlElement;
		final boolean isMultiple = selectElement.getMultiple();
		if (isMultiple && this.state != STATE_LIST) {
			this.state = STATE_LIST;
			removeAll();
			final JScrollPane scrollPane = new JScrollPane(this.list);
			this.add(scrollPane);
		} else if (!isMultiple && this.state != STATE_COMBO) {
			this.state = STATE_COMBO;
			removeAll();
			this.add(this.comboBox);
		}
		this.suspendSelections = true;
		try {
			final HTMLOptionsCollection optionElements = selectElement.getOptions();
			if (this.state == STATE_COMBO) {
				final JComboBox comboBox = this.comboBox;
				// First determine current selected option
				HTMLOptionElement priorSelectedOption = null;
				final int priorIndex = selectElement.getSelectedIndex();
				if (priorIndex != -1) {
					final int numOptions = optionElements.getLength();
					for (int index = 0; index < numOptions; index++) {
						final HTMLOptionElement option = (HTMLOptionElement) optionElements.item(index);
						if (index == priorIndex) {
							priorSelectedOption = option;
						}
					}
				}
				comboBox.removeAllItems();
				OptionItem defaultItem = null;
				OptionItem selectedItem = null;
				OptionItem firstItem = null;
				final int numOptions = optionElements.getLength();
				for (int index = 0; index < numOptions; index++) {
					final HTMLOptionElement option = (HTMLOptionElement) optionElements.item(index);
					if (option != null) {
						final OptionItem item = new OptionItem(option);
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
						if (option.getSelected()) {
							selectedItem = item;
						}
						if (option.getDefaultSelected()) {
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
				final JList list = this.list;
				Collection defaultSelectedIndexes = null;
				Collection selectedIndexes = null;
				OptionItem firstItem = null;
				final DefaultListModel listModel = this.listModel;
				listModel.clear();
				final int numOptions = optionElements.getLength();
				for (int index = 0; index < numOptions; index++) {
					final HTMLOptionElement option = (HTMLOptionElement) optionElements.item(index);
					final OptionItem item = new OptionItem(option);
					if (firstItem == null) {
						firstItem = item;
						listModel.addElement(item);
						// Do not select first item automatically.
						list.setSelectedIndex(-1);
					} else {
						listModel.addElement(item);
					}
					if (option.getSelected()) {
						if (selectedIndexes == null) {
							selectedIndexes = new LinkedList();
						}
						selectedIndexes.add(new Integer(index));
					}
					if (option.getDefaultSelected()) {
						if (defaultSelectedIndexes == null) {
							defaultSelectedIndexes = new LinkedList();
						}
						defaultSelectedIndexes.add(new Integer(index));
					}
				}
				if (selectedIndexes != null && selectedIndexes.size() != 0) {
					final Iterator sii = selectedIndexes.iterator();
					while (sii.hasNext()) {
						final Integer si = (Integer) sii.next();
						list.addSelectionInterval(si.intValue(), si.intValue());
					}
				} else if (defaultSelectedIndexes != null && defaultSelectedIndexes.size() != 0) {
					final Iterator sii = defaultSelectedIndexes.iterator();
					while (sii.hasNext()) {
						final Integer si = (Integer) sii.next();
						list.addSelectionInterval(si.intValue(), si.intValue());
					}
				}
			}
		} finally {
			this.suspendSelections = false;
		}
	}

	@Override
	public void setSelectedIndex(int value) {
		this.selectedIndex = value;
		final boolean prevSuspend = this.suspendSelections;
		this.suspendSelections = true;
		// Note that neither IE nor FireFox generate selection
		// events when the selection is changed programmatically.
		try {
			if (!this.inSelectionEvent) {
				if (this.state == STATE_COMBO) {
					final JComboBox comboBox = this.comboBox;
					if (comboBox.getSelectedIndex() != value) {
						// This check is done to avoid an infinite recursion
						// on ItemListener.
						final int size = comboBox.getItemCount();
						if (value < size) {
							comboBox.setSelectedIndex(value);
						}
					}
				} else {
					final JList list = this.list;
					final int[] selectedIndices = list.getSelectedIndices();
					if (selectedIndices == null || selectedIndices.length != 1 || selectedIndices[0] != value) {
						// This check is done to avoid an infinite recursion
						// on ItemListener.
						final int size = this.listModel.getSize();
						if (value < size) {
							list.setSelectedIndex(value);
						}
					}
				}
			}
		} finally {
			this.suspendSelections = prevSuspend;
		}
	}

	@Override
	public void setVisibleSize(int value) {
		this.comboBox.setMaximumRowCount(value);
	}
}
