package com.target.cs.mdc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Project: tar-bus-time-arrival-calc
 * Package: com.target.cs.mdc
 * <p>
 * User: vamshi
 * Date: 2019-03-10
 * Time: 16:47
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class RequestFilterMdc implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestFilterMdc.class);

    private final String responseHeader;
    private final String mdcTokenKey;
    private final String mdcClientIpKey;
    private final String requestHeader;

    public RequestFilterMdc() {
        responseHeader = RequestFilterConfiguration.DEFAULT_RESPONSE_TOKEN_HEADER;
        mdcTokenKey = RequestFilterConfiguration.DEFAULT_MDC_UUID_TOKEN_KEY;
        mdcClientIpKey = RequestFilterConfiguration.DEFAULT_MDC_CLIENT_IP_KEY;
        requestHeader = null;
    }

    public RequestFilterMdc(final String responseHeader, final String mdcTokenKey, final String mdcClientIPKey, final String requestHeader) {
        this.responseHeader = responseHeader;
        this.mdcTokenKey = mdcTokenKey;
        this.mdcClientIpKey = mdcClientIPKey;
        this.requestHeader = requestHeader;
    }

    private String extractToken(final HttpServletRequest request) {
        final String token;
        if (!StringUtils.isEmpty(requestHeader) && !StringUtils.isEmpty(request.getHeader(requestHeader))) {
            token = request.getHeader(requestHeader);
        } else {
            token = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        }
        return token;
    }

    private String extractClientIP(final HttpServletRequest request) {
        final String clientIP;
        if (request.getHeader("X-Forwarded-For") != null) {
            clientIP = request.getHeader("X-Forwarded-For").split(",")[0];
        } else {
            clientIP = request.getRemoteAddr();
        }
        return clientIP;
    }


    /**
     * Overriding the method destroy
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.info("Overriding  public void init(FilterConfig filterConfig)");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = ((HttpServletRequest) servletRequest);
            HttpServletResponse httpServletResponse = ((HttpServletResponse) servletResponse);

            final String token = extractToken(httpServletRequest);
            final String clientIP = extractClientIP(httpServletRequest);
            MDC.put("mdcData", clientIP + "-" + token + "-");
            if (!StringUtils.isEmpty(responseHeader)) {
                httpServletResponse.addHeader(responseHeader, token);
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            MDC.remove(mdcTokenKey);
            MDC.remove(mdcClientIpKey);
        }
    }

    /**
     * Overriding the method destroy
     */
    @Override
    public void destroy() {
        LOGGER.info("Overriding  public void init(FilterConfig filterConfig)");
    }
}