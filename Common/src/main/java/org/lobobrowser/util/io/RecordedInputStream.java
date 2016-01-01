/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Apr 15, 2005
 */
package org.lobobrowser.util.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Wraps an InputStream and records all of the bytes read. This stream supports
 * mark() and reset().
 * <p>
 * Note: Buffered streams should wrap this class as opposed to the other way
 * around.
 *
 * @author J. H. S.
 */
public class RecordedInputStream extends InputStream {
    /** The delegate. */
    private final InputStream delegate;
    /** The store. */
    private final ByteArrayOutputStream store = new ByteArrayOutputStream();
    /** The max buffer size. */
    private final int maxBufferSize;
    /** The has reached eof. */
    private boolean hasReachedEOF = false;
    /** The has reached max buffer size. */
    private boolean hasReachedMaxBufferSize = false;
    /** The mark position. */
    private int markPosition = -1;
    /** The read position. */
    private int readPosition = -1;
    /** The reset buffer. */
    private byte[] resetBuffer = null;
    
    /**
     * Instantiates a new recorded input stream.
     *
     * @param delegate
     *            the delegate
     * @param maxBufferSize
     *            the max buffer size
     */
    public RecordedInputStream(InputStream delegate, int maxBufferSize) {
        super();
        this.delegate = delegate;
        this.maxBufferSize = maxBufferSize;
    }
    
    /*
     * (non-Javadoc)
     * @see java.io.InputStream#read()
     */
    @Override
    public int read() throws IOException {
        if ((this.readPosition != -1)
                && (this.readPosition < this.resetBuffer.length)) {
            int b = this.resetBuffer[this.readPosition];
            this.readPosition++;
            return b;
        } else {
            int b = this.delegate.read();
            if (b != -1) {
                if (!this.hasReachedMaxBufferSize) {
                    this.store.write(b);
                    if (this.store.size() > this.maxBufferSize) {
                        this.hasReachedMaxBufferSize = true;
                    }
                }
            } else {
                this.hasReachedEOF = true;
            }
            return b;
        }
    }
    
    /*
     * (non-Javadoc)
     * @see java.io.InputStream#available()
     */
    @Override
    public int available() throws IOException {
        return this.delegate.available();
    }
    
    /*
     * (non-Javadoc)
     * @see java.io.InputStream#close()
     */
    @Override
    public void close() throws IOException {
        this.delegate.close();
    }
    
    /*
     * (non-Javadoc)
     * @see java.io.InputStream#markSupported()
     */
    @Override
    public boolean markSupported() {
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see java.io.InputStream#mark(int)
     */
    @Override
    public synchronized void mark(int readlimit) {
        if (this.hasReachedMaxBufferSize) {
            throw new IllegalStateException(
                    "Maximum buffer size was already reached.");
        }
        this.markPosition = this.store.size();
    }
    
    /*
     * (non-Javadoc)
     * @see java.io.InputStream#reset()
     */
    @Override
    public synchronized void reset() throws IOException {
        if (this.hasReachedMaxBufferSize) {
            throw new IllegalStateException(
                    "Maximum buffer size was already reached.");
        }
        int mp = this.markPosition;
        byte[] wholeBuffer = this.store.toByteArray();
        byte[] resetBuffer = new byte[wholeBuffer.length - mp];
        System.arraycopy(wholeBuffer, mp, resetBuffer, 0, resetBuffer.length);
        this.resetBuffer = resetBuffer;
        this.readPosition = 0;
    }
    
    /*
     * (non-Javadoc)
     * @see java.io.InputStream#read(byte[], int, int)
     */
    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        if ((this.readPosition != -1)
                && (this.readPosition < this.resetBuffer.length)) {
            int minLength = Math
                    .min(this.resetBuffer.length - this.readPosition, length);
            System.arraycopy(this.resetBuffer, this.readPosition, buffer,
                    offset, minLength);
            this.readPosition += minLength;
            return minLength;
        } else {
            int numRead = this.delegate.read(buffer, offset, length);
            if (numRead != -1) {
                if (!this.hasReachedMaxBufferSize) {
                    this.store.write(buffer, offset, numRead);
                    if (this.store.size() > this.maxBufferSize) {
                        this.hasReachedMaxBufferSize = true;
                    }
                }
            } else {
                this.hasReachedEOF = true;
            }
            return numRead;
        }
    }
    
    /**
     * Consume to eof.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void consumeToEOF() throws IOException {
        byte[] buffer = new byte[8192];
        while (this.read(buffer) != -1) {
            ;
        }
    }
    
    /** Gets the bytes read.
	 *
	 * @return the bytes read
	 * @throws BufferExceededException
	 *             the buffer exceeded exception
	 */
    public byte[] getBytesRead() throws BufferExceededException {
        if (this.hasReachedMaxBufferSize) {
            throw new BufferExceededException();
        }
        return this.store.toByteArray();
    }
    
    /**
     * Gets the string.
     *
     * @param encoding
     *            the encoding
     * @return the string
     * @throws UnsupportedEncodingException
     *             the unsupported encoding exception
     * @throws BufferExceededException
     *             the buffer exceeded exception
     */
    public String getString(String encoding)
            throws UnsupportedEncodingException, BufferExceededException {
        if (this.hasReachedMaxBufferSize) {
            throw new BufferExceededException();
        }
        byte[] bytes = this.store.toByteArray();
        return new String(bytes, encoding);
    }
    
    /**
     * Checks for reached eof.
     *
     * @return true, if successful
     */
    public boolean hasReachedEOF() {
        return this.hasReachedEOF;
    }
}
