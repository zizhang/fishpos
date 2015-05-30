package com.example.fishpos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;

/* Tab fragment for checking out a purchase 
 * Need to be tested more and check for illegal inputs
 * */
public class CheckoutTab extends Fragment implements OnClickListener {
	Button addButton, checkoutBtn, addOrderItemBtn;
	TextView bNameField, tvDate, tvTotalPrice, tvUAC, tvSubTotalPrice, tvLessUAC;
	Intent addCustIntent;
	Spinner custSpinner, fishTypeSpinner;
	ArrayList<Boat> custList;
	ArrayList<SpinnerFishType> fishTypeList;
	FishTypeAdapter fishTypeAdapter;
	CustomerAdapter custAdapter;
	String fishTypeOutput = "";
	String custOutput = "";
	String currentDate;
	SharedPreferences uacPrefs; // Store uac (fish tax) in shared preferences
	EditText etUAC, etPricePerPound, etTotalWeight, etReceiptNo;
	BigDecimal pricePerPound, totalWeight, totalPrice, amountPaid, uac, itemPrice, lessUAC;
	Order newSale;
	String chkoutMsg, receiptNo;
	long dateEpoch;
	String fishTypeSelected = "Sockeye RD.";
	ListView checkoutListFragment;
	TableLayout checkoutTable;
	
