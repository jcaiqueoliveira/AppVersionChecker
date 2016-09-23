package com.github.jcaiqueoliveira.checkversionapp.utils;

/**
 * Created by caique.oliveira on 22/09/2016.
 */

public class LocalAppInformation {
    private String packName;
    private int timeOut;
    private String currentVersion;

    public LocalAppInformation() {
        this.timeOut = 5000;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }
}
