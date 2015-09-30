/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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

import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.mozilla.javascript.Function;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * The Interface HTMLElement.
 */
public interface HTMLElement extends Element {
	
	/**
	 * Gets the elements by class name.
	 *
	 * @param classNames the class names
	 * @return the elements by class name
	 */
	// HTMLElement
	public NodeList getElementsByClassName(String classNames);

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
	 * Gets the outer html.
	 *
	 * @return the outer html
	 */
	public String getOuterHTML();

	/**
	 * Sets the outer html.
	 *
	 * @param outerHTML
	 *            the new outer html
	 */
	public void setOuterHTML(String outerHTML);

	/**
	 * Insert adjacent html.
	 *
	 * @param position the position
	 * @param text the text
	 */
	public void insertAdjacentHTML(String position, String text);

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId();

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id);

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
	 * Gets the lang.
	 *
	 * @return the lang
	 */
	public String getLang();

	/**
	 * Sets the lang.
	 *
	 * @param lang
	 *            the new lang
	 */
	public void setLang(String lang);

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
	 * Gets the class name.
	 *
	 * @return the class name
	 */
	public String getClassName();

	/**
	 * Sets the class name.
	 *
	 * @param className
	 *            the new class name
	 */
	public void setClassName(String className);

	/**
	 * Gets the class list.
	 *
	 * @return the class list
	 */
	public DOMTokenList getClassList();

	/**
	 * Gets the dataset.
	 *
	 * @return the dataset
	 */
	public DOMStringMap getDataset();

	/**
	 * Gets the item scope.
	 *
	 * @return the item scope
	 */
	public boolean getItemScope();

	/**
	 * Sets the item scope.
	 *
	 * @param itemScope
	 *            the new item scope
	 */
	public void setItemScope(boolean itemScope);

	/**
	 * Gets the item type.
	 *
	 * @return the item type
	 */
	public String getItemType();

	/**
	 * Sets the item type.
	 *
	 * @param itemType
	 *            the new item type
	 */
	public void setItemType(String itemType);

	/**
	 * Gets the item id.
	 *
	 * @return the item id
	 */
	public String getItemId();

	/**
	 * Sets the item id.
	 *
	 * @param itemId
	 *            the new item id
	 */
	public void setItemId(String itemId);

	/**
	 * Gets the item ref.
	 *
	 * @return the item ref
	 */
	public DOMSettableTokenList getItemRef();

	/**
	 * Sets the item ref.
	 *
	 * @param itemRef
	 *            the new item ref
	 */
	public void setItemRef(String itemRef);

	/**
	 * Gets the item prop.
	 *
	 * @return the item prop
	 */
	public DOMSettableTokenList getItemProp();

	/**
	 * Sets the item prop.
	 *
	 * @param itemProp
	 *            the new item prop
	 */
	public void setItemProp(String itemProp);

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public HTMLPropertiesCollection getProperties();

	/**
	 * Gets the item value.
	 *
	 * @return the item value
	 */
	public Object getItemValue();

	/**
	 * Sets the item value.
	 *
	 * @param itemValue
	 *            the new item value
	 */
	public void setItemValue(Object itemValue);

	/**
	 * Gets the hidden.
	 *
	 * @return the hidden
	 */
	public boolean getHidden();

	/**
	 * Sets the hidden.
	 *
	 * @param hidden
	 *            the new hidden
	 */
	public void setHidden(boolean hidden);

	/**
	 * Click.
	 */
	public void click();

	/**
	 * Gets the tab index.
	 *
	 * @return the tab index
	 */
	public int getTabIndex();

	/**
	 * Sets the tab index.
	 *
	 * @param tabIndex
	 *            the new tab index
	 */
	public void setTabIndex(int tabIndex);

	/**
	 * Focus.
	 */
	public void focus();

	/**
	 * Blur.
	 */
	public void blur();

	/**
	 * Gets the access key.
	 *
	 * @return the access key
	 */
	public String getAccessKey();

	/**
	 * Sets the access key.
	 *
	 * @param accessKey
	 *            the new access key
	 */
	public void setAccessKey(String accessKey);

	/**
	 * Gets the access key label.
	 *
	 * @return the access key label
	 */
	public String getAccessKeyLabel();

	/**
	 * Gets the draggable.
	 *
	 * @return the draggable
	 */
	public boolean getDraggable();

	/**
	 * Sets the draggable.
	 *
	 * @param draggable
	 *            the new draggable
	 */
	public void setDraggable(boolean draggable);

	/**
	 * Gets the dropzone.
	 *
	 * @return the dropzone
	 */
	public DOMSettableTokenList getDropzone();

	/**
	 * Sets the dropzone.
	 *
	 * @param dropzone
	 *            the new dropzone
	 */
	public void setDropzone(String dropzone);

	/**
	 * Gets the content editable.
	 *
	 * @return the content editable
	 */
	public String getContentEditable();

	/**
	 * Sets the content editable.
	 *
	 * @param contentEditable
	 *            the new content editable
	 */
	public void setContentEditable(String contentEditable);

	/**
	 * Gets the checks if is content editable.
	 *
	 * @return the checks if is content editable
	 */
	public boolean getIsContentEditable();

	/**
	 * Gets the context menu.
	 *
	 * @return the context menu
	 */
	public HTMLMenuElement getContextMenu();

	/**
	 * Sets the context menu.
	 *
	 * @param contextMenu
	 *            the new context menu
	 */
	public void setContextMenu(HTMLMenuElement contextMenu);

	/**
	 * Gets the spellcheck.
	 *
	 * @return the spellcheck
	 */
	public String getSpellcheck();

	/**
	 * Sets the spellcheck.
	 *
	 * @param spellcheck
	 *            the new spellcheck
	 */
	public void setSpellcheck(boolean spellcheck);

	/**
	 * Gets the command type.
	 *
	 * @return the command type
	 */
	public String getCommandType();

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel();

	/**
	 * Gets the icon.
	 *
	 * @return the icon
	 */
	public String getIcon();

	/**
	 * Gets the disabled.
	 *
	 * @return the disabled
	 */
	public boolean getDisabled();

	/**
	 * Gets the checked.
	 *
	 * @return the checked
	 */
	public boolean getChecked();

	/**
	 * Gets the style.
	 *
	 * @return the style
	 */
	public AbstractCSS2Properties getStyle();

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
	 * Gets the offset parent.
	 *
	 * @return the offset parent
	 */
	// HTMLElement
	public Element getOffsetParent();

	/**
	 * Gets the offset top.
	 *
	 * @return the offset top
	 */
	public int getOffsetTop();

	/**
	 * Gets the offset left.
	 *
	 * @return the offset left
	 */
	public int getOffsetLeft();

	/**
	 * Gets the offset width.
	 *
	 * @return the offset width
	 */
	public int getOffsetWidth();

	/**
	 * Gets the offset height.
	 *
	 * @return the offset height
	 */
	public int getOffsetHeight();

	/**
	 * Query selector all.
	 *
	 * @param selectors the selectors
	 * @return the node list
	 */
	NodeList querySelectorAll(String selectors);

	/**
	 * Query selector.
	 *
	 * @param selectors the selectors
	 * @return the element
	 */
	Element querySelector(String selectors);
}
