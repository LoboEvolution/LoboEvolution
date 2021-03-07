package org.loboevolution.html.node;

/**
 * A processing instruction embeds application-specific instructions in XML
 * which can be ignored by other applications that don't recognize them.
 */
public interface ProcessingInstruction extends CharacterData {

	String getTarget();

}