/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Since there is no context that is passed between the various classes that
 * perform the pdf parsing and rendering, we introduce this class to at least
 * globally configure PDFRenderer.
 * <p>
 * Typically, you would configure the global instance before using any other
 * PDFRenderer API.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
}
