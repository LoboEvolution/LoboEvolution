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

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * <p>ProgressBarUI class.</p>
 *
 *
 *
 */
public class ProgressBarUI extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	private final JProgressBar progressBar;
	private final StorageManager storage;
	private final JTextArea taskOutput;

	/**
	 * <p>Constructor for ProgressBarUI.</p>
	 *
	 * @param frame a {@link javax.swing.JFrame} object.
	 */
	public ProgressBarUI(JFrame frame) {
		super(new BorderLayout());

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		this.taskOutput = new JTextArea(5, 20);
		this.taskOutput.setMargin(new Insets(5, 5, 5, 5));
		this.taskOutput.setEditable(false);

		this.storage = new StorageManager(frame);
		this.storage.addPropertyChangeListener(this);
		this.storage.execute();

		this.progressBar = new JProgressBar(0, 100);
		this.progressBar.setValue(0);
		this.progressBar.setStringPainted(true);

		final JPanel panel = new JPanel();
		panel.add(this.progressBar);

		add(panel, BorderLayout.PAGE_START);
		add(new JScrollPane(this.taskOutput), BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}

	/** {@inheritDoc} */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			final int progress = (Integer) evt.getNewValue();
			this.progressBar.setValue(progress);
			this.taskOutput.append(String.format("Completed %d%% of task.\n", this.storage.getProgress()));
		}
	}
}
