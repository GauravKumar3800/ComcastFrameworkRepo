package com.comcast.crm.generic.fileutility;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonUtility {
	
	public String getDataFromJsonFile(String key) throws Throwable,ParseException
	{
		FileReader fileR=new FileReader("./configAppData/appCommondata.json");
		
		JSONParser parser=new JSONParser();
		Object obj= parser.parse(fileR);
		JSONObject map=(JSONObject)obj;
		System.out.println(map.get("url"));
		String data=(String)map.get(key);
		return data;
	}
}
