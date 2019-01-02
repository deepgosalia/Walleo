package com.deepifydroid30.app.walleo;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;


public class TabPage extends AppCompatActivity {
    Context context;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tab);
        setSupportActionBar(toolbar);
        /*boolean status = NotificationSwitch.getStatus();
        if (status){*/
           /* AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent notificationIntent = new Intent(this, AlarmReceiver.class);
            PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, 5);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 1000*10,broadcast);*/
     /*   }else{
            Context ctx = getApplicationContext();
            AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
            Intent cancelServiceIntent = new Intent(ctx, AlarmReceiver.class);
            PendingIntent cancelServicePendingIntent = PendingIntent.getBroadcast(
                    ctx,
                    100, // integer constant used to identify the service
                    cancelServiceIntent,
                    0 //no FLAG needed for a service cancel
            );
            am.cancel(cancelServicePendingIntent);

        }
*/


        MobileAds.initialize(TabPage.this, getString(R.string.appid));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("ALL"));
        tabLayout.addTab(tabLayout.newTab().setText("MORE"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
       /* if(ConstJava.FLAG_AD){
            getInterAd(20000);
        }
        else {
            //getInterAd(10000);
        }*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Your code to show add
                displayAds();
            }
        }, 10000);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share) {
            ConstJava.FLAG_AD = true;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String text = "Please checkout this app. It is amazing!!    "+ "http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
            intent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(intent, "Share with"));
            return true;
        }
        if (id == R.id.rate) {
            ConstJava.FLAG_AD = true;
            Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
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
                        Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
            }
        }
        if (id == R.id.more) {
            ConstJava.FLAG_AD = true;
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:Deepify+Droid")));
            } catch (ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=Deepify+Droid")));
            }
        }
        if (id == R.id.support) {
            ConstJava.FLAG_AD = true;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            String[] to = {"logicalscience17@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, to);
            intent.setType("message/rfc822");
            intent = Intent.createChooser(intent, "Send Email");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

    }

    public void displayAds(){
        mInterstitialAd = new InterstitialAd(TabPage.this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial));

        AdRequest adRequestBig = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequestBig);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                //getInterAd(20000);
            }
        });
    }

   /* public void getInterAd(int time){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Your code to show add
                displayAds();
            }
        }, time);


    }*/
   @Override
   protected void onPause() {
       super.onPause();
       if(mInterstitialAd != null) {
           mInterstitialAd.setAdListener(null);
       }
   }

}









