/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.html;

import org.loboevolution.info.ElementInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>HTMLEntities class.</p>
 */
public class HTMLEntities {
	
	/** Constant ELEMENT_INFOS */
	public static final Map<HTMLTag, ElementInfo> ELEMENT_INFOS = new HashMap<>();
	/** Constant ENTITIES */
	public static final Map<Entities, Character> ENTITIES = new HashMap<>();
	
	static {
		final Map<Entities, Character> entities = ENTITIES;
		entities.put(Entities.AMP, '&');
		entities.put(Entities.LT, '<');
		entities.put(Entities.GT, '>');
		entities.put(Entities.QUOT, '"');
		entities.put(Entities.NBSP, (char) 160);

		entities.put(Entities.LSQUO, '‘');
		entities.put(Entities.RSQUO, '’');

		entities.put(Entities.FRASL, (char) 47);
		entities.put(Entities.NDASH, (char) 8211);
		entities.put(Entities.MDASH, (char) 8212);
		entities.put(Entities.IEXCL, (char) 161);
		entities.put(Entities.CENT, (char) 162);
		entities.put(Entities.POUND, (char) 163);
		entities.put(Entities.CURREN, (char) 164);
		entities.put(Entities.YEN, (char) 165);
		entities.put(Entities.BRVBAR, (char) 166);
		entities.put(Entities.BRKBAR, (char) 166);
		entities.put(Entities.SECT, (char) 167);
		entities.put(Entities.UML, (char) 168);
		entities.put(Entities.DIE, (char) 168);
		entities.put(Entities.COPY, (char) 169);
		entities.put(Entities.ORDF, (char) 170);
		entities.put(Entities.LAQUO, (char) 171);
		entities.put(Entities.NOT, (char) 172);
		entities.put(Entities.SHY, (char) 173);
		entities.put(Entities.REG, (char) 174);
		entities.put(Entities.MACR, (char) 175);
		entities.put(Entities.HIBAR, (char) 175);
		entities.put(Entities.DEG, (char) 176);
		entities.put(Entities.PLUSMN, (char) 177);
		entities.put(Entities.SUP2, (char) 178);
		entities.put(Entities.SUP3, (char) 179);
		entities.put(Entities.ACUTE, (char) 180);
		entities.put(Entities.MICRO, (char) 181);
		entities.put(Entities.PARA, (char) 182);
		entities.put(Entities.MIDDOT, (char) 183);
		entities.put(Entities.CEDIL, (char) 184);
		entities.put(Entities.SUP1, (char) 185);
		entities.put(Entities.ORDM, (char) 186);
		entities.put(Entities.RAQUO, (char) 187);
		entities.put(Entities.FRAC14, (char) 188);
		entities.put(Entities.FRAC12, (char) 189);
		entities.put(Entities.FRAC34, (char) 190);
		entities.put(Entities.IQUEST, (char) 191);
		entities.put(Entities.AGRAVE_UP, (char) 192);
		entities.put(Entities.AACUTE_UP, (char) 193);
		entities.put(Entities.ACIRC_UP, (char) 194);
		entities.put(Entities.ATILDE_UP, (char) 195);
		entities.put(Entities.AUML_UP, (char) 196);
		entities.put(Entities.ARING_UP, (char) 197);
		entities.put(Entities.AELIG_UP, (char) 198);
		entities.put(Entities.CCEDIL_UP, (char) 199);
		entities.put(Entities.EGRAVE_UP, (char) 200);
		entities.put(Entities.EACUTE_UP, (char) 201);
		entities.put(Entities.ECIRC_UP, (char) 202);
		entities.put(Entities.EUML_UP, (char) 203);
		entities.put(Entities.IGRAVE_UP, (char) 204);
		entities.put(Entities.IACUTE_UP, (char) 205);
		entities.put(Entities.ICIRC_UP, (char) 206);
		entities.put(Entities.IUML_UP, (char) 207);
		entities.put(Entities.ETH_UP, (char) 208);
		entities.put(Entities.NTILDE_UP, (char) 209);
		entities.put(Entities.OGRAVE_UP, (char) 210);
		entities.put(Entities.OACUTE_UP, (char) 211);
		entities.put(Entities.OCIRC_UP, (char) 212);
		entities.put(Entities.OTILDE_UP, (char) 213);
		entities.put(Entities.OUML_UP, (char) 214);
		entities.put(Entities.TIMES, (char) 215);
		entities.put(Entities.OSLASH_UP, (char) 216);

		entities.put(Entities.UACUTE_UP, (char) 218);
		entities.put(Entities.UCIRC_UP, (char) 219);
		entities.put(Entities.UUML_UP, (char) 220);
		entities.put(Entities.YACUTE_UP, (char) 221);
		entities.put(Entities.THORN_UP, (char) 222);
		entities.put(Entities.SZLIG, (char) 223);
		entities.put(Entities.AGRAVE, (char) 224);
		entities.put(Entities.AACUTE, (char) 225);
		entities.put(Entities.ACIRC, (char) 226);
		entities.put(Entities.ATILDE, (char) 227);
		entities.put(Entities.AUML, (char) 228);
		entities.put(Entities.ARING, (char) 229);
		entities.put(Entities.AELIG, (char) 230);
		entities.put(Entities.CCEDIL, (char) 231);
		entities.put(Entities.EGRAVE, (char) 232);
		entities.put(Entities.EACUTE, (char) 233);
		entities.put(Entities.ECIRC, (char) 234);
		entities.put(Entities.EUML, (char) 235);
		entities.put(Entities.IGRAVE, (char) 236);
		entities.put(Entities.IACUTE, (char) 237);
		entities.put(Entities.ICIRC, (char) 238);
		entities.put(Entities.IUML, (char) 239);
		entities.put(Entities.ETH, (char) 240);
		entities.put(Entities.NTILDE, (char) 241);
		entities.put(Entities.OGRAVE, (char) 242);
		entities.put(Entities.OACUTE, (char) 243);
		entities.put(Entities.OCIRC, (char) 244);
		entities.put(Entities.OTILDE, (char) 245);
		entities.put(Entities.OUML, (char) 246);
		entities.put(Entities.DIVIDE, (char) 247);
		entities.put(Entities.OSLASH, (char) 248);
		entities.put(Entities.UGRAVE, (char) 249);
		entities.put(Entities.UACUTE, (char) 250);
		entities.put(Entities.UCIRC, (char) 251);
		entities.put(Entities.UUML, (char) 252);
		entities.put(Entities.YACUTE, (char) 253);
		entities.put(Entities.THORN, (char) 254);
		entities.put(Entities.YUML, (char) 255);

		// greek letters
		entities.put(Entities.ALPHA_UP, (char) 913);
		entities.put(Entities.BETA_UP, (char) 914);
		entities.put(Entities.GAMMA_UP, (char) 915);
		entities.put(Entities.DELTA_UP, (char) 916);
		entities.put(Entities.EPSILON_UP, (char) 917);
		entities.put(Entities.ZETA_UP, (char) 918);
		entities.put(Entities.ETA_UP, (char) 919);
		entities.put(Entities.THETA_UP, (char) 920);
		entities.put(Entities.IOTA_UP, (char) 921);
		entities.put(Entities.KAPPA_UP, (char) 922);
		entities.put(Entities.LAMBDA_UP, (char) 923);
		entities.put(Entities.MU_UP, (char) 924);
		entities.put(Entities.NU_UP, (char) 925);
		entities.put(Entities.XI_UP, (char) 926);
		entities.put(Entities.OMICRON_UP, (char) 927);
		entities.put(Entities.PI_UP, (char) 928);
		entities.put(Entities.RHO_UP, (char) 929);
		entities.put(Entities.SIGMA_UP, (char) 930);
		entities.put(Entities.SIGMAF_UP, (char) 931);
		entities.put(Entities.TAU_UP, (char) 932);
		entities.put(Entities.UPSILON_UP, (char) 933);
		entities.put(Entities.PHI_UP, (char) 934);
		entities.put(Entities.CHI_UP, (char) 935);
		entities.put(Entities.PSI_UP, (char) 936);
		entities.put(Entities.OMEGA_UP, (char) 937);

		entities.put(Entities.ALPHA, (char) 945);
		entities.put(Entities.BETA, (char) 946);
		entities.put(Entities.GAMMA, (char) 947);
		entities.put(Entities.DELTA, (char) 948);
		entities.put(Entities.EPSILON, (char) 949);
		entities.put(Entities.ZETA, (char) 950);
		entities.put(Entities.ETA, (char) 951);
		entities.put(Entities.THETA, (char) 952);
		entities.put(Entities.IOTA, (char) 953);
		entities.put(Entities.KAPPA, (char) 954);
		entities.put(Entities.LAMBDA, (char) 955);
		entities.put(Entities.MU, (char) 956);
		entities.put(Entities.NU, (char) 957);
		entities.put(Entities.XI, (char) 958);
		entities.put(Entities.OMICRON, (char) 959);
		entities.put(Entities.PI, (char) 960);
		entities.put(Entities.RHO, (char) 961);
		entities.put(Entities.SIGMA, (char) 962);
		entities.put(Entities.SIGMAF, (char) 963);
		entities.put(Entities.TAU, (char) 964);
		entities.put(Entities.UPSILON, (char) 965);
		entities.put(Entities.PHI, (char) 966);
		entities.put(Entities.CHI, (char) 967);
		entities.put(Entities.PSI, (char) 968);
		entities.put(Entities.OMEGA, (char) 969);
		entities.put(Entities.THETASYM, (char) 977);
		entities.put(Entities.UPSIH, (char) 978);
		entities.put(Entities.PIV, (char) 982);

		// math symbols
		entities.put(Entities.FORALL, (char) 8704);
		entities.put(Entities.PART, (char) 8706);
		entities.put(Entities.EXIST, (char) 8707);
		entities.put(Entities.EMPTY, (char) 8709);
		entities.put(Entities.NABLA, (char) 8711);
		entities.put(Entities.ISIN, (char) 8712);
		entities.put(Entities.NOTIN, (char) 8713);
		entities.put(Entities.NI, (char) 8715);
		entities.put(Entities.PROD, (char) 8719);
		entities.put(Entities.SUM, (char) 8721);
		entities.put(Entities.MINUS, (char) 8722);
		entities.put(Entities.LOWAST, (char) 8727);
		entities.put(Entities.RADIC, (char) 8730);
		entities.put(Entities.PROP, (char) 8733);
		entities.put(Entities.INFIN, (char) 8734);
		entities.put(Entities.ANG, (char) 8736);
		entities.put(Entities.AND, (char) 8743);
		entities.put(Entities.OR, (char) 8744);
		entities.put(Entities.CAP, (char) 8745);
		entities.put(Entities.CUP, (char) 8746);
		entities.put(Entities.INT, (char) 8747);
		entities.put(Entities.THERE4, (char) 8756);
		entities.put(Entities.SIM, (char) 8764);
		entities.put(Entities.CONG, (char) 8773);
		entities.put(Entities.ASYMP, (char) 8776);
		entities.put(Entities.NE, (char) 8800);
		entities.put(Entities.EQUIV, (char) 8801);
		entities.put(Entities.LE, (char) 8804);
		entities.put(Entities.GE, (char) 8805);
		entities.put(Entities.SUB, (char) 8834);
		entities.put(Entities.SUP, (char) 8835);
		entities.put(Entities.NSUB, (char) 8836);
		entities.put(Entities.SUBE, (char) 8838);
		entities.put(Entities.SUPE, (char) 8839);
		entities.put(Entities.OPLUS, (char) 8853);
		entities.put(Entities.OTIMES, (char) 8855);
		entities.put(Entities.PERP, (char) 8869);
		entities.put(Entities.SDOT, (char) 8901);
		entities.put(Entities.LOZ, (char) 9674);

		// technical symbols
		entities.put(Entities.LCEIL, (char) 8968);
		entities.put(Entities.RCEIL, (char) 8969);
		entities.put(Entities.LFLOOR, (char) 8970);
		entities.put(Entities.RFLOOR, (char) 8971);
		entities.put(Entities.LANG, (char) 9001);
		entities.put(Entities.RANG, (char) 9002);

		// arrow symbols
		entities.put(Entities.LARR, (char) 8592);
		entities.put(Entities.UARR, (char) 8593);
		entities.put(Entities.RARR, (char) 8594);
		entities.put(Entities.DARR, (char) 8595);
		entities.put(Entities.HARR, (char) 8596);
		entities.put(Entities.CRARR, (char) 8629);
		entities.put(Entities.LARR_UP, (char) 8656);
		entities.put(Entities.UARR_UP, (char) 8657);
		entities.put(Entities.RARR_UP, (char) 8658);
		entities.put(Entities.DARR_UP, (char) 8659);
		entities.put(Entities.HARR_UP, (char) 8960);

		// divers symbols
		entities.put(Entities.BULL, (char) 8226);
		entities.put(Entities.PRIME, (char) 8242);
		entities.put(Entities.PRIME_UP, (char) 8243);
		entities.put(Entities.OLINE, (char) 8254);
		entities.put(Entities.WEIERP_UP, (char) 8472);
		entities.put(Entities.IMAGE, (char) 8465);
		entities.put(Entities.REAL, (char) 8476);
		entities.put(Entities.TRADE, (char) 8482);
		entities.put(Entities.EURO, (char) 8364);
		entities.put(Entities.ALEFSYM, (char) 8501);
		entities.put(Entities.SPADES, (char) 9824);
		entities.put(Entities.CLUBS, (char) 9827);
		entities.put(Entities.HEARTS, (char) 9829);
		entities.put(Entities.DIAMS, (char) 9830);

		// ext lat symbols
		entities.put(Entities.OELIG_UP, (char) 338);
		entities.put(Entities.OELIG, (char) 339);
		entities.put(Entities.SCARON_UP, (char) 352);
		entities.put(Entities.SCARON, (char) 353);
		entities.put(Entities.FNOF, (char) 402);

		// interpunction
		entities.put(Entities.ENSP, (char) 8194);
		entities.put(Entities.EMSP, (char) 8195);
		entities.put(Entities.THINSP, (char) 8201);
		entities.put(Entities.ZWNJ, (char) 8204);
		entities.put(Entities.ZWJ, (char) 8205);
		entities.put(Entities.LRM, (char) 8206);
		entities.put(Entities.RLM, (char) 8207);

		entities.put(Entities.SBQUO, (char) 8218);
		entities.put(Entities.LDQUO, (char) 8220);
		entities.put(Entities.RDQUO, (char) 8221);
		entities.put(Entities.BDQUO, (char) 8222);
		entities.put(Entities.DAGGER, (char) 8224);
		entities.put(Entities.DAGGER_UP, (char) 8225);
		entities.put(Entities.HELLIP, (char) 8230);
		entities.put(Entities.PERMIL, (char) 8240);
		entities.put(Entities.LSAQUO, (char) 8249);
		entities.put(Entities.RSAQUO, (char) 8250);
		entities.put(Entities.CIRC, (char) 710);
		entities.put(Entities.TILDE, (char) 732);

		final ElementInfo optionalEndElement = ElementInfo.builder()
				.childElementOk(true)
				.endElementType(ElementInfo.END_ELEMENT_OPTIONAL)
				.stopTags(null)
				.noScriptElement(false)
				.decodeEntities(true)
				.build();

		final ElementInfo forbiddenEndElement = ElementInfo.builder()
				.childElementOk(false)
				.endElementType(ElementInfo.END_ELEMENT_FORBIDDEN)
				.stopTags(null)
				.noScriptElement(false)
				.decodeEntities(true)
				.build();

		final ElementInfo onlyText = ElementInfo.builder()
				.childElementOk(false)
				.endElementType(ElementInfo.END_ELEMENT_REQUIRED)
				.stopTags(null)
				.noScriptElement(false)
				.decodeEntities(false)
				.build();

		final ElementInfo onlyTextDE = ElementInfo.builder()
				.childElementOk(false)
				.endElementType(ElementInfo.END_ELEMENT_REQUIRED)
				.stopTags(null)
				.noScriptElement(false)
				.decodeEntities(true)
				.build();

		final ElementInfo only = ElementInfo.builder()
				.childElementOk(true)
				.endElementType(ElementInfo.END_ELEMENT_REQUIRED)
				.stopTags(null)
				.noScriptElement(true)
				.decodeEntities(true)
				.build();


		final Set<HTMLTag> tableCellStopElements = new HashSet<>();
		tableCellStopElements.add(HTMLTag.TH);
		tableCellStopElements.add(HTMLTag.TD);
		tableCellStopElements.add(HTMLTag.TR);
		final ElementInfo tableCellElement = ElementInfo.builder()
				.childElementOk(true)
				.endElementType(ElementInfo.END_ELEMENT_OPTIONAL)
				.stopTags(tableCellStopElements)
				.noScriptElement(false)
				.decodeEntities(true)
				.build();

		final Set<HTMLTag> headStopElements = new HashSet<>();
		headStopElements.add(HTMLTag.BODY);
		headStopElements.add(HTMLTag.DIV);
		headStopElements.add(HTMLTag.SPAN);
		headStopElements.add(HTMLTag.TABLE);
		final ElementInfo headElement = ElementInfo.builder()
				.childElementOk(true)
				.endElementType(ElementInfo.END_ELEMENT_OPTIONAL)
				.stopTags(headStopElements)
				.noScriptElement(false)
				.decodeEntities(true)
				.build();

		final Set<HTMLTag> optionStopElements = new HashSet<>();
		optionStopElements.add(HTMLTag.OPTION);
		optionStopElements.add(HTMLTag.SELECT);

		final ElementInfo optionElement = ElementInfo.builder()
				.childElementOk(true)
				.endElementType(ElementInfo.END_ELEMENT_REQUIRED)
				.stopTags(optionStopElements)
				.noScriptElement(false)
				.decodeEntities(true)
				.build();

		final Set<HTMLTag> paragraphStopElements = new HashSet<>();
		paragraphStopElements.add(HTMLTag.P);
		paragraphStopElements.add(HTMLTag.DIV);
		paragraphStopElements.add(HTMLTag.TABLE);
		paragraphStopElements.add(HTMLTag.PRE);
		paragraphStopElements.add(HTMLTag.UL);
		paragraphStopElements.add(HTMLTag.OL);

		final ElementInfo paragraphElement = ElementInfo.builder()
				.childElementOk(true)
				.endElementType(ElementInfo.END_ELEMENT_REQUIRED)
				.stopTags(paragraphStopElements)
				.noScriptElement(false)
				.decodeEntities(true)
				.build();

		final Map<HTMLTag, ElementInfo> elementInfos = ELEMENT_INFOS;
		elementInfos.put(HTMLTag.NOSCRIPT, only);
		elementInfos.put(HTMLTag.SCRIPT, onlyText);
		elementInfos.put(HTMLTag.STYLE, onlyText);
		elementInfos.put(HTMLTag.TEXTAREA, onlyTextDE);
		elementInfos.put(HTMLTag.IMG, forbiddenEndElement);
		elementInfos.put(HTMLTag.META, forbiddenEndElement);
		elementInfos.put(HTMLTag.LINK, forbiddenEndElement);
		elementInfos.put(HTMLTag.BASE, forbiddenEndElement);
		elementInfos.put(HTMLTag.INPUT, forbiddenEndElement);
		elementInfos.put(HTMLTag.BR, forbiddenEndElement);
		elementInfos.put(HTMLTag.HR, forbiddenEndElement);
		elementInfos.put(HTMLTag.EMBED, forbiddenEndElement);
		elementInfos.put(HTMLTag.SPACER, forbiddenEndElement);

		elementInfos.put(HTMLTag.P, paragraphElement);
		elementInfos.put(HTMLTag.LI, optionalEndElement);
		elementInfos.put(HTMLTag.DT, optionalEndElement);
		elementInfos.put(HTMLTag.DD, optionalEndElement);
		elementInfos.put(HTMLTag.TR, optionalEndElement);
		elementInfos.put(HTMLTag.DETAILS, optionalEndElement);

		elementInfos.put(HTMLTag.TH, tableCellElement);
		elementInfos.put(HTMLTag.TD, tableCellElement);
		elementInfos.put(HTMLTag.HEAD, headElement);
		elementInfos.put(HTMLTag.OPTION, optionElement);
		elementInfos.put(HTMLTag.A, optionalEndElement);
		elementInfos.put(HTMLTag.ANCHOR, optionalEndElement);
	}
}
