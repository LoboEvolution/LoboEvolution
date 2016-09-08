/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.clientlet;

import java.awt.Component;

/**
 * Abstract implementation of {@link ComponentContent}. It is recommended that
 * <code>ComponentContent</code> implementations extend this class for forward
 * compatibility.
 */
public abstract class AbstractComponentContent implements ComponentContent {

    /**
     * Instantiates a new abstract component content.
     */
    public AbstractComponentContent() {
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ComponentContent#canCopy()
     */
    @Override
    public boolean canCopy() {
        return false;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ComponentContent#copy()
     */
    @Override
    public boolean copy() {
        return false;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ComponentContent#getComponent()
     */
    @Override
    public abstract Component getComponent();

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ComponentContent#getSourceCode()
     */
    @Override
    public abstract String getSourceCode();

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ComponentContent#getTitle()
     */
    @Override
    public abstract String getTitle();

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ComponentContent#getDescription()
     */
    @Override
    public String getDescription() {
        return "";
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ComponentContent#addNotify()
     */
    @Override
    public void addNotify() {
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ComponentContent#removeNotify()
     */
    @Override
    public void removeNotify() {
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ComponentContent#getContentObject()
     */
    @Override
    public Object getContentObject() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ComponentContent#getMimeType()
     */
    @Override
    public String getMimeType() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.ComponentContent#setProperty(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public void setProperty(String name, Object value) {
        // NOP
    }

    // Backward compatibility note: Additional methods should provide an empty
    // body.
}
