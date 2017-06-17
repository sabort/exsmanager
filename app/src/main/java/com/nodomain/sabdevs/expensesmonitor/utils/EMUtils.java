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

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.nodomain.sabdevs.expensesmonitor.dataobjects.HistoryEntry;
import com.nodomain.sabdevs.expensesmonitor.dataobjects.FormattedHistoryEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sabort on 21.03.17.
 */

public class EMUtils {

    private static final int PROGRESS_BAR_WIDTH_IN_DP = 300;

    public static int getCurrentMonthNumber(){
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal.get(Calendar.MONTH) + 1;
    }

    static int getLastMonthNumber(){
        return  getCurrentMonthNumber() - 1 ;
    }

    static int getCurrentYear(){
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal.get(Calendar.YEAR);
    }

    public static int getProgressBarWidthInPx(double progress){
        double dP = (progress >= 1.0) ? PROGRESS_BAR_WIDTH_IN_DP : progress*PROGRESS_BAR_WIDTH_IN_DP;

        return convertDpToPx((int) dP);
    }

    private static int convertDpToPx(int input) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, input, metrics);

        return (int) px;
    }

    public static List<FormattedHistoryEntry> formatList(List<HistoryEntry> archiveEntries){
        List<FormattedHistoryEntry> formattedHistoryEntries  = new ArrayList<>();

        int currentDate = 0;
        for(HistoryEntry entry: archiveEntries){
            if(entry.getMonth() != currentDate) {
                FormattedHistoryEntry formattedHistoryEntry = new FormattedHistoryEntry(entry.getDate(), 0, 0, true);
                formattedHistoryEntries.add(formattedHistoryEntry);
                formattedHistoryEntry = new FormattedHistoryEntry(entry.getCategoryName(),  entry.getValue(), entry.getMaxValue(), false);
                formattedHistoryEntries.add(formattedHistoryEntry);
                currentDate = entry.getMonth();
            } else {
                FormattedHistoryEntry formattedHistoryEntry = new FormattedHistoryEntry(entry.getCategoryName(),  entry.getValue(), entry.getMaxValue(), false);
                formattedHistoryEntries.add(formattedHistoryEntry);
            }
        }
        return formattedHistoryEntries;
    }

    public static String upperString(String stringToUpper){
        return stringToUpper.substring(0,1).toUpperCase() + stringToUpper.substring(1);
    }
}
