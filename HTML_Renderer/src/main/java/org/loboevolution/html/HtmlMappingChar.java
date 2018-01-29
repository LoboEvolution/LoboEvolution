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
package org.loboevolution.html;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class HtmlMappingChar.
 */
public class HtmlMappingChar {

	/** The Constant AMP. */
	private static final int AMP = 26;

	/** The Constant QUOT. */
	private static final int QUOT = 34;

	/** The Constant FRASL. */
	private static final int FRASL = 47;

	/** The Constant LT. */
	private static final int LT = 60;

	/** The Constant GT. */
	private static final int GT = 62;

	/** The Constant NBSP. */
	private static final int NBSP = 160;

	/** The Constant IEXCL. */
	private static final int IEXCL = 161;

	/** The Constant CENT. */
	private static final int CENT = 162;

	/** The Constant POUND. */
	private static final int POUND = 163;

	/** The Constant CURREN. */
	private static final int CURREN = 164;

	/** The Constant YEN. */
	private static final int YEN = 165;

	/** The Constant BRVBAR. */
	private static final int BRVBAR = 166;

	/** The Constant BRKBAR. */
	private static final int BRKBAR = 166;

	/** The Constant SECT. */
	private static final int SECT = 167;

	/** The Constant UML. */
	private static final int UML = 168;

	/** The Constant DIE. */
	private static final int DIE = 168;

	/** The Constant COPY. */
	private static final int COPY = 169;

	/** The Constant ORDF. */
	private static final int ORDF = 170;

	/** The Constant LAQUO. */
	private static final int LAQUO = 171;

	/** The Constant NOT. */
	private static final int NOT = 172;

	/** The Constant SHY. */
	private static final int SHY = 173;

	/** The Constant REG. */
	private static final int REG = 174;

	/** The Constant MACR. */
	private static final int MACR = 175;

	/** The Constant HIBAR. */
	private static final int HIBAR = 175;

	/** The Constant DEG. */
	private static final int DEG = 176;

	/** The Constant PLUSMN. */
	private static final int PLUSMN = 177;

	/** The Constant SUP2. */
	private static final int SUP2 = 178;

	/** The Constant SUP3. */
	private static final int SUP3 = 179;

	/** The Constant ACUTE. */
	private static final int ACUTE = 180;

	/** The Constant MICRO. */
	private static final int MICRO = 181;

	/** The Constant PARA. */
	private static final int PARA = 182;

	/** The Constant MIDDOT. */
	private static final int MIDDOT = 183;

	/** The Constant CEDIL. */
	private static final int CEDIL = 184;

	/** The Constant SUP1. */
	private static final int SUP1 = 185;

	/** The Constant ORDM. */
	private static final int ORDM = 186;

	/** The Constant RAQUO. */
	private static final int RAQUO = 187;

	/** The Constant FRAC14. */
	private static final int FRAC14 = 188;

	/** The Constant FRAC12. */
	private static final int FRAC12 = 189;

	/** The Constant FRAC34. */
	private static final int FRAC34 = 190;

	/** The Constant IQUEST. */
	private static final int IQUEST = 191;

	/** The Constant AGRAVE2. */
	private static final int AGRAVE2 = 192;

	/** The Constant AACUTE2. */
	private static final int AACUTE2 = 193;

	/** The Constant ACIRC2. */
	private static final int ACIRC2 = 194;

	/** The Constant ATILDE2. */
	private static final int ATILDE2 = 195;

	/** The Constant AUML2. */
	private static final int AUML2 = 196;

	/** The Constant ARING2. */
	private static final int ARING2 = 197;

	/** The Constant AELIG2. */
	private static final int AELIG2 = 198;

	/** The Constant CCEDIL2. */
	private static final int CCEDIL2 = 199;

	/** The Constant EGRAVE2. */
	private static final int EGRAVE2 = 200;

	/** The Constant EACUTE2. */
	private static final int EACUTE2 = 201;

	/** The Constant ECIRC2. */
	private static final int ECIRC2 = 202;

	/** The Constant EUML2. */
	private static final int EUML2 = 203;

	/** The Constant IGRAVE2. */
	private static final int IGRAVE2 = 204;

	/** The Constant IACUTE2. */
	private static final int IACUTE2 = 205;

	/** The Constant ICIRC2. */
	private static final int ICIRC2 = 206;

	/** The Constant IUML2. */
	private static final int IUML2 = 207;

	/** The Constant ETH2. */
	private static final int ETH2 = 208;

	/** The Constant NTILDE2. */
	private static final int NTILDE2 = 209;

	/** The Constant OGRAVE2. */
	private static final int OGRAVE2 = 210;

	/** The Constant OACUTE2. */
	private static final int OACUTE2 = 211;

	/** The Constant OCIRC2. */
	private static final int OCIRC2 = 212;

	/** The Constant OTILDE2. */
	private static final int OTILDE2 = 213;

	/** The Constant OUML2. */
	private static final int OUML2 = 214;

	/** The Constant TIMES. */
	private static final int TIMES = 215;

	/** The Constant OSLASH2. */
	private static final int OSLASH2 = 216;

	/** The Constant UGRAVE2. */
	private static final int UGRAVE2 = 217;

	/** The Constant UACUTE2. */
	private static final int UACUTE2 = 218;

	/** The Constant UCIRC2. */
	private static final int UCIRC2 = 219;

	/** The Constant UUML2. */
	private static final int UUML2 = 220;

