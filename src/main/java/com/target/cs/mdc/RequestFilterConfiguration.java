package com.target.cs.mdc;

import lombok.Data;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

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
@Data
@Configuration

public class RequestFilterConfiguration {

    public static final String DEFAULT_RESPONSE_TOKEN_HEADER = "Response_Token";
    public static final String DEFAULT_MDC_UUID_TOKEN_KEY = "RequestFilter.UUID";
    public static final String DEFAULT_MDC_CLIENT_IP_KEY = "RequestFilter.ClientIP";

    private String responseHeader = DEFAULT_RESPONSE_TOKEN_HEADER;
    private String mdcTokenKey = DEFAULT_MDC_UUID_TOKEN_KEY;
    private String mdcClientIpKey = DEFAULT_MDC_CLIENT_IP_KEY;
    private String requestHeader = null;

    @Bean
    @Scope("prototype")
    public FilterRegistrationBean servletRegistrationBean() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        final RequestFilterMdc log4jMDCFilterFilter = new RequestFilterMdc(responseHeader, mdcTokenKey, mdcClientIpKey, requestHeader);
        registrationBean.setFilter(log4jMDCFilterFilter);
        registrationBean.setOrder(2);
        return registrationBean;
    }
}