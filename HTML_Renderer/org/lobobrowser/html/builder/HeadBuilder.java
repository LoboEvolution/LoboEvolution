package org.lobobrowser.html.builder;

import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.domimpl.HTMLHeadElementImpl;

public class HeadBuilder extends HTMLElementBuilder{

	@Override
	protected HTMLElementImpl build(String name) {
		return new HTMLHeadElementImpl(name);
	}

}
