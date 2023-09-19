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
