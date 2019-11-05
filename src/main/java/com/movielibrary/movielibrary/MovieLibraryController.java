package com.movielibrary.movielibrary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class MovieLibraryController {

    private static MovieLibrary movielibrary = new MovieLibrary("data/movies.json");
    Logger logger = LoggerFactory.getLogger(MovieLibraryController.class);

    @CrossOrigin
    @RequestMapping("/list")
    public String  list() {
        String res = movielibrary.toString();
        logger.info("/list result: "+res);
        return res;
    }

    @CrossOrigin
    @RequestMapping(value = "/delete/{title}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity delete(
      @PathVariable("title") String title) {
        if(movielibrary.delete(title)) {
          logger.info("Delete %s successful", title);
          return new ResponseEntity(HttpStatus.OK);
        } else {
          logger.error("Delete %s failed", title);
          return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/update/{title}/{director}/{releaseDate}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity update(
      @PathVariable("title") String title, @PathVariable("director") String director, @PathVariable("releaseDate") String releaseDate, @PathVariable("type") String type)  {
        if(movielibrary.update(title, director, releaseDate, type)) {
          logger.info("Update %s successful", title);
          return new ResponseEntity(HttpStatus.OK);
        } else {
          logger.error("Update %s failed", title);
          return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/insert/{title}/{director}/{releaseDate}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity insert(
      @PathVariable("title") String title, @PathVariable("director") String director, @PathVariable("releaseDate") String releaseDate, @PathVariable("type") String type)  {
        if(movielibrary.create(title, director, releaseDate, type)) {
          logger.info("Insert %s successful", title);
          return new ResponseEntity(HttpStatus.OK);
        } else {
          logger.error("Insert %s failed", title);
          return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}