package org.loboevolution.html.node.events;

public interface EventModifierInit extends UIEvent.UIEventInit {

	boolean isAltKey();

	void setAltKey(boolean altKey);

	boolean isCtrlKey();

	void setCtrlKey(boolean ctrlKey);

	boolean isMetaKey();

	void setMetaKey(boolean metaKey);

	boolean isModifierAltGraph();

	void setModifierAltGraph(boolean modifierAltGraph);

	boolean isModifierCapsLock();

	void setModifierCapsLock(boolean modifierCapsLock);

	boolean isModifierFn();

	void setModifierFn(boolean modifierFn);

	boolean isModifierFnLock();

	void setModifierFnLock(boolean modifierFnLock);

	boolean isModifierHyper();

	void setModifierHyper(boolean modifierHyper);

	boolean isModifierNumLock();

	void setModifierNumLock(boolean modifierNumLock);

	boolean isModifierScrollLock();

	void setModifierScrollLock(boolean modifierScrollLock);

	boolean isModifierSuper();

	void setModifierSuper(boolean modifierSuper);

	boolean isModifierSymbol();

	void setModifierSymbol(boolean modifierSymbol);

	boolean isModifierSymbolLock();

	void setModifierSymbolLock(boolean modifierSymbolLock);

	boolean isShiftKey();

	void setShiftKey(boolean shiftKey);

}
