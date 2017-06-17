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

package com.nodomain.sabdevs.expensesmonitor.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

/**
 * Created by sabort on 29.05.17.
 */

public class EmNumberPicker extends NumberPicker{

    public EmNumberPicker(Context context){
        super(context);
        this.setMaxValue(9);
        this.setMinValue(0);
        this.setWrapSelectorWheel(true);
    }

    public EmNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setMaxValue(9);
        this.setMinValue(0);
        this.setWrapSelectorWheel(true);
    }

    public EmNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setMaxValue(9);
        this.setMinValue(0);
        this.setWrapSelectorWheel(true);
    }

    public EmNumberPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.setMaxValue(9);
        this.setMinValue(0);
        this.setWrapSelectorWheel(true);
    }
}
