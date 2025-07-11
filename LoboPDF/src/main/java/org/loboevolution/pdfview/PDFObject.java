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
package org.loboevolution.pdfview;

import lombok.Getter;
import org.loboevolution.pdfview.decode.PDFDecoder;
import org.loboevolution.pdfview.decrypt.IdentityDecrypter;
import org.loboevolution.pdfview.decrypt.PDFDecrypter;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * a class encapsulating all the possibilities of content for
 * an object in a PDF file.
 * <p>
 * A PDF object can be a simple type, like a Boolean, a Number,
 * a String, or the Null value.  It can also be a NAME, which
 * looks like a string, but is a special type in PDF files, like
 * "/Name".
 * <p>
 * A PDF object can also be complex types, including Array;
 * Dictionary; Stream, which is a Dictionary plus an array of
 * bytes; or Indirect, which is a reference to some other
 * PDF object.  Indirect references will always be dereferenced
 * by the time any data is returned from one of the methods
 * in this class.
 * <p>
 * Author Mike Wessler
 */
public class PDFObject {

    /**
     * an indirect reference
     */
    public static final int INDIRECT = 0;      // PDFXref
    /**
     * a Boolean
     */
    public static final int BOOLEAN = 1;      // Boolean
    /**
     * a Number, represented as a double
     */
    public static final int NUMBER = 2;       // Double
    /**
     * a String
     */
    public static final int STRING = 3;       // String
    /**
     * a special string, seen in PDF files as /Name
     */
    public static final int NAME = 4;         // String
    /**
     * an array of PDFObjects
     */
    public static final int ARRAY = 5;        // Array of PDFObject
    /**
     * a Hashmap that maps String names to PDFObjects
     */
    public static final int DICTIONARY = 6;   // HashMap(String->PDFObject)
    /**
     * a Stream: a Hashmap with a byte array
     */
    public static final int STREAM = 7;        // HashMap + byte[]
    /**
     * the NULL object (there is only one)
     */
    public static final int NULL = 8;         // null
    /**
     * a special PDF bare word, like R, obj, true, false, etc
     */
    public static final int KEYWORD = 9;      // String
    /**
     * When a value of {@link #getObjGen objNum} or {@link #getObjGen objGen},
     * indicates that the object is not top-level, and is embedded in another
     * object
     */
    public static final int OBJ_NUM_EMBEDDED = -2;

    /**
     * When a value of {@link #getObjGen objNum} or {@link #getObjGen objGen},
     * indicates that the object is not top-level, and is embedded directly
     * in the trailer.
     */
    public static final int OBJ_NUM_TRAILER = -1;

    /**
     * the NULL PDFObject
     */
    public static final PDFObject nullObj = new PDFObject(null, NULL, null);
    /**
     * the PDFFile from which this object came, used for
     * dereferences
     */
    private final PDFFile owner;
    /**
     * the type of this object
     */
    private int type;
    /**
     * the value of this object. It can be a wide number of things, defined by type
     */
    private Object value;
    /**
     * the encoded stream, if this is a STREAM object
     */
    private ByteBuffer stream;
    /**
     * a cached version of the decoded stream
     */
    private SoftReference<ByteBuffer> decodedStream;
    /**
     * The filter limits used to generate the cached decoded stream
     */
    private Set<String> decodedStreamFilterLimits = null;
    /**
     * a cache of translated data.  This data can be
     * garbage collected at any time, after which it will
     * have to be rebuilt.
     */
    private SoftReference<?> cache;

    /**
     * Get the object number of this object; a negative value indicates that
     * the object is not numbered, as it's not a top-level object: if the value
     * is {@link #OBJ_NUM_EMBEDDED}, it is because it's embedded within
     * another object. If the value is {@link #OBJ_NUM_TRAILER}, it's because
     * it's an object from the trailer.
     * the object number, if positive
     */
    @Getter
    private int objNum = OBJ_NUM_EMBEDDED;

