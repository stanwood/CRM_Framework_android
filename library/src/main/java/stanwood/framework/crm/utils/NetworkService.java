package stanwood.framework.crm.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import stanwood.framework.crm.ApiService;


public class NetworkService extends BroadcastReceiver {

    private final ApiService service;
    private ConnectivityManager conMgr;


    public NetworkService(ApiService service, Context appContext) {
        this.service = service;
        conMgr = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        checkNetworkState();
        registerReceiver(appContext);
    }

    private void registerReceiver(Context appContext) {
        final IntentFilter mIFNetwork = new IntentFilter();
        mIFNetwork.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        appContext.registerReceiver(this, mIFNetwork);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        checkNetworkState();
    }

    private void checkNetworkState() {
        if(isConnectedNow()){
            service.retry();
        }
    }

    public boolean isConnectedNow() {
        NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
