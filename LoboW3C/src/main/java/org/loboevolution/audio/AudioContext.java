package org.loboevolution.audio;

/**
 * The AudioContext interface represents an audio-processing graph built from audio modules linked together, each represented by an AudioNode.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AudioContext">AudioContext - MDN</a>
 * @see <a href="https://webaudio.github.io/web-audio-api/#AudioContext"># AudioContext</a>
 */
public interface AudioContext extends BaseAudioContext {

    /**
     * The baseLatency read-only property of the AudioContext interface returns a double that represents the number of seconds of processing latency incurred by the AudioContext passing an audio buffer from the AudioDestinationNode &mdash; i.e. the end of the audio graph &mdash; into the host system's audio subsystem ready for playing.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AudioContext/baseLatency">AudioContext.baseLatency - MDN</a>
     * @see <a href="https://webaudio.github.io/web-audio-api/#dom-audiocontext-baselatency">baseLatency - Web Audio API</a>
     */
    Double getBaseLatency();

    /**
     * The outputLatency read-only property of the AudioContext Interface provides an estimation of the output latency of the current audio context.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AudioContext/outputLatency">AudioContext.outputLatency - MDN</a>
     * @see <a href="https://webaudio.github.io/web-audio-api/#dom-audiocontext-outputlatency">outputLatency - Web Audio API</a>
     */
    Double getOutputLatency();

    /**
     * The close() method of the AudioContext Interface closes the audio context, releasing any system audio resources that it uses.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AudioContext/close">AudioContext.close - MDN</a>
     * @see <a href="https://webaudio.github.io/web-audio-api/#dom-audiocontext-close">close() - Web Audio API</a>
     */
    void close();

    /**
     * The resume() method of the AudioContext interface resumes the progression of time in an audio context that has previously been suspended.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AudioContext/resume">AudioContext.resume - MDN</a>
     * @see <a href="https://webaudio.github.io/web-audio-api/#dom-audiocontext-resume">resume() - Web Audio API</a>
     */
    void resume();

    /**
     * The suspend() method of the AudioContext Interface suspends the progression of time in the audio context, temporarily halting audio hardware access and reducing CPU/battery usage in the process &mdash; this is useful if you want an application to power down the audio hardware when it will not be using an audio context for a while.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AudioContext/suspend">AudioContext.suspend - MDN</a>
     * @see <a href="https://webaudio.github.io/web-audio-api/#dom-audiocontext-suspend">close() - Web Audio API</a>
     */
    void suspend();
}
