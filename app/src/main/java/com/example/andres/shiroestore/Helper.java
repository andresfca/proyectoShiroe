package com.example.andres.shiroestore;

import android.app.Activity;
import android.widget.EditText;

public class Helper {
    public static boolean isEmpty(EditText text) {
        if (text.getText().toString().isEmpty()){
            text.requestFocus();
            text.setError("The field can not be empty");
            return true;
        }
        return false;
    }
}
