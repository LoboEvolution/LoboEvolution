/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.net;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

/**
 * <p>
 * A convenience class from which to extend all non-visual JavaBeans. It manages
 * the PropertyChange notification system, making it relatively trivial to add
 * support for property change events in getters/setters.
 * </p>
 *
 * <p>
 * A non-visual java bean is a Java class that conforms to the JavaBean patterns
 * to allow visual manipulation of the bean's properties and event handlers at
 * design-time.
 * </p>
 *
 * Here is a simple example bean that contains one property, foo, and the proper
 * pattern for implementing property change notification:
 *
 * <pre>
 *
 *  public class ABean extends JavaBean {
 *    private String foo;
 *
 *    public void setFoo(String newFoo) {
 *      String old = getFoo();
 *      this.foo = newFoo;
 *      firePropertyChange("foo", old, getFoo());
 *   }
 *
 *    public String getFoo() {
 *      return foo;
 *   }
 * }
 *
 * </pre>
 *
 * <p>
 * You will notice that "getFoo()" is used in the setFoo method rather than
 * accessing "foo" directly for the gets. This is done intentionally so that if
 * a subclass overrides getFoo() to return, for instance, a constant value the
 * property change notification system will continue to work properly.
 * </p>
 *
 * <p>
 * The firePropertyChange method takes into account the old value and the new
 * value. Only if the two differ will it fire a property change event. So you
 * can be assured from the above code fragment that a property change event will
 * only occur if old is indeed different from getFoo()
 * </p>
 *
 * JavaBean also supports VetoablePropertyChange events. These
 * events are similar to PropertyChange events, except a special
 * exception can be used to veto changing the property. For example, perhaps the
 * property is changing from "fred" to "red", but a listener deems that "red" is
 * unexceptable. In this case, the listener can fire a veto exception and the
 * property must remain "fred". For example:
 *
 * <pre>
 *
 *  public class ABean extends JavaBean {
 *    private String foo;
 *
 *    public void setFoo(String newFoo) throws PropertyVetoException {
 *      String old = getFoo();
 *      this.foo = newFoo;
 *      fireVetoableChange("foo", old, getFoo());
 *   }
 *
 *    public String getFoo() {
 *      return foo;
 *   }
 * }
 *
 *  public class Tester {
 *    public static void main(String... args) {
 *      try {
 *        ABean a = new ABean();
 *        a.setFoo("fred");
 *        a.addVetoableChangeListener(new VetoableChangeListener() {
 *          public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
 *            if ("red".equals(evt.getNewValue()) {
 *              throw new PropertyVetoException("Cannot be red!", evt);
 *           }
 *         }
 *       }
 *        a.setFoo("red");
 *     } catch (Exception e) {
 *        logger.error(e); // this will be executed
 *     }
 *   }
 * }
 *
 * </pre>
 *
 * status REVIEWED
 *
 * Author rbair
 *
 */
public abstract class AbstractBean {
	/**
	 * Helper class that manages all the property change notification machinery.
	 * PropertyChangeSupport cannot be extended directly because it requires a
	 * bean in the constructor, and the "this" argument is not valid until after
	 * super construction. Hence, delegation instead of extension
	 */
	private final PropertyChangeSupport pcs;
	/**
	 * Helper class that manages all the veto property change notification
	 * machinery.
	 */
	private final VetoableChangeSupport vcs;

	/**
	 * Creates a new instance of JavaBean.
	 */
	protected AbstractBean() {
		pcs = new PropertyChangeSupport(this);
		vcs = new VetoableChangeSupport(this);
	}

	/**
	 * Creates a new instance of JavaBean, using the supplied
	 * PropertyChangeSupport and VetoableChangeSupport delegates. Neither of
	 * these may be null.
	 *
	 * @param pcs
	 *            the pcs
	 * @param vcs
	 *            the vcs
	 */
	protected AbstractBean(final PropertyChangeSupport pcs, final VetoableChangeSupport vcs) {
		if (pcs == null) {
			throw new IllegalArgumentException ("PropertyChangeSupport must not be null");
		}
		if (vcs == null) {
			throw new IllegalArgumentException ("VetoableChangeSupport must not be null");
		}
		this.pcs = pcs;
		this.vcs = vcs;
	}

