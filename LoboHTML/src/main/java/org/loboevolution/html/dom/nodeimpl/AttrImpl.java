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
package org.loboevolution.html.dom.nodeimpl;

import com.gargoylesoftware.css.dom.DOMException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.loboevolution.html.dom.domimpl.AttributeTypeInfo;
import org.loboevolution.html.dom.nodeimpl.event.EventTargetImpl;
import org.loboevolution.html.node.*;

/**
 * <p>AttrImpl class.</p>
 */
@Data
@AllArgsConstructor
public class AttrImpl extends EventTargetImpl implements Attr {

    private final String name;

    private String value;

    private boolean nameId;

    private Element ownerElement;

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
    public String getNodeName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNodeType() {
        return NodeType.ATTRIBUTE_NODE;
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
        return this.nameId || name.toLowerCase().contains("id");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNodeValue(String nodeValue) {
        this.value = nodeValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node insertBefore(Node newChild, Node refChild) {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "'insertBefore' on 'Node': This node type does not support this method.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node removeChild(Node oldChild) {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "'removeChild' on 'Node': This node type does not support this method.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node replaceChild(Node newChild, Node refChild) {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "'replaceChild' on 'Node': This node type does not support this method.");
    }

    @Override
    public Node appendChild(Node newChild) {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "'appendChild' on 'Node': This node type does not support this method.");
    }

    @Override
    public String toString() {
        return "[object Attr]";
    }
}
