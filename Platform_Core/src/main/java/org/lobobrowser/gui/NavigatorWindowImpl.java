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
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;

import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ComponentContent;
import org.lobobrowser.main.ExtensionManager;
import org.lobobrowser.ua.NavigationEntry;
import org.lobobrowser.ua.NavigatorEvent;
import org.lobobrowser.ua.NavigatorEventType;
import org.lobobrowser.ua.NavigatorFrame;
import org.lobobrowser.ua.NavigatorProgressEvent;
import org.lobobrowser.ua.NavigatorWindow;
import org.lobobrowser.ua.NavigatorWindowEvent;
import org.lobobrowser.ua.NavigatorWindowListener;
import org.lobobrowser.ua.ParameterInfo;
import org.lobobrowser.ua.RequestType;
import org.lobobrowser.ua.TargetType;
import org.lobobrowser.ua.UserAgent;
import org.lobobrowser.util.EventDispatch2;
import org.lobobrowser.util.Objects;
import org.lobobrowser.util.Urls;

/**
 * Default implementation of the {@link NavigatorWindow} interface.
 */
public class NavigatorWindowImpl implements NavigatorWindow, WindowCallback {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(NavigatorWindowImpl.class);

	/** The Constant HGAP. */
	private static final int HGAP = 4;

	/** The Constant VGAP. */
	private static final int VGAP = 2;

	/** The frame panel. */
	private final FramePanel framePanel;

	/** The requested properties. */
	private final Properties requestedProperties;

	/** The window id. */
	private final String windowId;

	/** The progress window. */
	private final Window progressWindow;

	/** The browser window. */
	private final AbstractBrowserWindow browserWindow;

	/** The menus by id. */
	private final Map<String, JMenu> menusById = new HashMap<String, JMenu>();

	/** The menus. */
	private final Collection<JMenu> menus = new LinkedList<JMenu>();
	// private final Collection<JMenuItem> sharedMenuItems = new
	// LinkedList<JMenuItem>();
	/** The address bar components. */
	private final Collection<Component> addressBarComponents = new LinkedList<Component>();

	/** The shared toolbar components. */
	private final Collection<Component> sharedToolbarComponents = new LinkedList<Component>();

	/** The status bar components. */
	private final Collection<Component> statusBarComponents = new LinkedList<Component>();

	/** The tool bars. */
	private final Collection<Component> toolBars = new LinkedList<Component>();

	/** The launched. */
	private volatile boolean launched = false;

	/** The window factory. */
	private static volatile WindowFactory windowFactory = DefaultWindowFactory.getInstance();

	/**
	 * Sets the window factory.
	 *
	 * @param wf
	 *            the new window factory
	 */
	public static void setWindowFactory(WindowFactory wf) {
		windowFactory = wf;
	}

