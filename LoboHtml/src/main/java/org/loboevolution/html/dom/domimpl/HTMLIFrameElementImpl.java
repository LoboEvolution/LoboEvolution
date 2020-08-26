package org.loboevolution.html.dom.domimpl;

import java.awt.Dimension;
import java.net.URL;
import java.util.logging.Level;

import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.html.control.FrameControl;
import org.loboevolution.html.dom.HTMLIFrameElement;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.renderstate.IFrameRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.w3c.dom.Document;

/**
 * <p>HTMLIFrameElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLIFrameElementImpl extends HTMLAbstractUIElement implements HTMLIFrameElement {

	/**
	 * <p>Constructor for HTMLIFrameElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLIFrameElementImpl(String name) {
		super(name);
	}
	
	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new IFrameRenderState(prevRenderState, this);
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	/** {@inheritDoc} */
	@Override
	public Document getContentDocument() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getFrameBorder() {
		return getAttribute("frameborder");
	}

	/** {@inheritDoc} */
	@Override
	public String getHeight() {
		return getAttribute("height");
	}

	/** {@inheritDoc} */
	@Override
	public String getLongDesc() {
		return getAttribute("longdesc");
	}

	/** {@inheritDoc} */
	@Override
	public String getMarginHeight() {
		return getAttribute("marginheight");
	}

	/** {@inheritDoc} */
	@Override
	public String getMarginWidth() {
		return getAttribute("marginwidth");
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return getAttribute("name");
	}

	/** {@inheritDoc} */
	@Override
	public String getScrolling() {
		return getAttribute("scrolling");
	}

	/** {@inheritDoc} */
	@Override
	public String getSrc() {
		return getAttribute("src");
	}

	/** {@inheritDoc} */
	@Override
	public String getWidth() {
		return getAttribute("width");
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	/** {@inheritDoc} */
	@Override
	public void setFrameBorder(String frameBorder) {
		setAttribute("frameborder", frameBorder);
	}

	/** {@inheritDoc} */
	@Override
	public void setHeight(String height) {
		setAttribute("height", height);
	}

	/** {@inheritDoc} */
	@Override
	public void setLongDesc(String longDesc) {
		setAttribute("longdesc", longDesc);
	}

	/** {@inheritDoc} */
	@Override
	public void setMarginHeight(String marginHeight) {
		setAttribute("marginHeight", marginHeight);
	}

	/** {@inheritDoc} */
	@Override
	public void setMarginWidth(String marginWidth) {
		setAttribute("marginWidth", marginWidth);
	}

	/** {@inheritDoc} */
	@Override
	public void setName(String name) {
		setAttribute("name", name);
	}

	/** {@inheritDoc} */
	@Override
	public void setScrolling(String scrolling) {
		setAttribute("scrolling", scrolling);
	}

	/** {@inheritDoc} */
	@Override
	public void setSrc(String src) {
		setAttribute("src", src);
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}

	/**
	 * <p>draw.</p>
	 *
	 * @param frameControl a {@link org.loboevolution.html.control.FrameControl} object.
	 */
	public void draw(FrameControl frameControl) {
		try {
			HTMLDocumentImpl doc = (HTMLDocumentImpl) getDocumentNode();
			URL baseURL = new URL(doc.getBaseURI());
			URL createURL = Urls.createURL(baseURL, getSrc());
			final HtmlPanel hpanel = HtmlPanel.createHtmlPanel(null, createURL.toString());
			if (Strings.isNotBlank(getWidth()) && Strings.isNotBlank(getHeight())) {
				hpanel.setPreferredSize(new Dimension(Integer.parseInt(getWidth()), Integer.parseInt(getHeight())));
			}
			frameControl.add(hpanel);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
