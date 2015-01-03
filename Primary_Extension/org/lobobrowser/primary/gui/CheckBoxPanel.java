/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

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

public class CheckBoxPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final JCheckBox checkBox;
	private final JComponent subPanel;

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

	public void updateEnabling() {
		SwingTasks.setNestedEnabled(subPanel, checkBox.isSelected());
	}

	public void setText(String text) {
		this.checkBox.setText(text);
	}

	public void setSelected(boolean selected) {
		this.checkBox.setSelected(selected);
	}

	public boolean isSelected() {
		return this.checkBox.isSelected();
	}

	public class CheckBoxAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			SwingTasks.setNestedEnabled(subPanel, checkBox.isSelected());
		}
	}
}
