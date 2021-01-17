/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
/*
 * Created on Nov 19, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.awt.Dimension;
import java.net.URL;
import java.util.logging.Level;

import org.loboevolution.common.Urls;
import org.loboevolution.html.control.ImgSvgControl;
import org.loboevolution.html.dom.HTMLImageElement;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.renderstate.ImageRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.mozilla.javascript.Function;

/**
 * <p>HTMLImageElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLImageElementImpl extends HTMLAbstractUIElement implements HTMLImageElement {
	
	/**
	 * <p>Constructor for HTMLImageElementImpl.</p>
	 */
	public HTMLImageElementImpl() {
		super("IMG");
	}

	/**
	 * <p>Constructor for HTMLImageElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLImageElementImpl(String name) {
		super(name);
	}
	
	/** {@inheritDoc} */
	@Override
	protected void assignAttributeField(String normalName, String value) {
		if ("onload".equals(normalName)) {
			final Function onload = getEventFunction(null, normalName);
			if (onload != null) {
				setOnload(onload);
			}
		} else {
			super.assignAttributeField(normalName, value);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new ImageRenderState(prevRenderState, this);
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	/** {@inheritDoc} */
	@Override
	public String getAlt() {
		return getAttribute("alt");
	}

	/** {@inheritDoc} */
	@Override
	public String getBorder() {
		return getAttribute("border");
	}

	/** {@inheritDoc} */
	@Override
	public int getHeight() {
		final UINode r = this.uiNode;
		return r == null ? 0 : r.getBounds().height;
	}

	/** {@inheritDoc} */
	@Override
	public int getHspace() {
		return getAttributeAsInt("hspace", 0);
	}

	/** {@inheritDoc} */
	@Override
	public boolean getIsMap() {
		return getAttributeAsBoolean("isMap");
	}

	/** {@inheritDoc} */
	@Override
	public String getLongDesc() {
		return getAttribute("longDesc");
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return getAttribute("name");
	}

	/**
	 * <p>getOnload.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnload() {
		final Object document = this.document;
		if (document instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) document).getOnloadHandler();
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getSrc() {
		return getAttribute("src");
	}

	/** {@inheritDoc} */
	@Override
	public String getUseMap() {
		return getAttribute("useMap");
	}

	/** {@inheritDoc} */
	@Override
	public int getVspace() {
		return getAttributeAsInt("vspace", 0);
	}

	/** {@inheritDoc} */
	@Override
	public int getWidth() {
		final UINode r = this.uiNode;
		return r == null ? 0 : r.getBounds().width;
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	/** {@inheritDoc} */
	@Override
	public void setAlt(String alt) {
		setAttribute("alt", alt);
	}

	/** {@inheritDoc} */
	@Override
	public void setBorder(String border) {
		setAttribute("border", border);
	}

	/** {@inheritDoc} */
	@Override
	public void setHeight(int height) {
		setAttribute("height", String.valueOf(height));
	}

	/** {@inheritDoc} */
	@Override
	public void setHspace(int hspace) {
		setAttribute("hspace", "hspace");
	}

	/** {@inheritDoc} */
	@Override
	public void setIsMap(boolean isMap) {
		setAttribute("isMap", isMap ? "isMap" : null);
	}

	/** {@inheritDoc} */
	@Override
	public void setLongDesc(String longDesc) {
		setAttribute("longDesc", longDesc);
	}

	/** {@inheritDoc} */
	@Override
	public void setName(String name) {
		setAttribute("name", name);
	}

	/**
	 * <p>setOnload.</p>
	 *
	 * @param onload a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnload(Function onload) {
		final Object document = this.document;
		if (document instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) document).setOnloadHandler(onload);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Sets the image URI and starts to load the image. Note that an
	 * HtmlRendererContext should be available to the HTML document for images to be
	 * loaded.
	 */
	@Override
	public void setSrc(String src) {
		setAttribute("src", src);
	}

	/** {@inheritDoc} */
	@Override
	public void setUseMap(String useMap) {
		setAttribute("useMap", useMap);
	}

	/** {@inheritDoc} */
	@Override
	public void setVspace(int vspace) {
		setAttribute("vspace", String.valueOf(vspace));
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(int width) {
		setAttribute("width", String.valueOf(width));
	}
	
	/**
	 * <p>draw.</p>
	 *
	 * @param imgSvgControl a {@link org.loboevolution.html.control.ImgSvgControl} object.
	 */
	public void draw(ImgSvgControl imgSvgControl) {
		final Object document = this.document;
		String uri = null;
		if (document instanceof HTMLDocumentImpl) {
			try {
			HTMLDocumentImpl doc = (HTMLDocumentImpl)document;
			URL baseURL = new URL(doc.getBaseURI());
			String src = getSrc();
			URL scriptURL = Urls.createURL(baseURL, src );
			uri = scriptURL == null ? src : scriptURL.toExternalForm();
			
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		} else {
			uri = getSrc();
		}
		
		final HtmlPanel hpanel = HtmlPanel.createHtmlPanel(null, uri);
		if(getWidth() > 0 && getHeight() > 0) {
			hpanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
		}
		imgSvgControl.add(hpanel);
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLImageElement]";
	}
}