	/** The Constant YACUTE2. */
	private static final int YACUTE2 = 221;

	/** The Constant THORN. */
	private static final int THORN = 222;

	/** The Constant SZLIG. */
	private static final int SZLIG = 223;

	/** The Constant AGRAVE. */
	private static final int AGRAVE = 224;

	/** The Constant AACUTE. */
	private static final int AACUTE = 225;

	/** The Constant ACIRC. */
	private static final int ACIRC = 226;

	/** The Constant ATILDE. */
	private static final int ATILDE = 227;

	/** The Constant AUML. */
	private static final int AUML = 228;

	/** The Constant ARING. */
	private static final int ARING = 229;

	/** The Constant AELIG. */
	private static final int AELIG = 230;

	/** The Constant CCEDIL. */
	private static final int CCEDIL = 231;

	/** The Constant EGRAVE. */
	private static final int EGRAVE = 232;

	/** The Constant EACUTE. */
	private static final int EACUTE = 233;

	/** The Constant ECIRC. */
	private static final int ECIRC = 234;

	/** The Constant EUML. */
	private static final int EUML = 235;

	/** The Constant IGRAVE. */
	private static final int IGRAVE = 236;

	/** The Constant IACUTE. */
	private static final int IACUTE = 237;

	/** The Constant ICIRC. */
	private static final int ICIRC = 238;

	/** The Constant IUML. */
	private static final int IUML = 239;

	/** The Constant ETH. */
	private static final int ETH = 240;

	/** The Constant NTILDE. */
	private static final int NTILDE = 241;

	/** The Constant OGRAVE. */
	private static final int OGRAVE = 242;

	/** The Constant OACUTE. */
	private static final int OACUTE = 243;

	/** The Constant OCIRC. */
	private static final int OCIRC = 244;

	/** The Constant OTILDE. */
	private static final int OTILDE = 245;

	/** The Constant OUML. */
	private static final int OUML = 246;

	/** The Constant DIVIDE. */
	private static final int DIVIDE = 247;

	/** The Constant OSLASH. */
	private static final int OSLASH = 248;

	/** The Constant UGRAVE. */
	private static final int UGRAVE = 249;

	/** The Constant UACUTE. */
	private static final int UACUTE = 250;

	/** The Constant UCIRC. */
	private static final int UCIRC = 251;

	/** The Constant UUML. */
	private static final int UUML = 252;

	/** The Constant YACUTE. */
	private static final int YACUTE = 253;

	/** The Constant YUML. */
	private static final int YUML = 255;

	/** The Constant OELIG2. */
	private static final int OELIG2 = 338;

	/** The Constant OELIG. */
	private static final int OELIG = 339;

	/** The Constant SCARON2. */
	private static final int SCARON2 = 352;

	/** The Constant SCARON. */
	private static final int SCARON = 353;

	/** The Constant FNOF. */
	private static final int FNOF = 402;

	/** The Constant CIRC. */
	private static final int CIRC = 710;

	/** The Constant TILDE. */
	private static final int TILDE = 732;

	/** The Constant ALPHA2. */
	private static final int ALPHA2 = 913;

	/** The Constant BETA2. */
	private static final int BETA2 = 914;

	/** The Constant GAMMA2. */
	private static final int GAMMA2 = 915;

	/** The Constant DELTA2. */
	private static final int DELTA2 = 916;

	/** The Constant EPSILON2. */
	private static final int EPSILON2 = 917;

	/** The Constant ZETA2. */
	private static final int ZETA2 = 918;

	/** The Constant ETA2. */
	private static final int ETA2 = 919;

	/** The Constant THETA2. */
	private static final int THETA2 = 920;

	/** The Constant IOTA2. */
	private static final int IOTA2 = 921;

	/** The Constant KAPPA2. */
	private static final int KAPPA2 = 922;

	/** The Constant LAMBDA2. */
	private static final int LAMBDA2 = 923;

	/** The Constant MU2. */
	private static final int MU2 = 924;

	/** The Constant NU2. */
	private static final int NU2 = 925;

	/** The Constant XI2. */
	private static final int XI2 = 926;

	/** The Constant OMICRON2. */
	private static final int OMICRON2 = 927;

	/** The Constant PI2. */
	private static final int PI2 = 928;

	/** The Constant RHO2. */
	private static final int RHO2 = 929;

	/** The Constant SIGMA2. */
	private static final int SIGMA2 = 930;

	/** The Constant SIGMAF2. */
	private static final int SIGMAF2 = 931;

	/** The Constant TAU2. */
	private static final int TAU2 = 932;

	/** The Constant UPSILON2. */
	private static final int UPSILON2 = 933;

	/** The Constant PHI2. */
	private static final int PHI2 = 934;

	/** The Constant CHI2. */
	private static final int CHI2 = 935;

	/** The Constant PSI2. */
	private static final int PSI2 = 936;

	/** The Constant OMEGA2. */
	private static final int OMEGA2 = 937;

	/** The Constant ALPHA. */
	private static final int ALPHA = 945;

	/** The Constant BETA. */
	private static final int BETA = 946;

	/** The Constant GAMMA. */
	private static final int GAMMA = 947;

	/** The Constant DELTA. */
	private static final int DELTA = 948;

	/** The Constant EPSILON. */
	private static final int EPSILON = 949;

	/** The Constant ZETA. */
	private static final int ZETA = 950;

