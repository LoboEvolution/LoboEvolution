/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.pdfview;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.loboevolution.pdfview.colorspace.AlternateColorSpace;
import org.loboevolution.pdfview.colorspace.IndexedColor;
import org.loboevolution.pdfview.colorspace.PDFColorSpace;
import org.loboevolution.pdfview.colorspace.YCCKColorSpace;
import org.loboevolution.pdfview.decode.PDFDecoder;
import org.loboevolution.pdfview.function.FunctionType0;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataFormatImpl;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * Encapsulates a PDF Image
 */
@Data
public class PDFImage {

    private static final int[][] GREY_TO_ARGB = new int[8][];
    /**
     * the actual image data
     */
    private final PDFObject imageObj;
    /**
     * true if the image is in encoded in JPEG
     */
    private final boolean jpegDecode;
    /**
     * color key mask. Array of start/end pairs of ranges of color components to
     * mask out. If a component falls within any of the ranges it is clear.
     */
    private int[] colorKeyMask = null;
    /**
     * the width of this image in pixels
     */
    private int width;
    /**
     * the height of this image in pixels
     */
    private int height;
    /**
     * the colorspace to interpret the samples in
     */
    private PDFColorSpace colorSpace;
    /**
     * the number of bits per sample component
     */
    private int bitsPerComponent;
    /**
     * whether this image is a mask or not
     */
    @Getter
    @Setter
    private boolean imageMask = false;
    /**
     * the SMask image, if any
     */
    private PDFImage sMask;
    /**
     * the decode array
     */
    private float[] decode;

    /**
     * Create an instance of a PDFImage
     *
     * @param imageObj a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    protected PDFImage(final PDFObject imageObj) throws IOException {
        this.imageObj = imageObj;
        this.jpegDecode = PDFDecoder.isLastFilter(imageObj, PDFDecoder.DCT_FILTERS);
    }

    /**
     * Read a PDFImage from an image dictionary and stream
     *
     * @param obj        the PDFObject containing the image's dictionary and stream
     * @param resources  the current resources
     * @param useAsSMask - flag for switching colors in case image is used as sMask
     *                   internally this is needed for handling transparency in smask
     *                   images.
     * @return a {@link org.loboevolution.pdfview.PDFImage} object.
     * @throws java.io.IOException if any.
     */
    public static PDFImage createImage(final PDFObject obj, final Map<String, PDFObject> resources, final boolean useAsSMask)
            throws IOException {
        // create the image
        final PDFImage image = new PDFImage(obj);

        // get the width (required)
        final PDFObject widthObj = obj.getDictRef("Width");
        if (widthObj == null) {
            throw new PDFParseException("Unable to read image width: " + obj);
        }
        image.setWidth(widthObj.getIntValue());

        // get the height (required)
        final PDFObject heightObj = obj.getDictRef("Height");
        if (heightObj == null) {
            throw new PDFParseException("Unable to get image height: " + obj);
        }
        image.setHeight(heightObj.getIntValue());

        // figure out if we are an image mask (optional)
        final PDFObject imageMaskObj = obj.getDictRef("ImageMask");
        if (imageMaskObj != null) {
            image.setImageMask(imageMaskObj.getBooleanValue());
        }
        // read the bpc and colorspace (required except for masks)
        if (image.isImageMask()) {
            image.setBitsPerComponent(1);
            // create the indexed color space for the mask
            // [PATCHED by michal.busta@gmail.com] - default value od Decode
            // according to PDF spec. is [0, 1]
            // so the color arry should be:
            // [PATCHED by XOND] - switched colors in case the image is used as
            // SMask for another image, otherwise transparency isn't
            // handled correctly.
            Color[] colors = useAsSMask ? new Color[]{Color.WHITE, Color.BLACK}
                    : new Color[]{Color.BLACK, Color.WHITE};
            final PDFObject imageMaskDecode = obj.getDictRef("Decode");
            if (imageMaskDecode != null) {
                final PDFObject[] decodeArray = imageMaskDecode.getArray();
                final float decode0 = decodeArray[0].getFloatValue();
                if (decode0 == 1.0f) {
                    colors = useAsSMask ? new Color[]{Color.BLACK, Color.WHITE}
                            : new Color[]{Color.WHITE, Color.BLACK};
                }

                /*
                 * float[] decode = new float[decodeArray.length]; for (int i =
                 * 0; i < decodeArray.length; i++) { decode[i] =
                 * decodeArray[i].getFloatValue(); } image.setDecode(decode);
                 */
            }

            image.setColorSpace(new IndexedColor(colors));
        } else {
            // get the bits per component (required)
            final PDFObject bpcObj = obj.getDictRef("BitsPerComponent");
            if (bpcObj == null) {
                throw new PDFParseException("Unable to get bits per component: " + obj);
            }
            image.setBitsPerComponent(bpcObj.getIntValue());

            // get the color space (required)
            final PDFObject csObj = obj.getDictRef("ColorSpace");
            if (csObj == null) {
                throw new PDFParseException("No ColorSpace for image: " + obj);
            }

            final PDFColorSpace cs = PDFColorSpace.getColorSpace(csObj, resources);
            image.setColorSpace(cs);

            // read the decode array
            final PDFObject decodeObj = obj.getDictRef("Decode");
            if (decodeObj != null) {
                final PDFObject[] decodeArray = decodeObj.getArray();

                final float[] decode = new float[decodeArray.length];
                for (int i = 0; i < decodeArray.length; i++) {
                    decode[i] = decodeArray[i].getFloatValue();
                }

                image.setDecode(decode);
            }

            // read the soft mask.
            // If ImageMask is true, this entry must not be present.
            // (See implementation note 52 in Appendix H.)
            PDFObject sMaskObj = obj.getDictRef("SMask");
            if (sMaskObj == null) {
                // try the explicit mask, if there is no SoftMask
                sMaskObj = obj.getDictRef("Mask");
            }

            if (sMaskObj != null) {
                if (sMaskObj.getType() == PDFObject.STREAM) {
                    try {
                        final PDFImage sMaskImage = PDFImage.createImage(sMaskObj, resources, true);
                        image.setSMask(sMaskImage);
                    } catch (final IOException ex) {
                        PDFDebugger.debug("ERROR: there was a problem parsing the mask for this object");
                        PDFDebugger.dump(obj);
                        BaseWatchable.getErrorHandler().publishException(ex);
                    }
                } else if (sMaskObj.getType() == PDFObject.ARRAY) {
                    // retrieve the range of the ColorKeyMask
                    // colors outside this range will not be painted.
                    try {
                        image.setColorKeyMask(sMaskObj);
                    } catch (final IOException ex) {
                        PDFDebugger.debug("ERROR: there was a problem parsing the color mask for this object");
                        PDFDebugger.dump(obj);
                        BaseWatchable.getErrorHandler().publishException(ex);
                    }
                }
            }
        }

        return image;
    }

