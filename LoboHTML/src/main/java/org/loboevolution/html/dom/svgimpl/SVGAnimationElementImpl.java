/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.svg.SVGAnimationElement;
import org.loboevolution.html.dom.svg.SVGElement;

import java.util.StringTokenizer;
import java.util.Vector;


/**
 * <p>SVGAnimationElementImpl class.</p>
 */
public abstract class SVGAnimationElementImpl extends SVGAnimationImpl implements SVGAnimationElement {

	public Vector times = null;
	public Vector vals = null;
	public Vector splines = null;

	private boolean active = false;
	protected boolean finished = false;
	private float startTime;
	private float endTime;

	/**
	 * <p>Constructor for SVGAnimationElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGAnimationElementImpl(final String name) {
		super(name);
	}

	public abstract Object getCurrentValue(short animtype);

	/** {@inheritDoc} */
	@Override
	public SVGElement getTargetElement() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public float getStartTime() {
		if (getAttribute("begin").equalsIgnoreCase("indefinite")) {
			if (active) { // beginElement has been called
				return startTime;
			} else {
				return Float.MAX_VALUE;
			}
		} else { // begin attribute should contain a time
			return getBeginTime();
		}
	}

	/** {@inheritDoc} */
	@Override
	public float getCurrentTime() {
		if (getAttribute("begin").equalsIgnoreCase("indefinite")) {
			return getOwnerSVGElement().getCurrentTime() - startTime;
		} else {
			return getOwnerSVGElement().getCurrentTime();
		}
	}