    /**
     * Get the object generation number of this object; a negative value
     * indicates that the object is not numbered, as it's not a top-level
     * object: if the value is {@link #OBJ_NUM_EMBEDDED}, it is because it's
     * embedded within another object. If the value is {@link
     * #OBJ_NUM_TRAILER}, it's because it's an object from the trailer.
     * the object generation number, if positive
     */
    @Getter
    private int objGen = OBJ_NUM_EMBEDDED;

    /**
     * create a new simple PDFObject with a type and a value
     *
     * @param owner the PDFFile in which this object resides, used
     *              for dereferencing.  This may be null.
     * @param type  the type of object
     * @param value the value.  For DICTIONARY, this is a HashMap.
     *              for ARRAY it's an ArrayList.  For NUMBER, it's a Double.
     *              for BOOLEAN, it's Boolean.TRUE or Boolean.FALSE.  For
     *              everything else, it's a String.
     */
    public PDFObject(final PDFFile owner, final int type, Object value) {
        this.type = type;
        if (type == NAME) {
            value = ((String) value).intern();
        } else if (type == KEYWORD && value.equals("true")) {
            this.type = BOOLEAN;
            value = Boolean.TRUE;
        } else if (type == KEYWORD && value.equals("false")) {
            this.type = BOOLEAN;
            value = Boolean.FALSE;
        }
        this.value = value;
        this.owner = owner;
    }

    /**
     * create a new PDFObject that is the closest match to a
     * given Java object.  Possibilities include Double, String,
     * PDFObject[], HashMap, Boolean, or PDFParser.Tok,
     * which should be "true" or "false" to turn into a BOOLEAN.
     *
     * @param obj the sample Java object to convert to a PDFObject.
     * @throws org.loboevolution.pdfview.PDFParseException if any.
     */
    public PDFObject(final Object obj) throws PDFParseException {
        this.owner = null;
        this.value = obj;
        if ((obj instanceof Double) || (obj instanceof Integer)) {
            this.type = NUMBER;
        } else if (obj instanceof String) {
            this.type = NAME;
        } else if (obj instanceof PDFObject[]) {
            this.type = ARRAY;
        } else if (obj instanceof Object[] srcary) {
            final PDFObject[] dstary = new PDFObject[srcary.length];
            for (int i = 0; i < srcary.length; i++) {
                dstary[i] = new PDFObject(srcary[i]);
            }
            value = dstary;
            this.type = ARRAY;
        } else if (obj instanceof HashMap) {
            this.type = DICTIONARY;
        } else if (obj instanceof Boolean) {
            this.type = BOOLEAN;
        } else if (obj instanceof PDFParser.Tok tok) {
            if (tok.name != null && tok.name.equals("true")) {
                this.value = Boolean.TRUE;
                this.type = BOOLEAN;
            } else if (tok.name != null && tok.name.equals("false")) {
                this.value = Boolean.FALSE;
                this.type = BOOLEAN;
            } else {
                this.value = tok.name;
                this.type = NAME;
            }
        } else {
            throw new PDFParseException("Bad type for raw PDFObject: " + obj);
        }
    }

    /**
     * create a new PDFObject based on a PDFXref
     *
     * @param owner the PDFFile from which the PDFXref was drawn
     * @param xref  the PDFXref to turn into a PDFObject
     */
    public PDFObject(final PDFFile owner, final PDFXref xref) {
        this.type = INDIRECT;
        this.value = xref;
        this.owner = owner;
    }

    /**
     * get the type of this object.  The object will be
     * dereferenced, so INDIRECT will never be returned.
     *
     * @return the type of the object
     * @throws java.io.IOException if any.
     */
    public int getType() throws IOException {
        if (type == INDIRECT) {
            return dereference().getType();
        }

        return type;
    }

    /**
     * get the value in the cache.  May become null at any time.
     *
     * @return the cached value, or null if the value has been
     * garbage collected.
     * @throws java.io.IOException if any.
     */
    public Object getCache() throws IOException {
        if (type == INDIRECT) {
            return dereference().getCache();
        } else if (cache != null) {
            return cache.get();
        } else {
            return null;
        }
    }

