package org.loboevolution.audio;

/**
 * The AudioBuffer interface represents a short audio asset residing in memory, created from an audio file using the AudioContext.decodeAudioData() method, or from raw data using AudioContext.createBuffer(). Once put into an AudioBuffer, the audio can then be played by being passed into an AudioBufferSourceNode.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AudioBuffer">AudioBuffer - MDN</a>
 * @see <a href="https://webaudio.github.io/web-audio-api/#AudioBuffer"># AudioBuffer</a>
 */
public interface AudioBuffer {

    /**
     * A double.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AudioBuffer/duration">AudioBuffer.duration - MDN</a>
     */
    double duration();

    /**
     * The length property of the AudioBuffer interface returns an integer representing the length, in sample-frames, of the PCM data stored in the buffer.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AudioBuffer/length">AudioBuffer.length - MDN</a>
     */
    int length();

    /**
     * An integer.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AudioBuffer/numberOfChannels">AudioBuffer.numberOfChannels - MDN</a>
     */
    int numberOfChannels();

    /**
     * A floating-point value indicating the current sample rate of the buffers data, in samples per second.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AudioBuffer/sampleRate">AudioBuffer.sampleRate - MDN</a>
     */
    float sampleRate();
}
