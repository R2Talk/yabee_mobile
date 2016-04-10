package br.com.ca.asap.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.ca.asap.vo.AdminVo;

/**
 * AdminDAO
 *
 * DAO for ADMINISTRATOR yabee local table
 *
 * @author Rodrigo Carvalho
 */
public class AdminDAO {

    private DatabaseOpenHelper databaseOpenHelper;
    private SQLiteDatabase db;

    //PMO TABLE
    private String DATABASE_TABLE_ADMIN = "administrator";
    private String KEY_ADMIN_ID = "admin_id";
    private String KEY_NAME = "name";

    //Constructor
    public AdminDAO(Context context){
        this.databaseOpenHelper = DatabaseOpenHelper.getInstance(context);
    }

    //
    // HELPER METHODS
    //


    //insertInitiative
    public void insertPmo(AdminVo adminVo){
        SQLiteDatabase db = this.databaseOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ADMIN_ID, adminVo.getPmoId());
        values.put(KEY_NAME, adminVo.getName());

        // Inserting Row
        db.insert(DATABASE_TABLE_ADMIN, null, values);
        db.close(); // Closing database connection
    }

    //return selected list of work items by initiative id
    public AdminVo selectPmoByName(String name){

        AdminVo adminVo = null;
        SQLiteDatabase db = this.databaseOpenHelper.getReadableDatabase();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_ADMIN + " WHERE name = \"" + name + "\"";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                adminVo = new AdminVo(
                        cursor.getString(0),
                        cursor.getString(1)
                );
            } while (cursor.moveToNext());
        }

        // return adminVo
        return adminVo;
    }
}
