package com.example.fluke.liveat500px.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fluke.liveat500px.R;
import com.example.fluke.liveat500px.adapter.PhotoListAdapter;
import com.example.fluke.liveat500px.datatype.MutableInteger;
import com.example.fluke.liveat500px.deo.PhotoItemCollectionDeo;
import com.example.fluke.liveat500px.deo.PhotoItemDao;
import com.example.fluke.liveat500px.manager.Contextor;
import com.example.fluke.liveat500px.manager.HttpManger;
import com.example.fluke.liveat500px.manager.PhotoListManger;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {

    // Variables

    public interface FragmentListener {
        void onPhotoItemClicked(PhotoItemDao dao);
    }

    ListView listView;
    PhotoListAdapter listAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Button btnNewPhotos;

    PhotoListManger photoListManger;

    MutableInteger lastPositionInteger;

    /*******
     * Functions
     *******/

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
            // Restore Instance State
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle saveInstanceState) {
        // Initialize Fragment level's variable here
        photoListManger = new PhotoListManger();
        lastPositionInteger = new MutableInteger(-1);
    }

    private void initInstances(View rootView, Bundle saveInstanceState) {

        btnNewPhotos = (Button) rootView.findViewById(R.id.btnNewPhotos);

        btnNewPhotos.setOnClickListener(buttonClickListener);

        // init instance with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView);
        listAdapter = new PhotoListAdapter(lastPositionInteger);
        listAdapter.setDao(photoListManger.getDao());
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(listViewItemClickListener);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(pullToRefreshListener);
        listView.setOnScrollListener(listViewScrollListener);

        if (saveInstanceState == null)
        refreshData();
    }

    private void refreshData() {
        if (photoListManger.getCount() == 0)
            reloadData();
        else
            reloadDataNewer();
    }

    private void reloadDataNewer() {

        int maxId = photoListManger.getMaximumId();
        Call<PhotoItemCollectionDeo> call = HttpManger.getInstance().getService().loadPhotoListAfterId(maxId);
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD_NEWER));

    }

    private void reloadData() {
        Call<PhotoItemCollectionDeo> call = HttpManger.getInstance().getService().loadPhotoList();
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD));
    }

    boolean isLoadingMore = false;

    private void loadMoreData() {
        if (isLoadingMore)
            return;
        isLoadingMore =true;
        int minId = photoListManger.getMinimumId();
        Call<PhotoItemCollectionDeo> call = HttpManger.getInstance().getService().loadPhotoListBeforeId(minId);
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_LOAD_MORE));

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
        outState.putBundle("photoListManager", photoListManger.onSaveInstanceState());
        outState.putBundle("lastPositionInteger", lastPositionInteger.onSaveInstanceState());
    }

    private void onRestoreInstanceState(Bundle saveInstanceState) {
        // Restore Instance State here
        photoListManger.onRestoreInstance(saveInstanceState.getBundle("photoListManager"));
        lastPositionInteger.onRestoreInstanceState(saveInstanceState.getBundle("lastPositionInteger"));
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void showButtonNewPhotos() {
        btnNewPhotos.setVisibility(View.VISIBLE);
        Animation anim = AnimationUtils.loadAnimation(Contextor.getInstance().getContext(), R.anim.zoom_fade_in);
        btnNewPhotos.startAnimation(anim);
    }

    private void hideButtonNewPhotos() {
        btnNewPhotos.setVisibility(View.GONE);
        Animation anim = AnimationUtils.loadAnimation(Contextor.getInstance().getContext(), R.anim.zoom_fade_out);
        btnNewPhotos.startAnimation(anim);
    }

    private void showToast(String text) {
        Toast.makeText(Contextor.getInstance().getContext(),text, Toast.LENGTH_LONG).show();
    }

    /*****************
     * Listener Zone
     *****************/

    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnNewPhotos) {
                listView.smoothScrollToPosition(0);
                hideButtonNewPhotos();
            }
        }
    };

    SwipeRefreshLayout.OnRefreshListener pullToRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshData();
        }
    };

    AbsListView.OnScrollListener listViewScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {

        }

        @Override
        public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            if (absListView == listViewScrollListener) {
                swipeRefreshLayout.setEnabled(i == 0);
                if (i + i1 >= i2) {
                    if (photoListManger.getCount() > 0) {
                        // Load More
                        loadMoreData();
                    }
                }
            }
        }
    };

    AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position < photoListManger.getCount()) {
                PhotoItemDao dao = photoListManger.getDao().getData().get(position);
                FragmentListener listener = (FragmentListener) getActivity();
                listener.onPhotoItemClicked(dao);
            }
        }
    };

    /*********
     * Inner Class
     ********/

    class PhotoListLoadCallback implements Callback<PhotoItemCollectionDeo> {

        public static final int MODE_RELOAD = 1;
        public static final int MODE_RELOAD_NEWER = 2;
        public static final int MODE_LOAD_MORE = 3;

        int mode;

        public PhotoListLoadCallback(int mode) {
            this.mode = mode;
        }

        @Override
        public void onResponse(Call<PhotoItemCollectionDeo> call, Response<PhotoItemCollectionDeo> response) {
            swipeRefreshLayout.setRefreshing(false);
            if (response.isSuccess()) {
                PhotoItemCollectionDeo dao = response.body();

                int firstVisiblePosition = listView.getFirstVisiblePosition();
                View c = listView.getChildAt(0);
                int top = c == null ? 0 : c.getTop();

                if (mode == MODE_RELOAD_NEWER) {
                    photoListManger.insertDaoAtTopPosition(dao);
                }
                else if (mode == MODE_LOAD_MORE) {
                    photoListManger.appendDaoAtButtonPosition(dao);
                } else {
                    photoListManger.setDao(dao);
                }
                clearLoadingMoreFlagIfCapable(mode);
                listAdapter.setDao(photoListManger.getDao());
                listAdapter.notifyDataSetChanged();

                if (mode == MODE_RELOAD_NEWER) {
                    // Maintain Scroll Position
                    int additionalSize =
                            (dao != null && dao.getData() != null) ? dao.getData().size() : 0;
                    listAdapter.increaseLastPosition(additionalSize);
                    listView.setSelectionFromTop(firstVisiblePosition + additionalSize, top);
                    if (additionalSize > 0)
                        showButtonNewPhotos();
                } else {

                }
                showToast("Load Completed");
            } else {
                // Handle
                clearLoadingMoreFlagIfCapable(mode);
                try {
                    showToast(response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<PhotoItemCollectionDeo> call, Throwable t) {
            // Handle
            // TODO Clear Loading More Flag
            clearLoadingMoreFlagIfCapable(mode);
            swipeRefreshLayout.setRefreshing(false);
            showToast(t.toString());
        }

        private void clearLoadingMoreFlagIfCapable(int mode) {
            if (mode == MODE_LOAD_MORE)
                isLoadingMore = false;
        }

    }
}
