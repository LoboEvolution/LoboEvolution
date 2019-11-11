package org.loboevolution.html.js.geolocation;

import org.loboevolution.js.AbstractScriptableDelegate;

/**
 * <p>The <code>Position</code> class provides Java implementation of the "Position Interface" as 
 * detailed out in the W3C Specifications (<a http://www.w3.org/TR/geolocation-API/#position">
 * http://www.w3.org/TR/geolocation-API/#position</a>).</p>
 * 
 * <p><b>Note: This class must not have any sub-classes to ensure W3C Specifications are being 
 * strictly followed by the system or application that uses this geolocation package.</b></p>
 */
public class Position extends AbstractScriptableDelegate {
	
	/*
	 * The position coordinates.
	 */
	private Coordinates coords;
	
	/*
	 * The time (in milliseconds since January 1, 1970 GMT) when this position was found (or calculated). 
	 */
	private long timestamp;
	
	/**
	 * Constructs a <code>Position</code> object.
	 * 
	 * @param coords	the coordinates of the position.
	 * @param timestamp	the time (in milliseconds since January 1, 1970 GMT) when this position was found 
	 * (or calculated).
	 */
	public Position(final Coordinates coords, final long timestamp) {
		this.coords = coords;
		this.timestamp = timestamp;
	}

	/**
	 * Returns the position coordinates.
	 * 
	 * @return	the position coordinates.
	 */
	public Coordinates getCoords() {
		return coords;
	}

	/**
	 * Returns the time (in milliseconds since January 1, 1970 GMT) when this position was found (or 
	 * calculated).
	 * 
	 * @return	the time (in milliseconds since January 1, 1970 GMT) when this position was found (or 
	 * calculated).
	 */
	public long getTimestamp() {
		return timestamp;
	}
}
