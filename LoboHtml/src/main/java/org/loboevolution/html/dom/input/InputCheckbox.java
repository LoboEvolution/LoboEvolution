package org.loboevolution.html.dom.input;

import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.event.MouseInputAdapter;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.renderer.HtmlController;

/**
 * <p>InputCheckbox class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class InputCheckbox {
	
	final JCheckBox checkBox = new JCheckBox();

	/**
	 * <p>Constructor for InputCheckbox.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputCheckbox(HTMLInputElementImpl modelNode, InputControl ic) {
		checkBox.setOpaque(false);
		if(modelNode.getTitle() != null) checkBox.setToolTipText(modelNode.getTitle());
		checkBox.setVisible(!modelNode.getHidden());
		checkBox.applyComponentOrientation(ic.direction(modelNode.getDir()));
		checkBox.setSelected(modelNode.getAttributeAsBoolean("checked"));
		checkBox.setEnabled(!modelNode.getDisabled());
		checkBox.setSelected(modelNode.getChecked());
		checkBox.addActionListener(event -> HtmlController.getInstance().onPressed(modelNode, null, 0, 0));
		
		MouseInputAdapter mouseHandler = new MouseInputAdapter() {

			@Override
			public void mouseEntered(final MouseEvent e) {
				if (modelNode.getOnmouseover() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnmouseover(), null, new Object[] {});
				}
			}
		};
		checkBox.addMouseListener(mouseHandler);
		
		ic.add(checkBox);
	}

	/**
	 * <p>reset.</p>
	 */
	public void reset() {
		checkBox.setSelected(false);
	}
}
