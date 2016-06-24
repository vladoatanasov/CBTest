package com.atansov.cbtest;

import android.app.Application;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;

/**
 * Created by atanasovv on 24/06/2016.
 */

public class AppController extends Application {
    private static final String TAG = "AppController";

    private static Database database;

    @Override
    public void onCreate() {
        super.onCreate();

        openDatabase();

//        Intent i= new Intent(getBaseContext(), MyService.class);
//        getBaseContext().startService(i);

    }

    private void openDatabase() {
        Manager manager;

//        Manager.enableLogging(TAG, com.couchbase.lite.util.Log.VERBOSE);
//        Manager.enableLogging(com.couchbase.lite.util.Log.TAG, com.couchbase.lite.util.Log.VERBOSE);
//        Manager.enableLogging(com.couchbase.lite.util.Log.TAG_SYNC_ASYNC_TASK, com.couchbase.lite.util.Log.VERBOSE);
//        Manager.enableLogging(com.couchbase.lite.util.Log.TAG_SYNC, com.couchbase.lite.util.Log.VERBOSE);
//        Manager.enableLogging(com.couchbase.lite.util.Log.TAG_QUERY, com.couchbase.lite.util.Log.VERBOSE);
//        Manager.enableLogging(com.couchbase.lite.util.Log.TAG_VIEW, com.couchbase.lite.util.Log.VERBOSE);
//        Manager.enableLogging(com.couchbase.lite.util.Log.TAG_DATABASE, com.couchbase.lite.util.Log.VERBOSE);

        if (!Manager.isValidDatabaseName(Constants.DATABASE_NAME)) {
            Log.e(TAG, "Bad database name");
            return;
        }

        try {
            manager = new Manager(new AndroidContext(getBaseContext()), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            Log.e(TAG, "Cannot create manager object");
            return;
        }

        try {
            database = manager.getDatabase(Constants.DATABASE_NAME);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public Database getDatabase() {
        if(database == null) {
            openDatabase();
        }

        return database;
    }

}
