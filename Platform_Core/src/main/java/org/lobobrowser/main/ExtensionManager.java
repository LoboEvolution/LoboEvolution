/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.main;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletRequest;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.security.GenericLocalPermission;
import org.lobobrowser.ua.NavigationEvent;
import org.lobobrowser.ua.NavigationVetoException;
import org.lobobrowser.ua.NavigatorEventType;
import org.lobobrowser.ua.NavigatorExceptionEvent;
import org.lobobrowser.ua.NavigatorFrame;
import org.lobobrowser.ua.NavigatorWindow;
import org.lobobrowser.util.JoinableTask;

/**
 * Manages platform extensions.
 */
public class ExtensionManager {

    /** The Constant logger. */
    private static final Logger logger = Logger
            .getLogger(ExtensionManager.class.getName());

    /** The Constant instance. */
    private static final ExtensionManager instance = new ExtensionManager();

    /** The Constant EXT_DIR_NAME. */
    private static final String EXT_DIR_NAME = "ext";

    // Note: We do not synchronize around the extensions collection,
    // given that it is fully built in the constructor.
    /** The extension by id. */
    private final Map<String, Extension> extensionById = new HashMap<String, Extension>();

    /** The extensions. */
    private final SortedSet<Extension> extensions = new TreeSet<Extension>();

    /** The libraries. */
    private final ArrayList<Extension> libraries = new ArrayList<Extension>();

    /**
     * Instantiates a new extension manager.
     */
    private ExtensionManager() {
        this.createExtensions();
    }

    /** Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
    public static ExtensionManager getInstance() {
        // This security check should be enough, provided
        // ExtensionManager instances are not retained.
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
        }
        return instance;
    }

    /**
     * Creates the extensions.
     */
    private void createExtensions() {
        File[] extDirs;
        File[] extFiles;
        String extDirsProperty = System.getProperty("ext.dirs");
        if (extDirsProperty == null) {
            File appDir = PlatformInit.getInstance().getApplicationDirectory();
            extDirs = new File[] {new File(appDir, EXT_DIR_NAME)};
        } else {
            StringTokenizer tok = new StringTokenizer(extDirsProperty, ",");
            ArrayList<File> extDirsList = new ArrayList<File>();
            while (tok.hasMoreTokens()) {
                String token = tok.nextToken();
                extDirsList.add(new File(token.trim()));
            }
            extDirs = extDirsList.toArray(new File[0]);
        }
        String extFilesProperty = System.getProperty("ext.files");
        if (extFilesProperty == null) {
            extFiles = new File[0];
        } else {
            StringTokenizer tok = new StringTokenizer(extFilesProperty, ",");
            ArrayList<File> extFilesList = new ArrayList<File>();
            while (tok.hasMoreTokens()) {
                String token = tok.nextToken();
                extFilesList.add(new File(token.trim()));
            }
            extFiles = extFilesList.toArray(new File[0]);
        }
        this.createExtensions(extDirs, extFiles);
    }

