/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.info;

import java.io.Serializable;

/**
 * <p>TabInfo class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class TabInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String url;
	
	private String indexTab;
	
	private String title;
	
	
	/**
	 * <p>Getter for the field url.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * <p>Getter for the field indexTab.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getIndexTab() {
		return indexTab;
	}

	/**
	 * <p>Setter for the field url.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * <p>Setter for the field indexTab.</p>
	 *
	 * @param indexTab a {@link java.lang.String} object.
	 */
	public void setIndexTab(String indexTab) {
		this.indexTab = indexTab;
	}

	/**
	 * <p>Getter for the field title.</p>
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * <p>Setter for the field title.</p>
	 *
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	

}
