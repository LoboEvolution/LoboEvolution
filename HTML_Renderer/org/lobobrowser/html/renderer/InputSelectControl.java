package org.lobobrowser.html.renderer;

import java.awt.ComponentOrientation;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.html.domimpl.HTMLSelectElementImpl;
import org.lobobrowser.html.w3c.HTMLOptionElement;
import org.lobobrowser.html.w3c.HTMLOptionsCollection;
import org.lobobrowser.util.gui.WrapperLayout;

public class InputSelectControl extends BaseInputControl {

	private static final long serialVersionUID = 1L;
	private final JComboBox<OptionItem> comboBox;
	private final JList<OptionItem> list;
	private final DefaultListModel<OptionItem> listModel;
	private static final int STATE_NONE = 0;
	private static final int STATE_COMBO = 1;
	private static final int STATE_LIST = 2;
	private int state = STATE_NONE;
	private boolean suspendSelections = false;
	private boolean inSelectionEvent;

	public InputSelectControl(final HTMLBaseInputElement modelNode) {
		super(modelNode);
		this.setLayout(WrapperLayout.getInstance());
		final JComboBox<OptionItem> comboBox = new JComboBox<OptionItem>();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				OptionItem item = (OptionItem) e.getItem();
				if (item != null) {
					switch (e.getStateChange()) {
					case ItemEvent.SELECTED:
						if (!suspendSelections) {
							// In this case it's better to change the
							// selected index. We don't want multiple
							// selections.
							inSelectionEvent = true;
							try {
								int selectedIndex = comboBox.getSelectedIndex();
								HTMLSelectElementImpl selectElement = (HTMLSelectElementImpl) modelNode;
								selectElement.setSelectedIndex(selectedIndex);
							} finally {
								inSelectionEvent = false;
							}
							HtmlController.getInstance().onChange(modelNode);
						}
						break;
					case ItemEvent.DESELECTED:
						// Ignore deselection here. It must necessarily
						// be followed by combo-box selection. If we deselect,
						// that
						// changes the state of the control.
						break;
					}
				}
			}
		});
		final DefaultListModel<OptionItem> listModel = new DefaultListModel<OptionItem>();
		final JList<OptionItem> list = new JList<OptionItem>(listModel);
		this.listModel = listModel;
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && !suspendSelections) {
					boolean changed = false;
					inSelectionEvent = true;
					try {
						int modelSize = listModel.getSize();
						for (int i = 0; i < modelSize; i++) {
							OptionItem item = (OptionItem) listModel.get(i);
							if (item != null) {
								boolean oldIsSelected = item.isSelected();
								boolean newIsSelected = list.isSelectedIndex(i);
								if (oldIsSelected != newIsSelected) {
									changed = true;
									item.setSelected(newIsSelected);
								}
							}
						}
					} finally {
						inSelectionEvent = false;
					}
					if (changed) {
						HtmlController.getInstance().onChange(modelNode);
					}
				}
			}
		});

		// Note: Value attribute cannot be set in reset() method.
		// Otherwise, layout revalidation causes typed values to
		// be lost (including revalidation due to hover.)

		this.comboBox = comboBox;
		
		if(modelNode.getTitle() != null)
			comboBox.setToolTipText(modelNode.getTitle());
		comboBox.setVisible(modelNode.getHidden());
		comboBox.applyComponentOrientation(direction(modelNode.getDir()));
		comboBox.setEditable(new Boolean(modelNode.getContentEditable()));
		comboBox.setEnabled(!modelNode.getDisabled());
		this.list = list;
		this.resetItemList();
	}

	private void resetItemList() {
		HTMLSelectElementImpl selectElement = (HTMLSelectElementImpl) this.controlElement;
		boolean isMultiple = selectElement.getMultiple();
		if (isMultiple && this.state != STATE_LIST) {
			this.state = STATE_LIST;
			this.removeAll();
			JScrollPane scrollPane = new JScrollPane(this.list);
			this.add(scrollPane);
		} else if (!isMultiple && this.state != STATE_COMBO) {
			this.state = STATE_COMBO;
			this.removeAll();
			this.add(this.comboBox);
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
						HTMLOptionElement option = (HTMLOptionElement) optionElements
								.item(index);
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
					HTMLOptionElement option = (HTMLOptionElement) optionElements
							.item(index);
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
				JList<OptionItem> list = this.list;
				Collection<Integer> defaultSelectedIndexes = null;
				Collection<Integer> selectedIndexes = null;
				OptionItem firstItem = null;
				DefaultListModel<OptionItem> listModel = this.listModel;
				listModel.clear();
				int numOptions = optionElements.getLength();
				for (int index = 0; index < numOptions; index++) {
					HTMLOptionElement option = (HTMLOptionElement) optionElements
							.item(index);
					OptionItem item = new OptionItem(option);
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
							selectedIndexes = new LinkedList<Integer>();
						}
						selectedIndexes.add(new Integer(index));
					}
					if (option.getDefaultSelected()) {
						if (defaultSelectedIndexes == null) {
							defaultSelectedIndexes = new LinkedList<Integer>();
						}
						defaultSelectedIndexes.add(new Integer(index));
					}
				}
				if (selectedIndexes != null && selectedIndexes.size() != 0) {
					Iterator<Integer> sii = selectedIndexes.iterator();
					while (sii.hasNext()) {
						Integer si = (Integer) sii.next();
						list.addSelectionInterval(si.intValue(), si.intValue());
					}
				} else if (defaultSelectedIndexes != null
						&& defaultSelectedIndexes.size() != 0) {
					Iterator<Integer> sii = defaultSelectedIndexes.iterator();
					while (sii.hasNext()) {
						Integer si = (Integer) sii.next();
						list.addSelectionInterval(si.intValue(), si.intValue());
					}
				}
			}
		} finally {
			this.suspendSelections = false;
		}
	}

	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		// Need to do this here in case element was incomplete
		// when first rendered.
		this.resetItemList();
	}

	public String getValue() {
		if (this.state == STATE_COMBO) {
			OptionItem item = (OptionItem) this.comboBox.getSelectedItem();
			return item == null ? null : item.getValue();
		} else {
			OptionItem item = (OptionItem) this.list.getSelectedValue();
			return item == null ? null : item.getValue();
		}
	}

	private int selectedIndex = -1;

	public int getSelectedIndex() {
		return this.selectedIndex;
	}

	public void setSelectedIndex(int value) {
		this.selectedIndex = value;
		boolean prevSuspend = this.suspendSelections;
		this.suspendSelections = true;
		// Note that neither IE nor FireFox generate selection
		// events when the selection is changed programmatically.
		try {
			if (!this.inSelectionEvent) {
				if (this.state == STATE_COMBO) {
					JComboBox<OptionItem> comboBox = this.comboBox;
					if (comboBox.getSelectedIndex() != value) {
						// This check is done to avoid an infinite recursion
						// on ItemListener.
						int size = comboBox.getItemCount();
						if (value < size) {
							comboBox.setSelectedIndex(value);
						}
					}
				} else {
					JList<OptionItem> list = this.list;
					int[] selectedIndices = list.getSelectedIndices();
					if (selectedIndices == null || selectedIndices.length != 1
							|| selectedIndices[0] != value) {
						// This check is done to avoid an infinite recursion
						// on ItemListener.
						int size = this.listModel.getSize();
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

	public int getVisibleSize() {
		return this.comboBox.getMaximumRowCount();
	}

	public void setVisibleSize(int value) {
		this.comboBox.setMaximumRowCount(value);
	}

	public void resetInput() {
		this.list.setSelectedIndex(-1);
		this.comboBox.setSelectedIndex(-1);
	}

	public String[] getValues() {
		if (this.state == STATE_COMBO) {
			OptionItem item = (OptionItem) this.comboBox.getSelectedItem();
			return item == null ? null : new String[] { item.getValue() };
		} else {
			Object[] values = this.list.getSelectedValues();
			if (values == null) {
				return null;
			}
			ArrayList<String> al = new ArrayList<String>();
			for (int i = 0; i < values.length; i++) {
				OptionItem item = (OptionItem) values[i];
				al.add(item.getValue());
			}
			return (String[]) al.toArray(new String[0]);
		}
	}

	private static class OptionItem {
		private final HTMLOptionElement option;
		private final String caption;

		public OptionItem(HTMLOptionElement option) {
			this.option = option;
			String label = option.getLabel();
			if (label == null) {
				this.caption = option.getText();
			} else {
				this.caption = label;
			}
		}

		public void setSelected(boolean value) {
			this.option.setSelected(value);
		}

		public boolean isSelected() {
			return this.option.getSelected();
		}

		public String toString() {
			return this.caption;
		}

		public String getValue() {
			String value = this.option.getValue();
			if (value == null) {
				value = this.option.getText();
			}
			return value;
		}
	}
	
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
