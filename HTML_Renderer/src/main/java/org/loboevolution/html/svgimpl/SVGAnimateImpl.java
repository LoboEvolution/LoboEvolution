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

package org.loboevolution.html.svgimpl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.JComponent;
import javax.swing.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.color.ColorFactory;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.info.SVGInfo;
import org.loboevolution.w3c.smil.ElementTargetAttributes;
import org.loboevolution.w3c.svg.SVGTransform;
import org.loboevolution.w3c.svg.SVGTransformList;

public class SVGAnimateImpl extends JComponent implements ActionListener {

	private static final long serialVersionUID = 1L;
	protected static final Logger logger = LogManager.getLogger(SVGAnimateImpl.class);
	private Timer timer;
	private SVGInfo info;
	private RUIControl ruicontrol;
	private transient SVGAnimationImpl animate;
	private float from_xml;
	private float to_xml;
	private String from_trans;
	private String to_trans;
	private Color from_color;
	private Color to_color;
	private int count;
	private long repeatDuration;

	public SVGAnimateImpl(SVGInfo info, RUIControl ruicontrol) {
		this.info = info;
		this.ruicontrol = ruicontrol;
		this.animate = info.getAnimate();
		count = 0;
		int time = SVGUtility.timerDelay(animate);
		timer = new Timer(time, this);
		timer.setInitialDelay(SVGUtility.begin(animate));
		startAnimation();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (this.animate.getAttributeName().toLowerCase()) {
		case "width":
			animateWidth();
			break;
		case "height":
			animateHeight();
			break;
		case "y":
		case "cy":
			animateY();
			break;
		case "x":
		case "cx":
			animateX();
			break;
		case "x1":
			animateX1();
			break;
		case "x2":
			animateX2();
			break;
		case "y1":
			animateY1();
			break;
		case "y2":
			animateY2();
			break;
		case "r":
			animateR();
			break;
		case "fill":
		case "stroke":
			animate();
			break;
		case "transform":
			animateTransform();
			break;
		default:
			break;
		}
	}
	
