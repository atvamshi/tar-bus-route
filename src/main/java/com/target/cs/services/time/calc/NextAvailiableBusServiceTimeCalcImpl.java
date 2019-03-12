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
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static final String routesMultipleMessage = "Multiple routeNames found ";

    private AllRoutesService allRoutesService;

    private StopsService stopsService;

    private TimePointDeparturesService timePointDeparturesService;
    public static final String routesNotFoundMessage = "No routeNames found ";
    public static final String stopsMultipleMessage = "Multiple stops found ";
    public static final String stopsNotFoundMessage = "No stops found ";
    public static final String noBusesMessage = "No buses found for the duration route , stop";
    public static final String oopsMessaage = "!Oops you just missed your Bus, hit again to check the next available bus time!!!";
    public static final String invalidRouteMessage = "Invalid route name";
    public static final String invalidStopMessage = "Invalid stop name";
    public static final String invalidDirectionMessage = "Invalid direction specified direction can only be EAST, WEST, NORTH, SOUTH";
    static final Logger LOGGER = LoggerFactory.getLogger(NextAvailiableBusServiceTimeCalcImpl.class);
    private static final String routePattern = "^[a-zA-Z0-9|(\\s\\-\\*\\#\\$\\&)]{5,500}";
    private static final String stopPattern = "^[a-zA-Z0-9|(\\s\\-\\*\\#\\$\\&)]{5,500}";
    private static final String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";

    @Autowired
    public NextAvailiableBusServiceTimeCalcImpl(AllRoutesService allRoutesService,
                                                StopsService stopsService, TimePointDeparturesService timePointDeparturesService) {
        this.allRoutesService = allRoutesService;
        this.stopsService = stopsService;
        this.timePointDeparturesService = timePointDeparturesService;
    }


    @Override
    public String getNextDepartTime(String routeName, String directionId, String stopName) {

        //validate params
        //

        if (!checkPattern(routeName, routePattern)) {
            return invalidRouteMessage;
        }

        if (!checkPattern(stopName, stopPattern)) {
            return invalidStopMessage;
        }

        List<AppConstants> finalDirectionList = Stream.of(AppConstants.values()).filter(e ->
                e.getDirection().trim().equalsIgnoreCase(directionId.trim())).collect(Collectors.toList());

        if (finalDirectionList.isEmpty()) {
            return invalidDirectionMessage;
        }

        final String DIRECTION = AppConstants.valueOf(directionId.trim().toUpperCase()).getValue();

        //Getting routes
        List<RoutesVO> allRoutes = allRoutesService.getAllRoutes();

        //Filtering routes
        List<RoutesVO> filteredRoutes = allRoutes.parallelStream().filter(f -> f.getDescription().trim().toLowerCase()
                .contains(routeName.trim().toLowerCase())).collect(Collectors.toList());

        //Check if route more than one
        if (filteredRoutes.size() > 1) {
            final String logMsg = routesMultipleMessage + routeName;
            LOGGER.error(logMsg);
            return routesMultipleMessage + routeName;
        } else if (filteredRoutes.isEmpty()) {
            final String logMsg = routesNotFoundMessage + routeName;
            LOGGER.error(logMsg);
            return logMsg;
        }


        //Get stops of routes and direction
        List<StopsVO> stopsList = stopsService.getStopsByRouteIdAndDirection(filteredRoutes.get(0).getRoute(),
                DIRECTION);

        //Filter stops
        List<StopsVO> filteredStops = stopsList.parallelStream().filter(f -> f.getText().trim().toLowerCase().contains(stopName.trim().toLowerCase()))
                .collect(Collectors.toList());

        //Check if stops are more than one
        if (filteredStops.size() > 1) {
            final String logMsg = stopsMultipleMessage + routeName;
            LOGGER.error(logMsg);
            return logMsg;
        } else if (filteredStops.isEmpty()) {
            final String logMsg = stopsNotFoundMessage + routeName;
            LOGGER.error(logMsg);
            return logMsg;
        }

        //Get Time point departures
        List<TimePointDeparturesVO> timePointDeparturesList = timePointDeparturesService
                .getTimePointDeparturesByRouteIdDirectionIdAndStopId(filteredRoutes.get(0).getRoute(),
                        DIRECTION, filteredStops.get(0).getValue());

        if (timePointDeparturesList.isEmpty()) {
            LOGGER.error(noBusesMessage);
            return noBusesMessage;
        }

        timePointDeparturesList.sort(Comparator.comparing(TimePointDeparturesVO::getDepartureTime));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);


        LocalDateTime departDateTime = LocalDateTime.parse(timePointDeparturesList.get(0).getDepartureTime(), formatter);

        LocalDateTime currentTime = LocalDateTime.now();

        long diffInMinutes = currentTime.until(departDateTime, ChronoUnit.MINUTES);

        if (diffInMinutes == 0) {
            LOGGER.error(oopsMessaage);
            return oopsMessaage;
        }

        return diffInMinutes + " Minutes";

    }

    /**
     * Helper method
     *
     * @param actString
     * @param regEx
     * @return
     */
    private boolean checkPattern(String actString, String regEx) {
        Pattern pattern = Pattern.compile(regEx);
        return pattern.matcher(actString).find();
    }

}
