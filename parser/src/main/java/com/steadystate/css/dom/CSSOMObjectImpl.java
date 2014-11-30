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

	private static final long serialVersionUID = 0L;

	private Map<String, Object> userDataMap_;

	public Map<String, Object> getUserDataMap() {
		if (userDataMap_ == null) {
			userDataMap_ = new Hashtable<String, Object>();
		}
		return userDataMap_;
	}

	public void setUserDataMap(final Map<String, Object> userDataMap) {
		userDataMap_ = userDataMap;
	}

	public CSSOMObjectImpl() {
		super();
	}

	public Object getUserData(final String key) {
		return getUserDataMap().get(key);
	}

	public Object setUserData(final String key, final Object data) {
		return getUserDataMap().put(key, data);
	}

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

	@Override
	public int hashCode() {
		int hash = LangUtils.HASH_SEED;
		hash = LangUtils.hashCode(hash, userDataMap_);
		return hash;
	}
}
