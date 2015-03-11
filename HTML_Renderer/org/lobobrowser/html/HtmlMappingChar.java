/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.html;

import java.util.HashMap;
import java.util.Map;


/**
 * The Class HtmlMappingChar.
 */
public class HtmlMappingChar {

	/**
	 * Mapping char.
	 *
	 * @return the map
	 */
	public static Map<String, Character> mappingChar() {

		Map<String, Character> entities = new HashMap<String, Character>();
		entities.put(HtmlCharProperties.AMP, new Character((char) 26));
		entities.put(HtmlCharProperties.QUOT, new Character((char) 34));
		entities.put(HtmlCharProperties.FRASL, new Character((char) 47));
		entities.put(HtmlCharProperties.LT, new Character((char) 60));
		entities.put(HtmlCharProperties.GT, new Character((char) 62));
		entities.put(HtmlCharProperties.NBSP, new Character((char) 160));
		entities.put(HtmlCharProperties.IEXCL, new Character((char) 161));
		entities.put(HtmlCharProperties.CENT, new Character((char) 162));
		entities.put(HtmlCharProperties.POUND, new Character((char) 163));
		entities.put(HtmlCharProperties.CURREN, new Character((char) 164));
		entities.put(HtmlCharProperties.YEN, new Character((char) 165));
		entities.put(HtmlCharProperties.BRVBAR, new Character((char) 166));
		entities.put(HtmlCharProperties.BRKBAR, new Character((char) 166));
		entities.put(HtmlCharProperties.SECT, new Character((char) 167));
		entities.put(HtmlCharProperties.UML, new Character((char) 168));
		entities.put(HtmlCharProperties.DIE, new Character((char) 168));
		entities.put(HtmlCharProperties.COPY, new Character((char) 169));
		entities.put(HtmlCharProperties.ORDF, new Character((char) 170));
		entities.put(HtmlCharProperties.LAQUO, new Character((char) 171));
		entities.put(HtmlCharProperties.NOT, new Character((char) 172));
		entities.put(HtmlCharProperties.SHY, new Character((char) 173));
		entities.put(HtmlCharProperties.REG, new Character((char) 174));
		entities.put(HtmlCharProperties.MACR, new Character((char) 175));
		entities.put(HtmlCharProperties.HIBAR, new Character((char) 175));
		entities.put(HtmlCharProperties.DEG, new Character((char) 176));
		entities.put(HtmlCharProperties.PLUSMN, new Character((char) 177));
		entities.put(HtmlCharProperties.SUP2, new Character((char) 178));
		entities.put(HtmlCharProperties.SUP3, new Character((char) 179));
		entities.put(HtmlCharProperties.ACUTE, new Character((char) 180));
		entities.put(HtmlCharProperties.MICRO, new Character((char) 181));
		entities.put(HtmlCharProperties.PARA, new Character((char) 182));
		entities.put(HtmlCharProperties.MIDDOT, new Character((char) 183));
		entities.put(HtmlCharProperties.CEDIL, new Character((char) 184));
		entities.put(HtmlCharProperties.SUP1, new Character((char) 185));
		entities.put(HtmlCharProperties.ORDM, new Character((char) 186));
		entities.put(HtmlCharProperties.RAQUO, new Character((char) 187));
		entities.put(HtmlCharProperties.FRAC14, new Character((char) 188));
		entities.put(HtmlCharProperties.FRAC12, new Character((char) 189));
		entities.put(HtmlCharProperties.FRAC34, new Character((char) 190));
		entities.put(HtmlCharProperties.IQUEST, new Character((char) 191));
		entities.put(HtmlCharProperties.AGRAVE2, new Character((char) 192));
		entities.put(HtmlCharProperties.AACUTE2, new Character((char) 193));
		entities.put(HtmlCharProperties.ACIRC2, new Character((char) 194));
		entities.put(HtmlCharProperties.ATILDE2, new Character((char) 195));
		entities.put(HtmlCharProperties.AUML2, new Character((char) 196));
		entities.put(HtmlCharProperties.ARING2, new Character((char) 197));
		entities.put(HtmlCharProperties.AELIG2, new Character((char) 198));
		entities.put(HtmlCharProperties.CCEDIL2, new Character((char) 199));
		entities.put(HtmlCharProperties.EGRAVE2, new Character((char) 200));
		entities.put(HtmlCharProperties.EACUTE2, new Character((char) 201));
		entities.put(HtmlCharProperties.ECIRC2, new Character((char) 202));
		entities.put(HtmlCharProperties.EUML2, new Character((char) 203));
		entities.put(HtmlCharProperties.IGRAVE2, new Character((char) 204));
		entities.put(HtmlCharProperties.IACUTE2, new Character((char) 205));
		entities.put(HtmlCharProperties.ICIRC2, new Character((char) 206));
		entities.put(HtmlCharProperties.IUML2, new Character((char) 207));
		entities.put(HtmlCharProperties.ETH2, new Character((char) 208));
		entities.put(HtmlCharProperties.NTILDE2, new Character((char) 209));
		entities.put(HtmlCharProperties.OGRAVE2, new Character((char) 210));
		entities.put(HtmlCharProperties.OACUTE2, new Character((char) 211));
		entities.put(HtmlCharProperties.OCIRC2, new Character((char) 212));
		entities.put(HtmlCharProperties.OTILDE2, new Character((char) 213));
		entities.put(HtmlCharProperties.OUML2, new Character((char) 214));
		entities.put(HtmlCharProperties.TIMES, new Character((char) 215));
		entities.put(HtmlCharProperties.OSLASH2, new Character((char) 216));
		entities.put(HtmlCharProperties.UGRAVE2, new Character((char) 217));
		entities.put(HtmlCharProperties.UACUTE2, new Character((char) 218));
		entities.put(HtmlCharProperties.UCIRC2, new Character((char) 219));
		entities.put(HtmlCharProperties.UUML2, new Character((char) 220));
		entities.put(HtmlCharProperties.YACUTE2, new Character((char) 221));
		entities.put(HtmlCharProperties.THORN, new Character((char) 222));
		entities.put(HtmlCharProperties.SZLIG, new Character((char) 223));
		entities.put(HtmlCharProperties.AGRAVE, new Character((char) 224));
		entities.put(HtmlCharProperties.AACUTE, new Character((char) 225));
		entities.put(HtmlCharProperties.ACIRC, new Character((char) 226));
		entities.put(HtmlCharProperties.ATILDE, new Character((char) 227));
		entities.put(HtmlCharProperties.AUML, new Character((char) 228));
		entities.put(HtmlCharProperties.ARING, new Character((char) 229));
		entities.put(HtmlCharProperties.AELIG, new Character((char) 230));
		entities.put(HtmlCharProperties.CCEDIL, new Character((char) 231));
		entities.put(HtmlCharProperties.EGRAVE, new Character((char) 232));
		entities.put(HtmlCharProperties.EACUTE, new Character((char) 233));
		entities.put(HtmlCharProperties.ECIRC, new Character((char) 234));
		entities.put(HtmlCharProperties.EUML, new Character((char) 235));
		entities.put(HtmlCharProperties.IGRAVE, new Character((char) 236));
		entities.put(HtmlCharProperties.IACUTE, new Character((char) 237));
		entities.put(HtmlCharProperties.ICIRC, new Character((char) 238));
		entities.put(HtmlCharProperties.IUML, new Character((char) 239));
		entities.put(HtmlCharProperties.ETH, new Character((char) 240));
		entities.put(HtmlCharProperties.NTILDE, new Character((char) 241));
		entities.put(HtmlCharProperties.OGRAVE, new Character((char) 242));
		entities.put(HtmlCharProperties.OACUTE, new Character((char) 243));
		entities.put(HtmlCharProperties.OCIRC, new Character((char) 244));
		entities.put(HtmlCharProperties.OTILDE, new Character((char) 245));
		entities.put(HtmlCharProperties.OUML, new Character((char) 246));
		entities.put(HtmlCharProperties.DIVIDE, new Character((char) 247));
		entities.put(HtmlCharProperties.OSLASH, new Character((char) 248));
		entities.put(HtmlCharProperties.UGRAVE, new Character((char) 249));
		entities.put(HtmlCharProperties.UACUTE, new Character((char) 250));
		entities.put(HtmlCharProperties.UCIRC, new Character((char) 251));
		entities.put(HtmlCharProperties.UUML, new Character((char) 252));
		entities.put(HtmlCharProperties.YACUTE, new Character((char) 253));
		entities.put(HtmlCharProperties.THORN, new Character((char) 254));
		entities.put(HtmlCharProperties.YUML, new Character((char) 255));
		entities.put(HtmlCharProperties.OELIG2, new Character((char) 338));
		entities.put(HtmlCharProperties.OELIG, new Character((char) 339));
		entities.put(HtmlCharProperties.SCARON2, new Character((char) 352));
		entities.put(HtmlCharProperties.SCARON, new Character((char) 353));
		entities.put(HtmlCharProperties.FNOF, new Character((char) 402));
		entities.put(HtmlCharProperties.CIRC, new Character((char) 710));
		entities.put(HtmlCharProperties.TILDE, new Character((char) 732));
		entities.put(HtmlCharProperties.ALPHA2, new Character((char) 913));
		entities.put(HtmlCharProperties.BETA2, new Character((char) 914));
		entities.put(HtmlCharProperties.GAMMA2, new Character((char) 915));
		entities.put(HtmlCharProperties.DELTA2, new Character((char) 916));
		entities.put(HtmlCharProperties.EPSILON2, new Character((char) 917));
		entities.put(HtmlCharProperties.ZETA2, new Character((char) 918));
		entities.put(HtmlCharProperties.ETA2, new Character((char) 919));
		entities.put(HtmlCharProperties.THETA2, new Character((char) 920));
		entities.put(HtmlCharProperties.IOTA2, new Character((char) 921));
		entities.put(HtmlCharProperties.KAPPA2, new Character((char) 922));
		entities.put(HtmlCharProperties.LAMBDA2, new Character((char) 923));
		entities.put(HtmlCharProperties.MU2, new Character((char) 924));
		entities.put(HtmlCharProperties.NU2, new Character((char) 925));
		entities.put(HtmlCharProperties.XI2, new Character((char) 926));
		entities.put(HtmlCharProperties.OMICRON2, new Character((char) 927));
		entities.put(HtmlCharProperties.PI2, new Character((char) 928));
		entities.put(HtmlCharProperties.RHO2, new Character((char) 929));
		entities.put(HtmlCharProperties.SIGMA2, new Character((char) 930));
		entities.put(HtmlCharProperties.SIGMAF2, new Character((char) 931));
		entities.put(HtmlCharProperties.TAU2, new Character((char) 932));
		entities.put(HtmlCharProperties.UPSILON2, new Character((char) 933));
		entities.put(HtmlCharProperties.PHI2, new Character((char) 934));
		entities.put(HtmlCharProperties.CHI2, new Character((char) 935));
		entities.put(HtmlCharProperties.PSI2, new Character((char) 936));
		entities.put(HtmlCharProperties.OMEGA2, new Character((char) 937));
		entities.put(HtmlCharProperties.ALPHA, new Character((char) 945));
		entities.put(HtmlCharProperties.BETA, new Character((char) 946));
		entities.put(HtmlCharProperties.GAMMA, new Character((char) 947));
		entities.put(HtmlCharProperties.DELTA, new Character((char) 948));
		entities.put(HtmlCharProperties.EPSILON, new Character((char) 949));
		entities.put(HtmlCharProperties.ZETA, new Character((char) 950));
		entities.put(HtmlCharProperties.ETA, new Character((char) 951));
		entities.put(HtmlCharProperties.THETA, new Character((char) 952));
		entities.put(HtmlCharProperties.IOTA, new Character((char) 953));
		entities.put(HtmlCharProperties.KAPPA, new Character((char) 954));
		entities.put(HtmlCharProperties.LAMBDA, new Character((char) 955));
		entities.put(HtmlCharProperties.MU, new Character((char) 956));
		entities.put(HtmlCharProperties.NU, new Character((char) 957));
		entities.put(HtmlCharProperties.XI, new Character((char) 958));
		entities.put(HtmlCharProperties.OMICRON, new Character((char) 959));
		entities.put(HtmlCharProperties.PI, new Character((char) 960));
		entities.put(HtmlCharProperties.RHO, new Character((char) 961));
		entities.put(HtmlCharProperties.SIGMA, new Character((char) 962));
		entities.put(HtmlCharProperties.SIGMAF, new Character((char) 963));
		entities.put(HtmlCharProperties.TAU, new Character((char) 964));
		entities.put(HtmlCharProperties.UPSILON, new Character((char) 965));
		entities.put(HtmlCharProperties.PHI, new Character((char) 966));
		entities.put(HtmlCharProperties.CHI, new Character((char) 967));
		entities.put(HtmlCharProperties.PSI, new Character((char) 968));
		entities.put(HtmlCharProperties.OMEGA, new Character((char) 969));
		entities.put(HtmlCharProperties.THETASYM, new Character((char) 977));
		entities.put(HtmlCharProperties.UPSIH, new Character((char) 978));
		entities.put(HtmlCharProperties.PIV, new Character((char) 982));
		entities.put(HtmlCharProperties.ENSP, new Character((char) 8194));
		entities.put(HtmlCharProperties.EMSP, new Character((char) 8195));
		entities.put(HtmlCharProperties.THINSP, new Character((char) 8201));
		entities.put(HtmlCharProperties.ZWNJ, new Character((char) 8204));
		entities.put(HtmlCharProperties.ZWJ, new Character((char) 8205));
		entities.put(HtmlCharProperties.LRM, new Character((char) 8206));
		entities.put(HtmlCharProperties.RLM, new Character((char) 8207));
		entities.put(HtmlCharProperties.NDASH, new Character((char) 8211));
		entities.put(HtmlCharProperties.MDASH, new Character((char) 8212));
		entities.put(HtmlCharProperties.LSQUO, new Character((char) 8216));
		entities.put(HtmlCharProperties.RSQUO, new Character((char) 8217));
		entities.put(HtmlCharProperties.SBQUO, new Character((char) 8218));
		entities.put(HtmlCharProperties.LDQUO, new Character((char) 8220));
		entities.put(HtmlCharProperties.RDQUO, new Character((char) 8221));
		entities.put(HtmlCharProperties.BDQUO, new Character((char) 8222));
		entities.put(HtmlCharProperties.DAGGER, new Character((char) 8224));
		entities.put(HtmlCharProperties.DAGGER2, new Character((char) 8225));
		entities.put(HtmlCharProperties.BULL, new Character((char) 8226));
		entities.put(HtmlCharProperties.HELLIP, new Character((char) 8230));
		entities.put(HtmlCharProperties.PERMIL, new Character((char) 8240));
		entities.put(HtmlCharProperties.PRIME, new Character((char) 8242));
		entities.put(HtmlCharProperties.PRIME2, new Character((char) 8243));
		entities.put(HtmlCharProperties.LSAQUO, new Character((char) 8249));
		entities.put(HtmlCharProperties.RSAQUO, new Character((char) 8250));
		entities.put(HtmlCharProperties.OLINE, new Character((char) 8254));
		entities.put(HtmlCharProperties.EURO, new Character((char) 8364));
		entities.put(HtmlCharProperties.IMAGE, new Character((char) 8465));
		entities.put(HtmlCharProperties.WEIERP, new Character((char) 8472));
		entities.put(HtmlCharProperties.REAL, new Character((char) 8476));
		entities.put(HtmlCharProperties.TRADE, new Character((char) 8482));
		entities.put(HtmlCharProperties.ALEFSYM, new Character((char) 8501));
		entities.put(HtmlCharProperties.LARR, new Character((char) 8592));
		entities.put(HtmlCharProperties.UARR, new Character((char) 8593));
		entities.put(HtmlCharProperties.RARR, new Character((char) 8594));
		entities.put(HtmlCharProperties.DARR, new Character((char) 8595));
		entities.put(HtmlCharProperties.HARR, new Character((char) 8596));
		entities.put(HtmlCharProperties.CRARR, new Character((char) 8629));
		entities.put(HtmlCharProperties.LARR2, new Character((char) 8656));
		entities.put(HtmlCharProperties.UARR2, new Character((char) 8657));
		entities.put(HtmlCharProperties.RARR2, new Character((char) 8658));
		entities.put(HtmlCharProperties.DARR2, new Character((char) 8659));
		entities.put(HtmlCharProperties.FORALL, new Character((char) 8704));
		entities.put(HtmlCharProperties.PART, new Character((char) 8706));
		entities.put(HtmlCharProperties.EXIST, new Character((char) 8707));
		entities.put(HtmlCharProperties.EMPTY, new Character((char) 8709));
		entities.put(HtmlCharProperties.NABLA, new Character((char) 8711));
		entities.put(HtmlCharProperties.ISIN, new Character((char) 8712));
		entities.put(HtmlCharProperties.NOTIN, new Character((char) 8713));
		entities.put(HtmlCharProperties.NI, new Character((char) 8715));
		entities.put(HtmlCharProperties.PROD, new Character((char) 8719));
		entities.put(HtmlCharProperties.SUM, new Character((char) 8721));
		entities.put(HtmlCharProperties.MINUS, new Character((char) 8722));
		entities.put(HtmlCharProperties.LOWAST, new Character((char) 8727));
		entities.put(HtmlCharProperties.RADIC, new Character((char) 8730));
		entities.put(HtmlCharProperties.PROP, new Character((char) 8733));
		entities.put(HtmlCharProperties.INFIN, new Character((char) 8734));
		entities.put(HtmlCharProperties.ANG, new Character((char) 8736));
		entities.put(HtmlCharProperties.AND, new Character((char) 8743));
		entities.put(HtmlCharProperties.OR, new Character((char) 8744));
		entities.put(HtmlCharProperties.CAP, new Character((char) 8745));
		entities.put(HtmlCharProperties.CUP, new Character((char) 8746));
		entities.put(HtmlCharProperties.INT, new Character((char) 8747));
		entities.put(HtmlCharProperties.THERE4, new Character((char) 8756));
		entities.put(HtmlCharProperties.SIM, new Character((char) 8764));
		entities.put(HtmlCharProperties.CONG, new Character((char) 8773));
		entities.put(HtmlCharProperties.ASYMP, new Character((char) 8776));
		entities.put(HtmlCharProperties.NE, new Character((char) 8800));
		entities.put(HtmlCharProperties.EQUIV, new Character((char) 8801));
		entities.put(HtmlCharProperties.LE, new Character((char) 8804));
		entities.put(HtmlCharProperties.GE, new Character((char) 8805));
		entities.put(HtmlCharProperties.SUB, new Character((char) 8834));
		entities.put(HtmlCharProperties.SUP, new Character((char) 8835));
		entities.put(HtmlCharProperties.NSUB, new Character((char) 8836));
		entities.put(HtmlCharProperties.SUBE, new Character((char) 8838));
		entities.put(HtmlCharProperties.SUPE, new Character((char) 8839));
		entities.put(HtmlCharProperties.OPLUS, new Character((char) 8853));
		entities.put(HtmlCharProperties.OTIMES, new Character((char) 8855));
		entities.put(HtmlCharProperties.PERP, new Character((char) 8869));
		entities.put(HtmlCharProperties.SDOT, new Character((char) 8901));
		entities.put(HtmlCharProperties.HARR2, new Character((char) 8960));
		entities.put(HtmlCharProperties.LCEIL, new Character((char) 8968));
		entities.put(HtmlCharProperties.RCEIL, new Character((char) 8969));
		entities.put(HtmlCharProperties.LFLOOR, new Character((char) 8970));
		entities.put(HtmlCharProperties.RFLOOR, new Character((char) 8971));
		entities.put(HtmlCharProperties.SPADES, new Character((char) 9824));
		entities.put(HtmlCharProperties.CLUBS, new Character((char) 9827));
		entities.put(HtmlCharProperties.HEARTS, new Character((char) 9829));
		entities.put(HtmlCharProperties.DIAMS, new Character((char) 9830));
		entities.put(HtmlCharProperties.LANG, new Character((char) 9001));
		entities.put(HtmlCharProperties.RANG, new Character((char) 9002));
		entities.put(HtmlCharProperties.LOZ, new Character((char) 9674));
		return entities;
	}
}
