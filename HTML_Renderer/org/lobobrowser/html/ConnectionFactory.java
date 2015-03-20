package org.lobobrowser.html;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

/**
 * Creates {@link HttpURLConnection HTTP connections} for
 * {@link URL urls}.
 */
public interface ConnectionFactory {
  /**
   * Open an {@link HttpURLConnection} for the specified {@link URL}.
   *
   * @throws IOException
   */
  HttpURLConnection create(URL url) throws IOException;

  /**
   * Open an {@link HttpURLConnection} for the specified {@link URL}
   * and {@link Proxy}.
   *
   * @throws IOException
   */
  HttpURLConnection create(URL url, Proxy proxy) throws IOException;

  /**
   * A {@link ConnectionFactory} which uses the built-in
   * {@link URL#openConnection()}
   */
  ConnectionFactory DEFAULT = new ConnectionFactory() {
    public HttpURLConnection create(URL url) throws IOException {
      return (HttpURLConnection) url.openConnection();
    }

    public HttpURLConnection create(URL url, Proxy proxy) throws IOException {
      return (HttpURLConnection) url.openConnection(proxy);
    }
  };
}