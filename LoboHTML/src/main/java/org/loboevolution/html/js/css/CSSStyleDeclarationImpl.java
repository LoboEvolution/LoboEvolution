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

package org.loboevolution.html.js.css;

import org.loboevolution.html.node.css.CSSRule;
import org.loboevolution.html.node.css.CSSStyleDeclaration;

public class CSSStyleDeclarationImpl implements CSSStyleDeclaration {

    private final com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl style;

    public CSSStyleDeclarationImpl(com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl style) {
        this.style = style;
    }

    /** {@inheritDoc} */
    @Override
    public String getCssText() {
        return style.getCssText();
    }

    /** {@inheritDoc} */
    @Override
    public String getPropertyValue(String property) {
        return style.getPropertyValue(property);
    }

    /** {@inheritDoc} */
    @Override
    public String getPropertyPriority(String property) {
        return style.getPropertyPriority(property);
    }

    /** {@inheritDoc} */
    @Override
    public String getCssFloat() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String item(int index) {
        return style.getProperties().get(index).getName();
    }

    /** {@inheritDoc} */
    @Override
    public String removeProperty(String property) {
        return style.removeProperty(property);
    }

    /** {@inheritDoc} */
    @Override
    public int getLength() {
        return style.getLength();
    }

    /** {@inheritDoc} */
    @Override
    public CSSRule getParentRule() {
        return new CSSRuleImpl(style.getParentRule());
    }

    /** {@inheritDoc} */
    @Override
    public void setProperty(String propertyName, String value, String priority) {
        style.setProperty(propertyName, value, priority);
    }

    @Override
    public String toString() {
        return "[object CSSStyleDeclaration]";
    }
}
