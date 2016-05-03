package com.royken.bracongo.mobile.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by royken on 30/04/16.
 */
public class Action implements Serializable {

    @Expose(serialize = true)
    @SerializedName("besoinDeContrat")
    private boolean besoinDeContrat;

    @Expose(serialize = true)
    @SerializedName("besoinRenouvellementContrat")
    private boolean besoinRenouvellementContrat;

    @Expose(serialize = true)
    @SerializedName("contratPartiel")
    private boolean contratPartiel;

    @Expose(serialize = true)
    @SerializedName("reclamationRemise")
    private boolean reclamationRemise;

    @Expose(serialize = true)
    @SerializedName("fermeNonOperationel")
    private boolean fermeNonOperationel;

    @Expose(serialize = true)
    @SerializedName("mixteSollicitantCoversion")
    private boolean mixteSollicitantCoversion;

    @Expose(serialize = true)
    @SerializedName("besoinOperation3Bac1")
    private boolean besoinOperation3Bac1;

    @Expose(serialize = true)
    @SerializedName("nombreBacs")
    private int nombreBacs;

    @Expose(serialize = true)
    @SerializedName("demenageSansPrevenir")
    private boolean demenageSansPrevenir;

    @Expose(serialize = true)
    @SerializedName("renforcerEnCapacite")
    private boolean renforcerEnCapacite;

    @Expose(serialize = true)
    @SerializedName("besoinPlv")
    private boolean besoinPlv;

    @Expose(serialize = true)
    @SerializedName("besoinConsignationEmballage")
    private boolean besoinConsignationEmballage;

    @Expose(serialize = true)
    @SerializedName("adresseErronee")
    private boolean adresseErronee;

    @Expose(serialize = true)
    @SerializedName("besoin5ChaisesContre1")
    private boolean besoin5ChaisesContre1;

    @Expose(serialize = true)
    @SerializedName("phnCapsule")
    private boolean phnCapsule;

    public boolean isBesoinDeContrat() {
        return besoinDeContrat;
    }

    public void setBesoinDeContrat(boolean besoinDeContrat) {
        this.besoinDeContrat = besoinDeContrat;
    }

    public boolean isBesoinRenouvellementContrat() {
        return besoinRenouvellementContrat;
    }

    public void setBesoinRenouvellementContrat(boolean besoinRenouvellementContrat) {
        this.besoinRenouvellementContrat = besoinRenouvellementContrat;
    }

    public boolean isContratPartiel() {
        return contratPartiel;
    }

    public void setContratPartiel(boolean contratPartiel) {
        this.contratPartiel = contratPartiel;
    }

    public boolean isReclamationRemise() {
        return reclamationRemise;
    }

    public void setReclamationRemise(boolean reclamationRemise) {
        this.reclamationRemise = reclamationRemise;
    }

    public boolean isFermeNonOperationel() {
        return fermeNonOperationel;
    }

    public void setFermeNonOperationel(boolean fermeNonOperationel) {
        this.fermeNonOperationel = fermeNonOperationel;
    }

    public boolean isMixteSollicitantCoversion() {
        return mixteSollicitantCoversion;
    }

    public void setMixteSollicitantCoversion(boolean mixteSollicitantCoversion) {
        this.mixteSollicitantCoversion = mixteSollicitantCoversion;
    }

    public boolean isBesoinOperation3Bac1() {
        return besoinOperation3Bac1;
    }

    public void setBesoinOperation3Bac1(boolean besoinOperation3Bac1) {
        this.besoinOperation3Bac1 = besoinOperation3Bac1;
    }

    public int getNombreBacs() {
        return nombreBacs;
    }

    public void setNombreBacs(int nombreBacs) {
        this.nombreBacs = nombreBacs;
    }

    public boolean isDemenageSansPrevenir() {
        return demenageSansPrevenir;
    }

    public void setDemenageSansPrevenir(boolean demenageSansPrevenir) {
        this.demenageSansPrevenir = demenageSansPrevenir;
    }

    public boolean isRenforcerEnCapacite() {
        return renforcerEnCapacite;
    }

    public void setRenforcerEnCapacite(boolean renforcerEnCapacite) {
        this.renforcerEnCapacite = renforcerEnCapacite;
    }

    public boolean isBesoinPlv() {
        return besoinPlv;
    }

    public void setBesoinPlv(boolean besoinPlv) {
        this.besoinPlv = besoinPlv;
    }

    public boolean isBesoinConsignationEmballage() {
        return besoinConsignationEmballage;
    }

    public void setBesoinConsignationEmballage(boolean besoinConsignationEmballage) {
        this.besoinConsignationEmballage = besoinConsignationEmballage;
    }

    public boolean isAdresseErronee() {
        return adresseErronee;
    }

    public void setAdresseErronee(boolean adresseErronee) {
        this.adresseErronee = adresseErronee;
    }

    public boolean isBesoin5ChaisesContre1() {
        return besoin5ChaisesContre1;
    }

    public void setBesoin5ChaisesContre1(boolean besoin5ChaisesContre1) {
        this.besoin5ChaisesContre1 = besoin5ChaisesContre1;
    }

    public boolean isPhnCapsule() {
        return phnCapsule;
    }

    public void setPhnCapsule(boolean phnCapsule) {
        this.phnCapsule = phnCapsule;
    }
}
