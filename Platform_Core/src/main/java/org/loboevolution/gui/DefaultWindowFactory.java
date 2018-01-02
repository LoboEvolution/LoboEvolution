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
/*
 * Created on Mar 6, 2005
 */
package org.loboevolution.gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.WindowConstants;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.main.ExtensionManager;
import org.loboevolution.main.PlatformInit;
import org.loboevolution.request.RequestEngine;
import org.loboevolution.settings.GeneralSettings;
import org.loboevolution.ua.NavigatorWindow;
import org.loboevolution.util.EventDispatch;
import org.loboevolution.util.ID;
import org.loboevolution.util.WeakValueHashMap;

/**
 * Browser windows are created by this factory by default.
 */
public class DefaultWindowFactory implements WindowFactory {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(DefaultWindowFactory.class);

	/** The instance. */
	private static DefaultWindowFactory instance = new DefaultWindowFactory();

	/** The evt window shown. */
	public final EventDispatch evtWindowShown = new EventDispatch();

	/** The image map. */
	private final Map<Object, Object> imageMap = new WeakValueHashMap();

	/** The frames by id. */
	private final Map<Object, Object> framesById = new WeakValueHashMap();

	/** The frames. */
	private final Set<Frame> frames = new HashSet<Frame>();

	/** The general settings. */
	private final GeneralSettings generalSettings;

	/** The exit when all windows closed. */
	private volatile boolean exitWhenAllWindowsClosed = false;

	/**
	 * Instantiates a new default window factory.
	 */
	protected DefaultWindowFactory() {
		// One way to avoid security exceptions
		this.generalSettings = GeneralSettings.getInstance();
	}

	/**
	 * Sets the exit when all windows are closed.
	 *
	 * @param flag
	 *            the new exit when all windows are closed
	 */
	public void setExitWhenAllWindowsAreClosed(boolean flag) {
		this.exitWhenAllWindowsClosed = flag;
	}

	/**
	 * Gets the instance.
	 *
	 * @return the instance
	 */
	public static DefaultWindowFactory getInstance() {
		return instance;
	}

	/**
	 * Gets the default image icon.
	 *
	 * @return the default image icon
	 */
	public ImageIcon getDefaultImageIcon() {
		return getImageIcon();
	}

	/**
	 * Gets the image icon.
	 *
	 * @return the image icon
	 */
	private ImageIcon getImageIcon() {
		ImageIcon image = null;
		try {
			Image img = Toolkit.getDefaultToolkit()
					.getImage(getClass().getResource("/org/loboevolution/images/lobo.png"));

			if (img != null) {
				image = new ImageIcon(img);
			}

		} catch (Throwable ex) {
			logger.error(ex.getMessage());
		}
		return image;
	}

