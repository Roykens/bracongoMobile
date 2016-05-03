package com.royken.bracongo.mobile.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by royken on 07/04/16.
 */
public class Plv implements Serializable {

    @Expose(serialize = false, deserialize = false)
    private int id;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("id")
    private int idServeur;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("nom")
    private String nom;

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
}
