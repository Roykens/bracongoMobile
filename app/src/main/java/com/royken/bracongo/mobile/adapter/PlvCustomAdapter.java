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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.royken.bracongo.mobile.R;
import com.royken.bracongo.mobile.entities.Plv;
import com.royken.bracongo.mobile.entities.projection.PlvProjection;
import com.royken.bracongo.mobile.util.DataHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royken on 15/04/16.
 */
public class PlvCustomAdapter extends BaseAdapter {

    private static final String TAG = "PlvCustomAdapter";

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Plv> plvs;



    /* ensure that this constant is greater than the maximum list size */
    private static final int DEFAULT_ID_VALUE = -1;
    /* used to keep the note edit text row id within the list */
    private int mNoteId = DEFAULT_ID_VALUE;

    private int x = 1;

    private List<PlvProjection> plvProjections;

    public static String getTAG() {
        return TAG;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public LayoutInflater getmInflater() {
        return mInflater;
    }

    public void setmInflater(LayoutInflater mInflater) {
        this.mInflater = mInflater;
    }

    public List<Plv> getPlvs() {
        return plvs;
    }

    public void setPlvs(List<Plv> plvs) {
        this.plvs = plvs;
    }

    public List<PlvProjection> getPlvProjections() {
        return plvProjections;
    }

    public void setPlvProjections(List<PlvProjection> plvProjections) {
        this.plvProjections = plvProjections;
    }

    public PlvCustomAdapter(Context context, List<Plv> plvs) {
        mContext = context;
        this.plvs= plvs;
        getProjection(plvs);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return plvProjections.size();
    }

    @Override
    public Object getItem(int position) {
        return plvProjections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void getProjection(List<Plv> plvs){

        plvProjections = new ArrayList<>();
        for (Plv plv: plvs) {
            PlvProjection plvProjection = new PlvProjection(plv.getNom(),plv.getIdServeur(),"","","","");
            plvProjections.add(plvProjection);
        }
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
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
            convertView = mInflater.inflate(R.layout.plv,parent,false);
            layout = (FrameLayout) mInflater.inflate(R.layout.test, parent, false);
            holder.tv_Nom = (TextView)convertView.findViewById(R.id.tvAffiche);
            holder.txt_Brac = (EditText)convertView.findViewById(R.id.txtNbrBracongo);
            holder.txt_Conc = (EditText)convertView.findViewById(R.id.txtNbrConcu);
            holder.spinnerBra = (Spinner)convertView.findViewById(R.id.etatBracongo);
            holder.spinnerConc = (Spinner)convertView.findViewById(R.id.etatConcu);
            holder.data = new DataHolder(getmContext());
            if(holder.data == null){
                Log.i("data","Datat est NULLLLLLLLLLLLLLLLLLLLLLLL");
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
                    Toast.makeText(mContext, (String) arg0.getItemAtPosition(arg2) + " position " + arg2 + "  "+ arg3 + "Tag " + (Integer)arg0.getTag(),Toast.LENGTH_LONG).show();
                    plvProjections.get(pos).setEtatBrac((String)arg0.getItemAtPosition(arg2));
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
                    plvProjections.get(pos).setEtatConc((String)arg0.getItemAtPosition(arg2));
                    // (String)arg0.getItemAtPosition(arg2);
                    // viewHolder.text.setText(viewHolder.data.getText());
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }

            });
            convertView.setTag(holder);
            convertView.setTag(R.id.txtNbrBracongo, holder.txt_Brac);
            convertView.setTag(R.id.txtNbrConcu, holder.txt_Conc);
            convertView.setTag(R.id.etatBracongo, holder.spinnerBra);
            convertView.setTag(R.id.etatConcu, holder.spinnerConc);
        } else {
            layout = (FrameLayout) convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        holder.spinnerBra.setTag(position);
        holder.spinnerConc.setTag(position);
        holder.txt_Conc.setTag(position);
        holder.txt_Brac.setTag(position);

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

        holder.txt_Brac.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                int pos = (Integer) holder.txt_Brac.getTag();

              //  boissonProjections.get(pos).setPrix(s.toString());
                plvProjections.get(pos).setNombreBrac(s.toString().trim());

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.txt_Conc.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                int pos = (Integer) holder.txt_Conc.getTag();

             //   boissonProjections.get(pos).setStock(s.toString());
                plvProjections.get(pos).setNombreConc(s.toString().trim());

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

            holder.txt_Conc.setText(plvProjections.get(position).getNombreConc()+"");
            holder.txt_Brac.setText(plvProjections.get(position).getNombreBrac()+"");
            //holder.spinnerBra.setSe
            //  holder.tv_Nom = (TextView)layout.findViewById(R.id.textView2);
           // holder.tv_Nom.setText(boissonProjections.get(position).getNom());
            //  holder.cbox = (CheckBox)layout.findViewById(R.id.checkBox);
           // holder.cbox.setChecked(boissonProjections.get(position).isDisponible());
            // holder.txt_Prix = (EditText)layout.findViewById(R.id.txtPrix);
           // holder.txt_Prix.setText(boissonProjections.get(position).getPrix());
            // holder.txt_Stock = (EditText)layout.findViewById(R.id.txtStock);
           // holder.txt_Stock.setText(boissonProjections.get(position).getStock());
            // holder.tv_Nom.setTag(position);
            // holder.txt_Stock.setTag(position);
            //holder.txt_Prix.setTag(position);
            // holder.cbox.setTag(position);
        }
        return convertView;

    }

    public class ViewHolder {
        DataHolder data;
        TextView tv_Nom;
        EditText txt_Brac;
        EditText txt_Conc;
        Spinner spinnerBra;
        Spinner spinnerConc;
    }

}