	/**
	 * Add a PropertyChangeListener to the listener list. The listener is
	 * registered for all properties. The same listener object may be added more
	 * than once, and will be called as many times as it is added. If
	 * listener is null, no exception is thrown and no action is
	 * taken.
	 *
	 * @param listener
	 *            The PropertyChangeListener to be added
	 */
	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	/**
	 * Remove a PropertyChangeListener from the listener list. This removes a
	 * PropertyChangeListener that was registered for all properties. If
	 * listener was added more than once to the same event source,
	 * it will be notified one less time after being removed. If
	 * listener is null, or was never added, no exception is thrown
	 * and no action is taken.
	 *
	 * @param listener
	 *            The PropertyChangeListener to be removed
	 */
	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	/**
	 * Gets the property change listeners.
	 *
	 * @return the property change listeners
	 */
	public PropertyChangeListener[] getPropertyChangeListeners() {
		return pcs.getPropertyChangeListeners();
	}

	/**
	 * Add a PropertyChangeListener for a specific property. The listener will
	 * be invoked only when a call on firePropertyChange names that specific
	 * property. The same listener object may be added more than once. For each
	 * property, the listener will be invoked the number of times it was added
	 * for that property. If propertyName or listener
	 * is null, no exception is thrown and no action is taken.
	 *
	 * @param propertyName
	 *            The name of the property to listen on.
	 * @param listener
	 *            The PropertyChangeListener to be added
	 */
	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * Remove a PropertyChangeListener for a specific property. If
	 * listener was added more than once to the same event source
	 * for the specified property, it will be notified one less time after being
	 * removed. If propertyName is null, no exception is thrown and
	 * no action is taken. If listener is null, or was never added
	 * for the specified property, no exception is thrown and no action is
	 * taken.
	 *
	 * @param propertyName
	 *            The name of the property that was listened on.
	 * @param listener
	 *            The PropertyChangeListener to be removed
	 */
	public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * Returns an array of all the listeners which have been associated with the
	 * named property.
	 *
	 * @param propertyName
	 *            The name of the property being listened to
	 * @return all of the PropertyChangeListeners associated with
	 *         the named property. If no such listeners have been added, or if
	 *         propertyName is null, an empty array is returned.
	 */
	public PropertyChangeListener[] getPropertyChangeListeners(final String propertyName) {
		return pcs.getPropertyChangeListeners(propertyName);
	}

	/**
	 * Report a bound property update to any registered listeners. No event is
	 * fired if old and new are equal and non-null.
	 *
	 * <p>
	 * This is merely a convenience wrapper around the more general
	 * firePropertyChange method that takes {@code PropertyChangeEvent} value.
	 *
	 * @param propertyName
	 *            The programmatic name of the property that was changed.
	 * @param oldValue
	 *            The old value of the property.
	 * @param newValue
	 *            The new value of the property.
	 */
	protected void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {
		pcs.firePropertyChange(propertyName, oldValue, newValue);
	}

	/**
	 * Fire an existing PropertyChangeEvent to any registered listeners. No
	 * event is fired if the given event's old and new values are equal and
	 * non-null.
	 *
	 * @param evt
	 *            The PropertyChangeEvent object.
	 */
	protected void firePropertyChange(final PropertyChangeEvent evt) {
		pcs.firePropertyChange(evt);
	}

	/**
	 * Report a bound indexed property update to any registered listeners.
	 * <p>
	 * No event is fired if old and new values are equal and non-null.
	 *
	 * <p>
	 * This is merely a convenience wrapper around the more general
	 * firePropertyChange method that takes {@code PropertyChangeEvent} value.
	 *
	 * @param propertyName
	 *            The programmatic name of the property that was changed.
	 * @param index
	 *            index of the property element that was changed.
	 * @param oldValue
	 *            The old value of the property.
	 * @param newValue
	 *            The new value of the property.
	 */
	protected void fireIndexedPropertyChange(final String propertyName, final int index, final Object oldValue,
			final Object newValue) {
		pcs.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
	}

	/**
	 * Check if there are any listeners for a specific property, including those
	 * registered on all properties. If propertyName is null, only
	 * check for listeners registered on all properties.
	 *
	 * @param propertyName
	 *            the property name.
	 * @return true if there are one or more listeners for the given property
	 */
	protected boolean hasPropertyChangeListeners(final String propertyName) {
		return pcs.hasListeners(propertyName);
	}

	/**
	 * Check if there are any listeners for a specific property, including those
	 * registered on all properties. If propertyName is null, only
	 * check for listeners registered on all properties.
	 *
	 * @param propertyName
	 *            the property name.
	 * @return true if there are one or more listeners for the given property
	 */
	protected boolean hasVetoableChangeListeners(final String propertyName) {
		return vcs.hasListeners(propertyName);
	}

