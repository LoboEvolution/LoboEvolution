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
/*
 * Created on Jun 12, 2005
 */
package org.lobobrowser.store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.http.LRUCache;
import org.lobobrowser.security.GenericLocalPermission;
import org.lobobrowser.util.Strings;
import org.lobobrowser.util.Urls;
import org.lobobrowser.util.io.IORoutines;

/**
 * The Class CacheManager.
 *
 * @author J. H. S.
 */
public final class CacheManager implements Runnable {

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(CacheManager.class
            .getName());

    /** The Constant AFTER_SWEEP_SLEEP. */
    private static final int AFTER_SWEEP_SLEEP = 5 * 60 * 1000;

    /** The Constant INITIAL_SLEEP. */
    private static final int INITIAL_SLEEP = 30 * 1000;

    /** The Constant DELETE_TOLERANCE. */
    private static final int DELETE_TOLERANCE = 60 * 1000;

    /** The Constant MAX_CACHE_SIZE. */
    private static final long MAX_CACHE_SIZE = 100000000;

    /** The transient cache. */
    private final LRUCache transientCache = new LRUCache(1000000);

    /**
     * Instantiates a new cache manager.
     */
    private CacheManager() {
        super();
        Thread t = new Thread(this, "CacheManager");
        t.setDaemon(true);
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }

    /** The instance. */
    private static CacheManager instance;

