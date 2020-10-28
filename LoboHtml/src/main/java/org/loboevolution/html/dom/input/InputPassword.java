package org.loboevolution.html.dom.input;

import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.swing.JPasswordField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.common.Strings;
import org.loboevolution.component.input.Autocomplete;
import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.renderer.HtmlController;
import org.loboevolution.store.InputStore;

/**
 * <p>InputPassword class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class InputPassword {
	
	private HTMLInputElementImpl modelNode;
	
	private JPasswordField pwd = new JPasswordField();

	/**
	 * <p>Constructor for InputPassword.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputPassword(HTMLInputElementImpl modelNode, InputControl ic) {
		this.modelNode = modelNode;
		final String type = modelNode.getType();
		
		if (modelNode.getTitle() != null)
			pwd.setToolTipText(modelNode.getTitle());
		
		pwd.setVisible(!modelNode.getHidden());
		pwd.applyComponentOrientation(ic.direction(modelNode.getDir()));
		pwd.setEditable(Boolean.parseBoolean(modelNode.getContentEditable()));
		pwd.setEnabled(!modelNode.getDisabled());
		pwd.setEditable(!modelNode.getReadOnly());
		pwd.setDocument(new LimitedDocument());
		pwd.addActionListener(event -> HtmlController.getInstance().onEnterPressed(modelNode));
		final Dimension ps = pwd.getPreferredSize();
		final int size = modelNode.getSize();
		final int width = (128/15) * size;
		pwd.setPreferredSize(new Dimension(width, ps.height));
		final String baseUrl = modelNode.getBaseURI();

		List<String> list = suggestionList(type, "", baseUrl);
		Autocomplete.setupAutoComplete(pwd, list);
		
		pwd.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent event) {
				final boolean isNavigation = modelNode.getUserAgentContext().isNavigationEnabled();
				final String password = String.valueOf(pwd.getPassword());
				InputStore.deleteInput(password, baseUrl);
				InputStore.insertLogin(type, password, baseUrl, isNavigation);
			}
		});
		
		ic.add(pwd);
	}
	
	
	private List<String> suggestionList(String type, String text, String baseUrl) {
		List<String> list = InputStore.autocomplete(type, text, baseUrl);
		if (ArrayUtilities.isNotBlank(list)) {
			return list;
		}
		
		list = new ArrayList<String>();
		list.add(generatePassword());
		return list;
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
		
	private String generatePassword() {
		try {
			String password = Strings.randomAlphaNumeric(8);
			byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(32);
			return Base64.getEncoder().encodeToString(salt) + "$" + Strings.hash(password, salt);
		} catch (Exception e) {
			return "";
		}
	}
}
