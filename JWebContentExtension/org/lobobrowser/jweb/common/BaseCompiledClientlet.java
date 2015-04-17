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
package org.lobobrowser.jweb.common;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.reflect.FXClassType;
import javafx.reflect.FXContext;
import javafx.reflect.FXLocal;
import javafx.reflect.FXObjectValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.WindowConstants;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

import org.lobobrowser.clientlet.AbstractComponentContent;
import org.lobobrowser.clientlet.CancelClientletException;
import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ComponentContent;
import org.lobobrowser.jweb.compilation.BuildResult;
import org.lobobrowser.jweb.compilation.JavaResponseFileManager;
import org.lobobrowser.jweb.compilation.PathManager;
import org.lobobrowser.jweb.compilation.PathRepository;
import org.lobobrowser.jweb.compilation.ResponseJavaInputFile;
import org.lobobrowser.jweb.javafx.ClientletStageDelegate;
import org.lobobrowser.ua.NavigatorProgressEvent;
import org.lobobrowser.ua.ProgressType;
import org.lobobrowser.ua.RequestType;
import org.lobobrowser.util.Urls;
import org.lobobrowser.util.io.IORoutines;

import com.sun.javafx.runtime.Entry;
import com.sun.javafx.runtime.FXObject;
import com.sun.javafx.runtime.TypeInfo;
import com.sun.javafx.runtime.sequence.Sequence;
import com.sun.javafx.runtime.sequence.Sequences;

/**
 * The Class BaseCompiledClientlet.
 */
public abstract class BaseCompiledClientlet implements Clientlet {

    /** The Constant logger. */
    private static final Logger logger = Logger
            .getLogger(BaseCompiledClientlet.class.getName());

    /** The Constant BASE_PLATFORM_CLASS_PATH. */
    private static final ArrayList<URL> BASE_PLATFORM_CLASS_PATH;

    /** The Constant SUN_BOOT_CLASS_PATH_PROPERTY. */
    private static final String SUN_BOOT_CLASS_PATH_PROPERTY = "sun.boot.class.path";

    /** The Constant STAGE_DELEGATE_MONITOR. */
    private static final Object STAGE_DELEGATE_MONITOR = new Object();

    /** The scene type. */
    private static FXClassType sceneType;

    static {
        String bootClassPath = System.getProperty(SUN_BOOT_CLASS_PATH_PROPERTY);
        if (bootClassPath == null) {
            throw new IllegalStateException("Property "
                    + SUN_BOOT_CLASS_PATH_PROPERTY + " not found.");
        }
        String pathSeparator = System.getProperty("path.separator");
        StringTokenizer tok = new StringTokenizer(bootClassPath, pathSeparator);
        ArrayList<URL> entries = new ArrayList<URL>();
        BASE_PLATFORM_CLASS_PATH = entries;
        while (tok.hasMoreTokens()) {
            String entryPath = tok.nextToken().trim();
            try {
                URL url = Urls.guessURL(entryPath);
                entries.add(url);
            } catch (MalformedURLException mfu1) {
                logger.log(Level.WARNING,
                        "<clinit>: Unable to create URL for '" + entryPath
                        + "'.", mfu1);
            }
        }
        entries.add(ClientletContext.class.getProtectionDomain()
                .getCodeSource().getLocation());
        if (logger.isLoggable(Level.INFO)) {
            logger.info("<clinit>: Platform classpath: " + entries);
        }
    }

    /**
     * Gets the scene type.
     *
     * @return the scene type
     */
    private static FXClassType getSceneType() {
        if (sceneType == null) {
            synchronized (STAGE_DELEGATE_MONITOR) {
                if (sceneType == null) {
                    sceneType = FXContext.getInstance().findClass(
                            Scene.class.getName());
                }
            }
        }
        return sceneType;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.Clientlet#process(org.lobobrowser.clientlet.
     * ClientletContext)
     */
    @Override
    public void process(ClientletContext context) throws ClientletException {
        // Context class loader already set in Extension.
        this.processImpl(context);
    }

    /**
     * Prepare window.
     *
     * @param window
     *            the window
     */
    private void prepareWindow(Window window) {
        window.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                Object source = event.getSource();
                if (source instanceof javax.swing.JFrame) {
                    // Disable JFrame on-close operations.
                    ((javax.swing.JFrame) source)
                    .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                }
            }

