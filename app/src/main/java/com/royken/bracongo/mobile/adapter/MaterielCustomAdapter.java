package com.royken.bracongo.mobile.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.royken.bracongo.mobile.R;
import com.royken.bracongo.mobile.entities.Materiel;
import com.royken.bracongo.mobile.entities.Plv;
import com.royken.bracongo.mobile.entities.projection.MaterielProjection;
import com.royken.bracongo.mobile.entities.projection.PlvProjection;
import com.royken.bracongo.mobile.util.DataHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royken on 17/04/16.
 */
public class MaterielCustomAdapter extends BaseAdapter {

    private static final String TAG = "PlvCustomAdapter";

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Materiel> materiels;
    private List<MaterielProjection> materielProjections;

    public static String getTAG() {
        return TAG;
    }

    public LayoutInflater getmInflater() {
        return mInflater;
    }

    public void setmInflater(LayoutInflater mInflater) {
        this.mInflater = mInflater;
    }

    public List<MaterielProjection> getMaterielProjections() {
        return materielProjections;
    }

    public void setMaterielProjections(List<MaterielProjection> materielProjections) {
        this.materielProjections = materielProjections;
    }

    public static int getDefaultIdValue() {
        return DEFAULT_ID_VALUE;
    }

    public int getmNoteId() {
        return mNoteId;
    }

