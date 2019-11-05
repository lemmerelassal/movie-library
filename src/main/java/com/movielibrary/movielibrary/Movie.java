package com.movielibrary.movielibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class Movie {

    private final String title, director, releaseDate, type;
    Logger logger = LoggerFactory.getLogger(Movie.class);

    public Movie(String title, String director, String releaseDate, String type) {
        this.title = title;
        this.director = director;
        this.releaseDate = releaseDate;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getType() {
        return type;
    }
    
    @Override
    public String toString() {
        String res = "{\n\t\"title\":\"" + title + "\",\n\t\"director\":\"" + director + "\",\n\t\"releaseDate\":\""
                + releaseDate + "\",\n\t\"type\":\"" + type + "\"\n}";
        logger.debug("Movie.toString result:"+res);
        return res;
    }

}
