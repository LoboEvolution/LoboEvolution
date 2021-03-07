package org.loboevolution.html.dom;


import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.ValidityState;

/**
 * Provides special properties and methods (beyond those on the HTMLElement interface it also has available to it by inheritance) for manipulating the layout and presentation of &lt;object&gt; element, representing external resources.
 */
public interface HTMLObjectElement extends HTMLElement {

    @Deprecated
    
    String getAlign();

    
    void setAlign(String align);

    /**
     * Sets or retrieves a character string that can be used to implement your own archive functionality for the object.
     */
    @Deprecated
    
    String getArchive();

    
    void setArchive(String archive);

    @Deprecated
    
    String getBorder();

    
    void setBorder(String border);

    /**
     * Sets or retrieves the URL of the file containing the compiled Java class.
     */
    @Deprecated
    
    String getCode();

    
    void setCode(String code);

    /**
     * Sets or retrieves the URL of the component.
     */
    @Deprecated
    
    String getCodeBase();

    
    void setCodeBase(String codeBase);

    /**
     * Sets or retrieves the Internet media type for the code associated with the object.
     */
    @Deprecated
    
    String getCodeType();

    
    void setCodeType(String codeType);

    /**
     * Retrieves the document object of the page or frame.
     */
    
    Document getContentDocument();

        
  //  WindowProxy getContentWindow();

    /**
     * Sets or retrieves the URL that references the data of the object.
     */
    
    String getData();

    
    void setData(String data);

    @Deprecated
    
    boolean isDeclare();

    
    void setDeclare(boolean declare);

    /**
     * Retrieves a reference to the form that the object is embedded in.
     */
    
    
    HTMLFormElement getForm();

    /**
     * Sets or retrieves the height of the object.
     */
    
    String getHeight();

    
    void setHeight(String height);

    @Deprecated
    
    double getHspace();

    
    void setHspace(double hspace);

    /**
     * Sets or retrieves the name of the object.
     */
    
    String getName();

    
    void setName(String name);

    /**
     * Sets or retrieves a message to be displayed while an object is loading.
     */
    @Deprecated
    
    String getStandby();

    
    void setStandby(String standby);

    /**
     * Sets or retrieves the MIME type of the object.
     */
    
    String getType();

    
    void setType(String type);

    /**
     * Sets or retrieves the URL, often with a bookmark extension (#name), to use as a client-side image map.
     */
    
    String getUseMap();

    
    void setUseMap(String useMap);

    /**
     * Returns the error message that would be displayed if the user submits the form, or an empty string if no error message. It also triggers the standard error message, such as "this is a required field". The result is that the user sees validation messages without actually submitting.
     */
    
    String getValidationMessage();

    /**
     * Returns a  ValidityState object that represents the validity states of an element.
     */
    
    ValidityState getValidity();

    @Deprecated
    
    double getVspace();

    
    void setVspace(double vspace);

    /**
     * Sets or retrieves the width of the object.
     */
    
    String getWidth();

    
    void setWidth(String width);

    /**
     * Returns whether an element will successfully validate based on forms validation rules and constraints.
     */
    
    boolean isWillValidate();

    /**
     * Returns whether a form will validate when it is submitted, without having to submit it.
     */
    boolean checkValidity();

    
    Document getSVGDocument();

    boolean reportValidity();

    /**
     * Sets a custom error message that is displayed when a form is submitted.
     *
     * @param error Sets a custom error message that is displayed when a form is submitted.
     */
    void setCustomValidity(String error);

}
