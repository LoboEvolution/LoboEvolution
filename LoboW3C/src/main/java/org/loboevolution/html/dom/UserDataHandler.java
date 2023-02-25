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
package org.loboevolution.html.dom;

import org.loboevolution.html.node.Node;

public interface UserDataHandler {
    short NODE_CLONED = 1;
    short NODE_IMPORTED = 2;
    short NODE_DELETED = 3;
    short NODE_RENAMED = 4;
    short NODE_ADOPTED = 5;

    void handle(short operation,
                String key,
                Object data,
                Node src,
                Node dst);
}
