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

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FormPanel extends JComponent {

	private static final long serialVersionUID = 1L;
	private final Collection<FormField> fields = new ArrayList<FormField>();
	private boolean fieldsInvalid = false;

	public FormPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public void addField(FormField field) {
		// Call in GUI thread only.
		this.fields.add(field);
		this.fieldsInvalid = true;
	}

	public void revalidate() {
		this.fieldsInvalid = true;
		super.revalidate();
	}

	private int minLabelWidth = 0;

	public int getMinLabelWidth() {
		return minLabelWidth;
	}

	public void setMinLabelWidth(int minLabelWidth) {
		this.minLabelWidth = minLabelWidth;
	}

	private void populateComponents() {
		this.removeAll();
		int maxWidth = this.minLabelWidth;
		Collection<JLabel> labels = new ArrayList<JLabel>();
		boolean firstTime = true;
		for (FormField field : this.fields) {
			if (firstTime) {
				firstTime = false;
			} else {
				this.add(Box.createRigidArea(new Dimension(1, 4)));
			}
			JLabel label = field.getLabel();
			label.setEnabled(this.isEnabled());
			labels.add(label);
			label.setHorizontalAlignment(JLabel.RIGHT);
			String tooltip = field.getToolTip();
			if (tooltip != null) {
				label.setToolTipText(tooltip);
			}
			Dimension prefSize = label.getPreferredSize();
			if (prefSize.width > maxWidth) {
				maxWidth = prefSize.width;
			}
			JComponent entryPanel = new JPanel();
			entryPanel.setOpaque(false);
			entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.X_AXIS));
			entryPanel.add(label);
			// entryPanel.add(new FillerComponent(label, new Dimension(100, 24),
			// new Dimension(100, 24), new Dimension(100, 24)));
			entryPanel.add(Box.createRigidArea(new Dimension(4, 1)));
			Component editor = field.getFieldEditor();
			// Dimension eps = editor.getPreferredSize();
			// editor.setPreferredSize(new Dimension(100, eps.height));
			editor.setEnabled(this.isEnabled());
			entryPanel.add(editor);
			Dimension epps = entryPanel.getPreferredSize();
			entryPanel.setPreferredSize(new Dimension(100, epps.height));
			this.add(entryPanel);
		}
		for (JLabel label : labels) {
			Dimension psize = label.getPreferredSize();
			Dimension newSize = new Dimension(maxWidth, psize.height);
			label.setPreferredSize(newSize);
			label.setMinimumSize(newSize);
			label.setMaximumSize(newSize);
		}
		this.fieldsInvalid = false;
	}

	public void doLayout() {
		if (this.fieldsInvalid) {
			this.populateComponents();
		}
		super.doLayout();
	}

	public Dimension getPreferredSize() {
		if (this.fieldsInvalid) {
			this.populateComponents();
		}
		return super.getPreferredSize();
	}

	public Dimension getMinimumSize() {
		if (this.fieldsInvalid) {
			this.populateComponents();
		}
		return super.getMinimumSize();
	}

	public Dimension getMaximumSize() {
		if (this.fieldsInvalid) {
			this.populateComponents();
		}
		return super.getMaximumSize();
	}
}
