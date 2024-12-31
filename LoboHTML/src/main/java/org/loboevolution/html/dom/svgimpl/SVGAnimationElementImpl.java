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

package org.loboevolution.html.dom.svgimpl;

import lombok.extern.slf4j.Slf4j;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.svg.SVGAnimationElement;
import org.loboevolution.html.dom.svg.SVGElement;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.List;

/**
 * <p>SVGAnimationElementImpl class.</p>
 */
@Slf4j
public abstract class SVGAnimationElementImpl extends SVGAnimationImpl implements SVGAnimationElement {

	public List<Float> times = null;
	public List<String> vals = null;
	public List<SVGPathSegCurvetoCubicAbsImpl> splines = null;

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

	public abstract Object getCurrentValue(final short animtype);

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
		final float duration = getDuration();
		if (duration < 0) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
					"Simple duration is not defined for this animation element");
		} else {
			return duration;
		}
	}

	// returns begin time in seconds
	float getBeginTime() {
		final String beginTime = getAttribute("begin");
		if (!beginTime.equalsIgnoreCase("indefinite") && !beginTime.isEmpty()) {
			return getClockSecs(beginTime);
		} else {
			return 0;
		}
	}

	// returns the duration time in secs, will be -1 if indefinite
	float getDuration() {
		final String duration = getAttribute("dur");
		if (duration.equalsIgnoreCase("indefinite") || duration.isEmpty()) {
			return -1;
		} else {
			final float clockSecs = getClockSecs(duration);
			if (clockSecs == 0) { // there was a syntax error
				return -1;
			} else {
				return clockSecs;
			}
		}
	}

	// returns the end time in secs, will be -1 if indefinite
	float getEndTime() {
		final String endTime = getAttribute("end");
		if (endTime.equalsIgnoreCase("indefinite") || endTime.isEmpty()) {
			return -1;
		} else {
			final float clockSecs = getClockSecs(endTime);
			if (clockSecs == 0) { // there was a syntax error
				return -1;
			} else {
				return clockSecs;
			}
		}
	}

	protected float getNumRepeats(final float duration) {
		final String repeatCount = getAttribute("repeatCount");
		final String repeatDur = getAttribute("repeatDur");
		if (!repeatCount.isEmpty() || !repeatDur.isEmpty()) { // should
			// maybe
			// repeat
			if (!(repeatCount.equalsIgnoreCase("indefinite") || repeatDur.equalsIgnoreCase("indefinite"))) {
				if (!repeatCount.isEmpty() && repeatDur.isEmpty()) {
					return Float.parseFloat(repeatCount);
				} else if (repeatCount.isEmpty() && !repeatDur.isEmpty()) {
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

	protected float checkStatus(final float inTime, final float startTime, final float duration, final float numRepeats,
                                final boolean repeatForever) {

		float currentTime = inTime;
		if (currentTime < startTime) { // animation has not started yet
			active = false;
			return -1;

		} else if (currentTime >= startTime && (repeatForever || currentTime < startTime + duration * numRepeats)) {

			// animation is running
			active = true;
			final int currentRepeat = (int) Math.floor((currentTime - startTime) / duration);
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

	protected float getClockSecs(final String clockVal) {

		try {
			if (clockVal.contains(":")) { // is either a full/partial clock
				// value

				final StringTokenizer st = new StringTokenizer(clockVal, ":");
				final int numTokens = st.countTokens();

				if (numTokens == 3) { // is a full clock value
					final int hours = Integer.parseInt(st.nextToken());
					final int minutes = Integer.parseInt(st.nextToken());
					final float seconds = Float.parseFloat(st.nextToken());
					return hours * 3600 + minutes * 60 + seconds;

				} else if (numTokens == 2) { // is a partial clock value
					final int minutes = Integer.parseInt(st.nextToken());
					final float seconds = Float.parseFloat(st.nextToken());
					return minutes * 60 + seconds;

				} else {
					// something wrong
					log.info("Invalid clock value: {}, will use the default value 0", clockVal);
					return 0; // shouldn't get here
				}

			} else { // is a timecount value

				if (clockVal.contains("h")) {
					// is an hour value
					final float hour = Float.parseFloat(clockVal.substring(0, clockVal.indexOf("h")));
					return hour * 3600;

				} else if (clockVal.contains("min")) {
					final float min = Float.parseFloat(clockVal.substring(0, clockVal.indexOf("min")));
					return min * 60;

				} else if (clockVal.contains("ms")) {
					final float ms = Float.parseFloat(clockVal.substring(0, clockVal.indexOf("ms")));
					return (float) (ms / 1000.0);

				} else if (clockVal.contains("s")) {
					return Float.parseFloat(clockVal.substring(0, clockVal.indexOf("s")));

				} else { // must be seconds with no metric specified
					return Float.parseFloat(clockVal);
				}
			}
		} catch (final NumberFormatException e) {
			log.info("cannot decode time: {} ", clockVal);
			return 0;
		}
	}

	protected void setupTimeValueVectors(final String calcMode, final String values) {
		times = new ArrayList<>();
		vals = new ArrayList();
		final String keyTimes = getAttribute("keyTimes");

		if (Strings.isCssBlank(keyTimes)) {

			if (calcMode.equalsIgnoreCase("paced")) {
				final StringTokenizer stVals = new StringTokenizer(values, ";");
				final int numVals = stVals.countTokens();
				float currTime = 0;
				int currentTokenCount = 0;
				while (stVals.hasMoreTokens()) {
					if (currentTokenCount == 0 || currentTokenCount == numVals - 1) {
						times.add(currTime);
						currTime = 1;
						vals.add(stVals.nextToken());
					} else {
						stVals.nextToken();
					}
					currentTokenCount++;
				}
			} else {
				final StringTokenizer stVals = new StringTokenizer(values, ";");
				final int numVals = stVals.countTokens();
				final float timeInc = (float) (1.0 / (numVals - 1));
				float currTime = 0;
				while (stVals.hasMoreTokens()) {
					times.add(currTime);
					currTime += timeInc;
					vals.add(stVals.nextToken());
				}
			}

		} else { // use the keyTimes attribute
			final StringTokenizer stTimes = new StringTokenizer(keyTimes, ";");
			final StringTokenizer stVals = new StringTokenizer(values, ";");
			while (stTimes.hasMoreTokens() && stVals.hasMoreTokens()) {
				times.add(Float.parseFloat(stTimes.nextToken()));
				vals.add(stVals.nextToken());
			}
		}

		if (calcMode.equals("spline") && !getAttribute("keySplines").isEmpty()) {
			splines = new ArrayList<>();
			final String keySplines = getAttribute("keySplines");
			final StringTokenizer st = new StringTokenizer(keySplines, ";");
			while (st.hasMoreTokens()) {
				final String spline = st.nextToken();
				final StringTokenizer st2 = new StringTokenizer(spline, ", ");
				if (st2.countTokens() == 4) {
					final float x1 = Float.parseFloat(st2.nextToken());
					final float y1 = Float.parseFloat(st2.nextToken());
					final float x2 = Float.parseFloat(st2.nextToken());
					final float y2 = Float.parseFloat(st2.nextToken());
					final SVGPathSegCurvetoCubicAbsImpl bezierSeg = new SVGPathSegCurvetoCubicAbsImpl(1, 1, x1, y1, x2, y2);
					splines.add(bezierSeg);
				}
			}
		}
	}

	protected float getSplineValueAt(final int splineIndex, final float percent) {
		final SVGPathSegCurvetoCubicAbsImpl bezierSeg = splines.get(splineIndex);
		return bezierSeg.getYAt(percent, new SVGPointImpl(0, 0));
	}
}
