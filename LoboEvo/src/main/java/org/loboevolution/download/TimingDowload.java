package org.loboevolution.download;

public class TimingDowload {

    public static double round1(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

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
