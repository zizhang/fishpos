package com.example.fishpos;

// Class for a crewman of a boat
// Each crewman has a name and SIN #
public class Crew {
	int cid;
	String cName;
	String cSIN;
	String bname;
	
	public Crew() {
		
	}
	
	public Crew(int cid, String cName, String cSIN) {
		this.cid = cid;
		this.cName = cName;
		this.cSIN = cSIN;
		this.bname = "";
	}
	
	public Crew(String cName, String cSIN) {
		cid = 0;
		this.cName = cName;
		this.cSIN = cSIN;
		this.bname = "";
	}
	
	public String getCrewName() {
		return this.cName;
	}
	
	public void setCrewName(String cName) {
		this.cName = cName;
	}
	
	public String getCrewSIN() {
		return this.cSIN;
	}
	
	public void setCrewSIN(String cSIN) {
		this.cSIN = cSIN;
	}
	
	public String getCrewBoatName() {
		return this.bname;
	}
	
	public void setCrewBoatName(String bname) {
		this.bname = bname;
	}
	
	@Override
    public String toString () {
        return this.cid + ":" + this.cName + " - " + this.cSIN;
    }
}
