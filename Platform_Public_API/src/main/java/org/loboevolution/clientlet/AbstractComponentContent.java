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
package org.loboevolution.clientlet;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import org.loboevolution.http.Cookie;
import org.loboevolution.info.MetaInfo;

/**
 * Abstract implementation of {@link ComponentContent}. It is recommended that
 * <code>ComponentContent</code> implementations extend this class for forward
 * compatibility.
 */
public abstract class AbstractComponentContent implements ComponentContent {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ComponentContent#canCopy()
	 */
	@Override
	public boolean canCopy() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ComponentContent#copy()
	 */
	@Override
	public boolean copy() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ComponentContent#getComponent()
	 */
	@Override
	public abstract Component getComponent();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ComponentContent#getSourceCode()
	 */
	@Override
	public String getSourceCode() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ComponentContent#getTitle()
	 */
	@Override
	public String getTitle() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ComponentContent#getDescription()
	 */
	@Override
	public String getDescription() {
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ComponentContent#addNotify()
	 */
	@Override
	public void addNotify() {
		// Method not implemented
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ComponentContent#removeNotify()
	 */
	@Override
	public void removeNotify() {
		// Method not implemented
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ComponentContent#getContentObject()
	 */
	@Override
	public Object getContentObject() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ComponentContent#getMimeType()
	 */
	@Override
	public String getMimeType() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.clientlet.ComponentContent#setProperty(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void setProperty(String name, Object value) {
		// Method not implemented
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ComponentContent#getMetaList()
	 */
	@Override
	public List<MetaInfo> getMetaList() {
		return new ArrayList<MetaInfo>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ComponentContent#getStyleList()
	 */
	@Override
	public List<MetaInfo> getStyleList() {
		return new ArrayList<MetaInfo>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ComponentContent#getMediaList()
	 */
	@Override
	public List<MetaInfo> getMediaList() {
		return new ArrayList<MetaInfo>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ComponentContent#getJSList()
	 */
	@Override
	public List<MetaInfo> getJSList() {
		return new ArrayList<MetaInfo>();
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ComponentContent#getCookieList()
	 */
	@Override
	public List<Cookie> getCookieList(String url) {
		return new ArrayList<Cookie>();
	}

	// Backward compatibility note: Additional methods should provide an empty
	// body.
}
