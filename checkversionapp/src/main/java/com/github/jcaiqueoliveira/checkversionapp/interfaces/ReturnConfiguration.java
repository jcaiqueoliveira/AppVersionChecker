package com.github.jcaiqueoliveira.checkversionapp.interfaces;

import android.app.AlertDialog;
import com.github.jcaiqueoliveira.checkversionapp.utils.TypeMode;

/**
 * Created by caique.oliveira on 22/09/2016.
 */

public interface ReturnConfiguration {
    void setDialogTitle(int dialogTitle);

    void setDialogMessage(int dialogMessage);

    void setCustomDialog(AlertDialog.Builder builder);

    void setForceUpdate(boolean isToForce);

    void setReturnMode(@TypeMode.ReturnMode int mode);

    void setListener(ReturnListener returnInBackground);

    void setTextUpdateButton(int textUpdateButton);

    void setTimeOut(int millis);
}
