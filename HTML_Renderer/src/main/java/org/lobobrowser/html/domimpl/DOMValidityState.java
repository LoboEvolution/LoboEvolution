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
package org.lobobrowser.html.domimpl;

import java.util.regex.Pattern;

import org.lobobrowser.html.dombl.InputContext;
import org.lobobrowser.w3c.html.ValidityState;

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
		if (ic.getValue() == null) {
			return true;
		} else if (ic.getValue() != null && ic.getValue().length() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean getTypeMismatch() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getPatternMismatch() {
		if (ic.getValue() != null && Pattern.matches(ic.getPattern(), ic.getValue())){
			return false;
		}else{
			return true;
		}
		
	}

	@Override
	public boolean getTooLong() {
		if (ic.getValue() != null && ic.getValue().length() > ic.getMaxLength()) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean getRangeUnderflow() {
		int numeric = Integer.parseInt(ic.getValue());

		if (numeric < ic.getMin()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean getRangeOverflow() {
		int numeric = Integer.parseInt(ic.getValue());

		if (numeric > ic.getMax()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean getStepMismatch() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getCustomError() {
		if (customerError != null && customerError.length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean getValid() {

		if (!getValueMissing() && !getCustomError() && !getPatternMismatch() && !getRangeOverflow()
				&& !getRangeUnderflow() && !getStepMismatch() && !getTooLong() && !getTypeMismatch()) {
			return true;
		} else {
			return false;
		}

	}
}
