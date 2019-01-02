package com.deepifydroid30.app.walleo;


import android.Manifest;
import android.app.WallpaperManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FullScreen extends AppCompatActivity {
    Context context;
    int domColor;
    Bitmap bitmap;
    String NameOfFolder;
    Toolbar toolbar;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    public int resID;
    String NameOfFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        isStoragePermissionGranted();
       // toolbar = (Toolbar) findViewById(R.id.toolbar);
        /*setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        ConstJava.FLAG_AD = true;
        context = this;
        final ImageView imageView = findViewById(R.id.full_image);
        isNetworkConnected();
        final Button lock_wall = findViewById(R.id.lock_wall);
        final Button home_wall = findViewById(R.id.home_wall);
        final Button save_wall = findViewById(R.id.save_wall);

        /*FloatingActionButton share_button = findViewById(R.id.share_button);
        FloatingActionButton save_button = findViewById(R.id.save_button);
        FloatingActionButton set_wallpaper_button = findViewById(R.id.set_wallpaper);*/
        Intent intent = getIntent();
        final int res = intent.getIntExtra("Resource", 0);
        resID = res;
        NameOfFile = getString(R.string.name_of_file);
        NameOfFolder = getString(R.string.name_of_folder);
        MobileAds.initialize(FullScreen.this, getString(R.string.appid));
         displayAd();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(getString(R.string.bucket)).child(getString(R.string.bucket_child) + Integer.toString(res+1) + ".jpg");

        try {
            final File localFile = File.createTempFile("images", "jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    findViewById(R.id.mock).setVisibility(View.VISIBLE);
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    findViewById(R.id.lock_wall).setVisibility(View.VISIBLE);
                    findViewById(R.id.save_wall).setVisibility(View.VISIBLE);
                    findViewById(R.id.home_wall).setVisibility(View.VISIBLE);
                    findViewById(R.id.screen_switch).setVisibility(View.VISIBLE);
                    //findViewById(R.id.set_text).setVisibility(View.VISIBLE);
                    bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imageView.setImageBitmap(bitmap);
                    
                    synchronized (this) {
                        domColor = getDominantColor(bitmap);
//                        toolbar.setBackgroundColor(domColor);
                            lock_wall.setBackgroundColor(domColor);
                            home_wall.setBackgroundColor(domColor);
                            save_wall.setBackgroundColor(domColor);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            FullScreen.this.getWindow().setStatusBarColor(domColor);
                        }
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e) {
        }
        final ImageView mock = findViewById(R.id.mock);
        Switch mySwitch = findViewById(R.id.screen_switch);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mock.setImageResource(R.drawable.lock_mock);
                }
                else{
                    mock.setImageResource(R.drawable.home_mock);
                }

                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });

        home_wall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                /*String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,"image", null);
                Uri bitmapUri = Uri.parse(bitmapPath);
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_STREAM, bitmapUri );
                intent1.setType("image/png");
                startActivity(Intent.createChooser(intent1, "Share image using"));*/

                WallpaperManager myWallpaperManager = WallpaperManager.getInstance(FullScreen.this);
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        myWallpaperManager.setBitmap(bitmap, null, true, -1);
                    } else {
                        myWallpaperManager.setBitmap(bitmap);
                    }
                    // for homescreen    for LOCK screen set FLAGLOCK
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Snackbar.make(view, "Applied to Home Screen", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                displayAds();
                ConstJava.count = 0;
            }
        });

        save_wall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  WallpaperManager myWallpaperManager = WallpaperManager.getInstance(FullScreen.this);
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        myWallpaperManager.setBitmap(bitmap, null, true, -1);
                    } else {
                        myWallpaperManager.setBitmap(bitmap);
                    }
                    // for homescreen    for LOCK screen set FLAGLOCK
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Snackbar.make(view, "Applied to Home Screen", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                displayAds();
                ConstJava.count = 0;*/
                saveBitMap(getApplicationContext(),bitmap);
            }
        });

        lock_wall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //saveBitMap(getApplicationContext(),bitmap);

                WallpaperManager myWallpaperManager = WallpaperManager.getInstance(FullScreen.this);
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        myWallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                    } else {
                        myWallpaperManager.setBitmap(bitmap);
                    }
                    // for homescreen    for LOCK screen set FLAGLOCK
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Snackbar.make(view, "Applied to Lock Screen", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                ConstJava.count = 0;
                displayAds();
            } 

        });


    }

    public static int getDominantColor(Bitmap bitmap1) {
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap1, 1, 1, true);
        final int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();
        Log.e("HELLO", "getDominantColor: " + color);
        return color;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            isInternetAvailable();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection!", Toast.LENGTH_LONG).show();
        }
        return cm.getActiveNetworkInfo() != null;
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("www.google.com");
            //You can replace it with your name
            if (!ipAddr.equals("")) {
                Toast.makeText(getApplicationContext(), "No Internet Connection!", Toast.LENGTH_LONG).show();
            }
            return !ipAddr.equals("");

        } catch (Exception e) {

            return false;
        }
    }
    public void displayAd(){
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    public void displayAds(){
        mInterstitialAd = new InterstitialAd(FullScreen.this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial));

        AdRequest adRequestBig = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequestBig);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mInterstitialAd != null) {
            mInterstitialAd.setAdListener(null);
        }
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fullscreen_page, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       if (id==R.id.copyText){
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            String mystring = "Hello Apple";
            ClipData clip = ClipData.newPlainText("Text", mystring);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(),"Text copied",Toast.LENGTH_SHORT).show();



        }

        return super.onOptionsItemSelected(item);
    }*/


   //getApplicationContext().getString(R.string.name_of_folder);





    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }
    private File saveBitMap(Context context, Bitmap b) {
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder;
        String CurrentDateAndTime = getCurrentDateAndTime();
        File dir = new File(file_path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File pictureFile = new File(dir, NameOfFile + CurrentDateAndTime + ".jpg");
        Bitmap bitmap3 = b;
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap3.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "There was an issue saving the image.", Toast.LENGTH_SHORT).show();

        }
        scanGallery(context, pictureFile.getAbsolutePath());
        return pictureFile;
    }
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {


                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            //resume tasks needing this permission
        }
    }
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "There was an issue scanning gallery.", Toast.LENGTH_SHORT).show();

        }
    }
}
