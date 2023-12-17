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
package org.jpedal.jbig2.segment.region.refinement;

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
 * <p>RefinementRegionSegment class.</p>
 */
@Slf4j
public class RefinementRegionSegment extends RegionSegment {
    final int[] referedToSegments;
    private final RefinementRegionFlags refinementRegionFlags = new RefinementRegionFlags();
    private final boolean inlineImage;
    private final int noOfReferedToSegments;

    /**
     * <p>Constructor for RefinementRegionSegment.</p>
     *
     * @param streamDecoder         a {@link org.jpedal.jbig2.decoders.JBIG2StreamDecoder} object.
     * @param inlineImage           a boolean.
     * @param referedToSegments     an array of {@link int} objects.
     * @param noOfReferedToSegments a int.
     */
    public RefinementRegionSegment(final JBIG2StreamDecoder streamDecoder, final boolean inlineImage, final int[] referedToSegments, final int noOfReferedToSegments) {
        super(streamDecoder);

        this.inlineImage = inlineImage;
        this.referedToSegments = referedToSegments;
        this.noOfReferedToSegments = noOfReferedToSegments;
    }

    /**
     * <p>readSegment.</p>
     *
     * @throws java.io.IOException             if any.
     * @throws org.jpedal.jbig2.JBIG2Exception if any.
     */
    public void readSegment() throws IOException, JBIG2Exception {
        if (JBIG2StreamDecoder.debug)
            log.info("==== Reading Generic Refinement Region ====");

        super.readSegment();

        readGenericRegionFlags();

        final short[] genericRegionAdaptiveTemplateX = new short[2];
        final short[] genericRegionAdaptiveTemplateY = new short[2];

        final int template = refinementRegionFlags.getFlagValue(RefinementRegionFlags.GR_TEMPLATE);
        if (template == 0) {
            genericRegionAdaptiveTemplateX[0] = readATValue();
            genericRegionAdaptiveTemplateY[0] = readATValue();
            genericRegionAdaptiveTemplateX[1] = readATValue();
            genericRegionAdaptiveTemplateY[1] = readATValue();
        }

        if (noOfReferedToSegments == 0 || inlineImage) {
            final PageInformationSegment pageSegment = decoder.findPageSegement(segmentHeader.getPageAssociation());
            final JBIG2Bitmap pageBitmap = pageSegment.getPageBitmap();

            if (pageSegment.getPageBitmapHeight() == -1 && regionBitmapYLocation + regionBitmapHeight > pageBitmap.getHeight()) {
                pageBitmap.expand(regionBitmapYLocation + regionBitmapHeight, pageSegment.getPageInformationFlags().getFlagValue(PageInformationFlags.DEFAULT_PIXEL_VALUE));
            }
        }

        if (noOfReferedToSegments > 1) {
            if (JBIG2StreamDecoder.debug)
                log.info("Bad reference in JBIG2 generic refinement Segment");

            return;
        }

        final JBIG2Bitmap referedToBitmap;
        if (noOfReferedToSegments == 1) {
            referedToBitmap = decoder.findBitmap(referedToSegments[0]);
        } else {
            final PageInformationSegment pageSegment = decoder.findPageSegement(segmentHeader.getPageAssociation());
            final JBIG2Bitmap pageBitmap = pageSegment.getPageBitmap();

            referedToBitmap = pageBitmap.getSlice(regionBitmapXLocation, regionBitmapYLocation, regionBitmapWidth, regionBitmapHeight);
        }

        arithmeticDecoder.resetRefinementStats(template, null);
        arithmeticDecoder.start();

        final boolean typicalPredictionGenericRefinementOn = refinementRegionFlags.getFlagValue(RefinementRegionFlags.TPGDON) != 0;

        final JBIG2Bitmap bitmap = new JBIG2Bitmap(regionBitmapWidth, regionBitmapHeight, arithmeticDecoder, huffmanDecoder, mmrDecoder);

        bitmap.readGenericRefinementRegion(template, typicalPredictionGenericRefinementOn, referedToBitmap, 0, 0, genericRegionAdaptiveTemplateX, genericRegionAdaptiveTemplateY);

        if (inlineImage) {
            final PageInformationSegment pageSegment = decoder.findPageSegement(segmentHeader.getPageAssociation());
            final JBIG2Bitmap pageBitmap = pageSegment.getPageBitmap();

            final int extCombOp = regionFlags.getFlagValue(RegionFlags.EXTERNAL_COMBINATION_OPERATOR);

            pageBitmap.combine(bitmap, regionBitmapXLocation, regionBitmapYLocation, extCombOp);
        } else {
            bitmap.setBitmapNumber(getSegmentHeader().getSegmentNumber());
            decoder.appendBitmap(bitmap);
        }
    }

    private void readGenericRegionFlags() throws IOException {
        final short refinementRegionFlagsField = decoder.readByte();

        refinementRegionFlags.setFlags(refinementRegionFlagsField);

        if (JBIG2StreamDecoder.debug)
            log.info("generic region Segment flags = " + refinementRegionFlagsField);
    }

    /**
     * <p>getGenericRegionFlags.</p>
     *
     * @return a {@link org.jpedal.jbig2.segment.region.refinement.RefinementRegionFlags} object.
     */
    public RefinementRegionFlags getGenericRegionFlags() {
        return refinementRegionFlags;
    }
}
