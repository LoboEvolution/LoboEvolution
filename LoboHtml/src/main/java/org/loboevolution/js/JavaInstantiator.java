package org.loboevolution.js;

/**
 * <p>JavaInstantiator interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface JavaInstantiator {
	/**
	 * <p>newInstance.</p>
	 *
	 * @return a {@link java.lang.Object} object.
	 * @throws java.lang.InstantiationException if any.
	 * @throws java.lang.IllegalAccessException if any.
	 */
	Object newInstance() throws InstantiationException, IllegalAccessException;
}
