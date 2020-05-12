package org.loboevolution.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * The Class CheckBoxPanel.
 *
 * @author utente
 * @version $Id: $Id
 */
public class CheckBoxPanel extends JPanel {

	/**
	 * The Class CheckBoxAction.
	 */
	public class CheckBoxAction extends AbstractAction {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingTasks.setNestedEnabled(CheckBoxPanel.this.subPanel, CheckBoxPanel.this.checkBox.isSelected());
		}
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The check box. */
	private final JCheckBox checkBox;

	/** The sub panel. */
	private final JComponent subPanel;

	/**
	 * Instantiates a new check box panel.
	 *
	 * @param text     the text
	 * @param subPanel the sub panel
	 */
	public CheckBoxPanel(String text, JComponent subPanel) {
		this.subPanel = subPanel;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.checkBox = new JCheckBox();
		this.checkBox.setAlignmentX(0.0f);
		this.checkBox.setAction(new CheckBoxAction());
		this.checkBox.setText(text);
		final JPanel checkBoxArea = new JPanel();
		checkBoxArea.setLayout(new BoxLayout(checkBoxArea, BoxLayout.X_AXIS));
		checkBoxArea.add(this.checkBox);
		checkBoxArea.add(SwingTasks.createHorizontalFill());
		this.add(checkBoxArea);
		this.add(subPanel);
		SwingTasks.setNestedEnabled(subPanel, this.checkBox.isSelected());
	}

	/**
	 * Checks if is selected.
	 *
	 * @return true, if is selected
	 */
	public boolean isSelected() {
		return this.checkBox.isSelected();
	}

	/**
	 * Sets the selected.
	 *
	 * @param selected the new selected
	 */
	public void setSelected(boolean selected) {
		this.checkBox.setSelected(selected);
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.checkBox.setText(text);
	}

	/**
	 * Update enabling.
	 */
	public void updateEnabling() {
		SwingTasks.setNestedEnabled(this.subPanel, this.checkBox.isSelected());
	}
}
