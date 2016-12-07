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
/*
 * Created on Jun 2, 2005
 */
package org.lobobrowser.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * The Class Domains.
 *
 * @author J. H. S.
 */
public class Domains {
    /** The Constant gTLDs. */
    private static final Collection<String> gTLDs;
    
    static {
        gTLDs = new HashSet<String>();
        gTLDs.add(".aero");
        gTLDs.add(".asia");
        gTLDs.add(".biz");
        gTLDs.add(".cat");
        gTLDs.add(".com");
        gTLDs.add(".coop");
        gTLDs.add(".edu");
        gTLDs.add(".gov");
        gTLDs.add(".info");
        gTLDs.add(".int");
        gTLDs.add(".jobs");
        gTLDs.add(".mil");
        gTLDs.add(".mobi");
        gTLDs.add(".museum");
        gTLDs.add(".name");
        gTLDs.add(".net");
        gTLDs.add(".org");
        gTLDs.add(".pro");
        gTLDs.add(".tel");
        gTLDs.add(".travel");
        gTLDs.add(".xxx");
        gTLDs.add(".ac");
        gTLDs.add(".ad");
        gTLDs.add(".ae");
        gTLDs.add(".af");
        gTLDs.add(".ag");
        gTLDs.add(".ai");
        gTLDs.add(".al");
        gTLDs.add(".am");
        gTLDs.add(".an");
        gTLDs.add(".ao");
        gTLDs.add(".aq");
        gTLDs.add(".ar");
        gTLDs.add(".as");
        gTLDs.add(".at");
        gTLDs.add(".au");
        gTLDs.add(".aw");
        gTLDs.add(".ax");
        gTLDs.add(".az");
        gTLDs.add(".ba");
        gTLDs.add(".bb");
        gTLDs.add(".bd");
        gTLDs.add(".be");
        gTLDs.add(".bf");
        gTLDs.add(".bg");
        gTLDs.add(".bh");
        gTLDs.add(".bi");
        gTLDs.add(".bj");
        gTLDs.add(".bm");
        gTLDs.add(".bn");
        gTLDs.add(".bo");
        gTLDs.add(".br");
        gTLDs.add(".bs");
        gTLDs.add(".bt");
        gTLDs.add(".bv");
        gTLDs.add(".bw");
        gTLDs.add(".by");
        gTLDs.add(".bz");
        gTLDs.add(".ca");
        gTLDs.add(".cc");
        gTLDs.add(".cd");
        gTLDs.add(".cf");
        gTLDs.add(".cg");
        gTLDs.add(".ch");
        gTLDs.add(".ci");
        gTLDs.add(".ck");
        gTLDs.add(".cl");
        gTLDs.add(".cm");
        gTLDs.add(".cn");
        gTLDs.add(".co");
        gTLDs.add(".cr");
        gTLDs.add(".cu");
        gTLDs.add(".cv");
        gTLDs.add(".cx");
        gTLDs.add(".cy");
        gTLDs.add(".cz");
        gTLDs.add(".de");
        gTLDs.add(".dj");
        gTLDs.add(".dk");
        gTLDs.add(".dm");
        gTLDs.add(".do");
        gTLDs.add(".dz");
        gTLDs.add(".ec");
        gTLDs.add(".ee");
        gTLDs.add(".eg");
        gTLDs.add(".er");
        gTLDs.add(".es");
        gTLDs.add(".et");
        gTLDs.add(".eu");
        gTLDs.add(".fi");
        gTLDs.add(".fj");
        gTLDs.add(".fk");
        gTLDs.add(".fm");
        gTLDs.add(".fo");
        gTLDs.add(".fr");
        gTLDs.add(".ga");
        gTLDs.add(".gb");
        gTLDs.add(".gd");
        gTLDs.add(".ge");
        gTLDs.add(".gf");
        gTLDs.add(".gg");
        gTLDs.add(".gh");
        gTLDs.add(".gi");
        gTLDs.add(".gl");
        gTLDs.add(".gm");
        gTLDs.add(".gn");
        gTLDs.add(".gp");
        gTLDs.add(".gq");
        gTLDs.add(".gr");
        gTLDs.add(".gs");
        gTLDs.add(".gt");
        gTLDs.add(".gu");
        gTLDs.add(".gw");
        gTLDs.add(".gy");
        gTLDs.add(".hk");
        gTLDs.add(".hm");
        gTLDs.add(".hn");
        gTLDs.add(".hr");
        gTLDs.add(".ht");
        gTLDs.add(".hu");
        gTLDs.add(".id");
        gTLDs.add(".ie");
        gTLDs.add(".il");
        gTLDs.add(".im");
        gTLDs.add(".in");
        gTLDs.add(".io");
        gTLDs.add(".iq");
        gTLDs.add(".ir");
        gTLDs.add(".is");
        gTLDs.add(".it");
        gTLDs.add(".je");
        gTLDs.add(".jm");
        gTLDs.add(".jo");
        gTLDs.add(".jp");
        gTLDs.add(".ke");
        gTLDs.add(".kg");
        gTLDs.add(".kh");
        gTLDs.add(".ki");
        gTLDs.add(".km");
        gTLDs.add(".kn");
        gTLDs.add(".kp");
        gTLDs.add(".kr");
        gTLDs.add(".kw");
        gTLDs.add(".ky");
        gTLDs.add(".kz");
        gTLDs.add(".la");
        gTLDs.add(".lb");
        gTLDs.add(".lc");
        gTLDs.add(".li");
        gTLDs.add(".lk");
        gTLDs.add(".lr");
        gTLDs.add(".ls");
        gTLDs.add(".lt");
        gTLDs.add(".lu");
        gTLDs.add(".lv");
        gTLDs.add(".ly");
        gTLDs.add(".ma");
        gTLDs.add(".mc");
        gTLDs.add(".md");
        gTLDs.add(".me");
        gTLDs.add(".mg");
        gTLDs.add(".mh");
        gTLDs.add(".mk");
        gTLDs.add(".ml");
        gTLDs.add(".mm");
        gTLDs.add(".mn");
        gTLDs.add(".mo");
        gTLDs.add(".mp");
        gTLDs.add(".mq");
        gTLDs.add(".mr");
        gTLDs.add(".ms");
        gTLDs.add(".mt");
        gTLDs.add(".mu");
        gTLDs.add(".mv");
        gTLDs.add(".mw");
        gTLDs.add(".mx");
        gTLDs.add(".my");
        gTLDs.add(".mz");
        gTLDs.add(".na");
        gTLDs.add(".news");
        gTLDs.add(".nc");
        gTLDs.add(".ne");
        gTLDs.add(".nf");
        gTLDs.add(".ng");
        gTLDs.add(".ni");
        gTLDs.add(".nl");
        gTLDs.add(".no");
        gTLDs.add(".np");
        gTLDs.add(".nr");
        gTLDs.add(".nu");
        gTLDs.add(".nz");
        gTLDs.add(".om");
        gTLDs.add(".pa");
        gTLDs.add(".pe");
        gTLDs.add(".pf");
        gTLDs.add(".pg");
        gTLDs.add(".ph");
        gTLDs.add(".pk");
        gTLDs.add(".pl");
        gTLDs.add(".pm");
        gTLDs.add(".pn");
        gTLDs.add(".pr");
        gTLDs.add(".ps");
        gTLDs.add(".pt");
        gTLDs.add(".pw");
        gTLDs.add(".py");
        gTLDs.add(".qa");
        gTLDs.add(".re");
        gTLDs.add(".ro");
        gTLDs.add(".rs");
        gTLDs.add(".ru");
        gTLDs.add(".rw");
        gTLDs.add(".sa");
        gTLDs.add(".sb");
        gTLDs.add(".sc");
        gTLDs.add(".sd");
        gTLDs.add(".se");
        gTLDs.add(".sg");
        gTLDs.add(".sh");
        gTLDs.add(".si");
        gTLDs.add(".sj");
        gTLDs.add(".sk");
        gTLDs.add(".sl");
        gTLDs.add(".sm");
        gTLDs.add(".sn");
        gTLDs.add(".so");
        gTLDs.add(".sr");
        gTLDs.add(".ss");
        gTLDs.add(".st");
        gTLDs.add(".su");
        gTLDs.add(".sv");
        gTLDs.add(".sy");
        gTLDs.add(".sz");
        gTLDs.add(".tc");
        gTLDs.add(".td");
        gTLDs.add(".tf");
        gTLDs.add(".tg");
        gTLDs.add(".th");
        gTLDs.add(".tj");
        gTLDs.add(".tk");
        gTLDs.add(".tl");
        gTLDs.add(".tm");
        gTLDs.add(".tn");
        gTLDs.add(".to");
        gTLDs.add(".tp");
        gTLDs.add(".tr");
        gTLDs.add(".tt");
        gTLDs.add(".tv");
        gTLDs.add(".tw");
        gTLDs.add(".tz");
        gTLDs.add(".ua");
        gTLDs.add(".ug");
        gTLDs.add(".uk");
        gTLDs.add(".us");
        gTLDs.add(".uy");
        gTLDs.add(".uz");
        gTLDs.add(".va");
        gTLDs.add(".vc");
        gTLDs.add(".ve");
        gTLDs.add(".vg");
        gTLDs.add(".vi");
        gTLDs.add(".vn");
        gTLDs.add(".vu");
        gTLDs.add(".wf");
        gTLDs.add(".ws");
        gTLDs.add(".ye");
        gTLDs.add(".yt");
        gTLDs.add(".yu");
        gTLDs.add(".za");
        gTLDs.add(".zm");
        gTLDs.add(".zw");
    }
    
