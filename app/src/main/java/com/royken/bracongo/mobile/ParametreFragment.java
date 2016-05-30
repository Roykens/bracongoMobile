package com.royken.bracongo.mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.royken.bracongo.mobile.adapter.BoissonListAdapter;
import com.royken.bracongo.mobile.entities.projection.ReponseProjection;

import java.io.IOException;

/**
 * Created by royken on 03/05/16.
 */
public class ParametreFragment extends Fragment {

    public static final String PREFS_NAME = "com.royken.MyPrefsFile";
    private boolean isValide;
    SharedPreferences settings ;
    String url;
    String login;
    String password;
    private Button btn;
    private EditText urlTxt;
    private EditText loginTx;
    private EditText passTxt;
    SharedPreferences.Editor editor;
    private OnFragmentInteractionListener mListener;

    public static ParametreFragment newInstance() {
        ParametreFragment fragment = new ParametreFragment();

        return fragment;
    }

    public ParametreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.parametre_fragment, container, false);
        urlTxt = (EditText) rootView.findViewById(R.id.txtUrl);
        loginTx = (EditText)rootView.findViewById(R.id.txtlogin);
        passTxt = (EditText)rootView.findViewById(R.id.txtpass);
        btn = (Button)rootView.findViewById(R.id.btn_parametre);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("com.royken.login", loginTx.getText().toString().trim());
                editor.putString("com.royken.password",passTxt.getText().toString().trim());
                editor.putString("com.royken.url",urlTxt.getText().toString().trim());
                editor.commit();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        login = settings.getString("com.royken.login", "");
        password = settings.getString("com.royken.password","");
        url = settings.getString("com.royken.url","");
        loginTx.setText(login);
        passTxt.setText(password);
        urlTxt.setText(url);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Paramètres");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("");

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
