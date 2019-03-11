package com.target.cs.services.departure;

import com.target.cs.vo.TimePointDeparturesVO;

import java.util.List;

/**
 * Project: tar-bus-time-arrival-calc
 * Package: com.target.cs.services
 * <p>
 * User: vamshi
 * Date: 2019-03-10
 * Time: 17:58
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public interface TimePointDeparturesService {

    List<TimePointDeparturesVO> getTimePointDeparturesByRouteIdDirectionIdAndStopId(String routeId, String directionId, String stopId);

}
