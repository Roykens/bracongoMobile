package com.royken.bracongo.mobile.dao;

import android.content.Context;

/**
 * Created by royken on 07/04/16.
 */
public class DatabaseConnexionFactory {

    private static DatabaseConnection connexion ;


    public static DatabaseConnection getConnexion(Context context){
        connexion = new DatabaseConnection(context);
        return connexion;
    }
}