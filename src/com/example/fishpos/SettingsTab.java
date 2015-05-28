package com.example.fishpos;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

/* Fragment tab for keeping track of petty cash */ 
public class SettingsTab extends Fragment {
	
	private TextView tvDisplayDate;
	private EditText uacET;
	private DatePicker date_picker;
	private Button btnChangeDate;
	private Button setUAC;
	private SharedPreferences uacPrefs;
	private SharedPreferences datePrefs;
	private float uac;
 
	private int year;
	private int month;
	private int day;
 
	static final int DATE_DIALOG_ID = 999;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settingstab, container, false);
        
        uacPrefs = this.getActivity().getSharedPreferences("UAC", Context.MODE_PRIVATE);
        datePrefs = this.getActivity().getSharedPreferences("DATE", Context.MODE_PRIVATE);
        
        uac = uacPrefs.getFloat("UAC", 0.02f);
        uacET = (EditText) rootView.findViewById(R.id.uacSettings);
        
        tvDisplayDate = (TextView) rootView.findViewById(R.id.tvDate);
 		date_picker = (DatePicker) rootView.findViewById(R.id.date_picker);
 		btnChangeDate = (Button) rootView.findViewById(R.id.btnChangeDate);
 		setUAC = (Button) rootView.findViewById(R.id.setUACbtn);
 		
 		// Get UAC value and display in edittext
 		uacET.setText("" + uac);

		final Calendar calendar = Calendar.getInstance();

		year = datePrefs.getInt("YEAR", calendar.get(Calendar.YEAR));;
		month = datePrefs.getInt("MONTH", calendar.get(Calendar.YEAR));;
		day = datePrefs.getInt("DAY", calendar.get(Calendar.YEAR));;
		
		calendar.set(year, month, day);

		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = df.format(calendar.getTime());
		
		// set current date into textview
		tvDisplayDate.setText(currentDate);

		// set current date into Date Picker
		date_picker.init(year, month, day, null);
		
		
		setUAC.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				float newUAC = 0.0f;
				
				if(uacET.getText().toString().matches("")) {
					Toast.makeText(getActivity().getApplicationContext(),"Enter UAC value!",
							 Toast.LENGTH_SHORT).show();
				} else if(newUAC >= 0 && newUAC < 1) {
					newUAC = Float.parseFloat("" + uacET.getText());
					uacPrefs.edit().putFloat("UAC", newUAC).apply();
					Toast.makeText(getActivity().getApplicationContext(),"UAC = " + newUAC,
							 Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity().getApplicationContext(),"Invalid UAC value!",
							 Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		btnChangeDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				year = date_picker.getYear();
				month = date_picker.getMonth();
				day = date_picker.getDayOfMonth();
				Calendar c = Calendar.getInstance();
				c.set(year, month, day);
				
				
				datePrefs.edit().putInt("YEAR", year).apply();
				datePrefs.edit().putInt("MONTH", month).apply();
				datePrefs.edit().putInt("DAY", day).apply();
				
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		        String currentDate = df.format(c.getTime());
				
				// set current date into textview
				tvDisplayDate.setText(currentDate);

				// set selected date into Date Picker
				date_picker.init(year, month, day, null);

				Toast.makeText(getActivity().getApplicationContext(),"Date changed!",
						 Toast.LENGTH_SHORT).show();

			}

		});
        
        return rootView;
    }
}