/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
