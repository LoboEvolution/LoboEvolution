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
