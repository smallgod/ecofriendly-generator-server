package com.namaraka.ggserver.httpmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONStringer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.namaraka.ggserver.constant.APIContentType;
import com.namaraka.ggserver.constant.HTTPMethod;
import com.namaraka.ggserver.constant.NamedConstants;
import com.namaraka.ggserver.interfaces.RemoteRequest;
import com.namaraka.ggserver.utils.GeneralUtils;
import java.util.Map;
import org.apache.http.client.methods.HttpGet;

/**
 * Class to manage all HTTP connection needs of the application
 */
public final class HttpClientPool {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientPool.class);
    private static int readTimeout;
    private static int connTimeout;
    private static int connPerRoute;
    private static int maxConnections;
    private final APIContentType contentType;
    //private final String apiContentType;

    //private RemoteRequest remoteUnit;
    public HttpClientPool(int readTimeout, int connTimeout, int connPerRoute, int maxConnections, APIContentType contentType) {

        HttpClientPool.readTimeout = readTimeout;
        HttpClientPool.connTimeout = connTimeout;
        HttpClientPool.connPerRoute = connPerRoute;
        HttpClientPool.maxConnections = maxConnections;
        this.contentType = contentType;
        //this.apiContentType = contentType.getValue();
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public int getConnTimeout() {
        return connTimeout;
    }

    public int getConnPerRoute() {
        return connPerRoute;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public APIContentType getContentType() {
        return contentType;
    }

    /**
     * Single-element enum to implement Singleton
     */
    private static enum Singleton {

        /**
         * Just one, so constructor will be called once.
         */
        Client;

        /**
         * The thread-safe client.
         */
        protected final CloseableHttpClient threadSafeClient;

        /**
         * The pool idleConnMonitor
         */
        protected final IdleConnectionMonitorThread idleConnMonitor;

        // The constructor creates it - thus late
        private Singleton() {

            PoolingHttpClientConnectionManager connManager = null;
            try {
                connManager = getHttpClientPool();
            } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | KeyManagementException | UnrecoverableKeyException ex) {
                logger.error("Exception creating poolinghttpclientconnmanager: " + ex.getMessage());

            }

            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setSocketTimeout(HttpClientPool.connTimeout)
                    .setConnectTimeout(HttpClientPool.connTimeout)
                    .setConnectionRequestTimeout(HttpClientPool.readTimeout)
                    .build();

            // Build the client.
            threadSafeClient = HttpClients.custom()
                    .setConnectionManager(connManager)
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .build();

            // Start up an eviction thread.
            idleConnMonitor = new IdleConnectionMonitorThread(connManager);

            // Don't stop quitting.
            idleConnMonitor.setDaemon(true);
            idleConnMonitor.start();
        }

        private CloseableHttpClient get() {
            return threadSafeClient;
        }

    }

    // Watches for stale connections and evicts them.
    private static class IdleConnectionMonitorThread extends Thread {

        // The manager to watch.
        private final PoolingHttpClientConnectionManager connManager;

        // Use a BlockingQueue to stop everything.
        private final BlockingQueue<Stop> stopSignal = new ArrayBlockingQueue<>(1);

        // Pushed up the queue.
        private static class Stop {

            // The return queue.
            private final BlockingQueue<Stop> stop = new ArrayBlockingQueue<>(1);

            // Called by the process that is being told to stop.
            private void stopped() {

                // Push me back up the queue to indicate we are now stopped.
                stop.add(this);
            }

            // Called by the process requesting the stop.
            private void waitForStopped() throws InterruptedException {

                // Wait until the callee acknowledges that it has stopped.
                stop.take();
            }

        }

        private IdleConnectionMonitorThread(PoolingHttpClientConnectionManager connManager) {
            super();
            this.connManager = connManager;
        }

        @Override
        public void run() {

            try {

                // Holds the stop request that stopped the process.
                Stop stopRequest;

                // Every 5 seconds.
                while ((stopRequest = stopSignal.poll(5, TimeUnit.SECONDS)) == null) {

                    // Close expired connections
                    connManager.closeExpiredConnections();

                    // Optionally, close connections that have been idle too long.
                    connManager.closeIdleConnections(60, TimeUnit.SECONDS);

                    // Look at pool stats.
                    logger.debug("Stats: {}", connManager.getTotalStats());
                }

                // Acknowledge the stop request.
                stopRequest.stopped();

            } catch (InterruptedException ex) {
                // terminate
                logger.error("interrupted exception while connection thread is running: " + ex.getMessage());
            }
        }

        /**
         * Shutdown the http client and the connection manager
         *
         * @throws InterruptedException
         * @throws IOException
         */
        private void shutdown() throws InterruptedException, IOException {

            logger.debug("Shutting down client pool");

            // Signal the stop to the thread.
            Stop stop = new Stop();
            stopSignal.add(stop);

            // Wait for the stop to complete.
            stop.waitForStopped();

            // Close the pool - Added
            closeHttpclient();

            // Close the connection manager.
            connManager.close();

            logger.info("Client pool shut down");
        }

    }

//    private static PoolingHttpClientConnectionManager getHttpClientPool() throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, KeyManagementException, UnrecoverableKeyException {
//
//        PlainConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
//
//        Registry<ConnectionSocketFactory> connSocketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
//                .register("http", plainSocketFactory)
//                .build();
//
//        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(connSocketFactoryRegistry);
//
//        // Increase max total connection to 200
//        connManager.setMaxTotal(props.getMaxtotalconnections());
//        // Increase default max connection per route to 20
//        connManager.setDefaultMaxPerRoute(props.getConnectionsperroute());
//
//        //HttpClientConnectionManager poolHttpClientConnMgr = new PoolingHttpClientConnectionManager(connSocketFactoryRegistry);
//        //HttpClients.custom().setConnectionManager(poolHttpClientConnMgr).build();
//        return connManager;
//
//    }
    private static PoolingHttpClientConnectionManager getHttpClientPool() throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, KeyManagementException, UnrecoverableKeyException {

        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        try (FileInputStream inputStream = new FileInputStream(new File("myTrustStore.truststore"))) {
            trustStore.load(inputStream, "nopassword".toCharArray());
        }

        // this key store must contain the key/cert of the client
        //SSLContextBuilder sslContextBuilder = SSLContexts.custom().loadKeyMaterial(trustStore, "keyStorePassword".toCharArray());
        // this key store must contain the certs needed and trusted to verify the servers cert
        //sslContextBuilder.loadTrustMaterial(trustStore);
        //SSLContext sslContext2 = sslContextBuilder.build();
        //LayeredConnectionSocketFactory sslsf2 = new SSLConnectionSocketFactory(sslContext2);
        //SSLContext sslContext = SSLContext.
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(trustStore).build();
        LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, getHostNameVerifier());

        PlainConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();

        Registry<ConnectionSocketFactory> socketConnFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainSocketFactory)
                .register("https", sslSocketFactory)
                .build();

        PoolingHttpClientConnectionManager httpClientPoolConnManager = new PoolingHttpClientConnectionManager(socketConnFactoryRegistry);

        // Increase max total connection to 200
        httpClientPoolConnManager.setMaxTotal(HttpClientPool.maxConnections);
        // Increase default max connection per route to 20
        httpClientPoolConnManager.setDefaultMaxPerRoute(HttpClientPool.connPerRoute);

        //Defines period of inactivity in milliseconds after which persistent connections must be re-validated
        //httpClientPoolConnManager.setValidateAfterInactivity(30000);
        //HttpClientConnectionManager poolHttpClientConnMgr = new PoolingHttpClientConnectionManager(connSocketFactoryRegistry);
        //HttpClients.custom().setConnectionManager(poolHttpClientConnMgr).build();
        return httpClientPoolConnManager;

    }

    //for self-signed certificates
    private static CloseableHttpClient prepareClient4SelfSignedCert() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).useTLS().build();

        HttpClientBuilder builder = HttpClientBuilder.create();

        SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(sslContext, getHostNameVerifier());

        builder.setSSLSocketFactory(sslConnectionFactory);

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslConnectionFactory)
                .register("http", new PlainConnectionSocketFactory())
                .build();

        HttpClientConnectionManager ccm = new BasicHttpClientConnectionManager(registry);
        builder.setConnectionManager(ccm);
        return builder.build();

    }

    private static HostnameVerifier getHostNameVerifier() {

        //avoid "HTTPS hostname wrong: should be <myhostname>" exception:
        HostnameVerifier hv = new HostnameVerifier() {

            //String urlHostName = "https://10.31.30.126:443/LoadWSApp/LoadWebServiceV1";
            //String urlHodstsName = Props.getInstance().getSptransferURL();
            @Override
            public boolean verify(String urlHostName, SSLSession session) {

                if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
                    logger.debug("Warning: URL host '" + urlHostName + "' is different from SSLSession host '" + session.getPeerHost() + "'.");
                }

                // double check to  make sure all localhost connections are allowed
                if (urlHostName.equalsIgnoreCase("localhost")) {
                    logger.info("Warning: URL host '" + urlHostName + "' connecting... '" + "'.");

                    return true;
                }

                logger.debug("Host URL: '" + urlHostName + "' && SSLSession host URL: '" + session.getPeerHost() + "'.");

                return true; //also accept different hostname (e.g. domain name instead of IP address)
            }

        };

        return hv;
    }

    ///////////////////// utility methods start here /////////////////////////
    //////////////////////////////////////////////////////////////////////////
    private static CloseableHttpClient getHttpClient() {
        // The thread safe client is held by the singleton.
        return Singleton.Client.get();
    }

    /**
     * Shutdown the http client and the connection manager
     *
     * @throws InterruptedException
     * @throws IOException
     */
    public void releaseHttpResources() throws InterruptedException, IOException {

        // Shutdown the idleConnMonitor.
        Singleton.Client.idleConnMonitor.shutdown();
    }

    private HttpRequestBase setRequestHeaders(HttpRequestBase httpRequestBase) {

        //httpRequestBase.setHeader("Host", "accounts.google.com");
        // httpRequestBase.setHeader("User-Agent", USER_AGENT);
        // httpRequestBase.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        // httpRequestBase.setHeader("Accept-Language", "en-US,en;q=0.5");
        // httpPost.setHeader("Cookie", getCookies());
        //httpPost.setHeader("Connection", "keep-alive");
        //if (lastConnection){
        //httpPost.setHeader("Connection", "close"); 
        //}
        // httpPost.setHeader("Referer",  "https://accounts.google.com/ServiceLoginAuth");
        //httpPost.setHeader("Host","staff.ezshift.co.il");
        //  httpPost.setHeader("Connection","keep-alive");
        // httpPost.setHeader("Content-Length","" + postData.getBytes().length);
        //  httpPost.setHeader("Cache-Control","max-age=0");
        // httpPost.setHeader("Origin","http://staff.ezshift.co.il");
        //httpPost.setHeader("Keep-Alive","115");
        //httpPost.setHeader("Proxy-Connection","keep-alive");
        //httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1");
        //httpPost.setHeader("Content-Type","application/x-www-form-urlencoded"); //maybe the cause or 500 when i do get first
        //httpPost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8, value");
        //httpPost.setHeader("Referer","http://staff.ezshift.co.il/appfilesV3/loginHE-il.aspx");
        //httpPost.setHeader("Accept-Encoding","gzip,deflate,sdch");
        //httpPost.setHeader("Accept-Language","he-IL,he;q=0.8,en-US;q=0.6,en;q=0.4");
        //httpPost.setHeader("Accept-Charset","windows-1255,utf-8;q=0.7,*;q=0.3");
        //httpPost.setHeader("Cookie","
        httpRequestBase.setHeader("Connection", HTTP.CONN_CLOSE);
        httpRequestBase.setHeader("Content-Type", contentType.getValue());
        //httpRequestBase.setHeader("Content-Type", "application/xml");
        //httpRequestBase.setHeader("Content-Type", "application/json");

        return httpRequestBase;
    }

    private String getUrl(RemoteRequest remoteUnit) {

        String urlToCall;

        switch (contentType) {
            case JSON:
                urlToCall = remoteUnit.getJsonUrl();
                break;

            case HTTP:
                urlToCall = remoteUnit.getXmlUrl();
                break;

            default:
                urlToCall = remoteUnit.getJsonUrl();
                break;

        }
        return urlToCall;
    }

    /**
     * Send a request to a remote server
     *
     * @param requestPayloadString
     * @param urlToCall
     * @param contentType
     * @param username
     * @param password
     * @return CloseableHttpResponse contains a response that needs to be read
     * and decoded into a string
     */
    private CloseableHttpResponse sendHttpRequest(String requestPayloadString, String urlToCall, Map<String, String> paramPairs, HTTPMethod method) {

        List<NameValuePair> httpParams = GeneralUtils.convertToNameValuePair(paramPairs);

        HttpRequestBase request;
        CloseableHttpResponse response = null;

        //MyCustomException customException;
        String errorMessage;

        try {

            switch(method){
                case GET:
                    request = setHttpPostRequest(requestPayloadString, urlToCall, httpParams);
                    break;
                    
                case POST:
                    request = setHttpGetRequest(requestPayloadString, urlToCall, httpParams);
                    break;
                    
                default:
                    request = setHttpPostRequest(requestPayloadString, urlToCall, httpParams);
                    break;
            }
            
            //request = Security.setBasicEncoding(request, username, password);
            response = HttpClientPool.getHttpClient().execute(request);

        } catch (ConnectTimeoutException ex) {

            errorMessage = ex.getMessage();
            //customException = new MyCustomException("ConnectTimeoutException error", ErrorCode.CONNECTION_TIMEOUT_ERR, "ConnectTimeoutException while trying to send HTTP request: " + errorMessage, ErrorCategory.SERVER_ERR_TYPE);
            logger.error("ConnectTimeoutException error: " + errorMessage);
            //throw customException;

        } catch (SocketTimeoutException ex) {

            errorMessage = ex.getMessage();
            //customException = new MyCustomException("SocketTimeoutException error", ErrorCode.READ_TIMEOUT_ERR, "SocketTimeoutException while trying to send HTTP request: " + errorMessage, ErrorCategory.SERVER_ERR_TYPE);
            logger.error("SocketTimeoutException error: " + errorMessage);
            //throw customException;

        } catch (UnsupportedEncodingException ex) {

            errorMessage = ex.getMessage();
            //customException = new MyCustomException("UnsupportedEncoding error", ErrorCode.COMMUNICATION_ERR, "UnsupportedEncodingException while trying to send HTTP request: " + errorMessage, ErrorCategory.SERVER_ERR_TYPE);
            logger.error("UnsupportedEncoding error: " + errorMessage);
            //throw customException;

        } catch (IOException ex) {

            errorMessage = ex.getMessage();
            //customException = new MyCustomException("IO error", ErrorCode.COMMUNICATION_ERR, "I/O exception while trying to send HTTP request: " + errorMessage, ErrorCategory.SERVER_ERR_TYPE);
            logger.error("IO exception: " + errorMessage);
            //throw customException;

        } catch (Exception ex) {

            errorMessage = ex.getMessage();
            //customException = new MyCustomException("Exception error", ErrorCode.COMMUNICATION_ERR, "A general communicatin error occured while trying to send HTTP request: " + errorMessage, ErrorCategory.SERVER_ERR_TYPE);
            logger.error("Exception: " + errorMessage);
            //throw customException;
        }

        return response;
    }

    /**
     * @param response
     * @return responseContent string
     */
    private String readResponse(CloseableHttpResponse response) {

        int statusCode = 0;
        String message = null;
        String responseContent = "";
        InputStream content = null;
        BufferedReader buffer = null;

        try {

            if (response == null) {
                return null;
            }

            statusCode = response.getStatusLine().getStatusCode();
            message = response.getStatusLine().getReasonPhrase();
            HttpEntity responseHttpEntity = response.getEntity();

            content = responseHttpEntity.getContent();

            buffer = new BufferedReader(new InputStreamReader(content));
            String s;

            while ((s = buffer.readLine()) != null) {
                responseContent += s;
            }
            logger.debug("Response content: " + responseContent);

            //release all resources held by the responseHttpEntity
            EntityUtils.consume(responseHttpEntity);

        } catch (UnsupportedEncodingException ex) {
            logger.error("UnsupportedEncoding error: " + ex.getMessage());
            //throw new MyCustomException("HTTP UnsupportedEncoding error", ErrorCode.COMMUNICATION_ERR, "UnsupportedEncodingException while trying to send HTTP request to DBMS: " + ex.getMessage(), ErrorCategory.SERVER_ERR_TYPE);
        } catch (IOException ex) {
            logger.error("IO exception: " + ex.getMessage());
            //throw new MyCustomException("IO error", ErrorCode.COMMUNICATION_ERR, "I/O exception while trying to send HTTP request to DBMS: " + ex.getMessage(), ErrorCategory.SERVER_ERR_TYPE);
        } finally {

            closeInputStream(content);
            closeHttpResponse(response);
            closeBufferedReader(buffer);
        }

        logger.debug("\n:::::::::::::::: Response from  server ::::::::::::::::");
        logger.debug("\nResponse status             : " + statusCode + "\n");
        logger.debug("\nResponse message            : " + message + "\n");
        logger.debug("\nResponse content            : " + responseContent + "\n");
        logger.debug("<<< END of server response >>>");

        if (!(statusCode == HttpURLConnection.HTTP_ACCEPTED || statusCode == HttpURLConnection.HTTP_OK)) {
            //read XML response from DBMS
            logger.debug("server returned status NOK");

            //throw new MyCustomException("DBMS Unhandled error", ErrorCode.THIRDPARTY_SYSTEM_ERR, "DBMS server statuscode: " + statusCode + ", status msg: " + message + "... " + responseContent, ErrorCategory.SERVER_ERR_TYPE);
        } else {
            logger.debug("server returned status OK");
        }

        //return the statucode also so that we can feed it to the error handler
        return responseContent.trim();
    }

    /**
     * Send a request to a remote server and get a string response
     *
     * @param requestPayloadString
     * @param urlToCall
     * @param paramPairs
     * @return string response
     */
    public String sendRemoteRequest(String requestPayloadString, String urlToCall, Map<String, String> paramPairs, HTTPMethod method) {

        CloseableHttpResponse response = sendHttpRequest(requestPayloadString, urlToCall, paramPairs, method);
        String responsePayload = readResponse(response);

        return responsePayload;

    }

    /**
     * Send Json string with a String parameter
     *
     * @param url
     * @param jsonString
     * @return
     */
    private Object sendJsonHttpRequest(final String url, final String jsonString) {

        //String myString = new JSONStringer().object().key("JSON").value("Hello, World!").endObject().toString();
        //produces {"JSON":"Hello, World!"}
        //about to convert the  BEEP API request (in whatever form it is now) -> to a JSON string
        try {
            HttpPost request = new HttpPost(url);
            JSONStringer json = new JSONStringer();
            StringBuilder sb = new StringBuilder();

            StringEntity entity = new StringEntity(jsonString);
            entity.setContentType(contentType.getValue());
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, contentType.getValue()));
            request.setHeader("Accept", contentType.getValue());
            request.setEntity(entity);

            //CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            //HttpResponse response = httpClient.execute(request);
            HttpResponse response = HttpClientPool.getHttpClient().execute(request);

            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();

        } catch (IOException ex) {
            logger.error("IOException: " + ex.getMessage());
            return null;
        } catch (IllegalStateException ex) {
            logger.error("IllegalStateException: " + ex.getMessage());
            return null;
        }
    }

    /**
     * send request to server for processing
     *
     * @param requestXML
     * @param serverUrl
     * @param base64Creds
     * @return response from server
     * @throws java.io.IOException
     */
    //String concatCreds = ecwSPusername + ":" + ecwSPpassword;
    //String base64Creds = new Base64().encodeToString(concatCreds.getBytes());
    private String requestToECW(String requestXML, String serverUrl, String base64Creds) throws IOException {

        String xmlResponse = "";

        //certify this request (as trust all - though not good) before sending it out to an HTTPS - ssl-enabled server
        try {
            Cert.trustHttpsCertificates();
            logger.info("Cert.trustHttpsCertificates() called successfuly");
        } catch (Exception ex) {
            logger.error("failed to SSL certify destination HTTPs server: " + ex.getMessage());
            return null;
        }

        BufferedReader in = null;
        HttpsURLConnection conn = null;

        try {

            logger.info("about to send request to: " + serverUrl);

            URL url = new URL(serverUrl);
            URLConnection uc = url.openConnection();

            conn = (HttpsURLConnection) uc;

            //authentication
            conn.setRequestProperty("Authorization", "Basic " + base64Creds);
            conn.setRequestProperty("Content-type", "text/xml");

            logger.info("creds in header: Basic " + base64Creds);

            conn.setRequestMethod(NamedConstants.HTTP_REQUESTMETHOD_POST); //POST or GET

            conn.setDoInput(true);  //incoming traffic
            conn.setDoOutput(true); //outgoing traffic            

            conn.setConnectTimeout(50000);
            conn.setReadTimeout(20000);

            logger.info("..now starting to write request to ECW");

            PrintWriter pw = new PrintWriter(conn.getOutputStream());
            pw.write(requestXML);

            logger.info("request written!");

            pw.close();

            logger.info("after closing printwriter conn");

            logger.info("Connection Response   :" + conn.getResponseCode());
            logger.info("Response message      :" + conn.getResponseMessage());
            //logging.info("Resp. contenttype name: " + conn.getContent().getClass().getName());

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK || conn.getResponseCode() == NamedConstants.HTTPCODE_SUCCESS_NO_CONTENT) {

                logger.info("Connection OK");

                in = new BufferedReader(new InputStreamReader(uc.getInputStream()));

            } else {
                logger.info("Connection NOT OK " + conn.getResponseCode());

                in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));

                //ErrorResponse errorResponse = (ErrorResponse) conn.getContent();
                //xmlResponse = BindXmlAndPojo.objectToXML(errorResponse, ErrorResponse.class);
            }

            logger.info(">>> about to read response from server: ");

            String inputLine;

            while ((inputLine = in.readLine()) != null) {

                xmlResponse += inputLine;
                //xmlResponse += inputLine + "\n";
            }

            logger.info("<< XML response from ECW server: " + serverUrl + " >>");
            logger.info("\n\n XML |=> " + xmlResponse.trim() + "<=|\n\n");
            logger.info("<<< END of ECW server response >>>");

        } finally {

            logger.info("confirming that the finally block is executing");
            if (in != null) {
                try {
                    logger.info("confirming that the input stream is closed");
                    in.close();
                } catch (IOException ex) {
                    logger.error("IO Exception trying to close stream: " + ex.getMessage());
                }
            }

            if (conn != null) {
                conn.disconnect();
            }
        }

        return xmlResponse.trim();
    }

    private void closeHttpResponse(CloseableHttpResponse response) {

        // Always close the reponse.
        if (response != null) {
            try {
                response.close();
            } catch (IOException ex) {
                logger.error("IO exception trying to close CloseableHttpResponse: " + ex.getMessage());
            }
        }
    }

    private void closeInputStream(InputStream content) {
        // Always close the content.
        if (content != null) {

            try {
                content.close();
            } catch (IOException ex) {
                logger.error("IO exception trying to close inputstream: " + ex.getMessage());
            }
        }
    }

    private void closeBufferedReader(BufferedReader buffer) {
        if (buffer != null) {
            try {
                buffer.close();
            } catch (IOException ex) {
                logger.error("IO exception trying to close the bufferedreader: " + ex.getMessage());
            }
        }
    }

    private static void closeHttpclient() throws IOException {

        if (getHttpClient() != null) {
            getHttpClient().close();
        }
    }

    private HttpRequestBase setHttpPostRequest(String requestPayloadString, String urlToCall, List<NameValuePair> httpParams) throws UnsupportedEncodingException, IOException, URISyntaxException {

        logger.debug("Sending request to URL: " + urlToCall + " request: " + requestPayloadString);

        //List<NameValuePair> httpParams = new ArrayList<>();
        //nvps.add(new BasicNameValuePair("entity-name", "ADVERT"));
        URIBuilder uriBuilder = new URIBuilder(urlToCall);
        if (httpParams != null) {
            uriBuilder.addParameters(httpParams);
        }

        //final HttpRequestBase request;
        HttpPost postRequest = new HttpPost(uriBuilder.build());

        postRequest.addHeader(NamedConstants.MAMBOPAY_HEADER_SUBSCKEY, NamedConstants.SUBSCRIPTION_KEY);
        postRequest = (HttpPost) setRequestHeaders(postRequest);
        //postRequest.setEntity(new UrlEncodedFormEntity(httpParams, Consts.UTF_8));

        //URIBuilder builder = new URIBuilder();
        //builder.setScheme("http").setHost(host).setPort(port).setPath(restPath + taskUri + "/" + taskId)
        //builder.setParameter("entity-name", "ADVERT");
        //HttpPost post = new HttpPost(builder.build());
        //StringEntity entity = new StringEntity(requestPayloadString);
        HttpEntity entity = new ByteArrayEntity(requestPayloadString.getBytes("UTF-8"));
        postRequest.setEntity(entity);

        return postRequest;
    }
    
    private HttpRequestBase setHttpGetRequest(String requestPayloadString, String urlToCall, List<NameValuePair> httpParams) throws UnsupportedEncodingException, IOException, URISyntaxException {

        logger.debug("Sending request to URL: " + urlToCall + " request: " + requestPayloadString);

        //List<NameValuePair> httpParams = new ArrayList<>();
        //nvps.add(new BasicNameValuePair("entity-name", "ADVERT"));
        URIBuilder uriBuilder = new URIBuilder(urlToCall);
        if (httpParams != null) {
            uriBuilder.addParameters(httpParams);
        }

        //final HttpRequestBase request;
        HttpGet getRequest = new HttpGet(uriBuilder.build());

        //getRequest.addHeader(NamedConstants.MAMBOPAY_HEADER_SUBSCKEY, NamedConstants.SUBSCRIPTION_KEY);
        getRequest = (HttpGet) setRequestHeaders(getRequest);
        //postRequest.setEntity(new UrlEncodedFormEntity(httpParams, Consts.UTF_8));

        //URIBuilder builder = new URIBuilder();
        //builder.setScheme("http").setHost(host).setPort(port).setPath(restPath + taskUri + "/" + taskId)
        //builder.setParameter("entity-name", "ADVERT");
        //HttpPost post = new HttpPost(builder.build());
        //StringEntity entity = new StringEntity(requestPayloadString);
        HttpEntity entity = new ByteArrayEntity(requestPayloadString.getBytes("UTF-8"));
        //getRequest.setEntity(entity);

        return getRequest;
    }

    public Boolean validateRequest(String requestingClient, String authorizationHeader, List<String> allowedIps) {

        //String requestingClient = HttpServletRequest request.getServerName().trim();
        //final String authorization = HttpServletRequest request.getHeader("Authorization");
        boolean serveRequest = Boolean.FALSE;

        //check if request is coming from authorised IP
        if (allowedIps.contains(requestingClient)) {

            //check if credentials given are valid else throw MyCustomException
            String[] credentialsArray = Security.decodeBasicAuthCredentials(authorizationHeader, 2);

            String requestUname = credentialsArray[0].trim();
            String requestPass = credentialsArray[1].trim();

            serveRequest = Boolean.TRUE;
        } else {
            logger.warn("Request from UN-AUTHORISED_CLIENT : " + requestingClient);
            // throw new MyCustomException("Client IP NOT allowed", "UN-AUTHORISED_CLIENT", "Request from a client: " + serverName + " - NOT authorised to access this service", "CLIENT_ERROR");

        }

        return serveRequest;
    }

}
