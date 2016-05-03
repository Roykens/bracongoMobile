package com.royken.bracongo.mobile;


import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.royken.bracongo.mobile.dao.*;
import com.royken.bracongo.mobile.entities.*;
import com.royken.bracongo.mobile.entities.PointDeVente;
import com.royken.bracongo.mobile.util.AndroidNetworkUtility;
import com.royken.bracongo.mobile.util.WebserviceUtil;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BlankFragment.OnFragmentInteractionListener, ItemFragment.OnFragmentInteractionListener, CardListFragment2.OnFragmentInteractionListener, MapFragment.OnFragmentInteractionListener, TwoFragment.OnFragmentInteractionListener, PlanningFragment.OnFragmentInteractionListener, TestFragment.OnFragmentInteractionListener,PointDeVenteFragment.OnFragmentInteractionListener, PlvFragment.OnFragmentInteractionListener, MaterielFragment.OnFragmentInteractionListener, QuestionFragment.OnFragmentInteractionListener, BoissonListFragment.OnFragmentInteractionListener, MaterielListFragment.OnFragmentInteractionListener, PlvListFragment.OnFragmentInteractionListener, AccueilFragment.OnFragmentInteractionListener, ActionFragment.OnFragmentInteractionListener, CommentFragment.OnFragmentInteractionListener,ParametreFragment.OnFragmentInteractionListener{

    public static final String PREFS_NAME = "com.royken.MyPrefsFile";
    private boolean isValide;
    SharedPreferences settings ;
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
     //   ImageView iv = (ImageView) findViewById(R.id.back);
       // iv.setColorFilter(Color.argb(150, 118, 118, 188), PorterDuff.Mode.SRC_ATOP);

        // img.setI;

  /*      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        boolean hasLoggedIn = settings.getBoolean("com.royken.hasLoggedIn", false);
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
        String title  = "Toto";

        if (id == R.id.nav_accueil) {
            // Handle the camera action
          /*  title = "Accueil";
            fragment = AccueilFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.addToBackStack(null);
           */ //ft.
            //ft.commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if(id == R.id.nav_boisson){
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

    @Override
    public void onFragmentInteraction(String id) {

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
            MaterielDao matDao = new MaterielDao(getApplicationContext());
            PlvDao plvDao = new PlvDao(getApplicationContext());
            Log.i("", "getProducts ......");
           // ArrayList<Product> productList = null;
            //HttpGet httpGet = new HttpGet("http://192.168.43.126:8080/bracongo/api/question");
            HttpGet httpGet = new HttpGet("http://192.168.1.110:8080/bracongo/api/question");

            //setting header to request for a JSON response
            httpGet.setHeader("Accept", "application/json");
            AndroidNetworkUtility httpUtil = new AndroidNetworkUtility();
            String productJSONStr = httpUtil.getHttpResponse(httpGet);
            Log.d("", "Response: " + productJSONStr);

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
                    Log.i("INSERTION","JAI INSERER LELEMENT " + i+1);
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
                    Log.i("INSERTION","JAI INSERER LELEMENT " + i+1);
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
                    Log.i("INSERTION","JAI INSERER LELEMENT " + i+1);
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
                    Log.i("INSERTION","JAI INSERER LELEMENT " + i+1);
                }
                for (int i = 0; i < plvs.length(); i++) {
                    JSONObject object = plvs.getJSONObject(i);
                    Plv plv = new Plv();
                    plv.setNom(object.getString("nom"));
                    plv.setIdServeur(object.getInt("id"));
                    plvDao.insertPlv(plv);
                    Log.i("INSERTION","JAI INSERER LELEMENT " + i+1);
                }
                for (int i = 0; i < materiels.length(); i++) {
                    JSONObject object = materiels.getJSONObject(i);
                    Materiel materiel = new Materiel();
                    materiel.setNom(object.getString("nom"));
                    materiel.setIdServeur(object.getInt("id"));
                    matDao.insertMateriel(materiel);
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
            Dialog.dismiss();
            BoissonDao dao = new BoissonDao(getApplicationContext());
            List<Boisson> boissonList = dao.boissons();
            Log.i("BDTEST DD", boissonList.size()+" elements");
            Toast.makeText(getApplicationContext(),boissonList.size() +" elements",Toast.LENGTH_LONG).show();



        }

    }

    private class PlanningAsyncTask  extends AsyncTask<String, Void, Void> {



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
          //  BoissonDao dao = new BoissonDao(getApplicationContext());
            PointDvao dao = new PointDvao(getApplicationContext());
            dao.clear();
            Log.i("", "getProducts de planning......");
            // ArrayList<Product> productList = null;
            String url = "http://192.168.1.110:8080/bracongo/api/pdv/planning/"+login.trim()+"/"+password.trim();
            HttpGet httpGet = new HttpGet(url);

            //setting header to request for a JSON response
            httpGet.setHeader("Accept", "application/json");
            AndroidNetworkUtility httpUtil = new AndroidNetworkUtility();
            String productJSONStr = httpUtil.getHttpResponse(httpGet);
            Log.d("", "Response: " + productJSONStr);

            try {
                JSONObject obj = new JSONObject(productJSONStr);
                int idPln = obj.getInt("idPlanning");
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("com.royken.idPln", idPln);
                editor.commit();
                JSONArray pdvs = obj.getJSONArray("pointDeVentes");
                Log.i("Taillleeee", pdvs.length()+"");

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

                    dao.insertPdv(pointDeVente);
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
            Dialog.dismiss();

            PointDvao dao = new PointDvao(getApplicationContext());
            List<PointDeVente> pointDeVentes = dao.pointDeVentes();

            Log.i("BDTEST DD", pointDeVentes.size()+" elements");
            Toast.makeText(getApplicationContext(),pointDeVentes.size() +" elements",Toast.LENGTH_LONG).show();



        }

    }
}
