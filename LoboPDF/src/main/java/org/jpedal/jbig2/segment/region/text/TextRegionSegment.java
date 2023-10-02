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
package org.jpedal.jbig2.segment.region.text;

import lombok.extern.slf4j.Slf4j;
import org.jpedal.jbig2.JBIG2Exception;
import org.jpedal.jbig2.decoders.HuffmanDecoder;
import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;
import org.jpedal.jbig2.image.JBIG2Bitmap;
import org.jpedal.jbig2.segment.Segment;
import org.jpedal.jbig2.segment.pageinformation.PageInformationSegment;
import org.jpedal.jbig2.segment.region.RegionFlags;
import org.jpedal.jbig2.segment.region.RegionSegment;
import org.jpedal.jbig2.segment.symboldictionary.SymbolDictionarySegment;
import org.jpedal.jbig2.util.BinaryOperation;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>TextRegionSegment class.</p>
 */
@Slf4j
public class TextRegionSegment extends RegionSegment {
    private final TextRegionFlags textRegionFlags = new TextRegionFlags();

    private final TextRegionHuffmanFlags textRegionHuffmanFlags = new TextRegionHuffmanFlags();

    private final boolean inlineImage;

    private final short[] symbolRegionAdaptiveTemplateX = new short[2];
    private final short[] symbolRegionAdaptiveTemplateY = new short[2];

