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

package org.loboevolution.dom.css;

import org.w3c.dom.css.CSS2Properties;


/**
 * <p>
 * CSS3Properties interface.
 * </p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface CSS3Properties extends CSS2Properties {

	/**
	 * <p>
	 * getAlignItems.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAlignItems();

	/**
	 * <p>
	 * getAlignContent.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAlignContent();

	/**
	 * <p>
	 * getBoxSizing.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getBoxSizing();

	/**
	 * <p>
	 * getClipPath.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getClipPath();

	/**
	 * <p>
	 * getClipRule.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getClipRule();

	/**
	 * <p>
	 * getFill.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getFill();

	/**
	 * <p>
	 * getFloat.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getFloat();

	/**
	 * <p>
	 * getFlexDirection.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getFlexDirection();

	/**
	 * <p>
	 * flexWrap.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getFlexWrap();

	/**
	 * <p>
	 * getFlexFlow.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getFlexFlow();

	/**
	 * <p>
	 * getJustifyContent.
	 * </p>
	 *
	 */
	String getJustifyContent();

	/**
	 * <p>
	 * setClipPath.
	 * </p>
	 *
	 * @param clip a {@link java.lang.String} object.
	 */
	void setClipPath(String clip);

	/**
	 * <p>
	 * setClipRule.
	 * </p>
	 *
	 * @param clip a {@link java.lang.String} object.
	 */
	void setClipRule(String clip);

	/**
	 * <p>
	 * setFloat.
	 * </p>
	 *
	 * @param flt a {@link java.lang.String} object.
	 */
	void setFloat(String flt);
}
