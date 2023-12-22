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
package org.jpedal.jbig2.segment.region.generic;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jpedal.jbig2.JBIG2Exception;
import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;
import org.jpedal.jbig2.image.JBIG2Bitmap;
import org.jpedal.jbig2.segment.pageinformation.PageInformationFlags;
import org.jpedal.jbig2.segment.pageinformation.PageInformationSegment;
import org.jpedal.jbig2.segment.region.RegionFlags;
import org.jpedal.jbig2.segment.region.RegionSegment;

import java.io.IOException;

/**
 * <p>GenericRegionSegment class.</p>
 */
@Slf4j
public class GenericRegionSegment extends RegionSegment {
    @Getter
    private final GenericRegionFlags genericRegionFlags = new GenericRegionFlags();

    private final boolean inlineImage;

    private boolean unknownLength = false;

    /**
     * <p>Constructor for GenericRegionSegment.</p>
     *
     * @param streamDecoder a {@link org.jpedal.jbig2.decoders.JBIG2StreamDecoder} object.
     * @param inlineImage   a boolean.
     */
    public GenericRegionSegment(final JBIG2StreamDecoder streamDecoder, final boolean inlineImage) {
        super(streamDecoder);

        this.inlineImage = inlineImage;
    }

    /**
     * <p>readSegment.</p>
     *
     * @throws java.io.IOException             if any.
     * @throws org.jpedal.jbig2.JBIG2Exception if any.
     */
    public void readSegment() throws IOException, JBIG2Exception {

        if (JBIG2StreamDecoder.debug)
            log.info("==== Reading Immediate Generic Region ====");

        super.readSegment();

        // read text region Segment flags
        readGenericRegionFlags();

        final boolean useMMR = genericRegionFlags.getFlagValue(GenericRegionFlags.MMR) != 0;
        final int template = genericRegionFlags.getFlagValue(GenericRegionFlags.GB_TEMPLATE);

        final short[] genericBAdaptiveTemplateX = new short[4];
        final short[] genericBAdaptiveTemplateY = new short[4];

        if (!useMMR) {
            if (template == 0) {
                genericBAdaptiveTemplateX[0] = readATValue();
                genericBAdaptiveTemplateY[0] = readATValue();
                genericBAdaptiveTemplateX[1] = readATValue();
                genericBAdaptiveTemplateY[1] = readATValue();
                genericBAdaptiveTemplateX[2] = readATValue();
                genericBAdaptiveTemplateY[2] = readATValue();
                genericBAdaptiveTemplateX[3] = readATValue();
                genericBAdaptiveTemplateY[3] = readATValue();
            } else {
                genericBAdaptiveTemplateX[0] = readATValue();
                genericBAdaptiveTemplateY[0] = readATValue();
            }

            arithmeticDecoder.resetGenericStats(template, null);
            arithmeticDecoder.start();
        }

        final boolean typicalPredictionGenericDecodingOn = genericRegionFlags.getFlagValue(GenericRegionFlags.TPGDON) != 0;
        int length = segmentHeader.getSegmentDataLength();
        int bytesRead = 0;

        if (length == -1) {
            /**
             * length of data is unknown, so it needs to be determined through examination of the data.
             * See 7.2.7 - Segment data length of the JBIG2 specification.
             */

            unknownLength = true;

            final short match1;
            final short match2;

            if (useMMR) {
                // look for 0x00 0x00 (0, 0)

                match1 = 0;
                match2 = 0;
            } else {
                // look for 0xFF 0xAC (255, 172)

                match1 = 255;
                match2 = 172;
            }


            while (true) {
                final short bite1 = decoder.readByte();
                bytesRead++;

                if (bite1 == match1) {
                    final short bite2 = decoder.readByte();
                    bytesRead++;

                    if (bite2 == match2) {
                        length = bytesRead - 2;
                        break;
                    }
                }
            }

            decoder.movePointer(-bytesRead);
        }

        final JBIG2Bitmap bitmap = new JBIG2Bitmap(regionBitmapWidth, regionBitmapHeight, arithmeticDecoder, huffmanDecoder, mmrDecoder);
        bitmap.clear(0);
        bitmap.readBitmap(useMMR, template, typicalPredictionGenericDecodingOn, false, null, genericBAdaptiveTemplateX, genericBAdaptiveTemplateY, useMMR ? bytesRead : length - 18);


        if (inlineImage) {
            final PageInformationSegment pageSegment = decoder.findPageSegement(segmentHeader.getPageAssociation());
            final JBIG2Bitmap pageBitmap = pageSegment.getPageBitmap();

            final int extCombOp = regionFlags.getFlagValue(RegionFlags.EXTERNAL_COMBINATION_OPERATOR);

            if (pageSegment.getPageBitmapHeight() == -1 && regionBitmapYLocation + regionBitmapHeight > pageBitmap.getHeight()) {
                pageBitmap.expand(regionBitmapYLocation + regionBitmapHeight,
                        pageSegment.getPageInformationFlags().getFlagValue(PageInformationFlags.DEFAULT_PIXEL_VALUE));
            }

            pageBitmap.combine(bitmap, regionBitmapXLocation, regionBitmapYLocation, extCombOp);
        } else {
            bitmap.setBitmapNumber(getSegmentHeader().getSegmentNumber());
            decoder.appendBitmap(bitmap);
        }


        if (unknownLength) {
            decoder.movePointer(4);
        }

    }

    private void readGenericRegionFlags() throws IOException {
        // extract text region Segment flags
        final short genericRegionFlagsField = decoder.readByte();

        genericRegionFlags.setFlags(genericRegionFlagsField);

        if (JBIG2StreamDecoder.debug)
            log.info("generic region Segment flags = {} ", genericRegionFlagsField);
    }
}
