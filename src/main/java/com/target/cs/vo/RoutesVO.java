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
public class RoutesVO {

    @JsonProperty(value = "Description")
    private String description;

    @JsonProperty(value = "ProviderID")
    private String providerID;

    @JsonProperty(value = "Route")
    private String route;

}
