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

package com.nodomain.sabdevs.expensesmonitor.activities.exsmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.nodomain.sabdevs.expensesmonitor.activities.BaseSupportActivity;
import com.nodomain.sabdevs.expensesmonitor.activities.history.HistoryActivity;
import com.nodomain.sabdevs.expensesmonitor.activities.about.AboutActivity;
import com.nodomain.sabdevs.expensesmonitor.activities.addcategory.EditCategoriesActivity;
import com.nodomain.sabdevs.expensesmonitor.dataobjects.ExpensesMonthSummary;
import com.nodomain.sabdevs.expensesmonitor.R;
import com.nodomain.sabdevs.expensesmonitor.adapters.ProgressBarsArrayAdapter;
import com.nodomain.sabdevs.expensesmonitor.utils.DBHelper;
import com.nodomain.sabdevs.expensesmonitor.utils.EMUtils;

import java.util.List;

public class ExsManagerActivity extends BaseSupportActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String CURRENT_MONTH_STRING = "current_month";
    public static final String PREFS_NAME = "PrefsFile";
    private DBHelper emdb = new DBHelper(this);
    private ProgressBarsArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exs_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar();

        initializeSettings();
        archiveIfNewMonth();

        final ListView listview = (ListView) findViewById(R.id.listviewmainpage);
        adapter = new ProgressBarsArrayAdapter(this, emdb.getListOfExpensesMonthSummaries());
        listview.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCategoriesNotEmpty()) {
                    AddTransactionDialogFragment addTransactionDialogFragment = new AddTransactionDialogFragment();
                    addTransactionDialogFragment.show(getFragmentManager(), "Insert new transaction");
                } else {
                    Toast.makeText(getApplicationContext(), R.string.add_categories_first, Toast.LENGTH_LONG).show();

                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void updateAdapter() {
        List<ExpensesMonthSummary> categoriesList = emdb.getListOfExpensesMonthSummaries();
        adapter.getData().clear();
        adapter.getData().addAll(categoriesList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_edit) {
            startActivity(new Intent(this, EditCategoriesActivity.class));
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(this, HistoryActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void archiveIfNewMonth(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if(settings.getInt(CURRENT_MONTH_STRING, 0) != EMUtils.getCurrentMonthNumber()){
            emdb.archiveData();
            settings.edit().putInt(CURRENT_MONTH_STRING, EMUtils.getCurrentMonthNumber()).commit();
            Toast.makeText(getApplicationContext(), R.string.archivization_done, Toast.LENGTH_LONG).show();
        }
    }

    private void initializeSettings() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (settings.getBoolean("first_time", true)) {
            settings.edit().putInt(CURRENT_MONTH_STRING, EMUtils.getCurrentMonthNumber()).commit();
            settings.edit().putBoolean("first_time", false).commit();
        }
    }

    public boolean isCategoriesNotEmpty(){
        return !emdb.getCategoriesMap().isEmpty();
    }

}
