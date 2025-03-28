/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.svg;

import org.loboevolution.html.dom.smil.Time;
import org.loboevolution.html.dom.smil.TimeList;

import java.util.List;

/**
 * <p>TimeListImpl class.</p>
 */
public class TimeListImpl implements TimeList {

	private final List<Time> times;

	/**
	 * <p>Constructor for TimeListImpl.</p>
	 *
	 * @param times a {@link java.util.List} object.
	 */
	public TimeListImpl(final List<Time> times) {
		this.times = times;
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		return this.times.size();
	}

	/** {@inheritDoc} */
	@Override
	public Time item(final int index) {
		final int size = this.times.size();
		if (size > index && index > -1) {
			return this.times.get(index);
		} else {
			return null;
		}
	}
}
