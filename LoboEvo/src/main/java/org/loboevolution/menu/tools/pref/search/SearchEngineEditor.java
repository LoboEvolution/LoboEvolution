/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.menu.tools.pref.search;

import org.loboevolution.common.Strings;
import org.loboevolution.common.WrapperLayout;
import org.loboevolution.gui.AbstractItemEditor;
import org.loboevolution.gui.FieldType;
import org.loboevolution.gui.FormField;
import org.loboevolution.gui.FormPanel;
import org.loboevolution.store.SearchEngineStore;

/**
 * <p>SearchEngineEditor class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SearchEngineEditor extends AbstractItemEditor<SearchEngineStore> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The base url field. */
	private final transient FormField baseUrlField = new FormField(FieldType.TEXT);

	/** The description field. */
	private final transient FormField descriptionField = new FormField(FieldType.TEXT);

	/** The form panel. */
	private final FormPanel formPanel = new FormPanel();

	/** The name field. */
	private final transient FormField nameField = new FormField(FieldType.TEXT);

	/** The query parameter field. */
	private final transient FormField queryParameterField = new FormField(FieldType.TEXT);

	/**
	 * Instantiates a new search engine editor.
	 */
	public SearchEngineEditor() {
		this.nameField.setCaption("Name:");
		this.descriptionField.setCaption("Description:");
		this.baseUrlField.setCaption("Base URL:");
		this.baseUrlField.setToolTip("The search URL, excluding the query parameter.");
		this.queryParameterField.setCaption("Query Parameter:");
		this.queryParameterField.setToolTip("The name of the URL query parameter that is assigned the search string.");
		this.formPanel.addField(this.nameField);
		this.formPanel.addField(this.descriptionField);
		this.formPanel.addField(this.baseUrlField);
		this.formPanel.addField(this.queryParameterField);
		setLayout(WrapperLayout.getInstance());
		this.add(this.formPanel);
	}

	/** {@inheritDoc} */
	@Override
	public SearchEngineStore getItem() {
		final SearchEngineStore se = new SearchEngineStore();
		se.setName(this.nameField.getValue());
		se.setDescription(this.descriptionField.getValue());
		se.setBaseUrl(this.baseUrlField.getValue());
		se.setQueryParameter(this.queryParameterField.getValue());
		return se;
	}

	/** {@inheritDoc} */
	@Override
	public void setItem(SearchEngineStore item) {
		this.nameField.setValue(item.getName());
		this.descriptionField.setValue(item.getDescription());
		this.baseUrlField.setValue(item.getBaseUrl());
		this.queryParameterField.setValue(item.getQueryParameter());
		this.formPanel.revalidate();
	}

	/** {@inheritDoc} */
	@Override
	public void validateItem() {
		if (Strings.isBlank(this.nameField.getValue()) || Strings.isBlank(this.baseUrlField.getValue())
				|| Strings.isBlank(this.queryParameterField.getValue())) {
			throw new RuntimeException("Name, base URL and query parameter are required.");
		}
	}

}
