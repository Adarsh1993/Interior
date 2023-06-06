package com.optic.opticvyu.customviews;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.RadioButton;

import com.optic.interior.R;

public class HRD_option extends Dialog {

    public HRD_option(Context context, OnOptionSelectedListener listener) {
        super(context);
        setContentView(R.layout.radio_button_dialog);
        setTitle("Select an option");

        RadioButton radioButton1 = findViewById(R.id.radio_button_1);
        RadioButton radioButton2 = findViewById(R.id.radio_button_2);

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOptionSelected("ON");
                dismiss();
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOptionSelected("OFF");
                dismiss();
            }
        });
    }

    public interface OnOptionSelectedListener {
        void onOptionSelected(String option);
    }
}
