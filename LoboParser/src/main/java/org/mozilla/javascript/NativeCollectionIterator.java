package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Iterator;

/**
 * <p>NativeCollectionIterator class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class NativeCollectionIterator extends ES6Iterator {
    private static final long serialVersionUID = 7094840979404373443L;
    private String className;
    private Type type;
    private transient Iterator<Hashtable.Entry> iterator = Collections.emptyIterator();
    enum Type { KEYS, VALUES, BOTH }

    static void init(ScriptableObject scope, String tag, boolean sealed) {
        ES6Iterator.init(scope, sealed, new NativeCollectionIterator(tag), tag);
    }

    /**
     * <p>Constructor for NativeCollectionIterator.</p>
     *
     * @param tag a {@link java.lang.String} object.
     */
    public NativeCollectionIterator(String tag)
    {
        this.className = tag;
        this.iterator = Collections.emptyIterator();
        this.type = Type.BOTH;
    }

    /**
     * <p>Constructor for NativeCollectionIterator.</p>
     *
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param className a {@link java.lang.String} object.
     * @param type a {@link org.mozilla.javascript.NativeCollectionIterator.Type} object.
     * @param iterator a {@link java.util.Iterator} object.
     */
    public NativeCollectionIterator(
        Scriptable scope, String className,
        Type type, Iterator<Hashtable.Entry> iterator)
    {
        super(scope, className);
        this.className = className;
        this.iterator = iterator;
        this.type = type;
    }

    /** {@inheritDoc} */
    @Override
    public String getClassName() {
        return className;
    }

    /** {@inheritDoc} */
    @Override
    protected boolean isDone(Context cx, Scriptable scope) {
        return !iterator.hasNext();
    }

    /** {@inheritDoc} */
    @Override
    protected Object nextValue(Context cx, Scriptable scope) {
        final Hashtable.Entry e = iterator.next();
        switch (type) {
            case KEYS:
                return e.key;
            case VALUES:
                return e.value;
            case BOTH:
                return cx.newArray(scope, new Object[] { e.key, e.value });
            default:
                throw new AssertionError();
        }
    }

    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();
        className = (String) stream.readObject();
        type = (Type) stream.readObject();
        iterator = Collections.emptyIterator();
    }

    private void writeObject(ObjectOutputStream stream)
        throws IOException
    {
        stream.defaultWriteObject();
        stream.writeObject(className);
        stream.writeObject(type);
    }
}
