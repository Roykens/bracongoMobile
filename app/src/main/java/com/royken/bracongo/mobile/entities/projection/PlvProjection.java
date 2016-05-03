package com.royken.bracongo.mobile.entities.projection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by royken on 15/04/16.
 */
public class PlvProjection implements Serializable{

    @Expose(serialize = false)
    @SerializedName("nom")
    private String nom;

    @Expose
    @SerializedName("idServeur")
    private int idServeur;

    @Expose
    @SerializedName("nombreBrac")
    private String nombreBrac;

    @Expose
    @SerializedName("nombreConc")
    private String nombreConc;

    @Expose
    @SerializedName("etatBrac")
    private String etatBrac;

    @Expose
    @SerializedName("etatConc")
    private String etatConc;

    public PlvProjection() {

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public PlvProjection(String nom, int idServeur, String nombreBrac, String nombreConc, String etatBrac, String etatConc) {
        this.nom = nom;
        this.idServeur = idServeur;
        this.nombreBrac = nombreBrac;
        this.nombreConc = nombreConc;
        this.etatBrac = etatBrac;
        this.etatConc = etatConc;
    }

    public PlvProjection(String nom, int idServeur, String nombreBrac, String etatBrac) {
        this.nom = nom;
        this.idServeur = idServeur;
        this.nombreBrac = nombreBrac;
        this.etatBrac = etatBrac;
    }

    public int getIdServeur() {
        return idServeur;
    }

    public void setIdServeur(int idServeur) {
        this.idServeur = idServeur;
    }

    public String getNombreBrac() {
        return nombreBrac;
    }

    public void setNombreBrac(String nombreBrac) {
        this.nombreBrac = nombreBrac;
    }

    public String getEtatBrac() {
        return etatBrac;
    }

    public void setEtatBrac(String etatBrac) {
        this.etatBrac = etatBrac;
    }

    public String getNombreConc() {
        return nombreConc;
    }

    public void setNombreConc(String nombreConc) {
        this.nombreConc = nombreConc;
    }

    public String getEtatConc() {
        return etatConc;
    }

    public void setEtatConc(String etatConc) {
        this.etatConc = etatConc;
    }

    @Override
    public String toString() {
        return "PlvProjection{" +
                "nom='" + nom + '\'' +
                ", idServeur=" + idServeur +
                ", nombreBrac='" + nombreBrac + '\'' +
                ", nombreConc='" + nombreConc + '\'' +
                ", etatBrac='" + etatBrac + '\'' +
                ", etatConc='" + etatConc + '\'' +
                '}';
    }
}
