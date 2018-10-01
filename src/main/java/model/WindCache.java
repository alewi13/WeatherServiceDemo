package model;

/*
 * Interface for the wind cache, used to satisfy the requirement of implementing
 * the cache using the decorator pattern
 */

public interface WindCache {
	public boolean inCache(String zip);
	public void addToCache(Wind wind);
	public Wind getWind(String zip);
	public void clear();

}
