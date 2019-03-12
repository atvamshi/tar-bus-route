package com.target.cs.controller;

import com.target.cs.services.time.calc.NextAvailiableBusServiceTimeCalc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Project: tar-bus-time-arrival-calc
 * Package: com.target.cs.controller
 * <p>
 * User: vamshi
 * Date: 2019-03-10
 * Time: 18:25
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(path = "/app")
public class GetNextDepartTimeController {

    @Autowired
    private NextAvailiableBusServiceTimeCalc nextAvailiableBusServiceTimeCalc;

    @GetMapping(path = "/bus/{route}/{direction}/{stop}"
//            , produces = {MediaType.APPLICATION_STREAM_JSON_VALUE}
    )
    @ResponseBody
    public String getNextBusTime(@PathVariable String route,
                                 @PathVariable String direction,
                                 @PathVariable String stop) {

        return nextAvailiableBusServiceTimeCalc.getNextDepartTime(route, direction, stop);

    }


}
