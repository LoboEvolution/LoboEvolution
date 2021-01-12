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

package org.loboevolution.html;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.loboevolution.info.ElementInfo;

/**
 * <p>HTMLEntities class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLEntities {
	
	/** Constant ELEMENT_INFOS */
	public static final Map<HTMLTag, ElementInfo> ELEMENT_INFOS = new HashMap<>();
	/** Constant ENTITIES */
	public static final Map<Entities, Character> ENTITIES = new HashMap<>();
	
	static {
		final Map<Entities, Character> entities = ENTITIES;
		entities.put(Entities.amp, '&');
		entities.put(Entities.lt, '<');
		entities.put(Entities.gt, '>');
		entities.put(Entities.quot, '"');
		entities.put(Entities.nbsp, (char) 160);

		entities.put(Entities.lsquo, '\u2018');
		entities.put(Entities.rsquo, '\u2019');

		entities.put(Entities.frasl, (char) 47);
		entities.put(Entities.ndash, (char) 8211);
		entities.put(Entities.mdash, (char) 8212);
		entities.put(Entities.iexcl, (char) 161);
		entities.put(Entities.cent, (char) 162);
		entities.put(Entities.pound, (char) 163);
		entities.put(Entities.curren, (char) 164);
		entities.put(Entities.yen, (char) 165);
		entities.put(Entities.brvbar, (char) 166);
		entities.put(Entities.brkbar, (char) 166);
		entities.put(Entities.sect, (char) 167);
		entities.put(Entities.uml, (char) 168);
		entities.put(Entities.die, (char) 168);
		entities.put(Entities.copy, (char) 169);
		entities.put(Entities.ordf, (char) 170);
		entities.put(Entities.laquo, (char) 171);
		entities.put(Entities.not, (char) 172);
		entities.put(Entities.shy, (char) 173);
		entities.put(Entities.reg, (char) 174);
		entities.put(Entities.macr, (char) 175);
		entities.put(Entities.hibar, (char) 175);
		entities.put(Entities.deg, (char) 176);
		entities.put(Entities.plusmn, (char) 177);
		entities.put(Entities.sup2, (char) 178);
		entities.put(Entities.sup3, (char) 179);
		entities.put(Entities.acute, (char) 180);
		entities.put(Entities.micro, (char) 181);
		entities.put(Entities.para, (char) 182);
		entities.put(Entities.middot, (char) 183);
		entities.put(Entities.cedil, (char) 184);
		entities.put(Entities.sup1, (char) 185);
		entities.put(Entities.ordm, (char) 186);
		entities.put(Entities.raquo, (char) 187);
		entities.put(Entities.frac14, (char) 188);
		entities.put(Entities.frac12, (char) 189);
		entities.put(Entities.frac34, (char) 190);
		entities.put(Entities.iquest, (char) 191);
		entities.put(Entities.Agrave, (char) 192);
		entities.put(Entities.Aacute, (char) 193);
		entities.put(Entities.Acirc, (char) 194);
		entities.put(Entities.Atilde, (char) 195);
		entities.put(Entities.Auml, (char) 196);
		entities.put(Entities.Aring, (char) 197);
		entities.put(Entities.AElig, (char) 198);
		entities.put(Entities.Ccedil, (char) 199);
		entities.put(Entities.Egrave, (char) 200);
		entities.put(Entities.Eacute, (char) 201);
		entities.put(Entities.Ecirc, (char) 202);
		entities.put(Entities.Euml, (char) 203);
		entities.put(Entities.Igrave, (char) 204);
		entities.put(Entities.Iacute, (char) 205);
		entities.put(Entities.Icirc, (char) 206);
		entities.put(Entities.Iuml, (char) 207);
		entities.put(Entities.ETH, (char) 208);
		entities.put(Entities.Ntilde, (char) 209);
		entities.put(Entities.Ograve, (char) 210);
		entities.put(Entities.Oacute, (char) 211);
		entities.put(Entities.Ocirc, (char) 212);
		entities.put(Entities.Otilde, (char) 213);
		entities.put(Entities.Ouml, (char) 214);
		entities.put(Entities.times, (char) 215);
		entities.put(Entities.Oslash, (char) 216);
		entities.put(Entities.Ugrave, (char) 217);
		entities.put(Entities.Uacute, (char) 218);
		entities.put(Entities.Ucirc, (char) 219);
		entities.put(Entities.Uuml, (char) 220);
		entities.put(Entities.Yacute, (char) 221);
		entities.put(Entities.THORN, (char) 222);
		entities.put(Entities.szlig, (char) 223);
		entities.put(Entities.agrave, (char) 224);
		entities.put(Entities.aacute, (char) 225);
		entities.put(Entities.acirc, (char) 226);
		entities.put(Entities.atilde, (char) 227);
		entities.put(Entities.auml, (char) 228);
		entities.put(Entities.aring, (char) 229);
		entities.put(Entities.aelig, (char) 230);
		entities.put(Entities.ccedil, (char) 231);
		entities.put(Entities.egrave, (char) 232);
		entities.put(Entities.eacute, (char) 233);
		entities.put(Entities.ecirc, (char) 234);
		entities.put(Entities.euml, (char) 235);
		entities.put(Entities.igrave, (char) 236);
		entities.put(Entities.iacute, (char) 237);
		entities.put(Entities.icirc, (char) 238);
		entities.put(Entities.iuml, (char) 239);
		entities.put(Entities.eth, (char) 240);
		entities.put(Entities.ntilde, (char) 241);
		entities.put(Entities.ograve, (char) 242);
		entities.put(Entities.oacute, (char) 243);
		entities.put(Entities.ocirc, (char) 244);
		entities.put(Entities.otilde, (char) 245);
		entities.put(Entities.ouml, (char) 246);
		entities.put(Entities.divide, (char) 247);
		entities.put(Entities.oslash, (char) 248);
		entities.put(Entities.ugrave, (char) 249);
		entities.put(Entities.uacute, (char) 250);
		entities.put(Entities.ucirc, (char) 251);
		entities.put(Entities.uuml, (char) 252);
		entities.put(Entities.yacute, (char) 253);
		entities.put(Entities.thorn, (char) 254);
		entities.put(Entities.yuml, (char) 255);

		// symbols from http://de.selfhtml.org/html/referenz/zeichen.htm

		// greek letters
		entities.put(Entities.Alpha, (char) 913);
		entities.put(Entities.Beta, (char) 914);
		entities.put(Entities.Gamma, (char) 915);
		entities.put(Entities.Delta, (char) 916);
		entities.put(Entities.Epsilon, (char) 917);
		entities.put(Entities.Zeta, (char) 918);
		entities.put(Entities.Eta, (char) 919);
		entities.put(Entities.Theta, (char) 920);
		entities.put(Entities.Iota, (char) 921);
		entities.put(Entities.Kappa, (char) 922);
		entities.put(Entities.Lambda, (char) 923);
		entities.put(Entities.Mu, (char) 924);
		entities.put(Entities.Nu, (char) 925);
		entities.put(Entities.Xi, (char) 926);
		entities.put(Entities.Omicron, (char) 927);
		entities.put(Entities.Pi, (char) 928);
		entities.put(Entities.Rho, (char) 929);
		entities.put(Entities.Sigma, (char) 930);
		entities.put(Entities.Sigmaf, (char) 931);
		entities.put(Entities.Tau, (char) 932);
		entities.put(Entities.Upsilon, (char) 933);
		entities.put(Entities.Phi, (char) 934);
		entities.put(Entities.Chi, (char) 935);
		entities.put(Entities.Psi, (char) 936);
		entities.put(Entities.Omega, (char) 937);

		entities.put(Entities.alpha, (char) 945);
		entities.put(Entities.beta, (char) 946);
		entities.put(Entities.gamma, (char) 947);
		entities.put(Entities.delta, (char) 948);
		entities.put(Entities.epsilon, (char) 949);
		entities.put(Entities.zeta, (char) 950);
		entities.put(Entities.eta, (char) 951);
		entities.put(Entities.theta, (char) 952);
		entities.put(Entities.iota, (char) 953);
		entities.put(Entities.kappa, (char) 954);
		entities.put(Entities.lambda, (char) 955);
		entities.put(Entities.mu, (char) 956);
		entities.put(Entities.nu, (char) 957);
		entities.put(Entities.xi, (char) 958);
		entities.put(Entities.omicron, (char) 959);
		entities.put(Entities.pi, (char) 960);
		entities.put(Entities.rho, (char) 961);
		entities.put(Entities.sigma, (char) 962);
		entities.put(Entities.sigmaf, (char) 963);
		entities.put(Entities.tau, (char) 964);
		entities.put(Entities.upsilon, (char) 965);
		entities.put(Entities.phi, (char) 966);
		entities.put(Entities.chi, (char) 967);
		entities.put(Entities.psi, (char) 968);
		entities.put(Entities.omega, (char) 969);
		entities.put(Entities.thetasym, (char) 977);
		entities.put(Entities.upsih, (char) 978);
		entities.put(Entities.piv, (char) 982);

		// math symbols
		entities.put(Entities.forall, (char) 8704);
		entities.put(Entities.part, (char) 8706);
		entities.put(Entities.exist, (char) 8707);
		entities.put(Entities.empty, (char) 8709);
		entities.put(Entities.nabla, (char) 8711);
		entities.put(Entities.isin, (char) 8712);
		entities.put(Entities.notin, (char) 8713);
		entities.put(Entities.ni, (char) 8715);
		entities.put(Entities.prod, (char) 8719);
		entities.put(Entities.sum, (char) 8721);
		entities.put(Entities.minus, (char) 8722);
		entities.put(Entities.lowast, (char) 8727);
		entities.put(Entities.radic, (char) 8730);
		entities.put(Entities.prop, (char) 8733);
		entities.put(Entities.infin, (char) 8734);
		entities.put(Entities.ang, (char) 8736);
		entities.put(Entities.and, (char) 8743);
		entities.put(Entities.or, (char) 8744);
		entities.put(Entities.cap, (char) 8745);
		entities.put(Entities.cup, (char) 8746);
		entities.put(Entities.int_, (char) 8747);
		entities.put(Entities.there4, (char) 8756);
		entities.put(Entities.sim, (char) 8764);
		entities.put(Entities.cong, (char) 8773);
		entities.put(Entities.asymp, (char) 8776);
		entities.put(Entities.ne, (char) 8800);
		entities.put(Entities.equiv, (char) 8801);
		entities.put(Entities.le, (char) 8804);
		entities.put(Entities.ge, (char) 8805);
		entities.put(Entities.sub, (char) 8834);
		entities.put(Entities.sup, (char) 8835);
		entities.put(Entities.nsub, (char) 8836);
		entities.put(Entities.sube, (char) 8838);
		entities.put(Entities.supe, (char) 8839);
		entities.put(Entities.oplus, (char) 8853);
		entities.put(Entities.otimes, (char) 8855);
		entities.put(Entities.perp, (char) 8869);
		entities.put(Entities.sdot, (char) 8901);
		entities.put(Entities.loz, (char) 9674);

		// technical symbols
		entities.put(Entities.lceil, (char) 8968);
		entities.put(Entities.rceil, (char) 8969);
		entities.put(Entities.lfloor, (char) 8970);
		entities.put(Entities.rfloor, (char) 8971);
		entities.put(Entities.lang, (char) 9001);
		entities.put(Entities.rang, (char) 9002);

		// arrow symbols
		entities.put(Entities.larr, (char) 8592);
		entities.put(Entities.uarr, (char) 8593);
		entities.put(Entities.rarr, (char) 8594);
		entities.put(Entities.darr, (char) 8595);
		entities.put(Entities.harr, (char) 8596);
		entities.put(Entities.crarr, (char) 8629);
		entities.put(Entities.lArr, (char) 8656);
		entities.put(Entities.uArr, (char) 8657);
		entities.put(Entities.rArr, (char) 8658);
		entities.put(Entities.dArr, (char) 8659);
		entities.put(Entities.hArr, (char) 8960);

		// divers symbols
		entities.put(Entities.bull, (char) 8226);
		entities.put(Entities.prime, (char) 8242);
		entities.put(Entities.Prime, (char) 8243);
		entities.put(Entities.oline, (char) 8254);
		entities.put(Entities.weierp, (char) 8472);
		entities.put(Entities.image, (char) 8465);
		entities.put(Entities.real, (char) 8476);
		entities.put(Entities.trade, (char) 8482);
		entities.put(Entities.euro, (char) 8364);
		entities.put(Entities.alefsym, (char) 8501);
		entities.put(Entities.spades, (char) 9824);
		entities.put(Entities.clubs, (char) 9827);
		entities.put(Entities.hearts, (char) 9829);
		entities.put(Entities.diams, (char) 9830);

		// ext lat symbols
		entities.put(Entities.OElig, (char) 338);
		entities.put(Entities.oelig, (char) 339);
		entities.put(Entities.Scaron, (char) 352);
		entities.put(Entities.scaron, (char) 353);
		entities.put(Entities.fnof, (char) 402);

		// interpunction
		entities.put(Entities.ensp, (char) 8194);
		entities.put(Entities.emsp, (char) 8195);
		entities.put(Entities.thinsp, (char) 8201);
		entities.put(Entities.zwnj, (char) 8204);
		entities.put(Entities.zwj, (char) 8205);
		entities.put(Entities.lrm, (char) 8206);
		entities.put(Entities.rlm, (char) 8207);

		entities.put(Entities.sbquo, (char) 8218);
		entities.put(Entities.ldquo, (char) 8220);
		entities.put(Entities.rdquo, (char) 8221);
		entities.put(Entities.bdquo, (char) 8222);
		entities.put(Entities.dagger, (char) 8224);
		entities.put(Entities.Dagger, (char) 8225);
		entities.put(Entities.hellip, (char) 8230);
		entities.put(Entities.permil, (char) 8240);
		entities.put(Entities.lsaquo, (char) 8249);
		entities.put(Entities.rsaquo, (char) 8250);
		entities.put(Entities.circ, (char) 710);
		entities.put(Entities.tilde, (char) 732);

		final ElementInfo optionalEndElement = new ElementInfo(true, ElementInfo.END_ELEMENT_OPTIONAL);
		final ElementInfo forbiddenEndElement = new ElementInfo(false, ElementInfo.END_ELEMENT_FORBIDDEN);
		final ElementInfo only = new ElementInfo(true, ElementInfo.END_ELEMENT_REQUIRED, null, true);
		final ElementInfo onlyText = new ElementInfo(false, ElementInfo.END_ELEMENT_REQUIRED, false);
		final ElementInfo onlyTextDE = new ElementInfo(false, ElementInfo.END_ELEMENT_REQUIRED, true);
		
		final Set<HTMLTag> tableCellStopElements = new HashSet<>();
		tableCellStopElements.add(HTMLTag.TH);
		tableCellStopElements.add(HTMLTag.TD);
		tableCellStopElements.add(HTMLTag.TR);
		final ElementInfo tableCellElement = new ElementInfo(true, ElementInfo.END_ELEMENT_OPTIONAL, tableCellStopElements);

		final Set<HTMLTag> headStopElements = new HashSet<>();
		headStopElements.add(HTMLTag.BODY);
		headStopElements.add(HTMLTag.DIV);
		headStopElements.add(HTMLTag.SPAN);
		headStopElements.add(HTMLTag.TABLE);
		final ElementInfo headElement = new ElementInfo(true, ElementInfo.END_ELEMENT_OPTIONAL, headStopElements);

		final Set<HTMLTag> optionStopElements = new HashSet<>();
		optionStopElements.add(HTMLTag.OPTION);
		optionStopElements.add(HTMLTag.SELECT);
		final ElementInfo optionElement = new ElementInfo(true, ElementInfo.END_ELEMENT_OPTIONAL, optionStopElements);

		final Set<HTMLTag> paragraphStopElements = new HashSet<>();
		paragraphStopElements.add(HTMLTag.P);
		paragraphStopElements.add(HTMLTag.DIV);
		paragraphStopElements.add(HTMLTag.TABLE);
		paragraphStopElements.add(HTMLTag.PRE);
		paragraphStopElements.add(HTMLTag.UL);
		paragraphStopElements.add(HTMLTag.OL);
		final ElementInfo paragraphElement = new ElementInfo(true, ElementInfo.END_ELEMENT_OPTIONAL, paragraphStopElements);

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
		elementInfos.put(HTMLTag.TH, tableCellElement);
		elementInfos.put(HTMLTag.TD, tableCellElement);
		elementInfos.put(HTMLTag.HEAD, headElement);
		elementInfos.put(HTMLTag.OPTION, optionElement);
		elementInfos.put(HTMLTag.A, optionalEndElement);
		elementInfos.put(HTMLTag.ANCHOR, optionalEndElement);
	}

}
