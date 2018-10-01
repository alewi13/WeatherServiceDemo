package model;

/*
 * Abstract decorator implementation, used to satisfy the req of implementing
 * the cache as a decorator
 */

public abstract class WindCacheDecorator implements WindCache {
	protected final WindCache windCache;
	
	public WindCacheDecorator(WindCache windCache) {
		this.windCache = windCache;
	}
	
	public boolean inCache(String zip) {
		// TODO Auto-generated method stub
		return this.windCache.inCache(zip);
	}

	
	public void addToCache(Wind wind) {
		// TODO Auto-generated method stub
		this.windCache.addToCache(wind);
	}


	public Wind getWind(String zip) {
		// TODO Auto-generated method stub
		return this.windCache.getWind(zip);
	}
	
	public void clear() {
		this.windCache.clear();
	}

}
