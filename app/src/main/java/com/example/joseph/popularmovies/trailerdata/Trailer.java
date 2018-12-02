package com.example.joseph.popularmovies.trailerdata;

public class Trailer {

    private String trailerId; // equivalent is id in trailers json
    private String trailerKey; // equivalent is key
    private String trailerName; // equivalent is name
    private String trailerSite; // equivalent is site
    private String trailerType; // equivalent is type


    public Trailer(String id, String key, String name, String site, String type) {

        trailerId = id;
        trailerKey = key;
        trailerName = name;
        trailerSite = site;
        trailerType = type;
    }
    public Trailer(){}

    public Trailer(String key){trailerKey = key;}


    public String gettrailerId() { return trailerId; }
    public String gettrailerKey(){return trailerKey;}
    public String gettrailerName(){return trailerName;}
    public String gettrailerSite(){return trailerSite;}
    public String gettrailerType(){return trailerType;}


}

