/*
Copyright 2012 James Edwards

This file is part of Jhrome.

Jhrome is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Jhrome is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with Jhrome.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sexydock.tabs;

import javax.swing.*;

/**
 * An interface for creating {@link Tab}s. {@link JTabbedPane} uses this interface to create new tabs whenever its new jhromeTab button is pressed. This way you
 * can control what jhromeTab is created.
 *
 * @author andy.edwards
 */
public interface ITabFactory {
    /**
     * @return a new {IJhromeTab}.
     */
    Tab createTab();

    Tab createTabWithContent();
}
