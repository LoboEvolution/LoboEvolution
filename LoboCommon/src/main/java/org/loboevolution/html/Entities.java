/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.html;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Entities class.</p>
 *
 *
 *
 */
public enum Entities {
	
	amp("amp"),

	lt("lt"),

	gt("gt"),

	quot("quot"),

	nbsp("nbsp"),

	lsquo("lsquo"),

	rsquo("rsquo"),


	frasl("frasl"),

	ndash("ndash"),

	mdash("mdash"),

	iexcl("iexcl"),

	cent("cent"),

	pound("pound"),

	curren("curren"),

	yen("yen"),

	brvbar("brvbar"),

	brkbar("brkbar"),

	sect("sect"),

	uml("uml"),

	die("die"),

	copy("copy"),

	ordf("ordf"),

	laquo("laquo"),

	not("not"),

	shy("shy"),

	reg("reg"),

	macr("macr"),

	hibar("hibar"),

	deg("deg"),

	plusmn("plusmn"),

	sup2("sup2"),

	sup3("sup3"),

	acute("acute"),

	micro("micro"),

	para("para"),

	middot("middot"),

	cedil("cedil"),

	sup1("sup1"),

	ordm("ordm"),

	raquo("raquo"),

	frac14("frac14"),

	frac12("frac12"),

	frac34("frac34"),

	iquest("iquest"),

	Agrave("Agrave"),

	Aacute("Aacute"),

	Acirc("Acirc"),

	Atilde("Atilde"),

	Auml("Auml"),

	Aring("Aring"),

	AElig("AElig"),

	Ccedil("Ccedil"),

	Egrave("Egrave"),

	Eacute("Eacute"),

	Ecirc("Ecirc"),

	Euml("Euml"),

	Igrave("Igrave"),

	Iacute("Iacute"),

	Icirc("Icirc"),

	Iuml("Iuml"),

	ETH("ETH"),

	Ntilde("Ntilde"),

	Ograve("Ograve"),

	Oacute("Oacute"),

	Ocirc("Ocirc"),

	Otilde("Otilde"),

	Ouml("Ouml"),

	times("times"),

	Oslash("Oslash"),

	Ugrave("Ugrave"),

	Uacute("Uacute"),

	Ucirc("Ucirc"),

	Uuml("Uuml"),

	Yacute("Yacute"),

	THORN("THORN"),

	szlig("szlig"),

	agrave("agrave"),

	aacute("aacute"),

	acirc("acirc"),

	atilde("atilde"),

	auml("auml"),

	aring("aring"),

	aelig("aelig"),

	ccedil("ccedil"),

	egrave("egrave"),

	eacute("eacute"),

	ecirc("ecirc"),

	euml("euml"),

	igrave("igrave"),

	iacute("iacute"),

	icirc("icirc"),

	iuml("iuml"),

	eth("eth"),

	ntilde("ntilde"),

	ograve("ograve"),

	oacute("oacute"),

	ocirc("ocirc"),

	otilde("otilde"),

	ouml("ouml"),

	divide("divide"),

	oslash("oslash"),

	ugrave("ugrave"),

	uacute("uacute"),

	ucirc("ucirc"),

	uuml("uuml"),

	yacute("yacute"),

	thorn("thorn"),

	yuml("yuml"),

	Alpha("Alpha"),

	Beta("Beta"),

	Gamma("Gamma"),

	Delta("Delta"),

	Epsilon("Epsilon"),

	Zeta("Zeta"),

	Eta("Eta"),

	Theta("Theta"),

	Iota("Iota"),

	Kappa("Kappa"),

	Lambda("Lambda"),

	Mu("Mu"),

	Nu("Nu"),

	Xi("Xi"),

	Omicron("Omicron"),

	Pi("Pi"),

	Rho("Rho"),

	Sigma("Sigma"),

	Sigmaf("Sigmaf"),

	Tau("Tau"),

	Upsilon("Upsilon"),

	Phi("Phi"),

	Chi("Chi"),

	Psi("Psi"),

	Omega("Omega"),

	alpha("alpha"),

	beta("beta"),

	gamma("gamma"),

	delta("delta"),

	epsilon("epsilon"),

	zeta("zeta"),

	eta("eta"),

	theta("theta"),

	iota("iota"),

	kappa("kappa"),

	lambda("lambda"),

	mu("mu"),

	nu("nu"),

	xi("xi"),

	omicron("omicron"),

	pi("pi"),

	rho("rho"),

	sigma("sigma"),

	sigmaf("sigmaf"),

	tau("tau"),

	upsilon("upsilon"),

	phi("phi"),

	chi("chi"),

	psi("psi"),

	omega("omega"),

	thetasym("thetasym"),

	upsih("upsih"),

	piv("piv"),

	forall("forall"),

	part("part"),

	exist("exist"),

	empty("empty"),

	nabla("nabla"),

	isin("isin"),

	notin("notin"),

	ni("ni"),

	prod("prod"),

	sum("sum"),

	minus("minus"),

	lowast("lowast"),

	radic("radic"),

	prop("prop"),

	infin("infin"),

	ang("ang"),

	and("and"),

	or("or"),

	cap("cap"),

	cup("cup"),

	int_("int"),

	there4("there4"),

	sim("sim"),

	cong("cong"),

	asymp("asymp"),

	ne("ne"),

	equiv("equiv"),

	le("le"),

	ge("ge"),

	sub("sub"),

	sup("sup"),

	nsub("nsub"),

	sube("sube"),

	supe("supe"),

	oplus("oplus"),

	otimes("otimes"),

	perp("perp"),

	sdot("sdot"),

	loz("loz"),

	lceil("lceil"),

	rceil("rceil"),

	lfloor("lfloor"),

	rfloor("rfloor"),

	lang("lang"),

	rang("rang"),

	larr("larr"),

	uarr("uarr"),

	rarr("rarr"),

	darr("darr"),

	harr("harr"),

	crarr("crarr"),

	lArr("lArr"),

	uArr("uArr"),

	rArr("rArr"),

	dArr("dArr"),

	hArr("hArr"),

	bull("bull"),

	prime("prime"),

	Prime("Prime"),

	oline("oline"),

	weierp("weierp"),

	image("image"),

	real("real"),

	trade("trade"),

	euro("euro"),

	alefsym("alefsym"),

	spades("spades"),

	clubs("clubs"),

	hearts("hearts"),

	diams("diams"),

	OElig("OElig"),

	oelig("oelig"),

	Scaron("Scaron"),

	scaron("scaron"),

	fnof("fnof"),

	ensp("ensp"),

	emsp("emsp"),

	thinsp("thinsp"),

	zwnj("zwnj"),

	zwj("zwj"),

	lrm("lrm"),

	rlm("rlm"),

	sbquo("sbquo"),

	ldquo("ldquo"),

	rdquo("rdquo"),

	bdquo("bdquo"),

	dagger("dagger"),

	Dagger("Dagger"),

	hellip("hellip"),

	permil("permil"),

	lsaquo("lsaquo"),

	rsaquo("rsaquo"),

	circ("circ"),

	tilde("tilde");
	
	
	private final String value;
	private static final Map<String, Entities> ENUM_MAP;

	static {
		Map<String, Entities> map = new HashMap<>();
		for (Entities instance : Entities.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	Entities(String value) {
		this.value = value;
	}

	/**
	 * <p>Getter for the field value.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <p>isEqual.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean isEqual(String value) {
		return this.value.equals(value);
	}

	/**
	 * <p>get.</p>
	 *
	 * @param actionName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.Entities} object.
	 */
	public static Entities get(String actionName) {
		return ENUM_MAP.get(actionName);
	}

}
