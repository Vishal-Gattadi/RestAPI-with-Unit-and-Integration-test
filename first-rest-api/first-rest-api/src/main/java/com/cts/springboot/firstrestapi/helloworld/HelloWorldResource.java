package com.cts.springboot.firstrestapi.helloworld;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
public class HelloWorldResource {
	
	@RequestMapping("/hello-world")
	//@ResponseBody
	public String helloWorld() {
		/*
		 * here spring mvc view looks for specific name "Hello World", it will not return 
		 * the string directly back
		 * By using @ResponseBody we return the string
		 */
		return "Hello World";
	}
	
	@RequestMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	@RequestMapping("/hello-world-path-param/{name}")
	public HelloWorldBean helloWorldPathParam(@PathVariable String name) {
		return new HelloWorldBean("Hello World, "+ name);
	}
	
	@RequestMapping("/hello-world-path-param/{name}/message/{message}")
	public HelloWorldBean helloWorldMultiplePathParam(@PathVariable String name,
						@PathVariable String message) 
							{
		return new HelloWorldBean("Hello World, "+ name + "," + message);
	}

}
/*
 * we want to return the response as it is from the method without using the view i.e., jsp
 * We want to return all the methods as @ResponseBody, so that can be done using @RestController
 * 
 *@RestController - is a combination of @Controller and @ResponseBody
 *So inside the methods we don't need ResponseBody
 *
 *PathVariable:in url we send the requests, some may be complex requests such as
 *ex: /users/vishal/todos/id
 * here vishal and id are the values.
 * so we use @PathVariable to capture the path parameters.
 */



