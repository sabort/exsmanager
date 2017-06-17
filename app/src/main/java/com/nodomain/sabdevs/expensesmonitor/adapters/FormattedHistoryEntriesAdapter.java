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

package com.nodomain.sabdevs.expensesmonitor.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nodomain.sabdevs.expensesmonitor.R;
import com.nodomain.sabdevs.expensesmonitor.dataobjects.FormattedHistoryEntry;
import com.nodomain.sabdevs.expensesmonitor.utils.EMUtils;

import java.util.List;

/**
 * Created by sabort on 29.04.17.
 */

public class FormattedHistoryEntriesAdapter extends ArrayAdapter<FormattedHistoryEntry> {

    private final Context context;
    private List<FormattedHistoryEntry> formattedHistoryEntries;
    private View rowView;

    public FormattedHistoryEntriesAdapter(Context context, List<FormattedHistoryEntry> formattedHistoryEntries) {
        super(context, -1, formattedHistoryEntries);
        this.context = context;
        this.formattedHistoryEntries = formattedHistoryEntries;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        FormattedHistoryEntry formattedHistoryEntry = formattedHistoryEntries.get(position);
        if (formattedHistoryEntry.isMonth()) {
            rowView = inflater.inflate(R.layout.history_entry_month_layout, parent, false);
            setHistoryMonthText(formattedHistoryEntry.getCategoryName());
        } else {
            rowView = inflater.inflate(R.layout.history_entry_category_layout, parent, false);
            setHistoryCategoryNameText(formattedHistoryEntry.getCategoryName());
            setHistoryMonthValueText(formattedHistoryEntry.getSumSpentByCategoryText());
            setNotifyColorIfExceeded(formattedHistoryEntry.isExceeded());
            setHistoryMonthMaxValueText(formattedHistoryEntries.get(position).getCategoryLimitText());
        }

        return rowView;
    }

    private void setHistoryMonthText(String categoryName) {
        TextView historyMonthTextView = (TextView) rowView.findViewById(R.id.formattedHistoryEntryMonthText);
        historyMonthTextView.setText(categoryName);
    }

    private void setHistoryCategoryNameText(String categoryName) {
        TextView historyCategoryNameTextView = (TextView) rowView.findViewById(R.id.formattedHistoryEntryCategory);
        historyCategoryNameTextView.setText(EMUtils.upperString(categoryName));
    }

    private void setHistoryMonthValueText(String sumSpentByCategoryText) {
        TextView historyMonthValueView = (TextView) rowView.findViewById(R.id.formattedHistoryEntryValue);
        historyMonthValueView.setText(sumSpentByCategoryText);
    }

    private void setNotifyColorIfExceeded(boolean isExceeded) {
        if(isExceeded){
            TextView historyMonthValueView = (TextView) rowView.findViewById(R.id.formattedHistoryEntryValue);
            historyMonthValueView.setTextColor(ContextCompat.getColor(context, R.color.colorMaterialRed));
        }
    }

    private void setHistoryMonthMaxValueText(String categoryLimitText) {
        TextView historyMonthMaxValueTextView = (TextView) rowView.findViewById(R.id.formattedHistoryEntryMaxValue);
        historyMonthMaxValueTextView.setText(categoryLimitText);
    }
}
