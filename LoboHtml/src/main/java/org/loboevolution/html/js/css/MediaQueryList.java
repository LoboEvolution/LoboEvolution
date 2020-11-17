package org.loboevolution.html.js.css;

import com.gargoylesoftware.css.dom.MediaListImpl;
import org.loboevolution.html.js.Window;
import org.loboevolution.html.style.CSSUtilities;
import org.loboevolution.html.style.StyleSheetAggregator;

public class MediaQueryList {

    private final String media;

    private final Window window;

    public MediaQueryList(Window window, String mediaQueryString) {
        this.window = window;
        this.media = mediaQueryString;
    }

    public String getMedia() {
        return media;
    }

    public boolean isMatches() throws Exception {
        final String processedText = CSSUtilities.preProcessCss(media);
        MediaListImpl media = CSSUtilities.parseMedia(processedText);
        return StyleSheetAggregator.isActive(window, media);
    }

     public void addListener(final Object listener) {
       //TODO
    }

    public void removeListener(Object listener) {
        //TODO
    }
}

