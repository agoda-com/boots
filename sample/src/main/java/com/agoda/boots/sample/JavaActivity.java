package com.agoda.boots.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.agoda.boots.Boots;
import com.agoda.boots.Configuration;
import com.agoda.boots.Executor;
import com.agoda.boots.Key;
import com.agoda.boots.Listener;
import com.agoda.boots.Listener.Builder;
import com.agoda.boots.Report;
import com.agoda.boots.logger.AndroidLogger;

import org.jetbrains.annotations.NotNull;

import static com.agoda.boots.Key.all;
import static com.agoda.boots.Logger.Level.DEBUG;

public class JavaActivity extends AppCompatActivity {

    private static final Key.All KEY = all();

    private ProgressBar mProgressBar;
    private TextView mTextView;

    private Listener listener;
    private Executor executor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Executor executorFromIntent = extras.getParcelable("executor");
            if(executorFromIntent != null) {
                executor = executorFromIntent;
            }
        }
        mProgressBar = findViewById(R.id.progressBar);
        mTextView = findViewById(R.id.textView);
    }

    @Override
    public void onStart() {
        super.onStart();

        Boots.configure(new Configuration.Builder()
                .setExecutor(executor)
                .setLogger(new AndroidLogger(DEBUG))
                .build());

        Boots.add(
                new DeviceIdBootable(getApplicationContext()),
                new NetworkRequestBootable(),
                new DatabaseBootable(),
                new RandomTimeBootable()
        );

        listener = Boots.boot(KEY, new Builder() {
            @Override
            public void onBoot(@NotNull Report report) {
                mProgressBar.setVisibility(View.GONE);
                mTextView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(@NotNull Report report) {}
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        if (listener != null) {
            Boots.unsubscribe(KEY, listener);
        }
    }

}