    private static int[] getGreyToArgbMap(final int numBits) {
        assert numBits <= 8;
        int[] argbVals = GREY_TO_ARGB[numBits - 1];
        if (argbVals == null) {
            argbVals = createGreyToArgbMap(numBits);
        }
        return argbVals;
    }

    /**
     * Create a map from all bit-patterns of a certain depth greyscale to the
     * corresponding sRGB values via the ICC colorr converter.
     *
     * @param numBits the number of greyscale bits
     * @return a 2^bits array of standard 32-bit ARGB fits for each greyscale
     * value at that bitdepth
     */
    private static int[] createGreyToArgbMap(final int numBits) {
        final ColorSpace greyCs = PDFColorSpace.getColorSpace(PDFColorSpace.COLORSPACE_GRAY).getColorSpace();

        final byte[] greyVals = new byte[1 << numBits];
        for (int i = 0; i < greyVals.length; ++i) {
            greyVals[i] = (byte) (i & 0xFF);
        }

        final int[] argbVals = new int[greyVals.length];
        final int mask = (1 << numBits) - 1;
        final WritableRaster inRaster = Raster.createPackedRaster(new DataBufferByte(greyVals, greyVals.length),
                greyVals.length, 1, greyVals.length, new int[]{mask}, null);

        final BufferedImage greyImage = new BufferedImage(new PdfComponentColorModel(greyCs, new int[]{numBits}),
                inRaster, false, null);

        final ColorModel ccm = ColorModel.getRGBdefault();
        final WritableRaster outRaster = Raster.createPackedRaster(new DataBufferInt(argbVals, argbVals.length),
                argbVals.length, 1, argbVals.length, ((PackedColorModel) ccm).getMasks(), null);
        final BufferedImage srgbImage = new BufferedImage(ccm, outRaster, false, null);

        final ColorConvertOp op = new ColorConvertOp(greyCs, ColorSpace.getInstance(ColorSpace.CS_sRGB), null);

        op.filter(greyImage, srgbImage);

        GREY_TO_ARGB[numBits - 1] = argbVals;
        return argbVals;
    }

    /**
     * Get the image that this PDFImage generates.
     *
     * @return a buffered image containing the decoded image data
     * @throws org.loboevolution.pdfview.PDFImageParseException if any.
     */
    public BufferedImage getImage() throws PDFImageParseException {
        try {
            BufferedImage bi = (BufferedImage) this.imageObj.getCache();

            if (bi == null) {
                final byte[] data = imageObj.getStream();
                ByteBuffer jpegBytes = null;
                if (this.jpegDecode) {
                    // if we're lucky, the stream will have just the DCT
                    // filter applied to it, and we'll have a reference to
                    // an underlying mapped file, so we'll manage to avoid
                    // a copy of the encoded JPEG bytes
                    jpegBytes = imageObj.getStreamBuffer(PDFDecoder.DCT_FILTERS);
                }
                // parse the stream data into an actual image
                bi = parseData(data, jpegBytes);
                this.imageObj.setCache(bi);
            }
            return bi;
        } catch (final IOException ioe) {
            // let the caller know that there was a problem parsing the image
            throw new PDFImageParseException("Error reading image: " + ioe.getMessage(), ioe);
        }
    }

