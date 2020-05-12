package org.loboevolution.html.renderstate;

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

/**
 * <p>TableRowRenderState class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class TableRowRenderState extends DisplayRenderState {

    /**
     * <p>Constructor for TableRowRenderState.</p>
     *
     * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
     * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
     */
    public TableRowRenderState(RenderState prevRenderState, HTMLElementImpl element) {
        super(prevRenderState, element, DISPLAY_TABLE_ROW);
    }
}
