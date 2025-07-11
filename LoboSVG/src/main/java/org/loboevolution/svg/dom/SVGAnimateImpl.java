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

package org.loboevolution.svg.dom;

import org.loboevolution.html.dom.smil.ElementTargetAttributes;
import org.loboevolution.html.dom.smil.TimeList;
import org.loboevolution.laf.ColorFactory;
import org.loboevolution.svg.SVGTransform;
import org.loboevolution.svg.smil.TimeImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.StringTokenizer;

/**
 * <p>SVGAnimateImpl class.</p>
 */
public class SVGAnimateImpl extends JComponent implements ActionListener {

	@Serial
    private static final long serialVersionUID = 1L;

	private int counter;
	
	private int f_red = 0;
	
	private int f_green = 0;
	
	private int f_blue = 0;
	
	private int t_red = 0;
	
	private int t_green = 0;
	
	private int t_blue = 0;
	
	private float from;
	
	private float to;

	private float sxFrom = 0;

	private float sxTo = 0;

	private float syFrom = 0;

	private float syTo = 0;

	private float txFrom = 0;

	private float tyFrom = 0;

	private float txTo = 0;

	private float tyTo = 0;
	
	private float angleFrom = 0;

	private float cxFrom = 0;

	private float cyFrom = 0;

	private float angleTo = 0;

	private float cxTo = 0;

	private float cyTo = 0;
		
	private long dur;
	
	String from_trans = "";

	String to_trans = "";
	
	private Timer timer;
	
	private final SVGElementImpl elem;
	
	private final SVGAnimateElementImpl animate;
		
	/**
	 * <p>Constructor for SVGAnimateImpl.</p>
	 *
	 * @param elem a {@link SVGElementImpl} object.
	 * @param animate a {@link SVGAnimateElementImpl} object.
	 */
	public SVGAnimateImpl(final SVGElementImpl elem, final SVGAnimateElementImpl animate) {
		this.elem = elem;
		this.animate = animate;
		final SVGSVGElementImpl ownerSVGElement = ( SVGSVGElementImpl) elem.getOwnerSVGElement();
		if (!ownerSVGElement.isPainted()) {
			dur = System.currentTimeMillis();
			timer = new Timer(timerDelay(animate), this);
			timer.setInitialDelay(begin(animate));
			timer.start();
		}
	}

