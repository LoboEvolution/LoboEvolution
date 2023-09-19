/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.node.js;

/**
 * The state and the identity of the user agent. It allows scripts to query it
 * and to register themselves to carry on some activities.
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
