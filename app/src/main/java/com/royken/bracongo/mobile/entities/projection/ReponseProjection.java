package com.royken.bracongo.mobile.entities.projection;

import android.app.Application;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.royken.bracongo.mobile.entities.Action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by royken on 15/04/16.
 */
public class ReponseProjection extends Application {

    @Expose
    @SerializedName("idPdv")
    private int idPdv;

    @Expose
    @SerializedName("idPlanning")
    private Long idPlanning;

    @Expose
    @SerializedName("parcEmballageBracongo")
    private int parcEmballageBracongo;

    @Expose
    @SerializedName("parcEmballageBralima")
    private int parcEmballageBralima;

    @Expose
    @SerializedName("heureVisite")
    private Date heureVisite;

    @Expose
    @SerializedName("joursEcouleVisiteFDVBrac")
    private int joursEcouleVisiteFDVBrac;

    @Expose
    @SerializedName("srdBrac")
    private boolean srdBrac;

    @Expose
    @SerializedName("heurePassageSrdBrac")
    private Date heurePassageSrdBrac;

    @Expose
    @SerializedName("joursEcouleVisiteFDVBral")
    private int joursEcouleVisiteFDVBral;

    @Expose
    @SerializedName("srdBral")
    private boolean srdBral;

    @Expose
    @SerializedName("heurePassageSrdBral")
    private Date heurePassageSrdBral;

    @Expose
    @SerializedName("fifo")
    private boolean fifo;

    @Expose
    @SerializedName("nombrePhn")
    private int nombrePhn;


    @Expose
    @SerializedName("boissonProjections")
    private List<BoissonProjection> boissonProjections;

    @Expose
    @SerializedName("plvProjections")
    private List<PlvProjection> plvProjections;

    @Expose
    @SerializedName("materielProjections")
    private List<MaterielProjection> materielProjections;

    @Expose
    @SerializedName("action")
    private Action action;

    @Expose
    @SerializedName("commentaire")
    private String commentaire;

    public ReponseProjection(){
        if(boissonProjections == null) {
            boissonProjections = new ArrayList<>();
        }
        if(plvProjections == null){
            plvProjections = new ArrayList<>();
        }
        if(materielProjections == null){
            materielProjections = new ArrayList<>();
        }
    }

    public List<BoissonProjection> getBoissonProjections() {
        return boissonProjections;
    }

    public void setBoissonProjections(List<BoissonProjection> boissonProjections) {
        this.boissonProjections = boissonProjections;
    }

    public void addBoisson(BoissonProjection boissonProjection){
        boissonProjections.add(boissonProjection);
    }

    public void addPlv(PlvProjection plvProjection){
        plvProjections.add(plvProjection);
    }

    public void addMateriel(MaterielProjection materielProjection){
        materielProjections.add(materielProjection);
    }

    public void init(){

        boissonProjections = new ArrayList<>();
        plvProjections = new ArrayList<>();
        materielProjections = new ArrayList<>();
        action = new Action();

    }

    public List<PlvProjection> getPlvProjections() {
        return plvProjections;
    }

    public void setPlvProjections(List<PlvProjection> plvProjections) {
        this.plvProjections = plvProjections;
    }

    public List<MaterielProjection> getMaterielProjections() {
        return materielProjections;
    }

    public void setMaterielProjections(List<MaterielProjection> materielProjections) {
        this.materielProjections = materielProjections;
    }

    public int getIdPdv() {
        return idPdv;
    }

    public Long getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(Long idPlanning) {
        this.idPlanning = idPlanning;
    }

    public void setIdPdv(int idPdv) {
        this.idPdv = idPdv;
    }

    public int getParcEmballageBracongo() {
        return parcEmballageBracongo;
    }

    public void setParcEmballageBracongo(int parcEmballageBracongo) {
        this.parcEmballageBracongo = parcEmballageBracongo;
    }

    public int getParcEmballageBralima() {
        return parcEmballageBralima;
    }

    public void setParcEmballageBralima(int parcEmballageBralima) {
        this.parcEmballageBralima = parcEmballageBralima;
    }

    public Date getHeureVisite() {
        return heureVisite;
    }

    public void setHeureVisite(Date heureVisite) {
        this.heureVisite = heureVisite;
    }

    public int getJoursEcouleVisiteFDVBrac() {
        return joursEcouleVisiteFDVBrac;
    }

    public void setJoursEcouleVisiteFDVBrac(int joursEcouleVisiteFDVBrac) {
        this.joursEcouleVisiteFDVBrac = joursEcouleVisiteFDVBrac;
    }

    public boolean isSrdBrac() {
        return srdBrac;
    }

    public void setSrdBrac(boolean srdBrac) {
        this.srdBrac = srdBrac;
    }

    public Date getHeurePassageSrdBrac() {
        return heurePassageSrdBrac;
    }

    public void setHeurePassageSrdBrac(Date heurePassageSrdBrac) {
        this.heurePassageSrdBrac = heurePassageSrdBrac;
    }

    public int getJoursEcouleVisiteFDVBral() {
        return joursEcouleVisiteFDVBral;
    }

    public void setJoursEcouleVisiteFDVBral(int joursEcouleVisiteFDVBral) {
        this.joursEcouleVisiteFDVBral = joursEcouleVisiteFDVBral;
    }

    public boolean isSrdBral() {
        return srdBral;
    }

    public void setSrdBral(boolean srdBral) {
        this.srdBral = srdBral;
    }

    public Date getHeurePassageSrdBral() {
        return heurePassageSrdBral;
    }

    public void setHeurePassageSrdBral(Date heurePassageSrdBral) {
        this.heurePassageSrdBral = heurePassageSrdBral;
    }

    public boolean isFifo() {
        return fifo;
    }

    public void setFifo(boolean fifo) {
        this.fifo = fifo;
    }

    public int getNombrePhn() {
        return nombrePhn;
    }

    public void setNombrePhn(int nombrePhn) {
        this.nombrePhn = nombrePhn;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
