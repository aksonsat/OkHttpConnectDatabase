package com.softwareranger.okhttpconnectdatabase.adapter;

/**
 * Created by mac on 5/6/16 AD.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softwareranger.okhttpconnectdatabase.EditAccountActivity;
import com.softwareranger.okhttpconnectdatabase.R;
import com.softwareranger.okhttpconnectdatabase.model.Model;

import java.util.ArrayList;

import javax.security.auth.Subject;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private ArrayList<Model> android;
    private Context context;
    private String strName;

    public Adapter(Context context, ArrayList<Model> android) {
        this.context = context;
        this.android = android;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_row_query_name, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Adapter.ViewHolder viewHolder, final int i) {

        viewHolder.tvName.setText("Name : " + android.get(i).getName());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int acId = android.get(i).getId();
                Log.i("Check Get ID", "Account ID = " + acId);
                Intent i = new Intent(context, EditAccountActivity.class);
                i.putExtra("AC_ID", acId);
                context.startActivity(i);
            }
        });

        /** Use Picasso
        try {
            Picasso.with(context).load(android.get(i).getChannel_thumbnail_circle()).resize(80, 80).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(viewHolder.ivThumbnail);
        } catch (IllegalArgumentException e) {
            viewHolder.ivThumbnail.setImageResource(R.mipmap.ic_launcher);
        }
        **/
        // strName = android.get(i).getName();
        // Log.i("Check Get Name", "strName = " + strName);
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    /**
     * Variable in viewholder
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private CardView cardView;
        // private ImageView ivThumbnail;
        public ViewHolder(final View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.cardView);
            tvName = (TextView)view.findViewById(R.id.textViewQueryName);
            // ivThumbnail = (ImageView) view.findViewById(R.id.imageViewChannels);

        }
    }

}
