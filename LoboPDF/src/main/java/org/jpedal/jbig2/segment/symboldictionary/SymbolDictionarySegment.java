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
package org.jpedal.jbig2.segment.symboldictionary;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jpedal.jbig2.JBIG2Exception;
import org.jpedal.jbig2.decoders.ArithmeticDecoderStats;
import org.jpedal.jbig2.decoders.DecodeIntResult;
import org.jpedal.jbig2.decoders.HuffmanDecoder;
import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;
import org.jpedal.jbig2.image.JBIG2Bitmap;
import org.jpedal.jbig2.segment.Segment;
import org.jpedal.jbig2.util.BinaryOperation;

import java.io.IOException;

/**
 * <p>SymbolDictionarySegment class.</p>
 */
@Slf4j
@Getter
@Setter
public class SymbolDictionarySegment extends Segment {
    final short[] symbolDictionaryAdaptiveTemplateX = new short[4];
    final short[] symbolDictionaryAdaptiveTemplateY = new short[4];
    final short[] symbolDictionaryRAdaptiveTemplateX = new short[2];
    final short[] symbolDictionaryRAdaptiveTemplateY = new short[2];
    private int noOfExportedSymbols;
    private int noOfNewSymbols;
    private JBIG2Bitmap[] bitmaps;

    private SymbolDictionaryFlags symbolDictionaryFlags = new SymbolDictionaryFlags();

    private ArithmeticDecoderStats genericRegionStats;
    private ArithmeticDecoderStats refinementRegionStats;

    /**
     * <p>Constructor for SymbolDictionarySegment.</p>
     *
     * @param streamDecoder a {@link org.jpedal.jbig2.decoders.JBIG2StreamDecoder} object.
     */
    public SymbolDictionarySegment(final JBIG2StreamDecoder streamDecoder) {
        super(streamDecoder);
    }

