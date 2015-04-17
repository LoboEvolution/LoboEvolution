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
 * Create a grid of frames. See the FRAMESET element definition in HTML 4.01.
 * <p>
 * See also the Object Model (DOM) Level 2 HTML Specification
 * </p>
 * .
 */
public interface HTMLFrameSetElement extends HTMLElement {

    /**
     * The number of columns of frames in the frameset. See the cols attribute
     * definition in HTML 4.01.
     *
     * @return the cols
     */
    String getCols();

    /**
     * The number of columns of frames in the frameset. See the cols attribute
     * definition in HTML 4.01.
     *
     * @param cols
     *            the new cols
     */
    void setCols(String cols);

    /**
     * The number of rows of frames in the frameset. See the rows attribute
     * definition in HTML 4.01.
     *
     * @return the rows
     */
    String getRows();

    /**
     * The number of rows of frames in the frameset. See the rows attribute
     * definition in HTML 4.01.
     *
     * @param rows
     *            the new rows
     */
    void setRows(String rows);

    /*
     * HTMLFrameSetElement String getCols(); void setCols(String
     * cols); String getRows(); void setRows(String rows); public
     * Function getOnafterprint(); void setOnafterprint(Function
     * onafterprint); Function getOnbeforeprint(); void
     * setOnbeforeprint(Function onbeforeprint); Function
     * getOnbeforeunload(); void setOnbeforeunload(Function onbeforeunload);
     * Function getOnblur(); void setOnblur(Function onblur); public
     * Function getOnerror(); void setOnerror(Function onerror); public
     * Function getOnfocus(); void setOnfocus(Function onfocus); public
     * Function getOnhashchange(); void setOnhashchange(Function
     * onhashchange); Function getOnload(); void setOnload(Function
     * onload); Function getOnmessage(); void setOnmessage(Function
     * onmessage); Function getOnoffline(); void setOnoffline(Function
     * onoffline); Function getOnonline(); void setOnonline(Function
     * ononline); Function getOnpagehide(); void
     * setOnpagehide(Function onpagehide); Function getOnpageshow(); public
     * void setOnpageshow(Function onpageshow); Function getOnpopstate();
     * void setOnpopstate(Function onpopstate); Function getOnredo();
     * void setOnredo(Function onredo); Function getOnresize(); public
     * void setOnresize(Function onresize); Function getOnstorage(); public
     * void setOnstorage(Function onstorage); Function getOnundo(); public
     * void setOnundo(Function onundo); Function getOnunload(); void
     * setOnunload(Function onunload);
     */
}
