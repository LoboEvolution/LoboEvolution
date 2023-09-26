/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.install;

import javax.swing.*;

/**
 * <p>ProgressBar class.</p>
 */
public class ProgressBar extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>main.</p>
	 *
	 * @param args an array of {@link java.lang.String} objects.
	 */
	public static void main(final String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(ProgressBar::new);
	}

	/**
	 * <p>Constructor for ProgressBar.</p>
	 */
	public ProgressBar() {
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		setTitle("Installing...");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		final JComponent newContentPane = new ProgressBarUI(this);
		newContentPane.setOpaque(true);
		setContentPane(newContentPane);
		pack();
		setVisible(true);

	}

}
