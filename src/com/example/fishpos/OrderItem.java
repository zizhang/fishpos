package com.example.fishpos;

public class OrderItem {
	double pricePerPound;
	double totalWeight;
	String fishType;
	
	public OrderItem(double pricePerPound, double totalWeight) {
		this.pricePerPound = pricePerPound;
		this.totalWeight = totalWeight;
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
}