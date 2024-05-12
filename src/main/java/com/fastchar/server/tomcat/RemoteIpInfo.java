package com.fastchar.server.tomcat;

public class RemoteIpInfo {

    /**
     * Regular expression that matches proxies that are to be trusted.
     */
    private String internalProxies = "10\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|" // 10/8
            + "192\\.168\\.\\d{1,3}\\.\\d{1,3}|" // 192.168/16
            + "169\\.254\\.\\d{1,3}\\.\\d{1,3}|" // 169.254/16
            + "127\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|" // 127/8
            + "100\\.6[4-9]{1}\\.\\d{1,3}\\.\\d{1,3}|" // 100.64.0.0/10
            + "100\\.[7-9]{1}\\d{1}\\.\\d{1,3}\\.\\d{1,3}|" // 100.64.0.0/10
            + "100\\.1[0-1]{1}\\d{1}\\.\\d{1,3}\\.\\d{1,3}|" // 100.64.0.0/10
            + "100\\.12[0-7]{1}\\.\\d{1,3}\\.\\d{1,3}|" // 100.64.0.0/10
            + "172\\.1[6-9]{1}\\.\\d{1,3}\\.\\d{1,3}|" // 172.16/12
            + "172\\.2[0-9]{1}\\.\\d{1,3}\\.\\d{1,3}|" // 172.16/12
            + "172\\.3[0-1]{1}\\.\\d{1,3}\\.\\d{1,3}|" // 172.16/12
            + "0:0:0:0:0:0:0:1|::1";

    /**
     * Header that holds the incoming protocol, usually named "X-Forwarded-Proto".
     */
    private String protocolHeader;

    /**
     * Value of the protocol header indicating whether the incoming request uses
     * SSL.
     */
    private String protocolHeaderHttpsValue = "https";

    /**
     * Name of the HTTP header from which the remote host is extracted.
     */
    private String hostHeader = "X-Forwarded-Host";

    /**
     * Name of the HTTP header used to override the original port value.
     */
    private String portHeader = "X-Forwarded-Port";

    /**
     * Name of the HTTP header from which the remote IP is extracted. For
     * instance, 'X-FORWARDED-FOR'.
     */
    private String remoteIpHeader;

    /**
     * Regular expression defining proxies that are trusted when they appear in
     * the "remote-ip-header" header.
     */
    private String trustedProxies;

    public String getInternalProxies() {
        return this.internalProxies;
    }

    public void setInternalProxies(String internalProxies) {
        this.internalProxies = internalProxies;
    }

    public String getProtocolHeader() {
        return this.protocolHeader;
    }

    public void setProtocolHeader(String protocolHeader) {
        this.protocolHeader = protocolHeader;
    }

    public String getProtocolHeaderHttpsValue() {
        return this.protocolHeaderHttpsValue;
    }

    public String getHostHeader() {
        return this.hostHeader;
    }

    public void setHostHeader(String hostHeader) {
        this.hostHeader = hostHeader;
    }

    public void setProtocolHeaderHttpsValue(String protocolHeaderHttpsValue) {
        this.protocolHeaderHttpsValue = protocolHeaderHttpsValue;
    }

    public String getPortHeader() {
        return this.portHeader;
    }

    public void setPortHeader(String portHeader) {
        this.portHeader = portHeader;
    }

    public String getRemoteIpHeader() {
        return this.remoteIpHeader;
    }

    public void setRemoteIpHeader(String remoteIpHeader) {
        this.remoteIpHeader = remoteIpHeader;
    }

    public String getTrustedProxies() {
        return this.trustedProxies;
    }

    public void setTrustedProxies(String trustedProxies) {
        this.trustedProxies = trustedProxies;
    }

}