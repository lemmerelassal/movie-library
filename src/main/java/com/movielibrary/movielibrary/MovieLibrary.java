package com.movielibrary.movielibrary;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MovieLibrary {
    // HashMap is useful in this case because we want to allow CRUD, which is easily
    // manipulated.
    Map<String, Movie> movies = new HashMap<>();
    String filename;

    Logger logger = LoggerFactory.getLogger(MovieLibrary.class);

    /* Constructor */
    public MovieLibrary(String filename) {
        this.filename = filename;
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(filename));

            JSONArray movArr = (JSONArray) obj;

            Iterator<JSONObject> iterator = movArr.iterator();
            while (iterator.hasNext()) {
                JSONObject mov = iterator.next();
                movies.put((String) mov.get("title"), new Movie((String) mov.get("title"), (String) mov.get("director"),
                        (String) mov.get("releaseDate"), (String) mov.get("type")));
            }
        } catch (Exception e) {
            logger.error("Exception caught in MovieLibrary constructor:"+e.getMessage());
        }
    }

    @Override
    public String toString() {
        String res = "[\n";
        for (Movie movie : movies.values()) {
            res += movie.toString() + ",";
        }
        if(res.length() > 2)
            if(res.charAt(res.length()-1) == ',')
                res = res.substring(0, res.length() - 1);
        res += "\n]";
        logger.debug("toString res:"+res);
        return res;
    }

    public boolean saveToFile() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(this.filename));
            writer.write(toString());
            writer.close();
        } catch (IOException e) {
            logger.error("MovieLibrary.saveToFile exception: "+e.getMessage());
            return false;
        }
        return true;
    }

    public boolean create(String title, String director, String releaseDate, String type) {
        if(!movies.containsKey(title)) {
            movies.put(title, new Movie(title, director, releaseDate, type));
            return saveToFile();
        }
        return true;
    }

    public boolean update(String title, String director, String releaseDate, String type) {
        if(movies.containsKey(title)) {
            movies.replace(title, new Movie(title, director, releaseDate, type));
            return saveToFile();
        }
        return true;
    }

    public boolean delete(String title) {
        if(movies.containsKey(title)) {
            movies.remove(title);
            return saveToFile();
        }
        return true;
    }


}