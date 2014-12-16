/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright ("C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or ("at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.html.dombl;

import java.util.ArrayList;

public class Utility {

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
		
		System.out.println("script: " + script);
		return script;
	}

}
