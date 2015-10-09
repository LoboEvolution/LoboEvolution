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
/*
 * Created on Oct 9, 2005
 */
package org.lobobrowser.html.dombl;

import java.util.Collection;
import java.util.Iterator;

import org.lobobrowser.html.domfilter.NodeFilter;
import org.lobobrowser.js.AbstractScriptableDelegate;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class FilteredNodeListImpl.
 */
public class FilteredNodeListImpl extends AbstractScriptableDelegate implements
NodeList {

    /** The source node list. */
    private final Collection sourceNodeList;

    /** The filter. */
    private final NodeFilter filter;

    /** The lock. */
    private final Object lock;

    /**
     * Instantiates a new filtered node list impl.
     *
     * @param filter
     *            the filter
     * @param list
     *            the list
     * @param lock
     *            the lock
     */
    public FilteredNodeListImpl(NodeFilter filter, Collection list, Object lock) {
        super();
        this.filter = filter;
        sourceNodeList = list;
        this.lock = lock;
    }

    /*
     * (non-Javadoc)
     * @see org.w3c.dom.NodeList#item(int)
     */
    @Override
    public Node item(int index) {
        synchronized (this.lock) {
            int count = 0;
            Iterator i = this.sourceNodeList.iterator();
            while (i.hasNext()) {
                Node node = (Node) i.next();
                if (this.filter.accept(node)) {
                    if (count == index) {
                        return node;
                    }
                    count++;
                }
            }
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see org.w3c.dom.NodeList#getLength()
     */
    @Override
    public int getLength() {
        synchronized (this.lock) {
            int count = 0;
            Iterator i = this.sourceNodeList.iterator();
            while (i.hasNext()) {
                Node node = (Node) i.next();
                if (this.filter.accept(node)) {
                    count++;
                }
            }
            return count;
        }
    }
}
