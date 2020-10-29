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

/**
 * <p>SVGAnimateImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
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
	
	private SVGElementImpl elem;
	
	private SVGAnimateElementImpl animate;
		
	/**
	 * <p>Constructor for SVGAnimateImpl.</p>
	 *
	 * @param elem a {@link org.loboevolution.html.dom.svgimpl.SVGElementImpl} object.
	 * @param animate a {@link org.loboevolution.html.dom.svgimpl.SVGAnimateElementImpl} object.
	 */
	public SVGAnimateImpl(SVGElementImpl elem, SVGAnimateElementImpl animate) {
		this.elem = elem;
		this.animate = animate;
		SVGSVGElementImpl ownerSVGElement = (SVGSVGElementImpl) elem.getOwnerSVGElement();
		if (!ownerSVGElement.isPainted()) {
			dur = System.currentTimeMillis();
			timer = new Timer(timerDelay(animate), this);
			timer.setInitialDelay(begin(animate));
			timer.start();
		}
	}

	/** {@inheritDoc} */
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
		
		if(animate.getDur() > 0 && animate.getDur() <= (System.currentTimeMillis() - dur)){
			timer.stop();
		}

		if (animate.getDur() == 0 && (from == to)) {
			timer.stop();
		}

		counter++;
	}
	
	private void animateColor(SVGElementImpl elem) {
		if (counter == 0) {
			Color from_color = ColorFactory.getInstance().getColor(animate.getFrom());
			Color to_color = ColorFactory.getInstance().getColor(animate.getTo());
			
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
		
		boolean end = (++counter > 255);
				
		if(animate.getDur() > 0 && animate.getDur() <= (System.currentTimeMillis() - dur)){
			timer.stop();
		}
		
		if (animate.getDur() == 0 && end) {
			timer.stop();
		}
	}
	
	
	
	private void animateTransform(SVGElementImpl elem) {
		String transformString = "";
		if (counter == 0) {
			from_trans = animate.getFrom();
			to_trans = animate.getTo();
		}
		
		StringTokenizer stFrom = new StringTokenizer(from_trans, " ,");
		StringTokenizer stTo = new StringTokenizer(to_trans, " ,");

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
		if(animate.getDur() > 0 && animate.getDur() <= (System.currentTimeMillis() - dur)){
			timer.stop();
		}
		counter++;
	}
	
	/**
	 * <p>restart.</p>
	 */
	public void restart() {
		SVGSVGElementImpl ownerSVGElement = (SVGSVGElementImpl) elem.getOwnerSVGElement();
		ownerSVGElement.setPainted(false);
		new SVGAnimateImpl(elem, animate);
	}
	
	
	/**
	 * <p>timerDelay.</p>
	 *
	 * @param animate a {@link org.loboevolution.html.dom.svgimpl.SVGAnimationImpl} object.
	 * @return a int.
	 */
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
            case SVGTransform.SVG_TRANSFORM_SKEWY:
                sxFrom = Float.parseFloat(from_trans);
			sxTo = Float.parseFloat(to_trans);
			range = sxTo - sxFrom;
			return Math.round(dur / range);
            default:
			return 0;}
	}
}
