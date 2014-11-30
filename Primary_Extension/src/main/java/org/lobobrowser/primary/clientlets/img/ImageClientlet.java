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

package org.lobobrowser.primary.clientlets.img;

import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JScrollPane;

import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ComponentContent;
import org.lobobrowser.util.io.IORoutines;

public class ImageClientlet implements Clientlet {
	private static final Logger logger = Logger.getLogger(ImageClientlet.class
			.getName());

	public ImageClientlet() {
		super();
	}

	public void process(ClientletContext context) throws ClientletException {
		ClientletResponse response = context.getResponse();
		String mimeType = response.getMimeType();
		int contentLength = response.getContentLength();
		byte[] imageBytes;
		try {
			InputStream in = response.getInputStream();
			if (contentLength == -1) {
				imageBytes = IORoutines.load(in);
			} else {
				imageBytes = IORoutines.loadExact(in, contentLength);
			}
		} catch (IOException ioe) {
			throw new ClientletException(ioe);
		}
		if (logger.isLoggable(Level.INFO)) {
			logger.info("process(): Loaded " + imageBytes.length + " bytes.");
		}
		Image image = Toolkit.getDefaultToolkit().createImage(imageBytes);
		context.setResultingContent(new ImageContent(image, mimeType));
	}

	private static class ImageContent implements ComponentContent {
		private final Image image;
		private final String mimeType;
		private final JScrollPane scrollPane;

		public ImageContent(Image image, String mimeType) {
			ImageScrollable is = new ImageScrollable(image);
			JScrollPane sp = new JScrollPane(is);
			this.scrollPane = sp;
			this.image = image;
			this.mimeType = mimeType;
		}

		public void addNotify() {
		}

		public boolean canCopy() {
			// TODO: Support image copy?
			return false;
		}

		public boolean copy() {
			return false;
		}

		public Component getComponent() {
			return this.scrollPane;
		}

		public Object getContentObject() {
			return this.image;
		}

		public String getDescription() {
			return this.image.toString();
		}

		public String getMimeType() {
			return this.mimeType;
		}

		public String getSourceCode() {
			return null;
		}

		public String getTitle() {
			return null;
		}

		public void removeNotify() {
			this.image.flush();
		}

		public void setProperty(String name, Object value) {
			// NOP
		}
	}
}
