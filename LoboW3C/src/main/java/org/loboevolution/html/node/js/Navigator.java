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

package org.loboevolution.html.node.js;

/**
 * The state and the identity of the user agent. It allows scripts to query it
 * and to register themselves to carry on some activities.
 *
 *
 *
 */
public interface Navigator {
	
	/*
	 * extends NavigatorAutomationInformation, NavigatorBeacon,
	 * NavigatorConcurrentHardware, NavigatorContentUtils, NavigatorCookies,
	 * NavigatorID, NavigatorLanguage, NavigatorOnLine, NavigatorPlugins,
	 * NavigatorStorage
	 */

	/**
	 * <p>getMaxTouchPoints.</p>
	 *
	 * @return a int.
	 */
	int getMaxTouchPoints();

	/**
	 * <p>isMsManipulationViewsEnabled.</p>
	 *
	 * @return a boolean.
	 */
	boolean isMsManipulationViewsEnabled();

	/**
	 * <p>getMsMaxTouchPoints.</p>
	 *
	 * @return a int.
	 */
	int getMsMaxTouchPoints();

	/**
	 * <p>isMsPointerEnabled.</p>
	 *
	 * @return a boolean.
	 */
	boolean isMsPointerEnabled();

	/**
	 * <p>isPointerEnabled.</p>
	 *
	 * @return a boolean.
	 */
	boolean isPointerEnabled();

	/**
	 * <p>vibrate.</p>
	 *
	 * @param pattern a int.
	 * @return a boolean.
	 */
	boolean vibrate(int pattern);

	/**
	 * <p>vibrate.</p>
	 *
	 * @param pattern a int.
	 * @return a boolean.
	 */
	boolean vibrate(int... pattern);

	/*
	 * ReadonlyArray<VRDisplay> getActiveVRDisplays();
	 * 
	 * Clipboard getClipboard();
	 * 
	 * CredentialsContainer getCredentials();
	 * 
	 * String getDoNotTrack();
	 * 
	 * Geolocation getGeolocation();
	 * 
	 * MediaDevices getMediaDevices();
	 * 
	 * Permissions getPermissions();
	 * 
	 * ServiceWorkerContainer getServiceWorker();
	 * 
	 * Array<Gamepad> getGamepads();
	 * 
	 * void getUserMedia(MediaStreamConstraints constraints, JsConsumer<MediaStream>
	 * successCallback, JsConsumer<MediaStreamError> errorCallback);
	 * 
	 * Promise<Array<VRDisplay>> getVRDisplays();
	 * 
	 * Promise<MediaKeySystemAccess> requestMediaKeySystemAccess(String keySystem,
	 * MediaKeySystemConfiguration... supportedConfigurations);
	 * 
	 * Promise<MediaKeySystemAccess> requestMediaKeySystemAccess(String keySystem,
	 * Array<MediaKeySystemConfiguration> supportedConfigurations);
	 * 
	 * Promise<MediaKeySystemAccess> requestMediaKeySystemAccess(String keySystem,
	 * JsIterable<MediaKeySystemConfiguration> supportedConfigurations);
	 */

}
