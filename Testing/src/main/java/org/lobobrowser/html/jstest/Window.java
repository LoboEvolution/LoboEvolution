package org.lobobrowser.html.jstest;

import org.lobobrowser.js.AbstractScriptableDelegate;

public class Window extends AbstractScriptableDelegate{
	
	public void getDocument(){
		System.out.println("i am document");
	}
	
	public void getNavigator(){
		System.out.println("i am navigator");
	}
	
	public void getConsole(){
		System.out.println("i am console");
	}
}
