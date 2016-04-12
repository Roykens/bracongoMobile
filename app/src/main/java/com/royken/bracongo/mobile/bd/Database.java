package com.royken.bracongo.mobile.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by royken on 07/04/16.
 */
public class Database extends SQLiteOpenHelper {

    private static final String TABLE_BOISSON= "boissons";
    private static final String COL_BID = "id";
    private static final String COL_BIDS = "ids";
    private static final String COL_BNAME = "nom";
    private static final String COL_BISBRAC = "isBracongo";
    private static final String COL_BISBI = "isBi";

    private static String TABLE_MATERIEL = "materiels";
    private static String COL_MID = "id";
    private static String COL_MIDS = "ids";
    private static String COL_MNAME = "nom";

    private static String TABLE_PLV = "plvs";
    private static String COL_PID = "id";
    private static String COL_PIDS = "ids";
    private static String COL_PNAME = "nom";

    private static String TABLE_PDV = "pointdvs";
    private static String COL_PDID = "id";
    private static String COL_PDIDS = "ids";
    private static String COL_PDNAME = "nom";
    private static String COL_PDADRESSE = "adresse";
    private static String COL_PDLONG = "longitude";
    private static String COL_PDLAT = "latitude";
    private static String COL_PDTYPE = "type";
    private static String COL_PDREGIME = "regime";
    private static String COL_PDCATEGORIE = "categorie";


    private static final String CREATE_TABLE_BOISSON = "CREATE TABLE " + TABLE_BOISSON + " ( "+ COL_BID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL_BIDS + " INTEGER, "+ COL_BNAME + " TEXT NOT NULL, "+COL_BISBRAC + " INTEGER,"+COL_BISBI + " INTEGER);";

    private static final String CREATE_TABLE_MATERIEL = "CREATE TABLE " + TABLE_MATERIEL + " ( "+ COL_MID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL_MIDS + " INTEGER, "+ COL_MNAME + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_PLV = "CREATE TABLE " + TABLE_PLV + " ( "+ COL_PID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL_PIDS + " INTEGER, "+ COL_PNAME + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_PDV = "CREATE TABLE " + TABLE_PDV + "("+ COL_PDID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL_PDIDS + " INTEGER, "+ COL_PDNAME + " TEXT NOT NULL, " + COL_PDADRESSE + " TEXT, "+ COL_PDLAT + " REAL, "+ COL_PDLONG + " REAL, "+ COL_PDTYPE + " TEXT, "+ COL_PDCATEGORIE + " TEXT, "+ COL_PDREGIME+" TEXT);";

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_BOISSON);
        db.execSQL(CREATE_TABLE_MATERIEL);
        db.execSQL(CREATE_TABLE_PLV);
        db.execSQL(CREATE_TABLE_PDV);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqlitedatabase, int oldVersion, int newVersion) {
        if(newVersion > oldVersion) {
            recreateDB(sqlitedatabase);
        }
    }


    void recreateDB(SQLiteDatabase sqlitedatabase) {
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_BOISSON);
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_MATERIEL);
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_PLV);
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_PDV);
        onCreate(sqlitedatabase);
    }


}
