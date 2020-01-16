package org.loboevolution.html.dom.svgimpl;

import java.util.List;

import org.loboevolution.html.dom.smil.Time;
import org.loboevolution.html.dom.smil.TimeList;

public class TimeListImpl implements TimeList {

	private final List<Time> times;

	public TimeListImpl(List<Time> times) {
		this.times = times;
	}

	@Override
	public int getLength() {
		return this.times.size();
	}

	@Override
	public Time item(int index) {
		int size = this.times.size();
		if (size > index && index > -1) {
			return this.times.get(index);
		} else {
			return null;
		}
	}
}
