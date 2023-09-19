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

package org.loboevolution.html.dom.smil;

/**
 * This interface define the set of animation target extensions.
 */
public interface ElementTargetAttributes {

	// attributeTypes
	/** Constant ATTRIBUTE_TYPE_AUTO=0 */
    short ATTRIBUTE_TYPE_AUTO = 0;

	/** Constant ATTRIBUTE_TYPE_CSS=1 */
    short ATTRIBUTE_TYPE_CSS = 1;
	
	/** Constant ATTRIBUTE_TYPE_XML=2 */
    short ATTRIBUTE_TYPE_XML = 2;
	
    /**
     * The name of the target attribute.
     *
     * @return a {@link java.lang.String} object.
     */
    String getAttributeName();

    /**
     * <p>setAttributeName.</p>
     *
     * @param attributeName a {@link java.lang.String} object.
     */
    void setAttributeName(String attributeName);

    /**
     * A code representing the value of the attributeType attribute, as defined
     * above. Default value is ATTRIBUTE_TYPE_CODE .
     *
     * @return a short.
     */
    short getAttributeType();

    /**
     * <p>setAttributeType.</p>
     *
     * @param attributeType a short.
     */
    void setAttributeType(short attributeType);

}
