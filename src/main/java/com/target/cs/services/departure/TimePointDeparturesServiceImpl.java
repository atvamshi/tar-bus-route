package com.target.cs.services.departure;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.target.cs.vo.TimePointDeparturesVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final Logger LOGGER = LoggerFactory.getLogger(TimePointDeparturesServiceImpl.class);


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
                put("Accept", MediaType.APPLICATION_PROBLEM_XML_VALUE);
                put("Content-Type", MediaType.APPLICATION_PROBLEM_XML_VALUE);
            }
        };

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(timePointDeparturesUri
                .replace("{ROUTE}",routeId).replace("{DIRECTION}",directionId)
                .replace("{STOP}", stopId), HttpMethod.GET, httpEntity, String.class);


        List<TimePointDeparturesVO> responseList = null;
        try {

            XmlMapper xmlMapper = new XmlMapper();
            responseList = Arrays.asList(xmlMapper.readValue(response.getBody(), TimePointDeparturesVO[].class));

        } catch (IOException e) {
            LOGGER.error("IOException in getTimePointDeparturesByRouteIdDirectionIdAndStopId", e);
        }

        return responseList;
    }
}
