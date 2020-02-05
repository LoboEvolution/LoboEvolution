package org.loboevolution.html.dom.input;

import javax.swing.JTextField;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;

public class InputHidden {

	public InputHidden(HTMLInputElementImpl modelNode, InputControl ic) {
		JTextField hidden = new JTextField();
        hidden.setVisible(false);
        ic.add(hidden);
	}

}
