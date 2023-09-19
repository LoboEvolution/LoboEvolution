/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

public abstract class DatatypeFactory
{

    protected DatatypeFactory()
    {
        throw new RuntimeException("Stub!");
    }

    public static DatatypeFactory newInstance()
        throws DatatypeConfigurationException
    {
        throw new RuntimeException("Stub!");
    }

    public static DatatypeFactory newInstance(String factoryClassName, ClassLoader classLoader)
        throws DatatypeConfigurationException
    {
        throw new RuntimeException("Stub!");
    }

    public abstract Duration newDuration(String s);

    public abstract Duration newDuration(long l);

    public abstract Duration newDuration(boolean flag, BigInteger biginteger, BigInteger biginteger1, BigInteger biginteger2, BigInteger biginteger3, BigInteger biginteger4, BigDecimal bigdecimal);

    public Duration newDuration(boolean isPositive, int years, int months, int days, int hours, int minutes, int seconds)
    {
        throw new RuntimeException("Stub!");
    }

    public Duration newDurationDayTime(String lexicalRepresentation)
    {
        throw new RuntimeException("Stub!");
    }

    public Duration newDurationDayTime(long durationInMilliseconds)
    {
        throw new RuntimeException("Stub!");
    }

    public Duration newDurationDayTime(boolean isPositive, BigInteger day, BigInteger hour, BigInteger minute, BigInteger second)
    {
        throw new RuntimeException("Stub!");
    }

    public Duration newDurationDayTime(boolean isPositive, int day, int hour, int minute, int second)
    {
        throw new RuntimeException("Stub!");
    }

    public Duration newDurationYearMonth(String lexicalRepresentation)
    {
        throw new RuntimeException("Stub!");
    }

    public Duration newDurationYearMonth(long durationInMilliseconds)
    {
        throw new RuntimeException("Stub!");
    }

    public Duration newDurationYearMonth(boolean isPositive, BigInteger year, BigInteger month)
    {
        throw new RuntimeException("Stub!");
    }

    public Duration newDurationYearMonth(boolean isPositive, int year, int month)
    {
        throw new RuntimeException("Stub!");
    }

    public abstract XMLGregorianCalendar newXMLGregorianCalendar();

    public abstract XMLGregorianCalendar newXMLGregorianCalendar(String s);

    public abstract XMLGregorianCalendar newXMLGregorianCalendar(GregorianCalendar gregoriancalendar);

    public abstract XMLGregorianCalendar newXMLGregorianCalendar(BigInteger biginteger, int i, int j, int k, int l, int i1, BigDecimal bigdecimal, 
            int j1);

    public XMLGregorianCalendar newXMLGregorianCalendar(int year, int month, int day, int hour, int minute, int second, int millisecond, 
            int timezone)
    {
        throw new RuntimeException("Stub!");
    }

    public XMLGregorianCalendar newXMLGregorianCalendarDate(int year, int month, int day, int timezone)
    {
        throw new RuntimeException("Stub!");
    }

    public XMLGregorianCalendar newXMLGregorianCalendarTime(int hours, int minutes, int seconds, int timezone)
    {
        throw new RuntimeException("Stub!");
    }

    public XMLGregorianCalendar newXMLGregorianCalendarTime(int hours, int minutes, int seconds, BigDecimal fractionalSecond, int timezone)
    {
        throw new RuntimeException("Stub!");
    }

    public XMLGregorianCalendar newXMLGregorianCalendarTime(int hours, int minutes, int seconds, int milliseconds, int timezone)
    {
        throw new RuntimeException("Stub!");
    }

    public static final String DATATYPEFACTORY_IMPLEMENTATION_CLASS = null;
    public static final String DATATYPEFACTORY_PROPERTY = "org.loboevolution.javax.xml.datatype.DatatypeFactory";

}
