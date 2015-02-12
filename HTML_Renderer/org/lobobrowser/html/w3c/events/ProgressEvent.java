package org.lobobrowser.html.w3c.events;

public interface ProgressEvent extends Event {
	// ProgressEvent
	public boolean getLengthComputable();

	public int getLoaded();

	public int getTotal();

	public void initProgressEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, boolean lengthComputableArg, int loadedArg,
			int totalArg);
}
