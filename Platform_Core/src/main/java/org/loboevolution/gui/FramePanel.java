/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Window;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.clientlet.ClientletRequest;
import org.loboevolution.clientlet.ClientletResponse;
import org.loboevolution.clientlet.ComponentContent;
import org.loboevolution.clientlet.SimpleComponentContent;
import org.loboevolution.http.HttpRequest;
import org.loboevolution.main.ExtensionManager;
import org.loboevolution.main.PlatformInit;
import org.loboevolution.request.ClientletRequestHandler;
import org.loboevolution.request.ClientletRequestImpl;
import org.loboevolution.request.RequestEngine;
import org.loboevolution.request.RequestHandler;
import org.loboevolution.security.GenericLocalPermission;
import org.loboevolution.ua.NavigationEntry;
import org.loboevolution.ua.NavigationEvent;
import org.loboevolution.ua.NavigationListener;
import org.loboevolution.ua.NavigationVetoException;
import org.loboevolution.ua.NavigatorFrame;
import org.loboevolution.ua.NavigatorProgressEvent;
import org.loboevolution.ua.ParameterInfo;
import org.loboevolution.ua.ProgressType;
import org.loboevolution.ua.RequestType;
import org.loboevolution.ua.TargetType;
import org.loboevolution.util.Items;
import org.loboevolution.util.Urls;
import org.loboevolution.util.WrapperException;
import org.loboevolution.util.gui.WrapperLayout;

/**
 * A browser frame panel. It may be used as any other Swing component. The
 * {@link #navigate(String)} method may be invoked to load content into the
 * browser frame.
 * <p>
 * Content types supported depend on available browser extensions.
 * <p>
 * It's recommended that <code>FramePanel</code>s be placed in windows that
 * extend {@link AbstractBrowserWindow} or implement {@link BrowserWindow}. Such
 * windows may receive navigation notifications via {@link WindowCallback}.
 * <p>
 * A frame panel with navigation controls and a status bar can be obtained with
 * {@link BrowserPanel}.
 *
 * @see PlatformInit#init(boolean, boolean)
 */
