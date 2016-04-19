package com.royken.bracongo.mobile;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.royken.bracongo.mobile.adapter.BoissonCustomAdapter;
import com.royken.bracongo.mobile.adapter.PlvCustomAdapter;
import com.royken.bracongo.mobile.dao.PlvDao;
import com.royken.bracongo.mobile.entities.Plv;
import com.royken.bracongo.mobile.entities.projection.BoissonProjection;
import com.royken.bracongo.mobile.entities.projection.PlvProjection;
import com.royken.bracongo.mobile.entities.projection.Reponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royken on 15/04/16.
 */
public class PlvFragment extends Fragment implements AdapterView.OnItemClickListener{

    private List<Plv> plvs = new ArrayList<>();
    PlvCustomAdapter plvCustomAdapter;

    private Reponse reponse;
    private OnFragmentInteractionListener mListener;
    private ListView listView;

    public static PlvFragment newInstance() {
        PlvFragment fragment = new PlvFragment();
       // Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
       // fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //  mParam1 = getArguments().getString(ARG_PARAM1);
            // mParam2 = getArguments().getString(ARG_PARAM2);
           // mBiere = getArguments().getBoolean(ARG_BIERE);
           // mbBacongo = getArguments().getBoolean(ARG_BRA);
           // if(!mBiere || !mbBacongo ){
            //    boissonProjections = (List<BoissonProjection>)getArguments().getSerializable(ARG_BOISSON);
            //}
            //else {
             //   title = "Bi Bracongo";
              //  boissonProjections = new ArrayList<>();
            //}

        }
        PlvDao dao = new PlvDao(getActivity().getApplicationContext());
        plvs = dao.plvs();

        setRetainInstance(true);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.bi_bracongo, container, false);
       // TextView tv = (TextView) rootView.findViewById(R.id.toto);
        listView = (ListView)rootView.findViewById(android.R.id.list);

        Button btn = (Button)rootView.findViewById(R.id.btnTest);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reponse = (Reponse) getActivity().getApplicationContext();
                List<PlvProjection> projections = plvCustomAdapter.getPlvProjections();
                if (projections.size() > 0) {
                    for (PlvProjection projection : projections) {
                        reponse.addPlv(projection);
                        Log.i("PLVS", projection.getNom() +" Nombre brac " +projection.getNombreBrac()+ " Nombre Conc " + projection.getNombreConc() + "Etat Brac " + projection.getEtatBrac()+ " Etat Conc " + projection.getEtatConc());
                    }
                }
                Fragment fragment = MaterielFragment.newInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });




        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(plvs != null){
            plvCustomAdapter = new PlvCustomAdapter(getActivity(),plvs);
            listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            listView.setAdapter(plvCustomAdapter);

            //getListView().setOnItemClickListener(this);
        }
      //  ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(title);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("PLVS");

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
