package com.royken.bracongo.mobile;


import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.royken.bracongo.mobile.entities.PointDeVente;
import com.royken.bracongo.mobile.entities.projection.ReponseProjection;
import com.royken.bracongo.mobile.util.GMapV2Direction;
import com.royken.bracongo.mobile.util.GMapV2DirectionAsyncTask;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by royken on 12/04/16.
 */
public class PointDeVenteFragment extends Fragment  implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_ID = "id";
    private static final String ARG_IDS = "ids";
    private static final String ARG_NOM = "nom";
    private static final String ARG_ADRESSE = "adresse";
    private static final String ARG_LATITUDE = "latitude";
    private static final String ARG_LONGITUDE = "longitude";
    private static final String ARG_TYPE = "type";
    private static final String ARG_REGIME = "regime";
    private static final String ARG_CATEGORIE = "categorie";

    private List<PointDeVente> pointDeVentes = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int id;
    private int ids;
    private String nom;
    private String adresse;
    private double latitude;
    private double longitude;
    private String type;
    private String categorie;
    private String regime;
    LatLng moi;
    LatLng pdv;

    private OnFragmentInteractionListener mListener;

    MapView mMapView;
    private GoogleMap mMap;

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    String provider;
    protected String lat,lon;
    protected boolean gps_enabled,network_enabled;

    Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    TextView adressetv;


    public static PointDeVenteFragment newInstance(int id, int ids, String nom, String adresse, double longitude, double latitude,String type, String regime, String categorie) {
        PointDeVenteFragment fragment = new PointDeVenteFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_ID, id);
        args.putInt(ARG_IDS, ids);
        args.putString(ARG_NOM, nom);
        args.putString(ARG_ADRESSE,adresse);
        args.putDouble(ARG_LATITUDE,latitude);
        args.putDouble(ARG_LONGITUDE, longitude);
        args.putString(ARG_TYPE, type);
        args.putString(ARG_CATEGORIE, categorie);
        args.putString(ARG_REGIME, regime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
            ids = getArguments().getInt(ARG_IDS);
            nom = getArguments().getString(ARG_NOM);
            adresse = getArguments().getString(ARG_ADRESSE);
            latitude = getArguments().getDouble(ARG_LATITUDE);
            longitude = getArguments().getDouble(ARG_LONGITUDE);
            type = getArguments().getString(ARG_TYPE);
            regime = getArguments().getString(ARG_REGIME);
            categorie = getArguments().getString(ARG_CATEGORIE);
            ReponseProjection reponseProjection = (ReponseProjection) getActivity().getApplicationContext();
            reponseProjection.init();
            reponseProjection.setIdPdv(ids);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.point_de_vente_detail, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.pdvMap);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMap = mMapView.getMap();

        //locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);

        // Enable Zoom
        if(mMap != null) {
            mMap.getUiSettings().setZoomGesturesEnabled(true);
            //set Map TYPE
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            //enable Current location Button
            mMap.setMyLocationEnabled(true);
        }
        /////////////////

        buildGoogleApiClient();
        adressetv = (TextView)rootView.findViewById(R.id.adresse);
        adressetv.setText(adresse);
        Button fab = (Button) rootView.findViewById(R.id.pdvBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = QuestionFragment.newInstance("","");
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame, fragment);
                ft.addToBackStack(null);

                ft.commit();
              }
        });

        return rootView;
    }



    @Override
    public void onLocationChanged(Location location){
        mMap.clear();
        moi = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(moi)
                .title("Moi")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        Marker pdvMarker = mMap.addMarker(new MarkerOptions().position(pdv)
                .title(nom).snippet(adresse));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moi, 15));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        route(moi, pdv, "");

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    protected void route(LatLng sourcePosition, LatLng destPosition, String mode) {

        final  Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                try {
                    Document doc = (Document) msg.obj;
                    GMapV2Direction md = new GMapV2Direction();
                    ArrayList<LatLng> directionPoint = md.getDirection(doc);
                    PolylineOptions rectLine = new PolylineOptions().width(5).color(getActivity().getResources().getColor(R.color.accent_material_dark));

                    for (int i = 0; i < directionPoint.size(); i++) {
                        rectLine.add(directionPoint.get(i));
                    }
                    Polyline polylin = mMap.addPolyline(rectLine);
                    md.getDurationText(doc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };

        new GMapV2DirectionAsyncTask(handler, sourcePosition, destPosition, GMapV2Direction.MODE_DRIVING).execute();
    }

   /* public GoogleMap.OnMyLocationChangeListener myLocationChangeListener() {
        return new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();

                Marker marker;
                marker = mMap.addMarker(new MarkerOptions().position(loc));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));

            }
        };
    }

    */

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {


        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
       // mLocationRequest.setInterval(300); // Update location every second

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lat = String.valueOf(mLastLocation.getLatitude());
            lon = String.valueOf(mLastLocation.getLongitude());

        }

        pdv = new LatLng(latitude, longitude);
        if(mLastLocation != null) {
            moi = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
        else {
            moi = new LatLng(-4.3277457, 15.3415218);
        }
        mMap.addMarker(new MarkerOptions()
                .position(moi)
                .title("Moi")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        Marker pdvMarker = mMap.addMarker(new MarkerOptions().position(pdv)
                .title(nom).snippet(adresse));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moi, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        route(moi, pdv, "");
       // updateUI();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onConnectionSuspended(int i) {

    }
}