public class FramePanel extends JPanel implements NavigatorFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(FramePanel.class.getName());

	/** The window id. */
	private final String windowId;

	/** The navigation engine. */
	private final NavigationEngine navigationEngine = new NavigationEngine();

	/** The known parent frame. */
	private final FramePanel knownParentFrame;

	/** The navigation listeners. */
	private final Collection<NavigationListener> navigationListeners = new ArrayList<NavigationListener>();

	/** The response listeners. */
	private final Collection<ResponseListener> responseListeners = new ArrayList<ResponseListener>();

	/** The content listeners. */
	private final Collection<ContentListener> contentListeners = new ArrayList<ContentListener>();

	/** The properties monitor. */
	private final Object propertiesMonitor = new Object();

	/** The opener frame. */
	private NavigatorFrame openerFrame;

	/** The top frame window. */
	private Window topFrameWindow;
	
	/** The content. */
	private ComponentContent content;

	/** The progress event. */
	private NavigatorProgressEvent progressEvent;
	
	/** The content properties. */
	private Map<String, Object> contentProperties = null;

	/**
	 * Constructs a FramePanel specifying a "window" ID.
	 *
	 * @param windowId
	 *            the window id
	 */
	public FramePanel(String windowId) {
		this.knownParentFrame = null;
		this.windowId = windowId;
		this.setLayout(WrapperLayout.getInstance());
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
	}

	/**
	 * Constructs a FramePanel specifying a non-null parent frame. This
	 * constructor is useful when navigation in the new frame must occur before
	 * the frame is added to the GUI component hierarchy.
	 *
	 * @param parentFrame
	 *            the parent frame
	 */
	public FramePanel(FramePanel parentFrame) {
		this.knownParentFrame = parentFrame;
		this.windowId = null;
		this.setLayout(WrapperLayout.getInstance());
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
	}

	/**
	 * Constructs a standalone <code>FramePanel</code> that can be added to any
	 * Swing window or component. Note that the FramePanel should be part of a
	 * Swing or AWT window before it becomes functional.
	 */
	public FramePanel() {
		this((FramePanel) null);
	}

	/**
	 * Sets the opener frame.
	 *
	 * @param opener
	 *            the new opener frame
	 */
	public void setOpenerFrame(NavigatorFrame opener) {
		this.openerFrame = opener;
	}

	/**
	 * Causes an event to be fired. This method is for internal use.
	 */
	public void informResponseProcessed() {
		this.dispatchResponseProcessed(new ResponseEvent(this));
	}

	/**
	 * Adds a listener of navigation events.
	 *
	 * @param listener
	 *            The listener.
	 */
	public void addNavigationListener(NavigationListener listener) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
		}
		synchronized (this) {
			this.navigationListeners.add(listener);
		}
	}

	/**
	 * Removes a listener of navigation events previously added with
	 * {@link #addNavigationListener(NavigationListener)}.
	 *
	 * @param listener
	 *            The listener.
	 */
	public void removeNavigationListener(NavigationListener listener) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
		}
		synchronized (this) {
			this.navigationListeners.remove(listener);
		}
	}

	/**
	 * Adds a listener of content events.
	 *
	 * @param listener
	 *            The listener.
	 * @see #getComponentContent()
	 */
	public void addContentListener(ContentListener listener) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
		}
		synchronized (this) {
			this.contentListeners.add(listener);
		}
	}

	/**
	 * Removes a listener of content events previously added with
	 * {@link #addNavigationListener(NavigationListener)}.
	 *
	 * @param listener
	 *            The listener.
	 */
	public void removeContentListener(ContentListener listener) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
		}
		synchronized (this) {
			this.contentListeners.remove(listener);
		}
	}

	/**
	 * Adds a listener of response events.
	 *
	 * @param listener
	 *            The listener.
	 */
	public void addResponseListener(ResponseListener listener) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
		}
		synchronized (this) {
			this.responseListeners.add(listener);
		}
	}

	/**
	 * Removes a listener of navigation events previously added with
	 * {@link #addNavigationListener(NavigationListener)}.
	 *
	 * @param listener
	 *            The listener.
	 */
	public void removeResponseListener(ResponseListener listener) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
		}
		synchronized (this) {
			this.responseListeners.remove(listener);
		}
	}

	/**
	 * Dispatch before navigate.
	 *
	 * @param event
	 *            the event
	 * @throws NavigationVetoException
	 *             the navigation veto exception
	 */
	private void dispatchBeforeNavigate(final NavigationEvent event) throws NavigationVetoException {
		try {
			AccessController.doPrivileged(new PrivilegedAction<Object>() {
				// Reason: Dispatching an event to extensions requires
				// permission to,
				// among other things, setting the context class loader.
				@Override
				public Object run() {
					try {
						ExtensionManager.getInstance().dispatchBeforeNavigate(event);
						NavigationListener[] listeners;
						synchronized (this) {
							listeners = navigationListeners.toArray(NavigationListener.EMPTY_ARRAY);
						}
						for (NavigationListener listener : listeners) {
							listener.beforeNavigate(event);
						}
						return null;
					} catch (NavigationVetoException nve) {
						throw new WrapperException(nve);
					}
				}
			});
		} catch (WrapperException we) {
			throw (NavigationVetoException) we.getCause();
		}
	}

	/**
	 * Dispatch before local navigate.
	 *
	 * @param event
	 *            the event
	 * @throws NavigationVetoException
	 *             the navigation veto exception
	 */
	private void dispatchBeforeLocalNavigate(final NavigationEvent event) throws NavigationVetoException {
		try {
			AccessController.doPrivileged(new PrivilegedAction<Object>() {
				// Reason: Dispatching an event to extensions requires
				// permission to,
				// among other things, setting the context class loader.
				@Override
				public Object run() {
					try {
						ExtensionManager.getInstance().dispatchBeforeLocalNavigate(event);
						NavigationListener[] listeners;
						synchronized (this) {
							listeners = navigationListeners.toArray(NavigationListener.EMPTY_ARRAY);
						}
						for (NavigationListener listener : listeners) {
							listener.beforeLocalNavigate(event);
						}
						return null;
					} catch (NavigationVetoException nve) {
						throw new WrapperException(nve);
					}
				}
			});
		} catch (WrapperException we) {
			throw (NavigationVetoException) we.getCause();
		}
	}

	/**
	 * Dispatch before window open.
	 *
	 * @param event
	 *            the event
	 * @throws NavigationVetoException
	 *             the navigation veto exception
	 */
	private void dispatchBeforeWindowOpen(final NavigationEvent event) throws NavigationVetoException {
		try {
			AccessController.doPrivileged(new PrivilegedAction<Object>() {
				// Reason: Dispatching an event to extensions requires
				// permission to,
				// among other things, setting the context class loader.
				@Override
				public Object run() {
					try {
						ExtensionManager.getInstance().dispatchBeforeWindowOpen(event);
						NavigationListener[] listeners;
						synchronized (this) {
							listeners = navigationListeners.toArray(NavigationListener.EMPTY_ARRAY);
						}
						for (NavigationListener listener : listeners) {
							listener.beforeWindowOpen(event);
						}
						return null;
					} catch (NavigationVetoException nve) {
						throw new WrapperException(nve);
					}
				}
			});
		} catch (WrapperException we) {
			throw (NavigationVetoException) we.getCause();
		}
	}

	/**
	 * Dispatch content set.
	 *
	 * @param event
	 *            the event
	 */
	private void dispatchContentSet(ContentEvent event) {
		ContentListener[] listeners;
		synchronized (this) {
			listeners = this.contentListeners.toArray(ContentListener.EMPTY_ARRAY);
		}
		for (ContentListener listener : listeners) {
			listener.contentSet(event);
		}
	}

	/**
	 * Dispatch response processed.
	 *
	 * @param event
	 *            the event
	 */
	private void dispatchResponseProcessed(ResponseEvent event) {
		ResponseListener[] listeners;
		synchronized (this) {
			listeners = this.responseListeners.toArray(ResponseListener.EMPTY_ARRAY);
		}
		for (ResponseListener listener : listeners) {
			listener.responseProcessed(event);
		}
	}

	/**
	 * Gets the window callback.
	 *
	 * @return the window callback
	 */
	protected WindowCallback getWindowCallback() {
		FramePanel kpf = this.knownParentFrame;
		if (kpf != null) {
			return kpf.getWindowCallback();
		}
		Container parent = this.getParent();
		while (parent != null && !(parent instanceof BrowserWindow)) {
			parent = parent.getParent();
		}
		if (parent == null) {
			return null;
		}
		return ((BrowserWindow) parent).getWindowCallback();
	}

	/**
	 * Gets the parent frame. This is <code>null</code> for the top-most frame
	 * in a window or when the FramePanel is detached.
	 *
	 * @return the parent frame
	 */
	@Override
	public NavigatorFrame getParentFrame() {
		// TODO: Security?
		NavigatorFrame kpf = this.knownParentFrame;
		if (kpf != null) {
			return kpf;
		}
		Container parent = this.getParent();
		while (parent != null && !(parent instanceof NavigatorFrame)) {
			parent = parent.getParent();
		}
		return (NavigatorFrame) parent;
	}

	/**
	 * Gets the top-most frame in a window. It may return the current frame if
	 * its parent is <code>null</code>.
	 *
	 * @return the top frame
	 */
	@Override
	public NavigatorFrame getTopFrame() {
		NavigatorFrame current = this;
		while(true) {
			NavigatorFrame ancestor = current.getParentFrame();
			if (ancestor == null) {
				return current;
			}
			current = ancestor;
		}
	}

	/**
	 * Implements {@link NavigatorFrame#getComponent()}.
	 *
	 * @return the component
	 */
	@Override
	public Component getComponent() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		// Unless done this way, duplicate
		// painting occurs for nested frames.
		this.paintComponent(g);
		this.paintBorder(g);
		this.paintChildren(g);
	}

	/**
	 * Gets the back navigation entries.
	 *
	 * @return the back navigation entries
	 */
	public NavigationEntry[] getBackNavigationEntries() {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
		}
		synchronized (this) {
			return this.navigationEngine.getBackNavigationEntries();
		}
	}

	/**
	 * Gets the forward navigation entries.
	 *
	 * @return the forward navigation entries
	 */
	public NavigationEntry[] getForwardNavigationEntries() {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
		}
		synchronized (this) {
			return this.navigationEngine.getForwardNavigationEntries();
		}
	}

	/**
	 * Determines if the current navigation entry has associated source code.
	 *
	 * @return true, if successful
	 */
	public boolean hasSource() {
		// TODO: Security?
		ComponentContent content = this.content;
		return content != null && content.getSourceCode() != null;
	}

	/**
	 * Determines whether there's a selection with content to be copied in this
	 * frame.
	 *
	 * @return true, if successful
	 */
	public boolean canCopy() {
		// TODO: Security?
		ComponentContent content = this.content;
		return content == null ? false : content.canCopy();
	}

	/**
	 * Copies the selection, if any, to the clipboard. Whether this method is
	 * supported depends on content being rendered.
	 *
	 * @return true, if successful
	 */
	public boolean copy() {
		// TODO: Security?
		ComponentContent content = this.content;
		return content == null ? false : content.copy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#replaceContent(java.awt.Component)
	 */
	@Override
	public final void replaceContent(final Component component) {
		// TODO: Security?
		this.replaceContent(null, new SimpleComponentContent(component));
	}

	/**
	 * Replaces the content of the frame. This method can be safely called
	 * outside the GUI dispatch thread.
	 *
	 * @param response
	 *            the response
	 * @param content
	 *            the content
	 */
	@Override
	public void replaceContent(final ClientletResponse response, final ComponentContent content) {
		// Method probably invoked outside GUI thread.
		if (SwingUtilities.isEventDispatchThread()) {
			this.replaceContentImpl(response, content);
		} else {
			// Security note: Need to pass security context of caller
			// into invokeLater task.
			final AccessControlContext context = AccessController.getContext();
			SwingUtilities.invokeLater(() -> {
				PrivilegedAction<Object> action = () -> {
					FramePanel.this.replaceContentImpl(response, content);
					return null;
				};
				AccessController.doPrivileged(action, context);
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#addImpl(java.awt.Component, java.lang.Object,
	 * int)
	 */
	@Override
	protected void addImpl(Component comp, Object constraints, int index) {
		// Check security. Content downloaded off the web should
		// not be allowed to replace frame content at will.
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
		}
		super.addImpl(comp, constraints, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#remove(java.awt.Component)
	 */
	@Override
	public void remove(Component comp) {
		// Check security. Content downloaded off the web should
		// not be allowed to replace frame content at will.
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
		}
		super.remove(comp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#remove(int)
	 */
	@Override
	public void remove(int index) {
		// Check security. Content downloaded off the web should
		// not be allowed to replace frame content at will.
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
		}
		super.remove(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#removeAll()
	 */
	@Override
	public void removeAll() {
		// Check security. Content downloaded off the web should
		// not be allowed to replace frame content at will.
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
		}
		super.removeAll();
	}

	/**
	 * Replace content impl.
	 *
	 * @param response
	 *            the response
	 * @param content
	 *            the content
	 */
	protected void replaceContentImpl(final ClientletResponse response, final ComponentContent content) {
		if (logger.isInfoEnabled()) {
			logger.info("replaceContentImpl(): this=" + this + ",response=" + response + ", content=" + content);
		}
		// Security note: Currently expected to be private.
		// Always called in GUI thread.
		// removeAll and add will invalidate.
		ComponentContent oldContent = this.content;
		this.removeAll();
		if (oldContent != null) {
			oldContent.removeNotify();
		}
		if (content != null) {
			Component component = content.getComponent();
			if (component == null) {
				throw new IllegalStateException(
						"Component from " + content + " is null: " + response.getResponseURL() + ".");
			}
			this.add(component);
		}
		// Call to validate will lay out children.
		this.validate();
		this.repaint();

		// Set this at the end, after removal and addition of components has
		// succeded.
		this.content = content;
		if (content != null) {
			content.addNotify();
			this.updateContentProperties(content);
		}

		if (response != null) {
			String title = content == null ? null : content.getTitle();
			String description = content == null ? null : content.getDescription();
			NavigationEntry navigationEntry = NavigationEntry.fromResponse(this, response, title, description);
			if (response.isNewNavigationAction()) {
				synchronized (this) {
					this.navigationEngine.addNavigationEntry(navigationEntry);
				}
			}
			WindowCallback wc = this.getWindowCallback();
			if (wc != null) {
				// It's important that the handleDocumentRendering
				// method be called right after navigationEngine is
				// updated.
				wc.handleDocumentRendering(this, response, content);
			}
		}
		this.dispatchContentSet(new ContentEvent(this, content, response));
	}

	/**
	 * Clears current content. This method should be invoked in the GUI thread.
	 */
	public void clear() {
		this.removeAll();
		this.content = null;
	}

	/**
	 * Gets the window.
	 *
	 * @return the window
	 */
	private Window getWindow() {
		// TODO: Security? Getting parent security?
		FramePanel kpf = this.knownParentFrame;
		if (kpf != null) {
			return kpf.getWindow();
		}
		Container parent = this.getParent();
		if (parent instanceof FramePanel) {
			return ((FramePanel) parent).getWindow();
		}
		while (parent != null && !(parent instanceof Window)) {
			parent = parent.getParent();
		}
		return (Window) parent;
	}

	/**
	 * Closes the window this frame belongs to.
	 */
	@Override
	public void closeWindow() {
		// TODO: Security?
		Window window = this.getWindow();
		if (window != null) {
			window.dispose();
		}
	}

	/**
	 * Opens a confirmation dialog.
	 *
	 * @param message
	 *            the message
	 * @return true, if successful
	 */
	@Override
	public boolean confirm(final String message) {
		return AccessController.doPrivileged((PrivilegedAction<Boolean>) () -> JOptionPane
				.showConfirmDialog(FramePanel.this, message) == JOptionPane.YES_OPTION);
	}

	/**
	 * Implements {@link NavigatorFrame#invokeLater(Runnable)}.
	 *
	 * @param runnable
	 *            the runnable
	 */
	@Override
	public void invokeLater(Runnable runnable) {
		SwingUtilities.invokeLater(runnable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#navigate(java.lang.String)
	 */
	@Override
	public final void navigate(String urlOrPath) throws MalformedURLException {
		URL url = Urls.guessURL(urlOrPath);
		this.navigate(url, "GET", null, TargetType.SELF, RequestType.PROGRAMMATIC);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#navigate(java.lang.String,
	 * org.loboevolution.ua.RequestType)
	 */
	@Override
	public final void navigate(String urlOrPath, RequestType requestType) throws MalformedURLException {
		URL url = Urls.guessURL(urlOrPath);
		this.navigate(url, "GET", null, TargetType.SELF, requestType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#navigate(java.net.URL,
	 * java.lang.String, org.loboevolution.ua.ParameterInfo,
	 * org.loboevolution.ua.TargetType, org.loboevolution.ua.RequestType)
	 */
	@Override
	public void navigate(URL url, String method, ParameterInfo paramInfo, TargetType type, RequestType requestType) {
		this.navigate(url, method, paramInfo, type, requestType, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#navigate(java.net.URL,
	 * java.lang.String, org.loboevolution.ua.ParameterInfo,
	 * org.loboevolution.ua.TargetType, org.loboevolution.ua.RequestType,
	 * org.loboevolution.ua.NavigatorFrame)
	 */
	@Override
	public void navigate(URL url, String method, ParameterInfo paramInfo, TargetType type, RequestType requestType,
			NavigatorFrame originatingFrame) {
		NavigationEvent event = new NavigationEvent(this, url, method, paramInfo, type, requestType, originatingFrame);
		this.navigate(event);
	}

	/**
	 * Navigate.
	 *
	 * @param event
	 *            the event
	 */
	private void navigate(NavigationEvent event) {
		try {
			this.dispatchBeforeNavigate(event);
		} catch (NavigationVetoException nve) {
			if (logger.isInfoEnabled()) {
				logger.info("navigateLocal(): Navigation was vetoed: " + nve);
			}
			return;
		}
		TargetType type = event.getTargetType();
		URL url = event.getURL();
		String method = event.getMethod();
		ParameterInfo paramInfo = event.getParamInfo();
		RequestType requestType = event.getRequestType();
		switch (type) {
		case PARENT:
			NavigatorFrame parent = this.getParentFrame();
			if (parent != null) {
				parent.navigate(url, method, paramInfo, TargetType.SELF, requestType, this);
			} else {
				this.navigateLocal(event);
			}
			break;
		case TOP:
			NavigatorFrame top = this.getTopFrame();
			if (top == this) {
				this.navigateLocal(event);
			} else {
				top.navigate(url, method, paramInfo, TargetType.SELF, requestType, this);
			}
			break;
		case SELF:
			this.navigateLocal(event);
			break;
		case BLANK:
			this.open(url, method, paramInfo);
			break;
		default:
			break;
		}
	}

	/**
	 * Navigate to history entry.
	 *
	 * @param url
	 *            the url
	 */
	private void navigateToHistoryEntry(URL url) {
		this.navigateLocal(url, "GET", RequestType.HISTORY, this);
	}

	/**
	 * Checks if is OK to add referrer.
	 *
	 * @param requestType
	 *            the request type
	 * @return true, if is OK to add referrer
	 */
	protected boolean isOKToAddReferrer(RequestType requestType) {
		return requestType == RequestType.CLICK || requestType == RequestType.PROGRAMMATIC
				|| requestType == RequestType.PROGRAMMATIC_FROM_CLICK || requestType == RequestType.OPEN_WINDOW
				|| requestType == RequestType.OPEN_WINDOW_FROM_CLICK || requestType == RequestType.FORM;
	}

	/**
	 * Navigate local.
	 *
	 * @param url
	 *            the url
	 * @param method
	 *            the method
	 * @param requestType
	 *            the request type
	 * @param originatingFrame
	 *            the originating frame
	 */
	private void navigateLocal(URL url, String method, RequestType requestType, FramePanel originatingFrame) {
		NavigationEvent event = new NavigationEvent(this, url, method, requestType, originatingFrame);
		this.navigateLocal(event);
	}

	/**
	 * Navigate local.
	 *
	 * @param event
	 *            the event
	 */
	private void navigateLocal(NavigationEvent event) {
		try {
			this.dispatchBeforeLocalNavigate(event);
		} catch (NavigationVetoException nve) {
			if (logger.isInfoEnabled()) {
				logger.info("navigateLocal(): Navigation was vetoed: " + nve);
			}
			return;
		}
		String referrer = null;
		RequestType requestType = event.getRequestType();
		URL url = event.getURL();
		String method = event.getMethod();
		ParameterInfo paramInfo = event.getParamInfo();
		if (this.isOKToAddReferrer(requestType)) {
			// TODO: When child frame does a _top navigate, referrer
			// should apparently be from child.
			NavigationEntry entry = this.getCurrentNavigationEntry();
			if (entry != null) {
				referrer = entry.getUrl().toExternalForm();
			}
		}
		ClientletRequest request = new ClientletRequestImpl(false, url, method, paramInfo, null, referrer, null,
				requestType);
		final RequestHandler handler = new ClientletRequestHandler(request, this.getWindowCallback(), this);
		SecurityManager sm = System.getSecurityManager();
		if (sm == null) {
			RequestEngine.getInstance().scheduleRequest(handler);
		} else {
			AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
				// Justification: While requests by untrusted code
				// are generally only allowed on certain hosts,
				// navigation is an exception.
				RequestEngine.getInstance().scheduleRequest(handler);
				return null;
			});
		}
	}

	/**
	 * Opens a window and renders the URL or path provided.
	 *
	 * @param urlOrPath
	 *            the url or path
	 * @return The top frame of the new window.
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	@Override
	public final NavigatorFrame open(String urlOrPath) throws MalformedURLException {
		URL url = Urls.guessURL(urlOrPath);
		return this.open(url, (Properties) null);
	}

	/**
	 * Opens a window and renders the URL provided.
	 *
	 * @param url
	 *            The URL of the document.
	 * @param windowProperties
	 *            A Properties object that follows JavaScript Window.open()
	 *            conventions.
	 * @return The top frame of the new window.
	 */
	@Override
	public final NavigatorFrame open(URL url, Properties windowProperties) {
		return this.open(url, null, windowProperties);
	}

	/**
	 * Opens a window and renders the URL provided.
	 *
	 * @param url
	 *            The URL of the document.
	 * @param windowId
	 *            A unique ID for the window.
	 * @param windowProperties
	 *            A Properties object that follows JavaScript Window.open()
	 *            conventions.
	 * @return The top frame of the new window.
	 */
	public final NavigatorFrame open(URL url, String windowId, Properties windowProperties) {
		return this.open(url, "GET", null, windowId, windowProperties);
	}

	/**
	 * Opens a window and renders the URL provided.
	 *
	 * @param url
	 *            The URL of the document.
	 * @return The top frame of the new window.
	 */
	@Override
	public final NavigatorFrame open(URL url) {
		return this.open(url, (Properties) null);
	}

	/**
	 * Opens a window and renders the URL provided. This method is called to
	 * request that a new window is opened. For example, this method will be
	 * invoked on JavaScript Window.open() calls. Override to be informed of
	 * such calls.
	 *
	 * @param url
	 *            the url
	 * @param method
	 *            the method
	 * @param pinfo
	 *            the pinfo
	 * @param windowId
	 *            the window id
	 * @param windowProperties
	 *            the window properties
	 * @return The top frame of the new window. The method may return
	 *         <code>null</code> if navigation was vetoed by a listener.
	 */
	@Override
	public NavigatorFrame open(URL url, String method, ParameterInfo pinfo, String windowId,
			Properties windowProperties) {
		NavigationEvent event = new NavigationEvent(this, url, method, pinfo, TargetType.BLANK, RequestType.OPEN_WINDOW,
				this);
		try {
			this.dispatchBeforeWindowOpen(event);
		} catch (NavigationVetoException nve) {
			if (logger.isInfoEnabled()) {
				logger.info("navigateLocal(): Navigation was vetoed: " + nve);
			}
			return null;
		}
		return FramePanel.openWindow(this, url, windowId, windowProperties, method, pinfo);
	}

	/**
	 * Opens a window and renders the URL provided.
	 *
	 * @param url
	 *            The URL of the document.
	 * @param method
	 *            The request method.
	 * @param pinfo
	 *            Any additional parameter data.
	 * @return The top frame of the new window.
	 */
	@Override
	public final NavigatorFrame open(URL url, String method, ParameterInfo pinfo) {
		return this.open(url, method, pinfo, null, null);
	}

	/**
	 * Static method for opening a window.
	 *
	 * @param opener
	 *            the opener
	 * @param url
	 *            the url
	 * @param windowId
	 *            the window id
	 * @param windowProperties
	 *            the window properties
	 * @param method
	 *            the method
	 * @param pinfo
	 *            the pinfo
	 * @return The top frame in the window that was opened.
	 */
	public static NavigatorFrame openWindow(final FramePanel opener, URL url, final String windowId,
			final Properties windowProperties, String method, ParameterInfo pinfo) {
		ClientletRequest request = new ClientletRequestImpl(true, url, method, pinfo, RequestType.OPEN_WINDOW);
		final NavigatorWindowImpl wcontext = AccessController
				.doPrivileged((PrivilegedAction<NavigatorWindowImpl>) () -> new NavigatorWindowImpl(opener, windowId,
						windowProperties));
		final FramePanel newFrame = wcontext.getFramePanel();
		final ClientletRequestHandler handler = new ClientletRequestHandler(request, wcontext, newFrame);
		handler.evtProgress.addListener(new org.loboevolution.util.GenericEventListener() {
			@Override
			public void processEvent(java.util.EventObject event) {
				// Assumed to execute in GUI thread.
				NavigatorProgressEvent pe = (NavigatorProgressEvent) event;
				if (pe == null || pe.getProgressType() == ProgressType.DONE) {
					if (SwingUtilities.isEventDispatchThread()) {
						wcontext.resetAsNavigator(handler.getContextWindowProperties());
					} else {
						SwingUtilities
								.invokeLater(() -> wcontext.resetAsNavigator(handler.getContextWindowProperties()));
					}
					// We don't want to reset as navigator twice.
					handler.evtProgress.removeListener(this);
				} else {
					wcontext.updatePreNavigationProgress(pe);
				}
			}
		});
		SecurityManager sm = System.getSecurityManager();
		if (sm == null) {
			RequestEngine.getInstance().scheduleRequest(handler);
		} else {
			AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
				// Justification: While requests by untrusted code
				// are generally only allowed on certain hosts,
				// navigation is an exception.
				RequestEngine.getInstance().scheduleRequest(handler);
				return null;
			});
		}
		return newFrame;
	}

	/**
	 * Opens a message prompt dialog.
	 *
	 * @param message
	 *            the message
	 * @param inputDefault
	 *            the input default
	 * @return the string
	 */
	@Override
	public String prompt(final String message, final String inputDefault) {
		return AccessController.doPrivileged(
				(PrivilegedAction<String>) () -> JOptionPane.showInputDialog(FramePanel.this, message, inputDefault));
	}

	/**
	 * Sends the window to the back (blur).
	 */
	@Override
	public void windowToBack() {
		Window window = this.getWindow();
		if (window != null) {
			window.toBack();
		}
	}

	/**
	 * Sends the window to the front and grabs focus for the frame.
	 */
	@Override
	public void windowToFront() {
		Window window = this.getWindow();
		if (window != null) {
			window.toFront();
		}
		this.grabFocus();
	}

	/**
	 * Opens an alert dialog.
	 *
	 * @param message
	 *            the message
	 */
	@Override
	public void alert(final String message) {
		AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
			JOptionPane.showMessageDialog(FramePanel.this, message);
			return null;
		});
	}

	/**
	 * Navigates to the given entry without adding the entry to frame history.
	 * This is the mechanism that should be used to "go back" to an entry
	 * already visited.
	 *
	 * @param entry
	 *            the entry
	 * @return true, if successful
	 */
	public boolean goTo(NavigationEntry entry) {
		if (!"GET".equals(entry.getMethod())) {
			throw new IllegalArgumentException("Method only accepts entries with GET method.");
		}
		this.navigateToHistoryEntry(entry.getUrl());
		synchronized (this) {
			return this.navigationEngine.moveTo(entry);
		}
	}

	/**
	 * Navigates back.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean back() {
		return this.moveNavigation(-1);
	}

	/**
	 * Navigates forward.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean forward() {
		return this.moveNavigation(+1);
	}

	/**
	 * Move navigation.
	 *
	 * @param offset
	 *            the offset
	 * @return true, if successful
	 */
	private boolean moveNavigation(int offset) {
		if (offset == 0 || offset > 1 || offset < -1) {
			throw new IllegalArgumentException("offset: only +1 or -1 are allowed");
		}
		synchronized (this) {
			NavigationEntry newEntry;
			while(true) {
				newEntry = this.navigationEngine.move(offset);
				if (newEntry == null) {
					return false;
				}
				if (!"GET".equals(newEntry.getMethod())) {
					// back() and forward() only supported for GET.
					continue;
				}
				break;
			}
			this.navigateToHistoryEntry(newEntry.getUrl());
			return true;
		}
	}

	/**
	 * Determines whether the frame can navigate forward.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean canForward() {
		synchronized (this) {
			return this.navigationEngine.hasNextWithGET();
		}
	}

	/**
	 * Determines whether the frame can navigate back.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean canBack() {
		synchronized (this) {
			return this.navigationEngine.hasPrevWithGET();
		}
	}

	/**
	 * Reloads the current document.
	 */
	@Override
	public void reload() {
		NavigationEntry entry;
		synchronized (this) {
			entry = this.navigationEngine.getCurrentEntry();
		}
		if (entry != null) {
			String method = entry.getMethod();
			if (!"GET".equals(method)) {
				String lineBreak = System.getProperty("line.separator");
				this.alert("Reloading a document not obtained with the GET " + lineBreak
						+ "method is disallowed for security reasons." + lineBreak
						+ "The request method of the current page is " + method + ".");

			} else {
				this.navigateLocal(entry.getUrl(), entry.getMethod(), RequestType.SOFT_RELOAD, this);
			}
		}
	}

	/**
	 * Determines whether the current document can be reloaded.
	 *
	 * @return true, if successful
	 */
	public boolean canReload() {
		NavigationEntry entry;
		synchronized (this) {
			entry = this.navigationEngine.getCurrentEntry();
		}
		// Check for request method or not?
		return entry != null;
	}

	/**
	 * Creates a frame that is expected to be used as a child of the current
	 * one.
	 *
	 * @return the navigator frame
	 */
	@Override
	public NavigatorFrame createFrame() {
		return FramePanelFactorySource.getInstance().getActiveFactory().createFramePanel(this);
	}

	/**
	 * Gets the default window status.
	 *
	 * @return the default status
	 */
	@Override
	public String getDefaultStatus() {
		WindowCallback wc = this.getWindowCallback();
		if (wc != null) {
			return wc.getDefaultStatus();
		} else {
			return null;
		}
	}

	/**
	 * Gets the item.
	 *
	 * @param name
	 *            the name
	 * @return the item
	 */
	public Object getItem(String name) {
		return Items.getItem(this, name);
	}

	/**
	 * Gets the frame that opened the current frame, if any.
	 *
	 * @return the opener frame
	 */
	@Override
	public NavigatorFrame getOpenerFrame() {
		return this.openerFrame;
	}

	/**
	 * Gets the current window status.
	 *
	 * @return the status
	 */
	@Override
	public String getStatus() {
		WindowCallback wc = this.getWindowCallback();
		if (wc != null) {
			return wc.getStatus();
		} else {
			return null;
		}
	}

	/**
	 * Gets the window ID if this is the top frame in a window.
	 *
	 * @return the window id
	 */
	@Override
	public String getWindowId() {
		return this.windowId;
	}

	/**
	 * Determines if the window is closed.
	 *
	 * @return true, if is window closed
	 */
	@Override
	public boolean isWindowClosed() {
		Window window = this.getWindow();
		if (window != null) {
			return !window.isDisplayable();
		}
		return true;
	}

	/**
	 * Sets the default window status.
	 *
	 * @param value
	 *            the new default status
	 */
	@Override
	public void setDefaultStatus(String value) {
		WindowCallback wc = this.getWindowCallback();
		if (wc != null) {
			wc.setDefaultStatus(this, value);
		}
	}

	/**
	 * Sets the item.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void setItem(String name, Object value) {
		Items.setItem(this, name, value);
	}

	/**
	 * Sets the window status.
	 *
	 * @param status
	 *            the new status
	 */
	@Override
	public void setStatus(String status) {
		WindowCallback wc = this.getWindowCallback();
		if (wc != null) {
			wc.setStatus(this, status);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		if (this.isPreferredSizeSet()) {
			return super.getPreferredSize();
		} else {
			return new Dimension(600, 400);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getMinimumSize()
	 */
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(1, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getMaximumSize()
	 */
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(Short.MAX_VALUE, Short.MAX_VALUE);
	}

	/**
	 * Gets the component content currently set in the frame.
	 *
	 * @return the component content
	 */
	@Override
	public ComponentContent getComponentContent() {
		// TODO: Security?
		return this.content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#getSourceCode()
	 */
	@Override
	public String getSourceCode() {
		ComponentContent content = this.content;
		return content == null ? null : content.getSourceCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#navigate(java.net.URL)
	 */
	@Override
	public final void navigate(URL url) {
		this.navigate(url, RequestType.PROGRAMMATIC);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#navigate(java.net.URL,
	 * org.loboevolution.ua.RequestType)
	 */
	@Override
	public final void navigate(URL url, RequestType requestType) {
		this.navigate(url, "GET", null, TargetType.SELF, requestType);
	}

	/**
	 * Gets the current navigation entry.
	 *
	 * @return the current navigation entry
	 */
	@Override
	public NavigationEntry getCurrentNavigationEntry() {
		synchronized (this) {
			return this.navigationEngine.getCurrentEntry();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#getProgressEvent()
	 */
	@Override
	public NavigatorProgressEvent getProgressEvent() {
		return this.progressEvent;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.ua.NavigatorFrame#setProgressEvent(org.loboevolution.ua.
	 * NavigatorProgressEvent)
	 */
	@Override
	public void setProgressEvent(NavigatorProgressEvent event) {
		this.progressEvent = event;
		if (event != null) {
			WindowCallback wc = this.getWindowCallback();
			if (wc != null) {
				wc.updateProgress(event);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#createNetworkRequest()
	 */
	@Override
	public HttpRequest createHttpRequest() {
		return new HttpRequest();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#toString()
	 */
	@Override
	public String toString() {
		return "FramePanel[windowId=" + windowId + ",hashCode=" + this.hashCode() + ",parent=" + this.getParent() + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#resizeWindowBy(int, int)
	 */
	@Override
	public void resizeWindowBy(int byWidth, int byHeight) {
		Window window = this.getWindow();
		if (logger.isInfoEnabled()) {
			logger.info("resizeWindowBy(): byWidth=" + byWidth + ",byHeight=" + byHeight + "; window=" + window);
		}
		if (window != null) {
			window.setSize(window.getWidth() + byWidth, window.getHeight() + byHeight);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#resizeWindowTo(int, int)
	 */
	@Override
	public void resizeWindowTo(int width, int height) {
		Window window = this.getWindow();
		if (logger.isInfoEnabled()) {
			logger.info("resizeWindowTo(): width=" + width + ",height=" + height + "; window=" + window);
		}
		if (window != null) {
			window.setSize(width, height);
		}
	}

	/**
	 * Gets the top frame window.
	 *
	 * @return the top frame window
	 */
	public Window getTopFrameWindow() {
		return topFrameWindow;
	}

	/**
	 * Sets the top frame window.
	 *
	 * @param topFrameWindow
	 *            the new top frame window
	 */
	public void setTopFrameWindow(Window topFrameWindow) {
		this.topFrameWindow = topFrameWindow;
	}

	/**
	 * Gets the content object.
	 *
	 * @return the content object
	 */
	public Object getContentObject() {
		ComponentContent content = this.getComponentContent();
		return content == null ? null : content.getContentObject();
	}

	/**
	 * Gets the current mime type.
	 *
	 * @return the current mime type
	 */
	public String getCurrentMimeType() {
		ComponentContent content = this.getComponentContent();
		return content == null ? null : content.getMimeType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#linkClicked(java.net.URL,
	 * org.loboevolution.ua.TargetType, java.lang.Object)
	 */
	@Override
	public void linkClicked(URL url, TargetType targetType, Object linkObject) {
		NavigationEvent event = new NavigationEvent(this, url, targetType, RequestType.CLICK, linkObject, this);
		this.navigate(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#getHistoryLength()
	 */
	@Override
	public int getHistoryLength() {
		synchronized (this) {
			return this.navigationEngine.getLength();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#getNextNavigationEntry()
	 */
	@Override
	public NavigationEntry getNextNavigationEntry() {
		synchronized (this) {
			NavigationEntry[] entries = this.navigationEngine.getForwardNavigationEntries();
			return entries.length == 0 ? null : entries[0];
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#getPreviousNavigationEntry()
	 */
	@Override
	public NavigationEntry getPreviousNavigationEntry() {
		synchronized (this) {
			NavigationEntry[] entries = this.navigationEngine.getBackNavigationEntries();
			return entries.length == 0 ? null : entries[0];
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#moveInHistory(int)
	 */
	@Override
	public void moveInHistory(int offset) {
		this.moveNavigation(offset);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.ua.NavigatorFrame#navigateInHistory(java.lang.String)
	 */
	@Override
	public void navigateInHistory(String absoluteURL) {
		NavigationEntry entry;
		synchronized (this) {
			entry = this.navigationEngine.findEntry(absoluteURL);
		}
		if (entry != null) {
			this.navigateToHistoryEntry(entry.getUrl());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorFrame#setProperty(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void setProperty(String name, Object value) {
		ComponentContent content = this.getComponentContent();
		synchronized (this.propertiesMonitor) {
			if (content != null) {
				content.setProperty(name, value);
			}
			Map<String, Object> props = this.contentProperties;
			if (props == null) {
				props = new HashMap<String, Object>(5);
				this.contentProperties = props;
			}
			props.put(name, value);
		}
	}

	/**
	 * Update content properties.
	 *
	 * @param content
	 *            the content
	 */
	private void updateContentProperties(ComponentContent content) {
		synchronized (this.propertiesMonitor) {
			Map<String, Object> props = this.contentProperties;
			if (props != null) {
				Iterator<?> i = props.entrySet().iterator();
				while (i.hasNext()) {
					Map.Entry entry = (Map.Entry) i.next();
					content.setProperty((String) entry.getKey(), entry.getValue());
				}
			}
		}
	}
}
