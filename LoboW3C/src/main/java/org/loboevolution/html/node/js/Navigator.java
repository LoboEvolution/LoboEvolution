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

	int getMaxTouchPoints();

	boolean isMsManipulationViewsEnabled();

	int getMsMaxTouchPoints();

	boolean isMsPointerEnabled();

	boolean isPointerEnabled();

	boolean vibrate(int pattern);

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
