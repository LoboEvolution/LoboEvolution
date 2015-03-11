/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.commonjs.module.provider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;



/**
 * A URL-based script provider that can load modules against a set of base
 * privileged and fallback URIs. It is deliberately not named "URI provider"
 * but a "URL provider" since it actually only works against those URIs that
 * are URLs (and the JRE has a protocol handler for them). It creates cache
 * validators that are suitable for use with both file: and http: URL
 * protocols. Specifically, it is able to use both last-modified timestamps and
 * ETags for cache revalidation, and follows the HTTP cache expiry calculation
 * model, and allows for fallback heuristic expiry calculation when no server
 * specified expiry is provided.
 * @author Attila Szegedi
 * @version $Id: UrlModuleSourceProvider.java,v 1.4 2011/04/07 20:26:12 hannes%helma.at Exp $
 */
public class UrlModuleSourceProvider extends ModuleSourceProviderBase
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The privileged uris. */
    private final Iterable<URI> privilegedUris;
    
    /** The fallback uris. */
    private final Iterable<URI> fallbackUris;
    
    /** The url connection security domain provider. */
    private final UrlConnectionSecurityDomainProvider
        urlConnectionSecurityDomainProvider;
    
    /** The url connection expiry calculator. */
    private final UrlConnectionExpiryCalculator urlConnectionExpiryCalculator;

    /**
     * Creates a new module script provider that loads modules against a set of
     * privileged and fallback URIs. It will use a fixed default cache expiry
     * of 60 seconds, and provide no security domain objects for the resource.
     * @param privilegedUris an iterable providing the privileged URIs. Can be
     * null if no privileged URIs are used.
     * @param fallbackUris an iterable providing the fallback URIs. Can be
     * null if no fallback URIs are used.
     */
    public UrlModuleSourceProvider(Iterable<URI> privilegedUris,
            Iterable<URI> fallbackUris)
    {
        this(privilegedUris, fallbackUris,
                new DefaultUrlConnectionExpiryCalculator(), null);
    }

    /**
     * Creates a new module script provider that loads modules against a set of
     * privileged and fallback URIs. It will use the specified heuristic cache
     * expiry calculator and security domain provider.
     * @param privilegedUris an iterable providing the privileged URIs. Can be
     * null if no privileged URIs are used.
     * @param fallbackUris an iterable providing the fallback URIs. Can be
     * null if no fallback URIs are used.
     * @param urlConnectionExpiryCalculator the calculator object for heuristic
     * calculation of the resource expiry, used when no expiry is provided by
     * the server of the resource. Can be null, in which case the maximum age
     * of cached entries without validation will be zero.
     * @param urlConnectionSecurityDomainProvider object that provides security
     * domain objects for the loaded sources. Can be null, in which case the
     * loaded sources will have no security domain associated with them.
     */
    public UrlModuleSourceProvider(Iterable<URI> privilegedUris,
            Iterable<URI> fallbackUris,
            UrlConnectionExpiryCalculator urlConnectionExpiryCalculator,
            UrlConnectionSecurityDomainProvider urlConnectionSecurityDomainProvider)
    {
        this.privilegedUris = privilegedUris;
        this.fallbackUris = fallbackUris;
        this.urlConnectionExpiryCalculator = urlConnectionExpiryCalculator;
        this.urlConnectionSecurityDomainProvider =
            urlConnectionSecurityDomainProvider;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.commonjs.module.provider.ModuleSourceProviderBase#loadFromPrivilegedLocations(java.lang.String, java.lang.Object)
     */
    @Override
    protected ModuleSource loadFromPrivilegedLocations(
            String moduleId, Object validator)
            throws IOException, URISyntaxException
    {
        return loadFromPathList(moduleId, validator, privilegedUris);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.commonjs.module.provider.ModuleSourceProviderBase#loadFromFallbackLocations(java.lang.String, java.lang.Object)
     */
    @Override
    protected ModuleSource loadFromFallbackLocations(
            String moduleId, Object validator)
            throws IOException, URISyntaxException
    {
        return loadFromPathList(moduleId, validator, fallbackUris);
    }

    /**
     * Load from path list.
     *
     * @param moduleId the module id
     * @param validator the validator
     * @param paths the paths
     * @return the module source
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws URISyntaxException the URI syntax exception
     */
    private ModuleSource loadFromPathList(String moduleId,
            Object validator, Iterable<URI> paths)
            throws IOException, URISyntaxException
    {
        if(paths == null) {
            return null;
        }
        for (URI path : paths) {
            final ModuleSource moduleSource = loadFromUri(
                    path.resolve(moduleId), path, validator);
            if (moduleSource != null) {
                return moduleSource;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.commonjs.module.provider.ModuleSourceProviderBase#loadFromUri(java.net.URI, java.net.URI, java.lang.Object)
     */
    @Override
    protected ModuleSource loadFromUri(URI uri, URI base, Object validator)
    throws IOException, URISyntaxException
    {
        // We expect modules to have a ".js" file name extension ...
        URI fullUri = new URI(uri + ".js");
        ModuleSource source = loadFromActualUri(fullUri, base, validator);
        // ... but for compatibility we support modules without extension,
        // or ids with explicit extension.
        return source != null ?
               source : loadFromActualUri(uri, base, validator);
    }

    /**
     * Load from actual uri.
     *
     * @param uri the uri
     * @param base the base
     * @param validator the validator
     * @return the module source
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected ModuleSource loadFromActualUri(URI uri, URI base, Object validator)
    throws IOException
    {
        final URL url = new URL(base == null ? null : base.toURL(), uri.toString());
        final long request_time = System.currentTimeMillis();
        final URLConnection urlConnection = openUrlConnection(url);
        final URLValidator applicableValidator;
        if(validator instanceof URLValidator) {
            final URLValidator uriValidator = ((URLValidator)validator);
            applicableValidator = uriValidator.appliesTo(uri) ? uriValidator :
                null;
        }
        else {
            applicableValidator = null;
        }
        if(applicableValidator != null) {
            applicableValidator.applyConditionals(urlConnection);
        }
        try {
            urlConnection.connect();
            if(applicableValidator != null &&
                    applicableValidator.updateValidator(urlConnection,
                            request_time, urlConnectionExpiryCalculator))
            {
                close(urlConnection);
                return NOT_MODIFIED;
            }

            return new ModuleSource(getReader(urlConnection),
                    getSecurityDomain(urlConnection), uri, base,
                    new URLValidator(uri, urlConnection, request_time,
                            urlConnectionExpiryCalculator));
        }
        catch(FileNotFoundException e) {
            return null;
        }
        catch(RuntimeException e) {
            close(urlConnection);
            throw e;
        }
        catch(IOException e) {
            close(urlConnection);
            throw e;
        }
    }

    /**
     * Gets the reader.
     *
     * @param urlConnection the url connection
     * @return the reader
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static Reader getReader(URLConnection urlConnection)
    throws IOException
    {
        return new InputStreamReader(urlConnection.getInputStream(),
                getCharacterEncoding(urlConnection));
    }

    /**
     * Gets the character encoding.
     *
     * @param urlConnection the url connection
     * @return the character encoding
     */
    private static String getCharacterEncoding(URLConnection urlConnection) {
        final ParsedContentType pct = new ParsedContentType(
                urlConnection.getContentType());
        final String encoding = pct.getEncoding();
        if(encoding != null) {
            return encoding;
        }
        final String contentType = pct.getContentType();
        if(contentType != null && contentType.startsWith("text/")) {
            return "8859_1";
        }
        else {
            return "utf-8";
        }
    }

    /**
     * Gets the security domain.
     *
     * @param urlConnection the url connection
     * @return the security domain
     */
    private Object getSecurityDomain(URLConnection urlConnection) {
        return urlConnectionSecurityDomainProvider == null ? null :
            urlConnectionSecurityDomainProvider.getSecurityDomain(
                    urlConnection);
    }

    /**
     * Close.
     *
     * @param urlConnection the url connection
     */
    private void close(URLConnection urlConnection) {
        try {
            urlConnection.getInputStream().close();
        }
        catch(IOException e) {
            onFailedClosingUrlConnection(urlConnection, e);
        }
    }

    /**
     * Override if you want to get notified if the URL connection fails to
     * close. Does nothing by default.
     * @param urlConnection the connection
     * @param cause the cause it failed to close.
     */
    protected void onFailedClosingUrlConnection(URLConnection urlConnection,
            IOException cause) {
    }

    /**
     * Can be overridden in subclasses to customize the URL connection opening
     * process. By default, just calls {@link URL#openConnection()}.
     * @param url the URL
     * @return a connection to the URL.
     * @throws IOException if an I/O error occurs.
     */
    protected URLConnection openUrlConnection(URL url) throws IOException {
        return url.openConnection();
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.commonjs.module.provider.ModuleSourceProviderBase#entityNeedsRevalidation(java.lang.Object)
     */
    @Override
    protected boolean entityNeedsRevalidation(Object validator) {
        return !(validator instanceof URLValidator)
                || ((URLValidator)validator).entityNeedsRevalidation();
    }

    /**
     * The Class URLValidator.
     */
    private static class URLValidator implements Serializable {
        
        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 1L;

        /** The uri. */
        private final URI uri;
        
        /** The last modified. */
        private final long lastModified;
        
        /** The entity tags. */
        private final String entityTags;
        
        /** The expiry. */
        private long expiry;

        /**
         * Instantiates a new URL validator.
         *
         * @param uri the uri
         * @param urlConnection the url connection
         * @param request_time the request_time
         * @param urlConnectionExpiryCalculator the url connection expiry calculator
         */
        public URLValidator(URI uri, URLConnection urlConnection,
                long request_time, UrlConnectionExpiryCalculator
                urlConnectionExpiryCalculator) {
            this.uri = uri;
            this.lastModified = urlConnection.getLastModified();
            this.entityTags = getEntityTags(urlConnection);
            expiry = calculateExpiry(urlConnection, request_time,
                    urlConnectionExpiryCalculator);
        }

        /**
         * Update validator.
         *
         * @param urlConnection the url connection
         * @param request_time the request_time
         * @param urlConnectionExpiryCalculator the url connection expiry calculator
         * @return true, if successful
         * @throws IOException Signals that an I/O exception has occurred.
         */
        boolean updateValidator(URLConnection urlConnection, long request_time,
                UrlConnectionExpiryCalculator urlConnectionExpiryCalculator)
        throws IOException
        {
            boolean isResourceChanged = isResourceChanged(urlConnection);
            if(!isResourceChanged) {
                expiry = calculateExpiry(urlConnection, request_time,
                        urlConnectionExpiryCalculator);
            }
            return isResourceChanged;
        }

        /**
         * Checks if is resource changed.
         *
         * @param urlConnection the url connection
         * @return true, if is resource changed
         * @throws IOException Signals that an I/O exception has occurred.
         */
        private boolean isResourceChanged(URLConnection urlConnection)
        throws IOException {
            if(urlConnection instanceof HttpURLConnection) {
                return ((HttpURLConnection)urlConnection).getResponseCode() ==
                    HttpURLConnection.HTTP_NOT_MODIFIED;
            }
            return lastModified == urlConnection.getLastModified();
        }

        /**
         * Calculate expiry.
         *
         * @param urlConnection the url connection
         * @param request_time the request_time
         * @param urlConnectionExpiryCalculator the url connection expiry calculator
         * @return the long
         */
        private long calculateExpiry(URLConnection urlConnection,
                long request_time, UrlConnectionExpiryCalculator
                urlConnectionExpiryCalculator)
        {
            if("no-cache".equals(urlConnection.getHeaderField("Pragma"))) {
                return 0L;
            }
            final String cacheControl = urlConnection.getHeaderField(
                    "Cache-Control");
            if(cacheControl != null ) {
                if(cacheControl.indexOf("no-cache") != -1) {
                    return 0L;
                }
                final int max_age = getMaxAge(cacheControl);
                if(-1 != max_age) {
                    final long response_time = System.currentTimeMillis();
                    final long apparent_age = Math.max(0, response_time -
                            urlConnection.getDate());
                    final long corrected_received_age = Math.max(apparent_age,
                            urlConnection.getHeaderFieldInt("Age", 0) * 1000L);
                    final long response_delay = response_time - request_time;
                    final long corrected_initial_age = corrected_received_age +
                        response_delay;
                    final long creation_time = response_time -
                        corrected_initial_age;
                    return max_age * 1000L + creation_time;
                }
            }
            final long explicitExpiry = urlConnection.getHeaderFieldDate(
                    "Expires", -1L);
            if(explicitExpiry != -1L) {
                return explicitExpiry;
            }
            return urlConnectionExpiryCalculator == null ? 0L :
                urlConnectionExpiryCalculator.calculateExpiry(urlConnection);
        }

        /**
         * Gets the max age.
         *
         * @param cacheControl the cache control
         * @return the max age
         */
        private int getMaxAge(String cacheControl) {
            final int maxAgeIndex = cacheControl.indexOf("max-age");
            if(maxAgeIndex == -1) {
                return -1;
            }
            final int eq = cacheControl.indexOf('=', maxAgeIndex + 7);
            if(eq == -1) {
                return -1;
            }
            final int comma = cacheControl.indexOf(',', eq + 1);
            final String strAge;
            if(comma == -1) {
                strAge = cacheControl.substring(eq + 1);
            }
            else {
                strAge = cacheControl.substring(eq + 1, comma);
            }
            try {
                return Integer.parseInt(strAge);
            }
            catch(NumberFormatException e) {
                return -1;
            }
        }

        /**
         * Gets the entity tags.
         *
         * @param urlConnection the url connection
         * @return the entity tags
         */
        private String getEntityTags(URLConnection urlConnection) {
            final List<String> etags = urlConnection.getHeaderFields().get("ETag");
            if(etags == null || etags.isEmpty()) {
                return null;
            }
            final StringBuilder b = new StringBuilder();
            final Iterator<String> it = etags.iterator();
            b.append(it.next());
            while(it.hasNext()) {
                b.append(", ").append(it.next());
            }
            return b.toString();
        }

        /**
         * Applies to.
         *
         * @param uri the uri
         * @return true, if successful
         */
        boolean appliesTo(URI uri) {
            return this.uri.equals(uri);
        }

        /**
         * Apply conditionals.
         *
         * @param urlConnection the url connection
         */
        void applyConditionals(URLConnection urlConnection) {
            if(lastModified != 0L) {
                urlConnection.setIfModifiedSince(lastModified);
            }
            if(entityTags != null && entityTags.length() > 0) {
                urlConnection.addRequestProperty("If-None-Match", entityTags);
            }
        }

        /**
         * Entity needs revalidation.
         *
         * @return true, if successful
         */
        boolean entityNeedsRevalidation() {
            return System.currentTimeMillis() > expiry;
        }
    }
}