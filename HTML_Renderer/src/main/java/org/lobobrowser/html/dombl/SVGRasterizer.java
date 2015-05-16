package org.lobobrowser.html.dombl;

import java.awt.Image;
import java.awt.Paint;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

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
    private static final Logger logger = Logger.getLogger(SVGRasterizer.class.getName());

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
			logger.severe(e.getMessage());
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
	 * Sets the width of the image to rasterize.
	 *
	 * @param width
	 *            the image width
	 */
	public void setImageWidth(float width) {
		hints.put(ImageTranscoder.KEY_WIDTH, new Float(width));
	}

	/**
	 * Sets the height of the image to rasterize.
	 *
	 * @param width
	 *            the image height
	 */
	public void setImageHeight(float height) {
		hints.put(ImageTranscoder.KEY_HEIGHT, new Float(height));
	}

	/**
	 * Sets the preferred language to use. SVG documents can provide text in
	 * multiple languages, this method lets you control which language to use if
	 * possible. e.g. "en" for english or "fr" for french.
	 *
	 * @param language
	 *            the preferred language to use
	 */
	public void setLanguages(String language) {
		hints.put(ImageTranscoder.KEY_LANGUAGE, language);
	}

	/**
	 * Sets the unit conversion factor to the specified value. This method lets
	 * you choose how units such as 'em' are converted. e.g. 0.26458 is 96dpi
	 * (the default) or 0.3528 is 72dpi.
	 *
	 * @param px2mm
	 *            the pixel to millimeter convertion factor.
	 */
	public void setPixelToMMFactor(float px2mm) {
		hints.put(ImageTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER, new Float(px2mm));
	}

	/**
	 * Sets the uri of the user stylesheet. The user stylesheet can be used to
	 * override styles.
	 *
	 * @param uri
	 *            the uri of the user stylesheet
	 */
	public void setUserStyleSheetURI(String uri) {
		hints.put(ImageTranscoder.KEY_USER_STYLESHEET_URI, uri);
	}

	/**
	 * Sets whether or not the XML parser used to parse SVG document should be
	 * validating or not, depending on the specified parameter. For futher
	 * details about how media work, see the <a
	 * href="http://www.w3.org/TR/CSS2/media.html">Media types in the CSS2
	 * specification</a>.
	 *
	 * @param b
	 *            true means the XML parser will validate its input
	 */
	public void setXMLParserValidating(boolean b) {
		hints.put(ImageTranscoder.KEY_XML_PARSER_VALIDATING, (b ? Boolean.TRUE
				: Boolean.FALSE));
	}

	/**
	 * Sets the media to rasterize. The medium should be separated by comma.
	 * e.g. "screen", "print" or "screen, print"
	 *
	 * @param media
	 *            the media to use
	 */
	public void setMedia(String media) {
		hints.put(ImageTranscoder.KEY_MEDIA, media);
	}

	/**
	 * Sets the alternate stylesheet to use. For futher details, you can have a
	 * look at the <a href="http://www.w3.org/TR/xml-stylesheet/">Associating
	 * Style Sheets with XML documents</a>.
	 *
	 * @param alternateStylesheet
	 *            the alternate stylesheet to use if possible
	 */
	public void setAlternateStylesheet(String alternateStylesheet) {
		hints.put(ImageTranscoder.KEY_ALTERNATE_STYLESHEET, alternateStylesheet);
	}

	/**
	 * Sets the Paint to use for the background of the image.
	 *
	 * @param p
	 *            the paint to use for the background
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
