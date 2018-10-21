package com.soleeklab.www.countries;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

/**
 * Created by mohamed on 10/19/2018.
 */

public class CountriesAdapter extends ArrayAdapter {

    public CountriesAdapter(Context c, List<countries> countries){
        super(c,0,countries);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView=convertView;
        if(listItemView ==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        countries countr= (countries) getItem(position);

        TextView name=(TextView)listItemView.findViewById(R.id.Name);
        name.setText(countr.getName());

        TextView Region=(TextView)listItemView.findViewById(R.id.Region);
        Region.setText(countr.getRegion());

       ImageView Flag=(ImageView) listItemView.findViewById(R.id.ImageFlag);
        Picasso.get().load(countr.getImageUri()).into(Flag);


        return listItemView;
    }
}
