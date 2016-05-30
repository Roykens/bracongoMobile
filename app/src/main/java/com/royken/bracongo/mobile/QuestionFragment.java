package com.royken.bracongo.mobile;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.royken.bracongo.mobile.entities.PointDeVente;
import com.royken.bracongo.mobile.entities.projection.ReponseProjection;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by royken on 19/04/16.
 */
public class QuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String PREFS_NAME = "com.royken.MyPrefsFile";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PointDeVente pointDeVente;
    private EditText heurePassageSRDBrac;
    private EditText heurePassageSRDBral;
    private OnFragmentInteractionListener mListener;
    private String srdBracV;
    private String srdBralV;
    private String fifoStr;
    private ReponseProjection reponseProjection;
    private String heureBrac="00";
    private String minuteBrac = "00";
    private String heureBral = "00";
    private String minuteBral = "00";


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            // TextView tv = (TextView)fi;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.question, container, false);
        reponseProjection = (ReponseProjection) getActivity().getApplicationContext();
        final EditText txtmballageBrac = (EditText)rootView.findViewById(R.id.txtEmballageBrac);
        final EditText txtmballageConc = (EditText)rootView.findViewById(R.id.txtEmballageConc);
        final EditText txtpassgeBrac = (EditText)rootView.findViewById(R.id.txtjourPassageFDVBrac);
        final EditText txtPhn = (EditText)rootView.findViewById(R.id.txtphn);
        Spinner srdBrac = (Spinner)rootView.findViewById(R.id.srdBrac);
        heurePassageSRDBrac = (EditText)rootView.findViewById(R.id.txtHeurePassageSrdBrac);
        final EditText txtpassgeBral = (EditText)rootView.findViewById(R.id.txtjourPassageFDVBral);
        Spinner srdBral = (Spinner)rootView.findViewById(R.id.srdBral);
        Spinner fifo = (Spinner)rootView.findViewById(R.id.fifo);
         heurePassageSRDBral = (EditText)rootView.findViewById(R.id.txtHeurePassageSrdBral);

        Button suiteBtn = (Button)rootView.findViewById(R.id.btn_question_suivant);
        suiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reponseProjection.setParcEmballageBracongo(Integer.parseInt(txtmballageBrac.getText().toString().trim()));
                reponseProjection.setParcEmballageBralima(Integer.parseInt(txtmballageConc.getText().toString().trim()));
                String jourBrac = txtpassgeBrac.getText().toString().trim();
                String jourBral = txtpassgeBral.getText().toString().trim();
                boolean srdbrac;
                boolean srdbral;
                if(jourBrac.length() > 0){
                    reponseProjection.setJoursEcouleVisiteFDVBrac(Integer.parseInt(jourBrac));
                }
                else {
                    reponseProjection.setJoursEcouleVisiteFDVBrac(-1);
                }

                if(jourBral.length() > 0){
                    reponseProjection.setJoursEcouleVisiteFDVBral(Integer.parseInt(jourBral));
                }
                else {
                    reponseProjection.setJoursEcouleVisiteFDVBral(-1);
                }

                reponseProjection.setNombrePhn(Integer.parseInt(txtPhn.getText().toString().trim()));
                if(srdBracV.trim().equalsIgnoreCase("OUI")){
                    reponseProjection.setSrdBrac(true);
                    srdbrac = true;
                }
                else {
                    reponseProjection.setSrdBrac(false);
                    srdbrac = false;
                }
                if(srdBralV.trim().equalsIgnoreCase("OUI")){
                    reponseProjection.setSrdBral(true);
                    srdbral = true;
                }
                else {
                    reponseProjection.setSrdBral(false);
                    srdbral = true;
                }
                if(fifoStr.trim().equalsIgnoreCase("OUI")){
                    reponseProjection.setFifo(true);
                }
                else {
                    reponseProjection.setFifo(false);
                }
                Calendar gc = new GregorianCalendar();
                Date d2;
                if(srdbrac){

                    //gc.setTime(d);
                    gc.set(Calendar.HOUR_OF_DAY, Integer.parseInt(heureBrac)-1);
                    gc.set(Calendar.MINUTE,Integer.parseInt(minuteBrac));
                    // gc.add(Calendar.HOUR, 2);
                   d2 = gc.getTime();
                    reponseProjection.setHeurePassageSrdBrac(d2);
                }
                else {

                    reponseProjection.setHeurePassageSrdBrac(null);
                }
                if (srdbral){
                    gc.set(Calendar.HOUR_OF_DAY, Integer.parseInt(heureBral));
                    gc.set(Calendar.MINUTE, Integer.parseInt(minuteBral));
                    d2 = gc.getTime();
                    reponseProjection.setHeurePassageSrdBral(d2);
                }
                else {
                    reponseProjection.setHeurePassageSrdBral(null);
                }
                gc = new GregorianCalendar();
                gc.set(Calendar.HOUR_OF_DAY,gc.get(Calendar.HOUR_OF_DAY)-1);
                reponseProjection.setHeureVisite(gc.getTime());
                SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                int idPln = settings.getInt("com.royken.idPln", 0);
                reponseProjection.setIdPlanning(Long.parseLong(idPln+""));
                Fragment fragment = TwoFragment.newInstance(true,true,null);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame, fragment);
                ft.addToBackStack(null);

                ft.commit();
            }
        });
        heurePassageSRDBrac.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        heurePassageSRDBrac.setText( selectedHour + ":" + selectedMinute);
                        heureBrac = selectedHour+"";
                        minuteBrac = selectedMinute+"";
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Choisir le temps");
                mTimePicker.show();

            }
        });

        heurePassageSRDBral.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        heurePassageSRDBral.setText(selectedHour + ":" + selectedMinute);
                        heureBral = selectedHour + "";
                        minuteBral = selectedMinute + "";
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Choisir le temps");
                mTimePicker.show();

            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.choices2, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        srdBrac.setAdapter(adapter);
        srdBral.setAdapter(adapter);
        fifo.setAdapter(adapter);
        srdBrac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //holder.data.setSelected(arg2);
                //int pos = (Integer) arg0.getTag();

                //plvProjections.get(pos).setEtatBrac((String)arg0.getItemAtPosition(arg2));
                srdBracV = (String) arg0.getItemAtPosition(arg2);
                // viewHolder.text.setText(viewHolder.data.getText());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        fifo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //holder.data.setSelected(arg2);
                //int pos = (Integer) arg0.getTag();
                     //plvProjections.get(pos).setEtatBrac((String)arg0.getItemAtPosition(arg2));
                fifoStr = (String) arg0.getItemAtPosition(arg2);
                // viewHolder.text.setText(viewHolder.data.getText());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        srdBral.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //holder.data.setSelected(arg2);
                //int pos = (Integer) arg0.getTag();
                //plvProjections.get(pos).setEtatBrac((String)arg0.getItemAtPosition(arg2));
                srdBralV =  (String)arg0.getItemAtPosition(arg2);
                // viewHolder.text.setText(viewHolder.data.getText());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        return rootView;
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
