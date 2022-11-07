// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XPathFunction.java

package org.loboevolution.javax.xml.xpath;

import java.util.List;

// Referenced classes of package org.loboevolution.javax.xml.xpath:
//            XPathFunctionException

public interface XPathFunction
{

    public abstract Object evaluate(List list)
        throws XPathFunctionException;
}
