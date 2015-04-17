/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
/*
 * Created on Feb 4, 2006
 */
package org.lobobrowser.primary.clientlets.html;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.html.BrowserFrame;
import org.lobobrowser.html.FormInput;
import org.lobobrowser.html.HtmlObject;
import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.dombl.FrameNode;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLLinkElementImpl;
import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.html.w3c.HTMLCollection;
import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.HTMLLinkElement;
import org.lobobrowser.ua.NavigationEntry;
import org.lobobrowser.ua.NavigatorFrame;
import org.lobobrowser.ua.Parameter;
import org.lobobrowser.ua.ParameterInfo;
import org.lobobrowser.ua.RequestType;
import org.lobobrowser.ua.TargetType;
import org.w3c.dom.Document;

/**
 * The Class HtmlRendererContextImpl.
 */
public class HtmlRendererContextImpl implements HtmlRendererContext {

    /** The Constant logger. */
    private static final Logger logger = Logger
            .getLogger(HtmlRendererContextImpl.class.getName());

    /** The Constant weakAssociation. */
    private static final Map<NavigatorFrame, WeakReference<HtmlRendererContextImpl>> weakAssociation = new WeakHashMap<NavigatorFrame, WeakReference<HtmlRendererContextImpl>>();

    /** The clientlet frame. */
    private final NavigatorFrame clientletFrame;

    /** The html panel. */
    private final HtmlPanel htmlPanel;

    /**
     * Instantiates a new html renderer context impl.
     *
     * @param clientletFrame
     *            the clientlet frame
     */
    private HtmlRendererContextImpl(NavigatorFrame clientletFrame) {
        this.clientletFrame = clientletFrame;
        this.htmlPanel = new HtmlPanel();
    }

    // public static void clearFrameAssociations() {
    // synchronized(weakAssociation) {
    // weakAssociation.clear();
    //}
    //}
    //
    /**
     * Gets the html renderer context.
     *
     * @param frame
     *            the frame
     * @return the html renderer context
     */
    public static HtmlRendererContextImpl getHtmlRendererContext(
            NavigatorFrame frame) {
        synchronized (weakAssociation) {
            WeakReference<HtmlRendererContextImpl> existingWR = weakAssociation
                    .get(frame);
            HtmlRendererContextImpl hrc;
            if (existingWR != null) {
                hrc = existingWR.get();
                if (hrc != null) {
                    return hrc;
                }
            }
            hrc = new HtmlRendererContextImpl(frame);
            weakAssociation.put(frame,
                    new WeakReference<HtmlRendererContextImpl>(hrc));
            return hrc;
        }
    }

    /**
     * Gets the content document.
     *
     * @return the content document
     */
    public Document getContentDocument() {
        Object rootNode = this.htmlPanel.getRootNode();
        if (rootNode instanceof Document) {
            return (Document) rootNode;
        }
        return null;
    }

    /**
     * Gets the html panel.
     *
     * @return the html panel
     */
    public HtmlPanel getHtmlPanel() {
        return this.htmlPanel;
    }

    /**
     * Warn.
     *
     * @param message
     *            the message
     * @param throwable
     *            the throwable
     */
    public void warn(String message, Throwable throwable) {
        logger.log(Level.WARNING, message, throwable);
    }

