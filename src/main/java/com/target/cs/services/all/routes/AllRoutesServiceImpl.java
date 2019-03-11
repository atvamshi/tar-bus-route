package com.target.cs.services.all.routes;

import com.target.cs.vo.RoutesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Project: tar-bus-time-arrival-calc
 * Package: com.target.cs.services.all.routes
 * <p>
 * User: vamshi
 * Date: 2019-03-10
 * Time: 18:15
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AllRoutesServiceImpl implements AllRoutesService {


    @Value("${app.service.routes.url}")
    private String routesUri;

    private RestTemplate restTemplate;

    @Autowired
    public AllRoutesServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<RoutesVO> getAllRoutes() {

        Map<String, String> headers = new HashMap() {
            {
                put("Accept", MediaType.APPLICATION_JSON_VALUE);
                put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            }
        };

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);

        List<RoutesVO> response = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(routesUri, RoutesVO[].class, httpHeaders)));


        return response;
    }
}
