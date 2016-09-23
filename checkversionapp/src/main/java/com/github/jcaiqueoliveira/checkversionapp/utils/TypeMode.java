package com.github.jcaiqueoliveira.checkversionapp.utils;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public abstract class TypeMode {
    public static final int RETURN_WITH_SHOW_DIALOG = 0;
    public static final int RETURN_IN_BACKGROUND = 1;

    @IntDef({RETURN_WITH_SHOW_DIALOG, RETURN_IN_BACKGROUND})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ReturnMode {
    }
}