    /**
     * Error.
     *
     * @param message
     *            the message
     * @param throwable
     *            the throwable
     */
    public void error(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    /**
     * Warn.
     *
     * @param message
     *            the message
     */
    public void warn(String message) {
        logger.warning(message);
    }

    /**
     * Error.
     *
     * @param message
     *            the message
     */
    public void error(String message) {
        logger.log(Level.SEVERE, message);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.HtmlRendererContext#linkClicked(org.lobobrowser.html
     * .w3c.HTMLElement, java.net.URL, java.lang.String)
     */
    @Override
    public void linkClicked(HTMLElement linkNode, URL url, String target) {
        this.navigateImpl(url, target, RequestType.CLICK, linkNode);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#navigate(java.net.URL,
     * java.lang.String)
     */
    @Override
    public void navigate(URL href, String target) {
        this.navigateImpl(href, target, RequestType.PROGRAMMATIC, null);
    }

    /**
     * Navigate impl.
     *
     * @param href
     *            the href
     * @param target
     *            the target
     * @param requestType
     *            the request type
     * @param linkObject
     *            the link object
     */
    private void navigateImpl(URL href, String target, RequestType requestType,
            Object linkObject) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info("navigateImpl(): href=" + href + ",target=" + target);
        }
        // First check if target is a frame identifier.
        TargetType targetType;
        if (target != null) {
            HtmlRendererContext topCtx = this.getTop();
            HTMLCollection frames = topCtx.getFrames();
            if (frames != null) {
                org.w3c.dom.Node frame = frames.namedItem(target);
                if (frame instanceof FrameNode) {
                    BrowserFrame bframe = ((FrameNode) frame).getBrowserFrame();
                    if (bframe == null) {
                        throw new IllegalStateException(
                                "Frame node without a BrowserFrame instance: "
                                        + frame);
                    }
                    if (bframe.getHtmlRendererContext() != this) {
                        bframe.loadURL(href);
                        return;
                    }
                }
            }
            // Now try special target types.
            targetType = this.getTargetType(target);
        } else {
            targetType = TargetType.SELF;
        }
        if (requestType == RequestType.CLICK) {
            this.clientletFrame.linkClicked(href, targetType, linkObject);
        } else {
            this.clientletFrame.navigate(href, "GET", null, targetType,
                    requestType);
        }
    }

    /**
     * Gets the target type.
     *
     * @param target
     *            the target
     * @return the target type
     */
    private TargetType getTargetType(String target) {
        if ("_blank".equalsIgnoreCase(target)) {
            return TargetType.BLANK;
        } else if ("_parent".equalsIgnoreCase(target)) {
            return TargetType.PARENT;
        } else if ("_top".equalsIgnoreCase(target)) {
            return TargetType.TOP;
        } else {
            return TargetType.SELF;
        }
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#submitForm(java.lang.String,
     * java.net.URL, java.lang.String, java.lang.String,
     * org.lobobrowser.html.FormInput[])
     */
    @Override
    public void submitForm(String method, URL url, String target,
            String enctype, FormInput[] formInputs) {
        TargetType targetType = this.getTargetType(target);
        ParameterInfo pinfo = new LocalParameterInfo(enctype, formInputs);
        this.clientletFrame.navigate(url, method, pinfo, targetType,
                RequestType.FORM);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#createBrowserFrame()
     */
    @Override
    public BrowserFrame createBrowserFrame() {
        NavigatorFrame newFrame = this.clientletFrame.createFrame();
        return new BrowserFrameImpl(newFrame, this);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#alert(java.lang.String)
     */
    @Override
    public void alert(String message) {
        this.clientletFrame.alert(message);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#blur()
     */
    @Override
    public void blur() {
        this.clientletFrame.windowToBack();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#close()
     */
    @Override
    public void close() {
        this.clientletFrame.closeWindow();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#confirm(java.lang.String)
     */
    @Override
    public boolean confirm(String message) {
        return this.clientletFrame.confirm(message);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#focus()
     */
    @Override
    public void focus() {
        this.clientletFrame.windowToFront();
    }

    /**
     * Open.
     *
     * @param url
     *            the url
     * @param windowName
     *            the window name
     * @param windowFeatures
     *            the window features
     * @param replace
     *            the replace
     * @return the html renderer context
     */
    public HtmlRendererContext open(String url, String windowName,
            String windowFeatures, boolean replace) {
        try {
            URL urlObj = org.lobobrowser.util.Urls.guessURL(url);
            return this.open(urlObj, windowName, windowFeatures, replace);
        } catch (Exception err) {
            logger.log(Level.WARNING, "open(): Unable to open URL [" + url
                    + "].", err);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#open(java.net.URL,
     * java.lang.String, java.lang.String, boolean)
     */
    @Override
    public HtmlRendererContext open(URL urlObj, String windowName,
            String windowFeatures, boolean replace) {
        Properties windowProperties = windowFeatures == null ? null
                : org.lobobrowser.gui.NavigatorWindowImpl
                .getPropertiesFromWindowFeatures(windowFeatures);
        try {
            NavigatorFrame newFrame = this.clientletFrame.open(urlObj, "GET",
                    null, windowName, windowProperties);
            if (newFrame == null) {
                return null;
            }
            return HtmlRendererContextImpl.getHtmlRendererContext(newFrame);
        } catch (Exception err) {
            logger.log(Level.WARNING, "open(): Unable to open URL [" + urlObj
                    + "].", err);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#prompt(java.lang.String,
     * java.lang.String)
     */
    @Override
    public String prompt(String message, String inputDefault) {
        return this.clientletFrame.prompt(message, inputDefault);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#scroll(int, int)
     */
    @Override
    public void scroll(int x, int y) {
        this.htmlPanel.scroll(x, y);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#scrollBy(int, int)
     */
    @Override
    public void scrollBy(int xOffset, int yOffset) {
        this.htmlPanel.scrollBy(xOffset, yOffset);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#isClosed()
     */
    @Override
    public boolean isClosed() {
        return this.clientletFrame.isWindowClosed();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#getDefaultStatus()
     */
    @Override
    public String getDefaultStatus() {
        return this.clientletFrame.getDefaultStatus();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.HtmlRendererContext#setDefaultStatus(java.lang.String)
     */
    @Override
    public void setDefaultStatus(String value) {
        this.clientletFrame.setDefaultStatus(value);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#getFrames()
     */
    @Override
    public HTMLCollection getFrames() {
        Object rootNode = this.htmlPanel.getRootNode();
        if (rootNode instanceof HTMLDocumentImpl) {
            return ((HTMLDocumentImpl) rootNode).getFrames();
        } else {
            return null;
        }
    }

    /**
     * Gets the length.
     *
     * @return the length
     */
    public int getLength() {
        HTMLCollection frames = this.getFrames();
        return frames == null ? 0 : frames.getLength();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#getName()
     */
    @Override
    public String getName() {
        return this.clientletFrame.getWindowId();
    }

    // private static final String HTML_RENDERER_ITEM = "lobo.html.renderer";

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#getParent()
     */
    @Override
    public HtmlRendererContext getParent() {
        NavigatorFrame parentFrame = this.clientletFrame.getParentFrame();
        return parentFrame == null ? null : HtmlRendererContextImpl
                .getHtmlRendererContext(parentFrame);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#getOpener()
     */
    @Override
    public HtmlRendererContext getOpener() {
        HtmlRendererContext opener = this.assignedOpener;
        if (opener != null) {
            return opener;
        }
        NavigatorFrame openerFrame = this.clientletFrame.getOpenerFrame();
        return openerFrame == null ? null : HtmlRendererContextImpl
                .getHtmlRendererContext(openerFrame);
    }

    /** The assigned opener. */
    private volatile HtmlRendererContext assignedOpener;

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#setOpener(org.lobobrowser.html.
     * HtmlRendererContext)
     */
    @Override
    public void setOpener(HtmlRendererContext opener) {
        this.assignedOpener = opener;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#getStatus()
     */
    @Override
    public String getStatus() {
        return this.clientletFrame.getStatus();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#setStatus(java.lang.String)
     */
    @Override
    public void setStatus(String message) {
        this.clientletFrame.setStatus(message);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#reload()
     */
    @Override
    public void reload() {
        this.clientletFrame.reload();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#getTop()
     */
    @Override
    public HtmlRendererContext getTop() {
        NavigatorFrame parentFrame = this.clientletFrame.getTopFrame();
        return parentFrame == null ? null : HtmlRendererContextImpl
                .getHtmlRendererContext(parentFrame);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.HtmlRendererContext#getHtmlObject(org.lobobrowser.html
     * .w3c.HTMLElement)
     */
    @Override
    public HtmlObject getHtmlObject(HTMLElement element) {
        // TODO
        return null;
    }

    /** The ua context. */
    private UserAgentContext uaContext;

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#getUserAgentContext()
     */
    @Override
    public UserAgentContext getUserAgentContext() {
        if (this.uaContext == null) {
            synchronized (this) {
                if (this.uaContext == null) {
                    this.uaContext = new UserAgentContextImpl(
                            this.clientletFrame);
                }
            }
        }
        return this.uaContext;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.HtmlRendererContext#isVisitedLink(org.lobobrowser.html
     * .w3c.HTMLLinkElement)
     */
    @Override
    public boolean isVisitedLink(HTMLLinkElement link) {
        // TODO
        return false;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.HtmlRendererContext#onContextMenu(org.lobobrowser.html
     * .w3c.HTMLElement, java.awt.event.MouseEvent)
     */
    @Override
    public boolean onContextMenu(HTMLElement element, MouseEvent event) {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.HtmlRendererContext#onMouseOut(org.lobobrowser.html.
     * w3c.HTMLElement, java.awt.event.MouseEvent)
     */
    @Override
    public void onMouseOut(HTMLElement element, MouseEvent event) {
        if (element instanceof HTMLLinkElementImpl) {
            this.clientletFrame.setStatus(null);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#isImageLoadingEnabled()
     */
    @Override
    public boolean isImageLoadingEnabled() {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.HtmlRendererContext#onMouseOver(org.lobobrowser.html
     * .w3c.HTMLElement, java.awt.event.MouseEvent)
     */
    @Override
    public void onMouseOver(HTMLElement element, MouseEvent event) {
        if (element instanceof HTMLLinkElementImpl) {
            HTMLLinkElementImpl linkElement = (HTMLLinkElementImpl) element;
            this.clientletFrame.setStatus(linkElement.getAbsoluteHref());
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.HtmlRendererContext#onDoubleClick(org.lobobrowser.html
     * .w3c.HTMLElement, java.awt.event.MouseEvent)
     */
    @Override
    public boolean onDoubleClick(HTMLElement element, MouseEvent event) {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.HtmlRendererContext#onMouseClick(org.lobobrowser.html
     * .w3c.HTMLElement, java.awt.event.MouseEvent)
     */
    @Override
    public boolean onMouseClick(HTMLElement element, MouseEvent event) {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#resizeBy(int, int)
     */
    @Override
    public void resizeBy(int byWidth, int byHeight) {
        this.clientletFrame.resizeWindowBy(byWidth, byHeight);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#resizeTo(int, int)
     */
    @Override
    public void resizeTo(int width, int height) {
        this.clientletFrame.resizeWindowTo(width, height);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#forward()
     */
    @Override
    public void forward() {
        this.clientletFrame.forward();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#back()
     */
    @Override
    public void back() {
        this.clientletFrame.back();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#getCurrentURL()
     */
    @Override
    public String getCurrentURL() {
        NavigationEntry entry = this.clientletFrame.getCurrentNavigationEntry();
        return entry == null ? null : entry.getUrl().toExternalForm();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#getHistoryLength()
     */
    @Override
    public int getHistoryLength() {
        return this.clientletFrame.getHistoryLength();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#getNextURL()
     */
    @Override
    public String getNextURL() {
        NavigationEntry entry = this.clientletFrame.getNextNavigationEntry();
        return entry == null ? null : entry.getUrl().toExternalForm();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#getPreviousURL()
     */
    @Override
    public String getPreviousURL() {
        NavigationEntry entry = this.clientletFrame
                .getPreviousNavigationEntry();
        return entry == null ? null : entry.getUrl().toExternalForm();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.HtmlRendererContext#goToHistoryURL(java.lang.String)
     */
    @Override
    public void goToHistoryURL(String url) {
        this.clientletFrame.navigateInHistory(url);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.HtmlRendererContext#moveInHistory(int)
     */
    @Override
    public void moveInHistory(int offset) {
        this.clientletFrame.moveInHistory(offset);
    }
    
    @Override
    public void setCursor(Optional<Cursor> cursorOpt) {
        Cursor cursor = cursorOpt.orElse(Cursor.getDefaultCursor());
        htmlPanel.setCursor(cursor);
    }

    /**
     * The Class LocalParameterInfo.
     */
    private static class LocalParameterInfo implements ParameterInfo {

        /** The encoding type. */
        private final String encodingType;

        /** The form inputs. */
        private final FormInput[] formInputs;

        /**
         * Instantiates a new local parameter info.
         *
         * @param type
         *            the type
         * @param inputs
         *            the inputs
         */
        public LocalParameterInfo(String type, FormInput[] inputs) {
            super();
            encodingType = type;
            formInputs = inputs;
        }

        /*
         * (non-Javadoc)
         * @see org.xamjwg.clientlet.ParameterInfo#getEncoding()
         */
        @Override
        public String getEncoding() {
            return this.encodingType;
        }

        /*
         * (non-Javadoc)
         * @see org.xamjwg.clientlet.ParameterInfo#getParameters()
         */
        @Override
        public Parameter[] getParameters() {
            final FormInput[] formInputs = this.formInputs;
            Parameter[] params = new Parameter[formInputs.length];
            for (int i = 0; i < params.length; i++) {
                final int index = i;
                params[i] = new Parameter() {
                    @Override
                    public String getName() {
                        return formInputs[index].getName();
                    }

                    @Override
                    public File[] getFileValue() {
                        return formInputs[index].getFileValue();
                    }

                    @Override
                    public String getTextValue() {
                        return formInputs[index].getTextValue();
                    }

                    @Override
                    public boolean isFile() {
                        return formInputs[index].isFile();
                    }

                    @Override
                    public boolean isText() {
                        return formInputs[index].isText();
                    }
                };
            }
            return params;
        }
    }
}
