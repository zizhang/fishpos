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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ViewOrder extends Activity {
	//TextView tvTotalAmountPaidByBoat;
	//TableLayout orderTableByBoat;
	//ArrayList<Order> allOrdersListByBoat;
	//BigDecimal totalAmountPaidByBoat;
	String receiptNo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_order);
		
		//tvTotalAmountPaidByBoat = (TextView) findViewById(R.id.totalAmountPaidByBoat);
		//orderTableByBoat = (TableLayout) findViewById(R.id.orderTableByBoat);
		
		Bundle extras = getIntent().getExtras();
		
		if(extras != null) {
			receiptNo = extras.getString("orderReceiptNo");
		}
		
	}
}
