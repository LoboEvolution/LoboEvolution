/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.gui.item;

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

import org.loboevolution.primary.gui.AbstractItemEditor;

/**
 * The Class ItemEditorDialog.
 *
 * @param <T>
 *            the generic type
 */
public class ItemEditorDialog<T> extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The caption label. */
	private final JLabel captionLabel = new JLabel();

	/** The ok button. */
	private final JButton okButton = new JButton();

	/** The cancel button. */
	private final JButton cancelButton = new JButton();

	/** The editor. */
	private final AbstractItemEditor<T> editor;
	
	/** The resulting item. */
	private transient T resultingItem;

	/**
	 * Instantiates a new item editor dialog.
	 *
	 * @param owner
	 *            the owner
	 * @param factory
	 *            the factory
	 * @throws HeadlessException
	 *             the headless exception
	 */
	public ItemEditorDialog(Dialog owner, ItemEditorFactory<T> factory) throws HeadlessException {
		super(owner);
		this.editor = factory.createItemEditor();
		this.init();
	}

	/**
	 * Instantiates a new item editor dialog.
	 *
	 * @param owner
	 *            the owner
	 * @param factory
	 *            the factory
	 * @throws HeadlessException
	 *             the headless exception
	 */
	public ItemEditorDialog(Frame owner, ItemEditorFactory<T> factory) throws HeadlessException {
		super(owner);
		this.editor = factory.createItemEditor();
		this.init();
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

		Box rootBox = new Box(BoxLayout.Y_AXIS);
		rootBox.setBorder(new EmptyBorder(4, 4, 4, 4));
		rootBox.add(this.captionLabel);
		rootBox.add(this.getEditor());
		rootBox.add(this.createButtonPanel());

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(rootBox);
	}

	/**
	 * Sets the caption.
	 *
	 * @param caption
	 *            the new caption
	 */
	public void setCaption(String caption) {
		this.captionLabel.setText(caption);
	}

	/**
	 * Sets the item.
	 *
	 * @param item
	 *            the new item
	 */
	public void setItem(T item) {
		this.getEditor().setItem(item);
	}

	/**
	 * Creates the button panel.
	 *
	 * @return the component
	 */
	private Component createButtonPanel() {
		Box panel = new Box(BoxLayout.X_AXIS);
		// panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		panel.setBorder(new EmptyBorder(4, 4, 4, 4));
		panel.add(Box.createGlue());
		panel.add(this.okButton);
		panel.add(Box.createRigidArea(new Dimension(4, 1)));
		panel.add(this.cancelButton);
		panel.add(Box.createGlue());
		return panel;
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
	 * @return the editor
	 */
	public AbstractItemEditor<T> getEditor() {
		return editor;
	}



	/**
	 * @param resultingItem the resultingItem to set
	 */
	public void setResultingItem(T resultingItem) {
		this.resultingItem = resultingItem;
	}
}
