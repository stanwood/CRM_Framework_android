package stanwood.framework.crm.app;

import android.os.Bundle;

import stanwood.framework.crm.app.notification.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected String getScreenName() {
        return "main_view";
    }
}
