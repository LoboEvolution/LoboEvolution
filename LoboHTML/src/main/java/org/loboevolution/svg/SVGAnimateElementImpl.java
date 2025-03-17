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

package org.loboevolution.svg;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Strings;

import java.util.StringTokenizer;

/**
 * <p>SVGAnimateElementImpl class.</p>
 */
@Slf4j
public class SVGAnimateElementImpl extends SVGAnimationElementImpl implements SVGAnimateElement {

	/**
	 * <p>Constructor for SVGAnimateElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGAnimateElementImpl(final String name) {
		super(name);
	}

	@Override
	public Object getCurrentValue(final short animtype) {
		Object currentValue = null;
		switch (animtype) {
			case SVGAnimatedValue.ANIMTYPE_LENGTH:
				currentValue = getCurrentLengthValue();
				break;
			case SVGAnimatedValue.ANIMTYPE_LENGTHLIST:
				currentValue = getCurrentLengthListValue();
				break;
			case SVGAnimatedValue.ANIMTYPE_BOOLEAN:
				currentValue = getCurrentBooleanValue();
				break;
			case SVGAnimatedValue.ANIMTYPE_STRING:
			case SVGAnimatedValue.ANIMTYPE_ENUMERATION:
				currentValue = getCurrentStringValue();
				break;
			case SVGAnimatedValue.ANIMTYPE_NUMBER:
				currentValue = getCurrentNumberValue();
				break;
			case SVGAnimatedValue.ANIMTYPE_NUMBERLIST:
				currentValue = getCurrentNumberListValue();
				break;
			case SVGAnimatedValue.ANIMTYPE_ANGLE:
				currentValue = getCurrentAngleValue();
				break;
			default:
				break;


		}
		return currentValue;
	}

	private SVGLength getCurrentLengthValue() {

		final float currentTime = getCurrentTime();
		final float startTime = getStartTime();
		float duration = getSimpleDuration();
		if (duration == -1) {
			final float endTime = getEndTime();
			if (endTime != -1) {
				duration = endTime - startTime;
			}
		}

		final float numRepeats = getNumRepeats(duration);
		final boolean repeatForever = getRepeatForever();

		final float percentageComplete = checkStatus(currentTime, startTime, duration, numRepeats, repeatForever);
		if (percentageComplete < 0) {
			return null;
		}

		if (!getAttribute("values").isEmpty()) {

			final String values = getAttribute("values");
			String calcMode = getAttribute("calcMode");
			if (Strings.isCssBlank(calcMode)) {
				calcMode = "linear"; // set to default linear
			}

			if (times == null || vals == null) {
				setupTimeValueVectors(calcMode, values);
			}

			// find the appropriate keys and values
			float beforeTime = 0;
			float afterTime = 0;
			SVGLengthImpl beforeLength = null;
			SVGLengthImpl afterLength = null;
			int splineIndex = 0;
			for (int i = 0; i < times.size() - 1; i++) {
				beforeTime = times.get(i);
				afterTime = times.get(i + 1);
				if (percentageComplete >= beforeTime && percentageComplete <= afterTime) {
					beforeLength = new SVGLengthImpl(vals.get(i));
					afterLength = new SVGLengthImpl(vals.get(i + 1));
					break;
				}
				if (i == times.size() - 2 && calcMode.equals("discrete") && percentageComplete > afterTime) {
					beforeLength = new SVGLengthImpl(vals.get(i + 1));
					afterLength = new SVGLengthImpl(vals.get(i + 1));
					break;
				}
				splineIndex++;
			}

			if (beforeLength != null && afterLength != null) {
				float percentBetween = (percentageComplete - beforeTime) / (afterTime - beforeTime);
				switch (calcMode) {
					case "linear":
					case "paced":
						final float lengthVal = beforeLength.getValue()
								+ percentBetween * (afterLength.getValue() - beforeLength.getValue());
						return new SVGLengthImpl(lengthVal);
					case "discrete":
						if (percentBetween < 1) {
							return beforeLength;
						} else {
							return afterLength;
						}
					case "spline":
						percentBetween = getSplineValueAt(splineIndex, percentBetween);
						final float length = beforeLength.getValue() + percentBetween * (afterLength.getValue() - beforeLength.getValue());
						return new SVGLengthImpl(length);
					default:
						break;
				}
			}

		} else if (!getAttribute("from").isEmpty() || !getAttribute("to").isEmpty()
				|| !getAttribute("by").isEmpty()) {

			String from = getAttribute("from");
			if (Strings.isCssBlank(from)) {
				from = getTargetElement().getAttribute(getAttribute("attributeName"));
			}
			final String to = getAttribute("to");
			final String by = getAttribute("by");

			if (!from.isEmpty() && !to.isEmpty()) { // is a from-to or to
				// anim
				final SVGLengthImpl fromLength = new SVGLengthImpl(from);
				final SVGLengthImpl toLength = new SVGLengthImpl(to);
				final float fromValue = fromLength.getValue();
				final float toValue = toLength.getValue();
				final float value = fromValue + percentageComplete * (toValue - fromValue);
				return new SVGLengthImpl(value);

			} else if (!from.isEmpty() && !by.isEmpty()) { // is a from-by
				// or to anim
				final SVGLengthImpl fromLength = new SVGLengthImpl(from);
				final SVGLengthImpl byLength = new SVGLengthImpl(by);
				final float fromValue = fromLength.getValue();
				final float byValue = byLength.getValue();
				final float value = fromValue + percentageComplete * byValue;
				return new SVGLengthImpl(value);
			}
		}
		return null;
	}

	private SVGLengthList getCurrentLengthListValue() {

		final float currentTime = getCurrentTime();
		final float startTime = getStartTime();
		float duration = getSimpleDuration();
		if (duration == -1) {
			final float endTime = getEndTime();
			if (endTime != -1) {
				duration = endTime - startTime;
			}
		}
		final float numRepeats = getNumRepeats(duration);
		final boolean repeatForever = getRepeatForever();

		final float percentageComplete = checkStatus(currentTime, startTime, duration, numRepeats, repeatForever);
		if (percentageComplete < 0) {
			return null; // indicates to use the baseVal
		}

		if (!getAttribute("values").isEmpty()) {

			final String values = getAttribute("values");
			String calcMode = getAttribute("calcMode");
			if (Strings.isCssBlank(calcMode)) {
				calcMode = "linear";
			}

			if (times == null || vals == null) {
				setupTimeValueVectors(calcMode, values);
			}

			// find the appropriate keys and values
			float beforeTime = 0;
			float afterTime = 0;
			SVGLengthList beforeLengthList = null;
			SVGLengthList afterLengthList = null;
			int splineIndex = 0;
			for (int i = 0; i < times.size() - 1; i++) {
				beforeTime = times.get(i);
				afterTime = times.get(i + 1);
				if (percentageComplete >= beforeTime && percentageComplete <= afterTime) {
					beforeLengthList = makeLengthList(vals.get(i));
					afterLengthList = makeLengthList(vals.get(i + 1));
					break;
				}
				if (i == times.size() - 2 && calcMode.equals("discrete") && percentageComplete > afterTime) {
					beforeLengthList = makeLengthList(vals.get(i + 1));
					afterLengthList = makeLengthList(vals.get(i + 1));
					break;
				}
				splineIndex++;
			}
			if (beforeLengthList != null && afterLengthList != null) {
				float percentBetween = (percentageComplete - beforeTime) / (afterTime - beforeTime);
				if (calcMode.equals("linear") || calcMode.equals("paced") || calcMode.equals("spline")) {
					if (beforeLengthList.getNumberOfItems() == afterLengthList.getNumberOfItems()) {
						if (calcMode.equals("spline")) {
							// adjust the percentBetween by the spline value
							percentBetween = getSplineValueAt(splineIndex, percentBetween);
						}
						final SVGLengthList currentList = new SVGLengthListImpl();
						final int numItems = beforeLengthList.getNumberOfItems();
						for (int i = 0; i < numItems; i++) {
							final float beforeValue = beforeLengthList.getItem(i).getValue();
							final float afterValue = afterLengthList.getItem(i).getValue();
							final float value = beforeValue + percentBetween * (afterValue - beforeValue);
							currentList.appendItem(new SVGLengthImpl(value));
						}
						return currentList;
					} else {
						log.info("cannot animate length list, all lists need to contain the same number of items");
						return null;
					}

				} else if (calcMode.equals("discrete")) {
					if (percentBetween < 1) {
						return beforeLengthList;
					} else {
						return afterLengthList;
					}
				}
			}

		} else if (!getAttribute("from").isEmpty() || !getAttribute("to").isEmpty()
				|| !getAttribute("by").isEmpty()) {

			String from = getAttribute("from");
			if (Strings.isCssBlank(from)) {
				from = getTargetElement().getAttribute(getAttribute("attributeName"));
			}
			final String to = getAttribute("to");
			final String by = getAttribute("by");

			if (!from.isEmpty() && !to.isEmpty()) { // is a from-to anim
				final SVGLengthList fromLengthList = makeLengthList(from);
				final SVGLengthList toLengthList = makeLengthList(to);
				if (fromLengthList.getNumberOfItems() == toLengthList.getNumberOfItems()) {
					final SVGLengthList currentList = new SVGLengthListImpl();
					final int numItems = fromLengthList.getNumberOfItems();
					for (int i = 0; i < numItems; i++) {
						final float fromValue = fromLengthList.getItem(i).getValue();
						final float toValue = toLengthList.getItem(i).getValue();
						final float value = fromValue + percentageComplete * (toValue - fromValue);
						currentList.appendItem(new SVGLengthImpl(value));
					}
					return currentList;
				} else {
					log.info("cannot animate length list, from and to lists have different number of items");
					return null;
				}

			} else if (!from.isEmpty() && !by.isEmpty()) { // is a from-by
				// anim
				final SVGLengthList fromLengthList = makeLengthList(from);
				final SVGLengthList byLengthList = makeLengthList(by);
				if (fromLengthList.getNumberOfItems() == byLengthList.getNumberOfItems()) {
					final SVGLengthList currentList = new SVGLengthListImpl();
					final int numItems = fromLengthList.getNumberOfItems();
					for (int i = 0; i < numItems; i++) {
						final float fromValue = fromLengthList.getItem(i).getValue();
						final float byValue = byLengthList.getItem(i).getValue();
						currentList.appendItem(new SVGLengthImpl(fromValue + percentageComplete * byValue));
					}
					return currentList;
				} else {
					log.info("cannot animate length list, from and by lists have different number of items");
					return null;
				}
			}
		}
		return null;
	}

	private SVGLengthList makeLengthList(final String lengthListString) {
		final SVGLengthListImpl lengthList = new SVGLengthListImpl();
		final StringTokenizer st = new StringTokenizer(lengthListString, " ,");
		while (st.hasMoreTokens()) {
			final SVGLengthImpl length = new SVGLengthImpl(st.nextToken());
			lengthList.appendItem(length);
		}
		return lengthList;
	}

	private Boolean getCurrentBooleanValue() {

		final float currentTime = getCurrentTime();
		final float startTime = getStartTime();
		float duration = getSimpleDuration();
		if (duration == -1) {
			final float endTime = getEndTime();
			if (endTime != -1) {
				duration = endTime - startTime;
			}
		}
		final float numRepeats = getNumRepeats(duration);
		final boolean repeatForever = getRepeatForever();

		final float percentageComplete = checkStatus(currentTime, startTime, duration, numRepeats, repeatForever);
		if (percentageComplete < 0) {
			return null; // indicates to use the baseVal
		}

		if (!getAttribute("values").isEmpty()) {

			final String values = getAttribute("values");
			String calcMode = getAttribute("calcMode");
			if (Strings.isCssBlank(calcMode)) {
				calcMode = "linear"; // set to default linear
			}

			if (times == null || vals == null) {
				setupTimeValueVectors(calcMode, values);
			}

			// find the appropriate keys and values
			float beforeTime = 0;
			float afterTime = 0;
			Boolean beforeBool = null;
			Boolean afterBool = null;
			for (int i = 0; i < times.size() - 1; i++) {
				beforeTime = times.get(i);
				afterTime = times.get(i + 1);
				if (percentageComplete >= beforeTime && percentageComplete <= afterTime) {
					beforeBool = Boolean.valueOf(vals.get(i));
					afterBool = Boolean.valueOf(vals.get(i + 1));
					break;
				}
			}

			if (beforeBool != null && afterBool != null) {
				final float percentBetween = (percentageComplete - beforeTime) / (afterTime - beforeTime);
				if (percentBetween < 1) {
					return beforeBool;
				} else {
					return afterBool;
				}
			}
		}
		return null;
	}

	private String getCurrentStringValue() {

		final float currentTime = getCurrentTime();
		final float startTime = getStartTime();
		float duration = getSimpleDuration();
		if (duration == -1) {
			final float endTime = getEndTime();
			if (endTime != -1) {
				duration = endTime - startTime;
			}
		}
		final float numRepeats = getNumRepeats(duration);
		final boolean repeatForever = getRepeatForever();

		final float percentageComplete = checkStatus(currentTime, startTime, duration, numRepeats, repeatForever);
		if (percentageComplete < 0) {
			return null; // indicates to use the baseVal
		}

		if (!getAttribute("from").isEmpty() && !getAttribute("to").isEmpty()) {
			String from = getAttribute("from");
			if (Strings.isCssBlank(from)) {
				from = getTargetElement().getAttribute(getAttribute("attributeName"));
			}
			final String to = getAttribute("to");
			if (!finished) {
				return from;
			} else {
				return to;
			}

		} else if (!getAttribute("values").isEmpty()) {

			final String values = getAttribute("values");
			String calcMode = getAttribute("calcMode");
			if (Strings.isCssBlank(calcMode)) {
				calcMode = "linear"; // set to default linear
			}

			if (times == null || vals == null) {
				setupTimeValueVectors(calcMode, values);
			}

			// find the appropriate keys and values
			float beforeTime = 0;
			float afterTime = 0;
			String beforeString = null;
			String afterString = null;
			for (int i = 0; i < times.size() - 1; i++) {
				beforeTime = times.get(i);
				afterTime = times.get(i + 1);
				if (percentageComplete >= beforeTime && percentageComplete <= afterTime) {
					beforeString = vals.get(i);
					afterString = vals.get(i + 1);
					break;
				}
				if (i == times.size() - 2 && calcMode.equals("discrete") && percentageComplete > afterTime) {
					beforeString = vals.get(i + 1);
					afterString = vals.get(i + 1);
					break;
				}
			}

			if (beforeString != null && afterString != null) {
				final float percentBetween = (percentageComplete - beforeTime) / (afterTime - beforeTime);
				if (percentBetween < 1) {
					return beforeString;
				} else {
					return afterString;
				}
			}
		}
		return null;
	}

	private Float getCurrentNumberValue() {

		final float currentTime = getCurrentTime();
		final float startTime = getStartTime();
		float duration = getSimpleDuration();
		if (duration == -1) {
			final float endTime = getEndTime();
			if (endTime != -1) {
				duration = endTime - startTime;
			}
		}
		final float numRepeats = getNumRepeats(duration);
		final boolean repeatForever = getRepeatForever();

		final float percentageComplete = checkStatus(currentTime, startTime, duration, numRepeats, repeatForever);
		if (percentageComplete < 0) {
			return null; // indicates to use the baseVal
		}

		if (!getAttribute("values").isEmpty()) {

			final String values = getAttribute("values");
			String calcMode = getAttribute("calcMode");
			if (Strings.isCssBlank(calcMode)) {
				calcMode = "linear"; // set to default linear
			}

			if (times == null || vals == null) {
				setupTimeValueVectors(calcMode, values);
			}

			// find the appropriate keys and values
			float beforeTime = 0;
			float afterTime = 0;
			Float beforeNumber = null;
			Float afterNumber = null;
			int splineIndex = 0;
			for (int i = 0; i < times.size() - 1; i++) {
				beforeTime = times.get(i);
				afterTime = times.get(i + 1);
				if (percentageComplete >= beforeTime && percentageComplete <= afterTime) {
					beforeNumber = Float.parseFloat(vals.get(i));
					afterNumber = Float.parseFloat(vals.get(i + 1));
					break;
				}
				if (i == times.size() - 2 && calcMode.equals("discrete") && percentageComplete > afterTime) {
					beforeNumber = Float.parseFloat(vals.get(i + 1));
					afterNumber = Float.parseFloat(vals.get(i + 1));
					break;
				}
				splineIndex++;
			}

			if (beforeNumber != null && afterNumber != null) {
				float percentBetween = (percentageComplete - beforeTime) / (afterTime - beforeTime);
				switch (calcMode) {
					case "linear":
					case "paced":
						return beforeNumber
								+ percentBetween * (afterNumber - beforeNumber);

					case "discrete":
						if (percentBetween < 1) {
							return beforeNumber;
						} else {
							return afterNumber;
						}
					case "spline":
						// adjust the percentBetween by the spline value
						percentBetween = getSplineValueAt(splineIndex, percentBetween);
						return beforeNumber
								+ percentBetween * (afterNumber - beforeNumber);
				}
			}

		} else if (!getAttribute("from").isEmpty() || !getAttribute("to").isEmpty()
				|| !getAttribute("by").isEmpty()) {
			// it is either a from-to, from-by, by or to animation

			String from = getAttribute("from");
			if (Strings.isCssBlank(from)) {
				from = getTargetElement().getAttribute(getAttribute("attributeName"));
			}
			final String to = getAttribute("to");
			final String by = getAttribute("by");

			if (!from.isEmpty() && !to.isEmpty()) { // is a from-to anim
				final float fromNumber = Float.parseFloat(from);
                final float toValue = Float.parseFloat(to);
				return fromNumber + percentageComplete * (toValue - fromNumber);

			} else if (!from.isEmpty() && !by.isEmpty()) { // is a from-by
				// anim
				final float fromNumber = Float.parseFloat(from);
                final float byValue = Float.parseFloat(by);
				return fromNumber + percentageComplete * byValue;

			}
		}
		return null;
	}

	private SVGNumberList getCurrentNumberListValue() {

		final float currentTime = getCurrentTime();
		final float startTime = getStartTime();
		float duration = getSimpleDuration();
		if (duration == -1) {
			final float endTime = getEndTime();
			if (endTime != -1) {
				duration = endTime - startTime;
			}
		}
		final float numRepeats = getNumRepeats(duration);
		final boolean repeatForever = getRepeatForever();

		final float percentageComplete = checkStatus(currentTime, startTime, duration, numRepeats, repeatForever);
		if (percentageComplete < 0) {
			return null; // indicates to use the baseVal
		}

		if (!getAttribute("values").isEmpty()) {

			final String values = getAttribute("values");
			String calcMode = getAttribute("calcMode");
			if (Strings.isCssBlank(calcMode)) {
				calcMode = "linear"; // set to default linear
			}

			if (times == null || vals == null) {
				setupTimeValueVectors(calcMode, values);
			}

			// find the appropriate keys and values
			float beforeTime = 0;
			float afterTime = 0;
			SVGNumberList beforeNumberList = null;
			SVGNumberList afterNumberList = null;
			int splineIndex = 0;
			for (int i = 0; i < times.size() - 1; i++) {
				beforeTime = (times.get(i));
				afterTime = (times.get(i + 1));
				if (percentageComplete >= beforeTime && percentageComplete <= afterTime) {
					beforeNumberList = new SVGNumberListImpl(vals.get(i));
					afterNumberList = new SVGNumberListImpl(vals.get(i + 1));
					break;
				}
				if (i == times.size() - 2 && calcMode.equals("discrete") && percentageComplete > afterTime) {
					beforeNumberList = new SVGNumberListImpl(vals.get(i + 1));
					afterNumberList = new SVGNumberListImpl(vals.get(i + 1));
					break;
				}
				splineIndex++;
			}

			if (beforeNumberList != null && afterNumberList != null) {
				float percentBetween = (percentageComplete - beforeTime) / (afterTime - beforeTime);
				if (calcMode.equals("linear") || calcMode.equals("paced") || calcMode.equals("spline")) {
					if (beforeNumberList.getNumberOfItems() == afterNumberList.getNumberOfItems()) {
						if (calcMode.equals("spline")) {
							// adjust the percentBetween by the spline value
							percentBetween = getSplineValueAt(splineIndex, percentBetween);
						}
						final SVGNumberList currentList = new SVGNumberListImpl();
						final int numItems = beforeNumberList.getNumberOfItems();
						for (int i = 0; i < numItems; i++) {
							final float beforeValue = beforeNumberList.getItem(i).getValue();
							final float afterValue = afterNumberList.getItem(i).getValue();
							final float value = beforeValue + percentBetween * (afterValue - beforeValue);
							currentList.appendItem(new SVGNumberImpl(value));
						}
						return currentList;
					} else {
						log.info("cannot animate number list, all lists need to contain the same number of items");
						return null;
					}

				} else if (calcMode.equals("discrete")) {
					if (percentBetween < 1) {
						return beforeNumberList;
					} else {
						return afterNumberList;
					}
				}
			}

		} else if (!getAttribute("from").isEmpty() || !getAttribute("to").isEmpty()
				|| !getAttribute("by").isEmpty()) {

			String from = getAttribute("from");
			if (Strings.isCssBlank(from)) {
				from = getTargetElement().getAttribute(getAttribute("attributeName"));
			}
			final String to = getAttribute("to");
			final String by = getAttribute("by");

			if (!from.isEmpty() && !to.isEmpty()) { // is a from-to anim
				final SVGNumberList fromNumberList = new SVGNumberListImpl(from);
				final SVGNumberList toNumberList = new SVGNumberListImpl(to);
				if (fromNumberList.getNumberOfItems() == toNumberList.getNumberOfItems()) {
					final SVGNumberList currentList = new SVGNumberListImpl();
					final int numItems = fromNumberList.getNumberOfItems();
					for (int i = 0; i < numItems; i++) {
						final float fromValue = fromNumberList.getItem(i).getValue();
						final float toValue = toNumberList.getItem(i).getValue();
						final float value = fromValue + percentageComplete * (toValue - fromValue);
						currentList.appendItem(new SVGNumberImpl(value));
					}
					return currentList;
				} else {
					log.info("cannot animate length list, from and to lists have different number of items");
					return null;
				}

			} else if (!from.isEmpty() && !by.isEmpty()) { // is a from-by
				// anim
				final SVGNumberList fromNumberList = new SVGNumberListImpl(from);
				final SVGNumberList byNumberList = new SVGNumberListImpl(by);
				if (fromNumberList.getNumberOfItems() == byNumberList.getNumberOfItems()) {
					final SVGNumberList currentList = new SVGNumberListImpl();
					final int numItems = fromNumberList.getNumberOfItems();
					for (int i = 0; i < numItems; i++) {
						final float fromValue = fromNumberList.getItem(i).getValue();
						final float byValue = byNumberList.getItem(i).getValue();
						final float value = fromValue + percentageComplete * byValue;
						currentList.appendItem(new SVGNumberImpl(value));
					}
					return currentList;
				} else {
					log.info("cannot animate length list, from and by lists have different number of items");
					return null;
				}
			}
		}
		return null;
	}

	private SVGAngle getCurrentAngleValue() {

		final float currentTime = getCurrentTime();
		final float startTime = getStartTime();
		float duration = getSimpleDuration();
		if (duration == -1) {
			final float endTime = getEndTime();
			if (endTime != -1) {
				duration = endTime - startTime;
			}
		}
		final float numRepeats = getNumRepeats(duration);
		final boolean repeatForever = getRepeatForever();

		final float percentageComplete = checkStatus(currentTime, startTime, duration, numRepeats, repeatForever);
		if (percentageComplete < 0) {
			return null; // indicates to use the baseVal
		}

		if (!getAttribute("values").isEmpty()) {

			final String values = getAttribute("values");
			String calcMode = getAttribute("calcMode");
			if (Strings.isCssBlank(calcMode)) {
				calcMode = "linear"; // set to default linear
			}

			if (times == null || vals == null) {
				setupTimeValueVectors(calcMode, values);
			}

			// find the appropriate keys and values
			float beforeTime = 0;
			float afterTime = 0;
			SVGAngleImpl beforeAngle = null;
			SVGAngleImpl afterAngle = null;
			int splineIndex = 0;
			for (int i = 0; i < times.size() - 1; i++) {
				beforeTime = times.get(i);
				afterTime = times.get(i + 1);
				if (percentageComplete >= beforeTime && percentageComplete <= afterTime) {
					beforeAngle = new SVGAngleImpl(vals.get(i));
					afterAngle = new SVGAngleImpl(vals.get(i + 1));
					break;
				}
				if (i == times.size() - 2 && calcMode.equals("discrete") && percentageComplete > afterTime) {
					beforeAngle = new SVGAngleImpl(vals.get(i + 1));
					afterAngle = new SVGAngleImpl(vals.get(i + 1));
					break;
				}
				splineIndex++;
			}

			if (beforeAngle != null && afterAngle != null) {
				float percentBetween = (percentageComplete - beforeTime) / (afterTime - beforeTime);
				switch (calcMode) {
					case "linear":
					case "paced": {
						final float angleVal = beforeAngle.getValue()
								+ percentBetween * (afterAngle.getValue() - beforeAngle.getValue());
						return new SVGAngleImpl(angleVal);

					}
					case "discrete":
						if (percentBetween < 1) {
							return beforeAngle;
						} else {
							return afterAngle;
						}

					case "spline": {
						// adjust the percentBetween by the spline value
						percentBetween = getSplineValueAt(splineIndex, percentBetween);
						final float angleVal = beforeAngle.getValue()
								+ percentBetween * (afterAngle.getValue() - beforeAngle.getValue());
						return new SVGAngleImpl(angleVal);
					}
				}
			}

		} else if (!getAttribute("from").isEmpty() || !getAttribute("to").isEmpty()
				|| !getAttribute("by").isEmpty()) {
			// it is either a from-to or a from-by animation

			String from = getAttribute("from");
			if (Strings.isCssBlank(from)) {
				from = getTargetElement().getAttribute(getAttribute("attributeName"));
			}
			final String to = getAttribute("to");
			final String by = getAttribute("by");

			if (!from.isEmpty() && !to.isEmpty()) { // is a from-to anim
				final SVGAngleImpl fromAngle = new SVGAngleImpl(from);
				final SVGAngleImpl toAngle = new SVGAngleImpl(to);
				final float fromValue = fromAngle.getValue();
				final float toValue = toAngle.getValue();
				return new SVGAngleImpl(fromValue + percentageComplete * (toValue - fromValue));

			} else if (!from.isEmpty() && !by.isEmpty()) { // is a from-by
				final SVGAngleImpl fromAngle = new SVGAngleImpl(from);
				final SVGAngleImpl byAngle = new SVGAngleImpl(by);
				final float fromValue = fromAngle.getValue();
				final float byValue = byAngle.getValue();
				return new SVGAngleImpl(fromValue + percentageComplete * byValue);
			}
		}
		return null;
	}
}