	/**
	 * Add a VetoableListener to the listener list. The listener is registered
	 * for all properties. The same listener object may be added more than once,
	 * and will be called as many times as it is added. If listener
	 * is null, no exception is thrown and no action is taken.
	 *
	 * @param listener
	 *            The VetoableChangeListener to be added
	 */
	public void addVetoableChangeListener(final VetoableChangeListener listener) {
		vcs.addVetoableChangeListener(listener);
	}

	/**
	 * Remove a VetoableChangeListener from the listener list. This removes a
	 * VetoableChangeListener that was registered for all properties. If
	 * listener was added more than once to the same event source,
	 * it will be notified one less time after being removed. If
	 * listener is null, or was never added, no exception is thrown
	 * and no action is taken.
	 *
	 * @param listener
	 *            The VetoableChangeListener to be removed
	 */
	public void removeVetoableChangeListener(final VetoableChangeListener listener) {
		vcs.removeVetoableChangeListener(listener);
	}

	/**
	 * Gets the vetoable change listeners.
	 *
	 * @return the vetoable change listeners
	 */
	public VetoableChangeListener[] getVetoableChangeListeners() {
		return vcs.getVetoableChangeListeners();
	}

	/**
	 * Add a VetoableChangeListener for a specific property. The listener will
	 * be invoked only when a call on fireVetoableChange names that specific
	 * property. The same listener object may be added more than once. For each
	 * property, the listener will be invoked the number of times it was added
	 * for that property. If propertyName or listener
	 * is null, no exception is thrown and no action is taken.
	 *
	 * @param propertyName
	 *            The name of the property to listen on.
	 * @param listener
	 *            The VetoableChangeListener to be added
	 */
	public void addVetoableChangeListener(final String propertyName, final VetoableChangeListener listener) {
		vcs.addVetoableChangeListener(propertyName, listener);
	}

	/**
	 * Remove a VetoableChangeListener for a specific property. If
	 * listener was added more than once to the same event source
	 * for the specified property, it will be notified one less time after being
	 * removed. If propertyName is null, no exception is thrown and
	 * no action is taken. If listener is null, or was never added
	 * for the specified property, no exception is thrown and no action is
	 * taken.
	 *
	 * @param propertyName
	 *            The name of the property that was listened on.
	 * @param listener
	 *            The VetoableChangeListener to be removed
	 */
	public void removeVetoableChangeListener(final String propertyName, final VetoableChangeListener listener) {
		vcs.removeVetoableChangeListener(propertyName, listener);
	}

	/**
	 * Returns an array of all the listeners which have been associated with the
	 * named property.
	 *
	 * @param propertyName
	 *            The name of the property being listened to
	 * @return all the VetoableChangeListeners associated with the
	 *         named property. If no such listeners have been added, or if
	 *         propertyName is null, an empty array is returned.
	 */
	public VetoableChangeListener[] getVetoableChangeListeners(final String propertyName) {
		return vcs.getVetoableChangeListeners(propertyName);
	}

	/**
	 * Report a vetoable property update to any registered listeners. If anyone
	 * vetos the change, then fire a new event reverting everyone to the old
	 * value and then rethrow the PropertyVetoException.
	 * <p>
	 * No event is fired if old and new are equal and non-null.
	 *
	 * @param propertyName
	 *            The programmatic name of the property that is about to
	 *            change..
	 * @param oldValue
	 *            The old value of the property.
	 * @param newValue
	 *            The new value of the property.
	 * @exception PropertyVetoException
	 *                if the recipient wishes the property change to be rolled
	 *                back.
	 * @throws java.beans.PropertyVetoException if any.
	 */
	protected void fireVetoableChange(final String propertyName, final Object oldValue, final Object newValue)
			throws PropertyVetoException {
		vcs.fireVetoableChange(propertyName, oldValue, newValue);
	}

	/**
	 * Fire a vetoable property update to any registered listeners. If anyone
	 * vetos the change, then fire a new event reverting everyone to the old
	 * value and then rethrow the PropertyVetoException.
	 * <p>
	 * No event is fired if old and new are equal and non-null.
	 *
	 * @param evt
	 *            The PropertyChangeEvent to be fired.
	 * @exception PropertyVetoException
	 *                if the recipient wishes the property change to be rolled
	 *                back.
	 * @throws java.beans.PropertyVetoException if any.
	 */
	protected void fireVetoableChange(final PropertyChangeEvent evt) throws PropertyVetoException {
		vcs.fireVetoableChange(evt);
	}
}
