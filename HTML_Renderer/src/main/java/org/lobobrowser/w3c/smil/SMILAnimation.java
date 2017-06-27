/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.smil;

import org.w3c.dom.DOMException;
import org.w3c.dom.smil.ElementTimeControl;

/**
 *  This interface define the set of animation extensions for SMIL.  The  
 * attributes will go in a XLink interface. 
 */
public interface SMILAnimation extends SMILElement, ElementTargetAttributes, ElementTime, ElementTimeControl {
    // additiveTypes
    public static final short ADDITIVE_REPLACE          = 0;
    public static final short ADDITIVE_SUM              = 1;

    /**
     *  A code representing the value of the  additive attribute, as defined 
     * above. Default value is <code>ADDITIVE_REPLACE</code> . 
     * @exception DOMException
     *    NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is readonly. 
     */
    public short getAdditive();
    public void setAdditive(short additive)
                                throws DOMException;

    // accumulateTypes
    public static final short ACCUMULATE_NONE           = 0;
    public static final short ACCUMULATE_SUM            = 1;

    /**
     *  A code representing the value of the  accumulate attribute, as defined 
     * above. Default value is <code>ACCUMULATE_NONE</code> . 
     * @exception DOMException
     *    NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is readonly. 
     */
    public short getAccumulate();
    public void setAccumulate(short accumulate)
                                throws DOMException;

    // calcModeTypes
    public static final short CALCMODE_DISCRETE         = 0;
    public static final short CALCMODE_LINEAR           = 1;
    public static final short CALCMODE_PACED            = 2;
    public static final short CALCMODE_SPLINE           = 3;

    /**
     *  A code representing the value of the  calcMode attribute, as defined 
     * above. 
     * @exception DOMException
     *    NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is readonly. 
     */
    public short getCalcMode();
    public void setCalcMode(short calcMode)
                                throws DOMException;

    /**
     *  A <code>DOMString</code> representing the value of the  keySplines 
     * attribute.  Need an interface a point (x1,y1,x2,y2) 
     * @exception DOMException
     *    NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is readonly. 
     */
    public String getKeySplines();
    public void setKeySplines(String keySplines)
                                throws DOMException;

    /**
     *  A list of the time value of the  keyTimes attribute. 
     * @exception DOMException
     *    NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is readonly. 
     */
    public TimeList getKeyTimes();
    public void setKeyTimes(TimeList keyTimes)
                                throws DOMException;

    /**
     *  A <code>DOMString</code> representing the value of the  values 
     * attribute. 
     * @exception DOMException
     *    NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is readonly. 
     */
    public String getValues();
    public void setValues(String values)
                                throws DOMException;

    /**
     *  A <code>DOMString</code> representing the value of the  from attribute.
     *  
     * @exception DOMException
     *    NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is readonly. 
     */
    public String getFrom();
    public void setFrom(String from)
                                throws DOMException;

    /**
     *  A <code>DOMString</code> representing the value of the  to attribute. 
     * @exception DOMException
     *    NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is readonly. 
     */
    public String getTo();
    public void setTo(String to)
                                throws DOMException;

    /**
     *  A <code>DOMString</code> representing the value of the  by attribute. 
     * @exception DOMException
     *    NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is readonly. 
     */
    public String getBy();
    public void setBy(String by)
                                throws DOMException;

}

