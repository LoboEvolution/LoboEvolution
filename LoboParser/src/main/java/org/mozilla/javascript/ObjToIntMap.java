/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Map to associate objects to integers. The map does not synchronize any of its operation, so
 * either use it from a single thread or do own synchronization or perform all mutation operations
 * on one thread before passing the map to others
 *
 * @author Igor Bukanov
 */
@SuppressWarnings("unused")
public class ObjToIntMap implements Serializable {
    private static final long serialVersionUID = -1542220580748809402L;

    // Map implementation via hashtable,
    // follows "The Art of Computer Programming" by Donald E. Knuth

    // ObjToIntMap is a copy cat of ObjToIntMap with API adjusted to object keys

    public static class Iterator {

        Iterator(ObjToIntMap master) {
            this.master = master;
        }

        final void init(Object[] keys, int[] values, int keyCount) {
            this.keys = keys;
            this.values = values;
            this.cursor = -1;
            this.remaining = keyCount;
        }

        public void start() {
            master.initIterator(this);
            next();
        }

        public boolean done() {
            return remaining < 0;
        }

        public void next() {
            if (remaining == -1) Kit.codeBug();
            if (remaining == 0) {
                remaining = -1;
                cursor = -1;
            } else {
                for (++cursor; ; ++cursor) {
                    Object key = keys[cursor];
                    if (key != null && key != DELETED) {
                        --remaining;
                        break;
                    }
                }
            }
        }

        public Object getKey() {
            Object key = keys[cursor];
            if (key == UniqueTag.NULL_VALUE) {
                key = null;
            }
            return key;
        }

        public int getValue() {
            return values[cursor];
        }

        public void setValue(int value) {
            values[cursor] = value;
        }

        ObjToIntMap master;
        private int cursor;
        private int remaining;
        private Object[] keys;
        private int[] values;
    }

    public ObjToIntMap() {
        this(4);
    }

    public ObjToIntMap(int keyCountHint) {
        if (keyCountHint < 0) Kit.codeBug();
        // Table grow when number of stored keys >= 3/4 of max capacity
        int minimalCapacity = keyCountHint * 4 / 3;
        int i;
        for (i = 2; (1 << i) < minimalCapacity; ++i) {}
        power = i;
        if (check && power < 2) Kit.codeBug();
    }

    public boolean isEmpty() {
        return keyCount == 0;
    }

    public int size() {
        return keyCount;
    }

    public boolean has(Object key) {
        if (key == null) {
            key = UniqueTag.NULL_VALUE;
        }
        return 0 <= findIndex(key);
    }

    /**
     * Get integer value assigned with key.
     *
     * @return key integer value or defaultValue if key is absent
     */
    public int get(Object key, int defaultValue) {
        if (key == null) {
            key = UniqueTag.NULL_VALUE;
        }
        int index = findIndex(key);
        if (0 <= index) {
            return values[index];
        }
        return defaultValue;
    }

    /**
     * Get integer value assigned with key.
     *
     * @return key integer value
     * @throws RuntimeException if key does not exist
     */
    public int getExisting(Object key) {
        if (key == null) {
            key = UniqueTag.NULL_VALUE;
        }
        int index = findIndex(key);
        if (0 <= index) {
            return values[index];
        }
        // Key must exist
        Kit.codeBug();
        return 0;
    }

    public void put(Object key, int value) {
        if (key == null) {
            key = UniqueTag.NULL_VALUE;
        }
        int index = ensureIndex(key);
        values[index] = value;
    }

    /**
     * If table already contains a key that equals to keyArg, return that key while setting its
     * value to zero, otherwise add keyArg with 0 value to the table and return it.
     */
    public Object intern(Object keyArg) {
        boolean nullKey = false;
        if (keyArg == null) {
            nullKey = true;
            keyArg = UniqueTag.NULL_VALUE;
        }
        int index = ensureIndex(keyArg);
        values[index] = 0;
        return (nullKey) ? null : keys[index];
    }

    public void remove(Object key) {
        if (key == null) {
            key = UniqueTag.NULL_VALUE;
        }
        int index = findIndex(key);
        if (0 <= index) {
            keys[index] = DELETED;
            --keyCount;
        }
    }

    public void clear() {
        int i = keys.length;
        while (i != 0) {
            keys[--i] = null;
        }
        keyCount = 0;
        occupiedCount = 0;
    }

    public Iterator newIterator() {
        return new Iterator(this);
    }

    // The sole purpose of the method is to avoid accessing private fields
    // from the Iterator inner class to workaround JDK 1.1 compiler bug which
    // generates code triggering VerifierError on recent JVMs
    final void initIterator(Iterator i) {
        i.init(keys, values, keyCount);
    }

    /** Return array of present keys */
    public Object[] getKeys() {
        Object[] array = new Object[keyCount];
        getKeys(array, 0);
        return array;
    }

    public void getKeys(Object[] array, int offset) {
        int count = keyCount;
        for (int i = 0; count != 0; ++i) {
            Object key = keys[i];
            if (key != null && key != DELETED) {
                if (key == UniqueTag.NULL_VALUE) {
                    key = null;
                }
                array[offset] = key;
                ++offset;
                --count;
            }
        }
    }

    private static int tableLookupStep(int fraction, int mask, int power) {
        int shift = 32 - 2 * power;
        if (shift >= 0) {
            return ((fraction >>> shift) & mask) | 1;
        }
        return (fraction & (mask >>> -shift)) | 1;
    }

