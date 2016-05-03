package com.royken.bracongo.mobile.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by royken on 07/04/16.
 */
public class Boisson implements Serializable {

    @Expose(serialize = false, deserialize = false)
    private int id;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("idFormatBoisson")
    private int idServeur;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("prix")
    private int prix;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("nomFormat")
    private String nom;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("bracongo")
    private boolean isBracongo;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("biere")
    private boolean isBi;

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

    public boolean getIsBracongo() {
        return isBracongo;
    }

    public void setIsBracongo(boolean isBracongo) {
        this.isBracongo = isBracongo;
    }

    public boolean getIsBi() {
        return isBi;
    }

    public void setIsBi(boolean isBi) {
        this.isBi = isBi;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public boolean isBracongo() {
        return isBracongo;
    }

    public boolean isBi() {
        return isBi;
    }

    @Override
    public String toString() {
        return "Boisson{" +
                "id=" + id +
                ", idServeur=" + idServeur +
                ", nom='" + nom + '\'' +
                ", isBracongo=" + isBracongo +
                ", isBi=" + isBi +
                '}';
    }
}
