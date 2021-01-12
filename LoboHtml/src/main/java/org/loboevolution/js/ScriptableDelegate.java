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
package org.loboevolution.js;

import org.mozilla.javascript.Scriptable;

/**
 * Java classes used in Javascript should implement this interface. While all
 * classes can be mapped to JavaScript, implementing this interface ensures that
 * the Java object proxy is not garbage collected as long as the Java object is
 * not garbage collected.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface ScriptableDelegate {
	/**
	 * <p>getScriptable.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Scriptable} object.
	 */
	Scriptable getScriptable();

	/**
	 * <p>setScriptable.</p>
	 *
	 * @param scriptable a {@link org.mozilla.javascript.Scriptable} object.
	 */
	void setScriptable(Scriptable scriptable);
}
