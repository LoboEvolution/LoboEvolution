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
package org.loboevolution.html.domimpl;

import java.util.regex.Pattern;

import org.loboevolution.html.dombl.InputContext;
import org.loboevolution.util.Strings;
import org.loboevolution.w3c.html.ValidityState;

/**
 * The Class DOMValidityState.
 */
public class DOMValidityState implements ValidityState {

	/** The input context. */
	private InputContext ic;

	/** The customer error. */
	private String customerError;

	public DOMValidityState() {

	}

	public DOMValidityState(InputContext ic, String customerError) {
		this.ic = ic;
		this.customerError = customerError;
	}

	@Override
	public boolean getValueMissing() {
		return (ic.getValue() == null || (ic.getValue() != null && ic.getValue().length() == 0));
	}

	@Override
	public boolean getTypeMismatch() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getPatternMismatch() {
		return ic.getValue() != null && Pattern.matches(ic.getPattern(), ic.getValue());

	}

	@Override
	public boolean getTooLong() {
		return ic.getValue() != null && ic.getValue().length() > ic.getMaxLength();
	}

	@Override
	public boolean getRangeUnderflow() {
		int numeric = Integer.parseInt(ic.getValue());
		return numeric < ic.getMin();
	}

	@Override
	public boolean getRangeOverflow() {
		int numeric = Integer.parseInt(ic.getValue());
		return numeric > ic.getMax();
	}

	@Override
	public boolean getStepMismatch() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getCustomError() {
		return !Strings.isBlank(customerError);
	}

	@Override
	public boolean getValid() {
		return !getValueMissing() && !getCustomError() && !getPatternMismatch() && !getRangeOverflow()
				&& !getRangeUnderflow() && !getStepMismatch() && !getTooLong() && !getTypeMismatch();
	}
}
