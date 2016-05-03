package com.royken.bracongo.mobile;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.royken.bracongo.mobile.adapter.MaterielCustomAdapter;
import com.royken.bracongo.mobile.adapter.PlvCustomAdapter;
import com.royken.bracongo.mobile.dao.MaterielDao;
import com.royken.bracongo.mobile.entities.Materiel;
import com.royken.bracongo.mobile.entities.projection.MaterielProjection;
import com.royken.bracongo.mobile.entities.projection.ReponseProjection;
import com.royken.bracongo.mobile.util.ReponseService;
import com.royken.bracongo.mobile.util.Toto;

import okhttp3.OkHttpClient;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by royken on 17/04/16.
 */
public class MaterielFragment extends Fragment implements AdapterView.OnItemClickListener {

    private List<Materiel> materiels = new ArrayList<>();
    PlvCustomAdapter plvCustomAdapter;
    MaterielCustomAdapter materielCustomAdapter;

    private ReponseProjection reponseProjection;
    private OnFragmentInteractionListener mListener;
    private ListView listView;

    public static MaterielFragment newInstance() {
        MaterielFragment fragment = new MaterielFragment();
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
        MaterielDao dao = new MaterielDao(getActivity().getApplicationContext());
        materiels = dao.materiels();
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
                reponseProjection = (ReponseProjection) getActivity().getApplicationContext();

                List<MaterielProjection> projections1 = materielCustomAdapter.getMaterielProjections();
                if (projections1.size() > 0) {
                    for (MaterielProjection projection : projections1) {
                        reponseProjection.addMateriel(projection);
                        Log.i("PLVS", projection.getNom() + " Nombre brac " + projection.getNombreBrac() + " Nombre Conc " + projection.getNombreCon() + "Etat Brac " + projection.getEtatBrac() + " Etat Conc " + projection.getEtatConc() + " Brac casse "+ projection.getNombreCasseBrac());
                    }
                }

                Fragment fragment = ActionFragment.newInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame, fragment);
                ft.addToBackStack(null);
                ft.commit();
               // new BackgroundTask().execute();
               // Gson jeson = new Gson();
               // GsonBuilder builder = new GsonBuilder();
               // Gson gson = builder.create();
               // String jsonString = gson.toJson(reponseProjection);
               // Log.i("JSON TEST....",jsonString);
            }
        });




        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(materiels != null){
            materielCustomAdapter = new MaterielCustomAdapter(getActivity(),materiels);
            listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            listView.setAdapter(materielCustomAdapter);

            //getListView().setOnItemClickListener(this);
        }
          ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Materiel");

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

    private class BackgroundTask extends AsyncTask<String, Void,
                Void> {

            Retrofit retrofit;
        Toto totoa = new Toto("royken","toto",new Date());

        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .setPrettyPrinting()
                .serializeNulls()
                .excludeFieldsWithoutExposeAnnotation().create();
        @Override
        protected void onPreExecute() {
          //  JacksonConverter converter = new JacksonConverter(new ObjectMapper());
          //  Retrofit retrofit = new Retrofit.Builder()
            //        .baseUrl("https://api.github.com/").addConverterFactory(JacksonConverterFactory.create());
              //      .build();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!



            ObjectMapper mapper = new ObjectMapper();
            try {
                String jsonInString = mapper.writeValueAsString(totoa);
                String zzzzzz = gson.toJson(totoa);
                Log.i("TOOOOOOOOO",zzzzzz);
                String jsonInString2 = gson.toJson(reponseProjection);
                Log.i("MyJsonjjjjjjjjjjj",jsonInString2);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.110:8080/")
                    //.addConverterFactory(JacksonConverterFactory.create(mapper))
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                     .build();
            Log.i("RETROFIT",retrofit.toString());
        }

        @Override
        protected Void doInBackground(String... urls) {

            Log.i("Bacground","background test");
            ReponseService service = retrofit.create(ReponseService.class);
            Call<ReponseProjection> call= service.envoyerReponse(reponseProjection);
            call.enqueue(new Callback<ReponseProjection>() {
                @Override
                public void onResponse(Call<ReponseProjection> call, Response<ReponseProjection> response) {
                   // Log.i("Retrofit Logging", response.body().toString());
                }

                @Override
                public void onFailure(Call<ReponseProjection> call, Throwable t) {
                    Log.e("Retrofit Logging2", t.toString());

                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

        }
    }

}
