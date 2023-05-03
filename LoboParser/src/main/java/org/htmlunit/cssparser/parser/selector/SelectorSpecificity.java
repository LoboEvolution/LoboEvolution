/*
 * Copyright (c) 2019-2023 Ronald Brill.
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
package org.htmlunit.cssparser.parser.selector;

import java.io.Serializable;

import org.htmlunit.cssparser.parser.condition.Condition;
import org.htmlunit.cssparser.parser.condition.NotPseudoClassCondition;

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
    public static final SelectorSpecificity FROM_STYLE_ATTRIBUTE = new SelectorSpecificity(true, 0, 0, 0);
    /**
     * The specificity for browser defaults.
     */
    public static final SelectorSpecificity DEFAULT_STYLE_ATTRIBUTE = new SelectorSpecificity(false, 0, 0, 0);

    private int fromStyle_;
    private int idCount_;
    private int classCount_;
    private int typeCount_;

    /**
     * Ctor.
     * @param selector the selector to read from
     */
    public SelectorSpecificity(final Selector selector) {
        readSelectorSpecificity(selector);
    }

    private SelectorSpecificity(final boolean fromStyle, final int idCount, final int classCount, final int typeCount) {
        fromStyle_ = fromStyle ? 1 : 0;
        idCount_ = idCount;
        classCount_ = classCount;
        typeCount_ = typeCount;
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
                    typeCount_++;
                }
                if (es.getConditions() != null) {
                    for (final Condition condition : es.getConditions()) {
                        readSelectorSpecificity(condition);
                    }
                }
                return;
            case PSEUDO_ELEMENT_SELECTOR:
                final PseudoElementSelector pes = (PseudoElementSelector) selector;
                final String pesName = pes.getLocalName();
                if (pesName != null) {
                    typeCount_++;
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
                idCount_++;
                return;
            case CLASS_CONDITION:
                classCount_++;
                return;
            case ATTRIBUTE_CONDITION:
                classCount_++;
                return;
            case SUBSTRING_ATTRIBUTE_CONDITION:
                classCount_++;
                return;
            case SUFFIX_ATTRIBUTE_CONDITION:
                classCount_++;
                return;
            case PREFIX_ATTRIBUTE_CONDITION:
                classCount_++;
                return;
            case BEGIN_HYPHEN_ATTRIBUTE_CONDITION:
                classCount_++;
                return;
            case ONE_OF_ATTRIBUTE_CONDITION:
                classCount_++;
                return;
            case NOT_PSEUDO_CLASS_CONDITION:
                final NotPseudoClassCondition notPseudoCondition = (NotPseudoClassCondition) condition;
                final SelectorList selectorList = notPseudoCondition.getSelectors();
                for (final Selector selector : selectorList) {
                    readSelectorSpecificity(selector);
                }
                return;
            case PSEUDO_CLASS_CONDITION:
                classCount_++;
                return;
            case LANG_CONDITION:
                classCount_++;
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
        // return (fromStyle_ > 0 ? "!" : "") + idCount_ + "," + classCount_ + "," + typeCount_;
        return fromStyle_ + "," + idCount_ + "," + classCount_ + "," + typeCount_;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final SelectorSpecificity other) {
        if (fromStyle_ != other.fromStyle_) {
            return fromStyle_ - other.fromStyle_;
        }
        else if (idCount_ != other.idCount_) {
            return idCount_ - other.idCount_;
        }
        else if (classCount_ != other.classCount_) {
            return classCount_ - other.classCount_;
        }
        else if (typeCount_ != other.typeCount_) {
            return typeCount_ - other.typeCount_;
        }
        return 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + fromStyle_;
        result = prime * result + idCount_;
        result = prime * result + classCount_;
        result = prime * result + typeCount_;
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
        if (fromStyle_ != other.fromStyle_) {
            return false;
        }
        if (idCount_ != other.idCount_) {
            return false;
        }
        if (classCount_ != other.classCount_) {
            return false;
        }
        if (typeCount_ != other.typeCount_) {
            return false;
        }
        return true;
    }
}
