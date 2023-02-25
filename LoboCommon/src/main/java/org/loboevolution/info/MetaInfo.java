/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.info;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>MetaInfo class.</p>
 */
@Data
@Builder
public class MetaInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The charsetc. */
	private String charset;

	/** The content. */
	private String content;

	/** The description. */
	private String description;

	/** The httpEqui. */
	private String httpEqui;

	/** The itemprop. */
	private String itemprop;

	/** The name. */
	private String name;

	/** The property. */
	private String property;
}
