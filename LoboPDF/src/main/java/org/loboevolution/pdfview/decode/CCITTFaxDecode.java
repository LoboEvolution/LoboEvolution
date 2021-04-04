package org.loboevolution.pdfview.decode;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.loboevolution.pdfview.PDFDebugger;
import org.loboevolution.pdfview.PDFObject;

/**
 * <p>CCITTFaxDecode class.</p>
 *
  *
  *
 */
public class CCITTFaxDecode {



	/**
	 * <p>decode.</p>
	 *
	 * @param dict a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @param buf a {@link java.nio.ByteBuffer} object.
	 * @param params a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @return a {@link java.nio.ByteBuffer} object.
	 * @throws java.io.IOException if any.
	 */
	protected static ByteBuffer decode(PDFObject dict, ByteBuffer buf,
            PDFObject params) throws IOException {

		byte[] bytes = new byte[buf.remaining()];
	    buf.get(bytes, 0, bytes.length);
		return ByteBuffer.wrap(decode(dict, bytes));
	}


	/**
	 * <p>decode.</p>
	 *
	 * @param dict a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @param source an array of {@link byte} objects.
	 * @return an array of {@link byte} objects.
	 * @throws java.io.IOException if any.
	 */
	protected static byte[] decode(PDFObject dict, byte[] source) throws IOException {
		int width = 1728;
		PDFObject widthDef = dict.getDictRef("Width");
		if (widthDef == null) {
			widthDef = dict.getDictRef("W");
		}
		if (widthDef != null) {
			width = widthDef.getIntValue();
		}
		int height = 0;
		PDFObject heightDef = dict.getDictRef("Height");
		if (heightDef == null) {
			heightDef = dict.getDictRef("H");
		}
		if (heightDef != null) {
			height = heightDef.getIntValue();
		}

		//
		int columns = getOptionFieldInt(dict, "Columns", width);
		int rows = getOptionFieldInt(dict, "Rows", height);
		int k = getOptionFieldInt(dict, "K", 0);
		int size = rows * ((columns + 7) >> 3);
		byte[] destination = new byte[size];

		boolean align = getOptionFieldBoolean(dict, "EncodedByteAlign", false);

		CCITTFaxDecoder decoder = new CCITTFaxDecoder(1, columns, rows);
		decoder.setAlign(align);
		try {
			if (k == 0) {
				decoder.decodeT41D(destination, source, 0, rows);
			} else if (k > 0) {
				decoder.decodeT42D(destination, source, 0, rows);
			} else if (k < 0) {
				decoder.decodeT6(destination, source, 0, rows);
			}
		}catch (Exception e) {
		    PDFDebugger.debug("Error decoding CCITTFax image k: "+ k);
			// some PDf producer don't correctly assign a k value for the deocde,
			// as  result we can try one more time using the T6.
			//first, reset buffer
			destination = new byte[size];
			try {
				decoder.decodeT6(destination, source, 0, rows);
			}catch (Exception e1) {
				// do nothing
			    PDFDebugger.debug("Error decoding CCITTFax image");
			}
		}
		if (!getOptionFieldBoolean(dict, "BlackIs1", false)) {
			for (int i = 0; i < destination.length; i++) {
				// bitwise not
				destination[i] = (byte) ~destination[i];
			}
		}

		return destination;
	}

	/**
	 * <p>getOptionFieldInt.</p>
	 *
	 * @param dict a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @param name a {@link java.lang.String} object.
	 * @param defaultValue a int.
	 * @return a int.
	 * @throws java.io.IOException if any.
	 */
	public static int getOptionFieldInt(PDFObject dict, String name, int defaultValue) throws IOException {

        PDFObject dictParams = getDecodeParams(dict);

		if (dictParams == null) {
			return defaultValue;
		}
		PDFObject value = dictParams.getDictRef(name);
		if (value == null) {
			return defaultValue;
		}
		return value.getIntValue();
	}

	/**
	 * <p>getOptionFieldBoolean.</p>
	 *
	 * @param dict a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @param name a {@link java.lang.String} object.
	 * @param defaultValue a boolean.
	 * @return a boolean.
	 * @throws java.io.IOException if any.
	 */
	public static boolean getOptionFieldBoolean(PDFObject dict, String name, boolean defaultValue) throws IOException {

        PDFObject dictParams = getDecodeParams(dict);

		if (dictParams == null) {
			return defaultValue;
		}
		PDFObject value = dictParams.getDictRef(name);
		if (value == null) {
			return defaultValue;
		}
		return value.getBooleanValue();
	}

    private static PDFObject getDecodeParams(PDFObject dict) throws IOException {
        PDFObject decdParams = dict.getDictRef("DecodeParms");
        if (decdParams != null && decdParams.getType() == PDFObject.ARRAY) {
            return decdParams.getArray()[0];
        }
        return decdParams;
    }
}