	/**
	 * Constructs a PlatformWindowContextImpl. It starts out by showing a
	 * progress window. Later a new browser window is obtained given the
	 * windowId, or created.
	 *
	 * @param openerFrame
	 *            the opener frame
	 * @param windowId
	 *            the window id
	 * @param properties
	 *            the properties
	 */
	public NavigatorWindowImpl(NavigatorFrame openerFrame, String windowId, Properties properties) {
		this.requestedProperties = properties;
		this.windowId = windowId;
		WindowFactory wf = windowFactory;
		if (wf == null) {
			throw new IllegalStateException("Global WindowFactory is null.");
		}
		AbstractBrowserWindow window = wf.getExistingWindow(windowId);
		FramePanel framePanel = null;
		if (window != null) {
			framePanel = window.getTopFramePanel();
			if (framePanel == null) {
				throw new IllegalStateException("Window with ID " + windowId + " exists but its top frame is null.");
			}
		} else {
			framePanel = FramePanelFactorySource.getInstance().getActiveFactory().createFramePanel(windowId);
			framePanel.setOpenerFrame(openerFrame);
		}
		this.framePanel = framePanel;
		// Starts out as progress window.
		// We allow documents to override window properties, but
		// it can also be the case that such methods as alert() are
		// invoked while the document loads.
		if (window != null) {
			this.browserWindow = window;
			this.progressWindow = null;
			this.launched = true;
		} else {
			AbstractBrowserWindow newWindow = wf.createWindow(this.windowId, properties, this);
			this.browserWindow = newWindow;
			JFrame progressWindow = new ProgressWindow();
			this.progressWindow = progressWindow;
			// Pack to use preferred sizes
			progressWindow.pack();
			// Then resize
			progressWindow.setSize(new Dimension(400, progressWindow.getHeight()));
			progressWindow.setLocationByPlatform(true);
			progressWindow.setVisible(true);
			progressWindow.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					if (!launched) {
						if (logger.isInfoEnabled()) {
							logger.info(
									"NavigatorWindowImpl(): Disposing browserWindow due to progress window getting closed.");
						}
						browserWindow.dispose();
					}
				}
			});
		}
	}

	/**
	 * Checks if is closed.
	 *
	 * @return true, if is closed
	 */
	public boolean isClosed() {
		return !this.browserWindow.isDisplayable();
	}

	/**
	 * Gets the frame panel.
	 *
	 * @return the frame panel
	 */
	public FramePanel getFramePanel() {
		return this.framePanel;
	}

	/**
	 * Reset as navigator.
	 *
	 * @param overridingProperties
	 *            the overriding properties
	 */
	void resetAsNavigator(Properties overridingProperties) {
		// Invoke in GUI thread
		if (this.launched) {
			return;
		}
		this.launched = true;
		if (this.progressWindow != null) {
			if (!progressWindow.isDisplayable()) {
				if (logger.isInfoEnabled()) {
					logger.info(
							"resetAsNavigator(): Progress window is not displayable, so it must have been closed; cancelling operation.");
				}
				this.browserWindow.dispose();
				return;
			}
			this.progressWindow.dispose();
		}
		AbstractBrowserWindow window = this.browserWindow;
		if (!window.isVisible()) {
			window.setVisible(true);
		}
		window.toFront();

		// Come up with combination properties object
		if (overridingProperties != null) {
			Properties original = this.requestedProperties;
			if (original == null) {
				original = new Properties();
			}
			original.putAll(overridingProperties);
			WindowFactory wf = windowFactory;
			if (wf == null) {
				throw new IllegalStateException("Global WindowFactory is null.");
			}
			wf.overrideProperties(window, original);
		}

		// Initialize title
		if (window != null) {
			NavigationEntry currentEntry = this.getCurrentNavigationEntry();
			if (currentEntry != null) {
				String title = currentEntry.getTitle();
				if (title == null) {
					title = Urls.getNoRefForm(currentEntry.getUrl());
				}
				((Frame) window).setTitle(title);
			}
			
			// Make visible and bring to front
			if (!window.isVisible()) {
				window.setVisible(true);
			}
			window.toFront();
		}
	}

	/**
	 * Close.
	 */
	public void close() {
		Window window = this.browserWindow;
		if (window != null) {
			window.dispose();
		}
		Window pw = this.progressWindow;
		if (pw != null) {
			pw.dispose();
		}
	}

	/**
	 * Update pre navigation progress.
	 *
	 * @param event
	 *            the event
	 */
	public void updatePreNavigationProgress(NavigatorProgressEvent event) {
		Object window = this.progressWindow;
		if (window instanceof ProgressWindow) {
			ProgressWindow pw = (ProgressWindow) window;
			pw.updateProgress(event);
		}
	}

	/**
	 * Creates the from window features.
	 *
	 * @param openerFrame
	 *            the opener frame
	 * @param windowId
	 *            the window id
	 * @param windowFeatures
	 *            Window features formatted as in the window.open() method of
	 *            Javascript.
	 * @return the navigator window impl
	 */
	public static NavigatorWindowImpl createFromWindowFeatures(NavigatorFrame openerFrame, String windowId,
			String windowFeatures) {
		// Transform into properties file format.
		return new NavigatorWindowImpl(openerFrame, windowId, getPropertiesFromWindowFeatures(windowFeatures));
	}

	/**
	 * Gets the properties from window features.
	 *
	 * @param windowFeatures
	 *            the window features
	 * @return the properties from window features
	 */
	public static Properties getPropertiesFromWindowFeatures(String windowFeatures) {
		String lineBreak = System.getProperty("line.separator");
		StringBuffer buffer = new StringBuffer();
		StringTokenizer tok = new StringTokenizer(windowFeatures, ",");
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken();
			buffer.append(token);
			buffer.append(lineBreak);
		}
		Properties props = new Properties();
		byte[] bytes = buffer.toString().getBytes(StandardCharsets.UTF_8);
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		try {
			props.load(in);
		} catch (IOException ioe) {
			// impossible
			logger.log(Level.ERROR, "unexpected", ioe);
		}
		return props;
	}

	/**
	 * Navigate.
	 *
	 * @param urlOrPath
	 *            the url or path
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	public void navigate(String urlOrPath) throws MalformedURLException {
		this.framePanel.navigate(urlOrPath);
	}

	/**
	 * Navigate.
	 *
	 * @param url
	 *            the url
	 * @param method
	 *            the method
	 * @param paramInfo
	 *            the param info
	 */
	public void navigate(URL url, String method, ParameterInfo paramInfo) {
		this.framePanel.navigate(url, method, paramInfo, TargetType.SELF, RequestType.PROGRAMMATIC);
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
		ExtensionManager.getInstance().handleError(frame, response, exception);
		// Also inform as if document rendering.
		this.handleDocumentRendering(frame, response, null);
	}

	/** The latest accessed frame. */
	private volatile NavigatorFrame latestAccessedFrame = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.gui.WindowCallback#handleDocumentAccess(org.lobobrowser.
	 * ua .NavigatorFrame, org.lobobrowser.clientlet.ClientletResponse)
	 */
	@Override
	public void handleDocumentAccess(final NavigatorFrame frame, ClientletResponse response) {
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

	/**
	 * Handle document access.
	 *
	 * @param frame
	 *            the frame
	 * @param response
	 *            the response
	 * @param okToAddToNavigationList
	 *            the ok to add to navigation list
	 */
	public void handleDocumentAccess(NavigatorFrame frame, ClientletResponse response,
			boolean okToAddToNavigationList) {
		this.handleDocumentAccess(frame, response);
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
	 * @see org.lobobrowser.ua.NavigatorWindow#dispose()
	 */
	@Override
	public void dispose() {
		this.progressWindow.dispose();
		this.browserWindow.dispose();
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
					NavigatorWindowImpl.this.handleDocumentRenderingImpl(frame, response, content);
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
	private String getWindowTitle(ClientletResponse response, ComponentContent content) {
		String title = content == null ? null : content.getTitle();
		if (title == null) {
			title = response == null ? "" : Urls.getNoRefForm(response.getResponseURL());
		}
		return title;
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
			Object window = this.browserWindow;
			if (window != null) {
				((Frame) window).setTitle(title);
			}
		}
		final NavigatorWindowEvent event = new NavigatorWindowEvent(this, NavigatorEventType.DOCUMENT_RENDERING, frame,
				response);
		latestAccessedFrame = event.getNavigatorFrame();
		if (!EVENT.fireEvent(event)) {
			logger.warn("handleDocumentRendering(): Did not deliver event to any window: " + event);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.gui.WindowCallback#getComponent()
	 */
	@Override
	public Component getComponent() {
		// Probably for dialogs
		if (this.launched) {
			return this.browserWindow;
		} else {
			return this.progressWindow;
		}
	}

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

	/**
	 * To front.
	 */
	public void toFront() {
		this.browserWindow.toFront();
	}

	/**
	 * To back.
	 */
	public void toBack() {
		this.browserWindow.toBack();
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

	/**
	 * Status updated.
	 *
	 * @param clientletFrame
	 *            the clientlet frame
	 * @param value
	 *            the value
	 */
	public void statusUpdated(final NavigatorFrame clientletFrame, final String value) {
		final NavigatorWindowEvent event = new NavigatorWindowEvent(NavigatorWindowImpl.this,
				NavigatorEventType.STATUS_UPDATED, clientletFrame, value, RequestType.NONE);
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
	 * Default status updated.
	 *
	 * @param clientletFrame
	 *            the clientlet frame
	 * @param value
	 *            the value
	 */
	public void defaultStatusUpdated(final NavigatorFrame clientletFrame, final String value) {
		final NavigatorWindowEvent event = new NavigatorWindowEvent(NavigatorWindowImpl.this,
				NavigatorEventType.STATUS_UPDATED, clientletFrame, value, RequestType.NONE);
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

	/** The status. */
	private String status;

	/** The default status. */
	private String defaultStatus;

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
	 * org.lobobrowser.gui.WindowCallback#setDefaultStatus(org.lobobrowser.ua.
	 * NavigatorFrame, java.lang.String)
	 */
	@Override
	public void setDefaultStatus(NavigatorFrame frame, String value) {
		synchronized (this) {
			this.defaultStatus = value;
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
	 * @see org.lobobrowser.ua.NavigatorWindow#addAddressBarComponent(java.awt.
	 * Component)
	 */
	@Override
	public void addAddressBarComponent(Component addressBar) {
		synchronized (this) {
			this.addressBarComponents.add(addressBar);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#addMenu(java.lang.String,
	 * javax.swing.JMenu)
	 */
	@Override
	public void addMenu(String menuId, JMenu menu) {
		Map map = this.menusById;
		synchronized (this) {
			if (map.containsKey(menuId)) {
				throw new IllegalArgumentException("Menu " + menuId + " already exists.");
			}
			this.menusById.put(menuId, menu);
			this.menus.add(menu);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#getMenu(java.lang.String)
	 */
	@Override
	public JMenu getMenu(String menuId) {
		synchronized (this) {
			return this.menusById.get(menuId);
		}
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
		synchronized (this) {
			this.sharedToolbarComponents.add(toolBarComponent);
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
		synchronized (this) {
			this.statusBarComponents.add(statusBarComponent);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#addToolBar(java.awt.Component)
	 */
	@Override
	public void addToolBar(Component toolBar) {
		synchronized (this) {
			this.toolBars.add(toolBar);
		}
	}

	// public void addItemToSharedMenu(JMenuItem menuItem) {
	// synchronized(this) {
	// this.sharedMenuItems.add(menuItem);
	// }
	// }

	/** The event. */
	private final EventDispatch2 EVENT = new LocalEventDispatch();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#addNavigatorWindowListener(org.
	 * lobobrowser .ua.NavigatorWindowListener)
	 */
	@Override
	public void addNavigatorWindowListener(final NavigatorWindowListener listener) {
		EVENT.addListener(listener);
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
	 * Gets the address bar components.
	 *
	 * @return the address bar components
	 */
	public Collection<Component> getAddressBarComponents() {
		return addressBarComponents;
	}

	/**
	 * Gets the menus.
	 *
	 * @return the menus
	 */
	public Collection<JMenu> getMenus() {
		return this.menus;
	}

	// public Collection<JMenuItem> getSharedMenuItems() {
	// return sharedMenuItems;
	// }

	/**
	 * Gets the shared toolbar components.
	 *
	 * @return the shared toolbar components
	 */
	public Collection<Component> getSharedToolbarComponents() {
		return sharedToolbarComponents;
	}

	/**
	 * Gets the status bar components.
	 *
	 * @return the status bar components
	 */
	public Collection<Component> getStatusBarComponents() {
		return statusBarComponents;
	}

	/**
	 * Gets the tool bars.
	 *
	 * @return the tool bars
	 */
	public Collection<Component> getToolBars() {
		return toolBars;
	}

	/**
	 * Gets the component lock.
	 *
	 * @return the component lock
	 */
	public Object getComponentLock() {
		return this;
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
	 * @see org.lobobrowser.ua.NavigatorWindow#getBackNavigationEntries()
	 */
	@Override
	public NavigationEntry[] getBackNavigationEntries() {
		return this.framePanel.getBackNavigationEntries();
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
	 * @see org.lobobrowser.ua.NavigatorWindow#getCurrentNavigationEntry()
	 */
	@Override
	public NavigationEntry getCurrentNavigationEntry() {
		return this.framePanel.getCurrentNavigationEntry();
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

	// public Frame getAwtFrame() {
	// Window window = this.window;
	// return window instanceof Frame ? (Frame) window : null;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.ua.NavigatorWindow#getAwtWindow()
	 */
	@Override
	public Window getAwtWindow() {
		// For dialogs
		if (this.launched) {
			return this.browserWindow;
		} else {
			return this.progressWindow;
		}
	}
}
