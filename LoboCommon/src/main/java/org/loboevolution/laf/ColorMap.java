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

package org.loboevolution.laf;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ColorMap {

    private static final Map<String, Color> COLOR_MAP = new HashMap<>();

    static {

        COLOR_MAP.put("transparent", new Color(0, 0, 0, 0));
        COLOR_MAP.put("aliceblue", new Color(240, 248, 255));
        COLOR_MAP.put("antiquewhite", new Color(250, 235, 215));
        COLOR_MAP.put("aqua", new Color(0, 255, 255));
        COLOR_MAP.put("aquamarine", new Color(127, 255, 212));
        COLOR_MAP.put("azure", new Color(240, 255, 255));
        COLOR_MAP.put("beige", new Color(245, 245, 220));
        COLOR_MAP.put("bisque", new Color(255, 228, 196));
        COLOR_MAP.put("black", new Color(0, 0, 0));
        COLOR_MAP.put("blanchedalmond", new Color(255, 235, 205));
        COLOR_MAP.put("blue", new Color(0, 0, 255));
        COLOR_MAP.put("blueviolet", new Color(138, 43, 226));
        COLOR_MAP.put("brown", new Color(165, 42, 42));
        COLOR_MAP.put("burlywood", new Color(222, 184, 135));
        COLOR_MAP.put("cadetblue", new Color(95, 158, 160));
        COLOR_MAP.put("chartreuse", new Color(127, 255, 0));
        COLOR_MAP.put("chocolate", new Color(210, 105, 30));
        COLOR_MAP.put("coral", new Color(255, 127, 80));
        COLOR_MAP.put("cornflowerblue", new Color(100, 149, 237));
        COLOR_MAP.put("cornsilk", new Color(255, 248, 220));
        COLOR_MAP.put("crimson", new Color(220, 20, 60));
        COLOR_MAP.put("cyan", new Color(0, 255, 255));
        COLOR_MAP.put("darkblue", new Color(0, 0, 139));
        COLOR_MAP.put("darkcyan", new Color(0, 139, 139));
        COLOR_MAP.put("darkgoldenrod", new Color(184, 134, 11));
        COLOR_MAP.put("darkgray", new Color(169, 169, 169));
        COLOR_MAP.put("darkgreen", new Color(0, 100, 0));
        COLOR_MAP.put("darkgrey", new Color(169, 169, 169));
        COLOR_MAP.put("darkkhaki", new Color(189, 183, 107));
        COLOR_MAP.put("darkmagenta", new Color(139, 0, 139));
        COLOR_MAP.put("darkolivegreen", new Color(85, 107, 47));
        COLOR_MAP.put("darkorange", new Color(255, 140, 0));
        COLOR_MAP.put("darkorchid", new Color(153, 50, 204));
        COLOR_MAP.put("darkred", new Color(139, 0, 0));
        COLOR_MAP.put("darksalmon", new Color(233, 150, 122));
        COLOR_MAP.put("darkseagreen", new Color(143, 188, 143));
        COLOR_MAP.put("darkslateblue", new Color(72, 61, 139));
        COLOR_MAP.put("darkslategray", new Color(47, 79, 79));
        COLOR_MAP.put("darkslategrey", new Color(47, 79, 79));
        COLOR_MAP.put("darkturquoise", new Color(0, 206, 209));
        COLOR_MAP.put("darkviolet", new Color(148, 0, 211));
        COLOR_MAP.put("deeppink", new Color(255, 20, 147));
        COLOR_MAP.put("deepskyblue", new Color(0, 191, 255));
        COLOR_MAP.put("dimgray", new Color(105, 105, 105));
        COLOR_MAP.put("dimgrey", new Color(105, 105, 105));
        COLOR_MAP.put("dodgerblue", new Color(30, 144, 255));
        COLOR_MAP.put("firebrick", new Color(178, 34, 34));
        COLOR_MAP.put("floralwhite", new Color(255, 250, 240));
        COLOR_MAP.put("forestgreen", new Color(34, 139, 34));
        COLOR_MAP.put("fuchsia", new Color(255, 0, 255));
        COLOR_MAP.put("gainsboro", new Color(220, 220, 220));
        COLOR_MAP.put("ghostwhite", new Color(248, 248, 255));
        COLOR_MAP.put("gold", new Color(255, 215, 0));
        COLOR_MAP.put("goldenrod", new Color(218, 165, 32));
        COLOR_MAP.put("gray", new Color(128, 128, 128));
        COLOR_MAP.put("green", new Color(0, 128, 0));
        COLOR_MAP.put("greenyellow", new Color(173, 255, 47));
        COLOR_MAP.put("grey", new Color(128, 128, 128));
        COLOR_MAP.put("honeydew", new Color(240, 255, 240));
        COLOR_MAP.put("hotpink", new Color(255, 105, 180));
        COLOR_MAP.put("indianred", new Color(205, 92, 92));
        COLOR_MAP.put("indigo", new Color(75, 0, 130));
        COLOR_MAP.put("ivory", new Color(255, 255, 240));
        COLOR_MAP.put("khaki", new Color(240, 230, 140));
        COLOR_MAP.put("lavender", new Color(230, 230, 250));
        COLOR_MAP.put("lavenderblush", new Color(255, 240, 245));
        COLOR_MAP.put("lawngreen", new Color(124, 252, 0));
        COLOR_MAP.put("lemonchiffon", new Color(255, 250, 205));
        COLOR_MAP.put("lightblue", new Color(173, 216, 230));
        COLOR_MAP.put("lightcoral", new Color(240, 128, 128));
        COLOR_MAP.put("lightcyan", new Color(224, 255, 255));
        COLOR_MAP.put("lightgoldenrodyellow", new Color(250, 250, 210));
        COLOR_MAP.put("lightgray", new Color(211, 211, 211));
        COLOR_MAP.put("lightgreen", new Color(144, 238, 144));
        COLOR_MAP.put("lightgrey", new Color(211, 211, 211));
        COLOR_MAP.put("lightpink", new Color(255, 182, 193));
        COLOR_MAP.put("lightsalmon", new Color(255, 160, 122));
        COLOR_MAP.put("lightseagreen", new Color(32, 178, 170));
        COLOR_MAP.put("lightskyblue", new Color(135, 206, 250));
        COLOR_MAP.put("lightslategray", new Color(119, 136, 153));
        COLOR_MAP.put("lightslategrey", new Color(119, 136, 153));
        COLOR_MAP.put("lightsteelblue", new Color(176, 196, 222));
        COLOR_MAP.put("lightyellow", new Color(255, 255, 224));
        COLOR_MAP.put("lime", new Color(0, 255, 0));
        COLOR_MAP.put("limegreen", new Color(50, 205, 50));
        COLOR_MAP.put("linen", new Color(250, 240, 230));
        COLOR_MAP.put("magenta", new Color(255, 0, 255));
        COLOR_MAP.put("maroon", new Color(128, 0, 0));
        COLOR_MAP.put("mediumaquamarine", new Color(102, 205, 170));
        COLOR_MAP.put("mediumblue", new Color(0, 0, 205));
        COLOR_MAP.put("mediumorchid", new Color(186, 85, 211));
        COLOR_MAP.put("mediumpurple", new Color(147, 112, 219));
        COLOR_MAP.put("mediumseagreen", new Color(60, 179, 113));
        COLOR_MAP.put("mediumslateblue", new Color(123, 104, 238));
        COLOR_MAP.put("mediumspringgreen", new Color(0, 250, 154));
        COLOR_MAP.put("mediumturquoise", new Color(72, 209, 204));
        COLOR_MAP.put("mediumvioletred", new Color(199, 21, 133));
        COLOR_MAP.put("midnightblue", new Color(25, 25, 112));
        COLOR_MAP.put("mintcream", new Color(245, 255, 250));
        COLOR_MAP.put("mistyrose", new Color(255, 228, 225));
        COLOR_MAP.put("moccasin", new Color(255, 228, 181));
        COLOR_MAP.put("navajowhite", new Color(255, 222, 173));
        COLOR_MAP.put("navy", new Color(0, 0, 128));
        COLOR_MAP.put("oldlace", new Color(253, 245, 230));
        COLOR_MAP.put("olive", new Color(128, 128, 0));
        COLOR_MAP.put("olivedrab", new Color(107, 142, 35));
        COLOR_MAP.put("orange", new Color(255, 165, 0));
        COLOR_MAP.put("orangered", new Color(255, 69, 0));
        COLOR_MAP.put("orchid", new Color(218, 112, 214));
        COLOR_MAP.put("palegoldenrod", new Color(238, 232, 170));
        COLOR_MAP.put("palegreen", new Color(152, 251, 152));
        COLOR_MAP.put("paleturquoise", new Color(175, 238, 238));
        COLOR_MAP.put("palevioletred", new Color(219, 112, 147));
        COLOR_MAP.put("papayawhip", new Color(255, 239, 213));
        COLOR_MAP.put("peachpuff", new Color(255, 218, 185));
        COLOR_MAP.put("peru", new Color(205, 133, 63));
        COLOR_MAP.put("pink", new Color(255, 192, 203));
        COLOR_MAP.put("plum", new Color(221, 160, 221));
        COLOR_MAP.put("powderblue", new Color(176, 224, 230));
        COLOR_MAP.put("purple", new Color(128, 0, 128));
        COLOR_MAP.put("red", new Color(255, 0, 0));
        COLOR_MAP.put("rosybrown", new Color(188, 143, 143));
        COLOR_MAP.put("royalblue", new Color(65, 105, 225));
        COLOR_MAP.put("saddlebrown", new Color(139, 69, 19));
        COLOR_MAP.put("salmon", new Color(250, 128, 114));
        COLOR_MAP.put("sandybrown", new Color(244, 164, 96));
        COLOR_MAP.put("seagreen", new Color(46, 139, 87));
        COLOR_MAP.put("seashell", new Color(255, 245, 238));
        COLOR_MAP.put("sienna", new Color(160, 82, 45));
        COLOR_MAP.put("silver", new Color(192, 192, 192));
        COLOR_MAP.put("skyblue", new Color(135, 206, 235));
        COLOR_MAP.put("slateblue", new Color(106, 90, 205));
        COLOR_MAP.put("slategray", new Color(112, 128, 144));
        COLOR_MAP.put("slategrey", new Color(112, 128, 144));
        COLOR_MAP.put("snow", new Color(255, 250, 250));
        COLOR_MAP.put("springgreen", new Color(0, 255, 127));
        COLOR_MAP.put("steelblue", new Color(70, 130, 180));
        COLOR_MAP.put("tan", new Color(210, 180, 140));
        COLOR_MAP.put("teal", new Color(0, 128, 128));
        COLOR_MAP.put("thistle", new Color(216, 191, 216));
        COLOR_MAP.put("tomato", new Color(255, 99, 71));
        COLOR_MAP.put("turquoise", new Color(64, 224, 208));
        COLOR_MAP.put("violet", new Color(238, 130, 238));
        COLOR_MAP.put("wheat", new Color(245, 222, 179));
        COLOR_MAP.put("white", new Color(255, 255, 255));
        COLOR_MAP.put("whitesmoke", new Color(245, 245, 245));
        COLOR_MAP.put("yellow", new Color(255, 255, 0));
        COLOR_MAP.put("yellowgreen", new Color(154, 205, 50));
    }

    public static Map<String, Color> colorMap() {
        return COLOR_MAP;
    }
}
