// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DOMLocator.java

package org.loboevolution.javax.xml.transform.dom;

import org.loboevolution.javax.xml.transform.SourceLocator;
import org.loboevolution.html.node.Node;

public interface DOMLocator
    extends SourceLocator
{

    public abstract Node getOriginatingNode();
}
