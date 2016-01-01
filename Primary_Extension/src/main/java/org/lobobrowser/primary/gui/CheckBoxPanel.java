/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * The Class CheckBoxPanel.
 */
public class CheckBoxPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The check box. */
	private final JCheckBox checkBox;

	/** The sub panel. */
	private final JComponent subPanel;

	/**
	 * Instantiates a new check box panel.
	 *
	 * @param text
	 *            the text
	 * @param subPanel
	 *            the sub panel
	 */
	public CheckBoxPanel(String text, JComponent subPanel) {
		this.subPanel = subPanel;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.checkBox = new JCheckBox();
		this.checkBox.setAlignmentX(0.0f);
		this.checkBox.setAction(new CheckBoxAction());
		this.checkBox.setText(text);
		JPanel checkBoxArea = new JPanel();
		checkBoxArea.setLayout(new BoxLayout(checkBoxArea, BoxLayout.X_AXIS));
		checkBoxArea.add(this.checkBox);
		checkBoxArea.add(SwingTasks.createHorizontalFill());
		this.add(checkBoxArea);
		this.add(subPanel);
		SwingTasks.setNestedEnabled(subPanel, checkBox.isSelected());
	}

	/**
	 * Update enabling.
	 */
	public void updateEnabling() {
		SwingTasks.setNestedEnabled(subPanel, checkBox.isSelected());
	}

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	public void setText(String text) {
		this.checkBox.setText(text);
	}

	/**
	 * Sets the selected.
	 *
	 * @param selected
	 *            the new selected
	 */
	public void setSelected(boolean selected) {
		this.checkBox.setSelected(selected);
	}

	/**
	 * Checks if is selected.
	 *
	 * @return true, if is selected
	 */
	public boolean isSelected() {
		return this.checkBox.isSelected();
	}

	/**
	 * The Class CheckBoxAction.
	 */
	public class CheckBoxAction extends AbstractAction {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingTasks.setNestedEnabled(subPanel, checkBox.isSelected());
		}
	}
}
