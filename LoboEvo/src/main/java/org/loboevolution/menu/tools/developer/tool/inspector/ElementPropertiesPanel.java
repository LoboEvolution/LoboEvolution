/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

package org.loboevolution.menu.tools.developer.tool.inspector;

import lombok.extern.slf4j.Slf4j;
import org.htmlunit.cssparser.dom.CSSStyleDeclarationImpl;
import org.htmlunit.cssparser.dom.CSSStyleSheetImpl;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.Node;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>ElementPropertiesPanel class.</p>
 */
@Slf4j
public class ElementPropertiesPanel extends JPanel {

    private final JTable _properties;

    private final TableModel _defaultTableModel;

    /**
     * <p>Constructor for ElementPropertiesPanel.</p>
     */
    public ElementPropertiesPanel() {
        this._properties = new PropertiesJTable();
        this._defaultTableModel = new DefaultTableModel();

        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(_properties), BorderLayout.CENTER);
    }

    /**
     * Sets the forElement attribute of the ElementPropertiesPanel object
     *
     * @param node a {@link org.loboevolution.html.node.Node} object.
     */
    public void setForElement(final Node node) {
        try {
            _properties.setModel(tableModel(node));
            final TableColumnModel model = _properties.getColumnModel();
            if (model.getColumnCount() > 0) {
                model.getColumn(0).sizeWidthToFit();
            }
        } catch (final Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

     private TableModel tableModel(final Node node) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            Toolkit.getDefaultToolkit().beep();
            return _defaultTableModel;
        }

        final HTMLElementImpl elm = (HTMLElementImpl) node;
        final Map<String, String> cssProperties = new HashMap<>();

        final String classNames = elm.getClassName();
        final String elementName = elm.getTagName();
        final String[] classNameArray = Strings.isNotBlank(classNames) ? Strings.split(classNames) : null;
        final List<CSSStyleSheetImpl.SelectorEntry> matchingRules = elm.findStyleDeclarations(elementName, classNameArray, false);
        for (final CSSStyleSheetImpl.SelectorEntry entry : matchingRules) {
            final CSSStyleDeclarationImpl styleDeclaration = entry.getRule().getStyle();
            styleDeclaration.getProperties().forEach(prop -> {
                final String propertyName = prop.getName();
                final String propertyValue = styleDeclaration.getPropertyValue(propertyName);
                cssProperties.put(propertyName.toLowerCase(), propertyValue);
            });
        }
        return new PropertiesTableModel(cssProperties);
    }
}