package com.royken.bracongo.mobile.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.royken.bracongo.mobile.entities.Boisson;
import com.royken.bracongo.mobile.entities.Materiel;
import com.royken.bracongo.mobile.entities.Plv;
import com.royken.bracongo.mobile.entities.projection.ReponseProjection;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by royken on 20/04/16.
 */
public interface ReponseService {

    @POST("/bracongo/api/reponse")
    Call<ReponseProjection> envoyerReponse(@Body ReponseProjection reponseProjection);

    @POST("/toto")
    Call<Toto> test(@Body Toto toto);

    @GET("/bracongo/api/boisson")
    Call<List<Boisson>> getAllBoisson();

    @GET("/bracongo/api/materiel")
    Call<List<Materiel>> getAllMateriels();

    @GET("/bracongo/api/plv")
    Call<List<Plv>> getAllPlvs();

    @POST("/3/image")
    void postImage(
            @Header("Authorization") String auth,
            @Query("album") String albumId,
            @Path("image")String name
    );



    public static Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
            .setPrettyPrinting()
            .serializeNulls()
            .excludeFieldsWithoutExposeAnnotation().create();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();


}
