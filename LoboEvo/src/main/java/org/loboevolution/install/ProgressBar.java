/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.install;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * <p>ProgressBar class.</p>
 *
 *
 *
 */
public class ProgressBar extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>main.</p>
	 *
	 * @param args an array of {@link java.lang.String} objects.
	 */
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(ProgressBar::new);
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
