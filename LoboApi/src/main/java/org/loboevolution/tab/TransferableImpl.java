package org.loboevolution.tab;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * <p>TransferableImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class TransferableImpl implements Transferable {

	/** {@inheritDoc} */
	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return false;
	}

}