            @Override
            public void windowClosed(WindowEvent event) {
                Object source = event.getSource();
                // Dispose all windows on close.
                ((Window) source).dispose();
            }

        });
        if (!window.isVisible()) {
            window.setVisible(true);
        }
    }

    /**
     * Gets the component content from fx object.
     *
     * @param context
     *            the context
     * @param fxObject
     *            the fx object
     * @param binfo
     *            the binfo
     * @return the component content from fx object
     * @throws ClientletException
     *             the clientlet exception
     */
    private static ComponentContent getComponentContentFromFXObject(
            ClientletContext context, FXObject fxObject, ClassInfo binfo)
                    throws ClientletException {
        if (fxObject instanceof Scene) {
            Scene scene = (Scene) fxObject;
            Component component = ClientletStageDelegate
                    .getSceneComponent(scene);
            return new ScriptContent(component, binfo.directives,
                    binfo.sourceCode, context);
        } else if (fxObject instanceof Node) {
            Node node = (Node) fxObject;
            FXLocal.getContext().mirrorOf(node);
            FXObjectValue fxScene = getSceneType().allocate();
            Sequence<Node> contentSequence = Sequences.make(
                    TypeInfo.makeTypeInfo(node), new Node[] {node });
            fxScene.initVar("content",
                    FXLocal.getContext().mirrorOf(contentSequence));
            fxScene.initialize();
            java.awt.Component panel = ClientletStageDelegate
                    .getSceneComponent(fxScene);
            return new ScriptContent(panel, binfo.directives, binfo.sourceCode,
                    context);
        } else if (fxObject == null) {
            throw new IllegalArgumentException("fxObject is null");
        } else {
            throw new ClientletException("FXObject instances of type "
                    + fxObject.getClass().getName() + " cannot be displayed.");
        }
    }

    /**
     * Gets the actual response object.
     *
     * @param context
     *            the context
     * @param clazz
     *            the clazz
     * @param tentative
     *            the tentative
     * @return the actual response object
     * @throws ClientletException
     *             the clientlet exception
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws InvocationTargetException
     *             the invocation target exception
     * @throws NoSuchMethodException
     *             the no such method exception
     */
    private static ResponseObject getActualResponseObject(
            ClientletContext context, Class<? extends Object> clazz,
            Object tentative) throws ClientletException,
            IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        ClientletStageDelegate csd = new ClientletStageDelegate(context);
        synchronized (STAGE_DELEGATE_MONITOR) {
            // This $appletDelegate hack is required so that the Stage doesn't
            // use a JFrame.
            Stage.$appletDelegate.set(csd);
            // This $stages hack is needed so that it doesn't keep adding
            // to the collection of stages. When it finds there had been
            // some stages previously, it opens a new window. This could
            // have a side-effect in the wrap() method.
            Stage.$stages.setDefault();
            try {
                for (int level = 0; level < 3; level++) {
                    if (logger.isLoggable(Level.INFO)) {
                        logger.info("processImpl(): level=" + level
                                + "; clazz=" + clazz);
                    }
                    if (tentative instanceof Stage) {
                        Stage stage = (Stage) tentative;
                        csd.get$stage().setFromLiteral(stage);
                        csd.initialize$();
                        return new ResponseObject(csd.createComponent(), stage
                                .get$title().get(), stage);
                    } else if ((tentative instanceof Scene)
                            || (tentative instanceof Node)) {
                        break;
                    } else if (tentative instanceof FXObject) {
                        // TODO: Call initialize$?
                        Method runMethod = clazz.getMethod(
                                Entry.entryMethodName(),
                                new Class[] {Sequence.class });
                        if (runMethod == null) {
                            throw new ClientletException(
                                    "Expected to find a special run method in FXObject instance.");
                        }
                        Sequence<String> sequence = Sequences
                                .emptySequence(String.class);
                        tentative = runMethod.invoke(tentative,
                                new Object[] {sequence });
                        if (tentative == null) {
                            // Allow null return values (e.g. a page with a
                            // simple redirect)
                            tentative = new javax.swing.JPanel();
                            break;
                        }
                        clazz = tentative.getClass();
                    } else {
                        break;
                    }
                }
            } finally {
                Stage.$appletDelegate.set(null);
            }
        }
        return new ResponseObject(tentative, null, null);
    }

    /**
     * Process impl.
     *
     * @param context
     *            the context
     * @throws ClientletException
     *             the clientlet exception
     */
    private void processImpl(ClientletContext context)
            throws ClientletException {
        try {
            ClassInfo binfo = this.getClassForResponse(context);
            if (binfo == null) {
                throw new ClientletException(
                        "Unexpected: Null result from document.");
            }
            // We need to set the class loader in context before
            // we start executing compiled code.
            ClassLoader resultClassLoader = binfo.classLoader;
            Thread currentThread = Thread.currentThread();
            ClassLoader prevClassLoader = currentThread.getContextClassLoader();
            currentThread.setContextClassLoader(resultClassLoader);
            try {
                Class<? extends Object> clazz = binfo.resultClass;
                Object retValue = clazz.newInstance();
                ResponseObject ro = getActualResponseObject(context, clazz,
                        retValue);
                retValue = ro.object;
                if (retValue instanceof ComponentContent) {
                    context.setResultingContent((ComponentContent) retValue);
                } else if (retValue instanceof java.awt.Window) {
                    Window window = (java.awt.Window) retValue;
                    this.prepareWindow(window);
                    throw new CancelClientletException("Opening window.");
                } else if (retValue instanceof Component) {
                    ScriptContent content = new ScriptContent(
                            (Component) retValue, binfo.directives,
                            binfo.sourceCode, context);
                    content.setSecondaryTitle(ro.secondaryTitle);
                    context.setResultingContent(content);
                } else if (retValue instanceof FXObject) {
                    ComponentContent content = getComponentContentFromFXObject(
                            context, (FXObject) retValue, binfo);
                    context.setResultingContent(content);
                } else {
                    Class<? extends Object> retClass = retValue.getClass();
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("Methods below.");
                    Method[] methods = retClass.getMethods();
                    for (int i = 0; i < methods.length; i++) {
                        buffer.append("\r\n");
                        buffer.append("\t" + methods[i]);
                    }
                    if (logger.isLoggable(Level.INFO)) {
                        logger.info("processImpl(): Unknown object type: "
                                + buffer);
                    }
                    throw new ClientletException(
                            "Java value resulting from document is of type "
                                    + retClass.getName()
                                    + " "
                                    + getFullClassInfo(retClass)
                                    + " which is not recognized as one that can be rendered.");
                }
            } finally {
                currentThread.setContextClassLoader(prevClassLoader);
            }
        } catch (ClientletException ce) {
            throw ce;
        } catch (ClassNotFoundException cnf) {
            throw new ClientletException(
                    "Unexpected: Class not found after building document.", cnf);
        } catch (InstantiationException ie) {
            throw new ClientletException(
                    "Unable to instantiate class. Note that the class should be non-abstract and have a default constructor.",
                    ie);
        } catch (InvocationTargetException ite) {
            throw new ClientletException(
                    "Got InvactionTargetException. Target exception attached.",
                    ite.getTargetException());
        } catch (Exception other) {
            throw new ClientletException(other);
        }
    }

    /**
     * Gets the full class info.
     *
     * @param clazz
     *            the clazz
     * @return the full class info
     */
    private static String getFullClassInfo(Class<? extends Object> clazz) {
        StringBuffer buffer = new StringBuffer();
        Class<?> superclass = clazz.getSuperclass();
        boolean startParen = false;
        if (superclass != null) {
            startParen = true;
            buffer.append('(');
            buffer.append("extends " + superclass.getName());
        }
        Class[] interfaces = clazz.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            if (i == 0) {
                if (startParen) {
                    buffer.append(" implements ");
                } else {
                    startParen = true;
                    buffer.append("(implements ");
                }
            } else {
                buffer.append(',');
            }
            Class<? extends Object> inter = interfaces[i];
            buffer.append(inter.getName());
            buffer.append(' ');
            buffer.append(getFullClassInfo(inter));
            buffer.append(' ');
        }
        if (startParen) {
            buffer.append(')');
        }
        return buffer.toString();
    }

    /**
     * Checks if is reload request.
     *
     * @param requestType
     *            the request type
     * @return true, if is reload request
     */
    private static boolean isReloadRequest(RequestType requestType) {
        return (requestType == RequestType.SOFT_RELOAD)
                || (requestType == RequestType.HARD_RELOAD);
    }

    /**
     * Gets the directives.
     *
     * @param sourceCode
     *            the source code
     * @return the directives
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private Properties getDirectives(String sourceCode) throws IOException {
        Properties directives = new Properties();
        String commentStart = "/*!";
        String commentEnd = "*/";
        int commentStartIdx = sourceCode.indexOf(commentStart);
        if (commentStartIdx == -1) {
            return directives;
        }
        int commentEndIdx = sourceCode.indexOf(commentEnd, commentStartIdx);
        if (commentEndIdx == -1) {
            return directives;
        }
        String rawCommentText = sourceCode.substring(commentStartIdx
                + commentStart.length(), commentEndIdx);
        BufferedReader reader = new BufferedReader(new StringReader(
                rawCommentText));
        StringBuffer commentText = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            String newLine = line.trim();
            if (newLine.startsWith("*")) {
                newLine = newLine.substring(1);
            }
            commentText.append(newLine);
            commentText.append("\r\n");
        }
        directives.load(new StringReader(commentText.toString()));
        return directives;
    }

    /**
     * Gets the class for response.
     *
     * @param context
     *            the context
     * @return the class for response
     * @throws ClassNotFoundException
     *             the class not found exception
     * @throws ClientletException
     *             the clientlet exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private ClassInfo getClassForResponse(ClientletContext context)
            throws ClassNotFoundException, ClientletException, IOException {
        ClientletResponse response = context.getResponse();
        BuildResult buildResult;
        Object ramObject = response.getTransientCachedObject();
        if (ramObject instanceof BuildResult) {
            buildResult = (BuildResult) ramObject;
        } else {
            Object pbr = response.getPersistentCachedObject(BuildResult.class
                    .getClassLoader());
            if (pbr instanceof BuildResult) {
                // It could be of a different type.
                buildResult = (BuildResult) pbr;
            } else {
                buildResult = null;
            }
        }
        String newSourceCode;
        if (response.isFromCache()) {
            if (buildResult != null) {
                Properties prevDirectives = buildResult.directives;
                response.setNewTransientCachedObject(buildResult,
                        buildResult.getEstimatedTransientSize());
                ClassLoader systemLoader = this.getClass().getClassLoader();
                PathRepository classPathRepository = this.getPathRepository(
                        context, prevDirectives, "classpath", "classlist");
                ClassLoader finalLoader = buildResult.buildClassLoader(context,
                        response.getResponseURL(), systemLoader,
                        classPathRepository);
                return new ClassInfo(finalLoader,
                        buildResult.loadClass(finalLoader), prevDirectives,
                        buildResult.sourceCode);
            } else {
                newSourceCode = IORoutines.loadAsText(
                        response.getInputStream(), response.getCharset());
            }
        } else {
            // Not from cache, but let's see if the source code has changed.
            newSourceCode = IORoutines.loadAsText(response.getInputStream(),
                    response.getCharset());
            if ((buildResult != null)
                    && !isReloadRequest(context.getRequest().getRequestType())
                    && newSourceCode.equals(buildResult.sourceCode)) {
                if (logger.isLoggable(Level.INFO)) {
                    logger.info("getClassForResponse(): Shouldn't get content from cache, but it's the same content - not building; url="
                            + response.getResponseURL());
                }
                Properties prevDirectives = buildResult.directives;
                ClassLoader systemLoader = this.getClass().getClassLoader();
                PathRepository classPathRepository = this.getPathRepository(
                        context, prevDirectives, "classpath", "classlist");
                ClassLoader finalLoader = buildResult.buildClassLoader(context,
                        response.getResponseURL(), systemLoader,
                        classPathRepository);
                return new ClassInfo(finalLoader,
                        buildResult.loadClass(finalLoader), prevDirectives,
                        newSourceCode);
            }
        }
        NavigatorProgressEvent event = context.getProgressEvent();
        context.setProgressEvent(ProgressType.BUILDING, event == null ? 0
                : event.getCurrentValue(), -1);
        Properties directives = this.getDirectives(newSourceCode);
        PathRepository classPath = this.getPathRepository(context, directives,
                "classpath", "classlist");
        PathRepository sourcePath = this.getPathRepository(context, directives,
                "sourcepath", "sourcelist");
        PathRepository platformClassPath = this
                .getPlatformClassPathRepository();
        JavaResponseFileManager fileManager = new JavaResponseFileManager(
                context, platformClassPath, classPath, sourcePath);
        BuildResult result = this.buildResponse(fileManager, response,
                newSourceCode, directives);
        response.setNewTransientCachedObject(result,
                result.getEstimatedTransientSize());
        response.setNewPersistentCachedObject(result);
        ClassLoader systemLoader = this.getClass().getClassLoader();
        ClassLoader finalLoader = result.buildClassLoader(context,
                response.getResponseURL(), systemLoader, classPath);
        return new ClassInfo(finalLoader, result.loadClass(finalLoader),
                directives, newSourceCode);
    }

    /**
     * Gets the path repository.
     *
     * @param context
     *            the context
     * @param directives
     *            the directives
     * @param pathPropName
     *            the path prop name
     * @param listPropName
     *            the list prop name
     * @return the path repository
     * @throws ClientletException
     *             the clientlet exception
     */
    private PathRepository getPathRepository(ClientletContext context,
            Properties directives, String pathPropName, String listPropName)
                    throws ClientletException {
        URL[] paths;
        String[] entryList;
        if (directives == null) {
            paths = new URL[0];
            entryList = null;
        } else {
            String classPath = directives.getProperty(pathPropName);
            if (classPath == null) {
                paths = new URL[0];
            } else {
                URL baseURL = context.getResponse().getResponseURL();
                // Always separated by a comma.
                StringTokenizer tok = new StringTokenizer(classPath, ",");
                ArrayList<URL> urlList = new ArrayList<URL>();
                while (tok.hasMoreTokens()) {
                    String token = tok.nextToken().trim();
                    try {
                        URL url = Urls.guessURL(baseURL, token);
                        urlList.add(url);
                    } catch (MalformedURLException mfu) {
                        throw new ClientletException("Path URI in directive '"
                                + pathPropName + "' is malformed: " + classPath
                                + ".");
                    }
                }
                paths = urlList.toArray(new URL[0]);
            }
            String files = directives.getProperty(listPropName);
            if (files == null) {
                entryList = null;
            } else {
                StringTokenizer tok = new StringTokenizer(files, ",");
                ArrayList<String> nameList = new ArrayList<String>();
                while (tok.hasMoreTokens()) {
                    String token = tok.nextToken().trim();
                    nameList.add(token);
                }
                entryList = nameList.toArray(new String[0]);
            }
        }
        return PathManager.getInstance().getPathRepository(paths, entryList);
    }

    /**
     * Gets the platform class path repository.
     *
     * @return the platform class path repository
     */
    protected PathRepository getPlatformClassPathRepository() {
        return PathManager.getInstance().getPathRepository(
                this.getPlatformClassPath(), null);
    }

    /**
     * Gets the platform class path.
     *
     * @return the platform class path
     */
    protected URL[] getPlatformClassPath() {
        URL[] extraEntries = this.getExtraPlatformClassPath();
        URL[] sunBootClassPath = BASE_PLATFORM_CLASS_PATH.toArray(new URL[0]);
        URL[] wholeClassPath = new URL[extraEntries.length
                                       + sunBootClassPath.length];
        System.arraycopy(sunBootClassPath, 0, wholeClassPath, 0,
                sunBootClassPath.length);
        System.arraycopy(extraEntries, 0, wholeClassPath,
                sunBootClassPath.length, extraEntries.length);
        return wholeClassPath;
    }

    /**
     * Compile.
     *
     * @param fileManager
     *            the file manager
     * @param diagnosticListener
     *            the diagnostic listener
     * @param compilationUnits
     *            the compilation units
     * @throws ClientletException
     *             the clientlet exception
     */
    protected abstract void compile(JavaFileManager fileManager,
            DiagnosticListener<? super JavaFileObject> diagnosticListener,
            java.util.List<JavaFileObject> compilationUnits)
                    throws ClientletException;

    /**
     * Gets the extra platform class path.
     *
     * @return the extra platform class path
     */
    protected abstract URL[] getExtraPlatformClassPath();

    /**
     * Builds the response.
     *
     * @param fileManager
     *            the file manager
     * @param response
     *            the response
     * @param sourceCode
     *            the source code
     * @param directives
     *            the directives
     * @return the builds the result
     * @throws ClientletException
     *             the clientlet exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException
     *             the class not found exception
     */
    private BuildResult buildResponse(JavaResponseFileManager fileManager,
            ClientletResponse response, String sourceCode, Properties directives)
                    throws ClientletException, IOException, ClassNotFoundException {
        java.util.List<JavaFileObject> compilationUnits = new ArrayList<JavaFileObject>();
        String fileName = Urls.getNoRefForm(response.getResponseURL());
        JavaFileObject entryUnit = new ResponseJavaInputFile(response,
                sourceCode, fileName, JavaFileObject.Kind.SOURCE);
        fileManager.setCompilationUnit(entryUnit);
        compilationUnits.add(entryUnit);
        long time1 = System.currentTimeMillis();
        LocalDiagnosticListener diagnosticListener = new LocalDiagnosticListener();
        this.compile(fileManager, diagnosticListener, compilationUnits);
        long time2 = System.currentTimeMillis();
        if (logger.isLoggable(Level.INFO)) {
            logger.info("buildResponse(): Compile time: " + (time2 - time1)
                    + " ms. for " + response.getResponseURL() + ".");
        }
        if (diagnosticListener.hasErrors()) {
            throw new ClientletException("There are compilation errors:\r\n"
                    + diagnosticListener.getDiagnosticOutput(), sourceCode);
        } else {
            String errorOutput = diagnosticListener.getDiagnosticOutput()
                    .trim();
            if (errorOutput.length() > 0) {
                logger.warning("buildResponse(): There are compilation warnings:\r\n"
                        + errorOutput);
            }
        }
        return fileManager.getBuildResult(sourceCode, directives);
    }

    /**
     * The Class ScriptContent.
     */
    private static class ScriptContent extends AbstractComponentContent {

        /** The component. */
        private final Component component;

        /** The properties. */
        private final Properties properties;

        /** The script source. */
        private final String scriptSource;

        /** The context. */
        private final ClientletContext context;

        /** The secondary title. */
        private String secondaryTitle;

        /** The stage. */
        private Stage stage;

        /** The cached actual component. */
        private Component cachedActualComponent;

        /**
         * Instantiates a new script content.
         *
         * @param component
         *            the component
         * @param properties
         *            the properties
         * @param scriptSource
         *            the script source
         * @param context
         *            the context
         */
        public ScriptContent(final Component component,
                final Properties properties, final String scriptSource,
                final ClientletContext context) {
            this.component = component;
            this.properties = properties;
            this.scriptSource = scriptSource;
            this.context = context;
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.AbstractComponentContent#canCopy()
         */
        @Override
        public boolean canCopy() {
            return false;
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.AbstractComponentContent#copy()
         */
        @Override
        public boolean copy() {
            return false;
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.AbstractComponentContent#getComponent()
         */
        @Override
        public Component getComponent() {
            Component cac = this.cachedActualComponent;
            if (cac == null) {
                synchronized (this) {
                    if (this.cachedActualComponent == null) {
                        cac = this.createComponent();
                        this.cachedActualComponent = cac;
                    }
                }
            }
            return cac;
        }

        /**
         * Creates the component.
         *
         * @return the component
         */
        private Component createComponent() {
            // Always wrap component, so that we can set the
            // ClientletContext in the event queue thread.
            Properties props = this.properties;
            boolean usePreferredSize = props == null ? false : "true"
                .equalsIgnoreCase(props.getProperty("use-preferred-size"));
            javax.swing.JPanel panel = new ComponentWrapper(this.context,
                    this.component, usePreferredSize);
            return panel;
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.AbstractComponentContent#getDescription()
         */
        @Override
        public String getDescription() {
            Properties props = this.properties;
            return props == null ? "" : props.getProperty("description");
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.AbstractComponentContent#getSourceCode()
         */
        @Override
        public String getSourceCode() {
            return this.scriptSource;
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.AbstractComponentContent#getTitle()
         */
        @Override
        public String getTitle() {
            Properties props = this.properties;
            String title = props == null ? null : props.getProperty("title");
            if (title == null) {
                String to = this.secondaryTitle;
                title = to != null ? to : this.context.getResponse()
                        .getResponseURL().toExternalForm();
            }
            return title;
        }

        /**
         * Sets the secondary title.
         *
         * @param titleOverride
         *            the new secondary title
         */
        public void setSecondaryTitle(String titleOverride) {
            this.secondaryTitle = titleOverride;
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.AbstractComponentContent#addNotify()
         */
        @Override
        public void addNotify() {
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.AbstractComponentContent#removeNotify()
         */
        @Override
        public void removeNotify() {
            Stage stage = this.stage;
            if (stage != null) {
                stage.close();
            }
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.AbstractComponentContent#getContentObject()
         */
        @Override
        public Object getContentObject() {
            return this.component;
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.AbstractComponentContent#getMimeType()
         */
        @Override
        public String getMimeType() {
            return "text/x-java-source";
        }

        /*
         * (non-Javadoc)
         * @see
         * org.lobobrowser.clientlet.AbstractComponentContent#setProperty(java.lang.
         * String, java.lang.Object)
         */
        @Override
        public void setProperty(String name, Object value) {
            // TODO Auto-generated method stub

        }
    }

    /**
     * The listener interface for receiving localDiagnostic events. The class
     * that is interested in processing a localDiagnostic event implements this
     * interface, and the object created with that class is registered with a
     * component using the component's <code>addLocalDiagnosticListener</code>
     * method. When the localDiagnostic event occurs, that object's appropriate
     * method is invoked.
     */
    public static class LocalDiagnosticListener implements
    DiagnosticListener<JavaFileObject> {

        /** The buffer. */
        private final StringBuffer buffer = new StringBuffer();

        /** The has errors. */
        private boolean hasErrors = false;

        /*
         * (non-Javadoc)
         * @see javax.tools.DiagnosticListener#report(javax.tools.Diagnostic)
         */
        @Override
        public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
            if (diagnostic.getKind() == Diagnostic.Kind.ERROR) {
                hasErrors = true;
            }
            StringBuffer buffer = this.buffer;
            buffer.append(diagnostic.getSource().getName());
            buffer.append(':');
            buffer.append(diagnostic.getLineNumber());
            buffer.append(": ");
            buffer.append(diagnostic.getMessage(Locale.getDefault()));
            buffer.append("\r\n");
        }

        /**
         * Gets the diagnostic output.
         *
         * @return the diagnostic output
         */
        public String getDiagnosticOutput() {
            return buffer.toString();
        }

        /**
         * Checks for errors.
         *
         * @return true, if successful
         */
        public boolean hasErrors() {
            return this.hasErrors;
        }

    }

    /**
     * The Class ClassInfo.
     */
    private static class ClassInfo {

        /** The class loader. */
        public final ClassLoader classLoader;

        /** The result class. */
        public final Class<? extends Object> resultClass;

        /** The directives. */
        public final Properties directives;

        /** The source code. */
        public final String sourceCode;

        /**
         * Instantiates a new class info.
         *
         * @param classLoader
         *            the class loader
         * @param resultClass
         *            the result class
         * @param directives
         *            the directives
         * @param sourceCode
         *            the source code
         */
        public ClassInfo(ClassLoader classLoader,
                Class<? extends Object> resultClass, Properties directives,
                String sourceCode) {
            super();
            this.classLoader = classLoader;
            this.resultClass = resultClass;
            this.directives = directives;
            this.sourceCode = sourceCode;
        }
    }

    /**
     * The Class ResponseObject.
     */
    private static class ResponseObject {

        /** The object. */
        public final Object object;

        /** The secondary title. */
        public final String secondaryTitle;

        /**
         * Instantiates a new response object.
         *
         * @param object
         *            the object
         * @param secondaryTitle
         *            the secondary title
         * @param stage
         *            the stage
         */
        public ResponseObject(Object object, String secondaryTitle, Stage stage) {
            this.object = object;
            this.secondaryTitle = secondaryTitle;
        }
    }
}