    /**
     * set the cached value.  The object may be garbage collected
     * if no other reference exists to it.
     *
     * @param obj the object to be cached
     * @throws java.io.IOException if any.
     */
    public void setCache(final Object obj) throws IOException {
        if (type == INDIRECT) {
            dereference().setCache(obj);
        } else {
            cache = new SoftReference<>(obj);
        }
    }

    /**
     * <p>Getter for the field <code>stream</code>.</p>
     *
     * @param filterLimits a {@link java.util.Set} object.
     * @return an array of {@link byte} objects.
     * @throws java.io.IOException if any.
     */
    public byte[] getStream(final Set<String> filterLimits) throws IOException {
        if (type == INDIRECT) {
            return dereference().getStream(filterLimits);
        } else if (type == STREAM && stream != null) {
            byte[] data;

            synchronized (stream) {
                // decode
                final ByteBuffer streamBuf = decodeStream(filterLimits);
                // ByteBuffer streamBuf = stream;

                // First try to use the array with no copying.  This can only
                // be done if the buffer has a backing array, and is not a slice
                if (streamBuf.hasArray() && streamBuf.arrayOffset() == 0) {
                    final byte[] ary = streamBuf.array();

                    // make sure there is no extra data in the buffer
                    if (ary.length == streamBuf.remaining()) {
                        return ary;
                    }
                }

                // Can't use the direct buffer, so copy the data (bad)
                data = new byte[streamBuf.remaining()];
                streamBuf.get(data);

                // return the stream to its starting position
                streamBuf.flip();
            }

            return data;
        } else if (type == STRING) {
            return PDFStringUtil.asBytes(getStringValue());
        } else {
            // wrong type
            return null;
        }
    }

    /**
     * get the stream from this object.  Will return null if this
     * object isn't a STREAM.
     *
     * @return the stream, or null, if this isn't a STREAM.
     * @throws java.io.IOException if any.
     */
    public byte[] getStream() throws IOException {
        return getStream(Collections.emptySet());
    }

    /**
     * set the stream of this object.  It should have been
     * a DICTIONARY before the call.
     *
     * @param data the data, as a ByteBuffer.
     */
    public void setStream(final ByteBuffer data) {
        this.type = STREAM;
        this.stream = data;
    }

    /**
     * get the stream from this object as a byte buffer.  Will return null if
     * this object isn't a STREAM.
     *
     * @return the buffer, or null, if this isn't a STREAM.
     * @throws java.io.IOException if any.
     */
    public ByteBuffer getStreamBuffer() throws IOException {
        return getStreamBuffer(Collections.emptySet());
    }

    /**
     * get the stream from this object as a byte buffer.  Will return null if
     * this object isn't a STREAM.
     *
     * @param filterLimits a {@link java.util.Set} object.
     * @return the buffer, or null, if this isn't a STREAM.
     * @throws java.io.IOException if any.
     */
    public ByteBuffer getStreamBuffer(final Set<String> filterLimits) throws IOException {
        if (type == INDIRECT) {
            return dereference().getStreamBuffer(filterLimits);
        } else if (type == STREAM && stream != null) {
            synchronized (stream) {
                final ByteBuffer streamBuf = decodeStream(filterLimits);
                // ByteBuffer streamBuf = stream;
                return streamBuf.duplicate();
            }
        } else if (type == STRING) {
            final String src = getStringValue();
            return ByteBuffer.wrap(src.getBytes());
        }

        // wrong type
        return null;
    }

    /**
     * Get the decoded stream value
     */
    private ByteBuffer decodeStream(final Set<String> filterLimits) throws IOException {
        ByteBuffer outStream = null;

        // first try the cache
        if (decodedStream != null && filterLimits.equals(decodedStreamFilterLimits)) {
            outStream = decodedStream.get();
        }

        // no luck in the cache, do the actual decoding
        if (outStream == null) {
            stream.rewind();
            outStream = PDFDecoder.decodeStream(this, stream, filterLimits);
            decodedStreamFilterLimits = new HashSet<>(filterLimits);
            decodedStream = new SoftReference<>(outStream);
        }

        return outStream;
    }

