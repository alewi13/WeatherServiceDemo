package model;

/*
 * Extremely simple cache example.I created this just to demonstrate the Decorator pattern, 
 * may also be useful for testing by setting up some static data.
 * 
 * A little weird to have something like this in a real production code base, for demonstration only.
 */
public class WindCacheSimple implements WindCache {
	
	public void WindCachesimple() {
		
	}

	@Override
	public boolean inCache(String zip) {
		// Left intentionally blank
		return false;
	}

	@Override
	public void addToCache(Wind wind) {
		// Left intentionally blank
		
	}

	@Override
	public Wind getWind(String zip) {
		// Left intentionally blank
		return null;
	}

	@Override
	public void clear() {
		// Left intentionally blank
		
	}

}
