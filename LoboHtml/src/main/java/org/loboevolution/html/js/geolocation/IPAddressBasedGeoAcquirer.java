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
	 * @return a {@link org.loboevolution.html.js.geolocation.Position} object.
	 * @throws java.lang.Exception if any.
	 */
	protected Position acquireLocation() throws Exception {
		final String source = HttpNetwork.getSource("https://freegeoip.app/json/");
		final JSONObject children = new JSONObject(source);
		final double latitude = Double.parseDouble(children.optString("latitude"));
		final double longitude = Double.parseDouble(children.optString("longitude"));
		final Coordinates coords = new Coordinates(latitude, longitude, Double.valueOf(0), 0, Double.valueOf(0), null, Double.valueOf(0));
		return new Position(coords, -1);
	}
}
