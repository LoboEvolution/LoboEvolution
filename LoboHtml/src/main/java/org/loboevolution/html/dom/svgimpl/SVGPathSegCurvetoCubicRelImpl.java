package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegCurvetoCubicRel;

public class SVGPathSegCurvetoCubicRelImpl implements SVGPathSegCurvetoCubicRel {

	private float x;

	private float y;

	private float x1;

	private float y1;

	private float x2;

	private float y2;

	public SVGPathSegCurvetoCubicRelImpl(float x, float y, float x1, float y1, float x2, float y2) {
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public short getPathSegType() {
		return PATHSEG_CURVETO_CUBIC_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "c";
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public float getX1() {
		return x1;
	}

	@Override
	public void setX1(float x1) {
		this.x1 = x1;
	}

	@Override
	public float getY1() {
		return y1;
	}

	@Override
	public void setY1(float y1) {
		this.y1 = y1;
	}

	@Override
	public float getX2() {
		return x2;
	}

	@Override
	public void setX2(float x2) {
		this.x2 = x2;
	}

	@Override
	public float getY2() {
		return y2;
	}

	@Override
	public void setY2(float y2) {
		this.y2 = y2;
	}
}