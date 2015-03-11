package org.lobobrowser.util.io;

import java.io.IOException;
import java.io.Reader;


/**
 * The Class EmptyReader.
 */
public class EmptyReader extends Reader {
	
	/* (non-Javadoc)
	 * @see java.io.Reader#close()
	 */
	public void close() throws IOException {
	}

	/* (non-Javadoc)
	 * @see java.io.Reader#read(char[], int, int)
	 */
	public int read(char[] cbuf, int off, int len) throws IOException {
		return 0;
	}
}
