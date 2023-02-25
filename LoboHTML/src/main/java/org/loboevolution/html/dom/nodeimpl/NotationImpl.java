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

package org.loboevolution.html.dom.nodeimpl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.loboevolution.html.dom.Notation;
import org.loboevolution.html.dom.nodeimpl.event.EventTargetImpl;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotationImpl extends EventTargetImpl implements Notation {

    private String publicId;
    private String systemId;
    private String nodeName;

    @Override
    public int getNodeType() {
        return NOTATION_NODE;
    }
}