	/** {@inheritDoc} */
	@Override
	public float getSimpleDuration() {
		float duration = getDuration();
		if (duration < 0) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
					"Simple duration is not defined for this animation element");
		} else {
			return duration;
		}
	}

	// returns begin time in seconds
	float getBeginTime() {
		String beginTime = getAttribute("begin");
		if (!beginTime.equalsIgnoreCase("indefinite") && beginTime.length() > 0) {
			return getClockSecs(beginTime);
		} else {
			return 0;
		}
	}

	// returns the duration time in secs, will be -1 if indefinite
	float getDuration() {
		String duration = getAttribute("dur");
		if (duration.equalsIgnoreCase("indefinite") || duration.length() == 0) {
			return -1;
		} else {
			float clockSecs = getClockSecs(duration);
			if (clockSecs == 0) { // there was a syntax error
				return -1;
			} else {
				return clockSecs;
			}
		}
	}

	// returns the end time in secs, will be -1 if indefinite
	float getEndTime() {
		String endTime = getAttribute("end");
		if (endTime.equalsIgnoreCase("indefinite") || endTime.length() == 0) {
			return -1;
		} else {
			float clockSecs = getClockSecs(endTime);
			if (clockSecs == 0) { // there was a syntax error
				return -1;
			} else {
				return clockSecs;
			}
		}
	}

	protected float getNumRepeats(float duration) {
		String repeatCount = getAttribute("repeatCount");
		String repeatDur = getAttribute("repeatDur");
		if (repeatCount.length() > 0 || repeatDur.length() > 0) { // should
			// maybe
			// repeat
			if (!(repeatCount.equalsIgnoreCase("indefinite") || repeatDur.equalsIgnoreCase("indefinite"))) {
				if (repeatCount.length() > 0 && repeatDur.length() == 0) {
					return Float.parseFloat(repeatCount);
				} else if (repeatCount.length() == 0 && repeatDur.length() > 0) {
					return getClockSecs(repeatDur) / duration;
				} else { // take the min of both
					return Math.min(Float.parseFloat(repeatCount), getClockSecs(repeatDur) / duration);
				}
			}
		}
		return 1;
	}

	protected boolean getRepeatForever() {
		return getAttribute("repeatCount").equalsIgnoreCase("indefinite")
				|| getAttribute("repeatDur").equalsIgnoreCase("indefinite");
	}

	protected float checkStatus(float currentTime, float startTime, float duration, float numRepeats,
								boolean repeatForever) {

		if (currentTime < startTime) { // animation has not started yet
			active = false;
			return -1;

		} else if (currentTime >= startTime && (repeatForever || currentTime < startTime + duration * numRepeats)) {

			// animation is running
			active = true;
			int currentRepeat = (int) Math.floor((currentTime - startTime) / duration);
			return (currentTime - startTime - currentRepeat * duration) / duration;

		} else { // animation has finished
			active = false;
			finished = true;

			if (getAttribute("fill").equalsIgnoreCase("remove")) {
				return -1; // this will indicate to use the baseVal as the
				// animVal

			} else { // freeze

				// set currentTime to be the last active time
				currentTime = startTime + duration * numRepeats;
				int currentRepeat = (int) Math.floor((currentTime - startTime) / duration);
				if (currentRepeat == numRepeats) {
					currentRepeat--;
				}
				return (currentTime - startTime - currentRepeat * duration) / duration;
			}
		}
	}

	protected float getClockSecs(String clockVal) {

		try {
			if (clockVal.contains(":")) { // is either a full/partial clock
				// value

				StringTokenizer st = new StringTokenizer(clockVal, ":");
				int numTokens = st.countTokens();

				if (numTokens == 3) { // is a full clock value
					int hours = Integer.parseInt(st.nextToken());
					int minutes = Integer.parseInt(st.nextToken());
					float seconds = Float.parseFloat(st.nextToken());
					return hours * 3600 + minutes * 60 + seconds;

				} else if (numTokens == 2) { // is a partial clock value
					int minutes = Integer.parseInt(st.nextToken());
					float seconds = Float.parseFloat(st.nextToken());
					return minutes * 60 + seconds;

				} else {
					// something wrong
					System.out.println("Invalid clock value: " + clockVal + ", will use the default value 0");
					return 0; // shouldn't get here
				}

			} else { // is a timecount value

				if (clockVal.contains("h")) {
					// is an hour value
					float hour = Float.parseFloat(clockVal.substring(0, clockVal.indexOf("h")));
					return hour * 3600;

				} else if (clockVal.contains("min")) {
					float min = Float.parseFloat(clockVal.substring(0, clockVal.indexOf("min")));
					return min * 60;

				} else if (clockVal.contains("ms")) {
					float ms = Float.parseFloat(clockVal.substring(0, clockVal.indexOf("ms")));
					return (float) (ms / 1000.0);

				} else if (clockVal.contains("s")) {
					return Float.parseFloat(clockVal.substring(0, clockVal.indexOf("s")));

				} else { // must be seconds with no metric specified
					return Float.parseFloat(clockVal);
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("cannot decode time: " + clockVal);
			return 0;
		}
	}

	protected void setupTimeValueVectors(String calcMode, String values) {
		times = new Vector();
		vals = new Vector();
		String keyTimes = getAttribute("keyTimes");

		if (Strings.isCssBlank(keyTimes)) {

			if (calcMode.equalsIgnoreCase("paced")) {
				StringTokenizer stVals = new StringTokenizer(values, ";");
				int numVals = stVals.countTokens();
				float currTime = 0;
				int currentTokenCount = 0;
				while (stVals.hasMoreTokens()) {
					if (currentTokenCount == 0 || currentTokenCount == numVals - 1) {
						times.addElement(currTime);
						currTime = 1;
						vals.addElement(stVals.nextToken());
					} else {
						stVals.nextToken();
					}
					currentTokenCount++;
				}
			} else {
				StringTokenizer stVals = new StringTokenizer(values, ";");
				int numVals = stVals.countTokens();
				float timeInc = (float) (1.0 / (numVals - 1));
				float currTime = 0;
				while (stVals.hasMoreTokens()) {
					times.addElement(currTime);
					currTime += timeInc;
					vals.addElement(stVals.nextToken());
				}
			}

		} else { // use the keyTimes attribute
			StringTokenizer stTimes = new StringTokenizer(keyTimes, ";");
			StringTokenizer stVals = new StringTokenizer(values, ";");
			while (stTimes.hasMoreTokens() && stVals.hasMoreTokens()) {
				times.addElement(Float.parseFloat(stTimes.nextToken()));
				vals.addElement(stVals.nextToken());
			}
		}

		if (calcMode.equals("spline") && getAttribute("keySplines").length() > 0) {
			splines = new Vector();
			String keySplines = getAttribute("keySplines");
			StringTokenizer st = new StringTokenizer(keySplines, ";");
			while (st.hasMoreTokens()) {
				String spline = st.nextToken();
				StringTokenizer st2 = new StringTokenizer(spline, ", ");
				if (st2.countTokens() == 4) {
					float x1 = Float.parseFloat(st2.nextToken());
					float y1 = Float.parseFloat(st2.nextToken());
					float x2 = Float.parseFloat(st2.nextToken());
					float y2 = Float.parseFloat(st2.nextToken());
					SVGPathSegCurvetoCubicAbsImpl bezierSeg = new SVGPathSegCurvetoCubicAbsImpl(1, 1, x1, y1, x2, y2);
					splines.addElement(bezierSeg);
				}
			}
		}
	}

	protected float getSplineValueAt(int splineIndex, float percent) {
		SVGPathSegCurvetoCubicAbsImpl bezierSeg = (SVGPathSegCurvetoCubicAbsImpl) splines.elementAt(splineIndex);
		return bezierSeg.getYAt(percent, new SVGPointImpl(0, 0));
	}
}
