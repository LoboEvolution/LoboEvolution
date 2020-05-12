/*
 * Copyright (c) 2019 Ronald Brill.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.css.parser;

/**
 * Locatable interface.
 *
 * @author Ronald Brill
 * @version $Id: $Id
 */
public interface Locatable {

    /**
     * <p>getLocator.</p>
     *
     * @return the locator
     */
    Locator getLocator();

    /**
     * Set the locator.
     *
     * @param locator the new locator
     */
    void setLocator(Locator locator);
}
