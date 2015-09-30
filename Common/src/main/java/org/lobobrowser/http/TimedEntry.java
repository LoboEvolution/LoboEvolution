/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.http;

/**
 * t The Class TimedEntry.
 */
public class TimedEntry implements Comparable<Object>, java.io.Serializable {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2257845000000000200L;
    /** The timestamp. */
    private long timestamp = System.currentTimeMillis();
    /** The value. */
    final String value;
    
    /**
     * Instantiates a new timed entry.
     *
     * @param value
     *            the value
     */
    public TimedEntry(String value) {
        this.value = value;
    }
    
    /**
     * Touch.
     */
    public void touch() {
        this.timestamp = System.currentTimeMillis();
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        TimedEntry other = (TimedEntry) obj;
        return other.value.equals(this.value);
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(Object arg0) {
        if (this.equals(arg0)) {
            return 0;
        }
        TimedEntry other = (TimedEntry) arg0;
        long time1 = this.timestamp;
        long time2 = other.timestamp;
        if (time1 > time2) {
            // More recent goes first
            return -1;
        } else if (time2 > time1) {
            return +1;
        } else {
            int diff = System.identityHashCode(this)
                    - System.identityHashCode(other);
            if (diff == 0) {
                return +1;
            }
            return diff;
        }
    }
}
