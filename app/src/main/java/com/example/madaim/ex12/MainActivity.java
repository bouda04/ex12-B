package com.example.madaim.ex12;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Vibrator;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements MyDialog.ResultsListener {



    int counter;
    ImageView egg;
    TextView tv;
    SharedPreferences sp;
    VideoView explosion;
    Button btn;
    String name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        this.explosion = (VideoView)findViewById(R.id.explosion);
        this.egg = (ImageView)findViewById(R.id.egg);
        this.tv = (TextView)findViewById(R.id.number);
        this.btn = (Button)findViewById(R.id.button);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        counter = sp.getInt("numOfClicks", 10000);
        name = sp.getString("userName", "Guest");
        tv.setText(Integer.toString(counter));




        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);


        String uriPath = "android.resource://com.example.madaim.ex12/"+ R.raw.explosion_10;
        Uri uri = Uri.parse(uriPath);
        explosion.setVideoURI(uri);


        egg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                counter--;
                tv.setText(Integer.toString(counter));
                vibrator.vibrate(50);
                egg.startAnimation(animShake);
                if(counter<10000&&counter>=9500)
                    egg.setImageResource(R.mipmap.egg);
                if(counter<9500&&counter>=9000)
                    egg.setImageResource(R.mipmap.egg1);
                if(counter<9000&&counter>=8500)
                    egg.setImageResource(R.mipmap.egg2);
                if(counter<8500&&counter>=8000)
                    egg.setImageResource(R.mipmap.egg3);
                if(counter<8000&&counter>=7500)
                    egg.setImageResource(R.mipmap.egg4);
                if(counter<7500&&counter>=7000)
                    egg.setImageResource(R.mipmap.egg5);
                if(counter<7000&&counter>=6500)
                    egg.setImageResource(R.mipmap.egg6);

            }
        });

        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                explosion.setSystemUiVisibility(View.VISIBLE);
                explosion.requestFocus();
                explosion.start();
            }
        });


    }


    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(sp.getBoolean("rotation", false))
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }
    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("numOfClicks", counter);
        editor.commit();
        super.onPause();
    }



    public static class MyPreferences extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_reset:
                MyDialog.newInstance(MyDialog.RESET_DIALOG).show(getFragmentManager(), null);
                return true;
            case R.id.action_settings:
                getFragmentManager().beginTransaction().add(android.R.id.content, new MyPreferences()).addToBackStack(null).commit();
                return true;
            case R.id.action_exit:
                MyDialog.newInstance(MyDialog.EXIT_DIALOG).show(getFragmentManager(), null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishedDialog(int requestCode, Object results) {
        switch(requestCode) {
            case MyDialog.RESET_DIALOG:
                counter = 10000;
                egg.setImageResource(R.mipmap.egg);
                tv.setText(Integer.toString(counter));
                break;
            case MyDialog.EXIT_DIALOG:
                finish();
                return;
        }
    }


}
