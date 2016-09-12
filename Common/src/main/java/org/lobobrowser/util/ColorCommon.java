/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.util;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class ColorCommon.
 */
public class ColorCommon {
    /** The Constant ALICEBLUE. */
    private static final int ALICEBLUE = 0xF0F8FF;
    /** The Constant ANTIQUEWHITE. */
    private static final int ANTIQUEWHITE = 0xFAEBD7;
    /** The Constant ANTIQUEWHITE1. */
    private static final int ANTIQUEWHITE1 = 0xFFEFDB;
    /** The Constant ANTIQUEWHITE2. */
    private static final int ANTIQUEWHITE2 = 0xEEDFCC;
    /** The Constant ANTIQUEWHITE3. */
    private static final int ANTIQUEWHITE3 = 0xCDC0B0;
    /** The Constant ANTIQUEWHITE4. */
    private static final int ANTIQUEWHITE4 = 0x8B8378;
    /** The Constant AQUAMARINE. */
    private static final int AQUAMARINE = 0x7FFFD4;
    /** The Constant AQUAMARINE1. */
    private static final int AQUAMARINE1 = 0x76EEC6;
    /** The Constant AQUAMARINE2. */
    private static final int AQUAMARINE2 = 0x458B74;
    /** The Constant AZURE. */
    private static final int AZURE = 0xF0FFFF;
    /** The Constant AZURE1. */
    private static final int AZURE1 = 0xE0EEEE;
    /** The Constant AZURE2. */
    private static final int AZURE2 = 0xC1CDCD;
    /** The Constant AZURE3. */
    private static final int AZURE3 = 0x838B8B;
    /** The Constant BEIGE. */
    private static final int BEIGE = 0xF5F5DC;
    /** The Constant BISQUE. */
    private static final int BISQUE = 0xFFE4C4;
    /** The Constant BISQUE1. */
    private static final int BISQUE1 = 0xEED5B7;
    /** The Constant BISQUE2. */
    private static final int BISQUE2 = 0xCDB79E;
    /** The Constant BISQUE3. */
    private static final int BISQUE3 = 0x8B7D6B;
    /** The Constant BLACK. */
    private static final int BLACK = 0x000000;
    /** The Constant BLANCHEDALMOND. */
    private static final int BLANCHEDALMOND = 0xFFEBCD;
    /** The Constant BLUE. */
    private static final int BLUE = 0x0000FF;
    /** The Constant BLUE1. */
    private static final int BLUE1 = 0x0000EE;
    /** The Constant BLUEVIOLET. */
    private static final int BLUEVIOLET = 0x8A2BE2;
    /** The Constant BROWN. */
    private static final int BROWN = 0xA52A2A;
    /** The Constant BROWN1. */
    private static final int BROWN1 = 0xFF4040;
    /** The Constant BROWN2. */
    private static final int BROWN2 = 0xEE3B3B;
    /** The Constant BROWN3. */
    private static final int BROWN3 = 0xCD3333;
    /** The Constant BROWN4. */
    private static final int BROWN4 = 0x8B2323;
    /** The Constant BURLYWOOD. */
    private static final int BURLYWOOD = 0xDEB887;
    /** The Constant BURLYWOOD1. */
    private static final int BURLYWOOD1 = 0xFFD39B;
    /** The Constant BURLYWOOD2. */
    private static final int BURLYWOOD2 = 0xEEC591;
    /** The Constant BURLYWOOD3. */
    private static final int BURLYWOOD3 = 0xCDAA7D;
    /** The Constant BURLYWOOD4. */
    private static final int BURLYWOOD4 = 0x8B7355;
    /** The Constant CADETBLUE. */
    private static final int CADETBLUE = 0x5F9EA0;
    /** The Constant CADETBLUE1. */
    private static final int CADETBLUE1 = 0x98F5FF;
    /** The Constant CADETBLUE2. */
    private static final int CADETBLUE2 = 0x8EE5EE;
    /** The Constant CADETBLUE3. */
    private static final int CADETBLUE3 = 0x7AC5CD;
    /** The Constant CADETBLUE4. */
    private static final int CADETBLUE4 = 0x53868B;
    /** The Constant CHARTREUSE. */
    private static final int CHARTREUSE = 0x7FFF00;
    /** The Constant CHARTREUSE1. */
    private static final int CHARTREUSE1 = 0x76EE00;
    /** The Constant CHARTREUSE2. */
    private static final int CHARTREUSE2 = 0x66CD00;
    /** The Constant CHARTREUSE3. */
    private static final int CHARTREUSE3 = 0x458B00;
    /** The Constant CHOCOLATE. */
    private static final int CHOCOLATE = 0xD2691E;
    /** The Constant CHOCOLATE1. */
    private static final int CHOCOLATE1 = 0xFF7F24;
    /** The Constant CHOCOLATE2. */
    private static final int CHOCOLATE2 = 0xEE7621;
    /** The Constant CHOCOLATE3. */
    private static final int CHOCOLATE3 = 0xCD661D;
    /** The Constant CORAL. */
    private static final int CORAL = 0xFF7F50;
    /** The Constant CORAL1. */
    private static final int CORAL1 = 0xFF7256;
    /** The Constant CORAL2. */
    private static final int CORAL2 = 0xEE6A50;
    /** The Constant CORAL3. */
    private static final int CORAL3 = 0xCD5B45;
    /** The Constant CORAL4. */
    private static final int CORAL4 = 0x8B3E2F;
    /** The Constant CORNFLOWERBLUE. */
    private static final int CORNFLOWERBLUE = 0x6495ED;
    /** The Constant CORNSILK. */
    private static final int CORNSILK = 0xFFF8DC;
    /** The Constant CORNSILK1. */
    private static final int CORNSILK1 = 0xEEE8CD;
    /** The Constant CORNSILK2. */
    private static final int CORNSILK2 = 0xCDC8B1;
    /** The Constant CORNSILK3. */
    private static final int CORNSILK3 = 0x8B8878;
    /** The Constant CRIMSON. */
    private static final int CRIMSON = 0xDC143C;
    /** The Constant CYAN. */
    private static final int CYAN = 0x00FFFF;
    /** The Constant CYAN1. */
    private static final int CYAN1 = 0x00EEEE;
    /** The Constant CYAN2. */
    private static final int CYAN2 = 0x00CDCD;
    /** The Constant DARKBLUE. */
    private static final int DARKBLUE = 0x00008B;
    /** The Constant DARKCYAN. */
    private static final int DARKCYAN = 0x008B8B;
    /** The Constant DARKGOLDENROD. */
    private static final int DARKGOLDENROD = 0xB8860B;
    /** The Constant DARKGOLDENROD1. */
    private static final int DARKGOLDENROD1 = 0xFFB90F;
    /** The Constant DARKGOLDENROD2. */
    private static final int DARKGOLDENROD2 = 0xEEAD0E;
    /** The Constant DARKGOLDENROD3. */
    private static final int DARKGOLDENROD3 = 0xCD950C;
    /** The Constant DARKGOLDENROD4. */
    private static final int DARKGOLDENROD4 = 0x8B6508;
    /** The Constant DARKGRAY. */
    private static final int DARKGRAY = 0xA9A9A9;
    /** The Constant DARKGREEN. */
    private static final int DARKGREEN = 0x006400;
    /** The Constant DARKKHAKI. */
    private static final int DARKKHAKI = 0xBDB76B;
    /** The Constant DARKMAGENTA. */
    private static final int DARKMAGENTA = 0x8B008B;
    /** The Constant DARKOLIVEGREEN. */
    private static final int DARKOLIVEGREEN = 0x556B2F;
    /** The Constant DARKOLIVEGREEN1. */
    private static final int DARKOLIVEGREEN1 = 0xCAFF70;
    /** The Constant DARKOLIVEGREEN2. */
    private static final int DARKOLIVEGREEN2 = 0xBCEE68;
    /** The Constant DARKOLIVEGREEN3. */
    private static final int DARKOLIVEGREEN3 = 0xA2CD5A;
    /** The Constant DARKOLIVEGREEN4. */
    private static final int DARKOLIVEGREEN4 = 0x6E8B3D;
    /** The Constant DARKORANGE. */
    private static final int DARKORANGE = 0xFF8C00;
    /** The Constant DARKORANGE1. */
    private static final int DARKORANGE1 = 0xFF7F00;
    /** The Constant DARKORANGE2. */
    private static final int DARKORANGE2 = 0xEE7600;
    /** The Constant DARKORANGE3. */
    private static final int DARKORANGE3 = 0xCD6600;
    /** The Constant DARKORANGE4. */
    private static final int DARKORANGE4 = 0x8B4500;
    /** The Constant DARKORCHID. */
    private static final int DARKORCHID = 0x9932CC;
    /** The Constant DARKORCHID1. */
    private static final int DARKORCHID1 = 0xBF3EFF;
    /** The Constant DARKORCHID2. */
    private static final int DARKORCHID2 = 0xB23AEE;
    /** The Constant DARKORCHID3. */
    private static final int DARKORCHID3 = 0x9A32CD;
    /** The Constant DARKORCHID4. */
    private static final int DARKORCHID4 = 0x68228B;
    /** The Constant DARKRED. */
    private static final int DARKRED = 0x8B0000;
    /** The Constant DARKSALMON. */
    private static final int DARKSALMON = 0xE9967A;
    /** The Constant DARKSEAGREEN. */
    private static final int DARKSEAGREEN = 0x8FBC8F;
    /** The Constant DARKSEAGREEN1. */
    private static final int DARKSEAGREEN1 = 0xC1FFC1;
    /** The Constant DARKSEAGREEN2. */
    private static final int DARKSEAGREEN2 = 0xB4EEB4;
    /** The Constant DARKSEAGREEN3. */
    private static final int DARKSEAGREEN3 = 0x9BCD9B;
    /** The Constant DARKSEAGREEN4. */
    private static final int DARKSEAGREEN4 = 0x698B69;
    /** The Constant DARKSLATEBLUE. */
    private static final int DARKSLATEBLUE = 0x483D8B;
    /** The Constant DARKSLATEGRAY. */
    private static final int DARKSLATEGRAY = 0x2F4F4F;
    /** The Constant DARKSLATEGRAY1. */
    private static final int DARKSLATEGRAY1 = 0x97FFFF;
    /** The Constant DARKSLATEGRAY2. */
    private static final int DARKSLATEGRAY2 = 0x8DEEEE;
    /** The Constant DARKSLATEGRAY3. */
    private static final int DARKSLATEGRAY3 = 0x79CDCD;
    /** The Constant DARKSLATEGRAY4. */
    private static final int DARKSLATEGRAY4 = 0x528B8B;
    /** The Constant DARKTURQUOISE. */
    private static final int DARKTURQUOISE = 0x00CED1;
    /** The Constant DARKVIOLET. */
    private static final int DARKVIOLET = 0x9400D3;
    /** The Constant DEEPPINK. */
    private static final int DEEPPINK = 0xFF1493;
    /** The Constant DEEPPINK1. */
    private static final int DEEPPINK1 = 0xEE1289;
    /** The Constant DEEPPINK2. */
    private static final int DEEPPINK2 = 0xCD1076;
    /** The Constant DEEPPINK3. */
    private static final int DEEPPINK3 = 0x8B0A50;
    /** The Constant DEEPSKYBLUE. */
    private static final int DEEPSKYBLUE = 0x00BFFF;
    /** The Constant DEEPSKYBLUE1. */
    private static final int DEEPSKYBLUE1 = 0x00B2EE;
    /** The Constant DEEPSKYBLUE2. */
    private static final int DEEPSKYBLUE2 = 0x009ACD;
    /** The Constant DEEPSKYBLUE3. */
    private static final int DEEPSKYBLUE3 = 0x00688B;
    /** The Constant DIMGRAY. */
    private static final int DIMGRAY = 0x696969;
    /** The Constant DODGERBLUE. */
    private static final int DODGERBLUE = 0x1E90FF;
    /** The Constant DODGERBLUE1. */
    private static final int DODGERBLUE1 = 0x1C86EE;
    /** The Constant DODGERBLUE2. */
    private static final int DODGERBLUE2 = 0x1874CD;
    /** The Constant DODGERBLUE3. */
    private static final int DODGERBLUE3 = 0x104E8B;
    /** The Constant FIREBRICK. */
    private static final int FIREBRICK = 0xB22222;
    /** The Constant FIREBRICK1. */
    private static final int FIREBRICK1 = 0xFF3030;
    /** The Constant FIREBRICK2. */
    private static final int FIREBRICK2 = 0xEE2C2C;
    /** The Constant FIREBRICK3. */
    private static final int FIREBRICK3 = 0xCD2626;
    /** The Constant FIREBRICK4. */
    private static final int FIREBRICK4 = 0x8B1A1A;
    /** The Constant FLORALWHITE. */
    private static final int FLORALWHITE = 0xFFFAF0;
    /** The Constant FORESTGREEN. */
    private static final int FORESTGREEN = 0x228B22;
    /** The Constant FRACTAL. */
    private static final int FRACTAL = 0x808080;
    /** The Constant GAINSBORO. */
    private static final int GAINSBORO = 0xDCDCDC;
    /** The Constant GHOSTWHITE. */
    private static final int GHOSTWHITE = 0xF8F8FF;
    /** The Constant GOLD. */
    private static final int GOLD = 0xFFD700;
    /** The Constant GOLD1. */
    private static final int GOLD1 = 0xEEC900;
    /** The Constant GOLD2. */
    private static final int GOLD2 = 0xCDAD00;
    /** The Constant GOLD3. */
    private static final int GOLD3 = 0x8B7500;
    /** The Constant GOLDENROD. */
    private static final int GOLDENROD = 0xDAA520;
    /** The Constant GOLDENROD1. */
    private static final int GOLDENROD1 = 0xFFC125;
    /** The Constant GOLDENROD2. */
    private static final int GOLDENROD2 = 0xEEB422;
    /** The Constant GOLDENROD3. */
    private static final int GOLDENROD3 = 0xCD9B1D;
    /** The Constant GOLDENROD4. */
    private static final int GOLDENROD4 = 0x8B6914;
    /** The Constant GRAY. */
    private static final int GRAY = 0xBEBEBE;
    /** The Constant GRAY1. */
    private static final int GRAY1 = 0x030303;
    /** The Constant GRAY10. */
    private static final int GRAY10 = 0x1A1A1A;
    /** The Constant GRAY11. */
    private static final int GRAY11 = 0x1C1C1C;
    /** The Constant GRAY12. */
    private static final int GRAY12 = 0x1F1F1F;
    /** The Constant GRAY13. */
    private static final int GRAY13 = 0x212121;
    /** The Constant GRAY14. */
    private static final int GRAY14 = 0x242424;
    /** The Constant GRAY15. */
    private static final int GRAY15 = 0x262626;
    /** The Constant GRAY16. */
    private static final int GRAY16 = 0x292929;
    /** The Constant GRAY17. */
    private static final int GRAY17 = 0x2B2B2B;
    /** The Constant GRAY18. */
    private static final int GRAY18 = 0x2E2E2E;
    /** The Constant GRAY19. */
    private static final int GRAY19 = 0x303030;
    /** The Constant GRAY2. */
    private static final int GRAY2 = 0x050505;
    /** The Constant GRAY20. */
    private static final int GRAY20 = 0x333333;
    /** The Constant GRAY21. */
    private static final int GRAY21 = 0x363636;
    /** The Constant GRAY22. */
    private static final int GRAY22 = 0x383838;
    /** The Constant GRAY23. */
    private static final int GRAY23 = 0x3B3B3B;
    /** The Constant GRAY24. */
    private static final int GRAY24 = 0x3D3D3D;
    /** The Constant GRAY25. */
    private static final int GRAY25 = 0x404040;
    /** The Constant GRAY26. */
    private static final int GRAY26 = 0x424242;
    /** The Constant GRAY27. */
    private static final int GRAY27 = 0x454545;
    /** The Constant GRAY28. */
    private static final int GRAY28 = 0x474747;
    /** The Constant GRAY29. */
    private static final int GRAY29 = 0x4A4A4A;
    /** The Constant GRAY3. */
    private static final int GRAY3 = 0x080808;
    /** The Constant GRAY30. */
    private static final int GRAY30 = 0x4D4D4D;
    /** The Constant GRAY31. */
    private static final int GRAY31 = 0x4F4F4F;
    /** The Constant GRAY32. */
    private static final int GRAY32 = 0x525252;
    /** The Constant GRAY33. */
    private static final int GRAY33 = 0x545454;
    /** The Constant GRAY34. */
    private static final int GRAY34 = 0x575757;
    /** The Constant GRAY35. */
    private static final int GRAY35 = 0x595959;
    /** The Constant GRAY36. */
    private static final int GRAY36 = 0x5C5C5C;
    /** The Constant GRAY37. */
    private static final int GRAY37 = 0x5E5E5E;
    /** The Constant GRAY38. */
    private static final int GRAY38 = 0x616161;
    /** The Constant GRAY4. */
    private static final int GRAY4 = 0x0A0A0A;
    /** The Constant GRAY40. */
    private static final int GRAY40 = 0x666666;
    /** The Constant GRAY41. */
    private static final int GRAY41 = 0x6B6B6B;
    /** The Constant GRAY42. */
    private static final int GRAY42 = 0x6E6E6E;
    /** The Constant GRAY43. */
    private static final int GRAY43 = 0x707070;
    /** The Constant GRAY44. */
    private static final int GRAY44 = 0x737373;
    /** The Constant GRAY45. */
    private static final int GRAY45 = 0x757575;
    /** The Constant GRAY46. */
    private static final int GRAY46 = 0x787878;
    /** The Constant GRAY47. */
    private static final int GRAY47 = 0x7A7A7A;
    /** The Constant GRAY48. */
    private static final int GRAY48 = 0x7D7D7D;
    /** The Constant GRAY49. */
    private static final int GRAY49 = 0x7F7F7F;
    /** The Constant GRAY5. */
    private static final int GRAY5 = 0x0D0D0D;
    /** The Constant GRAY50. */
    private static final int GRAY50 = 0x828282;
    /** The Constant GRAY51. */
    private static final int GRAY51 = 0x858585;
    /** The Constant GRAY52. */
    private static final int GRAY52 = 0x878787;
    /** The Constant GRAY53. */
    private static final int GRAY53 = 0x8A8A8A;
    /** The Constant GRAY54. */
    private static final int GRAY54 = 0x8C8C8C;
    /** The Constant GRAY55. */
    private static final int GRAY55 = 0x8F8F8F;
    /** The Constant GRAY56. */
    private static final int GRAY56 = 0x919191;
    /** The Constant GRAY57. */
    private static final int GRAY57 = 0x949494;
    /** The Constant GRAY58. */
    private static final int GRAY58 = 0x969696;
    /** The Constant GRAY59. */
    private static final int GRAY59 = 0x999999;
    /** The Constant GRAY6. */
    private static final int GRAY6 = 0x0F0F0F;
    /** The Constant GRAY60. */
    private static final int GRAY60 = 0x9C9C9C;
    /** The Constant GRAY61. */
    private static final int GRAY61 = 0x9E9E9E;
    /** The Constant GRAY62. */
    private static final int GRAY62 = 0xA1A1A1;
    /** The Constant GRAY63. */
    private static final int GRAY63 = 0xA3A3A3;
    /** The Constant GRAY64. */
    private static final int GRAY64 = 0xA6A6A6;
    /** The Constant GRAY65. */
    private static final int GRAY65 = 0xA8A8A8;
    /** The Constant GRAY66. */
    private static final int GRAY66 = 0xABABAB;
    /** The Constant GRAY67. */
    private static final int GRAY67 = 0xADADAD;
    /** The Constant GRAY68. */
    private static final int GRAY68 = 0xB0B0B0;
    /** The Constant GRAY69. */
    private static final int GRAY69 = 0xB3B3B3;
    /** The Constant GRAY7. */
    private static final int GRAY7 = 0x121212;
    /** The Constant GRAY70. */
    private static final int GRAY70 = 0xB5B5B5;
    /** The Constant GRAY71. */
    private static final int GRAY71 = 0xB8B8B8;
    /** The Constant GRAY72. */
    private static final int GRAY72 = 0xBABABA;
    /** The Constant GRAY73. */
    private static final int GRAY73 = 0xBDBDBD;
    /** The Constant GRAY74. */
    private static final int GRAY74 = 0xBFBFBF;
    /** The Constant GRAY75. */
    private static final int GRAY75 = 0xC2C2C2;
    /** The Constant GRAY76. */
    private static final int GRAY76 = 0xC4C4C4;
    /** The Constant GRAY77. */
    private static final int GRAY77 = 0xC7C7C7;
    /** The Constant GRAY78. */
    private static final int GRAY78 = 0xC9C9C9;
    /** The Constant GRAY79. */
    private static final int GRAY79 = 0xCCCCCC;
    /** The Constant GRAY8. */
    private static final int GRAY8 = 0x141414;
    /** The Constant GRAY80. */
    private static final int GRAY80 = 0xCFCFCF;
    /** The Constant GRAY81. */
    private static final int GRAY81 = 0xD1D1D1;
    /** The Constant GRAY82. */
    private static final int GRAY82 = 0xD4D4D4;
    /** The Constant GRAY83. */
    private static final int GRAY83 = 0xD6D6D6;
    /** The Constant GRAY84. */
    private static final int GRAY84 = 0xD9D9D9;
    /** The Constant GRAY85. */
    private static final int GRAY85 = 0xDBDBDB;
    /** The Constant GRAY86. */
    private static final int GRAY86 = 0xDEDEDE;
    /** The Constant GRAY87. */
    private static final int GRAY87 = 0xE0E0E0;
    /** The Constant GRAY88. */
    private static final int GRAY88 = 0xE3E3E3;
    /** The Constant GRAY89. */
    private static final int GRAY89 = 0xE5E5E5;
    /** The Constant GRAY9. */
    private static final int GRAY9 = 0x171717;
    /** The Constant GRAY90. */
    private static final int GRAY90 = 0xE8E8E8;
    /** The Constant GRAY91. */
    private static final int GRAY91 = 0xEBEBEB;
    /** The Constant GRAY92. */
    private static final int GRAY92 = 0xEDEDED;
    /** The Constant GRAY93. */
    private static final int GRAY93 = 0xF0F0F0;
    /** The Constant GRAY94. */
    private static final int GRAY94 = 0xF2F2F2;
    /** The Constant GRAY95. */
    private static final int GRAY95 = 0xF7F7F7;
    /** The Constant GRAY96. */
    private static final int GRAY96 = 0xFAFAFA;
    /** The Constant GRAY97. */
    private static final int GRAY97 = 0xFCFCFC;
    /** The Constant GREEN. */
    private static final int GREEN = 0x00FF00;
    /** The Constant GREEN1. */
    private static final int GREEN1 = 0x00EE00;
    /** The Constant GREEN2. */
    private static final int GREEN2 = 0x00CD00;
    /** The Constant GREEN3. */
    private static final int GREEN3 = 0x008B00;
    /** The Constant GREENYELLOW. */
    private static final int GREENYELLOW = 0xADFF2F;
    /** The Constant HONEYDEW. */
    private static final int HONEYDEW = 0xF0FFF0;
    /** The Constant HONEYDEW1. */
    private static final int HONEYDEW1 = 0xE0EEE0;
    /** The Constant HONEYDEW2. */
    private static final int HONEYDEW2 = 0xC1CDC1;
    /** The Constant HONEYDEW3. */
    private static final int HONEYDEW3 = 0x838B83;
    /** The Constant HOTPINK. */
    private static final int HOTPINK = 0xFF69B4;
    /** The Constant HOTPINK1. */
    private static final int HOTPINK1 = 0xFF6EB4;
    /** The Constant HOTPINK2. */
    private static final int HOTPINK2 = 0xEE6AA7;
    /** The Constant HOTPINK3. */
    private static final int HOTPINK3 = 0xCD6090;
    /** The Constant HOTPINK4. */
    private static final int HOTPINK4 = 0x8B3A62;
    /** The Constant INDIANRED. */
    private static final int INDIANRED = 0xCD5C5C;
    /** The Constant INDIANRED1. */
    private static final int INDIANRED1 = 0xFF6A6A;
    /** The Constant INDIANRED2. */
    private static final int INDIANRED2 = 0xEE6363;
    /** The Constant INDIANRED3. */
    private static final int INDIANRED3 = 0xCD5555;
    /** The Constant INDIANRED4. */
    private static final int INDIANRED4 = 0x8B3A3A;
    /** The Constant INDIGO. */
    private static final int INDIGO = 0x4B0082;
    /** The Constant IVORY. */
    private static final int IVORY = 0xFFFFF0;
    /** The Constant IVORY1. */
    private static final int IVORY1 = 0xEEEEE0;
    /** The Constant IVORY2. */
    private static final int IVORY2 = 0xCDCDC1;
    /** The Constant IVORY3. */
    private static final int IVORY3 = 0x8B8B83;
    /** The Constant KHAKI. */
    private static final int KHAKI = 0xF0E68C;
    /** The Constant KHAKI1. */
    private static final int KHAKI1 = 0xFFF68F;
    /** The Constant KHAKI2. */
    private static final int KHAKI2 = 0xEEE685;
    /** The Constant KHAKI3. */
    private static final int KHAKI3 = 0xCDC673;
    /** The Constant KHAKI4. */
    private static final int KHAKI4 = 0x8B864E;
    /** The Constant LAVENDER. */
    private static final int LAVENDER = 0xE6E6FA;
    /** The Constant LAVENDERBLUSH. */
    private static final int LAVENDERBLUSH = 0xFFF0F5;
    /** The Constant LAVENDERBLUSH1. */
    private static final int LAVENDERBLUSH1 = 0xEEE0E5;
    /** The Constant LAVENDERBLUSH2. */
    private static final int LAVENDERBLUSH2 = 0xCDC1C5;
    /** The Constant LAVENDERBLUSH3. */
    private static final int LAVENDERBLUSH3 = 0x8B8386;
    /** The Constant LAWNGREEN. */
    private static final int LAWNGREEN = 0x7CFC00;
    /** The Constant LEMONCHIFFON. */
    private static final int LEMONCHIFFON = 0xFFFACD;
    /** The Constant LEMONCHIFFON1. */
    private static final int LEMONCHIFFON1 = 0xEEE9BF;
    /** The Constant LEMONCHIFFON2. */
    private static final int LEMONCHIFFON2 = 0xCDC9A5;
    /** The Constant LEMONCHIFFON3. */
    private static final int LEMONCHIFFON3 = 0x8B8970;
    /** The Constant LIGHTBLUE. */
    private static final int LIGHTBLUE = 0xADD8E6;
    /** The Constant LIGHTBLUE1. */
    private static final int LIGHTBLUE1 = 0xBFEFFF;
    /** The Constant LIGHTBLUE2. */
    private static final int LIGHTBLUE2 = 0xB2DFEE;
    /** The Constant LIGHTBLUE3. */
    private static final int LIGHTBLUE3 = 0x9AC0CD;
    /** The Constant LIGHTBLUE4. */
    private static final int LIGHTBLUE4 = 0x68838B;
    /** The Constant LIGHTCORAL. */
    private static final int LIGHTCORAL = 0xF08080;
    /** The Constant LIGHTCYAN. */
    private static final int LIGHTCYAN = 0xE0FFFF;
    /** The Constant LIGHTCYAN1. */
    private static final int LIGHTCYAN1 = 0xD1EEEE;
    /** The Constant LIGHTCYAN2. */
    private static final int LIGHTCYAN2 = 0xB4CDCD;
    /** The Constant LIGHTCYAN3. */
    private static final int LIGHTCYAN3 = 0x7A8B8B;
    /** The Constant LIGHTGOLDENROD. */
    private static final int LIGHTGOLDENROD = 0xEEDD82;
    /** The Constant LIGHTGOLDENROD1. */
    private static final int LIGHTGOLDENROD1 = 0xFFEC8B;
    /** The Constant LIGHTGOLDENROD2. */
    private static final int LIGHTGOLDENROD2 = 0xEEDC82;
    /** The Constant LIGHTGOLDENROD3. */
    private static final int LIGHTGOLDENROD3 = 0xCDBE70;
    /** The Constant LIGHTGOLDENROD4. */
    private static final int LIGHTGOLDENROD4 = 0x8B814C;
    /** The Constant LIGHTGOLDENRODYELLOW. */
    private static final int LIGHTGOLDENRODYELLOW = 0xFAFAD2;
    /** The Constant LIGHTGRAY. */
    private static final int LIGHTGRAY = 0xD3D3D3;
    /** The Constant LIGHTGREEN. */
    private static final int LIGHTGREEN = 0x90EE90;
    /** The Constant LIGHTPINK. */
    private static final int LIGHTPINK = 0xFFB6C1;
    /** The Constant LIGHTPINK1. */
    private static final int LIGHTPINK1 = 0xFFAEB9;
    /** The Constant LIGHTPINK2. */
    private static final int LIGHTPINK2 = 0xEEA2AD;
    /** The Constant LIGHTPINK3. */
    private static final int LIGHTPINK3 = 0xCD8C95;
    /** The Constant LIGHTPINK4. */
    private static final int LIGHTPINK4 = 0x8B5F65;
    /** The Constant LIGHTSALMON. */
    private static final int LIGHTSALMON = 0xFFA07A;
    /** The Constant LIGHTSALMON1. */
    private static final int LIGHTSALMON1 = 0xEE9572;
    /** The Constant LIGHTSALMON2. */
    private static final int LIGHTSALMON2 = 0xCD8162;
    /** The Constant LIGHTSALMON3. */
    private static final int LIGHTSALMON3 = 0x8B5742;
    /** The Constant LIGHTSEAGREEN. */
    private static final int LIGHTSEAGREEN = 0x20B2AA;
    /** The Constant LIGHTSKYBLUE. */
    private static final int LIGHTSKYBLUE = 0x87CEFA;
    /** The Constant LIGHTSKYBLUE1. */
    private static final int LIGHTSKYBLUE1 = 0xB0E2FF;
    /** The Constant LIGHTSKYBLUE2. */
    private static final int LIGHTSKYBLUE2 = 0xA4D3EE;
    /** The Constant LIGHTSKYBLUE3. */
    private static final int LIGHTSKYBLUE3 = 0x8DB6CD;
    /** The Constant LIGHTSKYBLUE4. */
    private static final int LIGHTSKYBLUE4 = 0x607B8B;
    /** The Constant LIGHTSLATEBLUE. */
    private static final int LIGHTSLATEBLUE = 0x8470FF;
    /** The Constant LIGHTSLATEGRAY. */
    private static final int LIGHTSLATEGRAY = 0x778899;
    /** The Constant LIGHTSTEELBLUE. */
    private static final int LIGHTSTEELBLUE = 0xB0C4DE;
    /** The Constant LIGHTSTEELBLUE1. */
    private static final int LIGHTSTEELBLUE1 = 0xCAE1FF;
    /** The Constant LIGHTSTEELBLUE2. */
    private static final int LIGHTSTEELBLUE2 = 0xBCD2EE;
    /** The Constant LIGHTSTEELBLUE3. */
    private static final int LIGHTSTEELBLUE3 = 0xA2B5CD;
    /** The Constant LIGHTSTEELBLUE4. */
    private static final int LIGHTSTEELBLUE4 = 0x6E7B8B;
    /** The Constant LIGHTYELLOW. */
    private static final int LIGHTYELLOW = 0xFFFFE0;
    /** The Constant LIGHTYELLOW1. */
    private static final int LIGHTYELLOW1 = 0xEEEED1;
    /** The Constant LIGHTYELLOW2. */
    private static final int LIGHTYELLOW2 = 0xCDCDB4;
    /** The Constant LIGHTYELLOW3. */
    private static final int LIGHTYELLOW3 = 0x8B8B7A;
    /** The Constant LIMEGREEN. */
    private static final int LIMEGREEN = 0x32CD32;
    /** The Constant LINEN. */
    private static final int LINEN = 0xFAF0E6;
    /** The Constant MAGENTA. */
    private static final int MAGENTA = 0xFF00FF;
    /** The Constant MAGENTA1. */
    private static final int MAGENTA1 = 0xEE00EE;
    /** The Constant MAGENTA2. */
    private static final int MAGENTA2 = 0xCD00CD;
    /** The Constant MAROON. */
    private static final int MAROON = 0xB03060;
    /** The Constant MAROON1. */
    private static final int MAROON1 = 0xFF34B3;
    /** The Constant MAROON2. */
    private static final int MAROON2 = 0xEE30A7;
    /** The Constant MAROON3. */
    private static final int MAROON3 = 0xCD2990;
    /** The Constant MAROON4. */
    private static final int MAROON4 = 0x8B1C62;
    /** The Constant MEDIUMAQUAMARINE. */
    private static final int MEDIUMAQUAMARINE = 0x66CDAA;
    /** The Constant MEDIUMBLUE. */
    private static final int MEDIUMBLUE = 0x0000CD;
    /** The Constant MEDIUMFORESTGREEN. */
    private static final int MEDIUMFORESTGREEN = 0x32814B;
    /** The Constant MEDIUMGOLDENROD. */
    private static final int MEDIUMGOLDENROD = 0xD1C166;
    /** The Constant MEDIUMORCHID. */
    private static final int MEDIUMORCHID = 0xBA55D3;
    /** The Constant MEDIUMORCHID1. */
    private static final int MEDIUMORCHID1 = 0xE066FF;
    /** The Constant MEDIUMORCHID2. */
    private static final int MEDIUMORCHID2 = 0xD15FEE;
    /** The Constant MEDIUMORCHID3. */
    private static final int MEDIUMORCHID3 = 0xB452CD;
    /** The Constant MEDIUMORCHID4. */
    private static final int MEDIUMORCHID4 = 0x7A378B;
    /** The Constant MEDIUMPURPLE. */
    private static final int MEDIUMPURPLE = 0x9370DB;
    /** The Constant MEDIUMPURPLE1. */
    private static final int MEDIUMPURPLE1 = 0xAB82FF;
    /** The Constant MEDIUMPURPLE2. */
    private static final int MEDIUMPURPLE2 = 0x9F79EE;
    /** The Constant MEDIUMPURPLE3. */
    private static final int MEDIUMPURPLE3 = 0x8968CD;
    /** The Constant MEDIUMPURPLE4. */
    private static final int MEDIUMPURPLE4 = 0x5D478B;
    /** The Constant MEDIUMSEAGREEN. */
    private static final int MEDIUMSEAGREEN = 0x3CB371;
    /** The Constant MEDIUMSLATEBLUE. */
    private static final int MEDIUMSLATEBLUE = 0x7B68EE;
    /** The Constant MEDIUMSPRINGGREEN. */
    private static final int MEDIUMSPRINGGREEN = 0x00FA9A;
    /** The Constant MEDIUMTURQUOISE. */
    private static final int MEDIUMTURQUOISE = 0x48D1CC;
    /** The Constant MEDIUMVIOLETRED. */
    private static final int MEDIUMVIOLETRED = 0xC71585;
    /** The Constant MIDNIGHTBLUE. */
    private static final int MIDNIGHTBLUE = 0x191970;
    /** The Constant MINTCREAM. */
    private static final int MINTCREAM = 0xF5FFFA;
    /** The Constant MISTYROSE. */
    private static final int MISTYROSE = 0xFFE4E1;
    /** The Constant MISTYROSE1. */
    private static final int MISTYROSE1 = 0xEED5D2;
    /** The Constant MISTYROSE2. */
    private static final int MISTYROSE2 = 0xCDB7B5;
    /** The Constant MISTYROSE3. */
    private static final int MISTYROSE3 = 0x8B7D7B;
    /** The Constant MOCCASIN. */
    private static final int MOCCASIN = 0xFFE4B5;
    /** The Constant NAVAJOWHITE. */
    private static final int NAVAJOWHITE = 0xFFDEAD;
    /** The Constant NAVAJOWHITE1. */
    private static final int NAVAJOWHITE1 = 0xEECFA1;
    /** The Constant NAVAJOWHITE2. */
    private static final int NAVAJOWHITE2 = 0xCDB38B;
    /** The Constant NAVAJOWHITE3. */
    private static final int NAVAJOWHITE3 = 0x8B795E;
    /** The Constant NAVYBLUE. */
    private static final int NAVYBLUE = 0x000080;
    /** The Constant OLDLACE. */
    private static final int OLDLACE = 0xFDF5E6;
    /** The Constant OLIVE. */
    private static final int OLIVE = 0x808000;
    /** The Constant OLIVEDRAB. */
    private static final int OLIVEDRAB = 0x6B8E23;
    /** The Constant OLIVEDRAB1. */
    private static final int OLIVEDRAB1 = 0xC0FF3E;
    /** The Constant OLIVEDRAB2. */
    private static final int OLIVEDRAB2 = 0xB3EE3A;
    /** The Constant OLIVEDRAB3. */
    private static final int OLIVEDRAB3 = 0x698B22;
    /** The Constant ORANGE. */
    private static final int ORANGE = 0xFFA500;
    /** The Constant ORANGE1. */
    private static final int ORANGE1 = 0xEE9A00;
    /** The Constant ORANGE2. */
    private static final int ORANGE2 = 0xCD8500;
    /** The Constant ORANGE3. */
    private static final int ORANGE3 = 0x8B5A00;
    /** The Constant ORANGERED. */
    private static final int ORANGERED = 0xFF4500;
    /** The Constant ORANGERED1. */
    private static final int ORANGERED1 = 0xEE4000;
    /** The Constant ORANGERED2. */
    private static final int ORANGERED2 = 0xCD3700;
    /** The Constant ORANGERED3. */
    private static final int ORANGERED3 = 0x8B2500;
    /** The Constant ORCHID. */
    private static final int ORCHID = 0xDA70D6;
    /** The Constant ORCHID1. */
    private static final int ORCHID1 = 0xFF83FA;
    /** The Constant ORCHID2. */
    private static final int ORCHID2 = 0xEE7AE9;
    /** The Constant ORCHID3. */
    private static final int ORCHID3 = 0xCD69C9;
    /** The Constant ORCHID4. */
    private static final int ORCHID4 = 0x8B4789;
    /** The Constant PALEGOLDENROD. */
    private static final int PALEGOLDENROD = 0xEEE8AA;
    /** The Constant PALEGREEN. */
    private static final int PALEGREEN = 0x98FB98;
    /** The Constant PALEGREEN1. */
    private static final int PALEGREEN1 = 0x9AFF9A;
    /** The Constant PALEGREEN2. */
    private static final int PALEGREEN2 = 0x7CCD7C;
    /** The Constant PALEGREEN3. */
    private static final int PALEGREEN3 = 0x548B54;
    /** The Constant PALETURQUOISE. */
    private static final int PALETURQUOISE = 0xAFEEEE;
    /** The Constant PALETURQUOISE1. */
    private static final int PALETURQUOISE1 = 0xBBFFFF;
    /** The Constant PALETURQUOISE2. */
    private static final int PALETURQUOISE2 = 0xAEEEEE;
    /** The Constant PALETURQUOISE3. */
    private static final int PALETURQUOISE3 = 0x96CDCD;
    /** The Constant PALETURQUOISE4. */
    private static final int PALETURQUOISE4 = 0x668B8B;
    /** The Constant PALEVIOLETRED. */
    private static final int PALEVIOLETRED = 0xDB7093;
    /** The Constant PALEVIOLETRED1. */
    private static final int PALEVIOLETRED1 = 0xFF82AB;
    /** The Constant PALEVIOLETRED2. */
    private static final int PALEVIOLETRED2 = 0xEE799F;
    /** The Constant PALEVIOLETRED3. */
    private static final int PALEVIOLETRED3 = 0xCD6889;
    /** The Constant PALEVIOLETRED4. */
    private static final int PALEVIOLETRED4 = 0x8B475D;
    /** The Constant PAPAYAWHIP. */
    private static final int PAPAYAWHIP = 0xFFEFD5;
    /** The Constant PEACHPUFF. */
    private static final int PEACHPUFF = 0xFFDAB9;
    /** The Constant PEACHPUFF1. */
    private static final int PEACHPUFF1 = 0xEECBAD;
    /** The Constant PEACHPUFF2. */
    private static final int PEACHPUFF2 = 0xCDAF95;
    /** The Constant PEACHPUFF3. */
    private static final int PEACHPUFF3 = 0x8B7765;
    /** The Constant PINK. */
    private static final int PINK = 0xFFC0CB;
    /** The Constant PINK1. */
    private static final int PINK1 = 0xFFB5C5;
    /** The Constant PINK2. */
    private static final int PINK2 = 0xEEA9B8;
    /** The Constant PINK3. */
    private static final int PINK3 = 0xCD919E;
    /** The Constant PINK4. */
    private static final int PINK4 = 0x8B636C;
    /** The Constant PLUM. */
    private static final int PLUM = 0xDDA0DD;
    /** The Constant PLUM1. */
    private static final int PLUM1 = 0xFFBBFF;
    /** The Constant PLUM2. */
    private static final int PLUM2 = 0xEEAEEE;
    /** The Constant PLUM3. */
    private static final int PLUM3 = 0xCD96CD;
    /** The Constant PLUM4. */
    private static final int PLUM4 = 0x8B668B;
    /** The Constant POWDERBLUE. */
    private static final int POWDERBLUE = 0xB0E0E6;
    /** The Constant PURPLE. */
    private static final int PURPLE = 0xA020F0;
    /** The Constant PURPLE1. */
    private static final int PURPLE1 = 0x9B30FF;
    /** The Constant PURPLE2. */
    private static final int PURPLE2 = 0x912CEE;
    /** The Constant PURPLE3. */
    private static final int PURPLE3 = 0x7D26CD;
    /** The Constant PURPLE4. */
    private static final int PURPLE4 = 0x551A8B;
    /** The Constant RED. */
    private static final int RED = 0xFF0000;
    /** The Constant RED1. */
    private static final int RED1 = 0xEE0000;
    /** The Constant RED2. */
    private static final int RED2 = 0xCD0000;
    /** The Constant ROSYBROWN. */
    private static final int ROSYBROWN = 0xBC8F8F;
    /** The Constant ROSYBROWN1. */
    private static final int ROSYBROWN1 = 0xFFC1C1;
    /** The Constant ROSYBROWN2. */
    private static final int ROSYBROWN2 = 0xEEB4B4;
    /** The Constant ROSYBROWN3. */
    private static final int ROSYBROWN3 = 0xCD9B9B;
    /** The Constant ROSYBROWN4. */
    private static final int ROSYBROWN4 = 0x8B6969;
    /** The Constant ROYALBLUE. */
    private static final int ROYALBLUE = 0x4169E1;
    /** The Constant ROYALBLUE1. */
    private static final int ROYALBLUE1 = 0x4876FF;
    /** The Constant ROYALBLUE2. */
    private static final int ROYALBLUE2 = 0x436EEE;
    /** The Constant ROYALBLUE3. */
    private static final int ROYALBLUE3 = 0x3A5FCD;
    /** The Constant ROYALBLUE4. */
    private static final int ROYALBLUE4 = 0x27408B;
    /** The Constant SADDLEBROWN. */
    private static final int SADDLEBROWN = 0x8B4513;
    /** The Constant SALMON. */
    private static final int SALMON = 0xFA8072;
    /** The Constant SALMON1. */
    private static final int SALMON1 = 0xFF8C69;
    /** The Constant SALMON2. */
    private static final int SALMON2 = 0xEE8262;
    /** The Constant SALMON3. */
    private static final int SALMON3 = 0xCD7054;
    /** The Constant SALMON4. */
    private static final int SALMON4 = 0x8B4C39;
    /** The Constant SANDYBROWN. */
    private static final int SANDYBROWN = 0xF4A460;
    /** The Constant SEAGREEN. */
    private static final int SEAGREEN = 0x2E8B57;
    /** The Constant SEAGREEN1. */
    private static final int SEAGREEN1 = 0x54FF9F;
    /** The Constant SEAGREEN2. */
    private static final int SEAGREEN2 = 0x4EEE94;
    /** The Constant SEAGREEN3. */
    private static final int SEAGREEN3 = 0x43CD80;
    /** The Constant SEASHELL. */
    private static final int SEASHELL = 0xFFF5EE;
    /** The Constant SEASHELL1. */
    private static final int SEASHELL1 = 0xEEE5DE;
    /** The Constant SEASHELL2. */
    private static final int SEASHELL2 = 0xCDC5BF;
    /** The Constant SEASHELL3. */
    private static final int SEASHELL3 = 0x8B8682;
    /** The Constant SIENNA. */
    private static final int SIENNA = 0xA0522D;
    /** The Constant SIENNA1. */
    private static final int SIENNA1 = 0xFF8247;
    /** The Constant SIENNA2. */
    private static final int SIENNA2 = 0xEE7942;
    /** The Constant SIENNA3. */
    private static final int SIENNA3 = 0xCD6839;
    /** The Constant SIENNA4. */
    private static final int SIENNA4 = 0x8B4726;
    /** The Constant SILVER. */
    private static final int SILVER = 0xC0C0C0;
    /** The Constant SKYBLUE. */
    private static final int SKYBLUE = 0x87CEEB;
    /** The Constant SKYBLUE1. */
    private static final int SKYBLUE1 = 0x87CEFF;
    /** The Constant SKYBLUE2. */
    private static final int SKYBLUE2 = 0x7EC0EE;
    /** The Constant SKYBLUE3. */
    private static final int SKYBLUE3 = 0x6CA6CD;
    /** The Constant SKYBLUE4. */
    private static final int SKYBLUE4 = 0x4A708B;
    /** The Constant SLATEBLUE. */
    private static final int SLATEBLUE = 0x6A5ACD;
    /** The Constant SLATEBLUE1. */
    private static final int SLATEBLUE1 = 0x836FFF;
    /** The Constant SLATEBLUE2. */
    private static final int SLATEBLUE2 = 0x7A67EE;
    /** The Constant SLATEBLUE3. */
    private static final int SLATEBLUE3 = 0x6959CD;
    /** The Constant SLATEBLUE4. */
    private static final int SLATEBLUE4 = 0x473C8B;
    /** The Constant SLATEGRAY. */
    private static final int SLATEGRAY = 0x708090;
    /** The Constant SLATEGRAY1. */
    private static final int SLATEGRAY1 = 0xC6E2FF;
    /** The Constant SLATEGRAY2. */
    private static final int SLATEGRAY2 = 0xB9D3EE;
    /** The Constant SLATEGRAY3. */
    private static final int SLATEGRAY3 = 0x9FB6CD;
    /** The Constant SLATEGRAY4. */
    private static final int SLATEGRAY4 = 0x6C7B8B;
    /** The Constant SNOW. */
    private static final int SNOW = 0xFFFAFA;
    /** The Constant SNOW1. */
    private static final int SNOW1 = 0xEEE9E9;
    /** The Constant SNOW2. */
    private static final int SNOW2 = 0xCDC9C9;
    /** The Constant SNOW3. */
    private static final int SNOW3 = 0x8B8989;
    /** The Constant SPRINGGREEN. */
    private static final int SPRINGGREEN = 0x00FF7F;
    /** The Constant SPRINGGREEN1. */
    private static final int SPRINGGREEN1 = 0x00EE76;
    /** The Constant SPRINGGREEN2. */
    private static final int SPRINGGREEN2 = 0x00CD66;
    /** The Constant SPRINGGREEN3. */
    private static final int SPRINGGREEN3 = 0x008B45;
    /** The Constant STEELBLUE. */
    private static final int STEELBLUE = 0x4682B4;
    /** The Constant STEELBLUE1. */
    private static final int STEELBLUE1 = 0x63B8FF;
    /** The Constant STEELBLUE2. */
    private static final int STEELBLUE2 = 0x5CACEE;
    /** The Constant STEELBLUE3. */
    private static final int STEELBLUE3 = 0x4F94CD;
    /** The Constant STEELBLUE4. */
    private static final int STEELBLUE4 = 0x36648B;
    /** The Constant TAN. */
    private static final int TAN = 0xD2B48C;
    /** The Constant TAN1. */
    private static final int TAN1 = 0xFFA54F;
    /** The Constant TAN2. */
    private static final int TAN2 = 0xEE9A49;
    /** The Constant TAN3. */
    private static final int TAN3 = 0xCD853F;
    /** The Constant TAN4. */
    private static final int TAN4 = 0x8B5A2B;
    /** The Constant TEAL. */
    private static final int TEAL = 0x008080;
    /** The Constant THISTLE. */
    private static final int THISTLE = 0xD8BFD8;
    /** The Constant THISTLE1. */
    private static final int THISTLE1 = 0xFFE1FF;
    /** The Constant THISTLE2. */
    private static final int THISTLE2 = 0xEED2EE;
    /** The Constant THISTLE3. */
    private static final int THISTLE3 = 0xCDB5CD;
    /** The Constant THISTLE4. */
    private static final int THISTLE4 = 0x8B7B8B;
    /** The Constant TOMATO. */
    private static final int TOMATO = 0xFF6347;
    /** The Constant TOMATO1. */
    private static final int TOMATO1 = 0xEE5C42;
    /** The Constant TOMATO2. */
    private static final int TOMATO2 = 0xCD4F39;
    /** The Constant TOMATO3. */
    private static final int TOMATO3 = 0x8B3626;
    /** The Constant TURQUOISE. */
    private static final int TURQUOISE = 0x40E0D0;
    /** The Constant TURQUOISE1. */
    private static final int TURQUOISE1 = 0x00F5FF;
    /** The Constant TURQUOISE2. */
    private static final int TURQUOISE2 = 0x00E5EE;
    /** The Constant TURQUOISE3. */
    private static final int TURQUOISE3 = 0x00C5CD;
    /** The Constant TURQUOISE4. */
    private static final int TURQUOISE4 = 0x00868B;
    /** The Constant VIOLET. */
    private static final int VIOLET = 0xEE82EE;
    /** The Constant VIOLETRED. */
    private static final int VIOLETRED = 0xD02090;
    /** The Constant VIOLETRED1. */
    private static final int VIOLETRED1 = 0xFF3E96;
    /** The Constant VIOLETRED2. */
    private static final int VIOLETRED2 = 0xEE3A8C;
    /** The Constant VIOLETRED3. */
    private static final int VIOLETRED3 = 0xCD3278;
    /** The Constant VIOLETRED4. */
    private static final int VIOLETRED4 = 0x8B2252;
    /** The Constant WHEAT. */
    private static final int WHEAT = 0xF5DEB3;
    /** The Constant WHEAT1. */
    private static final int WHEAT1 = 0xFFE7BA;
    /** The Constant WHEAT2. */
    private static final int WHEAT2 = 0xEED8AE;
    /** The Constant WHEAT3. */
    private static final int WHEAT3 = 0xCDBA96;
    /** The Constant WHEAT4. */
    private static final int WHEAT4 = 0x8B7E66;
    /** The Constant WHITE. */
    private static final int WHITE = 0xFFFFFF;
    /** The Constant WHITESMOKE. */
    private static final int WHITESMOKE = 0xF5F5F5;
    /** The Constant YELLOW. */
    private static final int YELLOW = 0xFFFF00;
    /** The Constant YELLOW1. */
    private static final int YELLOW1 = 0xEEEE00;
    /** The Constant YELLOW2. */
    private static final int YELLOW2 = 0xCDCD00;
    /** The Constant YELLOW3. */
    private static final int YELLOW3 = 0x8B8B00;
    /** The Constant YELLOWGREEN. */
    private static final int YELLOWGREEN = 0x9ACD32;

