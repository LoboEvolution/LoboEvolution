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

package org.loboevolution.pdfview.font.ttf;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * A single simple glyph in a pdf font.
 */
public class GlyfCompound extends Glyf {
    /**
     * flags
     */
    private static final int ARG_1_AND_2_ARE_WORDS = 0x1;
    private static final int ARGS_ARE_XY_VALUES = 0x2;
    private static final int ROUND_XY_TO_GRID = 0x4;
    private static final int WE_HAVE_A_SCALE = 0x8;
    private static final int MORE_COMPONENTS = 0x20;
    private static final int WE_HAVE_AN_X_AND_Y_SCALE = 0x40;
    private static final int WE_HAVE_A_TWO_BY_TWO = 0x80;
    private static final int WE_HAVE_INSTRUCTIONS = 0x100;
    private static final int USE_MY_METRICS = 0x200;
    private static final int OVERLAP_COMPOUND = 0x400;

    /**
     * the flags for each compound glyph
     */
    private GlyfComponent[] components;

    /**
     * the instructions for the compound as a whole
     */
    private byte[] instructions;

    /**
     * Creates a new instance of a simple glyf
     */
    protected GlyfCompound() {
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the data in this glyf as a byte buffer.  Not implemented.
     */
    @Override
    public ByteBuffer getData() {
        final ByteBuffer buf = super.getData();

        // don't flip the buffer, since it may be used by subclasses
        return buf;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Set the data for this glyf.
     */
    @Override
    public void setData(final ByteBuffer data) {
        // int pos = data.position();
        // byte[] prdata = new byte[data.remaining()];
        // data.get(prdata);
        // HexDump.printData(prdata);
        // data.position(pos);

        // read the contour end points
        final List<GlyfComponent> comps = new ArrayList<>();
        GlyfComponent cur = null;
        boolean hasInstructions = false;

        do {
            cur = new GlyfComponent();
            cur.flags = data.getShort();
            cur.glyphIndex = data.getShort() & 0xFFFF;

            // read either e/f or matching points, as shorts or bytes...
            if (((cur.flags & ARG_1_AND_2_ARE_WORDS) != 0) &&
                    ((cur.flags & ARGS_ARE_XY_VALUES) != 0)) {
                cur.e = data.getShort();
                cur.f = data.getShort();
            } else if ((cur.flags & ARG_1_AND_2_ARE_WORDS) == 0 &&
                    ((cur.flags & ARGS_ARE_XY_VALUES) != 0)) {
                cur.e = data.get();
                cur.f = data.get();
            } else if (((cur.flags & ARG_1_AND_2_ARE_WORDS) != 0) &&
                    (cur.flags & ARGS_ARE_XY_VALUES) == 0) {
                cur.compoundPoint = data.getShort();
                cur.componentPoint = data.getShort();
            } else {
                cur.compoundPoint = data.get();
                cur.componentPoint = data.get();
            }

            // read the linear transform
            if ((cur.flags & WE_HAVE_A_SCALE) != 0) {
                cur.a = (float) data.getShort() / (float) (1 << 14);
                cur.d = cur.a;
            } else if ((cur.flags & WE_HAVE_AN_X_AND_Y_SCALE) != 0) {
                cur.a = (float) data.getShort() / (float) (1 << 14);
                cur.d = (float) data.getShort() / (float) (1 << 14);
            } else if ((cur.flags & WE_HAVE_A_TWO_BY_TWO) != 0) {
                cur.a = (float) data.getShort() / (float) (1 << 14);
                cur.b = (float) data.getShort() / (float) (1 << 14);
                cur.c = (float) data.getShort() / (float) (1 << 14);
                cur.d = (float) data.getShort() / (float) (1 << 14);
            }

            if ((cur.flags & WE_HAVE_INSTRUCTIONS) != 0) {
                hasInstructions = true;
            }

            comps.add(cur);
        } while ((cur.flags & MORE_COMPONENTS) != 0);

        final GlyfComponent[] componentArray = new GlyfComponent[comps.size()];
        comps.toArray(componentArray);
        setComponents(componentArray);

        byte[] instr = null;
        if (hasInstructions) {
            // read the instructions
            final short numInstructions = data.getShort();
            instr = new byte[numInstructions];
            for (int i = 0; i < instr.length; i++) {
                instr[i] = data.get();
            }
        } else {
            instr = new byte[0];
        }
        setInstructions(instr);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the length of this glyf.  Not implemented.
     */
    @Override
    public short getLength() {

        // start with the length of the superclass
        final short length = super.getLength();
        return length;
    }

    /**
     * Get the number of components in this compound
     *
     * @return a int.
     */
    public int getNumComponents() {
        return this.components.length;
    }

    /**
     * Get a given flag
     *
     * @param index a int.
     * @return a short.
     */
    public short getFlag(final int index) {
        return this.components[index].flags;
    }

    /**
     * Get the glyf index for a given glyf
     *
     * @param index a int.
     * @return a int.
     */
    public int getGlyphIndex(final int index) {
        return this.components[index].glyphIndex;
    }

    /**
     * Get the base affine transform.  This is based on a whacy formula
     * defined in the true type font spec.
     *
     * @param index a int.
     * @return an array of {@link double} objects.
     */
    public double[] getTransform(final int index) {
        final GlyfComponent gc = this.components[index];

        float m = Math.max(Math.abs(gc.a), Math.abs(gc.b));
        if (Math.abs(Math.abs(gc.a) - Math.abs(gc.c)) < ((float) 33 / 65536)) {
            m *= 2;
        }

        float n = Math.max(Math.abs(gc.c), Math.abs(gc.d));
        if (Math.abs(Math.abs(gc.c) - Math.abs(gc.d)) < ((float) 33 / 65536)) {
            n *= 2;
        }

        final float e = m * gc.e;
        final float f = n * gc.f;

        return new double[]{gc.a, gc.b, gc.c, gc.d, e, f};
    }

    /**
     * Get the point in the compound glyph to match
     *
     * @param index a int.
     * @return a int.
     */
    public int getCompoundPoint(final int index) {
        return this.components[index].compoundPoint;
    }

    /**
     * Get the point in the component glyph to match
     *
     * @param index a int.
     * @return a int.
     */
    public int getComponentPoint(final int index) {
        return this.components[index].componentPoint;
    }

    /**
     * Determine whether args 1 and 2 are words or bytes
     *
     * @param index a int.
     * @return a boolean.
     */
    public boolean argsAreWords(final int index) {
        return ((getFlag(index) & ARG_1_AND_2_ARE_WORDS) != 0);
    }

    /**
     * Determine whether args 1 and 2 are xy values or point indices
     *
     * @param index a int.
     * @return a boolean.
     */
    public boolean argsAreXYValues(final int index) {
        return ((getFlag(index) & ARGS_ARE_XY_VALUES) != 0);
    }

    /**
     * Determine whether to round XY values to the grid
     *
     * @param index a int.
     * @return a boolean.
     */
    public boolean roundXYToGrid(final int index) {
        return ((getFlag(index) & ROUND_XY_TO_GRID) != 0);
    }

    /**
     * Determine whether there is a simple scale
     *
     * @param index a int.
     * @return a boolean.
     */
    public boolean hasAScale(final int index) {
        return ((getFlag(index) & WE_HAVE_A_SCALE) != 0);
    }

    /**
     * Determine whether there are more components left to read
     *
     * @param index a int.
     * @return a boolean.
     */
    protected boolean moreComponents(final int index) {
        return ((getFlag(index) & MORE_COMPONENTS) != 0);
    }

    /**
     * Determine whether there are separate scales on X and Y
     *
     * @param index a int.
     * @return a boolean.
     */
    protected boolean hasXYScale(final int index) {
        return ((getFlag(index) & WE_HAVE_AN_X_AND_Y_SCALE) != 0);
    }

    /**
     * Determine whether there is a 2x2 transform
     *
     * @param index a int.
     * @return a boolean.
     */
    protected boolean hasTwoByTwo(final int index) {
        return ((getFlag(index) & WE_HAVE_A_TWO_BY_TWO) != 0);
    }

    /**
     * Determine whether there are instructions
     *
     * @param index a int.
     * @return a boolean.
     */
    protected boolean hasInstructions(final int index) {
        return ((getFlag(index) & WE_HAVE_INSTRUCTIONS) != 0);
    }

    /**
     * Use the metrics of this component for the compound
     *
     * @param index a int.
     * @return a boolean.
     */
    public boolean useMetrics(final int index) {
        return ((getFlag(index) & USE_MY_METRICS) != 0);
    }

    /**
     * This component overlaps the existing compound
     *
     * @param index a int.
     * @return a boolean.
     */
    public boolean overlapCompound(final int index) {
        return ((getFlag(index) & OVERLAP_COMPOUND) != 0);
    }

    /**
     * Set the components
     */
    void setComponents(final GlyfComponent[] components) {
        this.components = components;
    }

    /**
     * Get the number of instructions
     *
     * @return a short.
     */
    public short getNumInstructions() {
        return (short) this.instructions.length;
    }

    /**
     * Get a given instruction
     *
     * @param index a int.
     * @return a byte.
     */
    public byte getInstruction(final int index) {
        return this.instructions[index];
    }

    /**
     * Set the instructions
     *
     * @param instructions an array of {@link byte} objects.
     */
    protected void setInstructions(final byte[] instructions) {
        this.instructions = instructions;
    }

    /**
     * The record for a single component of this compound glyph
     */
    static class GlyfComponent {
        /**
         * flags
         */
        short flags;

        /**
         * the index of the component glyf
         */
        int glyphIndex;

        /**
         * the points to match
         */
        int compoundPoint;
        int componentPoint;

        /**
         * affine transform of this component
         */
        float a = 1.0f;
        float b = 0.0f;
        float c = 0.0f;
        float d = 1.0f;
        float e = 0.0f;
        float f = 0.0f;
    }
}
