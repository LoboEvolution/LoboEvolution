package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegArcRel;

public class SVGPathSegArcRelImpl implements SVGPathSegArcRel {
	
	private float x;
	
	private float y;
	
	private float r1;
	
	private float r2;
	
	private float angle;
	
	private boolean largeArcFlag;
	
	private boolean sweepFlag;

	public SVGPathSegArcRelImpl(float x, float y, float r1, float r2, float angle, boolean largeArcFlag, boolean sweepFlag) {
		this.x = x;
		this.y = y;
		this.r1 = r1;
		this.r2 = r2;
		this.angle = angle;
		this.largeArcFlag = largeArcFlag;
		this.sweepFlag = sweepFlag;
	}

	@Override
	public short getPathSegType() {
		return PATHSEG_ARC_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		return "a";
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
	public float getR1() {
		return r1;
	}

	@Override
	public void setR1(float r1) {
		this.r1 = r1;
	}

	@Override
	public float getR2() {
		return r2;
	}

	@Override
	public void setR2(float r2) {
		this.r2 = r2;
	}

	@Override
	public float getAngle() {
		return angle;
	}

	@Override
	public void setAngle(float angle) {
		this.angle = angle;
	}

	@Override
	public boolean getLargeArcFlag() {
		return largeArcFlag;
	}

	@Override
	public void setLargeArcFlag(boolean largeArcFlag) {
		this.largeArcFlag = largeArcFlag;
	}

	@Override
	public boolean getSweepFlag() {
		return sweepFlag;
	}

	@Override
	public void setSweepFlag(boolean sweepFlag) {
		this.sweepFlag = sweepFlag;
	}
}