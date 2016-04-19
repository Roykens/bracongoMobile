package com.royken.bracongo.mobile.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.royken.bracongo.mobile.MainActivity;
import com.royken.bracongo.mobile.dao.PointDvao;
import com.royken.bracongo.mobile.entities.PointDeVente;
import com.royken.bracongo.mobile.util.AndroidNetworkUtility;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by royken on 18/04/16.
 */
public class PlanningAsyncTask extends AsyncTask<Object, Void, Void> {

    // Required initialization

    private final HttpClient Client = new DefaultHttpClient();
    private String Content;
    private String Error = null;
 //   Activity act = (Activity)
   // private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);
    String data ="";


    protected void onPreExecute() {
        // NOTE: You can call UI Element here.

        //Start Progress Dialog (Message)

     //   Dialog.setMessage("Please wait..");
      //  Dialog.show();

    }

    // Call after onPreExecute method
    protected Void doInBackground(Object... urls) {
        //  BoissonDao dao = new BoissonDao(getApplicationContext());
      //  PointDvao dao = new PointDvao(getApplicationContext());
        Log.i("", "getProducts de planning......");
        // ArrayList<Product> productList = null;
        HttpGet httpGet = new HttpGet("http://192.168.43.126:8080/bracongo/api/pdv/551");

        //setting header to request for a JSON response
        httpGet.setHeader("Accept", "application/json");
        AndroidNetworkUtility httpUtil = new AndroidNetworkUtility();
        String productJSONStr = httpUtil.getHttpResponse(httpGet);
        Log.d("", "Response: " + productJSONStr);

        try {
            JSONObject obj = new JSONObject(productJSONStr);
            JSONArray pdvs = obj.getJSONArray("pointDeVentes");

            for (int i = 0; i < pdvs.length(); i++) {
                JSONObject object = pdvs.getJSONObject(i);
                PointDeVente pointDeVente = new PointDeVente();
                pointDeVente.setNom(object.getString("nom"));
                pointDeVente.setType(object.getString("typePdv"));
                pointDeVente.setCategorie(object.getString("typeCategorie"));
                pointDeVente.setRegime(object.getString("typeRegime"));
                pointDeVente.setIdServeur(object.getInt("id"));
                pointDeVente.setLatitude(object.getDouble("latitude"));
                pointDeVente.setLongitude(object.getDouble("longitude"));
                pointDeVente.setAdresse(object.getString("adresse"));

        //        dao.insertPdv(pointDeVente);
                Log.i("INSERTION","JAI INSERER LELEMENT " + i+1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    protected void onPostExecute(Void unused) {
        // NOTE: You can call UI Element here.
        Log.i("Fin","J'ai fini");
        // Close progress dialog
       // Dialog.dismiss();

    }

}