    public static Map<String, Color> mapColor() {
        Map<String, Color> colorMap = new HashMap<String, Color>();
        colorMap.put("transparent", new Color(0, 0, 0, 0));
        colorMap.put("aliceblue", new Color(ALICEBLUE));
        colorMap.put("antiquewhite", new Color(ANTIQUEWHITE));
        colorMap.put("antiquewhite1", new Color(ANTIQUEWHITE1));
        colorMap.put("antiquewhite2", new Color(ANTIQUEWHITE2));
        colorMap.put("antiquewhite3", new Color(ANTIQUEWHITE3));
        colorMap.put("antiquewhite4", new Color(ANTIQUEWHITE4));
        colorMap.put("aquamarine", new Color(AQUAMARINE));
        colorMap.put("aquamarine1", new Color(AQUAMARINE1));
        colorMap.put("aquamarine2", new Color(AQUAMARINE2));
        colorMap.put("azure", new Color(AZURE));
        colorMap.put("azure1", new Color(AZURE1));
        colorMap.put("azure2", new Color(AZURE2));
        colorMap.put("azure3", new Color(AZURE3));
        colorMap.put("beige", new Color(BEIGE));
        colorMap.put("bisque", new Color(BISQUE));
        colorMap.put("bisque1", new Color(BISQUE1));
        colorMap.put("bisque2", new Color(BISQUE2));
        colorMap.put("bisque3", new Color(BISQUE3));
        colorMap.put("black", new Color(BLACK));
        colorMap.put("blanchedalmond", new Color(BLANCHEDALMOND));
        colorMap.put("blue", new Color(BLUE));
        colorMap.put("blue1", new Color(BLUE1));
        colorMap.put("blueviolet", new Color(BLUEVIOLET));
        colorMap.put("brown", new Color(BROWN));
        colorMap.put("brown1", new Color(BROWN1));
        colorMap.put("brown2", new Color(BROWN2));
        colorMap.put("brown3", new Color(BROWN3));
        colorMap.put("brown4", new Color(BROWN4));
        colorMap.put("burlywood", new Color(BURLYWOOD));
        colorMap.put("burlywood1", new Color(BURLYWOOD1));
        colorMap.put("burlywood2", new Color(BURLYWOOD2));
        colorMap.put("burlywood3", new Color(BURLYWOOD3));
        colorMap.put("burlywood4", new Color(BURLYWOOD4));
        colorMap.put("cadetblue", new Color(CADETBLUE));
        colorMap.put("cadetblue1", new Color(CADETBLUE1));
        colorMap.put("cadetblue2", new Color(CADETBLUE2));
        colorMap.put("cadetblue3", new Color(CADETBLUE3));
        colorMap.put("cadetblue4", new Color(CADETBLUE4));
        colorMap.put("chartreuse", new Color(CHARTREUSE));
        colorMap.put("chartreuse1", new Color(CHARTREUSE1));
        colorMap.put("chartreuse2", new Color(CHARTREUSE2));
        colorMap.put("chartreuse3", new Color(CHARTREUSE3));
        colorMap.put("chocolate", new Color(CHOCOLATE));
        colorMap.put("chocolate1", new Color(CHOCOLATE1));
        colorMap.put("chocolate2", new Color(CHOCOLATE2));
        colorMap.put("chocolate3", new Color(CHOCOLATE3));
        colorMap.put("coral", new Color(CORAL));
        colorMap.put("coral1", new Color(CORAL1));
        colorMap.put("coral2", new Color(CORAL2));
        colorMap.put("coral3", new Color(CORAL3));
        colorMap.put("coral4", new Color(CORAL4));
        colorMap.put("cornflowerblue", new Color(CORNFLOWERBLUE));
        colorMap.put("cornsilk", new Color(CORNSILK));
        colorMap.put("cornsilk1", new Color(CORNSILK1));
        colorMap.put("cornsilk2", new Color(CORNSILK2));
        colorMap.put("cornsilk3", new Color(CORNSILK3));
        colorMap.put("crimson", new Color(CRIMSON));
        colorMap.put("cyan", new Color(CYAN));
        colorMap.put("cyan1", new Color(CYAN1));
        colorMap.put("cyan2", new Color(CYAN2));
        colorMap.put("darkblue", new Color(DARKBLUE));
        colorMap.put("darkcyan", new Color(DARKCYAN));
        colorMap.put("darkgoldenrod", new Color(DARKGOLDENROD));
        colorMap.put("darkgoldenrod1", new Color(DARKGOLDENROD1));
        colorMap.put("darkgoldenrod2", new Color(DARKGOLDENROD2));
        colorMap.put("darkgoldenrod3", new Color(DARKGOLDENROD3));
        colorMap.put("darkgoldenrod4", new Color(DARKGOLDENROD4));
        colorMap.put("darkgray", new Color(DARKGRAY));
        colorMap.put("darkgreen", new Color(DARKGREEN));
        colorMap.put("darkkhaki", new Color(DARKKHAKI));
        colorMap.put("darkmagenta", new Color(DARKMAGENTA));
        colorMap.put("darkolivegreen", new Color(DARKOLIVEGREEN));
        colorMap.put("darkolivegreen1", new Color(DARKOLIVEGREEN1));
        colorMap.put("darkolivegreen2", new Color(DARKOLIVEGREEN2));
        colorMap.put("darkolivegreen3", new Color(DARKOLIVEGREEN3));
        colorMap.put("darkolivegreen4", new Color(DARKOLIVEGREEN4));
        colorMap.put("darkorange", new Color(DARKORANGE));
        colorMap.put("darkorange1", new Color(DARKORANGE1));
        colorMap.put("darkorange2", new Color(DARKORANGE2));
        colorMap.put("darkorange3", new Color(DARKORANGE3));
        colorMap.put("darkorange4", new Color(DARKORANGE4));
        colorMap.put("darkorchid", new Color(DARKORCHID));
        colorMap.put("darkorchid1", new Color(DARKORCHID1));
        colorMap.put("darkorchid2", new Color(DARKORCHID2));
        colorMap.put("darkorchid3", new Color(DARKORCHID3));
        colorMap.put("darkorchid4", new Color(DARKORCHID4));
        colorMap.put("darkred", new Color(DARKRED));
        colorMap.put("darksalmon", new Color(DARKSALMON));
        colorMap.put("darkseagreen", new Color(DARKSEAGREEN));
        colorMap.put("darkseagreen1", new Color(DARKSEAGREEN1));
        colorMap.put("darkseagreen2", new Color(DARKSEAGREEN2));
        colorMap.put("darkseagreen3", new Color(DARKSEAGREEN3));
        colorMap.put("darkseagreen4", new Color(DARKSEAGREEN4));
        colorMap.put("darkslateblue", new Color(DARKSLATEBLUE));
        colorMap.put("darkslategray", new Color(DARKSLATEGRAY));
        colorMap.put("darkslategray1", new Color(DARKSLATEGRAY1));
        colorMap.put("darkslategray2", new Color(DARKSLATEGRAY2));
        colorMap.put("darkslategray3", new Color(DARKSLATEGRAY3));
        colorMap.put("darkslategray4", new Color(DARKSLATEGRAY4));
        colorMap.put("darkturquoise", new Color(DARKTURQUOISE));
        colorMap.put("darkviolet", new Color(DARKVIOLET));
        colorMap.put("deeppink", new Color(DEEPPINK));
        colorMap.put("deeppink1", new Color(DEEPPINK1));
        colorMap.put("deeppink2", new Color(DEEPPINK2));
        colorMap.put("deeppink3", new Color(DEEPPINK3));
        colorMap.put("deepskyblue", new Color(DEEPSKYBLUE));
        colorMap.put("deepskyblue1", new Color(DEEPSKYBLUE1));
        colorMap.put("deepskyblue2", new Color(DEEPSKYBLUE2));
        colorMap.put("deepskyblue3", new Color(DEEPSKYBLUE3));
        colorMap.put("dimgray", new Color(DIMGRAY));
        colorMap.put("dodgerblue", new Color(DODGERBLUE));
        colorMap.put("dodgerblue1", new Color(DODGERBLUE1));
        colorMap.put("dodgerblue2", new Color(DODGERBLUE2));
        colorMap.put("dodgerblue3", new Color(DODGERBLUE3));
        colorMap.put("firebrick", new Color(FIREBRICK));
        colorMap.put("firebrick1", new Color(FIREBRICK1));
        colorMap.put("firebrick2", new Color(FIREBRICK2));
        colorMap.put("firebrick3", new Color(FIREBRICK3));
        colorMap.put("firebrick4", new Color(FIREBRICK4));
        colorMap.put("floralwhite", new Color(FLORALWHITE));
        colorMap.put("forestgreen", new Color(FORESTGREEN));
        colorMap.put("fractal", new Color(FRACTAL));
        colorMap.put("gainsboro", new Color(GAINSBORO));
        colorMap.put("ghostwhite", new Color(GHOSTWHITE));
        colorMap.put("gold", new Color(GOLD));
        colorMap.put("gold1", new Color(GOLD1));
        colorMap.put("gold2", new Color(GOLD2));
        colorMap.put("gold3", new Color(GOLD3));
        colorMap.put("goldenrod", new Color(GOLDENROD));
        colorMap.put("goldenrod1", new Color(GOLDENROD1));
        colorMap.put("goldenrod2", new Color(GOLDENROD2));
        colorMap.put("goldenrod3", new Color(GOLDENROD3));
        colorMap.put("goldenrod4", new Color(GOLDENROD4));
        colorMap.put("gray1", new Color(GRAY1));
        colorMap.put("gray10", new Color(GRAY10));
        colorMap.put("gray11", new Color(GRAY11));
        colorMap.put("gray12", new Color(GRAY12));
        colorMap.put("gray13", new Color(GRAY13));
        colorMap.put("gray14", new Color(GRAY14));
        colorMap.put("gray15", new Color(GRAY15));
        colorMap.put("gray16", new Color(GRAY16));
        colorMap.put("gray17", new Color(GRAY17));
        colorMap.put("gray18", new Color(GRAY18));
        colorMap.put("gray19", new Color(GRAY19));
        colorMap.put("gray2", new Color(GRAY2));
        colorMap.put("gray20", new Color(GRAY20));
        colorMap.put("gray21", new Color(GRAY21));
        colorMap.put("gray22", new Color(GRAY22));
        colorMap.put("gray23", new Color(GRAY23));
        colorMap.put("gray24", new Color(GRAY24));
        colorMap.put("gray25", new Color(GRAY25));
        colorMap.put("gray26", new Color(GRAY26));
        colorMap.put("gray27", new Color(GRAY27));
        colorMap.put("gray28", new Color(GRAY28));
        colorMap.put("gray29", new Color(GRAY29));
        colorMap.put("gray3", new Color(GRAY3));
        colorMap.put("gray30", new Color(GRAY30));
        colorMap.put("gray31", new Color(GRAY31));
        colorMap.put("gray32", new Color(GRAY32));
        colorMap.put("gray33", new Color(GRAY33));
        colorMap.put("gray34", new Color(GRAY34));
        colorMap.put("gray35", new Color(GRAY35));
        colorMap.put("gray36", new Color(GRAY36));
        colorMap.put("gray37", new Color(GRAY37));
        colorMap.put("gray38", new Color(GRAY38));
        colorMap.put("gray", new Color(GRAY));
        colorMap.put("gray4", new Color(GRAY4));
        colorMap.put("gray40", new Color(GRAY40));
        colorMap.put("gray41", new Color(GRAY41));
        colorMap.put("gray42", new Color(GRAY42));
        colorMap.put("gray43", new Color(GRAY43));
        colorMap.put("gray44", new Color(GRAY44));
        colorMap.put("gray45", new Color(GRAY45));
        colorMap.put("gray46", new Color(GRAY46));
        colorMap.put("gray47", new Color(GRAY47));
        colorMap.put("gray48", new Color(GRAY48));
        colorMap.put("gray49", new Color(GRAY49));
        colorMap.put("gray5", new Color(GRAY5));
        colorMap.put("gray50", new Color(GRAY50));
        colorMap.put("gray51", new Color(GRAY51));
        colorMap.put("gray52", new Color(GRAY52));
        colorMap.put("gray53", new Color(GRAY53));
        colorMap.put("gray54", new Color(GRAY54));
        colorMap.put("gray55", new Color(GRAY55));
        colorMap.put("gray56", new Color(GRAY56));
        colorMap.put("gray57", new Color(GRAY57));
        colorMap.put("gray58", new Color(GRAY58));
        colorMap.put("gray59", new Color(GRAY59));
        colorMap.put("gray6", new Color(GRAY6));
        colorMap.put("gray60", new Color(GRAY60));
        colorMap.put("gray61", new Color(GRAY61));
        colorMap.put("gray62", new Color(GRAY62));
        colorMap.put("gray63", new Color(GRAY63));
        colorMap.put("gray64", new Color(GRAY64));
        colorMap.put("gray65", new Color(GRAY65));
        colorMap.put("gray66", new Color(GRAY66));
        colorMap.put("gray67", new Color(GRAY67));
        colorMap.put("gray68", new Color(GRAY68));
        colorMap.put("gray69", new Color(GRAY69));
        colorMap.put("gray7", new Color(GRAY7));
        colorMap.put("gray70", new Color(GRAY70));
        colorMap.put("gray71", new Color(GRAY71));
        colorMap.put("gray72", new Color(GRAY72));
        colorMap.put("gray73", new Color(GRAY73));
        colorMap.put("gray74", new Color(GRAY74));
        colorMap.put("gray75", new Color(GRAY75));
        colorMap.put("gray76", new Color(GRAY76));
        colorMap.put("gray77", new Color(GRAY77));
        colorMap.put("gray78", new Color(GRAY78));
        colorMap.put("gray79", new Color(GRAY79));
        colorMap.put("gray8", new Color(GRAY8));
        colorMap.put("gray80", new Color(GRAY80));
        colorMap.put("gray81", new Color(GRAY81));
        colorMap.put("gray82", new Color(GRAY82));
        colorMap.put("gray83", new Color(GRAY83));
        colorMap.put("gray84", new Color(GRAY84));
        colorMap.put("gray85", new Color(GRAY85));
        colorMap.put("gray86", new Color(GRAY86));
        colorMap.put("gray87", new Color(GRAY87));
        colorMap.put("gray88", new Color(GRAY88));
        colorMap.put("gray89", new Color(GRAY89));
        colorMap.put("gray9", new Color(GRAY9));
        colorMap.put("gray90", new Color(GRAY90));
        colorMap.put("gray91", new Color(GRAY91));
        colorMap.put("gray92", new Color(GRAY92));
        colorMap.put("gray93", new Color(GRAY93));
        colorMap.put("gray94", new Color(GRAY94));
        colorMap.put("gray95", new Color(GRAY95));
        colorMap.put("gray96", new Color(GRAY96));
        colorMap.put("gray97", new Color(GRAY97));
        colorMap.put("green", new Color(GREEN));
        colorMap.put("green1", new Color(GREEN1));
        colorMap.put("green2", new Color(GREEN2));
        colorMap.put("green3", new Color(GREEN3));
        colorMap.put("greenyellow", new Color(GREENYELLOW));
        colorMap.put("honeydew", new Color(HONEYDEW));
        colorMap.put("honeydew1", new Color(HONEYDEW1));
        colorMap.put("honeydew2", new Color(HONEYDEW2));
        colorMap.put("honeydew3", new Color(HONEYDEW3));
        colorMap.put("hotpink", new Color(HOTPINK));
        colorMap.put("hotpink1", new Color(HOTPINK1));
        colorMap.put("hotpink2", new Color(HOTPINK2));
        colorMap.put("hotpink3", new Color(HOTPINK3));
        colorMap.put("hotpink4", new Color(HOTPINK4));
        colorMap.put("indianred", new Color(INDIANRED));
        colorMap.put("indianred1", new Color(INDIANRED1));
        colorMap.put("indianred2", new Color(INDIANRED2));
        colorMap.put("indianred3", new Color(INDIANRED3));
        colorMap.put("indianred4", new Color(INDIANRED4));
        colorMap.put("indigo", new Color(INDIGO));
        colorMap.put("ivory", new Color(IVORY));
        colorMap.put("ivory1", new Color(IVORY1));
        colorMap.put("ivory2", new Color(IVORY2));
        colorMap.put("ivory3", new Color(IVORY3));
        colorMap.put("khaki", new Color(KHAKI));
        colorMap.put("khaki1", new Color(KHAKI1));
        colorMap.put("khaki2", new Color(KHAKI2));
        colorMap.put("khaki3", new Color(KHAKI3));
        colorMap.put("khaki4", new Color(KHAKI4));
        colorMap.put("lavender", new Color(LAVENDER));
        colorMap.put("lavenderblush", new Color(LAVENDERBLUSH));
        colorMap.put("lavenderblush1", new Color(LAVENDERBLUSH1));
        colorMap.put("lavenderblush2", new Color(LAVENDERBLUSH2));
        colorMap.put("lavenderblush3", new Color(LAVENDERBLUSH3));
        colorMap.put("lawngreen", new Color(LAWNGREEN));
        colorMap.put("lemonchiffon", new Color(LEMONCHIFFON));
        colorMap.put("lemonchiffon1", new Color(LEMONCHIFFON1));
        colorMap.put("lemonchiffon2", new Color(LEMONCHIFFON2));
        colorMap.put("lemonchiffon3", new Color(LEMONCHIFFON3));
        colorMap.put("lightblue", new Color(LIGHTBLUE));
        colorMap.put("lightblue1", new Color(LIGHTBLUE1));
        colorMap.put("lightblue2", new Color(LIGHTBLUE2));
        colorMap.put("lightblue3", new Color(LIGHTBLUE3));
        colorMap.put("lightblue4", new Color(LIGHTBLUE4));
        colorMap.put("lightcoral", new Color(LIGHTCORAL));
        colorMap.put("lightcyan", new Color(LIGHTCYAN));
        colorMap.put("lightcyan1", new Color(LIGHTCYAN1));
        colorMap.put("lightcyan2", new Color(LIGHTCYAN2));
        colorMap.put("lightcyan3", new Color(LIGHTCYAN3));
        colorMap.put("lightgoldenrod", new Color(LIGHTGOLDENROD));
        colorMap.put("lightgoldenrod1", new Color(LIGHTGOLDENROD1));
        colorMap.put("lightgoldenrod2", new Color(LIGHTGOLDENROD2));
        colorMap.put("lightgoldenrod3", new Color(LIGHTGOLDENROD3));
        colorMap.put("lightgoldenrod4", new Color(LIGHTGOLDENROD4));
        colorMap.put("lightgoldenrodyellow", new Color(LIGHTGOLDENRODYELLOW));
        colorMap.put("lightgray", new Color(LIGHTGRAY));
        colorMap.put("lightgrey", new Color(LIGHTGRAY));
        colorMap.put("lightgreen", new Color(LIGHTGREEN));
        colorMap.put("lightpink", new Color(LIGHTPINK));
        colorMap.put("lightpink1", new Color(LIGHTPINK1));
        colorMap.put("lightpink2", new Color(LIGHTPINK2));
        colorMap.put("lightpink3", new Color(LIGHTPINK3));
        colorMap.put("lightpink4", new Color(LIGHTPINK4));
        colorMap.put("lightsalmon", new Color(LIGHTSALMON));
        colorMap.put("lightsalmon1", new Color(LIGHTSALMON1));
        colorMap.put("lightsalmon2", new Color(LIGHTSALMON2));
        colorMap.put("lightsalmon3", new Color(LIGHTSALMON3));
        colorMap.put("lightseagreen", new Color(LIGHTSEAGREEN));
        colorMap.put("lightskyblue", new Color(LIGHTSKYBLUE));
        colorMap.put("lightskyblue1", new Color(LIGHTSKYBLUE1));
        colorMap.put("lightskyblue2", new Color(LIGHTSKYBLUE2));
        colorMap.put("lightskyblue3", new Color(LIGHTSKYBLUE3));
        colorMap.put("lightskyblue4", new Color(LIGHTSKYBLUE4));
        colorMap.put("lightslateblue", new Color(LIGHTSLATEBLUE));
        colorMap.put("lightslategray", new Color(LIGHTSLATEGRAY));
        colorMap.put("lightsteelblue", new Color(LIGHTSTEELBLUE));
        colorMap.put("lightsteelblue1", new Color(LIGHTSTEELBLUE1));
        colorMap.put("lightsteelblue2", new Color(LIGHTSTEELBLUE2));
        colorMap.put("lightsteelblue3", new Color(LIGHTSTEELBLUE3));
        colorMap.put("lightsteelblue4", new Color(LIGHTSTEELBLUE4));
        colorMap.put("lightyellow", new Color(LIGHTYELLOW));
        colorMap.put("lightyellow1", new Color(LIGHTYELLOW1));
        colorMap.put("lightyellow2", new Color(LIGHTYELLOW2));
        colorMap.put("lightyellow3", new Color(LIGHTYELLOW3));
        colorMap.put("limegreen", new Color(LIMEGREEN));
        colorMap.put("linen", new Color(LINEN));
        colorMap.put("magenta", new Color(MAGENTA));
        colorMap.put("magenta1", new Color(MAGENTA1));
        colorMap.put("magenta2", new Color(MAGENTA2));
        colorMap.put("maroon", new Color(MAROON));
        colorMap.put("maroon1", new Color(MAROON1));
        colorMap.put("maroon2", new Color(MAROON2));
        colorMap.put("maroon3", new Color(MAROON3));
        colorMap.put("maroon4", new Color(MAROON4));
        colorMap.put("mediumaquamarine", new Color(MEDIUMAQUAMARINE));
        colorMap.put("mediumblue", new Color(MEDIUMBLUE));
        colorMap.put("mediumforestgreen", new Color(MEDIUMFORESTGREEN));
        colorMap.put("mediumgoldenrod", new Color(MEDIUMGOLDENROD));
        colorMap.put("mediumorchid", new Color(MEDIUMORCHID));
        colorMap.put("mediumorchid1", new Color(MEDIUMORCHID1));
        colorMap.put("mediumorchid2", new Color(MEDIUMORCHID2));
        colorMap.put("mediumorchid3", new Color(MEDIUMORCHID3));
        colorMap.put("mediumorchid4", new Color(MEDIUMORCHID4));
        colorMap.put("mediumpurple", new Color(MEDIUMPURPLE));
        colorMap.put("mediumpurple1", new Color(MEDIUMPURPLE1));
        colorMap.put("mediumpurple2", new Color(MEDIUMPURPLE2));
        colorMap.put("mediumpurple3", new Color(MEDIUMPURPLE3));
        colorMap.put("mediumpurple4", new Color(MEDIUMPURPLE4));
        colorMap.put("mediumseagreen", new Color(MEDIUMSEAGREEN));
        colorMap.put("mediumslateblue", new Color(MEDIUMSLATEBLUE));
        colorMap.put("mediumspringgreen", new Color(MEDIUMSPRINGGREEN));
        colorMap.put("mediumturquoisenew", new Color(MEDIUMTURQUOISE));
        colorMap.put("mediumvioletred", new Color(MEDIUMVIOLETRED));
        colorMap.put("midnightblue", new Color(MIDNIGHTBLUE));
        colorMap.put("mintcream", new Color(MINTCREAM));
        colorMap.put("mistyrose", new Color(MISTYROSE));
        colorMap.put("mistyrose1", new Color(MISTYROSE1));
        colorMap.put("mistyrose2", new Color(MISTYROSE2));
        colorMap.put("mistyrose3", new Color(MISTYROSE3));
        colorMap.put("moccasin", new Color(MOCCASIN));
        colorMap.put("navajowhite", new Color(NAVAJOWHITE));
        colorMap.put("navajowhite1", new Color(NAVAJOWHITE1));
        colorMap.put("navajowhite2", new Color(NAVAJOWHITE2));
        colorMap.put("navajowhite3", new Color(NAVAJOWHITE3));
        colorMap.put("navy", new Color(NAVYBLUE));
        colorMap.put("navyblue", new Color(NAVYBLUE));
        colorMap.put("oldlace", new Color(OLDLACE));
        colorMap.put("olive", new Color(OLIVE));
        colorMap.put("olivedrab", new Color(OLIVEDRAB));
        colorMap.put("olivedrab1", new Color(OLIVEDRAB1));
        colorMap.put("olivedrab2", new Color(OLIVEDRAB2));
        colorMap.put("olivedrab3", new Color(OLIVEDRAB3));
        colorMap.put("orange", new Color(ORANGE));
        colorMap.put("orange1", new Color(ORANGE1));
        colorMap.put("orange2", new Color(ORANGE2));
        colorMap.put("orange3", new Color(ORANGE3));
        colorMap.put("orangered", new Color(ORANGERED));
        colorMap.put("orangered1", new Color(ORANGERED1));
        colorMap.put("orangered2", new Color(ORANGERED2));
        colorMap.put("orangered3", new Color(ORANGERED3));
        colorMap.put("orchid", new Color(ORCHID));
        colorMap.put("orchid1", new Color(ORCHID1));
        colorMap.put("orchid2", new Color(ORCHID2));
        colorMap.put("orchid3", new Color(ORCHID3));
        colorMap.put("orchid4", new Color(ORCHID4));
        colorMap.put("palegoldenrod", new Color(PALEGOLDENROD));
        colorMap.put("palegreen", new Color(PALEGREEN));
        colorMap.put("palegreen1", new Color(PALEGREEN1));
        colorMap.put("palegreen2", new Color(PALEGREEN2));
        colorMap.put("palegreen3", new Color(PALEGREEN3));
        colorMap.put("paleturquoise", new Color(PALETURQUOISE));
        colorMap.put("paleturquoise1", new Color(PALETURQUOISE1));
        colorMap.put("paleturquoise2", new Color(PALETURQUOISE2));
        colorMap.put("paleturquoise3", new Color(PALETURQUOISE3));
        colorMap.put("paleturquoise4", new Color(PALETURQUOISE4));
        colorMap.put("palevioletred", new Color(PALEVIOLETRED));
        colorMap.put("palevioletred1", new Color(PALEVIOLETRED1));
        colorMap.put("palevioletred2", new Color(PALEVIOLETRED2));
        colorMap.put("palevioletred3", new Color(PALEVIOLETRED3));
        colorMap.put("palevioletred4", new Color(PALEVIOLETRED4));
        colorMap.put("papayawhip", new Color(PAPAYAWHIP));
        colorMap.put("peachpuff", new Color(PEACHPUFF));
        colorMap.put("peachpuff1", new Color(PEACHPUFF1));
        colorMap.put("peachpuff2", new Color(PEACHPUFF2));
        colorMap.put("peachpuff3", new Color(PEACHPUFF3));
        colorMap.put("pink", new Color(PINK));
        colorMap.put("pink1", new Color(PINK1));
        colorMap.put("pink2", new Color(PINK2));
        colorMap.put("pink3", new Color(PINK3));
        colorMap.put("pink4", new Color(PINK4));
        colorMap.put("plum", new Color(PLUM));
        colorMap.put("plum1", new Color(PLUM1));
        colorMap.put("plum2", new Color(PLUM2));
        colorMap.put("plum3", new Color(PLUM3));
        colorMap.put("plum4", new Color(PLUM4));
        colorMap.put("powderblue", new Color(POWDERBLUE));
        colorMap.put("purple", new Color(PURPLE));
        colorMap.put("purple1", new Color(PURPLE1));
        colorMap.put("purple2", new Color(PURPLE2));
        colorMap.put("purple3", new Color(PURPLE3));
        colorMap.put("purple4", new Color(PURPLE4));
        colorMap.put("red", new Color(RED));
        colorMap.put("red1", new Color(RED1));
        colorMap.put("red2", new Color(RED2));
        colorMap.put("rosybrown", new Color(ROSYBROWN));
        colorMap.put("rosybrown1", new Color(ROSYBROWN1));
        colorMap.put("rosybrown2", new Color(ROSYBROWN2));
        colorMap.put("rosybrown3", new Color(ROSYBROWN3));
        colorMap.put("rosybrown4", new Color(ROSYBROWN4));
        colorMap.put("royalblue", new Color(ROYALBLUE));
        colorMap.put("royalblue1", new Color(ROYALBLUE1));
        colorMap.put("royalblue2", new Color(ROYALBLUE2));
        colorMap.put("royalblue3", new Color(ROYALBLUE3));
        colorMap.put("royalblue4", new Color(ROYALBLUE4));
        colorMap.put("saddlebrown", new Color(SADDLEBROWN));
        colorMap.put("salmon", new Color(SALMON));
        colorMap.put("salmon1", new Color(SALMON1));
        colorMap.put("salmon2", new Color(SALMON2));
        colorMap.put("salmon3", new Color(SALMON3));
        colorMap.put("salmon4", new Color(SALMON4));
        colorMap.put("sandybrown", new Color(SANDYBROWN));
        colorMap.put("seagreen", new Color(SEAGREEN));
        colorMap.put("seagreen1", new Color(SEAGREEN1));
        colorMap.put("seagreen2", new Color(SEAGREEN2));
        colorMap.put("seagreen3", new Color(SEAGREEN3));
        colorMap.put("seashell", new Color(SEASHELL));
        colorMap.put("seashell1", new Color(SEASHELL1));
        colorMap.put("seashell2", new Color(SEASHELL2));
        colorMap.put("seashell3", new Color(SEASHELL3));
        colorMap.put("sienna", new Color(SIENNA));
        colorMap.put("sienna1", new Color(SIENNA1));
        colorMap.put("sienna2", new Color(SIENNA2));
        colorMap.put("sienna3", new Color(SIENNA3));
        colorMap.put("sienna4", new Color(SIENNA4));
        colorMap.put("silver", new Color(SILVER));
        colorMap.put("skyblue", new Color(SKYBLUE));
        colorMap.put("skyblue1", new Color(SKYBLUE1));
        colorMap.put("skyblue2", new Color(SKYBLUE2));
        colorMap.put("skyblue3", new Color(SKYBLUE3));
        colorMap.put("skyblue4", new Color(SKYBLUE4));
        colorMap.put("slateblue", new Color(SLATEBLUE));
        colorMap.put("slateblue1", new Color(SLATEBLUE1));
        colorMap.put("slateblue2", new Color(SLATEBLUE2));
        colorMap.put("slateblue3", new Color(SLATEBLUE3));
        colorMap.put("slateblue4", new Color(SLATEBLUE4));
        colorMap.put("slategray", new Color(SLATEGRAY));
        colorMap.put("slategray1", new Color(SLATEGRAY1));
        colorMap.put("slategray2", new Color(SLATEGRAY2));
        colorMap.put("slategray3", new Color(SLATEGRAY3));
        colorMap.put("slategray4", new Color(SLATEGRAY4));
        colorMap.put("snow", new Color(SNOW));
        colorMap.put("snow1", new Color(SNOW1));
        colorMap.put("snow2", new Color(SNOW2));
        colorMap.put("snow3", new Color(SNOW3));
        colorMap.put("springgreen", new Color(SPRINGGREEN));
        colorMap.put("springgreen1", new Color(SPRINGGREEN1));
        colorMap.put("springgreen2", new Color(SPRINGGREEN2));
        colorMap.put("springgreen3", new Color(SPRINGGREEN3));
        colorMap.put("steelblue", new Color(STEELBLUE));
        colorMap.put("steelblue1", new Color(STEELBLUE1));
        colorMap.put("steelblue2", new Color(STEELBLUE2));
        colorMap.put("steelblue3", new Color(STEELBLUE3));
        colorMap.put("steelblue4", new Color(STEELBLUE4));
        colorMap.put("tan", new Color(TAN));
        colorMap.put("tan1", new Color(TAN1));
        colorMap.put("tan2", new Color(TAN2));
        colorMap.put("tan3", new Color(TAN3));
        colorMap.put("tan4", new Color(TAN4));
        colorMap.put("teal", new Color(TEAL));
        colorMap.put("thistle", new Color(THISTLE));
        colorMap.put("thistle1", new Color(THISTLE1));
        colorMap.put("thistle2", new Color(THISTLE2));
        colorMap.put("thistle3", new Color(THISTLE3));
        colorMap.put("thistle4", new Color(THISTLE4));
        colorMap.put("tomato", new Color(TOMATO));
        colorMap.put("tomato1", new Color(TOMATO1));
        colorMap.put("tomato2", new Color(TOMATO2));
        colorMap.put("tomato3", new Color(TOMATO3));
        colorMap.put("turquoise", new Color(TURQUOISE));
        colorMap.put("turquoise1", new Color(TURQUOISE1));
        colorMap.put("turquoise2", new Color(TURQUOISE2));
        colorMap.put("turquoise3", new Color(TURQUOISE3));
        colorMap.put("turquoise4", new Color(TURQUOISE4));
        colorMap.put("violet", new Color(VIOLET));
        colorMap.put("violetred", new Color(VIOLETRED));
        colorMap.put("violetred1", new Color(VIOLETRED1));
        colorMap.put("violetred2", new Color(VIOLETRED2));
        colorMap.put("violetred3", new Color(VIOLETRED3));
        colorMap.put("violetred4", new Color(VIOLETRED4));
        colorMap.put("wheat", new Color(WHEAT));
        colorMap.put("wheat1", new Color(WHEAT1));
        colorMap.put("wheat2", new Color(WHEAT2));
        colorMap.put("wheat3", new Color(WHEAT3));
        colorMap.put("wheat4", new Color(WHEAT4));
        colorMap.put("white", new Color(WHITE));
        colorMap.put("whitesmoke", new Color(WHITESMOKE));
        colorMap.put("yellow", new Color(YELLOW));
        colorMap.put("yellow1", new Color(YELLOW1));
        colorMap.put("yellow2", new Color(YELLOW2));
        colorMap.put("yellow3", new Color(YELLOW3));
        colorMap.put("yellowgreen", new Color(YELLOWGREEN));
        return colorMap;
    }
}
