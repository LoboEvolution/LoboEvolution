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

package org.loboevolution.menu.tools.developer;

import com.jtattoo.plaf.lobo.LoboButton;
import com.jtattoo.plaf.lobo.LoboPanel;
import lombok.Getter;
import lombok.Setter;
import org.loboevolution.component.BrowserFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serial;

/**
 * <p>DeveloperToolsWindow class.</p>
 */
public class DeveloperToolsWindow extends JFrame {

	/** The Constant serialVersionUID. */
	@Serial
    private static final long serialVersionUID = 1L;

	/** The Constant frame. */
	@Getter
	@Setter
	private BrowserFrame frame;

	/** The developer panel. */
	@Getter
	private final transient DeveloperToolPanel developerPanel;

	/** The developer tree. */
	private final transient DeveloperToolTree developerTree;

	/**
	 * Instantiates a new tools dialog.
	 *
	 * @param frame a {@link BrowserFrame} object.
	 */
	public DeveloperToolsWindow(final BrowserFrame frame) {
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
	private Component createRightPane(final Container prefsPanel) {
		final LoboPanel rightPanel = new LoboPanel("");
		rightPanel.setPreferredSize(new Dimension(600, 280));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(prefsPanel);
		rightPanel.add(createButtonsPanel());
		return rightPanel;
	}
}