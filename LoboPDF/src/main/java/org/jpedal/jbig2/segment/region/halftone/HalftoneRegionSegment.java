/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
package org.jpedal.jbig2.segment.region.halftone;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jpedal.jbig2.JBIG2Exception;
import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;
import org.jpedal.jbig2.image.JBIG2Bitmap;
import org.jpedal.jbig2.segment.Segment;
import org.jpedal.jbig2.segment.pageinformation.PageInformationSegment;
import org.jpedal.jbig2.segment.pattern.PatternDictionarySegment;
import org.jpedal.jbig2.segment.region.RegionFlags;
import org.jpedal.jbig2.segment.region.RegionSegment;
import org.jpedal.jbig2.util.BinaryOperation;

import java.io.IOException;

/**
 * <p>HalftoneRegionSegment class.</p>
 */
@Slf4j
public class HalftoneRegionSegment extends RegionSegment {
    @Getter
    private final HalftoneRegionFlags halftoneRegionFlags = new HalftoneRegionFlags();

    private final boolean inlineImage;

    /**
     * <p>Constructor for HalftoneRegionSegment.</p>
     *
     * @param streamDecoder a {@link org.jpedal.jbig2.decoders.JBIG2StreamDecoder} object.
     * @param inlineImage   a boolean.
     */
    public HalftoneRegionSegment(final JBIG2StreamDecoder streamDecoder, final boolean inlineImage) {
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
        super.readSegment();
        readHalftoneRegionFlags();

        short[] buf = new short[4];
        decoder.readByte(buf);
        final int gridWidth = BinaryOperation.getInt32(buf);

        buf = new short[4];
        decoder.readByte(buf);
        final int gridHeight = BinaryOperation.getInt32(buf);

        buf = new short[4];
        decoder.readByte(buf);
        final int gridX = BinaryOperation.getInt32(buf);

        buf = new short[4];
        decoder.readByte(buf);
        final int gridY = BinaryOperation.getInt32(buf);

        if (JBIG2StreamDecoder.debug)
            log.info("grid pos and size = {},{},{},{} ", gridX, gridY, gridWidth, gridHeight);

        buf = new short[2];
        decoder.readByte(buf);
        final int stepX = BinaryOperation.getInt16(buf);

        buf = new short[2];
        decoder.readByte(buf);
        final int stepY = BinaryOperation.getInt16(buf);

        if (JBIG2StreamDecoder.debug)
            log.info("step size = {}, {} ", stepX, stepY);

        final int[] referedToSegments = segmentHeader.getReferredToSegments();
        if (referedToSegments.length != 1) {
            log.info("Error in halftone Segment. refSegs should == 1");
        }

        final Segment segment = decoder.findSegment(referedToSegments[0]);
        if (segment.getSegmentHeader().getSegmentType() != Segment.PATTERN_DICTIONARY) {
            if (JBIG2StreamDecoder.debug)
                log.info("Error in halftone Segment. bad symbol dictionary reference");
        }

        final PatternDictionarySegment patternDictionarySegment = (PatternDictionarySegment) segment;

        int bitsPerValue = 0, i = 1;
        while (i < patternDictionarySegment.getSize()) {
            bitsPerValue++;
            i <<= 1;
        }

        JBIG2Bitmap bitmap = patternDictionarySegment.getBitmaps()[0];
        final int patternWidth = bitmap.getWidth();
        final int patternHeight = bitmap.getHeight();

        if (JBIG2StreamDecoder.debug)
            log.info("pattern size = {}, {} ", patternWidth, patternHeight);

        final boolean useMMR = halftoneRegionFlags.getFlagValue(HalftoneRegionFlags.H_MMR) != 0;
        final int template = halftoneRegionFlags.getFlagValue(HalftoneRegionFlags.H_TEMPLATE);

        if (!useMMR) {
            arithmeticDecoder.resetGenericStats(template, null);
            arithmeticDecoder.start();
        }

        final int halftoneDefaultPixel = halftoneRegionFlags.getFlagValue(HalftoneRegionFlags.H_DEF_PIXEL);
        bitmap = new JBIG2Bitmap(regionBitmapWidth, regionBitmapHeight, arithmeticDecoder, huffmanDecoder, mmrDecoder);
        bitmap.clear(halftoneDefaultPixel);

        final boolean enableSkip = halftoneRegionFlags.getFlagValue(HalftoneRegionFlags.H_ENABLE_SKIP) != 0;

        JBIG2Bitmap skipBitmap = null;
        if (enableSkip) {
            skipBitmap = new JBIG2Bitmap(gridWidth, gridHeight, arithmeticDecoder, huffmanDecoder, mmrDecoder);
            skipBitmap.clear(0);
            for (int y = 0; y < gridHeight; y++) {
                for (int x = 0; x < gridWidth; x++) {
                    final int xx = gridX + y * stepY + x * stepX;
                    final int yy = gridY + y * stepX - x * stepY;

                    if (((xx + patternWidth) >> 8) <= 0 || (xx >> 8) >= regionBitmapWidth || ((yy + patternHeight) >> 8) <= 0 || (yy >> 8) >= regionBitmapHeight) {
                        skipBitmap.setPixel(y, x, 1);
                    }
                }
            }
        }

        final int[] grayScaleImage = new int[gridWidth * gridHeight];

        final short[] genericBAdaptiveTemplateX = new short[4];
        final short[] genericBAdaptiveTemplateY = new short[4];

        genericBAdaptiveTemplateX[0] = (short) (template <= 1 ? 3 : 2);
        genericBAdaptiveTemplateY[0] = -1;
        genericBAdaptiveTemplateX[1] = -3;
        genericBAdaptiveTemplateY[1] = -1;
        genericBAdaptiveTemplateX[2] = 2;
        genericBAdaptiveTemplateY[2] = -2;
        genericBAdaptiveTemplateX[3] = -2;
        genericBAdaptiveTemplateY[3] = -2;

        JBIG2Bitmap grayBitmap;

        for (int j = bitsPerValue - 1; j >= 0; --j) {
            grayBitmap = new JBIG2Bitmap(gridWidth, gridHeight, arithmeticDecoder, huffmanDecoder, mmrDecoder);

            grayBitmap.readBitmap(useMMR, template, false, enableSkip, skipBitmap, genericBAdaptiveTemplateX, genericBAdaptiveTemplateY, -1);

            i = 0;
            for (int row = 0; row < gridHeight; row++) {
                for (int col = 0; col < gridWidth; col++) {
                    final int bit = grayBitmap.getPixel(col, row) ^ grayScaleImage[i] & 1;
                    grayScaleImage[i] = (grayScaleImage[i] << 1) | bit;
                    i++;
                }
            }
        }

        final int combinationOperator = halftoneRegionFlags.getFlagValue(HalftoneRegionFlags.H_COMB_OP);

        i = 0;
        for (int col = 0; col < gridHeight; col++) {
            int xx = gridX + col * stepY;
            int yy = gridY + col * stepX;
            for (int row = 0; row < gridWidth; row++) {
                if (!(enableSkip && skipBitmap.getPixel(col, row) == 1)) {
                    final JBIG2Bitmap patternBitmap = patternDictionarySegment.getBitmaps()[grayScaleImage[i]];
                    bitmap.combine(patternBitmap, xx >> 8, yy >> 8, combinationOperator);
                }

                xx += stepX;
                yy -= stepY;

                i++;
            }
        }

        if (inlineImage) {
            final PageInformationSegment pageSegment = decoder.findPageSegement(segmentHeader.getPageAssociation());
            final JBIG2Bitmap pageBitmap = pageSegment.getPageBitmap();

            final int externalCombinationOperator = regionFlags.getFlagValue(RegionFlags.EXTERNAL_COMBINATION_OPERATOR);
            pageBitmap.combine(bitmap, regionBitmapXLocation, regionBitmapYLocation, externalCombinationOperator);
        } else {
            bitmap.setBitmapNumber(getSegmentHeader().getSegmentNumber());
            decoder.appendBitmap(bitmap);
        }

    }

    private void readHalftoneRegionFlags() throws IOException {
        final short halftoneRegionFlagsField = decoder.readByte();

        halftoneRegionFlags.setFlags(halftoneRegionFlagsField);

        if (JBIG2StreamDecoder.debug)
            log.info("generic region Segment flags = {} ", halftoneRegionFlagsField);
    }
}
