package com.target.cs.services.stops;

import com.target.cs.vo.StopsVO;

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
public interface StopsService {

    List<StopsVO> getStopsByRouteIdAndDirection(String routeId, String direction);

}
