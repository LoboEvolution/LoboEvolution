package org.loboevolution.html;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.loboevolution.info.ElementInfo;

public class HTMLObject {
	
	public static final Map<HTMLTag, ElementInfo> ELEMENT_INFOS = new HashMap<HTMLTag, ElementInfo>();
	public static final Map<Entities, Character> ENTITIES = new HashMap<Entities, Character>();
	
	static {
		final Map<Entities, Character> entities = ENTITIES;
		entities.put(Entities.amp, new Character('&'));
		entities.put(Entities.lt, new Character('<'));
		entities.put(Entities.gt, new Character('>'));
		entities.put(Entities.quot, new Character('"'));
		entities.put(Entities.nbsp, new Character((char) 160));

		entities.put(Entities.lsquo, new Character('\u2018'));
		entities.put(Entities.rsquo, new Character('\u2019'));

		entities.put(Entities.frasl, new Character((char) 47));
		entities.put(Entities.ndash, new Character((char) 8211));
		entities.put(Entities.mdash, new Character((char) 8212));
		entities.put(Entities.iexcl, new Character((char) 161));
		entities.put(Entities.cent, new Character((char) 162));
		entities.put(Entities.pound, new Character((char) 163));
		entities.put(Entities.curren, new Character((char) 164));
		entities.put(Entities.yen, new Character((char) 165));
		entities.put(Entities.brvbar, new Character((char) 166));
		entities.put(Entities.brkbar, new Character((char) 166));
		entities.put(Entities.sect, new Character((char) 167));
		entities.put(Entities.uml, new Character((char) 168));
		entities.put(Entities.die, new Character((char) 168));
		entities.put(Entities.copy, new Character((char) 169));
		entities.put(Entities.ordf, new Character((char) 170));
		entities.put(Entities.laquo, new Character((char) 171));
		entities.put(Entities.not, new Character((char) 172));
		entities.put(Entities.shy, new Character((char) 173));
		entities.put(Entities.reg, new Character((char) 174));
		entities.put(Entities.macr, new Character((char) 175));
		entities.put(Entities.hibar, new Character((char) 175));
		entities.put(Entities.deg, new Character((char) 176));
		entities.put(Entities.plusmn, new Character((char) 177));
		entities.put(Entities.sup2, new Character((char) 178));
		entities.put(Entities.sup3, new Character((char) 179));
		entities.put(Entities.acute, new Character((char) 180));
		entities.put(Entities.micro, new Character((char) 181));
		entities.put(Entities.para, new Character((char) 182));
		entities.put(Entities.middot, new Character((char) 183));
		entities.put(Entities.cedil, new Character((char) 184));
		entities.put(Entities.sup1, new Character((char) 185));
		entities.put(Entities.ordm, new Character((char) 186));
		entities.put(Entities.raquo, new Character((char) 187));
		entities.put(Entities.frac14, new Character((char) 188));
		entities.put(Entities.frac12, new Character((char) 189));
		entities.put(Entities.frac34, new Character((char) 190));
		entities.put(Entities.iquest, new Character((char) 191));
		entities.put(Entities.Agrave, new Character((char) 192));
		entities.put(Entities.Aacute, new Character((char) 193));
		entities.put(Entities.Acirc, new Character((char) 194));
		entities.put(Entities.Atilde, new Character((char) 195));
		entities.put(Entities.Auml, new Character((char) 196));
		entities.put(Entities.Aring, new Character((char) 197));
		entities.put(Entities.AElig, new Character((char) 198));
		entities.put(Entities.Ccedil, new Character((char) 199));
		entities.put(Entities.Egrave, new Character((char) 200));
		entities.put(Entities.Eacute, new Character((char) 201));
		entities.put(Entities.Ecirc, new Character((char) 202));
		entities.put(Entities.Euml, new Character((char) 203));
		entities.put(Entities.Igrave, new Character((char) 204));
		entities.put(Entities.Iacute, new Character((char) 205));
		entities.put(Entities.Icirc, new Character((char) 206));
		entities.put(Entities.Iuml, new Character((char) 207));
		entities.put(Entities.ETH, new Character((char) 208));
		entities.put(Entities.Ntilde, new Character((char) 209));
		entities.put(Entities.Ograve, new Character((char) 210));
		entities.put(Entities.Oacute, new Character((char) 211));
		entities.put(Entities.Ocirc, new Character((char) 212));
		entities.put(Entities.Otilde, new Character((char) 213));
		entities.put(Entities.Ouml, new Character((char) 214));
		entities.put(Entities.times, new Character((char) 215));
		entities.put(Entities.Oslash, new Character((char) 216));
		entities.put(Entities.Ugrave, new Character((char) 217));
		entities.put(Entities.Uacute, new Character((char) 218));
		entities.put(Entities.Ucirc, new Character((char) 219));
		entities.put(Entities.Uuml, new Character((char) 220));
		entities.put(Entities.Yacute, new Character((char) 221));
		entities.put(Entities.THORN, new Character((char) 222));
		entities.put(Entities.szlig, new Character((char) 223));
		entities.put(Entities.agrave, new Character((char) 224));
		entities.put(Entities.aacute, new Character((char) 225));
		entities.put(Entities.acirc, new Character((char) 226));
		entities.put(Entities.atilde, new Character((char) 227));
		entities.put(Entities.auml, new Character((char) 228));
		entities.put(Entities.aring, new Character((char) 229));
		entities.put(Entities.aelig, new Character((char) 230));
		entities.put(Entities.ccedil, new Character((char) 231));
		entities.put(Entities.egrave, new Character((char) 232));
		entities.put(Entities.eacute, new Character((char) 233));
		entities.put(Entities.ecirc, new Character((char) 234));
		entities.put(Entities.euml, new Character((char) 235));
		entities.put(Entities.igrave, new Character((char) 236));
		entities.put(Entities.iacute, new Character((char) 237));
		entities.put(Entities.icirc, new Character((char) 238));
		entities.put(Entities.iuml, new Character((char) 239));
		entities.put(Entities.eth, new Character((char) 240));
		entities.put(Entities.ntilde, new Character((char) 241));
		entities.put(Entities.ograve, new Character((char) 242));
		entities.put(Entities.oacute, new Character((char) 243));
		entities.put(Entities.ocirc, new Character((char) 244));
		entities.put(Entities.otilde, new Character((char) 245));
		entities.put(Entities.ouml, new Character((char) 246));
		entities.put(Entities.divide, new Character((char) 247));
		entities.put(Entities.oslash, new Character((char) 248));
		entities.put(Entities.ugrave, new Character((char) 249));
		entities.put(Entities.uacute, new Character((char) 250));
		entities.put(Entities.ucirc, new Character((char) 251));
		entities.put(Entities.uuml, new Character((char) 252));
		entities.put(Entities.yacute, new Character((char) 253));
		entities.put(Entities.thorn, new Character((char) 254));
		entities.put(Entities.yuml, new Character((char) 255));

		// symbols from http://de.selfhtml.org/html/referenz/zeichen.htm

		// greek letters
		entities.put(Entities.Alpha, new Character((char) 913));
		entities.put(Entities.Beta, new Character((char) 914));
		entities.put(Entities.Gamma, new Character((char) 915));
		entities.put(Entities.Delta, new Character((char) 916));
		entities.put(Entities.Epsilon, new Character((char) 917));
		entities.put(Entities.Zeta, new Character((char) 918));
		entities.put(Entities.Eta, new Character((char) 919));
		entities.put(Entities.Theta, new Character((char) 920));
		entities.put(Entities.Iota, new Character((char) 921));
		entities.put(Entities.Kappa, new Character((char) 922));
		entities.put(Entities.Lambda, new Character((char) 923));
		entities.put(Entities.Mu, new Character((char) 924));
		entities.put(Entities.Nu, new Character((char) 925));
		entities.put(Entities.Xi, new Character((char) 926));
		entities.put(Entities.Omicron, new Character((char) 927));
		entities.put(Entities.Pi, new Character((char) 928));
		entities.put(Entities.Rho, new Character((char) 929));
		entities.put(Entities.Sigma, new Character((char) 930));
		entities.put(Entities.Sigmaf, new Character((char) 931));
		entities.put(Entities.Tau, new Character((char) 932));
		entities.put(Entities.Upsilon, new Character((char) 933));
		entities.put(Entities.Phi, new Character((char) 934));
		entities.put(Entities.Chi, new Character((char) 935));
		entities.put(Entities.Psi, new Character((char) 936));
		entities.put(Entities.Omega, new Character((char) 937));

		entities.put(Entities.alpha, new Character((char) 945));
		entities.put(Entities.beta, new Character((char) 946));
		entities.put(Entities.gamma, new Character((char) 947));
		entities.put(Entities.delta, new Character((char) 948));
		entities.put(Entities.epsilon, new Character((char) 949));
		entities.put(Entities.zeta, new Character((char) 950));
		entities.put(Entities.eta, new Character((char) 951));
		entities.put(Entities.theta, new Character((char) 952));
		entities.put(Entities.iota, new Character((char) 953));
		entities.put(Entities.kappa, new Character((char) 954));
		entities.put(Entities.lambda, new Character((char) 955));
		entities.put(Entities.mu, new Character((char) 956));
		entities.put(Entities.nu, new Character((char) 957));
		entities.put(Entities.xi, new Character((char) 958));
		entities.put(Entities.omicron, new Character((char) 959));
		entities.put(Entities.pi, new Character((char) 960));
		entities.put(Entities.rho, new Character((char) 961));
		entities.put(Entities.sigma, new Character((char) 962));
		entities.put(Entities.sigmaf, new Character((char) 963));
		entities.put(Entities.tau, new Character((char) 964));
		entities.put(Entities.upsilon, new Character((char) 965));
		entities.put(Entities.phi, new Character((char) 966));
		entities.put(Entities.chi, new Character((char) 967));
		entities.put(Entities.psi, new Character((char) 968));
		entities.put(Entities.omega, new Character((char) 969));
		entities.put(Entities.thetasym, new Character((char) 977));
		entities.put(Entities.upsih, new Character((char) 978));
		entities.put(Entities.piv, new Character((char) 982));

		// math symbols
		entities.put(Entities.forall, new Character((char) 8704));
		entities.put(Entities.part, new Character((char) 8706));
		entities.put(Entities.exist, new Character((char) 8707));
		entities.put(Entities.empty, new Character((char) 8709));
		entities.put(Entities.nabla, new Character((char) 8711));
		entities.put(Entities.isin, new Character((char) 8712));
		entities.put(Entities.notin, new Character((char) 8713));
		entities.put(Entities.ni, new Character((char) 8715));
		entities.put(Entities.prod, new Character((char) 8719));
		entities.put(Entities.sum, new Character((char) 8721));
		entities.put(Entities.minus, new Character((char) 8722));
		entities.put(Entities.lowast, new Character((char) 8727));
		entities.put(Entities.radic, new Character((char) 8730));
		entities.put(Entities.prop, new Character((char) 8733));
		entities.put(Entities.infin, new Character((char) 8734));
		entities.put(Entities.ang, new Character((char) 8736));
		entities.put(Entities.and, new Character((char) 8743));
		entities.put(Entities.or, new Character((char) 8744));
		entities.put(Entities.cap, new Character((char) 8745));
		entities.put(Entities.cup, new Character((char) 8746));
		entities.put(Entities.int_, new Character((char) 8747));
		entities.put(Entities.there4, new Character((char) 8756));
		entities.put(Entities.sim, new Character((char) 8764));
		entities.put(Entities.cong, new Character((char) 8773));
		entities.put(Entities.asymp, new Character((char) 8776));
		entities.put(Entities.ne, new Character((char) 8800));
		entities.put(Entities.equiv, new Character((char) 8801));
		entities.put(Entities.le, new Character((char) 8804));
		entities.put(Entities.ge, new Character((char) 8805));
		entities.put(Entities.sub, new Character((char) 8834));
		entities.put(Entities.sup, new Character((char) 8835));
		entities.put(Entities.nsub, new Character((char) 8836));
		entities.put(Entities.sube, new Character((char) 8838));
		entities.put(Entities.supe, new Character((char) 8839));
		entities.put(Entities.oplus, new Character((char) 8853));
		entities.put(Entities.otimes, new Character((char) 8855));
		entities.put(Entities.perp, new Character((char) 8869));
		entities.put(Entities.sdot, new Character((char) 8901));
		entities.put(Entities.loz, new Character((char) 9674));

		// technical symbols
		entities.put(Entities.lceil, new Character((char) 8968));
		entities.put(Entities.rceil, new Character((char) 8969));
		entities.put(Entities.lfloor, new Character((char) 8970));
		entities.put(Entities.rfloor, new Character((char) 8971));
		entities.put(Entities.lang, new Character((char) 9001));
		entities.put(Entities.rang, new Character((char) 9002));

		// arrow symbols
		entities.put(Entities.larr, new Character((char) 8592));
		entities.put(Entities.uarr, new Character((char) 8593));
		entities.put(Entities.rarr, new Character((char) 8594));
		entities.put(Entities.darr, new Character((char) 8595));
		entities.put(Entities.harr, new Character((char) 8596));
		entities.put(Entities.crarr, new Character((char) 8629));
		entities.put(Entities.lArr, new Character((char) 8656));
		entities.put(Entities.uArr, new Character((char) 8657));
		entities.put(Entities.rArr, new Character((char) 8658));
		entities.put(Entities.dArr, new Character((char) 8659));
		entities.put(Entities.hArr, new Character((char) 8960));

		// divers symbols
		entities.put(Entities.bull, new Character((char) 8226));
		entities.put(Entities.prime, new Character((char) 8242));
		entities.put(Entities.Prime, new Character((char) 8243));
		entities.put(Entities.oline, new Character((char) 8254));
		entities.put(Entities.weierp, new Character((char) 8472));
		entities.put(Entities.image, new Character((char) 8465));
		entities.put(Entities.real, new Character((char) 8476));
		entities.put(Entities.trade, new Character((char) 8482));
		entities.put(Entities.euro, new Character((char) 8364));
		entities.put(Entities.alefsym, new Character((char) 8501));
		entities.put(Entities.spades, new Character((char) 9824));
		entities.put(Entities.clubs, new Character((char) 9827));
		entities.put(Entities.hearts, new Character((char) 9829));
		entities.put(Entities.diams, new Character((char) 9830));

		// ext lat symbols
		entities.put(Entities.OElig, new Character((char) 338));
		entities.put(Entities.oelig, new Character((char) 339));
		entities.put(Entities.Scaron, new Character((char) 352));
		entities.put(Entities.scaron, new Character((char) 353));
		entities.put(Entities.fnof, new Character((char) 402));

		// interpunction
		entities.put(Entities.ensp, new Character((char) 8194));
		entities.put(Entities.emsp, new Character((char) 8195));
		entities.put(Entities.thinsp, new Character((char) 8201));
		entities.put(Entities.zwnj, new Character((char) 8204));
		entities.put(Entities.zwj, new Character((char) 8205));
		entities.put(Entities.lrm, new Character((char) 8206));
		entities.put(Entities.rlm, new Character((char) 8207));

		entities.put(Entities.sbquo, new Character((char) 8218));
		entities.put(Entities.ldquo, new Character((char) 8220));
		entities.put(Entities.rdquo, new Character((char) 8221));
		entities.put(Entities.bdquo, new Character((char) 8222));
		entities.put(Entities.dagger, new Character((char) 8224));
		entities.put(Entities.Dagger, new Character((char) 8225));
		entities.put(Entities.hellip, new Character((char) 8230));
		entities.put(Entities.permil, new Character((char) 8240));
		entities.put(Entities.lsaquo, new Character((char) 8249));
		entities.put(Entities.rsaquo, new Character((char) 8250));
		entities.put(Entities.circ, new Character((char) 710));
		entities.put(Entities.tilde, new Character((char) 732));

		final ElementInfo optionalEndElement = new ElementInfo(true, ElementInfo.END_ELEMENT_OPTIONAL);
		final ElementInfo forbiddenEndElement = new ElementInfo(false, ElementInfo.END_ELEMENT_FORBIDDEN);
		final ElementInfo only = new ElementInfo(true, ElementInfo.END_ELEMENT_REQUIRED, null, true);
		final ElementInfo onlyText = new ElementInfo(false, ElementInfo.END_ELEMENT_REQUIRED, false);
		final ElementInfo onlyTextDE = new ElementInfo(false, ElementInfo.END_ELEMENT_REQUIRED, true);
		
		final Set<HTMLTag> tableCellStopElements = new HashSet<HTMLTag>();
		tableCellStopElements.add(HTMLTag.TH);
		tableCellStopElements.add(HTMLTag.TD);
		tableCellStopElements.add(HTMLTag.TR);
		final ElementInfo tableCellElement = new ElementInfo(true, ElementInfo.END_ELEMENT_OPTIONAL, tableCellStopElements);

		final Set<HTMLTag> headStopElements = new HashSet<HTMLTag>();
		headStopElements.add(HTMLTag.BODY);
		headStopElements.add(HTMLTag.DIV);
		headStopElements.add(HTMLTag.SPAN);
		headStopElements.add(HTMLTag.TABLE);
		final ElementInfo headElement = new ElementInfo(true, ElementInfo.END_ELEMENT_OPTIONAL, headStopElements);

		final Set<HTMLTag> optionStopElements = new HashSet<HTMLTag>();
		optionStopElements.add(HTMLTag.OPTION);
		optionStopElements.add(HTMLTag.SELECT);
		final ElementInfo optionElement = new ElementInfo(true, ElementInfo.END_ELEMENT_OPTIONAL, optionStopElements);

		final Set<HTMLTag> paragraphStopElements = new HashSet<HTMLTag>();
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
		elementInfos.put(HTMLTag.FRAME, forbiddenEndElement);
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
