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
package org.jpedal.jbig2.io;

import java.io.IOException;

import org.jpedal.jbig2.pdf.PDFSegment;

/**
 * <p>StreamReader class.</p>
 *
  *
  *
 */
public class StreamReader {
	private final byte[] data;

	private int bitPointer = 7;

	private int bytePointer = 0;

	/**
	 * <p>Constructor for StreamReader.</p>
	 *
	 * @param data an array of {@link byte} objects.
	 */
	public StreamReader(final byte[] data) {
		this.data = data;
	}

	/**
	 * <p>readByte.</p>
	 *
	 * @param pdfSeg a {@link org.jpedal.jbig2.pdf.PDFSegment} object.
	 * @return a short.
	 */
	public short readByte(final PDFSegment pdfSeg) {
		final short bite = (short) (data[bytePointer++] & 255);

		if (pdfSeg != null)
			pdfSeg.writeToHeader(bite);

		return bite;
	}

	/**
	 * <p>readByte.</p>
	 *
	 * @param buf an array of {@link short} objects.
	 * @param pdfSeg a {@link org.jpedal.jbig2.pdf.PDFSegment} object.
	 * @throws java.io.IOException if any.
	 */
	public void readByte(final short[] buf, final PDFSegment pdfSeg) throws IOException {
		for (int i = 0; i < buf.length; i++) {
			buf[i] = (short) (data[bytePointer++] & 255);
		}

		if (pdfSeg != null)
			pdfSeg.writeToHeader(buf);
	}

	/**
	 * <p>readByte.</p>
	 *
	 * @return a short.
	 */
	public short readByte() {
		final short bite = (short) (data[bytePointer++] & 255);

		return bite;
	}

	/**
	 * <p>readByte.</p>
	 *
	 * @param buf an array of {@link short} objects.
	 */
	public void readByte(final short[] buf) {
		for (int i = 0; i < buf.length; i++) {
			buf[i] = (short) (data[bytePointer++] & 255);
		}
	}

	/**
	 * <p>readBit.</p>
	 *
	 * @return a int.
	 */
	public int readBit() {
		final short buf = readByte();
		final short mask = (short) (1 << bitPointer);

		final int bit = (buf & mask) >> bitPointer;

		bitPointer--;
		if (bitPointer == -1) {
			bitPointer = 7;
		} else {
			movePointer(-1);
		}

		return bit;
	}

	/**
	 * <p>readBits.</p>
	 *
	 * @param num a int.
	 * @return a int.
	 */
	public int readBits(final int num) {
		int result = 0;

		for (int i = 0; i < num; i++) {
			result = (result << 1) | readBit();
		}

		return result;
	}

	/**
	 * <p>movePointer.</p>
	 *
	 * @param ammount a int.
	 */
	public void movePointer(final int ammount) {
		bytePointer += ammount;
	}

	/**
	 * <p>consumeRemainingBits.</p>
	 */
	public void consumeRemainingBits() {
		if (bitPointer != 7)
			readBits(bitPointer + 1);
	}

	/**
	 * <p>isFinished.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isFinished() {
		return bytePointer == data.length;
	}
}
