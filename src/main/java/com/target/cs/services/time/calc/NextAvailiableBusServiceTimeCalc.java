package com.target.cs.services.time.calc;

/**
 * Project: tar-bus-time-arrival-calc
 * Package: com.target.cs.services
 * <p>
 * User: vamshi
 * Date: 2019-03-10
 * Time: 16:57
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public interface NextAvailiableBusServiceTimeCalc{
    String getNextDepartTime(String routeName, String directionId, String stopName);
}
