/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.jweb.compilation;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject.Kind;

import org.lobobrowser.util.LRUCache;
import org.lobobrowser.util.RemovalEvent;
import org.lobobrowser.util.RemovalListener;
import org.lobobrowser.util.Urls;


/**
 * The Class PathManager.
 */
public class PathManager implements RemovalListener {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(PathManager.class
			.getName());
	
	/** The Constant instance. */
	private static final PathManager instance = new PathManager();
	
	/** The cache. */
	private final LRUCache cache = new LRUCache(3);

	/**
	 * Instantiates a new path manager.
	 */
	public PathManager() {
		this.cache.addRemovalListener(this);
	}

	/**
	 * Gets the single instance of PathManager.
	 *
	 * @return single instance of PathManager
	 */
	public static PathManager getInstance() {
		return instance;
	}

	/**
	 * Gets the path repository.
	 *
	 * @param paths the paths
	 * @param entryList the entry list
	 * @return the path repository
	 */
	public PathRepository getPathRepository(URL[] paths, String[] entryList) {
		Object key = getKey(paths, entryList);
		synchronized (this.cache) {
			PathRepository pr = (PathRepository) this.cache.get(key);
			if (pr == null) {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("getPathRepository(): Constructing new repository for "
							+ Arrays.asList(paths) + ".");
				}
				pr = new PathRepository(paths, entryList == null, entryList);
				this.cache.put(key, pr, 1);
			}
			return pr;
		}
	}

	/**
	 * Gets the key.
	 *
	 * @param paths the paths
	 * @param entryList the entry list
	 * @return the key
	 */
	private static Object getKey(URL[] paths, String[] entryList) {
		// Needs to be a list and not a set, because
		// order of items in classpath matters.
		ArrayList<String> set = new ArrayList<String>();
		for (URL url : paths) {
			set.add(Urls.getNoRefForm(url));
		}
		if (entryList != null) {
			set.add("|");
			for (String entry : entryList) {
				set.add(entry);
			}
		}
		return set;
	}

	/**
	 * Checks if is name compatible.
	 *
	 * @param simpleName the simple name
	 * @param kind the kind
	 * @return true, if is name compatible
	 */
	public static boolean isNameCompatible(String simpleName, Kind kind) {
		if (kind == Kind.CLASS) {
			return simpleName.toLowerCase().endsWith(".class");
		} else if (kind == Kind.SOURCE) {
			String sntl = simpleName.toLowerCase();
			return sntl.endsWith(".java") || sntl.endsWith(".fx");
		} else {
			throw new UnsupportedOperationException("simpleName="
					+ simpleName + ",kind=" + kind);
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.util.RemovalListener#removed(org.lobobrowser.util.RemovalEvent)
	 */
	public void removed(RemovalEvent event) {
		try {
			PathRepository pr = (PathRepository) event.valueRemoved;
			pr.close();
		} catch (Exception err) {
			logger.log(Level.SEVERE, "removed()", err);
		}
	}

	/**
	 * Gets the nesting kind for name.
	 *
	 * @param fileNameTL the file name tl
	 * @return the nesting kind for name
	 */
	static NestingKind getNestingKindForName(String fileNameTL) {
		String suffix = ".class";
		if (!fileNameTL.endsWith(suffix)) {
			return NestingKind.LOCAL;
		}
		String nameTL = fileNameTL.substring(0,
				fileNameTL.length() - suffix.length());
		int lastDollarIdx = nameTL.lastIndexOf('$');
		if (lastDollarIdx == -1) {
			return NestingKind.TOP_LEVEL;
		} else {
			String rest = nameTL.substring(lastDollarIdx + 1);
			try {
				Integer.parseInt(rest);
				return NestingKind.ANONYMOUS;
			} catch (NumberFormatException nfe) {
				return NestingKind.MEMBER;
			}
		}
	}
}
