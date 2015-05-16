/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2015 David Schweinsberg.  All rights reserved.
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
import com.steadystate.css.parser.SACMediaListImpl;
import com.steadystate.css.parser.media.MediaQuery;
import com.steadystate.css.userdata.UserDataConstants;
import com.steadystate.css.util.LangUtils;
import com.steadystate.css.util.ThrowCssExceptionErrorHandler;

/**
 * Implements {@link MediaList}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class MediaListImpl extends CSSOMObjectImpl implements MediaList {
    private static final long serialVersionUID = 6662784733573034870L;

    private List<MediaQuery> mediaQueries_;

    /**
     * Creates new MediaList.
     * @param mediaList the media list
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
     * Constructor.
     * The attributes are null.
     */
    public MediaListImpl() {
        mediaQueries_ = new ArrayList<MediaQuery>(10);
    }

    public String getMediaText() {
        return getMediaText(null);
    }

    /**
     * Returns a string representation of the rule based on the given format.
     * If provided format is null, the result is the same as getCssText()
     *
     * @param format the formating rules
     * @return the formated string
     */
    public String getMediaText(final CSSFormat format) {
        final StringBuilder sb = new StringBuilder("");
        boolean isNotFirst = false;
        for (MediaQuery mediaQuery : mediaQueries_) {
            if (isNotFirst) {
                sb.append(", ");
            }
            else {
                isNotFirst = true;
            }
            sb.append(mediaQuery.getMedia());
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
        }
        catch (final CSSParseException e) {
            throw new DOMException(DOMException.SYNTAX_ERR, e.getLocalizedMessage());
        }
        catch (final IOException e) {
            throw new DOMException(DOMException.NOT_FOUND_ERR, e.getLocalizedMessage());
        }
    }

    public int getLength() {
        return mediaQueries_.size();
    }

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
