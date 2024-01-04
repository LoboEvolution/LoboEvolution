/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Entities class.</p>
 */
@Getter
public enum Entities {

    AMP("amp"),

    LT("lt"),

    GT("gt"),

    QUOT("quot"),

    NBSP("nbsp"),

    LSQUO("lsquo"),

    RSQUO("rsquo"),


    FRASL("frasl"),

    NDASH("ndash"),

    MDASH("mdash"),

    IEXCL("iexcl"),

    CENT("cent"),

    POUND("pound"),

    CURREN("curren"),

    YEN("yen"),

    BRVBAR("brvbar"),

    BRKBAR("brkbar"),

    SECT("sect"),

    UML("uml"),

    DIE("die"),

    COPY("copy"),

    ORDF("ordf"),

    LAQUO("laquo"),

    NOT("not"),

    SHY("shy"),

    REG("reg"),

    MACR("macr"),

    HIBAR("hibar"),

    DEG("deg"),

    PLUSMN("plusmn"),

    SUP2("sup2"),

    SUP3("sup3"),

    ACUTE("acute"),

    MICRO("micro"),

    PARA("para"),

    MIDDOT("middot"),

    CEDIL("cedil"),

    SUP1("sup1"),

    ORDM("ordm"),

    RAQUO("raquo"),

    FRAC14("frac14"),

    FRAC12("frac12"),

    FRAC34("frac34"),

    IQUEST("iquest"),

	AGRAVE_UP("Agrave"),

	AACUTE_UP("Aacute"),

	ACIRC_UP("Acirc"),

	ATILDE_UP("Atilde"),

	AUML_UP("Auml"),

	ARING_UP("Aring"),

	AELIG_UP("AElig"),

	CCEDIL_UP("Ccedil"),

	EGRAVE_UP("Egrave"),

	EACUTE_UP("Eacute"),

	ECIRC_UP("Ecirc"),

	EUML_UP("Euml"),

	IGRAVE_UP("Igrave"),

	IACUTE_UP("Iacute"),

	ICIRC_UP("Icirc"),

	IUML_UP("Iuml"),

	ETH_UP("ETH"),

	NTILDE_UP("Ntilde"),

	OGRAVE_UP("Ograve"),

	OACUTE_UP("Oacute"),

	OCIRC_UP("Ocirc"),

	OTILDE_UP("Otilde"),

	OUML_UP("Ouml"),

	TIMES("times"),

	OSLASH_UP("Oslash"),

	UGRAVE_UP("Ugrave"),

	UACUTE_UP("Uacute"),

	UCIRC_UP("Ucirc"),

	UUML_UP("Uuml"),

	YACUTE_UP("Yacute"),

    THORN_UP("THORN"),

    SZLIG("szlig"),

    AGRAVE("agrave"),

    AACUTE("aacute"),

    ACIRC("acirc"),

    ATILDE("atilde"),

    AUML("auml"),

    ARING("aring"),

    AELIG("aelig"),

    CCEDIL("ccedil"),

    EGRAVE("egrave"),

    EACUTE("eacute"),

    ECIRC("ecirc"),

    EUML("euml"),

    IGRAVE("igrave"),

    IACUTE("iacute"),

    ICIRC("icirc"),

    IUML("iuml"),

    ETH("eth"),

    NTILDE("ntilde"),

    OGRAVE("ograve"),

    OACUTE("oacute"),

    OCIRC("ocirc"),

    OTILDE("otilde"),

    OUML("ouml"),

    DIVIDE("divide"),

    OSLASH("oslash"),

    UGRAVE("ugrave"),

    UACUTE("uacute"),

    UCIRC("ucirc"),

    UUML("uuml"),

    YACUTE("yacute"),

    THORN("thorn"),

    YUML("yuml"),

	ALPHA_UP("Alpha"),

	BETA_UP("Beta"),

	GAMMA_UP("Gamma"),

	DELTA_UP("Delta"),

	EPSILON_UP("Epsilon"),

	ZETA_UP("Zeta"),

	ETA_UP("Eta"),

	THETA_UP("Theta"),

	IOTA_UP("Iota"),

	KAPPA_UP("Kappa"),

	LAMBDA_UP("Lambda"),

	MU_UP("Mu"),

	NU_UP("Nu"),

	XI_UP("Xi"),

	OMICRON_UP("Omicron"),

	PI_UP("Pi"),

	RHO_UP("Rho"),

	SIGMA_UP("Sigma"),

	SIGMAF_UP("Sigmaf"),

