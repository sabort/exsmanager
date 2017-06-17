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
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.nodomain.sabdevs.expensesmonitor.R;
import com.nodomain.sabdevs.expensesmonitor.utils.DBHelper;


/**
 * Created by sabort on 03.05.17.
 */

public class AddCategoryDialogFragment extends DialogFragment {

    public static final int INSERT = -1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_category_dialog_layout, null);

        builder.setView(view)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
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
                    validateDataAndSendToDb(alertDialog, INSERT);
                }
            });
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    protected void validateDataAndSendToDb(AlertDialog alertDialog, int catId){
        EditText editTextCategoryName = (EditText) alertDialog.findViewById(R.id.editTextCategoryName);
        EditText editTextMaxValue = (EditText) alertDialog.findViewById(R.id.editTextMaxValue);
        validateRequiredFields(editTextCategoryName, editTextMaxValue);
        insertCategoryToDbIfNotEmpty(editTextCategoryName, editTextMaxValue, catId);
    }

    private void validateRequiredFields(EditText editTextCategoryName, EditText editTextMaxValue){
        validateRequiredEditText(editTextCategoryName, getResources().getString(R.string.cat_name_empty_err));
        validateRequiredEditText(editTextMaxValue, getResources().getString(R.string.max_value_empty_err));
    }

    private void validateRequiredEditText(EditText editText, String errorMsg){
        if(TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError(errorMsg);
        }
    }

    private void insertCategoryToDbIfNotEmpty(EditText editTextCategoryName, EditText editTextMaxValue, int catId) {
        if (checkIfNotEmpty(editTextCategoryName, editTextMaxValue)) {
            insertCategoryToDb(editTextCategoryName, editTextMaxValue, catId);
            ((EditCategoriesActivity) getActivity()).updateAdapter();
            dismiss();
        }
    }

    protected boolean checkIfNotEmpty(EditText editTextCategoryName, EditText editTextMaxValue){
        String newCategoryName = editTextCategoryName.getText().toString();
        String newCategoryMaxValueText = editTextMaxValue.getText().toString();
        return !TextUtils.isEmpty(newCategoryName) && !TextUtils.isEmpty(newCategoryMaxValueText);
    }

    private void insertCategoryToDb(EditText editTextCategoryName, EditText editTextMaxValue, int catId){
        String categoryMaxValue = editTextMaxValue.getText().toString();
        String categoryName = editTextCategoryName.getText().toString();
        int maxValue = Integer.parseInt(categoryMaxValue);
        if(catId<0) {
            new DBHelper(getActivity()).insertCategory(categoryName, maxValue);
        } else {
            new DBHelper(getActivity()).updateCategory(catId, categoryName, maxValue);

        }
    }

}