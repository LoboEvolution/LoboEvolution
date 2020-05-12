package org.loboevolution.html.dom.input;

import javax.swing.JTextField;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;

/**
 * <p>InputHidden class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class InputHidden {

	/**
	 * <p>Constructor for InputHidden.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputHidden(HTMLInputElementImpl modelNode, InputControl ic) {
		JTextField hidden = new JTextField();
        hidden.setVisible(false);
        ic.add(hidden);
	}
}
