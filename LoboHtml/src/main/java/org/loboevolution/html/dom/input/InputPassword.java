package org.loboevolution.html.dom.input;

import java.awt.Dimension;

import javax.swing.JPasswordField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.renderer.HtmlController;

/**
 * <p>InputPassword class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class InputPassword {

	private HTMLInputElementImpl modelNode;
	
	JPasswordField pwd = new JPasswordField();

	/**
	 * <p>Constructor for InputPassword.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputPassword(HTMLInputElementImpl modelNode, InputControl ic) {
		this.modelNode = modelNode;
		if (modelNode.getTitle() != null)
			pwd.setToolTipText(modelNode.getTitle());
		pwd.setVisible(!modelNode.getHidden());
		pwd.applyComponentOrientation(ic.direction(modelNode.getDir()));
		pwd.setEditable(Boolean.valueOf(modelNode.getContentEditable()));
		pwd.setEnabled(!modelNode.getDisabled());
		pwd.setEditable(!modelNode.getReadOnly());
		pwd.setDocument(new LimitedDocument());
		pwd.addActionListener(event -> HtmlController.getInstance().onEnterPressed(modelNode));
		final Dimension ps = 		pwd.getPreferredSize();
		final int size = modelNode.getSize();
		final int width = (128/15) * size;
		pwd.setPreferredSize(new Dimension(width, ps.height));
		ic.add(pwd);
	}
	
	private class LimitedDocument extends PlainDocument {

		private static final long serialVersionUID = 1L;

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			int max = modelNode.getMaxLength();

			final int docLength = getLength();
			if (docLength >= max) {
				return;
			}
			final int strLen = str.length();
			if (docLength + strLen > max) {
				final String shorterStr = str.substring(0, max - docLength);
				super.insertString(offs, shorterStr, a);
			} else {
				super.insertString(offs, str, a);
			}
		}
	}

	/**
	 * <p>reset.</p>
	 */
	public void reset() {
		pwd.setText("");
	}
}
