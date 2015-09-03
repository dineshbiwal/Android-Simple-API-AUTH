package com.example.testuserauth;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

public class JParser extends Activity {
	
		static InputStream is = null;
	    static JSONObject jObj = null;
	    static String json = "";
	    public static boolean status=false;
	    public static String server_url="http://104.154.70.214:3000/";
	    
	    
	    public String openConnection(String url, String methods, ArrayList<NameValuePair> params )
	    {
	    	 try
	         {
	    		 if(methods=="POST"){
	             HttpClient httpclient = new DefaultHttpClient();
	             HttpPost httppost = new HttpPost(server_url+url);
	          
	             httppost.setEntity(new UrlEncodedFormEntity(params));
	             HttpResponse response = httpclient.execute(httppost); 
	             HttpEntity entity = response.getEntity();
	             is = entity.getContent();

	             Log.e("log_tag", "connection success ");
	    		 }
	    		 else
	    		 {
	    			 DefaultHttpClient httpClient = new DefaultHttpClient();
	                 String paramString = URLEncodedUtils.format(params, "utf-8");
	                 url += "?" + paramString;
	                 HttpGet httpGet = new HttpGet(server_url+url);
	                
	                 HttpResponse httpResponse = httpClient.execute(httpGet);
	                 HttpEntity httpEntity = httpResponse.getEntity();
	                 is = httpEntity.getContent();
	                 
	                 Log.e("log_tag", "connection success ");
	    		 }
	         }
	     catch(Exception e)
	         {
	             Log.e("log_tag", "Error in http connection "+e.toString());
	         }
	     
	    	 
	    	  try{
	              BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	              StringBuilder sb = new StringBuilder();
	              String line = null;
	              while ((line = reader.readLine()) != null) 
	              {
	                      sb.append(line + "\n");
	              }
	              is.close();
	              reader.close();
	              json=sb.toString();
	            
	          }
	          catch(Exception e)
	          {
	             Log.e("log_tag", "Error converting result "+e.toString());
	          }
	    	  return json;
	    }
	
			 public boolean isEmailValid(String email) { 
			        boolean isValid = false;
			        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			        CharSequence inputStr = email;
			        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			        Matcher matcher = pattern.matcher(inputStr);
			        if (matcher.matches()) {
			               isValid = true;
			        }
			        return isValid;
			    }
			 public boolean isPhonenumber(String pnumber)
			 {
				 boolean isValid = false;
				 String exp = "\\d{3}-\\d{7}";
				 CharSequence inputStr = pnumber;
				 Pattern pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE);
			        Matcher matcher = pattern.matcher(inputStr);
			        if (matcher.matches()) {
			               isValid = true;
			        }
			        return isValid;
			 }
			
}
