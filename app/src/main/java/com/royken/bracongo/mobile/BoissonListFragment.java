package com.royken.bracongo.mobile;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.royken.bracongo.mobile.adapter.BoissonListAdapter;
import com.royken.bracongo.mobile.adapter.PoinDeVenteCustomAdapter;
import com.royken.bracongo.mobile.dao.BoissonDao;
import com.royken.bracongo.mobile.dao.PointDvao;
import com.royken.bracongo.mobile.entities.Boisson;
import com.royken.bracongo.mobile.entities.projection.BoissonProjection;
import com.royken.bracongo.mobile.entities.projection.ReponseProjection;
import com.royken.bracongo.mobile.util.ReponseService;

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
public class BoissonListFragment extends ListFragment{

    private OnFragmentInteractionListener mListener;

    private List<Boisson> boissonList;

    private SwipeRefreshLayout swipeContainer;

    private ListView listView;

    BoissonListAdapter adapter;

    private List<Boisson> result;


    private BoissonDao boissonDao;

    public static BoissonListFragment newInstance() {
        BoissonListFragment fragment = new BoissonListFragment();
        return fragment;
    }

    public BoissonListFragment() {
        // Required empty public constructor
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        setRetainInstance(true);

        boissonDao = new BoissonDao(getActivity().getApplicationContext());
        boissonList = boissonDao.boissons();

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

        if (boissonList != null) {
            adapter = new BoissonListAdapter(getActivity(), boissonList);
            listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            listView.setAdapter(adapter);
            swipeContainer.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.error_color);

            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    try {
                        refreshContent();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("La liste des Boissons");


        }
    }

    // fake a network operation's delayed response
    // this is just for demonstration, not real code!
    private void refreshContent() throws IOException {
        Retrofit retrofit;
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .serializeNulls()
                .excludeFieldsWithoutExposeAnnotation().create();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
        httpClient.addInterceptor(logging);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.128:8080/")
                //.baseUrl("http://10.0.2.2:8080/")
                        //.addConverterFactory(JacksonConverterFactory.create(mapper))192.168.1.110
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ReponseService service = retrofit.create(ReponseService.class);
        Call<List<Boisson>> call = service.getAllBoisson();
        //List<Boisson> result = call.execute().body();
        //final List<Boisson> result;
        call.enqueue(new Callback<List<Boisson>>() {
            @Override
            public void onResponse(Call<List<Boisson>> call, Response<List<Boisson>> response) {
                Log.i("Result....", response.toString());
                result = response.body();
                boissonDao.clear();
                for (Boisson boisson : result) {
                    boissonDao.insertBoisson(boisson);
                }

                boissonList = boissonDao.boissons();
                adapter = new BoissonListAdapter(getActivity(), boissonList);
                listView.setAdapter(adapter);
                swipeContainer.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<List<Boisson>> call, Throwable t) {

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

}
