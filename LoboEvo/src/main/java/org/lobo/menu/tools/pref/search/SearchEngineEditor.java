package org.lobo.menu.tools.pref.search;

import org.lobo.common.Strings;
import org.lobo.gui.AbstractItemEditor;
import org.lobo.gui.FieldType;
import org.lobo.gui.FormField;
import org.lobo.gui.FormPanel;
import org.lobo.store.SearchEngineStore;
import org.lobo.common.WrapperLayout;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.primary.gui.AbstractItemEditor#getItem()
	 */
	@Override
	public SearchEngineStore getItem() {
		final SearchEngineStore se = new SearchEngineStore();
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
	public void setItem(SearchEngineStore item) {
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
	public void validateItem() {
		if (Strings.isBlank(this.nameField.getValue()) || Strings.isBlank(this.baseUrlField.getValue())
				|| Strings.isBlank(this.queryParameterField.getValue())) {
			throw new RuntimeException("Name, base URL and query parameter are required.");
		}
	}

}