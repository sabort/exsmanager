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
 * Created by sabort on 28.04.17.
 */

public class FormattedHistoryEntry {


    private final boolean isMonth;
    private final String categoryName;
    private final int categoryValue;
    private final int categoryLimit;


    public FormattedHistoryEntry(String categoryName, int categoryValue, int categoryLimit, boolean isMonth){
        this.isMonth = isMonth;
        this.categoryName = categoryName;
        this.categoryValue = categoryValue;
        this.categoryLimit = categoryLimit;
    }

    public boolean isMonth(){
        return isMonth;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getSumSpentByCategoryText() {
        return categoryValue > 0 ?  "-" + String.valueOf(categoryValue) : String.valueOf(categoryValue);
    }

    public String getCategoryLimitText() {
        return String.valueOf(categoryLimit);
    }

    public boolean isExceeded(){
        return categoryValue > categoryLimit;
    }
}
