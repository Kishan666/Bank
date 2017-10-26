package com.example.kishan.bankingapp3.FrontEnd.Utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by Kishan on 2017-10-26.
 */
public class App extends Application {

    private static Context context;

    private static App singleton;

    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
        singleton = this;
    }

    public static Context getAppContext() {
        return App.context;
    }

    public static final String TAG = App.class
            .getSimpleName();

    public static synchronized App getInstance() {
        return singleton;
    }
}
