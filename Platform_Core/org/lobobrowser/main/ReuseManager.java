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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.store.StorageManager;


/**
 * Class in charge of allowing mutiple browser launches to share a JVM.
 */
public class ReuseManager {
	
	/**
	 * Instantiates a new reuse manager.
	 */
	private ReuseManager() {
		super();
	}

	/** The Constant instance. */
	private static final ReuseManager instance = new ReuseManager();

	/**
	 * Gets the single instance of ReuseManager.
	 *
	 * @return single instance of ReuseManager
	 * @throws Exception the exception
	 */
	public static ReuseManager getInstance() throws Exception {
		return instance;
	}

	/** The Constant PORT_FILE. */
	private static final String PORT_FILE = "port.dat";

	/**
	 * Shutdown.
	 */
	public void shutdown() {
		try {
			java.io.File appHome = StorageManager.getInstance().getAppHome();
			java.io.File portFile = new File(appHome, PORT_FILE);
			portFile.delete();
		} catch (IOException ioe) {
			// ignore
		}
	}

	/**
	 * May launch in this VM or a second one.
	 *
	 * @param args the args
	 * @throws Exception the exception
	 */
	public void launch(String[] args) throws Exception {
		boolean launched = false;
		// long time1 = System.currentTimeMillis();
		try {
			// Bind host for reuse server is 127.0.0.1, and it can
			// only be accessed locally.
			InetAddress bindHost = InetAddress.getByAddress(new byte[] {
					(byte) 127, (byte) 0, (byte) 0, (byte) 1 });
			java.io.File appHome = StorageManager.getInstance().getAppHome();
			java.io.File portFile = new File(appHome, PORT_FILE);
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
					eofe.printStackTrace(System.err);
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
							OutputStreamWriter writer = new OutputStreamWriter(
									out);
							boolean hadPath = false;
							for (int i = 0; i < args.length; i++) {
								String url = args[i];
								if (!url.startsWith("-")) {
									hadPath = true;
									writer.write("LAUNCH " + args[i]);
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
						PlatformInit.getInstance().initLogging(false);
						Logger.getLogger(ReuseManager.class.getName())
								.log(Level.WARNING,
										"Another instance of the application must have been running but was not shut down properly.",
										ioe);
						portFile.delete();
					}finally{
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
		}finally{
			
		}
		if (!launched) {
			PlatformInit entry = PlatformInit.getInstance();
			boolean debugOn = false;
			for (int i = 0; i < args.length; i++) {
				String url = args[i];
				if (url.equals("-debug")) {
					debugOn = true;
				}
			}
			entry.initLogging(debugOn);
			entry.init(true, !debugOn);
			entry.start(args);
		}
	}
}
