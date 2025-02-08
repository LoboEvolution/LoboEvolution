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
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.smil.SMILAnimationImpl;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static org.loboevolution.svg.SVGTransform.*;

/**
 * <p>SVGAnimationElementImpl class.</p>
 */
@Slf4j
public abstract class SVGAnimationElementImpl extends SMILAnimationImpl implements SVGAnimationElement {

    public List<Float> times = null;
    public List<String> vals = null;
    public List<SVGPathSegCurvetoCubicAbsImpl> splines = null;

    private int counter;
    private boolean started = false;
    private boolean active = false;
    protected boolean finished = false;
    private float startTime;
    private float endTime;
    private float sxFrom = 0;
    private float sxTo = 0;
    private float syFrom = 0;
    private float syTo = 0;
    private float txFrom = 0;
    private  float tyFrom = 0;
    private float txTo = 0;
    float tyTo = 0;
    private float angleFrom = 0;
    private float cxFrom = 0;
    private float cyFrom = 0;
    private float angleTo = 0;
    private  float cxTo = 0;
    private float cyTo = 0;
    private long dur;
    private Timer timer;


    /**
     * <p>Constructor for SVGAnimationElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public SVGAnimationElementImpl(String name) {
        super(name);

    }

    public abstract Object getCurrentValue(short animtype);

    @Override
    public void animation(SVGElement transform) {
        if (!started) {
            dur = System.currentTimeMillis();
            HTMLElement parentElement = getParentElement();
            timer = new Timer((int) getDuration(), e -> {
                final String attribute = getAttribute("attributeName").toLowerCase();
                switch (attribute) {
                    case "width":
                    case "height":
                    case "x":
                    case "y":
                    case "cx":
                    case "cy":
                    case "x1":
                    case "x2":
                    case "y1":
                    case "y2":
                    case "r":
                        //   animateSize(elem, attribute);
                        break;
                    case "fill":
                    case "stroke":
                        // animateColor(elem);
                        break;
                    case "transform":
                        animateTransform(parentElement);
                        break;
                    default:
                        break;
                }
            });
            timer.start();
            started = true;
        }
    }

    @Override
    public SVGElement getTargetElement() {
        SVGElementImpl target = null;
        String href = getAttribute("xlink:href");
        if (!href.isEmpty()) {
            int hashIndex = href.indexOf('#');
            if (hashIndex != -1) {
                String id = href.substring(hashIndex + 1);
                target = (SVGElementImpl) getDocumentNode().getElementById(id);
            }
        } else {
            target = (SVGElementImpl) getParentNode();
        }
        return target;
    }

    /** {@inheritDoc} */
    @Override
    public float getStartTime() {
        if (getAttribute("begin").equalsIgnoreCase("indefinite")) {
            if (active) {
                return startTime;
            } else {
                return Float.MAX_VALUE;
            }
        } else {
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
    private float getBeginTime() {
        final String beginTime = getAttribute("begin");
        if (!beginTime.equalsIgnoreCase("indefinite") && !beginTime.isEmpty()) {
            return getClockSecs(beginTime);
        } else {
            return 0;
        }
    }

    // returns the duration time in secs, will be -1 if indefinite
    private float getDuration() {
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
    protected float getEndTime() {
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
        if (!repeatCount.isEmpty() || !repeatDur.isEmpty()) {
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
            if (clockVal.contains(":")) {

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
        vals = new ArrayList<>();
        final String keyTimes = getAttribute("keyTimes");

        if (Strings.isCssBlank(keyTimes)) {

            final StringTokenizer stVals = new StringTokenizer(values, ";");
            final int numVals = stVals.countTokens();
            if (calcMode.equalsIgnoreCase("paced")) {
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

    private void animateTransform(final HTMLElement elem) {
        String transformString = "";
        String fromTrans = "";
        String toTrans = "";

        if (counter == 0) {
            fromTrans = getFrom();
            toTrans = getTo();
        }

        final StringTokenizer stFrom = new StringTokenizer(fromTrans, " ,");
        final StringTokenizer stTo = new StringTokenizer(toTrans, " ,");

        switch (getType()) {
            case SVG_TRANSFORM_TRANSLATE:

                if (stFrom.countTokens() == 1) {
                    txFrom = Float.parseFloat(stFrom.nextToken());
                } else if (stFrom.countTokens() == 2) {
                    txFrom = Float.parseFloat(stFrom.nextToken());
                    tyFrom = Float.parseFloat(stFrom.nextToken());
                }

                if (stTo.countTokens() == 1) {
                    txTo = Float.parseFloat(stTo.nextToken());
                } else if (stTo.countTokens() == 2) {
                    txTo = Float.parseFloat(stTo.nextToken());
                    tyTo = Float.parseFloat(stTo.nextToken());
                }

                if (txFrom > txTo)
                    txFrom--;

                if (txFrom < txTo)
                    txFrom++;

                if (tyFrom > tyTo)
                    tyFrom--;

                if (tyFrom < tyTo)
                    tyFrom++;

                fromTrans = txFrom + ", " + tyFrom;
                transformString = "translate(" + fromTrans + ")";
                break;
            case SVG_TRANSFORM_SCALE:
                if (stFrom.countTokens() == 1) {
                    sxFrom = Float.parseFloat(stFrom.nextToken());
                } else if (stFrom.countTokens() == 2) {
                    sxFrom = Float.parseFloat(stFrom.nextToken());
                    syFrom = Float.parseFloat(stFrom.nextToken());
                }

                if (stTo.countTokens() == 1) {
                    sxTo = Float.parseFloat(stTo.nextToken());
                } else if (stTo.countTokens() == 2) {
                    sxTo = Float.parseFloat(stTo.nextToken());
                    syTo = Float.parseFloat(stTo.nextToken());
                }

                if (sxFrom > sxTo)
                    sxFrom--;

                if (sxFrom < sxTo)
                    sxFrom++;

                if (syFrom > syTo)
                    syFrom--;

                if (syFrom < syTo)
                    syFrom++;

                if (syFrom == 0) {
                    toTrans = String.valueOf(Float.parseFloat(toTrans));
                } else {
                    fromTrans = sxFrom + ", " + syFrom;
                }

                transformString = "scale(" + fromTrans + ")";
                break;
            case SVG_TRANSFORM_ROTATE:

                if (stFrom.countTokens() == 1) {
                    angleFrom = Float.parseFloat(stFrom.nextToken());
                } else if (stFrom.countTokens() == 3) {
                    angleFrom = Float.parseFloat(stFrom.nextToken());
                    cxFrom = Float.parseFloat(stFrom.nextToken());
                    cyFrom = Float.parseFloat(stFrom.nextToken());
                }

                if (stTo.countTokens() == 1) {
                    angleTo = Float.parseFloat(stTo.nextToken());
                } else if (stTo.countTokens() == 3) {
                    angleTo = Float.parseFloat(stTo.nextToken());
                    cxTo = Float.parseFloat(stTo.nextToken());
                    cyTo = Float.parseFloat(stTo.nextToken());
                }

                if (angleFrom > angleTo)
                    angleFrom--;

                if (angleFrom < angleTo)
                    angleFrom++;

                if (cxFrom > cxTo)
                    cxFrom--;

                if (cxFrom < cxTo)
                    cxFrom++;

                if (cyFrom > cyTo)
                    cyFrom--;

                if (cyFrom < cyTo)
                    cyFrom++;

                fromTrans = angleFrom + ", " + cxFrom + ", " + cyFrom;
                transformString = "rotate(" + fromTrans + ")";
                break;
            case SVG_TRANSFORM_SKEWX:

                sxFrom = Float.parseFloat(fromTrans);
                sxTo = Float.parseFloat(toTrans);

                if (sxFrom > sxTo)
                    sxFrom--;

                if (sxFrom < sxTo)
                    sxFrom++;

                transformString = "skewX(" + sxFrom + ")";
                break;
            case SVG_TRANSFORM_SKEWY:

                sxFrom = Float.parseFloat(fromTrans);
                sxTo = Float.parseFloat(toTrans);

                if (sxFrom > sxTo)
                    sxFrom--;

                if (sxFrom < sxTo)
                    sxFrom++;
                transformString = "skewY(" + sxFrom + ")";
                break;
            default:
                break;
        }

        System.out.println("elem " + elem.getNodeName());

        elem.setAttribute("transform", transformString);
        if (getDur() > 0 && getDur() <= (System.currentTimeMillis() - dur)) {
            timer.stop();
        }
        counter++;
    }
}

