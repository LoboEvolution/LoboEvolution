package org.loboevolution.menu.tools.pref.startup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import org.loboevolution.gui.LoboButton;

/**
 * <p>StartupListControl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class StartupListControl extends JComponent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The combo box. */
	private final JComboBox<String> comboBox;

	/** The edit list caption. */
	private String editListCaption;

	/** The strings. */
	private List<String> strings;

	/**
	 * Instantiates a String.valueOf list control.
	 */
	public StartupListControl() {
		this.comboBox = new JComboBox<String>();
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.comboBox.setEditable(false);
		final LoboButton editButton = new LoboButton();
		editButton.setAction(new EditActionStartup(this));
		editButton.setText("Edit List");
		this.add(this.comboBox);
		this.add(editButton);
	}

	/**
	 * Gets the edit list caption.
	 *
	 * @return the edit list caption
	 */
	public String getEditListCaption() {
		return this.editListCaption;
	}

	/**
	 * Gets the strings.
	 *
	 * @return the strings
	 */
	public List<String> getStrings() {
		return this.strings;
	}

	/**
	 * Gets the strings as text.
	 *
	 * @return the strings as text
	 */
	public String getStringsAsText() {
		final String lineSeparator = System.getProperty("line.separator");
		final List<String> strings = this.strings;
		if (strings == null) {
			return null;
		}
		final StringBuilder buffer = new StringBuilder();
		for (final String string : strings) {
			buffer.append(string);
			buffer.append(lineSeparator);
		}
		return buffer.toString();
	}

	/**
	 * Sets the edit list caption.
	 *
	 * @param caption the new edit list caption
	 */
	public void setEditListCaption(String caption) {
		this.editListCaption = caption;
	}

	/**
	 * Sets the strings.
	 *
	 * @param strings the String.valueOfs
	 */
	public void setStrings(List<String> strings) {
		this.strings = strings;
		final JComboBox<String> comboBox = this.comboBox;
		comboBox.removeAllItems();
		for (final String string : strings) {
			comboBox.addItem(string);
		}
	}

	/**
	 * Sets the strings from text.
	 *
	 * @param text the String.valueOfs from text
	 */
	public void setStringsFromText(String text) {
		try {
			final BufferedReader reader = new BufferedReader(new StringReader(text));
			String line;
			final ArrayList<String> stringsAL = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				stringsAL.add(line);
			}
			setStrings(stringsAL);
		} catch (final IOException ioe) {
			throw new IllegalStateException("not expected", ioe);
		}
	}
}