    /**
     * Adds the extension.
     *
     * @param file
     *            the file
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void addExtension(File file) throws IOException {
        if (!file.exists()) {
            logger.warning("addExtension(): File " + file + " does not exist.");
            return;
        }
        Extension ei = new Extension(file);
        this.extensionById.put(ei.getId(), ei);
        if (ei.isLibraryOnly()) {
            if (logger.isLoggable(Level.INFO)) {
                logger.info("createExtensions(): Loaded library (no lobo-extension.properties): "
                        + ei);
            }
            libraries.add(ei);
        } else {
            if (logger.isLoggable(Level.INFO)) {
                logger.info("createExtensions(): Loaded extension: " + ei);
            }
            extensions.add(ei);
        }
    }

    /**
     * Creates the extensions.
     *
     * @param extDirs
     *            the ext dirs
     * @param extFiles
     *            the ext files
     */
    private void createExtensions(File[] extDirs, File[] extFiles) {
        Collection<Extension> extensions = this.extensions;
        Collection<Extension> libraries = this.libraries;
        Map<String, Extension> extensionById = this.extensionById;
        extensions.clear();
        libraries.clear();
        extensionById.clear();
        for (File extDir : extDirs) {
            if (!extDir.exists()) {
                logger.warning("createExtensions(): Directory '" + extDir
                        + "' not found.");
                if (PlatformInit.getInstance().isCodeLocationDirectory()) {
                    logger.warning("createExtensions(): The application code location is a directory, which means the application is probably being run from an IDE. Additional setup is required. Please refer to README.txt file.");
                }
                continue;
            }
            File[] extRoots = extDir.listFiles(new ExtFileFilter());
            if ((extRoots == null) || (extRoots.length == 0)) {
                logger.warning("createExtensions(): No potential extensions found in "
                        + extDir + " directory.");
                continue;
            }
            for (File file : extRoots) {
                try {
                    this.addExtension(file);
                } catch (IOException ioe) {
                    logger.log(Level.WARNING,
                            "createExtensions(): Unable to load '" + file
                            + "'.", ioe);
                }
            }
        }
        for (File file : extFiles) {
            try {
                this.addExtension(file);
            } catch (IOException ioe) {
                logger.log(Level.WARNING,
                        "createExtensions(): Unable to load '" + file + "'.",
                        ioe);
            }
        }

        if (this.extensionById.size() == 0) {
            logger.warning("createExtensions(): No extensions found. This is indicative of a setup error. Extension directories scanned are: "
                    + Arrays.asList(extDirs) + ".");
        }

        // Get the system class loader
        ClassLoader rootClassLoader = this.getClass().getClassLoader();

        // Create class loader for extension "libraries"
        ArrayList<URL> libraryURLCollection = new ArrayList<URL>();
        for (Extension ei : libraries) {
            try {
                libraryURLCollection.add(ei.getCodeSource());
            } catch (MalformedURLException thrown) {
                logger.log(Level.SEVERE, "createExtensions()", thrown);
            }
        }
        if (logger.isLoggable(Level.INFO)) {
            logger.info("createExtensions(): Creating library class loader with URLs=["
                    + libraryURLCollection + "].");
        }
        ClassLoader librariesCL = new URLClassLoader(
                libraryURLCollection.toArray(new URL[0]), rootClassLoader);

        // Initialize class loader in each extension, using librariesCL as
        // the parent class loader. Extensions are initialized in parallel.
        Collection<JoinableTask> tasks = new ArrayList<JoinableTask>();
        PlatformInit pm = PlatformInit.getInstance();
        for (Extension ei : extensions) {
            final ClassLoader pcl = librariesCL;
            final Extension fei = ei;
            // Initialize rest of them in parallel.
            JoinableTask task = new JoinableTask() {
                @Override
                public void execute() {
                    try {
                        fei.initClassLoader(pcl);
                    } catch (Exception err) {
                        logger.log(Level.WARNING,
                                "Unable to create class loader for " + fei
                                + ".", err);
                    }
                }

                @Override
                public String toString() {
                    return "createExtensions:" + fei;
                }
            };
            tasks.add(task);
            pm.scheduleTask(task);
        }

        // Join tasks to make sure all extensions are
        // initialized at this point.
        for (JoinableTask task : tasks) {
            try {
                task.join();
            } catch (InterruptedException ie) {
                // ignore
            }
        }
    }

    /**
     * Gets the class loader.
     *
     * @param extensionId
     *            the extension id
     * @return the class loader
     */
    public ClassLoader getClassLoader(String extensionId) {
        Extension ei = this.extensionById.get(extensionId);
        if (ei != null) {
            return ei.getClassLoader();
        } else {
            return null;
        }
    }

    /**
     * Inits the extensions.
     */
    public void initExtensions() {
        Collection<JoinableTask> tasks = new ArrayList<JoinableTask>();
        PlatformInit pm = PlatformInit.getInstance();
        for (Extension ei : this.extensions) {
            final Extension fei = ei;
            JoinableTask task = new JoinableTask() {
                @Override
                public void execute() {
                    fei.initExtension();
                }

                @Override
                public String toString() {
                    return "initExtensions:" + fei;
                }
            };
            tasks.add(task);
            pm.scheduleTask(task);
        }
        // Join all tasks before returning
        for (JoinableTask task : tasks) {
            try {
                task.join();
            } catch (InterruptedException ie) {
                // ignore
            }
        }
    }

    /**
     * Inits the extensions window.
     *
     * @param context
     *            the context
     */
    public void initExtensionsWindow(final NavigatorWindow context) {
        // This must be done sequentially due to menu lookup infrastructure.
        for (Extension ei : this.extensions) {
            try {
                ei.initExtensionWindow(context);
            } catch (Exception err) {
                logger.log(
                        Level.SEVERE,
                        "initExtensionsWindow(): Extension could not properly initialize a new window.",
                        err);
            }
        }
    }

    /**
     * Shutdown extensions window.
     *
     * @param context
     *            the context
     */
    public void shutdownExtensionsWindow(final NavigatorWindow context) {
        // This must be done sequentially due to menu lookup infrastructure.
        for (Extension ei : this.extensions) {
            try {
                ei.shutdownExtensionWindow(context);
            } catch (Exception err) {
                logger.log(
                        Level.SEVERE,
                        "initExtensionsWindow(): Extension could not properly process window shutdown.",
                        err);
            }
        }
    }