	/** The Constant ETA. */
	private static final int ETA = 951;

	/** The Constant THETA. */
	private static final int THETA = 952;

	/** The Constant IOTA. */
	private static final int IOTA = 953;

	/** The Constant KAPPA. */
	private static final int KAPPA = 954;

	/** The Constant LAMBDA. */
	private static final int LAMBDA = 955;

	/** The Constant MU. */
	private static final int MU = 956;

	/** The Constant NU. */
	private static final int NU = 957;

	/** The Constant XI. */
	private static final int XI = 958;

	/** The Constant OMICRON. */
	private static final int OMICRON = 959;

	/** The Constant PI. */
	private static final int PI = 960;

	/** The Constant RHO. */
	private static final int RHO = 961;

	/** The Constant SIGMA. */
	private static final int SIGMA = 962;

	/** The Constant SIGMAF. */
	private static final int SIGMAF = 963;

	/** The Constant TAU. */
	private static final int TAU = 964;

	/** The Constant UPSILON. */
	private static final int UPSILON = 965;

	/** The Constant PHI. */
	private static final int PHI = 966;

	/** The Constant CHI. */
	private static final int CHI = 967;

	/** The Constant PSI. */
	private static final int PSI = 968;

	/** The Constant OMEGA. */
	private static final int OMEGA = 969;

	/** The Constant THETASYM. */
	private static final int THETASYM = 977;

	/** The Constant UPSIH. */
	private static final int UPSIH = 978;

	/** The Constant PIV. */
	private static final int PIV = 982;

	/** The Constant ENSP. */
	private static final int ENSP = 8194;

	/** The Constant EMSP. */
	private static final int EMSP = 8195;

	/** The Constant THINSP. */
	private static final int THINSP = 8201;

	/** The Constant ZWNJ. */
	private static final int ZWNJ = 8204;

	/** The Constant ZWJ. */
	private static final int ZWJ = 8205;

	/** The Constant LRM. */
	private static final int LRM = 8206;

	/** The Constant RLM. */
	private static final int RLM = 8207;

	/** The Constant NDASH. */
	private static final int NDASH = 8211;

	/** The Constant MDASH. */
	private static final int MDASH = 8212;

	/** The Constant LSQUO. */
	private static final int LSQUO = 8216;

	/** The Constant RSQUO. */
	private static final int RSQUO = 8217;

	/** The Constant SBQUO. */
	private static final int SBQUO = 8218;

	/** The Constant LDQUO. */
	private static final int LDQUO = 8220;

	/** The Constant RDQUO. */
	private static final int RDQUO = 8221;

	/** The Constant BDQUO. */
	private static final int BDQUO = 8222;

	/** The Constant DAGGER. */
	private static final int DAGGER = 8224;

	/** The Constant DAGGER2. */
	private static final int DAGGER2 = 8225;

	/** The Constant BULL. */
	private static final int BULL = 8226;

	/** The Constant HELLIP. */
	private static final int HELLIP = 8230;

	/** The Constant PERMIL. */
	private static final int PERMIL = 8240;

	/** The Constant PRIME. */
	private static final int PRIME = 8242;

	/** The Constant PRIME2. */
	private static final int PRIME2 = 8243;

	/** The Constant LSAQUO. */
	private static final int LSAQUO = 8249;

	/** The Constant RSAQUO. */
	private static final int RSAQUO = 8250;

	/** The Constant OLINE. */
	private static final int OLINE = 8254;

	/** The Constant EURO. */
	private static final int EURO = 8364;

	/** The Constant IMAGE. */
	private static final int IMAGE = 8465;

	/** The Constant WEIERP. */
	private static final int WEIERP = 8472;

	/** The Constant REAL. */
	private static final int REAL = 8476;

	/** The Constant TRADE. */
	private static final int TRADE = 8482;

	/** The Constant ALEFSYM. */
	private static final int ALEFSYM = 8501;

	/** The Constant LARR. */
	private static final int LARR = 8592;

	/** The Constant UARR. */
	private static final int UARR = 8593;

	/** The Constant RARR. */
	private static final int RARR = 8594;

	/** The Constant DARR. */
	private static final int DARR = 8595;

	/** The Constant HARR. */
	private static final int HARR = 8596;

	/** The Constant CRARR. */
	private static final int CRARR = 8629;

	/** The Constant LARR2. */
	private static final int LARR2 = 8656;

	/** The Constant UARR2. */
	private static final int UARR2 = 8657;

	/** The Constant RARR2. */
	private static final int RARR2 = 8658;

	/** The Constant DARR2. */
	private static final int DARR2 = 8659;

	/** The Constant FORALL. */
	private static final int FORALL = 8704;

	/** The Constant PART. */
	private static final int PART = 8706;

	/** The Constant EXIST. */
	private static final int EXIST = 8707;

	/** The Constant EMPTY. */
	private static final int EMPTY = 8709;

	/** The Constant NABLA. */
	private static final int NABLA = 8711;

	/** The Constant ISIN. */
	private static final int ISIN = 8712;

	/** The Constant NOTIN. */
	private static final int NOTIN = 8713;

	/** The Constant NI. */
	private static final int NI = 8715;

	/** The Constant PROD. */
	private static final int PROD = 8719;

	/** The Constant SUM. */
	private static final int SUM = 8721;

	/** The Constant MINUS. */
	private static final int MINUS = 8722;

	/** The Constant LOWAST. */
	private static final int LOWAST = 8727;

