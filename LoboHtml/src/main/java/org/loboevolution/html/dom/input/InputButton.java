package org.loboevolution.html.dom.input;

import java.awt.Color;

import javax.swing.JButton;

import org.loboevolution.common.Strings;
import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.renderer.HtmlController;

/**
 * <p>InputButton class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class InputButton {

	/**
	 * <p>Constructor for InputButton.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputButton(HTMLInputElementImpl modelNode, InputControl ic) {
		final JButton button = new JButton();
		button.setContentAreaFilled(false);
		
		if (modelNode.getTitle() != null) {
			button.setToolTipText(modelNode.getTitle());
		}
		
		button.setVisible(!modelNode.getHidden());
		button.applyComponentOrientation(ic.direction(modelNode.getDir()));
		button.setEnabled(!modelNode.getDisabled());
		button.setText(getText(modelNode));
		button.addActionListener(event -> HtmlController.getInstance().onPressed(modelNode, null, 0, 0));
		
		button.setContentAreaFilled(!ic.getRUIControl().hasBackground());
		final Color foregroundColor = ic.getRUIControl().getForegroundColor();
		if (foregroundColor != null) {
			button.setForeground(foregroundColor);
		}

		ic.add(button);
	}

	private String getText(HTMLInputElementImpl element) {
		String text = element.getAttribute("value");
		if (Strings.isBlank(text)) {
			final String type = element.getType();
			if ("submit".equalsIgnoreCase(type)) {
				text = "Submit Query";
			} else if ("reset".equalsIgnoreCase(type)) {
				text = "Reset";
			} else {
				text = "";
			}
		}
		return text;
	}
}
