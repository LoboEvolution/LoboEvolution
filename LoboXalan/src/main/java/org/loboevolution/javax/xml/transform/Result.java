// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Result.java

package org.loboevolution.javax.xml.transform;


public interface Result
{

    public abstract void setSystemId(String s);

    public abstract String getSystemId();

    public static final String PI_DISABLE_OUTPUT_ESCAPING = "org.loboevolution.javax.xml.transform.disable-output-escaping";
    public static final String PI_ENABLE_OUTPUT_ESCAPING = "org.loboevolution.javax.xml.transform.enable-output-escaping";
}
