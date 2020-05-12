package org.loboevolution.menu.tools.pref.startup;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.gui.SwingTasks;

/**
 * <p>EditActionStartup class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class EditActionStartup extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final StartupListControl control;

	/**
	 * <p>Constructor for EditActionStartup.</p>
	 *
	 * @param control a {@link org.loboevolution.menu.tools.pref.startup.StartupListControl} object.
	 */
	public EditActionStartup(StartupListControl control) {
		this.control = control;
	}

	/** {@inheritDoc} */
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
