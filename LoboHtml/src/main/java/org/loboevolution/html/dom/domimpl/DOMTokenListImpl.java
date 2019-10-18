package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.DOMTokenList;

public class DOMTokenListImpl implements DOMTokenList {

	private String itemValue;

	private HTMLElementImpl element;

	public DOMTokenListImpl(HTMLElementImpl element, String item) {
		this.element = element;
		this.itemValue = item;

	}

	@Override
	public int getLength() {
		if (itemValue != null) {
			return itemValue.split(" ").length;
		}

		return 0;
	}

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

	@Override
	public boolean contains(String token) {
		return itemValue.contains(token);
	}

	@Override
	public void add(String token) {
		if (element.getClassName() != null) {
			element.setClassName(element.getClassName() + " " + token);
		} else {
			element.setClassName(token);
		}
	}

	@Override
	public void remove(String token) {

		String[] listString = itemValue.split(" ");
		String result = "";
		for (String element2 : listString) {
			String test = element2;
			if (!test.equals(token)) {
				result += test;
			}
		}

		element.setClassName(result);

	}

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