    public void setmNoteId(int mNoteId) {
        this.mNoteId = mNoteId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public List<Materiel> getMateriels() {
        return materiels;
    }

    public void setMateriels(List<Materiel> materiels) {
        this.materiels = materiels;
    }

    /* ensure that this constant is greater than the maximum list size */
    private static final int DEFAULT_ID_VALUE = -1;
    /* used to keep the note edit text row id within the list */
    private int mNoteId = DEFAULT_ID_VALUE;

    private int x = 1;
    @Override
    public int getCount() {
        return materielProjections.size();
    }

    @Override
    public Object getItem(int position) {
        return materielProjections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public MaterielCustomAdapter(Context context, List<Materiel> materiels) {
        mContext = context;
        this.materiels= materiels;
        getProjection(materiels);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private void getProjection(List<Materiel> materiels){

        materielProjections = new ArrayList<>();
        for (Materiel materiel: materiels) {
            MaterielProjection materielProjection = new MaterielProjection(materiel.getIdServeur(),materiel.getNom(),"","","","","","");
            materielProjections.add(materielProjection);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getmContext(),
                R.array.choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getmContext(),
                R.array.choices, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        final ViewHolder holder ;
        LinearLayout layoutItem;
        FrameLayout layout;
        if (convertView == null || convertView.getTag() == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.materiel,parent,false);
            layout = (FrameLayout) mInflater.inflate(R.layout.materiel, parent, false);
            holder.tv_Nom = (TextView)convertView.findViewById(R.id.tvMateriel);
            holder.txt_Nombre_Brac = (EditText)convertView.findViewById(R.id.txtNbrBracongoM);
            holder.txt_Nombre_Brac_casse = (EditText)convertView.findViewById(R.id.txtNbrCasseBracongo);
            holder.txt_Nombre_Conc = (EditText)convertView.findViewById(R.id.txtNbrConcuM);
            holder.txt_Nombre_Conc_casse = (EditText)convertView.findViewById(R.id.txtNbrCasseConcu);
            holder.spinnerBra = (Spinner)convertView.findViewById(R.id.etatBracongoM);
            holder.spinnerConc = (Spinner)convertView.findViewById(R.id.etatConcuM);
            holder.data = new DataHolder(getmContext());
            if(holder.data == null){
                Log.i("data", "Datat est NULLLLLLLLLLLLLLLLLLLLLLLL");
            }
            if(holder.spinnerBra == null){
                Log.i("SPINNER","Le spinner est null");
            }
            holder.spinnerBra.setAdapter(adapter);
            holder.spinnerConc.setAdapter(adapter2);

            holder.spinnerBra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    notifyDataSetChanged();
                    //holder.data.setSelected(arg2);
                    int pos = (Integer) arg0.getTag();
                    Toast.makeText(mContext, (String) arg0.getItemAtPosition(arg2) + " position " + arg2 + "  " + arg3 + "Tag " + (Integer) arg0.getTag(), Toast.LENGTH_LONG).show();
                    materielProjections.get(pos).setEtatBrac((String) arg0.getItemAtPosition(arg2));
                    // (String)arg0.getItemAtPosition(arg2);
                    // viewHolder.text.setText(viewHolder.data.getText());
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }

            });
            holder.spinnerConc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    notifyDataSetChanged();
                    //holder.data.setSelected(arg2);
                    int pos = (Integer) arg0.getTag();
                    Toast.makeText(mContext, (String) arg0.getItemAtPosition(arg2), Toast.LENGTH_LONG).show();
                    materielProjections.get(pos).setEtatConc((String) arg0.getItemAtPosition(arg2));
                    // (String)arg0.getItemAtPosition(arg2);
                    // viewHolder.text.setText(viewHolder.data.getText());
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }

            });
            convertView.setTag(holder);
            convertView.setTag(R.id.txtNbrBracongoM, holder.txt_Nombre_Brac);
            convertView.setTag(R.id.txtNbrCasseBracongo, holder.txt_Nombre_Brac_casse);
            convertView.setTag(R.id.txtNbrCasseConcu, holder.txt_Nombre_Conc_casse);
            convertView.setTag(R.id.txtNbrConcuM, holder.txt_Nombre_Conc);
            convertView.setTag(R.id.etatBracongo, holder.spinnerBra);
            convertView.setTag(R.id.etatConcu, holder.spinnerConc);
        } else {
            layout = (FrameLayout) convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        holder.spinnerBra.setTag(position);
        holder.spinnerConc.setTag(position);
        holder.txt_Nombre_Conc.setTag(position);
        holder.txt_Nombre_Brac.setTag(position);
        holder.txt_Nombre_Brac_casse.setTag(position);
        holder.txt_Nombre_Conc_casse.setTag(position);

     /*   holder.spinnerBra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                holder.data.setSelected(arg2);
                int pos = (Integer) holder.tv_Nom.getTag();
                plvProjections.get(pos).setEtatBrac(holder.data.getText());
                // viewHolder.text.setText(viewHolder.data.getText());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });*/

        holder.txt_Nombre_Brac.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                int pos = (Integer) holder.txt_Nombre_Brac.getTag();

                //  boissonProjections.get(pos).setPrix(s.toString());
                materielProjections.get(pos).setNombreBrac(s.toString().trim());

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.txt_Nombre_Conc.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                int pos = (Integer) holder.txt_Nombre_Conc.getTag();

                //   boissonProjections.get(pos).setStock(s.toString());
                materielProjections.get(pos).setNombreCon(s.toString().trim());

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.txt_Nombre_Brac_casse.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                int pos = (Integer) holder.txt_Nombre_Brac_casse.getTag();

                //   boissonProjections.get(pos).setStock(s.toString());
                materielProjections.get(pos).setNombreCasseBrac(s.toString().trim());

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.txt_Nombre_Conc_casse.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                int pos = (Integer) holder.txt_Nombre_Conc_casse.getTag();

                //   boissonProjections.get(pos).setStock(s.toString());
                materielProjections.get(pos).setNombreCasseConc(s.toString().trim());

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(holder == null){
            Log.i("holder2", "holder est NULLLLLLLLLLLLLL");
        } else {
            Log.i("holder2", "holder est BONNNNNNNNNNNNNNNN");
            // v.spinner.setSelection(theItemAtPosition.getQuantity());
            // holder.spinnerBra.setSelection();
            holder.tv_Nom.setText(materielProjections.get(position).getNom());
            holder.txt_Nombre_Conc.setText(materielProjections.get(position).getNombreCon()+"");
            holder.txt_Nombre_Brac.setText(materielProjections.get(position).getNombreBrac()+"");
            holder.txt_Nombre_Brac_casse.setText(materielProjections.get(position).getNombreCasseBrac()+"");
            holder.txt_Nombre_Conc_casse.setText(materielProjections.get(position).getNombreCasseConc()+"");

        }
        return convertView;
    }

    public class ViewHolder {
        DataHolder data;
        TextView tv_Nom;
        EditText txt_Nombre_Brac;
        EditText txt_Nombre_Brac_casse;
        EditText txt_Nombre_Conc;
        EditText txt_Nombre_Conc_casse;
        Spinner spinnerBra;
        Spinner spinnerConc;
    }
}
