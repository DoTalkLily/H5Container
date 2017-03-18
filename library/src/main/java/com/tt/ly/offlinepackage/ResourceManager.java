package com.tt.ly.offlinepackage;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tt.ly.common.Consts;
import com.tt.ly.common.ResponseWrapper;
import com.tt.ly.tools.HttpClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Seven on 17/3/13.
 */

public class ResourceManager {

    private Context mContext;
    private static Gson sGson = new Gson();
    private List<ResourceRecord> mResourceRecordList;
    private static final String TAG = "ResourceManager";

    public ResourceManager(Context applicationContext) {
        mContext = applicationContext;
    }

    private void fetchResources() {
        String url = Consts.DEFAULT_RESOURCE_URL;
        String savePath = mContext.getFilesDir().getPath();
        new FetchResourceTask().execute(url, savePath);
    }

    public void fetchConfig() {
        RequestParams params = new RequestParams();
        params.put("applicationPackage", mContext.getPackageName());
        HttpClient.get(Consts.RESOURCE_RECORD_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processResourceRecord(response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void processResourceRecord(String response) {
        if (response == null) {
            Log.e(TAG, "response empty");
            return;
        }

        ResponseWrapper result = sGson.fromJson(response, ResponseWrapper.class);

        if (result.isError()) {
            Log.e(TAG, result.getErrMsg());
            return;
        }

        if (mResourceRecordList == null) {
            mResourceRecordList = new ArrayList<>();
        }

        if (result.getData() == null) {
            Log.i(TAG, "resource record empty for package: " + mContext.getPackageName());
            return;
        }

        mResourceRecordList = (List<ResourceRecord>) result.getData();
    }

}
