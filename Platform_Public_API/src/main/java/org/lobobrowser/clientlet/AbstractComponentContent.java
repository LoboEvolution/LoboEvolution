/*
 * Copyright 1994-2006 The Lobo Project. Copyright 2014 Lobo Evolution. All
 * rights reserved. Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following conditions
 * are met: Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer. Redistributions
 * in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE
 * LOBO PROJECT ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE FREEBSD PROJECT OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
