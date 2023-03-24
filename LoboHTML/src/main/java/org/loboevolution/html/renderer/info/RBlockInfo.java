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

package org.loboevolution.html.renderer.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.RenderableContainer;
import org.loboevolution.http.UserAgentContext;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RBlockInfo {

    private NodeImpl modelNode;

    private UserAgentContext pcontext;

    private HtmlRendererContext rcontext;

    private FrameContext frameContext;

    private RenderableContainer parentContainer;

    private int listNesting;
}
