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
package org.loboevolution.primary.clientlets.img;

import java.awt.Component;
import java.awt.Image;

import javax.swing.JScrollPane;

import org.loboevolution.clientlet.ComponentContent;

/**
 * The Class ImageContent.
 *
 * @author Administrator
 */
/**
 * The Class ImageContent.
 */
public class ImageContent implements ComponentContent {

	/** The image. */
	private transient Image image;

	/** The mime type. */
	private final String mimeType;

	/** The scroll pane. */
	private final JScrollPane scrollPane;

	/**
	 * Instantiates a new image content.
	 *
	 * @param image
	 *            the image
	 * @param mimeType
	 *            the mime type
	 */
	public ImageContent(Image image, String mimeType) {
		ImageScrollable is = new ImageScrollable(image);
		JScrollPane sp = new JScrollPane(is);
		this.scrollPane = sp;
		this.image = image;
		this.mimeType = mimeType;
	}

	@Override
	public void addNotify() {
		// Method not implemented
	}

	@Override
	public boolean canCopy() {
		// TODO: Support image copy?
		return false;
	}

	@Override
	public boolean copy() {
		return false;
	}

	@Override
	public Component getComponent() {
		return this.scrollPane;
	}

	@Override
	public Object getContentObject() {
		return this.image;
	}

	@Override
	public String getDescription() {
		return this.image.toString();
	}

	@Override
	public String getMimeType() {
		return this.mimeType;
	}

	@Override
	public String getSourceCode() {
		return null;
	}

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public void removeNotify() {
		this.image.flush();
	}

	@Override
	public void setProperty(String name, Object value) {
		// Method not implemented
	}
}
