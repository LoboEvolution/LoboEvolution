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

package org.loboevolution.w3c.html;

/**
 * The Interface HTMLAreaElement.
 */
public interface HTMLAreaElement extends HTMLElement {

	/**
	 * Gets the alt.
	 *
	 * @return the alt
	 */
	// HTMLAreaElement
	public String getAlt();

	/**
	 * Sets the alt.
	 *
	 * @param alt
	 *            the new alt
	 */
	public void setAlt(String alt);

	/**
	 * Gets the coords.
	 *
	 * @return the coords
	 */
	public String getCoords();

	/**
	 * Sets the coords.
	 *
	 * @param coords
	 *            the new coords
	 */
	public void setCoords(String coords);

	/**
	 * Gets the shape.
	 *
	 * @return the shape
	 */
	public String getShape();

	/**
	 * Sets the shape.
	 *
	 * @param shape
	 *            the new shape
	 */
	public void setShape(String shape);

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

	/**
	 * Gets the ping.
	 *
	 * @return the ping
	 */
	public String getPing();

	/**
	 * Sets the ping.
	 *
	 * @param ping
	 *            the new ping
	 */
	public void setPing(String ping);

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
	 * Gets the protocol.
	 *
	 * @return the protocol
	 */
	public String getProtocol();

	/**
	 * Sets the protocol.
	 *
	 * @param protocol
	 *            the new protocol
	 */
	public void setProtocol(String protocol);

	/**
	 * Gets the host.
	 *
	 * @return the host
	 */
	public String getHost();

	/**
	 * Sets the host.
	 *
	 * @param host
	 *            the new host
	 */
	public void setHost(String host);

	/**
	 * Gets the hostname.
	 *
	 * @return the hostname
	 */
	public String getHostname();

	/**
	 * Sets the hostname.
	 *
	 * @param hostname
	 *            the new hostname
	 */
	public void setHostname(String hostname);

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public String getPort();

	/**
	 * Sets the port.
	 *
	 * @param port
	 *            the new port
	 */
	public void setPort(String port);

	/**
	 * Gets the pathname.
	 *
	 * @return the pathname
	 */
	public String getPathname();

	/**
	 * Sets the pathname.
	 *
	 * @param pathname
	 *            the new pathname
	 */
	public void setPathname(String pathname);

	/**
	 * Gets the search.
	 *
	 * @return the search
	 */
	public String getSearch();

	/**
	 * Sets the search.
	 *
	 * @param search
	 *            the new search
	 */
	public void setSearch(String search);

	/**
	 * Gets the hash.
	 *
	 * @return the hash
	 */
	public String getHash();

	/**
	 * Sets the hash.
	 *
	 * @param hash
	 *            the new hash
	 */
	public void setHash(String hash);

	/**
	 * Gets the no href.
	 *
	 * @return the no href
	 */
	// HTMLAreaElement-4
	public boolean getNoHref();

	/**
	 * Sets the no href.
	 *
	 * @param noHref
	 *            the new no href
	 */
	public void setNoHref(boolean noHref);
}