	/**
	 * Gets an image icon.
	 *
	 * @param urlOrPath
	 *            A URL or path.
	 * @return the image icon
	 */
	private ImageIcon getImageIcon(String urlOrPath) {
		synchronized (this) {
			ImageIcon icon = (ImageIcon) this.imageMap.get(urlOrPath);
			if (icon == null) {
				try {
					byte[] imageBytes = RequestEngine.getInstance().loadBytes(urlOrPath);
					icon = new ImageIcon(imageBytes);
					this.imageMap.put(urlOrPath, icon);
				} catch (Exception err) {
					logger.error("getImageIcon(): Unable to load image: " + urlOrPath, err);
				}
			}
			return icon;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.gui.WindowFactory#getExistingWindow(java.lang.String)
	 */
	@Override
	public AbstractBrowserWindow getExistingWindow(String windowId) {
		if (windowId == null) {
			return null;
		}
		synchronized (this) {
			DefaultBrowserWindow window = (DefaultBrowserWindow) this.framesById.get(windowId);
			if (window != null && window.isDisplayable()) {
				return window;
			}
		}
		return null;
	}

	/**
	 * Creates a new DefaultWindow object.
	 *
	 * @param windowId
	 *            the window id
	 * @param windowContext
	 *            the window context
	 * @param hasMenuBar
	 *            the has menu bar
	 * @param hasAddressBar
	 *            the has address bar
	 * @param hasToolBar
	 *            the has tool bar
	 * @param hasStatusBar
	 *            the has status bar
	 * @return the abstract browser window
	 */
	private AbstractBrowserWindow createBaseWindow(String windowId, NavigatorWindow windowContext, boolean hasMenuBar,
			boolean hasAddressBar, boolean hasToolBar, boolean hasStatusBar) {
		final NavigatorWindowImpl pwc = (NavigatorWindowImpl) windowContext;
		synchronized (this) {
			final DefaultBrowserWindow window = new DefaultBrowserWindow(hasMenuBar, hasAddressBar, hasToolBar,
					hasStatusBar, pwc);
			if (windowId != null) {
				this.framesById.put(windowId, window);
			}
			window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			if (logger.isInfoEnabled()) {
				logger.info("createBaseWindow(): Adding window listener: window=" + window + ",windowId=" + windowId);
			}
			window.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					super.windowClosing(e);
					if (!window.isBoundsAssigned()) {
						if (logger.isInfoEnabled()) {
							logger.info("windowClosing(): Saving general settings: bounds=" + window.getBounds());
						}
						GeneralSettings settings = generalSettings;
						settings.setInitialWindowBounds(window.getBounds());
						settings.save();
					}
					ExtensionManager.getInstance().shutdownExtensionsWindow(pwc);
				}

				@Override
				public void windowClosed(WindowEvent e) {
					super.windowClosed(e);
					Set frames = DefaultWindowFactory.this.frames;
					synchronized (DefaultWindowFactory.this) {
						if (logger.isInfoEnabled()) {
							logger.info("windowClosed(): frames.size()=" + frames.size() + ",exitWhenAllWindowsClosed="
									+ exitWhenAllWindowsClosed);
						}
						frames.remove(window);
						if (frames.size() == 0 && exitWhenAllWindowsClosed) {
							logger.warn("Exiting JVM because all windows are now closed!");
							PlatformInit.shutdown();
						}
					}
				}

				@Override
				public void windowOpened(WindowEvent e) {
					evtWindowShown.fireEvent(null);
				}
			});
			this.frames.add(window);
			return window;
		}
	}

	/**
	 * Checks if is property true.
	 *
	 * @param properties
	 *            the properties
	 * @param name
	 *            the name
	 * @param defaultValue
	 *            the default value
	 * @return true, if is property true
	 */
	private final boolean isPropertyTrue(Properties properties, String name, boolean defaultValue) {
		if (properties == null) {
			return defaultValue;
		}
		String value = properties.getProperty(name);
		if (value == null) {
			return defaultValue;
		}
		return "1".equals(value) || "yes".equalsIgnoreCase(value);
	}

	/**
	 * Creates, sizes a browser window, and registeres listeners that allow the
	 * platform to persist window settings and shut itself down when all windows
	 * are closed.
	 *
	 * @param windowId
	 *            the window id
	 * @param properties
	 *            the properties
	 * @param windowContext
	 *            the window context
	 * @return the abstract browser window
	 */
	@Override
	public AbstractBrowserWindow createWindow(String windowId, Properties properties, NavigatorWindow windowContext) {
		String widthText = properties == null ? null : properties.getProperty("width");
		String heightText = properties == null ? null : properties.getProperty("height");
		boolean defaultValue = widthText == null && heightText == null;
		boolean hasMenuBar = this.isPropertyTrue(properties, "menubar", defaultValue);
		boolean hasToolBar = this.isPropertyTrue(properties, "toolbar", defaultValue);
		boolean hasAddressBar = this.isPropertyTrue(properties, "location", defaultValue);
		boolean hasStatusBar = this.isPropertyTrue(properties, "status", defaultValue);
		boolean isResizable = this.isPropertyTrue(properties, "resizable", defaultValue);
		String iconText = properties == null ? null : properties.getProperty("icon");
		String title = properties == null ? null : properties.getProperty("title");
		int width = -1;
		int height = -1;
		if (widthText != null) {
			try {
				width = Integer.parseInt(widthText);
			} catch (NumberFormatException nfe) {
				logger.log(Level.WARN, "PlatformWindowContextImpl(): Unable to parse window width.", nfe);
			}
		}
		if (heightText != null) {
			try {
				height = Integer.parseInt(heightText);
			} catch (NumberFormatException nfe) {
				logger.log(Level.WARN, "PlatformWindowContextImpl(): Unable to parse window height.", nfe);
			}
		}
		final AbstractBrowserWindow window = this.createBaseWindow(windowId, windowContext, hasMenuBar, hasAddressBar,
				hasToolBar, hasStatusBar);
		window.setTitle(title);
		Rectangle windowBounds = this.generalSettings.getInitialWindowBounds();
		if (width != -1 || height != -1) {
			if (width != -1) {
				windowBounds.width = width;
			}
			if (height != -1) {
				windowBounds.height = height;
			}
			window.setBoundsAssigned(true);
		}
		ImageIcon icon = null;
		if (iconText != null) {
			icon = this.getImageIcon(iconText);
			if (icon == null) {
				icon = this.getDefaultImageIcon();
			}
		} else {
			icon = this.getDefaultImageIcon();
		}
		if (icon != null) {
			window.setIconImage(icon.getImage());
		}
		Dimension windowSize = windowBounds.getSize();
		Rectangle maxBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int maxX = maxBounds.width - windowSize.width;
		int maxY = maxBounds.height - windowSize.height;
		int x = ID.random(0, maxX);
		int y = ID.random(0, maxY);
		window.setBounds(x, y, windowSize.width, windowSize.height);
		window.setResizable(isResizable);
		return window;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.gui.WindowFactory#overrideProperties(org.loboevolution.gui.
	 * AbstractBrowserWindow, java.util.Properties)
	 */
	@Override
	public void overrideProperties(AbstractBrowserWindow window, Properties properties) {
		String widthText = properties.getProperty("width");
		String heightText = properties.getProperty("height");
		boolean defaultValue = widthText == null && heightText == null;
		boolean isResizable = this.isPropertyTrue(properties, "resizable", defaultValue);
		properties.getProperty("icon");
		String title = properties.getProperty("title");
		int width = -1;
		int height = -1;
		if (widthText != null) {
			try {
				width = Integer.parseInt(widthText);
			} catch (NumberFormatException nfe) {
				logger.log(Level.WARN, "PlatformWindowContextImpl(): Unable to parse window width.", nfe);
			}
		}
		if (heightText != null) {
			try {
				height = Integer.parseInt(heightText);
			} catch (NumberFormatException nfe) {
				logger.log(Level.WARN, "PlatformWindowContextImpl(): Unable to parse window height.", nfe);
			}
		}
		window.setResizable(isResizable);
		window.setTitle(title);
		window.setSize(width == -1 ? window.getWidth() : width, height == -1 ? window.getHeight() : height);
	}

}
