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
package org.loboevolution.pdfview.decode;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

/**
 * decode an array of Run Length encoded bytes into a byte array
 *
 * Author Mike Wessler
  *
 */
public class RunLengthDecode {
	/** the end of data in the RunLength encoding. */
	private static final int RUN_LENGTH_EOD = 128;

	private final ByteBuffer buf;

	/**
	 * initialize the decoder with an array of bytes in RunLength format
	 */
	private RunLengthDecode(ByteBuffer buf) {
		this.buf = buf;
	}

	/**
	 * decode the array
	 * 
	 * @return the decoded bytes
	 */
	private ByteBuffer decode() {
		// start at the beginning of the buffer
		this.buf.rewind();

		// allocate the output buffer
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int dupAmount;
		byte[] buffer = new byte[128];
		while ((dupAmount = this.buf.get()&0xFF) != RUN_LENGTH_EOD) {
			if (dupAmount >= 0 && dupAmount <= 127) {
				int amountToCopy = dupAmount + 1;
				this.buf.get(buffer, 0, amountToCopy);
				baos.write(buffer, 0, amountToCopy);
			} else {
				byte dupByte = this.buf.get();
				for (int i = 0; i < 257 - dupAmount; i++) {
					baos.write(dupByte);
				}
			}
		}
		return ByteBuffer.wrap(baos.toByteArray());
	}

	/**
	 * decode an array of bytes in RunLength format.
	 * <p>
	 * RunLength format consists of a sequence of a byte-oriented format based
	 * on run length. There are a series of "runs", where a run is a length byte
	 * followed by 1 to 128 bytes of data. If the length is 0-127, the following
	 * length+1 (1 to 128) bytes are to be copied. If the length is 129 through
	 * 255, the following single byte is copied 257-length (2 to 128) times. A
	 * length value of 128 means and End of Data (EOD).
	 *
	 * @param buf
	 *            the RUnLEngth encoded bytes in a byte buffer
	 * @param params
	 *            parameters to the decoder (ignored)
	 * @return the decoded bytes
	 * @throws org.loboevolution.pdfview.PDFParseException if any.
	 */
	public static ByteBuffer decode(ByteBuffer buf, PDFObject params) throws PDFParseException {
		RunLengthDecode me = new RunLengthDecode(buf);
		return me.decode();
	}
}