	/** {@inheritDoc} */
	public void actionPerformed(final ActionEvent e) {
		final String attribute = animate.getAttributeName().toLowerCase();
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
			animateSize(elem, attribute);
			break;
		case "fill":
		case "stroke":
			animateColor(elem);
			break;
		case "transform":
			animateTransform(elem);
			break;
		default:
			break;
		}
	}

	private void animateSize(final SVGElementImpl elem, final String type) {
		if (counter == 0) {
			from = Float.parseFloat(animate.getFrom());
			to = Float.parseFloat(animate.getTo());
		}

		if (from > to) {
			from--;
		}

		if (to > from) {
			from++;	
		}

		elem.setAttribute(type, String.valueOf(from));		
		
		if (animate.getDur() > 0 && animate.getDur() <= (System.currentTimeMillis() - dur)) {
			timer.stop();
		}

		if (animate.getDur() == 0 && (from == to)) {
			timer.stop();
		}

		counter++;
	}
	
	private void animateColor(final SVGElementImpl elem) {
		if (counter == 0) {
			final Color from_color = ColorFactory.getInstance().getColor(animate.getFrom());
			final Color to_color = ColorFactory.getInstance().getColor(animate.getTo());
			
			f_red = from_color.getRed();
			f_green = from_color.getGreen();
			f_blue = from_color.getBlue();

			t_red = to_color.getRed();
			t_green = to_color.getGreen();
			t_blue = to_color.getBlue();
		}

		if (f_red > t_red)
			f_red--;

		if (f_red < t_red)
			f_red++;

		if (f_green > t_green)
			f_green--;

		if (f_green < t_green)
			f_green++;

		if (f_blue > t_blue)
			f_blue--;

		if (f_blue < t_blue)
			f_blue++;

		final String rgb = "rgb(" + f_red + "," + f_green + "," + f_blue + ")";
		elem.setAttribute("fill", rgb);
		
		final boolean end = (++counter > 255);
				
		if (animate.getDur() > 0 && animate.getDur() <= (System.currentTimeMillis() - dur)) {
			timer.stop();
		}
		
		if (animate.getDur() == 0 && end) {
			timer.stop();
		}
	}
	
	
	
	private void animateTransform(final SVGElementImpl elem) {
		String transformString = "";
		if (counter == 0) {
			from_trans = animate.getFrom();
			to_trans = animate.getTo();
		}
		
		final StringTokenizer stFrom = new StringTokenizer(from_trans, " ,");
		final StringTokenizer stTo = new StringTokenizer(to_trans, " ,");

		switch (animate.getType()) {
		case SVGTransform.SVG_TRANSFORM_TRANSLATE:

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

			from_trans = txFrom + ", " + tyFrom;
			transformString = "translate(" + from_trans + ")";
			break;
		case SVGTransform.SVG_TRANSFORM_SCALE:

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
				to_trans = String.valueOf(Float.parseFloat(to_trans));
			} else {
				from_trans = sxFrom + ", " + syFrom;
			}

			transformString = "scale(" + from_trans + ")";
			break;
		case SVGTransform.SVG_TRANSFORM_ROTATE:

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

			from_trans = angleFrom + ", " + cxFrom + ", " + cyFrom;
			transformString = "rotate(" + from_trans + ")";
			break;
		case SVGTransform.SVG_TRANSFORM_SKEWX:

			sxFrom = Float.parseFloat(from_trans);
			sxTo = Float.parseFloat(to_trans);

			if (sxFrom > sxTo)
				sxFrom--;

			if (sxFrom < sxTo)
				sxFrom++;

			transformString = "skewX(" + sxFrom + ")";
			break;
		case SVGTransform.SVG_TRANSFORM_SKEWY:

			sxFrom = Float.parseFloat(from_trans);
			sxTo = Float.parseFloat(to_trans);

			if (sxFrom > sxTo)
				sxFrom--;

			if (sxFrom < sxTo)
				sxFrom++;
			transformString = "skewY(" + sxFrom + ")";
			break;
		default:
			break;
		}

		elem.setAttribute("transform", transformString);
		if (animate.getDur() > 0 && animate.getDur() <= (System.currentTimeMillis() - dur)) {
			timer.stop();
		}
		counter++;
	}
	
	/**
	 * <p>restart.</p>
	 */
	public void restart() {
		final SVGSVGElementImpl ownerSVGElement = ( SVGSVGElementImpl) elem.getOwnerSVGElement();
		ownerSVGElement.setPainted(false);
		new SVGAnimateImpl(elem, animate);
	}
	
	
	/**
	 * <p>timerDelay.</p>
	 *
	 * @param animate a {@link SVGAnimationImpl} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public int timerDelay(final SVGAnimationImpl animate) {
		if (animate.getDur() == 0)
			return 5;

		if ("transform".equalsIgnoreCase(animate.getAttributeName())) {
			return timeDelayTransform(animate);
		} else if (ElementTargetAttributes.ATTRIBUTE_TYPE_XML == animate.getAttributeType()) {
			return timerDelayFloat(animate);
		} else {
			return timerDelayColor(animate);
		}
	}
	
	private int begin(final SVGAnimationImpl animate) {
		final TimeList begin = animate.getBegin();
		final TimeImpl time = (TimeImpl)begin.item(0);
		return Double.valueOf(time.getResolvedOffset()).intValue();
	}

	private int timerDelayFloat(final SVGAnimationImpl animate) {
		final float dur = animate.getDur();
		final float from = Float.parseFloat(animate.getFrom());
		final float to = Float.parseFloat(animate.getTo());
		final float range = to - from;
		return Math.round(dur / range);
	}

	private int timerDelayColor(final SVGAnimationImpl animate) {
		final float dur = animate.getDur();
		final Color from = ColorFactory.getInstance().getColor(animate.getFrom());
		final Color to = ColorFactory.getInstance().getColor(animate.getTo());
		final float range = (to.getRed() - from.getRed()) + (to.getBlue() - from.getBlue()) + (to.getGreen() - from.getGreen());
		if (range == 0) return 255;
		return Math.round(dur / range);
	}

	private int timeDelayTransform(final SVGAnimationImpl animate) {
		final float dur = animate.getDur();
		float range;
		float sxFrom;
		float sxTo;
		final String from_trans = animate.getFrom();
		final String to_trans = animate.getTo();
		final StringTokenizer stFrom = new StringTokenizer(from_trans, " ,");
		StringTokenizer stTo;
		switch (animate.getAttributeType()) {
		case SVGTransform.SVG_TRANSFORM_TRANSLATE:
			float txFrom = 0;
			float tyFrom = 0;
			if (stFrom.countTokens() == 1) {
				txFrom = Float.parseFloat(stFrom.nextToken());
			} else if (stFrom.countTokens() == 2) {
				txFrom = Float.parseFloat(stFrom.nextToken());
				tyFrom = Float.parseFloat(stFrom.nextToken());
			}

			stTo = new StringTokenizer(to_trans, " ,");
			float txTo = 0;
			float tyTo = 0;
			if (stTo.countTokens() == 1) {
				txTo = Float.parseFloat(stTo.nextToken());
			} else if (stTo.countTokens() == 2) {
				txTo = Float.parseFloat(stTo.nextToken());
				tyTo = Float.parseFloat(stTo.nextToken());
			}

			range = (txTo - txFrom) + (tyTo - tyFrom);
			return Math.round(dur / range);
		case SVGTransform.SVG_TRANSFORM_SCALE:
			sxFrom = 0;
			float syFrom = 0;
			if (stFrom.countTokens() == 1) {
				sxFrom = Float.parseFloat(stFrom.nextToken());
			} else if (stFrom.countTokens() == 2) {
				sxFrom = Float.parseFloat(stFrom.nextToken());
				syFrom = Float.parseFloat(stFrom.nextToken());
			}

			stTo = new StringTokenizer(to_trans, " ,");
			sxTo = 0;
			float syTo = 0;
			if (stTo.countTokens() == 1) {
				sxTo = Float.parseFloat(stTo.nextToken());
			} else if (stTo.countTokens() == 2) {
				sxTo = Float.parseFloat(stTo.nextToken());
				syTo = Float.parseFloat(stTo.nextToken());
			}

			range = (sxTo - sxFrom) + (syTo - syFrom);
			return Math.round(dur / range);
		case SVGTransform.SVG_TRANSFORM_ROTATE:

			float cxFrom = 0;
			float cyFrom = 0;

			if (stFrom.countTokens() == 3) {
				cxFrom = Float.parseFloat(stFrom.nextToken());
				cyFrom = Float.parseFloat(stFrom.nextToken());
			}

			stTo = new StringTokenizer(to_trans, " ,");
			float cxTo = 0;
			float cyTo = 0;

			if (stTo.countTokens() == 3) {
				cxTo = Float.parseFloat(stTo.nextToken());
				cyTo = Float.parseFloat(stTo.nextToken());
			}

			range = (cxTo - cxFrom) + (cyTo - cyFrom);
			return Math.round(dur / range);
		case SVGTransform.SVG_TRANSFORM_SKEWX:
            case SVGTransform.SVG_TRANSFORM_SKEWY:
                sxFrom = Float.parseFloat(from_trans);
			sxTo = Float.parseFloat(to_trans);
			range = sxTo - sxFrom;
			return Math.round(dur / range);
            default:
			return 0;}
	}
}
