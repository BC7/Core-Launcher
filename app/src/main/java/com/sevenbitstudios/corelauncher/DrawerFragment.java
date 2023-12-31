package com.sevenbitstudios.corelauncher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DrawerFragment extends Fragment {
    private static final String TAG = "DrawerFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 4;
    private static final int DATASET_COUNT = 32;
    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected DrawerRecyclerViewAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<AppInfo> mDataset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drawer, container, false);
        rootView.setTag(TAG);

        initSampleDataset();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.appList);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new DrawerRecyclerViewAdapter(rootView.getContext(), mDataset);

        mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;

        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }


    private void initSampleDataset() {
        for (int i = 0; i < DATASET_COUNT; i++) {
            final AppInfo appSample = new AppInfo(String.valueOf(i), String.valueOf(i), ResourcesCompat.getDrawable(getResources(), R.drawable.ic_launcher_foreground, null));
            mDataset.add(appSample);
        }
    }
}