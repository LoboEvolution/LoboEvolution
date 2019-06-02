package org.lobobrowser.util;

public class BoxedObject {
	private Object object;

	public BoxedObject() {
	}

	public BoxedObject(Object object) {
		super();
		this.object = object;
	}

	public Object getObject() {
		return this.object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
