package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBaseClass {
	public Properties prop;
	public static int RESPONCE_STATUS_CODE_200=200;
	public static int RESPONCE_STATUS_CODE_500=500;
	public static int RESPONCE_STATUS_CODE_400=400;
	public static int RESPONCE_STATUS_CODE_401=401;
	public static int RESPONCE_STATUS_CODE_201=201;
	public TestBaseClass(){
		try{
			prop =new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/qa/configuration/config.properties");     
            prop.load(ip);		
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
