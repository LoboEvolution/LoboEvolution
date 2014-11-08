/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2011 David Schweinsberg.  All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * To contact the authors of the library:
 *
 * http://cssparser.sourceforge.net/
 * mailto:davidsch@users.sourceforge.net
 *
 */

package com.steadystate.css.dom;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.Locator;
import org.w3c.css.sac.SACMediaList;
import org.w3c.dom.DOMException;
import org.w3c.dom.stylesheets.MediaList;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.Locatable;
import com.steadystate.css.userdata.UserDataConstants;
import com.steadystate.css.util.LangUtils;
import com.steadystate.css.util.ThrowCssExceptionErrorHandler;

/**
 * Implements {@link MediaList}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David
 *         Schweinsberg</a>
 * @author rbri
 */
public class MediaListImpl extends CSSOMObjectImpl implements MediaList {
	private static final long serialVersionUID = 6662784733573034870L;

	private List<String> media_ = new ArrayList<String>();

	/**
	 * Creates new MediaList.
	 * 
	 * @param mediaList
	 *            the media list
	 */
	public MediaListImpl(final SACMediaList mediaList) {
		setMediaList(mediaList);

		if (mediaList instanceof Locatable) {
			final Locator locator = ((Locatable) mediaList).getLocator();
			if (locator != null) {
				setUserData(UserDataConstants.KEY_LOCATOR, locator);
			}
		}
	}

	/**
	 * Constructor. The attributes are null.
	 */
	public MediaListImpl() {
		super();
	}

	public String getMediaText() {
		final StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < media_.size(); i++) {
			sb.append(media_.get(i));
			if (i < media_.size() - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	public void setMediaText(final String mediaText) throws DOMException {
		final InputSource source = new InputSource(new StringReader(mediaText));
		try {
			final CSSOMParser parser = new CSSOMParser();
			parser.setErrorHandler(ThrowCssExceptionErrorHandler.INSTANCE);
			final SACMediaList sml = parser.parseMedia(source);
			setMediaList(sml);
		} catch (final CSSParseException e) {
			throw new DOMException(DOMException.SYNTAX_ERR,
					e.getLocalizedMessage());
		} catch (final IOException e) {
			throw new DOMException(DOMException.NOT_FOUND_ERR,
					e.getLocalizedMessage());
		}
	}

	public int getLength() {
		return media_.size();
	}

	public String item(final int index) {
		return (index < media_.size()) ? media_.get(index) : null;
	}

	public void deleteMedium(final String oldMedium) throws DOMException {
		for (int i = 0; i < media_.size(); i++) {
			final String str = media_.get(i);
			if (str.equalsIgnoreCase(oldMedium)) {
				media_.remove(i);
				return;
			}
		}
		throw new DOMExceptionImpl(DOMException.NOT_FOUND_ERR,
				DOMExceptionImpl.NOT_FOUND);
	}

	public void appendMedium(final String newMedium) throws DOMException {
		media_.add(newMedium);
	}

	@Override
	public String toString() {
		return getMediaText();
	}

	public void setMedia(final List<String> media) {
		media_ = media;
	}

	private void setMediaList(final SACMediaList mediaList) {
		for (int i = 0; i < mediaList.getLength(); i++) {
			media_.add(mediaList.item(i));
		}
	}

	private boolean equalsMedia(final MediaList ml) {
		if ((ml == null) || (getLength() != ml.getLength())) {
			return false;
		}
		for (int i = 0; i < getLength(); i++) {
			final String m1 = item(i);
			final String m2 = ml.item(i);
			if (!LangUtils.equals(m1, m2)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MediaList)) {
			return false;
		}
		final MediaList ml = (MediaList) obj;
		return super.equals(obj) && equalsMedia(ml);
	}

	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = LangUtils.hashCode(hash, media_);
		return hash;
	}
}
