package com.example.fishpos;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.app.Fragment;
import android.graphics.Color;
 
public class CustomerTab extends Fragment {
	TableLayout boatTable, crewTable;
	ArrayList<Boat> allBoatsList;
	ArrayList<Crew> allCrewsList;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.customertab, container, false);
        
        boatTable = (TableLayout) rootView.findViewById(R.id.boatTable);
        crewTable = (TableLayout) rootView.findViewById(R.id.crewTable);
        
        buildBoatTable();
        buildCrewTable();
        
        return rootView;
    }
    
    private void buildBoatTable() {
    	
        // database handler
        DatabaseHandler db = new DatabaseHandler(getActivity());
 
        // Spinner Drop down elements
        allBoatsList = db.getAllBoats();
        
        TableRow tr_head = new TableRow(getActivity());
        tr_head.setId(20);
        tr_head.setBackgroundColor(Color.DKGRAY);
        
        TextView headBoatNo = new TextView(getActivity());
        headBoatNo.setId(21);
        headBoatNo.setText("Boat Number");
        headBoatNo.setTextColor(Color.WHITE);
        headBoatNo.setTextSize(25);
        headBoatNo.setPadding(5, 5, 5, 5);
        tr_head.addView(headBoatNo);// add the column to the table row here
        
        TextView headBoatName = new TextView(getActivity());
        headBoatName.setId(22);
        headBoatName.setText("Boat Name");
        headBoatName.setTextColor(Color.WHITE);
        headBoatName.setTextSize(25);
        headBoatName.setPadding(5, 5, 5, 5);
        tr_head.addView(headBoatName);// add the column to the table row here

        TextView headCptName = new TextView(getActivity());
        headCptName.setId(23);// define id that must be unique
        headCptName.setText("Captain"); // set the text for the header 
        headCptName.setTextColor(Color.WHITE); // set the color
        headCptName.setTextSize(25);
        headCptName.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(headCptName); // add the column to the table row here
        
        boatTable.addView(tr_head);
        
        int count = 0;
        
        for(int i=0;i < allBoatsList.size(); i++)
        {
        	TableRow row = new TableRow(getActivity());
        	row.setBackgroundColor(Color.LTGRAY);
        	
            String boatNo = allBoatsList.get(i).getBoatNo();
            String boatName = allBoatsList.get(i).getBoatName();
            String cpt = allBoatsList.get(i).getCptName();
            TextView tvBoatNo = new TextView(getActivity());
            tvBoatNo.setText("" + boatNo);
            tvBoatNo.setTextColor(Color.BLACK);
            tvBoatNo.setTextSize(18);
            TextView tvBoatName = new TextView(getActivity());
            tvBoatName.setText(boatName);
            tvBoatName.setTextColor(Color.BLACK);
            tvBoatName.setTextSize(18);
            TextView tvCptName = new TextView(getActivity());
            tvCptName.setText(cpt);
            tvCptName.setTextColor(Color.BLACK);
            tvCptName.setTextSize(18);
            
            row.addView(tvBoatNo);
            row.addView(tvBoatName);
            row.addView(tvCptName);
            
            boatTable.addView(row);
            count++;
        }
 
    }
    
    private void buildCrewTable() {
    	
        // database handler
        DatabaseHandler db = new DatabaseHandler(getActivity());
 
        // Spinner Drop down elements
        allCrewsList = db.getAllCrews();
        
        TableRow tr_head = new TableRow(getActivity());
        tr_head.setId(24);
        tr_head.setBackgroundColor(Color.DKGRAY);
        
        TextView headCrewName = new TextView(getActivity());
        headCrewName.setId(25);
        headCrewName.setText("Crew Name");
        headCrewName.setTextColor(Color.WHITE);
        headCrewName.setTextSize(25);
        headCrewName.setPadding(5, 5, 5, 5);
        tr_head.addView(headCrewName);// add the column to the table row here

        TextView headCrewSIN = new TextView(getActivity());
        headCrewSIN.setId(26);// define id that must be unique
        headCrewSIN.setText("SIN"); // set the text for the header 
        headCrewSIN.setTextColor(Color.WHITE); // set the color
        headCrewSIN.setTextSize(25);
        headCrewSIN.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(headCrewSIN); // add the column to the table row here
        
        TextView headBoatNo = new TextView(getActivity());
        headBoatNo.setId(27);// define id that must be unique
        headBoatNo.setText("Boat Name"); // set the text for the header 
        headBoatNo.setTextColor(Color.WHITE); // set the color
        headBoatNo.setTextSize(25);
        headBoatNo.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(headBoatNo); // add the column to the table row here
        
        crewTable.addView(tr_head);
        
        int count = 0;
        
        Log.d("Crew", "size=" + allCrewsList.size());
        
        for(int i=0;i < allCrewsList.size(); i++)
        {
        	TableRow row = new TableRow(getActivity());
        	
        	row.setBackgroundColor(Color.LTGRAY);
        	
            String crewName = allCrewsList.get(i).getCrewName();
            String SIN = allCrewsList.get(i).getCrewSIN();
            String crewBoatName = allCrewsList.get(i).getCrewBoatName();
            TextView tvCrewName = new TextView(getActivity());
            tvCrewName.setText("" + crewName);
            tvCrewName.setTextColor(Color.BLACK);
            tvCrewName.setTextSize(18);
            TextView tvSIN = new TextView(getActivity());
            tvSIN.setText(SIN);
            tvSIN.setTextColor(Color.BLACK);
            tvSIN.setTextSize(18);
            TextView tvcrewBoatName = new TextView(getActivity());
            tvcrewBoatName.setText(crewBoatName);
            tvcrewBoatName.setTextColor(Color.BLACK);
            tvcrewBoatName.setTextSize(18);
            
            row.addView(tvCrewName);
            row.addView(tvSIN);
            row.addView(tvcrewBoatName);
            
            crewTable.addView(row);
            count++;
        }
 
    }
 
}