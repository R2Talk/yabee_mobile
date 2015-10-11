package br.com.ca.asap.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.ca.asap.vo.DeliverableVo;

/**
 * DeliverableDAO
 */
public class DeliverableDAO {
    private DatabaseOpenHelper databaseOpenHelper;
    private SQLiteDatabase db;

    //WORK ITEM TABLE
    private String DATABASE_TABLE_DELIVERABLE = "deliverable";
    private String KEY_DELIVERABLE_ID = "deliverable_id";
    private String KEY_INITIATIVE_FK = "initiative_id_fk";
    private String KEY_DELIVERABLE_TITLE = "deliverable_title";
    private String KEY_DELIVERABLE_DESCRIPTION = "deliverable_description";
    private String KEY_DELIVERABLE_COMMENTS = "deliverable_comments";
    private String KEY_DELIVERABLE_STATUS = "deliverable_status";
    private String KEY_DELIVERABLE_DUE_DATE = "deliverable_due_date";
    private String KEY_DELIVERABLE_RESPONSIBLE = "deliverable_responsible";
    private String KEY_DELIVERABLE_RATING = "deliverable_rating";

    //Constructor
    public DeliverableDAO(Context context){
        this.databaseOpenHelper = DatabaseOpenHelper.getInstance(context);
    }

    //
    // HELPER METHODS
    //

    //insert Work Item
    public void insertWorkItem(DeliverableVo deliverableVo){
        SQLiteDatabase db = this.databaseOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DELIVERABLE_ID, deliverableVo.getDeliverable_id());
        values.put(KEY_INITIATIVE_FK, deliverableVo.getInitiative_id_fk());
        values.put(KEY_DELIVERABLE_TITLE, deliverableVo.getDeliverable_title());
        values.put(KEY_DELIVERABLE_DESCRIPTION, deliverableVo.getDeliverable_description());
        values.put(KEY_DELIVERABLE_COMMENTS, deliverableVo.getDeliverable_comments());
        values.put(KEY_DELIVERABLE_STATUS, deliverableVo.getDeliverable_status());
        values.put(KEY_DELIVERABLE_DUE_DATE, deliverableVo.getDeliverable_due_date());
        values.put(KEY_DELIVERABLE_RESPONSIBLE, deliverableVo.getDeliverable_responsible());
        values.put(KEY_DELIVERABLE_RATING, deliverableVo.getDeliverable_rating());

        // Inserting Row
        db.insert(DATABASE_TABLE_DELIVERABLE, null, values);
        db.close(); // Closing database connection
    }

    //return selected list of work items
    public List<DeliverableVo> selectWorkItems(){
        List<DeliverableVo> deliverableVoList =  new ArrayList<DeliverableVo>();
        SQLiteDatabase db = this.databaseOpenHelper.getReadableDatabase();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_DELIVERABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DeliverableVo deliverableVo = new DeliverableVo(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)
                        );

                // Adding contact to list
                deliverableVoList.add(deliverableVo);
            } while (cursor.moveToNext());
        }

        // return Initiative list
            return deliverableVoList;
    }

    //return selected list of work items by initiative id
    public List<DeliverableVo> selectWorkItemsByInitiativeId(String initiativeId){
        List<DeliverableVo> deliverableVoList =  new ArrayList<DeliverableVo>();
        SQLiteDatabase db = this.databaseOpenHelper.getReadableDatabase();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_DELIVERABLE + " WHERE initiative_id_fk = \"" + initiativeId + "\"";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DeliverableVo deliverableVo = new DeliverableVo(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)
                );

                // Adding contact to list
                deliverableVoList.add(deliverableVo);
            } while (cursor.moveToNext());
        }

        // return Initiative list
        return deliverableVoList;
    }
}
