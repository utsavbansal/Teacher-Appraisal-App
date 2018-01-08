package utsavbansal.teacher_evaluation_app;

/**
 * Created by Utsav Bansal on 02-08-2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {
    private static String databaseName="Student_db";        // create database name
    private static String TableName="Admin_info";         // create database table
    private static String Name="name";                      //create database fields name
    private static String Id="email";
    private static String Password="pass";
    private static String Contact="contact_number";         //create database fields contact
    private static int Version=1;                   //create database fields version for database version it will update after every update in database
    private SQLiteDatabase database;            // Create a database variable to get object


    public Helper(Context context) {
        super(context, databaseName, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TableName+"(id integer not null primary key autoincrement,"+Name+" text,"+Contact+" text,"+Id+" text,"+Password+" text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exist "+TableName);           // Query to drop table
        onCreate(db);

    }

    public boolean checkData(String id, String pass) {
        boolean msg=false;
        Cursor cursor=null;

        database = getReadableDatabase();           // get readable object for database
        cursor=database.query(TableName,new String[]{"id",Name,Contact,Id,Password},null,null,null,null,null);
        cursor.moveToFirst();
        do {
            if ((cursor.getString(3).toString()).equals(id) && (cursor.getString(4).toString()).equals(pass)) {
                msg = true;
                break;
            }
        }while(cursor.moveToNext());
        return msg;
    }

    public int getId(String id, String pass) {
        int unique_id=0;
        Cursor cursor=null;

        database = getReadableDatabase();           // get readable object for database
        cursor=database.query(TableName,new String[]{"id",Name,Contact,Id,Password},null,null,null,null,null);
        cursor.moveToFirst();
        do {
            if ((cursor.getString(3).toString()).equals(id) && (cursor.getString(4).toString()).equals(pass)) {
                unique_id=cursor.getInt(0);
                break;
            }
        }while(cursor.moveToNext());
        return unique_id;

    }

    public String saveData(String name, String contact, String id, String password) {
        String msg=null;

        try {
            ContentValues cv = new ContentValues();               // Mapping data with table field  so that data gets stored in correct attribute
            cv.put(Name, name);
            cv.put(Contact, contact);
            cv.put(Password,password);
            cv.put(Id,id);
            database = getWritableDatabase();             // Get object of database variable in writable mode

            long result = database.insert(TableName, null, cv);        // if data present so ok otherwise  pass null  to create a null field
            // database.insert returns a long type return value so to verify if query is executed successfully then it result value > 0

            if (result > 0) {
                msg = "Registration successfully";
            } else
                msg = "Registration Unsuccessfull";
        }
        catch (Exception ex)            // We use try catch to check whether error is occurred so user can know at text view
        {
            msg=ex.getMessage();
        }



        return msg;
    }

    public Cursor getObject(int id) {
        Cursor cursor=null;

        database = getReadableDatabase();           // get readable object for database
        cursor=database.query(TableName,new String[]{"id",Name,Contact,Id,Password},null,null,null,null,null);
        cursor.moveToFirst();

        do {
            if (id==cursor.getInt(0)) {

                break;
            }
        }while(cursor.moveToNext());


        return cursor;
    }
}
