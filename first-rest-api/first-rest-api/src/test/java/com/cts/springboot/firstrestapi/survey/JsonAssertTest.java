package com.cts.springboot.firstrestapi.survey;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

class JsonAssertTest {

	@Test
	void jsonAssert_learningBasics() throws JSONException {
		String expectedResponse = 
				"""
	 			  {"id":"Question1",
	 			  "descripton":"Most Popular Cloud Platform Today",
	 			  "correctAnswer":"AWS"}
	 		    """;
		
		String actualResponse = 
				"""
	 			    {"id":"Question1",
	 			    "descripton":"Most Popular Cloud Platform Today",
	 			    "options":["AWS","Azure","Google Cloud","Oracle Cloud"],
	 			    "correctAnswer":"AWS"}
	 		    """;
		
		JSONAssert.assertEquals(expectedResponse, actualResponse, false);
	}

}

/*
 * JSONAssert ignores the whitespaces.
 * JSONAssert excatly returns what is not matching. 
 * JSONAssert returns error if one of the field is missing in expected and actual.
 * In order to make it correct we need to change to false from true in strict param.
 * JSONAssert.assertEquals(expectedResponse, actualResponse, strict);
 */
