package com.mymanager;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class UtililtyClass extends Application {

        @Override
        public void onCreate() {
            super.onCreate();
            Realm.init(getApplicationContext());
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                    .name(ConstantClass.realmName)
                    .deleteRealmIfMigrationNeeded()
                    .build();

            Realm.setDefaultConfiguration(realmConfiguration);


        }
}
