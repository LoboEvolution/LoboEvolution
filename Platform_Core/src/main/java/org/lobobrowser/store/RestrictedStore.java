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
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lobobrowser.io.ManagedFile;
import org.lobobrowser.io.ManagedFileFilter;
import org.lobobrowser.io.ManagedStore;
import org.lobobrowser.io.QuotaExceededException;
import org.lobobrowser.util.WrapperException;

public final class RestrictedStore implements QuotaSource, ManagedStore {
	private static final Logger logger = Logger.getLogger(RestrictedStore.class
			.getName());

	/**
	 * Canonical base directory.
	 */
	private final File baseDirectory;
	private final String baseCanonicalPath;
	private final String sizeFileCanonicalPath;
	private final long quota;

	private final String SIZE_FILE_NAME = ".W$Dir$Size";
	/** Made up **/
	private final int EMPTY_FILE_SIZE = 64;
	/** Made up **/
	private final int DIRECTORY_SIZE = 64;

	private long size = -1;

	/**
	 * 
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
			throw new IllegalArgumentException(baseDirectory
					+ " not a directory");
		}
		this.baseDirectory = new File(canonical);
		this.baseCanonicalPath = canonical;
		this.sizeFileCanonicalPath = new File(this.baseDirectory,
				SIZE_FILE_NAME).getCanonicalPath();
		this.quota = quota;
	}

	long updateSizeFile() throws IOException {
		long totalSize = this.computeSize();
		long prevSize;
		synchronized (this) {
			prevSize = this.size;
			this.updateSizeFileImpl(totalSize);
		}
		if (prevSize != -1 && Math.abs(totalSize - prevSize) > 10000) {
			logger.warning("updateSizeFile(): Corrected a size discrepancy of "
					+ (totalSize - prevSize) + " bytes in store '"
					+ this.baseDirectory + "'.");
		}
		return totalSize;
	}

	private void updateSizeFileImpl(long totalSize) throws IOException {
		// The computed size is not necessarily precise. That's
		// why we have this.
		synchronized (this) {
			this.size = totalSize;
			File sizeFile = new File(this.baseDirectory, SIZE_FILE_NAME);
			FileOutputStream out = new FileOutputStream(sizeFile);
			try {
				DataOutputStream dout = new DataOutputStream(out);
				dout.writeLong(totalSize);
				dout.flush();
			} finally {
				out.close();
			}
		}
	}

	public long getQuota() {
		return this.quota;
	}

	public long getSize() throws IOException {
		try {
			return AccessController.doPrivileged(new PrivilegedAction<Long>() {
				public Long run() {
					synchronized (this) {
						try {
							long size = RestrictedStore.this.size;
							if (size == -1) {
								size = RestrictedStore.this.size = RestrictedStore.this
										.getSizeFromFile();
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
		} catch (java.io.FileNotFoundException fnf) {
			return this.updateSizeFile();
		}
	}

	private long computeSize() throws IOException {
		return this.computeSize(this.baseDirectory);
	}

	private long computeSize(File directory) throws IOException {
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException("'directory' not a directory");
		}
		long total = DIRECTORY_SIZE;
		File[] files = directory.listFiles();
		for (int i = 0; i < files.length; i++) {
			Thread.yield();
			File file = files[i];
			if (file.isDirectory() && !file.equals(directory)) {
				String fileCanonical = file.getCanonicalPath();
				if (fileCanonical.startsWith(this.baseCanonicalPath)) {
					total += this.computeSize(file);
				}
			} else {
				total += (EMPTY_FILE_SIZE + file.length());
			}
		}
		return total;
	}

	private long lastUpdatedSize = Long.MIN_VALUE;
	private static long SIZE_UPDATE_THRESHOLD = 4096;

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
				throw new QuotaExceededException("Quota would be exceeded by "
						+ (newTotal - this.quota) + " bytes.");
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
	public void subtractUsedBytes(long reduction) throws IOException {
		this.addUsedBytes(-reduction);
	}

	private void checkNotSizeFile(String canonicalPath, String ref) {
		if (this.sizeFileCanonicalPath.equals(canonicalPath)) {
			throw new SecurityException("This particular path not allowed: "
					+ ref);
		}
	}

	private void checkPath(String canonicalPath, String ref) {
		if (!canonicalPath.startsWith(this.baseCanonicalPath)) {
			throw new SecurityException("Path outside protected store: " + ref);
		}
		this.checkNotSizeFile(canonicalPath, ref);
	}

	public InputStream getInputStream(final File fullFile, final String ref)
			throws IOException {
		try {
			return AccessController
					.doPrivileged(new PrivilegedAction<InputStream>() {
						// Reason: Caller was able to get an instance of this
						// RestrictedStore. Additionally, we check that the File
						// path is within what's allowed.
						public InputStream run() {
							try {
								String canonical = fullFile.getCanonicalPath();
								checkPath(canonical, ref);
								return new FileInputStream(fullFile);
							} catch (IOException ioe) {
								throw new WrapperException(ioe);
							}
						}
					});
		} catch (WrapperException we) {
			throw (IOException) we.getCause();
		}
	}

	public OutputStream getOutputStream(final File fullFile, final String ref)
			throws IOException {
		try {
			return AccessController
					.doPrivileged(new PrivilegedAction<OutputStream>() {
						// Reason: Caller was able to get an instance of this
						// RestrictedStore. Additionally, we check that the File
						// path is within what's allowed.
						public OutputStream run() {
							try {
								long toSubtract = EMPTY_FILE_SIZE
										+ (fullFile.exists() ? fullFile
												.length() : 0);
								String canonical = fullFile.getCanonicalPath();
								checkPath(canonical, ref);
								// TODO: Disallow size file here
								File parent = fullFile.getParentFile();
								if (!parent.exists()) {
									parent.mkdirs();
								} else if (!parent.isDirectory()) {
									throw new IllegalArgumentException(
											"Parent of '" + ref
													+ "' is not a directory");
								}
								FileOutputStream fout = new FileOutputStream(
										fullFile);
								OutputStream out = new RestrictedOutputStream(
										fout, RestrictedStore.this);
								if (toSubtract != 0) {
									subtractUsedBytes(toSubtract);
								}
								return out;
							} catch (IOException ioe) {
								throw new WrapperException(ioe);
							}
						}
					});
		} catch (WrapperException we) {
			throw (IOException) we.getCause();
		}
	}

	private String getRelativePath(String canonicalPath) {
		String relativePath = canonicalPath.substring(this.baseCanonicalPath
				.length());
		if (relativePath.startsWith(File.separator)) {
			relativePath = relativePath.substring(File.separator.length());
		}
		if (!"/".equals(File.separator)) {
			relativePath = relativePath.replace(File.separatorChar, '/');
		}
		return relativePath;
	}

	public Collection getPaths(String regexp) throws IOException {
		final Pattern pattern = Pattern.compile(regexp);
		try {
			return AccessController
					.doPrivileged(new PrivilegedAction<Collection>() {
						// Reason: Calling getPaths() requires certain file
						// permissions
						// that the caller might not naturally have. Paths are
						// relative to
						// the baseDirectory of the RestrictedStore. The user
						// must have
						// proper hosts privileges to be able to get the
						// RestrictedStore
						// instance.
						public Collection run() {
							try {
								return getPaths(pattern, baseDirectory);
							} catch (IOException ioe) {
								throw new WrapperException(ioe);
							}
						}
					});
		} catch (WrapperException we) {
			throw (IOException) we.getCause();
		}
	}

	private Collection getPaths(Pattern pattern, File directory)
			throws IOException {
		// Security: This method is expected to be private.
		Collection paths = new LinkedList();
		File[] localFiles = directory.listFiles();
		for (int i = 0; i < localFiles.length; i++) {
			File file = localFiles[i];
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
	public long getSpaceLeft() throws IOException {
		return this.quota - this.getSize();
	}

	public void saveObject(String path, Serializable object) throws IOException {
		ManagedFile file = this.getManagedFile(path);
		OutputStream out = file.openOutputStream();
		try {
			ObjectOutputStream oout = new ObjectOutputStream(
					new BufferedOutputStream(out));
			oout.writeObject(object);
			oout.flush();
		} finally {
			out.close();
		}
	}

	public void removeObject(String path) throws IOException {
		final ManagedFile file = this.getManagedFile(path);
		file.delete();
	}

	public Object retrieveObject(String path) throws IOException,
			ClassNotFoundException {
		return this.retrieveObject(path, Thread.currentThread()
				.getContextClassLoader());
	}

	public Object retrieveObject(String path, ClassLoader classLoader)
			throws IOException, ClassNotFoundException {
		ManagedFile file = this.getManagedFile(path);
		try {
			InputStream in = file.openInputStream();
			try {
				ObjectInputStream oin = new ClassLoaderObjectInputStream(in,
						classLoader);
				return (Serializable) oin.readObject();
			} finally {
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
	public ManagedFile getManagedFile(ManagedFile parent, String relativePath)
			throws IOException {
		return new ManagedFileImpl(parent, relativePath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.io.ManagedStore#getManagedFile(java.lang.String)
	 */
	public ManagedFile getManagedFile(String path) throws IOException {
		return new ManagedFileImpl(path);
	}

