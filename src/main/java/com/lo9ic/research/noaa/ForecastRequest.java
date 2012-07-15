package com.lo9ic.research.noaa;

import java.util.Properties;

public class ForecastRequest extends Properties {
	
	private static final long serialVersionUID = -4226083220894643847L;
	public static String LATITUDE = "lat";
	public static String LONGITUDE = "lon";
	public static String PRODUCT = "product";
	public static String UNIT = "Unit";
	public static String BEGIN = "begin";
	public static String END = "end";
	public static String TEMPERATURE = "temp";
	public static String DEWPOINT = "dew";
	public static String SKYCOVER = "sky";
	public static String RELATIVEHUMIDITY = "rh";
	public static String WINDSPEED = "wspd";
	public static String WINDDIRECTION = "wdir";
	
	public String product = "time-series";
	public String unit = "m";
	
	public ForecastRequest () {
		this.setProperty(PRODUCT, product);
		this.setProperty(UNIT, unit);
		this.setProperty(BEGIN, "2004-01-1T00:00:00");
		this.setProperty(END, "2020-04-20T00:00:00");
		this.setProperty(TEMPERATURE, TEMPERATURE);
		this.setProperty(DEWPOINT, DEWPOINT);
		this.setProperty(SKYCOVER, SKYCOVER);
		this.setProperty(RELATIVEHUMIDITY, RELATIVEHUMIDITY);
		this.setProperty(WINDSPEED, WINDSPEED);
		this.setProperty(WINDDIRECTION, WINDDIRECTION);
	}
	
	/*
	* Here's the original request
	* lat=40.635498&lon=-111.963999&product=time-series&Unit=m&begin=2004-01-1T00:00:00&end=2020-04-20T00:00:00&temp=temp&dew=dew&sky=sky&rh=rh&wspd=wspd&wdir=wdir'
	*/
}