	/** The Constant RADIC. */
	private static final int RADIC = 8730;

	/** The Constant PROP. */
	private static final int PROP = 8733;

	/** The Constant INFIN. */
	private static final int INFIN = 8734;

	/** The Constant ANG. */
	private static final int ANG = 8736;

	/** The Constant AND. */
	private static final int AND = 8743;

	/** The Constant OR. */
	private static final int OR = 8744;

	/** The Constant CAP. */
	private static final int CAP = 8745;

	/** The Constant CUP. */
	private static final int CUP = 8746;

	/** The Constant INT. */
	private static final int INT = 8747;

	/** The Constant THERE4. */
	private static final int THERE4 = 8756;

	/** The Constant SIM. */
	private static final int SIM = 8764;

	/** The Constant CONG. */
	private static final int CONG = 8773;

	/** The Constant ASYMP. */
	private static final int ASYMP = 8776;

	/** The Constant NE. */
	private static final int NE = 8800;

	/** The Constant EQUIV. */
	private static final int EQUIV = 8801;

	/** The Constant LE. */
	private static final int LE = 8804;

	/** The Constant GE. */
	private static final int GE = 8805;

	/** The Constant SUB. */
	private static final int SUB = 8834;

	/** The Constant SUP. */
	private static final int SUP = 8835;

	/** The Constant NSUB. */
	private static final int NSUB = 8836;

	/** The Constant SUBE. */
	private static final int SUBE = 8838;

	/** The Constant SUPE. */
	private static final int SUPE = 8839;

	/** The Constant OPLUS. */
	private static final int OPLUS = 8853;

	/** The Constant OTIMES. */
	private static final int OTIMES = 8855;

	/** The Constant PERP. */
	private static final int PERP = 8869;

	/** The Constant SDOT. */
	private static final int SDOT = 8901;

	/** The Constant HARR2. */
	private static final int HARR2 = 8960;

	/** The Constant LCEIL. */
	private static final int LCEIL = 8968;

	/** The Constant RCEIL. */
	private static final int RCEIL = 8969;

	/** The Constant LFLOOR. */
	private static final int LFLOOR = 8970;

	/** The Constant RFLOOR. */
	private static final int RFLOOR = 8971;

	/** The Constant SPADES. */
	private static final int SPADES = 9824;

	/** The Constant CLUBS. */
	private static final int CLUBS = 9827;

	/** The Constant HEARTS. */
	private static final int HEARTS = 9829;

	/** The Constant DIAMS. */
	private static final int DIAMS = 9830;

	/** The Constant LANG. */
	private static final int LANG = 9001;

	/** The Constant RANG. */
	private static final int RANG = 9002;

	/** The Constant LOZ. */
	private static final int LOZ = 9674;

