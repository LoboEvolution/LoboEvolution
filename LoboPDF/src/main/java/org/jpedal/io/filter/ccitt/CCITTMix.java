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
package org.jpedal.io.filter.ccitt;

import java.io.IOException;

/**handle case with mix of CCITT1D and 2D*/
public class CCITTMix extends CCITT2D implements CCITTDecoder {

	private int fillBits = 0;

	public CCITTMix(final byte[] rawData, final int width, final int height,
                    final boolean blackIsOne, final boolean isByteAligned) {

		super(rawData, width, height, blackIsOne, isByteAligned);

		this.data=rawData;

		is2D =false;

	}

	public byte[] decode(){

		try {

			int[] prev = new int[width + 1];
			int[] curr = new int[width + 1];

			final int[] currentChangeElement = new int[2];

			// The data must start with an EOL code
			if( readEOL( true ) != 1 )
				throw new IOException( "TIFFFaxDecoder3");

			//always 1D at first
			decode1DRun(curr);

			//rest of lines either 1 or 2D
			for( int lines = 1;lines < height;lines++ ){

				// Every line must begin with an EOL followed by a bit which
				// indicates whether the following scanline is 1D or 2D encoded.
				if( readEOL( false ) == 0 ){

					//swap
					final int[] temp = prev;
					prev = curr;
					curr = temp;

					set2D(prev, curr, changingElemSize,currentChangeElement);

					curr[currIndex++] = bitOffset;
					changingElemSize = currIndex;
				}else{
					decode1DRun(curr);
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		//put it all together
		final byte[] buffer=creatOutputFromBitset();

		//by default blackIs1 is false so black pixels do not need to be set
		// invert image if needed -
		// ultimately will be quicker to add into decode
		if (!BlackIs1) {
			for (int i = 0; i < buffer.length; i++)
				buffer[i] = (byte) (255 - buffer[i]);
		}

		return buffer;
	}

	void decode1DRun(final int[] curr) throws Exception
	{
		int bitOffset=0;

		int bits, code, isT;
		int current, entry, twoBits;
		boolean isWhite = true;

		// Initialize starting of the changing elements array
		changingElemSize = 0;

		while( bitOffset < columns ){
			while( isWhite ){
				// White run (lookup entry in data and use this to fetch white value from lookuptable
				current = get1DBits( 10 );

				bitReached=bitReached+10;

				entry = white[current];

				// Get the 3 fields from the entry
				isT = entry & 0x0001;
				bits = ( entry >>> 1 ) & 0x0f;
				if( bits == 12 ){ // Additional Make up code
					// Get the next 2 bits
					twoBits = get1DBits( 2 );

					bitReached=bitReached+2;

					// Consolidate the 2 new bits and last 2 bits into 4 bits
					current = ( ( current << 2 ) & 0x000c ) | twoBits;
					entry = additionalMakeup[current];
					bits = ( entry >>> 1 ) & 0x07; // 3 bits 0000 0111
					code = ( entry >>> 4 ) & 0x0fff; // 12 bits
					bitOffset += code; // Skip white run

					outPtr=outPtr+code;

					bitReached=bitReached- (4 - bits);
				}else if( bits == 0 || bits == 15){ // ERROR
					throw new Exception(("1Derror" ) );
				}else{
					code = ( entry >>> 5 ) & 0x07ff;
					bitOffset += code;
					bitReached=bitReached- (10 - bits);
					if( isT == 0 ){
						isWhite = false;
						curr[changingElemSize++] = bitOffset;
					}
					outPtr=outPtr+code;
				}
			}

			if( bitOffset == columns )
				break;

			while(!isWhite){
				// Black run
				current = get1DBits( 4 );
				entry = initBlack[current];

				bitReached=bitReached+4;

				// Get the fields from the entry
				bits = ( entry >>> 1 ) & 0x000f;
				code = ( entry >>> 5 ) & 0x07ff;
				if( code == 100 ){
					current = get1DBits( 9 );

					bitReached=bitReached+9;

					entry = black[current];

					// Get the 3 fields from the entry
					isT = entry & 0x0001;
					bits = ( entry >>> 1 ) & 0x000f;
					code = ( entry >>> 5 ) & 0x07ff;

					if( bits == 12 ){
						// Additional makeup codes
						bitReached=bitReached- 5;
						current = get1DBits( 4 );

						bitReached=bitReached+4;

						entry = additionalMakeup[current];
						bits = ( entry >>> 1 ) & 0x07; // 3 bits 0000 0111
						code = ( entry >>> 4 ) & 0x0fff; // 12 bits

						out.set(outPtr,outPtr+ code,true);
						outPtr=outPtr+ code;

						bitOffset += code;
						bitReached=bitReached- (4 - bits);
					}else if( bits == 15 )
						// EOL code
						throw new IOException(("1D error" ) );
					else{
						out.set(outPtr,outPtr+ code,true);
						outPtr=outPtr+ code;
						bitOffset += code;
						bitReached=bitReached- (9 - bits);
						if( isT == 0 ){
							isWhite = true;
							curr[changingElemSize++] = bitOffset;
						}
					}
				}else if( code == 200 ){
					// Is a Terminating code
					current = get1DBits( 2 );

					bitReached=bitReached+2;

					entry = twoBitBlack[current];
					code = ( entry >>> 5 ) & 0x07ff;
					bits = ( entry >>> 1 ) & 0x0f;
					out.set(outPtr,outPtr+ code,true);
					outPtr=outPtr+ code;
					bitOffset += code;
					bitReached=bitReached- (2 - bits);
					isWhite = true;
					curr[changingElemSize++] = bitOffset;
				}else{
					// Is a Terminating code
					out.set(outPtr,outPtr+ code,true);
					outPtr=outPtr+ code;
					bitOffset += code;
					bitReached=bitReached- (4 - bits);
					isWhite = true;
					curr[changingElemSize++] = bitOffset;
				}
			}

			// Check whether this run completed one width
			if( bitOffset == columns )
				break;

		}
		curr[changingElemSize++] = bitOffset;
	}

	private int readEOL(final boolean isFirstEOL ) throws IOException{

		if( fillBits == 0 ){
			final int next12Bits = get1DBits( 12 );

			bitReached=bitReached+12;
			if( isFirstEOL && next12Bits == 0 ){

				final int aa=get1DBits( 4 );

				bitReached=bitReached+4;

				if( aa == 1 ){ // EOL must be padded: reset the fillBits flag.
					fillBits = 1;
					return 1;
				}
			}
			if( next12Bits != 1 )
				throw new IOException(("EOL error1" ) );
		}else if( fillBits == 1 ){

			final int bitsLeft = bitReached & 7;

			final int rr=get1DBits( bitsLeft );

			bitReached=bitReached+bitsLeft;

			if( rr != 0 )
				throw new IOException(("EOL error2" ) );

			if( bitsLeft < 4 ){
				final int rr2=get1DBits( 8 );

				bitReached=bitReached+8;

				if( rr2 != 0 )
					throw new IOException("EOL error3");
			}

			int n= get1DBits( 8 );
			bitReached=bitReached+8;

			while( n != 1 ){

				// If not all zeros
				if( n != 0 )
					throw new IOException("EOL error4");

				n = get1DBits( 8 );
				bitReached=bitReached+8;
			}
		}

		final int r= get1DBits( 1 );

		bitReached=bitReached+1;
		return r;
	}
}