package org.lobobrowser.html.renderstate;

import org.lobobrowser.html.dom.domimpl.HTMLElementImpl;

public class TableRowRenderState extends DisplayRenderState {

    public TableRowRenderState(RenderState prevRenderState, HTMLElementImpl element) {
        super(prevRenderState, element, DISPLAY_TABLE_ROW);
    }
}