package com.royken.bracongo.mobile.entities.projection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by royken on 17/04/16.
 */
public class MaterielProjection implements Serializable {

    @Expose
    @SerializedName("idServeur")
    private int idServeur;

    @Expose
    @SerializedName("nom")
    private String nom;

    @Expose
    @SerializedName("nombreBrac")
    private String nombreBrac;

    @Expose
    @SerializedName("EtatBrac")
    private String EtatBrac;

    @Expose
    @SerializedName("nombreCon")
    private String nombreCon;

    @Expose
    @SerializedName("EtatConc")
    private String EtatConc;

    @Expose
    @SerializedName("nombreCasseBrac")
    private String nombreCasseBrac;

    @Expose
    @SerializedName("nombreCasseConc")
    private String nombreCasseConc;

    public MaterielProjection(int idServeur, String nom, String nombreBrac, String etatBrac, String nombreCon, String etatConc, String nombreCasseBrac, String nombreCasseConc) {
        this.idServeur = idServeur;
        this.nom = nom;
        this.nombreBrac = nombreBrac;
        EtatBrac = etatBrac;
        this.nombreCon = nombreCon;
        EtatConc = etatConc;
        this.nombreCasseBrac = nombreCasseBrac;
        this.nombreCasseConc = nombreCasseConc;
    }

    public MaterielProjection() {
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

    public String getNombreBrac() {
        return nombreBrac;
    }

    public void setNombreBrac(String nombreBrac) {
        this.nombreBrac = nombreBrac;
    }

    public String getEtatBrac() {
        return EtatBrac;
    }

    public void setEtatBrac(String etatBrac) {
        EtatBrac = etatBrac;
    }

    public String getNombreCon() {
        return nombreCon;
    }

    public void setNombreCon(String nombreCon) {
        this.nombreCon = nombreCon;
    }

    public String getEtatConc() {
        return EtatConc;
    }

    public void setEtatConc(String etatConc) {
        EtatConc = etatConc;
    }

    public String getNombreCasseBrac() {
        return nombreCasseBrac;
    }

    public void setNombreCasseBrac(String nombreCasseBrac) {
        this.nombreCasseBrac = nombreCasseBrac;
    }

    public String getNombreCasseConc() {
        return nombreCasseConc;
    }

    public void setNombreCasseConc(String nombreCasseConc) {
        this.nombreCasseConc = nombreCasseConc;
    }

    @Override
    public String toString() {
        return "MaterielProjection{" +
                "idServeur=" + idServeur +
                ", nom='" + nom + '\'' +
                ", nombreBrac='" + nombreBrac + '\'' +
                ", EtatBrac='" + EtatBrac + '\'' +
                ", nombreCon='" + nombreCon + '\'' +
                ", EtatConc='" + EtatConc + '\'' +
                ", nombreCasseBrac='" + nombreCasseBrac + '\'' +
                ", nombreCasseConc='" + nombreCasseConc + '\'' +
                '}';
    }
}
