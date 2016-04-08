package com.royken.bracongo.mobile.entities;

import java.io.Serializable;

/**
 * Created by royken on 07/04/16.
 */
public class Plv implements Serializable {

    private int id;

    private int idServeur;

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
