package org.loboevolution.menu.tools.pref.search;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * The Class OkAction.
 *
 * @param <T> a T object.
 * @author utente
 * @version $Id: $Id
 */
public class OkAction<T> extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private final transient ItemEditor<T> item;

	/**
	 * <p>Constructor for OkAction.</p>
	 *
	 * @param item a {@link org.loboevolution.menu.tools.pref.search.ItemEditor} object.
	 */
	public OkAction(ItemEditor<T> item) {
		this.item = item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
	 * ActionEvent)
	 */
	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.item.getEditor().validateItem();
		this.item.setResultingItem(this.item.getEditor().getItem());
		this.item.dispose();
	}
}
