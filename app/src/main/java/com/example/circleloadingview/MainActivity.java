package com.example.circleloadingview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends AppCompatActivity {
    private CircleProgressView circleProgressView;
    private int progress = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            progress += 10;
            circleProgressView.updateProgress(progress);
            if (progress == 100) {
                handler.removeMessages(0);
            }

            handler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleProgressView = findViewById(R.id.progressview);

        handler.sendEmptyMessageDelayed(0, 1000);
    }
}