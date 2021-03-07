/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.js.geolocation;

import org.json.JSONObject;
import org.loboevolution.net.HttpNetwork;

/**
 * <p>IPAddressBasedGeoAcquirer class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class IPAddressBasedGeoAcquirer {
	
	/**
	 * <p>acquireLocation.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.js.geolocation.Position} object.
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