    /**
     * <p>
     * Parse the image stream into a buffered image. Note that this is
     * guaranteed to be called after all the other setXXX methods have been
     * called.
     * </p>
     *
     * <p>
     * NOTE: the color convolving is extremely slow on large images. It would be
     * good to see if it could be moved out into the rendering phases, where we
     * might be able to scale the image down first. </p>
     *
     * @param data     the data when already completely filtered and uncompressed
     * @param jpegData a byte buffer if data still requiring the DCDTecode filter is
     *                 being used
     * @return a {@link java.awt.image.BufferedImage} object.
     * @throws java.io.IOException if any.
     */
    protected BufferedImage parseData(final byte[] data, ByteBuffer jpegData) throws IOException {
        // pick a color model, based on the number of components and
        // bits per component
        ColorModel cm = createColorModel();

        BufferedImage bi = null;
        if (jpegData != null) {

            // Use imageio to decode the JPEG into
            // a BufferedImage. Perhaps JAI will be installed
            // so that decodes will be faster and better supported

            // TODO - strictly speaking, the application of the YUV->RGB
            // transformation when reading JPEGs does not adhere to the spec.
            // We're just going to let java read this in - as it is, the
            // standard
            // jpeg reader looks for the specific Adobe marker header so that
            // it may apply the transform, so that's good. If that marker
            // isn't present, then it also applies a number of other heuristics
            // to determine whether the transform should be applied.
            // (http://java.sun.com/javase/6/docs/api/javax/imageio/metadata/doc-files/jpeg_metadata.html)
            // In practice, it probably almost always does the right thing here,
            // though note that the present or default value of the
            // ColorTransform
            // dictionary entry is not being observed, so there is scope for
            // error. Hopefully the JAI reader does the same.

            // We might need to attempt this with multiple readers, so let's
            // remember where the jpeg data starts
            jpegData.mark();

            JpegDecoder decoder = new JpegDecoder(jpegData, cm);

            IOException decodeEx = null;
            try {
                bi = decoder.decode();
            } catch (final IOException e) {
                decodeEx = e;
                // The native readers weren't able to process the image.
                // One common situation is that the image is YCCK/CMYK encoded,
                // which isn't supported by the default jpeg readers.
                // We've got a work-around we can attempt, though:
                decoder.ycckcmykDecodeMode(true);
                try {
                    bi = decoder.decode();
                } catch (final IOException e2) {
                    // It probably wasn't the YCCK/CMYK issue!
                    // try the "old" implementation
                    bi = parseData(data, null);
                    return bi;
                }
            }

            // the decoder may have requested installation of a new color model
            cm = decoder.getColorModel();

            // make these immediately unreachable, as the referenced
            // jpeg data might be quite large
            jpegData = null;
            decoder = null;

            if (bi == null) {
                // This isn't pretty, but it's what's been happening
                // previously, so we'll preserve it for the time
                // being. At least we'll offer a hint now!
                assert decodeEx != null;
                throw new IOException(decodeEx.getMessage() + ". Maybe installing JAI for expanded image format "
                        + "support would help?", decodeEx);
            }
        } else {
            // create the data buffer
            DataBuffer db = new DataBufferByte(data, data.length);

            // pick a color model, based on the number of components and
            // bits per component
            cm = getColorModel();

            // create a compatible raster
            final SampleModel sm = cm.createCompatibleSampleModel(getWidth(), getHeight());
            WritableRaster raster;
            try {
                raster = Raster.createWritableRaster(sm, db, new Point(0, 0));
            } catch (final RasterFormatException e) {
                int tempExpectedSize = getWidth() * getHeight() * getColorSpace().getNumComponents()
                        * Math.max(8, getBitsPerComponent()) / 8;

                if (tempExpectedSize < 3) {
                    tempExpectedSize = 3;
                }
                if (tempExpectedSize > data.length) {
                    final byte[] tempLargerData = new byte[tempExpectedSize];
                    System.arraycopy(data, 0, tempLargerData, 0, data.length);
                    db = new DataBufferByte(tempLargerData, tempExpectedSize);
                    raster = Raster.createWritableRaster(sm, db, new Point(0, 0));
                } else {
                    throw e;
                }
            }

            /*
             * Workaround for a bug on the Mac -- a class cast exception in
             * drawImage() due to the wrong data buffer type (?)
             */
            bi = null;
            if (cm instanceof IndexColorModel) {
                final IndexColorModel icm = (IndexColorModel) cm;

                // choose the image type based on the size
                int type = BufferedImage.TYPE_BYTE_BINARY;
                if (getBitsPerComponent() == 8) {
                    type = BufferedImage.TYPE_BYTE_INDEXED;
                }

                // create the image with an explicit indexed color model.
                bi = new BufferedImage(getWidth(), getHeight(), type, icm);

                // set the data explicitly as well
                bi.setData(raster);
            } else if (cm.getPixelSize() == 1 && cm.getNumComponents() == 1) {
                // If the image is black and white only, convert it into
                // BYTE_GRAY
                // format
                // This is a lot faster compared to just drawing the original
                // image

                // Are pixels decoded?
                final int[] cc = new int[]{0, 1};
                final PDFObject o = imageObj.getDictRef("Decode");
                if (o != null && o.getAt(0) != null) {
                    cc[0] = o.getAt(0).getIntValue();
                    cc[1] = o.getAt(1).getIntValue();
                }

                final byte[] ncc = new byte[]{(byte) -cc[0], (byte) -cc[1]};

                bi = biColorToGrayscale(raster, ncc);
                // Return when there is no SMask
                if (getSMask() == null)
                    return bi;
            } else {
                // Raster is already in a format which is supported by Java2D,
                // such as RGB or Gray.
                bi = new BufferedImage(cm, raster, true, null);
            }
        }

        // hack to avoid *very* slow conversion
        final ColorSpace cs = cm.getColorSpace();
        final ColorSpace rgbCS = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        if (isGreyscale(cs) && bitsPerComponent <= 8 && getDecode() == null && jpegData == null
                && Configuration.getInstance().isConvertGreyscaleImagesToArgb()) {
            bi = convertGreyscaleToArgb(data, bi);
        } else if (!isImageMask() && cs instanceof ICC_ColorSpace && !cs.equals(rgbCS)
                && !Configuration.getInstance().isAvoidColorConvertOp()) {
            final ColorConvertOp op = new ColorConvertOp(cs, rgbCS, null);

            final BufferedImage converted = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

            bi = op.filter(bi, converted);
        } else if (cs.getType() == ColorSpace.TYPE_CMYK) {
            // convert to ARGB for faster drawing without ColorConvertOp
            final BufferedImage converted = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            final Graphics2D graphics = converted.createGraphics();
            graphics.drawImage(bi, 0, 0, null);
            graphics.dispose();
            bi = converted;
        }

        // add in the alpha data supplied by the SMask, if any
        final PDFImage sMaskImage = getSMask();
        if (sMaskImage != null) {
            BufferedImage si = null;
            try {
                int w = bi.getWidth();
                int h = bi.getHeight();
                // if the bitmap is only a few pixels it just defines the color
                final boolean maskOnly = (w <= 2);
                if (maskOnly) {
                    // use size of mask
                    si = sMaskImage.getImage();
                    w = si.getWidth();
                    h = si.getHeight();
                } else if (sMaskImage.getHeight() != h && sMaskImage.getWidth() != w) {
                    // in case the two images do not have the same size, scale
                    if (sMaskImage.getHeight() * sMaskImage.getWidth() > w * h) {
                        // upscale image
                        si = sMaskImage.getImage();
                        w = si.getWidth();
                        h = si.getHeight();
                        final int hints = Image.SCALE_FAST;
                        final Image scaledInstance = bi.getScaledInstance(w, h, hints);
                        bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                        final Graphics graphics = bi.createGraphics();
                        graphics.drawImage(scaledInstance, 0, 0, null);
                        graphics.dispose();
                    } else {
                        // upscale mask
                        si = scaleSMaskImage(sMaskImage);
                    }
                } else {
                    si = sMaskImage.getImage();
                }
                PDFDebugger.debugImage(si, "smask" + this.imageObj.getObjNum());

                final BufferedImage outImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                PDFDebugger.debugImage(si, "outImage" + this.imageObj.getObjNum());
                final int[] srcArray = new int[w];
                final int[] maskArray = new int[w];

                for (int i = 0; i < h; i++) {
                    if (maskOnly) {
                        // use first pixel color from image
                        Arrays.fill(srcArray, bi.getRGB(0, 0));
                    } else {
                        // pixel row from image
                        bi.getRGB(0, i, w, 1, srcArray, 0, w);
                    }
                    // pixel row from mask
                    si.getRGB(0, i, w, 1, maskArray, 0, w);

                    for (int j = 0; j < w; j++) {
                        final int ac = 0xff000000;
                        // alpha from mask with color from image
                        maskArray[j] = ((maskArray[j] & 0xff) << 24) | (srcArray[j] & ~ac);
                    }
                    // write pixel row
                    outImage.setRGB(0, i, w, 1, maskArray, 0, w);
                }

                bi = outImage;
            } catch (final PDFImageParseException e) {
                PDFDebugger.debug("Error parsing sMask image caused by:" + e.getMessage(), 100);
            }
        }

        PDFDebugger.debugImage(bi, "result" + this.imageObj.getObjNum());
        return bi;
    }

