/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Oct 23, 2005
 */
package org.loboevolution.info;

import java.util.Set;

import org.loboevolution.html.HTMLTag;

/**
 * <p>ElementInfo class.</p>
 *
 *
 *
 */
public class ElementInfo {
	/** Constant END_ELEMENT_FORBIDDEN=0 */
	public static final int END_ELEMENT_FORBIDDEN = 0;
	/** Constant END_ELEMENT_OPTIONAL=1 */
	public static final int END_ELEMENT_OPTIONAL = 1;
	/** Constant END_ELEMENT_REQUIRED=2 */
	public static final int END_ELEMENT_REQUIRED = 2;
	public final boolean childElementOk;
	public final boolean decodeEntities;

	public final int endElementType;
	public final boolean noScriptElement;
	public final Set<HTMLTag> stopTags;

	/**
	 * <p>Constructor for ElementInfo.</p>
	 *
	 * @param ok a boolean.
	 * @param type a int.
	 */
	public ElementInfo(boolean ok, int type) {
		this.childElementOk = ok;
		this.endElementType = type;
		this.stopTags = null;
		this.noScriptElement = false;
		this.decodeEntities = true;
	}

	/**
	 * <p>Constructor for ElementInfo.</p>
	 *
	 * @param ok a boolean.
	 * @param type a int.
	 * @param decodeEntities a boolean.
	 */
	public ElementInfo(boolean ok, int type, boolean decodeEntities) {
		this.childElementOk = ok;
		this.endElementType = type;
		this.stopTags = null;
		this.noScriptElement = false;
		this.decodeEntities = decodeEntities;
	}

	/**
	 * <p>Constructor for ElementInfo.</p>
	 *
	 * @param ok a boolean.
	 * @param type a int.
	 * @param stopTags a {@link java.util.Set} object.
	 */
	public ElementInfo(boolean ok, int type, Set<HTMLTag> stopTags) {
		this.childElementOk = ok;
		this.endElementType = type;
		this.stopTags = stopTags;
		this.noScriptElement = false;
		this.decodeEntities = true;
	}

	/**
	 * <p>Constructor for ElementInfo.</p>
	 *
	 * @param ok a boolean.
	 * @param type a int.
	 * @param stopTags a {@link java.util.Set} object.
	 * @param noScriptElement a boolean.
	 */
	public ElementInfo(boolean ok, int type, Set<HTMLTag> stopTags, boolean noScriptElement) {
		this.childElementOk = ok;
		this.endElementType = type;
		this.stopTags = stopTags;
		this.noScriptElement = noScriptElement;
		this.decodeEntities = true;
	}
}
