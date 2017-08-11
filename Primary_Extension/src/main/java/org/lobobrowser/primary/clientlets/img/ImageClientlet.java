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

package org.lobobrowser.primary.clientlets.img;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.html.dombl.SVGRasterizer;
import org.lobobrowser.util.io.IORoutines;

/**
 * The Class ImageClientlet.
 */
public class ImageClientlet implements Clientlet {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ImageClientlet.class);

	@Override
	public void process(ClientletContext context) throws ClientletException {
		ClientletResponse response = context.getResponse();
		String mimeType = response.getMimeType();
		int contentLength = response.getContentLength();
		URL url = response.getResponseURL();
		Image image = null;

		if (url != null && url.toString().endsWith(".svg")) {

			SVGRasterizer r = new SVGRasterizer(url);
			image = Toolkit.getDefaultToolkit().createImage(r.createBufferedImage().getSource());

		} else {

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
			if (logger.isInfoEnabled()) {
				logger.info("process(): Loaded " + imageBytes.length + " bytes.");
			}
			image = Toolkit.getDefaultToolkit().createImage(imageBytes);

		}

		context.setResultingContent(new ImageContent(image, mimeType));
	}
}
