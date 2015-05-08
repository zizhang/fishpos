package com.example.fishpos;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


public class MainActivity extends Activity {
	
	// Declare Tab Variable
    ActionBar.Tab Tab1, Tab2, Tab3, Tab4;
    Fragment checkouttab = new CheckoutTab();
    Fragment reporttab = new ReportTab();
    Fragment customertab = new CustomerTab();
    Fragment cashcountertab = new CashCounterTab();
    
    SharedPreferences prefs;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        prefs = this.getSharedPreferences("CASH_COUNTER", Context.MODE_PRIVATE);
 
        ActionBar actionBar = getActionBar();
 
        // Hide Actionbar Icon
        actionBar.setDisplayShowHomeEnabled(false);
 
        // Hide Actionbar Title
        actionBar.setDisplayShowTitleEnabled(false);
 
        // Create Actionbar Tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
        // Set Tab Icon and Titles
        Tab1 = actionBar.newTab().setText("Checkout");
        Tab2 = actionBar.newTab().setText("View Sales");
        Tab3 = actionBar.newTab().setText("View Customers");
        Tab4 = actionBar.newTab().setText("Cash Counter");
 
        // Set Tab Listeners
        Tab1.setTabListener(new TabListener(checkouttab));
        Tab2.setTabListener(new TabListener(reporttab));
        Tab3.setTabListener(new TabListener(customertab));
        Tab4.setTabListener(new TabListener(cashcountertab));
 
        // Add tabs to actionbar
        actionBar.addTab(Tab1);
        actionBar.addTab(Tab2);
        actionBar.addTab(Tab3);
        actionBar.addTab(Tab4);
    }
}
