package org.loboevolution.html.dom.input;

import java.awt.Dimension;

import javax.swing.JPasswordField;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.renderer.HtmlController;

public class InputPassword {

	public InputPassword(HTMLInputElementImpl modelNode, InputControl ic) {
		JPasswordField pwd = new JPasswordField();
		if (modelNode.getTitle() != null)
			pwd.setToolTipText(modelNode.getTitle());
		pwd.setVisible(!modelNode.getHidden());
		pwd.applyComponentOrientation(ic.direction(modelNode.getDir()));
		pwd.setEditable(new Boolean(modelNode.getContentEditable()));
		pwd.setEnabled(!modelNode.getDisabled());
		pwd.addActionListener(event -> HtmlController.getInstance().onEnterPressed(modelNode, null));
		final Dimension ps = pwd.getPreferredSize();
		pwd.setPreferredSize(new Dimension(128, ps.height));	
		ic.add(pwd);
	}
}