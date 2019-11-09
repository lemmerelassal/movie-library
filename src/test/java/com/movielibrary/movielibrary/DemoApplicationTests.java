package com.movielibrary.movielibrary;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	JSONArray parseJSON(String s) {
		Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);
		JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(s);
			JSONArray movArr = (JSONArray) obj;
			return movArr;
		} catch (Exception e) {
			logger.error("Exception caught in parseJSON:"+e.getMessage());
			return new JSONArray();
		}
	}

	@Test
	public void testEntireApplication() {

		MovieLibraryController mlc = new MovieLibraryController();

		// Test insertion and listing simultaneously
		int origSize = this.parseJSON(mlc.list()).size();
		assertEquals(true, mlc.insert("fromtest", "director", "releaseDate", "type"));
		JSONArray listResult = parseJSON(mlc.list());
		assertEquals(origSize, listResult.size()-1);
		
		Iterator<JSONObject> iterator = listResult.iterator();
		while (iterator.hasNext()) {
			JSONObject mov = iterator.next();
			if((String) mov.get("title") == "fromtest") {
				assertEquals("director", (String) mov.get("director"));
				assertEquals("releaseDate", (String) mov.get("releaseDate"));
				assertEquals("type", (String) mov.get("type"));
				break;
			}
		}

		// Test update
		assertEquals(true, mlc.update("fromtest", "newDirector", "newReleaseDate", "newType"));
		listResult = parseJSON(mlc.list());
		iterator = listResult.iterator();
		while (iterator.hasNext()) {
			JSONObject mov = iterator.next();
			if((String) mov.get("title") == "fromtest") {
				assertEquals("director", (String) mov.get("newDirector"));
				assertEquals("releaseDate", (String) mov.get("newReleaseDate"));
				assertEquals("type", (String) mov.get("newType"));
				break;
			}
		}

		// Test delete
		assertEquals(true, mlc.delete("fromtest"));
		assertEquals(origSize, parseJSON(mlc.list()).size());
	}

}