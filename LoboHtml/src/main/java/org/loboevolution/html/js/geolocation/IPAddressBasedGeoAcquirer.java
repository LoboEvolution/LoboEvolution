package org.loboevolution.html.js.geolocation;

import org.json.JSONObject;
import org.loboevolution.net.HttpNetwork;

public class IPAddressBasedGeoAcquirer {
	
	protected Position acquireLocation() throws Exception {
		final String source = HttpNetwork.getSource("https://freegeoip.app/json/");
		final JSONObject children = new JSONObject(source);
		final double latitude = Double.parseDouble(children.optString("latitude"));
		final double longitude = Double.parseDouble(children.optString("longitude"));
		final Coordinates coords = new Coordinates(latitude, longitude, new Double(0), 0, new Double(0), null, new Double(0));
		return new Position(coords, -1);
	}
}
