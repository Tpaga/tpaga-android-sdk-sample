package co.tpaga.example;


import android.app.Application;

import co.tpaga.android.Tpaga;

public class TpagaSdkUsageExampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Tpaga.initialize(getResources().getString(R.string.tpaga_public_api_key), Tpaga.SANDBOX);
    }


}
