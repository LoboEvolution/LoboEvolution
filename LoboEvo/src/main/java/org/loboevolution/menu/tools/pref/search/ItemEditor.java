/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.menu.tools.pref.search;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import org.loboevolution.gui.AbstractItemEditor;
import org.loboevolution.gui.ItemEditorFactory;

import com.jtattoo.plaf.lobo.LoboButton;
import com.jtattoo.plaf.lobo.LoboLabel;
import com.jtattoo.plaf.lobo.LoboLookAndFeel;

/**
 * The Class ItemEditor.
 *
 * @param <T> the generic type
 *
 *
 */
public class ItemEditor<T> extends JFrame implements LoboLookAndFeel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cancel button. */
	private final LoboButton cancelButton = new LoboButton();

	/** The caption label. */
	private final LoboLabel captionLabel = new LoboLabel("");

	/** The editor. */
	private final transient AbstractItemEditor<T> editor;

	/** The ok button. */
	private final LoboButton okButton = new LoboButton();

	/** The resulting item. */
	private transient T resultingItem;

	/**
	 * Instantiates a new item editor dialog.
	 *
	 * @param factory the factory
	 * @throws java.awt.HeadlessException the headless exception
	 * @throws java.awt.HeadlessException if any.
	 */
	public ItemEditor(final ItemEditorFactory<T> factory) throws HeadlessException {
		this.editor = factory.createItemEditor();
		this.createAndShowGUI();
	}

	/**
	 * Inits the.
	 */
	private void createAndShowGUI() {
		setResizable(false);
		this.captionLabel.setPreferredSize(new Dimension(Short.MAX_VALUE, 32));
		this.captionLabel.setAlignmentX(0.0f);
		this.captionLabel.setBorder(new EmptyBorder(8, 0, 8, 0));
		this.okButton.setAction(new OkAction<>(this));
		this.okButton.setText("OK");
		this.cancelButton.setAction(new CancelAction<>(this));
		this.cancelButton.setText("Cancel");

		final Box rootBox = new Box(BoxLayout.Y_AXIS);
		rootBox.setBorder(new EmptyBorder(4, 4, 4, 4));
		rootBox.add(this.captionLabel);
		rootBox.add(this.getEditor());
		rootBox.add(this.createButtonPanel());

		final Container contentPane = getContentPane();
		contentPane.setBackground(background());
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(rootBox);
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
	 * Sets the caption.
	 *
	 * @param caption the new caption
	 */
	public void setCaption(final String caption) {
		this.captionLabel.setText(caption);
	}

	/**
	 * Sets the item.
	 *
	 * @param item the new item
	 */
	public void setItem(final T item) {
		this.getEditor().setItem(item);
	}

	/**
	 * <p>Setter for the field resultingItem.</p>
	 *
	 * @param resultingItem the resultingItem to set
	 */
	public void setResultingItem(final T resultingItem) {
		this.resultingItem = resultingItem;
	}
}
