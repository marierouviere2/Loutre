package com.example.loutre.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import com.example.loutre.POJO.Data;
import com.example.loutre.R;

import java.util.ArrayList;

/**
 *
 */

public class ListViewAdapter extends ArrayAdapter<Data> {

    private Context context;
    private ArrayList<Data> data;

    public ListViewAdapter(Context context, int textViewResourceId, ArrayList<Data> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View curView = convertView;
        if (curView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            curView = vi.inflate(R.layout.feed_list_view_item, null);
        }

        // Get the user full name, profil picture and actual photo of the post
        TextView tv_user_fullname = (TextView) curView.findViewById(R.id.tv_user_fullname);
        ImageView iv_photo = (ImageView) curView.findViewById(R.id.iv_photo);
        ImageView iv_profile = (ImageView) curView.findViewById(R.id.iv_profile);

        tv_user_fullname.setText(data.get(position).getUser().getFull_name());

        // Picasso plugin to insert and resize the images into the views
        //Picasso.get() // New version
        Picasso.with(context)
                .load(data.get(position).getUser().getProfile_picture())
                .resize(100, 100)
                .centerInside()
                .into(iv_profile);
        //Picasso.get() // New version
        Picasso.with(context)
                .load(data.get(position).getImages().getStandard_resolution().getUrl())
                .into(iv_photo);

        return curView;
    }

    public void clearListView() {
        data.clear();
    }
}
