package com.target.cs.services.stops;

import com.target.cs.vo.StopsVO;
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
 * Package: com.target.cs.services.stops
 * <p>
 * User: vamshi
 * Date: 2019-03-10
 * Time: 18:16
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
@Component
public class StopsServiceImpl implements  StopsService{


    @Value("${app.service.stops.url}")
    private String stopsUri;

    private RestTemplate restTemplate;

    @Autowired
    public StopsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<StopsVO> getStopsByRouteIdAndDirection(String routeId, String direction) {

        Map<String, String> headers = new HashMap() {
            {
                put("Accept", MediaType.APPLICATION_JSON_VALUE);
                put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            }
        };

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);

        List<StopsVO> response = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(stopsUri
                .replace("{ROUTE}",routeId).replace("{DIRECTION}",direction), StopsVO[].class, httpHeaders)));

        return response;
    }


}
