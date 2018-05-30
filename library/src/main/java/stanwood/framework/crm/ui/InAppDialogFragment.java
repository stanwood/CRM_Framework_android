package stanwood.framework.crm.ui;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import stanwood.framework.crm.R;

public class InAppDialogFragment extends DialogFragment {

    public static final String LINK_KEY = "link_key";
    private String link;

    @Override
    public void onResume() {
        super.onResume();
        // Grab the window of the dialog, and change the width
        if (getDialog() != null) {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = getDialog().getWindow();
            lp.copyFrom(window.getAttributes());
            // This makes the dialog take up the full width
            int screenWidth = getActivity().getResources().getDisplayMetrics().widthPixels;
            int screenHeight = getActivity().getResources().getDisplayMetrics().heightPixels;
            lp.width = screenWidth * 3 / 4;
            lp.height = screenHeight * 3 / 4;
            window.setAttributes(lp);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.TransparentBackgroundDialog);
        link = getArguments().getString(LINK_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.default_inapp_dialog, container, false);
        WebView webView = view.findViewById(R.id.webView);
        webView.loadUrl(link);
        return view;
    }
}
