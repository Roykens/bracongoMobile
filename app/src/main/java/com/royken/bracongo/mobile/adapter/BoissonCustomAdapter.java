package com.royken.bracongo.mobile.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.royken.bracongo.mobile.R;
import com.royken.bracongo.mobile.entities.Boisson;
import com.royken.bracongo.mobile.entities.projection.BoissonProjection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royken on 11/04/16.
 */
public class BoissonCustomAdapter extends BaseAdapter {

    private static final String TAG = "BoissonCustomAdapter";
    private List<Boisson> boissons;
    private Context mContext;
    private LayoutInflater mInflater;
    private ListView list;
    /* ensure that this constant is greater than the maximum list size */
    private static final int DEFAULT_ID_VALUE = -1;
    /* used to keep the note edit text row id within the list */
    private int mNoteId = DEFAULT_ID_VALUE;

    private int x = 1;

    private List<BoissonProjection> boissonProjections;

    public List<BoissonProjection> getBoissonProjections() {
        return boissonProjections;
    }

    public void setBoissonProjections(List<BoissonProjection> boissonProjections) {
        this.boissonProjections = boissonProjections;
    }

    public static String getTAG() {
        return TAG;
    }

    public List<Boisson> getBoissons() {
        return boissons;
    }

    public void setBoissons(List<Boisson> boissons) {
        this.boissons = boissons;
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

    public BoissonCustomAdapter(Context context, List<Boisson> boissons) {
        mContext = context;
        this.boissons = boissons;
        getProjection(boissons);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return boissonProjections.size();
    }

    @Override
    public Object getItem(int position) {
        return boissonProjections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

         final ViewHolder holder ;
        LinearLayout layoutItem;
        FrameLayout layout;
        if (convertView == null || convertView.getTag() == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.test,parent,false);
            layout = (FrameLayout) mInflater.inflate(R.layout.test, parent, false);
            holder.tv_Nom = (TextView)convertView.findViewById(R.id.textView2);
            holder.cbox = (CheckBox)convertView.findViewById(R.id.checkBox);
            holder.txt_Prix = (EditText)convertView.findViewById(R.id.txtPrix);
            holder.txt_Stock = (EditText)convertView.findViewById(R.id.txtStock);
            holder.cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();
                    boissonProjections.get(getPosition).setDisponible(buttonView.isChecked());
                    //notifyDataSetChanged();
                }
            });

            convertView.setTag(holder);
            convertView.setTag(R.id.txtPrix, holder.txt_Prix);
            convertView.setTag(R.id.txtStock, holder.txt_Stock);
            convertView.setTag(R.id.checkBox, holder.cbox);
        } else {
            layout = (FrameLayout) convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        holder.cbox.setTag(position);
        holder.txt_Prix.setTag(position);
        holder.txt_Stock.setTag(position);

        holder.txt_Prix.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                int pos = (Integer) holder.txt_Prix.getTag();

                boissonProjections.get(pos).setPrix(s.toString());

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.txt_Stock.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                int pos = (Integer) holder.txt_Prix.getTag();

                boissonProjections.get(pos).setStock(s.toString());

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
        }else {
            Log.i("holder2", "holder est BONNNNNNNNNNNNNNNN");

      //  holder.tv_Nom = (TextView)layout.findViewById(R.id.textView2);
        holder.tv_Nom.setText(boissonProjections.get(position).getNom());
      //  holder.cbox = (CheckBox)layout.findViewById(R.id.checkBox);
        holder.cbox.setChecked(boissonProjections.get(position).isDisponible());
       // holder.txt_Prix = (EditText)layout.findViewById(R.id.txtPrix);
        holder.txt_Prix.setText(boissonProjections.get(position).getPrix());
       // holder.txt_Stock = (EditText)layout.findViewById(R.id.txtStock);
        holder.txt_Stock.setText(boissonProjections.get(position).getStock());
       // holder.tv_Nom.setTag(position);
       // holder.txt_Stock.setTag(position);
        //holder.txt_Prix.setTag(position);
       // holder.cbox.setTag(position);
        }
        return convertView;

    }

    public class ViewHolder {

        TextView tv_Nom;
        EditText txt_Prix;
        EditText txt_Stock;
        CheckBox cbox;
    }

    private void getProjection(List<Boisson> boissons){

        boissonProjections = new ArrayList<>();
        for (Boisson boisson: boissons) {
            BoissonProjection boissonProjection = new BoissonProjection(boisson.getNom(),boisson.getIdServeur(),boisson.getPrix()+"","",false);
            boissonProjections.add(boissonProjection);
        }
    }
}
