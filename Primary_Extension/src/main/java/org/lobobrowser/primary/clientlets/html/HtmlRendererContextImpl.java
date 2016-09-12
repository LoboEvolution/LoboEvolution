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

package org.lobobrowser.primary.clientlets.html;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.WeakHashMap;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JPopupMenu;

import org.lobobrowser.gui.NavigatorWindowImpl;
import org.lobobrowser.html.BrowserFrame;
import org.lobobrowser.html.FormInput;
import org.lobobrowser.html.HtmlObject;
import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.dombl.FrameNode;
import org.lobobrowser.html.domimpl.HTMLAbstractUIElement;
import org.lobobrowser.html.domimpl.HTMLAnchorElementImpl;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLImageElementImpl;
import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.primary.info.LocalParameterInfo;
import org.lobobrowser.ua.NavigationEntry;
import org.lobobrowser.ua.NavigatorFrame;
import org.lobobrowser.ua.ParameterInfo;
import org.lobobrowser.ua.RequestType;
import org.lobobrowser.ua.TargetType;
import org.lobobrowser.w3c.html.HTMLAnchorElement;
import org.lobobrowser.w3c.html.HTMLCollection;
import org.lobobrowser.w3c.html.HTMLElement;
import org.w3c.dom.Document;

/**
 * The Class HtmlRendererContextImpl.
 */
public class HtmlRendererContextImpl implements HtmlRendererContext {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(HtmlRendererContextImpl.class);

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

