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

package com.nodomain.sabdevs.expensesmonitor.activities.addcategory;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;

import com.nodomain.sabdevs.expensesmonitor.R;
import com.nodomain.sabdevs.expensesmonitor.activities.BaseSupportActivity;
import com.nodomain.sabdevs.expensesmonitor.adapters.CategoriesEditListArrayAdapter;

public class EditCategoriesActivity extends BaseSupportActivity {

    private CategoriesEditListArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_categories);
        setToolbar();

        adapter = new CategoriesEditListArrayAdapter(this, getCategoriesList());
        final ListView listview = (ListView) findViewById(R.id.listvieweditcategories);
        listview.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddCategory);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddCategoryDialogFragment().show(getFragmentManager(), getResources().getString(R.string.add_new_category));
            }
        });

    }

    public void updateAdapter() {
        adapter.getData().clear();
        adapter.getData().addAll(getCategoriesList());
        adapter.notifyDataSetChanged();
    }

}
