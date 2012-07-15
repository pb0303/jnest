package com.lo9ic.research.noaa;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lo9ic.research.Util;
import com.lo9ic.research.google.AltitudeResponse;

public class Forecast {

	public AltitudeResponse response = null;
	public ForecastRequest request = null;
	public static String APIURL = "http://graphical.weather.gov/xml/sample_products/browser_interface/ndfdXMLclient.php";
	public static Gson gson;
	
	public Forecast () {
		GsonBuilder gsonb = new GsonBuilder();
		gson = gsonb.create();
	}
	
	public void getForecastResponse () {
		
		if (request == null)
			return;
		
		String query = Util.makeQueryString(request);
		URL url;
		
		try {
			
			url = new URL(APIURL+"?"+query);
			System.out.println(url);
			HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
		    
		    switch (urlc.getResponseCode()) {
			    case HttpURLConnection.HTTP_OK :
			    	handleForecastSuccess(urlc);
			    	break;
			    default :
			    	handleForecastFailure(urlc);
		    }
		    

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void handleForecastSuccess (HttpURLConnection urlc) throws IOException {
		StringBuffer buffer = Util.getStringBufferFromResponse(urlc);
		System.out.println(buffer);
		//response = gson.fromJson(buffer.toString(), ForecastResponse.class);
	}
	
	private void handleForecastFailure (HttpURLConnection urlc) {
		
	}
	
}

