package com.github.jcaiqueoliveira.checkversionapp.request;

import android.os.AsyncTask;
import com.github.jcaiqueoliveira.checkversionapp.interfaces.ReturnListener;
import com.github.jcaiqueoliveira.checkversionapp.utils.DefaultArtifactVersion;
import com.github.jcaiqueoliveira.checkversionapp.utils.LocalAppInformation;
import org.jsoup.Jsoup;

public class RequestVersionApp extends AsyncTask<Void, Void, Boolean> {

    private LocalAppInformation localAppInformation;
    private ReturnListener returnListener;

    public RequestVersionApp(LocalAppInformation localAppInformation, ReturnListener returnListener) {
        this.localAppInformation = localAppInformation;
        this.returnListener = returnListener;
    }

    private boolean requestAppVersion() {
        try {
            String storeVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + this.localAppInformation.getPackName() + "&hl=it")
                    .timeout(localAppInformation.getTimeOut())
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
                    .select("div[itemprop=softwareVersion]")
                    .first()
                    .ownText();
            return hasNewVersion(this.localAppInformation.getCurrentVersion(), storeVersion);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean hasNewVersion(String localVersion, String storeVersion) {
        DefaultArtifactVersion localVersionMvn = new DefaultArtifactVersion(localVersion);
        DefaultArtifactVersion storeVersionMvn = new DefaultArtifactVersion(storeVersion);
        return localVersionMvn.compareTo(storeVersionMvn) == -1 && !localVersion.equals("");
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return requestAppVersion();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        this.returnListener.onReturnListener(aBoolean);
    }
}
