package com.royken.bracongo.mobile;

import android.app.Activity;
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
import com.royken.bracongo.mobile.adapter.PlvListAdapter;
import com.royken.bracongo.mobile.dao.BoissonDao;
import com.royken.bracongo.mobile.dao.PlvDao;
import com.royken.bracongo.mobile.entities.Boisson;
import com.royken.bracongo.mobile.entities.Materiel;
import com.royken.bracongo.mobile.entities.Plv;
import com.royken.bracongo.mobile.util.AndroidNetworkUtility;
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
public class PlvListFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;

    private List<Plv> plvList;

    private SwipeRefreshLayout swipeContainer;

    private ListView listView;

    PlvListAdapter adapter;

    private PlvDao plvDao;

    public static PlvListFragment newInstance() {
        PlvListFragment fragment = new PlvListFragment();
        return fragment;
    }

    public PlvListFragment() {
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
        plvDao = new PlvDao(getActivity().getApplicationContext());
        plvList = plvDao.plvs();

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

        if (plvList != null) {
            adapter = new PlvListAdapter(getActivity(), plvList);
            listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            listView.setAdapter(adapter);
            swipeContainer.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.error_color);

            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    try {
                        AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
                        if (!androidNetworkUtility.isConnectedToServer("http://192.168.1.110:8080", 1000)) {
                            Toast.makeText(getActivity(), "Aucune connexion au serveur. Veuillez reéssayer plus tard", Toast.LENGTH_LONG).show();
                        } else {
                            refreshContent();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("La liste des Plvs");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("");


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
                .baseUrl("http://192.168.1.110:8080/")
                        //.addConverterFactory(JacksonConverterFactory.create(mapper))
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ReponseService service = retrofit.create(ReponseService.class);
        Call<List<Plv>> call = service.getAllPlvs();
        //List<Boisson> result = call.execute().body();
        //final List<Boisson> result;
        call.enqueue(new Callback<List<Plv>>() {
            @Override
            public void onResponse(Call<List<Plv>> call, Response<List<Plv>> response) {
                Log.i("Result....", response.toString());
                List<Plv> result = response.body();
                plvDao.clear();
                for (Plv plv : result) {
                    plvDao.insertPlv(plv);
                }
                plvList = plvDao.plvs();

                adapter = new PlvListAdapter(getActivity(), plvList);
                listView.setAdapter(adapter);
                swipeContainer.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<List<Plv>> call, Throwable t) {

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
