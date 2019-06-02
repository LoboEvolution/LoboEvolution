package org.lobo.menu.tools.pref.startup;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.lobo.gui.SwingTasks;

public class EditActionStartup extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final StartupListControl control;

	public EditActionStartup(StartupListControl control) {
		this.control = control;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		final Frame parentFrame = SwingTasks.getFrame(this.control);
		SimpleTextEditDialog dialog;
		if (parentFrame != null) {
			dialog = new SimpleTextEditDialog(parentFrame);
		} else {
			final Dialog parentDialog = SwingTasks.getDialog(this.control);
			dialog = new SimpleTextEditDialog(parentDialog);
		}
		dialog.setModal(true);
		dialog.setTitle("Edit List");
		dialog.setCaption(this.control.getEditListCaption());
		dialog.setSize(new Dimension(400, 300));
		dialog.setLocationByPlatform(true);
		dialog.setText(this.control.getStringsAsText());
		dialog.setVisible(true);
		final String text = dialog.getResultingText();
		if (text != null) {
			this.control.setStringsFromText(text);
		}

	}
}
