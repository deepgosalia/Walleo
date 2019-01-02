package com.deepifydroid30.app.walleo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class Category extends Fragment {
    Bitmap bitmap;
    boolean GET_NODES = true;

    @Override
    // protected void onCreate(Bundle savedInstanceState) {
    // super.onCreate(savedInstanceState);
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //setContentView(R.layout.activity_main);
        View view = inflater.inflate(R.layout.category_card, container, false);
        TextView t = view.findViewById(R.id.text_thanks);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            t.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        Button share_app  = view.findViewById(R.id.share_app);
        Button rate_app  = view.findViewById(R.id.rate_app);
        Button more_app  = view.findViewById(R.id.more_app);

    //    Switch mySwitch = view.findViewById(R.id.notification_switch);

        more_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:Deepify+Droid")));
                } catch (ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=Deepify+Droid")));
                }
            }
        });

        rate_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + getActivity().getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getApplicationContext().getPackageName())));
                }

            }
        });

        share_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String text = "Please checkout this app. It is amazing!!    "+ "http://play.google.com/store/apps/details?id=" + getActivity().getApplicationContext().getPackageName();
                intent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(intent, "Share with"));
            }
        });

     /*   mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    NotificationSwitch.setStatus(true);
                }
                else{
                    NotificationSwitch.setStatus(false);
                }

                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });*/
        return view;
    }

}
