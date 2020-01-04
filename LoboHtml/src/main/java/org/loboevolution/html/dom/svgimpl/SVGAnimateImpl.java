package org.loboevolution.html.dom.svgimpl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.JComponent;
import javax.swing.Timer;

import org.loboevolution.html.dom.smil.ElementTargetAttributes;
import org.loboevolution.html.dom.smil.TimeList;
import org.loboevolution.html.dom.svg.SVGTransform;
import org.loboevolution.laf.ColorFactory;

public class SVGAnimateImpl extends JComponent implements ActionListener {

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
	
	private Timer timer;
	
	private SVGElementImpl elem;
	
	private SVGAnimateElementImpl animate;
	
	public SVGAnimateImpl(SVGElementImpl elem, SVGAnimateElementImpl animate) {
		this.elem = elem;
		this.animate = animate;
		SVGSVGElementImpl ownerSVGElement = (SVGSVGElementImpl) elem.getOwnerSVGElement();
		if (!ownerSVGElement.isPainted()) {
			timer = new Timer(timerDelay(animate), this);
			timer.setInitialDelay(begin(animate));
			timer.start();
		}
	}

	public void actionPerformed(ActionEvent e) {
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

	private void animateSize(SVGElementImpl elem, String type) {
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
		if (from == to) {
			timer.stop();
		}
		counter++;
	}
	
	private void animateColor(SVGElementImpl elem) {
		if (counter == 0) {
			Color from_color = Color.RED;
			Color to_color = Color.GREEN;

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

		String rgb = "rgb(" + f_red + "," + f_green + "," + f_blue + ")";
		elem.setAttribute("fill", rgb);
		
		if (++counter > 255) {
			timer.stop();
		}
	}
	
	private void animateTransform(SVGElementImpl elem) {
		// TODO Auto-generated method stub
		
	}
	
	
	public int timerDelay(SVGAnimationImpl animate) {
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
	
	private int begin(SVGAnimationImpl animate){
		TimeList begin = animate.getBegin();
		TimeImpl time = (TimeImpl)begin.item(0);
		return Double.valueOf(time.getResolvedOffset()).intValue();
	}

	private int timerDelayFloat(SVGAnimationImpl animate) {
		float dur = animate.getDur();
		float from = Float.parseFloat(animate.getFrom());
		float to = Float.parseFloat(animate.getTo());
		float range = to - from;
		return Math.round(dur / range);
	}

	private int timerDelayColor(SVGAnimationImpl animate) {
		float dur = animate.getDur();
		Color from = ColorFactory.getInstance().getColor(animate.getFrom());
		Color to = ColorFactory.getInstance().getColor(animate.getTo());
		float range = (to.getRed() - from.getRed()) + (to.getBlue() - from.getBlue()) + (to.getGreen() - from.getGreen());
		if(range == 0) return 255;
		return Math.round(dur / range);
	}

	private int timeDelayTransform(SVGAnimationImpl animate) {
		float dur = animate.getDur();
		float range = 0;
		float sxFrom = 0;
		float sxTo = 0;
		String from_trans = animate.getFrom();
		String to_trans = animate.getTo();
		StringTokenizer stFrom = new StringTokenizer(from_trans, " ,");
		StringTokenizer stTo = null;
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
			sxFrom = Float.parseFloat(from_trans);
			sxTo = Float.parseFloat(to_trans);
			range = sxTo - sxFrom;
			return Math.round(dur / range);
		case SVGTransform.SVG_TRANSFORM_SKEWY:
			sxFrom = Float.parseFloat(from_trans);
			sxTo = Float.parseFloat(to_trans);
			range = sxTo - sxFrom;
			return Math.round(dur / range);
		default:
			return 0;}
	}
}
