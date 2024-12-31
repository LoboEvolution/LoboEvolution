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

package org.loboevolution.js;

import org.mozilla.javascript.ClassShutter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;

/**
 * <p>LoboContextFactory class.</p>
 */
public class LoboContextFactory extends ContextFactory {

    final private ClassShutter myClassShutter = fullClassName -> {
        if (fullClassName.startsWith("java")) {
            final boolean isException = (fullClassName.startsWith("java.lang") &&
                    fullClassName.endsWith("Exception"));
            if (fullClassName.equals("java.lang.Object") || isException) {
                return true;
            }
            Thread.dumpStack();
            return false;
        }
        return true;
    };

    @Override
    protected Context makeContext() {
        final Context cx = super.makeContext();
        cx.setClassShutter(myClassShutter);
        cx.setOptimizationLevel(-1);
        cx.setLanguageVersion(Context.VERSION_1_8);
        cx.setInstructionObserverThreshold(100_000);
        return cx;
    }

    @Override
    protected boolean hasFeature(final Context cx, final int featureIndex) {
        if (featureIndex == Context.FEATURE_V8_EXTENSIONS) {
            return true;
        }
        return super.hasFeature(cx, featureIndex);
    }

}
