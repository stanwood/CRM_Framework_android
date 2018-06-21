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


import android.support.annotation.NonNull;

import stanwood.framework.crm.app.SimpleAppTracker;
import stanwood.framework.crm.BaseMessagingService;
import stanwood.framework.crm.BaseNotificationHandler;

/**
 * Concrete implementation of @{@link BaseMessagingService}
 */
public class CrmNotificationService extends BaseMessagingService {


    @NonNull
    @Override
    public BaseNotificationHandler createBaseNotificationHandler() {
        return new CrmNotificationsHandler(getApplication(), SimpleAppTracker.instance());
    }
}
