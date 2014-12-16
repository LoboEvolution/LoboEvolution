package org.lobobrowser.util;

import java.util.ArrayList;

public class JavascriptCommon {
	public String mapFunction(String script) {

		ArrayList<String> list = new ArrayList<String>();
		list.add("focus");
		list.add("blur");
		list.add("click");
		list.add("dblclick");
		list.add("mousedown");
		list.add("mouseup");
		list.add("mouseover");
		list.add("mousemove");
		list.add("mouseout");
		list.add("keypress");
		list.add("keydown");
		list.add("keyup");
		list.add("ctextmenu");
		list.add("abort");
		list.add("play");
		list.add("playing");
		list.add("progress");
		list.add("readystatechange");
		list.add("scroll");
		list.add("seeked");
		list.add("seeking");
		list.add("select");
		list.add("show");
		list.add("stalled");
		list.add("submit");
		list.add("suspend");
		list.add("timeupdate");
		list.add("waiting");
		list.add("volumechange");
		list.add("finish");
		list.add("start");
		list.add("bounce");
		list.add("load");

		for (int i = 0; i < list.size(); i++) {
			if (script.equals(list.get(0))) {
				script = "on" + script;
				break;
			}
		}
		
		return script;
	}
}
