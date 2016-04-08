package com.royken.bracongo.mobile.entities;

import java.io.Serializable;

/**
 * Created by royken on 07/04/16.
 */
public class Boisson implements Serializable {

    private int id;

    private int idServeur;

    private String nom;

    private boolean isBracongo;

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
