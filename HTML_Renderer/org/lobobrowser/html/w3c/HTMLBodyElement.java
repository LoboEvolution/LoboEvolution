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

import org.mozilla.javascript.Function;

/**
 * The Interface HTMLBodyElement.
 */
public interface HTMLBodyElement extends HTMLElement {

    /**
     * Gets the a link.
     *
     * @return the a link
     */
    String getALink();

    /**
     * Sets the a link.
     *
     * @param aLink the new a link
     */
    void setALink(String aLink);

    /**
     * Gets the background.
     *
     * @return the background
     */
    String getBackground();

    /**
     * Sets the background.
     *
     * @param background the new background
     */
    void setBackground(String background);

    /**
     * Gets the bg color.
     *
     * @return the bg color
     */
    String getBgColor();

    /**
     * Sets the bg color.
     *
     * @param bgColor the new bg color
     */
    void setBgColor(String bgColor);

    /**
     * Gets the link.
     *
     * @return the link
     */
    String getLink();

    /**
     * Sets the link.
     *
     * @param link the new link
     */
    void setLink(String link);

    /**
     * Gets the text.
     *
     * @return the text
     */
    String getText();

    /**
     * Sets the text.
     *
     * @param text the new text
     */
    void setText(String text);

    /**
     * Gets the v link.
     *
     * @return the v link
     */
    String getVLink();

    /**
     * Sets the v link.
     *
     * @param vLink the new v link
     */
    void setVLink(String vLink);
    
    /**
     * Gets the onafterprint.
     *
     * @return the onafterprint
     */
    Function getOnafterprint();

    /**
     * Sets the onafterprint.
     *
     * @param onafterprint the new onafterprint
     */
    void setOnafterprint(Function onafterprint);

    /**
     * Gets the onbeforeprint.
     *
     * @return the onbeforeprint
     */
    Function getOnbeforeprint();

    /**
     * Sets the onbeforeprint.
     *
     * @param onbeforeprint the new onbeforeprint
     */
    void setOnbeforeprint(Function onbeforeprint);

    /**
     * Gets the onbeforeunload.
     *
     * @return the onbeforeunload
     */
    Function getOnbeforeunload();

    /**
     * Sets the onbeforeunload.
     *
     * @param onbeforeunload the new onbeforeunload
     */
    void setOnbeforeunload(Function onbeforeunload);

    /**
     * Gets the onblur.
     *
     * @return the onblur
     */
    Function getOnblur();

    /**
     * Sets the onblur.
     *
     * @param onblur the new onblur
     */
    void setOnblur(Function onblur);

    /**
     * Gets the onerror.
     *
     * @return the onerror
     */
    public Function getOnerror();

    /**
     * Sets the onerror.
     *
     * @param onerror the new onerror
     */
    void setOnerror(Function onerror);

    /**
     * Gets the onfocus.
     *
     * @return the onfocus
     */
    public Function getOnfocus();

    /**
     * Sets the onfocus.
     *
     * @param onfocus the new onfocus
     */
    void setOnfocus(Function onfocus);

    /**
     * Gets the onhashchange.
     *
     * @return the onhashchange
     */
    public Function getOnhashchange();

    /**
     * Sets the onhashchange.
     *
     * @param onhashchange the new onhashchange
     */
    void setOnhashchange(Function onhashchange);

    /**
     * Gets the onload.
     *
     * @return the onload
     */
    Function getOnload();

    /**
     * Sets the onload.
     *
     * @param onload the new onload
     */
    void setOnload(Function onload);

    /**
     * Gets the onmessage.
     *
     * @return the onmessage
     */
    Function getOnmessage();

    /**
     * Sets the onmessage.
     *
     * @param onmessage the new onmessage
     */
    void setOnmessage(Function onmessage);

    /**
     * Gets the onoffline.
     *
     * @return the onoffline
     */
    Function getOnoffline();

    /**
     * Sets the onoffline.
     *
     * @param onoffline the new onoffline
     */
    void setOnoffline(Function onoffline);

    /**
     * Gets the ononline.
     *
     * @return the ononline
     */
    Function getOnonline();

    /**
     * Sets the ononline.
     *
     * @param ononline the new ononline
     */
    void setOnonline(Function ononline);

    /**
     * Gets the onpopstate.
     *
     * @return the onpopstate
     */
    Function getOnpopstate();

    /**
     * Sets the onpopstate.
     *
     * @param onpopstate the new onpopstate
     */
    void setOnpopstate(Function onpopstate);

    /**
     * Gets the onpagehide.
     *
     * @return the onpagehide
     */
    Function getOnpagehide();

    /**
     * Sets the onpagehide.
     *
     * @param onpagehide the new onpagehide
     */
    public void setOnpagehide(Function onpagehide);

    /**
     * Gets the onpageshow.
     *
     * @return the onpageshow
     */
    Function getOnpageshow();

    /**
     * Sets the onpageshow.
     *
     * @param onpageshow the new onpageshow
     */
    void setOnpageshow(Function onpageshow);

    /**
     * Gets the onredo.
     *
     * @return the onredo
     */
    Function getOnredo();

    /**
     * Sets the onredo.
     *
     * @param onredo the new onredo
     */
    void setOnredo(Function onredo);

    /**
     * Gets the onresize.
     *
     * @return the onresize
     */
    Function getOnresize();

    /**
     * Sets the onresize.
     *
     * @param onresize the new onresize
     */
    public void setOnresize(Function onresize);

    /**
     * Gets the onstorage.
     *
     * @return the onstorage
     */
    Function getOnstorage();

    /**
     * Sets the onstorage.
     *
     * @param onstorage the new onstorage
     */
    public void setOnstorage(Function onstorage);

    /**
     * Gets the onundo.
     *
     * @return the onundo
     */
    Function getOnundo();

    /**
     * Sets the onundo.
     *
     * @param onundo the new onundo
     */
    public void setOnundo(Function onundo);

    /**
     * Gets the onunload.
     *
     * @return the onunload
     */
    Function getOnunload();

    /**
     * Sets the onunload.
     *
     * @param onunload the new onunload
     */
    void setOnunload(Function onunload);
     
}