    /**
     * get the value as an int.  Will return 0 if this object
     * isn't a NUMBER.
     *
     * @return a {@link java.lang.Integer} object.
     * @throws java.io.IOException if any.
     */
    public int getIntValue() throws IOException {
        if (type == INDIRECT) {
            return dereference().getIntValue();
        } else if (type == NUMBER) {
            return ((Number) value).intValue();
        }

        // wrong type
        return 0;
    }

    /**
     * get the value as a float.  Will return 0 if this object
     * isn't a NUMBER
     *
     * @return a float.
     * @throws java.io.IOException if any.
     */
    public float getFloatValue() throws IOException {
        if (type == INDIRECT) {
            return dereference().getFloatValue();
        } else if (type == NUMBER) {
            return ((Double) value).floatValue();
        }

        // wrong type
        return 0;
    }

    /**
     * get the value as a double.  Will return 0 if this object
     * isn't a NUMBER.
     *
     * @return a double.
     * @throws java.io.IOException if any.
     */
    public double getDoubleValue() throws IOException {
        if (type == INDIRECT) {
            return dereference().getDoubleValue();
        } else if (type == NUMBER) {
            return ((Number) value).doubleValue();
        }

        // wrong type
        return 0;
    }

    /**
     * get the value as a String.  Will return null if the object
     * isn't a STRING, NAME, or KEYWORD.  This method will <b>NOT</b>
     * convert a NUMBER to a String. If the string is actually
     * a text string (i.e., may be encoded in UTF16-BE or PdfDocEncoding),
     * then one should use {@link #getTextStringValue()} or use one
     * of the {@link org.loboevolution.pdfview.PDFStringUtil} methods on the result from this
     * method. The string value represents exactly the sequence of 8 bit
     * characters present in the file, decrypted and decoded as appropriate,
     * into a string containing only 8 bit character values - that is, each
     * char will be between 0 and 255.
     *
     * @return a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
    public String getStringValue() throws IOException {
        if (type == INDIRECT) {
            return dereference().getStringValue();
        } else if (type == STRING || type == NAME || type == KEYWORD) {
            return (String) value;
        }

        // wrong type
        return null;
    }

    /**
     * Get the value as a text string; i.e., a string encoded in UTF-16BE
     * or PDFDocEncoding. Simple latin alpha-numeric characters are preserved in
     * both these encodings.
     *
     * @return the text string value
     * @throws java.io.IOException if any.
     */
    public String getTextStringValue() throws IOException {
        return PDFStringUtil.asTextString(getStringValue());
    }

    /**
     * get the value as a PDFObject[].  If this object is an ARRAY,
     * will return the array.  Otherwise, will return an array
     * of one element with this object as the element.
     *
     * @return an array of {@link org.loboevolution.pdfview.PDFObject} objects.
     * @throws java.io.IOException if any.
     */
    public PDFObject[] getArray() throws IOException {
        if (type == INDIRECT) {
            return dereference().getArray();
        } else if (type == ARRAY) {
            return (PDFObject[]) value;
        } else {
            final PDFObject[] ary = new PDFObject[1];
            ary[0] = this;
            return ary;
        }
    }

    /**
     * get the value as a boolean.  Will return false if this
     * object is not a BOOLEAN
     *
     * @return a boolean.
     * @throws java.io.IOException if any.
     */
    public boolean getBooleanValue() throws IOException {
        if (type == INDIRECT) {
            return dereference().getBooleanValue();
        } else if (type == BOOLEAN) {
            return value == Boolean.TRUE;
        }

        // wrong type
        return false;
    }

