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

package com.nodomain.sabdevs.expensesmonitor.dataobjects;

/**
 * Created by sabort on 30.04.17.
 */

public class HistoryEntry {

    private final String categoryName;
    private final int value;
    private final int maxValue;
    private final int month;
    private final int year;

    public HistoryEntry(String categoryName, int value, int maxValue, int month, int year) {
        this.categoryName = categoryName;
        this.value = value;
        this.maxValue = maxValue;
        this.month = month;
        this.year = year;
    }

    public String toString(){
        return categoryName + ": " + value + "/" + maxValue;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getValue() {
        return value;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public String getDate(){
        return getPrintableMonth() + "." + year;
    }

    public int getMonth(){
        return month;
    }

    private String getPrintableMonth(){
        return month <= 9 ? "0" + month : String.valueOf(month);
    }

}