	SharedPreferences datePrefs;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.checkouttab, container, false);
        
        // Retrieve uac value
        uacPrefs = this.getActivity().getSharedPreferences("UAC", Context.MODE_PRIVATE);
        datePrefs = this.getActivity().getSharedPreferences("DATE", Context.MODE_PRIVATE);
        
        // Initialization
        pricePerPound = new BigDecimal("0");
        totalWeight = new BigDecimal("0");
        itemPrice = new BigDecimal("0");
        uac = new BigDecimal(uacPrefs.getFloat("UAC", 0.02f));
        totalPrice = new BigDecimal("0");
        lessUAC = new BigDecimal("0");
        receiptNo = "000000";
        
        amountPaid = totalPrice.subtract(totalPrice.multiply(uac));
        
        newSale = new Order();
        
        checkoutTable = (TableLayout) rootView.findViewById(R.id.checkoutTable);
        
        addOrderItemBtn = (Button) rootView.findViewById(R.id.addOrderItem);
        addButton = (Button) rootView.findViewById(R.id.addCustBtn);
        checkoutBtn = (Button) rootView.findViewById(R.id.checkout);
        custSpinner = (Spinner) rootView.findViewById(R.id.custSpinner);
        fishTypeSpinner = (Spinner) rootView.findViewById(R.id.fishTypeSpinner);
        //output = (TextView) rootView.findViewById(R.id.output);
        
        tvDate = (TextView) rootView.findViewById(R.id.currentDate);
        tvTotalPrice = (TextView) rootView.findViewById(R.id.totalPrice);
        tvUAC = (TextView) rootView.findViewById(R.id.uacLabel);
        tvSubTotalPrice = (TextView) rootView.findViewById(R.id.subTotalPrice);
        tvLessUAC = (TextView) rootView.findViewById(R.id.lessUAC);
        
        //etUAC = (EditText) rootView.findViewById(R.id.uac);
        etPricePerPound = (EditText) rootView.findViewById(R.id.pricePerPoundInput);
        etTotalWeight = (EditText) rootView.findViewById(R.id.weightInput);
        etReceiptNo = (EditText) rootView.findViewById(R.id.receiptNoInput);
        
        //etUAC.setText((uac.setScale(2, RoundingMode.HALF_EVEN)).toString());
        tvTotalPrice.setText(String.format("$ %.2f", amountPaid));
        tvSubTotalPrice.setText(String.format("$ %.2f", totalPrice));
        tvLessUAC.setText(String.format("$ %.2f", lessUAC));
        
        tvUAC.setText(String.format("UAC %.3f", uac.doubleValue()));
        
        // Date can be changed in the Settings Tab
        Calendar c = Calendar.getInstance();
        
        // Get date from Shared Pref
        
        int year = datePrefs.getInt("YEAR", c.get(Calendar.YEAR));
		int month = datePrefs.getInt("MONTH", c.get(Calendar.MONTH));
		int day = datePrefs.getInt("DAY", c.get(Calendar.DAY_OF_MONTH));
		
		c.set(year, month, day);
		
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        currentDate = df.format(c.getTime());
        dateEpoch = c.getTimeInMillis();
        
        tvDate.setText(currentDate);
        
        loadCustSpinnerData();
        loadFishTypeSpinnerData();
        
        //etUAC.addTextChangedListener(txtEditWatcherUAC);
        //etPricePerPound.addTextChangedListener(txtEditWatcherPricePerPound);
        //etTotalWeight.addTextChangedListener(txtEditWatcherTotalWeight);
        
        // Dropdown displays all boats from customer database
        custSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                 
                // Get selected row data to show on screen
                /*String customer    = ((TextView) v.findViewById(R.id.cust_view)).getText().toString();
                 String customer = "";
                custOutput = "" + customer;
                output.setText(custOutput + "\t" + fishTypeOutput);*/
            	
            	Boat boatSelected = (Boat) parentView.getItemAtPosition(position);
            	
            	//custOutput = "" + boatSelected.getBoatNo() + "-" + boatSelected.getBoatName();
                //output.setText(custOutput + "\t" + fishTypeOutput);
                
                newSale.setName(boatSelected.getBoatName());
                newSale.setNo(boatSelected.getBoatNo());
                
                 
                //Toast.makeText(getActivity().getBaseContext(),OutputMsg, Toast.LENGTH_LONG).show();
            }
 
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
 
        });
        
        fishTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                // your code here
            	
                // Get selected row data to show on screen
                //String FishType    = ((TextView) v.findViewById(R.id.fishType)).getText().toString();
                SpinnerFishType ft = (SpinnerFishType) parentView.getItemAtPosition(position);
            	//String FishType = parentView.getItemAtPosition(position).getText().toString();
                 
                //fishTypeOutput = "" + ft.getFishType();
                //output.setText(custOutput + "\t" + fishTypeOutput);
                
                //newSale.setFishType(ft.getFishType());
                fishTypeSelected = ft.getFishType();
                 
                //Toast.makeText(getActivity().getBaseContext(),OutputMsg, Toast.LENGTH_LONG).show();
            }
 
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
 
        });
        
        // Add a new customer (customer is a boat)
        addButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                
                addCustIntent = new Intent(activity, AddCustomer.class);
                startActivityForResult(addCustIntent, 101);
                //startActivity(addCustIntent);

                /*if (activity != null) {
                    Toast.makeText(activity, "test", Toast.LENGTH_LONG).show();
                }*/
            }

        });
        
        addOrderItemBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	
            	String ppp = etPricePerPound.getText().toString();
            	
            	String wt = etTotalWeight.getText().toString();
            	String receipt = etReceiptNo.getText().toString();
                
                // database handler
            	DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());
            	
            	if(!db.isUniqueReceipt(receipt)) {
            		db.close();
            		Toast.makeText(getActivity().getApplicationContext(), "Receipt # already exists!", Toast.LENGTH_SHORT).show();
                    return;
            	} else if (ppp.trim().length() > 0 && wt.trim().length() > 0 && receipt.trim().length() > 0) {
                	newSale.setReceiptNo(receipt);
                	//newSale.setPricePerPound(pricePerPound.doubleValue());
                    //newSale.setTotalWeight(totalWeight.doubleValue());
                	updateTotals();
                	newSale.addOrderItem(fishTypeSelected, pricePerPound.doubleValue(), totalWeight.doubleValue(), itemPrice.doubleValue());
                	
                	Log.i("DEBUG", "Order Item Size = " + (newSale.getAllOrderItems()).size());
                	
                	updateTable();
                	
                	etPricePerPound.setText("");
                	etTotalWeight.setText("");
                    
                    // TODO: Show order item in checkout list
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Invalid Entry Field (ReceiptNo/PricePerPound/Weight)!", Toast.LENGTH_SHORT).show();
                    return;
                }
                
            }

        });
        
        
        
        // Add sale to sales table
        // TODO: multiple fish types and weigh ins per sale. Need a slight redesign
        checkoutBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	String receipt = etReceiptNo.getText().toString();
                
                // database handler
            	DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());
            	
            	if(!db.isUniqueReceipt(receipt)) {
            		db.close();
            		Toast.makeText(getActivity().getApplicationContext(), "Receipt # already exists!", Toast.LENGTH_SHORT).show();
                    return;
            	} else if (receipt.trim().length() > 0) {
                	newSale.setReceiptNo(receipt);
                	//newSale.setPricePerPound(pricePerPound.doubleValue());
                    //newSale.setTotalWeight(totalWeight.doubleValue());
                    newSale.setAmountPaid(amountPaid.doubleValue());
                    newSale.setDate(dateEpoch);
                    chkoutMsg = newSale.toString();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Invalid Entry Field (ReceiptNo)!", Toast.LENGTH_SHORT).show();
                    return;
                }
            	
            	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
            			getActivity());
            	
            	// set title
    			alertDialogBuilder.setTitle("Checkout");
    			
    			// set dialog message
    			alertDialogBuilder
    				.setMessage(chkoutMsg)
    				.setCancelable(false)
    				.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog,int id) {
    						// if this button is clicked, just close
    						// the dialog box and do nothing
    						dialog.cancel();
    					}
    				})
    				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog,int id) {
    						DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());
    						
    						db.addOrder(newSale);
    	                    
    						// Empty input fields and zero values
    						// clean up for next new order
    	                    etTotalWeight.setText("");
    	                    etPricePerPound.setText("");
    	                    etReceiptNo.setText("");
    	                    
    	                    pricePerPound = new BigDecimal("0");
    	                    totalWeight = new BigDecimal("0");
    	                    itemPrice = new BigDecimal("0");
    	                    totalPrice = new BigDecimal("0");
    	                    amountPaid = new BigDecimal("0");
    	                    
    	                    checkoutTable.removeAllViews();
    	                    
    	                    // Textviews should display zeros
    	                    tvTotalPrice.setText("$" + amountPaid.setScale(2, RoundingMode.HALF_EVEN));
    	                    tvSubTotalPrice.setText("$" + totalPrice.setScale(2, RoundingMode.HALF_EVEN));
    	                    tvLessUAC.setText("$" + totalPrice.setScale(2, RoundingMode.HALF_EVEN));
    	                    
    	                    
    	                    db.close();
    					}
    				  });
     
    				// create alert dialog
    				AlertDialog alertDialog = alertDialogBuilder.create();
    				
    				// show it
    				alertDialog.show();
    				
    				TextView tvDialog = (TextView) alertDialog.findViewById(android.R.id.message);
    				tvDialog.setTextSize(40);
                
            }

        });
        
        /*etUAC.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					TextKeyListener.clear((etUAC).getText());
				}
			}
		});*/
        
        return rootView;
    }
    
    private void loadCustSpinnerData() {
    	
        // database handler
        DatabaseHandler db = new DatabaseHandler(getActivity());
 
        // Spinner Drop down elements
        custList = db.getAllBoats();
        
        custAdapter = new CustomerAdapter(getActivity().getBaseContext(), R.layout.spinner_customer, custList);
        
        // Set adapter to spinner
        custSpinner.setAdapter(custAdapter);
    }
    
    public void loadFishTypeSpinnerData() {
            SpinnerFishType fishType1 = new SpinnerFishType();
            SpinnerFishType fishType2 = new SpinnerFishType();
            SpinnerFishType fishType3 = new SpinnerFishType();
            SpinnerFishType fishType4 = new SpinnerFishType();
            SpinnerFishType fishType5 = new SpinnerFishType();
            
                 
          	/******* Firstly take data in model object ******/
            // Images taken from http://www.charterboatsbc.com/fish.html
            
            fishType1.setFishType("Sockeye RD.");
        	
            fishType2.setFishType("Pink RD.");
           
            fishType3.setFishType("Chums RD.");
            
            fishType4.setFishType("Red Spring DR.");
        	
            fishType5.setFishType("White Spring DR.");
            
            
            
            /******** Take Model Object in ArrayList **********/
            fishTypeList = new ArrayList<SpinnerFishType>();
            fishTypeList.add(fishType1);
            fishTypeList.add(fishType2);
            fishTypeList.add(fishType3);
            fishTypeList.add(fishType4);
            fishTypeList.add(fishType5);
            
            // Resources passed to adapter to get image
            Resources res = getResources(); 
             
            // Creating adapter for spinners
            fishTypeAdapter = new FishTypeAdapter(getActivity().getBaseContext(), R.layout.spinner_fishtypes, fishTypeList,res);
            
            // Set adapter to spinner
            fishTypeSpinner.setAdapter(fishTypeAdapter);
    }
    
    public void updateTable() {
    	ArrayList<OrderItem> allOrderItems = newSale.getAllOrderItems();
    	
    	/*
    	TableRow tr_head = new TableRow(getActivity());
        tr_head.setId(24);
        tr_head.setBackgroundColor(Color.DKGRAY);
        
        TextView headReceiptNo = new TextView(getActivity());
        headReceiptNo.setId(25);
        headReceiptNo.setText("");
        headReceiptNo.setTextColor(Color.WHITE);
        headReceiptNo.setTextSize(25);
        headReceiptNo.setPadding(5, 5, 5, 5);
        tr_head.addView(headReceiptNo);// add the column to the table row here
        
        checkoutTable.addView(tr_head);*/
        
    	
        //for(int i=0;i < allOrderItems.size(); i++)
       // {
    	if(allOrderItems.size() > 0) {
        	TableRow row = new TableRow(getActivity());
        	
        	row.setBackgroundColor(Color.LTGRAY);
        	
            String itemFishType = allOrderItems.get(allOrderItems.size()-1).getFishType();
            double itemPrice = allOrderItems.get(allOrderItems.size()-1).getPrice();
            
            TextView tvItemFishType = new TextView(getActivity());
            tvItemFishType.setTextColor(Color.BLACK);
            tvItemFishType.setTextSize(18);
            tvItemFishType.setText(itemFishType);
            
            TextView tvItemPrice = new TextView(getActivity());
            tvItemPrice.setTextColor(Color.BLACK);
            tvItemPrice.setTextSize(18);
            tvItemPrice.setText(String.format("$%.2f" , itemPrice));
            
            
            
            
            row.addView(tvItemFishType);
            row.addView(tvItemPrice);
            
            checkoutTable.addView(row);
       // }
    	}
    	
    	
    }
    
    public void updateTotals() {
    	pricePerPound = new BigDecimal("0" + etPricePerPound.getText());
    	totalWeight = new BigDecimal("0" + etTotalWeight.getText());
    	
    	//Log.i("Price Per Pound =", "" + pricePerPound.doubleValue());
    	//Log.i("Total weight =", "" + totalWeight.doubleValue());
		itemPrice = pricePerPound.multiply(totalWeight);
		totalPrice = totalPrice.add(itemPrice);
		//Log.i("Item Price =", "" + itemPrice.doubleValue());
		//Log.i("Total Price =", "" + totalPrice.doubleValue());
		
		lessUAC = totalPrice.multiply(uac);
		
		amountPaid = totalPrice.subtract(lessUAC);
		
		amountPaid.setScale(2, RoundingMode.HALF_EVEN);
		
		tvTotalPrice.setText("$" + amountPaid.setScale(2, RoundingMode.HALF_EVEN));
		tvSubTotalPrice.setText("$" + totalPrice.setScale(2, RoundingMode.HALF_EVEN));
        tvLessUAC.setText("$" + lessUAC.setScale(2, RoundingMode.HALF_EVEN));
    }

	@Override
	public void onClick(View v) {
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		loadCustSpinnerData();
		loadFishTypeSpinnerData();
	}
	
	/* UAC no longer set on this Tab, need to be set in Settings Tab */
	/*private final TextWatcher txtEditWatcherUAC = new TextWatcher() {
    	public void afterTextChanged(Editable s) {
			if(s.length() > 0) {
				uac = new BigDecimal("" + etUAC.getText());
				uacPrefs.edit().putFloat("UAC", Float.parseFloat("" + etUAC.getText())).apply();
			}
		  }
		  @Override
		  public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		      int arg3) {
		  }
		  @Override
		  public void onTextChanged(CharSequence s, int a, int b, int c) {
		  }
    };*/
    /*
    private final TextWatcher txtEditWatcherPricePerPound = new TextWatcher() {
    	public void afterTextChanged(Editable s) {
			if(s.length() > 0) {
				
				pricePerPound = new BigDecimal("0" + etPricePerPound.getText());
				itemPrice = pricePerPound.multiply(totalWeight);
				totalPrice = totalPrice.add(itemPrice);
				
				amountPaid = totalPrice.subtract(totalPrice.multiply(uac));
				
				amountPaid.setScale(2, RoundingMode.HALF_EVEN);
				
				tvTotalPrice.setText("$" + amountPaid.setScale(2, RoundingMode.HALF_EVEN));
			}
		  }
		  @Override
		  public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		      int arg3) {
		  }
		  @Override
		  public void onTextChanged(CharSequence s, int a, int b, int c) {
		  }
    };
    
    private final TextWatcher txtEditWatcherTotalWeight = new TextWatcher() {
    	public void afterTextChanged(Editable s) {
    		// Use BigDecimal to avoid rounding errors
			if(s.length() > 0) {
				totalWeight = new BigDecimal("0" + etTotalWeight.getText());
				itemPrice = pricePerPound.multiply(totalWeight);
				totalPrice = totalPrice.add(itemPrice);
				
				amountPaid = totalPrice.subtract(totalPrice.multiply(uac));
				
				amountPaid.setScale(2, RoundingMode.HALF_EVEN);
				
				tvTotalPrice.setText("$" + amountPaid.setScale(2, RoundingMode.HALF_EVEN));
			}
		  }
		  @Override
		  public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		      int arg3) {
		  }
		  @Override
		  public void onTextChanged(CharSequence s, int a, int b, int c) {
		  }
    };*/
 
}