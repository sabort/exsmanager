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

package com.nodomain.sabdevs.expensesmonitor.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nodomain.sabdevs.expensesmonitor.dataobjects.ExpensesMonthSummary;
import com.nodomain.sabdevs.expensesmonitor.dataobjects.HistoryEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sabort on 29.01.17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EMDatabase.db";
    private static final String TRANSACTIONS_COLUMN_CATEGORY_ID = "category_id";
    private static final String TRANSACTIONS_COLUMN_VALUE = "value";
    private static final String TRANSACTION_COLUMN_DATE = "date";
    private static final String CATEGORIES_COLUMN_ID = "id";
    private static final String CATEGORIES_COLUMN_CATEGORY_NAME = "category_name";
    private static final String CATEGORIES_COLUMN_MAXVALUE = "max_value";
    private static final String ARCHIVE_COLUMN_CATEGORY_NAME = "archive_category_name";
    private static final String ARCHIVE_COLUMN_VALUE = "archive_value";
    private static final String ARCHIVE_COLUMN_MAX_VALUE = "archive_max_value";
    private static final String ARCHIVE_COLUMN_MONTH = "archive_month";
    private static final String ARCHIVE_COLUMN_YEAR = "archive_year";
    public static final String CATEGORIES = "categories";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS transactions");
        db.execSQL(
                "create table transactions " +
                        "(id integer primary key, category_id int, value int, date int, FOREIGN KEY(category_id) REFERENCES categories(id))"
        );
        db.execSQL("DROP TABLE IF EXISTS categories");
        db.execSQL(
                "create table categories " +
                        "(id integer primary key autoincrement, category_name text unique, max_value int)"
        );
        db.execSQL("DROP TABLE IF EXISTS archive");
        db.execSQL(
                "create table archive " +
                        "(id integer primary key, archive_category_name text, archive_value int, archive_max_value int, archive_month int, archive_year int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public boolean insertTransaction(int categoryId, int value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRANSACTIONS_COLUMN_CATEGORY_ID, categoryId);
        contentValues.put(TRANSACTIONS_COLUMN_VALUE, value);
        contentValues.put(TRANSACTION_COLUMN_DATE, System.currentTimeMillis());
        db.insert("transactions", null, contentValues);
        return true;
    }

    public boolean insertCategory(String categoryName, int maxValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORIES_COLUMN_CATEGORY_NAME, categoryName.toLowerCase());
        contentValues.put(CATEGORIES_COLUMN_MAXVALUE, maxValue);
        db.insert(CATEGORIES, null, contentValues);
        return true;
    }

    public boolean updateCategory(int catId, String categoryName, int maxValue){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORIES_COLUMN_CATEGORY_NAME, categoryName.toLowerCase());
        contentValues.put(CATEGORIES_COLUMN_MAXVALUE, maxValue);
        db.update(CATEGORIES, contentValues, "id ="+catId, null);
        return true;
    }

    public boolean deleteCategory(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String idText = String.valueOf(id);
        db.delete(CATEGORIES, "id=?", new String[]{idText});
        db.delete("transactions", "category_id=?", new String[]{idText});
        return true;
    }

    private boolean insertCategorySummaryToArchive(ExpensesMonthSummary category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ARCHIVE_COLUMN_CATEGORY_NAME, category.getCategoryName());
        contentValues.put(ARCHIVE_COLUMN_VALUE, category.getCurrentValue());
        contentValues.put(ARCHIVE_COLUMN_MAX_VALUE, category.getCategoryLimit());
        contentValues.put(ARCHIVE_COLUMN_MONTH, EMUtils.getLastMonthNumber());
        contentValues.put(ARCHIVE_COLUMN_YEAR, EMUtils.getCurrentYear());
        db.insert("archive", null, contentValues);
        return true;
    }

    public List<ExpensesMonthSummary> getListOfExpensesMonthSummaries() {
        List<ExpensesMonthSummary> categoriesList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from categories", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {

            int categoryId = res.getInt(res.getColumnIndex(CATEGORIES_COLUMN_ID));
            String categoryName = res.getString(res.getColumnIndex(CATEGORIES_COLUMN_CATEGORY_NAME));
            int maxValue = getMaxValueByCategoryId(categoryId);
            int currentValue = getMonthlySpentForCategory(categoryId);

            ExpensesMonthSummary expensesMonthSummary = new ExpensesMonthSummary(categoryId, categoryName, currentValue, maxValue);

            categoriesList.add(expensesMonthSummary);
            res.moveToNext();
        }

        res.close();

        return categoriesList;
    }


    private int getMaxValueByCategoryId(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from categories where id='" + id + "'", null);
        res.moveToFirst();

        int maxValue = res.getInt(res.getColumnIndex(CATEGORIES_COLUMN_MAXVALUE));
        res.close();

        return maxValue;
    }

    private int getMonthlySpentForCategory(int categoryId) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from transactions where category_id='" + categoryId + "'", null);
        res.moveToFirst();

        int sum = 0;
        while (!res.isAfterLast()) {
            sum += res.getInt(res.getColumnIndex(TRANSACTIONS_COLUMN_VALUE));
            res.moveToNext();
        }

        res.close();

        return sum;
    }

    public Map<String, Integer> getCategoriesMap() {
        Map<String, Integer> hashMap = new HashMap<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from categories", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            hashMap.put(EMUtils.upperString(res.getString(res.getColumnIndex(CATEGORIES_COLUMN_CATEGORY_NAME))),
                    res.getInt(res.getColumnIndex(CATEGORIES_COLUMN_ID)));
            res.moveToNext();
        }

        return hashMap;
    }

    public List<HistoryEntry> getHistoryEntries(){
        List<HistoryEntry> historyEntries = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from archive", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            historyEntries.add(new HistoryEntry(res.getString(res.getColumnIndex(ARCHIVE_COLUMN_CATEGORY_NAME)),
                    res.getInt(res.getColumnIndex(ARCHIVE_COLUMN_VALUE)),
                    res.getInt(res.getColumnIndex(ARCHIVE_COLUMN_MAX_VALUE)),
                    res.getInt(res.getColumnIndex(ARCHIVE_COLUMN_MONTH)),
                    res.getInt(res.getColumnIndex(ARCHIVE_COLUMN_YEAR))));
            res.moveToNext();
        }
        res.close();
        return historyEntries;
    }

    public void archiveData(){
        List<ExpensesMonthSummary> categories = getListOfExpensesMonthSummaries();

        for(ExpensesMonthSummary category: categories){
            insertCategorySummaryToArchive(category);
        }

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS transactions");
        db.execSQL(
                "create table transactions " +
                        "(id integer primary key, category_id int, value int, date int, FOREIGN KEY(category_id) REFERENCES categories(id))"
        );
    }
}
