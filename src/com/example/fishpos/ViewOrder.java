package com.example.fishpos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ViewOrder extends Activity {
	TextView tvOrderReceiptNo, tvOderDate, tvOrderBoatNo, tvOrderBoatName, tvOrderAmountPaid;
	TableLayout orderItemsTable;
	//ArrayList<Order> allOrdersListByBoat;
	//BigDecimal totalAmountPaidByBoat;
	String receiptNo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_order);
		
		tvOrderReceiptNo = (TextView) findViewById(R.id.orderReceiptNo);
		tvOderDate = (TextView) findViewById(R.id.orderDate);
		tvOrderBoatNo = (TextView) findViewById(R.id.orderBoatNo);
		tvOrderBoatName = (TextView) findViewById(R.id.orderBoatName);
		tvOrderAmountPaid = (TextView) findViewById(R.id.orderAmountPaid);
		orderItemsTable = (TableLayout) findViewById(R.id.orderItems);
		
		Bundle extras = getIntent().getExtras();
		
		if(extras != null) {
			receiptNo = extras.getString("orderReceiptNo");
		}
		
		retrieveOrderInfo();
		buildOrderItemTable();
	}
	
	public boolean retrieveOrderInfo() {
		Context context = getApplicationContext();
        // database handler
        DatabaseHandler db = new DatabaseHandler(context);
        
        Order order_to_view = db.getOrder(receiptNo);
        
        if(order_to_view.getReceiptNo().matches(receiptNo)) {
        	tvOrderReceiptNo.setText(order_to_view.getReceiptNo());
        	
        	// Date is in epoch long type. Convert to standard date format
        	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            String dateFormatted = sdf.format(new Date(order_to_view.getDate()));
        	
        	tvOderDate.setText(dateFormatted);
        	tvOrderBoatNo.setText(order_to_view.getNo());
        	tvOrderBoatName.setText(order_to_view.getName());
        	tvOrderAmountPaid.setText(String.format("$%.2f" , order_to_view.getAmountPaid()));
        }
		
		return true;
	}
	
	public boolean buildOrderItemTable() {
		Context context = getApplicationContext();
        // database handler
        DatabaseHandler db = new DatabaseHandler(context);
        
        ArrayList<OrderItem> allOrderItems = db.getOrderItems(receiptNo);;
        //Log.i("DEBUG", "OrderItemSize = " + allOrderItems.size());
        for(int i=0;i < allOrderItems.size(); i++) {
        	TableRow row = new TableRow(context);
        	
        	row.setBackgroundColor(Color.WHITE);
        	
            String itemFishType = allOrderItems.get(i).getFishType();
            
            BigDecimal pricePerPound = new BigDecimal("0" + allOrderItems.get(i).getPrice());
            BigDecimal totalWeight = new BigDecimal("0" + allOrderItems.get(i).getTotalWeight());
            
            BigDecimal totalItemPrice = pricePerPound.multiply(totalWeight);
            
            TextView tvItemFishType = new TextView(context);
            tvItemFishType.setTextColor(Color.BLACK);
            tvItemFishType.setTextSize(18);
            tvItemFishType.setText(itemFishType + "          ");
            
            TextView tvItemPricePound = new TextView(context);
            tvItemPricePound.setTextColor(Color.BLACK);
            tvItemPricePound.setTextSize(18);
            tvItemPricePound.setText("$" + pricePerPound.setScale(2, RoundingMode.HALF_EVEN));
            
            TextView tvMultiply = new TextView(context);
            tvMultiply.setTextColor(Color.BLACK);
            tvMultiply.setTextSize(18);
            tvMultiply.setText(" x ");
            
            TextView tvItemWeight = new TextView(context);
            tvItemWeight.setTextColor(Color.BLACK);
            tvItemWeight.setTextSize(18);
            tvItemWeight.setText(totalWeight.toBigInteger().toString() + " lbs");
            
            TextView tvEqual = new TextView(context);
            tvEqual.setTextColor(Color.BLACK);
            tvEqual.setTextSize(18);
            tvEqual.setText(" = ");
            
            TextView tvTotalItemPrice = new TextView(context);
            tvTotalItemPrice.setTextColor(Color.BLACK);
            tvTotalItemPrice.setTextSize(18);
            tvTotalItemPrice.setText("$" + totalItemPrice.setScale(2, RoundingMode.HALF_EVEN));
            
            
            
            
            row.addView(tvItemFishType);
            row.addView(tvItemPricePound);
            row.addView(tvMultiply);
            row.addView(tvItemWeight);
            row.addView(tvEqual);
            row.addView(tvTotalItemPrice);
            
            orderItemsTable.addView(row);
    	}
		
		return true;
	}
}
