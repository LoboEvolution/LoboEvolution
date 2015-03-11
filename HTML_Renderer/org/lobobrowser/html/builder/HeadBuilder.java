package org.lobobrowser.html.builder;

import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.domimpl.HTMLHeadElementImpl;


/**
 * The Class HeadBuilder.
 */
public class HeadBuilder extends HTMLElementBuilder{

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.builder.HTMLElementBuilder#build(java.lang.String)
	 */
	@Override
	protected HTMLElementImpl build(String name) {
		return new HTMLHeadElementImpl(name);
	}

}
