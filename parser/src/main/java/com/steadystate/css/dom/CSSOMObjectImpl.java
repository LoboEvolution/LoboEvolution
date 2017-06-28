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

	@Override
	public Object getUserData(final String key) {
		return getUserDataMap().get(key);
	}

	@Override
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
