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
package org.jpedal.jbig2.segment;

import java.util.logging.Logger;

import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;

/**
 * <p>SegmentHeader class.</p>
 *
  *
  *
 */
public class SegmentHeader {

	private static final Logger logger = Logger.getLogger(SegmentHeader.class.getName());
	private int segmentNumber;

	private int segmentType;
	private boolean pageAssociationSizeSet;
	private boolean deferredNonRetainSet;

	private int referredToSegmentCount;
	private short[] rententionFlags;

	private int[] referredToSegments;
	private int pageAssociation;
	private int dataLength;

	/**
	 * <p>Setter for the field <code>segmentNumber</code>.</p>
	 *
	 * @param SegmentNumber a int.
	 */
	public void setSegmentNumber(int SegmentNumber) {
		this.segmentNumber = SegmentNumber;
	}

	/**
	 * <p>setSegmentHeaderFlags.</p>
	 *
	 * @param SegmentHeaderFlags a short.
	 */
	public void setSegmentHeaderFlags(short SegmentHeaderFlags) {
		segmentType = SegmentHeaderFlags & 63; // 63 = 00111111
		pageAssociationSizeSet = (SegmentHeaderFlags & 64) == 64; // 64 = // 01000000
		deferredNonRetainSet = (SegmentHeaderFlags & 80) == 80; // 64 = 10000000

		if (JBIG2StreamDecoder.debug) {
			logger.info("SegmentType = " + segmentType);
			logger.info("pageAssociationSizeSet = " + pageAssociationSizeSet);
			logger.info("deferredNonRetainSet = " + deferredNonRetainSet);
		}
	}

	/**
	 * <p>Setter for the field <code>referredToSegmentCount</code>.</p>
	 *
	 * @param referredToSegmentCount a int.
	 */
	public void setReferredToSegmentCount(int referredToSegmentCount) {
		this.referredToSegmentCount = referredToSegmentCount;
	}

	/**
	 * <p>Setter for the field <code>rententionFlags</code>.</p>
	 *
	 * @param rententionFlags an array of {@link short} objects.
	 */
	public void setRententionFlags(short[] rententionFlags) {
		this.rententionFlags = rententionFlags;
	}

	/**
	 * <p>Setter for the field <code>referredToSegments</code>.</p>
	 *
	 * @param referredToSegments an array of {@link int} objects.
	 */
	public void setReferredToSegments(int[] referredToSegments) {
		this.referredToSegments = referredToSegments;
	}

	/**
	 * <p>Getter for the field <code>referredToSegments</code>.</p>
	 *
	 * @return an array of {@link int} objects.
	 */
	public int[] getReferredToSegments() {
		return referredToSegments;
	}

	/**
	 * <p>Getter for the field <code>segmentType</code>.</p>
	 *
	 * @return a int.
	 */
	public int getSegmentType() {
		return segmentType;
	}

	/**
	 * <p>Getter for the field <code>segmentNumber</code>.</p>
	 *
	 * @return a int.
	 */
	public int getSegmentNumber() {
		return segmentNumber;
	}

	/**
	 * <p>isPageAssociationSizeSet.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isPageAssociationSizeSet() {
		return pageAssociationSizeSet;
	}

	/**
	 * <p>isDeferredNonRetainSet.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isDeferredNonRetainSet() {
		return deferredNonRetainSet;
	}

	/**
	 * <p>Getter for the field <code>referredToSegmentCount</code>.</p>
	 *
	 * @return a int.
	 */
	public int getReferredToSegmentCount() {
		return referredToSegmentCount;
	}

	/**
	 * <p>Getter for the field <code>rententionFlags</code>.</p>
	 *
	 * @return an array of {@link short} objects.
	 */
	public short[] getRententionFlags() {
		return rententionFlags;
	}

	/**
	 * <p>Getter for the field <code>pageAssociation</code>.</p>
	 *
	 * @return a int.
	 */
	public int getPageAssociation() {
		return pageAssociation;
	}

	/**
	 * <p>Setter for the field <code>pageAssociation</code>.</p>
	 *
	 * @param pageAssociation a int.
	 */
	public void setPageAssociation(int pageAssociation) {
		this.pageAssociation = pageAssociation;
	}

	/**
	 * <p>Setter for the field <code>dataLength</code>.</p>
	 *
	 * @param dataLength a int.
	 */
	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	/**
	 * <p>Setter for the field <code>segmentType</code>.</p>
	 *
	 * @param type a int.
	 */
	public void setSegmentType(int type) {
		this.segmentType = type;
	}

	/**
	 * <p>getSegmentDataLength.</p>
	 *
	 * @return a int.
	 */
	public int getSegmentDataLength() {
		return dataLength;
	}
}
