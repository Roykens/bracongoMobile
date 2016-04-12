package com.royken.bracongo.mobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.royken.bracongo.mobile.entities.Plv;

import java.util.ArrayList;

/**
 * Created by royken on 08/04/16.
 */
public class PlvDao {

    private static String TABLE_PLV = "plvs";
    private static String COL_PID = "id";
    private static final int NUM_COL_PID = 0;
    private static String COL_PIDS = "ids";
    private static final int NUM_COL_PIDS = 1;
    private static String COL_PNAME = "nom";
    private static final int NUM_COL_PNAME= 2;

    private Context context;
    private DatabaseConnection connection;
    private SQLiteDatabase db;

    public PlvDao(Context context) {
        this.context = context;
        connection = DatabaseConnexionFactory.getConnexion(context);
        db = connection.getBDD();
        if (db == null) {
            Log.i("TEST DE DB DEPUIS CREA", "JE SUIS HYPER NULLLLLLLLLLLLLLLLLLLL");
        } else {
            Log.i("TEST DE DB DEPUIS CREA", "JNOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        }
    }

    public long insertPlv(Plv plv){
        ContentValues values = new ContentValues();
        values.put(COL_PIDS, plv.getIdServeur());
        values.put(COL_PNAME, plv.getNom());
        long result = db.insert(TABLE_PLV, null, values);
        return result;
    }

    public ArrayList<Plv> plvs() {
        //connection.open();
        ArrayList<Plv> plvs = new ArrayList<Plv>();
        Cursor mCursor = db.rawQuery("select * from " + TABLE_PLV, null);

        int id1;
        int id2;
        String nom;
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            Plv plv = new Plv();
            id1 = mCursor.getInt(0);
            id2 = mCursor.getInt(1);
            nom = mCursor.getString(2);

            plv.setNom(nom);
            plv.setIdServeur(id2);
            plv.setId(id1);

            plvs.add(plv);
        }
        mCursor.close();
        // connection.close();
        return plvs;
    }

    public Plv getPlvById(int id) {
        Cursor c = db.query(TABLE_PLV, new String[]{COL_PID, COL_PIDS, COL_PNAME}, COL_PID + " = \"" + id + "\"", null, null, null, null);
        return cursorToPlv(c);
    }

    private Plv cursorToPlv(Cursor c) {
        if (c.getCount() == 0)
            return null;
        c.moveToFirst();
        Plv plv = new Plv();
        plv.setId(c.getInt(NUM_COL_PID));
        plv.setIdServeur(c.getInt(NUM_COL_PIDS));
        plv.setNom(c.getString(NUM_COL_PNAME));

        c.close();
        return plv;
    }


}
