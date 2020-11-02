package org.loboevolution.component;

import java.io.InputStream;
import java.net.URL;

public interface IDownload {

    void downloadFile(URL url);

    void downloadFile(InputStream inputStream);
}
