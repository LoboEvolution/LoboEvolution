/*
 * Copyright (C) 1999-2017 David Schweinsberg.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.Locatable;
import com.steadystate.css.parser.SACMediaListImpl;
import com.steadystate.css.parser.media.MediaQuery;
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

	private List<MediaQuery> mediaQueries_;

	/**
	 * Creates new MediaList.
	 * 
	 * @param mediaList
	 *            the media list
	 */
	public MediaListImpl(final SACMediaList mediaList) {
		this();

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
		mediaQueries_ = new ArrayList<MediaQuery>(10);
	}

	@Override
	public String getMediaText() {
		return getMediaText(null);
	}

	/**
	 * Returns a string representation of the rule based on the given format. If
	 * provided format is null, the result is the same as getCssText()
	 *
	 * @param format
	 *            the formatting rules
	 * @return the formated string
	 */
	public String getMediaText(final CSSFormat format) {
		final StringBuilder sb = new StringBuilder("");
		boolean isNotFirst = false;
		for (MediaQuery mediaQuery : mediaQueries_) {
			if (isNotFirst) {
				sb.append(", ");
			} else {
				isNotFirst = true;
			}
			sb.append(mediaQuery.getCssText(format));
		}
		return sb.toString();
	}

	@Override
	public void setMediaText(final String mediaText) throws DOMException {
		final InputSource source = new InputSource(new StringReader(mediaText));
		try {
			final CSSOMParser parser = new CSSOMParser();
			parser.setErrorHandler(ThrowCssExceptionErrorHandler.INSTANCE);
			final SACMediaList sml = parser.parseMedia(source);
			setMediaList(sml);
		} catch (final CSSParseException e) {
			throw new DOMException(DOMException.SYNTAX_ERR, e.getLocalizedMessage());
		} catch (final IOException e) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, e.getLocalizedMessage());
		}
	}

	@Override
	public int getLength() {
		return mediaQueries_.size();
	}

	@Override
	public String item(final int index) {
		final MediaQuery mq = mediaQuery(index);
		if (null == mq) {
			return null;
		}

		return mq.getMedia();
	}

	public MediaQuery mediaQuery(final int index) {
		if (index < 0 || (index >= mediaQueries_.size())) {
			return null;
		}
		return mediaQueries_.get(index);
	}

	@Override
	public void deleteMedium(final String oldMedium) throws DOMException {
		for (MediaQuery mediaQuery : mediaQueries_) {
			final String str = mediaQuery.getMedia();
			if (str.equalsIgnoreCase(oldMedium)) {
				mediaQueries_.remove(mediaQuery);
				return;
			}
		}
		throw new DOMExceptionImpl(DOMException.NOT_FOUND_ERR, DOMExceptionImpl.NOT_FOUND);
	}

	@Override
	public void appendMedium(final String newMedium) throws DOMException {
		mediaQueries_.add(new MediaQuery(newMedium));
	}

	@Override
	public String toString() {
		return getMediaText(null);
	}

	public void setMedia(final List<String> media) {
		mediaQueries_.clear();
		for (String medium : media) {
			mediaQueries_.add(new MediaQuery(medium));
		}
	}

	private void setMediaList(final SACMediaList mediaList) {
		if (mediaList instanceof SACMediaListImpl) {
			final SACMediaListImpl impl = (SACMediaListImpl) mediaList;
			for (int i = 0; i < mediaList.getLength(); i++) {
				mediaQueries_.add(impl.mediaQuery(i));
			}
			return;
		}

		for (int i = 0; i < mediaList.getLength(); i++) {
			mediaQueries_.add(new MediaQuery(mediaList.item(i)));
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
		hash = LangUtils.hashCode(hash, mediaQueries_);
		return hash;
	}
}