    /**
     * <p>readSegment.</p>
     *
     * @throws java.io.IOException             if any.
     * @throws org.jpedal.jbig2.JBIG2Exception if any.
     */
    public void readSegment() throws IOException, JBIG2Exception {

        if (JBIG2StreamDecoder.debug)
            log.info("==== Read Segment Symbol Dictionary ====");

        /** read symbol dictionary flags */
        readSymbolDictionaryFlags();

        //List codeTables = new ArrayList();
        int numberOfInputSymbols = 0;
        final int noOfReferredToSegments = segmentHeader.getReferredToSegmentCount();
        final int[] referredToSegments = segmentHeader.getReferredToSegments();

        for (int i = 0; i < noOfReferredToSegments; i++) {
            final Segment seg = decoder.findSegment(referredToSegments[i]);
            final int type = seg.getSegmentHeader().getSegmentType();

            if (type == Segment.SYMBOL_DICTIONARY) {
                numberOfInputSymbols += ((SymbolDictionarySegment) seg).noOfExportedSymbols;
            } else if (type == Segment.TABLES) {
                //codeTables.add(seg);
            }
        }

        int symbolCodeLength = 0;
        int i = 1;
        while (i < numberOfInputSymbols + noOfNewSymbols) {
            symbolCodeLength++;
            i <<= 1;
        }

        final JBIG2Bitmap[] bitmaps = new JBIG2Bitmap[numberOfInputSymbols + noOfNewSymbols];

        int k = 0;
        SymbolDictionarySegment inputSymbolDictionary = null;
        for (i = 0; i < noOfReferredToSegments; i++) {
            final Segment seg = decoder.findSegment(referredToSegments[i]);
            if (seg.getSegmentHeader().getSegmentType() == Segment.SYMBOL_DICTIONARY) {
                inputSymbolDictionary = (SymbolDictionarySegment) seg;
                for (int j = 0; j < inputSymbolDictionary.noOfExportedSymbols; j++) {
                    bitmaps[k++] = inputSymbolDictionary.bitmaps[j];
                }
            }
        }

        int[][] huffmanDHTable = null;
        int[][] huffmanDWTable = null;

        int[][] huffmanBMSizeTable = null;
        int[][] huffmanAggInstTable = null;

        final boolean sdHuffman = symbolDictionaryFlags.getFlagValue(SymbolDictionaryFlags.SD_HUFF) != 0;
        final int sdHuffmanDifferenceHeight = symbolDictionaryFlags.getFlagValue(SymbolDictionaryFlags.SD_HUFF_DH);
        final int sdHuffmanDiferrenceWidth = symbolDictionaryFlags.getFlagValue(SymbolDictionaryFlags.SD_HUFF_DW);
        final int sdHuffBitmapSize = symbolDictionaryFlags.getFlagValue(SymbolDictionaryFlags.SD_HUFF_BM_SIZE);
        final int sdHuffAggregationInstances = symbolDictionaryFlags.getFlagValue(SymbolDictionaryFlags.SD_HUFF_AGG_INST);

        i = 0;
        if (sdHuffman) {
            if (sdHuffmanDifferenceHeight == 0) {
                huffmanDHTable = HuffmanDecoder.huffmanTableD;
            } else if (sdHuffmanDifferenceHeight == 1) {
                huffmanDHTable = HuffmanDecoder.huffmanTableE;
            } else {
                //huffmanDHTable = ((JBIG2CodeTable) codeTables.get(i++)).getHuffTable();
            }

            if (sdHuffmanDiferrenceWidth == 0) {
                huffmanDWTable = HuffmanDecoder.huffmanTableB;
            } else if (sdHuffmanDiferrenceWidth == 1) {
                huffmanDWTable = HuffmanDecoder.huffmanTableC;
            } else {
                //huffmanDWTable = ((JBIG2CodeTable) codeTables.get(i++)).getHuffTable();
            }

            if (sdHuffBitmapSize == 0) {
                huffmanBMSizeTable = HuffmanDecoder.huffmanTableA;
            } else {
                //huffmanBMSizeTable = ((JBIG2CodeTable) codeTables.get(i++)).getHuffTable();
            }

            if (sdHuffAggregationInstances == 0) {
                huffmanAggInstTable = HuffmanDecoder.huffmanTableA;
            } else {
                //huffmanAggInstTable = ((JBIG2CodeTable) codeTables.get(i++)).getHuffTable();
            }
        }

        final int contextUsed = symbolDictionaryFlags.getFlagValue(SymbolDictionaryFlags.BITMAP_CC_USED);
        final int sdTemplate = symbolDictionaryFlags.getFlagValue(SymbolDictionaryFlags.SD_TEMPLATE);

        if (!sdHuffman) {
            if (contextUsed != 0 && inputSymbolDictionary != null) {
                arithmeticDecoder.resetGenericStats(sdTemplate, inputSymbolDictionary.genericRegionStats);
            } else {
                arithmeticDecoder.resetGenericStats(sdTemplate, null);
            }
            arithmeticDecoder.resetIntStats(symbolCodeLength);
            arithmeticDecoder.start();
        }

        final int sdRefinementAggregate = symbolDictionaryFlags.getFlagValue(SymbolDictionaryFlags.SD_REF_AGG);
        final int sdRefinementTemplate = symbolDictionaryFlags.getFlagValue(SymbolDictionaryFlags.SD_R_TEMPLATE);
        if (sdRefinementAggregate != 0) {
            if (contextUsed != 0 && inputSymbolDictionary != null) {
                arithmeticDecoder.resetRefinementStats(sdRefinementTemplate, inputSymbolDictionary.refinementRegionStats);
            } else {
                arithmeticDecoder.resetRefinementStats(sdRefinementTemplate, null);
            }
        }

        final int[] deltaWidths = new int[noOfNewSymbols];

        int deltaHeight = 0;
        i = 0;

        while (i < noOfNewSymbols) {

            int instanceDeltaHeight = 0;

            if (sdHuffman) {
                instanceDeltaHeight = huffmanDecoder.decodeInt(huffmanDHTable).intResult();
            } else {
                instanceDeltaHeight = arithmeticDecoder.decodeInt(arithmeticDecoder.iadhStats).intResult();
            }

            if (instanceDeltaHeight < 0 && -instanceDeltaHeight >= deltaHeight) {
                if (JBIG2StreamDecoder.debug)
                    log.info("Bad delta-height value in JBIG2 symbol dictionary");
            }

            deltaHeight += instanceDeltaHeight;
            int symbolWidth = 0;
            int totalWidth = 0;
            int j = i;

            while (true) {

                int deltaWidth = 0;

                final DecodeIntResult decodeIntResult;
                if (sdHuffman) {
                    decodeIntResult = huffmanDecoder.decodeInt(huffmanDWTable);
                } else {
                    decodeIntResult = arithmeticDecoder.decodeInt(arithmeticDecoder.iadwStats);
                }

                if (!decodeIntResult.booleanResult())
                    break;

                deltaWidth = decodeIntResult.intResult();

                if (deltaWidth < 0 && -deltaWidth >= symbolWidth) {
                    if (JBIG2StreamDecoder.debug)
                        log.info("Bad delta-width value in JBIG2 symbol dictionary");
                }

                symbolWidth += deltaWidth;

                if (sdHuffman && sdRefinementAggregate == 0) {
                    deltaWidths[i] = symbolWidth;
                    totalWidth += symbolWidth;

                } else if (sdRefinementAggregate == 1) {

                    int refAggNum = 0;

                    if (sdHuffman) {
                        refAggNum = huffmanDecoder.decodeInt(huffmanAggInstTable).intResult();
                    } else {
                        refAggNum = arithmeticDecoder.decodeInt(arithmeticDecoder.iaaiStats).intResult();
                    }

                    if (refAggNum == 1) {

                        int symbolID = 0, referenceDX = 0, referenceDY = 0;

                        if (sdHuffman) {
                            symbolID = decoder.readBits(symbolCodeLength);
                            referenceDX = huffmanDecoder.decodeInt(HuffmanDecoder.huffmanTableO).intResult();
                            referenceDY = huffmanDecoder.decodeInt(HuffmanDecoder.huffmanTableO).intResult();

                            decoder.consumeRemainingBits();
                            arithmeticDecoder.start();
                        } else {
                            symbolID = (int) arithmeticDecoder.decodeIAID(symbolCodeLength, arithmeticDecoder.iaidStats);
                            referenceDX = arithmeticDecoder.decodeInt(arithmeticDecoder.iardxStats).intResult();
                            referenceDY = arithmeticDecoder.decodeInt(arithmeticDecoder.iardyStats).intResult();
                        }

                        final JBIG2Bitmap referredToBitmap = bitmaps[symbolID];

                        final JBIG2Bitmap bitmap = new JBIG2Bitmap(symbolWidth, deltaHeight, arithmeticDecoder, huffmanDecoder, mmrDecoder);
                        bitmap.readGenericRefinementRegion(sdRefinementTemplate, false, referredToBitmap, referenceDX, referenceDY, symbolDictionaryRAdaptiveTemplateX,
                                symbolDictionaryRAdaptiveTemplateY);

                        bitmaps[numberOfInputSymbols + i] = bitmap;

                    } else {
                        final JBIG2Bitmap bitmap = new JBIG2Bitmap(symbolWidth, deltaHeight, arithmeticDecoder, huffmanDecoder, mmrDecoder);
                        bitmap.readTextRegion(sdHuffman, true, refAggNum, 0, numberOfInputSymbols + i, null, symbolCodeLength, bitmaps, 0, 0, false, 1, 0,
                                HuffmanDecoder.huffmanTableF, HuffmanDecoder.huffmanTableH, HuffmanDecoder.huffmanTableK, HuffmanDecoder.huffmanTableO, HuffmanDecoder.huffmanTableO,
                                HuffmanDecoder.huffmanTableO, HuffmanDecoder.huffmanTableO, HuffmanDecoder.huffmanTableA, sdRefinementTemplate, symbolDictionaryRAdaptiveTemplateX,
                                symbolDictionaryRAdaptiveTemplateY, decoder);

                        bitmaps[numberOfInputSymbols + i] = bitmap;
                    }
                } else {
                    final JBIG2Bitmap bitmap = new JBIG2Bitmap(symbolWidth, deltaHeight, arithmeticDecoder, huffmanDecoder, mmrDecoder);
                    bitmap.readBitmap(false, sdTemplate, false, false, null, symbolDictionaryAdaptiveTemplateX, symbolDictionaryAdaptiveTemplateY, 0);
                    bitmaps[numberOfInputSymbols + i] = bitmap;
                }

                i++;
            }

            if (sdHuffman && sdRefinementAggregate == 0) {
                final int bmSize = huffmanDecoder.decodeInt(huffmanBMSizeTable).intResult();
                decoder.consumeRemainingBits();

                final JBIG2Bitmap collectiveBitmap = new JBIG2Bitmap(totalWidth, deltaHeight, arithmeticDecoder, huffmanDecoder, mmrDecoder);

                if (bmSize == 0) {

                    final int padding = totalWidth % 8;
                    final int bytesPerRow = (int) Math.ceil(totalWidth / 8d);

                    //short[] bitmap = new short[totalWidth];
                    //decoder.readByte(bitmap);
                    final int size = deltaHeight * ((totalWidth + 7) >> 3);
                    final short[] bitmap = new short[size];
                    decoder.readByte(bitmap);

                    final short[][] logicalMap = new short[deltaHeight][bytesPerRow];
                    int count = 0;
                    for (int row = 0; row < deltaHeight; row++) {
                        for (int col = 0; col < bytesPerRow; col++) {
                            logicalMap[row][col] = bitmap[count];
                            count++;
                        }
                    }

                    int collectiveBitmapRow = 0, collectiveBitmapCol = 0;

                    for (int row = 0; row < deltaHeight; row++) {
                        for (int col = 0; col < bytesPerRow; col++) {
                            if (col == (bytesPerRow - 1)) { // this is the last
                                // byte in the row
                                final short currentByte = logicalMap[row][col];
                                for (int bitPointer = 7; bitPointer >= padding; bitPointer--) {
                                    final short mask = (short) (1 << bitPointer);
                                    final int bit = (currentByte & mask) >> bitPointer;

                                    collectiveBitmap.setPixel(collectiveBitmapCol, collectiveBitmapRow, bit);
                                    collectiveBitmapCol++;
                                }
                                collectiveBitmapRow++;
                                collectiveBitmapCol = 0;
                            } else {
                                final short currentByte = logicalMap[row][col];
                                for (int bitPointer = 7; bitPointer >= 0; bitPointer--) {
                                    final short mask = (short) (1 << bitPointer);
                                    final int bit = (currentByte & mask) >> bitPointer;

                                    collectiveBitmap.setPixel(collectiveBitmapCol, collectiveBitmapRow, bit);
                                    collectiveBitmapCol++;
                                }
                            }
                        }
                    }

                } else {
                    collectiveBitmap.readBitmap(true, 0, false, false, null, null, null, bmSize);
                }

                int x = 0;
                while (j < i) {
                    bitmaps[numberOfInputSymbols + j] = collectiveBitmap.getSlice(x, 0, deltaWidths[j], deltaHeight);
                    x += deltaWidths[j];

                    j++;
                }
            }
        }

        this.bitmaps = new JBIG2Bitmap[noOfExportedSymbols];

        int j = i = 0;
        boolean export = false;
        while (i < numberOfInputSymbols + noOfNewSymbols) {

            int run = 0;
            if (sdHuffman) {
                run = huffmanDecoder.decodeInt(HuffmanDecoder.huffmanTableA).intResult();
            } else {
                run = arithmeticDecoder.decodeInt(arithmeticDecoder.iaexStats).intResult();
            }

            if (export) {
                for (int cnt = 0; cnt < run; cnt++) {
                    this.bitmaps[j++] = bitmaps[i++];
                }
            } else {
                i += run;
            }

            export = !export;
        }

        final int contextRetained = symbolDictionaryFlags.getFlagValue(SymbolDictionaryFlags.BITMAP_CC_RETAINED);
        if (!sdHuffman && contextRetained == 1) {
            genericRegionStats = genericRegionStats.copy();
            if (sdRefinementAggregate == 1) {
                refinementRegionStats = refinementRegionStats.copy();
            }
        }

        /** consume any remaining bits */
        decoder.consumeRemainingBits();
    }

