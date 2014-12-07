/*
GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Feb 4, 2006
 */
package org.lobobrowser.primary.clientlets.html;

import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Map;
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

public class HtmlRendererContextImpl implements HtmlRendererContext {
	private static final Logger logger = Logger
			.getLogger(HtmlRendererContextImpl.class.getName());
	private static final Map<NavigatorFrame, WeakReference<HtmlRendererContextImpl>> weakAssociation = new WeakHashMap<NavigatorFrame, WeakReference<HtmlRendererContextImpl>>();

	private final NavigatorFrame clientletFrame;
	private final HtmlPanel htmlPanel;

	private HtmlRendererContextImpl(NavigatorFrame clientletFrame) {
		this.clientletFrame = clientletFrame;
		this.htmlPanel = new HtmlPanel();
	}

	// public static void clearFrameAssociations() {
	// synchronized(weakAssociation) {
	// weakAssociation.clear();
	// }
	// }
	//
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

	public Document getContentDocument() {
		Object rootNode = this.htmlPanel.getRootNode();
		if (rootNode instanceof Document) {
			return (Document) rootNode;
		}
		return null;
	}

	public HtmlPanel getHtmlPanel() {
		return this.htmlPanel;
	}

	public void warn(String message, Throwable throwable) {
		logger.log(Level.WARNING, message, throwable);
	}

	public void error(String message, Throwable throwable) {
		logger.log(Level.SEVERE, message, throwable);
	}

	public void warn(String message) {
		logger.warning(message);
	}

	public void error(String message) {
		logger.log(Level.SEVERE, message);
	}

	public void linkClicked(HTMLElement linkNode, URL url, String target) {
		this.navigateImpl(url, target, RequestType.CLICK, linkNode);
	}

	public void navigate(URL href, String target) {
		this.navigateImpl(href, target, RequestType.PROGRAMMATIC, null);
	}

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

	public void submitForm(String method, URL url, String target,
			String enctype, FormInput[] formInputs) {
		TargetType targetType = this.getTargetType(target);
		ParameterInfo pinfo = new LocalParameterInfo(enctype, formInputs);
		this.clientletFrame.navigate(url, method, pinfo, targetType,
				RequestType.FORM);
	}

	public BrowserFrame createBrowserFrame() {
		NavigatorFrame newFrame = this.clientletFrame.createFrame();
		return new BrowserFrameImpl(newFrame, this);
	}

	public void alert(String message) {
		this.clientletFrame.alert(message);
	}

	public void blur() {
		this.clientletFrame.windowToBack();
	}

	public void close() {
		this.clientletFrame.closeWindow();
	}

	public boolean confirm(String message) {
		return this.clientletFrame.confirm(message);
	}

	public void focus() {
		this.clientletFrame.windowToFront();
	}

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

