package service;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import org.springframework.stereotype.Service;

import model.Wind;

/*
 * This class is an interface for the functionality of getting weather data
 * using an interface per requirements. It will be implemented in another class that
 * satisfies this functionality by calling the OpenWeatherMapAPI
 */

@Service
public interface WindService {
	/*
	 * This will serve as our cache. Depending on the usage of this application
	 * a DB might be a better option but for hundreds or a few thousand items
	 * in the cache a HashMap should be acceptable in RAM, an H2 internal DB might be another
	 * decent and easy solution
	 */
	
	public void clearCache();
	public Wind getWindData(String zip) throws IOException;
	public boolean isInCache(String zip);

}
