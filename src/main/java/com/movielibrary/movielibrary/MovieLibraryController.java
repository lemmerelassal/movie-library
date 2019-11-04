package com.movielibrary.movielibrary;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieLibraryController {

    private static MovieLibrary movielibrary = new MovieLibrary("data/movies.json");

    @RequestMapping("/list")
    public String list() {
        return movielibrary.toString();
    }

    @RequestMapping(value = "/delete/{title}", method = RequestMethod.GET)
    @ResponseBody
    public boolean delete(
      @PathVariable("title") String title) {
        return movielibrary.Delete(title);
    }

    @RequestMapping(value = "/update/{title}/{director}/{releaseDate}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public boolean update(
      @PathVariable("title") String title, @PathVariable("director") String director, @PathVariable("releaseDate") String releaseDate, @PathVariable("type") String type)  {
        return movielibrary.Update(title, director, releaseDate, type);
    }

    @RequestMapping(value = "/insert/{title}/{director}/{releaseDate}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public boolean insert(
      @PathVariable("title") String title, @PathVariable("director") String director, @PathVariable("releaseDate") String releaseDate, @PathVariable("type") String type)  {
        return movielibrary.Create(title, director, releaseDate, type);
    }

}