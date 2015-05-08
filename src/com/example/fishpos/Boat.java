package com.example.fishpos;

public class Boat {
	String boatName;
	String boatNo;
	String cptName;
	
	public Boat() {
		
	}
	
	public Boat(String boatNo, String boatName, String cptName) {
		this.cptName = cptName;
		this.boatName = boatName;
		this.boatNo = boatNo;
	}
	
	public String getBoatNo() {
		return this.boatNo;
	}
	
	public void setBoatNo(String boatNo) {
		this.boatNo = boatNo;
	}
	
	public String getBoatName() {
		return this.boatName;
	}
	
	public void setBoatName(String name) {
		this.boatName = name;
	}
	
	public String getCptName() {
		return this.cptName;
	}
	
	public void setCptName(String cptName) {
		this.cptName = cptName;
	}
	
	@Override
    public String toString () {
        return this.boatNo + " - " + this.boatName + " : " + this.cptName;
    }
}
