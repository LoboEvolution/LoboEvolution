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
/*
 * Created on Oct 23, 2005
 */
package org.loboevolution.html.info;

import java.io.Serializable;
import java.util.Set;

/**
 * The Class ElementInfo.
 */
public class ElementInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7783823451639961618L;

	/** The end element type. */
	private int endElementType;

	/** The child element ok. */
	private boolean childElementOk;

	/** The stop tags. */
	private Set<String> stopTags;

	/** The no script element. */
	private boolean noScriptElement;

	/** The decode entities. */
	private boolean decodeEntities;

	/** The Constant END_ELEMENT_FORBIDDEN. */
	public static final int END_ELEMENT_FORBIDDEN = 0;

	/** The Constant END_ELEMENT_OPTIONAL. */
	public static final int END_ELEMENT_OPTIONAL = 1;

	/** The Constant END_ELEMENT_REQUIRED. */
	public static final int END_ELEMENT_REQUIRED = 2;

	/**
	 * Instantiates a new element info.
	 *
	 * @param ok
	 *            the ok
	 * @param type
	 *            the type
	 */
	public ElementInfo(boolean ok, int type) {
		this.childElementOk = ok;
		this.endElementType = type;
		this.stopTags = null;
		this.noScriptElement = false;
		this.decodeEntities = true;
	}

	/**
	 * Instantiates a new element info.
	 *
	 * @param ok
	 *            the ok
	 * @param type
	 *            the type
	 * @param stopTags
	 *            the stop tags
	 */
	public ElementInfo(boolean ok, int type, Set<String> stopTags) {
		this.childElementOk = ok;
		this.endElementType = type;
		this.stopTags = stopTags;
		this.noScriptElement = false;
		this.decodeEntities = true;
	}

	/**
	 * Instantiates a new element info.
	 *
	 * @param ok
	 *            the ok
	 * @param type
	 *            the type
	 * @param stopTags
	 *            the stop tags
	 * @param noScriptElement
	 *            the no script element
	 */
	public ElementInfo(boolean ok, int type, Set<String> stopTags, boolean noScriptElement) {
		this.childElementOk = ok;
		this.endElementType = type;
		this.stopTags = stopTags;
		this.noScriptElement = noScriptElement;
		this.decodeEntities = true;
	}

	/**
	 * Instantiates a new element info.
	 *
	 * @param ok
	 *            the ok
	 * @param type
	 *            the type
	 * @param decodeEntities
	 *            the decode entities
	 */
	public ElementInfo(boolean ok, int type, boolean decodeEntities) {
		this.childElementOk = ok;
		this.endElementType = type;
		this.stopTags = null;
		this.noScriptElement = false;
		this.decodeEntities = decodeEntities;
	}

	/**
	 * Gets the end element type.
	 *
	 * @return the end element type
	 */
	public int getEndElementType() {
		return endElementType;
	}

	/**
	 * Sets the end element type.
	 *
	 * @param endElementType
	 *            the new end element type
	 */
	public void setEndElementType(int endElementType) {
		this.endElementType = endElementType;
	}

	/**
	 * Checks if is child element ok.
	 *
	 * @return the child element ok
	 */
	public boolean isChildElementOk() {
		return childElementOk;
	}

	/**
	 * Sets the child element ok.
	 *
	 * @param childElementOk
	 *            the new child element ok
	 */
	public void setChildElementOk(boolean childElementOk) {
		this.childElementOk = childElementOk;
	}

	/**
	 * Gets the stop tags.
	 *
	 * @return the stop tags
	 */
	public Set<String> getStopTags() {
		return stopTags;
	}

	/**
	 * Sets the stop tags.
	 *
	 * @param stopTags
	 *            the new stop tags
	 */
	public void setStopTags(Set<String> stopTags) {
		this.stopTags = stopTags;
	}

	/**
	 * Checks if is no script element.
	 *
	 * @return the no script element
	 */
	public boolean isNoScriptElement() {
		return noScriptElement;
	}

	/**
	 * Sets the no script element.
	 *
	 * @param noScriptElement
	 *            the new no script element
	 */
	public void setNoScriptElement(boolean noScriptElement) {
		this.noScriptElement = noScriptElement;
	}

	/**
	 * Checks if is decode entities.
	 *
	 * @return the decode entities
	 */
	public boolean isDecodeEntities() {
		return decodeEntities;
	}

	/**
	 * Sets the decode entities.
	 *
	 * @param decodeEntities
	 *            the new decode entities
	 */
	public void setDecodeEntities(boolean decodeEntities) {
		this.decodeEntities = decodeEntities;
	}
}
