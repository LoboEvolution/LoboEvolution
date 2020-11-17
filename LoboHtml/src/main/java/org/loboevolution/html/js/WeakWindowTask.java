package org.loboevolution.html.js;

import java.awt.event.ActionListener;
import java.lang.ref.WeakReference;

abstract class WeakWindowTask implements ActionListener {
	private final WeakReference<Window> windowRef;

	public WeakWindowTask(Window window) {
		this.windowRef = new WeakReference<>(window);
	}

	protected Window getWindow() {
		final WeakReference<Window> ref = this.windowRef;
		return ref == null ? null : ref.get();
	}
}