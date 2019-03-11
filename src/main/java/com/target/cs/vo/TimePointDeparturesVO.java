package com.target.cs.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Project: tar-bus-time-arrival-calc
 * Package: com.target.cs.vo
 * <p>
 * User: vamshi
 * Date: 2019-03-10
 * Time: 17:53
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
@Data
@Component
@Scope("prototype")
public class TimePointDeparturesVO {


    @JsonProperty(value = "Actual")
    private Boolean actual;

    @JsonProperty(value = "BlockNumber")
    private Integer blockNumber;

    @JsonProperty(value = "DepartureText")
    private String departureText;

    @JsonProperty(value = "DepartureTime")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss a z")
    private String departureTime;

    @JsonProperty(value = "Description")
    private String description;

    @JsonProperty(value = "Gate")
    private String gate;

    @JsonProperty(value = "Route")
    private String route;

    @JsonProperty(value = "RouteDirection")
    private String routeDirection;

    @JsonProperty(value = "Terminal")
    private String terminal;

    @JsonProperty(value = "VehicleHeading")
    private Integer vehicleHeading;

    @JsonProperty(value = "VehicleLatitude")
    private Integer vehicleLatitude;

    @JsonProperty(value = "VehicleLongitude")
    private Integer vehicleLongitude;

}