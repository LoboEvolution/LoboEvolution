/*
 * GNU LESSER GENERAL LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Copyright (c) 2003 World Wide Web Consortium, (Massachusetts Institute of
 * Technology, Institut National de Recherche en Informatique et en Automatique,
 * Keio University). All Rights Reserved. This program is distributed under the
 * W3C's Software Intellectual Property License. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * W3C License http://www.w3.org/Consortium/Legal/ for more details.
 */

package org.lobobrowser.html.w3c;

/**
 * Client-side image map area definition. See the AREA element definition in
 * HTML 4.01.
 * <p>
 * See also the Object Model (DOM) Level 2 HTML Specification
 * </p>
 * .
 */
public interface HTMLAreaElement extends HTMLElement {

    /**
     * A single character access key to give access to the form control. See the
     * accesskey attribute definition in HTML 4.01.
     *
     * @return the access key
     */
    @Override
    String getAccessKey();

    /**
     * A single character access key to give access to the form control. See the
     * accesskey attribute definition in HTML 4.01.
     *
     * @param accessKey
     *            the new access key
     */
    @Override
    void setAccessKey(String accessKey);

    /**
     * Alternate text for user agents not rendering the normal content of this
     * element. See the alt attribute definition in HTML 4.01.
     *
     * @return the alt
     */
    String getAlt();

    /**
     * Alternate text for user agents not rendering the normal content of this
     * element. See the alt attribute definition in HTML 4.01.
     *
     * @param alt
     *            the new alt
     */
    void setAlt(String alt);

    /**
     * Comma-separated list of lengths, defining an active region geometry. See
     * also <code>shape</code> for the shape of the region. See the coords
     * attribute definition in HTML 4.01.
     *
     * @return the coords
     */
    String getCoords();

    /**
     * Comma-separated list of lengths, defining an active region geometry. See
     * also <code>shape</code> for the shape of the region. See the coords
     * attribute definition in HTML 4.01.
     *
     * @param coords
     *            the new coords
     */
    void setCoords(String coords);

    /**
     * The URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
     * of the linked resource. See the href attribute definition in HTML 4.01.
     *
     * @return the href
     */
    String getHref();

    /**
     * The URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
     * of the linked resource. See the href attribute definition in HTML 4.01.
     *
     * @param href
     *            the new href
     */
    void setHref(String href);

    /**
     * Specifies that this area is inactive, i.e., has no associated action. See
     * the nohref attribute definition in HTML 4.01.
     *
     * @return the no href
     */
    boolean getNoHref();

    /**
     * Specifies that this area is inactive, i.e., has no associated action. See
     * the nohref attribute definition in HTML 4.01.
     *
     * @param noHref
     *            the new no href
     */
    void setNoHref(boolean noHref);

    /**
     * The shape of the active area. The coordinates are given by
     * <code>coords</code>. See the shape attribute definition in HTML 4.01.
     *
     * @return the shape
     */
    String getShape();

    /**
     * The shape of the active area. The coordinates are given by
     * <code>coords</code>. See the shape attribute definition in HTML 4.01.
     *
     * @param shape
     *            the new shape
     */
    void setShape(String shape);

    /**
     * Index that represents the element's position in the tabbing order. See
     * the tabindex attribute definition in HTML 4.01.
     *
     * @return the tab index
     */
    @Override
    int getTabIndex();

    /**
     * Index that represents the element's position in the tabbing order. See
     * the tabindex attribute definition in HTML 4.01.
     *
     * @param tabIndex
     *            the new tab index
     */
    @Override
    void setTabIndex(int tabIndex);

    /**
     * Frame to render the resource in. See the target attribute definition in
     * HTML 4.01.
     *
     * @return the target
     */
    String getTarget();

    /**
     * Frame to render the resource in. See the target attribute definition in
     * HTML 4.01.
     *
     * @param target
     *            the new target
     */
    void setTarget(String target);

    /**
     * Gets the ping.
     *
     * @return the ping
     */
    String getPing();

    /**
     * Sets the ping.
     *
     * @param ping
     *            the new ping
     */
    void setPing(String ping);

    /**
     * Gets the rel.
     *
     * @return the rel
     */
    String getRel();

    /**
     * Sets the rel.
     *
     * @param rel
     *            the new rel
     */
    void setRel(String rel);

    /**
     * Gets the rel list.
     *
     * @return the rel list
     */
    DOMTokenList getRelList();

    /**
     * Gets the media.
     *
     * @return the media
     */
    String getMedia();

    /**
     * Sets the media.
     *
     * @param media
     *            the new media
     */
    void setMedia(String media);

    /**
     * Gets the hreflang.
     *
     * @return the hreflang
     */
    String getHreflang();

    /**
     * Sets the hreflang.
     *
     * @param hreflang
     *            the new hreflang
     */
    void setHreflang(String hreflang);

    /**
     * Gets the type.
     *
     * @return the type
     */
    String getType();

    /**
     * Sets the type.
     *
     * @param type
     *            the new type
     */
    void setType(String type);

    /**
     * Gets the protocol.
     *
     * @return the protocol
     */
    String getProtocol();

    /**
     * Sets the protocol.
     *
     * @param protocol
     *            the new protocol
     */
    void setProtocol(String protocol);

    /**
     * Gets the host.
     *
     * @return the host
     */
    String getHost();

    /**
     * Sets the host.
     *
     * @param host
     *            the new host
     */
    void setHost(String host);

    /**
     * Gets the hostname.
     *
     * @return the hostname
     */
    String getHostname();

    /**
     * Sets the hostname.
     *
     * @param hostname
     *            the new hostname
     */
    void setHostname(String hostname);

    /**
     * Gets the port.
     *
     * @return the port
     */
    String getPort();

    /**
     * Sets the port.
     *
     * @param port
     *            the new port
     */
    void setPort(String port);

    /**
     * Gets the pathname.
     *
     * @return the pathname
     */
    String getPathname();

    /**
     * Sets the pathname.
     *
     * @param pathname
     *            the new pathname
     */
    void setPathname(String pathname);

    /**
     * Gets the search.
     *
     * @return the search
     */
    String getSearch();

    /**
     * Sets the search.
     *
     * @param search
     *            the new search
     */
    void setSearch(String search);

    /**
     * Gets the hash.
     *
     * @return the hash
     */
    String getHash();

    /**
     * Sets the hash.
     *
     * @param hash
     *            the new hash
     */
    void setHash(String hash);

}