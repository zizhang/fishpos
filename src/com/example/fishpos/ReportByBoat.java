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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ReportByBoat extends Activity {
	TextView tvTotalAmountPaidByBoat;
	TableLayout orderTableByBoat;
	ArrayList<Order> allOrdersListByBoat;
	BigDecimal totalAmountPaidByBoat;
	String searchBoat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reportbyboat);
		
		tvTotalAmountPaidByBoat = (TextView) findViewById(R.id.totalAmountPaidByBoat);
		orderTableByBoat = (TableLayout) findViewById(R.id.orderTableByBoat);
		
		Bundle extras = getIntent().getExtras();
		
		if(extras != null) {
			searchBoat = extras.getString("searchBoatName");
		}
		
		buildTable();
	}
	

    
    private void buildTable() {
    	Context context = getApplicationContext();
        // database handler
        DatabaseHandler db = new DatabaseHandler(context);
 
        // Spinner Drop down elements
        allOrdersListByBoat = db.getAllOrdersByBoat(searchBoat);
        
        TableRow tr_head = new TableRow(context);
        tr_head.setId(24);
        tr_head.setBackgroundColor(Color.DKGRAY);
        
        TextView headReceiptNo = new TextView(context);
        headReceiptNo.setId(25);
        headReceiptNo.setText("Receipt #");
        headReceiptNo.setTextColor(Color.WHITE);
        headReceiptNo.setTextSize(25);
        headReceiptNo.setPadding(5, 5, 5, 5);
        tr_head.addView(headReceiptNo);// add the column to the table row here
        
        TextView headDate = new TextView(context);
        headDate.setId(25);
        headDate.setText("Date");
        headDate.setTextColor(Color.WHITE);
        headDate.setTextSize(25);
        headDate.setPadding(5, 5, 5, 5);
        tr_head.addView(headDate);// add the column to the table row here
        
        TextView headBoatName = new TextView(context);
        headBoatName.setId(25);
        headBoatName.setText("Boat Name");
        headBoatName.setTextColor(Color.WHITE);
        headBoatName.setTextSize(25);
        headBoatName.setPadding(5, 5, 5, 5);
        tr_head.addView(headBoatName);// add the column to the table row here
        
        TextView headFishType = new TextView(context);
        headFishType.setId(25);
        headFishType.setText("Fish Type");
        headFishType.setTextColor(Color.WHITE);
        headFishType.setTextSize(25);
        headFishType.setPadding(5, 5, 5, 5);
        tr_head.addView(headFishType);// add the column to the table row here
        
        TextView headPrice = new TextView(context);
        headPrice.setId(25);
        headPrice.setText("Price/lb");
        headPrice.setTextColor(Color.WHITE);
        headPrice.setTextSize(25);
        headPrice.setPadding(5, 5, 5, 5);
        tr_head.addView(headPrice);// add the column to the table row here
        
        TextView headWeight = new TextView(context);
        headWeight.setId(25);
        headWeight.setText("Weight (lbs)");
        headWeight.setTextColor(Color.WHITE);
        headWeight.setTextSize(25);
        headWeight.setPadding(5, 5, 5, 5);
        tr_head.addView(headWeight);// add the column to the table row here
        
        TextView headAmountPaid = new TextView(context);
        headAmountPaid.setId(25);
        headAmountPaid.setText("Amount Paid");
        headAmountPaid.setTextColor(Color.WHITE);
        headAmountPaid.setTextSize(25);
        headAmountPaid.setPadding(5, 5, 5, 5);
        tr_head.addView(headAmountPaid);// add the column to the table row here
        
        orderTableByBoat.addView(tr_head);
        
        int count = 0;
        
        totalAmountPaidByBoat = new BigDecimal("0");
        
        BigDecimal amtPaid = new BigDecimal("0");
        
        for(int i=0;i < allOrdersListByBoat.size(); i++)
        {
        	TableRow row = new TableRow(context);
        	
        	row.setBackgroundColor(Color.LTGRAY);
        	
            int receiptNo = allOrdersListByBoat.get(i).getReceiptNo();
            long date = allOrdersListByBoat.get(i).getDate();
            String name = allOrdersListByBoat.get(i).getName();
            String fishType = allOrdersListByBoat.get(i).getFishType();
            double pricePerPound = allOrdersListByBoat.get(i).getPricePerPound();
            double weight = allOrdersListByBoat.get(i).getTotalWeight();
            double amountPaid = allOrdersListByBoat.get(i).getAmountPaid();
            
            amtPaid = new BigDecimal("" + allOrdersListByBoat.get(i).getAmountPaid());
            
            totalAmountPaidByBoat = totalAmountPaidByBoat.add(amtPaid);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            String dateFormatted = sdf.format(new Date(date*1000));
            
            
            TextView tvReceiptNo = new TextView(context);
            tvReceiptNo.setTextColor(Color.BLACK);
            tvReceiptNo.setTextSize(18);
            tvReceiptNo.setText("" + receiptNo);
            TextView tvDate = new TextView(context);
            tvDate.setTextColor(Color.BLACK);
            tvDate.setTextSize(18);
            tvDate.setText(dateFormatted);
            TextView tvName = new TextView(context);
            tvName.setTextColor(Color.BLACK);
            tvName.setTextSize(18);
            tvName.setText(name);
            TextView tvFishType = new TextView(context);
            tvFishType.setTextColor(Color.BLACK);
            tvFishType.setTextSize(18);
            tvFishType.setText(fishType);
            TextView tvPricePerPound = new TextView(context);
            tvPricePerPound.setTextColor(Color.BLACK);
            tvPricePerPound.setTextSize(18);
            tvPricePerPound.setText(String.format("$%.2f", pricePerPound));
            TextView tvWeight = new TextView(context);
            tvWeight.setTextColor(Color.BLACK);
            tvWeight.setTextSize(18);
            tvWeight.setText(String.valueOf(weight));
            TextView tvAmountPaid = new TextView(context);
            tvAmountPaid.setTextColor(Color.BLACK);
            tvAmountPaid.setTextSize(18);
            tvAmountPaid.setText(String.format("$%.2f", amountPaid));
            
            row.addView(tvReceiptNo);
            row.addView(tvDate);
            row.addView(tvName);
            row.addView(tvFishType);
            row.addView(tvPricePerPound);
            row.addView(tvWeight);
            row.addView(tvAmountPaid);
            
            orderTableByBoat.addView(row);
            count++;
        }
        
        totalAmountPaidByBoat.setScale(2, RoundingMode.HALF_EVEN);
        
        
        tvTotalAmountPaidByBoat.setText("Total Amount Paid \t $" + String.format("%.2f", totalAmountPaidByBoat.doubleValue()));
    }
}
