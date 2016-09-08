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
package org.lobobrowser.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class BrowserPanel extends JPanel implements NavigatorWindow, BrowserWindow, WindowCallback {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(BrowserPanel.class);

	/** The has tool bar. */
	private final boolean hasToolBar;

	/** The menu bar. */
	private final JMenuBar menuBar;

	/** The frame panel. */
	private final FramePanel framePanel;

	/** The address bar panel. */
	private final AddressBarPanel addressBarPanel;

	/** The shared tool bar panel. */
	private final SharedToolBarPanel sharedToolBarPanel;

	/** The status bar panel. */
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
	public BrowserPanel(JMenuBar menuBar, boolean hasAddressBar, boolean hasToolBar, boolean hasStatusBar) {
		this.hasToolBar = hasToolBar;
		this.menuBar = menuBar;

		String windowId = "BrowserPanel." + System.identityHashCode(this);
		FramePanel framePanel = FramePanelFactorySource.getInstance().getActiveFactory().createFramePanel(windowId);
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
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	public void navigate(String urlOrPath) throws MalformedURLException {
		this.framePanel.navigate(urlOrPath);
	}

	/**
	 * Navigates to the URL provided.
	 *
	 * @param url
	 *            A URL.
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	public void navigate(URL url) throws MalformedURLException {
		this.framePanel.navigate(url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#addAddressBarComponent(java.awt.
	 * Component)
	 */
	@Override
	public void addAddressBarComponent(Component addressBarComponent) {
		AddressBarPanel abp = this.addressBarPanel;
		if (abp != null) {
			abp.add(addressBarComponent);
		}
	}

	/** The menues by id. */
	private final Map<String, JMenu> menuesById = new HashMap<String, JMenu>(1);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#addMenu(java.lang.String,
	 * javax.swing.JMenu)
	 */
	@Override
	public void addMenu(String menuId, JMenu menu) {
		JMenuBar menuBar = this.menuBar;
		if (menuBar != null) {
			synchronized (this.menuesById) {
				this.menuesById.put(menuId, menu);
			}
			menuBar.add(menu);
		}
	}

	/** The event. */
	private final EventDispatch2 EVENT = new LocalEventDispatch();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#addNavigatorWindowListener(org.
	 * lobobrowser .ua.NavigatorWindowListener)
	 */
	@Override
	public void addNavigatorWindowListener(NavigatorWindowListener listener) {
		EVENT.addListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.ua.NavigatorWindow#addSharedToolBarComponent(java.awt.
	 * Component )
	 */
	@Override
	public void addSharedToolBarComponent(Component toolBarComponent) {
		SharedToolBarPanel stbp = this.sharedToolBarPanel;
		if (stbp != null) {
			stbp.add(toolBarComponent);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#addStatusBarComponent(java.awt.
	 * Component)
	 */
	@Override
	public void addStatusBarComponent(Component statusBarComponent) {
		StatusBarPanel sbp = this.statusBarPanel;
		if (sbp != null) {
			sbp.add(statusBarComponent);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#addToolBar(java.awt.Component)
	 */
	@Override
	public void addToolBar(Component toolBar) {
		if (this.hasToolBar) {
			this.add(toolBar);
		}
	}

	/** The latest accessed frame. */
	private volatile NavigatorFrame latestAccessedFrame = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#back()
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#canBack()
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#canCopy()
	 */
	@Override
	public boolean canCopy() {
		return this.framePanel.canCopy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#canForward()
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#canReload()
	 */
	@Override
	public boolean canReload() {
		return this.framePanel.canReload();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#copy()
	 */
	@Override
	public boolean copy() {
		return this.framePanel.copy();
	}

	/** The Constant HGAP. */
	private static final int HGAP = 4;

	/** The Constant VGAP. */
	private static final int VGAP = 2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#createGap()
	 */
	@Override
	public Component createGap() {
		return Box.createRigidArea(new Dimension(HGAP, VGAP));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#createGlueComponent(java.awt.
	 * Component, boolean)
	 */
	@Override
	public Component createGlueComponent(Component wrappedComponent, boolean usingMaxSize) {
		return new FillerComponent(wrappedComponent, usingMaxSize);
	}

	/** The close window on dispose. */
	private boolean closeWindowOnDispose = true;

	/**
	 * Checks if is close window on dispose.
	 *
	 * @return the close window on dispose
	 */
	public boolean isCloseWindowOnDispose() {
		return closeWindowOnDispose;
	}

	/**
	 * Sets the close window on dispose.
	 *
	 * @param closeWindowOnDispose
	 *            the new close window on dispose
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
	@Override
	public void dispose() {
		if (this.closeWindowOnDispose) {
			Window awtFrame = this.getAwtWindow();
			if (awtFrame != null) {
				awtFrame.dispose();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#forward()
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#getAwtWindow()
	 */
	@Override
	public Window getAwtWindow() {
		Container parent = this.getParent();
		while ((parent != null) && !(parent instanceof Window)) {
			parent = parent.getParent();
		}
		return (Window) parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#getBackNavigationEntries()
	 */
	@Override
	public NavigationEntry[] getBackNavigationEntries() {
		return this.framePanel.getBackNavigationEntries();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#getCurrentNavigationEntry()
	 */
	@Override
	public NavigationEntry getCurrentNavigationEntry() {
		return this.framePanel.getCurrentNavigationEntry();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#getForwardNavigationEntries()
	 */
	@Override
	public NavigationEntry[] getForwardNavigationEntries() {
		return this.framePanel.getForwardNavigationEntries();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#getMenu(java.lang.String)
	 */
	@Override
	public JMenu getMenu(String menuId) {
		synchronized (this.menuesById) {
			return this.menuesById.get(menuId);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#getTopFrame()
	 */
	@Override
	public NavigatorFrame getTopFrame() {
		return this.framePanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#getUserAgent()
	 */
	@Override
	public UserAgent getUserAgent() {
		return org.lobobrowser.request.UserAgentImpl.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#goTo(org.lobobrowser.ua.
	 * NavigationEntry)
	 */
	@Override
	public boolean goTo(NavigationEntry entry) {
		return this.framePanel.goTo(entry);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#hasSource()
	 */
	@Override
	public boolean hasSource() {
		return this.framePanel.hasSource();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#reload()
	 */
	@Override
	public boolean reload() {
		this.framePanel.reload();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.ua.NavigatorWindow#removeNavigatorWindowListener(org.
	 * lobobrowser .ua.NavigatorWindowListener)
	 */
	@Override
	public void removeNavigatorWindowListener(NavigatorWindowListener listener) {
		EVENT.removeListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#stop()
	 */
	@Override
	public boolean stop() {
		org.lobobrowser.request.RequestEngine.getInstance().cancelAllRequests();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.gui.BrowserWindow#getTopFramePanel()
	 */
	@Override
	public FramePanel getTopFramePanel() {
		return this.framePanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.gui.BrowserWindow#getWindowCallback()
	 */
	@Override
	public WindowCallback getWindowCallback() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.gui.WindowCallback#getComponent()
	 */
	@Override
	public Component getComponent() {
		return this;
	}

	/** The default status. */
	private String defaultStatus;

	/** The status. */
	private String status;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.gui.WindowCallback#getDefaultStatus()
	 */
	@Override
	public String getDefaultStatus() {
		synchronized (this) {
			return this.defaultStatus;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.gui.WindowCallback#getStatus()
	 */
	@Override
	public String getStatus() {
		synchronized (this) {
			return this.status;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.gui.WindowCallback#handleDocumentAccess(org.lobobrowser.
	 * ua .NavigatorFrame, org.lobobrowser.clientlet.ClientletResponse)
	 */
	@Override
	public void handleDocumentAccess(NavigatorFrame frame, ClientletResponse response) {
		final NavigatorWindowEvent event = new NavigatorWindowEvent(this, NavigatorEventType.DOCUMENT_ACCESSED, frame,
				response);
		if (SwingUtilities.isEventDispatchThread()) {
			EVENT.fireEvent(event);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					EVENT.fireEvent(event);
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.gui.WindowCallback#handleDocumentRendering(org.
	 * lobobrowser .ua.NavigatorFrame,
	 * org.lobobrowser.clientlet.ClientletResponse,
	 * org.lobobrowser.clientlet.ComponentContent)
	 */
	@Override
	public void handleDocumentRendering(final NavigatorFrame frame, final ClientletResponse response,
			final ComponentContent content) {
		if (SwingUtilities.isEventDispatchThread()) {
			this.handleDocumentRenderingImpl(frame, response, content);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					BrowserPanel.this.handleDocumentRenderingImpl(frame, response, content);
				}
			});
		}
	}

	/**
	 * Gets the window title.
	 *
	 * @param response
	 *            the response
	 * @param content
	 *            the content
	 * @return the window title
	 */
	protected String getWindowTitle(ClientletResponse response, ComponentContent content) {
		String title = content == null ? null : content.getTitle();
		if (title == null) {
			title = response == null ? "" : Urls.getNoRefForm(response.getResponseURL());
		}
		return title;
	}

	/** The document title. */
	private String documentTitle;

	/**
	 * Gets the document title.
	 *
	 * @return the document title
	 */
	public String getDocumentTitle() {
		return documentTitle;
	}

	/**
	 * Sets the document title.
	 *
	 * @param documentTitle
	 *            the new document title
	 */
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	/**
	 * Handle document rendering impl.
	 *
	 * @param frame
	 *            the frame
	 * @param response
	 *            the response
	 * @param content
	 *            the content
	 */
	private void handleDocumentRenderingImpl(final NavigatorFrame frame, ClientletResponse response,
			ComponentContent content) {
		if (frame == this.framePanel) {
			String title = this.getWindowTitle(response, content);
			this.setDocumentTitle(title);
		}
		final NavigatorWindowEvent event = new NavigatorWindowEvent(this, NavigatorEventType.DOCUMENT_RENDERING, frame,
				response);
		this.latestAccessedFrame = event.getNavigatorFrame();
		if (!EVENT.fireEvent(event)) {
			logger.warn("handleDocumentRendering(): Did not deliver event to any window: " + event);
		}
	}

	/**
	 * Gets the safe extension manager.
	 *
	 * @return the safe extension manager
	 */
	private ExtensionManager getSafeExtensionManager() {
		return AccessController.doPrivileged(new PrivilegedAction<ExtensionManager>() {
			@Override
			public ExtensionManager run() {
				return ExtensionManager.getInstance();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.gui.WindowCallback#handleError(org.lobobrowser.ua.
	 * NavigatorFrame , org.lobobrowser.clientlet.ClientletResponse,
	 * java.lang.Throwable)
	 */
	@Override
	public void handleError(NavigatorFrame frame, ClientletResponse response, Throwable exception) {
		this.getSafeExtensionManager().handleError(frame, response, exception);
		// Also inform as if document rendering.
		this.handleDocumentRendering(frame, response, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.gui.WindowCallback#setDefaultStatus(org.lobobrowser.ua.
	 * NavigatorFrame, java.lang.String)
	 */
	@Override
	public void setDefaultStatus(NavigatorFrame frame, String defaultStatus) {
		synchronized (this) {
			this.defaultStatus = defaultStatus;
			if (this.status == null) {
				String actualStatus = this.defaultStatus;
				final NavigatorWindowEvent event = new NavigatorWindowEvent(this, NavigatorEventType.STATUS_UPDATED,
						frame, actualStatus, RequestType.NONE);
				if (SwingUtilities.isEventDispatchThread()) {
					EVENT.fireEvent(event);
				} else {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							EVENT.fireEvent(event);
						}
					});
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.gui.WindowCallback#setStatus(org.lobobrowser.ua.
	 * NavigatorFrame , java.lang.String)
	 */
	@Override
	public void setStatus(NavigatorFrame frame, String value) {
		String actualStatus;
		synchronized (this) {
			if (!Objects.equals(this.status, value)) {
				this.status = value;
				actualStatus = value == null ? this.defaultStatus : value;
				final NavigatorWindowEvent event = new NavigatorWindowEvent(this, NavigatorEventType.STATUS_UPDATED,
						frame, actualStatus, RequestType.NONE);
				if (SwingUtilities.isEventDispatchThread()) {
					EVENT.fireEvent(event);
				} else {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							EVENT.fireEvent(event);
						}
					});
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.gui.WindowCallback#updateProgress(org.lobobrowser.ua.
	 * NavigatorProgressEvent)
	 */
	@Override
	public void updateProgress(final NavigatorProgressEvent event) {
		if (SwingUtilities.isEventDispatchThread()) {
			EVENT.fireEvent(event);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					EVENT.fireEvent(event);
				}
			});
		}
	}

	/**
	 * Gets the content object.
	 *
	 * @return the content object
	 */
	public Object getContentObject() {
		return this.framePanel.getContentObject();
	}

	/**
	 * Gets the current mime type.
	 *
	 * @return the current mime type
	 */
	public String getCurrentMimeType() {
		return this.framePanel.getCurrentMimeType();
	}

	/**
	 * The Class LocalEventDispatch.
	 */
	public static class LocalEventDispatch extends EventDispatch2 {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.lobobrowser.util.EventDispatch2#dispatchEvent(java.util.
		 * EventListener, java.util.EventObject)
		 */
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
			default:
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

	/**
	 * Removes the content listener.
	 *
	 * @param listener
	 *            the listener
	 */
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

	/**
	 * Removes the response listener.
	 *
	 * @param listener
	 *            the listener
	 */
	public void removeResponseListener(ResponseListener listener) {
		this.framePanel.removeResponseListener(listener);
	}

	/**
	 * Gets the component content.
	 *
	 * @return the component content
	 */
	public ComponentContent getComponentContent() {
		return this.framePanel.getComponentContent();
	}
}
