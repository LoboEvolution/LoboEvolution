/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.store;

/**
 * <p>SQLiteCommon class.</p>
 */
public class SQLiteCommon {

	/** Constant COOKIES=" SELECT DISTINCT cookieName, cookieValu"{trunked} */
	public static final String COOKIES = " SELECT DISTINCT cookieName, cookieValue, domain, path, expires, maxAge,secure, httponly FROM COOKIE WHERE domain = ? AND path = ?";

	/** Constant INSERT_COOKIES="INSERT INTO COOKIE (cookieName, cookieV"{trunked} */
	public static final String INSERT_COOKIES = "INSERT INTO COOKIE (cookieName, cookieValue, domain, path, expires, maxAge,secure, httponly) VALUES(?,?,?,?,?,?,?,?)";

	/** Constant SOURCE_CACHE="SELECT source FROM cache WHERE baseUrl "{trunked} */
	public static final String SOURCE_CACHE = "SELECT source FROM cache WHERE baseUrl = ? AND type = ? AND strftime('%Y-%m-%d %H:%M:%S', lastModified) > strftime('%Y-%m-%d %H:%M:%S', 'now', 'localtime')";
        
    /** Constant CHECK_CACHE="SELECT count(*) FROM cache WHERE baseUr"{trunked} */
    public static final String CHECK_CACHE = "SELECT count(*) FROM cache WHERE baseUrl = ? AND contenLenght = ? AND etag = ? AND type = ?";
    
    /** Constant INSERT_CACHE="INSERT INTO CACHE (baseUrl, source, con"{trunked} */
    public static final String INSERT_CACHE = "INSERT INTO CACHE (baseUrl, source, contenLenght, etag, lastModified, type) VALUES(?,?,?,?,?,?)";
    
    /** Constant INPUT="SELECT DISTINCT value from INPUT where "{trunked} */
    public static final String INPUT = "SELECT DISTINCT value from INPUT where name = ? and value like ? and baseUrl = ?";
    
    /** Constant INPUT="SELECT DISTINCT value from INPUT where "{trunked} */
    public static final String INPUT_LIMIT = "SELECT name, value, baseUrl from INPUT limit ?";
	
	/** Constant INSERT_INPUT="INSERT INTO INPUT (name, value) VALUES("{trunked} */
	public static final String INSERT_INPUT= "INSERT INTO INPUT (name, value, baseUrl) VALUES(?,?,?)";
	
	/** Constant DELETE_INPUT="DELETE FROM INPUT" */
	public static final String DELETE_INPUT = "DELETE FROM INPUT";
	
	/** Constant DELETE_INPUT="DELETE FROM INPUT" */
	public static final String DELETE_INPUT2 = "DELETE FROM INPUT where value = ? and baseUrl = ?";
	
    /** Constant DELETE_SOURCE_CACHE="DELETE FROM cache WHERE baseUrl = ? AND"{trunked} */
    public static final String DELETE_SOURCE_CACHE = "DELETE FROM cache WHERE baseUrl = ? AND type = ? AND strftime('%Y-%m-%d %H:%M:%S', lastModified) < strftime('%Y-%m-%d %H:%M:%S', 'now', 'localtime')";

    /** Constant DELETE_ALL_CACHE="DELETE FROM cache */
    public static final String DELETE_ALL_CACHE = "DELETE FROM cache";
	
    /** Constant DELETE_LINK="DELETE FROM LINK_VISITED" */
    public static final String DELETE_LINK = "DELETE FROM LINK_VISITED";
    
    /** Constant INSERT_LINK="INSERT INTO LINK_VISITED VALUES(?)" */
    public static final String INSERT_LINK = "INSERT INTO LINK_VISITED VALUES(?)";
    
    /** Constant LINK="SELECT COUNT(*) FROM LINK_VISITED WHERE"{trunked} */
    public static final String LINK = "SELECT COUNT(*) FROM LINK_VISITED WHERE HREF = ?";
    
    /** Constant LINK="SELECT NAME FROM WEB_STORAGE WHERE"{trunked} */
    public static final String WEBSTORAGE_VALUE = "SELECT VALUE FROM WEB_STORAGE WHERE KEY = ? AND SESSION = ? AND TABINDEX = ?";
    
    /** Constant LINK="SELECT NAME, VALUE FROM WEB_STORAGE"{trunked} */
    public static final String WEBSTORAGE_MAP = "SELECT KEY, VALUE FROM WEB_STORAGE WHERE TABINDEX = ? AND SESSION = ?";
        
    /** Constant WEBSTORAGE_DELETE_KEY="DELETE FROM WEB_STORAGE WHER"{trunked} */
    public static final String WEBSTORAGE_DELETE_KEY = "DELETE FROM WEB_STORAGE WHERE KEY = ? AND SESSION = ? AND TABINDEX = ?";
    
    /** Constant DELETE_WEBSTORAGE="DELETE FROM WEB_STORAGE WHER"{trunked} */
    public static final String DELETE_WEBSTORAGE = "DELETE FROM WEB_STORAGE WHERE SESSION = ? AND TABINDEX = ?";
    
    /** Constant DELETE_WEBSTORAGE="DELETE FROM WEB_STORAGE WHER"{trunked} */
    public static final String DELETE_ALL_WEBSTORAGE = "DELETE FROM WEB_STORAGE WHERE SESSION = ? AND TABINDEX > -1";
    
    /** Constant WEBSTORAGE="INSERT INTO WEB_STORAGE (key, value, session) "{trunked} */
	public static final String WEBSTORAGE= "INSERT INTO WEB_STORAGE (key, value, session, tabIndex) VALUES(?,?,?,?)";
	
	 /** Constant WEBSTORAGE_SIZE="SELECT COUNT(*) FROM LINK_VISITED WHERE"{trunked} */
    public static final String WEBSTORAGE_SIZE = "SELECT COUNT(*) FROM WEB_STORAGE WHERE TABINDEX = ?";

}
