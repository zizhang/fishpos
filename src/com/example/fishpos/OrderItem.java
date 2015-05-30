package com.example.fishpos;

public class OrderItem {
	double pricePerPound;
	double totalWeight;
	String fishType;
	String receiptNo;
	double price;
	
	public OrderItem(String fishType, double pricePerPound, double totalWeight) {
		this.pricePerPound = pricePerPound;
		this.totalWeight = totalWeight;
		this.fishType = fishType;
		this.receiptNo = "000000";
	}
	
	public double getPricePerPound() {
		return this.pricePerPound;
	}
	
	public void setPricePerPound(double pricePerPound) {
		this.pricePerPound = pricePerPound;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getTotalWeight() {
		return this.totalWeight;
	}
	
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	
	public String getFishType() {
		return this.fishType;
	}
	
	public void setFishType(String fishType) {
		this.fishType = fishType;
	}
	
	public String getReceiptNo() {
		return this.receiptNo;
	}
	
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
}