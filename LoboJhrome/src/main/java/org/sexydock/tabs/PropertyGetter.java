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

package org.sexydock.tabs;

import javax.swing.*;
import java.awt.*;

public class PropertyGetter {
    public static <T> T get(Class<T> type, String property, T defaultValue) {
        Object o = UIManager.get(property);
        if (o != null && type.isAssignableFrom(o.getClass())) {
            return type.cast(o);
        }
        return defaultValue;
    }

    public static <T> T get(Class<T> type, JComponent c, String property) {
        return get(type, c, property, property, null);
    }

    public static <T> T get(Class<T> type, JComponent c, String property, T defaultValue) {
        return get(type, c, property, property, defaultValue);
    }

    public static <T> T get(Class<T> type, JComponent c, String clientProperty, String UIManagerProperty) {
        return get(type, c, clientProperty, UIManagerProperty, null);
    }

    public static <T> T get(Class<T> type, JComponent c, String clientProperty, String UIManagerProperty, T defaultValue) {
        Object o = c.getClientProperty(clientProperty);
        if (o != null && type.isAssignableFrom(o.getClass())) {
            return type.cast(o);
        }
        if (UIManagerProperty != null) {
            o = UIManager.get(UIManagerProperty);
            if (o != null && type.isAssignableFrom(o.getClass())) {
                return type.cast(o);
            }
        }
        return defaultValue;
    }

    public static <T> T get(Class<T> type, JComponent c, String clientProperty, Class<? extends JComponent> ancestorType, String ancestorClientProperty) {
        return get(type, c, clientProperty, ancestorType, ancestorClientProperty, null);
    }

    public static <T> T get(Class<T> type, JComponent c, String clientProperty, Class<? extends JComponent> ancestorType, String ancestorClientProperty, T defaultValue) {
        Object o = c.getClientProperty(clientProperty);
        if (o != null && type.isAssignableFrom(o.getClass())) {
            return type.cast(o);
        }
        JComponent ancestor = getAncestor(c, ancestorType);
        if (ancestor != null) {
            o = ancestor.getClientProperty(ancestorClientProperty);
            if (o != null && type.isAssignableFrom(o.getClass())) {
                return type.cast(o);
            }
        }
        return defaultValue;
    }

    public static <C extends JComponent> C getAncestor(JComponent c, Class<C> ancestorType) {
        while (c != null) {
            if (ancestorType.isAssignableFrom(c.getClass())) {
                return ancestorType.cast(c);
            }
            Container parent = c.getParent();
            if (parent instanceof JComponent) {
                c = (JComponent) parent;
            } else {
                break;
            }
        }
        return null;
    }
}
