package com.example.kishan.bankingapp3.FrontEnd.Utils.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kishan on 2017-10-26.
 */
public class ManageDatabase extends SQLiteOpenHelper {

    public static final String TABLE_NAME_CLIENT = "client";
    private SQLiteDatabase db;



    public static final String COLUMN_ID_CLIENT = "id";
    public static final String COLUMN_IDNUMBER_CLIENT = "idNumber";
    public static final String COLUMN_NAME_CLIENT = "name";
    public static final String COLUMN_SURNAME_CLIENT = "surname";

    public static final String COLUMN_EMAIL_CLIENT = "email";

    private static final String DATABASE_CREATE_CLIENT = " CREATE TABLE "
            + TABLE_NAME_CLIENT + "("
            + COLUMN_ID_CLIENT + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME_CLIENT + " TEXT  NOT NULL , "
            + COLUMN_SURNAME_CLIENT + " TEXT  NOT NULL , "

            + COLUMN_IDNUMBER_CLIENT + " TEXT  NOT NULL , "
            + COLUMN_EMAIL_CLIENT + " TEXT  NOT NULL );";

    public ManageDatabase(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);

    }

    public ManageDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.db = db;
    }

    public ManageDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        this.db = db;
    }

    public void deleteTable(String tablename){
        db.execSQL("DROP TABLE IF EXISTS "+tablename+";");
    }

    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(DBConstants.DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_CLIENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
