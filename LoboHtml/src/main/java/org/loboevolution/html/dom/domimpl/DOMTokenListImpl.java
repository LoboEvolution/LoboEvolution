package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.DOMTokenList;

/**
 * <p>DOMTokenListImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class DOMTokenListImpl implements DOMTokenList {

	private final String itemValue;

	private final HTMLElementImpl element;

	/**
	 * <p>Constructor for DOMTokenListImpl.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param item a {@link java.lang.String} object.
	 */
	public DOMTokenListImpl(HTMLElementImpl element, String item) {
		this.element = element;
		this.itemValue = item;

	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		if (itemValue != null) {
			return itemValue.split(" ").length;
		}

		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public String item(int index) {
		if (itemValue != null) {
			String[] listString = itemValue.split(" ");
			for (int i = 0; i < listString.length; i++) {
				if (index == i) {
					return listString[i];

				}
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(String token) {
		return itemValue.contains(token);
	}

	/** {@inheritDoc} */
	@Override
	public void add(String token) {
		if (element.getClassName() != null) {
			element.setClassName(element.getClassName() + " " + token);
		} else {
			element.setClassName(token);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void remove(String token) {

		String[] listString = itemValue.split(" ");
		StringBuilder result = new StringBuilder();
		for (String element2 : listString) {
			String test = element2;
			if (!test.equals(token)) {
				result.append(test);
			}
		}

		element.setClassName(result.toString());

	}

	/** {@inheritDoc} */
	@Override
	public boolean toggle(String token) {

		if (contains(token)) {
			remove(token);
			return false;
		} else {
			add(token);
			return true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean toggle(String token, boolean force) {

		if (force) {
			add(token);
		} else {
			remove(token);
		}

		return force;
	}

}