    private int findIndex(Object key) {
        if (keys != null) {
            int hash = key.hashCode();
            int fraction = hash * A;
            int index = fraction >>> (32 - power);
            Object test = keys[index];
            if (test != null) {
                int N = 1 << power;
                if (test == key || (values[N + index] == hash && test.equals(key))) {
                    return index;
                }
                // Search in table after first failed attempt
                int mask = N - 1;
                int step = tableLookupStep(fraction, mask, power);
                int n = 0;
                for (; ; ) {
                    if (check) {
                        if (n >= occupiedCount) Kit.codeBug();
                        ++n;
                    }
                    index = (index + step) & mask;
                    test = keys[index];
                    if (test == null) {
                        break;
                    }
                    if (test == key || (values[N + index] == hash && test.equals(key))) {
                        return index;
                    }
                }
            }
        }
        return -1;
    }

    // Insert key that is not present to table without deleted entries
    // and enough free space
    private int insertNewKey(Object key, int hash) {
        if (check && occupiedCount != keyCount) Kit.codeBug();
        if (check && keyCount == 1 << power) Kit.codeBug();
        int fraction = hash * A;
        int index = fraction >>> (32 - power);
        int N = 1 << power;
        if (keys[index] != null) {
            int mask = N - 1;
            int step = tableLookupStep(fraction, mask, power);
            int firstIndex = index;
            do {
                if (check && keys[index] == DELETED) Kit.codeBug();
                index = (index + step) & mask;
                if (check && firstIndex == index) Kit.codeBug();
            } while (keys[index] != null);
        }
        keys[index] = key;
        values[N + index] = hash;
        ++occupiedCount;
        ++keyCount;

        return index;
    }

    private void rehashTable() {
        if (keys == null) {
            if (check && keyCount != 0) Kit.codeBug();
            if (check && occupiedCount != 0) Kit.codeBug();
            int N = 1 << power;
            keys = new Object[N];
            values = new int[2 * N];
        } else {
            // Check if removing deleted entries would free enough space
            if (keyCount * 2 >= occupiedCount) {
                // Need to grow: less then half of deleted entries
                ++power;
            }
            int N = 1 << power;
            Object[] oldKeys = keys;
            int[] oldValues = values;
            int oldN = oldKeys.length;
            keys = new Object[N];
            values = new int[2 * N];

            int remaining = keyCount;
            occupiedCount = keyCount = 0;
            for (int i = 0; remaining != 0; ++i) {
                Object key = oldKeys[i];
                if (key != null && key != DELETED) {
                    int keyHash = oldValues[oldN + i];
                    int index = insertNewKey(key, keyHash);
                    values[index] = oldValues[i];
                    --remaining;
                }
            }
        }
    }

    // Ensure key index creating one if necessary
    private int ensureIndex(Object key) {
        int hash = key.hashCode();
        int index = -1;
        int firstDeleted = -1;
        if (keys != null) {
            int fraction = hash * A;
            index = fraction >>> (32 - power);
            Object test = keys[index];
            if (test != null) {
                int N = 1 << power;
                if (test == key || (values[N + index] == hash && test.equals(key))) {
                    return index;
                }
                if (test == DELETED) {
                    firstDeleted = index;
                }

                // Search in table after first failed attempt
                int mask = N - 1;
                int step = tableLookupStep(fraction, mask, power);
                int n = 0;
                for (; ; ) {
                    if (check) {
                        if (n >= occupiedCount) Kit.codeBug();
                        ++n;
                    }
                    index = (index + step) & mask;
                    test = keys[index];
                    if (test == null) {
                        break;
                    }
                    if (test == key || (values[N + index] == hash && test.equals(key))) {
                        return index;
                    }
                    if (test == DELETED && firstDeleted < 0) {
                        firstDeleted = index;
                    }
                }
            }
        }
        // Inserting of new key
        if (check && keys != null && keys[index] != null) Kit.codeBug();
        if (firstDeleted >= 0) {
            index = firstDeleted;
        } else {
            // Need to consume empty entry: check occupation level
            if (keys == null || occupiedCount * 4 >= (1 << power) * 3) {
                // Too litle unused entries: rehash
                rehashTable();
                return insertNewKey(key, hash);
            }
            ++occupiedCount;
        }
        keys[index] = key;
        values[(1 << power) + index] = hash;
        ++keyCount;
        return index;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        int count = keyCount;
        for (int i = 0; count != 0; ++i) {
            Object key = keys[i];
            if (key != null && key != DELETED) {
                --count;
                out.writeObject(key);
                out.writeInt(values[i]);
            }
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        int writtenKeyCount = keyCount;
        if (writtenKeyCount != 0) {
            keyCount = 0;
            int N = 1 << power;
            keys = new Object[N];
            values = new int[2 * N];
            for (int i = 0; i != writtenKeyCount; ++i) {
                Object key = in.readObject();
                int hash = key.hashCode();
                int index = insertNewKey(key, hash);
                values[index] = in.readInt();
            }
        }
    }
    private static final int A = 0x9e3779b9;

    private static final Object DELETED = new Object();

    private transient Object[] keys;
    private transient int[] values;

    private int power;
    private int keyCount;
    private transient int occupiedCount; // == keyCount + deleted_count

    // If true, enables consitency checks
    private static final boolean check = false;

}
