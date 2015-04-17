/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Nov 19, 2005
 */
package org.lobobrowser.html.domimpl;

import java.awt.Image;
import java.util.ArrayList;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.dombl.ImageEvent;
import org.lobobrowser.html.dombl.ImageListener;
import org.lobobrowser.html.dombl.UINode;
import org.lobobrowser.html.js.Executor;
import org.lobobrowser.html.renderstate.ImageRenderState;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.w3c.HTMLImageElement;
import org.mozilla.javascript.Function;

/**
 * The Class HTMLImageElementImpl.
 */
public class HTMLImageElementImpl extends HTMLAbstractUIElement implements
HTMLImageElement {

    /**
     * Instantiates a new HTML image element impl.
     */
    public HTMLImageElementImpl() {
        super(HtmlProperties.IMG);
    }

    /**
     * Instantiates a new HTML image element impl.
     *
     * @param name
     *            the name
     */
    public HTMLImageElementImpl(String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#getName()
     */
    @Override
    public String getName() {
        return this.getAttribute(HtmlAttributeProperties.NAME);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
        this.setAttribute(HtmlAttributeProperties.NAME, name);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#getAlign()
     */
    @Override
    public String getAlign() {
        return this.getAttribute(HtmlAttributeProperties.ALIGN);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#setAlign(java.lang.String)
     */
    @Override
    public void setAlign(String align) {
        this.setAttribute(HtmlAttributeProperties.ALIGN, align);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#getAlt()
     */
    @Override
    public String getAlt() {
        return this.getAttribute(HtmlAttributeProperties.ALT);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#setAlt(java.lang.String)
     */
    @Override
    public void setAlt(String alt) {
        this.setAttribute(HtmlAttributeProperties.ALT, alt);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#getBorder()
     */
    @Override
    public String getBorder() {
        return this.getAttribute(HtmlAttributeProperties.BORDER);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#setBorder(java.lang.String)
     */
    @Override
    public void setBorder(String border) {
        this.setAttribute(HtmlAttributeProperties.BORDER, border);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#getHeight()
     */
    @Override
    public int getHeight() {
        String height = this.getAttribute(HtmlAttributeProperties.HEIGHT);
        UINode r = this.uiNode;

        if ((height != null) && (height.length() > 0)) {
            return new Integer(height).intValue();
        }

        return r == null ? 0 : r.getBounds().height;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#setHeight(int)
     */
    @Override
    public void setHeight(int height) {
        this.setAttribute(HtmlAttributeProperties.HEIGHT,
                String.valueOf(height));
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#getHspace()
     */
    @Override
    public int getHspace() {
        return this.getAttributeAsInt(HtmlAttributeProperties.HSPACE, 0);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#setHspace(int)
     */
    @Override
    public void setHspace(int hspace) {
        this.setAttribute(HtmlAttributeProperties.HSPACE,
                String.valueOf(hspace));
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#getIsMap()
     */
    @Override
    public boolean getIsMap() {
        return this.getAttributeAsBoolean(HtmlAttributeProperties.ISMAP);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#setIsMap(boolean)
     */
    @Override
    public void setIsMap(boolean isMap) {
        this.setAttribute(HtmlAttributeProperties.ISMAP,
                isMap ? HtmlAttributeProperties.ISMAP : null);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#getLongDesc()
     */
    @Override
    public String getLongDesc() {
        return this.getAttribute(HtmlAttributeProperties.LONGDESC);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#setLongDesc(java.lang.String)
     */
    @Override
    public void setLongDesc(String longDesc) {
        this.setAttribute(HtmlAttributeProperties.LONGDESC, longDesc);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#getSrc()
     */
    @Override
    public String getSrc() {
        return this.getAttribute(HtmlAttributeProperties.SRC);
    }

    /**
     * Sets the image URI and starts to load the image. Note that an
     * HtmlRendererContext should be available to the HTML document for images
     * to be loaded.
     *
     * @param src
     *            the new src
     */
    @Override
    public void setSrc(String src) {
        this.setAttribute(HtmlAttributeProperties.SRC, src);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#getUseMap()
     */
    @Override
    public String getUseMap() {
        return this.getAttribute(HtmlAttributeProperties.USEMAP);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#setUseMap(java.lang.String)
     */
    @Override
    public void setUseMap(String useMap) {
        this.setAttribute(HtmlAttributeProperties.USEMAP, useMap);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#getVspace()
     */
    @Override
    public int getVspace() {
        return this.getAttributeAsInt(HtmlAttributeProperties.VSPACE, 0);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#setVspace(int)
     */
    @Override
    public void setVspace(int vspace) {
        this.setAttribute(HtmlAttributeProperties.VSPACE,
                String.valueOf(vspace));
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#getWidth()
     */
    @Override
    public int getWidth() {

        String width = this.getAttribute(HtmlAttributeProperties.WIDTH);
        UINode r = this.uiNode;

        if ((width != null) && (width.length() > 0)) {
            return new Integer(width).intValue();
        }

        return r == null ? 0 : r.getBounds().width;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#setWidth(int)
     */
    @Override
    public void setWidth(int width) {
        this.setAttribute(HtmlAttributeProperties.WIDTH, String.valueOf(width));
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.domimpl.HTMLAbstractUIElement#assignAttributeField(java
     * .lang.String, java.lang.String)
     */
    @Override
    protected void assignAttributeField(String normalName, String value) {
        super.assignAttributeField(normalName, value);
        if (HtmlAttributeProperties.SRC.equals(normalName)) {
            this.loadImage(value);
        }
    }

    /** The onload. */
    private Function onload;

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.domimpl.HTMLAbstractUIElement#getOnload()
     */
    @Override
    public Function getOnload() {
        return this.getEventFunction(this.onload, "onload");
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.domimpl.HTMLAbstractUIElement#setOnload(org.mozilla.
     * javascript.Function)
     */
    @Override
    public void setOnload(Function onload) {
        this.onload = onload;
    }

    /** The image. */
    private Image image = null;

    /** The image src. */
    private String imageSrc;

    /**
     * Load image.
     *
     * @param src
     *            the src
     */
    private void loadImage(String src) {
        HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
        if (document != null) {
            synchronized (this.listeners) {
                this.imageSrc = src;
                this.image = null;
            }
            if (src != null) {
                document.loadImage(src, new LocalImageListener(src));
            }
        }
    }

    /**
     * Gets the image.
     *
     * @return the image
     */
    public final Image getImage() {
        synchronized (this.listeners) {
            return this.image;
        }
    }

    /** The listeners. */
    private final ArrayList<ImageListener> listeners = new ArrayList<ImageListener>(
            1);

    /**
     * Adds a listener of image loading events. The listener gets called right
     * away if there's already an image.
     *
     * @param listener
     *            the listener
     */
    public void addImageListener(ImageListener listener) {
        ArrayList<ImageListener> l = this.listeners;
        Image currentImage;
        synchronized (l) {
            currentImage = this.image;
            l.add(listener);
        }
        if (currentImage != null) {
            // Call listener right away if there's already an
            // image; holding no locks.
            listener.imageLoaded(new ImageEvent(this, currentImage));
            // Should not call onload handler here. That's taken
            // care of otherwise.
        }
    }

    /**
     * Removes the image listener.
     *
     * @param listener
     *            the listener
     */
    public void removeImageListener(ImageListener listener) {
        ArrayList<ImageListener> l = this.listeners;
        synchronized (l) {
            l.remove(l);
        }
    }

    /**
     * Dispatch event.
     *
     * @param expectedImgSrc
     *            the expected img src
     * @param event
     *            the event
     */
    private void dispatchEvent(String expectedImgSrc, ImageEvent event) {
        ArrayList<ImageListener> l = this.listeners;
        ImageListener[] listenerArray;
        synchronized (l) {
            if (!expectedImgSrc.equals(this.imageSrc)) {
                return;
            }
            this.image = event.image;
            // Get array of listeners while holding lock.
            listenerArray = l.toArray(ImageListener.EMPTY_ARRAY);
        }
        int llength = listenerArray.length;
        for (int i = 0; i < llength; i++) {
            // Inform listener, holding no lock.
            listenerArray[i].imageLoaded(event);
        }
        Function onload = this.getOnload();
        if (onload != null) {
            // TODO: onload event object?
            Executor.executeFunction(HTMLImageElementImpl.this, onload, null);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.lobobrowser
     * .html.renderstate.RenderState)
     */
    @Override
    protected RenderState createRenderState(RenderState prevRenderState) {
        return new ImageRenderState(prevRenderState, this);
    }

    /**
     * The listener interface for receiving localImage events. The class that is
     * interested in processing a localImage event implements this interface,
     * and the object created with that class is registered with a component
     * using the component's <code>addLocalImageListener</code> method. When the
     * localImage event occurs, that object's appropriate method is invoked.
     *
     * @see LocalImageEvent
     */
    private class LocalImageListener implements ImageListener {

        /** The expected img src. */
        private final String expectedImgSrc;

        /**
         * Instantiates a new local image listener.
         *
         * @param imgSrc
         *            the img src
         */
        public LocalImageListener(String imgSrc) {
            this.expectedImgSrc = imgSrc;
        }

        /*
         * (non-Javadoc)
         * @see
         * org.lobobrowser.html.dombl.ImageListener#imageLoaded(org.lobobrowser.html
         * .dombl.ImageEvent)
         */
        @Override
        public void imageLoaded(ImageEvent event) {
            dispatchEvent(this.expectedImgSrc, event);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#getNaturalWidth()
     */
    @Override
    public int getNaturalWidth() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#getNaturalHeight()
     */
    @Override
    public int getNaturalHeight() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLImageElement#getComplete()
     */
    @Override
    public boolean getComplete() {
        // TODO Auto-generated method stub
        return false;
    }
}
