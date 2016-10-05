package com.github.jcaiqueoliveira.checkversionapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.github.jcaiqueoliveira.checkversionapp.interfaces.ReturnConfiguration;
import com.github.jcaiqueoliveira.checkversionapp.interfaces.ReturnListener;
import com.github.jcaiqueoliveira.checkversionapp.request.RequestVersionApp;
import com.github.jcaiqueoliveira.checkversionapp.utils.LocalAppInformation;
import com.github.jcaiqueoliveira.checkversionapp.utils.TypeMode;

/**
 * Created by caique.oliveira on 22/09/2016.
 */

public class AppVersion implements ReturnConfiguration, ReturnListener {
    private String currentVersion;
    private String packageName;
    private Activity activity;
    private String dialogTitle;
    private String dialogMessage;
    private AlertDialog.Builder builder;
    private boolean forceUpdate = false;
    private int returnMode;
    private Intent intent;
    private ReturnListener mListener;
    private String textUpdateButton;
    private LocalAppInformation localAppInformation;

    public AppVersion(Activity activity, Intent intent) throws PackageManager.NameNotFoundException {
        this.activity = activity;
        this.intent = intent;
        this.currentVersion = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
        this.packageName = activity.getPackageName();
        returnMode = TypeMode.RETURN_WITH_SHOW_DIALOG;
        loadLocalInformations();
    }

    public AppVersion(Activity activity, ReturnListener returnListener) throws PackageManager.NameNotFoundException {
        this.activity = activity;
        this.currentVersion = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
        this.mListener = returnListener;
        loadLocalInformations();
        returnMode = TypeMode.RETURN_IN_BACKGROUND;
    }

    @Override
    public void setDialogTitle(int dialogTitle) {
        this.dialogTitle = activity.getString(dialogTitle);
    }

    @Override
    public void setDialogMessage(int dialogMessage) {
        this.dialogMessage = activity.getString(dialogMessage);
    }

    @Override
    public void setCustomDialog(AlertDialog.Builder builder) {
        this.builder = builder;
    }

    @Override
    public void setForceUpdate(boolean isToForce) {
        this.forceUpdate = isToForce;
    }

    /**
     * @param returnInBackground
     * @Deprecated Use the constructor with the listener
     */
    @Deprecated
    @Override
    public void setListener(ReturnListener returnInBackground) {
        this.mListener = returnInBackground;
    }


    @Override
    public void setTextUpdateButton(int textUpdateButton) {
        this.textUpdateButton = activity.getString(textUpdateButton);
    }

    @Override
    public void setTimeOut(int millis) {
        this.localAppInformation.setTimeOut(millis);
    }

    public void runVerification() {
        new RequestVersionApp(localAppInformation, this).execute();
    }

    @Override
    public void onReturnListener(boolean hasUpdate) {
        if (hasUpdate && returnMode == TypeMode.RETURN_WITH_SHOW_DIALOG) {
            if (builder == null) {
                createDialog();
            }
            builder.show();
        } else if (returnMode == TypeMode.RETURN_IN_BACKGROUND) {
            if (mListener != null)
                mListener.onReturnListener(hasUpdate);
            else
                throw new NullPointerException("Listener not found. You have set the returnListener?");
        } else {
            activity.startActivity(intent);
            activity.finish();
        }
    }

    private void createDialog() {
        builder = new AlertDialog.Builder(activity);
        builder.setTitle(dialogTitle == null ? "Update App" : dialogTitle);
        builder.setMessage(dialogMessage == null ? "The app has a new version" : dialogMessage);
        builder.setCancelable(false);
        if (!forceUpdate) {
            builder.setNegativeButton(activity.getString(R.string.close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activity.startActivity(intent);
                    dialog.dismiss();
                }
            });
        }
        builder.setPositiveButton("Atualizar agora", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                dialog.cancel();
            }
        });
    }

    private void loadLocalInformations() {
        localAppInformation = new LocalAppInformation();
        localAppInformation.setCurrentVersion(this.currentVersion);
        localAppInformation.setPackName(this.packageName);
        localAppInformation.setTimeOut(5000);
    }
}
