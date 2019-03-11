package com.target.cs.constants;

/**
 * Project: tar-bus-time-arrival-calc
 * Package: com.target.cs.constants
 * <p>
 * User: vamshi
 * Date: 2019-03-10
 * Time: 22:14
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public enum AppConstants {

    EAST("EAST","1"),
    WEST("WEST","2"),
    NORTH("NORTH","3"),
    SOUTH("SOUTH","4");

    private String direction, value;

    AppConstants(String direction, String value){
        this.direction = direction;
        this.value = value;
    }


    public String getDirection(){
        return this.direction;
    }

    public String getValue(){
        return this.value;
    }

}
