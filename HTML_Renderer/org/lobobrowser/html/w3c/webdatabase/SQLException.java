/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net
*/
package org.lobobrowser.html.w3c.webdatabase;

public class SQLException extends RuntimeException
{
    
	private static final long serialVersionUID = 1L;
	public SQLException(short code, String message)
    {
        super(message);
        this.code = code;
    }
    public static final short UNKNOWN_ERR = 0;
    public static final short DATABASE_ERR = 1;
    public static final short VERSION_ERR = 2;
    public static final short TOO_LARGE_ERR = 3;
    public static final short QUOTA_ERR = 4;
    public static final short SYNTAX_ERR = 5;
    public static final short CONSTRAINT_ERR = 6;
    public static final short TIMEOUT_ERR = 7;
    public short code;
    public String message;
}
