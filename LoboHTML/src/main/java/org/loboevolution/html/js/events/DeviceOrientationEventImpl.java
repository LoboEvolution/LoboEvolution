package org.loboevolution.html.js.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.events.DeviceOrientationEvent;

@NoArgsConstructor
@Getter
public class DeviceOrientationEventImpl extends EventImpl implements DeviceOrientationEvent {

    private Double alpha;
    private Double beta;
    private Double gamma;
    private boolean absolute;

    /**
     * <p>Constructor for DeviceOrientationEventImpl.</p>
     *
     * @param params event constructor parameters
     */
    public DeviceOrientationEventImpl(Object[] params) {
        try {
            setParams(params);
        } catch (DOMException e) {
            throw new RuntimeException("Failed to initialize Event", e);
        }
    }

    @Override
    public void initDeviceOrientationEvent(String type, Boolean bubbles, boolean cancelable,
                                           Double alpha, Double beta, Double gamma, boolean absolute) {
        super.initEvent(type, bubbles, cancelable);
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.absolute = absolute;
    }

    @Override
    public boolean getAbsolute() {
        return absolute;
    }

    @Override
    public String toString() {
        return "[object DeviceOrientationEvent]";
    }
}