    private void readSymbolDictionaryFlags() throws IOException {
        /** extract symbol dictionary flags */
        final short[] symbolDictionaryFlagsField = new short[2];
        decoder.readByte(symbolDictionaryFlagsField);

        final int flags = BinaryOperation.getInt16(symbolDictionaryFlagsField);
        symbolDictionaryFlags.setFlags(flags);

        if (JBIG2StreamDecoder.debug)
            log.info("symbolDictionaryFlags: {} ", flags);

        // symbol dictionary AT flags
        final int sdHuff = symbolDictionaryFlags.getFlagValue(SymbolDictionaryFlags.SD_HUFF);
        final int sdTemplate = symbolDictionaryFlags.getFlagValue(SymbolDictionaryFlags.SD_TEMPLATE);
        if (sdHuff == 0) {
            if (sdTemplate == 0) {
                symbolDictionaryAdaptiveTemplateX[0] = readATValue();
                symbolDictionaryAdaptiveTemplateY[0] = readATValue();
                symbolDictionaryAdaptiveTemplateX[1] = readATValue();
                symbolDictionaryAdaptiveTemplateY[1] = readATValue();
                symbolDictionaryAdaptiveTemplateX[2] = readATValue();
                symbolDictionaryAdaptiveTemplateY[2] = readATValue();
                symbolDictionaryAdaptiveTemplateX[3] = readATValue();
                symbolDictionaryAdaptiveTemplateY[3] = readATValue();
            } else {
                symbolDictionaryAdaptiveTemplateX[0] = readATValue();
                symbolDictionaryAdaptiveTemplateY[0] = readATValue();
            }
        }

        // symbol dictionary refinement AT flags
        final int refAgg = symbolDictionaryFlags.getFlagValue(SymbolDictionaryFlags.SD_REF_AGG);
        final int sdrTemplate = symbolDictionaryFlags.getFlagValue(SymbolDictionaryFlags.SD_R_TEMPLATE);
        if (refAgg != 0 && sdrTemplate == 0) {
            symbolDictionaryRAdaptiveTemplateX[0] = readATValue();
            symbolDictionaryRAdaptiveTemplateY[0] = readATValue();
            symbolDictionaryRAdaptiveTemplateX[1] = readATValue();
            symbolDictionaryRAdaptiveTemplateY[1] = readATValue();
        }

        /** extract no of exported symbols */
        final short[] noOfExportedSymbolsField = new short[4];
        decoder.readByte(noOfExportedSymbolsField);

        final int noOfExportedSymbols = BinaryOperation.getInt32(noOfExportedSymbolsField);
        this.noOfExportedSymbols = noOfExportedSymbols;

        if (JBIG2StreamDecoder.debug)
            log.info("noOfExportedSymbols = {} ", noOfExportedSymbols);

        /** extract no of new symbols */
        final short[] noOfNewSymbolsField = new short[4];
        decoder.readByte(noOfNewSymbolsField);

        final int noOfNewSymbols = BinaryOperation.getInt32(noOfNewSymbolsField);
        this.noOfNewSymbols = noOfNewSymbols;

        if (JBIG2StreamDecoder.debug)
            log.info("noOfNewSymbols = {}", noOfNewSymbols);
    }
}
