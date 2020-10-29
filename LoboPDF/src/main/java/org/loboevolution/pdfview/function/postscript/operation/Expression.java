package org.loboevolution.pdfview.function.postscript.operation;

import java.util.LinkedList;



public class Expression extends LinkedList<Object> {

    @Override
	public boolean equals(Object obj) {
        // actually validate the list contents are the same expressions
        return obj instanceof Expression;
    }
}
