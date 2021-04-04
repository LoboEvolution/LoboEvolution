/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.svgimpl;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import org.loboevolution.html.dom.smil.Time;
import org.loboevolution.util.DateUtil;
import org.loboevolution.html.node.Element;

/**
 * <p>TimeImpl class.</p>
 *
 *
 *
 */
public class TimeImpl implements Time {
	
	private final short mTimeType;
	
	private boolean mResolved;
	
	private double mResolvedOffset;

	/**
	 * <p>Constructor for TimeImpl.</p>
	 *
	 * @param timeValue a {@link java.lang.String} object.
	 */
	public TimeImpl(String timeValue) {
		if (timeValue == null) {
			mTimeType = SMIL_TIME_INDEFINITE;
			mResolved = true;
		} else if ("indefinite".equals(timeValue)) {
			mTimeType = SMIL_TIME_INDEFINITE;
			mResolved = true;
		} else if (timeValue.startsWith("wallclock(")) {
			timeValue = timeValue.replace("wallclock(", "").replace(")", "").trim();
			DateUtil du = new DateUtil();
			Date d = du.determineDateFormat(timeValue, Locale.US);
			long now = Calendar.getInstance().getTime().getTime();
			mResolvedOffset = d.getTime() - now;
			mTimeType = SMIL_TIME_WALLCLOCK;
			mResolved = true;
		} else if (timeValue.startsWith("accesskey(")) {
			mTimeType = SMIL_TIME_ACCESSKEY;
		} else if (timeValue.endsWith("begin")) {
			mTimeType = SMIL_TIME_SYNC_BASED;
		} else if (timeValue.endsWith("end")) {
			mTimeType = SMIL_TIME_SYNC_BASED;
		} else if ((timeValue.startsWith("marker("))) {
			mTimeType = SMIL_TIME_MEDIA_MARKER;
		} else if ((timeValue.startsWith("repeat("))) {
			mTimeType = SMIL_TIME_REPEAT;
		} else{
			mResolvedOffset = getClockMilliSecs(timeValue);
            mResolved = true;
            mTimeType = SMIL_TIME_OFFSET;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean getResolved() {
		return this.mResolved;
	}

	/** {@inheritDoc} */
	@Override
	public double getResolvedOffset() {
		return this.mResolvedOffset;
	}

	/** {@inheritDoc} */
	@Override
	public short getTimeType() {
		return this.mTimeType;
	}

	/** {@inheritDoc} */
	@Override
	public double getOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void setOffset(double offset) {
		this.mResolvedOffset = offset;

	}

	/** {@inheritDoc} */
	@Override
	public Element getBaseElement() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setBaseElement(Element baseElement) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public boolean getBaseBegin() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setBaseBegin(boolean baseBegin) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public String getEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setEvent(String event) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public String getMarker() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setMarker(String marker) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * <p>getClockMilliSecs.</p>
	 *
	 * @param clockVal a {@link java.lang.String} object.
	 * @return a float.
	 */
	protected static float getClockMilliSecs(String clockVal) {
		try {
			if (clockVal.indexOf(':') != -1) {
				StringTokenizer st = new StringTokenizer(clockVal, ":");
				int numTokens = st.countTokens();
				if (numTokens == 3) { // is a full clock value
					int hours = Integer.parseInt(st.nextToken());
					int minutes = Integer.parseInt(st.nextToken());
					float seconds = Float.parseFloat(st.nextToken());
					return (hours * 3600 + minutes * 60 + seconds) * 1000;
				} else if (numTokens == 2) { // is a partial clock value
					int minutes = Integer.parseInt(st.nextToken());
					float seconds = Float.parseFloat(st.nextToken());
					return (minutes * 60 + seconds) * 1000;
				} else {
					return 0;
				}
			} else {
				if (clockVal.indexOf('h') != -1) {
					float hour = Float.parseFloat(clockVal.substring(0, clockVal.indexOf('h')));
					return (hour * 3600) * 1000;
				} else if (clockVal.indexOf("min") != -1) {
					float min = Float.parseFloat(clockVal.substring(0, clockVal.indexOf("min")));
					return (min * 60) * 1000;
				} else if (clockVal.indexOf("ms") != -1) {
					return Float.parseFloat(clockVal.substring(0, clockVal.indexOf("ms")));
				} else if (clockVal.indexOf('s') != -1) {
					float secs = Float.parseFloat(clockVal.substring(0, clockVal.indexOf('s')));
					return secs * 1000;
				} else {
					float secs = Float.parseFloat(clockVal);
					return secs * 1000;
				}
			}
		} catch (Exception e) {
			return 0;
		}
	}
}
