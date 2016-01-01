/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.html;


/**
 * The Interface HTMLLinkElement.
 */
public interface HTMLLinkElement extends HTMLElement {
	
	/* (non-Javadoc)
	 * @see org.lobobrowser.w3c.html.HTMLElement#getDisabled()
	 */
	// HTMLLinkElement
	@Override
	public boolean getDisabled();

	/**
	 * Sets the disabled.
	 *
	 * @param disabled
	 *            the new disabled
	 */
	public void setDisabled(boolean disabled);

	/**
	 * Gets the href.
	 *
	 * @return the href
	 */
	public String getHref();

	/**
	 * Sets the href.
	 *
	 * @param href
	 *            the new href
	 */
	public void setHref(String href);

	/**
	 * Gets the rel.
	 *
	 * @return the rel
	 */
	public String getRel();

	/**
	 * Sets the rel.
	 *
	 * @param rel
	 *            the new rel
	 */
	public void setRel(String rel);

	/**
	 * Gets the rel list.
	 *
	 * @return the rel list
	 */
	public DOMTokenList getRelList();

	/**
	 * Gets the media.
	 *
	 * @return the media
	 */
	public String getMedia();

	/**
	 * Sets the media.
	 *
	 * @param media
	 *            the new media
	 */
	public void setMedia(String media);

	/**
	 * Gets the hreflang.
	 *
	 * @return the hreflang
	 */
	public String getHreflang();

	/**
	 * Sets the hreflang.
	 *
	 * @param hreflang
	 *            the new hreflang
	 */
	public void setHreflang(String hreflang);

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType();

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type);

	/**
	 * Gets the sizes.
	 *
	 * @return the sizes
	 */
	public DOMSettableTokenList getSizes();

	/**
	 * Sets the sizes.
	 *
	 * @param sizes
	 *            the new sizes
	 */
	public void setSizes(String sizes);

	/**
	 * Gets the charset.
	 *
	 * @return the charset
	 */
	// HTMLLinkElement-20
	public String getCharset();

	/**
	 * Sets the charset.
	 *
	 * @param charset
	 *            the new charset
	 */
	public void setCharset(String charset);

	/**
	 * Gets the rev.
	 *
	 * @return the rev
	 */
	public String getRev();

	/**
	 * Sets the rev.
	 *
	 * @param rev
	 *            the new rev
	 */
	public void setRev(String rev);

	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	public String getTarget();

	/**
	 * Sets the target.
	 *
	 * @param target
	 *            the new target
	 */
	public void setTarget(String target);
}
