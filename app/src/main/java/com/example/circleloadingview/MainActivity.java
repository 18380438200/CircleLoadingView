package com.example.circleloadingview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private CircleProgressView circleProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleProgressView = findViewById(R.id.progressview);

        final int totalTime = 10000;
        new CountDownTimer(totalTime,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) ((totalTime-millisUntilFinished) * 100 / totalTime);
                Log.i("minfo", progress + "");
                circleProgressView.updateProgress(progress);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
}