    /**
     * <p>Constructor for TextRegionSegment.</p>
     *
     * @param streamDecoder a {@link org.jpedal.jbig2.decoders.JBIG2StreamDecoder} object.
     * @param inlineImage   a boolean.
     */
    public TextRegionSegment(final JBIG2StreamDecoder streamDecoder, final boolean inlineImage) {
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
            log.info("==== Reading Text Region ====");

        super.readSegment();

        /** read text region Segment flags */
        readTextRegionFlags();

        final short[] buff = new short[4];
        decoder.readByte(buff);
        final int noOfSymbolInstances = BinaryOperation.getInt32(buff);

        if (JBIG2StreamDecoder.debug)
            log.info("noOfSymbolInstances = {} ", noOfSymbolInstances);

        final int noOfReferredToSegments = segmentHeader.getReferredToSegmentCount();
        final int[] referredToSegments = segmentHeader.getReferredToSegments();

        //List codeTables = new ArrayList();
        final List<Segment> segmentsReferenced = new LinkedList<>();
        int noOfSymbols = 0;

        if (JBIG2StreamDecoder.debug)
            log.info("noOfReferredToSegments = {} ", noOfReferredToSegments);

        for (int i = 0; i < noOfReferredToSegments; i++) {
            final Segment seg = decoder.findSegment(referredToSegments[i]);
            final int type = seg.getSegmentHeader().getSegmentType();

            if (type == Segment.SYMBOL_DICTIONARY) {
                segmentsReferenced.add(seg);
                noOfSymbols += ((SymbolDictionarySegment) seg).getNoOfExportedSymbols();
            } else if (type == Segment.TABLES) {
                //codeTables.add(seg);
            }
        }

        int symbolCodeLength = 0;
        int count = 1;

        while (count < noOfSymbols) {
            symbolCodeLength++;
            count <<= 1;
        }

        int currentSymbol = 0;
        final JBIG2Bitmap[] symbols = new JBIG2Bitmap[noOfSymbols];
        for (final Segment seg : segmentsReferenced) {
            if (seg.getSegmentHeader().getSegmentType() == Segment.SYMBOL_DICTIONARY) {
                final JBIG2Bitmap[] bitmaps = ((SymbolDictionarySegment) seg).getBitmaps();
                for (final JBIG2Bitmap jbig2Bitmap : bitmaps) {
                    symbols[currentSymbol] = jbig2Bitmap;
                    currentSymbol++;
                }
            }
        }

        int[][] huffmanFSTable = null;
        int[][] huffmanDSTable = null;
        int[][] huffmanDTTable = null;

        int[][] huffmanRDWTable = null;
        int[][] huffmanRDHTable = null;

        int[][] huffmanRDXTable = null;
        int[][] huffmanRDYTable = null;
        int[][] huffmanRSizeTable = null;

        final boolean sbHuffman = textRegionFlags.getFlagValue(TextRegionFlags.SB_HUFF) != 0;

        int i = 0;
        if (sbHuffman) {
            final int sbHuffFS = textRegionHuffmanFlags.getFlagValue(TextRegionHuffmanFlags.SB_HUFF_FS);
            if (sbHuffFS == 0) {
                huffmanFSTable = HuffmanDecoder.huffmanTableF;
            } else if (sbHuffFS == 1) {
                huffmanFSTable = HuffmanDecoder.huffmanTableG;
            } else {

            }

            final int sbHuffDS = textRegionHuffmanFlags.getFlagValue(TextRegionHuffmanFlags.SB_HUFF_DS);
            if (sbHuffDS == 0) {
                huffmanDSTable = HuffmanDecoder.huffmanTableH;
            } else if (sbHuffDS == 1) {
                huffmanDSTable = HuffmanDecoder.huffmanTableI;
            } else if (sbHuffDS == 2) {
                huffmanDSTable = HuffmanDecoder.huffmanTableJ;
            } else {

            }

            final int sbHuffDT = textRegionHuffmanFlags.getFlagValue(TextRegionHuffmanFlags.SB_HUFF_DT);
            if (sbHuffDT == 0) {
                huffmanDTTable = HuffmanDecoder.huffmanTableK;
            } else if (sbHuffDT == 1) {
                huffmanDTTable = HuffmanDecoder.huffmanTableL;
            } else if (sbHuffDT == 2) {
                huffmanDTTable = HuffmanDecoder.huffmanTableM;
            } else {

            }

            final int sbHuffRDW = textRegionHuffmanFlags.getFlagValue(TextRegionHuffmanFlags.SB_HUFF_RDW);
            if (sbHuffRDW == 0) {
                huffmanRDWTable = HuffmanDecoder.huffmanTableN;
            } else if (sbHuffRDW == 1) {
                huffmanRDWTable = HuffmanDecoder.huffmanTableO;
            } else {

            }

            final int sbHuffRDH = textRegionHuffmanFlags.getFlagValue(TextRegionHuffmanFlags.SB_HUFF_RDH);
            if (sbHuffRDH == 0) {
                huffmanRDHTable = HuffmanDecoder.huffmanTableN;
            } else if (sbHuffRDH == 1) {
                huffmanRDHTable = HuffmanDecoder.huffmanTableO;
            } else {

            }

            final int sbHuffRDX = textRegionHuffmanFlags.getFlagValue(TextRegionHuffmanFlags.SB_HUFF_RDX);
            if (sbHuffRDX == 0) {
                huffmanRDXTable = HuffmanDecoder.huffmanTableN;
            } else if (sbHuffRDX == 1) {
                huffmanRDXTable = HuffmanDecoder.huffmanTableO;
            } else {

            }

            final int sbHuffRDY = textRegionHuffmanFlags.getFlagValue(TextRegionHuffmanFlags.SB_HUFF_RDY);
            if (sbHuffRDY == 0) {
                huffmanRDYTable = HuffmanDecoder.huffmanTableN;
            } else if (sbHuffRDY == 1) {
                huffmanRDYTable = HuffmanDecoder.huffmanTableO;
            } else {

            }

            final int sbHuffRSize = textRegionHuffmanFlags.getFlagValue(TextRegionHuffmanFlags.SB_HUFF_RSIZE);
            if (sbHuffRSize == 0) {
                huffmanRSizeTable = HuffmanDecoder.huffmanTableA;
            } else {

            }
        }

        int[][] runLengthTable = new int[36][4];
        int[][] symbolCodeTable = new int[noOfSymbols + 1][4];
        if (sbHuffman) {

            decoder.consumeRemainingBits();

            for (i = 0; i < 32; i++) {
                runLengthTable[i] = new int[]{i, decoder.readBits(4), 0, 0};
            }

            runLengthTable[32] = new int[]{0x103, decoder.readBits(4), 2, 0};

            runLengthTable[33] = new int[]{0x203, decoder.readBits(4), 3, 0};

            runLengthTable[34] = new int[]{0x20b, decoder.readBits(4), 7, 0};

            runLengthTable[35] = new int[]{0, 0, HuffmanDecoder.jbig2HuffmanEOT};

            runLengthTable = HuffmanDecoder.buildTable(runLengthTable, 35);

            for (i = 0; i < noOfSymbols; i++) {
                symbolCodeTable[i] = new int[]{i, 0, 0, 0};
            }

            i = 0;
            while (i < noOfSymbols) {
                int j = huffmanDecoder.decodeInt(runLengthTable).intResult();
                if (j > 0x200) {
                    for (j -= 0x200; j != 0 && i < noOfSymbols; j--) {
                        symbolCodeTable[i++][1] = 0;
                    }
                } else if (j > 0x100) {
                    for (j -= 0x100; j != 0 && i < noOfSymbols; j--) {
                        symbolCodeTable[i][1] = symbolCodeTable[i - 1][1];
                        i++;
                    }
                } else {
                    symbolCodeTable[i++][1] = j;
                }
            }

            symbolCodeTable[noOfSymbols][1] = 0;
            symbolCodeTable[noOfSymbols][2] = HuffmanDecoder.jbig2HuffmanEOT;
            symbolCodeTable = HuffmanDecoder.buildTable(symbolCodeTable, noOfSymbols);

            decoder.consumeRemainingBits();
        } else {
            symbolCodeTable = null;
            arithmeticDecoder.resetIntStats(symbolCodeLength);
            arithmeticDecoder.start();
        }

        final boolean symbolRefine = textRegionFlags.getFlagValue(TextRegionFlags.SB_REFINE) != 0;
        final int logStrips = textRegionFlags.getFlagValue(TextRegionFlags.LOG_SB_STRIPES);
        final int defaultPixel = textRegionFlags.getFlagValue(TextRegionFlags.SB_DEF_PIXEL);
        final int combinationOperator = textRegionFlags.getFlagValue(TextRegionFlags.SB_COMB_OP);
        final boolean transposed = textRegionFlags.getFlagValue(TextRegionFlags.TRANSPOSED) != 0;
        final int referenceCorner = textRegionFlags.getFlagValue(TextRegionFlags.REF_CORNER);
        final int sOffset = textRegionFlags.getFlagValue(TextRegionFlags.SB_DS_OFFSET);
        final int template = textRegionFlags.getFlagValue(TextRegionFlags.SB_R_TEMPLATE);

        if (symbolRefine) {
            arithmeticDecoder.resetRefinementStats(template, null);
        }

        final JBIG2Bitmap bitmap = new JBIG2Bitmap(regionBitmapWidth, regionBitmapHeight, arithmeticDecoder, huffmanDecoder, mmrDecoder);

        bitmap.readTextRegion(sbHuffman, symbolRefine, noOfSymbolInstances, logStrips, noOfSymbols, symbolCodeTable, symbolCodeLength, symbols, defaultPixel, combinationOperator, transposed, referenceCorner, sOffset, huffmanFSTable, huffmanDSTable, huffmanDTTable, huffmanRDWTable, huffmanRDHTable, huffmanRDXTable, huffmanRDYTable, huffmanRSizeTable, template, symbolRegionAdaptiveTemplateX, symbolRegionAdaptiveTemplateY, decoder);

        if (inlineImage) {
            final PageInformationSegment pageSegment = decoder.findPageSegement(segmentHeader.getPageAssociation());
            final JBIG2Bitmap pageBitmap = pageSegment.getPageBitmap();

            if (JBIG2StreamDecoder.debug)
                log.info(pageBitmap + " " + bitmap);

            final int externalCombinationOperator = regionFlags.getFlagValue(RegionFlags.EXTERNAL_COMBINATION_OPERATOR);
            pageBitmap.combine(bitmap, regionBitmapXLocation, regionBitmapYLocation, externalCombinationOperator);
        } else {
            bitmap.setBitmapNumber(getSegmentHeader().getSegmentNumber());
            decoder.appendBitmap(bitmap);
        }

        decoder.consumeRemainingBits();
    }

