/*
 * Copyright (c) 2019-2024 Ronald Brill.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.htmlunit.cssparser.parser.selector;

/**
 * @author Ronald Brill
 */
public class RelativeSelector extends AbstractSelector {

    private final Combinator combinator_;
    private final Selector selector_;

    /**
     * Ctor.
     * @param combinator the combinator to use
     * @param selector the selector
     */
    public RelativeSelector(final Combinator combinator, final Selector selector) {
        combinator_ = combinator;
        selector_ = selector;
    }

    /**
     * @return the containing selector
     */
    public Selector getSelector() {
        return selector_;
    }

    @Override
    public SelectorType getSelectorType() {
        return SelectorType.RELATIVE_SELECTOR;
    }

    /**
     * @return the combinator
     */
    public Combinator getCombinator() {
        return combinator_;
    }

    @Override
    public SimpleSelector getSimpleSelector() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        if (' ' == combinator_.getChar()) {
            return selector_.toString();
        }

        return combinator_.getChar() + " " + selector_.toString();
    }
}