	private void animateWidth() {
		
		if(this.animate.getRepeatDur() <= (System.currentTimeMillis() - repeatDuration)){
			stopAnimation();
		}
		
		if (from_xml >= to_xml) {
			if (this.animate.getRepeatCount() == count) {
				stopAnimation();
			} else {
				count++;
				from_xml = Float.parseFloat(this.animate.getFrom());
				info.setWidth(from_xml);
			}
		
		} else {
			from_xml++;
			info.setWidth(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateHeight() {
		
		if(this.animate.getRepeatDur() <= (System.currentTimeMillis() - repeatDuration)){
			stopAnimation();
		}
		
		if (from_xml >= to_xml) {
			if (this.animate.getRepeatCount() == count) {
				stopAnimation();
			} else {
				count++;
				from_xml = Float.parseFloat(this.animate.getFrom());
				info.setHeight(from_xml);
			}
		} else {
			from_xml++;
			info.setHeight(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateX() {
		
		if(this.animate.getRepeatDur() <= (System.currentTimeMillis() - repeatDuration)){
			stopAnimation();
		}
		
		if (from_xml >= to_xml) {
			if (this.animate.getRepeatCount() == count) {
				stopAnimation();
			} else {
				count++;
				from_xml = Float.parseFloat(this.animate.getFrom());
				info.setX(from_xml);
			}
		} else {
			from_xml++;
			info.setX(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateY() {
		
		if(this.animate.getRepeatDur() <= (System.currentTimeMillis() - repeatDuration)){
			stopAnimation();
		}
		
		if (from_xml >= to_xml) {
			if (this.animate.getRepeatCount() == count) {
				stopAnimation();
			} else {
				count++;
				from_xml = Float.parseFloat(this.animate.getFrom());
				info.setY(from_xml);
			}
		} else {
			from_xml++;
			info.setY(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateR() {
		
		if(this.animate.getRepeatDur() <= (System.currentTimeMillis() - repeatDuration)){
			stopAnimation();
		}
		
		if (from_xml >= to_xml) {
			if (this.animate.getRepeatCount() == count) {
				stopAnimation();
			} else {
				count++;
				from_xml = Float.parseFloat(this.animate.getFrom());
				info.setR(from_xml);
			}
		} else {
			from_xml++;
			info.setR(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateX1() {
		
		if(this.animate.getRepeatDur() <= (System.currentTimeMillis() - repeatDuration)){
			stopAnimation();
		}
		
		if (from_xml >= to_xml) {
			if (this.animate.getRepeatCount() == count) {
				stopAnimation();
			} else {
				count++;
				from_xml = Float.parseFloat(this.animate.getFrom());
				info.setX1(from_xml);
			}
		} else {
			from_xml++;
			info.setX1(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateX2() {
		
		if(this.animate.getRepeatDur() <= (System.currentTimeMillis() - repeatDuration)){
			stopAnimation();
		}
		
		if (from_xml >= to_xml) {
			if (this.animate.getRepeatCount() == count) {
				stopAnimation();
			} else {
				count++;
				from_xml = Float.parseFloat(this.animate.getFrom());
				info.setX2(from_xml);
			}
		} else {
			from_xml++;
			info.setX2(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateY1() {
		
		if(this.animate.getRepeatDur() <= (System.currentTimeMillis() - repeatDuration)){
			stopAnimation();
		}
		
		if (from_xml >= to_xml) {
			if (this.animate.getRepeatCount() == count) {
				stopAnimation();
			} else {
				count++;
				from_xml = Float.parseFloat(this.animate.getFrom());
				info.setY1(from_xml);
			}
		} else {
			from_xml++;
			info.setY1(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateY2() {
		
		if(this.animate.getRepeatDur() <= (System.currentTimeMillis() - repeatDuration)){
			stopAnimation();
		}
		
		if (from_xml >= to_xml) {
			if (this.animate.getRepeatCount() == count) {
				stopAnimation();
			} else {
				count++;
				from_xml = Float.parseFloat(this.animate.getFrom());
				info.setY2(from_xml);
			}
		} else {
			from_xml++;
			info.setY2(from_xml);
			ruicontrol.relayout();
		}
	}
	
	private void animate() {

		if(this.animate.getRepeatDur() <= (System.currentTimeMillis() - repeatDuration)){
			stopAnimation();
		}
		
		if (from_color.getRGB() == to_color.getRGB()) {
			if (this.animate.getRepeatCount() == count) {
				stopAnimation();
			} else {
				count++;
				from_color = ColorFactory.getInstance().getColor(this.animate.getFrom());
				info.getStyle().setFill("rgb(" + from_color.getRed() + "," + from_color.getGreen() + "," + from_color.getBlue() + ")");
			}
		} else {

			int f_red = from_color.getRed();
			int f_green = from_color.getGreen();
			int f_blue = from_color.getBlue();

			int t_red = to_color.getRed();
			int t_green = to_color.getGreen();
			int t_blue = to_color.getBlue();

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
			from_color = ColorFactory.getInstance().getColor(rgb);
			info.getStyle().setStroke(rgb);
			ruicontrol.relayout();
		}
	}
	
	private void animateTransform() {
				
		if(this.animate.getRepeatDur() <= (System.currentTimeMillis() - repeatDuration)){
			stopAnimation();
		}
		
		if (from_trans.equals(to_trans)) {
			if (this.animate.getRepeatCount() == count) {
				stopAnimation();
			} else {
				count++;
				from_trans = animate.getFrom();
			}
		} else {
			String transformString = "";
			SVGAnimationImpl animate = info.getAnimate();
			StringTokenizer stFrom = new StringTokenizer(from_trans, " ,");
			
			if (animate.getType() == SVGTransform.SVG_TRANSFORM_TRANSLATE) {

				float txFrom = 0;
				float tyFrom = 0;
				if (stFrom.countTokens() == 1) {
					txFrom = Float.parseFloat(stFrom.nextToken());
				} else if (stFrom.countTokens() == 2) {
					txFrom = Float.parseFloat(stFrom.nextToken());
					tyFrom = Float.parseFloat(stFrom.nextToken());
				}

				StringTokenizer stTo = new StringTokenizer(to_trans, " ,");
				float txTo = 0;
				float tyTo = 0;
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
				to_trans = tyFrom + ", " + tyTo;
				transformString = "translate(" + from_trans + ")";

			} else if (animate.getType() == SVGTransform.SVG_TRANSFORM_SCALE) {

				float sxFrom = 0;
				float syFrom = 0;
				if (stFrom.countTokens() == 1) {
					sxFrom = Float.parseFloat(stFrom.nextToken());
				} else if (stFrom.countTokens() == 2) {
					sxFrom = Float.parseFloat(stFrom.nextToken());
					syFrom = Float.parseFloat(stFrom.nextToken());
				}

				StringTokenizer stTo = new StringTokenizer(to_trans, " ,");
				float sxTo = 0;
				float syTo = 0;
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
				
				if(syFrom == 0){
					from_trans = String.valueOf(sxFrom);
					to_trans = String.valueOf(Float.parseFloat(to_trans));
				} else{
					from_trans = sxFrom + ", " + syFrom;
					to_trans = syFrom + ", " + syTo;
				}

				transformString = "scale(" + from_trans + ")";
				
			} else if (animate.getType() == SVGTransform.SVG_TRANSFORM_ROTATE) {

				float angleFrom = 0;
				float cxFrom = 0;
				float cyFrom = 0;
				
				if (stFrom.countTokens() == 1) {
					angleFrom = Float.parseFloat(stFrom.nextToken());
				} else if (stFrom.countTokens() == 3) {
					angleFrom = Float.parseFloat(stFrom.nextToken());
					cxFrom = Float.parseFloat(stFrom.nextToken());
					cyFrom = Float.parseFloat(stFrom.nextToken());
				}

				StringTokenizer stTo = new StringTokenizer(to_trans, " ,");
				float angleTo = 0;
				float cxTo = 0;
				float cyTo = 0;
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
				to_trans = angleTo + ", " + cxTo + ", " + cyTo;
				transformString = "rotate(" + from_trans + ")";

			} else if (animate.getType() == SVGTransform.SVG_TRANSFORM_SKEWX) {

				float sxFrom = Float.parseFloat(from_trans);
				float sxTo = Float.parseFloat(to_trans);

				if (sxFrom > sxTo)
					sxFrom--;

				if (sxFrom < sxTo)
					sxFrom++;
				
				from_trans = String.valueOf(sxFrom);
				to_trans = String.valueOf(sxTo);
				transformString = "skewX(" + sxFrom + ")";

			} else if (animate.getType() == SVGTransform.SVG_TRANSFORM_SKEWY) {

				float sxFrom = Float.parseFloat(from_trans);
				float sxTo = Float.parseFloat(to_trans);

				if (sxFrom > sxTo)
					sxFrom--;

				if (sxFrom < sxTo)
					sxFrom++;
				
				from_trans = String.valueOf(sxFrom);
				to_trans = String.valueOf(sxTo);
				transformString = "skewY(" + sxFrom + ")";
			}
			
			SVGTransformList transformList = SVGTransformListImpl.createTransformList(transformString);
			info.setTransformList(transformList);
			ruicontrol.relayout();
		}
	}
	
	private void startAnimation() {
		if (!timer.isRunning()) {
			SVGAnimationImpl animate = info.getAnimate();
			if ("transform".equalsIgnoreCase(animate.getAttributeName())) {
				from_trans = animate.getFrom();
				to_trans = animate.getTo();
			} else if (ElementTargetAttributes.ATTRIBUTE_TYPE_XML == animate.getAttributeType()) {
				from_xml = Float.parseFloat(animate.getFrom());
				to_xml = Float.parseFloat(animate.getTo());
			} else {
				from_color = ColorFactory.getInstance().getColor(animate.getFrom());
				to_color = ColorFactory.getInstance().getColor(animate.getTo());
			}
			repeatDuration = System.currentTimeMillis();
			timer.start();
		}
	}

	private void stopAnimation() {
		if (timer.isRunning()) {
			timer.stop();
		}
	}
}
