package stanwood.framework.crm;

import android.app.Application;
import android.util.Log;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import stanwood.framework.crm.utils.NetworkService;


public class ApiService {


    private final Application application;
    private final CrmApiInterface crmApiInterface;
    private final Crm crm;
    private final NetworkService networkService;

    ApiService(Crm crm, Application application) {
        this.application = application;
        this.crm = crm;

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient().newBuilder();
        okHttpBuilder.addInterceptor(chain -> {
            Request request = chain
                    .request()
                    .newBuilder()
                    .addHeader("Authorization", application.getString(R.string.crm_api_token))
                    .build();

            return chain.proceed(request);
        });

        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(
                    new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY));
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(application.getString(R.string.crm_base_api_url))
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        crmApiInterface = retrofit.create(CrmApiInterface.class);
        networkService = new NetworkService(this, application);
    }


    void updateToken(String pushToken, String userId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("token", pushToken);

        Call<Void> call = crmApiInterface.sendTokens(application.getString(R.string.jira_project_name), params);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.v("CRM", "api update");
                crm.setPushTokenSent(true);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("CRM", "api update failed");
                crm.setPushTokenSent(false);
            }
        });
    }

    public void retry() {
        if (!crm.wasPushTokenSent()) {
            String pushToken = crm.getPushToken();
            String userId = crm.getUserId();

            if (pushToken != null && userId != null) {
                updateToken(pushToken, userId);
            }
        }
    }
}
