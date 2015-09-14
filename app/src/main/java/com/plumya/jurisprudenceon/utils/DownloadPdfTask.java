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

import com.plumya.jurisprudenceon.R;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by miltomasz on 04/09/15.
 */
public class DownloadPdfTask extends AsyncTask<String, Void, String> {

    private static final String TAG = DownloadPdfTask.class.getSimpleName();
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
            } catch (IOException e){
                e.printStackTrace();
            }
            PdfDownloader.downloadFile(mFileUrl, mPdfFile);
        }
        return mPdfFile.getAbsolutePath();
    }

    @Override
    protected void onPostExecute(String fileUrl) {
        super.onPostExecute(fileUrl);
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        File pdfFile = new File(fileUrl);
        Uri path = Uri.fromFile(pdfFile);
        Log.v(TAG, "Uri to downloaded pdf file: " + path);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(Intent.EXTRA_STREAM, path);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(fileUrl);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
        intent.setType(mimeType);
        intent.setData(path);

        givePermissionToOpenPdfToAllApps(path, intent);

        Intent chooser = Intent.createChooser(intent, mActivity.getString(R.string.open_pdf_file));
        try {
            mActivity.startActivity(chooser);
        } catch (ActivityNotFoundException e) {
            Log.v(TAG, "No application to open the file: " + e.getMessage());
        }
    }

    private void givePermissionToOpenPdfToAllApps(Uri path, Intent intent) {
        PackageManager pm = mActivity.getPackageManager();
        // Give permissions to the file to each external app that can open the file
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo externalApp: activities){
            String packageName = externalApp.activityInfo.packageName;
            mActivity.grantUriPermission(packageName, path, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }
}
