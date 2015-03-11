/*
Copyright 1994-2006 The Lobo Project. Copyright 2014 Lobo Evolution. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list 
of conditions and the following disclaimer. Redistributions in binary form must 
reproduce the above copyright notice, this list of conditions and the following 
disclaimer in the documentation and/or other materials provided with the distribution.
 
THIS SOFTWARE IS PROVIDED BY THE LOBO PROJECT ``AS IS'' AND ANY EXPRESS OR IMPLIED 
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO 
EVENT SHALL THE FREEBSD PROJECT OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.lobobrowser.clientlet;

import java.awt.Component;


/**
 * The Class SimpleComponentContent.
 */
public class SimpleComponentContent extends AbstractComponentContent {
	
	/** The component. */
	private final Component component;
	
	/** The title. */
	private final String title;
	
	/** The source code. */
	private final String sourceCode;

	/**
	 * Instantiates a new simple component content.
	 *
	 * @param component the component
	 * @param title the title
	 * @param sourceCode the source code
	 */
	public SimpleComponentContent(Component component, String title,
			String sourceCode) {
		this.component = component;
		this.title = title;
		this.sourceCode = sourceCode;
	}

	/**
	 * Instantiates a new simple component content.
	 *
	 * @param component the component
	 */
	public SimpleComponentContent(Component component) {
		this.component = component;
		this.title = component.toString();
		this.sourceCode = null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.AbstractComponentContent#canCopy()
	 */
	@Override
	public boolean canCopy() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.AbstractComponentContent#copy()
	 */
	@Override
	public boolean copy() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.AbstractComponentContent#getComponent()
	 */
	@Override
	public Component getComponent() {
		return this.component;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.AbstractComponentContent#getSourceCode()
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.AbstractComponentContent#getTitle()
	 */
	@Override
	public String getTitle() {
		return this.title;
	}
}
