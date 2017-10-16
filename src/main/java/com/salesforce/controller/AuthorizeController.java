package com.salesforce.controller;
import java.util.UUID;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthorizeController {
	@RequestMapping(value="/authorize", method=RequestMethod.GET)
	  public String authorize(
	      @RequestParam("code") String code, 
	     // @RequestParam("state") UUID state,
	      HttpServletRequest request) throws ClientProtocolException, IOException,Exception  { 
		
		System.out.println(code);
		
		HttpSession session = request.getSession();
	    
	      session.setAttribute("authCode", code);
	   accesstokenget(code);
	   
	    
	    return "mail";
}
	
	//function to fetch accesstoken
	public void accesstokenget(String code) throws ClientProtocolException, IOException,Exception   {
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		StringBuilder   builder = new StringBuilder();
		String code1=code;
		String clientid = "3MVG9d8..z.hDcPKNvaQPXpbS5HxfEtqxHL6j1SoVSDIsJcfYc2FxgBhU.uECv4Gy6LPFinB4uQEA9PNXmEdV";
		String redirecturi = "https://localhost:8443/salesforceapi/authorize.html";
		String clientsecret = "4906562629064842357";
		HttpPost postRequest = new HttpPost(
			"https://login.salesforce.com/services/oauth2/token?grant_type=authorization_code&code="+code1+"&client_id="+clientid+"&client_secret="+clientsecret+"&redirect_uri="+redirecturi);
		HttpResponse response = httpClient.execute(postRequest);
		 BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		  String line = "";
		  while ((line = rd.readLine()) != null) {
		    builder.append(line);
		    //System.out.println(line);
		    
		  }
		  JSONObject  jsonObject = new JSONObject(builder.toString());
		  String accesstoken = jsonObject.getString("access_token");
		  String instanceurl = jsonObject.getString("instance_url");
		  meetingget(accesstoken,instanceurl);
		  System.out.println(accesstoken);
	}
	
	
	//function to fetch meetings
	public void meetingget(String code,String instanceurl) throws ClientProtocolException, IOException,Exception  {
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		StringBuilder   builder = new StringBuilder();
		HttpGet postRequest = new HttpGet(instanceurl+
			"/services/data/v20.0/query/?q=SELECT+AccountId,ActivityDate,ActivityDateTime,Description,EndDateTime,StartDateTime,Subject+from+Event");
		postRequest.addHeader("Authorization", "Bearer "+code);
		HttpResponse response = httpClient.execute(postRequest);
		
		BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		  String line = "";
		  System.out.println("------total meetings are as follows------");
		  while ((line = rd.readLine()) != null) {
		    System.out.println(line);
		    builder.append(line);
		  }
		  
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}


