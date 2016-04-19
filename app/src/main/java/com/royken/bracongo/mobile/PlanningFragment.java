package com.royken.bracongo.mobile;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.royken.bracongo.mobile.adapter.PoinDeVenteCustomAdapter;
import com.royken.bracongo.mobile.dao.PointDvao;
import com.royken.bracongo.mobile.entities.Boisson;
import com.royken.bracongo.mobile.entities.PointDeVente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royken on 11/04/16.
 */
public class PlanningFragment extends ListFragment implements AdapterView.OnItemClickListener {

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TwoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanningFragment newInstance(String param1, String param2) {
        PlanningFragment fragment = new PlanningFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static PlanningFragment newInstance(int id, int ids, String nom, String adresse, double longitude, double latitude,String type, String regime, String categorie) {
        PlanningFragment fragment = new PlanningFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_ID,id);
        args.putInt(ARG_IDS,ids);
        args.putString(ARG_NOM, nom);
        args.putString(ARG_ADRESSE, adresse);
        args.putDouble(ARG_LATITUDE, latitude);
        args.putDouble(ARG_LONGITUDE, longitude);
        args.putString(ARG_TYPE, type);
        args.putString(ARG_CATEGORIE, categorie);
        args.putString(ARG_REGIME, regime);
        fragment.setArguments(args);
        return fragment;
    }

    public PlanningFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        setRetainInstance(true);
        PointDvao dao = new PointDvao(getActivity().getApplicationContext());
        pointDeVentes = dao.pointDeVentes();
        PoinDeVenteCustomAdapter boissonCustomAdapter = new PoinDeVenteCustomAdapter(getActivity(),pointDeVentes);

        // CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(getActivity(), R.layout.fragment_card_list);

        setHasOptionsMenu(true);
        setListAdapter(boissonCustomAdapter);
        // TODO: Change Adapter to display your content
        //   setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
        //         android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS));

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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
     //   Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();

                Fragment fragment = PointDeVenteFragment.newInstance(pointDeVentes.get(position).getId(),pointDeVentes.get(position).getIdServeur(),pointDeVentes.get(position).getNom(),pointDeVentes.get(position).getAdresse(),pointDeVentes.get(position).getLongitude(),pointDeVentes.get(position).getLatitude(),pointDeVentes.get(position).getType(),pointDeVentes.get(position).getRegime(),pointDeVentes.get(position).getCategorie());
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame,fragment);
                ft.addToBackStack(null);
                ft.commit();

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            // mListener.onFragmentInteraction(boissons1.get(position).getId());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.planning_actualise, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.actualisePlanning:
                Toast.makeText(getActivity().getApplicationContext(),"Du courage mon type",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
