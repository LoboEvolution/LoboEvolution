package org.lobo.menu.tools.pref.search;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * The Class OkAction.
 * 
 * @param <T>
 */
public class OkAction<T> extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private transient ItemEditorDialog<T> item;

	public OkAction(ItemEditorDialog<T> item) {
		this.item = item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
	 * ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.item.getEditor().validateItem();
		this.item.setResultingItem(this.item.getEditor().getItem());
		this.item.dispose();
	}
}