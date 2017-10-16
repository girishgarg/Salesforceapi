package com.salesforce.auth;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;

import org.springframework.web.util.UriComponentsBuilder;


public class AuthHelper {
	public static String getLoginUrl(UUID state)  {
		String clientid = "3MVG9d8..z.hDcPKNvaQPXpbS5HxfEtqxHL6j1SoVSDIsJcfYc2FxgBhU.uECv4Gy6LPFinB4uQEA9PNXmEdV";
		String redirecturi = "https://localhost:8443/salesforceapi/authorize.html";
		String authurl= "https://login.salesforce.com/services/oauth2/authorize?response_type=code&client_id="+clientid+"&redirect_uri="+redirecturi+"&state=mystate";
	    UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(authurl);
		    
		    
		    
		 
	    return urlBuilder.toUriString();
	  }
}
