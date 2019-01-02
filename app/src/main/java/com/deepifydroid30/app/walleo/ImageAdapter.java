package com.deepifydroid30.app.walleo;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapter extends ArrayAdapter<ImageList> {
    public ImageAdapter(Activity context, ArrayList<ImageList> im){
        super(context,0, im);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View imageItemView = convertView;
        if(imageItemView==null){
            imageItemView = LayoutInflater.from(getContext()).inflate(R.layout.image_card,parent,false);

        }
        ImageList imageList = getItem(position);
        ImageView image = imageItemView.findViewById(R.id.image);
        image.setImageResource(imageList.getResImage());


        return imageItemView;
    }
}
