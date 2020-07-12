package org.loboevolution.menu.tools.pref;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.gui.Panel;

/**
 * <p>PreferenceWindow class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class PreferenceWindow extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private BrowserFrame frame;

	/** The preferences panel. */
	private final transient PreferencesPanel preferencesPanel;

	/** The preferences tree. */
	private final transient PreferencesTree preferencesTree;

	/**
	 * Instantiates a new preferences dialog.
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public PreferenceWindow(BrowserFrame frame) {
		this.frame = frame;
		this.preferencesPanel = new PreferencesPanel();
		this.preferencesTree = new PreferencesTree();
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		setResizable(false);
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		contentPane.add(createLeftPane());
		contentPane.add(createRightPane(getPreferencesPanel()));
		this.preferencesTree.initSelection();
	}

	/**
	 * Creates the buttons panel.
	 *
	 * @return the component
	 */
	private Component createButtonsPanel() {
		final Panel buttonsPanel = new Panel("");
		buttonsPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.add(Box.createHorizontalGlue());
		final JButton okButton = new JButton();
		okButton.setAction(new OkCancelAction(this));
		okButton.setText("OK");
		final JButton cancelButton = new JButton();
		cancelButton.setAction(new OkCancelAction(this));
		cancelButton.setText("Cancel");
		final JButton applyButton = new JButton();
		applyButton.setAction(new ApplyAction(this));
		applyButton.setText("Apply");
		final JButton defaultsButton = new JButton();
		defaultsButton.setAction(new DefaultsAction(this));
		defaultsButton.setText("Restore Defaults");
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(applyButton);
		buttonsPanel.add(defaultsButton);
		return buttonsPanel;
	}

	/**
	 * Creates the left pane.
	 *
	 * @return the component
	 */
	private Component createLeftPane() {
		final PreferencesTree prefsTree = this.preferencesTree;
		prefsTree.addTreeSelectionListener(new LocalTreeSelectionListener(this));
		final JScrollPane scrollPane = new JScrollPane(prefsTree);
		final Dimension size = new Dimension(150, 200);
		scrollPane.setPreferredSize(size);
		scrollPane.setMinimumSize(size);
		scrollPane.setMaximumSize(new Dimension(150, Short.MAX_VALUE));
		return scrollPane;
	}

	/**
	 * Creates the right pane.
	 *
	 * @param prefsPanel the prefs panel
	 * @return the component
	 */
	private Component createRightPane(Container prefsPanel) {
		final Panel rightPanel = new Panel("");
		rightPanel.setPreferredSize(new Dimension(420, 280));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(prefsPanel);
		rightPanel.add(createButtonsPanel());
		return rightPanel;
	}

	/**
	 * <p>Getter for the field frame.</p>
	 *
	 * @return the frame
	 */
	public BrowserFrame getFrame() {
		return this.frame;
	}

	/**
	 * <p>Getter for the field preferencesPanel.</p>
	 *
	 * @return the preferencesPanel
	 */
	public PreferencesPanel getPreferencesPanel() {
		return this.preferencesPanel;
	}

	/**
	 * <p>Setter for the field frame.</p>
	 *
	 * @param frame the frame to set
	 */
	public void setFrame(BrowserFrame frame) {
		this.frame = frame;
	}
}
