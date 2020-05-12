package org.loboevolution.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>Files class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class Files {

	/**
	 * <p>getResourceAsFile.</p>
	 *
	 * @param in a {@link java.io.InputStream} object.
	 * @return a {@link java.io.File} object.
	 */
	public static File getResourceAsFile(InputStream in) {
		try {
			if (in == null) {
				return null;
			}

			final File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
			tempFile.deleteOnExit();

			try (FileOutputStream out = new FileOutputStream(tempFile)) {
				// copy stream
				final byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = in.read(buffer)) != -1) {
					out.write(buffer, 0, bytesRead);
				}
			}
			return tempFile;
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