    private void readTextRegionFlags() throws IOException {
        /** extract text region Segment flags */
        final short[] textRegionFlagsField = new short[2];
        decoder.readByte(textRegionFlagsField);

        int flags = BinaryOperation.getInt16(textRegionFlagsField);
        textRegionFlags.setFlags(flags);

        if (JBIG2StreamDecoder.debug)
            log.info("text region Segment flags = {} ", flags);

        final boolean sbHuff = textRegionFlags.getFlagValue(TextRegionFlags.SB_HUFF) != 0;
        if (sbHuff) {
            /** extract text region Segment Huffman flags */
            final short[] textRegionHuffmanFlagsField = new short[2];
            decoder.readByte(textRegionHuffmanFlagsField);

            flags = BinaryOperation.getInt16(textRegionHuffmanFlagsField);
            textRegionHuffmanFlags.setFlags(flags);

            if (JBIG2StreamDecoder.debug)
                log.info("text region segment Huffman flags = {} ", flags);
        }

        final boolean sbRefine = textRegionFlags.getFlagValue(TextRegionFlags.SB_REFINE) != 0;
        final int sbrTemplate = textRegionFlags.getFlagValue(TextRegionFlags.SB_R_TEMPLATE);
        if (sbRefine && sbrTemplate == 0) {
            symbolRegionAdaptiveTemplateX[0] = readATValue();
            symbolRegionAdaptiveTemplateY[0] = readATValue();
            symbolRegionAdaptiveTemplateX[1] = readATValue();
            symbolRegionAdaptiveTemplateY[1] = readATValue();
        }
    }

    /**
     * <p>Getter for the field <code>textRegionFlags</code>.</p>
     *
     * @return a {@link org.jpedal.jbig2.segment.region.text.TextRegionFlags} object.
     */
    public TextRegionFlags getTextRegionFlags() {
        return textRegionFlags;
    }

    /**
     * <p>Getter for the field <code>textRegionHuffmanFlags</code>.</p>
     *
     * @return a {@link org.jpedal.jbig2.segment.region.text.TextRegionHuffmanFlags} object.
     */
    public TextRegionHuffmanFlags getTextRegionHuffmanFlags() {
        return textRegionHuffmanFlags;
    }
}
