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
package org.loboevolution.common;

import java.util.Collection;
import java.util.HashSet;

/**
 * <p>Domains class.</p>
 */
public final class Domains {

	private static final Collection<String> gTLDs;

	private Domains() {
		super();
	}

	static {
		gTLDs = new HashSet<>();
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
		// TODO: New gTLDs?
	}

	/**
	 * <p>isValidCookieDomain.</p>
	 *
	 * @param domain a {@link java.lang.String} object.
	 * @param hostName a {@link java.lang.String} object.
	 * @return a boolean.
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
		final String plainDomainTL = plainDomain.toLowerCase();
		final String hostNameTL = hostName.toLowerCase();
		if (!hostNameTL.endsWith(plainDomainTL)) {
			return false;
		}
		final int lastDotIdx = domain.lastIndexOf('.');
		if (lastDotIdx == -1) {
			return false;
		}
		final String suffix = domain.substring(lastDotIdx).toLowerCase();
		if (gTLDs.contains(suffix)) {
			return Strings.countChars(domain, '.') >= 2;
		} else {
			return Strings.countChars(domain, '.') >= 3;
		}
	}
}