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

package stanwood.framework.crm.app.notification;


import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import stanwood.framework.crm.Crm;
import stanwood.framework.crm.app.SimpleAppTracker;


/**
 * Exemplar implementation. Same can be done with Fragments.
 * The important part is to check if there are any inApp messages to be displayed for the current screen.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract String getScreenName();

    @Override
    public void onStart() {
        super.onStart();
        if (!TextUtils.isEmpty(getScreenName())) {
            //Check if there are any screens to be displayed for specific screen
            Crm.getInstance().displayInAppIfAny(this, getScreenName());
            SimpleAppTracker.instance().trackScreenView(getScreenName());
        }
    }
}
