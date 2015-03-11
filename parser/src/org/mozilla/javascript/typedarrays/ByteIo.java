package org.mozilla.javascript.typedarrays;


/**
 * The Class ByteIo.
 */
public class ByteIo
{
    
    /**
     * Read int8.
     *
     * @param buf the buf
     * @param offset the offset
     * @return the object
     */
    public static Object readInt8(byte[] buf, int offset)
    {
        return buf[offset];
    }

    /**
     * Write int8.
     *
     * @param buf the buf
     * @param offset the offset
     * @param val the val
     */
    public static void writeInt8(byte[] buf, int offset, int val)
    {
        buf[offset] = (byte)val;
    }

    /**
     * Read uint8.
     *
     * @param buf the buf
     * @param offset the offset
     * @return the object
     */
    public static Object readUint8(byte[] buf, int offset)
    {
        return buf[offset] & 0xff;
    }

    /**
     * Write uint8.
     *
     * @param buf the buf
     * @param offset the offset
     * @param val the val
     */
    public static void writeUint8(byte[] buf, int offset, int val)
    {
        buf[offset] = (byte)(val & 0xff);
    }

    /**
     * Do read int16.
     *
     * @param buf the buf
     * @param offset the offset
     * @param littleEndian the little endian
     * @return the short
     */
    private static short doReadInt16(byte[] buf, int offset, boolean littleEndian)
    {
        // Need to coalesce to short here so that we stay in range
        if (littleEndian) {
            return
                (short)((buf[offset]      & 0xff) |
                       ((buf[offset + 1] & 0xff) << 8));
        }
        return
            (short)(((buf[offset]    & 0xff) << 8) |
                    (buf[offset + 1] & 0xff));
    }

    /**
     * Do write int16.
     *
     * @param buf the buf
     * @param offset the offset
     * @param val the val
     * @param littleEndian the little endian
     */
    private static void doWriteInt16(byte[] buf, int offset, int val, boolean littleEndian)
    {
        if (littleEndian) {
            buf[offset] =     (byte)(val & 0xff);
            buf[offset + 1] = (byte)((val >>> 8) & 0xff);
        } else {
            buf[offset]         =     (byte)((val >>> 8) & 0xff);
            buf[offset + 1]     =     (byte)(val & 0xff);
        }
    }

    /**
     * Read int16.
     *
     * @param buf the buf
     * @param offset the offset
     * @param littleEndian the little endian
     * @return the object
     */
    public static Object readInt16(byte[] buf, int offset, boolean littleEndian)
    {
        return doReadInt16(buf, offset, littleEndian);
    }

    /**
     * Write int16.
     *
     * @param buf the buf
     * @param offset the offset
     * @param val the val
     * @param littleEndian the little endian
     */
    public static void writeInt16(byte[] buf, int offset, int val, boolean littleEndian)
    {
        doWriteInt16(buf, offset, val, littleEndian);
    }

    /**
     * Read uint16.
     *
     * @param buf the buf
     * @param offset the offset
     * @param littleEndian the little endian
     * @return the object
     */
    public static Object readUint16(byte[] buf, int offset, boolean littleEndian)
    {
        return doReadInt16(buf, offset, littleEndian) & 0xffff;
    }

    /**
     * Write uint16.
     *
     * @param buf the buf
     * @param offset the offset
     * @param val the val
     * @param littleEndian the little endian
     */
    public static void writeUint16(byte[] buf, int offset, int val, boolean littleEndian)
    {
        doWriteInt16(buf, offset, val & 0xffff, littleEndian);
    }

    /**
     * Read int32.
     *
     * @param buf the buf
     * @param offset the offset
     * @param littleEndian the little endian
     * @return the object
     */
    public static Object readInt32(byte[] buf, int offset, boolean littleEndian)
    {
        if (littleEndian) {
            return
                (buf[offset]      & 0xff) |
                ((buf[offset + 1] & 0xff) << 8) |
                ((buf[offset + 2] & 0xff) << 16) |
                ((buf[offset + 3] & 0xff) << 24);
        }
        return
            ((buf[offset]     & 0xff) << 24) |
            ((buf[offset + 1] & 0xff) << 16) |
            ((buf[offset + 2] & 0xff) << 8) |
            (buf[offset + 3]  & 0xff);
    }

    /**
     * Write int32.
     *
     * @param buf the buf
     * @param offset the offset
     * @param val the val
     * @param littleEndian the little endian
     */
    public static void writeInt32(byte[] buf, int offset, int val, boolean littleEndian)
    {
        if (littleEndian) {
            buf[offset] =     (byte)(val & 0xff);
            buf[offset + 1] = (byte)((val >>> 8)  & 0xff);
            buf[offset + 2] = (byte)((val >>> 16) & 0xff);
            buf[offset + 3] = (byte)((val >>> 24) & 0xff);
        } else {
            buf[offset] =     (byte)((val >>> 24) & 0xff);
            buf[offset + 1] = (byte)((val >>> 16) & 0xff);
            buf[offset + 2] = (byte)((val >>> 8) & 0xff);
            buf[offset + 3] = (byte)(val & 0xff);
        }
    }

    /**
     * Read uint32 primitive.
     *
     * @param buf the buf
     * @param offset the offset
     * @param littleEndian the little endian
     * @return the long
     */
    public static long readUint32Primitive(byte[] buf, int offset, boolean littleEndian)
    {
        if (littleEndian) {
            return
                ((buf[offset]      & 0xffL) |
                 ((buf[offset + 1] & 0xffL) << 8L) |
                 ((buf[offset + 2] & 0xffL) << 16L) |
                 ((buf[offset + 3] & 0xffL) << 24L)) &
                0xffffffffL;
        }
        return
            (((buf[offset]     & 0xffL) << 24L) |
             ((buf[offset + 1] & 0xffL) << 16L) |
             ((buf[offset + 2] & 0xffL) << 8L) |
             (buf[offset + 3]  & 0xffL)) &
             0xffffffffL;
    }

