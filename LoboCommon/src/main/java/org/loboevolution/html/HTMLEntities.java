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
	public static final Map<HTMLTag, ElementInfo> ELEMENT_INFOS = new HashMap<HTMLTag, ElementInfo>();
	/** Constant ENTITIES */
	public static final Map<Entities, Character> ENTITIES = new HashMap<Entities, Character>();
	
	static {
		final Map<Entities, Character> entities = ENTITIES;
		entities.put(Entities.amp, Character.valueOf('&'));
		entities.put(Entities.lt, Character.valueOf('<'));
		entities.put(Entities.gt, Character.valueOf('>'));
		entities.put(Entities.quot, Character.valueOf('"'));
		entities.put(Entities.nbsp, Character.valueOf((char) 160));

		entities.put(Entities.lsquo, Character.valueOf('\u2018'));
		entities.put(Entities.rsquo, Character.valueOf('\u2019'));

		entities.put(Entities.frasl, Character.valueOf((char) 47));
		entities.put(Entities.ndash, Character.valueOf((char) 8211));
		entities.put(Entities.mdash, Character.valueOf((char) 8212));
		entities.put(Entities.iexcl, Character.valueOf((char) 161));
		entities.put(Entities.cent, Character.valueOf((char) 162));
		entities.put(Entities.pound, Character.valueOf((char) 163));
		entities.put(Entities.curren, Character.valueOf((char) 164));
		entities.put(Entities.yen, Character.valueOf((char) 165));
		entities.put(Entities.brvbar, Character.valueOf((char) 166));
		entities.put(Entities.brkbar, Character.valueOf((char) 166));
		entities.put(Entities.sect, Character.valueOf((char) 167));
		entities.put(Entities.uml, Character.valueOf((char) 168));
		entities.put(Entities.die, Character.valueOf((char) 168));
		entities.put(Entities.copy, Character.valueOf((char) 169));
		entities.put(Entities.ordf, Character.valueOf((char) 170));
		entities.put(Entities.laquo, Character.valueOf((char) 171));
		entities.put(Entities.not, Character.valueOf((char) 172));
		entities.put(Entities.shy, Character.valueOf((char) 173));
		entities.put(Entities.reg, Character.valueOf((char) 174));
		entities.put(Entities.macr, Character.valueOf((char) 175));
		entities.put(Entities.hibar, Character.valueOf((char) 175));
		entities.put(Entities.deg, Character.valueOf((char) 176));
		entities.put(Entities.plusmn, Character.valueOf((char) 177));
		entities.put(Entities.sup2, Character.valueOf((char) 178));
		entities.put(Entities.sup3, Character.valueOf((char) 179));
		entities.put(Entities.acute, Character.valueOf((char) 180));
		entities.put(Entities.micro, Character.valueOf((char) 181));
		entities.put(Entities.para, Character.valueOf((char) 182));
		entities.put(Entities.middot, Character.valueOf((char) 183));
		entities.put(Entities.cedil, Character.valueOf((char) 184));
		entities.put(Entities.sup1, Character.valueOf((char) 185));
		entities.put(Entities.ordm, Character.valueOf((char) 186));
		entities.put(Entities.raquo, Character.valueOf((char) 187));
		entities.put(Entities.frac14, Character.valueOf((char) 188));
		entities.put(Entities.frac12, Character.valueOf((char) 189));
		entities.put(Entities.frac34, Character.valueOf((char) 190));
		entities.put(Entities.iquest, Character.valueOf((char) 191));
		entities.put(Entities.Agrave, Character.valueOf((char) 192));
		entities.put(Entities.Aacute, Character.valueOf((char) 193));
		entities.put(Entities.Acirc, Character.valueOf((char) 194));
		entities.put(Entities.Atilde, Character.valueOf((char) 195));
		entities.put(Entities.Auml, Character.valueOf((char) 196));
		entities.put(Entities.Aring, Character.valueOf((char) 197));
		entities.put(Entities.AElig, Character.valueOf((char) 198));
		entities.put(Entities.Ccedil, Character.valueOf((char) 199));
		entities.put(Entities.Egrave, Character.valueOf((char) 200));
		entities.put(Entities.Eacute, Character.valueOf((char) 201));
		entities.put(Entities.Ecirc, Character.valueOf((char) 202));
		entities.put(Entities.Euml, Character.valueOf((char) 203));
		entities.put(Entities.Igrave, Character.valueOf((char) 204));
		entities.put(Entities.Iacute, Character.valueOf((char) 205));
		entities.put(Entities.Icirc, Character.valueOf((char) 206));
		entities.put(Entities.Iuml, Character.valueOf((char) 207));
		entities.put(Entities.ETH, Character.valueOf((char) 208));
		entities.put(Entities.Ntilde, Character.valueOf((char) 209));
		entities.put(Entities.Ograve, Character.valueOf((char) 210));
		entities.put(Entities.Oacute, Character.valueOf((char) 211));
		entities.put(Entities.Ocirc, Character.valueOf((char) 212));
		entities.put(Entities.Otilde, Character.valueOf((char) 213));
		entities.put(Entities.Ouml, Character.valueOf((char) 214));
		entities.put(Entities.times, Character.valueOf((char) 215));
		entities.put(Entities.Oslash, Character.valueOf((char) 216));
		entities.put(Entities.Ugrave, Character.valueOf((char) 217));
		entities.put(Entities.Uacute, Character.valueOf((char) 218));
		entities.put(Entities.Ucirc, Character.valueOf((char) 219));
		entities.put(Entities.Uuml, Character.valueOf((char) 220));
		entities.put(Entities.Yacute, Character.valueOf((char) 221));
		entities.put(Entities.THORN, Character.valueOf((char) 222));
		entities.put(Entities.szlig, Character.valueOf((char) 223));
		entities.put(Entities.agrave, Character.valueOf((char) 224));
		entities.put(Entities.aacute, Character.valueOf((char) 225));
		entities.put(Entities.acirc, Character.valueOf((char) 226));
		entities.put(Entities.atilde, Character.valueOf((char) 227));
		entities.put(Entities.auml, Character.valueOf((char) 228));
		entities.put(Entities.aring, Character.valueOf((char) 229));
		entities.put(Entities.aelig, Character.valueOf((char) 230));
		entities.put(Entities.ccedil, Character.valueOf((char) 231));
		entities.put(Entities.egrave, Character.valueOf((char) 232));
		entities.put(Entities.eacute, Character.valueOf((char) 233));
		entities.put(Entities.ecirc, Character.valueOf((char) 234));
		entities.put(Entities.euml, Character.valueOf((char) 235));
		entities.put(Entities.igrave, Character.valueOf((char) 236));
		entities.put(Entities.iacute, Character.valueOf((char) 237));
		entities.put(Entities.icirc, Character.valueOf((char) 238));
		entities.put(Entities.iuml, Character.valueOf((char) 239));
		entities.put(Entities.eth, Character.valueOf((char) 240));
		entities.put(Entities.ntilde, Character.valueOf((char) 241));
		entities.put(Entities.ograve, Character.valueOf((char) 242));
		entities.put(Entities.oacute, Character.valueOf((char) 243));
		entities.put(Entities.ocirc, Character.valueOf((char) 244));
		entities.put(Entities.otilde, Character.valueOf((char) 245));
		entities.put(Entities.ouml, Character.valueOf((char) 246));
		entities.put(Entities.divide, Character.valueOf((char) 247));
		entities.put(Entities.oslash, Character.valueOf((char) 248));
		entities.put(Entities.ugrave, Character.valueOf((char) 249));
		entities.put(Entities.uacute, Character.valueOf((char) 250));
		entities.put(Entities.ucirc, Character.valueOf((char) 251));
		entities.put(Entities.uuml, Character.valueOf((char) 252));
		entities.put(Entities.yacute, Character.valueOf((char) 253));
		entities.put(Entities.thorn, Character.valueOf((char) 254));
		entities.put(Entities.yuml, Character.valueOf((char) 255));

		// symbols from http://de.selfhtml.org/html/referenz/zeichen.htm

		// greek letters
		entities.put(Entities.Alpha, Character.valueOf((char) 913));
		entities.put(Entities.Beta, Character.valueOf((char) 914));
		entities.put(Entities.Gamma, Character.valueOf((char) 915));
		entities.put(Entities.Delta, Character.valueOf((char) 916));
		entities.put(Entities.Epsilon, Character.valueOf((char) 917));
		entities.put(Entities.Zeta, Character.valueOf((char) 918));
		entities.put(Entities.Eta, Character.valueOf((char) 919));
		entities.put(Entities.Theta, Character.valueOf((char) 920));
		entities.put(Entities.Iota, Character.valueOf((char) 921));
		entities.put(Entities.Kappa, Character.valueOf((char) 922));
		entities.put(Entities.Lambda, Character.valueOf((char) 923));
		entities.put(Entities.Mu, Character.valueOf((char) 924));
		entities.put(Entities.Nu, Character.valueOf((char) 925));
		entities.put(Entities.Xi, Character.valueOf((char) 926));
		entities.put(Entities.Omicron, Character.valueOf((char) 927));
		entities.put(Entities.Pi, Character.valueOf((char) 928));
		entities.put(Entities.Rho, Character.valueOf((char) 929));
		entities.put(Entities.Sigma, Character.valueOf((char) 930));
		entities.put(Entities.Sigmaf, Character.valueOf((char) 931));
		entities.put(Entities.Tau, Character.valueOf((char) 932));
		entities.put(Entities.Upsilon, Character.valueOf((char) 933));
		entities.put(Entities.Phi, Character.valueOf((char) 934));
		entities.put(Entities.Chi, Character.valueOf((char) 935));
		entities.put(Entities.Psi, Character.valueOf((char) 936));
		entities.put(Entities.Omega, Character.valueOf((char) 937));

		entities.put(Entities.alpha, Character.valueOf((char) 945));
		entities.put(Entities.beta, Character.valueOf((char) 946));
		entities.put(Entities.gamma, Character.valueOf((char) 947));
		entities.put(Entities.delta, Character.valueOf((char) 948));
		entities.put(Entities.epsilon, Character.valueOf((char) 949));
		entities.put(Entities.zeta, Character.valueOf((char) 950));
		entities.put(Entities.eta, Character.valueOf((char) 951));
		entities.put(Entities.theta, Character.valueOf((char) 952));
		entities.put(Entities.iota, Character.valueOf((char) 953));
		entities.put(Entities.kappa, Character.valueOf((char) 954));
		entities.put(Entities.lambda, Character.valueOf((char) 955));
		entities.put(Entities.mu, Character.valueOf((char) 956));
		entities.put(Entities.nu, Character.valueOf((char) 957));
		entities.put(Entities.xi, Character.valueOf((char) 958));
		entities.put(Entities.omicron, Character.valueOf((char) 959));
		entities.put(Entities.pi, Character.valueOf((char) 960));
		entities.put(Entities.rho, Character.valueOf((char) 961));
		entities.put(Entities.sigma, Character.valueOf((char) 962));
		entities.put(Entities.sigmaf, Character.valueOf((char) 963));
		entities.put(Entities.tau, Character.valueOf((char) 964));
		entities.put(Entities.upsilon, Character.valueOf((char) 965));
		entities.put(Entities.phi, Character.valueOf((char) 966));
		entities.put(Entities.chi, Character.valueOf((char) 967));
		entities.put(Entities.psi, Character.valueOf((char) 968));
		entities.put(Entities.omega, Character.valueOf((char) 969));
		entities.put(Entities.thetasym, Character.valueOf((char) 977));
		entities.put(Entities.upsih, Character.valueOf((char) 978));
		entities.put(Entities.piv, Character.valueOf((char) 982));

		// math symbols
		entities.put(Entities.forall, Character.valueOf((char) 8704));
		entities.put(Entities.part, Character.valueOf((char) 8706));
		entities.put(Entities.exist, Character.valueOf((char) 8707));
		entities.put(Entities.empty, Character.valueOf((char) 8709));
		entities.put(Entities.nabla, Character.valueOf((char) 8711));
		entities.put(Entities.isin, Character.valueOf((char) 8712));
		entities.put(Entities.notin, Character.valueOf((char) 8713));
		entities.put(Entities.ni, Character.valueOf((char) 8715));
		entities.put(Entities.prod, Character.valueOf((char) 8719));
		entities.put(Entities.sum, Character.valueOf((char) 8721));
		entities.put(Entities.minus, Character.valueOf((char) 8722));
		entities.put(Entities.lowast, Character.valueOf((char) 8727));
		entities.put(Entities.radic, Character.valueOf((char) 8730));
		entities.put(Entities.prop, Character.valueOf((char) 8733));
		entities.put(Entities.infin, Character.valueOf((char) 8734));
		entities.put(Entities.ang, Character.valueOf((char) 8736));
		entities.put(Entities.and, Character.valueOf((char) 8743));
		entities.put(Entities.or, Character.valueOf((char) 8744));
		entities.put(Entities.cap, Character.valueOf((char) 8745));
		entities.put(Entities.cup, Character.valueOf((char) 8746));
		entities.put(Entities.int_, Character.valueOf((char) 8747));
		entities.put(Entities.there4, Character.valueOf((char) 8756));
		entities.put(Entities.sim, Character.valueOf((char) 8764));
		entities.put(Entities.cong, Character.valueOf((char) 8773));
		entities.put(Entities.asymp, Character.valueOf((char) 8776));
		entities.put(Entities.ne, Character.valueOf((char) 8800));
		entities.put(Entities.equiv, Character.valueOf((char) 8801));
		entities.put(Entities.le, Character.valueOf((char) 8804));
		entities.put(Entities.ge, Character.valueOf((char) 8805));
		entities.put(Entities.sub, Character.valueOf((char) 8834));
		entities.put(Entities.sup, Character.valueOf((char) 8835));
		entities.put(Entities.nsub, Character.valueOf((char) 8836));
		entities.put(Entities.sube, Character.valueOf((char) 8838));
		entities.put(Entities.supe, Character.valueOf((char) 8839));
		entities.put(Entities.oplus, Character.valueOf((char) 8853));
		entities.put(Entities.otimes, Character.valueOf((char) 8855));
		entities.put(Entities.perp, Character.valueOf((char) 8869));
		entities.put(Entities.sdot, Character.valueOf((char) 8901));
		entities.put(Entities.loz, Character.valueOf((char) 9674));

		// technical symbols
		entities.put(Entities.lceil, Character.valueOf((char) 8968));
		entities.put(Entities.rceil, Character.valueOf((char) 8969));
		entities.put(Entities.lfloor, Character.valueOf((char) 8970));
		entities.put(Entities.rfloor, Character.valueOf((char) 8971));
		entities.put(Entities.lang, Character.valueOf((char) 9001));
		entities.put(Entities.rang, Character.valueOf((char) 9002));

		// arrow symbols
		entities.put(Entities.larr, Character.valueOf((char) 8592));
		entities.put(Entities.uarr, Character.valueOf((char) 8593));
		entities.put(Entities.rarr, Character.valueOf((char) 8594));
		entities.put(Entities.darr, Character.valueOf((char) 8595));
		entities.put(Entities.harr, Character.valueOf((char) 8596));
		entities.put(Entities.crarr, Character.valueOf((char) 8629));
		entities.put(Entities.lArr, Character.valueOf((char) 8656));
		entities.put(Entities.uArr, Character.valueOf((char) 8657));
		entities.put(Entities.rArr, Character.valueOf((char) 8658));
		entities.put(Entities.dArr, Character.valueOf((char) 8659));
		entities.put(Entities.hArr, Character.valueOf((char) 8960));

		// divers symbols
		entities.put(Entities.bull, Character.valueOf((char) 8226));
		entities.put(Entities.prime, Character.valueOf((char) 8242));
		entities.put(Entities.Prime, Character.valueOf((char) 8243));
		entities.put(Entities.oline, Character.valueOf((char) 8254));
		entities.put(Entities.weierp, Character.valueOf((char) 8472));
		entities.put(Entities.image, Character.valueOf((char) 8465));
		entities.put(Entities.real, Character.valueOf((char) 8476));
		entities.put(Entities.trade, Character.valueOf((char) 8482));
		entities.put(Entities.euro, Character.valueOf((char) 8364));
		entities.put(Entities.alefsym, Character.valueOf((char) 8501));
		entities.put(Entities.spades, Character.valueOf((char) 9824));
		entities.put(Entities.clubs, Character.valueOf((char) 9827));
		entities.put(Entities.hearts, Character.valueOf((char) 9829));
		entities.put(Entities.diams, Character.valueOf((char) 9830));

		// ext lat symbols
		entities.put(Entities.OElig, Character.valueOf((char) 338));
		entities.put(Entities.oelig, Character.valueOf((char) 339));
		entities.put(Entities.Scaron, Character.valueOf((char) 352));
		entities.put(Entities.scaron, Character.valueOf((char) 353));
		entities.put(Entities.fnof, Character.valueOf((char) 402));

		// interpunction
		entities.put(Entities.ensp, Character.valueOf((char) 8194));
		entities.put(Entities.emsp, Character.valueOf((char) 8195));
		entities.put(Entities.thinsp, Character.valueOf((char) 8201));
		entities.put(Entities.zwnj, Character.valueOf((char) 8204));
		entities.put(Entities.zwj, Character.valueOf((char) 8205));
		entities.put(Entities.lrm, Character.valueOf((char) 8206));
		entities.put(Entities.rlm, Character.valueOf((char) 8207));

		entities.put(Entities.sbquo, Character.valueOf((char) 8218));
		entities.put(Entities.ldquo, Character.valueOf((char) 8220));
		entities.put(Entities.rdquo, Character.valueOf((char) 8221));
		entities.put(Entities.bdquo, Character.valueOf((char) 8222));
		entities.put(Entities.dagger, Character.valueOf((char) 8224));
		entities.put(Entities.Dagger, Character.valueOf((char) 8225));
		entities.put(Entities.hellip, Character.valueOf((char) 8230));
		entities.put(Entities.permil, Character.valueOf((char) 8240));
		entities.put(Entities.lsaquo, Character.valueOf((char) 8249));
		entities.put(Entities.rsaquo, Character.valueOf((char) 8250));
		entities.put(Entities.circ, Character.valueOf((char) 710));
		entities.put(Entities.tilde, Character.valueOf((char) 732));

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
