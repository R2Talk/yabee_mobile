package br.com.ca.asap.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.ca.asap.vo.InitiativeVo;

/**
 * InitiativeDAO
 */
public class InitiativeDAO {
    private DatabaseOpenHelper databaseOpenHelper;
    private SQLiteDatabase db;

    //INITIATIVE TABLE
    private String DATABASE_TABLE_INITIATIVE = "initiative";
    private String KEY_INITIATIVE_ID = "initiative_id";
    private String KEY_TITLE = "title";
    private String KEY_DESCRIPTION = "description";

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
        values.put(KEY_INITIATIVE_ID, initiativeVo.getInitiativeId());
        values.put(KEY_TITLE, initiativeVo.getInitiativeTitle());
        values.put(KEY_DESCRIPTION, initiativeVo.getInitiativeDescription());

        // Inserting Row
        db.insert(DATABASE_TABLE_INITIATIVE, null, values);
        db.close(); // Closing database connection
    }

    //return selected list of initiatives
    public List<InitiativeVo> selectInitiatives(){
        List<InitiativeVo> initiativeVoList =  new ArrayList<InitiativeVo>();
        SQLiteDatabase db = this.databaseOpenHelper.getReadableDatabase();


        // Select All Query
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_INITIATIVE;

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
}
