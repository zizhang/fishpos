package com.example.fishpos;

import java.math.BigDecimal;

public class Order {
	int receiptNo;
	String bname;
	String fishType;
	long date;
	double pricePerPound;
	double totalWeight;
	double amountPaid;
	
	public Order() {
		
	}
	
	public Order(String bname, String fishType, double pricePerPound, double totalWeight, double amountPaid) {
		this.receiptNo = 0;
		this.date = 0;
		this.bname = bname;
		this.fishType = fishType;
		this.pricePerPound = pricePerPound;
		this.totalWeight = totalWeight;
		this.amountPaid = amountPaid;
	}
	
	public Order(int receiptNo, long date, String bname, String fishType, double pricePerPound, double totalWeight, double amountPaid) {
		this.receiptNo = receiptNo;
		this.date = date;
		this.bname = bname;
		this.fishType = fishType;
		this.pricePerPound = pricePerPound;
		this.totalWeight = totalWeight;
		this.amountPaid = amountPaid;
	}
	
	public long getDate() {
		return this.date;
	}
	
	public void setDate(long date) {
		this.date = date;
	}
	
	public double getAmountPaid() {
		return this.amountPaid;
	}
	
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
	
	public int getReceiptNo() {
		return this.receiptNo;
	}
	
	public void setReceiptNo(int receiptNo) {
		this.receiptNo = receiptNo;
	}
	
	public String getName() {
		return this.bname;
	}
	
	public void setName(String bname) {
		this.bname = bname;
	}
	
	public String getFishType() {
		return this.fishType;
	}
	
	public void setFishType(String fishType) {
		this.fishType = fishType;
	}
	
	public double getPricePerPound() {
		return this.pricePerPound;
	}
	
	public void setPricePerPound(double pricePerPound) {
		this.pricePerPound = pricePerPound;
	}
	
	public double getTotalWeight() {
		return this.totalWeight;
	}
	
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	
	@Override
    public String toString () {
        return "Boat: " + this.bname + "\n\nFish Type: " + this.fishType + "\n\nAmount Paid: $" + String.format("%.2f", this.amountPaid);
    }
}