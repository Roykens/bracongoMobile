package com.royken.bracongo.mobile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.royken.bracongo.mobile.adapter.BoissonListAdapter;
import com.royken.bracongo.mobile.adapter.MaterielListAdapter;
import com.royken.bracongo.mobile.dao.BoissonDao;
import com.royken.bracongo.mobile.dao.MaterielDao;
import com.royken.bracongo.mobile.entities.Boisson;
import com.royken.bracongo.mobile.entities.Materiel;
import com.royken.bracongo.mobile.util.AndroidNetworkUtility;
import com.royken.bracongo.mobile.util.ReponseService;
import com.royken.bracongo.mobile.util.RetrofitBuiler;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by royken on 28/04/16.
 */
public class MaterielListFragment extends ListFragment {

    public static final String PREFS_NAME = "com.royken.MyPrefsFile";
    private OnFragmentInteractionListener mListener;

    private SwipeRefreshLayout swipeContainer;

    private ListView listView;

    private MaterielListAdapter adapter;

    private List<Materiel> materielList;

    private MaterielDao materielDao;

    public static MaterielListFragment newInstance() {
        MaterielListFragment fragment = new MaterielListFragment();
        return fragment;
    }

    public MaterielListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        setRetainInstance(true);
        materielDao = new MaterielDao(getActivity().getApplicationContext());
        materielList = materielDao.materiels();

        // setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.list_boisson, container, false);
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.boisson_list_layout);

        listView = (ListView) rootView.findViewById(android.R.id.list);
        listView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (materielList != null) {
            adapter = new MaterielListAdapter(getActivity(), materielList);
            listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            listView.setAdapter(adapter);
            swipeContainer.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.error_color);

            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    try {
                        AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
                        if (!androidNetworkUtility.isConnected(getActivity())) {
                            //    Toast.makeText(getActivity(), "Aucune connexion au serveur. Veuillez reéssayer plus tard", Toast.LENGTH_LONG).show();
                            //    swipeContainer.setRefreshing(false);
                        } else {
                            refreshContent();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("La liste des Materiels");


        }
    }

    // fake a network operation's delayed response
    // this is just for demonstration, not real code!
    private void refreshContent() throws IOException {
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String url = settings.getString("com.royken.url","");

        Retrofit retrofit = RetrofitBuiler.getRetrofit(url+"/");
        ReponseService service = retrofit.create(ReponseService.class);
        Call<List<Materiel>> call = service.getAllMateriels();
        //List<Boisson> result = call.execute().body();
        //final List<Boisson> result;
        call.enqueue(new Callback<List<Materiel>>() {
            @Override
            public void onResponse(Call<List<Materiel>> call, Response<List<Materiel>> response) {
                Log.i("Result....", response.toString());
               List<Materiel> result = response.body();
                materielDao.clear();
                for (Materiel materiel : result) {
                    materielDao.insertMateriel(materiel);
                }

                materielList = materielDao.materiels();
                adapter = new MaterielListAdapter(getActivity(), materielList);
                listView.setAdapter(adapter);
                swipeContainer.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<List<Materiel>> call, Throwable t) {

                Log.i("Error...", t.toString());
            }


        });



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