    /**
     * Write uint32.
     *
     * @param buf the buf
     * @param offset the offset
     * @param val the val
     * @param littleEndian the little endian
     */
    public static void writeUint32(byte[] buf, int offset, long val, boolean littleEndian)
    {
        if (littleEndian) {
            buf[offset] =     (byte)(val & 0xffL);
            buf[offset + 1] = (byte)((val >>> 8L)  & 0xffL);
            buf[offset + 2] = (byte)((val >>> 16L) & 0xffL);
            buf[offset + 3] = (byte)((val >>> 24L) & 0xffL);
        } else {
            buf[offset] =     (byte)((val >>> 24L) & 0xffL);
            buf[offset + 1] = (byte)((val >>> 16L) & 0xffL);
            buf[offset + 2] = (byte)((val >>> 8L)  & 0xffL);
            buf[offset + 3] = (byte)(val & 0xffL);
        }
    }

    /**
     * Read uint32.
     *
     * @param buf the buf
     * @param offset the offset
     * @param littleEndian the little endian
     * @return the object
     */
    public static Object readUint32(byte[] buf, int offset, boolean littleEndian)
    {
        return readUint32Primitive(buf, offset, littleEndian);
    }

    /**
     * Read uint64 primitive.
     *
     * @param buf the buf
     * @param offset the offset
     * @param littleEndian the little endian
     * @return the long
     */
    public static long readUint64Primitive(byte[] buf, int offset, boolean littleEndian)
    {
        if (littleEndian) {
            return
                   ((buf[offset]     & 0xffL) |
                    ((buf[offset + 1] & 0xffL) << 8L) |
                    ((buf[offset + 2] & 0xffL) << 16L) |
                    ((buf[offset + 3] & 0xffL) << 24L) |
                    ((buf[offset + 4] & 0xffL) << 32L) |
                    ((buf[offset + 5] & 0xffL) << 40L) |
                    ((buf[offset + 6] & 0xffL) << 48L) |
                    ((buf[offset + 7] & 0xffL) << 56L));
        }
        return
            (((buf[offset]     & 0xffL) << 56L) |
             ((buf[offset + 1] & 0xffL) << 48L) |
             ((buf[offset + 2] & 0xffL) << 40L) |
             ((buf[offset + 3] & 0xffL) << 32L) |
             ((buf[offset + 4] & 0xffL) << 24L) |
             ((buf[offset + 5] & 0xffL) << 16L) |
             ((buf[offset + 6] & 0xffL) << 8L) |
             ((buf[offset + 7] & 0xffL) << 0L));
    }

    /**
     * Write uint64.
     *
     * @param buf the buf
     * @param offset the offset
     * @param val the val
     * @param littleEndian the little endian
     */
    public static void writeUint64(byte[] buf, int offset, long val, boolean littleEndian)
    {
        if (littleEndian) {
            buf[offset] =     (byte)(val & 0xffL);
            buf[offset + 1] = (byte)((val >>> 8L)  & 0xffL);
            buf[offset + 2] = (byte)((val >>> 16L) & 0xffL);
            buf[offset + 3] = (byte)((val >>> 24L) & 0xffL);
            buf[offset + 4] = (byte)((val >>> 32L)  & 0xffL);
            buf[offset + 5] = (byte)((val >>> 40L)  & 0xffL);
            buf[offset + 6] = (byte)((val >>> 48L) & 0xffL);
            buf[offset + 7] = (byte)((val >>> 56L) & 0xffL);
        } else {
            buf[offset] =     (byte)((val >>> 56L) & 0xffL);
            buf[offset + 1] = (byte)((val >>> 48L) & 0xffL);
            buf[offset + 2] = (byte)((val >>> 40L) & 0xffL);
            buf[offset + 3] = (byte)((val >>> 32L) & 0xffL);
            buf[offset + 4] = (byte)((val >>> 24L) & 0xffL);
            buf[offset + 5] = (byte)((val >>> 16L) & 0xffL);
            buf[offset + 6] = (byte)((val >>> 8L)  & 0xffL);
            buf[offset + 7] = (byte)(val & 0xffL);
        }
    }

    /**
     * Read float32.
     *
     * @param buf the buf
     * @param offset the offset
     * @param littleEndian the little endian
     * @return the object
     */
    public static Object readFloat32(byte[] buf, int offset, boolean littleEndian)
    {
        long base = readUint32Primitive(buf, offset, littleEndian);
        return Float.intBitsToFloat((int)base);
    }

    /**
     * Write float32.
     *
     * @param buf the buf
     * @param offset the offset
     * @param val the val
     * @param littleEndian the little endian
     */
    public static void writeFloat32(byte[] buf, int offset, double val, boolean littleEndian)
    {
        long base = Float.floatToIntBits((float)val);
        writeUint32(buf, offset, base, littleEndian);
    }

    /**
     * Read float64.
     *
     * @param buf the buf
     * @param offset the offset
     * @param littleEndian the little endian
     * @return the object
     */
    public static Object readFloat64(byte[] buf, int offset, boolean littleEndian)
    {
        long base = readUint64Primitive(buf, offset, littleEndian);
        return Double.longBitsToDouble(base);
    }

    /**
     * Write float64.
     *
     * @param buf the buf
     * @param offset the offset
     * @param val the val
     * @param littleEndian the little endian
     */
    public static void writeFloat64(byte[] buf, int offset, double val, boolean littleEndian)
    {
        long base = Double.doubleToLongBits(val);
        writeUint64(buf, offset, base, littleEndian);
    }
}
