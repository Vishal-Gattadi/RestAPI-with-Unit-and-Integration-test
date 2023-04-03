package com.cts.springboot.firstrestapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

	private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";
	private static String GENERIC_QUESTIONS_URL = "/surveys/Survey1/questions";

	@Autowired
	private TestRestTemplate template;

	/*
	 * Here we want to pass this url
	 * http://localhost:8080/surveys/Survey1/questions/Question1 and check it
	 * returns the output.*/
	 

	// here """ is called text blocks.

	@Test
	void retrieveSpecificSurveyQuestion_basicScenario() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

		String expectedResponse = """
				  {"id":"Question1",
				  "descripton":"Most Popular Cloud Platform Today",
				  "correctAnswer":"AWS"}
				""";
		// It is considered good practice that to check content type and status code
		// before actual response.
		// Status of Response is it 200
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// [Content-Type:"application/json"
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

	}

	@Test
	void retrieveAllSurveyQuestions_basicScenario() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity(GENERIC_QUESTIONS_URL, String.class);

		String expectedResponse = """
				  [{"id":"Question1"};
				  {"id":"Question2"},
				  {"id":"Question3"}]
				""";
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

	}

	@Test
	void addNewSurveyQuestion_basicScenario() {

		String requestBody = """
					{
					"descripton":"Your Favorite Language",
					"options":["Java","Python","JavaScript","Baskell"],
					"correctAnswer":"Java"
					}
				""";

		// http://localhost:8080/surveys/Survey1/questions/
		// We want to send POST request
		// RequesBody
		// Content-Type application/json

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Basic " + performBasicAuthEncoding("admin","password"));
		/*
		 * here we want to combine both the headers and requestbody, so we use
		 * HttpEntity(represents HTTP request/response, consisting of headers&body
		 */
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);
		// Asserts
		// 201
		// Location:"http:/surveys/Survey1/questions/"

		ResponseEntity<String> responseEntity = template.exchange(GENERIC_QUESTIONS_URL, HttpMethod.POST, httpEntity,
				String.class);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		String locationHeader = responseEntity.getHeaders().get("Location").get(0);
		assertTrue(locationHeader.contains("/surveys/Survey1/questions"));
		
		/*
		 * Here we are negating the request i.e., request will be deleted after
		 * gerenation. 
		 */
		template.delete(locationHeader);

	}
	
	String performBasicAuthEncoding(String user, String password) {
		String combined = user + ":" + password;
		// encode to Base64 which returns bytes
		byte[] encodedBytes = Base64.getEncoder().encode(combined.getBytes());
		//bytes are converted into string
		return new String(encodedBytes);
	}

}

/*
 * webEnvironment is used to check whether the port is available or not, coz
 * there are chances that port may be already occupied.
 * 
 */
//Location: http://localhost:8080/surveys/Survey1/questions/1215993616
/*
 * [Location:"http://localhost:60922/surveys/Survey1/questions490453911",
 * Content-Length:"0", Date:"Thu, 30 Mar 2023 06:23:13 GMT",
 * Keep-Alive:"timeout=60", Connection:"keep-alive"] null
 */

/*
 * This test suite may have errors because, we cannot extract some of the methods
 * Due to that we cannot change the code.
 */



