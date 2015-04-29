/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The XAMJ Project This
 * library is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with this library; if not, write
 * to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston,
 * MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.w3c.DOMTokenList;

/**
 * The Class DOMTokenListImpl.
 */
public class DOMTokenListImpl implements DOMTokenList {

    /** The item value. */
    private String itemValue;

    /** The element. */
    private HTMLElementImpl element;

    /**
     * Instantiates a new DOM token list impl.
     *
     * @param element
     *            the element
     * @param item
     *            the item
     */
    public DOMTokenListImpl(HTMLElementImpl element, String item) {
        this.element = element;
        this.itemValue = item;

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.DOMTokenList#getLength()
     */
    @Override
    public int getLength() {

        if (itemValue != null) {
            return itemValue.split(" ").length;
        }

        return 0;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.DOMTokenList#item(int)
     */
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

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.DOMTokenList#contains(java.lang.String)
     */
    @Override
    public boolean contains(String token) {
        return itemValue.contains(token);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.DOMTokenList#add(java.lang.String)
     */
    @Override
    public void add(String token) {

        if (element.getClassName() != null) {
            element.setClassName(element.getClassName() + " " + token);
        } else {
            element.setClassName(token);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.DOMTokenList#remove(java.lang.String)
     */
    @Override
    public void remove(String token) {

        String[] listString = itemValue.split(" ");
        String result = "";
        for (int i = 0; i < listString.length; i++) {
            String test = listString[i];
            if (!test.equals(token)) {
                result += test;
            }
        }

        element.setClassName(result);

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.DOMTokenList#toggle(java.lang.String)
     */
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

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.DOMTokenList#toggle(java.lang.String, boolean)
     */
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
