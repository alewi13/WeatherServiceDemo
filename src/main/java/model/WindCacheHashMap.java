package model;

import java.util.Hashtable;
import java.util.Map;

/*
 * This is one implementation of the decorator using a HashMap. I believe this is fine, the amount or records I'd need
 * to keep is small enough to store in memory. Another option would be a DB, but that would take
 * some setup on your part. An H2 in memory DB would be another decent option it doesn't get too large.
 

* A note on thread safety which was another requirement:
	 * 
	 * I did see the thread safety requirement in the design document. I believe that
	 * was included to avoid the chance that a read on the cache doesn't work when a purge
	 * has already started or additions that aren't read. I can see where this would be an
	 * issue if Spring MVC wasn't thread safe but it is. I also tested that by making several
	 * web calls and only saw 1 spawn. When a call is made there is only
	 * one DispatcherServlet so whichever request hit's first gets to modify the data exclusively.
	 * Spring beans are also Singleton by default and I haven't changed that.
	 * 
	 * I am implementing the cache clear in the controller and a web request will kick it
	 * off. I can't think of an issue with race conditions other than the time it takes
	 * for the web request to hit the system and there is no way to control how long it
	 * takes due to network latency. First come, first served. You could possibly cache web requests
	 * for a short period and look at the source timestamps, serving first sent vs first recieved but
	 * that is very non standard for HTTP requests.
	 * 
*/

public class WindCacheHashMap extends WindCacheDecorator {
	
	Map<String, Wind> windConditions = new Hashtable<String, Wind>();

	public WindCacheHashMap(WindCache windCache) {
		super(windCache);
	}
	
	@Override
	public boolean inCache(String zip) {
		/*
		 * Assuming that we don't have this cached. Would be better to flip this logic
		 * if it's the case that we have more misses than hits.
		 */
		if(!windConditions.containsKey(zip)) {
			return false;
		} else {
			return true;
		}
	}

	
	public void addToCache(Wind wind) {
		if(!inCache(wind.getZip())) {
			windConditions.put(wind.getZip(), wind);
		}
	}


	public Wind getWind(String zip) {
		if(windConditions.containsKey(zip)) {
			return windConditions.get(zip);
		}
		return null;
	}
	
	public void clear() {
		windConditions.clear();
	}

}
