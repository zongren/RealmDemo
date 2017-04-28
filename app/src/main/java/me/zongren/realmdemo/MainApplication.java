package me.zongren.realmdemo;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by zongren on 2017/4/28.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
