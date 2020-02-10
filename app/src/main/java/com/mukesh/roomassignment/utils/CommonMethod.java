package com.mukesh.roomassignment.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class CommonMethod {

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
