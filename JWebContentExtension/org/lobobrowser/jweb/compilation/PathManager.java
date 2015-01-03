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

public class PathManager implements RemovalListener {
	private static final Logger logger = Logger.getLogger(PathManager.class
			.getName());
	private static final PathManager instance = new PathManager();
	private final LRUCache cache = new LRUCache(3);

	public PathManager() {
		this.cache.addRemovalListener(this);
	}

	public static PathManager getInstance() {
		return instance;
	}

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

	public static boolean isNameCompatible(String simpleName, Kind kind) {
		if (kind == Kind.CLASS) {
			return simpleName.toLowerCase().endsWith(".class");
		} else if (kind == Kind.SOURCE) {
			String sntl = simpleName.toLowerCase();
			return sntl.endsWith(".java") || sntl.endsWith(".fx");
		} else {
			throw new java.lang.UnsupportedOperationException("simpleName="
					+ simpleName + ",kind=" + kind);
		}
	}

	public void removed(RemovalEvent event) {
		try {
			PathRepository pr = (PathRepository) event.valueRemoved;
			pr.close();
		} catch (Exception err) {
			logger.log(Level.SEVERE, "removed()", err);
		}
	}

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
