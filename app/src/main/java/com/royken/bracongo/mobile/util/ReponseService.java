package com.royken.bracongo.mobile.util;

import com.royken.bracongo.mobile.entities.projection.ReponseProjection;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by royken on 20/04/16.
 */
public interface ReponseService {

    @POST("/bracongo/api/reponse")
    Call<ReponseProjection> envoyerReponse(@Body ReponseProjection reponseProjection);

    @POST("/toto")
    Call<Toto> test(@Body Toto toto);
}