    /**
     * if this object is an ARRAY, get the PDFObject at some
     * position in the array.  If this is not an ARRAY, returns
     * null.
     *
     * @param idx a {@link java.lang.Integer} object.
     * @return a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    public PDFObject getAt(final int idx) throws IOException {
        if (type == INDIRECT) {
            return dereference().getAt(idx);
        } else if (type == ARRAY) {
            final PDFObject[] ary = (PDFObject[]) value;
            return ary[idx];
        }

        // wrong type
        return null;
    }

    /**
     * get an Iterator over all the keys in the dictionary.  If
     * this object is not a DICTIONARY or a STREAM, returns an
     * Iterator over the empty list.
     *
     * @return a {@link java.util.Iterator} object.
     * @throws java.io.IOException if any.
     */
    public Iterator<String> getDictKeys() throws IOException {
        if (type == INDIRECT) {
            return dereference().getDictKeys();
        } else if (type == DICTIONARY || type == STREAM) {
            return ((HashMap) value).keySet().iterator();
        }

        // wrong type
        return Collections.emptyIterator();
    }

    /**
     * get the dictionary as a HashMap.  If this isn't a DICTIONARY
     * or a STREAM, returns null
     *
     * @return a {@link java.util.Map} object.
     * @throws java.io.IOException if any.
     */
    public Map<String, PDFObject> getDictionary() throws IOException {
        if (type == INDIRECT) {
            return dereference().getDictionary();
        } else if (type == DICTIONARY || type == STREAM) {
            return (Map<String, PDFObject>) value;
        }

        // wrong type
        return new HashMap<>();
    }

    /**
     * get the value associated with a particular key in the
     * dictionary.  If this isn't a DICTIONARY or a STREAM,
     * or there is no such key, returns null.
     *
     * @param k a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    public PDFObject getDictRef(final String k) throws IOException {
        String key = k;
        if (type == INDIRECT) {
            return dereference().getDictRef(key);
        } else if (type == DICTIONARY || type == STREAM) {
            key = key.intern();
            final Map h = (HashMap) value;
            final PDFObject obj = (PDFObject) h.get(key.intern());
            return obj;
        }

        // wrong type
        return null;
    }

    /**
     * returns true only if this object is a DICTIONARY or a
     * STREAM, and the "Type" entry in the dictionary matches a
     * given value.
     *
     * @param match the expected value for the "Type" key in the
     *              dictionary
     * @return whether the dictionary is of the expected type
     * @throws java.io.IOException if any.
     */
    public boolean isDictType(final String match) throws IOException {
        if (type == INDIRECT) {
            return dereference().isDictType(match);
        } else if (type != DICTIONARY && type != STREAM) {
            return false;
        }

        final PDFObject obj = getDictRef("Type");
        return obj != null && obj.getStringValue().equals(match);
    }

    /**
     * <p>getDecrypter.</p>
     *
     * @return a {@link org.loboevolution.pdfview.decrypt.PDFDecrypter} object.
     */
    public PDFDecrypter getDecrypter() {
        // PDFObjects without owners are always created as part of
        // content instructions. Such an object will never have encryption
        // applied to it, as the stream that contains it is the
        // unit of encryption, with no further encryption being applied
        // within. So if someone asks for the decrypter for
        // one of these in-stream objects, no decryption should
        // ever be applied. This can be seen with inline images.
        return owner != null ?
                owner.getDefaultDecrypter() :
                IdentityDecrypter.getInstance();
    }

    /**
     * Set the object identifiers
     *
     * @param objNum the object number
     * @param objGen the object generation number
     */
    public void setObjectId(final int objNum, final int objGen) {
        assert objNum >= OBJ_NUM_TRAILER;
        assert objGen >= OBJ_NUM_TRAILER;
        this.objNum = objNum;
        this.objGen = objGen;
    }

