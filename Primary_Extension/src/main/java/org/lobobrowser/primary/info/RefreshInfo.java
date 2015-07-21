/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.primary.info;

/**
 * The Class RefreshInfo.
 */
public class RefreshInfo {

    /** The wait seconds. */
    private int waitSeconds;

    /** The destination url. */
    private String destinationUrl;

    /**
     * Instantiates a new refresh info.
     *
     * @param waitSeconds
     *            the wait seconds
     * @param destinationUrl
     *            the destination url
     */
    public RefreshInfo(final int waitSeconds, final String destinationUrl) {
        super();
        this.waitSeconds = waitSeconds;
        this.destinationUrl = destinationUrl;
    }

	/**
	 * @return the waitSeconds
	 */
	public int getWaitSeconds() {
		return waitSeconds;
	}

	/**
	 * @param waitSeconds the waitSeconds to set
	 */
	public void setWaitSeconds(int waitSeconds) {
		this.waitSeconds = waitSeconds;
	}

	/**
	 * @return the destinationUrl
	 */
	public String getDestinationUrl() {
		return destinationUrl;
	}

	/**
	 * @param destinationUrl the destinationUrl to set
	 */
	public void setDestinationUrl(String destinationUrl) {
		this.destinationUrl = destinationUrl;
	}
}
