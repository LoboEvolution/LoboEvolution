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
package org.loboevolution.pdfview;

import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;

/**
 * Provides a read-only sample-model where components are less than a byte in
 * width while allowing for pixels to cross byte-boundaries. For example, this
 * allows 2 pixels made of 3 4-bit-bands (p[pixel,band])
 * to be stored in 3 bytes as p[0,1] p[0,2] | p[0,3] p[1,0] | p[1,1] p[1,2].
 * {@link java.awt.image.MultiPixelPackedSampleModel}, which allows for sub-byte
 * components, does not allow for such byte spanning, while the PDF
 * specification does permit it -- hence the existence of this class.
 *
 * Author Luke Kirby, Pirion Systems
  *
 */
public class PdfSubByteSampleModel extends SampleModel
{
    private final int transferBytesPerPixel;
    private final int storageBitsPerPixel;
    private final int bitsPerLine;
    private final int bitsPerBand;
    private final int componentMask;
    private final int[] sampleSize;
    private final int ignoredBitsPerComponentPerByte;

    /**
     * <p>Constructor for PdfSubByteSampleModel.</p>
     *
     * @param w a int.
     * @param h a int.
     * @param numComponents a int.
     * @param bitsPerComponent a int.
     */
    public PdfSubByteSampleModel(int w, int h, int numComponents, int bitsPerComponent)
    {
        super(DataBuffer.TYPE_BYTE, w, h, numComponents);
        assert bitsPerComponent < 8 : "This is designed just for use with per-component sizes of less than 8 bits; " +
                "you should probably use PixelInterleavedSampleModel";
        assert bitsPerComponent == 1 || bitsPerComponent == 2 || bitsPerComponent == 4 :
                "we don't want to grab components across byte boundaries";
        transferBytesPerPixel = (numComponents * bitsPerComponent + 7) / 8;
        storageBitsPerPixel = numComponents * bitsPerComponent;
        // account for possible bits of padding on the end
        bitsPerLine = 8 * ((storageBitsPerPixel * w + 7) / 8);
        this.bitsPerBand = bitsPerComponent;
        componentMask = (1 << this.bitsPerBand) - 1;

        sampleSize = new int[numComponents];
        for (int i = 0; i < sampleSize.length; ++i) {
            sampleSize[i] = bitsPerComponent;
        }
        ignoredBitsPerComponentPerByte = 8 - bitsPerBand;
    }

    /** {@inheritDoc} */
    @Override
    public int getNumDataElements()
    {
        return transferBytesPerPixel;
    }

    /** {@inheritDoc} */
    @Override
    public Object getDataElements(int x, int y, Object obj, DataBuffer data)
    {
        byte[] elements = obj != null ? (byte[])obj : new byte[numBands];
        int bitIndex = y * bitsPerLine + storageBitsPerPixel * x;
        for (int i = 0; i < elements.length; ++i) {
            elements[i] = (byte) getComponent(data, bitIndex);
            bitIndex += bitsPerBand;
        }
        return elements;
    }

    private int getComponent(DataBuffer data, int aBitIndex)
    {
        final int boffset = aBitIndex >> 3; // == aBitIndex / 8
        final int b = data.getElem(boffset);
        final int bitIndexInB = aBitIndex & 7;
        final int shift =  ignoredBitsPerComponentPerByte - bitIndexInB;
        return (b >>> shift) & componentMask;
    }

    /** {@inheritDoc} */
    @Override
    public void setDataElements(int x, int y, Object obj, DataBuffer data)
    {
        throw new UnsupportedOperationException("read only");
    }

    /** {@inheritDoc} */
    @Override
    public int getSample(int x, int y, int b, DataBuffer data)
    {
        return getComponent(data, y * bitsPerLine + storageBitsPerPixel * x + bitsPerBand * b);
    }

    /** {@inheritDoc} */
    @Override
    public void setSample(int x, int y, int b, int s, DataBuffer data)
    {
        throw new UnsupportedOperationException("read only");

    }

    /** {@inheritDoc} */
    @Override
    public SampleModel createCompatibleSampleModel(int w, int h)
    {
        throw new UnsupportedOperationException("Not required");
    }

    /** {@inheritDoc} */
    @Override
    public SampleModel createSubsetSampleModel(int[] bands)
    {
        throw new UnsupportedOperationException("Not required");
    }

    /** {@inheritDoc} */
    @Override
    public DataBuffer createDataBuffer()
    {
        throw new UnsupportedOperationException("Not required");
    }

    /** {@inheritDoc} */
    @Override
    public int[] getSampleSize()
    {
        return sampleSize;
    }

    /** {@inheritDoc} */
    @Override
    public int getSampleSize(int band)
    {
        return bitsPerBand;
    }
}
