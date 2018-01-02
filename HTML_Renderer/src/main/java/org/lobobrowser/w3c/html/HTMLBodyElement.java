/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.html;

import org.mozilla.javascript.Function;

/**
 * The Interface HTMLBodyElement.
 */
public interface HTMLBodyElement extends HTMLElement {

	/**
	 * Gets the onafterprint.
	 *
	 * @return the onafterprint
	 */
	// HTMLBodyElement
	public Function getOnafterprint();

	/**
	 * Sets the onafterprint.
	 *
	 * @param onafterprint
	 *            the new onafterprint
	 */
	public void setOnafterprint(Function onafterprint);

	/**
	 * Gets the onbeforeprint.
	 *
	 * @return the onbeforeprint
	 */
	public Function getOnbeforeprint();

	/**
	 * Sets the onbeforeprint.
	 *
	 * @param onbeforeprint
	 *            the new onbeforeprint
	 */
	public void setOnbeforeprint(Function onbeforeprint);

	/**
	 * Gets the onbeforeunload.
	 *
	 * @return the onbeforeunload
	 */
	public Function getOnbeforeunload();

	/**
	 * Sets the onbeforeunload.
	 *
	 * @param onbeforeunload
	 *            the new onbeforeunload
	 */
	public void setOnbeforeunload(Function onbeforeunload);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLElement#getOnblur()
	 */
	@Override
	public Function getOnblur();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLElement#setOnblur(org.mozilla.javascript.
	 * Function)
	 */
	@Override
	public void setOnblur(Function onblur);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLElement#getOnerror()
	 */
	@Override
	public Function getOnerror();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLElement#setOnerror(org.mozilla.javascript.
	 * Function)
	 */
	@Override
	public void setOnerror(Function onerror);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLElement#getOnfocus()
	 */
	@Override
	public Function getOnfocus();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLElement#setOnfocus(org.mozilla.javascript.
	 * Function)
	 */
	@Override
	public void setOnfocus(Function onfocus);

	/**
	 * Gets the onhashchange.
	 *
	 * @return the onhashchange
	 */
	public Function getOnhashchange();

	/**
	 * Sets the onhashchange.
	 *
	 * @param onhashchange
	 *            the new onhashchange
	 */
	public void setOnhashchange(Function onhashchange);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLElement#getOnload()
	 */
	@Override
	public Function getOnload();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLElement#setOnload(org.mozilla.javascript.
	 * Function)
	 */
	@Override
	public void setOnload(Function onload);

	/**
	 * Gets the onmessage.
	 *
	 * @return the onmessage
	 */
	public Function getOnmessage();

	/**
	 * Sets the onmessage.
	 *
	 * @param onmessage
	 *            the new onmessage
	 */
	public void setOnmessage(Function onmessage);

	/**
	 * Gets the onoffline.
	 *
	 * @return the onoffline
	 */
	public Function getOnoffline();

	/**
	 * Sets the onoffline.
	 *
	 * @param onoffline
	 *            the new onoffline
	 */
	public void setOnoffline(Function onoffline);

	/**
	 * Gets the ononline.
	 *
	 * @return the ononline
	 */
	public Function getOnonline();

	/**
	 * Sets the ononline.
	 *
	 * @param ononline
	 *            the new ononline
	 */
	public void setOnonline(Function ononline);

	/**
	 * Gets the onpopstate.
	 *
	 * @return the onpopstate
	 */
	public Function getOnpopstate();

	/**
	 * Sets the onpopstate.
	 *
	 * @param onpopstate
	 *            the new onpopstate
	 */
	public void setOnpopstate(Function onpopstate);

	/**
	 * Gets the onpagehide.
	 *
	 * @return the onpagehide
	 */
	public Function getOnpagehide();

	/**
	 * Sets the onpagehide.
	 *
	 * @param onpagehide
	 *            the new onpagehide
	 */
	public void setOnpagehide(Function onpagehide);

	/**
	 * Gets the onpageshow.
	 *
	 * @return the onpageshow
	 */
	public Function getOnpageshow();

	/**
	 * Sets the onpageshow.
	 *
	 * @param onpageshow
	 *            the new onpageshow
	 */
	public void setOnpageshow(Function onpageshow);

	/**
	 * Gets the onredo.
	 *
	 * @return the onredo
	 */
	public Function getOnredo();

	/**
	 * Sets the onredo.
	 *
	 * @param onredo
	 *            the new onredo
	 */
	public void setOnredo(Function onredo);

	/**
	 * Gets the onresize.
	 *
	 * @return the onresize
	 */
	public Function getOnresize();

	/**
	 * Sets the onresize.
	 *
	 * @param onresize
	 *            the new onresize
	 */
	public void setOnresize(Function onresize);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLElement#getOnscroll()
	 */
	@Override
	public Function getOnscroll();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLElement#setOnscroll(org.mozilla.javascript.
	 * Function)
	 */
	@Override
	public void setOnscroll(Function onscroll);

	/**
	 * Gets the onstorage.
	 *
	 * @return the onstorage
	 */
	public Function getOnstorage();

	/**
	 * Sets the onstorage.
	 *
	 * @param onstorage
	 *            the new onstorage
	 */
	public void setOnstorage(Function onstorage);

	/**
	 * Gets the onundo.
	 *
	 * @return the onundo
	 */
	public Function getOnundo();

	/**
	 * Sets the onundo.
	 *
	 * @param onundo
	 *            the new onundo
	 */
	public void setOnundo(Function onundo);

	/**
	 * Gets the onunload.
	 *
	 * @return the onunload
	 */
	public Function getOnunload();

	/**
	 * Sets the onunload.
	 *
	 * @param onunload
	 *            the new onunload
	 */
	public void setOnunload(Function onunload);

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	// HTMLBodyElement-5
	public String getText();

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	public void setText(String text);

	/**
	 * Gets the bg color.
	 *
	 * @return the bg color
	 */
	public String getBgColor();

	/**
	 * Sets the bg color.
	 *
	 * @param bgColor
	 *            the new bg color
	 */
	public void setBgColor(String bgColor);

	/**
	 * Gets the background.
	 *
	 * @return the background
	 */
	public String getBackground();

	/**
	 * Sets the background.
	 *
	 * @param background
	 *            the new background
	 */
	public void setBackground(String background);

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public String getLink();

	/**
	 * Sets the link.
	 *
	 * @param link
	 *            the new link
	 */
	public void setLink(String link);

	/**
	 * Gets the v link.
	 *
	 * @return the v link
	 */
	public String getVLink();

	/**
	 * Sets the v link.
	 *
	 * @param vLink
	 *            the new v link
	 */
	public void setVLink(String vLink);

	/**
	 * Gets the a link.
	 *
	 * @return the a link
	 */
	public String getALink();

	/**
	 * Sets the a link.
	 *
	 * @param aLink
	 *            the new a link
	 */
	public void setALink(String aLink);
}