    /**
     * Scale the softmask image to the size of the actual image
     *
     * @param sMaskImage a {@link java.awt.image.BufferedImage} object.
     * @return a {@link java.awt.image.BufferedImage} object.
     * @throws org.loboevolution.pdfview.PDFImageParseException if any.
     */
    private BufferedImage scaleSMaskImage(final PDFImage sMaskImage) throws PDFImageParseException {
        final BufferedImage before = sMaskImage.getImage();
        final int w = before.getWidth();
        final int h = before.getHeight();

        if (PDFDebugger.DEBUG_IMAGES) {
            PDFDebugger.debug("Scaling image from " + w + "/" + h + " to " + this.width + "/" + this.height);
        }
        final BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        final AffineTransform at = new AffineTransform();

        at.scale(((double) this.width / w), ((double) this.height / h));

        final AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return scaleOp.filter(before, after);
    }

    private boolean isGreyscale(final ColorSpace aCs) {
        return aCs == PDFColorSpace.getColorSpace(PDFColorSpace.COLORSPACE_GRAY).getColorSpace();
    }

    private BufferedImage convertGreyscaleToArgb(final byte[] data, final BufferedImage bi) {
        // we use an optimised greyscale colour conversion, as with scanned
        // greyscale/mono documents consisting of nothing but page-size
        // images, using the ICC converter is perhaps 15 times slower than this
        // method. Using an example scanned, mainly monochrome document, on this
        // developer's machine pages took an average of 3s to render using the
        // ICC converter filter, and around 115ms using this method. We use
        // pre-calculated tables generated using the ICC converter to map
        // between
        // each possible greyscale value and its desired value in sRGB.
        // We also try to avoid going through SampleModels, WritableRasters or
        // BufferedImages as that takes about 3 times as long.
        final int[] convertedPixels = new int[getWidth() * getHeight()];
        final WritableRaster r = bi.getRaster();
        int i = 0;
        final int[] greyToArgbMap = getGreyToArgbMap(bitsPerComponent);
        if (bitsPerComponent == 1) {
            final int calculatedLineBytes = (getWidth() + 7) / 8;
            int rowStartByteIndex;
            // avoid hitting the WritableRaster for the common 1 bpc case
            if (greyToArgbMap[0] == 0 && greyToArgbMap[1] == 0xFFFFFFFF) {
                // optimisation for common case of a direct map to full white
                // and black, using bit twiddling instead of consulting the
                // greyToArgb map
                for (int y = 0; y < getHeight(); ++y) {
                    // each row is byte-aligned
                    rowStartByteIndex = y * calculatedLineBytes;
                    for (int x = 0; x < getWidth(); ++x) {
                        final byte b = data[rowStartByteIndex + x / 8];
                        final int white = b >> (7 - (x & 7)) & 1;
                        // if white == 0, white - 1 will be 0xFFFFFFFF,
                        // which when xored with 0xFFFFFF will produce 0
                        // if white == 1, white - 1 will be 0,
                        // which when xored with 0xFFFFFF will produce 0xFFFFFF
                        // (ignoring the top two bytes, which are always set
                        // high anyway)
                        convertedPixels[i] = 0xFF000000 | ((white - 1) ^ 0xFFFFFF);
                        ++i;
                    }
                }
            } else {
                // 1 bpc case where we can't bit-twiddle and need to consult
                // the map
                for (int y = 0; y < getHeight(); ++y) {
                    rowStartByteIndex = y * calculatedLineBytes;
                    for (int x = 0; x < getWidth(); ++x) {
                        final byte b = data[rowStartByteIndex + x / 8];
                        final int val = b >> (7 - (x & 7)) & 1;
                        convertedPixels[i] = greyToArgbMap[val];
                        ++i;
                    }
                }
            }
        } else {
            for (int y = 0; y < getHeight(); ++y) {
                for (int x = 0; x < getWidth(); ++x) {
                    final int greyscale = r.getSample(x, y, 0);
                    convertedPixels[i] = greyToArgbMap[greyscale];
                    ++i;
                }
            }
        }

        final ColorModel ccm = ColorModel.getRGBdefault();
        return new BufferedImage(ccm,
                Raster.createPackedRaster(new DataBufferInt(convertedPixels, convertedPixels.length), getWidth(),
                        getHeight(), getWidth(), ((PackedColorModel) ccm).getMasks(), null),
                false, null);
    }

