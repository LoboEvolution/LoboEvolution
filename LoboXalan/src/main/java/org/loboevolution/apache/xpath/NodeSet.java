/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the  "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.loboevolution.apache.xpath;

import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.AbstractList;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import java.util.List;

/**
 * The NodeSet class can act as either a NodeVector, NodeList, or NodeIterator. However, in order
 * for it to act as a NodeVector or NodeList, it's required that setShouldCacheNodes(true) be called
 * before the first nextNode() is called, in order that nodes can be added as they are fetched.
 * Derived classes that implement iterators must override runTo(int index), in order that they may
 * run the iteration to the given index.
 *
 * <p>Note that we directly implement the DOM's NodeIterator interface. We do not emulate all the
 * behavior of the standard NodeIterator. In particular, we do not guarantee to present a "live
 * view" of the document ... but in XSLT, the source document should never be mutated, so this
 * should never be an issue.
 *
 * <p>Thought: Should NodeSet really implement NodeList and NodeIterator, or should there be
 * specific subclasses of it which do so? The advantage of doing it all here is that all NodeSets
 * will respond to the same calls; the disadvantage is that some of them may return
 * less-than-enlightening results when you do so.
 */
public class NodeSet extends AbstractList<Node> implements HTMLCollection {


    public NodeSet(List<Node> listVector) {
        super(listVector);
    }

    @Override
    public Element namedItem(String name) {return null;}

    @Override
    public int getLength() {
        return size();
    }

    @Override
    public Node item(Object index) {
        try {
            double idx = Double.parseDouble(index.toString());
            if (idx >= getLength() || idx == -1) return null;
            return this.get((int) idx);
        } catch (NumberFormatException e) {
            return this.get(0);
        }
    }

    @Override
    public void setItem(Integer index, Node node) {
        set(index, node);
    }
}
