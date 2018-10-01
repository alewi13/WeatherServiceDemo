package controller;

import model.Wind;
import service.WindServiceOpenWeatherMap;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * This is a pretty standard spring MVC controller, handles web requests. In a real world example
 * I'd separate this into multiple controllers but considering there are only a few URL's I'm bundling
 * them into a single controller.
 */

@RestController
public class WeatherController {
    
	@Autowired
	private WindServiceOpenWeatherMap windService;
	
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String index() {
        return "Very boring landing page, give me service calls!";
    }
    
    
    /*
     * Gets a request for weather data based on IP address
     * Example: http://localhost:8080/api/v1/wind/89101
     */
    
    @RequestMapping(value = {"/api/v1/wind/{zip}"}, method=RequestMethod.GET)
    public Wind windByZip(@PathVariable("zip") String zip) throws IOException {
    	Wind wind = windService.getWindData(zip);
    	
    	/*
    	 * For a real world application I'd probably throw exceptions from the
    	 * model class and handle them here, maybe passing back a custom JSON
    	 * error message rather than setting the zip field to invalid.
    	 * Doing it this way for simplicity.
    	 */

    	if(wind.getZip()=="Invalid Zip") {
    		return wind;
    	}
    	
    	return wind;
    }
    
    /*
     * Would need to add authentication to this in the real world.
     * Wouldn't be good if anyone could clear the cache, could have DOS potential
     */
    
    @RequestMapping("/api/v1/wind/clearcache")
    public String clearCache() {
    	windService.clearCache();
    	
    	//Would use a StringBuilder if this wasn't so small
        String toReturn = "Cache cleared at epoch: ";
        toReturn += "\n" + new Date().getTime();
        return toReturn;
    }
    
}
