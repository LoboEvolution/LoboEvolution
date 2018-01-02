/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jun 18, 2005
 */
package org.loboevolution.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.store.StorageManager;

/**
 * Class in charge of allowing mutiple browser launches to share a JVM.
 */
public class ReuseManager {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ReuseManager.class);

	/** The Constant instance. */
	private static final ReuseManager instance = new ReuseManager();
	
	/** The Constant PORT_FILE. */
	private static final String PORT_FILE = "port.dat";
	
	/**
	 * Instantiates a new reuse manager.
	 */
	private ReuseManager() {
		super();
	}

	

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
	public static ReuseManager getInstance() throws Exception {
		return instance;
	}

	/**
	 * Shutdown.
	 */
	public void shutdown() {
		try {
			File appHome = StorageManager.getInstance().getAppHome();
			File portFile = new File(appHome, PORT_FILE);
			portFile.delete();
		} catch (IOException ioe) {
			// ignore
		}
	}

	/**
	 * May launch in this VM or a second one.
	 *
	 * @param args
	 *            the args
	 * @throws Exception
	 *             the exception
	 */
	public void launch(String[] args) throws Exception {
		boolean launched = false;
		// long time1 = System.currentTimeMillis();
		try {
			// Bind host for reuse server is 127.0.0.1, and it can
			// only be accessed locally.
			InetAddress bindHost = InetAddress.getByAddress(new byte[] { (byte) 127, (byte) 0, (byte) 0, (byte) 1 });
			File appHome = StorageManager.getInstance().getAppHome();
			File portFile = new File(appHome, PORT_FILE);
			OUTER: for (int tries = 0; tries < 5; tries++) {
				// Look for running VM
				int port = -1;
				try {
					InputStream in = new FileInputStream(portFile);
					DataInputStream din = new DataInputStream(in);
					try {
						port = din.readInt();
					} finally {
						din.close();
						in.close();
					}
				} catch (EOFException eofe) {
					logger.log(Level.ERROR, eofe);
					portFile.delete();
				} catch (FileNotFoundException fnfe) {
					// Likely not running
				}
				if (port != -1) {
					Socket s = new Socket(bindHost, port);
					try {
						s.setTcpNoDelay(true);
						OutputStream out = s.getOutputStream();
						try {
							OutputStreamWriter writer = new OutputStreamWriter(out);
							boolean hadPath = false;
							for (String arg : args) {
								String url = arg;
								if (!url.startsWith("-")) {
									hadPath = true;
									writer.write("LAUNCH " + arg);
									writer.write("\r\n");
								}
							}
							// TODO: Hmm, should check
							// for a response. Some other
							// program could in theory
							// be listening on that port?
							if (!hadPath) {
								writer.write("LAUNCH_BLANK");
								writer.write("\r\n");
							}
							writer.flush();
							launched = true;
						} finally {
							out.close();
						}
					} catch (IOException ioe) {
						// VM must have died. We don't have logging at this
						// point.
						PlatformInit.getInstance().initLogging();
						LogManager.getLogger(ReuseManager.class).log(Level.WARN,
								"Another instance of the application must have been running but was not shut down properly.",
								ioe);
						portFile.delete();
					} finally {
						s.close();
					}
				}
				if (launched) {
					break OUTER;
				}
				ReuseServer server = new ReuseServer();
				port = server.start(bindHost);
				if (!portFile.createNewFile()) {
					// Another app beat us to it.
					server.stop();
					continue OUTER;
				}
				OutputStream out = new FileOutputStream(portFile);
				DataOutputStream dout = new DataOutputStream(out);
				try {
					dout.writeInt(port);
					dout.flush();
				} finally {
					dout.close();
					out.close();
				}
				break OUTER;
			}
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
		if (!launched) {
			PlatformInit entry = PlatformInit.getInstance();
			entry.initLogging();
			entry.init(true);
			entry.start(args);
		}
	}
}
