package com.example.madaim.ex12;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int counter;
    ImageView egg;
    TextView tv;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        this.egg = (ImageView)findViewById(R.id.egg);
        this.tv = (TextView)findViewById(R.id.number);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        counter = sp.getInt("numOfClicks", 100000);
        tv.setText(Integer.toString(counter));

        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);

        egg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                counter--;
                tv.setText(Integer.toString(counter));
                vibrator.vibrate(50);
                egg.startAnimation(animShake);

            }
        });

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
            //addPreferencesFromResource(R.id.number);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            return view;
        }
    }





}