    /**
     * Creates a new image which represents the
     * given raster
     *
     * @param raster Raster of an image with just two colors, bitwise encoded
     * @param ncc    Array with two entries that describe the corresponding gray
     *               values
     */
    private BufferedImage biColorToGrayscale(final WritableRaster raster, final byte[] ncc) {

        final byte[] bufferO = ((DataBufferByte) raster.getDataBuffer()).getData();

        final BufferedImage converted = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        final byte[] buffer = ((DataBufferByte) converted.getRaster().getDataBuffer()).getData();
        int i = 0;
        final int height = converted.getHeight();
        final int width = converted.getWidth();
        for (int y = 0; y < height; y++) {
            int base = y * width + 7;
            if ((y + 1) * width < buffer.length) {
                for (int x = 0; x < width; x += 8) {
                    final byte bits = bufferO[i];
                    i++;
                    for (byte j = 7; j >= 0; j--) {
                        if (buffer.length <= (base - j)) {
                            break;
                        }
                        final int c = ((bits >>> j) & 1);
                        buffer[base - j] = ncc[c];
                    }
                    base += 8;
                }
            } else {
                for (int x = 0; x < width; x += 8) {
                    final byte bits = bufferO[i];
                    i++;
                    for (byte j = 7; j >= 0; j--) {
                        if (base - j >= buffer.length)
                            break;
                        buffer[base - j] = ncc[((bits >>> j) & 1)];
                    }
                    base += 8;
                }
            }
        }
        return converted;
    }

    /**
     * set the color key mask. It is an array of start/end entries to indicate
     * ranges of color indicies that should be masked out.
     *
     * @param maskArrayObject
     */
    private void setColorKeyMask(final PDFObject maskArrayObject) throws IOException {
        final PDFObject[] maskObjects = maskArrayObject.getArray();
        this.colorKeyMask = null;
        final int[] masks = new int[maskObjects.length];
        for (int i = 0; i < masks.length; i++) {
            masks[i] = maskObjects[i].getIntValue();
        }
        this.colorKeyMask = masks;
    }

