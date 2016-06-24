package com.atansov.cbtest;

import android.util.Log;

import com.couchbase.lite.Database;
import com.couchbase.lite.replicator.Replication;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by atanasovv on 24/06/2016.
 */

public class ReplicationManager {
    private static final String TAG = "ReplicationManager";
    URL url;

    ReplicationManager() {
        try {
            url = new URL(Constants.syncUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    void startContinuous(Database database) {
        Replication replication = database.createPullReplication(url);
        replication.setChannels(Arrays.asList(Constants.channels));
        replication.setContinuous(true);
        replication.start();
    }

    void startOneShot(Database database, Replication.ChangeListener listener) {
        Replication replication = database.createPullReplication(url);
        replication.setChannels(Arrays.asList(Constants.channels));
        replication.setContinuous(false);
        replication.start();

        replication.addChangeListener(listener);
    }
}
