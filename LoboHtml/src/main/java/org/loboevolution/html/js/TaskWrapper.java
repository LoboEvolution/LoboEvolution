package org.loboevolution.html.js;

import javax.swing.Timer;

class TaskWrapper {
	public final Timer timer;

	public TaskWrapper(Timer timer, Object retained) {
		this.timer = timer;
	}
}