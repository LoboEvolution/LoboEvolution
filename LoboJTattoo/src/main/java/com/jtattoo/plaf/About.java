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

package com.jtattoo.plaf;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * <p>About class.</p>
 *
 * Author Michael Hagen
 *
 */

// TODO:
// - Auf dem Mac scheint es ein Problem mit dem Zeichnen des Aluminium Hintergrunds zu geben
// - setMaximizedBounds unter Linux bei multiscreen Umgebungen funktioniert nicht. Aus diesem Grund
//   wird in Linux die Toolbar beim maximieren verdeckt (siehe BaseTitlePane maximize)
public class About extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Constant JTATTOO_VERSION="Version: 1.6.12" */
	public static final String JTATTOO_VERSION = "Version: 1.6.12";

	private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	private static final Dimension DLG_SIZE = new Dimension(440, 240);
	private static final int DLG_POS_X = SCREEN_SIZE.width / 2 - DLG_SIZE.width / 2;
	private static final int DLG_POS_Y = SCREEN_SIZE.height / 2 - DLG_SIZE.height / 2;

	/**
	 * <p>Constructor for About.</p>
	 */
	public About() {
		super((JFrame) null, "About JTattoo");
		final JPanel contentPanel = new JPanel(null);
		final JLabel titleLabel = new JLabel("JTattoo " + JTATTOO_VERSION);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setBounds(0, 20, DLG_SIZE.width - 8, 36);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(titleLabel);

		final JLabel copyrightLabel = new JLabel("(c) 2002 and later by MH Software-Entwicklung");
		copyrightLabel.setBounds(0, 80, DLG_SIZE.width - 8, 20);
		copyrightLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(copyrightLabel);

		final JButton okButton = new JButton("OK");
		okButton.setBounds((DLG_SIZE.width - 80) / 2, 170, 80, 24);
		contentPanel.add(okButton);

		setContentPane(contentPanel);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(final WindowEvent ev) {
				System.exit(0);
			}
		});

		okButton.addActionListener(ev -> System.exit(0));
	}

} // end of class About
