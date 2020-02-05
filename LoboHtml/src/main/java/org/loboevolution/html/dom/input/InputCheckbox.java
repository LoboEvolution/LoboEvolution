package org.loboevolution.html.dom.input;

import javax.swing.JCheckBox;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.renderer.HtmlController;

public class InputCheckbox {

	public InputCheckbox(HTMLInputElementImpl modelNode, InputControl ic) {
		final JCheckBox checkBox = new JCheckBox();
		checkBox.setOpaque(false);
		if(modelNode.getTitle() != null) checkBox.setToolTipText(modelNode.getTitle());
		checkBox.setVisible(!modelNode.getHidden());
		checkBox.applyComponentOrientation(ic.direction(modelNode.getDir()));
		checkBox.setSelected(modelNode.getAttributeAsBoolean("checked"));
		checkBox.setEnabled(!modelNode.getDisabled());
		checkBox.setSelected(modelNode.getChecked());
		checkBox.addActionListener(event -> HtmlController.getInstance().onPressed(modelNode, null, 0, 0));
		ic.add(checkBox);
	}
}