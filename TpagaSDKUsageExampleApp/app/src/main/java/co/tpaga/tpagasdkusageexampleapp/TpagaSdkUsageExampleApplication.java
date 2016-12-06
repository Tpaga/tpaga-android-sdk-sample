package co.tpaga.tpagasdkusageexampleapp;


import android.app.Application;

import co.tpaga.tpagasdk.Network.TpagaAPI;
import co.tpaga.tpagasdk.Tpaga;

public class TpagaSdkUsageExampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Tpaga.initialize(getResources().getString(R.string.tpaga_public_api_key), TpagaAPI.SANDBOX);
    }


}
