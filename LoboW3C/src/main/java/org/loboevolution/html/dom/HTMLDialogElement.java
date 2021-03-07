package org.loboevolution.html.dom;





public interface HTMLDialogElement extends HTMLElement {


    
    boolean isOpen();

    
    void setOpen(boolean open);

    
    String getReturnValue();

    
    void setReturnValue(String returnValue);

    void close(String returnValue);

    void close();

    void show();

    void showModal();

}