    /**
     * Gets the single instance of CacheManager.
     *
     * @return single instance of CacheManager
     */
    public static CacheManager getInstance() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
        }
        if (instance == null) {
            synchronized (CacheManager.class) {
                if (instance == null) {
                    instance = new CacheManager();
                }
            }
        }
        return instance;
    }

    /**
     * Put transient.
     *
     * @param url
     *            the url
     * @param value
     *            the value
     * @param approxSize
     *            the approx size
     */
    public void putTransient(URL url, Object value, int approxSize) {
        String key = Urls.getNoRefForm(url);
        synchronized (this.transientCache) {
            this.transientCache.put(key, value, approxSize);
        }
    }

    /**
     * Gets the transient.
     *
     * @param url
     *            the url
     * @return the transient
     */
    public Object getTransient(URL url) {
        String key = Urls.getNoRefForm(url);
        synchronized (this.transientCache) {
            return this.transientCache.get(key);
        }
    }

    /**
     * Removes the transient.
     *
     * @param url
     *            the url
     */
    public void removeTransient(URL url) {
        String key = Urls.getNoRefForm(url);
        synchronized (this.transientCache) {
            this.transientCache.remove(key);
        }
    }

    /**
     * Sets the max transient cache size.
     *
     * @param approxMaxSize
     *            the new max transient cache size
     */
    public void setMaxTransientCacheSize(int approxMaxSize) {
        synchronized (this.transientCache) {
            this.transientCache.setApproxMaxSize(approxMaxSize);
        }
    }

    /**
     * Gets the max transient cache size.
     *
     * @return the max transient cache size
     */
    public int getMaxTransientCacheSize() {
        synchronized (this.transientCache) {
            return this.transientCache.getApproxMaxSize();
        }
    }

    /**
     * Gets the transient cache info.
     *
     * @return the transient cache info
     */
    public CacheInfo getTransientCacheInfo() {
        long approxSize;
        int numEntries;
        List entryInfo;
        synchronized (this.transientCache) {
            approxSize = this.transientCache.getApproxSize();
            numEntries = this.transientCache.getNumEntries();
            entryInfo = this.transientCache.getEntryInfoList();
        }
        return new CacheInfo(approxSize, numEntries, entryInfo);
    }

    /**
     * Put persistent.
     *
     * @param url
     *            the url
     * @param rawContent
     *            the raw content
     * @param isDecoration
     *            the is decoration
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void putPersistent(URL url, byte[] rawContent, boolean isDecoration)
            throws IOException {
        File cacheFile = getCacheFile(url, isDecoration);
        synchronized (getLock(cacheFile)) {
            File parent = cacheFile.getParentFile();
            if ((parent != null) && !parent.exists()) {
                parent.mkdirs();
            }
            FileOutputStream fout = new FileOutputStream(cacheFile);
            try {
                fout.write(rawContent);
            } finally {
                fout.close();
            }
        }
    }

    /**
     * Gets the persistent.
     *
     * @param url
     *            the url
     * @param isDecoration
     *            the is decoration
     * @return the persistent
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public byte[] getPersistent(URL url, boolean isDecoration)
            throws IOException {
        // We don't return an InputStream because further synchronization
        // would be needed to prevent concurrent writes into the file.
        File cacheFile = getCacheFile(url, isDecoration);
        synchronized (getLock(cacheFile)) {
            cacheFile.setLastModified(System.currentTimeMillis());
            try {
                return IORoutines.load(cacheFile);
            } catch (FileNotFoundException fnf) {
                return null;
            }
        }
    }

    /**
     * Removes the persistent.
     *
     * @param url
     *            the url
     * @param isDecoration
     *            the is decoration
     * @return true, if successful
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public boolean removePersistent(URL url, boolean isDecoration)
            throws IOException {
        File cacheFile = getCacheFile(url, isDecoration);
        synchronized (getLock(cacheFile)) {
            return cacheFile.delete();
        }
    }

    /**
     * Gets the jar file.
     *
     * @param url
     *            the url
     * @return the jar file
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public JarFile getJarFile(URL url) throws IOException {
        File cacheFile = getCacheFile(url, false);
        synchronized (getLock(cacheFile)) {
            if (!cacheFile.exists()) {
                if (Urls.isLocalFile(url)) {
                    return new JarFile(url.getFile());
                }
                throw new java.io.FileNotFoundException(
                        "JAR file cannot be obtained for a URL that is not cached locally: "
                                + url + ".");
            }
            cacheFile.setLastModified(System.currentTimeMillis());
            return new JarFile(cacheFile);
        }
    }

    /**
     * Gets the cache file.
     *
     * @param url
     *            the url
     * @param isDecoration
     *            the is decoration
     * @return the cache file
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private static File getCacheFile(URL url, boolean isDecoration)
            throws IOException {
        // Use file, not path, because query string matters in caching.
        String urlFile = url.getFile();
        String urlText = Urls.getNoRefForm(url);
        int lastSlashIdx = urlFile.lastIndexOf('/');
        String simpleName = lastSlashIdx == -1 ? urlFile : urlFile
                .substring(lastSlashIdx + 1);
        if (simpleName.length() > 16) {
            simpleName = simpleName.substring(0, 16);
        }
        String normalizedName = Strings.getJavaIdentifier(simpleName);
        String hash = Strings.getMD5(urlText);
        String fileName = normalizedName + "_" + hash;
        if (isDecoration) {
            fileName += ".decor";
        }
        return StorageManager.getInstance().getContentCacheFile(url.getHost(),
                fileName);
    }

    /**
     * Gets the lock.
     *
     * @param file
     *            the file
     * @return the lock
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private static Object getLock(File file) throws IOException {
        return ("cm:" + file.getCanonicalPath()).intern();
    }

    /**
     * Touches the cache file corresponding to the given URL and returns
     * <code>true</code> if the file exists.
     *
     * @param url
     *            the url
     * @param isDecoration
     *            the is decoration
     * @return true, if successful
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public boolean checkCacheFile(URL url, boolean isDecoration)
            throws IOException {
        File file = getCacheFile(url, isDecoration);
        synchronized (getLock(file)) {
            if (file.exists()) {
                file.setLastModified(System.currentTimeMillis());
                return true;
            }
            return false;
        }
    }

    /**
     * Clear cache.
     *
     * @throws Exception
     *             the exception
     */
    public void clearCache() throws Exception {

        File userHome = new File(System.getProperty("user.home"));
        File loboHome = new File(userHome, ".lobo");
        File cacheHome = new File(loboHome, "cache");
        File hostHome = new File(loboHome, "HostStore");
        deleteRecursive(cacheHome);
        deleteRecursive(hostHome);
    }

    /**
     * Delete recursive.
     *
     * @param rootDir
     *            the root dir
     */
    private void deleteRecursive(File rootDir) {

        File[] c = rootDir.listFiles();
        for (File file : c) {
            if (file.isDirectory()) {
                deleteRecursive(file);
                file.delete();
            } else {
                file.delete();
            }
        }
    }

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     * @throws Exception
     *             the exception
     */
    public static void main(String[] args) throws Exception {
        CacheManager c = new CacheManager();
        c.clearCache();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            Thread.sleep(INITIAL_SLEEP);
        } catch (InterruptedException ie) {
            // ignore
        }
        for (;;) {
            try {
                this.sweepCache();
                Thread.sleep(AFTER_SWEEP_SLEEP);
            } catch (Throwable err) {
                logger.log(Level.SEVERE, "run()", err);
                try {
                    Thread.sleep(AFTER_SWEEP_SLEEP);
                } catch (InterruptedException ie) {
                    // ignore
                }
            }
        }
    }

    /**
     * Gets the max cache size.
     *
     * @return the max cache size
     */
    private long getMaxCacheSize() {
        return MAX_CACHE_SIZE;
    }

    /**
     * Sweep cache.
     *
     * @throws Exception
     *             the exception
     */
    private void sweepCache() throws Exception {
        CacheStoreInfo sinfo = this.getCacheStoreInfo();
        if (logger.isLoggable(Level.INFO)) {
            logger.info("sweepCache(): Cache size is " + sinfo.getLength()
                    + " with a max of " + this.getMaxCacheSize()
                    + ". The number of cache files is "
                    + sinfo.getFileInfos().length + ".");
        }
        long oversize = sinfo.getLength() - this.getMaxCacheSize();
        if (oversize > 0) {
            CacheFileInfo[] finfos = sinfo.getFileInfos();
            // Sort in ascending order of modification
            Arrays.sort(finfos);
            long okToDeleteBeforeThis = System.currentTimeMillis()
                    - DELETE_TOLERANCE;
            for (int i = 0; i < finfos.length; i++) {
                try {
                    Thread.yield();
                    CacheFileInfo finfo = finfos[i];
                    synchronized (getLock(finfo.getFile())) {
                        long lastModified = finfo.getLastModified();
                        if (lastModified < okToDeleteBeforeThis) {
                            Thread.sleep(1);
                            long time1 = System.currentTimeMillis();
                            finfo.delete();
                            long time2 = System.currentTimeMillis();
                            if (logger.isLoggable(Level.INFO)) {
                                logger.info("sweepCache(): Removed " + finfo
                                        + " in " + (time2 - time1) + " ms.");
                            }
                            oversize -= finfo.getInitialLength();
                            if (oversize <= 0) {
                                break;
                            }
                        }
                    }
                } catch (Throwable thrown) {
                    logger.log(Level.WARNING, "sweepCache()", thrown);
                }
            }
        }
    }

    /**
     * Gets the cache store info.
     *
     * @return the cache store info
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private CacheStoreInfo getCacheStoreInfo() throws IOException {
        CacheStoreInfo csinfo = new CacheStoreInfo();
        File cacheRoot = StorageManager.getInstance().getCacheRoot();
        populateCacheStoreInfo(csinfo, cacheRoot);
        return csinfo;
    }

    /**
     * Populate cache store info.
     *
     * @param csinfo
     *            the csinfo
     * @param directory
     *            the directory
     */
    private void populateCacheStoreInfo(CacheStoreInfo csinfo, File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            logger.severe("populateCacheStoreInfo(): Unexpected: '" + directory
                    + "' is not a directory.");
            return;
        }
        if (files.length == 0) {
            directory.delete();
        }
        for (int i = 0; i < files.length; i++) {
            Thread.yield();
            File file = files[i];
            if (file.isDirectory()) {
                this.populateCacheStoreInfo(csinfo, file);
            } else {
                csinfo.addCacheFile(file);
            }
        }
    }
}
