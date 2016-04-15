package com.royken.bracongo.mobile.entities.projection;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royken on 15/04/16.
 */
public class Reponse extends Application {

    private List<BoissonProjection> boissonProjections;

    public Reponse(){
        if(boissonProjections == null) {
            boissonProjections = new ArrayList<>();
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

    public void init(){
        boissonProjections = new ArrayList<>();
    }
}
