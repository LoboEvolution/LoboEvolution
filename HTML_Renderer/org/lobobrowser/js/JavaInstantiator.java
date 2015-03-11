package org.lobobrowser.js;


/**
 * The Interface JavaInstantiator.
 */
public interface JavaInstantiator {
	
	/**
	 * New instance.
	 *
	 * @return the object
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public Object newInstance() throws InstantiationException,
			IllegalAccessException;
}
