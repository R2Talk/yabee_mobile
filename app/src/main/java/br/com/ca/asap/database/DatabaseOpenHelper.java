package br.com.ca.asap.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * DatabaseOpenHelper
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static DatabaseOpenHelper sInstance;
    private static SQLiteDatabase db = null;
    private Context context;

    private static final String DATABASE_NAME = "CA_ASAP_DB";
    private static final int DATABASE_VERSION = 1;

    //INITIATIVE TABLE
    private String DATABASE_TABLE_INITIATIVE = "initiative";
    private String KEY_INITIATIVE_INITIATIVE_ID = "initiative_id";
    private String KEY_INITIATIVE_TITLE = "title";
    private String KEY_INITIATIVE_DESCRIPTION = "description";

    //INITIATIVE TABLE
    private String DATABASE_TABLE_DELIVERABLE = "deliverable";
    private String KEY_DELIVERABLE_ID = "deliverable_id";
    private String KEY_INITIATIVE_FK = "initiative_id_fk";
    private String KEY_DELIVERABLE_TITLE = "deliverable_title";
    private String KEY_DELIVERABLE_DESCRIPTION = "deliverable_description";
    private String KEY_DELIVERABLE_COMMENTS = "deliverable_comments";
    private String KEY_DELIVERABLE_STATUS = "deliverable_status";
    private String KEY_DELIVERABLE_RATING = "deliverable_rating";

    //PMO TABLE
    private String DATABASE_TABLE_PMO = "pmo";
    private String KEY_PMO_ID = "pmo_id";
    private String KEY_PMO_NAME = "name";

    public static synchronized DatabaseOpenHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseOpenHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        //create tables if it did not already exist
        try {
            //INITIATIVE
            String CREATE_INITIATIVE_TABLE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_INITIATIVE +
                    "(initiative_id TEXT PRIMARY KEY, " +
                    "title TEXT," +
                    "description TEXT)";

            db.execSQL(CREATE_INITIATIVE_TABLE);

            //DELIVERABLE
            String CREATE_DELIVERABLE_TABLE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_DELIVERABLE +
                    "(deliverable_id TEXT PRIMARY KEY, " +
                    "initiative_id_fk TEXT," +
                    "deliverable_title TEXT," +
                    "deliverable_description TEXT," +
                    "deliverable_comments TEXT," +
                    "deliverable_status TEXT," +
                    "deliverable_due_date TEXT," +
                    "deliverable_responsible TEXT," +
                    "deliverable_rating TEXT)";

            db.execSQL(CREATE_DELIVERABLE_TABLE);

            //PMO
            String CREATE_PMO_TABLE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_PMO +
                    "(pmo_id TEXT PRIMARY KEY, " +
                    "name TEXT)";

            db.execSQL(CREATE_PMO_TABLE);

        }catch(Exception e) {
            Log.d("deleteTables", e.getMessage());
        }

        //
        // TODO: check if db should be closed
        // if (db!=null) db.close(); //close connection

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        this.db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_INITIATIVE);

        // Create tables again
        onCreate(this.db);

    }

    //Delete from CA Tables
    public void deleteCaTables(){

        //GetWritableDatabase
        db = getWritableDatabase();

        String deleteInitiativeStatement = "delete from " + DATABASE_TABLE_INITIATIVE;
        String deleteWorkItemStatement = "delete from " + DATABASE_TABLE_DELIVERABLE;

        try {
            db.execSQL(deleteWorkItemStatement);
            db.execSQL(deleteInitiativeStatement);

        } catch (Exception e) {
            Log.d("deleteTables", e.getMessage());
        }

        if (db!=null) db.close(); //close connection
    }

}
