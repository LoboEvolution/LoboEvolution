package org.w3c.dom.css;

/**
 * <p>
 * CSS3Properties interface.
 * </p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface CSS3Properties extends CSS2Properties {

	/**
	 * <p>
	 * getAlignItems.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAlignItems();

	/**
	 * <p>
	 * getAlignContent.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAlignContent();

	/**
	 * <p>
	 * getBoxSizing.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getBoxSizing();

	/**
	 * <p>
	 * getClipPath.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getClipPath();

	/**
	 * <p>
	 * getClipRule.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getClipRule();

	/**
	 * <p>
	 * getFill.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getFill();

	/**
	 * <p>
	 * getFloat.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getFloat();

	/**
	 * <p>
	 * getFlexDirection.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getFlexDirection();

	/**
	 * <p>
	 * flexWrap.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getFlexWrap();

	/**
	 * <p>
	 * getFlexFlow.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getFlexFlow();

	/**
	 * <p>
	 * getJustifyContent.
	 * </p>
	 *
	 * @param clip a {@link java.lang.String} object.
	 */
	String getJustifyContent();

	/**
	 * <p>
	 * setClipPath.
	 * </p>
	 *
	 * @param clip a {@link java.lang.String} object.
	 */
	void setClipPath(String clip);

	/**
	 * <p>
	 * setClipRule.
	 * </p>
	 *
	 * @param clip a {@link java.lang.String} object.
	 */
	void setClipRule(String clip);

	/**
	 * <p>
	 * setFloat.
	 * </p>
	 *
	 * @param flt a {@link java.lang.String} object.
	 */
	void setFloat(String flt);
}