	public ManagedFile getRootManagedDirectory() throws IOException {
		return new ManagedFileImpl("/");
	}

	private File managedToNative(final String path) throws IOException {
		try {
			return AccessController.doPrivileged(new PrivilegedAction<File>() {
				public File run() {
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
				}
			});
		} catch (WrapperException we) {
			throw (IOException) we.getCause();
		}
	}

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

	private class ManagedFileImpl implements ManagedFile {
		// NOTE: ManagedFileImpl instances should only be allowed
		// to exist in association with a RestrictedStore.
		private final String path;
		private final File nativeFile;

		private ManagedFileImpl(String path) throws IOException {
			this.path = path;
			// Note: managedToNative has a security check.
			this.nativeFile = managedToNative(path);
		}

		private ManagedFileImpl(ManagedFile parent, String relPath)
				throws IOException {
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
		public boolean createNewFile() throws IOException {
			try {
				return AccessController
						.doPrivileged(new PrivilegedAction<Boolean>() {
							// Reason: Should be allowed. Obtaining an instance
							// of ManagedFileImpl
							// requires privileges.
							public Boolean run() {
								try {
									boolean success = nativeFile
											.createNewFile();
									if (success) {
										RestrictedStore.this
												.addUsedBytes(EMPTY_FILE_SIZE);
									}
									return success;
								} catch (IOException ioe) {
									throw new WrapperException(ioe);
								}
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
		public boolean exists() {
			return AccessController
					.doPrivileged(new PrivilegedAction<Boolean>() {
						// Reason: Should be allowed. Obtaining an instance of
						// ManagedFileImpl
						// requires privileges.
						public Boolean run() {
							return nativeFile.exists();
						}
					});
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#getInputStream()
		 */
		public InputStream openInputStream() throws IOException {
			return RestrictedStore.this.getInputStream(this.nativeFile,
					this.path);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#getOutputStream()
		 */
		public OutputStream openOutputStream() throws IOException {
			return RestrictedStore.this.getOutputStream(this.nativeFile,
					this.path);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#getParent()
		 */
		public ManagedFile getParent() throws IOException {
			try {
				return AccessController
						.doPrivileged(new PrivilegedAction<ManagedFile>() {
							// Reason: Should be allowed. Obtaining an instance
							// of ManagedFileImpl
							// requires privileges.
							public ManagedFile run() {
								try {
									File parentFile = nativeFile
											.getParentFile();
									// Note: nativeToManaged checks canonical
									// path for permissions.
									return nativeToManaged(parentFile);
								} catch (IOException ioe) {
									throw new WrapperException(ioe);
								}
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
		public String getPath() {
			return this.path;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#isDirectory()
		 */
		public boolean isDirectory() {
			return AccessController
					.doPrivileged(new PrivilegedAction<Boolean>() {
						// Reason: Should be allowed. Obtaining an instance of
						// ManagedFileImpl
						// requires privileges.
						public Boolean run() {
							return nativeFile.isDirectory();
						}
					});
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#listFiles()
		 */
		public ManagedFile[] listFiles() throws IOException {
			return this.listFiles(null);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.xamjwg.io.ManagedFile#listFiles(org.xamjwg.io.ManagedFileFilter)
		 */
		public ManagedFile[] listFiles(final ManagedFileFilter filter)
				throws IOException {
			try {
				return AccessController
						.doPrivileged(new PrivilegedAction<ManagedFile[]>() {
							// Reason: Should be allowed. Obtaining an instance
							// of ManagedFileImpl
							// requires privileges.
							public ManagedFile[] run() {
								try {
									File[] files = nativeFile.listFiles();
									List<ManagedFile> mfs = new ArrayList<ManagedFile>();
									for (int i = 0; i < files.length; i++) {
										File file = files[i];
										ManagedFile mf = nativeToManaged(file);
										if (filter == null || filter.accept(mf)) {
											mfs.add(mf);
										}
									}
									return mfs.toArray(new ManagedFile[0]);
								} catch (IOException ioe) {
									throw new WrapperException(ioe);
								}
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
		public boolean mkdir() {
			return AccessController
					.doPrivileged(new PrivilegedAction<Boolean>() {
						// Reason: Should be allowed. Obtaining an instance of
						// ManagedFileImpl
						// requires privileges.
						public Boolean run() {
							boolean success = nativeFile.mkdir();
							if (success) {
								try {
									RestrictedStore.this
											.addUsedBytes(DIRECTORY_SIZE);
								} catch (IOException ioe) {
									// Ignore
								}
							}
							return success;
						}
					});
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#mkdirs()
		 */
		public boolean mkdirs() {
			return AccessController
					.doPrivileged(new PrivilegedAction<Boolean>() {
						// Reason: Should be allowed. Obtaining an instance of
						// ManagedFileImpl
						// requires privileges.
						public Boolean run() {
							boolean success = nativeFile.mkdirs();
							if (success) {
								try {
									RestrictedStore.this
											.addUsedBytes(DIRECTORY_SIZE);
								} catch (IOException ioe) {
									// Ignore
								}
							}
							return success;
						}
					});
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.io.ManagedFile#delete()
		 */
		public boolean delete() throws IOException {
			try {
				return AccessController
						.doPrivileged(new PrivilegedAction<Boolean>() {
							// Reason: Should be allowed. Obtaining an instance
							// of ManagedFileImpl
							// requires privileges.
							public Boolean run() {
								try {
									long prevLength = nativeFile.length()
											+ EMPTY_FILE_SIZE;
									if (nativeFile.delete()) {
										subtractUsedBytes(prevLength);
										return true;
									} else {
										return false;
									}
								} catch (IOException ioe) {
									throw new WrapperException(ioe);
								}
							}
						});
			} catch (WrapperException we) {
				throw (IOException) we.getCause();
			}
		}
	}
}
