package org.loboevolution.menu.tools.pref.search;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import org.loboevolution.gui.AbstractItemEditor;
import org.loboevolution.gui.ItemEditorFactory;

/**
 * The Class ItemEditorDialog.
 *
 * @param <T> the generic type
 * @author utente
 * @version $Id: $Id
 */
public class ItemEditorDialog<T> extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cancel button. */
	private final JButton cancelButton = new JButton();

	/** The caption label. */
	private final JLabel captionLabel = new JLabel();

	/** The editor. */
	private final transient AbstractItemEditor<T> editor;

	/** The ok button. */
	private final JButton okButton = new JButton();

	/** The resulting item. */
	private transient T resultingItem;

	/**
	 * Instantiates a new item editor dialog.
	 *
	 * @param owner   the owner
	 * @param factory the factory
	 * @throws java.awt.HeadlessException the headless exception
	 */
	public ItemEditorDialog(Dialog owner, ItemEditorFactory<T> factory) throws HeadlessException {
		super(owner);
		this.editor = factory.createItemEditor();
		this.init();
	}

	/**
	 * Instantiates a new item editor dialog.
	 *
	 * @param owner   the owner
	 * @param factory the factory
	 * @throws java.awt.HeadlessException the headless exception
	 */
	public ItemEditorDialog(Frame owner, ItemEditorFactory<T> factory) throws HeadlessException {
		super(owner);
		this.editor = factory.createItemEditor();
		this.init();
	}

	/**
	 * Creates the button panel.
	 *
	 * @return the component
	 */
	private Component createButtonPanel() {
		final Box panel = new Box(BoxLayout.X_AXIS);
		panel.setBorder(new EmptyBorder(4, 4, 4, 4));
		panel.add(Box.createGlue());
		panel.add(this.okButton);
		panel.add(Box.createRigidArea(new Dimension(4, 1)));
		panel.add(this.cancelButton);
		panel.add(Box.createGlue());
		return panel;
	}

	/**
	 * <p>Getter for the field editor.</p>
	 *
	 * @return the editor
	 */
	public AbstractItemEditor<T> getEditor() {
		return this.editor;
	}

	/**
	 * Gets the resulting item.
	 *
	 * @return the resulting item
	 */
	public T getResultingItem() {
		return this.resultingItem;
	}

	/**
	 * Inits the.
	 */
	private void init() {
		this.captionLabel.setPreferredSize(new Dimension(Short.MAX_VALUE, 32));
		this.captionLabel.setAlignmentX(0.0f);
		this.captionLabel.setBorder(new EmptyBorder(8, 0, 8, 0));
		this.okButton.setAction(new OkAction<T>(this));
		this.okButton.setText("OK");
		this.cancelButton.setAction(new CancelAction<T>(this));
		this.cancelButton.setText("Cancel");

		final Box rootBox = new Box(BoxLayout.Y_AXIS);
		rootBox.setBorder(new EmptyBorder(4, 4, 4, 4));
		rootBox.add(this.captionLabel);
		rootBox.add(this.getEditor());
		rootBox.add(this.createButtonPanel());

		final Container contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(rootBox);
	}

	/**
	 * Sets the caption.
	 *
	 * @param caption the new caption
	 */
	public void setCaption(String caption) {
		this.captionLabel.setText(caption);
	}

	/**
	 * Sets the item.
	 *
	 * @param item the new item
	 */
	public void setItem(T item) {
		this.getEditor().setItem(item);
	}

	/**
	 * <p>Setter for the field resultingItem.</p>
	 *
	 * @param resultingItem the resultingItem to set
	 */
	public void setResultingItem(T resultingItem) {
		this.resultingItem = resultingItem;
	}
}
