/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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