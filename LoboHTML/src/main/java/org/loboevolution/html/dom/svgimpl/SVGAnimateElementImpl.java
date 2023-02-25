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

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.svg.*;

import java.util.StringTokenizer;

/**
 * <p>SVGAnimateElementImpl class.</p>
 */
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
	public Object getCurrentValue(short animtype) {
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

		float currentTime = getCurrentTime();
		float startTime = getStartTime();
		float duration = getSimpleDuration();
		if (duration == -1) {
			float endTime = getEndTime();
			if (endTime != -1) {
				duration = endTime - startTime;
			}
		}

		float numRepeats = getNumRepeats(duration);
		boolean repeatForever = getRepeatForever();

		float percentageComplete = checkStatus(currentTime, startTime, duration, numRepeats, repeatForever);
		if (percentageComplete < 0) {
			return null;
		}

		if (getAttribute("values").length() > 0) {

			String values = getAttribute("values");
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
				beforeTime = (Float) times.elementAt(i);
				afterTime = (Float) times.elementAt(i + 1);
				if (percentageComplete >= beforeTime && percentageComplete <= afterTime) {
					beforeLength = new SVGLengthImpl((String) vals.elementAt(i));
					afterLength = new SVGLengthImpl((String) vals.elementAt(i + 1));
					break;
				}
				if (i == times.size() - 2 && calcMode.equals("discrete") && percentageComplete > afterTime) {
					beforeLength = new SVGLengthImpl((String) vals.elementAt(i + 1));
					afterLength = new SVGLengthImpl((String) vals.elementAt(i + 1));
					break;
				}
				splineIndex++;
			}

			if (beforeLength != null && afterLength != null) {
				float percentBetween = (percentageComplete - beforeTime) / (afterTime - beforeTime);
				switch (calcMode) {
					case "linear":
					case "paced":
						float lengthVal = beforeLength.getValue()
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
						float length = beforeLength.getValue() + percentBetween * (afterLength.getValue() - beforeLength.getValue());
						return new SVGLengthImpl(length);
					default:
						break;
				}
			}

		} else if (getAttribute("from").length() > 0 || getAttribute("to").length() > 0
				|| getAttribute("by").length() > 0) {

			String from = getAttribute("from");
			if (Strings.isCssBlank(from)) {
				from = getTargetElement().getAttribute(getAttribute("attributeName"));
			}
			String to = getAttribute("to");
			String by = getAttribute("by");

			if (from.length() > 0 && to.length() > 0) { // is a from-to or to
				// anim
				SVGLengthImpl fromLength = new SVGLengthImpl(from);
				SVGLengthImpl toLength = new SVGLengthImpl(to);
				float fromValue = fromLength.getValue();
				float toValue = toLength.getValue();
				float value = fromValue + percentageComplete * (toValue - fromValue);
				return new SVGLengthImpl(value);

			} else if (from.length() > 0 && by.length() > 0) { // is a from-by
				// or to anim
				SVGLengthImpl fromLength = new SVGLengthImpl(from);
				SVGLengthImpl byLength = new SVGLengthImpl(by);
				float fromValue = fromLength.getValue();
				float byValue = byLength.getValue();
				float value = fromValue + percentageComplete * byValue;
				return new SVGLengthImpl(value);
			}
		}
		return null;
	}

	private SVGLengthList getCurrentLengthListValue() {

		float currentTime = getCurrentTime();
		float startTime = getStartTime();
		float duration = getSimpleDuration();
		if (duration == -1) {
			float endTime = getEndTime();
			if (endTime != -1) {
				duration = endTime - startTime;
			}
		}
		float numRepeats = getNumRepeats(duration);
		boolean repeatForever = getRepeatForever();

		float percentageComplete = checkStatus(currentTime, startTime, duration, numRepeats, repeatForever);
		if (percentageComplete < 0) {
			return null; // indicates to use the baseVal
		}

		if (getAttribute("values").length() > 0) {

			String values = getAttribute("values");
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
				beforeTime = (Float) times.elementAt(i);
				afterTime = (Float) times.elementAt(i + 1);
				if (percentageComplete >= beforeTime && percentageComplete <= afterTime) {
					beforeLengthList = makeLengthList((String) vals.elementAt(i));
					afterLengthList = makeLengthList((String) vals.elementAt(i + 1));
					break;
				}
				if (i == times.size() - 2 && calcMode.equals("discrete") && percentageComplete > afterTime) {
					beforeLengthList = makeLengthList((String) vals.elementAt(i + 1));
					afterLengthList = makeLengthList((String) vals.elementAt(i + 1));
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
						SVGLengthList currentList = new SVGLengthListImpl();
						int numItems = beforeLengthList.getNumberOfItems();
						for (int i = 0; i < numItems; i++) {
							float beforeValue = beforeLengthList.getItem(i).getValue();
							float afterValue = afterLengthList.getItem(i).getValue();
							float value = beforeValue + percentBetween * (afterValue - beforeValue);
							currentList.appendItem(new SVGLengthImpl(value));
						}
						return currentList;
					} else {
						System.out.println(
								"cannot animate length list, all lists need to contain the same number of items");
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

		} else if (getAttribute("from").length() > 0 || getAttribute("to").length() > 0
				|| getAttribute("by").length() > 0) {

			String from = getAttribute("from");
			if (Strings.isCssBlank(from)) {
				from = getTargetElement().getAttribute(getAttribute("attributeName"));
			}
			String to = getAttribute("to");
			String by = getAttribute("by");

			if (from.length() > 0 && to.length() > 0) { // is a from-to anim
				SVGLengthList fromLengthList = makeLengthList(from);
				SVGLengthList toLengthList = makeLengthList(to);
				if (fromLengthList.getNumberOfItems() == toLengthList.getNumberOfItems()) {
					SVGLengthList currentList = new SVGLengthListImpl();
					int numItems = fromLengthList.getNumberOfItems();
					for (int i = 0; i < numItems; i++) {
						float fromValue = fromLengthList.getItem(i).getValue();
						float toValue = toLengthList.getItem(i).getValue();
						float value = fromValue + percentageComplete * (toValue - fromValue);
						currentList.appendItem(new SVGLengthImpl(value));
					}
					return currentList;
				} else {
					System.out.println("cannot animate length list, from and to lists have different number of items");
					return null;
				}

			} else if (from.length() > 0 && by.length() > 0) { // is a from-by
				// anim
				SVGLengthList fromLengthList = makeLengthList(from);
				SVGLengthList byLengthList = makeLengthList(by);
				if (fromLengthList.getNumberOfItems() == byLengthList.getNumberOfItems()) {
					SVGLengthList currentList = new SVGLengthListImpl();
					int numItems = fromLengthList.getNumberOfItems();
					for (int i = 0; i < numItems; i++) {
						float fromValue = fromLengthList.getItem(i).getValue();
						float byValue = byLengthList.getItem(i).getValue();
						currentList.appendItem(new SVGLengthImpl(fromValue + percentageComplete * byValue));
					}
					return currentList;
				} else {
					System.out.println("cannot animate length list, from and by lists have different number of items");
					return null;
				}
			}
		}
		return null;
	}

	private SVGLengthList makeLengthList(String lengthListString) {
		SVGLengthListImpl lengthList = new SVGLengthListImpl();
		StringTokenizer st = new StringTokenizer(lengthListString, " ,");
		while (st.hasMoreTokens()) {
			SVGLengthImpl length = new SVGLengthImpl(st.nextToken());
			lengthList.appendItem(length);
		}
		return lengthList;
	}

	private Boolean getCurrentBooleanValue() {

		float currentTime = getCurrentTime();
		float startTime = getStartTime();
		float duration = getSimpleDuration();
		if (duration == -1) {
			float endTime = getEndTime();
			if (endTime != -1) {
				duration = endTime - startTime;
			}
		}
		float numRepeats = getNumRepeats(duration);
		boolean repeatForever = getRepeatForever();

		float percentageComplete = checkStatus(currentTime, startTime, duration, numRepeats, repeatForever);
		if (percentageComplete < 0) {
			return null; // indicates to use the baseVal
		}

		if (getAttribute("values").length() > 0) {

			String values = getAttribute("values");
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
				beforeTime = (Float) times.elementAt(i);
				afterTime = (Float) times.elementAt(i + 1);
				if (percentageComplete >= beforeTime && percentageComplete <= afterTime) {
					beforeBool = Boolean.valueOf((String) vals.elementAt(i));
					afterBool = Boolean.valueOf((String) vals.elementAt(i + 1));
					break;
				}
			}

			if (beforeBool != null && afterBool != null) {
				float percentBetween = (percentageComplete - beforeTime) / (afterTime - beforeTime);
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

		float currentTime = getCurrentTime();
		float startTime = getStartTime();
		float duration = getSimpleDuration();
		if (duration == -1) {
			float endTime = getEndTime();
			if (endTime != -1) {
				duration = endTime - startTime;
			}
		}
		float numRepeats = getNumRepeats(duration);
		boolean repeatForever = getRepeatForever();

		float percentageComplete = checkStatus(currentTime, startTime, duration, numRepeats, repeatForever);
		if (percentageComplete < 0) {
			return null; // indicates to use the baseVal
		}

		if (getAttribute("from").length() > 0 && getAttribute("to").length() > 0) {
			String from = getAttribute("from");
			if (Strings.isCssBlank(from)) {
				from = getTargetElement().getAttribute(getAttribute("attributeName"));
			}
			String to = getAttribute("to");
			if (!finished) {
				return from;
			} else {
				return to;
			}

		} else if (getAttribute("values").length() > 0) {

			String values = getAttribute("values");
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
				beforeTime = (Float) times.elementAt(i);
				afterTime = (Float) times.elementAt(i + 1);
				if (percentageComplete >= beforeTime && percentageComplete <= afterTime) {
					beforeString = (String) vals.elementAt(i);
					afterString = (String) vals.elementAt(i + 1);
					break;
				}
				if (i == times.size() - 2 && calcMode.equals("discrete") && percentageComplete > afterTime) {
					beforeString = (String) vals.elementAt(i + 1);
					afterString = (String) vals.elementAt(i + 1);
					break;
				}
			}

			if (beforeString != null && afterString != null) {
				float percentBetween = (percentageComplete - beforeTime) / (afterTime - beforeTime);
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

		float currentTime = getCurrentTime();
		float startTime = getStartTime();
		float duration = getSimpleDuration();
		if (duration == -1) {
			float endTime = getEndTime();
			if (endTime != -1) {
				duration = endTime - startTime;
			}
		}
		float numRepeats = getNumRepeats(duration);
		boolean repeatForever = getRepeatForever();

		float percentageComplete = checkStatus(currentTime, startTime, duration, numRepeats, repeatForever);
		if (percentageComplete < 0) {
			return null; // indicates to use the baseVal
		}

		if (getAttribute("values").length() > 0) {

			String values = getAttribute("values");
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
				beforeTime = (Float) times.elementAt(i);
				afterTime = (Float) times.elementAt(i + 1);
				// System.out.println("checking for between: " + beforeTime + "
				// and " + afterTime + " percent complete = " +
				// percentageComplete);
				if (percentageComplete >= beforeTime && percentageComplete <= afterTime) {
					beforeNumber = new Float((String) vals.elementAt(i));
					afterNumber = new Float((String) vals.elementAt(i + 1));
					// System.out.println("time between " + i + " and " +
					// (i+1));
					break;
				}
				if (i == times.size() - 2 && calcMode.equals("discrete") && percentageComplete > afterTime) {
					beforeNumber = new Float((String) vals.elementAt(i + 1));
					afterNumber = new Float((String) vals.elementAt(i + 1));
					// System.out.println("time between " + i + " and " +
					// (i+1));
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

		} else if (getAttribute("from").length() > 0 || getAttribute("to").length() > 0
				|| getAttribute("by").length() > 0) {
			// it is either a from-to, from-by, by or to animation

			String from = getAttribute("from");
			if (Strings.isCssBlank(from)) {
				from = getTargetElement().getAttribute(getAttribute("attributeName"));
			}
			String to = getAttribute("to");
			String by = getAttribute("by");

			if (from.length() > 0 && to.length() > 0) { // is a from-to anim
				Float fromNumber = new Float(from);
				Float toNumber = new Float(to);
				float fromValue = fromNumber;
				float toValue = toNumber;
				return fromValue + percentageComplete * (toValue - fromValue);

			} else if (from.length() > 0 && by.length() > 0) { // is a from-by
				// anim
				Float fromNumber = new Float(from);
				Float byNumber = new Float(by);
				float fromValue = fromNumber;
				float byValue = byNumber;
				return fromValue + percentageComplete * byValue;

			}
		}
		return null;
	}

	private SVGNumberList getCurrentNumberListValue() {

		float currentTime = getCurrentTime();
		float startTime = getStartTime();
		float duration = getSimpleDuration();
		if (duration == -1) {
			float endTime = getEndTime();
			if (endTime != -1) {
				duration = endTime - startTime;
			}
		}
		float numRepeats = getNumRepeats(duration);
		boolean repeatForever = getRepeatForever();

		float percentageComplete = checkStatus(currentTime, startTime, duration, numRepeats, repeatForever);
		if (percentageComplete < 0) {
			return null; // indicates to use the baseVal
		}

		if (getAttribute("values").length() > 0) {

			String values = getAttribute("values");
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
				beforeTime = ((Float) times.elementAt(i));
				afterTime = ((Float) times.elementAt(i + 1));
				if (percentageComplete >= beforeTime && percentageComplete <= afterTime) {
					beforeNumberList = new SVGNumberListImpl((String) vals.elementAt(i));
					afterNumberList = new SVGNumberListImpl((String) vals.elementAt(i + 1));
					break;
				}
				if (i == times.size() - 2 && calcMode.equals("discrete") && percentageComplete > afterTime) {
					beforeNumberList = new SVGNumberListImpl((String) vals.elementAt(i + 1));
					afterNumberList = new SVGNumberListImpl((String) vals.elementAt(i + 1));
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
						SVGNumberList currentList = new SVGNumberListImpl();
						int numItems = beforeNumberList.getNumberOfItems();
						for (int i = 0; i < numItems; i++) {
							float beforeValue = beforeNumberList.getItem(i).getValue();
							float afterValue = afterNumberList.getItem(i).getValue();
							float value = beforeValue + percentBetween * (afterValue - beforeValue);
							currentList.appendItem(new SVGNumberImpl(value));
						}
						return currentList;
					} else {
						System.out.println(
								"cannot animate number list, all lists need to contain the same number of items");
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

		} else if (getAttribute("from").length() > 0 || getAttribute("to").length() > 0
				|| getAttribute("by").length() > 0) {

			String from = getAttribute("from");
			if (Strings.isCssBlank(from)) {
				from = getTargetElement().getAttribute(getAttribute("attributeName"));
			}
			String to = getAttribute("to");
			String by = getAttribute("by");

			if (from.length() > 0 && to.length() > 0) { // is a from-to anim
				SVGNumberList fromNumberList = new SVGNumberListImpl(from);
				SVGNumberList toNumberList = new SVGNumberListImpl(to);
				if (fromNumberList.getNumberOfItems() == toNumberList.getNumberOfItems()) {
					SVGNumberList currentList = new SVGNumberListImpl();
					int numItems = fromNumberList.getNumberOfItems();
					for (int i = 0; i < numItems; i++) {
						float fromValue = fromNumberList.getItem(i).getValue();
						float toValue = toNumberList.getItem(i).getValue();
						float value = fromValue + percentageComplete * (toValue - fromValue);
						currentList.appendItem(new SVGNumberImpl(value));
					}
					return currentList;
				} else {
					System.out.println("cannot animate length list, from and to lists have different number of items");
					return null;
				}

			} else if (from.length() > 0 && by.length() > 0) { // is a from-by
				// anim
				SVGNumberList fromNumberList = new SVGNumberListImpl(from);
				SVGNumberList byNumberList = new SVGNumberListImpl(by);
				if (fromNumberList.getNumberOfItems() == byNumberList.getNumberOfItems()) {
					SVGNumberList currentList = new SVGNumberListImpl();
					int numItems = fromNumberList.getNumberOfItems();
					for (int i = 0; i < numItems; i++) {
						float fromValue = fromNumberList.getItem(i).getValue();
						float byValue = byNumberList.getItem(i).getValue();
						float value = fromValue + percentageComplete * byValue;
						currentList.appendItem(new SVGNumberImpl(value));
					}
					return currentList;
				} else {
					System.out.println("cannot animate length list, from and by lists have different number of items");
					return null;
				}
			}
		}
		return null;
	}

	private SVGAngle getCurrentAngleValue() {

		float currentTime = getCurrentTime();
		float startTime = getStartTime();
		float duration = getSimpleDuration();
		if (duration == -1) {
			float endTime = getEndTime();
			if (endTime != -1) {
				duration = endTime - startTime;
			}
		}
		float numRepeats = getNumRepeats(duration);
		boolean repeatForever = getRepeatForever();

		float percentageComplete = checkStatus(currentTime, startTime, duration, numRepeats, repeatForever);
		if (percentageComplete < 0) {
			return null; // indicates to use the baseVal
		}

		if (getAttribute("values").length() > 0) {

			String values = getAttribute("values");
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
				beforeTime = (Float) times.elementAt(i);
				afterTime = (Float) times.elementAt(i + 1);
				if (percentageComplete >= beforeTime && percentageComplete <= afterTime) {
					beforeAngle = new SVGAngleImpl((String) vals.elementAt(i));
					afterAngle = new SVGAngleImpl((String) vals.elementAt(i + 1));
					break;
				}
				if (i == times.size() - 2 && calcMode.equals("discrete") && percentageComplete > afterTime) {
					beforeAngle = new SVGAngleImpl((String) vals.elementAt(i + 1));
					afterAngle = new SVGAngleImpl((String) vals.elementAt(i + 1));
					break;
				}
				splineIndex++;
			}

			if (beforeAngle != null && afterAngle != null) {
				float percentBetween = (percentageComplete - beforeTime) / (afterTime - beforeTime);
				switch (calcMode) {
					case "linear":
					case "paced": {
						float angleVal = beforeAngle.getValue()
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
						float angleVal = beforeAngle.getValue()
								+ percentBetween * (afterAngle.getValue() - beforeAngle.getValue());
						return new SVGAngleImpl(angleVal);
					}
				}
			}

		} else if (getAttribute("from").length() > 0 || getAttribute("to").length() > 0
				|| getAttribute("by").length() > 0) {
			// it is either a from-to or a from-by animation

			String from = getAttribute("from");
			if (Strings.isCssBlank(from)) {
				from = getTargetElement().getAttribute(getAttribute("attributeName"));
			}
			String to = getAttribute("to");
			String by = getAttribute("by");

			if (from.length() > 0 && to.length() > 0) { // is a from-to anim
				SVGAngleImpl fromAngle = new SVGAngleImpl(from);
				SVGAngleImpl toAngle = new SVGAngleImpl(to);
				float fromValue = fromAngle.getValue();
				float toValue = toAngle.getValue();
				return new SVGAngleImpl(fromValue + percentageComplete * (toValue - fromValue));

			} else if (from.length() > 0 && by.length() > 0) { // is a from-by
				SVGAngleImpl fromAngle = new SVGAngleImpl(from);
				SVGAngleImpl byAngle = new SVGAngleImpl(by);
				float fromValue = fromAngle.getValue();
				float byValue = byAngle.getValue();
				return new SVGAngleImpl(fromValue + percentageComplete * byValue);
			}
		}
		return null;
	}
}
