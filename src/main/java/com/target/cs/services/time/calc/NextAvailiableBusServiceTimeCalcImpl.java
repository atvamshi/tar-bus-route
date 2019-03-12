package com.target.cs.services.time.calc;

import com.target.cs.constants.AppConstants;
import com.target.cs.services.all.routes.AllRoutesService;
import com.target.cs.services.departure.TimePointDeparturesService;
import com.target.cs.services.stops.StopsService;
import com.target.cs.vo.RoutesVO;
import com.target.cs.vo.StopsVO;
import com.target.cs.vo.TimePointDeparturesVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Project: tar-bus-time-arrival-calc
 * Package: com.target.cs.services
 * <p>
 * User: vamshi
 * Date: 2019-03-10
 * Time: 18:09
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
@Service
public class NextAvailiableBusServiceTimeCalcImpl implements NextAvailiableBusServiceTimeCalc {

    static final  Logger LOGGER = LoggerFactory.getLogger(NextAvailiableBusServiceTimeCalcImpl.class);

    private AllRoutesService allRoutesService;

    private StopsService stopsService;

    private TimePointDeparturesService timePointDeparturesService;

    @Autowired
    public NextAvailiableBusServiceTimeCalcImpl (AllRoutesService allRoutesService,
                StopsService stopsService, TimePointDeparturesService timePointDeparturesService){
        this.allRoutesService = allRoutesService;
        this.stopsService = stopsService;
        this.timePointDeparturesService = timePointDeparturesService;
    }


    @Override
    public String getNextDepartTime(String routeName, String directionId, String stopName) {

        final String DIRECTION = AppConstants.valueOf(directionId.trim().toUpperCase()).getValue();

        //Getting routes
        List<RoutesVO> allRoutes = allRoutesService.getAllRoutes();

        //Filtering routes
        List<RoutesVO> filteredRoutes = allRoutes.parallelStream().filter(f -> f.getDescription().trim().toLowerCase()
                .contains(routeName.trim().toLowerCase())).collect(Collectors.toList());

        //Check if route more than one
        checkAnOccuranceAndFail("Multiple routeNames found " + routeName, 1, filteredRoutes);

        //Get stops of routes and direction
        List<StopsVO> stopsList = stopsService.getStopsByRouteIdAndDirection(filteredRoutes.get(0).getRoute(),
                DIRECTION);

        //Filter stops
        List<StopsVO> filteredStops = stopsList.parallelStream().filter(f -> f.getText().trim().toLowerCase().contains(stopName.trim().toLowerCase()))
                .collect(Collectors.toList());

        //Check if stops are more than one
        checkAnOccuranceAndFail( "Multiple stops found " + stopName, 1, filteredStops);

        //Get Time point departures
        List<TimePointDeparturesVO> timePointDeparturesList = timePointDeparturesService.getTimePointDeparturesByRouteIdDirectionIdAndStopId(filteredRoutes.get(0).getRoute(),
                DIRECTION,filteredStops.get(0).getValue());

        if (timePointDeparturesList.isEmpty()) {
            throw new NullPointerException("No buses found for the duration route , stop");
        }

        timePointDeparturesList.sort(Comparator.comparing(TimePointDeparturesVO::getDepartureTime));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


        LocalDateTime departDateTime = LocalDateTime.parse(timePointDeparturesList.get(0).getDepartureTime(), formatter);

        LocalDateTime currentTime = LocalDateTime.now();

        return String.valueOf(currentTime.until(departDateTime, ChronoUnit.MINUTES));

    }


    private void checkAnOccuranceAndFail(String message, int greaterOccurance, List list){
        if(list.size() > greaterOccurance){
            throw new NullPointerException(message);
        }
    }

}
