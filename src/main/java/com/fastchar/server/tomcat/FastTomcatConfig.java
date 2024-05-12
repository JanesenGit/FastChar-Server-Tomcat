package com.fastchar.server.tomcat;

import com.fastchar.core.FastChar;
import com.fastchar.server.SameSite;
import com.fastchar.utils.FastClassUtils;
import com.fastchar.utils.FastStringUtils;
import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.valves.RemoteIpValve;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.coyote.http2.Http2Protocol;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FastTomcatConfig {

    private int port = 8080;

    private String contextPath = "/";

    private String host = "0.0.0.0";

    private String displayName;

    private String docBase;

    private String baseDirectory;

    private String protocol = "org.apache.coyote.http11.Http11NioProtocol";

    private int maxPostSize = -1;

    private int maxSavePostSize = -1;

    private int maxSwallowSize;

    private Charset uriEncoding = StandardCharsets.UTF_8;

    private final List<String> hostAlias = new ArrayList<>();

    private SameSite cookieSameSite;

    private int maxConnections = 8192;

    private int maxThreads = 1000;

    private int acceptCount = 1000;

    private int processorCache = 200;

    private Duration keepAliveTimeout = Duration.ofSeconds(30);

    private Duration backgroundProcessorDelay = Duration.ofSeconds(10);

    private int maxKeepAliveRequests = 100;

    private Duration connectionTimeout = Duration.ofSeconds(30);

    private int maxHttpHeaderSize = 65536;

    private int minSpareThreads = 20;


    private boolean disableUploadTimeout = true;

    private boolean http2 = false;

    private final List<LifecycleListener> serverLifecycleListeners = new ArrayList<>();
    private final List<Valve> engineValves = new ArrayList<>();
    private final List<Connector> additionalTomcatConnectors = new ArrayList<>();

    private final List<Valve> contextValves = new ArrayList<>();

    private final List<LifecycleListener> contextLifecycleListeners = new ArrayList<>();

    private final RemoteIpInfo remoteIp = new RemoteIpInfo();

    private final List<URL> resources = new ArrayList<>();


    public FastTomcatConfig() {
        AprLifecycleListener aprLifecycleListener = new AprLifecycleListener();
        if (AprLifecycleListener.isAprAvailable()) {
            serverLifecycleListeners.add(aprLifecycleListener);
        }
        serverLifecycleListeners.add(new Tomcat.FixContextListener());
        serverLifecycleListeners.add(new org.apache.catalina.core.JreMemoryLeakPreventionListener());
        serverLifecycleListeners.add(new org.apache.catalina.mbeans.GlobalResourcesLifecycleListener());
        serverLifecycleListeners.add(new org.apache.catalina.core.ThreadLocalLeakPreventionListener());

        contextLifecycleListeners.add(new Tomcat.FixContextListener());
    }

    public int getPort() {
        return port;
    }

    public FastTomcatConfig setPort(int port) {
        this.port = port;
        return this;
    }

    public String getContextPath() {
        return contextPath;
    }

    public FastTomcatConfig setContextPath(String contextPath) {
        this.contextPath = contextPath;
        return this;
    }

    public String getHost() {
        return host;
    }

    public FastTomcatConfig setHost(String host) {
        this.host = host;
        return this;
    }

    public String getDocBase() {
        return docBase;
    }

    public FastTomcatConfig setDocBase(String docBase) {
        this.docBase = docBase;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public FastTomcatConfig setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public int getMaxPostSize() {
        return maxPostSize;
    }

    public FastTomcatConfig setMaxPostSize(int maxPostSize) {
        this.maxPostSize = maxPostSize;
        return this;
    }

    public int getMaxSavePostSize() {
        return maxSavePostSize;
    }

    public FastTomcatConfig setMaxSavePostSize(int maxSavePostSize) {
        this.maxSavePostSize = maxSavePostSize;
        return this;
    }

    public int getMaxSwallowSize() {
        return maxSwallowSize;
    }

    public FastTomcatConfig setMaxSwallowSize(int maxSwallowSize) {
        this.maxSwallowSize = maxSwallowSize;
        return this;
    }

    public Charset getUriEncoding() {
        return uriEncoding;
    }

    public FastTomcatConfig setUriEncoding(Charset uriEncoding) {
        this.uriEncoding = uriEncoding;
        return this;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public FastTomcatConfig setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
        return this;
    }

    public int getAcceptCount() {
        return acceptCount;
    }

    public FastTomcatConfig setAcceptCount(int acceptCount) {
        this.acceptCount = acceptCount;
        return this;
    }

    public int getProcessorCache() {
        return processorCache;
    }

    public FastTomcatConfig setProcessorCache(int processorCache) {
        this.processorCache = processorCache;
        return this;
    }

    public Duration getKeepAliveTimeout() {
        return keepAliveTimeout;
    }

    public FastTomcatConfig setKeepAliveTimeout(Duration keepAliveTimeout) {
        this.keepAliveTimeout = keepAliveTimeout;
        return this;
    }

    public int getMaxKeepAliveRequests() {
        return maxKeepAliveRequests;
    }

    public FastTomcatConfig setMaxKeepAliveRequests(int maxKeepAliveRequests) {
        this.maxKeepAliveRequests = maxKeepAliveRequests;
        return this;
    }

    public Duration getConnectionTimeout() {
        return connectionTimeout;
    }

    public FastTomcatConfig setConnectionTimeout(Duration connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public int getMaxHttpHeaderSize() {
        return maxHttpHeaderSize;
    }

    public FastTomcatConfig setMaxHttpHeaderSize(int maxHttpHeaderSize) {
        this.maxHttpHeaderSize = maxHttpHeaderSize;
        return this;
    }

    public int getMinSpareThreads() {
        return minSpareThreads;
    }

    public FastTomcatConfig setMinSpareThreads(int minSpareThreads) {
        this.minSpareThreads = minSpareThreads;
        return this;
    }

    public boolean isDisableUploadTimeout() {
        return disableUploadTimeout;
    }

    public FastTomcatConfig setDisableUploadTimeout(boolean disableUploadTimeout) {
        this.disableUploadTimeout = disableUploadTimeout;
        return this;
    }

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public FastTomcatConfig setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
        return this;
    }

    public boolean isHttp2() {
        return http2;
    }

    public FastTomcatConfig setHttp2(boolean http2) {
        this.http2 = http2;
        return this;
    }

    public RemoteIpInfo getRemoteIp() {
        return remoteIp;
    }

    public Duration getBackgroundProcessorDelay() {
        return backgroundProcessorDelay;
    }

    public FastTomcatConfig setBackgroundProcessorDelay(Duration backgroundProcessorDelay) {
        this.backgroundProcessorDelay = backgroundProcessorDelay;
        return this;
    }

    public FastTomcatConfig addAdditionalTomcatConnectors(Connector... connectors) {
        this.additionalTomcatConnectors.addAll(Arrays.asList(connectors));
        return this;
    }


    public FastTomcatConfig addEngineValves(Valve... Valves) {
        this.engineValves.addAll(Arrays.asList(Valves));
        return this;
    }

    public FastTomcatConfig addServerLifecycleListeners(LifecycleListener... lifecycleListeners) {
        this.serverLifecycleListeners.addAll(Arrays.asList(lifecycleListeners));
        return this;
    }

    public FastTomcatConfig addHostAlias(String... alias) {
        this.hostAlias.addAll(Arrays.asList(alias));
        return this;
    }

    public FastTomcatConfig addContextValves(Valve... Valves) {
        this.contextValves.addAll(Arrays.asList(Valves));
        return this;
    }

    public FastTomcatConfig addContextLifecycleListeners(LifecycleListener... lifecycleListeners) {
        this.contextLifecycleListeners.addAll(Arrays.asList(lifecycleListeners));
        return this;
    }


    public FastTomcatConfig addResources(URL path) {
        this.resources.add(path);
        return this;
    }


    public FastTomcatConfig addResources(File path) {
        try {
            this.resources.add(path.toURI().toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }


    public List<URL> getResources() {
        return resources;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public FastTomcatConfig setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public FastTomcatConfig setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }


    public SameSite getCookieSameSite() {
        return cookieSameSite;
    }

    public FastTomcatConfig setCookieSameSite(SameSite cookieSameSite) {
        this.cookieSameSite = cookieSameSite;
        return this;
    }

    void configureServer(Server server) {
        for (LifecycleListener listener : this.serverLifecycleListeners) {
            server.addLifecycleListener(listener);
        }
    }


    void configConnector(Connector connector) {
        if (getUriEncoding() != null) {
            connector.setURIEncoding(getUriEncoding().name());
        }
        connector.setThrowOnFailure(true);
        connector.setPort(this.port);
        connector.setProperty("bindOnInit", "false");
        if (isHttp2()) {
            connector.addUpgradeProtocol(new Http2Protocol());
        }
        connector.setMaxPostSize(this.maxPostSize);

        ProtocolHandler handler = connector.getProtocolHandler();

        if (handler instanceof AbstractProtocol) {
            AbstractProtocol<?> abstractProtocol = (AbstractProtocol<?>) handler;
            abstractProtocol.setMaxConnections(this.maxConnections);
            abstractProtocol.setMaxThreads(this.maxThreads);
            abstractProtocol.setMinSpareThreads(this.minSpareThreads);
            abstractProtocol.setAcceptCount(this.acceptCount);
            abstractProtocol.setKeepAliveTimeout((int) this.keepAliveTimeout.getSeconds());
            abstractProtocol.setConnectionTimeout((int) this.connectionTimeout.getSeconds());
            abstractProtocol.setProcessorCache(this.processorCache);
        }

        if (handler instanceof AbstractHttp11Protocol) {
            AbstractHttp11Protocol<?> abstractProtocol = (AbstractHttp11Protocol<?>) handler;
            abstractProtocol.setMaxHttpRequestHeaderSize(this.maxHttpHeaderSize);
            abstractProtocol.setMaxKeepAliveRequests(this.maxKeepAliveRequests);
            abstractProtocol.setDisableUploadTimeout(this.disableUploadTimeout);
            abstractProtocol.setMaxSwallowSize(this.maxSwallowSize);
        }

    }


    void configHost(Host host) {
        host.setAutoDeploy(false);
        for (String hostAlias : this.hostAlias) {
            host.addAlias(hostAlias);
        }

        LifecycleListener listener;
        try {
            Class<?> clazz = Class.forName(host.getConfigClass());
            listener = (LifecycleListener) clazz.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            // Wrap in IAE since we can't easily change the method signature to
            // to throw the specific checked exceptions
            throw new IllegalArgumentException(e);
        }
        contextLifecycleListeners.add(listener);
    }


    void configService(Service service) {
        service.setName("FastChar-Server-Tomcat");
        for (Connector additionalConnector : this.additionalTomcatConnectors) {
            service.addConnector(additionalConnector);
        }
    }

    void configureEngine(Engine engine) {
        configRemoteIp();
        engine.setBackgroundProcessorDelay((int) this.backgroundProcessorDelay.getSeconds());
        for (Valve valve : this.engineValves) {
            engine.getPipeline().addValve(valve);
        }
    }


    void configRemoteIp() {
        RemoteIpInfo remoteIp = this.getRemoteIp();
        String protocolHeader = remoteIp.getProtocolHeader();
        String remoteIpHeader = remoteIp.getRemoteIpHeader();
        if (FastStringUtils.isNotEmpty(protocolHeader) || FastStringUtils.isNotEmpty(remoteIpHeader)) {
            RemoteIpValve valve = new RemoteIpValve();
            valve.setProtocolHeader(FastStringUtils.isNotEmpty(protocolHeader) ? protocolHeader : "X-Forwarded-Proto");
            if (FastStringUtils.isNotEmpty(remoteIpHeader)) {
                valve.setRemoteIpHeader(remoteIpHeader);
            }
            valve.setTrustedProxies(remoteIp.getTrustedProxies());
            // The internal proxies default to a list of "safe" internal IP addresses
            valve.setInternalProxies(remoteIp.getInternalProxies());
            try {
                valve.setHostHeader(remoteIp.getHostHeader());
            } catch (NoSuchMethodError ex) {
                // Avoid failure with war deployments to Tomcat 8.5 before 8.5.44 and
                // Tomcat 9 before 9.0.23
            }
            valve.setPortHeader(remoteIp.getPortHeader());
            valve.setProtocolHeaderHttpsValue(remoteIp.getProtocolHeaderHttpsValue());
        }
    }


    void configContext(Context context) {
        context.setName(this.contextPath);
        context.setDisplayName(this.displayName);
        context.setPath(this.contextPath);
        if (FastStringUtils.isEmpty(this.docBase)) {
            this.docBase = FastChar.getPath().getWebRootPath();
        }
        context.setDocBase(this.docBase);

        context.addLifecycleListener(new Tomcat.FixContextListener());
        context.setParentClassLoader(FastClassUtils.getDefaultClassLoader());
        context.setCreateUploadTargets(true);
        context.setCrossContext(true);
        StandardRoot standardRoot = new StandardRoot(context);
        context.setResources(standardRoot);

        context.addLocaleEncodingMappingParameter(Locale.CHINA.toString(), FastChar.getConstant().getCharset());
        context.addLocaleEncodingMappingParameter(Locale.ENGLISH.toString(), FastChar.getConstant().getCharset());
        context.addLocaleEncodingMappingParameter(Locale.FRENCH.toString(), FastChar.getConstant().getCharset());
        context.addLocaleEncodingMappingParameter(Locale.JAPANESE.toString(), FastChar.getConstant().getCharset());

        context.setSessionTimeout(FastChar.getConstant().getSessionMaxInterval());

        context.addWelcomeFile("index.html");
        context.addWelcomeFile("index.htm");
        context.addWelcomeFile("index.jsp");


        Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
        if (getCookieSameSite() != null) {
            cookieProcessor.setSameSiteCookies(getCookieSameSite().attributeValue());
        }

        context.setCookieProcessor(cookieProcessor);

        addDefaultServlet(context);
        addJspServlet(context);

        Tomcat.addDefaultMimeTypeMappings(context);

        for (Valve valve : this.contextValves) {
            context.getPipeline().addValve(valve);
        }

        contextLifecycleListeners.add(new StaticResourceConfigurer(this, context));

        for (LifecycleListener listener : this.contextLifecycleListeners) {
            context.addLifecycleListener(listener);
        }
    }


    void addDefaultServlet(Context context) {

        Wrapper defaultServlet = context.createWrapper();
        defaultServlet.setName("default");
        defaultServlet.setLoadOnStartup(1);
        defaultServlet.setServletClass("org.apache.catalina.servlets.DefaultServlet");
        defaultServlet.addInitParameter("debug", "0");
        defaultServlet.addInitParameter("listings", "false");
        defaultServlet.addInitParameter("fileEncoding", FastChar.getConstant().getCharset());
        defaultServlet.addInitParameter("listings", "false");
        defaultServlet.addInitParameter("precompressed", "true");
        defaultServlet.addInitParameter("gzip", "true");

        // Otherwise the default location of a Spring DispatcherServlet cannot be set
        defaultServlet.setOverridable(true);
        context.addChild(defaultServlet);
        context.addServletMappingDecoded("/", "default");
    }


    void addJspServlet(Context context) {
        Wrapper jspServlet = context.createWrapper();
        jspServlet.setName("jsp");
        jspServlet.setServletClass("org.apache.jasper.servlet.JspServlet");
        jspServlet.addInitParameter("fork", "false");
        jspServlet.setLoadOnStartup(3);
        context.addChild(jspServlet);
        context.addServletMappingDecoded("*.jsp", "jsp");
        context.addServletMappingDecoded("*.jspx", "jsp");
    }

}
