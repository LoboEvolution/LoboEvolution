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

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import com.steadystate.css.util.LangUtils;


/**
 * Implementation of {@link CSSOMObject}.
 *
 * @author koch
 */
public class CSSOMObjectImpl implements CSSOMObject, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 0L;

    /** The user data map_. */
    private Map<String, Object> userDataMap_;

    /**
     * Gets the user data map.
     *
     * @return the user data map
     */
    public Map<String, Object> getUserDataMap() {
        if (userDataMap_ == null) {
            userDataMap_ = new Hashtable<String, Object>();
        }
        return userDataMap_;
    }

    /**
     * Sets the user data map.
     *
     * @param userDataMap the user data map
     */
    public void setUserDataMap(final Map<String, Object> userDataMap) {
        userDataMap_ = userDataMap;
    }

    /**
     * Instantiates a new CSSOM object impl.
     */
    public CSSOMObjectImpl() {
        super();
    }

    /* (non-Javadoc)
     * @see com.steadystate.css.dom.CSSOMObject#getUserData(java.lang.String)
     */
    public Object getUserData(final String key) {
        return getUserDataMap().get(key);
    }

    /* (non-Javadoc)
     * @see com.steadystate.css.dom.CSSOMObject#setUserData(java.lang.String, java.lang.Object)
     */
    public Object setUserData(final String key, final Object data) {
        return getUserDataMap().put(key, data);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSOMObjectImpl)) {
            return false;
        }
        final CSSOMObjectImpl coi = (CSSOMObjectImpl) obj;
        return LangUtils.equals(userDataMap_, coi.userDataMap_);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = LangUtils.HASH_SEED;
        hash = LangUtils.hashCode(hash, userDataMap_);
        return hash;
    }
}
