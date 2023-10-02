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
package org.jpedal.jbig2.util;

/**
 * <p>BinaryOperation class.</p>
 */
public class BinaryOperation {

    /**
     * Constant <code>LEFT_SHIFT=0</code>
     */
    public static final int LEFT_SHIFT = 0;
    /**
     * Constant <code>RIGHT_SHIFT=1</code>
     */
    public static final int RIGHT_SHIFT = 1;

    /**
     * Constant <code>LONGMASK=0xffffffffl</code>
     */
    public static final long LONGMASK = 0xffffffffL; // 1111 1111 1111 1111 1111 1111 1111 1111
    /**
     * Constant <code>INTMASK=0xff</code>
     */
    public static final int INTMASK = 0xff; // 1111 1111

    /**
     * <p>getInt32.</p>
     *
     * @param number an array of {@link short} objects.
     * @return a int.
     */
    public static int getInt32(final short[] number) {
        return (number[0] << 24) | (number[1] << 16) | (number[2] << 8) | number[3];
    }

    /**
     * <p>getInt16.</p>
     *
     * @param number an array of {@link short} objects.
     * @return a int.
     */
    public static int getInt16(final short[] number) {
        return (number[0] << 8) | number[1];
    }

    /**
     * <p>bit32ShiftL.</p>
     *
     * @param number a long.
     * @param shift  a int.
     * @return a long.
     */
    public static long bit32ShiftL(final long number, final int shift) {
        //return (number << shift) & LONGMASK;
        return number << shift;
    }

    /**
     * <p>bit32ShiftR.</p>
     *
     * @param number a long.
     * @param shift  a int.
     * @return a long.
     */
    public static long bit32ShiftR(final long number, final int shift) {
        //return (number >> shift) & LONGMASK;
        return number >> shift;
    }

	/*public static final  long bit32Shift(long number, final int shift, final int direction) {
		if (direction == LEFT_SHIFT)
			number <<= shift;
		else
			number >>= shift;

		return (number & LONGMASK);
	}*/

    /**
     * <p>bit8Shift.</p>
     *
     * @param number    a int.
     * @param shift     a int.
     * @param direction a int.
     * @return a int.
     */
    public static int bit8Shift(int number, final int shift, final int direction) {
        if (direction == LEFT_SHIFT)
            number <<= shift;
        else
            number >>= shift;

        return (number & INTMASK);
    }

    /**
     * <p>getInt32.</p>
     *
     * @param number an array of {@link byte} objects.
     * @return a int.
     */
    public static int getInt32(final byte[] number) {
        return (number[0] << 24) | (number[1] << 16) | (number[2] << 8) | number[3];
    }
}
