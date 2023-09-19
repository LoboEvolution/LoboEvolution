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

package org.sexydock;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.UUID;

public final class InternalTransferableStore {
    public static final DataFlavor INTERNAL_TRANSFERABLE_FLAVOR = new DataFlavor("application/x-sexydock-tab", "SexyDock Tab");
    private String storedUUID = null;
    private Object storedObject = null;

    public static InternalTransferableStore getDefaultInstance() {
        return DefaultInstanceHolder.INSTANCE;
    }

    public Transferable createTransferable(Object o) {
        String uuid = UUID.randomUUID().toString();
        while (uuid.equals(storedUUID)) {
            uuid = UUID.randomUUID().toString();
        }
        storedUUID = uuid;
        storedObject = o;
        return new InternalTransferable(uuid);
    }

    public Object getTransferableData(Transferable t) throws UnsupportedFlavorException, IOException {
        String uuid = (String) t.getTransferData(INTERNAL_TRANSFERABLE_FLAVOR);
        return uuid.equals(storedUUID) ? storedObject : null;
    }

    public void cleanUp(Transferable t) {
        if (t.isDataFlavorSupported(INTERNAL_TRANSFERABLE_FLAVOR)) {
            try {
                String uuid = (String) t.getTransferData(INTERNAL_TRANSFERABLE_FLAVOR);
                if (uuid.equals(storedUUID)) {
                    storedUUID = null;
                    storedObject = null;
                }
            } catch (Exception ex) {

            }
        }
    }

    private static class InternalTransferable extends StringSelection {
        public InternalTransferable(String data) {
            super(data);
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{INTERNAL_TRANSFERABLE_FLAVOR};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor == INTERNAL_TRANSFERABLE_FLAVOR;
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return super.getTransferData(DataFlavor.stringFlavor);
        }
    }

    private static class DefaultInstanceHolder {
        public static final InternalTransferableStore INSTANCE = new InternalTransferableStore();
    }
}
