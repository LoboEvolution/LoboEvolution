/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import org.json.JSONObject;
import org.loboevolution.net.HttpNetwork;

/**
 * <p>IPAddressBasedGeoAcquirer class.</p>
 *
 *
 *
 */
public class IPAddressBasedGeoAcquirer {
	
	/**
	 * <p>acquireLocation.</p>
	 *
	 * @return a {@link org.loboevolution.html.js.geolocation.Position} object.
	 * @throws java.lang.Exception if any.
	 */
	protected Position acquireLocation() throws Exception {
		final String source = HttpNetwork.getSource("https://freegeoip.app/json/");
		final JSONObject children = new JSONObject(source);
		final double latitude = Double.parseDouble(children.optString("latitude"));
		final double longitude = Double.parseDouble(children.optString("longitude"));
		final Coordinates coords = new Coordinates(latitude, longitude, (double) 0, 0, (double) 0, null, (double) 0);
		return new Position(coords, -1);
	}
}
