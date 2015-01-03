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

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;

import org.lobobrowser.clientlet.ClientletContext;

public class URLJavaFileObject extends URLFileObject implements JavaFileObject {
	private final Kind kind;
	private final NestingKind nestingKind;

	public URLJavaFileObject(ClientletContext context, URL url,
			String fullName, Kind kind, NestingKind nestingKind) {
		super(context, url, fullName);
		this.kind = kind;
		this.nestingKind = nestingKind;
	}

	public Modifier getAccessLevel() {
		return Modifier.PUBLIC;
	}

	public Kind getKind() {
		return this.kind;
	}

	public NestingKind getNestingKind() {
		return this.nestingKind;
	}

	public boolean isNameCompatible(String simpleName, Kind kind) {
		return PathManager.isNameCompatible(simpleName, kind);
	}
}
