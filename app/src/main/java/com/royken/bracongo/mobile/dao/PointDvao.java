package com.royken.bracongo.mobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.royken.bracongo.mobile.entities.PointDeVente;

import java.util.ArrayList;

/**
 * Created by royken on 08/04/16.
 */
public class PointDvao {

    private Context context;
    private DatabaseConnection connection;
    private SQLiteDatabase db;

    private static String TABLE_PDV = "pointdvs";
    private static String COL_PDID = "id";
    private static final int NUM_COL_PDID = 0;
    private static String COL_PDIDS = "ids";
    private static final int NUM_COL_PDIDS = 1;
    private static String COL_PDNAME = "nom";
    private static final int NUM_COL_PDNAME = 2;
    private static String COL_PDADRESSE = "adresse";
    private static final int NUM_COL_PDADRESSE = 3;
    private static String COL_PDLONG = "longitude";
    private static final int NUM_COL_PDLONG = 4;
    private static String COL_PDLAT = "latitude";
    private static final int NUM_COL_PDLAT = 5;
    private static String COL_PDTYPE = "type";
    private static final int NUM_COL_PDTYPE = 6;
    private static String COL_PDREGIME = "regime";
    private static final int NUM_COL_PDREGIME = 7;
    private static String COL_PDCATEGORIE = "categorie";
    private static final int NUM_COL_PDCATEGORIE = 8;

    public PointDvao(Context context) {
        this.context = context;
        connection = DatabaseConnexionFactory.getConnexion(context);
        db = connection.getBDD();
        if (db == null) {
            Log.i("TEST DE DB DEPUIS CREA", "JE SUIS HYPER NULLLLLLLLLLLLLLLLLLLL");
        } else {
            Log.i("TEST DE DB DEPUIS CREA", "JNOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        }
    }

    public long insertPdv(PointDeVente pointDeVente){
        ContentValues values = new ContentValues();
        values.put(COL_PDIDS, pointDeVente.getIdServeur());
        values.put(COL_PDNAME, pointDeVente.getNom());
        values.put(COL_PDADRESSE, pointDeVente.getAdresse());
        values.put(COL_PDLAT, pointDeVente.getLatitude());
        values.put(COL_PDLONG, pointDeVente.getLongitude());
        values.put(COL_PDCATEGORIE, pointDeVente.getCategorie());
        values.put(COL_PDREGIME, pointDeVente.getRegime());
        values.put(COL_PDTYPE, pointDeVente.getType());
        long result = db.insert(TABLE_PDV, null, values);
        return result;
    }

    public ArrayList<PointDeVente> pointDeVentes() {
        //connection.open();
        ArrayList<PointDeVente> pointDeVentes = new ArrayList<PointDeVente>();
        Cursor mCursor = db.rawQuery("select * from " + TABLE_PDV, null);

        int id1;
        int id2;
        String nom;
        String adresse;
        double lat;
        double longi;
        String type;
        String regime;
        String categorie;
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {

            PointDeVente pointDeVente = new PointDeVente();
            id1 = mCursor.getInt(0);
            id2 = mCursor.getInt(1);
            nom = mCursor.getString(2);
            adresse = mCursor.getString(3);
            lat = mCursor.getDouble(4);
            longi = mCursor.getDouble(5);
            type = mCursor.getString(6);
            regime = mCursor.getString(8);
            categorie = mCursor.getString(7);

            pointDeVente.setId(id1);
            pointDeVente.setIdServeur(id2);
            pointDeVente.setNom(nom);
            pointDeVente.setAdresse(adresse);
            pointDeVente.setLatitude(lat);
            pointDeVente.setLongitude(longi);
            pointDeVente.setType(type);
            pointDeVente.setCategorie(categorie);
            pointDeVente.setRegime(regime);

            pointDeVentes.add(pointDeVente);
        }
        mCursor.close();
        // connection.close();
        return pointDeVentes;
    }

    public void clear(){
        String query = "DELETE  FROM " +TABLE_PDV; ;
        db.execSQL(query);
    }

    public PointDeVente getPointById(int id) {
        Cursor c = db.query(TABLE_PDV, new String[]{COL_PDID, COL_PDIDS, COL_PDNAME, COL_PDADRESSE, COL_PDLAT, COL_PDLONG, COL_PDTYPE, COL_PDCATEGORIE, COL_PDREGIME}, COL_PDID + " = \"" + id + "\"", null, null, null, null);
        return cursorToPointDeVente(c);
    }


    public int deletePointDeVente(int id){
        return  db.delete(TABLE_PDV,COL_PDIDS + " = "+ id,null);
    }

    private PointDeVente cursorToPointDeVente(Cursor c) {
        if (c.getCount() == 0)
            return null;
        c.moveToFirst();
        PointDeVente pointDeVente = new PointDeVente();
        pointDeVente.setId(c.getInt(NUM_COL_PDID));
        pointDeVente.setIdServeur(c.getInt(NUM_COL_PDIDS));
        pointDeVente.setNom(c.getString(NUM_COL_PDNAME));
        pointDeVente.setAdresse(c.getString(NUM_COL_PDADRESSE));
        pointDeVente.setLatitude(c.getDouble(NUM_COL_PDLAT));
        pointDeVente.setLongitude(c.getDouble(NUM_COL_PDLONG));
        pointDeVente.setRegime(c.getString(NUM_COL_PDREGIME));
        pointDeVente.setCategorie(c.getString(NUM_COL_PDCATEGORIE));
        pointDeVente.setType(c.getString(NUM_COL_PDTYPE));
        c.close();
        return pointDeVente;
    }


}
