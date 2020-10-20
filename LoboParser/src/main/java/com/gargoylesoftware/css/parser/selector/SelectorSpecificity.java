/*
 * Copyright (c) 2019-2020 Ronald Brill.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.css.parser.selector;

import java.io.Serializable;

import com.gargoylesoftware.css.parser.condition.Condition;

/**
 * Calculates a selector's specificity.
 * @see <a href="http://www.w3.org/TR/CSS21/cascade.html#specificity">W3C CSS21</a>
 *
 * @author Marc Guillemot
 * @author Ronald Brill
 */
public class SelectorSpecificity implements Comparable<SelectorSpecificity>, Serializable {

    /**
     * The specificity for declarations made in the style attributes of an element.
     */
    public static final SelectorSpecificity FROM_STYLE_ATTRIBUTE = new SelectorSpecificity(1, 0, 0, 0);
    /**
     * The specificity for browser defaults.
     */
    public static final SelectorSpecificity DEFAULT_STYLE_ATTRIBUTE = new SelectorSpecificity(0, 0, 0, 0);

    private int fieldA_;
    private int fieldB_;
    private int fieldC_;
    private int fieldD_;

    /**
     * Ctor.
     * @param selector the selector to read from
     */
    public SelectorSpecificity(final Selector selector) {
        readSelectorSpecificity(selector);
    }

    private SelectorSpecificity(final int a, final int b, final int c, final int d) {
        fieldA_ = a;
        fieldB_ = b;
        fieldC_ = c;
        fieldD_ = d;
    }

    private void readSelectorSpecificity(final Selector selector) {
        switch (selector.getSelectorType()) {
            case DESCENDANT_SELECTOR:
                final DescendantSelector ds = (DescendantSelector) selector;
                readSelectorSpecificity(ds.getAncestorSelector());
                readSelectorSpecificity(ds.getSimpleSelector());
                return;
            case CHILD_SELECTOR:
                final ChildSelector cs = (ChildSelector) selector;
                readSelectorSpecificity(cs.getAncestorSelector());
                readSelectorSpecificity(cs.getSimpleSelector());
                return;
            case ELEMENT_NODE_SELECTOR:
                final ElementSelector es = (ElementSelector) selector;
                if (es.getLocalName() != null) {
                    fieldD_++;
                }
                if (es.getConditions() != null) {
                    for (Condition condition : es.getConditions()) {
                        readSelectorSpecificity(condition);
                    }
                }
                return;
            case PSEUDO_ELEMENT_SELECTOR:
                final PseudoElementSelector pes = (PseudoElementSelector) selector;
                final String pesName = pes.getLocalName();
                if (pesName != null) {
                    fieldD_++;
                }
                return;
            case DIRECT_ADJACENT_SELECTOR:
                final DirectAdjacentSelector das = (DirectAdjacentSelector) selector;
                readSelectorSpecificity(das.getSelector());
                readSelectorSpecificity(das.getSimpleSelector());
                return;
            case GENERAL_ADJACENT_SELECTOR:
                final GeneralAdjacentSelector gas = (GeneralAdjacentSelector) selector;
                readSelectorSpecificity(gas.getSelector());
                readSelectorSpecificity(gas.getSimpleSelector());
                return;
            default:
                throw new RuntimeException("Unhandled CSS selector type for specificity computation: '"
                        + selector.getSelectorType() + "'.");
        }
    }

    private void readSelectorSpecificity(final Condition condition) {
        switch (condition.getConditionType()) {
            case ID_CONDITION:
                fieldB_++;
                return;
            case CLASS_CONDITION:
                fieldC_++;
                return;
            case ATTRIBUTE_CONDITION:
                fieldC_++;
                return;
            case SUBSTRING_ATTRIBUTE_CONDITION:
                fieldC_++;
                return;
            case SUFFIX_ATTRIBUTE_CONDITION:
                fieldC_++;
                return;
            case PREFIX_ATTRIBUTE_CONDITION:
                fieldC_++;
                return;
            case BEGIN_HYPHEN_ATTRIBUTE_CONDITION:
                fieldC_++;
                return;
            case ONE_OF_ATTRIBUTE_CONDITION:
                fieldC_++;
                return;
            case PSEUDO_CLASS_CONDITION:
                fieldC_++;
                return;
            case LANG_CONDITION:
                fieldC_++;
                return;
            default:
                throw new RuntimeException("Unhandled CSS condition type for specifity computation: '"
                        + condition.getConditionType() + "'.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return fieldA_ + "," + fieldB_ + "," + fieldC_ + "," + fieldD_;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final SelectorSpecificity other) {
        if (fieldA_ != other.fieldA_) {
            return fieldA_ - other.fieldA_;
        }
        else if (fieldB_ != other.fieldB_) {
            return fieldB_ - other.fieldB_;
        }
        else if (fieldC_ != other.fieldC_) {
            return fieldC_ - other.fieldC_;
        }
        else if (fieldD_ != other.fieldD_) {
            return fieldD_ - other.fieldD_;
        }
        return 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + fieldA_;
        result = prime * result + fieldB_;
        result = prime * result + fieldC_;
        result = prime * result + fieldD_;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SelectorSpecificity other = (SelectorSpecificity) obj;
        if (fieldA_ != other.fieldA_) {
            return false;
        }
        if (fieldB_ != other.fieldB_) {
            return false;
        }
        if (fieldC_ != other.fieldC_) {
            return false;
        }
        if (fieldD_ != other.fieldD_) {
            return false;
        }
        return true;
    }
}
