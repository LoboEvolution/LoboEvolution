/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.html.js.geolocation;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.js.WindowImpl;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.js.Window;
import org.mozilla.javascript.Function;

import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * The Geolocation class provides Java implementation of the
 * "Geolocation Interface" as detailed out in the W3C Specifications (
 * <a href="http://www.w3.org/TR/geolocation-API/#geolocation_interface">
 * http://www.w3.org/TR/geolocation-API/#geolocation_interface</a>).
 * </p>
 *
 * <p>
 * <b>Note: This class must not have any sub-classes to ensure W3C
 * Specifications are being strictly followed by the system or application that
 * uses this geolocation package.</b>
 * </p>
 */
@Slf4j
public class Geolocation extends AbstractScriptableDelegate {

	private final WindowImpl window;

	/**
	 * <p>Constructor for Geolocation.</p>
	 *
	 * @param window a {@link Window} object.
	 */
	public Geolocation(final WindowImpl window) {
		this.window = window;
	}

	/**
	 * <p>getCurrentPosition.</p>
	 *
	 * @param success a {@link org.mozilla.javascript.Function} object.
	 * @throws java.lang.Exception if any.
	 */
	public void getCurrentPosition(final Function success) throws Exception {
		final IPAddressBasedGeoAcquirer ip = new IPAddressBasedGeoAcquirer();
		final Position acquireLocation = ip.acquireLocation();
		final NodeImpl node = (NodeImpl) window.getDocumentNode();
		Executor.executeFunction(node, success, new Object[] { acquireLocation }, window.getContextFactory());
	}

	/**
	 * <p>getCurrentPosition.</p>
	 *
	 * @param success a {@link org.mozilla.javascript.Function} object.
	 * @param error a {@link org.mozilla.javascript.Function} object.
	 */
	public void getCurrentPosition(final Function success, final Function error) {
		try {
			getCurrentPosition(success);
		} catch (final Exception e) {
			geoError(error, e);
		}
	}

	/**
	 * <p>watchPosition.</p>
	 *
	 * @param success a {@link org.mozilla.javascript.Function} object.
	 * @return a long.
	 */
	public long watchPosition(final Function success) {
		final long watchId = System.currentTimeMillis();
		final Thread t = new Thread(() -> {
			while (true) {
				try {
					getCurrentPosition(success);
					Thread.sleep(500);
				} catch (final Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		});
		t.start();
		return watchId;
	}

	/**
	 * <p>watchPosition.</p>
	 *
	 * @param success a {@link org.mozilla.javascript.Function} object.
	 * @param error a {@link org.mozilla.javascript.Function} object.
	 * @return a long.
	 */
	public long watchPosition(final Function success, final Function error) {
		final long watchId = System.currentTimeMillis();
		final Thread t = new Thread(() -> {
			while (true) {
				try {
					getCurrentPosition(success, error);
					Thread.sleep(500);
				} catch (final Exception e) {
					geoError(error, e);
					break;
				}
			}
		});
		t.start();
		return watchId;
	}

	private void geoError(final Function error, final Exception e) {
		final NodeImpl node = (NodeImpl) window.getDocumentNode();
		PositionError pError = null;
		if (e instanceof UnknownHostException) {
			pError = new PositionError(PositionError.POSITION_UNAVAILABLE);
		} else if (e instanceof TimeoutException) {
			pError = new PositionError(PositionError.TIMEOUT);
		}
		Executor.executeFunction(node, error, new Object[] { pError }, window.getContextFactory());
	}
}
