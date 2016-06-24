package com.atansov.cbtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.replicator.Replication;

public class MainActivity extends AppCompatActivity implements Replication.ChangeListener {
    AppController app;
    TextView statusLabel;

    long timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (AppController) getApplication();
        findViewById(R.id.btn_one_shot).setOnClickListener(onOneShotClick);

        statusLabel = (TextView) findViewById(R.id.status_label);

    }

    View.OnClickListener onContinuousClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ReplicationManager manager = new ReplicationManager();
            manager.startContinuous(app.getDatabase());

            Toast.makeText(getBaseContext(), "Continuous replication started", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener onOneShotClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ReplicationManager manager = new ReplicationManager();
            manager.startOneShot(app.getDatabase(), MainActivity.this);

            timestamp = System.currentTimeMillis();

            Toast.makeText(getBaseContext(), "One-shot replication started", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void changed(final Replication.ChangeEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                long delta = (System.currentTimeMillis() - timestamp) / 1000;

                long threshold = 0;
                int completedDocs = event.getCompletedChangeCount();
                if (delta > 0) {
                    threshold = completedDocs / delta;
                }

                statusLabel.setText(String.format("%d docs in %d sec (%d doc/sec)", completedDocs, delta, threshold));
            }
        });
    }


}
