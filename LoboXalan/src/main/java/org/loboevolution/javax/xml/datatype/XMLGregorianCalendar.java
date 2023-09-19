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
import java.util.*;
import org.loboevolution.javax.xml.namespace.QName;

// Referenced classes of package org.loboevolution.javax.xml.datatype:
//            Duration

public abstract class XMLGregorianCalendar
    implements Cloneable
{

    public XMLGregorianCalendar()
    {
        throw new RuntimeException("Stub!");
    }

    public abstract void clear();

    public abstract void reset();

    public abstract void setYear(BigInteger biginteger);

    public abstract void setYear(int i);

    public abstract void setMonth(int i);

    public abstract void setDay(int i);

    public abstract void setTimezone(int i);

    public void setTime(int hour, int minute, int second)
    {
        throw new RuntimeException("Stub!");
    }

    public abstract void setHour(int i);

    public abstract void setMinute(int i);

    public abstract void setSecond(int i);

    public abstract void setMillisecond(int i);

    public abstract void setFractionalSecond(BigDecimal bigdecimal);

    public void setTime(int hour, int minute, int second, BigDecimal fractional)
    {
        throw new RuntimeException("Stub!");
    }

    public void setTime(int hour, int minute, int second, int millisecond)
    {
        throw new RuntimeException("Stub!");
    }

    public abstract BigInteger getEon();

    public abstract int getYear();

    public abstract BigInteger getEonAndYear();

    public abstract int getMonth();

    public abstract int getDay();

    public abstract int getTimezone();

    public abstract int getHour();

    public abstract int getMinute();

    public abstract int getSecond();

    public int getMillisecond()
    {
        throw new RuntimeException("Stub!");
    }

    public abstract BigDecimal getFractionalSecond();

    public abstract int compare(XMLGregorianCalendar xmlgregoriancalendar);

    public abstract XMLGregorianCalendar normalize();

    public boolean equals(Object obj)
    {
        throw new RuntimeException("Stub!");
    }

    public int hashCode()
    {
        throw new RuntimeException("Stub!");
    }

    public abstract String toXMLFormat();

    public abstract QName getXMLSchemaType();

    public String toString()
    {
        throw new RuntimeException("Stub!");
    }

    public abstract boolean isValid();

    public abstract void add(Duration duration);

    public abstract GregorianCalendar toGregorianCalendar();

    public abstract GregorianCalendar toGregorianCalendar(TimeZone timezone, Locale locale, XMLGregorianCalendar xmlgregoriancalendar);

    public abstract TimeZone getTimeZone(int i);

    public abstract Object clone();
}
