package org.loboevolution.html.dom.input;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;

public class InputRadio {
	
	private ButtonGroup buttonGroup;
	
	final JRadioButton radio = new JRadioButton();

	public InputRadio(HTMLInputElementImpl modelNode, InputControl ic) {
		radio.setOpaque(false);
		if(modelNode.getTitle() != null) radio.setToolTipText(modelNode.getTitle());
		radio.setVisible(!modelNode.getHidden());
		radio.applyComponentOrientation(ic.direction(modelNode.getDir()));
		radio.setEnabled(!modelNode.getDisabled());
		radio.setSelected(modelNode.getChecked());
		final String name = modelNode.getAttribute("name");
		final ButtonGroup prevGroup = this.buttonGroup;
		if (prevGroup != null) {
			prevGroup.remove(radio);
		}
		if (name != null) {
			final String key = "cobra.radio.group." + name;
			ButtonGroup group = (ButtonGroup) modelNode.getDocumentItem(key);
			if (group == null) {
				group = new ButtonGroup();
				modelNode.setDocumentItem(key, group);
			}
			group.add(radio);
			this.buttonGroup = group;
		} else {
			this.buttonGroup = null;
		}

		ic.add(radio);
	}

	public void reset() {
		radio.setSelected(false);
	}
}