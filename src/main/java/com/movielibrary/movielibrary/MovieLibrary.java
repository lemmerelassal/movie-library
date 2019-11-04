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

final class Movie {

    private final String title, director, releaseDate, type;

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

    public String toString() {
        return "{\n\t\"title\":\"" + title + "\",\n\tdirector\":\"" + director + "\",\n\t\"releaseDate\":\""
                + releaseDate + "\",\n\t\"type\":\"" + type + "\"\n}";
    }

}

public class MovieLibrary {
    // HashMap is useful in this case because we want to allow CRUD, which is easily
    // manipulated.
    Map<String, Movie> movies = new HashMap<>();
    String filename;

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
            e.printStackTrace();
        }
    }

    public String toString() {
        String result = "[\n";
        for (Movie movie : movies.values()) {
            result += movie.toString() + ",";
        }
        result = result.substring(0, result.length() - 1);
        result += "\n]";
        return result;
    }

    public boolean SaveToFile() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(this.filename));
            writer.write(toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean Create(String title, String director, String releaseDate, String type) {
        if(!movies.containsKey(title)) {
            movies.put(title, new Movie(title, director, releaseDate, type));
            return SaveToFile();
        }
        return false;
    }

    public boolean Update(String title, String director, String releaseDate, String type) {
        if(movies.containsKey(title)) {
            movies.replace(title, new Movie(title, director, releaseDate, type));
            return SaveToFile();
        }
        return false;
    }

    public boolean Delete(String title) {
        if(movies.containsKey(title)) {
            movies.remove(title);
            return SaveToFile();
        }
        return false;
    }


}