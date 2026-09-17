package com.ferhatozcelik.wear.example.common;

import android.content.SharedPreferences;

import com.google.android.gms.wearable.DataMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Shared helpers that serialise the QR code list and exchange it over the Wearable Data Layer.
 */
public final class WearDataStore {

    public static final String DATA_PATH = "/data_path";
    public static final String PREFS_NAME = "Shared_Preferences";
    public static final String PREF_KEY_DATA_LIST = "dataList";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DATA = "data";
    public static final String KEY_IMAGE = "image";

    private WearDataStore() {
    }

    public static List<Data> load(SharedPreferences preferences) {
        String json = preferences.getString(PREF_KEY_DATA_LIST, null);
        Type type = new TypeToken<ArrayList<Data>>() {
        }.getType();
        List<Data> items = new Gson().fromJson(json, type);
        return items != null ? items : new ArrayList<Data>();
    }

    public static void save(SharedPreferences preferences, List<Data> items) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_KEY_DATA_LIST, new Gson().toJson(items));
        editor.apply();
    }

    public static ArrayList<DataMap> toDataMapList(List<Data> items) {
        ArrayList<DataMap> maps = new ArrayList<>();
        for (Data item : items) {
            DataMap map = new DataMap();
            map.putString(KEY_TITLE, item.getTitle());
            map.putString(KEY_DATA, item.getData());
            map.putString(KEY_IMAGE, item.getImage());
            maps.add(map);
        }
        return maps;
    }

    public static List<Data> fromDataMapList(List<DataMap> maps) {
        List<Data> items = new ArrayList<>();
        if (maps == null) {
            return items;
        }
        for (DataMap map : maps) {
            Data data = new Data();
            data.setTitle(map.getString(KEY_TITLE));
            data.setData(map.getString(KEY_DATA));
            data.setImage(map.getString(KEY_IMAGE));
            items.add(data);
        }
        return items;
    }
}
