package com.example.invoice.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.invoice.enity.invoicemessage;
import java.util.ArrayList;
import java.util.List;

public class userdata extends SQLiteOpenHelper{
    private static final String db_name="invoicemassage1.db";
    private static final String table_name="massage1";
    private static userdata mHelper=null;
    private static final int version=1;
    private SQLiteDatabase mRDB=null;
    private SQLiteDatabase wRDB=null;

    public userdata(Context context) {
        super(context,db_name,null,version);
    }

    public static userdata getInstance(Context context){
        if (mHelper==null){
            mHelper=new userdata(context);
        }
        return mHelper;
    }
    public SQLiteDatabase openReadLink(){
        if(mRDB==null||!mRDB.isOpen()){
            mRDB=mHelper.getReadableDatabase();
        }
        return mRDB;
    }
    public void closeLink(){
        if (mRDB!=null && mRDB.isOpen()){
            mRDB.close();
            mRDB=null;
        }
        if(wRDB!=null && wRDB.isOpen()){
            wRDB.close();
            wRDB=null;
        }
    }
    public SQLiteDatabase openWriteLink(){
        if(wRDB==null||!wRDB.isOpen()){
            wRDB=mHelper.getWritableDatabase();
        }
        return wRDB;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE IF NOT EXISTS "+table_name+"(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "code VARCHAR NOT NULL," +
                "number VARCHAR NOT NULL," +
                "time VARCHAR NOT NULL," +
                "category VARCHAR NOT NULL," +
                "price FLOAT NOT NULL," +
                "state INT NOT NULL);";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }
    public long save(invoicemessage invoice){
        ContentValues cv =new ContentValues();
        cv.put("code",invoice.code);
        cv.put("number",invoice.number);
        cv.put("time",invoice.time);
        cv.put("category",invoice.category);
        cv.put("price",invoice.price);
        cv.put("state",invoice.state);
        return wRDB.insert(table_name,null,cv);
    }
    public long delectbycode(String code, int state){
        String[] whereArgs = {code, String.valueOf(state)};
        return wRDB.delete(table_name, "code=? AND state=?", whereArgs);
    }
    public List<invoicemessage> selectbystate(int state){
        List<invoicemessage> list=new ArrayList<>();
        String[] selectionArgs = { String.valueOf(state) };
        Cursor cursor = mRDB.rawQuery("SELECT * FROM massage1 WHERE state = "+ state, null);
        while (cursor.moveToNext()){
            invoicemessage invoice=new invoicemessage();
            invoice.code=cursor.getString(1);
            invoice.number=cursor.getString(2);
            invoice.time=cursor.getString(3);
            invoice.category=cursor.getString(4);
            invoice.price=cursor.getFloat(5);
            invoice.state=cursor.getInt(6);
            list.add(invoice);
        }
        return list;
    }
    public float pricebystate(int state){
        float price=0;
        String[] selectionArgs = { String.valueOf(state) };
        Cursor cursor = mRDB.rawQuery("SELECT * FROM massage1 WHERE state = "+ state, null);
        while (cursor.moveToNext()){
            price=price+cursor.getFloat(5);
        }
        return price;
    }
    public List<MyElement> selectallstate(int state,String category){
        ArrayList<MyElement> list = new ArrayList<>();
        String state1=String.valueOf(state);
        String selection = "state = ? AND category = ?";
        String[] selectionArgs = { state1, category };
        Cursor cursor = mRDB.query(table_name,null,selection,selectionArgs,null,null,null);
        if (cursor != null && cursor.moveToFirst()) {
            do{
                MyElement element=new MyElement();
                element.price=cursor.getFloat(5);
                element.id=cursor.getInt(0);
                list.add(element);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public long updatebyid(int id){
        ContentValues values = new ContentValues();
        values.put("state", 1);
        String[] selectionArgs = { String.valueOf(id) };
        return mRDB.update("massage1", values, "_id=?", selectionArgs);
    }
    public invoicemessage selectbyid(int id){
        Cursor cursor = mRDB.rawQuery("SELECT * FROM massage1 WHERE _id = "+ id, null);
        invoicemessage invoice = new invoicemessage();
        if (cursor != null && cursor.moveToFirst()) {
            do{
                invoice.code = cursor.getString(1);
                invoice.number = cursor.getString(2);
                invoice.time = cursor.getString(3);
                invoice.category = cursor.getString(4);
                invoice.price = cursor.getFloat(5);
                invoice.state = cursor.getInt(6);
            }while (cursor.moveToNext());
        }
        return invoice;
    }
}
