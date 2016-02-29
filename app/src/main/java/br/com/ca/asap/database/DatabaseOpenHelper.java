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

    private static final String DATABASE_NAME = "APP_DB";
    private static final int DATABASE_VERSION = 1;

    //INITIATIVE TABLE
    public static String DATABASE_TABLE_INITIATIVE = "initiative";
    public static String KEY_INITIATIVE_INITIATIVE_ID = "initiative_id";
    public static String KEY_INITIATIVE_TITLE = "title";
    public static String KEY_INITIATIVE_DESCRIPTION = "description";

    //INITIATIVE TABLE
    public static String DATABASE_TABLE_DELIVERABLE = "deliverable";
    public static String KEY_DELIVERABLE_ID = "deliverable_id";
    public static String KEY_INITIATIVE_FK = "initiative_id_fk";
    public static String KEY_DELIVERABLE_TITLE = "title";
    public static String KEY_DELIVERABLE_DESCRIPTION = "description";
    public static String KEY_DELIVERABLE_COMMENTS = "comments";
    public static String KEY_DELIVERABLE_STATUS = "status";
    public static String KEY_DELIVERABLE_DUE_DATE = "due_date";
    public static String KEY_DELIVERABLE_RESPONSIBLE = "responsible";
    public static String KEY_DELIVERABLE_RATING = "rating";

    //PMO TABLE
    public static String DATABASE_TABLE_PMO = "pmo";
    public static String KEY_PMO_ID = "pmo_id";
    public static String KEY_PMO_NAME = "name";

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
                    KEY_INITIATIVE_INITIATIVE_ID + " TEXT PRIMARY KEY, " +
                    KEY_INITIATIVE_TITLE + " TEXT," +
                    KEY_INITIATIVE_DESCRIPTION + " TEXT)";

            db.execSQL(CREATE_INITIATIVE_TABLE);

            //DELIVERABLE
            String CREATE_DELIVERABLE_TABLE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_DELIVERABLE + "(" +
                    KEY_DELIVERABLE_ID + " TEXT PRIMARY KEY, " +
                    KEY_INITIATIVE_FK + " TEXT," +
                    KEY_DELIVERABLE_TITLE + " TEXT," +
                    KEY_DELIVERABLE_DESCRIPTION + " TEXT," +
                    KEY_DELIVERABLE_COMMENTS + " TEXT," +
                    KEY_DELIVERABLE_STATUS + " TEXT," +
                    KEY_DELIVERABLE_DUE_DATE + " TEXT," +
                    KEY_DELIVERABLE_RESPONSIBLE + " TEXT," +
                    KEY_DELIVERABLE_RATING + " TEXT)";

            db.execSQL(CREATE_DELIVERABLE_TABLE);

            //PMO
            String CREATE_PMO_TABLE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_PMO  + "(" +
                    KEY_PMO_ID + " TEXT PRIMARY KEY, " +
                    KEY_PMO_NAME + " TEXT)";

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
