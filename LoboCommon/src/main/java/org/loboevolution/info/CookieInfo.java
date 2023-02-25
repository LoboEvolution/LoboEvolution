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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>CookieInfo class.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CookieInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The comment. */
	private String comment;

	/** The domain. */
	private String domain;

	/** The expires. */
	private String expires;

	/** The name. */
	private String name;

	/** The path. */
	private String path;

	/** The value. */
	private String value;

	/** The httpOnly. */
	private boolean httpOnly;

	/** The maxAge. */
	@Builder.Default
	private int maxAge = -1;

	/** The version. */
	private int version;

	/** The secure. */
	private boolean secure;
}
