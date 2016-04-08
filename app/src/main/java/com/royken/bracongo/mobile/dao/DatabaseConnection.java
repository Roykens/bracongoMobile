package com.royken.bracongo.mobile.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.royken.bracongo.mobile.bd.Database;

/**
 * Created by royken on 07/04/16.
 */
public class DatabaseConnection {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "bracongoSurvey.db";

    private static SQLiteDatabase bdd;

    private static Database maBaseSQLite;

    public DatabaseConnection(Context context){
        maBaseSQLite = new Database(context, NOM_BDD, null, VERSION_BDD);
    }

    public static void open(){
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return maBaseSQLite.getWritableDatabase();
    }

}
