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

package org.lobobrowser.html.dom.svgimpl;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.lobo.util.DateUtil;
import org.lobobrowser.html.dom.smil.Time;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

public class TimeImpl implements Time {
	
	private short mTimeType;
	
	private boolean mResolved;
	
	private double mResolvedOffset;

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
			mResolvedOffset = SVGUtility.getClockMilliSecs(timeValue);
            mResolved = true;
            mTimeType = SMIL_TIME_OFFSET;
		}
	}

	@Override
	public boolean getResolved() {
		return this.mResolved;
	}

	@Override
	public double getResolvedOffset() {
		return this.mResolvedOffset;
	}

	@Override
	public short getTimeType() {
		return this.mTimeType;
	}

	@Override
	public double getOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setOffset(double offset) throws DOMException {
		this.mResolvedOffset = offset;

	}

	@Override
	public Element getBaseElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBaseElement(Element baseElement) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getBaseBegin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBaseBegin(boolean baseBegin) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEvent(String event) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMarker() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMarker(String marker) throws DOMException {
		// TODO Auto-generated method stub

	}

}
