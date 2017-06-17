/*
 * Copyright (c) 2017 SAbort
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nodomain.sabdevs.expensesmonitor.activities.history;

import android.os.Bundle;
import android.widget.ListView;
import com.nodomain.sabdevs.expensesmonitor.R;
import com.nodomain.sabdevs.expensesmonitor.activities.BaseSupportActivity;
import com.nodomain.sabdevs.expensesmonitor.adapters.FormattedHistoryEntriesAdapter;


public class HistoryActivity extends BaseSupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setToolbar();

        final ListView listview = (ListView) findViewById(R.id.archive_list_view);

        FormattedHistoryEntriesAdapter adapter = new FormattedHistoryEntriesAdapter(this, getFormattedArchiveEntries());
        listview.setAdapter(adapter);
    }
}

