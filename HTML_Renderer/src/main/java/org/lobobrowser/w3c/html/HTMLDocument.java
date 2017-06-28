/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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

import org.lobobrowser.html.js.Location;
import org.lobobrowser.html.xpath.XPathNSResolverImpl;
import org.lobobrowser.html.xpath.XPathResultImpl;
import org.lobobrowser.w3c.events.EventTarget;
import org.mozilla.javascript.Function;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.views.AbstractView;

/**
 * The Interface HTMLDocument.
 */
public interface HTMLDocument extends Document, EventTarget {

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	// HTMLDocument
	public Location getLocation();

	/**
	 * Sets the location.
	 *
	 * @param location
	 *            the new location
	 */
	public void setLocation(String location);

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getURL();

	/**
	 * Gets the domain.
	 *
	 * @return the domain
	 */
	public String getDomain();

	/**
	 * Sets the domain.
	 *
	 * @param domain
	 *            the new domain
	 */
	public void setDomain(String domain);

	/**
	 * Gets the referrer.
	 *
	 * @return the referrer
	 */
	public String getReferrer();

	/**
	 * Gets the cookie.
	 *
	 * @return the cookie
	 */
	public String getCookie();

	/**
	 * Sets the cookie.
	 *
	 * @param cookie
	 *            the new cookie
	 */
	public void setCookie(String cookie);

	/**
	 * Gets the last modified.
	 *
	 * @return the last modified
	 */
	public String getLastModified();

	/**
	 * Gets the ready state.
	 *
	 * @return the ready state
	 */
	public String getReadyState();

