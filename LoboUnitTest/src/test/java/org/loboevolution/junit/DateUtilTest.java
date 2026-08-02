/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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
package org.loboevolution.junit;

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.util.DateUtil;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for {@link DateUtil}.
 */
public class DateUtilTest extends LoboUnitTest {

	@Test
	public void parseRfc1123Date() {
		assertNotNull(new DateUtil().determineDateFormat("Sun, 02 Aug 2026 23:09:46 GMT", Locale.US));
	}

	@Test
	public void parseRfc850Date() {
		assertNotNull(new DateUtil().determineDateFormat("Sunday, 02-Aug-26 23:09:46 GMT", Locale.US));
	}

	@Test
	public void parseRfc1123NoLeadingZero() {
		assertNotNull(new DateUtil().determineDateFormat("Sun, 2 Aug 2026 23:09:46 GMT", Locale.US));
	}
}