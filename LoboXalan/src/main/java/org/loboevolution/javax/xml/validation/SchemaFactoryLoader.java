// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SchemaFactoryLoader.java

package org.loboevolution.javax.xml.validation;


// Referenced classes of package org.loboevolution.javax.xml.validation:
//            SchemaFactory

public abstract class SchemaFactoryLoader
{

    protected SchemaFactoryLoader()
    {
        throw new RuntimeException("Stub!");
    }

    public abstract SchemaFactory newFactory(String s);
}
