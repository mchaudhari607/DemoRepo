package com.qa.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBaseClass;
import com.qa.client.Restclient;
import com.qa.data.Users;

public class postAPITestClass extends TestBaseClass{
	       
	TestBaseClass testbase;
	String serviceurl;
	String APIurl;
	String url;
	Restclient restClient;
	CloseableHttpResponse closeablehttpresponse;       
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException, JSONException{
		testbase =new TestBaseClass();
		serviceurl = prop.getProperty("URL");
		APIurl = prop.getProperty("ServiceURL");
		url = serviceurl + APIurl;
		
	 }
       
	@Test
   public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException, JSONException{
		restClient = new Restclient();
		HashMap<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type", "application/json");
		
		//Marshelling with Jackson API
		ObjectMapper mapper = new ObjectMapper();
	    Users users = new Users("morpheus","leader");//This is the Expected user object
	    
		//conver java object to JSON
	    mapper.writeValue(new File("D:\\Manish\\Automation\\API\\RestAPI\\src\\main\\java\\com\\qa\\data\\users.json"),users);
	    
	    //Object to JSON in perticular string
	    String usersJSONString=mapper.writeValueAsString(users);//This string we can send along with the post method
	    System.out.println(usersJSONString);
	    
	    //After marshalling hit the API
	    closeablehttpresponse = restClient.post(url, usersJSONString, headerMap);
	
	    //1. Check status code
	    int statuscode = closeablehttpresponse.getStatusLine().getStatusCode();
	    Assert.assertEquals(statuscode, TestBaseClass.RESPONCE_STATUS_CODE_201);
	    
	    //2.JSON string correct or not
	   String responceString = EntityUtils.toString(closeablehttpresponse.getEntity(),"UTF-8");//This is utility used to convert the JSON into the string
	   
	   //Converting Responce string into the JSON string
	   JSONObject responeJson = new JSONObject(responceString); //Convert the above string into the JSON String
	   System.out.println("The responce from the API is------>"+responeJson);
	   
	   //Converting JSON to java
	   //This is Un-marshalling code
	   //This is actual user object
	   
	   Users usersResponceObj=mapper.readValue(responceString,Users.class);//Whatever the content and what exactly the value type() and return users obj object
	   System.out.println(usersResponceObj);
	   
	   //Printing on the console
	   Assert.assertTrue(users.getName().equals(usersResponceObj.getName()));
	   
	   Assert.assertTrue(users.getJob().equals(usersResponceObj.getJob()));
	   
	   	   
	   System.out.println(usersResponceObj.getId());
	   System.out.println(usersResponceObj.getCreatedAt());
	   
	   
	}
}
