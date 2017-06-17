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
 * Created by sabort on 09.04.17.
 */

public class ExpensesMonthSummary {

    private String categoryName;
    private int currentValue;
    private int categoryLimit;
    private int dbId;

    public ExpensesMonthSummary(int dbId, String categoryName, int currentValue, int categoryLimit) {
        this.dbId = dbId;
        this.categoryName = categoryName;
        this.currentValue = currentValue;
        this.categoryLimit = categoryLimit;
    }

    public String toString(){
        return categoryName;
    }

    public double getProgress(){
        return ((double) currentValue)/ categoryLimit;
    }

    public int getId(){
        return this.dbId;
    }

    public String getCategoryName(){
        return this.categoryName;
    }

    public String getMaxValueText(){
        return String.valueOf(this.categoryLimit);
    }

    public int getCategoryLimit(){
        return this.categoryLimit;
    }

    public int getCurrentValue(){
        return this.currentValue;
    }

    public String getCurrentValueText() { return currentValue > 0 ? "-" + String.valueOf(this.currentValue) : String.valueOf(this.currentValue); }

    public boolean isExceeded(){
        return currentValue > categoryLimit;
    }

    public String getWhatLeftText(){
        return String.valueOf(categoryLimit - currentValue);
    }

}
