/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.download;

/**
 * <p>TimingDowload class.</p>
 *
 *
 *
 */
public class TimingDowload {

    /**
     * <p>round1.</p>
     *
     * @param value a double.
     * @return a double.
     */
    public static double round1(final double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    /**
     * <p>getSizeText.</p>
     *
     * @param numBytes a long.
     * @return a {@link java.lang.String} object.
     */
    public static String getSizeText(final long numBytes) {
        if (numBytes == -1) {
            return "Not known";
        } else if (numBytes < 1024) {
            return numBytes + " bytes";
        } else {
            final double numK = numBytes / 1024.0;
            if (numK < 1024) {
                return round1(numK) + " Kb";
            } else {
                final double numM = numK / 1024.0;
                if (numM < 1024) {
                    return round1(numM) + " Mb";
                } else {
                    final double numG = numM / 1024.0;
                    return round1(numG) + " Gb";
                }
            }
        }
    }

    /**
     * <p>getElapsedText.</p>
     *
     * @param cl a {@link java.lang.Integer} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getElapsedText(final int cl) {

        if (cl == -1) {
            return "Not known";
        }

        final long elapsedMillis = cl / 100;

        if (elapsedMillis < 60000) {
            final double unit = round1(elapsedMillis / 1000.0);
            return unit + (unit == 1 ? " second" : " seconds");
        } else if (elapsedMillis < 60000 * 60) {
            final double unit = round1(elapsedMillis / 60000.0);
            return unit + (unit == 1 ? " minute" : " minutes");
        } else if (elapsedMillis < 60000 * 60 * 24) {
            final double unit = round1(elapsedMillis / (60000.0 * 60));
            return unit + (unit == 1 ? " hour" : " hours");
        } else {
            final double unit = round1(elapsedMillis / (60000.0 * 60 * 24));
            return unit + (unit == 1 ? " day" : " days");
        }
    }
}
