/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.dom.svg;

/**
 * <p>SVGRenderingIntent interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGRenderingIntent {
	// Rendering Intent Types
	/** Constant RENDERING_INTENT_UNKNOWN=0 */
    short RENDERING_INTENT_UNKNOWN = 0;
	/** Constant RENDERING_INTENT_AUTO=1 */
    short RENDERING_INTENT_AUTO = 1;
	/** Constant RENDERING_INTENT_PERCEPTUAL=2 */
    short RENDERING_INTENT_PERCEPTUAL = 2;
	/** Constant RENDERING_INTENT_RELATIVE_COLORIMETRIC=3 */
    short RENDERING_INTENT_RELATIVE_COLORIMETRIC = 3;
	/** Constant RENDERING_INTENT_SATURATION=4 */
    short RENDERING_INTENT_SATURATION = 4;
	/** Constant RENDERING_INTENT_ABSOLUTE_COLORIMETRIC=5 */
    short RENDERING_INTENT_ABSOLUTE_COLORIMETRIC = 5;
}
