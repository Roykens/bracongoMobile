package com.royken.bracongo.mobile;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.royken.bracongo.mobile.dao.*;
import com.royken.bracongo.mobile.entities.*;
import com.royken.bracongo.mobile.entities.PointDeVente;
import com.royken.bracongo.mobile.util.AndroidNetworkUtility;
import com.royken.bracongo.mobile.util.RetrofitBuiler;
import com.royken.bracongo.mobile.util.WebserviceUtil;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.List;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MapFragment.OnFragmentInteractionListener, TwoFragment.OnFragmentInteractionListener, PlanningFragment.OnFragmentInteractionListener,PointDeVenteFragment.OnFragmentInteractionListener, PlvFragment.OnFragmentInteractionListener, MaterielFragment.OnFragmentInteractionListener, QuestionFragment.OnFragmentInteractionListener, BoissonListFragment.OnFragmentInteractionListener, MaterielListFragment.OnFragmentInteractionListener, PlvListFragment.OnFragmentInteractionListener, AccueilFragment.OnFragmentInteractionListener, ActionFragment.OnFragmentInteractionListener, CommentFragment.OnFragmentInteractionListener,ParametreFragment.OnFragmentInteractionListener{

    public static final String PREFS_NAME = "com.royken.MyPrefsFile";
    private boolean isValide;
    SharedPreferences settings ;
    SharedPreferences.Editor editor;
    String login;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
       setSupportActionBar(toolbar);
        ImageView img= (ImageView) findViewById(R.id.imageView);
       // getActionBar().setIcon(R.drawable.user1);
       // getSupportActionBar().setIcon(R.drawable.user1);
        img.setImageResource(R.drawable.user1);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        TextView txt = (TextView)findViewById(R.id.name);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        boolean hasLoggedIn = settings.getBoolean("com.royken.hasLoggedIn", false);
        String nom = settings.getString("com.royken.login","");
        txt.setText(nom);
        if(hasLoggedIn == false){
            try{

                login = settings.getString("com.royken.login","");
                password = settings.getString("com.royken.password","");
                AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
                if (androidNetworkUtility.isConnected(this)) {
                    Log.i("Test Connection", "Connected.");
                    new LongOperation().execute();
                    new PlanningAsyncTask().execute();
                    editor.putBoolean("com.royken.hasLoggedIn", true);
                    editor.commit();
                } else {
                    Toast.makeText(getApplicationContext(),"Erreur de reseau",Toast.LENGTH_LONG).show();
                    Log.v("Test Connecion", "Network not Available!");
                }
            }catch (Exception e){}
        }
       // LongOperation longo = new LongOperation();
       // longo.execute();
      //  PlanningAsyncTask asc = new PlanningAsyncTask();

   /*     try{

            AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
            if (androidNetworkUtility.isConnected(this)) {
                Log.i("Test Connection", "Connected.");
                new LongOperation().execute();
                new PlanningAsyncTask().execute();
            } else {
                Log.v("Test Connecion", "Network not Available!");
            }
        }catch (Exception e){}
    */
        Fragment fragment = PlanningFragment.newInstance("","");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame,fragment);
        ft.addToBackStack(null);
        ft.commit();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Planning");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment ;
        String title  = "";

         if(id == R.id.nav_boisson){
            fragment = BoissonListFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);

            ft.addToBackStack(null);
            ft.commit();

        }else if(id == R.id.nav_mat){
            fragment = MaterielListFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        }else if(id == R.id.nav_plv){
            fragment = PlvListFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        }

        else if (id == R.id.nav_my_map) {
            title = "Ma position";
            fragment = MapFragment.newInstance("","");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        else if(id == R.id.nav_planning){
            title = "Planning";
            fragment = PlanningFragment.newInstance("","");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame,fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        else if(id == R.id.nav_manage){
            title = "Paramètres";
            fragment = ParametreFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame,fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        else if(id == R.id.nav_deconnexion){
            deconnexion();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }




    private class LongOperation  extends AsyncTask<String, Void, Void> {



        // Required initialization

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);
        String data ="";


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //Start Progress Dialog (Message)

            Dialog.setMessage("Please wait..");
            Dialog.show();

        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            BoissonDao dao = new BoissonDao(getApplicationContext());
            dao.clear();
            MaterielDao matDao = new MaterielDao(getApplicationContext());
            matDao.clear();
            PlvDao plvDao = new PlvDao(getApplicationContext());
            plvDao.clear();
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String url = settings.getString("com.royken.url", "");

            HttpGet httpGet = new HttpGet(url+"/bracongo/api/question");

            httpGet.setHeader("Accept", "application/json");
            AndroidNetworkUtility httpUtil = new AndroidNetworkUtility();
            String productJSONStr = httpUtil.getHttpResponse(httpGet);

            try {
                JSONObject obj = new JSONObject(productJSONStr);
                JSONArray biBrac = obj.getJSONArray("biereBracongo");
                JSONArray bgBrac = obj.getJSONArray("bgBracongo");
                JSONArray biBral = obj.getJSONArray("bierreBralima");
                JSONArray bgBral =  obj.getJSONArray("bgBralima");
                JSONArray plvs = obj.getJSONArray("plvs");
                JSONArray materiels = obj.getJSONArray("materiels");
                for (int i = 0; i < biBrac.length(); i++) {
                    JSONObject object = biBrac.getJSONObject(i);
                    Boisson boisson = new Boisson();
                    boisson.setIdServeur(object.getInt("idFormatBoisson"));
                    boisson.setPrix(object.getInt("prix"));
                    boisson.setNom(object.getString("nomFormat"));
                    boisson.setIsBracongo(true);
                    boisson.setIsBi(true);
                    dao.insertBoisson(boisson);
                }
                for (int i = 0; i < bgBrac.length(); i++) {
                    JSONObject object = bgBrac.getJSONObject(i);
                    Boisson boisson = new Boisson();
                    boisson.setIdServeur(object.getInt("idFormatBoisson"));
                    boisson.setNom(object.getString("nomFormat"));
                    boisson.setPrix(object.getInt("prix"));
                    boisson.setIsBracongo(true);
                    boisson.setIsBi(false);
                    dao.insertBoisson(boisson);
                }
                for (int i = 0; i < biBral.length(); i++) {
                    JSONObject object = biBral.getJSONObject(i);
                    Boisson boisson = new Boisson();
                    boisson.setIdServeur(object.getInt("idFormatBoisson"));
                    boisson.setNom(object.getString("nomFormat"));
                    boisson.setPrix(object.getInt("prix"));
                    boisson.setIsBracongo(false);
                    boisson.setIsBi(true);
                    dao.insertBoisson(boisson);
                }
                for (int i = 0; i < bgBral.length(); i++) {
                    JSONObject object = bgBral.getJSONObject(i);
                    Boisson boisson = new Boisson();
                    boisson.setIdServeur(object.getInt("idFormatBoisson"));
                    boisson.setNom(object.getString("nomFormat"));
                    boisson.setPrix(object.getInt("prix"));
                    boisson.setIsBracongo(false);
                    boisson.setIsBi(false);
                    dao.insertBoisson(boisson);
                }
                for (int i = 0; i < plvs.length(); i++) {
                    JSONObject object = plvs.getJSONObject(i);
                    Plv plv = new Plv();
                    plv.setNom(object.getString("nom"));
                    plv.setIdServeur(object.getInt("id"));
                    plvDao.insertPlv(plv);
                }
                for (int i = 0; i < materiels.length(); i++) {
                    JSONObject object = materiels.getJSONObject(i);
                    Materiel materiel = new Materiel();
                    materiel.setNom(object.getString("nom"));
                    materiel.setIdServeur(object.getInt("id"));
                    matDao.insertMateriel(materiel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(Void unused) {
            Dialog.dismiss();

        }

    }


    private class PlanningAsyncTask  extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);
        String data ="";
        protected void onPreExecute() {

            Dialog.setMessage("Veuillez patientez...");
            Dialog.show();

        }

        protected Void doInBackground(String... urls) {

            PointDvao dao = new PointDvao(getApplicationContext());
            dao.clear();
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String url1 = settings.getString("com.royken.url","");
            String url = url1+"/bracongo/api/pdv/planning/"+login.trim()+"/"+password.trim();
            HttpGet httpGet = new HttpGet(url);


            httpGet.setHeader("Accept", "application/json");
            AndroidNetworkUtility httpUtil = new AndroidNetworkUtility();
            String productJSONStr = httpUtil.getHttpResponse(httpGet);

            try {
                JSONObject obj = new JSONObject(productJSONStr);
                int idPln = obj.getInt("idPlanning");
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("com.royken.idPln", idPln);
                editor.commit();
                JSONArray pdvs = obj.getJSONArray("pointDeVentes");

                for (int i = 0; i < pdvs.length(); i++) {
                    JSONObject object = pdvs.getJSONObject(i);
                    PointDeVente pointDeVente = new PointDeVente();
                    pointDeVente.setNom(object.getString("nom"));
                    pointDeVente.setType(object.getString("typePdv"));
                    pointDeVente.setCategorie(object.getString("typeCategorie"));
                    pointDeVente.setRegime(object.getString("typeRegime"));
                    pointDeVente.setIdServeur(object.getInt("id"));
                    if(object.has("latitude")){
                        pointDeVente.setLatitude(object.getDouble("latitude"));
                    }
                    else {
                        pointDeVente.setLatitude(-4.3275949);
                    }
                    if(object.has("longitude")){
                        pointDeVente.setLongitude(object.getDouble("longitude"));
                    }
                    else {
                        pointDeVente.setLongitude(15.341604);
                    }
                    pointDeVente.setAdresse(object.getString("adresse"));

                    dao.insertPdv(pointDeVente);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(Void unused) {
            Dialog.dismiss();

        }

    }

    private void deconnexion(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Déconnexion");

        alertDialogBuilder
                .setMessage("Êtes vous sûr de vouloir vous déconnecter?")
                .setCancelable(false)
                .setPositiveButton("OUI",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        editor.putString("com.royken.login", "");
                        editor.putString("com.royken.password","");
                        editor.putBoolean("com.royken.hasLoggedIn", false);
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this,
                                LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("NON",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }
}
