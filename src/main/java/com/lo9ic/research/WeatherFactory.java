package com.lo9ic.research;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.lo9ic.research.noaa.jaxb.DataType;
import com.lo9ic.research.noaa.jaxb.Dwml;
import com.lo9ic.research.noaa.jaxb.LocationType;
import com.lo9ic.research.noaa.jaxb.ParametersType;
import com.lo9ic.research.noaa.jaxb.PercentageValType;
import com.lo9ic.research.noaa.jaxb.StartValidTimeType;
import com.lo9ic.research.noaa.jaxb.TempValType;
import com.lo9ic.research.noaa.jaxb.TimeCoordinateType;
import com.lo9ic.research.noaa.jaxb.TimeLayoutElementType;
import com.lo9ic.research.noaa.jaxb.WdirValType;
import com.lo9ic.research.noaa.jaxb.WspdValType;
import com.lo9ic.research.noaa.jaxb.ParametersType.CloudAmount;
import com.lo9ic.research.noaa.jaxb.ParametersType.Direction;
import com.lo9ic.research.noaa.jaxb.ParametersType.Humidity;
import com.lo9ic.research.noaa.jaxb.ParametersType.Temperature;
import com.lo9ic.research.noaa.jaxb.ParametersType.WindSpeed;
import com.lo9ic.research.noaa.jaxb.ParametersType.Humidity.Value;

public class WeatherFactory {
	
