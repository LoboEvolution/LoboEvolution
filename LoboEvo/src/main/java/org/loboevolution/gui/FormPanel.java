/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

package org.loboevolution.gui;

import lombok.Getter;
import lombok.Setter;

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
 */
public class FormPanel extends JComponent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The fields. */
	private final transient Collection<FormField> fields = new ArrayList<>();

	/** The fields invalid. */
	private boolean fieldsInvalid = false;

	/** The min label width. */
	@Getter
	@Setter
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
	public void addField(final FormField field) {
		// Call in GUI thread only.
		this.fields.add(field);
		this.fieldsInvalid = true;
	}

	private void createAndShowGUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	/** {@inheritDoc} */
	@Override
	public void doLayout() {
		if (this.fieldsInvalid) {
			populateComponents();
		}
		super.doLayout();
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getMaximumSize() {
		if (this.fieldsInvalid) {
			populateComponents();
		}
		return super.getMaximumSize();
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getMinimumSize() {
		if (this.fieldsInvalid) {
			populateComponents();
		}
		return super.getMinimumSize();
	}

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

	/** {@inheritDoc} */
	@Override
	public void revalidate() {
		this.fieldsInvalid = true;
		super.revalidate();
	}
}