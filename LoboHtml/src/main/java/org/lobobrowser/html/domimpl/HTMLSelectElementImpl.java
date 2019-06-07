package org.lobobrowser.html.domimpl;

import java.util.ArrayList;

import org.lobobrowser.html.FormInput;
import org.lobobrowser.html.dom.HTMLElement;
import org.lobobrowser.html.dom.HTMLOptionsCollection;
import org.lobobrowser.html.dom.HTMLSelectElement;
import org.mozilla.javascript.Function;
import org.w3c.dom.DOMException;

public class HTMLSelectElementImpl extends HTMLBaseInputElement implements HTMLSelectElement {
	private int deferredSelectedIndex = -1;

	private Boolean multipleState = null;

	private Function onchange;

	private HTMLOptionsCollection options;

	public HTMLSelectElementImpl(String name) {
		super(name);
	}

	@Override
	public void add(HTMLElement element, HTMLElement before) throws DOMException {
		insertBefore(element, before);
	}

	@Override
	protected FormInput[] getFormInputs() {
		// Needs to be overriden for forms to submit.
		final InputContext ic = this.inputContext;
		String[] values = ic == null ? null : ic.getValues();
		if (values == null) {
			final String value = getValue();
			values = value == null ? null : new String[] { value };
			if (values == null) {
				return null;
			}
		}
		final String name = getName();
		if (name == null) {
			return null;
		}
		final ArrayList<FormInput> formInputs = new ArrayList<FormInput>();
		for (final String value : values) {
			formInputs.add(new FormInput(name, value));
		}
		return (FormInput[]) formInputs.toArray(FormInput.EMPTY_ARRAY);
	}

	@Override
	public int getLength() {
		return getOptions().getLength();
	}

	@Override
	public boolean getMultiple() {
		final Boolean m = this.multipleState;
		if (m != null) {
			return m.booleanValue();
		}
		return getAttributeAsBoolean("multiple");
	}

	public Function getOnchange() {
		return getEventFunction(this.onchange, "onchange");
	}

	@Override
	public HTMLOptionsCollection getOptions() {
		synchronized (this) {
			if (this.options == null) {
				this.options = new HTMLOptionsCollectionImpl(this);
			}
			return this.options;
		}
	}

	@Override
	public int getSelectedIndex() {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			return ic.getSelectedIndex();
		} else {
			return this.deferredSelectedIndex;
		}
	}

	@Override
	public int getSize() {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			return ic.getVisibleSize();
		} else {
			return 0;
		}
	}

	@Override
	public String getType() {
		return getMultiple() ? "select-multiple" : "select-one";
	}

	@Override
	public void remove(int index) {
		try {
			removeChild(getOptions().item(index));
		} catch (final DOMException de) {
			this.warn("remove(): Unable to remove option at index " + index + ".", de);
		}
	}

	@Override
	public void resetInput() {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.resetInput();
		}
	}

	@Override
	public void setInputContext(InputContext ic) {
		super.setInputContext(ic);
		if (ic != null) {
			ic.setSelectedIndex(this.deferredSelectedIndex);
		}
	}

	@Override
	public void setLength(int length) throws DOMException {
		getOptions().setLength(length);
	}

	@Override
	public void setMultiple(boolean multiple) {
		final boolean prevMultiple = getMultiple();
		this.multipleState = Boolean.valueOf(multiple);
		if (prevMultiple != multiple) {
			informLayoutInvalid();
		}
	}

	public void setOnchange(Function value) {
		this.onchange = value;
	}

	@Override
	public void setSelectedIndex(int selectedIndex) {
		setSelectedIndexImpl(selectedIndex);
		final HTMLOptionsCollection options = getOptions();
		final int length = options.getLength();
		for (int i = 0; i < length; i++) {
			final HTMLOptionElementImpl option = (HTMLOptionElementImpl) options.item(i);
			option.setSelectedImpl(i == selectedIndex);
		}
	}

	void setSelectedIndexImpl(int selectedIndex) {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setSelectedIndex(selectedIndex);
		} else {
			this.deferredSelectedIndex = selectedIndex;
		}
	}

	@Override
	public void setSize(int size) {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setVisibleSize(size);
		}
	}
}
