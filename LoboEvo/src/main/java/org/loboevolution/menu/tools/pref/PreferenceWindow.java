/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.menu.tools.pref;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.io.Serial;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import lombok.Getter;
import lombok.Setter;
import org.loboevolution.component.BrowserFrame;

import com.jtattoo.plaf.lobo.LoboButton;
import com.jtattoo.plaf.lobo.LoboPanel;

/**
 * <p>PreferenceWindow class.</p>
 */
public class PreferenceWindow extends JFrame {

	/** The Constant serialVersionUID. */
	@Serial
    private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private BrowserFrame frame;

	/** The preferences panel. */
	@Getter
	private final transient PreferencesPanel preferencesPanel;

	/** The preferences tree. */
	private final transient PreferencesTree preferencesTree;

	/**
	 * Instantiates a new preferences dialog.
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public PreferenceWindow(final BrowserFrame frame) {
		this.frame = frame;
		this.preferencesPanel = new PreferencesPanel();
		this.preferencesTree = new PreferencesTree();
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		setResizable(false);
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		contentPane.add(createLeftPane());
		contentPane.add(createRightPane(getPreferencesPanel()));
		this.preferencesTree.initSelection();
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
		final LoboButton okButton = new LoboButton();
		okButton.setAction(new OkCancelAction(this));
		okButton.setText("OK");
		final LoboButton cancelButton = new LoboButton();
		cancelButton.setAction(new OkCancelAction(this));
		cancelButton.setText("Cancel");
		final LoboButton applyButton = new LoboButton();
		applyButton.setAction(new ApplyAction(this));
		applyButton.setText("Apply");
		final LoboButton defaultsButton = new LoboButton();
		defaultsButton.setAction(new DefaultsAction(this));
		defaultsButton.setText("Restore Defaults");
		buttonsPanel.add(okButton);
		buttonsPanel.add(Box.createHorizontalStrut(5));
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(Box.createHorizontalStrut(5));
		buttonsPanel.add(applyButton);
		buttonsPanel.add(Box.createHorizontalStrut(5));
		buttonsPanel.add(defaultsButton);
		return buttonsPanel;
	}

	/**
	 * Creates the left pane.
	 *
	 * @return the component
	 */
	private Component createLeftPane() {
		final PreferencesTree prefsTree = this.preferencesTree;
		prefsTree.addTreeSelectionListener(new LocalTreeSelectionListener(this));
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
	private Component createRightPane(final Container prefsPanel) {
		final LoboPanel rightPanel = new LoboPanel("");
		rightPanel.setPreferredSize(new Dimension(420, 280));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(prefsPanel);
		rightPanel.add(createButtonsPanel());
		return rightPanel;
	}
}