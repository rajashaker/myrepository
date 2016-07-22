package com.test.user.rest.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.user.rest.exception.UserException;
import com.test.user.rest.request.UserRequest;
import com.test.user.rest.response.JsonResponse;
import com.test.user.rest.response.UserResponse;
import com.test.user.rest.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	public static final String APPLICATION_JSON_UTF8 = "application/json; charset=UTF-8";
	public static final String STATUS_OK = "OK";

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String ping() {
		return "Hello";
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.POST, consumes = APPLICATION_JSON_UTF8, produces = APPLICATION_JSON_UTF8)
	@ResponseBody
	public JsonResponse createUser(@RequestBody UserRequest userRequest) throws UserException{
		UserResponse userResponse = userService.createUser(userRequest);
		JsonResponse json = new JsonResponse();
		json.setSuccess(true);
		json.setStatus(STATUS_OK);
		json.setResult(userResponse);
		return json;
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = APPLICATION_JSON_UTF8)
	@ResponseBody
	public JsonResponse viewAllUsers() throws UserException{
		UserResponse[] userResponse = userService.viewAllUsers();
		JsonResponse json = new JsonResponse();
		json.setSuccess(true);
		json.setStatus(STATUS_OK);
		json.setResult(userResponse);
		return json;
	}

	@RequestMapping(value = "/users/{userName}", method = RequestMethod.GET, produces = APPLICATION_JSON_UTF8)
	@ResponseBody
	public JsonResponse viewUser(@PathVariable("userName") String userName) throws UserException{
		UserResponse userResponse = userService.viewUser(userName);
		JsonResponse json = new JsonResponse();
		json.setSuccess(true);
		json.setStatus(STATUS_OK);
		json.setResult(userResponse);
		return json;
	}

	@RequestMapping(value = "/users/{userName}", method = RequestMethod.POST, consumes = APPLICATION_JSON_UTF8, produces = APPLICATION_JSON_UTF8)
	@ResponseBody
	public JsonResponse updateUser(@PathVariable("userName") String userName, @RequestBody UserRequest userRequest, HttpServletRequest request) throws UserException{
		String method = null; 
		if(request.getHeaderNames()!=null){
			method=request.getHeader("x-http-method-override");
			if(null==method){
				method="";
			}
		}
		
		UserResponse userResponse=null;
		JsonResponse json = null;
		if(method.equalsIgnoreCase("delete")){
			userResponse = userService.deleteUser(userName);
			json = new JsonResponse();
			json.setSuccess(true);
			json.setStatus(STATUS_OK);
			json.setResult(userResponse);
		}
		
		if(method.equalsIgnoreCase("put") || method.equalsIgnoreCase("patch")){
			userResponse = userService.updateUser(userRequest);
			json = new JsonResponse();
			json.setSuccess(true);
			json.setStatus(STATUS_OK);
			json.setResult(userResponse);
		}
		return json;
	}

}
