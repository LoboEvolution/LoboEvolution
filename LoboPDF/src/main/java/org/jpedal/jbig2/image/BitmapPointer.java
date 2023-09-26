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
package org.jpedal.jbig2.image;

/**
 * <p>BitmapPointer class.</p>
 *
  *
  *
 */
public class BitmapPointer {
	private int x; 
	private int y;
	private final int width;
	private final int height;
	private int count;
	private boolean output;
	private final JBIG2Bitmap bitmap;

	/**
	 * <p>Constructor for BitmapPointer.</p>
	 *
	 * @param bitmap a {@link org.jpedal.jbig2.image.JBIG2Bitmap} object.
	 */
	public BitmapPointer(final JBIG2Bitmap bitmap) {
		this.bitmap = bitmap;
		this.height = bitmap.getHeight();
		this.width = bitmap.getWidth();
	}

	/**
	 * <p>setPointer.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 */
	public void setPointer(final int x, final int y) {
		this.x = x;
		this.y = y;
		output = this.y >= 0 && this.y < height && this.x < width;
		count = this.y * width;
	}

	/**
	 * <p>nextPixel.</p>
	 *
	 * @return a int.
	 */
	public int nextPixel() {

		// fairly certain the byte can be cached here - seems to work fine. only
		// problem would be if cached pixel was modified, and the modified
		// version needed.
//		if (y < 0 || y >= height || x >= width) {
//			return 0;
//		} else if (x < 0) {
//			x++;
//			return 0;
//		}
//
//		if (count == 0 && width - x >= 8) {
//			bits = bitmap.getPixelByte(x, y);
//			count = 8;
//		} else {
//			count = 0;
//		}
//
//		if (count > 0) {
//			int b = bits & 0x01;
//			count--;
//			bits >>= 1;
//			x++;
//			return b;
//		}
//
//		int pixel = bitmap.getPixel(x, y);
//		x++;
//
//		return pixel;
		
		if (!output) {
			return 0;
		} else if (x < 0 || x >= width) {
			x++;
			return 0;
		}
		/*if ((output == false) || x < 0) {
			x++;
			return 0;
		}*/
		//int pixel = bitmap.getPixel(x, y);
		//x++;
		return bitmap.data.get(count + x++) ? 1 : 0;
		

		//return pixel;
	}
}
