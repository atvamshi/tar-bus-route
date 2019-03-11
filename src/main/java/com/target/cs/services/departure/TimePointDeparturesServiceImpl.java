package com.target.cs.services.departure;

import com.target.cs.vo.TimePointDeparturesVO;
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
 * Package: com.target.cs.services.departure
 * <p>
 * User: vamshi
 * Date: 2019-03-10
 * Time: 18:16
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
@Component
public class TimePointDeparturesServiceImpl implements TimePointDeparturesService {


    @Value("${app.service.time.departure.url}")
    private String timePointDeparturesUri;

    private RestTemplate restTemplate;

    @Autowired
    public TimePointDeparturesServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public List<TimePointDeparturesVO> getTimePointDeparturesByRouteIdDirectionIdAndStopId(String routeId, String directionId, String stopId) {

        Map<String, String> headers = new HashMap() {
            {
                put("Accept", MediaType.APPLICATION_JSON_VALUE);
                put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            }
        };

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);

        List<TimePointDeparturesVO> response = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(timePointDeparturesUri
                .replace("{ROUTE}",routeId).replace("{DIRECTION}",directionId)
                .replace("{STOP}",stopId), TimePointDeparturesVO[].class, httpHeaders)));
        return response;
    }
}
