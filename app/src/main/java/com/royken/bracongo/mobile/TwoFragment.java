package com.royken.bracongo.mobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.royken.bracongo.mobile.adapter.BoissonCustomAdapter;
import com.royken.bracongo.mobile.dao.BoissonDao;
import com.royken.bracongo.mobile.dummy.DummyContent;
import com.royken.bracongo.mobile.entities.Boisson;
import com.royken.bracongo.mobile.entities.projection.BoissonProjection;
import com.royken.bracongo.mobile.util.WebserviceUtil;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TwoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<Boisson> boissons1 = new ArrayList<>();
    BoissonCustomAdapter boissonCustomAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView listView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TwoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TwoFragment newInstance(String param1, String param2) {
        TwoFragment fragment = new TwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setRetainInstance(true);
        BoissonDao dao = new BoissonDao(getActivity().getApplicationContext());
         boissons1 = dao.boissons();


       // CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(getActivity(), R.layout.fragment_card_list);

        //listView = (ListView)fi;
      //  setListAdapter(boissonCustomAdapter);
        Log.i("TESTDEPASSAGEDEDONEES ",mParam1);
        //Toast.makeText(getActivity().getApplicationContext(),mParam1,Toast.LENGTH_LONG);
        // TODO: Change Adapter to display your content
        //   setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
        //         android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.bi_bracongo, container, false);
        TextView tv = (TextView) rootView.findViewById(R.id.toto);
        listView = (ListView)rootView.findViewById(android.R.id.list);


        Button btn = (Button)rootView.findViewById(R.id.btnTest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText prix = null;
                EditText stock = null;
                TextView nom = null;
                List<BoissonProjection> boissonProjections = boissonCustomAdapter.getBoissonProjections();
                if(boissonProjections.size() > 0){
                    for (BoissonProjection projection:boissonProjections){
                        Log.i("TEST LISTES 1", projection.getNom() + " prix " + projection.getPrix() + " stock"+ projection.getStock());
                    }
                }
/*
                for (int i = 0; i < listView.getCount(); i++) {
                    if(listView.getChildAt(i) != null) {
                        nom = (TextView) listView.getChildAt(i).findViewById(R.id.textView2);
                        prix = (EditText) listView.getChildAt(i).findViewById(R.id.txtPrix);
                        stock = (EditText) listView.getChildAt(i).findViewById(R.id.txtStock);
                    }
                    if (nom!=null && prix != null) {
                        Log.i("Test LISTES",nom.getText().toString() + " prix " + prix.getText().toString() + " stock " + stock.getText().toString());
                    }
                }
                */
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(boissons1 != null){
            boissonCustomAdapter = new BoissonCustomAdapter(getActivity(),boissons1);
            listView.setAdapter(boissonCustomAdapter);

            //getListView().setOnItemClickListener(this);
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
/*
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
           // mListener.onFragmentInteraction(boissons1.get(position).getId());
        }
    }
*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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



}
