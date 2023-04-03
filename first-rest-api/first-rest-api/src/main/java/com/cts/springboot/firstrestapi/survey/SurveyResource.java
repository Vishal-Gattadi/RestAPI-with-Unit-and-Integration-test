package com.cts.springboot.firstrestapi.survey;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class SurveyResource {
	
	private SurveyService surveyService;
	
	//constructor injection of SurveyService
	public SurveyResource(SurveyService surveyService) {
		super();
		this.surveyService = surveyService;
	}



	/*whenever we are creating resouces, we need to create in plural
	 * i.e., /surveys
	 *  /surveys/Survey1  
	*/
	@RequestMapping("/surveys")
	public List<Survey> retrieveAllSurveys(){
		return surveyService.retrieveAllSurveys();
	}
	
	@RequestMapping("/surveys/{surveyId}")
	public Survey retrieveSurveyById(@PathVariable String surveyId){
		Survey survey = surveyService.retrieveSurveyById(surveyId);
		if(survey==null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		return survey;
	}
	
	@RequestMapping("/surveys/{surveyId}/questions")
	public List<Question> retrieveAllSurveyQuestions(@PathVariable String surveyId){
		List<Question> questions = surveyService.retrieveAllSurveyQuestions(surveyId);
		if(questions==null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		return questions;
	}
	
	@RequestMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question retrieveSpecificSurveyQuestion(@PathVariable String surveyId,
			@PathVariable String questionId){
		Question question = surveyService.retrieveSpecificSurveyQuestion(surveyId, questionId);
		if(question==null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		return question;
	}
	
	@RequestMapping(value="/surveys/{surveyId}/questions", method = RequestMethod.POST)
	public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId,
			@RequestBody Question question){
		
		String questionId = surveyService.addNewSurveyQuestion(surveyId, question);
		
		//here the location is /surveys/{surveyId}/questions/{questionId}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().
				path("{questionId}").buildAndExpand(questionId).toUri();
		return ResponseEntity.created(location).build(); 
		/*in above block we are taking the uri from above block and appending questionId
		 * and we replace the questionId with Actual questionId
		 */
	}
	
	@RequestMapping(value="/surveys/{surveyId}/questions/{questionId}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable String surveyId,
			@PathVariable String questionId){
		surveyService.deleteSurveyQuestion(surveyId, questionId);
		return ResponseEntity.noContent().build(); 
	}
	
	@RequestMapping(value="/surveys/{surveyId}/questions/{questionId}", method=RequestMethod.PUT)
	public ResponseEntity<Object> updateSurveyQuestion(@PathVariable String surveyId,
			@PathVariable String questionId,
			@RequestBody Question question){
		
		surveyService.updateSurveyQuestion(surveyId, questionId, question);
		
		return ResponseEntity.noContent().build(); 
	}
			
}
/* Here /surveys/{surveyId} we get the response code as 200, i.e., status=success
* it we return success for even null value, which isn't correct.
* so in order to avoid that we add an exception ResponseStatusException(HttpStatus.NOT_FOUND)
* 
* We we type a url in browser, it sends a GET Request.
* To execute a POST request, we uset REST API
* 
* ServletUriComponentsBuilder: Returns the current path of the request.
*/
