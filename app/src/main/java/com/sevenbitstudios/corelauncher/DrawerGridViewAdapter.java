package com.sevenbitstudios.corelauncher;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public class DrawerGridViewAdapter extends BaseAdapter {
    Context context;
    List<AppInfo> appListDataSet;

    public DrawerGridViewAdapter(Context context, List<AppInfo> appListData){
        this.context = context;
        this.appListDataSet = appListData;
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

        ConstraintLayout appTile = v.findViewById((R.id.appItemLayout));
        ImageView mImage = v.findViewById(R.id.appItemIcon);
        TextView mLabel = v.findViewById(R.id.appItemNameLabel);

        mImage.setImageDrawable(appListDataSet.get(position).getIcon());
        mLabel.setText(appListDataSet.get(position).getName());

        appTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launAppIntent = context.getPackageManager().getLaunchIntentForPackage(appListDataSet.get(position).getPackageName());

                if (launAppIntent != null){
                    context.startActivity(launAppIntent);
                }
            }
        });

        return v;
    }
}
