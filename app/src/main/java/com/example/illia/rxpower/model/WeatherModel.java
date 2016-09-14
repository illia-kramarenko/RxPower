package com.example.illia.rxpower.model;

import java.util.List;

/**
 * Created by illia on 13.02.16.
 */
public class WeatherModel {
    public Coord coord;
    public List<Weather> weather;
    public String base;
    public Main main;
    public Wind wind;
    public Clouds clouds;
    public long dt;
    public Sys sys;
    public long id;
    public String name;
    public int cod;
}
