package com.bridgelabz.fundoo.util;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;

public class StatusHelper {

	/**
	 * Method to return response object
	 * 
	 * @param statusMessage takes from user
	 * @param statusCode takes from user
	 * @return response object
	 */
	public static Response statusInfo(String statusMessage, int statusCode)
	{
		Response response = new Response();
		response.setStatusCode(statusCode);
		response.setStatusMessage(statusMessage);
	
		return response;
	}
	
	/**
	 * Method to return responseToken object
	 * 
	 * @param statusMessage takes from user
	 * @param statusCode takes from user
	 * @return responseToken object
	 */
	public static ResponseToken tokenStatusInfo(String statusMessage,int statusCode,String token)
	{
		ResponseToken tokenResponse = new ResponseToken();
		tokenResponse.setStatusCode(statusCode);
		tokenResponse.setStatusMessage(statusMessage);
		tokenResponse.setToken(token);

		return tokenResponse;
	}
}
