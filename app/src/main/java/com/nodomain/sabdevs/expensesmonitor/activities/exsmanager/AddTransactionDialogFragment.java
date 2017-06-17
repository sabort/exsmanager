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

package com.nodomain.sabdevs.expensesmonitor.activities.exsmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.nodomain.sabdevs.expensesmonitor.R;
import com.nodomain.sabdevs.expensesmonitor.activities.exsmanager.ExsManagerActivity;
import com.nodomain.sabdevs.expensesmonitor.ui.ValuesSpinner;
import com.nodomain.sabdevs.expensesmonitor.utils.DBHelper;

import java.util.Map;


/**
 * Created by sabort on 07.01.17.
 */

public class AddTransactionDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.add_transaction_dialog_layout, null);
        final Spinner spinner = createSpinner(view);

        builder.setView(view)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        passDataFromViewToDb(view, spinner);
                        ((ExsManagerActivity) getActivity()).updateAdapter();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Closes dialog
                    }
                });

        return builder.create();
    }

    private void passDataFromViewToDb(View view, Spinner spinner) {
        int categoryId = getCategoryId(spinner);
        int currentValue = new ValuesSpinner(view).getSelectedValue();
        new DBHelper(getActivity()).insertTransaction(categoryId, currentValue);
    }

    private int getCategoryId(Spinner spinner){
        return getCategoriesMap().get(spinner.getSelectedItem().toString());
    }

    private Spinner createSpinner(View view){
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(getAdapter());
        return spinner;
    }

    private ArrayAdapter<String> getAdapter(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getSpinnerArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private String[] getSpinnerArray(){
        return getCategoriesMap().keySet().toArray(new String[0]);
    }

    private Map<String, Integer> getCategoriesMap(){
        return new DBHelper(getActivity()).getCategoriesMap();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }

}


