package com.plumya.jurisprudenceon.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.parse.ParseInstallation;
import com.plumya.jurisprudenceon.R;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;

/**
 * Created by miltomasz on 04/09/15.
 */
public class DownloadPdfTask extends AsyncTask<String, Void, String> {

    private static final String TAG = DownloadPdfTask.class.getSimpleName();
    public static final String EMPTY_STRING = "";
    private Activity mActivity;
    private String mFileUrl;
    private File mPdfFile;
    private ProgressDialog mProgressDialog;

    public DownloadPdfTask(Activity activity, String urlToPdf) {
        mActivity = activity;
        mFileUrl = urlToPdf;
        mProgressDialog = new ProgressDialog(mActivity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        String extStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();

        File folder = new File(extStorageDirectory, mActivity.getString(R.string.judgements_folder));
        folder.mkdir();

        final String SLASH = "/";
        int length = mFileUrl.split(SLASH).length;
        String fileName = mFileUrl.split(SLASH)[length - 1];
        mPdfFile = new File(folder, fileName);
        if (!mPdfFile.exists()) {
            mProgressDialog.setMessage(mActivity.getString(R.string.downloading_judgement));
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.show();
        }
    }

    @Override
    protected String doInBackground(String... urlToPdf) {
        if (!mPdfFile.exists()) {
            try {
                mPdfFile.createNewFile();
                PdfDownloader.downloadFile(mFileUrl, mPdfFile);
                return mPdfFile.getAbsolutePath();
            } catch (IOException e) {
                String objectId = ParseInstallation.getCurrentInstallation().getObjectId();
                Crashlytics.logException(e);
                Crashlytics.log(Log.ERROR, TAG,
                        "Exception occurred while downloading file: " + mFileUrl +
                                ", for objectId: " + objectId);
                if (mPdfFile.exists()) {
                    mPdfFile.delete();
                }
            }
        } else {
            return mPdfFile.getAbsolutePath();
        }
        return EMPTY_STRING;
    }

    @Override
    protected void onPostExecute(String fileUrl) {
        super.onPostExecute(fileUrl);
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        if (StringUtils.isEmpty(fileUrl)) {
            Toast.makeText(mActivity, mActivity.getString(R.string.check_internet_connection),
                    Toast.LENGTH_LONG).show();
            return;
        }
        File pdfFile = new File(fileUrl);
        Uri path = Uri.fromFile(pdfFile);
        Log.v(TAG, "Uri to downloaded pdf file: " + path);

        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(pdfFile),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent chooser = Intent.createChooser(target, mActivity.getString(R.string.open_pdf_file));

        try {
            mActivity.startActivity(chooser);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
            Log.d(TAG, "No app can perform the action");
            Toast.makeText(mActivity, R.string.app_not_available, Toast.LENGTH_LONG).show();
        }
    }

    private void givePermissionToOpenPdfToAllApps(Uri path, Intent intent) {
        PackageManager pm = mActivity.getPackageManager();
        // Give permissions to the file to each external app that can open the file
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo externalApp: activities){
            String packageName = externalApp.activityInfo.packageName;
            mActivity.grantUriPermission(packageName, path, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }
}
