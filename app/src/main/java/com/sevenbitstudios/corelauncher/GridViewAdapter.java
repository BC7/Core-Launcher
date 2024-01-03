package com.sevenbitstudios.corelauncher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    Context context;
    List<AppInfo> appListDataSet;
    EventListener listener;
    int cellHeight;

    public interface EventListener {
        void appItemOnClick(AppInfo appInfo);
        void appItemOnLongClick(AppInfo appInfo);

    }

    public GridViewAdapter(Context context, List<AppInfo> appListData, int cellHeight, EventListener listener){

        this.context = context;
        this.appListDataSet = appListData;
        this.cellHeight = cellHeight;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return appListDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return appListDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.app_item, parent, false);
        } else {
            v = convertView;
        }

        ConstraintLayout appTileLayout = v.findViewById((R.id.appItemLayout));
        ImageView mImage = v.findViewById(R.id.appItemIcon);
        TextView mLabel = v.findViewById(R.id.appItemNameLabel);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, cellHeight);

        mImage.setImageDrawable(appListDataSet.get(position).getIcon());
        mLabel.setText(appListDataSet.get(position).getName());
        appTileLayout.setLayoutParams(layoutParams);

        appTileLayout.setOnClickListener(view -> {
            listener.appItemOnClick(appListDataSet.get(position));
        });
        appTileLayout.setOnLongClickListener(view -> {
            listener.appItemOnLongClick(appListDataSet.get(position));
            return true;
        });

        return v;
    }
}
