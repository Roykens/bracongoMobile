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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.royken.bracongo.mobile.adapter.MaterielCustomAdapter;
import com.royken.bracongo.mobile.entities.Action;
import com.royken.bracongo.mobile.entities.projection.ReponseProjection;
import com.royken.bracongo.mobile.util.ReponseService;
import com.royken.bracongo.mobile.util.RetrofitBuiler;
import com.royken.bracongo.mobile.util.Toto;

import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by royken on 01/05/16.
 */
public class ActionFragment extends Fragment {

    private ReponseProjection reponseProjection;

    private Button suivantBtn;

    private Action action;

    private OnFragmentInteractionListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActionFragment newInstance() {
        ActionFragment fragment = new ActionFragment();

        return fragment;
    }

    public ActionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.action_fragment, container, false);
        reponseProjection = (ReponseProjection) getActivity().getApplicationContext();
        action = new Action();
        final CheckBox besoinContratCbx = (CheckBox)rootView.findViewById(R.id.besoinContratcbx);
        final CheckBox renouvelContratcbx = (CheckBox)rootView.findViewById(R.id.renouvelContratcbx);
        final CheckBox contratPartielcbx = (CheckBox)rootView.findViewById(R.id.renouvelContratcbx);
        final CheckBox reclamcbx = (CheckBox)rootView.findViewById(R.id.reclamcbx);
        final CheckBox fermecbx = (CheckBox)rootView.findViewById(R.id.fermecbx);
        final CheckBox mixteConversioncbx = (CheckBox)rootView.findViewById(R.id.mixteConversioncbx);
        final CheckBox ope3Baccbx = (CheckBox)rootView.findViewById(R.id.ope3Baccbx);
        final EditText nombreBac =(EditText)rootView.findViewById(R.id.nombreBac);
        final CheckBox demenagercbx = (CheckBox)rootView.findViewById(R.id.demenagercbx);
        final CheckBox renforcementcbx = (CheckBox)rootView.findViewById(R.id.renforcementcbx);
        final CheckBox besoinPlvcbx = (CheckBox)rootView.findViewById(R.id.besoinPlvcbx);
        final CheckBox consignationcbx = (CheckBox)rootView.findViewById(R.id.consignationcbx);
        final CheckBox adressecbx = (CheckBox)rootView.findViewById(R.id.adressecbx);
        final CheckBox chaisecbx = (CheckBox)rootView.findViewById(R.id.chaisecbx);
        final CheckBox phncbx = (CheckBox)rootView.findViewById(R.id.phncbx);
        final CheckBox regimeCbx = (CheckBox)rootView.findViewById(R.id.regimeCorriger);
        final CheckBox materielRenseigneCbx = (CheckBox) rootView.findViewById(R.id.mNrenseigne);
        final CheckBox materielTrouveCbx = (CheckBox) rootView.findViewById(R.id.mNtrouve);
        final CheckBox renforcerFroidCbx = (CheckBox) rootView.findViewById(R.id.renforcerFroidcbx);
        suivantBtn = (Button) rootView.findViewById(R.id.btn_question_suivant);
        suivantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.setMaterielNonRenseigneDansContrat(materielRenseigneCbx.isChecked());
                action.setMaterielNonTrouves(materielTrouveCbx.isChecked());
                action.setRegimeACorriger(regimeCbx.isChecked());
                action.setaRenforcerFroid(renforcerFroidCbx.isChecked());
                action.setBesoinDeContrat(besoinContratCbx.isChecked());
                action.setContratPartiel(contratPartielcbx.isChecked());
                action.setBesoinRenouvellementContrat(renouvelContratcbx.isChecked());
                action.setReclamationRemise(reclamcbx.isChecked());
                action.setFermeNonOperationel(fermecbx.isChecked());
                action.setMixteSollicitantCoversion(mixteConversioncbx.isChecked());
                action.setBesoinOperation3Bac1(ope3Baccbx.isChecked());
                if(ope3Baccbx.isChecked()){
                    if(nombreBac.getText().toString().length() > 0){
                        action.setNombreBacs(Integer.parseInt(nombreBac.getText().toString()));
                    }else{

                    }
                }
                else {
                    action.setNombreBacs(0);
                }
                action.setDemenageSansPrevenir(demenagercbx.isChecked());
                action.setRenforcerEnCapacite(renforcementcbx.isChecked());
                action.setBesoinPlv(besoinPlvcbx.isChecked());
                action.setBesoinConsignationEmballage(consignationcbx.isChecked());
                action.setAdresseErronee(adressecbx.isChecked());
                action.setBesoin5ChaisesContre1(chaisecbx.isChecked());
                action.setPhnCapsule(phncbx.isChecked());
                reponseProjection.setAction(action);

                Fragment fragment = CommentFragment.newInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame, fragment);
                ft.addToBackStack(null);

                ft.commit();

               // new BackgroundTask().execute();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Actions");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("");

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

    private class BackgroundTask extends AsyncTask<String, Void,
            Void> {


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... urls) {

          //  Log.i("Bacground","background test");
            Retrofit retrofit = RetrofitBuiler.getRetrofit("");
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
