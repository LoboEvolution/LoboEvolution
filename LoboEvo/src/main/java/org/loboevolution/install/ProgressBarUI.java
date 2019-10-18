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

public class ProgressBarUI extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	private final JProgressBar progressBar;
	private final StorageManager storage;
	private final JTextArea taskOutput;

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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			final int progress = (Integer) evt.getNewValue();
			this.progressBar.setValue(progress);
			this.taskOutput.append(String.format("Completed %d%% of task.\n", this.storage.getProgress()));
		}
	}
}
