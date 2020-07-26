package io.google.happybirthday;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by patel on 10/9/2019.
 */

public class SqliteDatabase extends SQLiteOpenHelper {
    private	static final int DATABASE_VERSION =	5;
    private	static final String	DATABASE_NAME = "birthday";
    private	static final String TABLE_CONTACTS = "birthdays";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NO = "phno";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_WISH_STR = "wishstr";

    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String	CREATE_CONTACTS_TABLE = "CREATE	TABLE " + TABLE_CONTACTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT," + COLUMN_NO + " TEXT," + COLUMN_DATE + " TEXT," + COLUMN_WISH_STR + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public ArrayList<Birthdays> listBirthdays(){
        String sql = "select * from " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Birthdays> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                Log.i("JOY",""+name);
                String phno = cursor.getString(2);
                String date = cursor.getString(3);
                String wishstr = cursor.getString(4);
                storeContacts.add(new Birthdays(id, name, phno, date, wishstr));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

    public void addBirthdays(Birthdays birthdays){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NO, birthdays.getPhnum());
        values.put(COLUMN_NAME, birthdays.getName());
        values.put(COLUMN_DATE, birthdays.getDate());
        values.put(COLUMN_WISH_STR, birthdays.getWishStr());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CONTACTS, null, values);
//        Log.i("JOY","INSERTED SUCCESSFULLY"+birthdays.getName());
    }

    public void updateBirthdays(Birthdays birthdays){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NO, birthdays.getPhnum());
        values.put(COLUMN_NAME, birthdays.getName());
        values.put(COLUMN_DATE, birthdays.getDate());
        values.put(COLUMN_WISH_STR, birthdays.getWishStr());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_CONTACTS, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(birthdays.getId())});
    }

    public Birthdays findBirthdays(String name){
        String query = "Select * FROM "	+ TABLE_CONTACTS + " WHERE " + COLUMN_NAME + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        Birthdays contacts = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            String name1 = cursor.getString(1);
            String phnum = cursor.getString(2);
            String date = cursor.getString(3);
            String wishstr = cursor.getString(4);
            contacts = new Birthdays(id, name1, phnum, date, wishstr);
        }
        cursor.close();
        return contacts;
    }

    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
}
