/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
		gTLDs.add(".com");
		gTLDs.add(".edu");
		gTLDs.add(".gov");
		gTLDs.add(".int");
		gTLDs.add(".mil");
		gTLDs.add(".net");
		gTLDs.add(".org");
		gTLDs.add(".biz");
		gTLDs.add(".info");
		gTLDs.add(".name");
		gTLDs.add(".pro");
		gTLDs.add(".aero");
		gTLDs.add(".coop");
		gTLDs.add(".museum");
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
	 * @param domain the domain
	 * @param hostName the host name
	 * @return true, if is valid cookie domain
	 */
	public static boolean isValidCookieDomain(String domain, String hostName) {
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
	 * @param host            A host name in lower case.
	 * @return true, if successful
	 */
	public static boolean endsWithGTLD(String host) {
		Iterator<String> i = gTLDs.iterator();
		while (i.hasNext()) {
			String ending = (String) i.next();
			if (host.endsWith(ending)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if is likely host name.
	 *
	 * @param name the name
	 * @return true, if is likely host name
	 */
	public static boolean isLikelyHostName(String name) {
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
		return lastDotIdx == nameTL.length() - 3;
	}

	/**
	 * Gets the possible domains.
	 *
	 * @param hostName the host name
	 * @return the possible domains
	 */
	public static Collection<String> getPossibleDomains(String hostName) {
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
