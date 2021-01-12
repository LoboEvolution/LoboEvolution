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

package org.loboevolution.html.dom.smil;

import org.w3c.dom.DOMException;

/**
 * This interface define the set of animation extensions for SMIL. The
 * attributes will go in a XLink interface.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SMILAnimation extends SMILElement, ElementTargetAttributes, ElementTime, ElementTimeControl {
	
	// additiveTypes
	/** Constant ADDITIVE_REPLACE=0 */
	short ADDITIVE_REPLACE = 0;

	/** Constant ADDITIVE_SUM=1 */
	short ADDITIVE_SUM = 1;

	// calcModeTypes
	/** Constant CALCMODE_DISCRETE=0 */
	short CALCMODE_DISCRETE = 0;
	
	/** Constant CALCMODE_LINEAR=1 */
	short CALCMODE_LINEAR = 1;
	
	/** Constant CALCMODE_PACED=2 */
	short CALCMODE_PACED = 2;
	
	/** Constant CALCMODE_SPLINE=3 */
	short CALCMODE_SPLINE = 3;

	// accumulateTypes
	/** Constant ACCUMULATE_NONE=0 */
	short ACCUMULATE_NONE = 0;
	
	/** Constant ACCUMULATE_SUM=1 */
	short ACCUMULATE_SUM = 1;
	
	/**
	 * A code representing the value of the additive attribute, as defined
	 * above. Default value is ADDITIVE_REPLACE .
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a short.
	 */
	default short getAdditive() {return 0;}

	/**
	 * <p>setAdditive.</p>
	 *
	 * @param additive a short.
	 */
	default void setAdditive(short additive) {}

	

	/**
	 * A code representing the value of the accumulate attribute, as defined
	 * above. Default value is ACCUMULATE_NONE .
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a short.
	 */
	default short getAccumulate() {return 0;}

	/**
	 * <p>setAccumulate.</p>
	 *
	 * @param accumulate a short.
	 */
	default void setAccumulate(short accumulate) {}

    /**
	 * A code representing the value of the calcMode attribute, as defined
	 * above.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a short.
	 */
	default short getCalcMode() {return 0;}

	/**
	 * <p>setCalcMode.</p>
	 *
	 * @param calcMode a short.
	 */
	default void setCalcMode(short calcMode) {}

    /**
	 * A DOMString representing the value of the keySplines
	 * attribute. Need an interface a point (x1,y1,x2,y2)
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link java.lang.String} object.
	 */
	default String getKeySplines() {return "";}

	/**
	 * <p>setKeySplines.</p>
	 *
	 * @param keySplines a {@link java.lang.String} object.
	 */
	default void setKeySplines(String keySplines) {}

    /**
	 * A list of the time value of the keyTimes attribute.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link org.loboevolution.html.dom.smil.TimeList} object.
	 */
	default TimeList getKeyTimes() {return null;}

	/**
	 * <p>setKeyTimes.</p>
	 *
	 * @param keyTimes a {@link org.loboevolution.html.dom.smil.TimeList} object.
	 */
	default void setKeyTimes(TimeList keyTimes)  {}

    /**
	 * A DOMString representing the value of the values attribute.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link java.lang.String} object.
	 */
	String getValues();

	/**
	 * <p>setValues.</p>
	 *
	 * @param values a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setValues(String values) throws DOMException;

	/**
	 * A DOMString representing the value of the from attribute.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link java.lang.String} object.
	 */
	String getFrom();

	/**
	 * <p>setFrom.</p>
	 *
	 * @param from a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setFrom(String from) throws DOMException;

	/**
	 * A DOMString representing the value of the to attribute.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link java.lang.String} object.
	 */
	String getTo();

	/**
	 * <p>setTo.</p>
	 *
	 * @param to a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setTo(String to) throws DOMException;

	/**
	 * A DOMString representing the value of the by attribute.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link java.lang.String} object.
	 */
	String getBy();

	/**
	 * <p>setBy.</p>
	 *
	 * @param by a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setBy(String by) throws DOMException;

}
