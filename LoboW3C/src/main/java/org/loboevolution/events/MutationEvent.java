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

package org.loboevolution.events;

import org.loboevolution.html.node.Node;

/**
 * The MutationEvent interface provides event properties that are specific to modifications
 * to the Document Object Model (DOM) hierarchy and nodes.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MutationEvent">MutationEvent - MDN</a>
 */
public interface MutationEvent extends Event {

    int ADDITION = 2;

    int MODIFICATION = 1;

    int REMOVAL = 3;

    int getAttrChange();

    String getAttrName();

    String getNewValue();

    String getPrevValue();

    Node getRelatedNode();

    void initMutationEvent(String typeArg, Boolean bubblesArg,
                           boolean cancelableArg, Node relatedNodeArg, String prevValueArg,
                           String newValueArg, String attrNameArg, int attrChangeArg);

    void initMutationEvent(String typeArg, Boolean bubblesArg,
                           boolean cancelableArg, Node relatedNodeArg, String prevValueArg,
                           String newValueArg, String attrNameArg);

    void initMutationEvent(String typeArg, Boolean bubblesArg,
                           boolean cancelableArg, Node relatedNodeArg, String prevValueArg,
                           String newValueArg);

    void initMutationEvent(String typeArg, Boolean bubblesArg,
                           boolean cancelableArg, Node relatedNodeArg, String prevValueArg);

    void initMutationEvent(String typeArg, Boolean bubblesArg,
                           boolean cancelableArg, Node relatedNodeArg);

    void initMutationEvent(String typeArg, Boolean bubblesArg,
                           boolean cancelableArg);

    void initMutationEvent(String typeArg, Boolean bubblesArg);

    void initMutationEvent(String typeArg);
}
