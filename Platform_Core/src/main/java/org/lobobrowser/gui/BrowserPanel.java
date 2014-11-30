/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ComponentContent;
import org.lobobrowser.main.ExtensionManager;
import org.lobobrowser.main.PlatformInit;
import org.lobobrowser.ua.NavigationEntry;
import org.lobobrowser.ua.NavigationListener;
import org.lobobrowser.ua.NavigatorEvent;
import org.lobobrowser.ua.NavigatorEventType;
import org.lobobrowser.ua.NavigatorFrame;
import org.lobobrowser.ua.NavigatorProgressEvent;
import org.lobobrowser.ua.NavigatorWindow;
import org.lobobrowser.ua.NavigatorWindowEvent;
import org.lobobrowser.ua.NavigatorWindowListener;
import org.lobobrowser.ua.RequestType;
import org.lobobrowser.ua.UserAgent;
import org.lobobrowser.util.EventDispatch2;
import org.lobobrowser.util.Objects;
import org.lobobrowser.util.Urls;

/**
 * A <code>BrowserPanel</code> contains a {@link FramePanel} along with optional
 * address bar, toolbars, menu bar and status bar.
 * <p>
 * Invoke {@link #navigate(String)} to load a document into the top frame of the
 * <code>BrowserPanel</code>.
 * 
 * @see PlatformInit#init(boolean, boolean)
 */
