package com.royken.bracongo.mobile.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by royken on 07/04/16.
 */
public class PointDeVente implements Serializable {


    private int id;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("id")
    private int idServeur;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("nom")
    private String nom;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("adresse")
    private String adresse;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("latitude")
    private double latitude;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("longitude")
    private double longitude;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("typeCategorie")
    private String categorie;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("typeRegime")
    private String regime;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("typePdv")
    private String type;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdServeur() {
        return idServeur;
    }

    public void setIdServeur(int idServeur) {
        this.idServeur = idServeur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getRegime() {
        return regime;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PointDeVente{" +
                "id=" + id +
                ", idServeur=" + idServeur +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", categorie='" + categorie + '\'' +
                ", regime='" + regime + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
