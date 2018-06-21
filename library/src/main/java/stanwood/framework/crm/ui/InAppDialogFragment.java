/*
 * Copyright 2018 stanwood Gmbh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
