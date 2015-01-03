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
/*
 * Created on Mar 13, 2005
 */
package org.lobobrowser.request;

import java.security.AccessController;

import org.lobobrowser.settings.GeneralSettings;
import org.lobobrowser.ua.UserAgent;

/**
 * @author J. H. S.
 */
public class UserAgentImpl implements UserAgent {
	private static final UserAgentImpl instance = new UserAgentImpl();

	// private static final Logger logger =
	// Logger.getLogger(UserAgentImpl.class.getName());

	private UserAgentImpl() {
	}

	public static UserAgentImpl getInstance() {
		return instance;
	}

	public String getName() {
		return "Lobo";
	}

	public String getMajorVersion() {
		return "0";
	}

	public String getMinorVersion() {
		return "98.5";
	}

	public String getVersion() {
		return this.getMajorVersion() + "." + this.getMinorVersion();
	}

	public String getJavaVersion() {
		return System.getProperty("java.version");
	}

	private volatile String textValue = null;

	public String getUserAgentString() {
		String tv = this.textValue;
		if (tv == null) {
			GeneralSettings settings = AccessController
					.doPrivileged(new java.security.PrivilegedAction<GeneralSettings>() {
						public GeneralSettings run() {
							return GeneralSettings.getInstance();
						}
					});
			boolean spoofIE = settings.isSpoofIE();
			String ieVersion = settings.getIeVersion();
			tv = "Mozilla/" + settings.getMozVersion() + " (compatible"
					+ (spoofIE ? "; MSIE " + ieVersion : "") + "; "
					+ this.getOs() + ") " + this.getName() + "/"
					+ this.getVersion();
			this.textValue = tv;
		}
		return tv;
	}

	public String toString() {
		return this.getUserAgentString();
	}

	/**
	 * Removes cached user agent string.
	 */
	public void invalidateUserAgent() {
		this.textValue = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.clientlet.UserAgent#getNameAndVersion()
	 */
	public String getNameAndVersion() {
		return this.getName() + " " + this.getVersion();
	}

	public String getInfoUrl() {
		return "http://sourceforge.net/projects/loboevolution/";
	}

	private String getOs() {
		return System.getProperty("os.name") + " "
				+ System.getProperty("os.version");
	}
}
