package com.railway_services.indian.railway;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Abhishek on 30-03-2018.
 */

public class TbSClass {

    private String number;
    private String name;
    private String travel_time;
    private String src_departure;
    private String dest_arrival_time;
    private String src_departure_time;
    private String from_station_code;
    private String to_station_code;
    private String from_station_name;
    private String to_station_name;
    private Map<String,String> classesMap;
    private  Map<String,String> daysMap;
    private ArrayList<String> classes;
    private ArrayList<String> days;

    public double getDestination_lat() {
        return destination_lat;
    }

    public void setDestination_lat(double destination_lat) {
        this.destination_lat = destination_lat;
    }

    public double getDestination_lng() {
        return destination_lng;
    }

    public void setDestination_lng(double destination_lng) {
        this.destination_lng = destination_lng;
    }

    public double getSource_lat() {
        return source_lat;
    }

    public void setSource_lat(double source_lat) {
        this.source_lat = source_lat;
    }

    public double getSource_long() {
        return source_long;
    }

    public void setSource_long(double source_long) {
        this.source_long = source_long;
    }

    private double destination_lat;
    private double destination_lng;
    private double source_lat;
    private double source_long;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(String travel_time) {
        this.travel_time = travel_time;
    }

    public String getSrc_departure() {
        return src_departure;
    }

    public void setSrc_departure(String src_departure) {
        this.src_departure = src_departure;
    }

    public String getDest_arrival_time() {
        return dest_arrival_time;
    }

    public void setDest_arrival_time(String dest_arrival_time) {
        this.dest_arrival_time = dest_arrival_time;
    }

    public String getSrc_departure_time() {
        return src_departure_time;
    }

    public void setSrc_departure_time(String src_departure_time) {
        this.src_departure_time = src_departure_time;
    }

    public String getFrom_station_code() {
        return from_station_code;
    }

    public void setFrom_station_code(String from_station_code) {
        this.from_station_code = from_station_code;
    }

    public String getTo_station_code() {
        return to_station_code;
    }

    public void setTo_station_code(String to_station_code) {
        this.to_station_code = to_station_code;
    }

    public String getFrom_station_name() {
        return from_station_name;
    }

    public void setFrom_station_name(String from_station_name) {
        this.from_station_name = from_station_name;
    }

    public String getTo_station_name() {
        return to_station_name;
    }

    public void setTo_station_name(String to_station_name) {
        this.to_station_name = to_station_name;
    }

    public Map<String, String> getClassesMap() {
        return classesMap;
    }

    public void setClassesMap(Map<String, String> classesMap) {
        this.classesMap = classesMap;
    }

    public Map<String, String> getDaysMap() {
        return daysMap;
    }

    public void setDaysMap(Map<String, String> daysMap) {
        this.daysMap = daysMap;
    }

    public ArrayList<String> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<String> classes) {
        this.classes = classes;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }
}
