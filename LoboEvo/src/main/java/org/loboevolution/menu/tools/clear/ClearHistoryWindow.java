package org.loboevolution.menu.tools.clear;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.LineBorder;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.gui.LoboCheckBox;
import org.loboevolution.gui.LoboPanel;
import org.loboevolution.gui.SwingTasks;

/**
 * <p>ClearHistoryWindow class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ClearHistoryWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	/** The bookmark panel. */
	private LoboCheckBox bookmark;

	/** The cache anel. */
	private LoboCheckBox cache;

	/** The cookie panel. */
	private LoboCheckBox cookie;

	/** The history button. */
	private JButton historyButton;

	/** The navigation panel. */
	private LoboCheckBox navigation;
		
	/** The color text. */
	private final Color COLOR_TEXT = new Color(108, 216, 158);
	

	/**
	 * <p>Constructor for ClearHistoryWindow.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public ClearHistoryWindow(BrowserFrame frame) {
		createAndShowGUI(frame);
	}

	private void createAndShowGUI(BrowserFrame frame) {
		this.cache = new LoboCheckBox("Cache");
		this.cookie = new LoboCheckBox("Cookies");
		this.navigation = new LoboCheckBox("Navigation");
		this.bookmark = new LoboCheckBox("Bookmarks");

		final JButton historyButton = new JButton();
		historyButton.setAction(new ClearDataAction(this.cache, this.cookie, this.navigation, this.bookmark));
		historyButton.setText("Delete Now");
		this.historyButton = historyButton;
		this.historyButton.setForeground(COLOR_TEXT);
		this.historyButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		this.historyButton.setFocusPainted(false);
		this.historyButton.setContentAreaFilled(false);
		this.historyButton.setBorder(new LineBorder(COLOR_TEXT));

		add(getHistoryBox());

	}
	
	/**
	 * Gets the history box.
	 *
	 * @return the history box
	 */
	private Component getHistoryBox() {
		final LoboPanel groupBox = new LoboPanel("Clear History");
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.add(cache);
		groupBox.add(cookie);
		groupBox.add(navigation);
		groupBox.add(bookmark);
		groupBox.add(SwingTasks.createVerticalFill());
		groupBox.add(historyButton);
		return groupBox;
	}
}
