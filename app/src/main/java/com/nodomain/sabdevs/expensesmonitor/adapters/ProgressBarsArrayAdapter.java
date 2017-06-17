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
import android.widget.ImageView;
import android.widget.TextView;

import com.nodomain.sabdevs.expensesmonitor.dataobjects.ExpensesMonthSummary;
import com.nodomain.sabdevs.expensesmonitor.R;
import com.nodomain.sabdevs.expensesmonitor.utils.EMUtils;

import java.util.List;

/**
 * Created by sabort on 02.04.17.
 */

public class ProgressBarsArrayAdapter extends ArrayAdapter<ExpensesMonthSummary> {

    private final Context context;
    private View rowView;
    private List<ExpensesMonthSummary> categories;
    private static final double PROGRESS_WARNING_LEVEL = 0.9;

    public ProgressBarsArrayAdapter(Context context, List<ExpensesMonthSummary> categories) {
        super(context, -1, categories);
        this.context = context;
        this.categories = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ExpensesMonthSummary expensesMonthSummary = categories.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.progress_bar_layout, parent, false);

        setCategoryNameText(expensesMonthSummary.getCategoryName());
        setCategoryCurrentValueText(expensesMonthSummary.getCurrentValueText());
        setCategoryLimitText(expensesMonthSummary.getMaxValueText());
        setCategoryWhatLeftValueText(expensesMonthSummary.getWhatLeftText());

        if(expensesMonthSummary.isExceeded()){
            setNotifyColor();
        }

        setProgressBar(rowView, expensesMonthSummary.getProgress());

        return rowView;
    }

    public List<ExpensesMonthSummary> getData() {
        return categories;
    }

    private void setCategoryNameText(String categoryName) {
        TextView categoryNameTv = (TextView) rowView.findViewById(R.id.categoryNameTv);
        categoryNameTv.setText(EMUtils.upperString(categoryName));
    }

    private void setCategoryCurrentValueText(String currentValueText) {
        TextView categoryValueTv = (TextView) rowView.findViewById(R.id.categoryValueTv);
        categoryValueTv.setText(currentValueText);
    }

    private void setCategoryLimitText(String maxValueText) {
        TextView categoryLimitTv = (TextView) rowView.findViewById(R.id.categoryLimitTv);
        categoryLimitTv.setText(maxValueText);
    }

    private void setCategoryWhatLeftValueText(String whatLeftText) {
        TextView categoryWhatLeftTv = (TextView) rowView.findViewById(R.id.categoryWhatLeftTv);
        categoryWhatLeftTv.setText(whatLeftText);
    }

    private void setNotifyColor() {
        TextView categoryValueTv = (TextView) rowView.findViewById(R.id.categoryValueTv);
        categoryValueTv.setTextColor(ContextCompat.getColor(context,  R.color.colorMaterialRed));
        TextView categoryWhatLeftTv = (TextView) rowView.findViewById(R.id.categoryWhatLeftTv);
        categoryWhatLeftTv.setTextColor(ContextCompat.getColor(context,  R.color.colorMaterialRed));
    }

    private void setProgressBar(View rowView, double progress) {
        ImageView imageView = (ImageView) rowView.findViewById(R.id.categoryBarFill);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = EMUtils.getProgressBarWidthInPx(progress);
        imageView.setLayoutParams(layoutParams);

        if(progress >= PROGRESS_WARNING_LEVEL) {
            imageView.setBackgroundResource(R.drawable.rectangle_fill_yellow);
        }
    }
}
