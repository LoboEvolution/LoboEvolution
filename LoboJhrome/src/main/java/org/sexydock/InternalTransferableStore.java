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
