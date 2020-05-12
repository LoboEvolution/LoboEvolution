package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegClosePath;

/**
 * <p>SVGPathSegClosePathImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGPathSegClosePathImpl implements SVGPathSegClosePath {

	/** {@inheritDoc} */
	@Override
	public short getPathSegType() {
		return PATHSEG_CLOSEPATH;
	}

	/** {@inheritDoc} */
	@Override
	public String getPathSegTypeAsLetter() {
		return "z";
	}
}
