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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import com.gargoylesoftware.css.parser.AbstractLocatable;

/**
 * Implementation of SelectorList.
 *
 * @author Ronald Brill
 */
public class SelectorListImpl extends AbstractLocatable implements SelectorList, Serializable {

    private List<Selector> selectors_ = new ArrayList<>();

    /**
     * @return the list of selectors.
     */
    public List<Selector> getSelectors() {
        return selectors_;
    }

    @Override
    public String toString() {
        return selectors_.stream()
                .map(n -> n.toString())
                .collect(Collectors.joining(", "));
    }

    @Override
    public int size() {
        return selectors_.size();
    }

    @Override
    public boolean isEmpty() {
        return selectors_.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return selectors_.contains(o);
    }

    @Override
    public Iterator<Selector> iterator() {
        return selectors_.iterator();
    }

    @Override
    public Object[] toArray() {
        return selectors_.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        return selectors_.toArray(a);
    }

    @Override
    public boolean add(final Selector e) {
        return selectors_.add(e);
    }

    @Override
    public boolean remove(final Object o) {
        return selectors_.remove(o);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        return selectors_.containsAll(c);
    }

    @Override
    public boolean addAll(final Collection<? extends Selector> c) {
        return selectors_.addAll(c);
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends Selector> c) {
        return selectors_.addAll(index, c);
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        return selectors_.removeAll(c);
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        return selectors_.retainAll(c);
    }

    @Override
    public void clear() {
        selectors_.clear();
    }

    @Override
    public Selector get(final int index) {
        return selectors_.get(index);
    }

    @Override
    public Selector set(final int index, final Selector element) {
        return selectors_.set(index, element);
    }

    @Override
    public void add(final int index, final Selector element) {
        selectors_.add(index, element);
    }

    @Override
    public Selector remove(final int index) {
        return selectors_.remove(index);
    }

    @Override
    public int indexOf(final Object o) {
        return selectors_.indexOf(o);
    }

    @Override
    public int lastIndexOf(final Object o) {
        return selectors_.lastIndexOf(o);
    }

    @Override
    public ListIterator<Selector> listIterator() {
        return selectors_.listIterator();
    }

    @Override
    public ListIterator<Selector> listIterator(final int index) {
        return selectors_.listIterator(index);
    }

    @Override
    public List<Selector> subList(final int fromIndex, final int toIndex) {
        return selectors_.subList(fromIndex, toIndex);
    }
}
