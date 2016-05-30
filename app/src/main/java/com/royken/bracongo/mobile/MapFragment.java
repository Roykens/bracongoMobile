package com.royken.bracongo.mobile;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MapView mMapView;
    private GoogleMap mMap;

    private TextView locationText;
    private TextView addressText;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
       // SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
         //       .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate and return the layout
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        locationText = (TextView)v.findViewById(R.id.location);
        addressText = (TextView)v.findViewById(R.id.address);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();// needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }



        mMap = mMapView.getMap();
        // Updates the location and zoom of the MapView
     //   CameraPosition cameraPosition = new CameraPosition.Builder()
       //         .target(new LatLng(17.385044, 78.486671)).zoom(12).build();
       // mMap.animateCamera(CameraUpdateFactory
         //       .newCameraPosition(cameraPosition));



        // Enable Zoom
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        //set Map TYPE
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //enable Current location Button
        mMap.setMyLocationEnabled(true);

        //set "listener" for changing my location
        mMap.setOnMyLocationChangeListener(myLocationChangeListener());


       // mMap.getUiSettings().setCompassEnabled(true);
       // LatLng pdv = new LatLng(latitude, longitude);
      //  LatLng moi = new LatLng(-4.3257419,15.3384682);
      //  Marker hamburg = mMap.addMarker(new MarkerOptions().position(moi)
      //          .title("toto").snippet("tata"));

        // Perform any camera updates here
        return v;
    }

    public GoogleMap.OnMyLocationChangeListener myLocationChangeListener() {
        return new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();

                Marker marker;
                marker = mMap.addMarker(new MarkerOptions().position(loc));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                locationText.setText("Vos Coordonnées [" + longitude + " ; " + latitude + " ]");

                //get current address by invoke an AsyncTask object
                new GetAddressTask().execute(String.valueOf(latitude), String.valueOf(longitude));
            }
        };
    }
    public void callBackDataFromAsyncTask(String address) {
        addressText.setText(address);
    }

    public class GetAddressTask extends AsyncTask<String, Void, String> {


        public GetAddressTask() {
            super();

        }

        @Override
        protected String doInBackground(String... params) {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getActivity(), Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(Double.parseDouble(params[0]), Double.parseDouble(params[1]), 1);

                //get current Street name
                String address = addresses.get(0).getAddressLine(0);

                //get current province/City
                String province = addresses.get(0).getAdminArea();

                //get country
                String country = addresses.get(0).getCountryName();

                //get postal code
                String postalCode = addresses.get(0).getPostalCode();

                //get place Name
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                return "Rue : " + address + "\n" + "Commune/Ville : " + province + "\nPays : " + country
                        + "\nCODE Postal : " + postalCode + "\n" + "Lieu: " + knownName;

            } catch (IOException ex) {
                ex.printStackTrace();
                return "IOE EXCEPTION";

            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                return "IllegalArgument Exception";
            }

        }

        /**
         * When the task finishes, onPostExecute() call back data to Activity UI and displays the address.
         * @param address
         */
        @Override
        protected void onPostExecute(String address) {
            // Call back Data and Display the current address in the UI
            callBackDataFromAsyncTask(address);
        }
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }



}
