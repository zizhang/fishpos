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
public class FishTypeAdapter extends ArrayAdapter<String>{
     
    private Context context;
    private ArrayList data;
    public Resources res;
    SpinnerFishType tempValues=null;
    LayoutInflater inflater;
     
    /*************  CustomAdapter Constructor *****************/
    public FishTypeAdapter(
                          Context activitySpinner, 
                          int textViewResourceId,   
                          ArrayList objects,
                          Resources resLocal
                         ) 
     {
        super(activitySpinner, textViewResourceId, objects);
         
        /********** Take passed values **********/
        context  = activitySpinner;
        data     = objects;
        res      = resLocal;
    
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
        View row = inflater.inflate(R.layout.spinner_fishtypes, parent, false);
         
        /***** Get each Model object from Arraylist ********/
        tempValues = null;
        tempValues = (SpinnerFishType) data.get(position);
         
        TextView label        = (TextView) row.findViewById(R.id.fishType);
        ImageView fishTypeImage = (ImageView) row.findViewById(R.id.image);
         
        
        // Set values for spinner each row 
        label.setText(tempValues.getFishType());
        fishTypeImage.setImageResource(tempValues.getImageFile());
             
 
        return row;
      }
 }