package org.loboevolution.html.dom.input;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.event.MouseInputAdapter;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.js.Executor;

/**
 * <p>InputColorPicker class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class InputColorPicker {
	
	private HTMLInputElementImpl modelNode;
	
	final JButton widget = new JButton("Choose Color");

	/**
	 * <p>Constructor for InputColorPicker.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputColorPicker(HTMLInputElementImpl modelNode, InputControl ic) {
		this.modelNode = modelNode;
		if (modelNode.getTitle() != null) {
			widget.setToolTipText(modelNode.getTitle());
		}
		widget.setVisible(!modelNode.getHidden());
		widget.applyComponentOrientation(ic.direction(modelNode.getDir()));
		widget.setEnabled(!modelNode.getDisabled());

		widget.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Color c = JColorChooser.showDialog(null, "Choose a Color", null);
				String value = "#" + Integer.toHexString(c.getRGB()).substring(2);
				modelNode.setValue(value);
				widget.setToolTipText(value);
				widget.setBackground(c);
			}
		});

		MouseInputAdapter mouseHandler = new MouseInputAdapter() {

			@Override
			public void mouseEntered(final MouseEvent e) {
				if (modelNode.getOnmouseover() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnmouseover(), null, new Object[] {});
				}
			}
		};
		widget.addMouseListener(mouseHandler);

		ic.add(widget);
	}

	/**
	 * <p>reset.</p>
	 */
	public void reset() {
		Color c = Color.BLACK;
		String value = "#" + Integer.toHexString(c.getRGB()).substring(2);
		modelNode.setValue(value);
		widget.setToolTipText(value);
		widget.setBackground(c);
	}
}
