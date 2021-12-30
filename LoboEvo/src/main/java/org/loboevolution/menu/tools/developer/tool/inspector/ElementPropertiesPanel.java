/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.menu.tools.developer.tool.inspector;

import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;
import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeType;

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
    public void setForElement(Node node) {
        try {
            _properties.setModel(tableModel(node));
            TableColumnModel model = _properties.getColumnModel();
            if (model.getColumnCount() > 0) {
                model.getColumn(0).sizeWidthToFit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

     private TableModel tableModel(Node node) {
        if (node.getNodeType() != NodeType.ELEMENT_NODE) {
            Toolkit.getDefaultToolkit().beep();
            return _defaultTableModel;
        }

        HTMLElementImpl elm = (HTMLElementImpl) node;
        Map<String, String> cssProperties = new HashMap<>();

        final String classNames = elm.getClassName();
        final String elementName = elm.getTagName();
        final String[] classNameArray = Strings.isNotBlank(classNames) ? Strings.split(classNames) : null;
        final List<CSSStyleSheetImpl.SelectorEntry> matchingRules = elm.findStyleDeclarations(elementName, classNameArray, false);
        for (CSSStyleSheetImpl.SelectorEntry entry : matchingRules) {
            final CSSStyleDeclarationImpl styleDeclaration = entry.getRule().getStyle();
            styleDeclaration.getProperties().forEach(prop -> {
                final String propertyName = prop.getName();
                final String propertyValue = styleDeclaration.getPropertyValue(propertyName);
                cssProperties.put(propertyName.toLowerCase(), propertyValue);
            });
        }

        List<CSSStyleDeclarationImpl> styleDeclaration = elm.getStyle().getStyleDeclarations();
        if(styleDeclaration != null) {
            styleDeclaration.forEach(decl -> decl.getProperties().forEach(prop -> {
                final String propertyName = prop.getName();
                final String propertyValue = decl.getPropertyValue(propertyName);
                cssProperties.put(propertyName.toLowerCase(), propertyValue);
            }));
        }

        return new PropertiesTableModel(cssProperties);
    }
}