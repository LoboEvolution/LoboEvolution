/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
/*
 * Created on May 31, 2005
 */
package org.lobobrowser.store;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.io.ManagedFile;
import org.lobobrowser.io.ManagedFileFilter;
import org.lobobrowser.io.ManagedStore;
import org.lobobrowser.io.QuotaExceededException;
import org.lobobrowser.util.WrapperException;
import org.lobobrowser.util.gui.ClassLoaderObjectInputStream;

/**
 * The Class RestrictedStore.
 */
public final class RestrictedStore implements QuotaSource, ManagedStore {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(RestrictedStore.class.getName());

	/**
	 * Canonical base directory.
	 */
	private final File baseDirectory;

	/** The base canonical path. */
	private final String baseCanonicalPath;

	/** The size file canonical path. */
	private final String sizeFileCanonicalPath;

	/** The quota. */
	private final long quota;

	/** The size file name. */
	private final String SIZE_FILE_NAME = ".W$Dir$Size";

	/** Made up *. */
	private final int EMPTY_FILE_SIZE = 64;

	/** Made up *. */
	private final int DIRECTORY_SIZE = 64;

	/** The size. */
	private long size = -1;

	/**
	 * Instantiates a new restricted store.
	 *
	 * @param baseDirectory
	 *            the base directory
	 * @param quota
	 *            the quota
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public RestrictedStore(File baseDirectory, long quota) throws IOException {
		// Security: This constructor is only allowed to be invoked
		// by a caller with privileged access to the directory.
		SecurityManager sm = System.getSecurityManager();
		String canonical = baseDirectory.getCanonicalPath();
		if (sm != null) {
			sm.checkWrite(canonical);
		}
		if (!baseDirectory.exists()) {
			baseDirectory.mkdirs();
		} else if (!baseDirectory.isDirectory()) {
			throw new IllegalArgumentException(baseDirectory + " not a directory");
		}
		this.baseDirectory = new File(canonical);
		this.baseCanonicalPath = canonical;
		this.sizeFileCanonicalPath = new File(this.baseDirectory, SIZE_FILE_NAME).getCanonicalPath();
		this.quota = quota;
	}

	/**
	 * Update size file.
	 *
	 * @return the long
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public long updateSizeFile() throws IOException {
		long totalSize = this.computeSize();
		long prevSize;
		synchronized (this) {
			prevSize = this.size;
			this.updateSizeFileImpl(totalSize);
		}
		if (prevSize != -1 && Math.abs(totalSize - prevSize) > 10000) {
			logger.warn("updateSizeFile(): Corrected a size discrepancy of " + (totalSize - prevSize)
					+ " bytes in store '" + this.baseDirectory + "'.");
		}
		return totalSize;
	}

	/**
	 * Update size file impl.
	 *
	 * @param totalSize
	 *            the total size
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void updateSizeFileImpl(long totalSize) throws IOException {
		// The computed size is not necessarily precise. That's
		// why we have this.
		synchronized (this) {
			this.size = totalSize;
			File sizeFile = new File(this.baseDirectory, SIZE_FILE_NAME);
			FileOutputStream out = new FileOutputStream(sizeFile);
			DataOutputStream dout = new DataOutputStream(out);
			try {
				dout.writeLong(totalSize);
				dout.flush();
			} finally {
				dout.close();
				out.close();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.io.ManagedStore#getQuota()
	 */
	@Override
	public long getQuota() {
		return this.quota;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.io.ManagedStore#getSize()
	 */
	@Override
	public long getSize() throws IOException {
		try {
			return AccessController.doPrivileged(new PrivilegedAction<Long>() {
				@Override
				public Long run() {
					synchronized (this) {
						try {
							long size = RestrictedStore.this.size;
							if (size == -1) {
								size = RestrictedStore.this.size = RestrictedStore.this.getSizeFromFile();
							}
							return size;
						} catch (IOException ioe) {
							throw new WrapperException(ioe);
						}
					}
				}
			});
		} catch (WrapperException we) {
			throw (IOException) we.getCause();
		}
	}

	/**
	 * Gets the size from file.
	 *
	 * @return the size from file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private long getSizeFromFile() throws IOException {
		File sizeFile = new File(this.baseDirectory, SIZE_FILE_NAME);
		try {
			FileInputStream in = new FileInputStream(sizeFile);
			try {
				DataInputStream din = new DataInputStream(in);
				return din.readLong();
			} finally {
				in.close();
			}
		} catch (FileNotFoundException fnf) {
			return this.updateSizeFile();
		}
	}

	/**
	 * Compute size.
	 *
	 * @return the long
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private long computeSize() throws IOException {
		return this.computeSize(this.baseDirectory);
	}

	/**
	 * Compute size.
	 *
	 * @param directory
	 *            the directory
	 * @return the long
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private long computeSize(File directory) throws IOException {
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException("'directory' not a directory");
		}
		long total = DIRECTORY_SIZE;
		File[] files = directory.listFiles();
		for (File file2 : files) {
			Thread.yield();
			File file = file2;
			if (file.isDirectory() && !file.equals(directory)) {
				String fileCanonical = file.getCanonicalPath();
				if (fileCanonical.startsWith(this.baseCanonicalPath)) {
					total += this.computeSize(file);
				}
			} else {
				total += EMPTY_FILE_SIZE + file.length();
			}
		}
		return total;
	}

	/** The last updated size. */
	private long lastUpdatedSize = Long.MIN_VALUE;

	/** The size update threshold. */
	private static long SIZE_UPDATE_THRESHOLD = 4096;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.store.QuotaSource#addUsedBytes(long)
	 */
	@Override
	public void addUsedBytes(long addition) throws IOException {
		synchronized (this) {
			// long size = this.getSize();
			boolean fromFile = false;
			if (this.size == -1) {
				this.size = this.getSizeFromFile();
				fromFile = true;
			}
			long newTotal = this.size + addition;
			if (addition > 0 && newTotal > this.quota) {
				throw new QuotaExceededException("Quota would be exceeded by " + (newTotal - this.quota) + " bytes.");
			}
			this.size = newTotal;
			if (fromFile) {
				this.lastUpdatedSize = newTotal;
			} else if (Math.abs(newTotal - this.lastUpdatedSize) > SIZE_UPDATE_THRESHOLD) {
				this.lastUpdatedSize = newTotal;
				this.updateSizeFileImpl(newTotal);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sourceforge.xamj.store.QuotaSource#addUsedBytes(long)
	 */
	/**
	 * Subtract used bytes.
	 *
	 * @param reduction
	 *            the reduction
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void subtractUsedBytes(long reduction) throws IOException {
		this.addUsedBytes(-reduction);
	}

	/**
	 * Check not size file.
	 *
	 * @param canonicalPath
	 *            the canonical path
	 * @param ref
	 *            the ref
	 */
	private void checkNotSizeFile(String canonicalPath, String ref) {
		if (this.sizeFileCanonicalPath.equals(canonicalPath)) {
			throw new SecurityException("This particular path not allowed: " + ref);
		}
	}

	/**
	 * Check path.
	 *
	 * @param canonicalPath
	 *            the canonical path
	 * @param ref
	 *            the ref
	 */
	private void checkPath(String canonicalPath, String ref) {
		if (!canonicalPath.startsWith(this.baseCanonicalPath)) {
			throw new SecurityException("Path outside protected store: " + ref);
		}
		this.checkNotSizeFile(canonicalPath, ref);
	}

	/**
	 * Gets the input stream.
	 *
	 * @param fullFile
	 *            the full file
	 * @param ref
	 *            the ref
	 * @return the input stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public InputStream getInputStream(final File fullFile, final String ref) throws IOException {
		try {
			return AccessController.doPrivileged((PrivilegedAction<InputStream>) () -> {
				try {
					String canonical = fullFile.getCanonicalPath();
					checkPath(canonical, ref);
					return new FileInputStream(fullFile);
				} catch (IOException ioe) {
					throw new WrapperException(ioe);
				}
			});
		} catch (WrapperException we) {
			throw (IOException) we.getCause();
		}
	}

	/**
	 * Gets the output stream.
	 *
	 * @param fullFile
	 *            the full file
	 * @param ref
	 *            the ref
	 * @return the output stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public OutputStream getOutputStream(final File fullFile, final String ref) throws IOException {
		try {
			return AccessController.doPrivileged((PrivilegedAction<OutputStream>) () -> {
				try {
					long toSubtract = EMPTY_FILE_SIZE + (fullFile.exists() ? fullFile.length() : 0);
					String canonical = fullFile.getCanonicalPath();
					checkPath(canonical, ref);
					// TODO: Disallow size file here
					File parent = fullFile.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					} else if (!parent.isDirectory()) {
						throw new IllegalArgumentException("Parent of '" + ref + "' is not a directory");
					}
					FileOutputStream fout = new FileOutputStream(fullFile);
					OutputStream out = new RestrictedOutputStream(fout, RestrictedStore.this);
					if (toSubtract != 0) {
						subtractUsedBytes(toSubtract);
					}
					return out;
				} catch (IOException ioe) {
					throw new WrapperException(ioe);
				}
			});
		} catch (WrapperException we) {
			throw (IOException) we.getCause();
		}
	}

	/**
	 * Gets the relative path.
	 *
	 * @param canonicalPath
	 *            the canonical path
	 * @return the relative path
	 */
	private String getRelativePath(String canonicalPath) {
		String relativePath = canonicalPath.substring(this.baseCanonicalPath.length());
		if (relativePath.startsWith(File.separator)) {
			relativePath = relativePath.substring(File.separator.length());
		}
		if (!"/".equals(File.separator)) {
			relativePath = relativePath.replace(File.separatorChar, '/');
		}
		return relativePath;
	}

	/**
	 * Gets the paths.
	 *
	 * @param regexp
	 *            the regexp
	 * @return the paths
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Collection getPaths(String regexp) throws IOException {
		final Pattern pattern = Pattern.compile(regexp);
		try {
			return AccessController.doPrivileged((PrivilegedAction<Collection>) () -> {
				try {
					return getPaths(pattern, baseDirectory);
				} catch (IOException ioe) {
					throw new WrapperException(ioe);
				}
			});
		} catch (WrapperException we) {
			throw (IOException) we.getCause();
		}
	}

	/**
	 * Gets the paths.
	 *
	 * @param pattern
	 *            the pattern
	 * @param directory
	 *            the directory
	 * @return the paths
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private Collection getPaths(Pattern pattern, File directory) throws IOException {
		// Security: This method is expected to be private.
		Collection paths = new LinkedList();
		File[] localFiles = directory.listFiles();
		for (File file : localFiles) {
			if (file.isDirectory()) {
				Collection subPaths = this.getPaths(pattern, file);
				paths.addAll(subPaths);
			} else {
				String canonical = file.getCanonicalPath();
				String relativePath = this.getRelativePath(canonical);
				Matcher matcher = pattern.matcher(relativePath);
				if (matcher.matches()) {
					try {
						this.checkPath(canonical, "not-shown");
						paths.add(relativePath);
					} catch (SecurityException se) {
						// ignore file
					}
				}
			}
		}
		return paths;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sourceforge.xamj.store.QuotaSource#getSpaceLeft()
	 */
	@Override
	public long getSpaceLeft() throws IOException {
		return this.quota - this.getSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.io.ManagedStore#saveObject(java.lang.String,
	 * java.io.Serializable)
	 */
	@Override
	public void saveObject(String path, Serializable object) throws IOException {
		ManagedFile file = this.getManagedFile(path);
		OutputStream out = file.openOutputStream();
		try {
			ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(out));
			oout.writeObject(object);
			oout.flush();
		} finally {
			out.close();
		}
	}

	/**
	 * Removes the object.
	 *
	 * @param path
	 *            the path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void removeObject(String path) throws IOException {
		final ManagedFile file = this.getManagedFile(path);
		file.delete();
	}

	/**
	 * Retrieve object.
	 *
	 * @param path
	 *            the path
	 * @return the object
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public Object retrieveObject(String path) throws IOException, ClassNotFoundException {
		return this.retrieveObject(path, Thread.currentThread().getContextClassLoader());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.io.ManagedStore#retrieveObject(java.lang.String,
	 * java.lang.ClassLoader)
	 */
	@Override
	public Object retrieveObject(String path, ClassLoader classLoader) throws IOException, ClassNotFoundException {
		ManagedFile file = this.getManagedFile(path);
		try {
			InputStream in = file.openInputStream();
			ObjectInputStream oin = new ClassLoaderObjectInputStream(in, classLoader);
			try {
				return oin.readObject();
			} finally {
				oin.close();
				in.close();
			}
		} catch (FileNotFoundException err) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.io.ManagedStore#getManagedFile(org.xamjwg.io.ManagedFile,
	 * java.lang.String)
	 */
	@Override
	public ManagedFile getManagedFile(ManagedFile parent, String relativePath) throws IOException {
		return new ManagedFileImpl(parent, relativePath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.io.ManagedStore#getManagedFile(String)
	 */
	@Override
	public ManagedFile getManagedFile(String path) throws IOException {
		return new ManagedFileImpl(path);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.io.ManagedStore#getRootManagedDirectory()
	 */
	@Override
	public ManagedFile getRootManagedDirectory() throws IOException {
		return new ManagedFileImpl("/");
	}

	/**
	 * Managed to native.
	 *
	 * @param path
	 *            the path
	 * @return the file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private File managedToNative(final String path) throws IOException {
		try {
			return AccessController.doPrivileged((PrivilegedAction<File>) () -> {
				try {
					if (path.contains("\\")) {
						throw new IllegalArgumentException(
								"Characer backslash (\\) not allowed in managed paths. Use a forward slash. Path="
										+ path);
					}
					String relPath = path;
					while (relPath.startsWith("/")) {
						relPath = relPath.substring(1);
					}
					relPath = relPath.replace("/", File.separator);
					File fullFile;
					if (relPath.length() == 0) {
						fullFile = baseDirectory;
					} else {
						fullFile = new File(baseDirectory, relPath);
					}
					String canonical = fullFile.getCanonicalPath();
					// Must check so that all ManagedFile instances
					// are known to be safe.
					checkPath(canonical, path);
					return fullFile;
				} catch (IOException ioe) {
					throw new WrapperException(ioe);
				}
			});
		} catch (WrapperException we) {
			throw (IOException) we.getCause();
		}
	}

	/**
	 * Native to managed.
	 *
	 * @param file
	 *            the file
	 * @return the managed file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private ManagedFile nativeToManaged(File file) throws IOException {
		String canonical = file.getCanonicalPath();
		if (!canonical.startsWith(this.baseCanonicalPath)) {
			throw new SecurityException("File is outside of managed store");
		}
		String mpath = canonical.substring(this.baseCanonicalPath.length());
		if (!mpath.startsWith(File.separator)) {
			mpath = File.separator + mpath;
		}
		return new ManagedFileImpl(mpath);
	}

	/**
	 * The Class ManagedFileImpl.
	 */
	private class ManagedFileImpl implements ManagedFile {
		// NOTE: ManagedFileImpl instances should only be allowed
		// to exist in association with a RestrictedStore.
		/** The path. */
		private final String path;

		/** The native file. */
		private final File nativeFile;

		/**
		 * Instantiates a new managed file impl.
		 *
		 * @param path
		 *            the path
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		private ManagedFileImpl(String path) throws IOException {
			this.path = path;
			// Note: managedToNative has a security check.
			this.nativeFile = managedToNative(path);
		}

		/**
		 * Instantiates a new managed file impl.
		 *
		 * @param parent
		 *            the parent
		 * @param relPath
		 *            the rel path
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		private ManagedFileImpl(ManagedFile parent, String relPath) throws IOException {
			if (parent == null) {
				this.path = relPath;
			} else {
				if (relPath.startsWith("/")) {
					this.path = relPath;
				} else {
					String pp = parent.getPath();
					if (pp.endsWith("/")) {
						this.path = pp + relPath;
					} else {
						this.path = pp + "/" + relPath;
					}
				}
			}
			// Note: managedToNative has a security check.
			this.nativeFile = managedToNative(this.path);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#createFile()
		 */
		@Override
		public boolean createNewFile() throws IOException {
			try {
				return AccessController.doPrivileged((PrivilegedAction<Boolean>) () -> {
					try {
						boolean success = nativeFile.createNewFile();
						if (success) {
							RestrictedStore.this.addUsedBytes(EMPTY_FILE_SIZE);
						}
						return success;
					} catch (IOException ioe) {
						throw new WrapperException(ioe);
					}
				});
			} catch (WrapperException we) {
				throw (IOException) we.getCause();
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#exists()
		 */
		@Override
		public boolean exists() {
			return AccessController.doPrivileged((PrivilegedAction<Boolean>) () -> nativeFile.exists());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#getInputStream()
		 */
		@Override
		public InputStream openInputStream() throws IOException {
			return RestrictedStore.this.getInputStream(this.nativeFile, this.path);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#getOutputStream()
		 */
		@Override
		public OutputStream openOutputStream() throws IOException {
			return RestrictedStore.this.getOutputStream(this.nativeFile, this.path);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#getParent()
		 */
		@Override
		public ManagedFile getParent() throws IOException {
			try {
				return AccessController.doPrivileged((PrivilegedAction<ManagedFile>) () -> {
					try {
						File parentFile = nativeFile.getParentFile();
						// Note: nativeToManaged checks canonical
						// path for permissions.
						return nativeToManaged(parentFile);
					} catch (IOException ioe) {
						throw new WrapperException(ioe);
					}
				});
			} catch (WrapperException we) {
				throw (IOException) we.getCause();
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#getPath()
		 */
		@Override
		public String getPath() {
			return this.path;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#isDirectory()
		 */
		@Override
		public boolean isDirectory() {
			return AccessController.doPrivileged((PrivilegedAction<Boolean>) () -> nativeFile.isDirectory());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#listFiles()
		 */
		@Override
		public ManagedFile[] listFiles() throws IOException {
			return this.listFiles(null);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.xamjwg.io.ManagedFile#listFiles(org.xamjwg.io.ManagedFileFilter)
		 */
		@Override
		public ManagedFile[] listFiles(final ManagedFileFilter filter) throws IOException {
			try {
				return AccessController.doPrivileged((PrivilegedAction<ManagedFile[]>) () -> {
					try {
						File[] files = nativeFile.listFiles();
						List<ManagedFile> mfs = new ArrayList<ManagedFile>();
						for (File file : files) {
							ManagedFile mf = nativeToManaged(file);
							if (filter == null || filter.accept(mf)) {
								mfs.add(mf);
							}
						}
						return mfs.toArray(new ManagedFile[0]);
					} catch (IOException ioe) {
						throw new WrapperException(ioe);
					}
				});
			} catch (WrapperException we) {
				throw (IOException) we.getCause();
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#mkdir()
		 */
		@Override
		public boolean mkdir() {
			return AccessController.doPrivileged((PrivilegedAction<Boolean>) () -> {
				boolean success = nativeFile.mkdir();
				if (success) {
					try {
						RestrictedStore.this.addUsedBytes(DIRECTORY_SIZE);
					} catch (IOException ioe) {
						// Ignore
					}
				}
				return success;
			});
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#mkdirs()
		 */
		@Override
		public boolean mkdirs() {
			return AccessController.doPrivileged((PrivilegedAction<Boolean>) () -> {
				boolean success = nativeFile.mkdirs();
				if (success) {
					try {
						RestrictedStore.this.addUsedBytes(DIRECTORY_SIZE);
					} catch (IOException ioe) {
						// Ignore
					}
				}
				return success;
			});
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#delete()
		 */
		@Override
		public boolean delete() throws IOException {
			try {
				return AccessController.doPrivileged((PrivilegedAction<Boolean>) () -> {
					try {
						long prevLength = nativeFile.length() + EMPTY_FILE_SIZE;
						if (nativeFile.delete()) {
							subtractUsedBytes(prevLength);
							return true;
						} else {
							return false;
						}
					} catch (IOException ioe) {
						throw new WrapperException(ioe);
					}
				});
			} catch (WrapperException we) {
				throw (IOException) we.getCause();
			}
		}
	}
}
