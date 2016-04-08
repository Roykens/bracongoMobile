package com.royken.bracongo.mobile;


import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.royken.bracongo.mobile.dao.BoissonDao;
import com.royken.bracongo.mobile.entities.Boisson;
import com.royken.bracongo.mobile.util.AndroidNetworkUtility;
import com.royken.bracongo.mobile.util.WebserviceUtil;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BlankFragment.OnFragmentInteractionListener, ItemFragment.OnFragmentInteractionListener, CardListFragment2.OnFragmentInteractionListener, MapFragment.OnFragmentInteractionListener, TwoFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       // LongOperation longo = new LongOperation();
       // longo.execute();
        AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
        if (androidNetworkUtility.isConnected(this)) {
            Log.i("Test Connection", "Connected.");
            new LongOperation().execute();
        } else {
            Log.v("Test Connecion", "Network not Available!");
        }
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

        if (id == R.id.nav_camara) {
            // Handle the camera action
            title = "One";
            fragment = BlankFragment.newInstance("one", "two");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //android.app.FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
          //  ft.remove();

            ft.replace(R.id.mainFrame, fragment);
            ft.addToBackStack(null);
            //ft.
            ft.commit();
        } else if (id == R.id.nav_gallery) {
            title = "Two";
            fragment = TwoFragment.newInstance("","");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.addToBackStack(null);

            ft.commit();
        } else if (id == R.id.nav_slideshow) {
            title = "List";
            fragment = CardListFragment2.newInstance("one","two");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_manage) {
            title = "Map";
            fragment = MapFragment.newInstance("","");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            fragment = MapFragment.newInstance("one","two");
           // MapFragment fragment = new MapFragment();
            transaction.replace(R.id.mainFrame, fragment);
            transaction.commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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

            Log.i("", "getProducts ......");
           // ArrayList<Product> productList = null;
            HttpGet httpGet = new HttpGet("http://10.0.2.2:8080/bracongo/api/question");

            //setting header to request for a JSON response
            httpGet.setHeader("Accept", "application/json");
            AndroidNetworkUtility httpUtil = new AndroidNetworkUtility();
            String productJSONStr = httpUtil.getHttpResponse(httpGet);
            Log.d("", "Response: " + productJSONStr);

            try {
                JSONObject obj = new JSONObject(productJSONStr);
                JSONArray biBrac = obj.getJSONArray("biereBracongo");
                for (int i = 0; i < biBrac.length(); i++) {
                    JSONObject object = biBrac.getJSONObject(i);
                    Boisson boisson = new Boisson();
                    boisson.setIdServeur(object.getInt("idFormatBoisson"));
                    boisson.setNom(object.getString("nomFormat"));
                    boisson.setIsBracongo(true);
                    boisson.setIsBi(true);
                    dao.insertBoisson(boisson);
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
}
