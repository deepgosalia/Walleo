package com.deepifydroid30.app.walleo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends Fragment {

    @Override
   // protected void onCreate(Bundle savedInstanceState) {
       // super.onCreate(savedInstanceState);
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //setContentView(R.layout.activity_main);
        View view = inflater.inflate(R.layout.activity_main, container, false);
        final ArrayList<ImageList> im = new ArrayList<>();

        im.add(new ImageList(R.drawable.r_1));
        im.add(new ImageList(R.drawable.r_2));
        im.add(new ImageList(R.drawable.r_3));
        im.add(new ImageList(R.drawable.r_4));
        im.add(new ImageList(R.drawable.r_5));
        im.add(new ImageList(R.drawable.r_6));
        im.add(new ImageList(R.drawable.r_7));
        im.add(new ImageList(R.drawable.r_8));
        im.add(new ImageList(R.drawable.r_9));
        im.add(new ImageList(R.drawable.r_10));
        im.add(new ImageList(R.drawable.r_11));
        im.add(new ImageList(R.drawable.r_12));
        im.add(new ImageList(R.drawable.r_13));
        im.add(new ImageList(R.drawable.r_14));
        im.add(new ImageList(R.drawable.r_15));
        im.add(new ImageList(R.drawable.r_16));
        im.add(new ImageList(R.drawable.r_17));
        im.add(new ImageList(R.drawable.r_18));
        im.add(new ImageList(R.drawable.r_19));
        im.add(new ImageList(R.drawable.r_20));
        im.add(new ImageList(R.drawable.r_21));
        im.add(new ImageList(R.drawable.r_22));
        im.add(new ImageList(R.drawable.r_23));
        im.add(new ImageList(R.drawable.r_24));
        im.add(new ImageList(R.drawable.r_25));
        im.add(new ImageList(R.drawable.r_26));
        im.add(new ImageList(R.drawable.r_27));
        im.add(new ImageList(R.drawable.r_28));
        im.add(new ImageList(R.drawable.r_29));
        im.add(new ImageList(R.drawable.r_30));
        im.add(new ImageList(R.drawable.r_31));
        im.add(new ImageList(R.drawable.r_32));
        im.add(new ImageList(R.drawable.r_33));
        im.add(new ImageList(R.drawable.r_34));
        im.add(new ImageList(R.drawable.r_35));
        im.add(new ImageList(R.drawable.r_36));
        im.add(new ImageList(R.drawable.r_37));
        im.add(new ImageList(R.drawable.r_38));
        im.add(new ImageList(R.drawable.r_39));
        im.add(new ImageList(R.drawable.r_40));
        im.add(new ImageList(R.drawable.r_41));
        im.add(new ImageList(R.drawable.r_42));





        //ImageAdapter imageAdapter = new ImageAdapter(this,im);
        ImageAdapter imageAdapter = new ImageAdapter(getActivity(),im);
        final GridView gv = view.findViewById(R.id.grid);
        gv.setAdapter(imageAdapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.e("IDCHECK", "onCreate: " + String.valueOf(im.get(position)));
                Intent i = new Intent(getActivity(),FullScreen.class);
                i.putExtra("Resource", position);
                startActivity(i);
            }
        });


        return view;

    }



}
