/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Mar 13, 2005
 */
package org.loboevolution.request;

import org.loboevolution.settings.GeneralSettings;
import org.loboevolution.ua.UserAgent;

/**
 * The Class UserAgentImpl.
 *
 * @author J. H. S.
 */
public class UserAgentImpl implements UserAgent {

	/** The Constant instance. */
	private static final UserAgentImpl instance = new UserAgentImpl();

	/** The text value. */
	private volatile String textValue = null;

	/**
	 * Instantiates a new user agent impl.
	 */
	private UserAgentImpl() {
	}

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
	public static UserAgentImpl getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.UserAgent#getName()
	 */
	@Override
	public String getName() {
		return "Lobo";
	}

	/**
	 * Gets the major version.
	 *
	 * @return the major version
	 */
	public String getMajorVersion() {
		return "0";
	}

	/**
	 * Gets the minor version.
	 *
	 * @return the minor version
	 */
	public String getMinorVersion() {
		return "99.3";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.UserAgent#getVersion()
	 */
	@Override
	public String getVersion() {
		return this.getMajorVersion() + "." + this.getMinorVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.UserAgent#getJavaVersion()
	 */
	@Override
	public String getJavaVersion() {
		return System.getProperty("java.version");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.UserAgent#getUserAgentString()
	 */
	@Override
	public String getUserAgentString() {
		String tv = this.textValue;
		if (tv == null) {
			GeneralSettings settings = GeneralSettings.getNetwork();
			boolean spoofIE = settings.isIe();
			String ieVersion = settings.getIeVersion();
			StringBuilder sb = new StringBuilder();
			sb.append("Mozilla/" + settings.getMozVersion());
			sb.append(" (compatible" + (spoofIE ? "; MSIE " + ieVersion : "") + "; ");
			sb.append(this.getOs() + ") " + this.getName() + "/" + this.getVersion());
			tv = sb.toString();
			this.textValue = tv;
		}
		return tv;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
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
	@Override
	public String getNameAndVersion() {
		return this.getName() + " " + this.getVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.UserAgent#getInfoUrl()
	 */
	@Override
	public String getInfoUrl() {
		return "http://sourceforge.net/projects/loboevolution/";
	}

	/**
	 * Gets the os.
	 *
	 * @return the os
	 */
	private String getOs() {
		return System.getProperty("os.name") + " " + System.getProperty("os.version");
	}
}
