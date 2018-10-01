package model;

import java.util.Date;

/*
 * Simple POJO to represent wind conditions for a particular zip
 * Code is self explanatory, just getters and setters for the various fields
 */
public class Wind {

	private String zip;
	private double speed;
	private double degrees;
	private double gust;
	private Date refreshed;
	
	public Date getRefreshed() {
		return refreshed;
	}

	public void setRefreshed(Date refreshed) {
		this.refreshed = refreshed;
	}

	public String getZip() {
		return zip;
	}
	
	/*
	 * Regex to check for valid zip format, satisfying the requirement of input validation.
	 * I could hard code or store valid zips in a text file but that would need to be regularly updated.
	 * Calling a service to check for valid zips is another options, however it doesn't help to avoid
	 * calls out which was the requirement. Maybe validate via an API with a cache as a best solution?
	 * 
	 * The model will populate the zip field with an error message and that will be passed back
	 * when you make a call to the service
	 */
	
	public void setZip(String zip) {
		if(!zip.matches("^[0-9]{5}(?:-[0-9]{4})?$")) {
			this.zip = "Invalid Zip";
		} else {
			this.zip = zip;
		}
		
	}
	
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getDegrees() {
		return degrees;
	}
	public void setDegrees(double degrees) {
		this.degrees = degrees;
	}
	public double getGust() {
		return gust;
	}
	public void setGust(double gust) {
		this.gust = gust;
	}
	

}
