package com.royken.bracongo.mobile.entities.projection;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by royken on 14/04/16.
 */

public class BoissonProjection implements Parcelable, Serializable{

    private String nom;
    private int idSrveur;
    private String prix;
    private String stock;
    private boolean disponible;

    public BoissonProjection() {

    }

    public BoissonProjection(String nom, int idSrveur, String prix, String stock, boolean disponible) {
        this.nom = nom;
        this.idSrveur = idSrveur;
        this.prix = prix;
        this.stock = stock;
        this.disponible = disponible;
    }

    public static final Creator<BoissonProjection> CREATOR = new Creator<BoissonProjection>() {
        @Override
        public BoissonProjection createFromParcel(Parcel in) {
            return new BoissonProjection(in);
        }

        @Override
        public BoissonProjection[] newArray(int size) {
            return new BoissonProjection[size];
        }
    };

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdSrveur() {
        return idSrveur;
    }

    public void setIdSrveur(int idSrveur) {
        this.idSrveur = idSrveur;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

 /*   public BoissonProjection(Parcel in){
        String[] data = new String[5];

        in.readStringArray(data);
        this.id = data[0];
        this.name = data[1];
        this.grade = data[2];
    }
*/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.v("Boisson", "writeToParcel..." + flags);
        dest.writeInt(idSrveur);
        dest.writeString(prix);
        dest.writeString(stock);
        dest.writeByte((byte) (disponible ? 1 : 0));

    }

    public BoissonProjection(Parcel source){
            /*
             * Reconstruct from the Parcel
             */
        Log.v("", "ParcelData(Parcel source): time to put back parcel data");
        idSrveur = source.readInt();
        prix = source.readString();
        stock = source.readString();
        disponible = source.readByte() != 0;
        //source.readStringArray(cities);
    }

    @Override
    public int describeContents(){
        return 0;
    }
}
