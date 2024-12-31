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

package org.loboevolution.annotation;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class AlertsExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final String EXPECTED_KEY = "value";

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        Alerts compareValues = context.getRequiredTestMethod().getAnnotation(Alerts.class);
        if (compareValues != null) {
            Object[] expected = compareValues.value();
            context.getStore(ExtensionContext.Namespace.create(getClass())).put(EXPECTED_KEY, expected);
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        Alerts compareValues = context.getRequiredTestMethod().getAnnotation(Alerts.class);
        if (compareValues != null) {
            Object[] expected = (Object[]) context.getStore(ExtensionContext.Namespace.create(getClass())).get(EXPECTED_KEY, Object.class);
            Object[] actual = getActualValue(context);
            System.out.println(Arrays.toString(expected) + " " + Arrays.toString(actual));
            assertArrayEquals(expected, actual);
        }
    }

    private Object[] getActualValue(ExtensionContext context) {
        Object testInstance = context.getTestInstance().orElse(null);
        try {
            Method actualValueMethod = testInstance.getClass().getMethod("getActualValue");
            return (Object[]) actualValueMethod.invoke(testInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}