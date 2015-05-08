package com.example.fishpos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
 
public class CheckoutTab extends Fragment implements OnClickListener {
	Button addButton, checkoutBtn;
	TextView bNameField, tvDate, tvTotalPrice;
	Intent addCustIntent;
	Spinner custSpinner, fishTypeSpinner;
	ArrayList<Boat> custList;
	ArrayList<SpinnerFishType> fishTypeList;
	FishTypeAdapter fishTypeAdapter;
	CustomerAdapter custAdapter;
	String fishTypeOutput = "";
	String custOutput = "";
	SharedPreferences uacPrefs;
	EditText etUAC, etPricePerPound, etTotalWeight;
	BigDecimal pricePerPound, totalWeight, totalPrice, amountPaid, uac;
	Order newSale;
	String chkoutMsg;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.checkouttab, container, false);
        
        uacPrefs = this.getActivity().getSharedPreferences("CASH_COUNTER", Context.MODE_PRIVATE);
        
        pricePerPound = new BigDecimal("0");
        totalWeight = new BigDecimal("0");
        uac = new BigDecimal(uacPrefs.getFloat("UAC", 0.02f));
        totalPrice = pricePerPound.multiply(totalWeight);
        
        amountPaid = totalPrice.subtract(totalPrice.multiply(uac));
        
        newSale = new Order();
        
        addButton = (Button) rootView.findViewById(R.id.addCustBtn);
        checkoutBtn = (Button) rootView.findViewById(R.id.checkout);
        custSpinner = (Spinner) rootView.findViewById(R.id.custSpinner);
        fishTypeSpinner = (Spinner) rootView.findViewById(R.id.fishTypeSpinner);
        //output = (TextView) rootView.findViewById(R.id.output);
        
        tvDate = (TextView) rootView.findViewById(R.id.currentDate);
        tvTotalPrice = (TextView) rootView.findViewById(R.id.totalPrice);
        
        etUAC = (EditText) rootView.findViewById(R.id.uac);
        etPricePerPound = (EditText) rootView.findViewById(R.id.pricePerPoundInput);
        etTotalWeight = (EditText) rootView.findViewById(R.id.weightInput);
        
        etUAC.setText((uac.setScale(2, RoundingMode.HALF_EVEN)).toString());
        tvTotalPrice.setText(String.format("$ %.2f", totalPrice));
        
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = df.format(c.getTime());
        
        tvDate.setText(currentDate);
        
        loadCustSpinnerData();
        loadFishTypeSpinnerData();
        
        etUAC.addTextChangedListener(txtEditWatcherUAC);
        etPricePerPound.addTextChangedListener(txtEditWatcherPricePerPound);
        etTotalWeight.addTextChangedListener(txtEditWatcherTotalWeight);
        
        
        custSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                // your code here
                 
                // Get selected row data to show on screen
                /*String customer    = ((TextView) v.findViewById(R.id.cust_view)).getText().toString();
                 String customer = "";
                custOutput = "" + customer;
                output.setText(custOutput + "\t" + fishTypeOutput);*/
            	
            	Boat boatSelected = (Boat) parentView.getItemAtPosition(position);
            	
            	//custOutput = "" + boatSelected.getBoatNo() + "-" + boatSelected.getBoatName();
                //output.setText(custOutput + "\t" + fishTypeOutput);
                
                newSale.setName(boatSelected.getBoatName());
                
                 
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
                
                newSale.setFishType(ft.getFishType());
                 
                //Toast.makeText(getActivity().getBaseContext(),OutputMsg, Toast.LENGTH_LONG).show();
            }
 
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
 
        });
        
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
        
        checkoutBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	
            	String ppp = etPricePerPound.getText().toString();
            	String wt = etTotalWeight.getText().toString();
                
                // database handler
                
                if (ppp.trim().length() > 0 && wt.trim().length() > 0 ) {
                	newSale.setPricePerPound(pricePerPound.doubleValue());
                    newSale.setTotalWeight(totalWeight.doubleValue());
                    newSale.setAmountPaid(amountPaid.doubleValue());
                    chkoutMsg = newSale.toString();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Invalid Entry Field (PricePerPound/Weight)!", Toast.LENGTH_SHORT).show();
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
    	                    
    	                    etTotalWeight.setText("");
    	                    etPricePerPound.setText("");
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
        
        etUAC.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					TextKeyListener.clear((etUAC).getText());
				}
			}
		});
        
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
                 
          	/******* Firstly take data in model object ******/
            // Images taken from http://www.charterboatsbc.com/fish.html
            
            fishType1.setFishType("Sockeye");
            fishType1.setImageFile(R.drawable.sockeye);
        	
            fishType2.setFishType("Pink");
            fishType2.setImageFile(R.drawable.pink_salmon);
        	
            fishType3.setFishType("Spring");
            fishType3.setImageFile(R.drawable.spring);
            
            fishType4.setFishType("Chum");
            fishType4.setImageFile(R.drawable.chum);
            
            /******** Take Model Object in ArrayList **********/
            fishTypeList = new ArrayList<SpinnerFishType>();
            fishTypeList.add(fishType1);
            fishTypeList.add(fishType2);
            fishTypeList.add(fishType3);
            fishTypeList.add(fishType4);
            
            // Resources passed to adapter to get image
            Resources res = getResources(); 
             
            // Creating adapter for spinners
            fishTypeAdapter = new FishTypeAdapter(getActivity().getBaseContext(), R.layout.spinner_fishtypes, fishTypeList,res);
            
            // Set adapter to spinner
            fishTypeSpinner.setAdapter(fishTypeAdapter);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		loadCustSpinnerData();
		loadFishTypeSpinnerData();
	}
	
	private final TextWatcher txtEditWatcherUAC = new TextWatcher() {
    	public void afterTextChanged(Editable s) {
		    // TODO Auto-generated method stub
			if(s.length() > 0) {
				uac = new BigDecimal("" + etUAC.getText());
				uacPrefs.edit().putFloat("UAC", Float.parseFloat("" + etUAC.getText())).apply();
			}
		  }
		  @Override
		  public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		      int arg3) {
		    // TODO Auto-generated method stub
		  }
		  @Override
		  public void onTextChanged(CharSequence s, int a, int b, int c) {
		    // TODO Auto-generated method stub
		  }
    };
    
    private final TextWatcher txtEditWatcherPricePerPound = new TextWatcher() {
    	public void afterTextChanged(Editable s) {
		    // TODO Auto-generated method stub
			if(s.length() > 0) {
				pricePerPound = new BigDecimal("" + etPricePerPound.getText());
				totalPrice = pricePerPound.multiply(totalWeight);
				
				amountPaid = totalPrice.subtract(totalPrice.multiply(uac));
				
				amountPaid.setScale(2, RoundingMode.HALF_EVEN);
				
				tvTotalPrice.setText("$" + amountPaid.setScale(2, RoundingMode.HALF_EVEN));
			}
		  }
		  @Override
		  public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		      int arg3) {
		    // TODO Auto-generated method stub
		  }
		  @Override
		  public void onTextChanged(CharSequence s, int a, int b, int c) {
		    // TODO Auto-generated method stub
		  }
    };
    
    private final TextWatcher txtEditWatcherTotalWeight = new TextWatcher() {
    	public void afterTextChanged(Editable s) {
		    // TODO Auto-generated method stub
			if(s.length() > 0) {
				totalWeight = new BigDecimal("" + etTotalWeight.getText());
				totalPrice = pricePerPound.multiply(totalWeight);
				
				amountPaid = totalPrice.subtract(totalPrice.multiply(uac));
				
				amountPaid.setScale(2, RoundingMode.HALF_EVEN);
				
				tvTotalPrice.setText("$" + amountPaid.setScale(2, RoundingMode.HALF_EVEN));
			}
		  }
		  @Override
		  public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		      int arg3) {
		    // TODO Auto-generated method stub
		  }
		  @Override
		  public void onTextChanged(CharSequence s, int a, int b, int c) {
		    // TODO Auto-generated method stub
		  }
    };
 
}