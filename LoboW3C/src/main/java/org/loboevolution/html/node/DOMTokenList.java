package org.loboevolution.html.node;

/**
 * A set of space-separated tokens. Such a set is returned by Element.classList, HTMLLinkElement.relList, HTMLAnchorElement.relList, HTMLAreaElement.relList, HTMLIframeElement.sandbox, or HTMLOutputElement.htmlFor. It is indexed beginning with 0 as with JavaScript Array objects. DOMTokenList is always case-sensitive.
 */
public interface DOMTokenList {
    
    /**
     * Returns the number of tokens.
     */
    
    int getLength();

    /**
     * Returns the associated set as string.
     * <p>
     * Can be set, to change the associated attribute.
     */
    
    String getValue();

    
    void setValue(String value);

    /**
     * Adds all arguments passed, except those already present.
     * <p>
     * Throws a "SyntaxError" DOMException if one of the arguments is the empty string.
     * <p>
     * Throws an "InvalidCharacterError" DOMException if one of the arguments contains any ASCII whitespace.
     */
    void add(String tokens);

    /**
     * Returns true if token is present, and false otherwise.
     */
    boolean contains(String token);

    /**
     * Returns the token with index index.
     */
    
    String item(int index);

    /**
     * Removes arguments passed, if they are present.
     * <p>
     * Throws a "SyntaxError" DOMException if one of the arguments is the empty string.
     * <p>
     * Throws an "InvalidCharacterError" DOMException if one of the arguments contains any ASCII whitespace.
     */
    void remove(String tokens);

    /**
     * Replaces token with newToken.
     * <p>
     * Returns true if token was replaced with newToken, and false otherwise.
     * <p>
     * Throws a "SyntaxError" DOMException if one of the arguments is the empty string.
     * <p>
     * Throws an "InvalidCharacterError" DOMException if one of the arguments contains any ASCII whitespace.
     */
    boolean replace(String oldToken, String newToken);

    /**
     * Returns true if token is in the associated attribute's supported tokens. Returns false otherwise.
     * <p>
     * Throws a TypeError if the associated attribute has no supported tokens defined.
     */
    boolean supports(String token);

    /**
     * If force is not given, "toggles" token, removing it if it's present and adding it if it's not present. If force is true, adds token (same as add()). If force is false, removes token (same as remove()).
     * <p>
     * Returns true if token is now present, and false otherwise.
     * <p>
     * Throws a "SyntaxError" DOMException if token is empty.
     * <p>
     * Throws an "InvalidCharacterError" DOMException if token contains any spaces.
     */
    boolean toggle(String token, boolean force);

    boolean toggle(String token);

    String get(int index);

}

