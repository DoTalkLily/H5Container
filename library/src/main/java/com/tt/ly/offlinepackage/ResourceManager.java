package com.tt.ly.offlinepackage;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tt.ly.common.Consts;
import com.tt.ly.common.ResponseWrapper;
import com.tt.ly.tools.FileUtils;
import com.tt.ly.tools.HttpClient;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Seven on 17/3/13.
 */

public class ResourceManager {
    private Context mContext;
    private static Gson mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
    private Map<String, ResourceRecord> mNameRecordMap;
    private static final String TAG = "ResourceManager";

    public ResourceManager(Context applicationContext) {
        mContext = applicationContext;
        initConfig();
    }

    //初始化资源记录
    public void initConfig() {
        String path = mContext.getFilesDir().getPath() + "/config.json";
        String recordStr = FileUtils.readFile(path);

        if (recordStr == null) {
            Log.w(TAG, "config file not found");
            return;
        }

        List<ResourceRecord> records = mGson.fromJson(recordStr,
                new TypeToken<List<ResourceRecord>>() {
                }.getType());

        if (records == null || records.size() == 0) {
            Log.e(TAG, "config content is null");
            return;
        }

        for (int i = 0; i < records.size(); i++) {
            ResourceRecord record = records.get(i);
            mNameRecordMap.put(record.getName(), record);
        }
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
        List<ResourceRecord> newConfig = getResourceRecord(response);
        Map<String, ResourceRecord> resourceRecordMap = transformListToMap(newConfig);
        List<ResourceRecord> recordsOutdated = getResourceNeedToBeDeleted(resourceRecordMap);
        List<ResourceRecord> recordsShouldBeDownloaded = getResourceNeedToBeUpdated(newConfig);

        mNameRecordMap = resourceRecordMap;
        //update resource version
        if (recordsShouldBeDownloaded != null) {
            for (int i = 0; i < recordsShouldBeDownloaded.size(); i++) {
                fetchResources(recordsShouldBeDownloaded.get(i));
            }
        }
        //delete outdated files
        if (recordsOutdated != null) {
            for (int i = 0; i < recordsOutdated.size(); i++) {
                FileUtils.deleteAllFilesOfDir(new File(recordsOutdated.get(i).getName()));
            }
        }
    }

    private void fetchResources(ResourceRecord record) {
        Log.i(TAG, "fetching ..." + record.getName());

        String url = Consts.DEFAULT_RESOURCE_URL + record.getName();
        String savePath = mContext.getFilesDir().getPath();
        new FetchResourceTask().execute(url, savePath, record.getName());
    }

    private List<ResourceRecord> getResourceNeedToBeUpdated(List<ResourceRecord> newConfig) {
        List<ResourceRecord> newRecord = new ArrayList<>();

        for (int i = 0; i < newConfig.size(); i++) {
            ResourceRecord record = newConfig.get(i);
            if (mNameRecordMap.containsKey(record.getName())) {
                ResourceRecord oldRecord = mNameRecordMap.get(record.getName());
                if (!record.getMd5().equals(oldRecord.getMd5())) {
                    newRecord.add(record);//这里先不计入要删除列表中，以免资源下载、解压失败，更新过程中再删除旧的
                }
            } else {
                newRecord.add(record);
            }
        }

        return newRecord;
    }

    private Map<String, ResourceRecord> transformListToMap(List<ResourceRecord> newConfig){
        Map<String, ResourceRecord> newConfigMap = new HashMap<>();

        for (int i = 0; i < newConfig.size(); i++) {
            ResourceRecord record = newConfig.get(i);
            newConfigMap.put(record.getName(), record);
        }

        return  newConfigMap;
    }

    private List<ResourceRecord> getResourceNeedToBeDeleted(Map<String, ResourceRecord> newConfigMap) {
        if (mNameRecordMap == null || mNameRecordMap.size() == 0) {
            return null;
        }

        List<ResourceRecord> outDated = new ArrayList<>();
        Map.Entry<String, ResourceRecord> entry;
        Iterator<Map.Entry<String, ResourceRecord>> iterator = mNameRecordMap.entrySet().iterator();

        while (iterator.hasNext()) {
            entry = iterator.next();
            if (newConfigMap.containsKey(entry.getKey())) {
                ResourceRecord record = newConfigMap.get(entry.getKey());
                if (!record.getMd5().equals(entry.getValue().getMd5())) {
                    outDated.add(entry.getValue());
                }
            } else {
                outDated.add(entry.getValue());
            }
        }
        return outDated;
    }

    private List<ResourceRecord> getResourceRecord(String response) {
        if (response == null) {
            Log.e(TAG, "response empty");
            return null;
        }

        ResponseWrapper result = mGson.fromJson(response, ResponseWrapper.class);

        if (result.isError()) {
            Log.e(TAG, result.getErrMsg());
            return null;
        }

        if (result.getData() == null) {
            Log.i(TAG, "resource record empty for package: " + mContext.getPackageName());
            return null;
        }

        return result.getData();
    }

}
