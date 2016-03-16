package com.example.fluke.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import com.example.fluke.liveat500px.R;
import com.example.fluke.liveat500px.datatype.MutableInteger;
import com.example.fluke.liveat500px.deo.PhotoItemCollectionDeo;
import com.example.fluke.liveat500px.deo.PhotoItemDao;
import com.example.fluke.liveat500px.view.PhotoListItem;

/**
 * Created by FLUKE on 2/18/2016 AD.
 */
public class PhotoListAdapter extends BaseAdapter {

    PhotoItemCollectionDeo dao;

    MutableInteger lastPositionInteger;

    public PhotoListAdapter(MutableInteger lastPositionInteger) {
        this.lastPositionInteger = lastPositionInteger;
    }

    public void setDao(PhotoItemCollectionDeo dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if (dao == null)
            return 1;
        if (dao.getData() == null)
            return 1;
        return dao.getData().size() + 1;
    }

    @Override
    public Object getItem(int i) {
        return dao.getData().get(i);
    }

    @Override

    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position == getCount() - 1 ? 1 : 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (i == getCount() - 1) {
            // Progress bar
            ProgressBar item;
            if (view != null)
                item = (ProgressBar) view;
            else
                item = new ProgressBar(viewGroup.getContext());
            return item;
        }
            PhotoListItem item;
            if (view != null)
                item = (PhotoListItem) view;
            else
                item = new PhotoListItem(viewGroup.getContext());

        PhotoItemDao dao = (PhotoItemDao) getItem(i);
        item.setNameText(dao.getCaption());
        item.setDescriptionText(dao.getUsername() + "\n" + dao.getCamera());
        item.setImageUrl(dao.getImageUrl());

        if (i > lastPositionInteger.getValue()) {
            Animation anim = AnimationUtils.loadAnimation(viewGroup.getContext(),
                    R.anim.up_form_bottom);
            item.startAnimation(anim);
            lastPositionInteger.setValue(i);
        }

            return item;
    }

    public void increaseLastPosition (int amount) {
        lastPositionInteger.setValue(lastPositionInteger.getValue() + amount);
    }
}
