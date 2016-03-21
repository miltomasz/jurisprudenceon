package com.plumya.jurisprudenceon.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;

/**
 * Created by miltomasz on 04/09/15.
 */
public class PdfDownloader {
    private static final int  MEGABYTE = 1024 * 1024;

    public static void downloadFile(String fileUrl, File file) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(30000);

            InputStream inputStream = urlConnection.getInputStream();
            fileOutputStream = new FileOutputStream(file);

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while((bufferLength = inputStream.read(buffer))>0 ){
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}