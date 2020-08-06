package org.w3c.dom.css;

/**
 * <p>CSS3Properties interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface CSS3Properties extends CSS2Properties {

	/**
	 * <p>getBoxSizing.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getBoxSizing();
	
	/**
	 * <p>getClipPath.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getClipPath();
	
	/**
	 * <p>getClipRule.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getClipRule();
	
	/**
	 * <p>getFill.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getFill();
	
	/**
	 * <p>getFlexDirection.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */	
	String getFlexDirection(); 

	/**
	 * <p>setClipPath.</p>
	 *
	 * @param clip a {@link java.lang.String} object.
	 */
	void setClipPath(String clip);
	
	/**
	 * <p>setClipRule.</p>
	 *
	 * @param clip a {@link java.lang.String} object.
	 */
	void setClipRule(String clip);
}