    /**
     * {@inheritDoc}
     * <p>
     * return a representation of this PDFObject as a String.
     * Does NOT dereference anything:  this is the only method
     * that allows you to distinguish an INDIRECT PDFObject.
     */
    @Override
    public String toString() {
        try {
            if (type == INDIRECT) {
                final StringBuilder str = new StringBuilder();
                str.append("Indirect to #").append(((PDFXref) value).getID());
                try {
                    str.append("\n").append(dereference().toString());
                } catch (final Throwable t) {
                    str.append(t);
                }
                return str.toString();
            } else if (type == BOOLEAN) {
                return "Boolean: " + (getBooleanValue() ? "true" : "false");
            } else if (type == NUMBER) {
                return "Number: " + getDoubleValue();
            } else if (type == STRING) {
                return "String: " + getStringValue();
            } else if (type == NAME) {
                return "Name: /" + getStringValue();
            } else if (type == ARRAY) {
                return "Array, length=" + ((PDFObject[]) value).length;
            } else if (type == DICTIONARY) {
                final StringBuilder sb = new StringBuilder();
                PDFObject obj = getDictRef("Type");
                if (obj != null) {
                    sb.append(obj.getStringValue());
                    obj = getDictRef("Subtype");
                    if (obj == null) {
                        obj = getDictRef("S");
                    }
                    if (obj != null) {
                        sb.append("/").append(obj.getStringValue());
                    }
                } else {
                    sb.append("Untyped");
                }
                sb.append(" dictionary. Keys:");
                final Map hm = (HashMap) value;
                final Iterator it = hm.entrySet().iterator();
                Map.Entry entry;
                while (it.hasNext()) {
                    entry = (Map.Entry) it.next();
                    sb.append("\n   ").append(entry.getKey()).append("  ").append(entry.getValue());
                }
                return sb.toString();
            } else if (type == STREAM) {
                final byte[] st = getStream();
                if (st == null) {
                    return "Broken stream";
                }
                return "Stream: [[" + new String(st, 0, Math.min(st.length, 30)) + "]]";
            } else if (type == NULL) {
                return "Null";
            } else if (type == KEYWORD) {
                return "Keyword: " + getStringValue();
            /*	    } else if (type==IMAGE) {
            StringBuilder sb= new StringBuilder();
            java.awt.Image im= (java.awt.Image)stream;
            sb.append("Image ("+im.getWidth(null)+"x"+im.getHeight(null)+", with keys:");
            HashMap hm= (HashMap)value;
            Iterator it= hm.keySet().iterator();
            while(it.hasNext()) {
            sb.append(" "+(String)it.next());
            }
            return sb.toString();*/
            } else {
                return "Whoops!  big error!  Unknown type";
            }
        } catch (final IOException ioe) {
            return "Caught an error: " + ioe;
        }
    }

    /**
     * Make sure that this object is dereferenced.  Use the cache of
     * an indirect object to cache the dereferenced value, if possible.
     *
     * @return a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    public PDFObject dereference() throws IOException {
        if (type == INDIRECT) {
            PDFObject obj = null;

            if (cache != null) {
                obj = (PDFObject) cache.get();
            }

            if (obj == null || obj.value == null) {
                if (owner == null) {
                    PDFDebugger.debug("Bad seed (owner==null)!  Object=" + this);
                    throw new IOException("Cannot dereference: owner is null for object " + this);
                }
                obj = owner.dereference((PDFXref) value, getDecrypter());
                cache = new SoftReference<>(obj);
            }

            return obj;
        } else {
            // not indirect, no need to dereference
            return this;
        }
    }

    /**
     * Identify whether the object is currently an indirect/cross-reference
     *
     * @return whether currently indirect
     */
    public boolean isIndirect() {
        return (type == INDIRECT);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Test whether two PDFObject are equal.  Objects are equal IFF they
     * are the same reference OR they are both indirect objects with the
     * same id and generation number in their xref
     */
    @Override
    public boolean equals(final Object o) {
        if (super.equals(o)) {
            // they are the same object
            return true;
        } else if (type == INDIRECT && o instanceof PDFObject obj) {
            // they are both PDFObjects.  Check type and xref.

            if (obj.type == INDIRECT) {
                final PDFXref lXref = (PDFXref) value;
                final PDFXref rXref = (PDFXref) obj.value;

                return ((lXref.getID() == rXref.getID()) &&
                        (lXref.getGeneration() == rXref.getGeneration()));
            }
        }

        return false;
    }

    /**
     * Returns the root of this object.
     *
     * @return a {@link org.loboevolution.pdfview.PDFObject} object.
     */
    public PDFObject getRoot() {
        return owner.getRoot();
    }
}
