package org.loboevolution.html.renderstate;

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

public class TableRowRenderState extends DisplayRenderState {

    public TableRowRenderState(RenderState prevRenderState, HTMLElementImpl element) {
        super(prevRenderState, element, DISPLAY_TABLE_ROW);
    }
}