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

package org.lobobrowser.primary.clientlets.img;

import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JScrollPane;

import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ComponentContent;
import org.lobobrowser.html.dombl.SVGRasterizer;
import org.lobobrowser.util.io.IORoutines;

/**
 * The Class ImageClientlet.
 */
public class ImageClientlet implements Clientlet {

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(ImageClientlet.class.getName());
    
    /**
     * Instantiates a new image clientlet.
     */
    public ImageClientlet() {
        super();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.clientlet.Clientlet#process(org.lobobrowser.clientlet.
     * ClientletContext)
     */
    @Override
	public void process(ClientletContext context) throws ClientletException {
		ClientletResponse response = context.getResponse();
		String mimeType = response.getMimeType();
		int contentLength = response.getContentLength();
		URL url = response.getResponseURL();
		Image image = null;
				
		if (url != null && url.toString().contains("svg")) {

			SVGRasterizer r = new SVGRasterizer(url);
			image  = Toolkit.getDefaultToolkit().createImage(r.createBufferedImage().getSource());
		
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
			if (logger.isLoggable(Level.INFO)) {
				logger.info("process(): Loaded " + imageBytes.length
						+ " bytes.");
			}
			image = Toolkit.getDefaultToolkit().createImage(imageBytes);

		}

		context.setResultingContent(new ImageContent(image, mimeType));
	}

    /**
     * The Class ImageContent.
     */
    private static class ImageContent implements ComponentContent {

        /** The image. */
        private final Image image;

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

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.ComponentContent#addNotify()
         */
        @Override
        public void addNotify() {
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.ComponentContent#canCopy()
         */
        @Override
        public boolean canCopy() {
            // TODO: Support image copy?
            return false;
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.ComponentContent#copy()
         */
        @Override
        public boolean copy() {
            return false;
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.ComponentContent#getComponent()
         */
        @Override
        public Component getComponent() {
            return this.scrollPane;
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.ComponentContent#getContentObject()
         */
        @Override
        public Object getContentObject() {
            return this.image;
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.ComponentContent#getDescription()
         */
        @Override
        public String getDescription() {
            return this.image.toString();
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.ComponentContent#getMimeType()
         */
        @Override
        public String getMimeType() {
            return this.mimeType;
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.ComponentContent#getSourceCode()
         */
        @Override
        public String getSourceCode() {
            return null;
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.ComponentContent#getTitle()
         */
        @Override
        public String getTitle() {
            return null;
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.ComponentContent#removeNotify()
         */
        @Override
        public void removeNotify() {
            this.image.flush();
        }

        /*
         * (non-Javadoc)
         * @see org.lobobrowser.clientlet.ComponentContent#setProperty(java.lang.String,
         * java.lang.Object)
         */
        @Override
        public void setProperty(String name, Object value) {
            // NOP
        }
    }
}
