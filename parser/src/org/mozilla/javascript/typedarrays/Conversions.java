package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.ScriptRuntime;


/**
 * Numeric conversions from section 7 of the ECMAScript 6 standard.
 */

public class Conversions
{
    
    /** The Constant EIGHT_BIT. */
    public static final int EIGHT_BIT = 1 << 8;
    
    /** The Constant SIXTEEN_BIT. */
    public static final int SIXTEEN_BIT = 1 << 16;
    
    /** The Constant THIRTYTWO_BIT. */
    public static final long THIRTYTWO_BIT = 1L << 32L;

    /**
     * To int8.
     *
     * @param arg the arg
     * @return the int
     */
    public static int toInt8(Object arg)
    {
        int iv;
        if (arg instanceof Integer) {
            iv = (Integer)arg;
        } else {
            iv = ScriptRuntime.toInt32(arg);
        }

        int int8Bit = iv % EIGHT_BIT;
        return (int8Bit >= (1 << 7)) ? (int8Bit - EIGHT_BIT) : int8Bit;
    }

    /**
     * To uint8.
     *
     * @param arg the arg
     * @return the int
     */
    public static int toUint8(Object arg)
    {
        int iv;
        if (arg instanceof Integer) {
            iv = ((Integer)arg);
        } else {
            iv = ScriptRuntime.toInt32(arg);
        }

        return iv % EIGHT_BIT;
    }

    /**
     * To uint8 clamp.
     *
     * @param arg the arg
     * @return the int
     */
    public static int toUint8Clamp(Object arg)
    {
        double d = ScriptRuntime.toNumber(arg);
        if (d <= 0.0) {
            return 0;
        }
        if (d >= 255.0) {
            return 255;
        }

        // Complex rounding behavior -- see 7.1.11
        double f = Math.floor(d);
        if ((f + 0.5) < d) {
            return (int)(f + 1.0);
        }
        if (d < (f + 0.5)) {
            return (int)f;
        }
        if (((int)f % 2) != 0) {
            return (int)f + 1;
        }
        return (int)f;
    }

    /**
     * To int16.
     *
     * @param arg the arg
     * @return the int
     */
    public static int toInt16(Object arg)
    {
        int iv;
        if (arg instanceof Integer) {
            iv = ((Integer)arg);
        } else {
            iv = ScriptRuntime.toInt32(arg);
        }

        int int16Bit = iv % SIXTEEN_BIT;
        return (int16Bit >= (1 << 15)) ? (int16Bit - SIXTEEN_BIT) : int16Bit;
    }

    /**
     * To uint16.
     *
     * @param arg the arg
     * @return the int
     */
    public static int toUint16(Object arg)
    {
        int iv;
        if (arg instanceof Integer) {
            iv = ((Integer)arg);
        } else {
            iv = ScriptRuntime.toInt32(arg);
        }

        return iv % SIXTEEN_BIT;
    }

    /**
     * To int32.
     *
     * @param arg the arg
     * @return the int
     */
    public static int toInt32(Object arg)
    {
        long lv = (long)ScriptRuntime.toNumber(arg);
        long int32Bit = lv % THIRTYTWO_BIT;
        return (int)((int32Bit >= (1L << 31L)) ? (int32Bit - THIRTYTWO_BIT) : int32Bit);
    }

    /**
     * To uint32.
     *
     * @param arg the arg
     * @return the long
     */
    public static long toUint32(Object arg)
    {
        long lv = (long)ScriptRuntime.toNumber(arg);
        return lv % THIRTYTWO_BIT;
    }
}
