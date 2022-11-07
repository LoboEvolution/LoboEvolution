// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Duration.java

package org.loboevolution.javax.xml.datatype;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import org.loboevolution.javax.xml.namespace.QName;

// Referenced classes of package org.loboevolution.javax.xml.datatype:
//            DatatypeConstants

public abstract class Duration
{

    public Duration()
    {
        throw new RuntimeException("Stub!");
    }

    public QName getXMLSchemaType()
    {
        throw new RuntimeException("Stub!");
    }

    public abstract int getSign();

    public int getYears()
    {
        throw new RuntimeException("Stub!");
    }

    public int getMonths()
    {
        throw new RuntimeException("Stub!");
    }

    public int getDays()
    {
        throw new RuntimeException("Stub!");
    }

    public int getHours()
    {
        throw new RuntimeException("Stub!");
    }

    public int getMinutes()
    {
        throw new RuntimeException("Stub!");
    }

    public int getSeconds()
    {
        throw new RuntimeException("Stub!");
    }

    public long getTimeInMillis(Calendar startInstant)
    {
        throw new RuntimeException("Stub!");
    }

    public long getTimeInMillis(Date startInstant)
    {
        throw new RuntimeException("Stub!");
    }

    public abstract Number getField(DatatypeConstants.Field field);

    public abstract boolean isSet(DatatypeConstants.Field field);

    public abstract Duration add(Duration duration);

    public abstract void addTo(Calendar calendar);

    public void addTo(Date date)
    {
        throw new RuntimeException("Stub!");
    }

    public Duration subtract(Duration rhs)
    {
        throw new RuntimeException("Stub!");
    }

    public Duration multiply(int factor)
    {
        throw new RuntimeException("Stub!");
    }

    public abstract Duration multiply(BigDecimal bigdecimal);

    public abstract Duration negate();

    public abstract Duration normalizeWith(Calendar calendar);

    public abstract int compare(Duration duration);

    public boolean isLongerThan(Duration duration)
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isShorterThan(Duration duration)
    {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(Object duration)
    {
        throw new RuntimeException("Stub!");
    }

    public abstract int hashCode();

    public String toString()
    {
        throw new RuntimeException("Stub!");
    }
}
