package org.loboevolution.audio;

/**
 * The BaseAudioContext interface of the Web Audio API acts as a base definition for online and offline audio-processing graphs, as represented by AudioContext and OfflineAudioContext respectively. You wouldn't use BaseAudioContext directly &mdash; you'd use its features via one of these two inheriting interfaces.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/BaseAudioContext">BaseAudioContext - MDN</a>
 * @see <a href="https://webaudio.github.io/web-audio-api/#BaseAudioContext"># BaseAudioContext</a>
 */
public interface BaseAudioContext {

  /**
   * An AudioBuffer configured based on the specified options.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/BaseAudioContext/createBuffer">BaseAudioContext.createBuffer - MDN</a>
   * @see <a href="https://webaudio.github.io/web-audio-api/#dom-baseaudiocontext-createbuffer">createBuffer() - Web Audio API</a>
   */
  AudioBuffer createBuffer(int numberOfChannels, int length, float sampleRate);
}
