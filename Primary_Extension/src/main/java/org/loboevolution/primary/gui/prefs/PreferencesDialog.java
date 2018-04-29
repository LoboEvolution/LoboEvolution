/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.gui.prefs;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * The Class PreferencesDialog.
 */
public class PreferencesDialog extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The preferences panel. */
	private final transient PreferencesPanel preferencesPanel;

	/** The preferences tree. */
	private final transient PreferencesTree preferencesTree;

	/**
	 * Instantiates a new preferences dialog.
	 *
	 * @param parent
	 *            the parent
	 * @throws HeadlessException
	 *             the headless exception
	 */
	public PreferencesDialog(Frame parent) throws HeadlessException {
		super(parent);
		this.preferencesPanel = new PreferencesPanel();
		this.preferencesTree = new PreferencesTree();
		createAndShowGUI();
	}

	private void createAndShowGUI() throws HeadlessException {
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		contentPane.add(this.createLeftPane());
		contentPane.add(this.createRightPane(this.getPreferencesPanel()));
		this.preferencesTree.initSelection();
	}

	/**
	 * Creates the left pane.
	 *
	 * @return the component
	 */
	private Component createLeftPane() {
		PreferencesTree prefsTree = this.preferencesTree;
		prefsTree.addTreeSelectionListener(new LocalTreeSelectionListener(this));
		JScrollPane scrollPane = new JScrollPane(prefsTree);
		Dimension size = new Dimension(150, 200);
		scrollPane.setPreferredSize(size);
		scrollPane.setMinimumSize(size);
		scrollPane.setMaximumSize(new Dimension(150, Short.MAX_VALUE));
		return scrollPane;
	}

	/**
	 * Creates the right pane.
	 *
	 * @param prefsPanel
	 *            the prefs panel
	 * @return the component
	 */
	private Component createRightPane(Container prefsPanel) {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(prefsPanel);
		rightPanel.add(this.createButtonsPanel());
		return rightPanel;
	}

	/**
	 * Creates the buttons panel.
	 *
	 * @return the component
	 */
	private Component createButtonsPanel() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.add(Box.createHorizontalGlue());
		JButton okButton = new JButton();
		okButton.setAction(new OkCancelAction(this));
		okButton.setText("OK");
		JButton cancelButton = new JButton();
		cancelButton.setAction(new OkCancelAction(this));
		cancelButton.setText("Cancel");
		JButton applyButton = new JButton();
		applyButton.setAction(new ApplyAction(this));
		applyButton.setText("Apply");
		JButton defaultsButton = new JButton();
		defaultsButton.setAction(new DefaultsAction(this));
		defaultsButton.setText("Restore Defaults");
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(applyButton);
		buttonsPanel.add(defaultsButton);
		return buttonsPanel;
	}

	/**
	 * @return the preferencesPanel
	 */
	public PreferencesPanel getPreferencesPanel() {
		return preferencesPanel;
	}
}
