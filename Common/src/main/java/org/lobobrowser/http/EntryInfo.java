/*
 * GNU GENERAL LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 -
 * 2015 Lobo Evolution This program is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either verion 2 of the License, or
 * (at your option) any later version. This program is distributed in the hope
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General License for more details. You should have received a copy of the GNU
 * General Public License along with this library; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301
 * USA Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.http;

/**
 * The Class EntryInfo.
 */
public class EntryInfo {
    /** The value class. */
    private Class<?> valueClass;
    /** The approximate size. */
    private int approximateSize;
    
    /**
     * Instantiates a new entry info.
     *
     * @param valueClass
     *            the value class
     * @param approximateSize
     *            the approximate size
     */
    public EntryInfo(final Class<?> valueClass, final int approximateSize) {
        super();
        this.valueClass = valueClass;
        this.approximateSize = approximateSize;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        Class<?> vc = this.valueClass;
        String vcName = vc == null ? "<none>" : vc.getName();
        return "[class=" + vcName + ",approx-size=" + this.approximateSize
                + "]";
    }
    
    /**
     * @return the valueClass
     */
    public Class<?> getValueClass() {
        return valueClass;
    }

    /**
     * @param valueClass the valueClass to set
     */
    public void setValueClass(Class<?> valueClass) {
        this.valueClass = valueClass;
    }

    /**
     * @return the approximateSize
     */
    public int getApproximateSize() {
        return approximateSize;
    }

    /**
     * @param approximateSize the approximateSize to set
     */
    public void setApproximateSize(int approximateSize) {
        this.approximateSize = approximateSize;
    }
 }