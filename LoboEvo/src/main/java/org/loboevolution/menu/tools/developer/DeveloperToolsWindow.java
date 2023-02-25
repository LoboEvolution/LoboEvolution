/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.menu.tools.developer;

import com.jtattoo.plaf.lobo.LoboButton;
import com.jtattoo.plaf.lobo.LoboPanel;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.menu.tools.pref.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * <p>DeveloperToolsWindow class.</p>
 */
public class DeveloperToolsWindow extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant frame. */
	private BrowserFrame frame;

	/** The developer panel. */
	private final transient DeveloperToolPanel developerPanel;

	/** The developer tree. */
	private final transient DeveloperToolTree developerTree;

	/**
	 * Instantiates a new tools dialog.
	 *
	 * @param frame a {@link BrowserFrame} object.
	 */
	public DeveloperToolsWindow(BrowserFrame frame) {
		this.frame = frame;
		this.developerPanel = new DeveloperToolPanel();
		this.developerTree = new DeveloperToolTree(frame);
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		setResizable(false);
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		contentPane.add(createLeftPane());
		contentPane.add(createRightPane(getDeveloperPanel()));
		this.developerTree.initSelection();
	}

	/**
	 * Creates the buttons panel.
	 *
	 * @return the component
	 */
	private Component createButtonsPanel() {
		final LoboPanel buttonsPanel = new LoboPanel("");
		buttonsPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.add(Box.createHorizontalGlue());
		final LoboButton close = new LoboButton();
		close.setAction(new CloseAction(this));
		close.setText("Close");
		buttonsPanel.add(close);
		return buttonsPanel;
	}

	/**
	 * Creates the left pane.
	 *
	 * @return the component
	 */
	private Component createLeftPane() {
		final DeveloperToolTree prefsTree = this.developerTree;
		prefsTree.addTreeSelectionListener(new DeveloperToolsTreeSelectionListener(this));
		final JScrollPane scrollPane = new JScrollPane(prefsTree);
		final Dimension size = new Dimension(150, 200);
		scrollPane.setPreferredSize(size);
		scrollPane.setMinimumSize(size);
		scrollPane.setMaximumSize(new Dimension(150, Short.MAX_VALUE));
		return scrollPane;
	}

	/**
	 * Creates the right pane.
	 *
	 * @param prefsPanel the prefs panel
	 * @return the component
	 */
	private Component createRightPane(Container prefsPanel) {
		final LoboPanel rightPanel = new LoboPanel("");
		rightPanel.setPreferredSize(new Dimension(600, 280));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(prefsPanel);
		rightPanel.add(createButtonsPanel());
		return rightPanel;
	}

	/**
	 * <p>Getter for the field frame.</p>
	 *
	 * @return the frame
	 */
	public BrowserFrame getFrame() {
		return this.frame;
	}

	/**
	 * <p>Getter for the field developerPanel.</p>
	 *
	 * @return the developerPanel
	 */
	public DeveloperToolPanel getDeveloperPanel() {
		return this.developerPanel;
	}

	/**
	 * <p>Setter for the field frame.</p>
	 *
	 * @param frame the frame to set
	 */
	public void setFrame(BrowserFrame frame) {
		this.frame = frame;
	}
}
