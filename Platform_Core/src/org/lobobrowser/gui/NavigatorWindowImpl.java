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
package org.lobobrowser.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private static final Logger logger = Logger
			.getLogger(NavigatorWindowImpl.class.getName());
	private static final int HGAP = 4;
	private static final int VGAP = 2;

	private final FramePanel framePanel;
	private final Properties requestedProperties;
	private final String windowId;
	private final Window progressWindow;
	private final AbstractBrowserWindow browserWindow;
	private final Map<String, JMenu> menusById = new HashMap<String, JMenu>();
	private final Collection<JMenu> menus = new LinkedList<JMenu>();
	// private final Collection<JMenuItem> sharedMenuItems = new
	// LinkedList<JMenuItem>();
	private final Collection<Component> addressBarComponents = new LinkedList<Component>();
	private final Collection<Component> sharedToolbarComponents = new LinkedList<Component>();
	private final Collection<Component> statusBarComponents = new LinkedList<Component>();
	private final Collection<Component> toolBars = new LinkedList<Component>();

	private volatile boolean launched = false;
	private volatile boolean disposingProgressWindow = false;

	private static volatile WindowFactory windowFactory = DefaultWindowFactory
			.getInstance();

	/**
	 * Changes the {@link WindowFactory} that is used to create browser windows.
	 */
	public static void setWindowFactory(WindowFactory wf) {
		windowFactory = wf;
	}

	/**
	 * Constructs a PlatformWindowContextImpl. It starts out by showing a
	 * progress window. Later a new browser window is obtained given the
	 * windowId, or created.
	 */
	public NavigatorWindowImpl(NavigatorFrame openerFrame, String windowId,
			Properties properties) {
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
				throw new IllegalStateException("Window with ID " + windowId
						+ " exists but its top frame is null.");
			}
		} else {
			framePanel = FramePanelFactorySource.getInstance()
					.getActiveFactory().createFramePanel(windowId);
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
			AbstractBrowserWindow newWindow = wf.createWindow(this.windowId,
					properties, this);
			this.browserWindow = newWindow;
			JFrame progressWindow = new ProgressWindow();
			this.progressWindow = progressWindow;
			// Pack to use preferred sizes
			progressWindow.pack();
			// Then resize
			progressWindow.setSize(new java.awt.Dimension(400, progressWindow
					.getHeight()));
			progressWindow.setLocationByPlatform(true);
			progressWindow.setVisible(true);
			progressWindow.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					if (!launched) {
						if (logger.isLoggable(Level.INFO)) {
							logger.info("NavigatorWindowImpl(): Disposing browserWindow due to progress window getting closed.");
						}
						browserWindow.dispose();
					}
				}
			});
		}
	}

	public boolean isClosed() {
		return !this.browserWindow.isDisplayable();
	}

	public FramePanel getFramePanel() {
		return this.framePanel;
	}

	void resetAsNavigator(Properties overridingProperties) {
		// Invoke in GUI thread
		if (this.launched) {
			return;
		}
		this.launched = true;
		if (this.progressWindow != null) {
			if (!progressWindow.isDisplayable()) {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("resetAsNavigator(): Progress window is not displayable, so it must have been closed; cancelling operation.");
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
		if (window instanceof Frame) {
			NavigationEntry currentEntry = this.getCurrentNavigationEntry();
			if (currentEntry != null) {
				String title = currentEntry.getTitle();
				if (title == null) {
					title = Urls.getNoRefForm(currentEntry.getUrl());
				}
				((Frame) window).setTitle(title);
			}
		}
		// Make visible and bring to front
		if (!window.isVisible()) {
			window.setVisible(true);
		}
		window.toFront();
	}

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

	public void updatePreNavigationProgress(NavigatorProgressEvent event) {
		Object window = this.progressWindow;
		if (window instanceof ProgressWindow) {
			ProgressWindow pw = (ProgressWindow) window;
			pw.updateProgress(event);
		}
	}

	/**
	 * @param windowFeatures
	 *            Window features formatted as in the window.open() method of
	 *            Javascript.
	 */
	public static NavigatorWindowImpl createFromWindowFeatures(
			NavigatorFrame openerFrame, String windowId, String windowFeatures) {
		// Transform into properties file format.
		return new NavigatorWindowImpl(openerFrame, windowId,
				getPropertiesFromWindowFeatures(windowFeatures));
	}

	public static Properties getPropertiesFromWindowFeatures(
			String windowFeatures) {
		String lineBreak = System.getProperty("line.separator");
		StringBuffer buffer = new StringBuffer();
		StringTokenizer tok = new StringTokenizer(windowFeatures, ",");
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken();
			buffer.append(token);
			buffer.append(lineBreak);
		}
		Properties props = new Properties();
		byte[] bytes = buffer.toString().getBytes();
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		try {
			props.load(in);
		} catch (IOException ioe) {
			// impossible
			logger.log(Level.SEVERE, "unexpected", ioe);
		}
		return props;
	}

	public void navigate(String urlOrPath)
			throws java.net.MalformedURLException {
		this.framePanel.navigate(urlOrPath);
	}

	public void navigate(java.net.URL url, String method,
			ParameterInfo paramInfo) {
		this.framePanel.navigate(url, method, paramInfo, TargetType.SELF,
				RequestType.PROGRAMMATIC);
	}

	public void handleError(NavigatorFrame frame, ClientletResponse response,
			Throwable exception) {
		ExtensionManager.getInstance().handleError(frame, response, exception);
		// Also inform as if document rendering.
		this.handleDocumentRendering(frame, response, null);
	}

	private volatile NavigatorFrame latestAccessedFrame = null;

	public void handleDocumentAccess(final NavigatorFrame frame,
			ClientletResponse response) {
		final NavigatorWindowEvent event = new NavigatorWindowEvent(this,
				NavigatorEventType.DOCUMENT_ACCESSED, frame, response);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				EVENT.fireEvent(event);
			}
		});
	}

	public void handleDocumentAccess(NavigatorFrame frame,
			ClientletResponse response, boolean okToAddToNavigationList) {
		this.handleDocumentAccess(frame, response);
	}

	public boolean canCopy() {
		return this.framePanel.canCopy();
	}

	public boolean canReload() {
		return this.framePanel.canReload();
	}

	public boolean copy() {
		return this.framePanel.copy();
	}

	public UserAgent getUserAgent() {
		return org.lobobrowser.request.UserAgentImpl.getInstance();
	}

	public void dispose() {
		this.progressWindow.dispose();
		this.browserWindow.dispose();
	}

	public boolean reload() {
		this.framePanel.reload();
		return true;
	}

	public boolean stop() {
		org.lobobrowser.request.RequestEngine.getInstance().cancelAllRequests();
		return true;
	}

	public void handleDocumentRendering(final NavigatorFrame frame,
			final ClientletResponse response, final ComponentContent content) {
		if (EventQueue.isDispatchThread()) {
			this.handleDocumentRenderingImpl(frame, response, content);
		} else {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					NavigatorWindowImpl.this.handleDocumentRenderingImpl(frame,
							response, content);
				}
			});
		}
	}

	private String getWindowTitle(ClientletResponse response,
			ComponentContent content) {
		String title = content == null ? null : content.getTitle();
		if (title == null) {
			title = response == null ? "" : Urls.getNoRefForm(response
					.getResponseURL());
		}
		return title;
	}

	private void handleDocumentRenderingImpl(final NavigatorFrame frame,
			ClientletResponse response, ComponentContent content) {
		if (frame == this.framePanel) {
			String title = this.getWindowTitle(response, content);
			Object window = this.browserWindow;
			if (window instanceof Frame) {
				((Frame) window).setTitle(title);
			}
		}
		final NavigatorWindowEvent event = new NavigatorWindowEvent(this,
				NavigatorEventType.DOCUMENT_RENDERING, frame, response);
		latestAccessedFrame = event.getNavigatorFrame();
		if (!EVENT.fireEvent(event)) {
			logger.warning("handleDocumentRendering(): Did not deliver event to any window: "
					+ event);
		}
	}

	public void updateProgress(final NavigatorProgressEvent event) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				EVENT.fireEvent(event);
			}
		});
	}

	public Component getComponent() {
		// Probably for dialogs
		if (this.launched) {
			return this.browserWindow;
		} else {
			return this.progressWindow;
		}
	}

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

	public void toFront() {
		this.browserWindow.toFront();
	}

	public void toBack() {
		this.browserWindow.toBack();
	}

	public NavigatorFrame getTopFrame() {
		return this.framePanel;
	}

	public void statusUpdated(final NavigatorFrame clientletFrame,
			final String value) {
		final NavigatorWindowEvent event = new NavigatorWindowEvent(
				NavigatorWindowImpl.this, NavigatorEventType.STATUS_UPDATED,
				clientletFrame, value, RequestType.NONE);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				EVENT.fireEvent(event);
			}
		});
	}

	public void defaultStatusUpdated(final NavigatorFrame clientletFrame,
			final String value) {
		final NavigatorWindowEvent event = new NavigatorWindowEvent(
				NavigatorWindowImpl.this, NavigatorEventType.STATUS_UPDATED,
				clientletFrame, value, RequestType.NONE);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				EVENT.fireEvent(event);
			}
		});
	}

	private String status;
	private String defaultStatus;

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

	public void setDefaultStatus(NavigatorFrame frame, String value) {
		synchronized (this) {
			this.defaultStatus = value;
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

	public String getStatus() {
		synchronized (this) {
			return this.status;
		}
	}

	public String getDefaultStatus() {
		synchronized (this) {
			return this.defaultStatus;
		}
	}

	public void addAddressBarComponent(Component addressBar) {
		synchronized (this) {
			this.addressBarComponents.add(addressBar);
		}
	}

	public void addMenu(String menuId, JMenu menu) {
		Map map = this.menusById;
		synchronized (this) {
			if (map.containsKey(menuId)) {
				throw new IllegalArgumentException("Menu " + menuId
						+ " already exists.");
			}
			this.menusById.put(menuId, menu);
			this.menus.add(menu);
		}
	}

	public JMenu getMenu(String menuId) {
		synchronized (this) {
			return this.menusById.get(menuId);
		}
	}

	public void addSharedToolBarComponent(Component toolBarComponent) {
		synchronized (this) {
			this.sharedToolbarComponents.add(toolBarComponent);
		}
	}

	public void addStatusBarComponent(Component statusBarComponent) {
		synchronized (this) {
			this.statusBarComponents.add(statusBarComponent);
		}
	}

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

	private final EventDispatch2 EVENT = new LocalEventDispatch();

	public void addNavigatorWindowListener(
			final NavigatorWindowListener listener) {
		EVENT.addListener(listener);
	}

	public void removeNavigatorWindowListener(NavigatorWindowListener listener) {
		EVENT.removeListener(listener);
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

	public Collection<Component> getAddressBarComponents() {
		return addressBarComponents;
	}

	public Collection<JMenu> getMenus() {
		return this.menus;
	}

	// public Collection<JMenuItem> getSharedMenuItems() {
	// return sharedMenuItems;
	// }

	public Collection<Component> getSharedToolbarComponents() {
		return sharedToolbarComponents;
	}

	public Collection<Component> getStatusBarComponents() {
		return statusBarComponents;
	}

	public Collection<Component> getToolBars() {
		return toolBars;
	}

	public Object getComponentLock() {
		return this;
	}

	public Component createGlueComponent(Component wrappedComponent,
			boolean usingMaxSize) {
		return new FillerComponent(wrappedComponent, usingMaxSize);
	}

	public Component createGap() {
		return Box.createRigidArea(new Dimension(HGAP, VGAP));
	}

	public boolean goTo(NavigationEntry entry) {
		return this.framePanel.goTo(entry);
	}

	public NavigationEntry[] getBackNavigationEntries() {
		return this.framePanel.getBackNavigationEntries();
	}

	public NavigationEntry[] getForwardNavigationEntries() {
		return this.framePanel.getForwardNavigationEntries();
	}

	public NavigationEntry getCurrentNavigationEntry() {
		return this.framePanel.getCurrentNavigationEntry();
	}

	public boolean hasSource() {
		return this.framePanel.hasSource();
	}

	// public Frame getAwtFrame() {
	// Window window = this.window;
	// return window instanceof Frame ? (Frame) window : null;
	// }

	public Window getAwtWindow() {
		// For dialogs
		if (this.launched) {
			return this.browserWindow;
		} else {
			return this.progressWindow;
		}
	}
}
