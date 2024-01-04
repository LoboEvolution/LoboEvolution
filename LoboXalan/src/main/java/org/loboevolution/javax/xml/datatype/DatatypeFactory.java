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

package org.loboevolution.javax.xml.datatype;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.GregorianCalendar;

// Referenced classes of package org.loboevolution.javax.xml.datatype:
//            DatatypeConfigurationException, Duration, XMLGregorianCalendar

public abstract class DatatypeFactory {

    public static final String DATATYPEFACTORY_IMPLEMENTATION_CLASS = null;
    public static final String DATATYPEFACTORY_PROPERTY = "org.loboevolution.javax.xml.datatype.DatatypeFactory";

    protected DatatypeFactory() {
        throw new RuntimeException("Stub!");
    }

    public static DatatypeFactory newInstance()
            throws DatatypeConfigurationException {
        throw new RuntimeException("Stub!");
    }

    public static DatatypeFactory newInstance(final String factoryClassName, final ClassLoader classLoader)
            throws DatatypeConfigurationException {
        throw new RuntimeException("Stub!");
    }

    public abstract Duration newDuration(final String s);

    public abstract Duration newDuration(long l);

    public abstract Duration newDuration(boolean flag, BigInteger biginteger, BigInteger biginteger1, BigInteger biginteger2, BigInteger biginteger3, BigInteger biginteger4, BigDecimal bigdecimal);

    public Duration newDuration(final boolean isPositive, final int years, final int months, final int days, final int hours, final int minutes, final int seconds) {
        throw new RuntimeException("Stub!");
    }

    public Duration newDurationDayTime(final String lexicalRepresentation) {
        throw new RuntimeException("Stub!");
    }

    public Duration newDurationDayTime(final long durationInMilliseconds) {
        throw new RuntimeException("Stub!");
    }

    public Duration newDurationDayTime(final boolean isPositive, final BigInteger day, final BigInteger hour, final BigInteger minute, final BigInteger second) {
        throw new RuntimeException("Stub!");
    }

    public Duration newDurationDayTime(final boolean isPositive, final int day, final int hour, final int minute, final int second) {
        throw new RuntimeException("Stub!");
    }

    public Duration newDurationYearMonth(final String lexicalRepresentation) {
        throw new RuntimeException("Stub!");
    }

    public Duration newDurationYearMonth(final long durationInMilliseconds) {
        throw new RuntimeException("Stub!");
    }

    public Duration newDurationYearMonth(final boolean isPositive, final BigInteger year, final BigInteger month) {
        throw new RuntimeException("Stub!");
    }

    public Duration newDurationYearMonth(final boolean isPositive, final int year, final int month) {
        throw new RuntimeException("Stub!");
    }

    public abstract XMLGregorianCalendar newXMLGregorianCalendar();

    public abstract XMLGregorianCalendar newXMLGregorianCalendar(final String s);

    public abstract XMLGregorianCalendar newXMLGregorianCalendar(GregorianCalendar gregoriancalendar);

    public abstract XMLGregorianCalendar newXMLGregorianCalendar(BigInteger biginteger, final int i, final int j, final int k, final int l, final int i1, BigDecimal bigdecimal,
                                                                 int j1);

    public XMLGregorianCalendar newXMLGregorianCalendar(final int year, final int month, final int day, final int hour, final int minute, final int second, final int millisecond,
                                                        final int timezone) {
        throw new RuntimeException("Stub!");
    }

    public XMLGregorianCalendar newXMLGregorianCalendarDate(final int year, final int month, final int day, final int timezone) {
        throw new RuntimeException("Stub!");
    }

    public XMLGregorianCalendar newXMLGregorianCalendarTime(final int hours, final int minutes, final int seconds, final int timezone) {
        throw new RuntimeException("Stub!");
    }

    public XMLGregorianCalendar newXMLGregorianCalendarTime(final int hours, final int minutes, final int seconds, final BigDecimal fractionalSecond, final int timezone) {
        throw new RuntimeException("Stub!");
    }

    public XMLGregorianCalendar newXMLGregorianCalendarTime(final int hours, final int minutes, final int seconds, final int milliseconds, final int timezone) {
        throw new RuntimeException("Stub!");
    }

}
