package org.loboevolution.html.dom.svgimpl;

import java.util.List;

import org.loboevolution.html.dom.smil.Time;
import org.loboevolution.html.dom.smil.TimeList;

/**
 * <p>TimeListImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class TimeListImpl implements TimeList {

	private final List<Time> times;

	/**
	 * <p>Constructor for TimeListImpl.</p>
	 *
	 * @param times a {@link java.util.List} object.
	 */
	public TimeListImpl(List<Time> times) {
		this.times = times;
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		return this.times.size();
	}

	/** {@inheritDoc} */
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
