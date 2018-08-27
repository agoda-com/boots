package com.agoda.boots.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.agoda.boots.*;
import com.agoda.boots.Listener.Builder;
import com.agoda.boots.executor.RxAndroidExecutor;
import com.agoda.boots.logger.AndroidLogger;
import org.jetbrains.annotations.NotNull;
import rx.schedulers.Schedulers;

import java.util.Arrays;

import static com.agoda.boots.Logger.Level.DEBUG;

public class JavaActivity extends AppCompatActivity {

    private static final Key.Multiple KEY = Key.multiple(Keys.NETWORK, Keys.DATABASE, Keys.RANDOM);

    private ProgressBar mProgressBar;
    private TextView mTextView;

    private Listener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progressBar);
        mTextView = findViewById(R.id.textView);
    }

    @Override
    public void onStart() {
        super.onStart();

        Boots.configure(new Configuration.Builder()
                .setExecutor(new RxAndroidExecutor(Schedulers.io()))
                .setLogger(new AndroidLogger(DEBUG))
                .build());

        Boots.add(Arrays.asList(
                new DeviceIdBootable(getApplicationContext()),
                new NetworkRequestBootable(),
                new DatabaseBootable(),
                new RandomTimeBootable()
        ));

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
