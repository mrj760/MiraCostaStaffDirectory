package com.example.miracostastaffdirectory.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = DBHelper.class.getSimpleName();


    /** Define all database info in constants */
    public static final String DATABASE_NAME = "ToDo2Day";
    public static final String DATABASE_TABLE = "Tasks";
    public static final String FIELD_PRIMARY_KEY = "_id";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_IS_DONE = "is_done";




    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /** Create all database tables
         * 1.) Determine whether to read or write the database (CREATE = write)
         * */

        /** Open a writable connection to the ToDo2Day database */
        db = getWritableDatabase();
        // other option for reading another database instead =>> db = getReadableDatabase()



        // this string will be used as a sql command
        String sql = (     "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " ( "    +
                FIELD_PRIMARY_KEY + " INTEGER PRIMARY KEY , "             +
                FIELD_DESCRIPTION + "TEXT, "                               +
                FIELD_IS_DONE + "INTEGER" + ")"                           );

        // run that string command here
        db.execSQL(sql);


        // Log the SQL String in case shit hits the fan
        Log.i(TAG, sql);







        db.close();



    }





    public void addTask(Task t) {

        String desc = t.getDescription();
        boolean isDone = t.isDone();

        SQLiteDatabase db = getWritableDatabase();

        // make this set of 'key / value' pairs for our database to insert
        ContentValues values = new ContentValues();
        // add each to the list above
        values.put(FIELD_DESCRIPTION, desc);
        values.put(FIELD_IS_DONE, isDone? 1: 0);

        // then add the 2 items that are stored in the ContentValues to the database to make one new record
        // also doing this returns a long ( the id val from the database) so we'll set that objects id in java too
        long id = db.insert(DATABASE_TABLE, null, values);
        t.setId(id);

        // close this bitch up
        db.close();

    }

    public List<Task> getAllTasks() {

        // make empty list
        List<Task> taskList= new ArrayList<>();

        // fill it from data from the database records ... readable this time since we're just reading info
        SQLiteDatabase db = getReadableDatabase();

        // Query results in SQLite are called cursors, so lets get them
        Cursor cursor  = db.query(DATABASE_TABLE,
                new String[]{FIELD_PRIMARY_KEY, FIELD_DESCRIPTION, FIELD_IS_DONE},
                null,
                null,
                null,
                null,
                null);


        // Loop through the cursor results
        // Create Task objects to add to list
        // First figure out if there are any results
        if (cursor.moveToFirst()) {

            do {

                Task t = new Task(cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getInt(3)==1);

                taskList.add(t);

            } while (cursor.moveToNext());


        }


        cursor.close();
        db.close();



        // return the filled out list
        return taskList;
    }

    public void clearAllTasks() {

        SQLiteDatabase db = getWritableDatabase();
        // this deletes the whole table
        db.delete(DATABASE_TABLE, null, null);

        db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }
}
