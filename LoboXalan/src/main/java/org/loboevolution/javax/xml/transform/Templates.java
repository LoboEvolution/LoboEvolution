// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Templates.java

package org.loboevolution.javax.xml.transform;

import java.util.Properties;

// Referenced classes of package org.loboevolution.javax.xml.transform:
//            TransformerConfigurationException, Transformer

public interface Templates
{

    public abstract Transformer newTransformer()
        throws TransformerConfigurationException;

    public abstract Properties getOutputProperties();
}
