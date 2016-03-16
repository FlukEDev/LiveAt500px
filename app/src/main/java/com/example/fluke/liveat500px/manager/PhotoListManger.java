package com.example.fluke.liveat500px.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.fluke.liveat500px.deo.PhotoItemCollectionDeo;
import com.example.fluke.liveat500px.deo.PhotoItemDao;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PhotoListManger {

    private Context mContext;
    private PhotoItemCollectionDeo dao;

    public PhotoItemCollectionDeo getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDeo dao) {
        this.dao = dao;
        saveCache();
    }

    public void insertDaoAtTopPosition(PhotoItemCollectionDeo newDao) {

        if (dao == null)
            dao = new PhotoItemCollectionDeo();
        if (dao.getData() == null)
            dao.setData(new ArrayList<PhotoItemDao>());
        dao.getData().addAll(0, newDao.getData());
        saveCache();

    }

    public void appendDaoAtButtonPosition(PhotoItemCollectionDeo newDao) {

        if (dao == null)
            dao = new PhotoItemCollectionDeo();
        if (dao.getData() == null)
            dao.setData(new ArrayList<PhotoItemDao>());
        dao.getData().addAll(dao.getData().size(), newDao.getData());
        saveCache();
    }

    public PhotoListManger() {
        mContext = Contextor.getInstance().getContext();
        // Load data from Persistent Storage
        loadCache();
    }

    public int getMaximumId() {
        if (dao == null)
            return 0;
        if (dao.getData() == null)
            return 0;
        if (dao.getData().size() == 0)
            return 0;
        int maxId = dao.getData().get(0).getId();
        for (int i = 1; i < dao.getData().size(); i++)
            maxId = Math.max(maxId, dao.getData().get(i).getId());
        return maxId;
    }

    public int getMinimumId() {
        if (dao == null)
            return 0;
        if (dao.getData() == null)
            return 0;
        if (dao.getData().size() == 0)
            return 0;
        int minId = dao.getData().get(0).getId();
        for (int i = 1; i < dao.getData().size(); i++)
            minId = Math.min(minId, dao.getData().get(i).getId());
        return minId;
    }

    public int getCount() {
        if (dao == null)
            return 0;
        if (dao.getData() == null)
            return 0;
        return dao.getData().size();
    }

    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("dao", dao);
        return bundle;
    }

    public void onRestoreInstance(Bundle saveInstanceState) {
        dao = saveInstanceState.getParcelable("dao");
    }

    private void saveCache() {

        PhotoItemCollectionDeo cacheDao = new PhotoItemCollectionDeo();
        if (dao != null && dao.getData() != null)
        cacheDao.setData(dao.getData().subList(0, Math.min(20, dao.getData().size())));
        String json = new Gson().toJson(cacheDao);

        SharedPreferences prefs = mContext.getSharedPreferences("photo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        // Add / Edit / Delete
        editor.putString("json", json);
        editor.apply();
    }

    private void loadCache() {
        SharedPreferences prefs = mContext.getSharedPreferences("photo", Context.MODE_PRIVATE);
        String json = prefs.getString("json", null);
        if (json == null)
            return;
        dao= new Gson().fromJson(json, PhotoItemCollectionDeo.class);
    }

}
