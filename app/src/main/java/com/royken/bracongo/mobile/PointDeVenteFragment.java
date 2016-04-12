package com.royken.bracongo.mobile;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.royken.bracongo.mobile.entities.PointDeVente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royken on 12/04/16.
 */
public class PointDeVenteFragment extends Fragment{

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

    private OnFragmentInteractionListener mListener;

    MapView mMapView;
    private GoogleMap mMap;

    public static PointDeVenteFragment newInstance(int id, int ids, String nom, String adresse, double longitude, double latitude,String type, String regime, String categorie) {
        PointDeVenteFragment fragment = new PointDeVenteFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_ID,id);
        args.putInt(ARG_IDS,ids);
        args.putString(ARG_NOM, nom);
        args.putString(ARG_ADRESSE,adresse);
        args.putDouble(ARG_LATITUDE,latitude);
        args.putDouble(ARG_LONGITUDE,longitude);
        args.putString(ARG_TYPE,type);
        args.putString(ARG_CATEGORIE,categorie);
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.point_de_vente_detail, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.pdvMap);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMap = mMapView.getMap();
        LatLng pdv = new LatLng(latitude, longitude);
        Marker hamburg = mMap.addMarker(new MarkerOptions().position(pdv)
                .title(nom).snippet(adresse));
 /*       Marker kiel = mMap.addMarker(new MarkerOptions()
                .position(KIEL)
                .title("Kiel")
                .snippet("Kiel is cool"));
*/
        // Move the camera instantly to hamburg with a zoom of 15.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pdv, 15));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.pdvBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return rootView;
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
        public void onFragmentInteraction(Uri uri);
    }
}
