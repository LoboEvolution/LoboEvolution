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

package org.loboevolution.svg.smil;

import org.loboevolution.html.dom.smil.Time;
import org.loboevolution.html.node.Element;
import org.loboevolution.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * <p>TimeImpl class.</p>
 */
public class TimeImpl implements Time {
	
	private final short mTimeType;
	
	private boolean mResolved;
	
	private double mResolvedOffset;

	/**
	 * <p>Constructor for TimeImpl.</p>
	 * @param time a {@link java.lang.String} object.
	 */
	public TimeImpl(final String time) {
		String timeValue = time;
		if (timeValue == null) {
			mTimeType = SMIL_TIME_INDEFINITE;
			mResolved = true;
		} else if ("indefinite".equals(timeValue)) {
			mTimeType = SMIL_TIME_INDEFINITE;
			mResolved = true;
		} else if (timeValue.startsWith("wallclock(")) {
			timeValue = timeValue.replace("wallclock(", "").replace(")", "").trim();
			final DateUtil du = new DateUtil();
			final Date d = du.determineDateFormat(timeValue, Locale.US);
			final long now = Calendar.getInstance().getTime().getTime();
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
	public void setOffset(final double offset) {
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
	public void setBaseElement(final Element baseElement) {
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
	public void setBaseBegin(final boolean baseBegin) {
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
	public void setEvent(final String event) {
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
	public void setMarker(final String marker) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * <p>getClockMilliSecs.</p>
	 *
	 * @param clockVal a {@link java.lang.String} object.
	 * @return a float.
	 */
	public static float getClockMilliSecs(final String clockVal) {
		try {
			if (clockVal.indexOf(':') != -1) {
				final StringTokenizer st = new StringTokenizer(clockVal, ":");
				final int numTokens = st.countTokens();
				if (numTokens == 3) { // is a full clock value
					final int hours = Integer.parseInt(st.nextToken());
					final int minutes = Integer.parseInt(st.nextToken());
					final float seconds = Float.parseFloat(st.nextToken());
					return (hours * 3600 + minutes * 60 + seconds) * 1000;
				} else if (numTokens == 2) { // is a partial clock value
					final int minutes = Integer.parseInt(st.nextToken());
					final float seconds = Float.parseFloat(st.nextToken());
					return (minutes * 60 + seconds) * 1000;
				} else {
					return 0;
				}
			} else {
				if (clockVal.indexOf('h') != -1) {
					final float hour = Float.parseFloat(clockVal.substring(0, clockVal.indexOf('h')));
					return (hour * 3600) * 1000;
				} else if (clockVal.contains("min")) {
					final float min = Float.parseFloat(clockVal.substring(0, clockVal.indexOf("min")));
					return (min * 60) * 1000;
				} else if (clockVal.contains("ms")) {
					return Float.parseFloat(clockVal.substring(0, clockVal.indexOf("ms")));
				} else if (clockVal.indexOf('s') != -1) {
					final float secs = Float.parseFloat(clockVal.substring(0, clockVal.indexOf('s')));
					return secs * 1000;
				} else {
					final float secs = Float.parseFloat(clockVal);
					return secs * 1000;
				}
			}
		} catch (final Exception e) {
			return 0;
		}
	}
}
