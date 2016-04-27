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


    //
    // Defines constants that identify database , tables and columns
    //

    private static final String DATABASE_NAME = "APP_DB";
    private static final int DATABASE_VERSION = 1;

    //INITIATIVE TABLE
    public static String DATABASE_TABLE_INITIATIVE = "initiative";
    public static String KEY_INITIATIVE_idinitiative = "idinitiative";
    public static String KEY_INITIATIVE_title = "title";
    public static String KEY_INITIATIVE_description = "description";

    //DELIVERABLE TABLE
    public static String DATABASE_TABLE_DELIVERABLE = "deliverable";
    public static String KEY_DELIVERABLE_iddeliverable = "iddeliverable";
    public static String KEY_DELIVERBALE_INITIATIVE_idinititative = "idinitiative";
    public static String KEY_DELIVERABLE_title = "title";
    public static String KEY_DELIVERABLE_description = "description";
    public static String KEY_DELIVERABLE_comment = "comments";
    public static String KEY_DELIVERABLE_status = "status";
    public static String KEY_DELIVERABLE_duedate = "duedate";
    public static String KEY_DELIVERABLE_idresponsible = "idresponsible";
    public static String KEY_DELIVERABLE_rating = "rating";
    public static String KEY_DELIVERABLE_ispriority = "ispriority";
    public static String KEY_DELIVERABLE_prioritycomment = "prioritycomment";
    public static String KEY_DELIVERABLE_prioritizedby = "prioritizedby";
    public static String KEY_DELIVERABLE_deliverablevalue = "deliverablevalue";
    public static String KEY_DELIVERABLE_code = "code";
    public static String KEY_DELIVERABLE_currentusername = "currentusername";



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
            String CREATE_INITIATIVE_TABLE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_INITIATIVE + "(" +
                    KEY_INITIATIVE_idinitiative + " TEXT PRIMARY KEY, " +
                    KEY_INITIATIVE_title + " TEXT," +
                    KEY_INITIATIVE_description + " TEXT)";

            db.execSQL(CREATE_INITIATIVE_TABLE);

            //DELIVERABLE
            String CREATE_DELIVERABLE_TABLE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_DELIVERABLE + "(" +
                    KEY_DELIVERABLE_iddeliverable  + " TEXT PRIMARY KEY, " +
                    KEY_DELIVERBALE_INITIATIVE_idinititative  + " TEXT," +
                    KEY_DELIVERABLE_title  + " TEXT," +
                    KEY_DELIVERABLE_description  + " TEXT," +
                    KEY_DELIVERABLE_comment  + " TEXT," +
                    KEY_DELIVERABLE_status  + " TEXT," +
                    KEY_DELIVERABLE_duedate  + " TEXT," +
                    KEY_DELIVERABLE_idresponsible  + " TEXT," +
                    KEY_DELIVERABLE_rating  + " TEXT," +
                    KEY_DELIVERABLE_ispriority  + " TEXT," +
                    KEY_DELIVERABLE_prioritycomment  + " TEXT," +
                    KEY_DELIVERABLE_prioritizedby  + " TEXT," +
                    KEY_DELIVERABLE_deliverablevalue  + " TEXT," +
                    KEY_DELIVERABLE_code  + " TEXT," +
                    KEY_DELIVERABLE_currentusername + " TEXT)";

            db.execSQL(CREATE_DELIVERABLE_TABLE);

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
    public void deleteTables(){

        //GetWritableDatabase
        db = getWritableDatabase();

        String deleteInitiativesStatement = "delete from " + DATABASE_TABLE_INITIATIVE;
        String deleteDeliverablesStatement = "delete from " + DATABASE_TABLE_DELIVERABLE;

        try {
            db.execSQL(deleteDeliverablesStatement);
            db.execSQL(deleteInitiativesStatement);

        } catch (Exception e) {
            Log.d("deleteTables", e.getMessage());
        }

        if (db!=null) db.close(); //close connection
    }

}
