/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jun 18, 2005
 */
package org.lobobrowser.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


/**
 * The local-bound server that allows the browser JVM to be reused.
 */
public class ReuseServer implements Runnable {
	
	/**
	 * Instantiates a new reuse server.
	 */
	public ReuseServer() {
		Thread t = new Thread(this, "ReuseServer");
		t.setDaemon(true);
		t.start();
	}

	/** The Constant MIN_PORT. */
	private static final int MIN_PORT = 55000;
	
	/** The Constant TOP_PORT. */
	private static final int TOP_PORT = 65000;
	
	/** The Constant RAND. */
	private static final Random RAND = new Random(System.currentTimeMillis());

	/**
	 * Gets the random port.
	 *
	 * @return the random port
	 */
	private static int getRandomPort() {
		return Math.abs(RAND.nextInt()) % (TOP_PORT - MIN_PORT) + MIN_PORT;
	}

	/** The server socket. */
	private ServerSocket serverSocket;

	/**
	 * Start.
	 *
	 * @param bindAddr the bind addr
	 * @return the int
	 */
	public int start(InetAddress bindAddr) {
		// Should be called with bindAddr=127.0.0.1 only.
		synchronized (this) {
			if (this.serverSocket != null) {
				throw new IllegalStateException("Already started");
			}
			for (int tries = 0; tries < 100; tries++) {
				int rport = getRandomPort();
				try {
					ServerSocket ss = new ServerSocket(rport, 100, bindAddr);
					this.serverSocket = ss;
					this.notify();
					return rport;
				} catch (IOException ioe) {
					ioe.printStackTrace(System.err);
				}
			}
		}
		throw new IllegalStateException(
				"Unable to bind reuse server after many tries.");
	}

	/**
	 * Stop.
	 */
	public void stop() {
		synchronized (this) {
			if (this.serverSocket != null) {
				try {
					this.serverSocket.close();
				} catch (IOException ioe) {
					// ignore
				}
				this.serverSocket = null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		for (;;) {
			try {
				ServerSocket ss;
				synchronized (this) {
					while (this.serverSocket == null) {
						this.wait();
					}
					ss = this.serverSocket;
				}
				Socket s = ss.accept();
				s.setSoTimeout(10000);
				s.setTcpNoDelay(true);
				InputStream in = s.getInputStream();
				try {
					Reader reader = new InputStreamReader(in);
					BufferedReader br = new BufferedReader(reader);
					String line;
					while ((line = br.readLine()) != null) {
						int blankIdx = line.indexOf(' ');
						String command = blankIdx == -1 ? line : line
								.substring(0, blankIdx).trim();
						if ("LAUNCH".equals(command)) {
							if (blankIdx == -1) {
								PlatformInit.getInstance().launch();
							} else {
								String path = line.substring(blankIdx + 1)
										.trim();
								PlatformInit.getInstance().launch(path);
							}
						} else if ("LAUNCH_BLANK".equals(command)) {
							PlatformInit.getInstance().launch();
						}
					}
				} finally {
					in.close();
				}
			} catch (Throwable t) {
				t.printStackTrace(System.err);
			}
		}
	}
}
