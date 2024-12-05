/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of che, to any person obtaining a copy
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

package org.loboevolution.html.js.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.loboevolution.events.MutationEvent;
import org.loboevolution.html.node.Node;

/**
 * <p>UIEventImpl class.</p>
 */
@Getter
@NoArgsConstructor
public class MutationEventImpl extends EventImpl implements MutationEvent {

    private Node relatedNode;
    private String prevValue;
    private String newValue;
    private String attrName;
    private int attrChange;

    /**
     * <p>Constructor for MutationEventImpl.</p>
     *
     * @param params event constructor parameters
     */
    public MutationEventImpl(Object[] params) {
        setParams(params);
    }

    @Override
    public void initMutationEvent(String type, Boolean bubbles, boolean cancelable, Node relatedNode,
                                  String prevValue, String newValue, String attrName, int attrChange) {
        initMutationEvent(type, bubbles, cancelable, relatedNode, prevValue, newValue, attrName);
        this.attrChange = attrChange;
    }

    @Override
    public void initMutationEvent(String type, Boolean bubbles, boolean cancelable, Node relatedNode,
                                  String prevValue, String newValue, String attrName) {
        initMutationEvent(type, bubbles, cancelable, relatedNode, prevValue, newValue);
        this.attrName = attrName;
    }

    @Override
    public void initMutationEvent(String type, Boolean bubbles, boolean cancelable, Node relatedNode,
                                  String prevValue, String newValue) {
        initMutationEvent(type, bubbles, cancelable, relatedNode, prevValue);
        this.newValue = newValue;
    }

    @Override
    public void initMutationEvent(String type, Boolean bubbles, boolean cancelable, Node relatedNode, String prevValue) {
        initMutationEvent(type, bubbles, cancelable, relatedNode);
        this.prevValue = prevValue;
    }

    @Override
    public void initMutationEvent(String type, Boolean bubbles, boolean cancelable, Node relatedNode) {
        initMutationEvent(type, bubbles, cancelable);
        this.relatedNode = relatedNode;
    }

    @Override
    public void initMutationEvent(String type, Boolean bubbles, boolean cancelable) {
        super.initEvent(type, bubbles, cancelable);
    }

    @Override
    public void initMutationEvent(String type, Boolean bubbles) {
        super.initEvent(type, bubbles);
    }

    @Override
    public void initMutationEvent(String type) {
        super.initEvent(type);
    }

    @Override
    public String toString() {
        return "[object MutationEvent]";
    }
}
