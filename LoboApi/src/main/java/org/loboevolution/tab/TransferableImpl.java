package org.loboevolution.tab;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class TransferableImpl implements Transferable {

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		return null;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return null;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return false;
	}

}
