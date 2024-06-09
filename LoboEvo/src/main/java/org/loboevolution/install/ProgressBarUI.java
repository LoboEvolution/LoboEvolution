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

package org.loboevolution.install;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serial;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * <p>ProgressBarUI class.</p>
 */
public class ProgressBarUI extends JPanel implements PropertyChangeListener {

	@Serial
    private static final long serialVersionUID = 1L;

	private final JProgressBar progressBar;
	private final StorageManager storage;
	private final JTextArea taskOutput;

	/**
	 * <p>Constructor for ProgressBarUI.</p>
	 *
	 * @param frame a {@link javax.swing.JFrame} object.
	 */
	public ProgressBarUI(final JFrame frame) {
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
	public void propertyChange(final PropertyChangeEvent evt) {
		if ("progress".equals(evt.getPropertyName())) {
			final int progress = (Integer) evt.getNewValue();
			this.progressBar.setValue(progress);
			this.taskOutput.append(String.format("Completed %d%% of task.\n", this.storage.getProgress()));
		}
	}
}
