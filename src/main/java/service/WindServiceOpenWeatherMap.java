package service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Wind;
import model.WindCache;
import model.WindCacheDecorator;
import model.WindCacheHashMap;
import model.WindCacheSimple;

/*
 * Implementation of the WindService, implements the WindService interface per specs
 * 
 * 
 * Please correct me if I'm wrong but I believe this is thread safe by design.
 */


@Service
public class WindServiceOpenWeatherMap implements WindService {
	
	//Showing that I've used the decorator pattern and can change the type dynamically
	WindCache windCache = new WindCacheHashMap(new WindCacheSimple());
	
	/*
	 * 1000 ms, 60 secs in a minute and 15 minutes
	 * Would be easy to implement the timing in the model, that may be better design.
	 * Pass both the zip and time and see if there is a relevant record in that time frame.
	 */
	static final float CACHE_MINUTES = 15;
	static final float CACHE_TIME = 1000 * 60 * CACHE_MINUTES;

	
	@Override
	public Wind getWindData(String zip) throws IOException {
		boolean inCache = isInCache(zip);
		Date curTime = new Date();
		Wind wind;
		
		if(inCache) {
			System.out.println("Time diff is: " + (curTime.getTime() - windCache.getWind(zip).getRefreshed().getTime()));
		}
		//We have a cache hit and its recent
		if(inCache && curTime.getTime() - windCache.getWind(zip).getRefreshed().getTime() < CACHE_TIME) {
			return windCache.getWind(zip);
		} else {
			String sURL = "http://api.openweathermap.org/data/2.5/weather?zip=89118&APPID=e9a775ed5028d8dfdd653a0bd5b9f544";

		    // Connect to the URL using java's native library
		    URL url = new URL(sURL);
		    URLConnection request = url.openConnection();
			request.connect();
			
			//Get the timestamp, used for the cache
			Date pullTime = new Date();
			
		    // Convert to a JSON object to print data
		    JsonParser jp = new JsonParser();
		    JsonElement root = null;
			
			root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
			
			//Convert the input stream to a json element
		    JsonObject rootobj = root.getAsJsonObject(); 
		    JsonObject weatherObject = rootobj.get("wind").getAsJsonObject();
		    
		    
		    /*
		     * While coding I had some errors with setting these values.
		     * It seems that if something is zero ie. no guest, the API doesn't return
		     * anything for that field, hence the error checking.
		     */
		    String speed = null;
		    String degrees = null;
		    String gust = null;

		    if(weatherObject.has("speed")) {
		    	speed = weatherObject.get("speed").toString();
		    }
		    if(weatherObject.has("deg")) {
		    	degrees = weatherObject.get("deg").toString();
		    }
		    if(weatherObject.has("gust")) {
		    	gust = weatherObject.get("gust").toString();
		    }
		    
		    /*
		     * I'm assuming if the fields come back blank it's because they were 0. I haven't verified
		     * this but doing some google searches it appears there are some issues with the weather API.
		     * It might be a better design decision for me to return a null string.
		     */
		    
		    if(gust==null) {
		    	gust="0.0";
		    }
		    if(degrees==null) {
		    	degrees="0.0";
		    }
		    if(speed==null) {
		    	degrees="0.0";
		    }
		    	
		    if(inCache) {
		    	wind = windCache.getWind(zip);
		    	wind.setRefreshed(pullTime);
		    } else {
		    	wind = new Wind();
			    wind.setZip(zip);
			    wind.setDegrees(Double.parseDouble(degrees));
			    wind.setGust(Double.parseDouble(gust));
			    wind.setSpeed(Double.parseDouble(speed));
			    wind.setRefreshed(pullTime);
			    windCache.addToCache(wind);
		    }
		    
		    
		    return wind;
		}
	}


	@Override
	public void clearCache() {
		windCache.clear();
		
	}


	@Override
	public boolean isInCache(String zip) {
		// TODO Auto-generated method stub
		return windCache.inCache(zip);
	}

	

}