	public HtmlRendererContext open(java.net.URL urlObj, String windowName,
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

	public String prompt(String message, String inputDefault) {
		return this.clientletFrame.prompt(message, inputDefault);
	}

	public void scroll(int x, int y) {
		this.htmlPanel.scroll(x, y);
	}

	public void scrollBy(int xOffset, int yOffset) {
		this.htmlPanel.scrollBy(xOffset, yOffset);
	}

	public boolean isClosed() {
		return this.clientletFrame.isWindowClosed();
	}

	public String getDefaultStatus() {
		return this.clientletFrame.getDefaultStatus();
	}

	public void setDefaultStatus(String value) {
		this.clientletFrame.setDefaultStatus(value);
	}

	public HTMLCollection getFrames() {
		Object rootNode = this.htmlPanel.getRootNode();
		if (rootNode instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) rootNode).getFrames();
		} else {
			return null;
		}
	}

	public int getLength() {
		HTMLCollection frames = this.getFrames();
		return frames == null ? 0 : frames.getLength();
	}

	public String getName() {
		return this.clientletFrame.getWindowId();
	}

	// private static final String HTML_RENDERER_ITEM = "lobo.html.renderer";

	public HtmlRendererContext getParent() {
		NavigatorFrame parentFrame = this.clientletFrame.getParentFrame();
		return parentFrame == null ? null : HtmlRendererContextImpl
				.getHtmlRendererContext(parentFrame);
	}

	public HtmlRendererContext getOpener() {
		HtmlRendererContext opener = this.assignedOpener;
		if (opener != null) {
			return opener;
		}
		NavigatorFrame openerFrame = this.clientletFrame.getOpenerFrame();
		return openerFrame == null ? null : HtmlRendererContextImpl
				.getHtmlRendererContext(openerFrame);
	}

	private volatile HtmlRendererContext assignedOpener;

	public void setOpener(HtmlRendererContext opener) {
		this.assignedOpener = opener;
	}

	public String getStatus() {
		return this.clientletFrame.getStatus();
	}

	public void setStatus(String message) {
		this.clientletFrame.setStatus(message);
	}

	public void reload() {
		this.clientletFrame.reload();
	}

	public HtmlRendererContext getTop() {
		NavigatorFrame parentFrame = this.clientletFrame.getTopFrame();
		return parentFrame == null ? null : HtmlRendererContextImpl
				.getHtmlRendererContext(parentFrame);
	}

	public HtmlObject getHtmlObject(HTMLElement element) {
		// TODO
		return null;
	}

	private UserAgentContext uaContext;

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

	public boolean isVisitedLink(HTMLLinkElement link) {
		// TODO
		return false;
	}

	public boolean onContextMenu(HTMLElement element, MouseEvent event) {
		return true;
	}

	public void onMouseOut(HTMLElement element, MouseEvent event) {
		if (element instanceof HTMLLinkElementImpl) {
			this.clientletFrame.setStatus(null);
		}
	}

	public boolean isImageLoadingEnabled() {
		return true;
	}

	public void onMouseOver(HTMLElement element, MouseEvent event) {
		if (element instanceof HTMLLinkElementImpl) {
			HTMLLinkElementImpl linkElement = (HTMLLinkElementImpl) element;
			this.clientletFrame.setStatus(linkElement.getAbsoluteHref());
		}
	}

	public boolean onDoubleClick(HTMLElement element, MouseEvent event) {
		return true;
	}

	public boolean onMouseClick(HTMLElement element, MouseEvent event) {
		return true;
	}

	public void resizeBy(int byWidth, int byHeight) {
		this.clientletFrame.resizeWindowBy(byWidth, byHeight);
	}

	public void resizeTo(int width, int height) {
		this.clientletFrame.resizeWindowTo(width, height);
	}

	public void forward() {
		this.clientletFrame.forward();
	}

	public void back() {
		this.clientletFrame.back();
	}

	public String getCurrentURL() {
		NavigationEntry entry = this.clientletFrame.getCurrentNavigationEntry();
		return entry == null ? null : entry.getUrl().toExternalForm();
	}

	public int getHistoryLength() {
		return this.clientletFrame.getHistoryLength();
	}

	public String getNextURL() {
		NavigationEntry entry = this.clientletFrame.getNextNavigationEntry();
		return entry == null ? null : entry.getUrl().toExternalForm();
	}

	public String getPreviousURL() {
		NavigationEntry entry = this.clientletFrame
				.getPreviousNavigationEntry();
		return entry == null ? null : entry.getUrl().toExternalForm();
	}

	public void goToHistoryURL(String url) {
		this.clientletFrame.navigateInHistory(url);
	}

	public void moveInHistory(int offset) {
		this.clientletFrame.moveInHistory(offset);
	}

	private static class LocalParameterInfo implements ParameterInfo {
		private final String encodingType;
		private final FormInput[] formInputs;

		/**
		 * @param type
		 * @param inputs
		 */
		public LocalParameterInfo(String type, FormInput[] inputs) {
			super();
			encodingType = type;
			formInputs = inputs;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.clientlet.ParameterInfo#getEncoding()
		 */
		public String getEncoding() {
			return this.encodingType;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.clientlet.ParameterInfo#getParameters()
		 */
		public Parameter[] getParameters() {
			final FormInput[] formInputs = this.formInputs;
			Parameter[] params = new Parameter[formInputs.length];
			for (int i = 0; i < params.length; i++) {
				final int index = i;
				params[i] = new Parameter() {
					public String getName() {
						return formInputs[index].getName();
					}

					public File[] getFileValue() {
						return formInputs[index].getFileValue();
					}

					public String getTextValue() {
						return formInputs[index].getTextValue();
					}

					public boolean isFile() {
						return formInputs[index].isFile();
					}

					public boolean isText() {
						return formInputs[index].isText();
					}
				};
			}
			return params;
		}
	}

}
