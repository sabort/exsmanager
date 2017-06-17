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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.nodomain.sabdevs.expensesmonitor.R;
import com.nodomain.sabdevs.expensesmonitor.activities.addcategory.EditCategoriesActivity;
import com.nodomain.sabdevs.expensesmonitor.activities.addcategory.EditCategoryDialogFragment;
import com.nodomain.sabdevs.expensesmonitor.dataobjects.ExpensesMonthSummary;
import com.nodomain.sabdevs.expensesmonitor.utils.DBHelper;
import com.nodomain.sabdevs.expensesmonitor.utils.EMUtils;

import java.util.List;

/**
 * Created by sabort on 03.05.17.
 */

public class CategoriesEditListArrayAdapter extends ArrayAdapter<ExpensesMonthSummary> {

    private final Context context;
    private List<ExpensesMonthSummary> categories;

    public CategoriesEditListArrayAdapter(Context context, List<ExpensesMonthSummary> categories) {
        super(context, -1, categories);
        this.context = context;
        this.categories = categories;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ExpensesMonthSummary expensesMonthSummary = categories.get(position);
        final Context context = parent.getContext();
        final FragmentManager fm = ((Activity) context).getFragmentManager();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.edit_category_layout, parent, false);

        final TextView categoryEditLabelText = (TextView) rowView.findViewById(R.id.categoryEditLabelText);
        final TextView categoryEditMaxValue = (TextView) rowView.findViewById(R.id.categoryEditMaxValue);


        categoryEditLabelText.setText(EMUtils.upperString(expensesMonthSummary.getCategoryName()));
        categoryEditMaxValue.setText(expensesMonthSummary.getMaxValueText());

        Button editButton = (Button) rowView.findViewById(R.id.buttonEditCategory);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newDialog = EditCategoryDialogFragment.newInstance(expensesMonthSummary.getCategoryName(), expensesMonthSummary.getCategoryLimit(), expensesMonthSummary.getId());
                newDialog.show(fm, "Edit category");
            }
        });

        final Button deleteButton = (Button) rowView.findViewById(R.id.buttonDeleteCategory);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCategoryAlertDialog(expensesMonthSummary.getCategoryName(), expensesMonthSummary.getId());
            }
        });

        return rowView;
    }


    public List<ExpensesMonthSummary> getData() {
        return categories;
    }

    public void deleteCategory(int categoryId){
        DBHelper emdb = new DBHelper(getContext());
        emdb.deleteCategory(categoryId);
    }


    private void deleteCategoryAlertDialog(final String categoryName, final int categoryId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getContext().getResources().getString(R.string.to_remove) + " " + categoryName + "?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteCategory(categoryId);
                ((EditCategoriesActivity) context).updateAdapter();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}