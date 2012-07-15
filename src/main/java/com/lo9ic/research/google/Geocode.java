package com.lo9ic.research.google;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lo9ic.research.Util;

public class Geocode {
	
	public GeocodeResponse response = null;
	public Address address;
	public String userAgent = "Mozilla";
	public static String STREETADDRESS = "address";
	public static String REGION = "region";
	public static String SENSOR = "sensor";
	public static String APIURL = "http://maps.googleapis.com/maps/api/geocode/json";
	public static Gson gson;
	
	public Geocode () {
		GsonBuilder gsonb = new GsonBuilder();
		gson = gsonb.create();
	}

	public void getGeocodeResponse () {
		
		Properties properties = addressToProperties();
		String query = Util.makeQueryString(properties);
		URL url;
		
		try {
			
			url = new URL(APIURL+"?"+query);
			System.out.println(url);
			HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
		    
		    switch (urlc.getResponseCode()) {
			    case HttpURLConnection.HTTP_OK :
			    	handleGeocodeSuccess(urlc);
			    	break;
			    default :
			    	handleGeocodeFailure(urlc);
		    }
		    

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void handleGeocodeSuccess (HttpURLConnection urlc) throws IOException {
		StringBuffer buffer = Util.getStringBufferFromResponse(urlc);
		System.out.println(buffer);
		response = gson.fromJson(buffer.toString(), GeocodeResponse.class);
	}
	
	private void handleGeocodeFailure (HttpURLConnection urlc) {
		
	}
	
	private Properties addressToProperties () {
		Properties properties = new Properties();
		properties.setProperty(STREETADDRESS, address.streetAddress + ", " + address.cityState + ", " + address.postalCode);
		properties.setProperty(REGION, address.region);
		properties.setProperty(SENSOR, "false");
		return properties;
	}
	
}
