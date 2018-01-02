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
package org.lobobrowser.w3c.svg;

public interface SVGPaint extends SVGColor {
	// Paint Types
	public static final short SVG_PAINTTYPE_UNKNOWN = 0;
	public static final short SVG_PAINTTYPE_RGBCOLOR = 1;
	public static final short SVG_PAINTTYPE_RGBCOLOR_ICCCOLOR = 2;
	public static final short SVG_PAINTTYPE_NONE = 101;
	public static final short SVG_PAINTTYPE_CURRENTCOLOR = 102;
	public static final short SVG_PAINTTYPE_URI_NONE = 103;
	public static final short SVG_PAINTTYPE_URI_CURRENTCOLOR = 104;
	public static final short SVG_PAINTTYPE_URI_RGBCOLOR = 105;
	public static final short SVG_PAINTTYPE_URI_RGBCOLOR_ICCCOLOR = 106;
	public static final short SVG_PAINTTYPE_URI = 107;

	public short getPaintType();

	public String getUri();

	public void setUri(String uri);

	public void setPaint(short paintType, String uri, String rgbColor, String iccColor) throws SVGException;
}
