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

import java.io.IOException;
import java.util.logging.Logger;

import org.jpedal.jbig2.JBIG2Exception;
import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;
import org.jpedal.jbig2.image.JBIG2Bitmap;
import org.jpedal.jbig2.segment.pageinformation.PageInformationFlags;
import org.jpedal.jbig2.segment.pageinformation.PageInformationSegment;
import org.jpedal.jbig2.segment.region.RegionFlags;
import org.jpedal.jbig2.segment.region.RegionSegment;

/**
 * <p>GenericRegionSegment class.</p>
 *
  *
  *
 */
public class GenericRegionSegment extends RegionSegment {
	
	private static final Logger logger = Logger.getLogger(GenericRegionSegment.class.getName());
    private final GenericRegionFlags genericRegionFlags = new GenericRegionFlags();

    private final boolean inlineImage;

    private boolean unknownLength = false;
    
    /**
     * <p>Constructor for GenericRegionSegment.</p>
     *
     * @param streamDecoder a {@link org.jpedal.jbig2.decoders.JBIG2StreamDecoder} object.
     * @param inlineImage a boolean.
     */
    public GenericRegionSegment(JBIG2StreamDecoder streamDecoder, boolean inlineImage) {
        super(streamDecoder);

        this.inlineImage = inlineImage;
    }

    /**
     * <p>readSegment.</p>
     *
     * @throws java.io.IOException if any.
     * @throws org.jpedal.jbig2.JBIG2Exception if any.
     */
    public void readSegment() throws IOException, JBIG2Exception {
    	
    	if (JBIG2StreamDecoder.debug)
    		logger.info("==== Reading Immediate Generic Region ====");
    	
    	super.readSegment();
        
        /** read text region Segment flags */
        readGenericRegionFlags();

        boolean useMMR = genericRegionFlags.getFlagValue(GenericRegionFlags.MMR) != 0;
        int template = genericRegionFlags.getFlagValue(GenericRegionFlags.GB_TEMPLATE);
        
        short[] genericBAdaptiveTemplateX = new short[4];
    	short[] genericBAdaptiveTemplateY = new short[4];
        
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
        
        boolean typicalPredictionGenericDecodingOn = genericRegionFlags.getFlagValue(GenericRegionFlags.TPGDON) != 0;
        int length = segmentHeader.getSegmentDataLength();
        int bytesRead = 0;

        if (length == -1) {
        	/** 
        	 * length of data is unknown, so it needs to be determined through examination of the data.
        	 * See 7.2.7 - Segment data length of the JBIG2 specification.
        	 */
        	
        	unknownLength = true;
        	
        	short match1;
        	short match2;
        	
        	if (useMMR) {
        		// look for 0x00 0x00 (0, 0)
        		
        		match1 = 0;
        		match2 = 0;
        	} else {
        		// look for 0xFF 0xAC (255, 172)
        		
        		match1 = 255;
        		match2 = 172;
        	}
        	
        	
    		while(true) {
    			short bite1 = decoder.readByte();
    			bytesRead++;
    			
    			if (bite1 == match1) {
    				short bite2 = decoder.readByte();
    				bytesRead++;
    				
    				if (bite2 == match2) {
    					length = bytesRead - 2;
    					break;
    				}
    			}
    		}
    		
    		decoder.movePointer(-bytesRead);
        }
        
        JBIG2Bitmap bitmap = new JBIG2Bitmap(regionBitmapWidth, regionBitmapHeight, arithmeticDecoder, huffmanDecoder, mmrDecoder);
        bitmap.clear(0);
        bitmap.readBitmap(useMMR, template, typicalPredictionGenericDecodingOn, false, null, genericBAdaptiveTemplateX, genericBAdaptiveTemplateY, useMMR ? bytesRead : length - 18);
        
        
        if (inlineImage) {
            PageInformationSegment pageSegment = decoder.findPageSegement(segmentHeader.getPageAssociation());
            JBIG2Bitmap pageBitmap = pageSegment.getPageBitmap();

            int extCombOp = regionFlags.getFlagValue(RegionFlags.EXTERNAL_COMBINATION_OPERATOR);
            
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
        /** extract text region Segment flags */
        short genericRegionFlagsField = decoder.readByte();

        genericRegionFlags.setFlags(genericRegionFlagsField);
        
        if (JBIG2StreamDecoder.debug)
        	logger.info("generic region Segment flags = " + genericRegionFlagsField);
    }

    /**
     * <p>Getter for the field <code>genericRegionFlags</code>.</p>
     *
     * @return a {@link org.jpedal.jbig2.segment.region.generic.GenericRegionFlags} object.
     */
    public GenericRegionFlags getGenericRegionFlags() {
        return genericRegionFlags;
    }
}
