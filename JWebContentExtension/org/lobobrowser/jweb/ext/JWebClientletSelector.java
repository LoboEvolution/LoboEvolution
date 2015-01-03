/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.jweb.ext;

import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletRequest;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ClientletSelector;
import org.lobobrowser.jweb.java.JavaSourceClientlet;
import org.lobobrowser.jweb.javafx.CompiledJavaFXClientlet;
import org.lobobrowser.util.Strings;

public class JWebClientletSelector implements ClientletSelector {
	private static final String JAVAFX_REQ_VERSION = "1.6.0_10";

	public Clientlet lastResortSelect(ClientletRequest request,
			ClientletResponse response) {
		return null;
	}

	private void checkJavaVersionForJWeb(String missingFeature,
			String preferredVersion) throws InvalidVersionException {
		try {
			Class.forName("javax.tools.JavaCompiler");
			Class.forName("javax.annotation.processing.ProcessingEnvironment");
		} catch (ClassNotFoundException cnf) {
			throw new InvalidVersionException(missingFeature, preferredVersion);
		}
		if (preferredVersion != null) {
			String currentVersion = System.getProperty("java.version");
			if (Strings.compareVersions(currentVersion, preferredVersion, true) < 0) {
				throw new InvalidVersionException(missingFeature,
						preferredVersion);
			}
		}
	}

	public Clientlet select(ClientletRequest request, ClientletResponse response) {
		try {
			if (response
					.matches("text/x-javafx-source", new String[] { ".fx" })) {
				this.checkJavaVersionForJWeb("JavaFX 1.0+", JAVAFX_REQ_VERSION);
				return new CompiledJavaFXClientlet();
			} else if (response.matches("text/x-java-source",
					new String[] { ".java" })) {
				this.checkJavaVersionForJWeb("the Java Compiler API", null);
				return new JavaSourceClientlet();
			} else {
				String mimeType = response.getMimeType();
				String mimeTypeTL = mimeType == null ? null : mimeType
						.toLowerCase();
				if (mimeTypeTL == null || mimeTypeTL.startsWith("text/")
						|| mimeTypeTL.startsWith("application/x-java")) {
					// Java files sometimes have text/plain as MIME-Type.
					String path = response.getResponseURL().getPath();
					if (path.endsWith(".java")) {
						this.checkJavaVersionForJWeb("the Java Compiler API",
								null);
						return new JavaSourceClientlet();
					} else if (path.endsWith(".fx")) {
						this.checkJavaVersionForJWeb("JavaFX 1.0+",
								JAVAFX_REQ_VERSION);
						return new CompiledJavaFXClientlet();
					}
				}
			}
			return null;
		} catch (InvalidVersionException ve) {
			return new BadVersionClientlet(ve.getMissingFeature(),
					ve.getPreferredVersion());
		}
	}

	private static class InvalidVersionException extends Exception {

		private static final long serialVersionUID = 1L;
		private final String preferredVersion;

		public InvalidVersionException(String missingFeature,
				String preferredVersion) {
			super(missingFeature);
			this.preferredVersion = preferredVersion;
		}

		public String getMissingFeature() {
			return this.getMessage();
		}

		public String getPreferredVersion() {
			return this.preferredVersion;
		}
	}
}
