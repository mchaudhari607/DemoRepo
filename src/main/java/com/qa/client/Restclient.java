package com.qa.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Restclient {

	//1.Get method without headers
public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException, JSONException{
		
		CloseableHttpClient httpclient=HttpClients.createDefault();//create 1 client connection
		HttpGet httpget = new HttpGet(url);//create one get connection for particular url
		
		
		CloseableHttpResponse closeablehttpresponse = httpclient.execute(httpget); //hit the get url
		return closeablehttpresponse;
		
		
		
	}
	//2.Get method with headers
public CloseableHttpResponse get(String url,HashMap<String,String> headerMap) throws ClientProtocolException, IOException, JSONException{
		
		CloseableHttpClient httpclient=HttpClients.createDefault();//create 1 client connection
		HttpGet httpget = new HttpGet(url);//http get request
		
		
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httpget.addHeader(entry.getKey(),entry.getValue());
		}
		
		CloseableHttpResponse closeablehttpresponse = httpclient.execute(httpget); //hit the get url
		return closeablehttpresponse;
	
}
    //3.Create post method
 public CloseableHttpResponse post(String url,String entityString,HashMap<String,String> headerMap) throws ClientProtocolException, IOException{
	 CloseableHttpClient httpclient=HttpClients.createDefault();//create 1 client connection
		HttpPost httppost = new HttpPost(url);//http post request
		httppost.setEntity(new StringEntity(entityString));
		//for headers 
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httppost.addHeader(entry.getKey(),entry.getValue());
		}
		CloseableHttpResponse  closeableHttpResponse = httpclient.execute(httppost);
		return closeableHttpResponse;
 }

}