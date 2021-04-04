/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
    public static double round1(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    /**
     * <p>getSizeText.</p>
     *
     * @param numBytes a long.
     * @return a {@link java.lang.String} object.
     */
    public static String getSizeText(long numBytes) {
        if (numBytes == -1) {
            return "Not known";
        } else if (numBytes < 1024) {
            return numBytes + " bytes";
        } else {
            double numK = numBytes / 1024.0;
            if (numK < 1024) {
                return round1(numK) + " Kb";
            } else {
                double numM = numK / 1024.0;
                if (numM < 1024) {
                    return round1(numM) + " Mb";
                } else {
                    double numG = numM / 1024.0;
                    return round1(numG) + " Gb";
                }
            }
        }
    }

    /**
     * <p>getElapsedText.</p>
     *
     * @param cl a int.
     * @return a {@link java.lang.String} object.
     */
    public static String getElapsedText(int cl) {

        if (cl == -1) {
            return "Not known";
        }

        long elapsedMillis = cl / 100;

        if (elapsedMillis < 60000) {
            double unit = round1(elapsedMillis / 1000.0);
            return unit + (unit == 1 ? " second" : " seconds");
        } else if (elapsedMillis < 60000 * 60) {
            double unit = round1(elapsedMillis / 60000.0);
            return unit + (unit == 1 ? " minute" : " minutes");
        } else if (elapsedMillis < 60000 * 60 * 24) {
            double unit = round1(elapsedMillis / (60000.0 * 60));
            return unit + (unit == 1 ? " hour" : " hours");
        } else {
            double unit = round1(elapsedMillis / (60000.0 * 60 * 24));
            return unit + (unit == 1 ? " day" : " days");
        }
    }
}
