package org.lobobrowser.util;


/**
 * The Class BoxedObject.
 */
public class BoxedObject {
	
	/** The object. */
	private Object object;

	/**
	 * Instantiates a new boxed object.
	 */
	public BoxedObject() {
	}

	/**
	 * Instantiates a new boxed object.
	 *
	 * @param object the object
	 */
	public BoxedObject(Object object) {
		super();
		this.object = object;
	}

	/**
	 * Gets the object.
	 *
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * Sets the object.
	 *
	 * @param object the new object
	 */
	public void setObject(Object object) {
		this.object = object;
	}

}
