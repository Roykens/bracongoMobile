package com.royken.bracongo.mobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.royken.bracongo.mobile.R;
import com.royken.bracongo.mobile.entities.Boisson;

import java.util.ArrayList;

/**
 * Created by royken on 07/04/16.
 */
public class BoissonDao {

    private static final String TABLE_BOISSON = "boissons";
    private static final String COL_BID = "id";
    private static final int NUM_COL_BID = 0;
    private static final String COL_BIDS = "ids";
    private static final int NUM_COL_BIDS = 1;
    private static final String COL_BNAME = "nom";
    private static final int NUM_COL_BNAME = 2;
    private static final String COL_BISBRAC = "isBracongo";
    private static final int NUM_COL_BISBRAC = 4;
    private static final String COL_BISBI = "isBi";
    private static final int NUM_COL_BISBI = 5;
    private static final String COL_BPRIX = "prix";
    private static final int NUL_COL_BPRIX = 3;

    private Context context;
    private DatabaseConnection connection;
    private SQLiteDatabase db;

    public BoissonDao(Context context) {
        this.context = context;
        connection = DatabaseConnexionFactory.getConnexion(context);
        db = connection.getBDD();
        if (db == null) {
            Log.i("TEST DE DB DEPUIS CREA", "JE SUIS HYPER NULLLLLLLLLLLLLLLLLLLL");
        } else {
            Log.i("TEST DE DB DEPUIS CREA", "JNOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        }
    }

    public void clear(){
        String query = "DELETE  FROM " +TABLE_BOISSON; ;
        db.execSQL(query);
    }

    public long insertBoisson(Boisson boisson) {
        //   connection.open();
        if (db == null) {
            Log.i("TEST DE DB", "JE SUIS HYPER NULLLLLLLLLLLLLLLLLLLL");
        } else {
            Log.i("TEST DE DB", "JNOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        }
        ContentValues values = new ContentValues();
        values.put(COL_BIDS, boisson.getIdServeur());
        values.put(COL_BNAME, boisson.getNom());
        values.put(COL_BPRIX, boisson.getPrix());
        if (boisson.getIsBi()) {
            values.put(COL_BISBI, 1);
        } else {
            values.put(COL_BISBI, 0);
        }
        if (boisson.getIsBracongo()) {
            values.put(COL_BISBRAC, 1);
        } else {
            values.put(COL_BISBRAC, 0);
        }
        long result = db.insert(TABLE_BOISSON, null, values);
        // connection.close();
        return result;
    }

    public Boisson getBoissonById(int id) {
        Cursor c = db.query(TABLE_BOISSON, new String[]{COL_BID, COL_BIDS, COL_BNAME,COL_BPRIX ,COL_BISBRAC, COL_BISBI}, COL_BID + " = \"" + id + "\"", null, null, null, null);
        return cursorToBoisson(c);
    }

    public ArrayList<Boisson> getBoissonsByCriteria(boolean isBracongo,boolean isBiere){
        ArrayList<Boisson> boissons = new ArrayList<>();
        Cursor mCursor = db.rawQuery("select * from " + TABLE_BOISSON +" where " +COL_BISBRAC + " = " + (isBracongo ? 1:0) + " and " + COL_BISBI + " = " + (isBiere ? 1:0), null);
        int id1;
        int id2;
        int isBra;
        int isBi;
        int prix;
        String nom;
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            Boisson boisson = new Boisson();
            id1 = mCursor.getInt(0);
            id2 = mCursor.getInt(1);
            nom = mCursor.getString(2);
            prix = mCursor.getInt(3);
            isBra = mCursor.getInt(4);
            isBi = mCursor.getInt(5);
            boisson.setNom(nom);
            boisson.setPrix(prix);
            boisson.setIdServeur(id2);
            boisson.setId(id1);
            if (isBra == 1) {
                boisson.setIsBracongo(true);
            } else {
                boisson.setIsBracongo(false);
            }
            if (isBi == 1) {
                boisson.setIsBi(true);
            } else {
                boisson.setIsBi(false);
            }
            boissons.add(boisson);
        }
        mCursor.close();
        // connection.close();
        return boissons;

    }

    public ArrayList<Boisson> boissons() {
        //connection.open();
        ArrayList<Boisson> boissons = new ArrayList<Boisson>();
        Cursor mCursor = db.rawQuery("select * from " + TABLE_BOISSON, null);

        int id1;
        int id2;
        int isBra;
        int isBi;
        int prix;
        String nom;
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            Boisson boisson = new Boisson();
            id1 = mCursor.getInt(0);
            id2 = mCursor.getInt(1);
            nom = mCursor.getString(2);
            prix = mCursor.getInt(3);
            isBra = mCursor.getInt(4);
            isBi = mCursor.getInt(5);
            boisson.setNom(nom);
            boisson.setPrix(prix);
            boisson.setIdServeur(id2);
            boisson.setId(id1);
            if (isBra == 1) {
                boisson.setIsBracongo(true);
            } else {
                boisson.setIsBracongo(false);
            }
            if (isBi == 1) {
                boisson.setIsBi(true);
            } else {
                boisson.setIsBi(false);
            }
            boissons.add(boisson);
        }
        mCursor.close();
        // connection.close();
        return boissons;
    }


    private Boisson cursorToBoisson(Cursor c) {
        if (c.getCount() == 0)
            return null;
        c.moveToFirst();
        Boisson boisson = new Boisson();
        boisson.setId(c.getInt(NUM_COL_BID));
        boisson.setIdServeur(c.getInt(NUM_COL_BIDS));
        boisson.setNom(c.getString(NUM_COL_BNAME));
        boisson.setPrix(c.getInt(NUL_COL_BPRIX));
        int bi = c.getInt(NUM_COL_BISBI);
        if (bi == 1) {
            boisson.setIsBi(true);
        } else {
            boisson.setIsBi(false);
        }
        int bra = c.getInt(NUM_COL_BISBRAC);
        if (bra == 1) {
            boisson.setIsBracongo(true);
        } else {
            boisson.setIsBracongo(false);
        }
        c.close();
        return boisson;
    }


}
