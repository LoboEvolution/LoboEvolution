/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

import lombok.Builder;
import lombok.Data;
import org.loboevolution.html.HTMLTag;

/**
 * <p>ElementInfo class.</p>
 */
@Data
@Builder
public class ElementInfo {

	/** Constant END_ELEMENT_FORBIDDEN=0 */
	public static final int END_ELEMENT_FORBIDDEN = 0;

	/** Constant END_ELEMENT_OPTIONAL=1 */
	public static final int END_ELEMENT_OPTIONAL = 1;

	/** Constant END_ELEMENT_REQUIRED=2 */
	public static final int END_ELEMENT_REQUIRED = 2;

	/** The childElementOk. */
	private boolean childElementOk;

	/** The decodeEntities. */
	private boolean decodeEntities;

	/** The endElementType. */
	private int endElementType;
	/** The noScriptElement. */
	private boolean noScriptElement;

	/** The stopTags. */
	private Set<HTMLTag> stopTags;
}