	/**
	 * Gets the element.
	 *
	 * @param name
	 *            the name
	 * @return the element
	 */
	public Object getElement(String name);

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle();

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title);

	/**
	 * Gets the dir.
	 *
	 * @return the dir
	 */
	public String getDir();

	/**
	 * Sets the dir.
	 *
	 * @param dir
	 *            the new dir
	 */
	public void setDir(String dir);

	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public HTMLElement getBody();

	/**
	 * Sets the body.
	 *
	 * @param body
	 *            the new body
	 */
	public void setBody(HTMLElement body);

	/**
	 * Gets the head.
	 *
	 * @return the head
	 */
	public HTMLHeadElement getHead();

	/**
	 * Gets the images.
	 *
	 * @return the images
	 */
	public HTMLCollection getImages();

	/**
	 * Gets the embeds.
	 *
	 * @return the embeds
	 */
	public HTMLCollection getEmbeds();

	/**
	 * Gets the plugins.
	 *
	 * @return the plugins
	 */
	public HTMLCollection getPlugins();

	/**
	 * Gets the links.
	 *
	 * @return the links
	 */
	public HTMLCollection getLinks();

	/**
	 * Gets the forms.
	 *
	 * @return the forms
	 */
	public HTMLCollection getForms();

	/**
	 * Gets the scripts.
	 *
	 * @return the scripts
	 */
	public HTMLCollection getScripts();

	/**
	 * Gets the elements by name.
	 *
	 * @param elementName
	 *            the element name
	 * @return the elements by name
	 */
	public NodeList getElementsByName(String elementName);

	/**
	 * Gets the css element map.
	 *
	 * @return the css element map
	 */
	public DOMElementMap getCssElementMap();

	/**
	 * Gets the inner html.
	 *
	 * @return the inner html
	 */
	public String getInnerHTML();

	/**
	 * Sets the inner html.
	 *
	 * @param innerHTML
	 *            the new inner html
	 */
	public void setInnerHTML(String innerHTML);

	/**
	 * Open.
	 */
	public void open();

	/**
	 * Open.
	 *
	 * @param type
	 *            the type
	 * @return the HTML document
	 */
	public HTMLDocument open(String type);

	/**
	 * Open.
	 *
	 * @param type
	 *            the type
	 * @param replace
	 *            the replace
	 * @return the HTML document
	 */
	public HTMLDocument open(String type, String replace);

	/**
	 * Close.
	 */
	public void close();

	/**
	 * Gets the default view.
	 *
	 * @return the default view
	 */
	public AbstractView getDefaultView();

	/**
	 * Gets the active element.
	 *
	 * @return the active element
	 */
	public Element getActiveElement();

	/**
	 * Checks for focus.
	 *
	 * @return true, if successful
	 */
	public boolean hasFocus();

	/**
	 * Gets the design mode.
	 *
	 * @return the design mode
	 */
	public String getDesignMode();

	/**
	 * Sets the design mode.
	 *
	 * @param designMode
	 *            the new design mode
	 */
	public void setDesignMode(String designMode);

	/**
	 * Exec command.
	 *
	 * @param commandId
	 *            the command id
	 * @return true, if successful
	 */
	public boolean execCommand(String commandId);

	/**
	 * Exec command.
	 *
	 * @param commandId
	 *            the command id
	 * @param showUI
	 *            the show ui
	 * @return true, if successful
	 */
	public boolean execCommand(String commandId, boolean showUI);

	/**
	 * Exec command.
	 *
	 * @param commandId
	 *            the command id
	 * @param showUI
	 *            the show ui
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	public boolean execCommand(String commandId, boolean showUI, String value);

	/**
	 * Query command enabled.
	 *
	 * @param commandId
	 *            the command id
	 * @return true, if successful
	 */
	public boolean queryCommandEnabled(String commandId);

	/**
	 * Query command indeterm.
	 *
	 * @param commandId
	 *            the command id
	 * @return true, if successful
	 */
	public boolean queryCommandIndeterm(String commandId);

	/**
	 * Query command state.
	 *
	 * @param commandId
	 *            the command id
	 * @return true, if successful
	 */
	public boolean queryCommandState(String commandId);

	/**
	 * Query command supported.
	 *
	 * @param commandId
	 *            the command id
	 * @return true, if successful
	 */
	public boolean queryCommandSupported(String commandId);

	/**
	 * Query command value.
	 *
	 * @param commandId
	 *            the command id
	 * @return the string
	 */
	public String queryCommandValue(String commandId);

	/**
	 * Gets the commands.
	 *
	 * @return the commands
	 */
	public HTMLCollection getCommands();

	/**
	 * Gets the onabort.
	 *
	 * @return the onabort
	 */
	public Function getOnabort();

	/**
	 * Sets the onabort.
	 *
	 * @param onabort
	 *            the new onabort
	 */
	public void setOnabort(Function onabort);

	/**
	 * Gets the onblur.
	 *
	 * @return the onblur
	 */
	public Function getOnblur();

	/**
	 * Sets the onblur.
	 *
	 * @param onblur
	 *            the new onblur
	 */
	public void setOnblur(Function onblur);

	/**
	 * Gets the oncanplay.
	 *
	 * @return the oncanplay
	 */
	public Function getOncanplay();

	/**
	 * Sets the oncanplay.
	 *
	 * @param oncanplay
	 *            the new oncanplay
	 */
	public void setOncanplay(Function oncanplay);

	/**
	 * Gets the oncanplaythrough.
	 *
	 * @return the oncanplaythrough
	 */
	public Function getOncanplaythrough();

	/**
	 * Sets the oncanplaythrough.
	 *
	 * @param oncanplaythrough
	 *            the new oncanplaythrough
	 */
	public void setOncanplaythrough(Function oncanplaythrough);

	/**
	 * Gets the onchange.
	 *
	 * @return the onchange
	 */
	public Function getOnchange();

	/**
	 * Sets the onchange.
	 *
	 * @param onchange
	 *            the new onchange
	 */
	public void setOnchange(Function onchange);

	/**
	 * Gets the onclick.
	 *
	 * @return the onclick
	 */
	public Function getOnclick();

	/**
	 * Sets the onclick.
	 *
	 * @param onclick
	 *            the new onclick
	 */
	public void setOnclick(Function onclick);

	/**
	 * Gets the oncontextmenu.
	 *
	 * @return the oncontextmenu
	 */
	public Function getOncontextmenu();

	/**
	 * Sets the oncontextmenu.
	 *
	 * @param oncontextmenu
	 *            the new oncontextmenu
	 */
	public void setOncontextmenu(Function oncontextmenu);

	/**
	 * Gets the oncuechange.
	 *
	 * @return the oncuechange
	 */
	public Function getOncuechange();

	/**
	 * Sets the oncuechange.
	 *
	 * @param oncuechange
	 *            the new oncuechange
	 */
	public void setOncuechange(Function oncuechange);

	/**
	 * Gets the ondblclick.
	 *
	 * @return the ondblclick
	 */
	public Function getOndblclick();

	/**
	 * Sets the ondblclick.
	 *
	 * @param ondblclick
	 *            the new ondblclick
	 */
	public void setOndblclick(Function ondblclick);

	/**
	 * Gets the ondrag.
	 *
	 * @return the ondrag
	 */
	public Function getOndrag();

	/**
	 * Sets the ondrag.
	 *
	 * @param ondrag
	 *            the new ondrag
	 */
	public void setOndrag(Function ondrag);

	/**
	 * Gets the ondragend.
	 *
	 * @return the ondragend
	 */
	public Function getOndragend();

	/**
	 * Sets the ondragend.
	 *
	 * @param ondragend
	 *            the new ondragend
	 */
	public void setOndragend(Function ondragend);

	/**
	 * Gets the ondragenter.
	 *
	 * @return the ondragenter
	 */
	public Function getOndragenter();

	/**
	 * Sets the ondragenter.
	 *
	 * @param ondragenter
	 *            the new ondragenter
	 */
	public void setOndragenter(Function ondragenter);

	/**
	 * Gets the ondragleave.
	 *
	 * @return the ondragleave
	 */
	public Function getOndragleave();

	/**
	 * Sets the ondragleave.
	 *
	 * @param ondragleave
	 *            the new ondragleave
	 */
	public void setOndragleave(Function ondragleave);

	/**
	 * Gets the ondragover.
	 *
	 * @return the ondragover
	 */
	public Function getOndragover();

	/**
	 * Sets the ondragover.
	 *
	 * @param ondragover
	 *            the new ondragover
	 */
	public void setOndragover(Function ondragover);

	/**
	 * Gets the ondragstart.
	 *
	 * @return the ondragstart
	 */
	public Function getOndragstart();

	/**
	 * Sets the ondragstart.
	 *
	 * @param ondragstart
	 *            the new ondragstart
	 */
	public void setOndragstart(Function ondragstart);

	/**
	 * Gets the ondrop.
	 *
	 * @return the ondrop
	 */
	public Function getOndrop();

	/**
	 * Sets the ondrop.
	 *
	 * @param ondrop
	 *            the new ondrop
	 */
	public void setOndrop(Function ondrop);

	/**
	 * Gets the ondurationchange.
	 *
	 * @return the ondurationchange
	 */
	public Function getOndurationchange();

	/**
	 * Sets the ondurationchange.
	 *
	 * @param ondurationchange
	 *            the new ondurationchange
	 */
	public void setOndurationchange(Function ondurationchange);

	/**
	 * Gets the onemptied.
	 *
	 * @return the onemptied
	 */
	public Function getOnemptied();

	/**
	 * Sets the onemptied.
	 *
	 * @param onemptied
	 *            the new onemptied
	 */
	public void setOnemptied(Function onemptied);

	/**
	 * Gets the onended.
	 *
	 * @return the onended
	 */
	public Function getOnended();

	/**
	 * Sets the onended.
	 *
	 * @param onended
	 *            the new onended
	 */
	public void setOnended(Function onended);

	/**
	 * Gets the onerror.
	 *
	 * @return the onerror
	 */
	public Function getOnerror();

	/**
	 * Sets the onerror.
	 *
	 * @param onerror
	 *            the new onerror
	 */
	public void setOnerror(Function onerror);

	/**
	 * Gets the onfocus.
	 *
	 * @return the onfocus
	 */
	public Function getOnfocus();

	/**
	 * Sets the onfocus.
	 *
	 * @param onfocus
	 *            the new onfocus
	 */
	public void setOnfocus(Function onfocus);

	/**
	 * Gets the oninput.
	 *
	 * @return the oninput
	 */
	public Function getOninput();

	/**
	 * Sets the oninput.
	 *
	 * @param oninput
	 *            the new oninput
	 */
	public void setOninput(Function oninput);

	/**
	 * Gets the oninvalid.
	 *
	 * @return the oninvalid
	 */
	public Function getOninvalid();

	/**
	 * Sets the oninvalid.
	 *
	 * @param oninvalid
	 *            the new oninvalid
	 */
	public void setOninvalid(Function oninvalid);

	/**
	 * Gets the onkeydown.
	 *
	 * @return the onkeydown
	 */
	public Function getOnkeydown();

	/**
	 * Sets the onkeydown.
	 *
	 * @param onkeydown
	 *            the new onkeydown
	 */
	public void setOnkeydown(Function onkeydown);

	/**
	 * Gets the onkeypress.
	 *
	 * @return the onkeypress
	 */
	public Function getOnkeypress();

	/**
	 * Sets the onkeypress.
	 *
	 * @param onkeypress
	 *            the new onkeypress
	 */
	public void setOnkeypress(Function onkeypress);

	/**
	 * Gets the onkeyup.
	 *
	 * @return the onkeyup
	 */
	public Function getOnkeyup();

	/**
	 * Sets the onkeyup.
	 *
	 * @param onkeyup
	 *            the new onkeyup
	 */
	public void setOnkeyup(Function onkeyup);

	/**
	 * Gets the onload.
	 *
	 * @return the onload
	 */
	public Function getOnload();

	/**
	 * Sets the onload.
	 *
	 * @param onload
	 *            the new onload
	 */
	public void setOnload(Function onload);

	/**
	 * Gets the onloadeddata.
	 *
	 * @return the onloadeddata
	 */
	public Function getOnloadeddata();

	/**
	 * Sets the onloadeddata.
	 *
	 * @param onloadeddata
	 *            the new onloadeddata
	 */
	public void setOnloadeddata(Function onloadeddata);

	/**
	 * Gets the onloadedmetadata.
	 *
	 * @return the onloadedmetadata
	 */
	public Function getOnloadedmetadata();

	/**
	 * Sets the onloadedmetadata.
	 *
	 * @param onloadedmetadata
	 *            the new onloadedmetadata
	 */
	public void setOnloadedmetadata(Function onloadedmetadata);

	/**
	 * Gets the onloadstart.
	 *
	 * @return the onloadstart
	 */
	public Function getOnloadstart();

	/**
	 * Sets the onloadstart.
	 *
	 * @param onloadstart
	 *            the new onloadstart
	 */
	public void setOnloadstart(Function onloadstart);

	/**
	 * Gets the onmousedown.
	 *
	 * @return the onmousedown
	 */
	public Function getOnmousedown();

	/**
	 * Sets the onmousedown.
	 *
	 * @param onmousedown
	 *            the new onmousedown
	 */
	public void setOnmousedown(Function onmousedown);

	/**
	 * Gets the onmousemove.
	 *
	 * @return the onmousemove
	 */
	public Function getOnmousemove();

	/**
	 * Sets the onmousemove.
	 *
	 * @param onmousemove
	 *            the new onmousemove
	 */
	public void setOnmousemove(Function onmousemove);

	/**
	 * Gets the onmouseout.
	 *
	 * @return the onmouseout
	 */
	public Function getOnmouseout();

	/**
	 * Sets the onmouseout.
	 *
	 * @param onmouseout
	 *            the new onmouseout
	 */
	public void setOnmouseout(Function onmouseout);

	/**
	 * Gets the onmouseover.
	 *
	 * @return the onmouseover
	 */
	public Function getOnmouseover();

	/**
	 * Sets the onmouseover.
	 *
	 * @param onmouseover
	 *            the new onmouseover
	 */
	public void setOnmouseover(Function onmouseover);

	/**
	 * Gets the onmouseup.
	 *
	 * @return the onmouseup
	 */
	public Function getOnmouseup();

	/**
	 * Sets the onmouseup.
	 *
	 * @param onmouseup
	 *            the new onmouseup
	 */
	public void setOnmouseup(Function onmouseup);

	/**
	 * Gets the onmousewheel.
	 *
	 * @return the onmousewheel
	 */
	public Function getOnmousewheel();

	/**
	 * Sets the onmousewheel.
	 *
	 * @param onmousewheel
	 *            the new onmousewheel
	 */
	public void setOnmousewheel(Function onmousewheel);

	/**
	 * Gets the onpause.
	 *
	 * @return the onpause
	 */
	public Function getOnpause();

	/**
	 * Sets the onpause.
	 *
	 * @param onpause
	 *            the new onpause
	 */
	public void setOnpause(Function onpause);

	/**
	 * Gets the onplay.
	 *
	 * @return the onplay
	 */
	public Function getOnplay();

	/**
	 * Sets the onplay.
	 *
	 * @param onplay
	 *            the new onplay
	 */
	public void setOnplay(Function onplay);

	/**
	 * Gets the onplaying.
	 *
	 * @return the onplaying
	 */
	public Function getOnplaying();

	/**
	 * Sets the onplaying.
	 *
	 * @param onplaying
	 *            the new onplaying
	 */
	public void setOnplaying(Function onplaying);

	/**
	 * Gets the onprogress.
	 *
	 * @return the onprogress
	 */
	public Function getOnprogress();

	/**
	 * Sets the onprogress.
	 *
	 * @param onprogress
	 *            the new onprogress
	 */
	public void setOnprogress(Function onprogress);

	/**
	 * Gets the onratechange.
	 *
	 * @return the onratechange
	 */
	public Function getOnratechange();

	/**
	 * Sets the onratechange.
	 *
	 * @param onratechange
	 *            the new onratechange
	 */
	public void setOnratechange(Function onratechange);

	/**
	 * Gets the onreadystatechange.
	 *
	 * @return the onreadystatechange
	 */
	public Function getOnreadystatechange();

	/**
	 * Sets the onreadystatechange.
	 *
	 * @param onreadystatechange
	 *            the new onreadystatechange
	 */
	public void setOnreadystatechange(Function onreadystatechange);

	/**
	 * Gets the onreset.
	 *
	 * @return the onreset
	 */
	public Function getOnreset();

	/**
	 * Sets the onreset.
	 *
	 * @param onreset
	 *            the new onreset
	 */
	public void setOnreset(Function onreset);

	/**
	 * Gets the onscroll.
	 *
	 * @return the onscroll
	 */
	public Function getOnscroll();

	/**
	 * Sets the onscroll.
	 *
	 * @param onscroll
	 *            the new onscroll
	 */
	public void setOnscroll(Function onscroll);

	/**
	 * Gets the onseeked.
	 *
	 * @return the onseeked
	 */
	public Function getOnseeked();

	/**
	 * Sets the onseeked.
	 *
	 * @param onseeked
	 *            the new onseeked
	 */
	public void setOnseeked(Function onseeked);

	/**
	 * Gets the onseeking.
	 *
	 * @return the onseeking
	 */
	public Function getOnseeking();

	/**
	 * Sets the onseeking.
	 *
	 * @param onseeking
	 *            the new onseeking
	 */
	public void setOnseeking(Function onseeking);

	/**
	 * Gets the onselect.
	 *
	 * @return the onselect
	 */
	public Function getOnselect();

	/**
	 * Sets the onselect.
	 *
	 * @param onselect
	 *            the new onselect
	 */
	public void setOnselect(Function onselect);

	/**
	 * Gets the onshow.
	 *
	 * @return the onshow
	 */
	public Function getOnshow();

	/**
	 * Sets the onshow.
	 *
	 * @param onshow
	 *            the new onshow
	 */
	public void setOnshow(Function onshow);

	/**
	 * Gets the onstalled.
	 *
	 * @return the onstalled
	 */
	public Function getOnstalled();

	/**
	 * Sets the onstalled.
	 *
	 * @param onstalled
	 *            the new onstalled
	 */
	public void setOnstalled(Function onstalled);

	/**
	 * Gets the onsubmit.
	 *
	 * @return the onsubmit
	 */
	public Function getOnsubmit();

	/**
	 * Sets the onsubmit.
	 *
	 * @param onsubmit
	 *            the new onsubmit
	 */
	public void setOnsubmit(Function onsubmit);

	/**
	 * Gets the onsuspend.
	 *
	 * @return the onsuspend
	 */
	public Function getOnsuspend();

	/**
	 * Sets the onsuspend.
	 *
	 * @param onsuspend
	 *            the new onsuspend
	 */
	public void setOnsuspend(Function onsuspend);

	/**
	 * Gets the ontimeupdate.
	 *
	 * @return the ontimeupdate
	 */
	public Function getOntimeupdate();

	/**
	 * Sets the ontimeupdate.
	 *
	 * @param ontimeupdate
	 *            the new ontimeupdate
	 */
	public void setOntimeupdate(Function ontimeupdate);

	/**
	 * Gets the onvolumechange.
	 *
	 * @return the onvolumechange
	 */
	public Function getOnvolumechange();

	/**
	 * Sets the onvolumechange.
	 *
	 * @param onvolumechange
	 *            the new onvolumechange
	 */
	public void setOnvolumechange(Function onvolumechange);

	/**
	 * Gets the onwaiting.
	 *
	 * @return the onwaiting
	 */
	public Function getOnwaiting();

	/**
	 * Sets the onwaiting.
	 *
	 * @param onwaiting
	 *            the new onwaiting
	 */
	public void setOnwaiting(Function onwaiting);

	/**
	 * Gets the fg color.
	 *
	 * @return the fg color
	 */
	// HTMLDocument-34
	public String getFgColor();

	/**
	 * Sets the fg color.
	 *
	 * @param fgColor
	 *            the new fg color
	 */
	public void setFgColor(String fgColor);

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
	 * Gets the link color.
	 *
	 * @return the link color
	 */
	public String getLinkColor();

	/**
	 * Sets the link color.
	 *
	 * @param linkColor
	 *            the new link color
	 */
	public void setLinkColor(String linkColor);

	/**
	 * Gets the vlink color.
	 *
	 * @return the vlink color
	 */
	public String getVlinkColor();

	/**
	 * Sets the vlink color.
	 *
	 * @param vlinkColor
	 *            the new vlink color
	 */
	public void setVlinkColor(String vlinkColor);

	/**
	 * Gets the alink color.
	 *
	 * @return the alink color
	 */
	public String getAlinkColor();

	/**
	 * Sets the alink color.
	 *
	 * @param alinkColor
	 *            the new alink color
	 */
	public void setAlinkColor(String alinkColor);

	/**
	 * Gets the anchors.
	 *
	 * @return the anchors
	 */
	public HTMLCollection getAnchors();

	/**
	 * Gets the applets.
	 *
	 * @return the applets
	 */
	public HTMLCollection getApplets();

	/**
	 * Clear.
	 */
	public void clear();

	/**
	 * Evaluate.
	 *
	 * @param expression
	 *            the expression
	 * @param contextNode
	 *            the context node
	 * @param resolver
	 *            the resolver
	 * @param type
	 *            the type
	 * @param result
	 *            the result
	 * @return the x path result impl
	 */
	XPathResultImpl evaluate(String expression, HTMLElement contextNode, XPathNSResolverImpl resolver, Short type,
			Object result);

	/**
	 * Adds the event listener.
	 *
	 * @param script
	 *            the script
	 * @param function
	 *            the function
	 */
	void addEventListener(String script, Function function);

	/**
	 * Removes the event listener.
	 *
	 * @param script
	 *            the script
	 * @param function
	 *            the function
	 * @param bool
	 *            the bool
	 */
	@Override
	void removeEventListener(String script, Function function, boolean bool);

	/**
	 * Adds the event listener.
	 *
	 * @param script
	 *            the script
	 * @param function
	 *            the function
	 * @param bool
	 *            the bool
	 */
	@Override
	void addEventListener(String script, Function function, boolean bool);

	/**
	 * Removes the event listener.
	 *
	 * @param script
	 *            the script
	 * @param function
	 *            the function
	 */
	void removeEventListener(String script, Function function);

	/**
	 * Query selector all.
	 *
	 * @param selectors
	 *            the selectors
	 * @return the node list
	 */
	NodeList querySelectorAll(String selectors);

	/**
	 * Query selector.
	 *
	 * @param selectors
	 *            the selectors
	 * @return the element
	 */
	Element querySelector(String selectors);

	/**
	 * Gets the elements by class name.
	 *
	 * @param classNames
	 *            the class names
	 * @return the elements by class name
	 */
	NodeList getElementsByClassName(String classNames);

	/**
	 * Gets the default charset.
	 *
	 * @return the default charset
	 */
	String getDefaultCharset();

	/**
	 * Gets the character set.
	 *
	 * @return the character set
	 */
	String getCharacterSet();

	/**
	 * Gets the compat mode.
	 *
	 * @return the compat mode
	 */
	String getCompatMode();

	/**
	 * Writeln.
	 *
	 * @param text
	 *            the text
	 */
	void writeln(String text);

	/**
	 * Write.
	 *
	 * @param text
	 *            the text
	 */
	void write(String text);
}