    /**
     * Gets the clientlet.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @return the clientlet
     */
    public Clientlet getClientlet(ClientletRequest request,
            ClientletResponse response) {
        Collection<Extension> extensions = this.extensions;
        // Call all plugins once to see if they can select the response.
        for (Extension ei : extensions) {
            try {
                Clientlet clientlet = ei.getClientlet(request, response);
                if (clientlet != null) {
                    return clientlet;
                }
            } catch (Exception thrown) {
                logger.log(Level.SEVERE, "getClientlet(): Extension " + ei
                        + " threw exception.", thrown);
            }
        }

        // None handled it. Call the last resort handlers in reverse order.
        for (Extension ei : (Collection<Extension>) org.lobobrowser.util.CollectionUtilities
                .reverse(extensions)) {
            try {
                Clientlet clientlet = ei.getLastResortClientlet(request,
                        response);
                if (clientlet != null) {
                    return clientlet;
                }
            } catch (Exception thrown) {
                logger.log(Level.SEVERE, "getClientlet(): Extension " + ei
                        + " threw exception.", thrown);
            }
        }
        return null;
    }

    /**
     * Handle error.
     *
     * @param frame
     *            the frame
     * @param response
     *            the response
     * @param exception
     *            the exception
     */
    public void handleError(NavigatorFrame frame,
            final ClientletResponse response, final Throwable exception) {
        final NavigatorExceptionEvent event = new NavigatorExceptionEvent(this,
                NavigatorEventType.ERROR_OCCURRED, frame, response, exception);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Collection<Extension> ext = extensions;
                // Call all plugins once to see if they can select the response.
                boolean dispatched = false;
                for (Extension ei : ext) {
                    if (ei.handleError(event)) {
                        dispatched = true;
                    }
                }
                if (!dispatched && logger.isLoggable(Level.INFO)) {
                    logger.log(Level.WARNING,
                            "No error handlers found for error that occurred while processing response=["
                                    + response + "].", exception);
                }
            }
        });
    }

    /**
     * Dispatch before navigate.
     *
     * @param event
     *            the event
     * @throws NavigationVetoException
     *             the navigation veto exception
     */
    public void dispatchBeforeNavigate(NavigationEvent event)
            throws NavigationVetoException {
        for (Extension ei : extensions) {
            try {
                ei.dispatchBeforeLocalNavigate(event);
            } catch (NavigationVetoException nve) {
                throw nve;
            } catch (Exception other) {
                logger.log(
                        Level.SEVERE,
                        "dispatchBeforeNavigate(): Extension threw an unexpected exception.",
                        other);
            }
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
    public void dispatchBeforeLocalNavigate(NavigationEvent event)
            throws NavigationVetoException {
        for (Extension ei : extensions) {
            try {
                ei.dispatchBeforeLocalNavigate(event);
            } catch (NavigationVetoException nve) {
                throw nve;
            } catch (Exception other) {
                logger.log(
                        Level.SEVERE,
                        "dispatchBeforeLocalNavigate(): Extension threw an unexpected exception.",
                        other);
            }
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
    public void dispatchBeforeWindowOpen(NavigationEvent event)
            throws NavigationVetoException {
        for (Extension ei : extensions) {
            try {
                ei.dispatchBeforeWindowOpen(event);
            } catch (NavigationVetoException nve) {
                throw nve;
            } catch (Exception other) {
                logger.log(
                        Level.SEVERE,
                        "dispatchBeforeWindowOpen(): Extension threw an unexpected exception.",
                        other);
            }
        }
    }

    /**
     * Dispatch pre connection.
     *
     * @param connection
     *            the connection
     * @return the URL connection
     */
    public URLConnection dispatchPreConnection(URLConnection connection) {
        for (Extension ei : extensions) {
            try {
                connection = ei.dispatchPreConnection(connection);
            } catch (Exception other) {
                logger.log(
                        Level.SEVERE,
                        "dispatchPreConnection(): Extension threw an unexpected exception.",
                        other);
            }
        }
        return connection;
    }

    /**
     * Dispatch post connection.
     *
     * @param connection
     *            the connection
     * @return the URL connection
     */
    public URLConnection dispatchPostConnection(URLConnection connection) {
        for (Extension ei : extensions) {
            try {
                connection = ei.dispatchPostConnection(connection);
            } catch (Exception other) {
                logger.log(
                        Level.SEVERE,
                        "dispatchPostConnection(): Extension threw an unexpected exception.",
                        other);
            }
        }
        return connection;
    }

    /**
     * The Class ExtFileFilter.
     */
    private static class ExtFileFilter implements FileFilter {

        /*
         * (non-Javadoc)
         * @see java.io.FileFilter#accept(java.io.File)
         */
        @Override
        public boolean accept(File file) {
            return file.isDirectory()
                    || file.getName().toLowerCase().endsWith(".jar");
        }
    }
}