	/**
	 * Mapping char.
	 *
	 * @return the map
	 */
	public static Map<String, Character> mappingChar() {

		Map<String, Character> entities = new HashMap<String, Character>();
		entities.put(HtmlCharProperties.AMP, Character.valueOf((char) AMP));
		entities.put(HtmlCharProperties.QUOT, Character.valueOf((char) QUOT));
		entities.put(HtmlCharProperties.FRASL, Character.valueOf((char) FRASL));
		entities.put(HtmlCharProperties.LT, Character.valueOf((char) LT));
		entities.put(HtmlCharProperties.GT, Character.valueOf((char) GT));
		entities.put(HtmlCharProperties.NBSP, Character.valueOf((char) NBSP));
		entities.put(HtmlCharProperties.IEXCL, Character.valueOf((char) IEXCL));
		entities.put(HtmlCharProperties.CENT, Character.valueOf((char) CENT));
		entities.put(HtmlCharProperties.POUND, Character.valueOf((char) POUND));
		entities.put(HtmlCharProperties.CURREN, Character.valueOf((char) CURREN));
		entities.put(HtmlCharProperties.YEN, Character.valueOf((char) YEN));
		entities.put(HtmlCharProperties.BRVBAR, Character.valueOf((char) BRVBAR));
		entities.put(HtmlCharProperties.BRKBAR, Character.valueOf((char) BRKBAR));
		entities.put(HtmlCharProperties.SECT, Character.valueOf((char) SECT));
		entities.put(HtmlCharProperties.UML, Character.valueOf((char) UML));
		entities.put(HtmlCharProperties.DIE, Character.valueOf((char) DIE));
		entities.put(HtmlCharProperties.COPY, Character.valueOf((char) COPY));
		entities.put(HtmlCharProperties.ORDF, Character.valueOf((char) ORDF));
		entities.put(HtmlCharProperties.LAQUO, Character.valueOf((char) LAQUO));
		entities.put(HtmlCharProperties.NOT, Character.valueOf((char) NOT));
		entities.put(HtmlCharProperties.SHY, Character.valueOf((char) SHY));
		entities.put(HtmlCharProperties.REG, Character.valueOf((char) REG));
		entities.put(HtmlCharProperties.MACR, Character.valueOf((char) MACR));
		entities.put(HtmlCharProperties.HIBAR, Character.valueOf((char) HIBAR));
		entities.put(HtmlCharProperties.DEG, Character.valueOf((char) DEG));
		entities.put(HtmlCharProperties.PLUSMN, Character.valueOf((char) PLUSMN));
		entities.put(HtmlCharProperties.SUP2, Character.valueOf((char) SUP2));
		entities.put(HtmlCharProperties.SUP3, Character.valueOf((char) SUP3));
		entities.put(HtmlCharProperties.ACUTE, Character.valueOf((char) ACUTE));
		entities.put(HtmlCharProperties.MICRO, Character.valueOf((char) MICRO));
		entities.put(HtmlCharProperties.PARA, Character.valueOf((char) PARA));
		entities.put(HtmlCharProperties.MIDDOT, Character.valueOf((char) MIDDOT));
		entities.put(HtmlCharProperties.CEDIL, Character.valueOf((char) CEDIL));
		entities.put(HtmlCharProperties.SUP1, Character.valueOf((char) SUP1));
		entities.put(HtmlCharProperties.ORDM, Character.valueOf((char) ORDM));
		entities.put(HtmlCharProperties.RAQUO, Character.valueOf((char) RAQUO));
		entities.put(HtmlCharProperties.FRAC14, Character.valueOf((char) FRAC14));
		entities.put(HtmlCharProperties.FRAC12, Character.valueOf((char) FRAC12));
		entities.put(HtmlCharProperties.FRAC34, Character.valueOf((char) FRAC34));
		entities.put(HtmlCharProperties.IQUEST, Character.valueOf((char) IQUEST));
		entities.put(HtmlCharProperties.AGRAVE2, Character.valueOf((char) AGRAVE2));
		entities.put(HtmlCharProperties.AACUTE2, Character.valueOf((char) AACUTE2));
		entities.put(HtmlCharProperties.ACIRC2, Character.valueOf((char) ACIRC2));
		entities.put(HtmlCharProperties.ATILDE2, Character.valueOf((char) ATILDE2));
		entities.put(HtmlCharProperties.AUML2, Character.valueOf((char) AUML2));
		entities.put(HtmlCharProperties.ARING2, Character.valueOf((char) ARING2));
		entities.put(HtmlCharProperties.AELIG2, Character.valueOf((char) AELIG2));
		entities.put(HtmlCharProperties.CCEDIL2, Character.valueOf((char) CCEDIL2));
		entities.put(HtmlCharProperties.EGRAVE2, Character.valueOf((char) EGRAVE2));
		entities.put(HtmlCharProperties.EACUTE2, Character.valueOf((char) EACUTE2));
		entities.put(HtmlCharProperties.ECIRC2, Character.valueOf((char) ECIRC2));
		entities.put(HtmlCharProperties.EUML2, Character.valueOf((char) EUML2));
		entities.put(HtmlCharProperties.IGRAVE2, Character.valueOf((char) IGRAVE2));
		entities.put(HtmlCharProperties.IACUTE2, Character.valueOf((char) IACUTE2));
		entities.put(HtmlCharProperties.ICIRC2, Character.valueOf((char) ICIRC2));
		entities.put(HtmlCharProperties.IUML2, Character.valueOf((char) IUML2));
		entities.put(HtmlCharProperties.ETH2, Character.valueOf((char) ETH2));
		entities.put(HtmlCharProperties.NTILDE2, Character.valueOf((char) NTILDE2));
		entities.put(HtmlCharProperties.OGRAVE2, Character.valueOf((char) OGRAVE2));
		entities.put(HtmlCharProperties.OACUTE2, Character.valueOf((char) OACUTE2));
		entities.put(HtmlCharProperties.OCIRC2, Character.valueOf((char) OCIRC2));
		entities.put(HtmlCharProperties.OTILDE2, Character.valueOf((char) OTILDE2));
		entities.put(HtmlCharProperties.OUML2, Character.valueOf((char) OUML2));
		entities.put(HtmlCharProperties.TIMES, Character.valueOf((char) TIMES));
		entities.put(HtmlCharProperties.OSLASH2, Character.valueOf((char) OSLASH2));
		entities.put(HtmlCharProperties.UGRAVE2, Character.valueOf((char) UGRAVE2));
		entities.put(HtmlCharProperties.UACUTE2, Character.valueOf((char) UACUTE2));
		entities.put(HtmlCharProperties.UCIRC2, Character.valueOf((char) UCIRC2));
		entities.put(HtmlCharProperties.UUML2, Character.valueOf((char) UUML2));
		entities.put(HtmlCharProperties.YACUTE2, Character.valueOf((char) YACUTE2));
		entities.put(HtmlCharProperties.THORN, Character.valueOf((char) THORN));
		entities.put(HtmlCharProperties.SZLIG, Character.valueOf((char) SZLIG));
		entities.put(HtmlCharProperties.AGRAVE, Character.valueOf((char) AGRAVE));
		entities.put(HtmlCharProperties.AACUTE, Character.valueOf((char) AACUTE));
		entities.put(HtmlCharProperties.ACIRC, Character.valueOf((char) ACIRC));
		entities.put(HtmlCharProperties.ATILDE, Character.valueOf((char) ATILDE));
		entities.put(HtmlCharProperties.AUML, Character.valueOf((char) AUML));
		entities.put(HtmlCharProperties.ARING, Character.valueOf((char) ARING));
		entities.put(HtmlCharProperties.AELIG, Character.valueOf((char) AELIG));
		entities.put(HtmlCharProperties.CCEDIL, Character.valueOf((char) CCEDIL));
		entities.put(HtmlCharProperties.EGRAVE, Character.valueOf((char) EGRAVE));
		entities.put(HtmlCharProperties.EACUTE, Character.valueOf((char) EACUTE));
		entities.put(HtmlCharProperties.ECIRC, Character.valueOf((char) ECIRC));
		entities.put(HtmlCharProperties.EUML, Character.valueOf((char) EUML));
		entities.put(HtmlCharProperties.IGRAVE, Character.valueOf((char) IGRAVE));
		entities.put(HtmlCharProperties.IACUTE, Character.valueOf((char) IACUTE));
		entities.put(HtmlCharProperties.ICIRC, Character.valueOf((char) ICIRC));
		entities.put(HtmlCharProperties.IUML, Character.valueOf((char) IUML));
		entities.put(HtmlCharProperties.ETH, Character.valueOf((char) ETH));
		entities.put(HtmlCharProperties.NTILDE, Character.valueOf((char) NTILDE));
		entities.put(HtmlCharProperties.OGRAVE, Character.valueOf((char) OGRAVE));
		entities.put(HtmlCharProperties.OACUTE, Character.valueOf((char) OACUTE));
		entities.put(HtmlCharProperties.OCIRC, Character.valueOf((char) OCIRC));
		entities.put(HtmlCharProperties.OTILDE, Character.valueOf((char) OTILDE));
		entities.put(HtmlCharProperties.OUML, Character.valueOf((char) OUML));
		entities.put(HtmlCharProperties.DIVIDE, Character.valueOf((char) DIVIDE));
		entities.put(HtmlCharProperties.OSLASH, Character.valueOf((char) OSLASH));
		entities.put(HtmlCharProperties.UGRAVE, Character.valueOf((char) UGRAVE));
		entities.put(HtmlCharProperties.UACUTE, Character.valueOf((char) UACUTE));
		entities.put(HtmlCharProperties.UCIRC, Character.valueOf((char) UCIRC));
		entities.put(HtmlCharProperties.UUML, Character.valueOf((char) UUML));
		entities.put(HtmlCharProperties.YACUTE, Character.valueOf((char) YACUTE));
		entities.put(HtmlCharProperties.THORN, Character.valueOf((char) THORN));
		entities.put(HtmlCharProperties.YUML, Character.valueOf((char) YUML));
		entities.put(HtmlCharProperties.OELIG2, Character.valueOf((char) OELIG2));
		entities.put(HtmlCharProperties.OELIG, Character.valueOf((char) OELIG));
		entities.put(HtmlCharProperties.SCARON2, Character.valueOf((char) SCARON2));
		entities.put(HtmlCharProperties.SCARON, Character.valueOf((char) SCARON));
		entities.put(HtmlCharProperties.FNOF, Character.valueOf((char) FNOF));
		entities.put(HtmlCharProperties.CIRC, Character.valueOf((char) CIRC));
		entities.put(HtmlCharProperties.TILDE, Character.valueOf((char) TILDE));
		entities.put(HtmlCharProperties.ALPHA2, Character.valueOf((char) ALPHA2));
		entities.put(HtmlCharProperties.BETA2, Character.valueOf((char) BETA2));
		entities.put(HtmlCharProperties.GAMMA2, Character.valueOf((char) GAMMA2));
		entities.put(HtmlCharProperties.DELTA2, Character.valueOf((char) DELTA2));
		entities.put(HtmlCharProperties.EPSILON2, Character.valueOf((char) EPSILON2));
		entities.put(HtmlCharProperties.ZETA2, Character.valueOf((char) ZETA2));
		entities.put(HtmlCharProperties.ETA2, Character.valueOf((char) ETA2));
		entities.put(HtmlCharProperties.THETA2, Character.valueOf((char) THETA2));
		entities.put(HtmlCharProperties.IOTA2, Character.valueOf((char) IOTA2));
		entities.put(HtmlCharProperties.KAPPA2, Character.valueOf((char) KAPPA2));
		entities.put(HtmlCharProperties.LAMBDA2, Character.valueOf((char) LAMBDA2));
		entities.put(HtmlCharProperties.MU2, Character.valueOf((char) MU2));
		entities.put(HtmlCharProperties.NU2, Character.valueOf((char) NU2));
		entities.put(HtmlCharProperties.XI2, Character.valueOf((char) XI2));
		entities.put(HtmlCharProperties.OMICRON2, Character.valueOf((char) OMICRON2));
		entities.put(HtmlCharProperties.PI2, Character.valueOf((char) PI2));
		entities.put(HtmlCharProperties.RHO2, Character.valueOf((char) RHO2));
		entities.put(HtmlCharProperties.SIGMA2, Character.valueOf((char) SIGMA2));
		entities.put(HtmlCharProperties.SIGMAF2, Character.valueOf((char) SIGMAF2));
		entities.put(HtmlCharProperties.TAU2, Character.valueOf((char) TAU2));
		entities.put(HtmlCharProperties.UPSILON2, Character.valueOf((char) UPSILON2));
		entities.put(HtmlCharProperties.PHI2, Character.valueOf((char) PHI2));
		entities.put(HtmlCharProperties.CHI2, Character.valueOf((char) CHI2));
		entities.put(HtmlCharProperties.PSI2, Character.valueOf((char) PSI2));
		entities.put(HtmlCharProperties.OMEGA2, Character.valueOf((char) OMEGA2));
		entities.put(HtmlCharProperties.ALPHA, Character.valueOf((char) ALPHA));
		entities.put(HtmlCharProperties.BETA, Character.valueOf((char) BETA));
		entities.put(HtmlCharProperties.GAMMA, Character.valueOf((char) GAMMA));
		entities.put(HtmlCharProperties.DELTA, Character.valueOf((char) DELTA));
		entities.put(HtmlCharProperties.EPSILON, Character.valueOf((char) EPSILON));
		entities.put(HtmlCharProperties.ZETA, Character.valueOf((char) ZETA));
		entities.put(HtmlCharProperties.ETA, Character.valueOf((char) ETA));
		entities.put(HtmlCharProperties.THETA, Character.valueOf((char) THETA));
		entities.put(HtmlCharProperties.IOTA, Character.valueOf((char) IOTA));
		entities.put(HtmlCharProperties.KAPPA, Character.valueOf((char) KAPPA));
		entities.put(HtmlCharProperties.LAMBDA, Character.valueOf((char) LAMBDA));
		entities.put(HtmlCharProperties.MU, Character.valueOf((char) MU));
		entities.put(HtmlCharProperties.NU, Character.valueOf((char) NU));
		entities.put(HtmlCharProperties.XI, Character.valueOf((char) XI));
		entities.put(HtmlCharProperties.OMICRON, Character.valueOf((char) OMICRON));
		entities.put(HtmlCharProperties.PI, Character.valueOf((char) PI));
		entities.put(HtmlCharProperties.RHO, Character.valueOf((char) RHO));
		entities.put(HtmlCharProperties.SIGMA, Character.valueOf((char) SIGMA));
		entities.put(HtmlCharProperties.SIGMAF, Character.valueOf((char) SIGMAF));
		entities.put(HtmlCharProperties.TAU, Character.valueOf((char) TAU));
		entities.put(HtmlCharProperties.UPSILON, Character.valueOf((char) UPSILON));
		entities.put(HtmlCharProperties.PHI, Character.valueOf((char) PHI));
		entities.put(HtmlCharProperties.CHI, Character.valueOf((char) CHI));
		entities.put(HtmlCharProperties.PSI, Character.valueOf((char) PSI));
		entities.put(HtmlCharProperties.OMEGA, Character.valueOf((char) OMEGA));
		entities.put(HtmlCharProperties.THETASYM, Character.valueOf((char) THETASYM));
		entities.put(HtmlCharProperties.UPSIH, Character.valueOf((char) UPSIH));
		entities.put(HtmlCharProperties.PIV, Character.valueOf((char) PIV));
		entities.put(HtmlCharProperties.ENSP, Character.valueOf((char) ENSP));
		entities.put(HtmlCharProperties.EMSP, Character.valueOf((char) EMSP));
		entities.put(HtmlCharProperties.THINSP, Character.valueOf((char) THINSP));
		entities.put(HtmlCharProperties.ZWNJ, Character.valueOf((char) ZWNJ));
		entities.put(HtmlCharProperties.ZWJ, Character.valueOf((char) ZWJ));
		entities.put(HtmlCharProperties.LRM, Character.valueOf((char) LRM));
		entities.put(HtmlCharProperties.RLM, Character.valueOf((char) RLM));
		entities.put(HtmlCharProperties.NDASH, Character.valueOf((char) NDASH));
		entities.put(HtmlCharProperties.MDASH, Character.valueOf((char) MDASH));
		entities.put(HtmlCharProperties.LSQUO, Character.valueOf((char) LSQUO));
		entities.put(HtmlCharProperties.RSQUO, Character.valueOf((char) RSQUO));
		entities.put(HtmlCharProperties.SBQUO, Character.valueOf((char) SBQUO));
		entities.put(HtmlCharProperties.LDQUO, Character.valueOf((char) LDQUO));
		entities.put(HtmlCharProperties.RDQUO, Character.valueOf((char) RDQUO));
		entities.put(HtmlCharProperties.BDQUO, Character.valueOf((char) BDQUO));
		entities.put(HtmlCharProperties.DAGGER, Character.valueOf((char) DAGGER));
		entities.put(HtmlCharProperties.DAGGER2, Character.valueOf((char) DAGGER2));
		entities.put(HtmlCharProperties.BULL, Character.valueOf((char) BULL));
		entities.put(HtmlCharProperties.HELLIP, Character.valueOf((char) HELLIP));
		entities.put(HtmlCharProperties.PERMIL, Character.valueOf((char) PERMIL));
		entities.put(HtmlCharProperties.PRIME, Character.valueOf((char) PRIME));
		entities.put(HtmlCharProperties.PRIME2, Character.valueOf((char) PRIME2));
		entities.put(HtmlCharProperties.LSAQUO, Character.valueOf((char) LSAQUO));
		entities.put(HtmlCharProperties.RSAQUO, Character.valueOf((char) RSAQUO));
		entities.put(HtmlCharProperties.OLINE, Character.valueOf((char) OLINE));
		entities.put(HtmlCharProperties.EURO, Character.valueOf((char) EURO));
		entities.put(HtmlCharProperties.IMAGE, Character.valueOf((char) IMAGE));
		entities.put(HtmlCharProperties.WEIERP, Character.valueOf((char) WEIERP));
		entities.put(HtmlCharProperties.REAL, Character.valueOf((char) REAL));
		entities.put(HtmlCharProperties.TRADE, Character.valueOf((char) TRADE));
		entities.put(HtmlCharProperties.ALEFSYM, Character.valueOf((char) ALEFSYM));
		entities.put(HtmlCharProperties.LARR, Character.valueOf((char) LARR));
		entities.put(HtmlCharProperties.UARR, Character.valueOf((char) UARR));
		entities.put(HtmlCharProperties.RARR, Character.valueOf((char) RARR));
		entities.put(HtmlCharProperties.DARR, Character.valueOf((char) DARR));
		entities.put(HtmlCharProperties.HARR, Character.valueOf((char) HARR));
		entities.put(HtmlCharProperties.CRARR, Character.valueOf((char) CRARR));
		entities.put(HtmlCharProperties.LARR2, Character.valueOf((char) LARR2));
		entities.put(HtmlCharProperties.UARR2, Character.valueOf((char) UARR2));
		entities.put(HtmlCharProperties.RARR2, Character.valueOf((char) RARR2));
		entities.put(HtmlCharProperties.DARR2, Character.valueOf((char) DARR2));
		entities.put(HtmlCharProperties.FORALL, Character.valueOf((char) FORALL));
		entities.put(HtmlCharProperties.PART, Character.valueOf((char) PART));
		entities.put(HtmlCharProperties.EXIST, Character.valueOf((char) EXIST));
		entities.put(HtmlCharProperties.EMPTY, Character.valueOf((char) EMPTY));
		entities.put(HtmlCharProperties.NABLA, Character.valueOf((char) NABLA));
		entities.put(HtmlCharProperties.ISIN, Character.valueOf((char) ISIN));
		entities.put(HtmlCharProperties.NOTIN, Character.valueOf((char) NOTIN));
		entities.put(HtmlCharProperties.NI, Character.valueOf((char) NI));
		entities.put(HtmlCharProperties.PROD, Character.valueOf((char) PROD));
		entities.put(HtmlCharProperties.SUM, Character.valueOf((char) SUM));
		entities.put(HtmlCharProperties.MINUS, Character.valueOf((char) MINUS));
		entities.put(HtmlCharProperties.LOWAST, Character.valueOf((char) LOWAST));
		entities.put(HtmlCharProperties.RADIC, Character.valueOf((char) RADIC));
		entities.put(HtmlCharProperties.PROP, Character.valueOf((char) PROP));
		entities.put(HtmlCharProperties.INFIN, Character.valueOf((char) INFIN));
		entities.put(HtmlCharProperties.ANG, Character.valueOf((char) ANG));
		entities.put(HtmlCharProperties.AND, Character.valueOf((char) AND));
		entities.put(HtmlCharProperties.OR, Character.valueOf((char) OR));
		entities.put(HtmlCharProperties.CAP, Character.valueOf((char) CAP));
		entities.put(HtmlCharProperties.CUP, Character.valueOf((char) CUP));
		entities.put(HtmlCharProperties.INT, Character.valueOf((char) INT));
		entities.put(HtmlCharProperties.THERE4, Character.valueOf((char) THERE4));
		entities.put(HtmlCharProperties.SIM, Character.valueOf((char) SIM));
		entities.put(HtmlCharProperties.CONG, Character.valueOf((char) CONG));
		entities.put(HtmlCharProperties.ASYMP, Character.valueOf((char) ASYMP));
		entities.put(HtmlCharProperties.NE, Character.valueOf((char) NE));
		entities.put(HtmlCharProperties.EQUIV, Character.valueOf((char) EQUIV));
		entities.put(HtmlCharProperties.LE, Character.valueOf((char) LE));
		entities.put(HtmlCharProperties.GE, Character.valueOf((char) GE));
		entities.put(HtmlCharProperties.SUB, Character.valueOf((char) SUB));
		entities.put(HtmlCharProperties.SUP, Character.valueOf((char) SUP));
		entities.put(HtmlCharProperties.NSUB, Character.valueOf((char) NSUB));
		entities.put(HtmlCharProperties.SUBE, Character.valueOf((char) SUBE));
		entities.put(HtmlCharProperties.SUPE, Character.valueOf((char) SUPE));
		entities.put(HtmlCharProperties.OPLUS, Character.valueOf((char) OPLUS));
		entities.put(HtmlCharProperties.OTIMES, Character.valueOf((char) OTIMES));
		entities.put(HtmlCharProperties.PERP, Character.valueOf((char) PERP));
		entities.put(HtmlCharProperties.SDOT, Character.valueOf((char) SDOT));
		entities.put(HtmlCharProperties.HARR2, Character.valueOf((char) HARR2));
		entities.put(HtmlCharProperties.LCEIL, Character.valueOf((char) LCEIL));
		entities.put(HtmlCharProperties.RCEIL, Character.valueOf((char) RCEIL));
		entities.put(HtmlCharProperties.LFLOOR, Character.valueOf((char) LFLOOR));
		entities.put(HtmlCharProperties.RFLOOR, Character.valueOf((char) RFLOOR));
		entities.put(HtmlCharProperties.SPADES, Character.valueOf((char) SPADES));
		entities.put(HtmlCharProperties.CLUBS, Character.valueOf((char) CLUBS));
		entities.put(HtmlCharProperties.HEARTS, Character.valueOf((char) HEARTS));
		entities.put(HtmlCharProperties.DIAMS, Character.valueOf((char) DIAMS));
		entities.put(HtmlCharProperties.LANG, Character.valueOf((char) LANG));
		entities.put(HtmlCharProperties.RANG, Character.valueOf((char) RANG));
		entities.put(HtmlCharProperties.LOZ, Character.valueOf((char) LOZ));
		return entities;
	}
}
