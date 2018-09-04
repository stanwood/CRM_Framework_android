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

package stanwood.framework.crm.app;


import android.app.Application;

import stanwood.framework.crm.Crm;
import stanwood.framework.crm.app.notification.CustomInAppDialogFragment;

public class CrmApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        //Initialization of tracking library
        SimpleAppTracker.init(this);

        //Needs initialization for tracking push token
        Crm.init(this, SimpleAppTracker.instance());

        //Adding user tracking for campaigns purposes
        Crm.getInstance().saveUserId("1234");

        //Optional code for specifying different inApp designs based on screen
        Crm.getInstance().addDialogDesign("main_view", CustomInAppDialogFragment.class);
    }
}
