package com.tt.ly.tools;

import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Seven on 17/3/11.
 */
public class HttpClient {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String TAG = "HttpClient";

    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        if (TextUtils.isEmpty(url)) {
            Log.e(TAG, "url not provided");
            return;
        }
        client.get(url, responseHandler);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (TextUtils.isEmpty(url)) {
            Log.e(TAG, "url not provided");
            return;
        }
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (TextUtils.isEmpty(url)) {
            Log.e(TAG, "url not provided");
            return;
        }
        client.post(url, params, responseHandler);
    }

}
