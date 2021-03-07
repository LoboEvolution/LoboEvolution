package org.loboevolution.html.dom.nodeimpl;

import org.loboevolution.html.node.Code;


public class DOMException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Code code;
	
	private String name;
	
	public DOMException(Code code, String name) {
		this.code = code;
		this.name = name;
	}

	
	public Code getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		return "[Object DOMException]";
	}
}