    /**
     * get a Java ColorModel consistent with the current color space, number of
     * bits per component and decode array
     */
    private ColorModel getColorModel() {
        final PDFColorSpace cs = getColorSpace();

        if (cs instanceof IndexedColor) {
            final IndexedColor ics = (IndexedColor) cs;

            byte[] components = ics.getColorComponents();
            int num = ics.getCount();

            // process the decode array
            if (this.decode != null) {
                final byte[] normComps = new byte[components.length];

                // move the components array around
                for (int i = 0; i < num; i++) {
                    final byte[] orig = new byte[1];
                    orig[0] = (byte) i;

                    final float[] res = normalize(orig, null, 0);
                    final int idx = (int) res[0];

                    normComps[i * 3] = components[idx * 3];
                    normComps[(i * 3) + 1] = components[(idx * 3) + 1];
                    normComps[(i * 3) + 2] = components[(idx * 3) + 2];
                }

                components = normComps;
            }

            // make sure the size of the components array is 2 ^ numBits
            // since if it's not, Java will complain
            final int correctCount = 1 << getBitsPerComponent();
            if (correctCount < num) {
                final byte[] fewerComps = new byte[correctCount * 3];

                System.arraycopy(components, 0, fewerComps, 0, correctCount * 3);

                components = fewerComps;
                num = correctCount;
            }
            if (this.colorKeyMask == null || this.colorKeyMask.length == 0) {
                return new IndexColorModel(getBitsPerComponent(), num, components, 0, false);
            } else {
                final byte[] aComps = new byte[num * 4];
                int idx = 0;
                for (int i = 0; i < num; i++) {
                    aComps[idx++] = components[(i * 3)];
                    aComps[idx++] = components[(i * 3) + 1];
                    aComps[idx++] = components[(i * 3) + 2];
                    aComps[idx++] = (byte) 0xFF;
                }
                for (int i = 0; i < this.colorKeyMask.length; i += 2) {
                    for (int j = this.colorKeyMask[i]; j <= this.colorKeyMask[i + 1]; j++) {
                        aComps[(j * 4) + 3] = 0; // make transparent
                    }
                }
                return new IndexColorModel(getBitsPerComponent(), num, aComps, 0, true);
            }
        } else if (cs instanceof AlternateColorSpace) {
            // ColorSpace altCS = new AltColorSpace(((AlternateColorSpace)
            // cs).getFunktion(), cs.getColorSpace());
            final ColorSpace altCS = cs.getColorSpace();
            final int[] bits = new int[altCS.getNumComponents()];
            Arrays.fill(bits, getBitsPerComponent());
            return new DecodeComponentColorModel(altCS, bits);
        } else {
            // If the image is a JPEG, then CMYK color space has been converted to RGB in DCTDecode
            if (this.jpegDecode && cs.getColorSpace().getType() == ColorSpace.TYPE_CMYK) {
                final ColorSpace rgbCS = ColorSpace.getInstance(ColorSpace.CS_sRGB);
                final int[] bits = new int[rgbCS.getNumComponents()];
                Arrays.fill(bits, getBitsPerComponent());
                return new DecodeComponentColorModel(rgbCS, bits);
            }
            final ColorSpace colorSpace = cs.getColorSpace();
            final int[] bits = new int[colorSpace.getNumComponents()];
            Arrays.fill(bits, getBitsPerComponent());

            return new DecodeComponentColorModel(cs.getColorSpace(), bits);
        }
    }

    /**
     * Normalize an array of values to match the decode array
     */
    private float[] normalize(final byte[] pixels, float[] normComponents, final int normOffset) {
        if (normComponents == null) {
            normComponents = new float[normOffset + pixels.length];
        }

        final float[] decodeArray = getDecode();

        for (int i = 0; i < pixels.length; i++) {
            final int val = pixels[i] & 0xff;
            final int pow = ((int) Math.pow(2, getBitsPerComponent())) - 1;
            final float ymin = decodeArray[i * 2];
            final float ymax = decodeArray[(i * 2) + 1];

            normComponents[normOffset + i] = FunctionType0.interpolate(val, 0, pow, ymin, ymax);
        }

        return normComponents;
    }

    /**
     * get a Java ColorModel consistent with the current color space, number of
     * bits per component and decode array
     */
    private ColorModel createColorModel() {
        final PDFColorSpace cs = getColorSpace();

        if (cs instanceof IndexedColor) {
            final IndexedColor ics = (IndexedColor) cs;

            byte[] components = ics.getColorComponents();
            int num = ics.getCount();

            // process the decode array
            if (decode != null) {
                final byte[] normComps = new byte[components.length];

                // move the components array around
                for (int i = 0; i < num; i++) {
                    final byte[] orig = new byte[1];
                    orig[0] = (byte) i;

                    final float[] res = normalize(orig, null, 0);
                    final int idx = (int) res[0];

                    normComps[i * 3] = components[idx * 3];
                    normComps[(i * 3) + 1] = components[(idx * 3) + 1];
                    normComps[(i * 3) + 2] = components[(idx * 3) + 2];
                }

                components = normComps;
            }

            // make sure the size of the components array is 2 ^ numBits
            // since if it's not, Java will complain
            final int correctCount = 1 << getBitsPerComponent();
            if (correctCount < num) {
                final byte[] fewerComps = new byte[correctCount * 3];

                System.arraycopy(components, 0, fewerComps, 0, correctCount * 3);

                components = fewerComps;
                num = correctCount;
            }
            if (colorKeyMask == null || colorKeyMask.length == 0) {
                return new IndexColorModel(getBitsPerComponent(), num, components, 0, false);
            } else {
                final byte[] aComps = new byte[num * 4];
                int idx = 0;
                for (int i = 0; i < num; i++) {
                    aComps[idx++] = components[(i * 3)];
                    aComps[idx++] = components[(i * 3) + 1];
                    aComps[idx++] = components[(i * 3) + 2];
                    aComps[idx++] = (byte) 0xFF;
                }
                for (int i = 0; i < colorKeyMask.length; i += 2) {
                    for (int j = colorKeyMask[i]; j <= colorKeyMask[i + 1]; j++) {
                        aComps[(j * 4) + 3] = 0; // make transparent
                    }
                }
                return new IndexColorModel(getBitsPerComponent(), num, aComps, 0, true);
            }
        } else {
            final int[] bits = new int[cs.getNumComponents()];
            for (int i = 0; i < bits.length; i++) {
                bits[i] = getBitsPerComponent();
            }

            return decode != null ? new DecodeComponentColorModel(cs.getColorSpace(), bits)
                    : new PdfComponentColorModel(cs.getColorSpace(), bits);
        }
    }

