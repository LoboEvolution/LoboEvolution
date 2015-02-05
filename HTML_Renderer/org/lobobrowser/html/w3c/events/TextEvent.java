package org.lobobrowser.html.w3c.events;

import org.w3c.dom.views.AbstractView;

public interface TextEvent extends UIEvent {
	// TextEvent
	public static final int DOM_INPUT_METHOD_UNKNOWN = 0x00;
	public static final int DOM_INPUT_METHOD_KEYBOARD = 0x01;
	public static final int DOM_INPUT_METHOD_PASTE = 0x02;
	public static final int DOM_INPUT_METHOD_DROP = 0x03;
	public static final int DOM_INPUT_METHOD_IME = 0x04;
	public static final int DOM_INPUT_METHOD_OPTION = 0x05;
	public static final int DOM_INPUT_METHOD_HANDWRITING = 0x06;
	public static final int DOM_INPUT_METHOD_VOICE = 0x07;
	public static final int DOM_INPUT_METHOD_MULTIMODAL = 0x08;
	public static final int DOM_INPUT_METHOD_SCRIPT = 0x09;

	public String getData();

	public int getInputMode();

	public void initTextEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, String dataArg,
			int inputMode);
}
