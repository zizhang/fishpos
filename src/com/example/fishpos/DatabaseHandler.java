package com.example.fishpos;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "fishpos";
 
    // Boat table name
    private static final String TABLE_BOAT = "Boat";
 
    // Boat Table Columns names
    private static final String KEY_BOATNO = "boatNo";
    private static final String KEY_BOAT_NAME = "boatName";
    private static final String KEY_CAPTAIN_NAME = "cptName";
    
    // Crew table name
    private static final String TABLE_CREW = "Crew";
 
    // Crew Table Columns names
    private static final String KEY_CID = "cid";
    private static final String KEY_CREW_NAME = "cName";
    private static final String KEY_SIN = "cSIN";
    
    // Orders table
    private static final String TABLE_ORDERS = "Sales";
    
    // Order Table Columns names
    private static final String KEY_RECEIPT_NO = "receiptNo";
    private static final String KEY_DATE = "saleDate";
    private static final String KEY_BNO = "bNo";
    private static final String KEY_NAME = "bname";
    private static final String KEY_AMOUNT_PAID = "amountPaid";
    
    // Order Item table
    private static final String TABLE_ORDER_ITEM = "OrderItem";
    
    // Order Item Table Columns names
    private static final String KEY_ORDER_ITEM_ID = "orderItemID";
    private static final String KEY_RECEIPT_NO_ORDITEM = "receiptNo";
    private static final String KEY_FISH_TYPE = "fishType";
    private static final String KEY_PRICE = "price";
    private static final String KEY_WEIGHT = "weight";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOATS_TABLE = "CREATE TABLE " + TABLE_BOAT + "("
                + KEY_BOATNO + " TEXT PRIMARY KEY," + KEY_BOAT_NAME + " TEXT NOT NULL," + KEY_CAPTAIN_NAME + " TEXT NOT NULL,"
                + " FOREIGN KEY(" + KEY_CAPTAIN_NAME + ") REFERENCES " + TABLE_CREW + "(" + KEY_CREW_NAME + "))";
        
        String CREATE_CREWS_TABLE = "CREATE TABLE " + TABLE_CREW + "("
        		+ KEY_CID + " INTEGER PRIMARY KEY," + KEY_CREW_NAME + " TEXT NOT NULL," + KEY_SIN + " TEXT," + KEY_BOATNO + " TEXT NOT NULL,"
        		+ " FOREIGN KEY(" + KEY_BOATNO + ") REFERENCES " + TABLE_BOAT + "(" + KEY_BOATNO + "))";
        
        
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "(" 
        		+ KEY_RECEIPT_NO + " TEXT PRIMARY KEY," + KEY_DATE + " INTEGER NOT NULL DEFAULT (strftime('%s', 'now'))," + KEY_BNO + " TEXT," + KEY_NAME + " TEXT," + KEY_AMOUNT_PAID + " REAL" +  ")";
        
        String CREATE_ORDER_ITEM_TABLE = "CREATE TABLE " + TABLE_ORDER_ITEM + "(" 
        		+ KEY_ORDER_ITEM_ID + " INTEGER PRIMARY KEY, "+ KEY_RECEIPT_NO + " TEXT," + KEY_FISH_TYPE + " TEXT,"
        		+ KEY_PRICE + " REAL," + KEY_WEIGHT + " REAL" + ")";
        db.execSQL(CREATE_BOATS_TABLE);
        db.execSQL(CREATE_CREWS_TABLE);
        db.execSQL(CREATE_ORDERS_TABLE);
        db.execSQL(CREATE_ORDER_ITEM_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_ITEM);
 
        // Create tables again
        onCreate(db);
        
    }
    
    public void deleteAllBoats() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("delete from "+ TABLE_BOAT);
    	db.execSQL("delete from "+ TABLE_CREW);
    	db.close();
    }
    
    public void deleteAllOrders() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("delete from "+ TABLE_ORDERS);
    	db.execSQL("delete from "+ TABLE_ORDER_ITEM);
    	db.close();
    }
    
    // Insert entry to Boat table
    public void addBoat(Boat newBoat) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
    	// Get data to be inserted into Boat Table
        ContentValues values = new ContentValues();
        values.put(KEY_BOATNO, newBoat.getBoatNo());
        values.put(KEY_BOAT_NAME, newBoat.getBoatName()); 
        values.put(KEY_CAPTAIN_NAME, newBoat.getCptName()); 
     
        // Inserting Row
        db.insert(TABLE_BOAT, null, values);
        db.close(); // Closing database connection
    }
    
    // Insert entry to Crew table
    public void addCrew(Crew newCrew, String boatNo) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
    	// Get data to be inserted into Boat Table
        ContentValues values = new ContentValues();
        values.put(KEY_CREW_NAME, newCrew.getCrewName());
        values.put(KEY_SIN, newCrew.getCrewSIN()); 
        values.put(KEY_BOATNO, boatNo); 
     
        // Inserting Row
        db.insert(TABLE_CREW, null, values);
        db.close(); // Closing database connection
    }
    
    // Adding new order
    public void addOrder(Order newOrder) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        ContentValues orderItemValues = new ContentValues();
        values.put(KEY_RECEIPT_NO, newOrder.getReceiptNo());
        values.put(KEY_DATE, newOrder.getDate());
        values.put(KEY_NAME, newOrder.getName()); // Customer Info
        values.put(KEY_BNO, newOrder.getNo());
        //Log.i("db handler", "CFV = " + newOrder.getNo());
        //values.put(KEY_FISH_TYPE, newOrder.getFishType()); // Fish Type
        //values.put(KEY_PRICE, newOrder.getPricePerPound()); // Price
        //values.put(KEY_WEIGHT, newOrder.getTotalWeight()); // Total weight
        values.put(KEY_AMOUNT_PAID, newOrder.getAmountPaid()); // Amount paid in cents
        
        ArrayList<OrderItem> newOrderItems = newOrder.getAllOrderItems();
        
        Log.i("DEBUG", "OrderItemSize = " + newOrderItems.size());
        
        for(OrderItem item : newOrderItems) {
        	orderItemValues = new ContentValues();
        	orderItemValues.put(KEY_RECEIPT_NO_ORDITEM, newOrder.getReceiptNo());
        	orderItemValues.put(KEY_FISH_TYPE, item.getFishType());
        	orderItemValues.put(KEY_PRICE, item.getPricePerPound());
        	orderItemValues.put(KEY_WEIGHT, item.getTotalWeight());
        	
        	// Insert Order Item to db
        	db.insert(TABLE_ORDER_ITEM, null, orderItemValues);
        }
        
     
        // Inserting Row
        db.insert(TABLE_ORDERS, null, values);
        db.close(); // Closing database connection
    }
     
    // Getting single contact
    /*public Boat getCustomer(int custNo) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query(TABLE_CUSTOMERS, new String[] { KEY_CUSTNO,
                KEY_BOAT_NAME, KEY_BOAT_NO }, KEY_CUSTNO + "=?",
                new String[] { String.valueOf(custNo) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        Boat newCust = new Boat(cursor.getString(0),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return newCust;
    }*/
     
    // Getting All Boats
    public ArrayList<Boat> getAllBoats() {
    	ArrayList<Boat> boatList = new ArrayList<Boat>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BOAT;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Boat _boat = new Boat();
                _boat.setBoatNo(cursor.getString(0));
                _boat.setBoatName(cursor.getString(1));
                _boat.setCptName(cursor.getString(2));
                // Adding contact to list
                boatList.add(_boat);
            } while (cursor.moveToNext());
        }
        
        db.close();
     
        // return contact list
        return boatList;
    }
    
    // Getting All Crew
    public ArrayList<Crew> getAllCrews() {
    	ArrayList<Crew> crewList = new ArrayList<Crew>();
        // Select All Query
        String selectQuery = "SELECT a." + KEY_CREW_NAME + ", a." + KEY_SIN + ", b." + KEY_BOAT_NAME 
        		+ " FROM " + TABLE_CREW + " a JOIN " + TABLE_BOAT + " b ON a." + KEY_BOATNO + "=b." + KEY_BOATNO;
        
        //String selectQuery = "SELECT * FROM " + TABLE_CREW;
        
        Log.d("Crew query JOIN", selectQuery);
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Crew _crew = new Crew();
                _crew.setCrewName(cursor.getString(0));
                _crew.setCrewSIN(cursor.getString(1));
                _crew.setCrewBoatName(cursor.getString(2));
                //Log.d("", cursor.getString(2)); 
                // Adding contact to list
                crewList.add(_crew);
            } while (cursor.moveToNext());
        }
        
        db.close();
     
        // return contact list
        return crewList;
    }
    
    // Getting All Order Items of a specific receiptNo
    public ArrayList<OrderItem> getAllOrderItems(String receiptNo) {
    	ArrayList<OrderItem> ordItemList = new ArrayList<OrderItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ORDER_ITEM + " WHERE " + KEY_RECEIPT_NO + "='" + receiptNo + "'";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OrderItem item = new OrderItem(cursor.getString(1), Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)));
                //item.setReceiptNo(cursor.getString(0));
                //item.setDate(Long.parseLong(cursor.getString(1)));
                //item.setName(cursor.getString(2));
                //ord.setFishType(cursor.getString(3));
                //ord.setPricePerPound(Double.parseDouble(cursor.getString(4)));
                //ord.setTotalWeight(Double.parseDouble(cursor.getString(5)));
                //ord.setAmountPaid(Double.parseDouble(cursor.getString(3)));
                item.setReceiptNo(receiptNo);
                ordItemList.add(item);
            } while (cursor.moveToNext());
        }
        
        db.close();
     
        // return contact list
        return ordItemList;
    }
    
    // Getting All Orders
    public ArrayList<Order> getAllOrders() {
    	ArrayList<Order> ordList = new ArrayList<Order>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ORDERS;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Order ord = new Order();
                ord.setReceiptNo(cursor.getString(0));
                ord.setDate(Long.parseLong(cursor.getString(1)));
                ord.setNo(cursor.getString(2));
                ord.setName(cursor.getString(3));
                Log.i("db handler", "CFV = " + ord.getNo());
                //ord.setFishType(cursor.getString(3));
                //ord.setPricePerPound(Double.parseDouble(cursor.getString(4)));
                //ord.setTotalWeight(Double.parseDouble(cursor.getString(5)));
                ord.setAmountPaid(Double.parseDouble(cursor.getString(4)));
                // Adding contact to list
                ordList.add(ord);
            } while (cursor.moveToNext());
        }
        
        db.close();
     
        // return contact list
        return ordList;
    }
    
    // Getting All Orders sorted by date
    // Date is in epoch timestamp
    public ArrayList<Order> getAllOrdersByDate() {
    	ArrayList<Order> ordList = new ArrayList<Order>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ORDERS + " ORDER BY " + KEY_DATE + " DESC";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Order ord = new Order();
                ord.setReceiptNo(cursor.getString(0));
                ord.setDate(Long.parseLong(cursor.getString(1)));
                ord.setNo(cursor.getString(2));
                ord.setName(cursor.getString(3));
                //ord.setFishType(cursor.getString(3));
                //ord.setPricePerPound(Double.parseDouble(cursor.getString(4)));
                //ord.setTotalWeight(Double.parseDouble(cursor.getString(5)));
                ord.setAmountPaid(Double.parseDouble(cursor.getString(4)));
                // Adding contact to list
                ordList.add(ord);
            } while (cursor.moveToNext());
        }
        
        db.close();
     
        // return contact list
        return ordList;
    }
    
    // Find order using unique receiptNo
    // Return Order object
    public Order getOrder(String receiptNo) {
    	Order ord = new Order();
    	String selectQuery = "SELECT  * FROM " + TABLE_ORDERS + " WHERE " + KEY_RECEIPT_NO + "='" + receiptNo + "'";
        
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ord.setReceiptNo(cursor.getString(0));
                ord.setDate(Long.parseLong(cursor.getString(1)));
                ord.setNo(cursor.getString(2));
                ord.setName(cursor.getString(3));
                ord.setAmountPaid(Double.parseDouble(cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
    	
    	return ord;
    }
    
    public ArrayList<OrderItem> getOrderItems(String receiptNo) {
    	ArrayList<OrderItem> ordItemList = new ArrayList<OrderItem>();
    	
    	String selectQuery = "SELECT  * FROM " + TABLE_ORDER_ITEM + " WHERE " + KEY_RECEIPT_NO_ORDITEM + "='" + receiptNo + "'";
        
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	OrderItem ordItem = new OrderItem();
            	ordItem.setReceiptNo(cursor.getString(1));
            	ordItem.setFishType(cursor.getString(2));
            	ordItem.setPrice(Double.parseDouble(cursor.getString(3)));
            	ordItem.setTotalWeight(Double.parseDouble(cursor.getString(4)));
                // Adding contact to list
                ordItemList.add(ordItem);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
     
        // return contact list
        return ordItemList;
    }
    
    // Getting All Orders sorted by date
    // Date is in epoch timestamp
    public ArrayList<Order> getAllOrdersByBoat(String boatName) {
    	ArrayList<Order> ordList = new ArrayList<Order>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ORDERS + " WHERE " + KEY_NAME + " LIKE '%" + boatName + "%' OR " + KEY_BNO + "='" + boatName + "'" + " ORDER BY " + KEY_DATE + " DESC";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Order ord = new Order();
                ord.setReceiptNo(cursor.getString(0));
                ord.setDate(Long.parseLong(cursor.getString(1)));
                ord.setNo(cursor.getString(2));
                ord.setName(cursor.getString(3));
                //ord.setFishType(cursor.getString(3));
                //ord.setPricePerPound(Double.parseDouble(cursor.getString(4)));
                //ord.setTotalWeight(Double.parseDouble(cursor.getString(5)));
                ord.setAmountPaid(Double.parseDouble(cursor.getString(4)));
                // Adding contact to list
                ordList.add(ord);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
     
        // return contact list
        return ordList;
    }
    
    public boolean isUniqueReceipt(String receipt) {
    	String selectQuery = "SELECT  * FROM " + TABLE_ORDERS + " WHERE " + KEY_RECEIPT_NO + "= '" + receipt + "'";
        
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        Log.i("ReceiptNoCount", "= " + cursor.getCount());     
        if(cursor.getCount() <= 0){
            cursor.close();
            return true;
        }
	    cursor.close();
	    return false;
    }
     
    // Getting contacts Count
    public int getCustomerCount() {
    	String countQuery = "SELECT  * FROM " + TABLE_BOAT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        
        db.close();
 
        // return count
        return cursor.getCount();
    }
    
    // Updating single contact
    /*public int updateContact(Boat cust) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(KEY_CUST_NAME, cust.getCustName());
        values.put(KEY_BOAT_NAME, cust.getBoatName());
        values.put(KEY_BOAT_NO, cust.getBoatNo());
     
        // updating row
        return db.update(TABLE_CUSTOMERS, values, KEY_BOAT_NO + " = ?",
                new String[] { String.valueOf(cust.getBoatNo()) });
    }
     
    // Deleting single contact
    public void deleteContact(String boatName) {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CUSTOMERS, KEY_BOAT_NAME + " = ?",
                new String[] { boatName });
        db.close();
    }*/

}
