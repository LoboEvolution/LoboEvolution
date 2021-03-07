package org.loboevolution.html.dom;

/**
 * A &lt;form&gt; element in the DOM; it allows access to and in some cases modification of aspects of the form, as well as access to its component elements.
 */
public interface HTMLFormElement extends HTMLElement {

    /**
     * Sets or retrieves a list of character encodings for input data that must be accepted by the server processing the form.
     */
    
    String getAcceptCharset();

    
    void setAcceptCharset(String acceptCharset);

    /**
     * Sets or retrieves the URL to which the form content is sent for processing.
     */
    
    String getAction();

    
    void setAction(String action);

    /**
     * Specifies whether autocomplete is applied to an editable text field.
     */
    
    String getAutocomplete();

    
    void setAutocomplete(String autocomplete);

    /**
     * Retrieves a collection, in source order, of all controls in a given form.
     */
    
    HTMLCollection getElements();

    /**
     * Sets or retrieves the MIME encoding for the form.
     */
    
    String getEncoding();

    
    void setEncoding(String encoding);

    /**
     * Sets or retrieves the encoding type for the form.
     */
    
    String getEnctype();

    
    void setEnctype(String enctype);

    /**
     * Sets or retrieves the number of objects in a collection.
     */
    
    int getLength();

    /**
     * Sets or retrieves how to send the form data to the server.
     */
    
    String getMethod();

    
    void setMethod(String method);

    /**
     * Sets or retrieves the name of the object.
     */
    
    String getName();

    
    void setName(String name);

    /**
     * Designates a form that is not validated when submitted.
     */
    
    boolean isNoValidate();

    
    void setNoValidate(boolean noValidate);

    /**
     * Sets or retrieves the window or frame at which to target content.
     */
    
    String getTarget();

    
    void setTarget(String target);

    /**
     * Returns whether a form will validate when it is submitted, without having to submit it.
     */
    boolean checkValidity();

    boolean reportValidity();

    /**
     * Fires when the user resets a form.
     */
    void reset();

    /**
     * Fires when a FORM is about to be submitted.
     */
    void submit();

}
