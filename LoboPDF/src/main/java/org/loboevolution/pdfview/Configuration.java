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

/**
 * Since there is no context that is passed between the various classes that
 * perform the pdf parsing and rendering, we introduce this class to at least
 * globally configure PDFRenderer.
 * <p>
 * Typically you would configure the global instance before using any other
 * PDFRenderer API.
 */
public class Configuration {
    private static Configuration INSTANCE;

    /**
     * whether grey scale images will be converted to ARGB
     */
    private boolean convertGreyscaleImagesToArgb = true;
    /**
     * threshold in pixels after which images are rendered in chunks (disabled by default)
     */
    private int thresholdForBandedImageRendering = 0;
    /**
     * whether color convert op should be used or not for parsing an image
     */
    private boolean avoidColorConvertOp = false;
    /**
     * Use blur before image resize to enhance the result (Antialias)
     **/
    private boolean useBlurResizingForImages = true;

    /**
     * Print signature fields on pdf
     **/
    private boolean printSigantureFields = true;
    /**
     * Print stamp annotations on pdf
     **/
    private boolean printStampAnnotations = true;
    /**
     * Print widget annotations on pdf
     **/
    private boolean printWidgetAnnotations = true;
    /**
     * Print freetext annotations on pdf
     **/
    private boolean printFreetextAnnotations = true;
    /**
     * Print link annotations on pdf
     **/
    private boolean printLinkAnnotations = true;

    /**
     * <p>getInstance.</p>
     *
     * @return a {@link org.loboevolution.pdfview.Configuration} object.
     */
    public static synchronized Configuration getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Configuration();
        }
        return INSTANCE;
    }

    /**
     * Returns <code>true</code> if greyscale images will be converted to ARGB
     *
     * @return a boolean.
     */
    public boolean isConvertGreyscaleImagesToArgb() {
        return convertGreyscaleImagesToArgb;
    }

    /**
     * Enables or disables the conversion of greyscale images to ARGB.
     * Disabling this may have a lower memory overhead with high resolution
     * (e.g. scanned) images. Note that this has to be called before
     * is called to have an effect.
     * <p>
     * Enabled by default.
     *
     * @param aFlag whether greyscale images shall be converted to ARGB.
     */
    public void setConvertGreyscaleImagesToArgb(final boolean aFlag) {
        convertGreyscaleImagesToArgb = aFlag;
    }

    /**
     * Returns the image height threshold at which to enable banded image rendering.
     *
     * @return the threshold value, or a value {@literal <}= 0 if banded rendering is disabled
     */
    public int getThresholdForBandedImageRendering() {
        return thresholdForBandedImageRendering;
    }

    /**
     * If an image is higher than the given size (in pixels) then
     * the image will be rendered in chunks, rather than as one big image.
     * This may lead to lower memory consumption for e.g. scanned PDFs with
     * large images.
     * <p>
     * Set to a value &lt;= 0 to disable banded image rendering.
     * Defaults to 0 (off)
     *
     * @param aSize the height threshold at which to enable banded image rendering
     */
    public void setThresholdForBandedImageRendering(final int aSize) {
        thresholdForBandedImageRendering = aSize;
    }

    /**
     * Is the color converting op switched on or off?
     *
     * @return - the usage of this color convert op
     */
    public boolean isAvoidColorConvertOp() {
        return avoidColorConvertOp;
    }

    /**
     * Set this to false to switch off the
     * use of this color convert op which may segfault on some platforms
     * due to a variety of problems related to thread safety and
     * the native cmm library underlying this conversion op
     * <p>
     * If the system is bug-free, though, this does make use
     * of native libraries and sees a not insignificant speed-up,
     * though it's still not exactly fast. If we don't run this op
     * now, it's performed at some later stage, but without using
     * the native code
     *
     * @param avoidColorConvertOp a boolean.
     */
    public void setAvoidColorConvertOp(final boolean avoidColorConvertOp) {
        this.avoidColorConvertOp = avoidColorConvertOp;
    }

    /**
     * Use blur before image resize to enhance the result (Antialias)?
     *
     * @return the useBlurResizingForImages
     */
    public boolean isUseBlurResizingForImages() {
        return useBlurResizingForImages;
    }

    /**
     * Use blur before image resize to enhance the result (Antialias)
     *
     * @param useBlurResizingForImages a boolean.
     */
    public void setUseBlurResizingForImages(final boolean useBlurResizingForImages) {
        this.useBlurResizingForImages = useBlurResizingForImages;
    }

    /**
     * <p>isPrintSignatureFields.</p>
     *
     * @return <code>true</code> if signature fields will be printed on pdf
     */
    public boolean isPrintSignatureFields() {
        return this.printSigantureFields;
    }

    /**
     * Print signature fields on pdf
     *
     * @param printSignatureFields a boolean.
     */
    public void setPrintSignatureFields(final boolean printSignatureFields) {
        this.printSigantureFields = printSignatureFields;
    }

    /**
     * <p>isPrintStampAnnotations.</p>
     *
     * @return <code>true</code> if stamp annotations will be printed on pdf
     */
    public boolean isPrintStampAnnotations() {
        return this.printStampAnnotations;
    }

    /**
     * Print stamp annotations on pdf
     *
     * @param printStampAnnotations a boolean.
     */
    public void setPrintStampAnnotations(final boolean printStampAnnotations) {
        this.printStampAnnotations = printStampAnnotations;
    }

    /**
     * <p>isPrintWidgetAnnotations.</p>
     *
     * @return <code>true</code> if widget annotations will be printed on pdf
     */
    public boolean isPrintWidgetAnnotations() {
        return this.printWidgetAnnotations;
    }

    /**
     * Print widget annotations on pdf
     *
     * @param printWidgetAnnotations a boolean.
     */
    public void setPrintWidgetAnnotations(final boolean printWidgetAnnotations) {
        this.printWidgetAnnotations = printWidgetAnnotations;
    }

    /**
     * <p>isPrintFreetextAnnotations.</p>
     *
     * @return <code>true</code> if freetext annotations will be printed on pdf
     */
    public boolean isPrintFreetextAnnotations() {
        return this.printFreetextAnnotations;
    }

    /**
     * Print freetext annotations on pdf
     *
     * @param printFreetextAnnotations a boolean.
     */
    public void setPrintFreetextAnnotations(final boolean printFreetextAnnotations) {
        this.printFreetextAnnotations = printFreetextAnnotations;
    }

    /**
     * <p>isPrintLinkAnnotations.</p>
     *
     * @return <code>true</code> if link annotations will be printed on pdf
     */
    public boolean isPrintLinkAnnotations() {
        return this.printLinkAnnotations;
    }

    /**
     * Print link annotations on pdf
     *
     * @param printLinkAnnotations a boolean.
     */
    public void setPrintLinkAnnotations(final boolean printLinkAnnotations) {
        this.printLinkAnnotations = printLinkAnnotations;
    }
}
