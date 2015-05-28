package com.example.fishpos;

import java.util.ArrayList;

public class Order {
	String receiptNo;
	String bname;
	String bno;
	//String fishType;
	long date;
	double amountPaid;
	ArrayList<OrderItem> orderItemList;
	
	public Order() {
		
	}
	
	
	public Order(String bname, String bno, String fishType, double amountPaid) {
		this.receiptNo = "000000";
		this.date = 0;
		this.bname = bname;
		this.bno = bno;
		this.fishType = fishType;
		this.amountPaid = amountPaid;
		orderItemList = new ArrayList<OrderItem>();
	}
	
	public Order(long date, String bname, String bno, String fishType, double amountPaid) {
		this.receiptNo = "000000";
		this.date = date;
		this.bname = bname;
		this.bno = bno;
		this.fishType = fishType;
		this.amountPaid = amountPaid;
		orderItemList = new ArrayList<OrderItem>();
	}
	
	public Order(String receiptNo, long date, String bname, String bno, String fishType, double amountPaid) {
		this.receiptNo = receiptNo;
		this.date = date;
		this.bname = bname;
		this.bno = bno;
		this.fishType = fishType;
		this.amountPaid = amountPaid;
		orderItemList = new ArrayList<OrderItem>();
	}
	
	public long getDate() {
		return this.date;
	}
	
	public ArrayList<OrderItem> getAllOrderItems() {
		return this.orderItemList;
	}
	
	public boolean addOrderItem(double pricePerPound, double totalWeight) {
		OrderItem newOrderItem = new OrderItem(pricePerPound, totalWeight);
		
		if(pricePerPound > 0 && totalWeight > 0) {
			orderItemList.add(newOrderItem);
			return true;
		}
		
		return false;
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
	
	public String getReceiptNo() {
		return this.receiptNo;
	}
	
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	
	public String getNo() {
		return this.bno;
	}
	
	public void setNo(String bno) {
		this.bno = bno;
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
	
	@Override
    public String toString () {
        return "Boat: " + this.bname + "\n\nFish Type: " + this.fishType + "\n\nAmount Paid: $" + String.format("%.2f", this.amountPaid);
    }
}