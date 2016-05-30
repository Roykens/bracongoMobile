package com.royken.bracongo.mobile.entities.projection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.royken.bracongo.mobile.entities.PointDeVente;

import java.io.Serializable;
import java.util.List;

/**
 * Created by royken on 30/05/16.
 */
public class PlanningEnquetteur implements Serializable {

    @Expose(serialize = false, deserialize = true)
    @SerializedName("idPlanning")
    private Long idPlanning;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("pointDeVentes")
    private List<PointDeVente> pointDeVentes;

    public Long getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(Long idPlanning) {
        this.idPlanning = idPlanning;
    }

    public List<PointDeVente> getPointDeVentes() {
        return pointDeVentes;
    }

    public void setPointDeVentes(List<PointDeVente> pointDeVentes) {
        this.pointDeVentes = pointDeVentes;
    }

    @Override
    public String toString() {
        return "PlanningEnquetteur{" +
                "idPlanning=" + idPlanning +
                ", pointDeVentes=" + pointDeVentes +
                '}';
    }
}
