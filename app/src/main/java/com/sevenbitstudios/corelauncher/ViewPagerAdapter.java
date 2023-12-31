package com.sevenbitstudios.corelauncher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<PagerObj> appList;
    ArrayList<GridViewAdapter> gridViewAdapterList = new ArrayList<>();
    int cellHeight;
    GridViewAdapter.EventListener listener;

    public ViewPagerAdapter(Context context, ArrayList<PagerObj> pagerAppList, int cellHeight, GridViewAdapter.EventListener listener) {
        this.context = context;
        this.appList = pagerAppList;
        this.cellHeight = cellHeight;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Object instantiateItem (@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.pager_layout, container, false);

        final GridView cPagerGridView = layout.findViewById(R.id.pager_grid_view);
        GridViewAdapter gridViewAdapter = new GridViewAdapter(context, appList.get(position).getAppList(), cellHeight, this.listener);
        cPagerGridView.setAdapter(gridViewAdapter);

        gridViewAdapterList.add(gridViewAdapter);

        container.addView(layout);
        return layout;
    }

    @Override
    public int getCount() {
        return this.appList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void notifyGridChange(){
        for (int i = 0; i < gridViewAdapterList.size(); i++) {
            gridViewAdapterList.get(i).notifyDataSetChanged();
        }
    }
}
