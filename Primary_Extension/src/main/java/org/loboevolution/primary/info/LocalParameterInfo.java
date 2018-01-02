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
package org.loboevolution.primary.info;

import java.io.File;

import org.loboevolution.html.FormInput;
import org.loboevolution.ua.Parameter;
import org.loboevolution.ua.ParameterInfo;

/**
 * The Class LocalParameterInfo.
 */
public class LocalParameterInfo implements ParameterInfo {

	/** The encoding type. */
	private final String encodingType;

	/** The form inputs. */
	private final FormInput[] formInputs;

	/**
	 * Instantiates a new local parameter info.
	 *
	 * @param type
	 *            the type
	 * @param inputs
	 *            the inputs
	 */
	public LocalParameterInfo(String type, FormInput[] inputs) {
		super();
		encodingType = type;
		formInputs = inputs;
	}

	@Override
	public String getEncoding() {
		return this.encodingType;
	}

	@Override
	public Parameter[] getParameters() {
		final FormInput[] formInputs = this.formInputs;
		Parameter[] params = new Parameter[formInputs.length];
		for (int i = 0; i < params.length; i++) {
			final int index = i;
			params[i] = new Parameter() {
				@Override
				public String getName() {
					return formInputs[index].getName();
				}

				@Override
				public File[] getFileValue() {
					return formInputs[index].getFileValue();
				}

				@Override
				public String getTextValue() {
					return formInputs[index].getTextValue();
				}

				@Override
				public boolean isFile() {
					return formInputs[index].isFile();
				}

				@Override
				public boolean isText() {
					return formInputs[index].isText();
				}
			};
		}
		return params;
	}
}