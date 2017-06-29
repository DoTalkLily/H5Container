package com.tt.ly.offlinepackage;

import android.os.AsyncTask;
import com.tt.ly.tools.FileDecompresser;
import com.tt.ly.tools.FileDownloader;

import java.io.File;

/**
 * Created by Seven on 17/3/12.
 */

public class FetchResourceTask extends AsyncTask<String, Void, Exception> {

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Exception doInBackground(String... params) {
        if(params == null || params.length != 2){
            return null;
        }
        String url = params[0];
        String savePath = params[1];
        String fileName = params[2];

        try {
            downloadAllAssets(url, savePath, fileName);
        } catch (Exception e) {
            return e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Exception result) {
    }

    private void downloadAllAssets(String url, String savePath, String fileName) {
        File zipFile = new File(savePath + "/"+fileName+".zip");
        File outputDir = new File(savePath + "/"+fileName);

        try {
            FileDownloader.download(url, zipFile, new File(savePath));
            unzipFile(zipFile, outputDir);
        } finally {
            zipFile.delete();
        }
    }

    protected void unzipFile(File zipFile, File destination) {
        String savePath = destination.getPath() + File.separator;
        FileDecompresser decomp = new FileDecompresser(zipFile.getPath(), savePath);
        decomp.unzip();
    }
}

