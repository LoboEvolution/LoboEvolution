package org.loboevolution.html.dom.input;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;

public class InputColorPicker {

	public InputColorPicker(HTMLInputElementImpl modelNode, InputControl ic) {
		JButton widget = new JButton("Choose Color");
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

		ic.add(widget);
	}
}