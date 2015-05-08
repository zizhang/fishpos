package com.example.fishpos;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
/***** Adapter class extends with ArrayAdapter ******/
public class CustomerAdapter extends ArrayAdapter<Boat>{
     
    private Context context;
    private ArrayList data;
    public Resources res;
    Boat tempValues=null;
    LayoutInflater inflater;
     
    /*************  CustomAdapter Constructor *****************/
    public CustomerAdapter(
                          Context activitySpinner, 
                          int textViewResourceId,   
                          ArrayList objects
                         ) 
     {
        super(activitySpinner, textViewResourceId, objects);
         
        /********** Take passed values **********/
        context  = activitySpinner;
        data     = objects;
    
        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         
      }
 
    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
 
    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {
 
        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.spinner_customer, parent, false);
         
        /***** Get each Model object from Arraylist ********/
        tempValues = null;
        tempValues = (Boat) data.get(position);
         
        TextView label = (TextView) row.findViewById(R.id.cust_view);
         
        
        // Set values for spinner each row 
        label.setText("" + tempValues);
             
 
        return row;
      }
 }