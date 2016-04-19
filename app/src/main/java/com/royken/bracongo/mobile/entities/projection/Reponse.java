package com.royken.bracongo.mobile.entities.projection;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royken on 15/04/16.
 */
public class Reponse extends Application {

    private List<BoissonProjection> boissonProjections;

    private List<PlvProjection> plvProjections;

    private List<MaterielProjection> materielProjections;

    public Reponse(){
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

    }

    public List<PlvProjection> getPlvProjections() {
        return plvProjections;
    }

    public void setPlvProjections(List<PlvProjection> plvProjections) {
        this.plvProjections = plvProjections;
    }
}
