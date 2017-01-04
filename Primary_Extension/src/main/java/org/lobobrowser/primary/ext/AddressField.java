/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.ext;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * The Class AddressField.
 */
public class AddressField extends JComboBox<String> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The component source. */
	private final ComponentSource componentSource;

	/**
	 * Instantiates a new address field.
	 *
	 * @param cs
	 *            the cs
	 */
	public AddressField(ComponentSource cs) {
		this.componentSource = cs;
		this.setEditable(true);
		TextFieldComboBoxEditor editor = new TextFieldComboBoxEditor();
		this.setEditor(editor);
		editor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				onKeyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				onKeyPressed(e);
			}
		});
		this.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				onBeforePopupVisible();
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String cmd = event.getActionCommand();
				if ("comboBoxEdited".equals(cmd)) {
					onEdited(event.getModifiers());
				} else if ("comboBoxChanged".equals(cmd)) {
				}
			}
		});
		// This needed the first time to set a reasonable
		// popup size.
		this.onBeforePopupVisible();
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		if (this.isEditable()) {
			return (String) this.getEditor().getItem();
		} else {
			return String.valueOf(this.getSelectedItem());
		}
	}

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	public void setText(String text) {
		JComboBox<String> combo = this;
		boolean editable = this.isEditable();
		if (editable) {
			combo.getEditor().setItem(text);
		}
	}

	/**
	 * Sets the url.
	 *
	 * @param url
	 *            the new url
	 */
	public void setUrl(URL url) {
		this.setText(url == null ? "" : url.toExternalForm());
	}

	/**
	 * On before popup visible.
	 */
	private void onBeforePopupVisible() {
		if ((comboInvalid || comboHasHeadMatches) && !populatingMatches) {
			populateCombo(this.getText());
		}
	}

	/** The combo invalid. */
	private boolean comboInvalid = true;

	/** The combo has head matches. */
	private boolean comboHasHeadMatches = false;

	/** The populating matches. */
	private boolean populatingMatches = false;

	/**
	 * Populate combo.
	 *
	 * @param comboBoxText
	 *            the combo box text
	 */
	private void populateCombo(String comboBoxText) {
		// Expected to be called in GUI thread.
		this.populatingMatches = true;
		try {
			JComboBox<String> urlComboBox = this;
			urlComboBox.removeAllItems();
			Collection<String> recentUrls = this.componentSource.getRecentLocations(30);
			Iterator<String> i = recentUrls.iterator();
			while (i.hasNext()) {
				String matchUrl = i.next();
				urlComboBox.addItem(matchUrl);
			}
			this.setText(comboBoxText);
			this.comboHasHeadMatches = false;
			this.comboInvalid = false;
		} finally {
			this.populatingMatches = false;
		}
	}

	/**
	 * On edited.
	 *
	 * @param modifiers
	 *            the modifiers
	 */
	private void onEdited(int modifiers) {
		// if(this.getText().length() != 0) {
		// this.componentSource.navigateOrSearch();
		// }
	}

	/**
	 * On key released.
	 *
	 * @param event
	 *            the event
	 */
	private void onKeyReleased(KeyEvent event) {
		AddressField urlComboBox = this;
		char releasedChar = event.getKeyChar();
		if (validPopupChar(releasedChar)) {
			String urlText = urlComboBox.getText();
			Collection<String> headMatches = this.componentSource.getPotentialMatches(urlText);
			if (headMatches.size() == 0) {
				if (urlComboBox.isPopupVisible()) {
					urlComboBox.hidePopup();
				}
			} else {
				populatingMatches = true;
				try {
					urlComboBox.removeAllItems();
					Iterator<String> i = headMatches.iterator();
					while (i.hasNext()) {
						String matchUrl = (String) i.next();
						urlComboBox.addItem(matchUrl);
					}
					comboHasHeadMatches = true;
					if (!urlComboBox.isPopupVisible()) {
						urlComboBox.showPopup();
					}
					urlComboBox.setSelectedItem(null);
					urlComboBox.setText(urlText);
				} finally {
					populatingMatches = false;
				}
			}
		}

	}

	/**
	 * On key pressed.
	 *
	 * @param event
	 *            the event
	 */
	private void onKeyPressed(KeyEvent event) {
		AddressField urlComboBox = this;
		if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			String urlText = urlComboBox.getText();
			if (urlText.length() != 0) {
				this.componentSource.navigateOrSearch();
			}
		}
	}

	/**
	 * Valid popup char.
	 *
	 * @param ch
	 *            the ch
	 * @return true, if successful
	 */
	private boolean validPopupChar(char ch) {
		return Character.isLetterOrDigit(ch) || (ch == '.') || (ch == '/');
	}
}
