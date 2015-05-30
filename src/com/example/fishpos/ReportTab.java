package com.example.fishpos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.os.Bundle;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
 
public class ReportTab extends Fragment {
	TableLayout orderTable;
	ArrayList<Order> allOrdersList;
	BigDecimal totalAmountPaid;
	TextView tvTotalAmountPaid;
	Button dateBtn, boatBtn;
	Intent sortByDateIntent, sortByBoatIntent;
	EditText etBoatName;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reporttab, container, false);
        
        orderTable = (TableLayout) rootView.findViewById(R.id.orderTable);
        tvTotalAmountPaid = (TextView) rootView.findViewById(R.id.totalAmountPaid);
        dateBtn = (Button) rootView.findViewById(R.id.sortOrdersByDate);
        boatBtn = (Button) rootView.findViewById(R.id.searchBoat);
        etBoatName = (EditText) rootView.findViewById(R.id.searchByBoatName);
        
        
        buildTable();
        
        dateBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                
                sortByDateIntent = new Intent(activity, ReportByDate.class);
                startActivityForResult(sortByDateIntent, 101);
                //startActivity(addCustIntent);

                /*if (activity != null) {
                    Toast.makeText(activity, "test", Toast.LENGTH_LONG).show();
                }*/
            }

        });
        
        boatBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                
                sortByBoatIntent = new Intent(activity, ReportByBoat.class);
                sortByBoatIntent.putExtra("searchBoatName", "" + etBoatName.getText());
                TextKeyListener.clear((etBoatName).getText());
                startActivityForResult(sortByBoatIntent, 101);
                //startActivity(addCustIntent);

                /*if (activity != null) {
                    Toast.makeText(activity, "test", Toast.LENGTH_LONG).show();
                }*/
            }

        });
        
        return rootView;
    }
    
    private void buildTable() {
    	
        // database handler
        DatabaseHandler db = new DatabaseHandler(getActivity());
 
        // Spinner Drop down elements
        allOrdersList = db.getAllOrders();
        
        TableRow tr_head = new TableRow(getActivity());
        tr_head.setId(24);
        tr_head.setBackgroundColor(Color.DKGRAY);
        
        TextView headReceiptNo = new TextView(getActivity());
        headReceiptNo.setId(25);
        headReceiptNo.setText("Receipt #");
        headReceiptNo.setTextColor(Color.WHITE);
        headReceiptNo.setTextSize(25);
        headReceiptNo.setPadding(5, 5, 5, 5);
        tr_head.addView(headReceiptNo);// add the column to the table row here
        
        TextView headDate = new TextView(getActivity());
        headDate.setId(25);
        headDate.setText("Date");
        headDate.setTextColor(Color.WHITE);
        headDate.setTextSize(25);
        headDate.setPadding(5, 5, 5, 5);
        tr_head.addView(headDate);// add the column to the table row here
        
        TextView headBoatNo = new TextView(getActivity());
        headBoatNo.setId(25);
        headBoatNo.setText("CFV #");
        headBoatNo.setTextColor(Color.WHITE);
        headBoatNo.setTextSize(25);
        headBoatNo.setPadding(5, 5, 5, 5);
        tr_head.addView(headBoatNo);// add the column to the table row here
        
        TextView headBoatName = new TextView(getActivity());
        headBoatName.setId(25);
        headBoatName.setText("Boat Name");
        headBoatName.setTextColor(Color.WHITE);
        headBoatName.setTextSize(25);
        headBoatName.setPadding(5, 5, 5, 5);
        tr_head.addView(headBoatName);// add the column to the table row here
        /*
        TextView headFishType = new TextView(getActivity());
        headFishType.setId(25);
        headFishType.setText("Fish Type");
        headFishType.setTextColor(Color.WHITE);
        headFishType.setTextSize(25);
        headFishType.setPadding(5, 5, 5, 5);
        tr_head.addView(headFishType);// add the column to the table row here
        
        TextView headPrice = new TextView(getActivity());
        headPrice.setId(25);
        headPrice.setText("Price/lb");
        headPrice.setTextColor(Color.WHITE);
        headPrice.setTextSize(25);
        headPrice.setPadding(5, 5, 5, 5);
        tr_head.addView(headPrice);// add the column to the table row here
        
        TextView headWeight = new TextView(getActivity());
        headWeight.setId(25);
        headWeight.setText("Weight (lbs)");
        headWeight.setTextColor(Color.WHITE);
        headWeight.setTextSize(25);
        headWeight.setPadding(5, 5, 5, 5);
        tr_head.addView(headWeight);// add the column to the table row here
        */
        
        TextView headAmountPaid = new TextView(getActivity());
        headAmountPaid.setId(25);
        headAmountPaid.setText("Amount Paid");
        headAmountPaid.setTextColor(Color.WHITE);
        headAmountPaid.setTextSize(25);
        headAmountPaid.setPadding(5, 5, 5, 5);
        tr_head.addView(headAmountPaid);// add the column to the table row here
        
        orderTable.addView(tr_head);
        
        int count = 0;
        
        totalAmountPaid = new BigDecimal("0");
        
        BigDecimal amtPaid = new BigDecimal("0");
        
        for(int i=0;i < allOrdersList.size(); i++)
        {
        	TableRow row = new TableRow(getActivity());
        	
        	row.setBackgroundColor(Color.LTGRAY);
        	
            String receiptNo = allOrdersList.get(i).getReceiptNo();
            long date = allOrdersList.get(i).getDate();
            String name = allOrdersList.get(i).getName();
            String boatno = allOrdersList.get(i).getNo();
            //Log.i("debug", "CFV = " + boatno);
            //String fishType = allOrdersList.get(i).getFishType();
            //double pricePerPound = allOrdersList.get(i).getPricePerPound();
            //double weight = allOrdersList.get(i).getTotalWeight();
            double amountPaid = allOrdersList.get(i).getAmountPaid();
            
            amtPaid = new BigDecimal("" + allOrdersList.get(i).getAmountPaid());
            
            totalAmountPaid = totalAmountPaid.add(amtPaid);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            String dateFormatted = sdf.format(new Date(date));
            
            
            TextView tvReceiptNo = new TextView(getActivity());
            tvReceiptNo.setTextColor(Color.BLACK);
            tvReceiptNo.setTextSize(18);
            tvReceiptNo.setText("" + receiptNo);
            
            TextView tvDate = new TextView(getActivity());
            tvDate.setTextColor(Color.BLACK);
            tvDate.setTextSize(18);
            tvDate.setText(dateFormatted);
            
            TextView tvNo = new TextView(getActivity());
            tvNo.setTextColor(Color.BLACK);
            tvNo.setTextSize(18);
            tvNo.setText(boatno);
            
            TextView tvName = new TextView(getActivity());
            tvName.setTextColor(Color.BLACK);
            tvName.setTextSize(18);
            tvName.setText(name);
            /*TextView tvFishType = new TextView(getActivity());
            tvFishType.setTextColor(Color.BLACK);
            tvFishType.setTextSize(18);
            tvFishType.setText(fishType);
            TextView tvPricePerPound = new TextView(getActivity());
            tvPricePerPound.setTextColor(Color.BLACK);
            tvPricePerPound.setTextSize(18);
            tvPricePerPound.setText(String.format("$%.2f", pricePerPound));
            TextView tvWeight = new TextView(getActivity());
            tvWeight.setTextColor(Color.BLACK);
            tvWeight.setTextSize(18);
            tvWeight.setText(String.valueOf(weight));
            */
            TextView tvAmountPaid = new TextView(getActivity());
            tvAmountPaid.setTextColor(Color.BLACK);
            tvAmountPaid.setTextSize(18);
            tvAmountPaid.setText(String.format("$%.2f", amountPaid));
            
            row.addView(tvReceiptNo);
            row.addView(tvDate);
            row.addView(tvNo);
            row.addView(tvName);
            //row.addView(tvFishType);
            //row.addView(tvPricePerPound);
            //row.addView(tvWeight);
            row.addView(tvAmountPaid);
            
            orderTable.addView(row);
            count++;
        }
        
        totalAmountPaid.setScale(2, RoundingMode.HALF_EVEN);
        
        tvTotalAmountPaid.setText("Total Amount Paid \t $" + String.format("%.2f", totalAmountPaid.doubleValue()));
 
    }
 
}