    /**
     * Instantiates a new domains.
     */
    private Domains() {
        super();
    }
    
    /**
     * Checks if is valid cookie domain.
     *
     * @param domain
     *            the domain
     * @param hostName
     *            the host name
     * @return true, if is valid cookie domain
     */
    public static boolean isValidCookieDomain(String domain, final String hostName) {
        String plainDomain;
        if (!domain.startsWith(".")) {
            // Valid domains must start with a dot
            // according to RFC 2109, but
            // RFC 2965 specifies a dot is prepended
            // in the Set-Cookie2 header.
            plainDomain = domain;
            domain = "." + domain;
        } else {
            plainDomain = domain.substring(1);
        }
        String plainDomainTL = plainDomain.toLowerCase();
        String hostNameTL = hostName.toLowerCase();
        if (!hostNameTL.endsWith(plainDomainTL)) {
            return false;
        }
        int lastDotIdx = domain.lastIndexOf('.');
        if (lastDotIdx == -1) {
            return false;
        }
        String suffix = domain.substring(lastDotIdx).toLowerCase();
        if (gTLDs.contains(suffix)) {
            return Strings.countChars(domain, '.') >= 2;
        } else {
            return Strings.countChars(domain, '.') >= 3;
        }
    }
    
    /**
     * Ends with gtld.
     *
     * @param host
     *            A host name in lower case.
     * @return true, if successful
     */
    public static boolean endsWithGTLD(final String host) {
        Iterator<String> i = gTLDs.iterator();
        while (i.hasNext()) {
            String ending = i.next();
            if (host.endsWith(ending)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if is likely host name.
     *
     * @param name
     *            the name
     * @return true, if is likely host name
     */
    public static boolean isLikelyHostName(final String name) {
        String nameTL = name.toLowerCase();
        if (nameTL.startsWith("www.")) {
            return true;
        }
        if (endsWithGTLD(name)) {
            return true;
        }
        int lastDotIdx = nameTL.lastIndexOf('.');
        if (lastDotIdx == -1) {
            return false;
        }
        // Check for country code.
        return lastDotIdx == (nameTL.length() - 3);
    }
    
    /**
     * Gets the possible domains.
     *
     * @param hostName
     *            the host name
     * @return the possible domains
     */
    public static Collection<String> getPossibleDomains(final String hostName) {
        Collection<String> domains = new LinkedList<String>();
        domains.add(hostName);
        int dotIdx = hostName.indexOf('.', 1);
        if (dotIdx == -1) {
            return domains;
        }
        String testDomain = hostName.substring(dotIdx);
        if (!Domains.isValidCookieDomain(testDomain, hostName)) {
            return domains;
        }
        domains.addAll(Domains.getPossibleDomains(testDomain.substring(1)));
        return domains;
    }
}