	public static void toWeather(Dwml dwml) {
        
        //WeatherFactory weather = null;
        Date[] dates;
        int l = 0;
        
        if (dwml != null) {
            
            String layoutKey = null;
            DataType data = dwml.getData();
            // we are going to assume that there is only one location here...
            List<LocationType> locationTypeList = data.getLocation();
            Iterator<LocationType> ltIterator = locationTypeList.iterator();
            String locationKey = "";
            
            while (ltIterator.hasNext()) {
                LocationType location = ltIterator.next();
                locationKey = location.getLocationKey();
            }
            
            List<TimeLayoutElementType> timeLayoutElementList = data.getTimeLayout();
            Iterator<TimeLayoutElementType> tletIterator = timeLayoutElementList.iterator();
            TimeLayoutElementType timeLayoutElement = null;
            
            while (tletIterator.hasNext()) {
                timeLayoutElement = tletIterator.next();
                if (timeLayoutElement.getSummarization().equals("none") && timeLayoutElement.getTimeCoordinate() == TimeCoordinateType.LOCAL) {
                    layoutKey = timeLayoutElement.getLayoutKey();
                    break;
                }
            }
            
            if (layoutKey != null) {
                
                List<Object> startTimeList = timeLayoutElement.getStartValidTimeAndEndValidTime();
                Iterator<Object> stlIterator = startTimeList.iterator();
                l = startTimeList.size();
                dates = new Date[l];
                int c = 0;
                
                while (stlIterator.hasNext()) {
                    StartValidTimeType startValidTime = (StartValidTimeType) stlIterator.next();
                    dates[c] = startValidTime.getValue().toGregorianCalendar().getTime();
                    System.out.println(startValidTime.getValue().toGregorianCalendar().getTime().toString());
                    c++;
                }
                
                List<ParametersType> pList = data.getParameters();
                Iterator<ParametersType> plIterator = pList.iterator();
                ParametersType parametersType = null;
                
                while (plIterator.hasNext()) {
                    parametersType = plIterator.next();
                    if (parametersType.getApplicableLocation().equals(locationKey))
                        break;
                }
                
                if (parametersType == null)
                	return;
                    //return null;
                
                List<Temperature> tList = parametersType.getTemperature();
                Iterator<Temperature> tlIterator = tList.iterator();
                Temperature temperature = null;
                
                double[] drybulb = null;
                double[] dewpoint = null;
                
                while (tlIterator.hasNext()) {
                    temperature = tlIterator.next();
                    System.out.println(temperature.getType());
                    if (temperature.getType().equals("hourly") && temperature.getTimeLayout().equals(layoutKey)) {
                        List<TempValType> tvtList = temperature.getValue();
                        Iterator<TempValType> tvtIterator = tvtList.iterator();
                        drybulb = new double[l];
                        c = 0;
                        while (tvtIterator.hasNext()) {
                            drybulb[c] = Double.parseDouble(tvtIterator.next().getValue().toString());
                            System.out.println(drybulb[c]);
                            c++;
                        }
                    }
                    if (temperature.getType().equals("dew point") && temperature.getTimeLayout().equals(layoutKey)) {
                        List<TempValType> tvtList = temperature.getValue();
                        Iterator<TempValType> tvtIterator = tvtList.iterator();
                        dewpoint = new double[l];
                        c = 0;
                        while (tvtIterator.hasNext()) {
                            dewpoint[c] = Double.parseDouble(tvtIterator.next().getValue().toString());
                            System.out.println(dewpoint[c]);
                            c++;
                        }
                    }
                }
                
                List<WindSpeed> wsList = parametersType.getWindSpeed();
                Iterator<WindSpeed> wsIterator = wsList.iterator();
                WindSpeed windSpeed = null;
                
                double[] windspd = null;
                
                while (wsIterator.hasNext()) {
                    windSpeed = wsIterator.next();
                    System.out.println(windSpeed.getType());
                    if (windSpeed.getType().equals("sustained") && temperature.getTimeLayout().equals(layoutKey)) {
                        List<WspdValType> wvtList = windSpeed.getValue();
                        Iterator<WspdValType> wvtIterator = wvtList.iterator();
                        windspd = new double[l];
                        c = 0;
                        while (wvtIterator.hasNext()) {
                            windspd[c] = Double.parseDouble(wvtIterator.next().getValue().toString());
                            System.out.println(windspd[c]);
                            c++;
                        }
                    }
                }
                
                List<Direction> dList = parametersType.getDirection();
                Iterator<Direction> dIterator = dList.iterator();
                Direction direction = null;
                
                double[] winddir = null;
                
                while (dIterator.hasNext()) {
                    direction = dIterator.next();
                    System.out.println(direction.getType());
                    if (direction.getType().equals("wind") && temperature.getTimeLayout().equals(layoutKey)) {
                        List<WdirValType> wvtList = direction.getValue();
                        Iterator<WdirValType> wvtIterator = wvtList.iterator();
                        winddir = new double[l];
                        c = 0;
                        while (wvtIterator.hasNext()) {
                            winddir[c] = Double.parseDouble(wvtIterator.next().getValue().toString());
                            System.out.println(winddir[c]);
                            c++;
                        }
                    }
                }
                
                List<CloudAmount> caList = parametersType.getCloudAmount();
                Iterator<CloudAmount> caIterator = caList.iterator();
                CloudAmount cloudAmount = null;
                
                double[] totskycov = null;
                
                while (caIterator.hasNext()) {
                    cloudAmount = caIterator.next();
                    System.out.println(cloudAmount.getType());
                    if (cloudAmount.getType().equals("total") && temperature.getTimeLayout().equals(layoutKey)) {
                        List<PercentageValType> pvtList = cloudAmount.getValue();
                        Iterator<PercentageValType> pvtIterator = pvtList.iterator();
                        totskycov = new double[l];
                        c = 0;
                        while (pvtIterator.hasNext()) {
                            totskycov[c] = Double.parseDouble(pvtIterator.next().getValue().toString());
                            System.out.println(totskycov[c]);
                            c++;
                        }
                    }
                }
                
                List<Humidity> hList = parametersType.getHumidity();
                Iterator<Humidity> hIterator = hList.iterator();
                Humidity humidity = null;
                
                double[] relhum = null;
                
                while (hIterator.hasNext()) {
                    humidity = hIterator.next();
                    System.out.println(humidity.getType());
                    if (humidity.getType().equals("relative") && temperature.getTimeLayout().equals(layoutKey)) {
                        List<Value> vList = humidity.getValue();
                        Iterator<Value> vIterator = vList.iterator();
                        relhum = new double[l];
                        c = 0;
                        while (vIterator.hasNext()) {
                            relhum[c] = Double.parseDouble(vIterator.next().getValue().toString());
                            System.out.println(relhum[c]);
                            c++;
                        }
                    }
                }
                                
            }
            
            
        }
        
        //return weather;
    }

}
