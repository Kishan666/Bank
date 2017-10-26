package com.example.kishan.bankingapp3.FrontEnd.Repositories.client.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.kishan.bankingapp3.FrontEnd.Domain.client.Client;
import com.example.kishan.bankingapp3.FrontEnd.Repositories.client.ClientRepository;
import com.example.kishan.bankingapp3.FrontEnd.Utils.App;
import com.example.kishan.bankingapp3.FrontEnd.Utils.database.DBConstants;
import com.example.kishan.bankingapp3.FrontEnd.Utils.database.ManageDatabase;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ferin on 2016-08-28.
 */
public class ClientRepositoryImpl extends SQLiteOpenHelper implements ClientRepository {
    public static final String TABLE_NAME = "client";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IDNUMBER = "idNumber";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_MEMBERSHIPTYPE = "membershipType";
    public static final String COLUMN_EMAIL = "email";

    public ClientRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public ClientRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ClientRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public boolean validateUser(String email, String idnumber){
        boolean isValid = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM client WHERE email = '" + email + "' AND idNumber = '" + idnumber + "'", null);

        return c.moveToNext();
    }

    @Override
    public Client findById(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_EMAIL,
                        COLUMN_IDNUMBER,
                        COLUMN_SURNAME,

                        COLUMN_NAME},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        if(cursor.moveToFirst())
        {
            String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
            String idNumber = cursor.getString(cursor.getColumnIndex(COLUMN_IDNUMBER));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String surname = cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME));

            Long columnId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));

            /* dont need this */
            final Client client = new Client.Builder()
                    .id(columnId)

                    .idNumber(idNumber)
                    .email(email)
                    .name(name)
                    .surname(surname)
                    .build();

            return client;
        }
        else
            return null;
    }

    @Override
    public Client save(Client entity) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_IDNUMBER, entity.getIdNumber());
        values.put(COLUMN_EMAIL, entity.getEmail());
        values.put(COLUMN_NAME, entity.getName());

        values.put(COLUMN_SURNAME, entity.getSurname());

        Long id = db.insertOrThrow(TABLE_NAME, null,values);

        Client client = new Client.Builder()
                .copy(entity)
                .id(new Long(id))
                .build();

        return client;
    }

    @Override
    public Client update(Client entity) {
        open();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, entity.getId());
        values.put(COLUMN_IDNUMBER, entity.getIdNumber());
        values.put(COLUMN_EMAIL, entity.getEmail());
        values.put(COLUMN_NAME, entity.getName());

        values.put(COLUMN_SURNAME, entity.getSurname());

        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf((entity.getId()))}
        );
        return entity;
    }

    @Override
    public Client delete(Client entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getId())});
        return entity;
    }

    @Override
    public Set<Client> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<Client> clientSet = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                String idNumber = cursor.getString(cursor.getColumnIndex(COLUMN_IDNUMBER));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String surname = cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME));

                Long columnId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));

            /* dont need this */
                final Client client = new Client.Builder()
                        .id(columnId)

                        .idNumber(idNumber)
                        .email(email)
                        .name(name)
                        .surname(surname)
                        .build();

                clientSet.add(client);
            }
            while (cursor.moveToNext());
        }
        return clientSet;
    }

    @Override
    public int deleteAll() {
        open();
        int rowsDeleted = db.delete(TABLE_NAME,null,null);
        //close();
        return rowsDeleted;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ManageDatabase manageDatabase = new ManageDatabase(App.getInstance());
        manageDatabase.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
