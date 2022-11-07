// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   URIResolver.java

package org.loboevolution.javax.xml.transform;


// Referenced classes of package org.loboevolution.javax.xml.transform:
//            TransformerException, Source

public interface URIResolver
{

    public abstract Source resolve(String s, String s1)
        throws TransformerException;
}