    /**
     * A wrapper for ComponentColorSpace which normalizes based on the decode
     * array.
     */
    static class PdfComponentColorModel extends ComponentColorModel {

        final int bitsPerComponent;

        public PdfComponentColorModel(final ColorSpace cs, final int[] bpc) {
            super(cs, bpc, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);

            pixel_bits = bpc.length * bpc[0];
            this.bitsPerComponent = bpc[0];
        }

        @Override
        public SampleModel createCompatibleSampleModel(final int width, final int height) {

            if (bitsPerComponent >= 8) {
                assert bitsPerComponent == 8 || bitsPerComponent == 16;
                final int numComponents = getNumComponents();
                final int[] bandOffsets = new int[numComponents];
                for (int i = 0; i < numComponents; i++) {
                    bandOffsets[i] = i;
                }
                return new PixelInterleavedSampleModel(getTransferType(), width, height, numComponents,
                        width * numComponents, bandOffsets);
            } else {
                switch (getPixelSize()) {
                    case 1:
                    case 2:
                    case 4:
                        // pixels don't span byte boundaries, so we can use the
                        // standard multi pixel
                        // packing, which offers a slight performance advantage over
                        // the other sample
                        // model, which must consider such cases. Given that sample
                        // model interactions
                        // can dominate processing, this small distinction is
                        // worthwhile
                        return new MultiPixelPackedSampleModel(getTransferType(), width, height, getPixelSize());
                    default:
                        // pixels will cross byte boundaries
                        assert getTransferType() == DataBuffer.TYPE_BYTE;
                        return new PdfSubByteSampleModel(width, height, getNumComponents(), bitsPerComponent);
                }
            }
        }

        @Override
        public boolean isCompatibleRaster(final Raster raster) {
            if (bitsPerComponent < 8 || getNumComponents() == 1) {
                final SampleModel sm = raster.getSampleModel();
                return sm.getSampleSize(0) == bitsPerComponent;
            }
            return super.isCompatibleRaster(raster);
        }

    }

    /**
     * A wrapper for ComponentColorSpace which normalizes based on the decode
     * array.
     */
    class DecodeComponentColorModel extends ComponentColorModel {

        public DecodeComponentColorModel(final ColorSpace cs, final int[] bpc) {
            super(cs, bpc, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);

            if (bpc != null) {
                this.pixel_bits = bpc.length * bpc[0];
            }
        }

        @Override
        public SampleModel createCompatibleSampleModel(final int width, final int height) {
            // workaround -- create a MultiPixelPackedSample models for
            // single-sample, less than 8bpp color models
            if (getNumComponents() == 1 && getPixelSize() < 8) {
                return new MultiPixelPackedSampleModel(getTransferType(), width, height, getPixelSize());
            }

            return super.createCompatibleSampleModel(width, height);
        }

        @Override
        public boolean isCompatibleRaster(final Raster raster) {
            if (getNumComponents() == 1 && getPixelSize() < 8) {
                final SampleModel sm = raster.getSampleModel();

                if (sm instanceof MultiPixelPackedSampleModel) {
                    return (sm.getSampleSize(0) == getPixelSize());
                } else {
                    return false;
                }
            }

            return super.isCompatibleRaster(raster);
        }

        @Override
        public float[] getNormalizedComponents(final Object pixel, final float[] normComponents, final int normOffset) {
            if (getDecode() == null) {
                return super.getNormalizedComponents(pixel, normComponents, normOffset);
            }

            return normalize((byte[]) pixel, normComponents, normOffset);
        }
    }

    /**
     * Decodes jpeg data, possibly attempting a manual YCCK decode if requested.
     * Users should use {@link #getColorModel()} to see which color model should
     * now be used after a successful decode.
     */
    private final class JpegDecoder {
        /**
         * The jpeg bytes
         */
        private final ByteBuffer jpegData;
        /**
         * The color model employed
         */
        private ColorModel cm;
        /**
         * Whether the YCCK/CMYK decode work-around should be used
         */
        private boolean ycckcmykDecodeMode = false;

        /**
         * Class constructor
         *
         * @param jpegData the JPEG data
         * @param cm       the color model as presented in the PDF
         */
        private JpegDecoder(final ByteBuffer jpegData, final ColorModel cm) {
            this.jpegData = jpegData;
            this.cm = cm;
        }

        /**
         * Identify whether the decoder should operate in YCCK/CMYK decode mode,
         * whereby the YCCK Chroma is specifically looked for and the color
         * model is changed to support converting raw YCCK color values, working
         * around a lack of YCCK/CMYK report in the standard Java jpeg readers.
         * Non-YCCK images will not be decoded while in this mode.
         *
         * @param ycckcmykDecodeMode
         */
        public void ycckcmykDecodeMode(final boolean ycckcmykDecodeMode) {
            this.ycckcmykDecodeMode = ycckcmykDecodeMode;
        }

        /**
         * Get the color model that should be used now
         *
         * @return
         */
        public ColorModel getColorModel() {
            return cm;
        }

