package br.com.ca.blueocean.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.ca.blueocean.vo.InitiativeVo;

/**
 * InitiativeDAO
 *
 * DAO for INITIATIVE yabee local table
 *
 * @author Rodrigo Carvalho
 */
public class InitiativeDAO {
    private DatabaseOpenHelper databaseOpenHelper;
    private SQLiteDatabase db;

    //Constructor
    public InitiativeDAO(Context context){
        this.databaseOpenHelper = DatabaseOpenHelper.getInstance(context);
    }

    //
    // HELPER METHODS
    //


    //insertInitiative
    public void insertInitiative(InitiativeVo initiativeVo){
        SQLiteDatabase db = this.databaseOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseOpenHelper.KEY_INITIATIVE_idinitiative, initiativeVo.getInitiativeId());
        values.put(DatabaseOpenHelper.KEY_INITIATIVE_title, initiativeVo.getInitiativeTitle());
        values.put(DatabaseOpenHelper.KEY_INITIATIVE_description, initiativeVo.getInitiativeDescription());

        // Inserting Row
        db.insertOrThrow(DatabaseOpenHelper.DATABASE_TABLE_INITIATIVE, null, values);
        db.close(); // Closing database connection
    }

    //return selected list of initiatives
    public List<InitiativeVo> selectInitiatives(){
        List<InitiativeVo> initiativeVoList =  new ArrayList<InitiativeVo>();
        SQLiteDatabase db = this.databaseOpenHelper.getReadableDatabase();


        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseOpenHelper.DATABASE_TABLE_INITIATIVE;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InitiativeVo initiativeVo = new InitiativeVo(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)
                        );

                // Adding contact to list
                initiativeVoList.add(initiativeVo);
            } while (cursor.moveToNext());
        }

        //access initiative list via Iterator
        Iterator iterator = initiativeVoList.iterator();
        while(iterator.hasNext()){
            InitiativeVo initiativeVo = (InitiativeVo) iterator.next();
            Log.d("initiativeVoList", initiativeVo.getInitiativeTitle());
            //...insert into initiative table

        }

        // return Initiative list
        return initiativeVoList;
    }

    /**
     * getIdInititiveByTitle
     *
     * @param title
     * @return
     */
    public String getIdInitiativeByTitle(String title) {
        InitiativeVo initiativeVo = null;
        SQLiteDatabase db = this.databaseOpenHelper.getReadableDatabase();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseOpenHelper.DATABASE_TABLE_INITIATIVE + " WHERE title = '" + title + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                initiativeVo = new InitiativeVo(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );

            } while (cursor.moveToNext());
        }

        return initiativeVo.getInitiativeId();

    }
}
