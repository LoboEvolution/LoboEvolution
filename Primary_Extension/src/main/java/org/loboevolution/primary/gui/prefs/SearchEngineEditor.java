/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.gui.prefs;

import org.loboevolution.primary.gui.AbstractItemEditor;
import org.loboevolution.primary.gui.FieldType;
import org.loboevolution.primary.gui.FormField;
import org.loboevolution.primary.gui.FormPanel;
import org.loboevolution.primary.gui.ValidationException;
import org.loboevolution.settings.SearchEngine;
import org.loboevolution.util.Strings;
import org.loboevolution.util.gui.WrapperLayout;

/**
 * The Class SearchEngineEditor.
 */
public class SearchEngineEditor extends AbstractItemEditor<SearchEngine> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The form panel. */
	private final FormPanel formPanel = new FormPanel();

	/** The name field. */
	private final transient FormField nameField = new FormField(FieldType.TEXT);

	/** The description field. */
	private final transient FormField descriptionField = new FormField(FieldType.TEXT);

	/** The base url field. */
	private final transient FormField baseUrlField = new FormField(FieldType.TEXT);

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
		this.setLayout(WrapperLayout.getInstance());
		this.add(this.formPanel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.primary.gui.AbstractItemEditor#getItem()
	 */
	@Override
	public SearchEngine getItem() {
		SearchEngine se = new SearchEngine();
		se.setName(this.nameField.getValue());
		se.setDescription(this.descriptionField.getValue());
		se.setBaseUrl(this.baseUrlField.getValue());
		se.setQueryParameter(this.queryParameterField.getValue());
		return se;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.primary.gui.AbstractItemEditor#setItem(java.lang.Object)
	 */
	@Override
	public void setItem(SearchEngine item) {
		this.nameField.setValue(item.getName());
		this.descriptionField.setValue(item.getDescription());
		this.baseUrlField.setValue(item.getBaseUrl());
		this.queryParameterField.setValue(item.getQueryParameter());
		this.formPanel.revalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.primary.gui.AbstractItemEditor#validateItem()
	 */
	@Override
	public void validateItem() throws ValidationException {
		if (Strings.isBlank(this.nameField.getValue()) || Strings.isBlank(this.baseUrlField.getValue())
				|| Strings.isBlank(this.queryParameterField.getValue())) {
			throw new ValidationException("Name, base URL and query parameter are required.");
		}
	}

}