public class BrowserPanel extends JPanel implements NavigatorWindow,
		BrowserWindow, WindowCallback {
	private static final Logger logger = Logger.getLogger(BrowserPanel.class
			.getName());
	private final boolean hasAddressBar;
	private final boolean hasToolBar;
	private final boolean hasStatusBar;
	private final JMenuBar menuBar;
	private final FramePanel framePanel;
	private final AddressBarPanel addressBarPanel;
	private final SharedToolBarPanel sharedToolBarPanel;
	private final StatusBarPanel statusBarPanel;

	/**
	 * Constructs a <code>BrowserPanel</code> with toolbars, an address bar, a
	 * status bar, but no menu bar.
	 */
	public BrowserPanel() {
		this(null, true, true, true);
	}

	/**
	 * Constructs a <code>BrowserPanel</code> with a menu bar, toolbars, an
	 * address bar and a status bar.
	 * 
	 * @param menuBar
	 *            A <code>JMenuBar</code> instance presumably set on a JFrame.
	 */
	public BrowserPanel(JMenuBar menuBar) {
		this(menuBar, true, true, true);
	}

	/**
	 * Constructs a <code>BrowserPanel</code> with optional menu bar, toolbars,
	 * an address bar and a status bar.
	 * 
	 * @param menuBar
	 *            A <code>JMenuBar</code> instance presumably set on a JFrame.
	 * @param hasAddressBar
	 *            Whether the panel has an address bar.
	 * @param hasToolBar
	 *            Whether the panel has toolbars.
	 * @param hasStatusBar
	 *            Whether the panel has a status bar.
	 */
	public BrowserPanel(JMenuBar menuBar, boolean hasAddressBar,
			boolean hasToolBar, boolean hasStatusBar) {
		this.hasToolBar = hasToolBar;
		this.hasAddressBar = hasAddressBar;
		this.hasStatusBar = hasStatusBar;
		this.menuBar = menuBar;

		String windowId = "BrowserPanel." + System.identityHashCode(this);
		FramePanel framePanel = FramePanelFactorySource.getInstance()
				.getActiveFactory().createFramePanel(windowId);
		this.framePanel = framePanel;

		Container contentPane = this;
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		if (hasAddressBar) {
			AddressBarPanel abp = new AddressBarPanel();
			this.addressBarPanel = abp;
			contentPane.add(abp);
		} else {
			this.addressBarPanel = null;
		}
		if (hasToolBar) {
			SharedToolBarPanel stbp = new SharedToolBarPanel();
			this.sharedToolBarPanel = stbp;
			contentPane.add(stbp);
		} else {
			this.sharedToolBarPanel = null;
		}
		contentPane.add(new FillerComponent(framePanel, false));
		if (hasStatusBar) {
			StatusBarPanel statusBar = new StatusBarPanel();
			this.statusBarPanel = statusBar;
			contentPane.add(statusBar);
		} else {
			this.statusBarPanel = null;
		}
		ExtensionManager.getInstance().initExtensionsWindow(this);
	}

	/**
	 * Invoke this method when the window that owns the
	 * <code>BrowserPanel</code> is about to close. This should be done in order
	 * to inform extensions about the window closing.
	 */
	public void windowClosing() {
		ExtensionManager.getInstance().shutdownExtensionsWindow(this);
	}

	/**
	 * Navigates to the URL or path provided.
	 * 
	 * @param urlOrPath
	 *            An absolute URL or file path.
	 */
	public void navigate(String urlOrPath)
			throws java.net.MalformedURLException {
		this.framePanel.navigate(urlOrPath);
	}

	/**
	 * Navigates to the URL provided.
	 * 
	 * @param url
	 *            A URL.
	 */
	public void navigate(java.net.URL url)
			throws java.net.MalformedURLException {
		this.framePanel.navigate(url);
	}

	public void addAddressBarComponent(Component addressBarComponent) {
		AddressBarPanel abp = this.addressBarPanel;
		if (abp != null) {
			abp.add(addressBarComponent);
		}
	}

	private final Map<String, JMenu> menuesById = new HashMap<String, JMenu>(1);

	public void addMenu(String menuId, JMenu menu) {
		JMenuBar menuBar = this.menuBar;
		if (menuBar != null) {
			synchronized (this.menuesById) {
				this.menuesById.put(menuId, menu);
			}
			menuBar.add(menu);
		}
	}

	private final EventDispatch2 EVENT = new LocalEventDispatch();

	public void addNavigatorWindowListener(NavigatorWindowListener listener) {
		EVENT.addListener(listener);
	}

	public void addSharedToolBarComponent(Component toolBarComponent) {
		SharedToolBarPanel stbp = this.sharedToolBarPanel;
		if (stbp != null) {
			stbp.add(toolBarComponent);
		}
	}

	public void addStatusBarComponent(Component statusBarComponent) {
		StatusBarPanel sbp = this.statusBarPanel;
		if (sbp != null) {
			sbp.add(statusBarComponent);
		}
	}

	public void addToolBar(Component toolBar) {
		if (this.hasToolBar) {
			this.add(toolBar);
		}
	}

	private volatile NavigatorFrame latestAccessedFrame = null;

	public boolean back() {
		NavigatorFrame frame = this.latestAccessedFrame;
		if (frame != null) {
			if (frame.back()) {
				return true;
			}
			if (frame == this.framePanel) {
				return false;
			}
		}
		return this.framePanel.back();
	}

	public boolean canBack() {
		NavigatorFrame frame = this.latestAccessedFrame;
		if (frame != null) {
			if (frame.canBack()) {
				return true;
			}
			if (frame == this.framePanel) {
				return false;
			}
		}
		return this.framePanel.canBack();
	}

	public boolean canCopy() {
		return this.framePanel.canCopy();
	}

	public boolean canForward() {
		NavigatorFrame frame = this.latestAccessedFrame;
		if (frame != null) {
			if (frame.canForward()) {
				return true;
			}
			if (frame == this.framePanel) {
				return false;
			}
		}
		return this.framePanel.canForward();
	}

	public boolean canReload() {
		return this.framePanel.canReload();
	}

	public boolean copy() {
		return this.framePanel.copy();
	}

	private static final int HGAP = 4;
	private static final int VGAP = 2;

	public Component createGap() {
		return Box.createRigidArea(new Dimension(HGAP, VGAP));
	}

	public Component createGlueComponent(Component wrappedComponent,
			boolean usingMaxSize) {
		return new FillerComponent(wrappedComponent, usingMaxSize);
	}

	private boolean closeWindowOnDispose = true;

	/**
	 * Returns a value indicating whether the parent window is closed when the
	 * current <code>BrowserPanel</code> is disposed.
	 * 
	 * @see #setCloseWindowOnDispose(boolean)
	 */
	public boolean isCloseWindowOnDispose() {
		return closeWindowOnDispose;
	}

	/**
	 * Sets a flag indicating whether the parent window should be closed when
	 * the current <code>BrowserPanel</code> is disposed. The
	 * <code>BrowserPanel</code> is normally disposed by the standard File/Exit
	 * menu and equivalent actions.
	 * <p>
	 * The default value of the flag is <code>true</code>.
	 * 
	 * @param closeWindowOnDispose
	 *            A boolean value.
	 * @see #dispose()
	 */
	public void setCloseWindowOnDispose(boolean closeWindowOnDispose) {
		this.closeWindowOnDispose = closeWindowOnDispose;
	}

	/**
	 * Disposes the current <code>BrowserPanel</code>. This method is normally
	 * activated by the standard File/Exit menu.
	 * 
	 * @see #setCloseWindowOnDispose(boolean)
	 */
	public void dispose() {
		if (this.closeWindowOnDispose) {
			java.awt.Window awtFrame = this.getAwtWindow();
			if (awtFrame != null) {
				awtFrame.dispose();
			}
		}
	}

	public boolean forward() {
		NavigatorFrame frame = this.latestAccessedFrame;
		if (frame != null) {
			if (frame.forward()) {
				return true;
			}
			if (frame == this.framePanel) {
				return false;
			}
		}
		return this.framePanel.forward();
	}

	public java.awt.Window getAwtWindow() {
		Container parent = this.getParent();
		while (parent != null && !(parent instanceof java.awt.Window)) {
			parent = parent.getParent();
		}
		return (java.awt.Window) parent;
	}

	public NavigationEntry[] getBackNavigationEntries() {
		return this.framePanel.getBackNavigationEntries();
	}

	public NavigationEntry getCurrentNavigationEntry() {
		return this.framePanel.getCurrentNavigationEntry();
	}

	public NavigationEntry[] getForwardNavigationEntries() {
		return this.framePanel.getForwardNavigationEntries();
	}

	public JMenu getMenu(String menuId) {
		synchronized (this.menuesById) {
			return this.menuesById.get(menuId);
		}
	}

	public NavigatorFrame getTopFrame() {
		return this.framePanel;
	}

	public UserAgent getUserAgent() {
		return org.lobobrowser.request.UserAgentImpl.getInstance();
	}

	public boolean goTo(NavigationEntry entry) {
		return this.framePanel.goTo(entry);
	}

	public boolean hasSource() {
		return this.framePanel.hasSource();
	}

	public boolean reload() {
		this.framePanel.reload();
		return true;
	}

	public void removeNavigatorWindowListener(NavigatorWindowListener listener) {
		EVENT.removeListener(listener);
	}

	public boolean stop() {
		org.lobobrowser.request.RequestEngine.getInstance().cancelAllRequests();
		return true;
	}

	public FramePanel getTopFramePanel() {
		return this.framePanel;
	}

	public WindowCallback getWindowCallback() {
		return this;
	}

	public Component getComponent() {
		return this;
	}

	private String defaultStatus;
	private String status;

	public String getDefaultStatus() {
		synchronized (this) {
			return this.defaultStatus;
		}
	}

	public String getStatus() {
		synchronized (this) {
			return this.status;
		}
	}

	public void handleDocumentAccess(NavigatorFrame frame,
			ClientletResponse response) {
		final NavigatorWindowEvent event = new NavigatorWindowEvent(this,
				NavigatorEventType.DOCUMENT_ACCESSED, frame, response);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				EVENT.fireEvent(event);
			}
		});
	}

	public void handleDocumentRendering(final NavigatorFrame frame,
			final ClientletResponse response, final ComponentContent content) {
		if (EventQueue.isDispatchThread()) {
			this.handleDocumentRenderingImpl(frame, response, content);
		} else {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					BrowserPanel.this.handleDocumentRenderingImpl(frame,
							response, content);
				}
			});
		}
	}

	protected String getWindowTitle(ClientletResponse response,
			ComponentContent content) {
		String title = content == null ? null : content.getTitle();
		if (title == null) {
			title = response == null ? "" : Urls.getNoRefForm(response
					.getResponseURL());
		}
		return title;
	}

	private String documentTitle;

	/**
	 * Gets the recommended title of the document currently in the top frame.
	 */
	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	private void handleDocumentRenderingImpl(final NavigatorFrame frame,
			ClientletResponse response, ComponentContent content) {
		if (frame == this.framePanel) {
			String title = this.getWindowTitle(response, content);
			this.setDocumentTitle(title);
		}
		final NavigatorWindowEvent event = new NavigatorWindowEvent(this,
				NavigatorEventType.DOCUMENT_RENDERING, frame, response);
		this.latestAccessedFrame = event.getNavigatorFrame();
		if (!EVENT.fireEvent(event)) {
			logger.warning("handleDocumentRendering(): Did not deliver event to any window: "
					+ event);
		}
	}

	private ExtensionManager getSafeExtensionManager() {
		return AccessController
				.doPrivileged(new PrivilegedAction<ExtensionManager>() {
					public ExtensionManager run() {
						return ExtensionManager.getInstance();
					}
				});
	}

	public void handleError(NavigatorFrame frame, ClientletResponse response,
			Throwable exception) {
		this.getSafeExtensionManager().handleError(frame, response, exception);
		// Also inform as if document rendering.
		this.handleDocumentRendering(frame, response, null);
	}

	public void setDefaultStatus(NavigatorFrame frame, String defaultStatus) {
		synchronized (this) {
			this.defaultStatus = defaultStatus;
			if (this.status == null) {
				String actualStatus = this.defaultStatus;
				final NavigatorWindowEvent event = new NavigatorWindowEvent(
						this, NavigatorEventType.STATUS_UPDATED, frame,
						actualStatus, RequestType.NONE);
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						EVENT.fireEvent(event);
					}
				});
			}
		}
	}

	public void setStatus(NavigatorFrame frame, String value) {
		String actualStatus;
		synchronized (this) {
			if (!Objects.equals(this.status, value)) {
				this.status = value;
				actualStatus = value == null ? this.defaultStatus : value;
				final NavigatorWindowEvent event = new NavigatorWindowEvent(
						this, NavigatorEventType.STATUS_UPDATED, frame,
						actualStatus, RequestType.NONE);
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						EVENT.fireEvent(event);
					}
				});
			}
		}
	}

	public void updateProgress(final NavigatorProgressEvent event) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				EVENT.fireEvent(event);
			}
		});
	}

	/**
	 * Gets an object that is used to represent the current frame content. For
	 * example, if the frame is currently showing HTML, this method will
	 * probably return an instance of
	 * <code>org.w3c.dom.html2.HTMLDocument</code>.
	 */
	public Object getContentObject() {
		return this.framePanel.getContentObject();
	}

	/**
	 * Gets a mime type that goes with the object returned by
	 * {@link FramePanel#getContentObject()}. This is not necessarily the same
	 * as the mime type declared in the headers of the response that produced
	 * the current content.
	 */
	public String getCurrentMimeType() {
		return this.framePanel.getCurrentMimeType();
	}

	public static class LocalEventDispatch extends EventDispatch2 {
		@Override
		protected void dispatchEvent(EventListener listener, EventObject event) {
			NavigatorEvent ne = (NavigatorEvent) event;
			NavigatorWindowListener nwl = (NavigatorWindowListener) listener;
			switch (ne.getEventType()) {
			case DOCUMENT_ACCESSED:
				nwl.documentAccessed((NavigatorWindowEvent) ne);
				break;
			case DOCUMENT_RENDERING:
				nwl.documentRendering((NavigatorWindowEvent) ne);
				break;
			case PROGRESS_UPDATED:
				nwl.progressUpdated((NavigatorProgressEvent) ne);
				break;
			case STATUS_UPDATED:
				nwl.statusUpdated((NavigatorWindowEvent) ne);
				break;
			case DEFAULT_STATUS_UPDATED:
				nwl.defaultStatusUpdated((NavigatorWindowEvent) ne);
				break;
			}
		}
	}

	/**
	 * Adds a listener of navigation events, applicable only to the top frame.
	 * 
	 * @param listener
	 *            The listener.
	 * @see FramePanel#addNavigationListener(NavigationListener)
	 * @see FramePanelFactorySource
	 */
	public void addNavigationListener(NavigationListener listener) {
		this.framePanel.addNavigationListener(listener);
	}

	/**
	 * Removes a listener of navigation events previously added with
	 * {@link #addNavigationListener(NavigationListener)}.
	 * 
	 * @param listener
	 *            The listener.
	 */
	public void removeNavigationListener(NavigationListener listener) {
		this.framePanel.addNavigationListener(listener);
	}

	/**
	 * Adds a listener of content events.
	 * 
	 * @param listener
	 *            The listener.
	 * @see #getComponentContent()
	 */
	public void addContentListener(ContentListener listener) {
		this.framePanel.addContentListener(listener);
	}

	public void removeContentListener(ContentListener listener) {
		this.framePanel.removeContentListener(listener);
	}

	/**
	 * Adds a listener of response events.
	 * 
	 * @param listener
	 *            The listener.
	 */
	public void addResponseListener(ResponseListener listener) {
		this.framePanel.addResponseListener(listener);
	}

	public void removeResponseListener(ResponseListener listener) {
		this.framePanel.removeResponseListener(listener);
	}

	/**
	 * Gets the component content currently set in the frame.
	 */
	public ComponentContent getComponentContent() {
		return this.framePanel.getComponentContent();
	}
}
