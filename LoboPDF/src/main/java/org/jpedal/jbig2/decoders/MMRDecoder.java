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
package org.jpedal.jbig2.decoders;

import lombok.extern.slf4j.Slf4j;
import org.jpedal.jbig2.io.StreamReader;
import org.jpedal.jbig2.util.BinaryOperation;

/**
 * <p>MMRDecoder class.</p>
 */
@Slf4j
public class MMRDecoder {

    /**
     * Constant <code>ccittEndOfLine=-2</code>
     */
    public static final int ccittEndOfLine = -2;
    /**
     * Constant <code>twoDimensionalPass=0</code>
     */
    public static final int twoDimensionalPass = 0;
    /**
     * Constant <code>twoDimensionalHorizontal=1</code>
     */
    public static final int twoDimensionalHorizontal = 1;
    /**
     * Constant <code>twoDimensionalVertical0=2</code>
     */
    public static final int twoDimensionalVertical0 = 2;
    /**
     * Constant <code>twoDimensionalVerticalR1=3</code>
     */
    public static final int twoDimensionalVerticalR1 = 3;
    /**
     * Constant <code>twoDimensionalVerticalL1=4</code>
     */
    public static final int twoDimensionalVerticalL1 = 4;
    /**
     * Constant <code>twoDimensionalVerticalR2=5</code>
     */
    public static final int twoDimensionalVerticalR2 = 5;
    /**
     * Constant <code>twoDimensionalVerticalL2=6</code>
     */
    public static final int twoDimensionalVerticalL2 = 6;
    /**
     * Constant <code>twoDimensionalVerticalR3=7</code>
     */
    public static final int twoDimensionalVerticalR3 = 7;
    /**
     * Constant <code>twoDimensionalVerticalL3=8</code>
     */
    public static final int twoDimensionalVerticalL3 = 8;
    /**
     * black run lengths
     */
    final int[][] blackTable1 = {{-1, -1}, {-1, -1}, {12, ccittEndOfLine}, {12, ccittEndOfLine}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {11, 1792}, {11, 1792}, {11, 1792}, {11, 1792}, {12, 1984}, {12, 1984}, {12, 2048}, {12, 2048}, {12, 2112}, {12, 2112},
            {12, 2176}, {12, 2176}, {12, 2240}, {12, 2240}, {12, 2304}, {12, 2304}, {11, 1856}, {11, 1856}, {11, 1856}, {11, 1856}, {11, 1920}, {11, 1920}, {11, 1920}, {11, 1920}, {12, 2368}, {12, 2368}, {12, 2432}, {12, 2432}, {12, 2496}, {12, 2496}, {12, 2560}, {12, 2560}, {10, 18}, {10, 18}, {10, 18}, {10, 18}, {10, 18}, {10, 18}, {10, 18}, {10, 18}, {12, 52}, {12, 52}, {13, 640}, {13, 704}, {13, 768}, {13, 832}, {12, 55}, {12, 55}, {12, 56}, {12, 56}, {13, 1280}, {13, 1344},
            {13, 1408}, {13, 1472}, {12, 59}, {12, 59}, {12, 60}, {12, 60}, {13, 1536}, {13, 1600}, {11, 24}, {11, 24}, {11, 24}, {11, 24}, {11, 25}, {11, 25}, {11, 25}, {11, 25}, {13, 1664}, {13, 1728}, {12, 320}, {12, 320}, {12, 384}, {12, 384}, {12, 448}, {12, 448}, {13, 512}, {13, 576}, {12, 53}, {12, 53}, {12, 54}, {12, 54}, {13, 896}, {13, 960}, {13, 1024}, {13, 1088}, {13, 1152}, {13, 1216}, {10, 64}, {10, 64}, {10, 64}, {10, 64}, {10, 64}, {10, 64}, {10, 64}, {10, 64}};
    final int[][] blackTable2 = {{8, 13}, {8, 13}, {8, 13}, {8, 13}, {8, 13}, {8, 13}, {8, 13}, {8, 13}, {8, 13}, {8, 13}, {8, 13}, {8, 13}, {8, 13}, {8, 13}, {8, 13}, {8, 13}, {11, 23}, {11, 23}, {12, 50}, {12, 51}, {12, 44}, {12, 45}, {12, 46}, {12, 47}, {12, 57}, {12, 58}, {12, 61}, {12, 256}, {10, 16}, {10, 16}, {10, 16}, {10, 16}, {10, 17}, {10, 17}, {10, 17}, {10, 17}, {12, 48}, {12, 49}, {12, 62}, {12, 63}, {12, 30}, {12, 31}, {12, 32}, {12, 33}, {12, 40}, {12, 41}, {11, 22},
            {11, 22}, {8, 14}, {8, 14}, {8, 14}, {8, 14}, {8, 14}, {8, 14}, {8, 14}, {8, 14}, {8, 14}, {8, 14}, {8, 14}, {8, 14}, {8, 14}, {8, 14}, {8, 14}, {8, 14}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 10}, {7, 11}, {7, 11},
            {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {7, 11}, {9, 15}, {9, 15}, {9, 15}, {9, 15}, {9, 15}, {9, 15}, {9, 15}, {9, 15}, {12, 128}, {12, 192}, {12, 26}, {12, 27}, {12, 28}, {12, 29}, {11, 19}, {11, 19}, {11, 20}, {11, 20}, {12, 34}, {12, 35},
            {12, 36}, {12, 37}, {12, 38}, {12, 39}, {11, 21}, {11, 21}, {12, 42}, {12, 43}, {10, 0}, {10, 0}, {10, 0}, {10, 0}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}, {7, 12}};
    final int[][] blackTable3 = {{-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {6, 9}, {6, 8}, {5, 7}, {5, 7}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {3, 1}, {3, 1}, {3, 1}, {3, 1}, {3, 1}, {3, 1}, {3, 1}, {3, 1}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 2}, {2, 2}, {2, 2}, {2, 2}, {2, 2}, {2, 2},
            {2, 2}, {2, 2}, {2, 2}, {2, 2}, {2, 2}, {2, 2}, {2, 2}, {2, 2}, {2, 2}, {2, 2}};
    private final StreamReader reader;
    private final int[][] twoDimensionalTable1 = {{-1, -1}, {-1, -1}, {7, twoDimensionalVerticalL3}, {7, twoDimensionalVerticalR3}, {6, twoDimensionalVerticalL2}, {6, twoDimensionalVerticalL2}, {6, twoDimensionalVerticalR2}, {6, twoDimensionalVerticalR2}, {4, twoDimensionalPass}, {4, twoDimensionalPass}, {4, twoDimensionalPass}, {4, twoDimensionalPass}, {4, twoDimensionalPass}, {4, twoDimensionalPass}, {4, twoDimensionalPass}, {4, twoDimensionalPass}, {3, twoDimensionalHorizontal}, {3, twoDimensionalHorizontal}, {3, twoDimensionalHorizontal}, {3, twoDimensionalHorizontal},
            {3, twoDimensionalHorizontal}, {3, twoDimensionalHorizontal}, {3, twoDimensionalHorizontal}, {3, twoDimensionalHorizontal}, {3, twoDimensionalHorizontal}, {3, twoDimensionalHorizontal}, {3, twoDimensionalHorizontal}, {3, twoDimensionalHorizontal}, {3, twoDimensionalHorizontal}, {3, twoDimensionalHorizontal}, {3, twoDimensionalHorizontal}, {3, twoDimensionalHorizontal}, {3, twoDimensionalVerticalL1}, {3, twoDimensionalVerticalL1}, {3, twoDimensionalVerticalL1}, {3, twoDimensionalVerticalL1}, {3, twoDimensionalVerticalL1}, {3, twoDimensionalVerticalL1}, {3, twoDimensionalVerticalL1}, {3, twoDimensionalVerticalL1},
            {3, twoDimensionalVerticalL1}, {3, twoDimensionalVerticalL1}, {3, twoDimensionalVerticalL1}, {3, twoDimensionalVerticalL1}, {3, twoDimensionalVerticalL1}, {3, twoDimensionalVerticalL1}, {3, twoDimensionalVerticalL1}, {3, twoDimensionalVerticalL1}, {3, twoDimensionalVerticalR1}, {3, twoDimensionalVerticalR1}, {3, twoDimensionalVerticalR1}, {3, twoDimensionalVerticalR1}, {3, twoDimensionalVerticalR1}, {3, twoDimensionalVerticalR1}, {3, twoDimensionalVerticalR1}, {3, twoDimensionalVerticalR1}, {3, twoDimensionalVerticalR1}, {3, twoDimensionalVerticalR1}, {3, twoDimensionalVerticalR1},
            {3, twoDimensionalVerticalR1}, {3, twoDimensionalVerticalR1}, {3, twoDimensionalVerticalR1}, {3, twoDimensionalVerticalR1}, {3, twoDimensionalVerticalR1}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0},
            {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0},
            {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0},
            {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}, {1, twoDimensionalVertical0}};
    /**
     * white run lengths
     */
    private final int[][] whiteTable1 = {{-1, -1}, {12, ccittEndOfLine}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {11, 1792}, {11, 1792}, {12, 1984}, {12, 2048}, {12, 2112}, {12, 2176}, {12, 2240}, {12, 2304}, {11, 1856}, {11, 1856}, {11, 1920}, {11, 1920}, {12, 2368}, {12, 2432}, {12, 2496}, {12, 2560}};
    private final int[][] whiteTable2 = {{-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}, {8, 29}, {8, 29}, {8, 30}, {8, 30}, {8, 45}, {8, 45}, {8, 46}, {8, 46}, {7, 22}, {7, 22}, {7, 22}, {7, 22}, {7, 23}, {7, 23}, {7, 23}, {7, 23}, {8, 47}, {8, 47}, {8, 48}, {8, 48}, {6, 13}, {6, 13}, {6, 13}, {6, 13}, {6, 13}, {6, 13}, {6, 13}, {6, 13}, {7, 20}, {7, 20}, {7, 20}, {7, 20}, {8, 33}, {8, 33}, {8, 34}, {8, 34}, {8, 35}, {8, 35}, {8, 36}, {8, 36}, {8, 37}, {8, 37}, {8, 38}, {8, 38}, {7, 19}, {7, 19},
            {7, 19}, {7, 19}, {8, 31}, {8, 31}, {8, 32}, {8, 32}, {6, 1}, {6, 1}, {6, 1}, {6, 1}, {6, 1}, {6, 1}, {6, 1}, {6, 1}, {6, 12}, {6, 12}, {6, 12}, {6, 12}, {6, 12}, {6, 12}, {6, 12}, {6, 12}, {8, 53}, {8, 53}, {8, 54}, {8, 54}, {7, 26}, {7, 26}, {7, 26}, {7, 26}, {8, 39}, {8, 39}, {8, 40}, {8, 40}, {8, 41}, {8, 41}, {8, 42}, {8, 42}, {8, 43}, {8, 43}, {8, 44}, {8, 44}, {7, 21}, {7, 21}, {7, 21}, {7, 21}, {7, 28}, {7, 28}, {7, 28}, {7, 28}, {8, 61}, {8, 61},
            {8, 62}, {8, 62}, {8, 63}, {8, 63}, {8, 0}, {8, 0}, {8, 320}, {8, 320}, {8, 384}, {8, 384}, {5, 10}, {5, 10}, {5, 10}, {5, 10}, {5, 10}, {5, 10}, {5, 10}, {5, 10}, {5, 10}, {5, 10}, {5, 10}, {5, 10}, {5, 10}, {5, 10}, {5, 10}, {5, 10}, {5, 11}, {5, 11}, {5, 11}, {5, 11}, {5, 11}, {5, 11}, {5, 11}, {5, 11}, {5, 11}, {5, 11}, {5, 11}, {5, 11}, {5, 11}, {5, 11}, {5, 11}, {5, 11}, {7, 27}, {7, 27}, {7, 27}, {7, 27}, {8, 59}, {8, 59}, {8, 60}, {8, 60}, {9, 1472},
            {9, 1536}, {9, 1600}, {9, 1728}, {7, 18}, {7, 18}, {7, 18}, {7, 18}, {7, 24}, {7, 24}, {7, 24}, {7, 24}, {8, 49}, {8, 49}, {8, 50}, {8, 50}, {8, 51}, {8, 51}, {8, 52}, {8, 52}, {7, 25}, {7, 25}, {7, 25}, {7, 25}, {8, 55}, {8, 55}, {8, 56}, {8, 56}, {8, 57}, {8, 57}, {8, 58}, {8, 58}, {6, 192}, {6, 192}, {6, 192}, {6, 192}, {6, 192}, {6, 192}, {6, 192}, {6, 192}, {6, 1664}, {6, 1664}, {6, 1664}, {6, 1664}, {6, 1664}, {6, 1664}, {6, 1664}, {6, 1664}, {8, 448},
            {8, 448}, {8, 512}, {8, 512}, {9, 704}, {9, 768}, {8, 640}, {8, 640}, {8, 576}, {8, 576}, {9, 832}, {9, 896}, {9, 960}, {9, 1024}, {9, 1088}, {9, 1152}, {9, 1216}, {9, 1280}, {9, 1344}, {9, 1408}, {7, 256}, {7, 256}, {7, 256}, {7, 256}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2},
            {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {4, 3}, {5, 128}, {5, 128}, {5, 128}, {5, 128}, {5, 128}, {5, 128}, {5, 128}, {5, 128}, {5, 128}, {5, 128}, {5, 128}, {5, 128}, {5, 128}, {5, 128}, {5, 128}, {5, 128}, {5, 8},
            {5, 8}, {5, 8}, {5, 8}, {5, 8}, {5, 8}, {5, 8}, {5, 8}, {5, 8}, {5, 8}, {5, 8}, {5, 8}, {5, 8}, {5, 8}, {5, 8}, {5, 8}, {5, 9}, {5, 9}, {5, 9}, {5, 9}, {5, 9}, {5, 9}, {5, 9}, {5, 9}, {5, 9}, {5, 9}, {5, 9}, {5, 9}, {5, 9}, {5, 9}, {5, 9}, {5, 9}, {6, 16}, {6, 16}, {6, 16}, {6, 16}, {6, 16}, {6, 16}, {6, 16}, {6, 16}, {6, 17}, {6, 17}, {6, 17}, {6, 17}, {6, 17}, {6, 17}, {6, 17}, {6, 17}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4},
            {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 4}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5}, {4, 5},
            {6, 14}, {6, 14}, {6, 14}, {6, 14}, {6, 14}, {6, 14}, {6, 14}, {6, 14}, {6, 15}, {6, 15}, {6, 15}, {6, 15}, {6, 15}, {6, 15}, {6, 15}, {6, 15}, {5, 64}, {5, 64}, {5, 64}, {5, 64}, {5, 64}, {5, 64}, {5, 64}, {5, 64}, {5, 64}, {5, 64}, {5, 64}, {5, 64}, {5, 64}, {5, 64}, {5, 64}, {5, 64}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6},
            {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 6}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}, {4, 7}};
    private long bufferLength = 0, buffer = 0, noOfBytesRead = 0;
    /**
     * <p>Constructor for MMRDecoder.</p>
     *
     * @param reader a {@link org.jpedal.jbig2.io.StreamReader} object.
     */
    public MMRDecoder(final StreamReader reader) {
        this.reader = reader;
    }

    /**
     * <p>reset.</p>
     */
    public void reset() {
        bufferLength = 0;
        noOfBytesRead = 0;
        buffer = 0;
    }

    /**
     * <p>skipTo.</p>
     *
     * @param length a {@link java.lang.Integer} object.
     */
    public void skipTo(final int length) {
        while (noOfBytesRead < length) {
            reader.readByte();
            noOfBytesRead++;
        }
    }

    /**
     * <p>get24Bits.</p>
     *
     * @return a long.
     */
    public long get24Bits() {
        while (bufferLength < 24) {

            buffer = ((BinaryOperation.bit32ShiftL(buffer, 8)) | (reader.readByte() & 0xff));
            bufferLength += 8;
            noOfBytesRead++;
        }

        return (BinaryOperation.bit32ShiftR(buffer, (int) (bufferLength - 24))) & 0xffffff;
    }

    /**
     * <p>get2DCode.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    public int get2DCode() {
        int[] tuple;

        if (bufferLength == 0) {
            buffer = (reader.readByte() & 0xff);

            bufferLength = 8;

            noOfBytesRead++;

            final int lookup = (int) ((BinaryOperation.bit32ShiftR(buffer, 1)) & 0x7f);

            tuple = twoDimensionalTable1[lookup];
        } else if (bufferLength == 8) {
            final int lookup = (int) ((BinaryOperation.bit32ShiftR(buffer, 1)) & 0x7f);
            tuple = twoDimensionalTable1[lookup];
        } else {
            final int lookup = (int) ((BinaryOperation.bit32ShiftL(buffer, (int) (7 - bufferLength))) & 0x7f);

            tuple = twoDimensionalTable1[lookup];
            if (tuple[0] < 0 || tuple[0] > (int) bufferLength) {
                final int right = (reader.readByte() & 0xff);

                final long left = (BinaryOperation.bit32ShiftL(buffer, 8));

                buffer = left | right;
                bufferLength += 8;
                noOfBytesRead++;

                final int look = (int) (BinaryOperation.bit32ShiftR(buffer, (int) (bufferLength - 7)) & 0x7f);

                tuple = twoDimensionalTable1[look];
            }
        }
        if (tuple[0] < 0) {
            if (JBIG2StreamDecoder.debug)
                log.info("Bad two dim code in JBIG2 MMR stream");

            return 0;

        }
        bufferLength -= tuple[0];

        return tuple[1];
    }

    /**
     * <p>getWhiteCode.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    public int getWhiteCode() {
        int[] tuple;
        long code;

        if (bufferLength == 0) {
            buffer = (reader.readByte() & 0xff);
            bufferLength = 8;
            noOfBytesRead++;
        }
        while (true) {
            if (bufferLength >= 7 && ((BinaryOperation.bit32ShiftR(buffer, (int) (bufferLength - 7))) & 0x7f) == 0) {
                if (bufferLength <= 12) {
                    code = BinaryOperation.bit32ShiftL(buffer, (int) (12 - bufferLength));
                } else {
                    code = BinaryOperation.bit32ShiftR(buffer, (int) (bufferLength - 12));
                }

                tuple = whiteTable1[(int) (code & 0x1f)];
            } else {
                if (bufferLength <= 9) {
                    code = BinaryOperation.bit32ShiftL(buffer, (int) (9 - bufferLength));
                } else {
                    code = BinaryOperation.bit32ShiftR(buffer, (int) (bufferLength - 9));
                }

                final int lookup = (int) (code & 0x1ff);
                if (lookup >= 0)
                    tuple = whiteTable2[lookup];
                else
                    tuple = whiteTable2[whiteTable2.length + lookup];
            }
            if (tuple[0] > 0 && tuple[0] <= (int) bufferLength) {
                bufferLength -= tuple[0];
                return tuple[1];
            }
            if (bufferLength >= 12) {
                break;
            }
            buffer = ((BinaryOperation.bit32ShiftL(buffer, 8)) | reader.readByte() & 0xff);
            bufferLength += 8;
            noOfBytesRead++;
        }
        if (JBIG2StreamDecoder.debug)
            log.info("Bad white code in JBIG2 MMR stream");

        bufferLength--;

        return 1;
    }

    /**
     * <p>getBlackCode.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    public int getBlackCode() {
        int[] tuple;
        long code;

        if (bufferLength == 0) {
            buffer = (reader.readByte() & 0xff);
            bufferLength = 8;
            noOfBytesRead++;
        }
        while (true) {
            if (bufferLength >= 6 && ((BinaryOperation.bit32ShiftR(buffer, (int) (bufferLength - 6))) & 0x3f) == 0) {
                if (bufferLength <= 13) {
                    code = BinaryOperation.bit32ShiftL(buffer, (int) (13 - bufferLength));
                } else {
                    code = BinaryOperation.bit32ShiftR(buffer, (int) (bufferLength - 13));
                }
                tuple = blackTable1[(int) (code & 0x7f)];
            } else if (bufferLength >= 4 && ((buffer >> (bufferLength - 4)) & 0x0f) == 0) {
                if (bufferLength <= 12) {
                    code = BinaryOperation.bit32ShiftL(buffer, (int) (12 - bufferLength));
                } else {
                    code = BinaryOperation.bit32ShiftR(buffer, (int) (bufferLength - 12));
                }

                final int lookup = (int) ((code & 0xff) - 64);
                if (lookup >= 0)
                    tuple = blackTable2[lookup];
                else
                    tuple = blackTable1[blackTable1.length + lookup];
            } else {
                if (bufferLength <= 6) {
                    code = BinaryOperation.bit32ShiftL(buffer, (int) (6 - bufferLength));
                } else {
                    code = BinaryOperation.bit32ShiftR(buffer, (int) (bufferLength - 6));
                }

                final int lookup = (int) (code & 0x3f);
                if (lookup >= 0)
                    tuple = blackTable3[lookup];
                else
                    tuple = blackTable2[blackTable2.length + lookup];
            }
            if (tuple[0] > 0 && tuple[0] <= (int) bufferLength) {
                bufferLength -= tuple[0];
                return tuple[1];
            }
            if (bufferLength >= 13) {
                break;
            }
            buffer = ((BinaryOperation.bit32ShiftL(buffer, 8)) | (reader.readByte() & 0xff));
            bufferLength += 8;
            noOfBytesRead++;
        }

        if (JBIG2StreamDecoder.debug)
            log.info("Bad black code in JBIG2 MMR stream");

        bufferLength--;
        return 1;
    }

}
