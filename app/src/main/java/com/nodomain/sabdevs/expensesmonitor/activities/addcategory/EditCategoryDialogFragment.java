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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nodomain.sabdevs.expensesmonitor.R;

/**
 * Created by sabort on 04.05.17.
 */

public class EditCategoryDialogFragment extends AddCategoryDialogFragment {

    public static EditCategoryDialogFragment newInstance(String categoryName, int maxValue, int catId) {

        EditCategoryDialogFragment fragment = new EditCategoryDialogFragment();
        Bundle args = createArgs(categoryName, maxValue, catId);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_category_dialog_layout, null);

        setEditTextCurrentValues(view);

        builder.setView(view)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Because of validation, onClick method is overriden
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Because of validation, onClick method is overriden
                    }
                });

        return builder.create();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        final AlertDialog alertDialog = (AlertDialog)getDialog();
        if(alertDialog != null)
        {
            Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    validateDataAndSendToDb(alertDialog, getArguments().getInt("id"));
                }
            });
        }
    }

    private void setEditTextCurrentValues(View view) {
        setEditTexCategoryName(view);
        setEditTextMaxValue(view);
    }

    private void setEditTextMaxValue(View view) {
        int maxValue = getArguments().getInt("maxValue");
        EditText editTextMaxValue = (EditText) view.findViewById(R.id.editTextMaxValue);
        editTextMaxValue.setText(String.valueOf(maxValue));
    }

    private void setEditTexCategoryName(View view) {
        String categoryName = getArguments().getString("categoryName");
        EditText editTextCategoryName = (EditText) view.findViewById(R.id.editTextCategoryName);
        editTextCategoryName.setText(categoryName);
    }

    private static Bundle createArgs(String categoryName, int maxValue, int catId){
        Bundle args = new Bundle();
        args.putString("categoryName", categoryName);
        args.putInt("maxValue", maxValue);
        args.putInt("id", catId);
        return args;
    }


}