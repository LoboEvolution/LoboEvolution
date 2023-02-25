/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
package org.loboevolution.html.dom.nodeimpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.nodeimpl.event.EventTargetImpl;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.TypeInfo;

/**
 * <p>AttrImpl class.</p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class AttrImpl extends EventTargetImpl implements Attr {

    private final String name;

    private String value;

    private boolean nameId;

    private Node ownerElement;

    @Builder.Default
    private boolean specified = true;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocalName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return Strings.isNotBlank(getPrefix()) ? getPrefix() + ":" + this.name : this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNodeName() {
        return Strings.isNotBlank(getPrefix()) ? getPrefix() + ":" + this.name : this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNodeType() {
        return Node.ATTRIBUTE_NODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNodeValue() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTextContent() {
        return getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeInfo getSchemaTypeInfo() {
        return new AttributeTypeInfo(isId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isId() {
        return ownerElement != null && (this.nameId || name.toLowerCase().contains("id"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNodeValue(String nodeValue) {
        this.value = nodeValue;
    }

    @Override
    public String toString() {
        return "[object Attr]";
    }
}
