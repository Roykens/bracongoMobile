package com.royken.bracongo.mobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.royken.bracongo.mobile.entities.Boisson;
import com.royken.bracongo.mobile.entities.Materiel;

import java.util.ArrayList;

/**
 * Created by royken on 07/04/16.
 */
public class MaterielDao {
    private static String TABLE_MATERIEL = "materiels";
    private static String COL_MID = "id";
    private static final int NUM_COL_MID = 0;
    private static String COL_MIDS = "ids";
    private static final int NUM_COL_MIDS = 1;
    private static String COL_MNAME = "nom";
    private static final int NUM_COL_MNAME = 3;


    private Context context;
    private DatabaseConnection connection;
    private SQLiteDatabase db;

    public MaterielDao(Context context) {
        this.context = context;
        connection = DatabaseConnexionFactory.getConnexion(context);
        db = connection.getBDD();
        if (db == null) {
            Log.i("TEST DE DB DEPUIS CREA", "JE SUIS HYPER NULLLLLLLLLLLLLLLLLLLL");
        } else {
            Log.i("TEST DE DB DEPUIS CREA", "JNOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        }
    }

    public long insertMateriel(Materiel materiel){
        ContentValues values = new ContentValues();
        values.put(COL_MIDS, materiel.getIdServeur());
        values.put(COL_MNAME, materiel.getNom());
        long result = db.insert(TABLE_MATERIEL, null, values);
        return result;
    }


    public ArrayList<Materiel> materiels() {
        //connection.open();
        ArrayList<Materiel> materiels = new ArrayList<Materiel>();
        Cursor mCursor = db.rawQuery("select * from " + TABLE_MATERIEL, null);

        int id1;
        int id2;
        String nom;
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            Materiel materiel = new Materiel();
            id1 = mCursor.getInt(0);
            id2 = mCursor.getInt(1);
            nom = mCursor.getString(2);

            materiel.setNom(nom);
            materiel.setIdServeur(id2);
            materiel.setId(id1);

            materiels.add(materiel);
        }
        mCursor.close();
        // connection.close();
        return materiels;
    }

    public Materiel getMaterielById(int id) {
        Cursor c = db.query(TABLE_MATERIEL, new String[]{COL_MID, COL_MIDS, COL_MNAME}, COL_MID + " = \"" + id + "\"", null, null, null, null);
        return cursorToMateriel(c);
    }

    private Materiel cursorToMateriel(Cursor c) {
        if (c.getCount() == 0)
            return null;
        c.moveToFirst();
        Materiel materiel = new Materiel();
        materiel.setId(c.getInt(NUM_COL_MID));
        materiel.setIdServeur(c.getInt(NUM_COL_MIDS));
        materiel.setNom(c.getString(NUM_COL_MNAME));

        c.close();
        return materiel;
    }
}
