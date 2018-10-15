package com.qa.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBaseClass;
import com.qa.client.Restclient;
import com.qa.util.TestUtil;

public class getAPITestClass extends TestBaseClass{
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

@Test(priority=1)
public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException, JSONException{
	restClient = new Restclient();
	closeablehttpresponse = restClient.get(url);
	
	//1.Status code:-
	int statuscode = closeablehttpresponse.getStatusLine().getStatusCode();
    System.out.println("Status code------>"+statuscode);
    
    Assert.assertEquals(statuscode,RESPONCE_STATUS_CODE_200,"Status code is not 200");//This is for response
    
    
    //2.Get the JSON string
    String responceString=EntityUtils.toString(closeablehttpresponse.getEntity(),"UTF-8");//It will convert the JSOn into the string
    
    JSONObject ResponceJson=new JSONObject(responceString);//Convert the string into the JSON 
    System.out.println("Responce JSON from API-->>>>"+ResponceJson);
   
    //3.Will get all the headers
    
    //Single value assertion
    String perPage =  TestUtil.getValueByJPath(ResponceJson,"/per_page");
    System.out.println("Value of per page is---->"+perPage);
    Assert.assertEquals(Integer.parseInt(perPage),3);   
  
   
   String TotalValue =  TestUtil.getValueByJPath(ResponceJson,"/total");
    System.out.println("Value of per page is---->"+TotalValue);
    Assert.assertEquals(Integer.parseInt(TotalValue),12);
    
    
    
    String lastName = TestUtil.getValueByJPath(ResponceJson, "/data[0]/last_name");
    String id = TestUtil.getValueByJPath(ResponceJson, "/data[0]/id");
    String avatar = TestUtil.getValueByJPath(ResponceJson, "/data[0]/avatar");
    String firstName = TestUtil.getValueByJPath(ResponceJson, "/data[0]/first_name");
    
    System.out.println(lastName);
    System.out.println(id);
    System.out.println(avatar);
    System.out.println(firstName);
    
    
    
    //Get the value from the JSON array
    Header[] headerArray=closeablehttpresponse.getAllHeaders();
    
    HashMap<String,String> AllHeaders = new HashMap<String,String>();
    for(Header header : headerArray){
    	AllHeaders.put(header.getName(),header.getValue());
    }
    System.out.println("Headers Array----->"+AllHeaders);

}

@Test(priority=2)
public void getAPITestWithHeaders() throws ClientProtocolException, IOException, JSONException{
	restClient = new Restclient();
	HashMap<String,String> headerMap = new HashMap<String,String>();
	headerMap.put("Content-Type", "application/json");//if the file is xml the values will be application/xml
	
	//This is for reference puprope only.It is dymmy data
	/*headerMap.put("username", "Test@test.com");
	headerMap.put("password", "pass");
	headerMap.put("Auth Token", "1234");*/
	
	closeablehttpresponse = restClient.get(url,headerMap);

  }
}