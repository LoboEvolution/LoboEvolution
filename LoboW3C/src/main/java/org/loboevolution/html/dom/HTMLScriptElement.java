package org.loboevolution.html.dom;


/**
 * HTML &lt;script&gt; elements expose the HTMLScriptElement interface, which provides special properties and methods for manipulating the behavior and execution of &lt;script&gt; elements (beyond the inherited HTMLElement interface).
 */
public interface HTMLScriptElement extends HTMLElement {

    boolean isAsync();

    void setAsync(boolean async);

    /**
     * Sets or retrieves the character set used to encode the object.
     */
    @Deprecated
    
    String getCharset();

    
    void setCharset(String charset);

    
    
    String getCrossOrigin();

    
    void setCrossOrigin(String crossOrigin);

    /**
     * Sets or retrieves the status of the script.
     */
    
    boolean isDefer();

    
    void setDefer(boolean defer);

    /**
     * Sets or retrieves the event for which the script is written.
     */
    @Deprecated
    
    String getEvent();

    
    void setEvent(String event);

    /**
     * Sets or retrieves the object that is bound to the event script.
     */
    @Deprecated
    
    String getHtmlFor();

    
    void setHtmlFor(String htmlFor);

    
    String getIntegrity();

    
    void setIntegrity(String integrity);

    
    boolean isNoModule();

    
    void setNoModule(boolean noModule);

    
    String getReferrerPolicy();

    
    void setReferrerPolicy(String referrerPolicy);

    /**
     * Retrieves the URL to an external file that contains the source code or data.
     */
    
    String getSrc();

    
    void setSrc(String src);

    /**
     * Retrieves or sets the text of the object as a string.
     */
    
    String getText();

    
    void setText(String text);

    /**
     * Sets or retrieves the MIME type for the associated scripting engine.
     */
    
    String getType();

    
    void setType(String type);

}