	TAU_UP("Tau"),

	UPSILON_UP("Upsilon"),

	PHI_UP("Phi"),

	CHI_UP("Chi"),

	PSI_UP("Psi"),

	OMEGA_UP("Omega"),

    ALPHA("alpha"),

    BETA("beta"),

    GAMMA("gamma"),

    DELTA("delta"),

    EPSILON("epsilon"),

    ZETA("zeta"),

    ETA("eta"),

    THETA("theta"),

    IOTA("iota"),

    KAPPA("kappa"),

    LAMBDA("lambda"),

    MU("mu"),

    NU("nu"),

    XI("xi"),

    OMICRON("omicron"),

    PI("pi"),

    RHO("rho"),

    SIGMA("sigma"),

    SIGMAF("sigmaf"),

    TAU("tau"),

    UPSILON("upsilon"),

    PHI("phi"),

    CHI("chi"),

    PSI("psi"),

    OMEGA("omega"),

    THETASYM("thetasym"),

    UPSIH("upsih"),

    PIV("piv"),

    FORALL("forall"),

    PART("part"),

    EXIST("exist"),

    EMPTY("empty"),

    NABLA("nabla"),

    ISIN("isin"),

    NOTIN("notin"),

    NI("ni"),

    PROD("prod"),

    SUM("sum"),

    MINUS("minus"),

    LOWAST("lowast"),

    RADIC("radic"),

    PROP("prop"),

    INFIN("infin"),

    ANG("ang"),

    AND("and"),

    OR("or"),

    CAP("cap"),

    CUP("cup"),

    INT_("int"),

    THERE4("there4"),

    SIM("sim"),

    CONG("cong"),

    ASYMP("asymp"),

    NE("ne"),

    EQUIV("equiv"),

    LE("le"),

    GE("ge"),

    SUB("sub"),

    SUP("sup"),

    NSUB("nsub"),

    SUBE("sube"),

    SUPE("supe"),

    OPLUS("oplus"),

    OTIMES("otimes"),

    PERP("perp"),

    SDOT("sdot"),

    LOZ("loz"),

    LCEIL("lceil"),

    RCEIL("rceil"),

    LFLOOR("lfloor"),

    RFLOOR("rfloor"),

    LANG("lang"),

    RANG("rang"),

    LARR("larr"),

    UARR("uarr"),

    RARR("rarr"),

    DARR("darr"),

    HARR("harr"),

    CRARR("crarr"),

    LARR_UP("lArr"),

    UARR_UP("uArr"),

    RARR_UP("rArr"),

    DARR_UP("dArr"),

    HARR_UP("hArr"),

    BULL("bull"),

    PRIME("prime"),

    PRIME_UP("Prime"),

    OLINE("oline"),

    WEIERP_UP("weierp"),

    IMAGE("image"),

    REAL("real"),

    TRADE("trade"),

    EURO("euro"),

    ALEFSYM("alefsym"),

    SPADES("spades"),

    CLUBS("clubs"),

    HEARTS("hearts"),

    DIAMS("diams"),

    OELIG_UP("OElig"),

    OELIG("oelig"),

    SCARON_UP("Scaron"),

    SCARON("scaron"),

    FNOF("fnof"),

    ENSP("ensp"),

    EMSP("emsp"),

    THINSP("thinsp"),

    ZWNJ("zwnj"),

    ZWJ("zwj"),

    LRM("lrm"),

    RLM("rlm"),

    SBQUO("sbquo"),

    LDQUO("ldquo"),

    RDQUO("rdquo"),

    BDQUO("bdquo"),

    DAGGER("dagger"),

    DAGGER_UP("Dagger"),

    HELLIP("hellip"),

    PERMIL("permil"),

    LSAQUO("lsaquo"),

    RSAQUO("rsaquo"),

    CIRC("circ"),

    TILDE("tilde");

    private final String value;
    private static final Map<String, Entities> ENUM_MAP;

    static {
        final Map<String, Entities> map = new HashMap<>();
        for (final Entities instance : Entities.values()) {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    Entities(final String value) {
        this.value = value;
    }

    /**
     * <p>isEqual.</p>
     *
     * @param value a {@link java.lang.String} object.
     * @return a boolean.
     */
    public boolean isEqual(final String value) {
        return this.value.equals(value);
    }

    /**
     * <p>get.</p>
     *
     * @param actionName a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.Entities} object.
     */
    public static Entities get(final String actionName) {
        return ENUM_MAP.get(actionName);
    }

}