	/**
	 * Gets the html renderer context.
	 *
	 * @param frame
	 *            the frame
	 * @return the html renderer context
	 */
	public static HtmlRendererContextImpl getHtmlRendererContext(NavigatorFrame frame) {
		synchronized (weakAssociation) {
			WeakReference<HtmlRendererContextImpl> existingWR = weakAssociation.get(frame);
			HtmlRendererContextImpl hrc;
			if (existingWR != null) {
				hrc = existingWR.get();
				if (hrc != null) {
					return hrc;
				}
			}
			hrc = new HtmlRendererContextImpl(frame);
			weakAssociation.put(frame, new WeakReference<HtmlRendererContextImpl>(hrc));
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
		logger.error(message, throwable);
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
		logger.log(Level.ERROR, message, throwable);
	}

	/**
	 * Warn.
	 *
	 * @param message
	 *            the message
	 */
	public void warn(String message) {
		logger.warn(message);
	}

	/**
	 * Error.
	 *
	 * @param message
	 *            the message
	 */
	public void error(String message) {
		logger.log(Level.ERROR, message);
	}

	@Override
	public void linkClicked(HTMLElement linkNode, URL url, String target) {
		this.navigateImpl(url, target, RequestType.CLICK, linkNode);
	}

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
	private void navigateImpl(URL href, String target, RequestType requestType, Object linkObject) {
		if (logger.isInfoEnabled()) {
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
						throw new IllegalStateException("Frame node without a BrowserFrame instance: " + frame);
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
			this.clientletFrame.navigate(href, "GET", null, targetType, requestType);
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
	public HtmlRendererContext open(String url, String windowName, String windowFeatures, boolean replace) {
		try {
			URL urlObj = org.lobobrowser.util.Urls.guessURL(url);
			return this.open(urlObj, windowName, windowFeatures, replace);
		} catch (Exception err) {
			logger.error("open(): Unable to open URL [" + url + "].", err);
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

	/**
	 * Gets the clientlet frame.
	 *
	 * @return the clientlet frame
	 */
	public NavigatorFrame getClientletFrame() {
		return clientletFrame;
	}

	@Override
	public void submitForm(String method, URL url, String target, String enctype, FormInput[] formInputs) {
		TargetType targetType = this.getTargetType(target);
		ParameterInfo pinfo = new LocalParameterInfo(enctype, formInputs);
		this.clientletFrame.navigate(url, method, pinfo, targetType, RequestType.FORM);
	}

	@Override
	public BrowserFrame createBrowserFrame() {
		NavigatorFrame newFrame = this.clientletFrame.createFrame();
		return new BrowserFrameImpl(newFrame, this);
	}

	@Override
	public void alert(String message) {
		this.clientletFrame.alert(message);
	}

	@Override
	public void blur() {
		this.clientletFrame.windowToBack();
	}

	@Override
	public void close() {
		this.clientletFrame.closeWindow();
	}

	@Override
	public boolean confirm(String message) {
		return this.clientletFrame.confirm(message);
	}

	@Override
	public void focus() {
		this.clientletFrame.windowToFront();
	}

	@Override
	public HtmlRendererContext open(URL urlObj, String windowName, String windowFeatures, boolean replace) {
		Properties windowProperties = windowFeatures == null ? null
				: NavigatorWindowImpl.getPropertiesFromWindowFeatures(windowFeatures);
		try {
			NavigatorFrame newFrame = this.clientletFrame.open(urlObj, "GET", null, windowName, windowProperties);
			if (newFrame == null) {
				return null;
			}
			return HtmlRendererContextImpl.getHtmlRendererContext(newFrame);
		} catch (Exception err) {
			logger.error("open(): Unable to open URL [" + urlObj + "].", err);
			return null;
		}
	}

	@Override
	public String prompt(String message, String inputDefault) {
		return this.clientletFrame.prompt(message, inputDefault);
	}

	@Override
	public void scroll(int x, int y) {
		this.htmlPanel.scroll(x, y);
	}

	@Override
	public void scrollBy(int xOffset, int yOffset) {
		this.htmlPanel.scrollBy(xOffset, yOffset);
	}

	@Override
	public boolean isClosed() {
		return this.clientletFrame.isWindowClosed();
	}

	@Override
	public String getDefaultStatus() {
		return this.clientletFrame.getDefaultStatus();
	}

	@Override
	public void setDefaultStatus(String value) {
		this.clientletFrame.setDefaultStatus(value);
	}

	@Override
	public HTMLCollection getFrames() {
		Object rootNode = this.htmlPanel.getRootNode();
		if (rootNode instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) rootNode).getFrames();
		} else {
			return null;
		}
	}

	@Override
	public String getName() {
		return this.clientletFrame.getWindowId();
	}

	@Override
	public HtmlRendererContext getParent() {
		NavigatorFrame parentFrame = this.clientletFrame.getParentFrame();
		return parentFrame == null ? null : HtmlRendererContextImpl.getHtmlRendererContext(parentFrame);
	}

	@Override
	public HtmlRendererContext getOpener() {
		HtmlRendererContext opener = this.assignedOpener;
		if (opener != null) {
			return opener;
		}
		NavigatorFrame openerFrame = this.clientletFrame.getOpenerFrame();
		return openerFrame == null ? null : HtmlRendererContextImpl.getHtmlRendererContext(openerFrame);
	}

	/** The assigned opener. */
	private volatile HtmlRendererContext assignedOpener;

	@Override
	public void setOpener(HtmlRendererContext opener) {
		this.assignedOpener = opener;
	}

	@Override
	public String getStatus() {
		return this.clientletFrame.getStatus();
	}

	@Override
	public void setStatus(String message) {
		this.clientletFrame.setStatus(message);
	}

	@Override
	public void reload() {
		this.clientletFrame.reload();
	}

	@Override
	public HtmlRendererContext getTop() {
		NavigatorFrame parentFrame = this.clientletFrame.getTopFrame();
		return parentFrame == null ? null : HtmlRendererContextImpl.getHtmlRendererContext(parentFrame);
	}

	@Override
	public HtmlObject getHtmlObject(HTMLElement element) {
		// TODO
		return null;
	}

	/** The ua context. */
	private UserAgentContext uaContext;

	@Override
	public UserAgentContext getUserAgentContext() {
		if (this.uaContext == null) {
			synchronized (this) {
				if (this.uaContext == null) {
					this.uaContext = new UserAgentContextImpl(this.getClientletFrame());
				}
			}
		}
		return this.uaContext;
	}

	@Override
	public boolean isVisitedLink(HTMLAnchorElement link) {
		// TODO
		return false;
	}

	@Override
	public boolean onContextMenu(HTMLElement element, MouseEvent event) {

		HtmlContextMenu imageMenu = new HtmlContextMenu(element, this);

		if (element instanceof HTMLImageElementImpl) {

			JPopupMenu popupMenuImage = imageMenu.popupMenuImage();
			popupMenuImage.show(event.getComponent(), event.getX(), event.getY());
			return false;
		} else if (element instanceof HTMLAnchorElementImpl) {

			JPopupMenu popupMenuImage = imageMenu.popupMenuLink();
			popupMenuImage.show(event.getComponent(), event.getX(), event.getY());
			return false;
		} else if (element instanceof HTMLAbstractUIElement) {
			JPopupMenu popupMenuImage = imageMenu.popupMenuAbstractUI();
			popupMenuImage.show(event.getComponent(), event.getX(), event.getY());
			return false;
		}

		return true;
	}

	@Override
	public void onMouseOut(HTMLElement element, MouseEvent event) {
		if (element instanceof HTMLAnchorElementImpl) {
			this.clientletFrame.setStatus(null);
		}
	}

	@Override
	public boolean isImageLoadingEnabled() {
		return true;
	}

	@Override
	public void onMouseOver(HTMLElement element, MouseEvent event) {
		if (element instanceof HTMLAnchorElementImpl) {
			HTMLAnchorElementImpl linkElement = (HTMLAnchorElementImpl) element;
			this.clientletFrame.setStatus(linkElement.getAbsoluteHref());
		}
	}

	@Override
	public boolean onDoubleClick(HTMLElement element, MouseEvent event) {
		return true;
	}

	@Override
	public boolean onMouseClick(HTMLElement element, MouseEvent event) {
		return true;
	}

	@Override
	public void resizeBy(int byWidth, int byHeight) {
		this.clientletFrame.resizeWindowBy(byWidth, byHeight);
	}

	@Override
	public void resizeTo(int width, int height) {
		this.clientletFrame.resizeWindowTo(width, height);
	}

	@Override
	public void forward() {
		this.clientletFrame.forward();
	}

	@Override
	public void back() {
		this.clientletFrame.back();
	}

	@Override
	public String getCurrentURL() {
		NavigationEntry entry = this.clientletFrame.getCurrentNavigationEntry();
		return entry == null ? null : entry.getUrl().toExternalForm();
	}

	@Override
	public int getHistoryLength() {
		return this.clientletFrame.getHistoryLength();
	}

	@Override
	public String getNextURL() {
		NavigationEntry entry = this.clientletFrame.getNextNavigationEntry();
		return entry == null ? null : entry.getUrl().toExternalForm();
	}

	@Override
	public String getPreviousURL() {
		NavigationEntry entry = this.getClientletFrame().getPreviousNavigationEntry();
		return entry == null ? null : entry.getUrl().toExternalForm();
	}

	@Override
	public void goToHistoryURL(String url) {
		this.clientletFrame.navigateInHistory(url);
	}

	@Override
	public void moveInHistory(int offset) {
		this.clientletFrame.moveInHistory(offset);
	}

	@Override
	public void setCursor(Optional<Cursor> cursorOpt) {
		Cursor cursor = cursorOpt.orElse(Cursor.getDefaultCursor());
		htmlPanel.setCursor(cursor);
	}
}
