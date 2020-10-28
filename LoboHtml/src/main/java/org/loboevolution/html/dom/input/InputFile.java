package org.loboevolution.html.dom.input;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;

/**
 * <p>InputFile class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class InputFile {
	
	private final JTextField textField = new JTextField();
	
	private InputControl control;

	/**
	 * <p>Constructor for InputFile.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputFile(HTMLInputElementImpl modelNode, InputControl ic) {
		control = ic;
		final JButton browseButton = new JButton();
		browseButton.setContentAreaFilled(false);
		browseButton.setAction(new BrowseAction());
		browseButton.setText("Browse");
		final Dimension ps = this.textField.getPreferredSize();
		this.textField.setPreferredSize(new Dimension(128, ps.height));
		this.textField.setEditable(false);
		if (modelNode.getTitle() != null)
			this.textField.setToolTipText(modelNode.getTitle());
		textField.setVisible(!modelNode.getHidden());
		textField.applyComponentOrientation(ic.direction(modelNode.getDir()));
		textField.setEditable(Boolean.parseBoolean(modelNode.getContentEditable()));
		textField.setEnabled(!modelNode.getDisabled());

		Box buttonBar = Box.createHorizontalBox();
		buttonBar.add(Box.createHorizontalGlue());
		buttonBar.add(textField);
		buttonBar.add(browseButton);
		ic.add(buttonBar, BorderLayout.SOUTH);

	}
	
	
	private class BrowseAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final JFileChooser chooser = new JFileChooser();
			if (chooser.showOpenDialog(control) == JFileChooser.APPROVE_OPTION) {
				String paths = chooser.getSelectedFile().getAbsolutePath();
				textField.setText(paths);
			} else {
				textField.setText("");
			}
		}
	}
}
