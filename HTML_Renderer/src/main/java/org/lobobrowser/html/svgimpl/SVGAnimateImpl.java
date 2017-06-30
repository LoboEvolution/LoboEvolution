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

import javax.swing.JComponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.html.control.RUIControl;
import org.lobobrowser.html.info.SVGInfo;

public class SVGAnimateImpl extends JComponent implements Runnable {

	private static final long serialVersionUID = 1L;
	protected static final Logger logger = LogManager.getLogger(SVGAnimateImpl.class.getName());
	private Thread runner;
	private SVGInfo info;
	private RUIControl ruicontrol;

	public SVGAnimateImpl(SVGInfo info, RUIControl ruicontrol) {
		this.info = info;
		runner = new Thread(this);
		runner.start();
		this.ruicontrol = ruicontrol;
	}

	@Override
	public void run() {
		SVGAnimateElementImpl animate = info.getAnimate();
		switch (animate.getAttributeName().toLowerCase()) {
		case "width":
			animeteWidth(info, animate);
			break;
		case "heigth":
			animeHeigth(info, animate);
			break;
		case "y":
		case "cy":
			animeY(info, animate);
			break;
		case "x":
		case "cx":
			animeX(info, animate);
			break;
		case "x1":
			animeX1(info, animate);
		case "x2":
			animeX2(info, animate);
		case "y1":
			animeY1(info, animate);
		case "y2":
			animeY2(info, animate);
		case "r":
			animeR(info, animate);
			break;
		default:
			break;
		}
	}

	private void animeteWidth(SVGInfo info, SVGAnimateElementImpl animate) {

		float from = Float.parseFloat(animate.getFrom());
		float to = Float.parseFloat(animate.getTo());
		info.setWidth(from);
		for (float i = .001f; i < 1f; i += .001f) {
			ruicontrol.relayout();
			info.setWidth(info.getWidth() + i);
			try {
				Thread.sleep(5);
			} catch (InterruptedException ex) {
			}

			if (info.getWidth() > to) {
				Thread.currentThread().interrupt();
				info.setWidth(to);

			}
		}
	}

	private void animeHeigth(SVGInfo info, SVGAnimateElementImpl animate) {
		float from = Float.parseFloat(animate.getFrom());
		float to = Float.parseFloat(animate.getTo());
		info.setHeight(from);
		for (float i = .001f; i < 1f; i += .001f) {
			ruicontrol.relayout();
			info.setHeight(info.getHeight() + i);
			try {
				Thread.sleep(5);
			} catch (InterruptedException ex) {
			}

			if (info.getHeight() > to) {
				Thread.currentThread().interrupt();
				info.setHeight(to);
			}
		}
	}

	private void animeX(SVGInfo info, SVGAnimateElementImpl animate) {
		float from = Float.parseFloat(animate.getFrom());
		float to = Float.parseFloat(animate.getTo());
		info.setX(from);
		for (float i = .001f; i < 1f; i += .001f) {
			ruicontrol.relayout();
			info.setX(info.getX() + i);
			try {
				Thread.sleep(5);
			} catch (InterruptedException ex) {
			}

			if (info.getX() > to) {
				Thread.currentThread().interrupt();
				info.setX(to);
			}
		}
	}

	private void animeY(SVGInfo info, SVGAnimateElementImpl animate) {
		float from = Float.parseFloat(animate.getFrom());
		float to = Float.parseFloat(animate.getTo());
		info.setY(from);
		for (float i = .001f; i < 1f; i += .001f) {
			ruicontrol.relayout();
			info.setY(info.getY() + i);
			try {
				Thread.sleep(5);
			} catch (InterruptedException ex) {
			}

			if (info.getY() > to) {
				Thread.currentThread().interrupt();
				info.setY(to);
			}
		}
	}

	private void animeR(SVGInfo info, SVGAnimateElementImpl animate) {
		float from = Float.parseFloat(animate.getFrom());
		float to = Float.parseFloat(animate.getTo());
		info.setR(from);
		for (float i = .001f; i < 1f; i += .001f) {
			ruicontrol.relayout();
			info.setR(info.getR() + i);
			try {
				Thread.sleep(5);
			} catch (InterruptedException ex) {
			}

			if (info.getR() > to) {
				Thread.currentThread().interrupt();
				info.setR(to);
			}
		}
	}

	private void animeX1(SVGInfo info, SVGAnimateElementImpl animate) {
		float from = Float.parseFloat(animate.getFrom());
		float to = Float.parseFloat(animate.getTo());
		info.setR(from);
		for (float i = .001f; i < 1f; i += .001f) {
			ruicontrol.relayout();
			info.setX1(info.getX1() + i);
			try {
				Thread.sleep(5);
			} catch (InterruptedException ex) {
			}

			if (info.getX1() > to) {
				Thread.currentThread().interrupt();
				info.setX1(to);
			}
		}
	}

	private void animeX2(SVGInfo info, SVGAnimateElementImpl animate) {
		float from = Float.parseFloat(animate.getFrom());
		float to = Float.parseFloat(animate.getTo());
		info.setR(from);
		for (float i = .001f; i < 1f; i += .001f) {
			ruicontrol.relayout();
			info.setX2(info.getX2() + i);
			try {
				Thread.sleep(5);
			} catch (InterruptedException ex) {
			}

			if (info.getX2() > to) {
				Thread.currentThread().interrupt();
				info.setX2(to);
			}
		}
	}

	private void animeY1(SVGInfo info, SVGAnimateElementImpl animate) {
		float from = Float.parseFloat(animate.getFrom());
		float to = Float.parseFloat(animate.getTo());
		info.setY1(from);
		for (float i = .001f; i < 1f; i += .001f) {
			ruicontrol.relayout();
			info.setY1(info.getY1() + i);
			try {
				Thread.sleep(5);
			} catch (InterruptedException ex) {
			}

			if (info.getY1() > to) {
				Thread.currentThread().interrupt();
				info.setR(to);
			}
		}
	}

	private void animeY2(SVGInfo info, SVGAnimateElementImpl animate) {
		float from = Float.parseFloat(animate.getFrom());
		float to = Float.parseFloat(animate.getTo());
		info.setY2(from);
		for (float i = .001f; i < 1f; i += .001f) {
			ruicontrol.relayout();
			info.setY2(info.getY2() + i);
			try {
				Thread.sleep(5);
			} catch (InterruptedException ex) {
			}

			if (info.getY2() > to) {
				Thread.currentThread().interrupt();
				info.setY2(to);
			}
		}
	}
}
