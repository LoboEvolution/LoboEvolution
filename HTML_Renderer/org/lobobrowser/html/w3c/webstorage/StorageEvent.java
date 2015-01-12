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

package org.lobobrowser.html.w3c.webstorage;

import org.w3c.dom.events.Event;

public interface StorageEvent extends Event
{
    // StorageEvent
    public String getKey();
    public Object getOldValue();
    public Object getNewValue();
    public String getUrl();
    public Storage getStorageArea();
    public void initStorageEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, String keyArg, Object oldValueArg, Object newValueArg, String urlArg, Storage storageAreaArg);
}
