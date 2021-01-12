/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The Class FormPanel.
 *
 * @author utente
 * @version $Id: $Id
 */
public class FormPanel extends JComponent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The fields. */
	private final transient Collection<FormField> fields = new ArrayList<>();

	/** The fields invalid. */
	private boolean fieldsInvalid = false;

	/** The min label width. */
	private int minLabelWidth = 0;

	/**
	 * Instantiates a new form panel.
	 */
	public FormPanel() {
		createAndShowGUI();
	}

	/**
	 * Adds the field.
	 *
	 * @param field the field
	 */
	public void addField(FormField field) {
		// Call in GUI thread only.
		this.fields.add(field);
		this.fieldsInvalid = true;
	}

	private void createAndShowGUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#doLayout()
	 */
	/** {@inheritDoc} */
	@Override
	public void doLayout() {
		if (this.fieldsInvalid) {
			populateComponents();
		}
		super.doLayout();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getMaximumSize()
	 */
	/** {@inheritDoc} */
	@Override
	public Dimension getMaximumSize() {
		if (this.fieldsInvalid) {
			populateComponents();
		}
		return super.getMaximumSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getMinimumSize()
	 */
	/** {@inheritDoc} */
	@Override
	public Dimension getMinimumSize() {
		if (this.fieldsInvalid) {
			populateComponents();
		}
		return super.getMinimumSize();
	}

	/**
	 * Gets the min label width.
	 *
	 * @return the min label width
	 */
	public int getMinLabelWidth() {
		return this.minLabelWidth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		if (this.fieldsInvalid) {
			populateComponents();
		}
		return super.getPreferredSize();
	}

	/**
	 * Populate components.
	 */
	private void populateComponents() {
		removeAll();
		int maxWidth = this.minLabelWidth;
		final Collection<JLabel> labels = new ArrayList<>();
		boolean firstTime = true;
		for (final FormField field : this.fields) {
			if (firstTime) {
				firstTime = false;
			} else {
				this.add(Box.createRigidArea(new Dimension(1, 4)));
			}
			final JLabel label = field.getLabel();
			label.setEnabled(isEnabled());
			labels.add(label);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			final String tooltip = field.getToolTip();
			if (tooltip != null) {
				label.setToolTipText(tooltip);
			}
			final Dimension prefSize = label.getPreferredSize();
			if (prefSize.width > maxWidth) {
				maxWidth = prefSize.width;
			}
			final JComponent entryPanel = new JPanel();
			entryPanel.setOpaque(false);
			entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.X_AXIS));
			entryPanel.add(label);
			// entryPanel.add(new FillerComponent(label, new Dimension(100, 24),
			// new Dimension(100, 24), new Dimension(100, 24)));
			entryPanel.add(Box.createRigidArea(new Dimension(4, 1)));
			final Component editor = field.getFieldEditor();
			// Dimension eps = editor.getPreferredSize();
			// editor.setPreferredSize(new Dimension(100, eps.height));
			editor.setEnabled(isEnabled());
			entryPanel.add(editor);
			final Dimension epps = entryPanel.getPreferredSize();
			entryPanel.setPreferredSize(new Dimension(100, epps.height));
			this.add(entryPanel);
		}
		for (final JLabel label : labels) {
			final Dimension psize = label.getPreferredSize();
			final Dimension newSize = new Dimension(maxWidth, psize.height);
			label.setPreferredSize(newSize);
			label.setMinimumSize(newSize);
			label.setMaximumSize(newSize);
		}
		this.fieldsInvalid = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#revalidate()
	 */
	/** {@inheritDoc} */
	@Override
	public void revalidate() {
		this.fieldsInvalid = true;
		super.revalidate();
	}

	/**
	 * Sets the min label width.
	 *
	 * @param minLabelWidth the new min label width
	 */
	public void setMinLabelWidth(int minLabelWidth) {
		this.minLabelWidth = minLabelWidth;
	}
}
