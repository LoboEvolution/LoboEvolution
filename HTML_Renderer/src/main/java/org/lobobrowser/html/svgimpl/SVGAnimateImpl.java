/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.html.svgimpl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.html.control.RUIControl;
import org.lobobrowser.html.info.SVGInfo;
import org.lobobrowser.w3c.smil.ElementTargetAttributes;

public class SVGAnimateImpl extends JComponent implements ActionListener {

	private static final long serialVersionUID = 1L;
	protected static final Logger logger = LogManager.getLogger(SVGAnimateImpl.class.getName());
	private Timer timer;
	private SVGInfo info;
	private RUIControl ruicontrol;
	private float from_xml;
	private float to_xml;

	public SVGAnimateImpl(SVGInfo info, RUIControl ruicontrol) {
		this.info = info;
		this.ruicontrol = ruicontrol;
		timer = new Timer(1, this);
		timer.setInitialDelay(0);
		timer.setCoalesce(true);
		startAnimation();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SVGAnimateElementImpl animate = info.getAnimate();
		switch (animate.getAttributeName().toLowerCase()) {
		case "width":
			animateWidth();
			break;
		case "heigth":
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
		case "x2":
			animateX2();
		case "y1":
			animateY1();
		case "y2":
			animateY2();
		case "r":
			animateR();
			break;
		default:
			break;
		}
	}

	private void animateWidth() {
		if (info.getWidth() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setWidth(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateHeight() {
		if (info.getHeight() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setHeight(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateX() {
		if (info.getX() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setX(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateY() {
		if (info.getY() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setY(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateR() {
		if (info.getR() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setR(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateX1() {
		if (info.getX1() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setX1(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateX2() {
		if (info.getX2() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setX2(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateY1() {
		if (info.getY1() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setY1(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateY2() {
		if (info.getY2() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setY2(from_xml);
			ruicontrol.relayout();
		}
	}

	private void startAnimation() {
		if (!timer.isRunning()) {
			SVGAnimateElementImpl animate = info.getAnimate();

			if (ElementTargetAttributes.ATTRIBUTE_TYPE_XML == animate.getAttributeType()) {
				from_xml = Float.parseFloat(animate.getFrom());
				to_xml = Float.parseFloat(animate.getTo());
			}
			timer.start();
		}
	}

	private void stopAnimation() {
		if (timer.isRunning()) {
			timer.stop();
		}
	}
}
