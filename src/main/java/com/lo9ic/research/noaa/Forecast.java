package com.lo9ic.research.noaa;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.lo9ic.research.Util;
import com.lo9ic.research.WeatherFactory;
import com.lo9ic.research.noaa.jaxb.Dwml;

public class Forecast {

	public ForecastRequest request = null;
	public static String APIURL = "http://graphical.weather.gov/xml/sample_products/browser_interface/ndfdXMLclient.php";
	
	private Dwml dwml;
    private JAXBContext jaxb;
    private Unmarshaller unmarshaller;
    
    {
        try {
            jaxb = JAXBContext.newInstance("com.lo9ic.research.noaa.jaxb");
            unmarshaller = jaxb.createUnmarshaller();
        }
        catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    
	
	public Forecast () {

	
	}
	
	public void getForecastResponse () {
		
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
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}
	
	private void handleForecastSuccess (HttpURLConnection urlc) throws IOException, JAXBException {
		InputStream is = urlc.getInputStream();
		//StringBuffer buffer = Util.getStringBufferFromResponse(urlc);
		//System.out.println(buffer);
		dwml = (Dwml) unmarshaller.unmarshal(is);
		System.out.println(dwml.toString());
		WeatherFactory.toWeather(dwml);
		//response = gson.fromJson(buffer.toString(), ForecastResponse.class);
	}
	
	private void handleForecastFailure (HttpURLConnection urlc) {
		
	}
	
	/**
     * @param dwml the dwml to set
     */
    public void setDwml(Dwml dwml) {
        this.dwml = dwml;
    }

    /**
     * @return the dwml
     */
    public Dwml getDwml() {
        return this.dwml;
    }
    
    /**
     * @return the dwml
     */
    public Dwml getDwml(URI uri) throws Exception {
        this.dwml = (Dwml) unmarshaller.unmarshal(uri.toURL());
        System.out.println(uri.toURL().toString());
        return this.dwml;
    }

    public Dwml getDwml(URL url) throws Exception {
        this.dwml = (Dwml) unmarshaller.unmarshal(url);
        System.out.println(url);
        return this.dwml;
    }
    
    /**
     * @return the dwml
     */
    public Dwml getDwml(String location) throws Exception {
        this.dwml = (Dwml) unmarshaller.unmarshal(new File(location));
        return this.dwml;
    }
	
}

