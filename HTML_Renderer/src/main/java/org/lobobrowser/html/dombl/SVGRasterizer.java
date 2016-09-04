/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.dombl;

import java.awt.Image;
import java.awt.Paint;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscodingHints;
import org.apache.batik.transcoder.image.ImageTranscoder;

/**
 * This class provides a simple and method based API for converting a SVG
 * document fragment to a <tt>BufferedImage</tt>.
 *
 * @author <a href="mailto:Thierry.Kormann@sophia.inria.fr">Thierry Kormann</a>
 * @version $Id$
 */
public class SVGRasterizer {
	
	/** The Constant logger. */
    private static final Logger logger = LogManager.getLogger(SVGRasterizer.class.getName());

	/**
	 * The transcoder input.
	 */
	protected TranscoderInput input;

	/**
	 * The transcoder hints.
	 */
	protected TranscodingHints hints = new TranscodingHints();

	/**
	 * The image that represents the SVG document.
	 */
	protected BufferedImage img;

	/**
	 * Constructs a new SVGRasterizer.
	 *
	 * @param uri
	 *            the uri of the document to rasterize
	 */
	public SVGRasterizer(String uri) {
		this.input = new TranscoderInput(uri);
	}

	/**
	 * Constructs a new SVGRasterizer.
	 *
	 * @param url
	 *            the URL of the document to rasterize
	 */
	public SVGRasterizer(URL url) {
		this.input = new TranscoderInput(url.toString());
	}

	/**
	 * Constructs a new SVGRasterizer converter.
	 *
	 * @param istream
	 *            the input stream that represents the SVG document to rasterize
	 */
	public SVGRasterizer(InputStream istream) {
		this.input = new TranscoderInput(istream);
	}

	/**
	 * Constructs a new SVGRasterizer converter.
	 *
	 * @param reader
	 *            the reader that represents the SVG document to rasterize
	 */
	public SVGRasterizer(Reader reader) {
		this.input = new TranscoderInput(reader);
	}

	/**
	 * Constructs a new SVGRasterizer converter.
	 *
	 * @param document
	 *            the SVG document to rasterize
	 */
	/*public SVGRasterizer(SVGDocument document) {
		this.input = new TranscoderInput(document);//TODO
	}*/

	/**
	 * Returns the image that represents the SVG document.
	 */
	public BufferedImage createBufferedImage() {
		Rasterizer r = new Rasterizer();
		r.setTranscodingHints((Map) hints);
		try {
			r.transcode(input, null);
		} catch (TranscoderException e) {
			logger.error(e.getMessage());
		}
		return img;
	}
	
	
	/**
	 * Returns the image
	 */
	public Image bufferedImageToImage() throws TranscoderException {
		BufferedImage img = createBufferedImage();
		return Toolkit.getDefaultToolkit().createImage(img.getSource());
	}

	/**
	 * Sets the image width.
	 *
	 * @param width
	 *            the new image width
	 */
	public void setImageWidth(float width) {
		hints.put(ImageTranscoder.KEY_WIDTH, new Float(width));
	}

	/**
	 * Sets the image height.
	 *
	 * @param height
	 *            the new image height
	 */
	public void setImageHeight(float height) {
		hints.put(ImageTranscoder.KEY_HEIGHT, new Float(height));
	}

	/**
	 * Sets the languages.
	 *
	 * @param language
	 *            the new languages
	 */
	public void setLanguages(String language) {
		hints.put(ImageTranscoder.KEY_LANGUAGE, language);
	}

	/**
	 * Sets the pixel to mm factor.
	 *
	 * @param px2mm
	 *            the new pixel to mm factor
	 */
	public void setPixelToMMFactor(float px2mm) {
		hints.put(ImageTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER, new Float(px2mm));
	}

	/**
	 * Sets the user style sheet uri.
	 *
	 * @param uri
	 *            the new user style sheet uri
	 */
	public void setUserStyleSheetURI(String uri) {
		hints.put(ImageTranscoder.KEY_USER_STYLESHEET_URI, uri);
	}

	/**
	 * Sets the XML parser validating.
	 *
	 * @param b
	 *            the new XML parser validating
	 */
	public void setXMLParserValidating(boolean b) {
		hints.put(ImageTranscoder.KEY_XML_PARSER_VALIDATING, (b ? Boolean.TRUE
				: Boolean.FALSE));
	}

	/**
	 * Sets the media.
	 *
	 * @param media
	 *            the new media
	 */
	public void setMedia(String media) {
		hints.put(ImageTranscoder.KEY_MEDIA, media);
	}

	/**
	 * Sets the alternate stylesheet.
	 *
	 * @param alternateStylesheet
	 *            the new alternate stylesheet
	 */
	public void setAlternateStylesheet(String alternateStylesheet) {
		hints.put(ImageTranscoder.KEY_ALTERNATE_STYLESHEET, alternateStylesheet);
	}

	/**
	 * Sets the background color.
	 *
	 * @param p
	 *            the new background color
	 */
	public void setBackgroundColor(Paint p) {
		hints.put(ImageTranscoder.KEY_BACKGROUND_COLOR, p);
	}

	/**
	 * An image transcoder that stores the resulting image.
	 */
	protected class Rasterizer extends ImageTranscoder {

		public BufferedImage createImage(int w, int h) {
			return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		}

		public void writeImage(BufferedImage img, TranscoderOutput output)
				throws TranscoderException {
			SVGRasterizer.this.img = img;
		}
	}
}
