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
 * Created on Jun 7, 2005
 */
package org.loboevolution.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class ID.
 *
 * @author J. H. S.
 */
public class ID {
	/** The Constant RANDOM1. */
	private static final Random RANDOM1;
	/** The Constant RANDOM2. */
	private static final Random RANDOM2;
	/** The Constant RANDOM3. */
	private static final Random RANDOM3;
	/** The Constant globalProcessID. */
	private static final long globalProcessID;
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ID.class);

	static {
		long time = System.currentTimeMillis();
		long nanoTime = System.nanoTime();
		long freeMemory = Runtime.getRuntime().freeMemory();
		long addressHashCode;
		try {
			InetAddress inetAddress;
			inetAddress = InetAddress.getLocalHost();
			addressHashCode = inetAddress.getHostName().hashCode() ^ inetAddress.getHostAddress().hashCode();
		} catch (Exception err) {
			logger.error("Unable to get local host information.", err);
			addressHashCode = ID.class.hashCode();
		}
		globalProcessID = time ^ nanoTime ^ freeMemory ^ addressHashCode;
		RANDOM1 = new Random(time);
		RANDOM2 = new Random(nanoTime);
		RANDOM3 = new Random(addressHashCode ^ freeMemory);
	}

	/**
	 * Instantiates a new id.
	 */
	private ID() {
	}

	/**
	 * Generate long.
	 *
	 * @return the long
	 */
	public static long generateLong() {
		return Math.abs(RANDOM1.nextLong() ^ RANDOM2.nextLong() ^ RANDOM3.nextLong());
	}

	/**
	 * Generate int.
	 *
	 * @return the int
	 */
	public static int generateInt() {
		return (int) generateLong();
	}

	/**
	 * Gets the m d5 bytes.
	 *
	 * @param content
	 *            the content
	 * @return the m d5 bytes
	 */
	public static byte[] getMD5Bytes(String content) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			return digest.digest(content.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		} catch (UnsupportedEncodingException uee) {
			throw new IllegalStateException(uee);
		}
	}

	/**
	 * Gets the hex string.
	 *
	 * @param bytes
	 *            the bytes
	 * @return the hex string
	 */
	public static String getHexString(byte[] bytes) {
		// This method cannot change even if it's wrong.
		BigInteger bigInteger = BigInteger.ZERO;
		int shift = 0;
		for (int i = bytes.length; --i >= 0;) {
			BigInteger contrib = BigInteger.valueOf(bytes[i] & 0xFF);
			contrib = contrib.shiftLeft(shift);
			bigInteger = bigInteger.add(contrib);
			shift += 8;
		}
		return bigInteger.toString(16).toUpperCase();
	}

	/**
	 * Gets the Constant globalProcessID.
	 *
	 * @return the Constant globalProcessID
	 */
	public static long getGlobalProcessID() {
		return globalProcessID;
	}

	/**
	 * Random.
	 *
	 * @param min
	 *            the min
	 * @param max
	 *            the max
	 * @return the int
	 */
	public static int random(int min, int max) {
		if (max <= min) {
			return min;
		}
		return Math.abs(RANDOM1.nextInt()) % (max - min) + min;
	}
}
