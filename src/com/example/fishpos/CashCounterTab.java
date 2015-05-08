package com.example.fishpos;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;

 
public class CashCounterTab extends Fragment {
	EditText ethundreds, etfifties, ettwenties, ettens, etfives, ettoonies, etloonies, etquarters, etdimes, etnickels;
	TextView tvHundreds, tvFifties, tvTwenties, tvTens, tvFives, tvToonies, tvLoonies, tvQuarters, tvDimes, tvNickels, tvTotalCash;
	SharedPreferences cashCounterPrefs;
	Button clearBtn, updateBtn, loadBtn;
	
	long hundreds, fifties, twenties, tens, fives, toonies, loonies, quarters, dimes, nickels;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cashcountertab, container, false);
        
        cashCounterPrefs = this.getActivity().getSharedPreferences("CASH_COUNTER", Context.MODE_PRIVATE);
        
        clearBtn = (Button) rootView.findViewById(R.id.clearFieldsCashCounter);
        updateBtn = (Button) rootView.findViewById(R.id.updateBtn);
        loadBtn = (Button) rootView.findViewById(R.id.loadBtn);
        
        ethundreds = (EditText) rootView.findViewById(R.id.hundreds);
		etfifties = (EditText) rootView.findViewById(R.id.fifties);
		ettwenties = (EditText) rootView.findViewById(R.id.twenties);
		ettens = (EditText) rootView.findViewById(R.id.tens);
		etfives = (EditText) rootView.findViewById(R.id.fives);
		ettoonies = (EditText) rootView.findViewById(R.id.toonies);
		etloonies = (EditText) rootView.findViewById(R.id.loonies);
		etquarters = (EditText) rootView.findViewById(R.id.quarters);
		etdimes = (EditText) rootView.findViewById(R.id.dimes);
		etnickels = (EditText) rootView.findViewById(R.id.nickels);
		
		tvHundreds = (TextView) rootView.findViewById(R.id.totalHundreds);
		tvFifties = (TextView) rootView.findViewById(R.id.totalFifties);
		tvTwenties = (TextView) rootView.findViewById(R.id.totalTwenties);
		tvTens = (TextView) rootView.findViewById(R.id.totalTens);
		tvFives = (TextView) rootView.findViewById(R.id.totalFives);
		tvToonies = (TextView) rootView.findViewById(R.id.totalToonies);
		tvLoonies = (TextView) rootView.findViewById(R.id.totalLoonies);
		tvQuarters = (TextView) rootView.findViewById(R.id.totalQuarters);
		tvDimes = (TextView) rootView.findViewById(R.id.totalDimes);
		tvNickels = (TextView) rootView.findViewById(R.id.totalNickels);
		
		tvTotalCash = (TextView) rootView.findViewById(R.id.totalCash);
		
		hundreds = cashCounterPrefs.getLong("Hundreds", 0);
		fifties = cashCounterPrefs.getLong("Fifties", 0);
		twenties = cashCounterPrefs.getLong("Twenties", 0);
		tens = cashCounterPrefs.getLong("Tens", 0);
		fives = cashCounterPrefs.getLong("Fives", 0);
		toonies = cashCounterPrefs.getLong("Toonies", 0);
		loonies = cashCounterPrefs.getLong("Loonies", 0);
		quarters = cashCounterPrefs.getLong("Quarters", 0);
		dimes = cashCounterPrefs.getLong("Dimes", 0);
		nickels = cashCounterPrefs.getLong("Nickels", 0);
		
		ethundreds.setText("" + hundreds);
		etfifties.setText("" + fifties);
		ettwenties.setText("" + twenties);
		ettens.setText("" + tens);
		etfives.setText("" + fives);
		ettoonies.setText("" + toonies);
		etloonies.setText("" + loonies);
		etquarters.setText("" + quarters);
		etdimes.setText("" + dimes);
		etnickels.setText("" + nickels);
		
		updateTotalCash();
        
		clearBtn.setOnClickListener(new OnClickListener() {
			@Override
            public void onClick(View v) {
				ethundreds.setText("0");
				etfifties.setText("0");
				ettwenties.setText("0");
				ettens.setText("0");
				etfives.setText("0");
				ettoonies.setText("0");
				etloonies.setText("0");
				etquarters.setText("0");
				etdimes.setText("0");
				etnickels.setText("0");
            }
		});
		
		updateBtn.setOnClickListener(new OnClickListener() {
			@Override
            public void onClick(View v) {
				cashCounterPrefs.edit().putLong("Hundreds", Long.parseLong("" + ethundreds.getText())).apply();
				cashCounterPrefs.edit().putLong("Fifties", Long.parseLong("" + etfifties.getText())).apply();
				cashCounterPrefs.edit().putLong("Twenties", Long.parseLong("" + ettwenties.getText())).apply();
				cashCounterPrefs.edit().putLong("Tens", Long.parseLong("" + ettens.getText())).apply();
				cashCounterPrefs.edit().putLong("Fives", Long.parseLong("" + etfives.getText())).apply();
				cashCounterPrefs.edit().putLong("Toonies", Long.parseLong("" + ettoonies.getText())).apply();
				cashCounterPrefs.edit().putLong("Loonies", Long.parseLong("" + etloonies.getText())).apply();
				cashCounterPrefs.edit().putLong("Quarters", Long.parseLong("" + etquarters.getText())).apply();
				cashCounterPrefs.edit().putLong("Dimes", Long.parseLong("" + etdimes.getText())).apply();
				cashCounterPrefs.edit().putLong("Nickels", Long.parseLong("" + etnickels.getText())).apply();
				
				Toast.makeText(getActivity(), "Cash Counter Saved!", Toast.LENGTH_SHORT).show();
            }
		});
		
		loadBtn.setOnClickListener(new OnClickListener() {
			@Override
            public void onClick(View v) {
				hundreds = cashCounterPrefs.getLong("Hundreds", 0);
				fifties = cashCounterPrefs.getLong("Fifties", 0);
				twenties = cashCounterPrefs.getLong("Twenties", 0);
				tens = cashCounterPrefs.getLong("Tens", 0);
				fives = cashCounterPrefs.getLong("Fives", 0);
				toonies = cashCounterPrefs.getLong("Toonies", 0);
				loonies = cashCounterPrefs.getLong("Loonies", 0);
				quarters = cashCounterPrefs.getLong("Quarters", 0);
				dimes = cashCounterPrefs.getLong("Dimes", 0);
				nickels = cashCounterPrefs.getLong("Nickels", 0);
				
				ethundreds.setText("" + hundreds);
				etfifties.setText("" + fifties);
				ettwenties.setText("" + twenties);
				ettens.setText("" + tens);
				etfives.setText("" + fives);
				ettoonies.setText("" + toonies);
				etloonies.setText("" + loonies);
				etquarters.setText("" + quarters);
				etdimes.setText("" + dimes);
				etnickels.setText("" + nickels);
            }
		});
		
		ethundreds.addTextChangedListener(txtEditWatcher);
		etfifties.addTextChangedListener(txtEditWatcher);
		ettwenties.addTextChangedListener(txtEditWatcher);
		ettens.addTextChangedListener(txtEditWatcher);
		etfives.addTextChangedListener(txtEditWatcher);
		ettoonies.addTextChangedListener(txtEditWatcher);
		etloonies.addTextChangedListener(txtEditWatcher);
		etquarters.addTextChangedListener(txtEditWatcher);
		etdimes.addTextChangedListener(txtEditWatcher);
		etnickels.addTextChangedListener(txtEditWatcher);
        
		ethundreds.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					TextKeyListener.clear((ethundreds).getText());
				}
			}
		});
		
		etfifties.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					TextKeyListener.clear((etfifties).getText());
				}
			}
		});
		
		ettwenties.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					TextKeyListener.clear((ettwenties).getText());
				}
			}
		});
		
		ettens.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					TextKeyListener.clear((ettens).getText());
				}
			}
		});
		
		etfives.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					TextKeyListener.clear((etfives).getText());
				}
			}
		});
		
		ettoonies.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					TextKeyListener.clear((ettoonies).getText());
				}
			}
		});
		
		etloonies.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					TextKeyListener.clear((etloonies).getText());
				}
			}
		});
		
		etquarters.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					TextKeyListener.clear((etquarters).getText());
				}
			}
		});
		
		etdimes.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					TextKeyListener.clear((etdimes).getText());
				}
			}
		});
		
		etnickels.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					TextKeyListener.clear((etnickels).getText());
				}
			}
		});
        
        return rootView;
    }
    
    public void updateTotalCash() {
    	double total100, total50, total20, total10, total5, totalToonies, totalLoonies, totalQuarters, totalDimes, totalNickels;
    	
    	double total;
    	
    	if(ethundreds.getText().length() > 0) {
    		total100 = 100 * Double.parseDouble("" + ethundreds.getText());
    	} else {
    		total100 = 0;
    	}
    	
    	if(etfifties.getText().length() > 0) {
    		total50 = 50 * Double.parseDouble("" + etfifties.getText());
    	} else {
    		total50 = 0;
    	}
    	
    	if(ettwenties.getText().length() > 0) {
    		total20 = 20 * Double.parseDouble("" + ettwenties.getText());
    	} else {
    		total20 = 0;
    	}
    	
    	if(ettens.getText().length() > 0) {
    		total10 = 10 * Double.parseDouble("" + ettens.getText());
    	} else {
    		total10 = 0;
    	}
    	
    	if(etfives.getText().length() > 0) {
    		total5 = 5 * Double.parseDouble("" + etfives.getText());
    	} else {
    		total5 = 0;
    	}
    	
    	if(ettoonies.getText().length() > 0) {
    		totalToonies = 2 * Double.parseDouble("" + ettoonies.getText());
    	} else {
    		totalToonies = 0;
    	}
    	
    	if(etloonies.getText().length() > 0) {
    		totalLoonies = 1* Double.parseDouble("" + etloonies.getText());
    	} else {
    		totalLoonies = 0;
    	}
    	
    	if(etquarters.getText().length() > 0) {
    		totalQuarters = 0.25 * Double.parseDouble("" + etquarters.getText());
    	} else {
    		totalQuarters = 0;
    	}
    	
    	if(etdimes.getText().length() > 0) {
    		totalDimes = 0.1 * Double.parseDouble("" + etdimes.getText());
    	} else {
    		totalDimes = 0;
    	}
    	
    	if(etnickels.getText().length() > 0) {
    		totalNickels = 0.05 * Double.parseDouble("" + etnickels.getText());
    	} else {
    		totalNickels = 0;
    	}
    	
    	total = total100 + total50 + total20 + total10 + total5 + totalToonies + totalLoonies + totalQuarters + totalDimes + totalNickels;
    	
    	tvHundreds.setText(String.format("$ %.2f", total100));
    	tvFifties.setText(String.format("$ %.2f", total50));
    	tvTwenties.setText(String.format("$ %.2f", total20));
    	tvTens.setText(String.format("$ %.2f", total10));
    	tvFives.setText(String.format("$ %.2f", total5));
    	tvToonies.setText(String.format("$ %.2f", totalToonies));
    	tvLoonies.setText(String.format("$ %.2f", totalLoonies));
    	tvQuarters.setText(String.format("$ %.2f", totalQuarters));
    	tvDimes.setText(String.format("$ %.2f", totalDimes));
    	tvNickels.setText(String.format("$ %.2f", totalNickels));
    	
    	tvTotalCash.setText(String.format("$ %.2f", total));
    }
    
    private final TextWatcher  txtEditWatcher = new TextWatcher() {
    	public void afterTextChanged(Editable s) {
		    // TODO Auto-generated method stub
			if(s.length() > 0) {
				updateTotalCash();
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