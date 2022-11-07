// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XMLGregorianCalendar.java

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