        /**
         * Attempt to decode the jpeg data
         *
         * @return the successfully decoded image
         * @throws IOException if the image couldn't be decoded due to a lack of support
         *                     or some IO problem
         */
        private BufferedImage decode() throws IOException {

            ImageReadParam readParam = null;
            if (getDecode() != null) {
                // we have to allocate our own buffered image so that we can
                // install our colour model which will do the desired decode
                readParam = new ImageReadParam();
                final SampleModel sm = cm.createCompatibleSampleModel(getWidth(), getHeight());
                final WritableRaster raster = Raster.createWritableRaster(sm, new Point(0, 0));
                readParam.setDestination(new BufferedImage(cm, raster, true, null));
            }

            final Iterator<ImageReader> jpegReaderIt = ImageIO.getImageReadersByFormatName("jpeg");
            IIOException lastIioEx = null;
            while (jpegReaderIt.hasNext()) {
                try {
                    final ImageReader jpegReader = jpegReaderIt.next();
                    jpegReader.setInput(ImageIO.createImageInputStream(new ByteBufferInputStream(jpegData)), true,
                            false);
                    try {
                        return readImage(jpegReader, readParam);
                    } catch (final Exception e) {
                        if (e instanceof IIOException) {
                            throw (IIOException) e;
                        }
                        // Any other exceptions here are probably due to
                        // internal
                        // problems with the image reader.
                        // A concrete example of this happening is described
                        // here:
                        // http://java.net/jira/browse/PDF_RENDERER-132 where
                        // JAI imageio extension throws an
                        // IndexOutOfBoundsException on progressive JPEGs.
                        // We'll just treat it as an IIOException for
                        // convenience
                        // and hopefully a subsequent reader can handle it
                        throw new IIOException("Internal reader error?", e);
                    } finally {
                        jpegReader.dispose();
                    }
                } catch (final IIOException e) {
                    // its most likely complaining about an unsupported image
                    // type; hopefully the next image reader will be able to
                    // understand it
                    jpegData.reset();
                    lastIioEx = e;
                }
            }

            throw lastIioEx;

        }

        private BufferedImage readImage(final ImageReader jpegReader, final ImageReadParam param) throws IOException {
            if (ycckcmykDecodeMode) {
                // The standard Oracle Java JPEG readers can't deal with CMYK
                // YCCK encoded images
                // without a little help from us. We'll try and pick up such
                // instances and work around it.
                final IIOMetadata imageMeta = jpegReader.getImageMetadata(0);
                if (imageMeta != null) {
                    final Node standardMeta = imageMeta.getAsTree(IIOMetadataFormatImpl.standardMetadataFormatName);
                    if (standardMeta != null) {
                        final Node chroma = getChild(standardMeta, "Chroma");
                        if (chroma != null) {
                            final Node csType = getChild(chroma, "ColorSpaceType");
                            if (csType != null) {
                                final Attr csTypeNameNode = (Attr) csType.getAttributes().getNamedItem("name");
                                if (csTypeNameNode != null) {
                                    final String typeName = csTypeNameNode.getValue();
                                    final boolean YCCK;
                                    if ((YCCK = "YCCK".equals(typeName)) || "CMYK".equals(typeName)) {
                                        // If it's a YCCK image, then we can
                                        // coax a workable image out of it
                                        // by grabbing the raw raster and
                                        // installing a YCCK converting
                                        // color space wrapper around the
                                        // existing (CMYK) color space; this
                                        // will
                                        // do the YCCK conversion for us
                                        //
                                        // If it's a CMYK image - just raster it
                                        // in existing CMYK color space

                                        // first make sure we can get the
                                        // unadjusted raster
                                        final Raster raster = jpegReader.readRaster(0, param);

                                        if (YCCK) {
                                            // and now use it with a YCCK
                                            // converting color space.
                                            PDFImage.this.colorSpace = new PDFColorSpace(
                                                    new YCCKColorSpace(colorSpace.getColorSpace()));
                                            // re-calculate the color model
                                            // since the color space has changed
                                            cm = PDFImage.this.createColorModel();
                                        }

                                        return new BufferedImage(cm, Raster.createWritableRaster(
                                                raster.getSampleModel(), raster.getDataBuffer(), null), true, null);

                                    }
                                }
                            }
                        }
                    }
                }

                throw new IIOException("Neither YCCK nor CMYK image");

            } else {

                if (param != null && param.getDestination() != null) {
                    // if we've already set up a destination image then we'll
                    // use it
                    return jpegReader.read(0, param);
                } else {
                    // otherwise we'll create a new buffered image with the
                    // desired color model
                    //return new BufferedImage(cm, jpegReader.read(0, param).getRaster(), true, null);
                    final BufferedImage bi = jpegReader.read(0, param);
                    try {
                        return new BufferedImage(cm, bi.getRaster(), true, null);
                    } catch (final IllegalArgumentException raster_ByteInterleavedRaster) {
                        final BufferedImage bi2 = new BufferedImage(bi.getWidth(), bi.getHeight(),
                                BufferedImage.TYPE_BYTE_INDEXED,
                                new IndexColorModel(8, 1, new byte[]{0}, new byte[]{0}, new byte[]{0}, 0));
                        cm = bi2.getColorModel();
                        return bi2;
                    }
                }
            }

        }

        /**
         * Get a named child node
         *
         * @param aNode      the node
         * @param aChildName the name of the child node
         * @return the first direct child node with that name or null if it
         * doesn't exist
         */
        private Node getChild(final Node aNode, final String aChildName) {
            for (int i = 0; i < aNode.getChildNodes().getLength(); ++i) {
                final Node child = aNode.getChildNodes().item(i);
                if (child.getNodeName().equals(aChildName)) {
                    return child;
                }
            }
            return null;
        }
    }
}
